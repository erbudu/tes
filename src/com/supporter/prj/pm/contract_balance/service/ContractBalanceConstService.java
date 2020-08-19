package com.supporter.prj.pm.contract_balance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.constant.PmSwfConstant;
import com.supporter.prj.pm.contract_balance.dao.ContractBalanceConstDao;
import com.supporter.prj.pm.contract_balance.dao.ContractBalanceConstSiteDao;
import com.supporter.prj.pm.contract_balance.dao.ContractBalanceConstSwfDao;
import com.supporter.prj.pm.contract_balance.dao.ContractBalanceVisaDao;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceConst;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceConstSite;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceConstSwf;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceVisa;
import com.supporter.prj.pm.enginee_negotiate.dao.EngineeVisaDao;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisa;
import com.supporter.prj.pm.util.PmSwfUtil;

/**   
 * @Title: 合同结算.施工合同
 * @Description: PM_CONTRACT_BALANCE_CONST.
 * @author Administrator
 * @date 2018-07-04 18:07:38
 * @version V1.0   
 *
 */
@Service
public class ContractBalanceConstService {
	public static final String MODULE_ID = "CONTRBALANC"; //应用ID
	
	public static final String AUTH_EDIT = "edit"; //权限-编辑
	public static final String FILE = "file"; //附件
	
	@Autowired
	private ContractBalanceConstDao balanceDao;
	@Autowired
	private ContractBalanceVisaDao balanceVisaDao;
	@Autowired
	private ContractBalanceConstSiteDao balanceSiteDao;
	@Autowired
	private EngineeVisaDao engineeVisaDao;
	@Autowired
	private ContractBalanceConstSwfDao balanceSwfDao;
	
	/**
	 * 根据主键获取PM_ENGINEE_NEGOTIATE.
	 * 
	 * @param balanceId 主键
	 * @return ContractBalanceConst
	 */
	public ContractBalanceConst get(String balanceId) {
		return  balanceDao.get(balanceId);
	}
	
	/**
	 * 分页表格展示数据.
	 * @param user 用户
	 * @param jqGrid 请求表格参数
	 * @param balance 结算对象
	 * @return List< ContractBalanceConst >
	 */
	public List< ContractBalanceConst > getGrid(UserProfile user, JqGrid jqGrid, ContractBalanceConst balance) {
		List< ContractBalanceConst > list = balanceDao.findPage(user, jqGrid, balance);

		return list;
	}
	
	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * @param user 用户
	 * @param balanceId 结算ID
	 * @param contractId 合同ID
	 * @param periodStartDate 结算期间开始日期
	 * @param periodEndDate 结算期间结束日期
	 * @return ContractBalanceConst
	 */
	public ContractBalanceConst initEditOrViewPage(UserProfile user, String balanceId, String contractId,
			Date periodStartDate, Date periodEndDate, String calculationRate, String isFinalBalance,String visaIds) {
		ContractBalanceConst entity = null;
		//long time1 = System.currentTimeMillis();
		if (StringUtils.isNotBlank(balanceId)) {
			entity = balanceDao.get(balanceId);
		}
		if (entity != null) {
			JqGrid jqGrid = new JqGrid();
			jqGrid.setPageSize(0); //不分页
			//long time2 = System.currentTimeMillis();
			List < ContractBalanceConstSite > sites = getSiteTreeGrid(jqGrid, balanceId);
			entity.setSites(sites);

			List<ContractBalanceVisa> listVisa = this.balanceVisaDao.getListContractBalanceVisa(balanceId);
			String inquiryVisaIds = "";
			String inquiryVisaNo = "";
			for (int i = 0; i < listVisa.size(); i++) {
				if (i == listVisa.size() - 1) {
					inquiryVisaIds += listVisa.get(i).getVisaId();
					if(listVisa.get(i).getVisaId() != null) {
						EngineeVisa engineeVisa = engineeVisaDao.get(listVisa.get(i).getVisaId());
						if(engineeVisa != null) {
							inquiryVisaNo += engineeVisa.getApplyNo();
						}
					}
				} else {
					if(listVisa.get(i).getVisaId() != null) {
						EngineeVisa engineeVisa = engineeVisaDao.get(listVisa.get(i).getVisaId());
						if(engineeVisa != null) {
							inquiryVisaNo += engineeVisa.getApplyNo() + ",";
						}
					}
					inquiryVisaIds += listVisa.get(i).getVisaId() + ",";
				}
			}
			entity.setInquiryVisaIds(inquiryVisaIds);
			entity.setInquiryVisaNo(inquiryVisaNo);
		}
		
		
		return entity;
	}
	
