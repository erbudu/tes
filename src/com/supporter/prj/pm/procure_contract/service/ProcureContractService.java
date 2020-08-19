package com.supporter.prj.pm.procure_contract.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.com_codetable.dao.CodetableItemDao;
import com.supporter.prj.eip.com_codetable.entity.CodetableItem;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.procure_contract.dao.ProcureContractContentDao;
import com.supporter.prj.pm.procure_contract.dao.ProcureContractDao;
import com.supporter.prj.pm.procure_contract.dao.ProcureContractPayDao;
import com.supporter.prj.pm.procure_contract.dao.ProcureContractSiteDao;
import com.supporter.prj.pm.procure_contract.entity.ProcureContract;
import com.supporter.prj.pm.procure_contract.entity.ProcureContractContent;
import com.supporter.prj.pm.procure_contract.entity.ProcureContractPay;
import com.supporter.prj.pm.procure_contract.entity.ProcureContractSite;
import com.supporter.prj.pm.util.AuthUtils;

/**
 * @Title: procureContractService
 * @Description: 业务操作类
 * @author liyinfeng
 * @date 2018-6-14
 * @version: V1.0
 */
@Service
public class ProcureContractService {
	public static final String MODULE_ID = "PURCONTRACT"; //应用ID
	public static final String AUTH_EDIT = "edit"; //权限-编辑采购合同
	public static final String FILE_CONTRACT = "file"; //采购合同附件
	public static final String LOG_EFFECT = "EFFECT"; //日志-采购合同生效

	@Autowired
	private ProcureContractDao procureContractDao;
	@Autowired
	private ProcureContractContentDao contractContentDao;
	@Autowired
	private ProcureContractContentDao goodsDao;
	@Autowired
	private ProcureContractPayDao payDao;
	@Autowired
	private ProcureContractSiteDao projectSiteDao;
	
	@Autowired
	private CodetableItemDao codetableItemDao;

	/**
	 * 获取预付款项
	 * @param contractId 合同ID
	 * @return ProcureContractPay
	 */
	public ProcureContractPay getYFK(String contractId) {
		String payItemId = "预付款";
		return payDao.getByPayItemId(contractId, payItemId);
	}
	
	public List<ProcureContractPay> getListYFK(String contractId) {
		String payItemId = "预付款";
		return payDao.getListByPayItemId(contractId, payItemId);
	}

	/**
	 *  数据维护权限过滤权限验证
	 * @param user 用户
	 * @param procureContract 采购合同
	 */
	public void getAuthCanExecute(UserProfile user, ProcureContract procureContract) {
		AuthUtils.canExecute(user, MODULE_ID, AUTH_EDIT,
				procureContract.getContractId(), procureContract);
	}
	
	/**
	 * 获取销售合同上线对象集合
	 * @param user 用户
	 * @param jqGrid 表格参数
	 * @param procureContract 采购合同对象
	 * @param map 
	 * @return List<ProcureContract>
	 */
	public List < ProcureContract > getGrid(UserProfile user, JqGrid jqGrid,
			ProcureContract procureContract, Map<String, Object> map) {
		String authFilter = EIPService.getAuthorityService().getHqlFilter(user, MODULE_ID, AUTH_EDIT);
		
		List< ProcureContract > list = procureContractDao.findPage(jqGrid, procureContract, authFilter, map);

		return list;
	}

	/**
	 * 获取涉及工程部位树状列表.
	 * @param jqGrid 表格参数
	 * @param contractId 采购合同ID
	 * @return List<ProcureContractSite>
	 */
	@SuppressWarnings("unchecked")
	public List<ProcureContractSite> getTreeGrid(JqGrid jqGrid, String contractId) {
		List<ProcureContractSite> list = this.projectSiteDao.findPage(jqGrid, contractId);
		if (list == null || list.size() == 0) {
			return null;
		}

		//树层次排序(level+isLeaf+rootParentNull)
		List<ProcureContractSite> newList = orderTreeLevel(list);

		jqGrid.setRows(newList);
		return newList;
	}
	
