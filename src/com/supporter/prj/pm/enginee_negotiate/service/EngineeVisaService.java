package com.supporter.prj.pm.enginee_negotiate.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.codetable.entity.ICodeTable;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.constant.PmSwfConstant;
import com.supporter.prj.pm.enginee_negotiate.dao.EngineeVisaDao;
import com.supporter.prj.pm.enginee_negotiate.dao.EngineeVisaMeetDao;
import com.supporter.prj.pm.enginee_negotiate.dao.EngineeVisaSiteDao;
import com.supporter.prj.pm.enginee_negotiate.dao.EngineeVisaSwfDao;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisa;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisaMeet;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisaSite;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisaSwf;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisaSwf.OAExamStatus;
import com.supporter.prj.pm.util.PmSwfUtil;

/**   
 * @Title: 签证
 * @Description: PM_ENGINEE_VISA.
 * @author Administrator
 * @date 2018-07-04 18:08:55
 * @version V1.0   
 *
 */
@Service
public class EngineeVisaService {
	@Autowired
	private EngineeVisaDao engineeVisaDao;
	@Autowired
	private EngineeVisaSiteDao engineeVisaSiteDao;
	@Autowired
	private EngineeVisaSwfDao engineeVisaSwfDao;
	@Autowired
	private EngineeVisaMeetDao engineeVisaMeetDao;
	
	public static final String CTBL_APPLY_TYPE = "VISA_APPLY_TYPE"; //签证申请类型（工程变更/施工签证）

	/**
	 * 根据主键获取PM_ENGINEE_NEGOTIATE.
	 * 
	 * @param visaId 主键
	 * @return EngineeVisa
	 */
	public EngineeVisa get(String visaId) {
		return  engineeVisaDao.get(visaId);
	}
	
	/**
	 * 分页表格展示数据.
	 * @param user 用户
	 * @param jqGrid 请求表格参数
	 * @param visa 签证对象
	 * @return List< EngineeVisa >
	 */
	public List< EngineeVisa > getGrid(UserProfile user, JqGrid jqGrid, EngineeVisa visa) {
		List< EngineeVisa > list = engineeVisaDao.findPage(user, jqGrid, visa);
		
		return list;
	}

	
	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * @param visaId 签证ID
	 * @param negotiateId 洽商ID
	 * @param siteId 工程部位ID
	 * @return EngineeVisa
	 */
	public EngineeVisa initEditOrViewPage(UserProfile user, String visaId, String negotiateId, String siteId) {
		EngineeVisa entity = null;
		if (StringUtils.isNotBlank(visaId)) {
			entity = engineeVisaDao.get(visaId);
		}
		
		if (entity != null) {
			String treePath = "";
//			String wbsId =getParSiteId("visaId",visaId);
//			String parentSiteId =getParSiteId("wbsId",wbsId);
//			while(true) {
//				if(StringUtils.isNotBlank(parentSiteId)) {
//					 parentSiteId =getParSiteId("siteId",parentSiteId);
//				}else {
//					 treePath = getParSiteId(" "," ");
//					 entity.setTreePath(treePath);
//					 break;
//				}
//			}
			
			JqGrid jqGrid = new JqGrid();
			List < EngineeVisaSite > sites = getSiteTreeGrid(jqGrid, visaId);
			entity.setSites(sites);
			
		}
		
		return entity;
	}

	
	/**
	 * 获取涉及工程部位树状列表.
	 * @param jqGrid 表格参数
	 * @param visaId 签证ID
	 * @return List<EngineeVisaSite>
	 */
	@SuppressWarnings("unchecked")
	public List<EngineeVisaSite> getSiteTreeGrid(JqGrid jqGrid, String visaId) {
		List<EngineeVisaSite> list = engineeVisaSiteDao.findPage(jqGrid, visaId);
		if (list == null || list.size() == 0) {
			return null;
		}

		//树层次排序(level+isLeaf+rootParentNull)
		List<EngineeVisaSite> newlist = orderTreeLevel(list);

		jqGrid.setRows(newlist);
		return newlist;
	}
	
	
	/**
	 * 树层次排序(level+isLeaf+rootParentNull)
	 * @param list 树节点列表
	 * @return List<EngineeVisaSite>
	 */
	private List<EngineeVisaSite> orderTreeLevel(List<EngineeVisaSite> list) {
		if (list == null || list.size() == 0) {
			return null;
		}
		
		int listSize = list.size();
		List < EngineeVisaSite > treeNodes = new ArrayList < EngineeVisaSite >();
		Stack < EngineeVisaSite > stack = new Stack < EngineeVisaSite >();
		
		//找根节点
		for (int i = 0; i < listSize; i++) {
			EngineeVisaSite site = list.get(i);
			if (StringUtils.isBlank(site.getParentSiteId())) {
				site.setLevel(4);
				site.setIsLeaf(true);
				site.setParentSiteId(null);
				stack.push(site);
				break;
			}
		}
		//遍历生成子树节点
		while (!stack.isEmpty()) {
			EngineeVisaSite node = stack.pop();
			String id = node.getSiteId();
			//获得节点的子节点
			for (int i = listSize - 1; i >= 0; i--) {
				EngineeVisaSite temp = list.get(i);
				String parentId = temp.getParentSiteId();
				
				if (StringUtils.isBlank(parentId)) {
					continue;
				}
				if (id.equals(parentId)) {
					node.setIsLeaf(false);
					temp.setLevel(node.getLevel() + 1);
					temp.setIsLeaf(true);
					stack.push(temp);
				}
			}
			treeNodes.add(node);
	    }
		
//		System.out.println("treeNodes2222:"+treeNodes.get(0).getWbsName());
//		System.out.println("treeNodes2222:"+treeNodes.get(0).getWbsId());
		return treeNodes;
	}