	/**
	 * 获取涉及工程部位树状列表.
	 * @param jqGrid 表格参数
	 * @param balanceId 结算ID
	 * @return List<ContractBalanceConstSite>
	 */
	@SuppressWarnings("unchecked")
	private List<ContractBalanceConstSite> getSiteTreeGrid(JqGrid jqGrid, String balanceId) {
		List<ContractBalanceConstSite> list = balanceSiteDao.findPage(jqGrid, balanceId);
		if (list == null || list.size() == 0) {
			return null;
		}

		//树层次排序(level+isLeaf+rootParentNull)
		List<ContractBalanceConstSite> newList = orderTreeLevel(list);
		
		jqGrid.setRows(newList);
		return newList;
	}
	
	/**
	 * 树层次排序(level+isLeaf+rootParentNull),支持多棵树
	 * @param list 树节点列表
	 * @return List<ContractBalanceConstSite>
	 */
	private List<ContractBalanceConstSite> orderTreeLevel(List<ContractBalanceConstSite> list) {
		if (list == null || list.size() == 0) {
			return null;
		}
		int listSize = list.size();
		//根节点集合
		List <ContractBalanceConstSite> rootList = new ArrayList <ContractBalanceConstSite>();
		//找根节点
		for (int i = 0; i < listSize; i++) {
			ContractBalanceConstSite site = list.get(i);
			if (StringUtils.isBlank(site.getParentSiteId())) {
				site.setLevel(0);
				site.setIsLeaf(true);
				site.setParentSiteId(null);
				rootList.add(site);
			}
		}
		
		List < ContractBalanceConstSite > treeNodes = new ArrayList < ContractBalanceConstSite >();
		for (int i = 0; i < rootList.size(); i++) {
			Stack < ContractBalanceConstSite > stack = new Stack< ContractBalanceConstSite >();
			
			ContractBalanceConstSite root = rootList.get(i);
			stack.push(root);
			//遍历生成子树节点
			while (!stack.isEmpty()) {
				ContractBalanceConstSite node = stack.pop();
				String id = node.getSiteId();
				//获得节点的子节点
				for (int j = listSize - 1; j >= 0; j--) {
					ContractBalanceConstSite temp = list.get(j);
					String parentId = temp.getParentSiteId();
					
					if (id.equals(parentId)) {
						node.setIsLeaf(false);
						temp.setLevel(node.getLevel() + 1);
						temp.setIsLeaf(true);
						stack.push(temp);
					}
				}
				treeNodes.add(node);
		    }
		}

		return treeNodes;
	}
	
	/**
	 * 判断指定字段唯一性
	 * 
	 * @param balanceId 结算ID
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return boolean
	 */
	public boolean checkPropertyUniquenes(String balanceId, String propertyName, String propertyValue) {
		return this.balanceDao.checkPropertyUniquenes(balanceId, propertyName, propertyValue);
	}
	
	/**
	 * 获取WBS的ZTREE树
	 * @param balanceId 结算ID
	 * @return List <ContractBalanceConstSite>
	 */
	public List <ContractBalanceConstSite> getSiteTree(String balanceId) {
		JqGrid jqGrid = new JqGrid();
		jqGrid.setPageSize(0); //不分页
		List<ContractBalanceConstSite> list = balanceSiteDao.findPage(jqGrid, balanceId);
		return list;
	}
	
	/**
	 * 批量启动流程
	 * @param creatorId 创建者ID
	 */
	public void startProcBatch(String creatorId) {
		List<ContractBalanceConstSwf> swfs = balanceSwfDao.getNotStartProcList();
		if (swfs != null && swfs.size() > 0) {
			int size = swfs.size();
			for (int i = 0; i < size; i++) {
				ContractBalanceConstSwf swf = swfs.get(i);
				swf.setBalance(this.get(swf.getBalanceId()));
				try {
					PmSwfUtil.startProc(swf, creatorId,
							PmSwfConstant.PM_SWF_PROC_INNER_NAME_BALANCE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}