	/**
	 * 树层次排序(level+isLeaf+rootParentNull)
	 * @param list 树节点列表
	 * @return List<ProcureContractSite>
	 */
	private List<ProcureContractSite> orderTreeLevel(List<ProcureContractSite> list) {
		if (list == null || list.size() == 0) {
			return null;
		}
		
		int listSize = list.size();
		List < ProcureContractSite > treeNodes = new ArrayList < ProcureContractSite >();
		Stack < ProcureContractSite > stack = new Stack < ProcureContractSite >();
		
		//找根节点
		for (int i = 0; i < listSize; i++) {
			ProcureContractSite site = list.get(i);
			if (StringUtils.isBlank(site.getParentSiteId())) {
				site.setLevel(0);
				site.setIsLeaf(true);
				site.setParentSiteId(null);
				stack.push(site);
				break;
			}
		}
		//遍历生成子树节点
		while (!stack.isEmpty()) {
			ProcureContractSite node = stack.pop();
			String id = node.getSiteId();
			//获得节点的子节点
			for (int i = listSize - 1; i >= 0; i--) {
				ProcureContractSite temp = list.get(i);
				String parentId = temp.getParentSiteId();
				
				if (StringUtils.isBlank(parentId)) {
					continue;
				}
				if (id.equals(parentId)) {
					node.setIsLeaf(false);
					temp.setLevel(node.getLevel() + 1);
					temp.setIsLeaf(true);
					stack.add(temp);
				}
			}
			treeNodes.add(node);
	    }
		return treeNodes;
	}

	/**
	 * 查找节点
	 * @param list 列表
	 * @param id 树节点ID
	 * @return ProcureContractSite
	 */
	private ProcureContractSite findSiteBySiteId(List<ProcureContractSite> list, String id) {
		int len = list.size();
		for (int i = 0; i < len; i++) {
			ProcureContractSite node = list.get(i);
			if (id.equals(node.getSiteId())) {
				return node;
			}
		}
		return null;
	}
	
	
	/**
	 * 获取货物及服务明细集合
	 * @param user 用户
	 * @param jqGrid 表格参数
	 * @param contractId 采购合同ID
	 * @return List<ProcureContractContent>
	 */
	public List<ProcureContractContent> getGoodsGrid(UserProfile user, JqGrid jqGrid, String contractId,String buyItem) {
		return this.goodsDao.findPage(jqGrid, contractId,buyItem);
	}

	/**
	 * 获取收款条件集合
	 * @param user
	 * @param jqGrid
	 * @param contractId
	 * @return
	 */
	public List <ProcureContractPay> getPayGrid(JqGrid jqGrid, String contractId) {
		return this.payDao.findPage(jqGrid, contractId);
	}
	
	public List <ProcureContractPay> getPayGridToChange(JqGrid jqGrid, String contractId) {
		return this.payDao.getPayGridToChange(jqGrid, contractId);
	}
	
	public List <ProcureContractPay> getPaymentGrid(JqGrid jqGrid,ProcureContractPay contractPay, String contractId) {
		return this.payDao.findPaymentPage(jqGrid,contractPay, contractId);
	}