	/**
	 * 查找签证涉及工程部位节点
	 * @param list 列表
	 * @param id 树节点ID
	 * @return EngineeVisaSite
	 */
	private EngineeVisaSite findSiteBySiteId(List<EngineeVisaSite> list, String id) {
		int len = list.size();
		for (int i = 0; i < len; i++) {
			EngineeVisaSite node = list.get(i);
			if (id.equals(node.getSiteId())) {
				return node;
			}
		}
		return null;
	}
	
	
	/**
	 * 签证类型MAP码表
	 * @return Map < ?, ? >
	 */
	public Map < String, String > getMapApplyType() {
		Map < String, String > map = new LinkedHashMap < String, String >();
		ICodeTable ctbl = EIPService.getComCodeTableService().getCodeTable(CTBL_APPLY_TYPE);
		String[] values = ctbl.getDataValues();
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				map.put(values[i], ctbl.getDisplay(values[i]));
			}
		}
		return map;
	}
	
	
	/**
	 * 判断指定字段唯一性
	 * 
	 * @param visaId 签证ID
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return boolean
	 */
	public boolean checkPropertyUniquenes(String visaId, String propertyName, String propertyValue) {
		return this.engineeVisaDao.checkPropertyUniquenes(visaId, propertyName, propertyValue);
	}
	
	public List<EngineeVisaSite> getSiteList(String visaId){
		return engineeVisaSiteDao.getSiteList(visaId);
	}
	
	/**
	 * 获取签证变动金额.
	 * @param visaId 签证ID
	 * @return double
	 */
	public double getVisaAmount(String visaId) {
		EngineeVisaSite site = engineeVisaSiteDao.getRootSite(visaId);
		if (site == null) {
			return 0D;
		} else {
			return site.getTotal();
		}
		
	}
	
	/**
	 * 批量启动流程
	 * @param creatorId 创建者ID
	 */
	public void startProcBatch(String creatorId) {
		List<EngineeVisaSwf> swfs = engineeVisaSwfDao.getNotStartProcList();
		if (swfs != null && swfs.size() > 0) {
			int size = swfs.size();
			for (int i = 0; i < size; i++) {
				EngineeVisaSwf swf = swfs.get(i);
				EngineeVisa visa = this.get(swf.getVisaId());
				swf.setEngineeVisa(visa);
				try {
					// 获取签证总额
					Double total = getVisaTotalAmount(visa.getVisaId());
					// 获取签证项
					int visaItemInt = visa.getVisaItem();
					if (visaItemInt == EngineeVisa.VisaItem.SINGLE_ITEM) {
						if (total >= 100000 && total < 400000) {// 启动签证_较大流程
							PmSwfUtil.startProc(swf, creatorId, PmSwfConstant.PM_SWF_PROC_INNER_NAME_VISA_2);
						} else if (total >= 400000) {// 启动签证_重大流程
							PmSwfUtil.startProc(swf, creatorId, PmSwfConstant.PM_SWF_PROC_INNER_NAME_VISA_1);
						} else {// 较小金额签证直接设置审批完成
							swf.setOaExamStatus(OAExamStatus.COMPLETE);
							engineeVisaSwfDao.update(swf);
						}
					} else if (visaItemInt == EngineeVisa.VisaItem.MULTI_ITEM) {
						if (total >= 400000 && total < 800000) {// 启动签证_较大流程
							PmSwfUtil.startProc(swf, creatorId, PmSwfConstant.PM_SWF_PROC_INNER_NAME_VISA_2);
						} else if (total >= 800000) {// 启动签证_重大流程
							PmSwfUtil.startProc(swf, creatorId, PmSwfConstant.PM_SWF_PROC_INNER_NAME_VISA_1);
						} else {// 较小金额签证直接设置审批完成
							swf.setOaExamStatus(OAExamStatus.COMPLETE);
							engineeVisaSwfDao.update(swf);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 获取签证总金额
	 * 
	 * @param visaId 签证ID
	 * @return double
	 */
	private double getVisaTotalAmount(String visaId) {
		double total = 0d;
		List<EngineeVisaSite> siteList = engineeVisaSiteDao.getSiteList(visaId);
		if (CollectionUtils.isNotEmpty(siteList)) {
			EngineeVisaSite visaSite;
			for (int j = 0; j < siteList.size(); j++) {
				visaSite = siteList.get(j);
				total += visaSite.getTotal();
			}
		}
		return total;
	}

	/**
	 * 保存签证会议信息
	 * @param visaMeet
	 */
	public void saveVisaMeet(EngineeVisaMeet visaMeet) {
		EngineeVisaSwf swf = engineeVisaSwfDao.get(visaMeet.getVisaId());
		visaMeet.setProcId(swf.getOaProcId());
		if (StringUtils.isBlank(visaMeet.getRecId())) {
			engineeVisaMeetDao.save(visaMeet);
		} else {
			engineeVisaMeetDao.update(visaMeet);
		}
	}

	/**
	 * 是否显示签证信息
	 * @param visaId
	 */
	public boolean showVisaMeet(String visaId) {
		// 获取签证项
		EngineeVisa visa = engineeVisaDao.get(visaId);
		if(visa == null) {
			return false;
		}
		Double amount = getVisaTotalAmount(visaId);
		int visaItemInt = visa.getVisaItem();
		if (visaItemInt == EngineeVisa.VisaItem.SINGLE_ITEM) {// 单一项
			if (amount >= 400000) {//启动签证_重大流程
				return true;
			}
		} else if (visaItemInt == EngineeVisa.VisaItem.MULTI_ITEM) {// 单一累积项
			if (amount >= 800000) {//启动签证_重大流程
				return true;
			}
		}
		return false;
	}


}