	/**
	 * 获取单个销售合同上线对象
	 * @param contractId 采购合同ID
	 * @return ProcureContract
	 */
	public ProcureContract get(String contractId) {
		ProcureContract procureContract = this.procureContractDao.get(contractId);
		return procureContract;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param contractId 主键
	 * @param contractType 采购类型
	 * @param user 用户
	 * @return ProcureContract
	 */
	public ProcureContract initEditOrViewPage(String contractId, int contractType, UserProfile user) {
		ProcureContract procureContract = null;
		if (StringUtils.isNotBlank(contractId)) {
			procureContract = (ProcureContract) this.procureContractDao.get(contractId);
		}
		
		if (procureContract != null) {
			procureContract.setIsNew(false);
			String relativeContractId = procureContract.getRelativeContractId();
			if (StringUtils.isNotBlank(relativeContractId)) {
				ProcureContract relativeContract = procureContractDao.get(relativeContractId);
				if (relativeContract != null) {
					procureContract.setRelativeContractName(relativeContract.getContractName());
				}
			}

		}
		
		return procureContract;
	}
	
	
	//获取币别码表中兑人民币的汇率
	 public double getCnyRateByCurrency(String currencyId){
		String hql = "from CodetableItem where codetableItemId = ? and codetableId='CNEEC_CURRENCY'";
		List<CodetableItem> areaItemList = codetableItemDao.find(hql,currencyId);
		if(areaItemList != null && areaItemList.size()>0){
			return Double.parseDouble(areaItemList.get(0).getExtField1());
		}else{
			return 0;
		}
	 }
	//获取币别码表中兑美元的汇率
	 public double getUsdRateByCurrency(String currencyId){
		String hql = "from CodetableItem where codetableItemId = ? and codetableId='CNEEC_CURRENCY'";
		List<CodetableItem> areaItemList = codetableItemDao.find(hql,currencyId);
		if(areaItemList != null && areaItemList.size()>0){
			return Double.parseDouble(areaItemList.get(0).getExtField2());
		}else{
			return 0;
		}
	 }
	 
	//double比较
	public boolean equal(double a, double b) {
        if ((a- b> -0.0001) && (a- b) < 0.0001)
            return true;
        else
            return false;
    }


	public ProcureContractPay getByPayItemId(String contractId,String payItemId){
		return payDao.getByPayItemId(contractId,payItemId);
	}
	
	/**
	 * 获取合同的预付款
	 * @param contractId 合同ID
	 * @return double
	 */
	public double getAdvancePayment(String contractId, String calculationRate) {
		double calRate = 0d;
		if(calculationRate != null && !calculationRate.equals("")){
			calRate = Double.parseDouble(calculationRate);
		}
		List<ProcureContractPay> items = payDao.getByContractId(contractId);
		if (items == null || items.size() == 0) {
			return 0D;
		}
		double payment = 0D;
		for (int i = 0; i < items.size(); i++) {
			ProcureContractPay item = items.get(i);
			if ("预付款".equals(item.getPayItem())) {
				if(item.getCurrencyId().equals("USD")){//将美元转化为结算币金额
					payment += item.getAmount()*calRate;
				}else{
					payment += item.getAmount();
				}
			}
		}
		return payment;
	}
	
	/**
	 * 获取合同的预付款规则定义金额
	 * @param contractId 合同ID
	 * @return double
	 */
	public double getDefPayment(String contractId, String calculationRate) {
		double calRate = 0d;
		if(calculationRate != null && !calculationRate.equals("")){
			calRate = Double.parseDouble(calculationRate);
		}
		List<ProcureContractPay> items = payDao.getByContractId(contractId);
		if (items == null || items.size() == 0) {
			return 0D;
		}
		double payment = 0D;
		for (int i = 0; i < items.size(); i++) {
			ProcureContractPay item = items.get(i);
			if ("预付款".equals(item.getPayItem())) {
				if(item.getDeductionRule().equals(ProcureContractPay.DeductionRules.DENGE)){//等额
					if(item.getCurrencyId().equals("USD")){//将美元转化为结算币金额
						payment += item.getRuleDefinition()*calRate;
					}else{
						payment += item.getRuleDefinition();
					}
				}
				if(item.getDeductionRule().equals(ProcureContractPay.DeductionRules.DENGBILI)){//等比例
					if(item.getCurrencyId().equals("USD")){//将美元转化为结算币金额
						payment += ((item.getAmount()*item.getRuleDefinition())/100)*calRate;
					}else{
						payment += (item.getAmount()*item.getRuleDefinition())/100;
					}
				}
			}
		}
		return payment;
	}
	
	
	public ProcureContractContent getByContractId(String contractId){
		return contractContentDao.getByContractId(contractId);
	}
	
	/**
     * 获取导入动态列
     * @param dbYear 年库
     * @param user
     * @param memberId
     * @return
     */
	public List<String> getImprotExpFields (UserProfile user, String contractId){
	    List<String> colNames = new ArrayList<String>();
	    
	    ProcureContractContent procureContractContent = this.getByContractId(contractId);
	    
	    // 获取基本属性列
	    colNames = this.getBaseExpFields(procureContractContent);
	       
	    return colNames;
	}
	
	/**
     * 获取基本属性导出有效列
     * 
     * @param dimension
     * @return
     */
	public List<String> getBaseExpFields(ProcureContractContent procureContractContent){ 
	    List<String> expFields = new ArrayList<String>();
	    expFields.add("buyItem");
        expFields.add("unit");
        expFields.add("specification");
        expFields.add("buyCount");
        expFields.add("price");
        expFields.add("budgetItemId");
        return expFields;
	}

	public void changeStatusScan(UserProfile user, String id) {
		if (StringUtils.isNotBlank(id)) {
			ProcureContract procureContract = this.procureContractDao.get(id);
			procureContract.setStatusScan(1);
			procureContractDao.update(procureContract);
		}
	}

}
