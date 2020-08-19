package com.supporter.prj.ppm.tool_tips.service;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.ppm.template_register.entity.TemplateRegister;
import com.supporter.prj.ppm.tool_tips.dao.ToolTipsDao;

import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.tool_tips.entity.OperationDesc;
import com.supporter.prj.ppm.tool_tips.entity.ToolTips;
import com.supporter.util.CommonUtil;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.module.util.ExcelExport;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.module.entity.IDomainObject;
import com.supporter.prj.eip_service.module.entity.IDomainObjectAttr;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.service.PPMService;

/**
 * @Title: ToolTipsService
 * @Description: 业务操作类
 * @author: liyinfeng
 * @date: 2018-07-13
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class ToolTipsService {

	@Autowired
	private ToolTipsDao toolTipsDao;
	
	/**
	 * 获取销售合同上线对象集合
	 * @param user
	 * @param jqGrid
	 * @param toolTips
	 * @return
	 */
	public List<ToolTips> getGrid(UserProfile user, JqGrid jqGrid, ToolTips toolTips) {
		return this.toolTipsDao.findPage(jqGrid, toolTips);
	}

	/**
	 * 获取单个销售合同上线对象
	 * @param id
	 * @return
	 */
	public ToolTips get(String id) {
		ToolTips toolTips = this.toolTipsDao.get(id);
		return toolTips;
	}

	/**
	 * 新建销售合同上线对象
	 * @param user
	 * @return
	 */
	public ToolTips newToolTips(UserProfile user) {
		ToolTips toolTips = new ToolTips();
		loadToolTips(toolTips, user);
		toolTips.setStatus(ToolTips.DRAFT);
		return toolTips;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public ToolTips loadToolTips(ToolTips toolTips, UserProfile user) {
		toolTips.setCreatedBy(user.getName());
		toolTips.setCreatedById(user.getPersonId());
		toolTips.setCreatedDate(new Date());
		toolTips.setModifiedBy(user.getName());
		toolTips.setModifiedById(user.getPersonId());
		toolTips.setModifiedDate(new Date());
		Dept dept = user.getDept();
		if (dept != null) {
			toolTips.setDeptName(dept.getName());
			toolTips.setDeptId(dept.getDeptId());
		}
		// 设置状态
		toolTips.setStatus(ToolTips.DRAFT);
		return toolTips;
	}
	
	/**
	 * 为编辑或查看页面初始化对象
	 * @param id
	 * @param user
	 * @return
	 */
	public ToolTips initEditOrViewPage(String id, UserProfile user) {
		ToolTips toolTips = toolTipsDao.get(id);
		if (toolTips == null) {
			toolTips = newToolTips(user);
			toolTips.setTipsId(com.supporter.util.UUIDHex.newId());
			toolTips.setAdd(true);
		} else {
			toolTips = (ToolTips) this.toolTipsDao.get(id);
			toolTips.setAdd(false);
		}
		return toolTips;
	}
	
	/**
	 * 为编辑或查看页面初始化对象
	 * @param id
	 * @param user
	 * @return
	 */
	public String getTextDisplay(String moduleName, String busiType, String oneLevelId, String oneLevelIdEle) {
		ToolTips toolTips = this.toolTipsDao.getToolTipsByModuleNameAndBusiType(moduleName, busiType);
		String textDisplay = "";
		if(toolTips != null) {
			textDisplay = replaceVar(toolTips.getTextDisplay(), oneLevelId, moduleName, oneLevelIdEle, 
				toolTips.getEntityName(), toolTips.getDomainObjectId());
		}else {
			EIPService.getLogService(ToolTips.LOG_TYPE).info(
					null, "返回提示语信息失败","标签编码：" + busiType+"，应用编码："+moduleName+"，未获取到提示语信息。",
					null, null);
		}
		return textDisplay;
	}
	
	/**
	 * 获取业务操作
	 * @param id
	 * @param user
	 * @return
	 */
	public OperationDesc getOperationDesc(String moduleName, String busiType, String oneLevelId, String oneLevelIdEle) {
		ToolTips toolTips = this.toolTipsDao.getToolTipsByModuleNameAndBusiType(moduleName, busiType);
		String textDisplay = replaceVar(toolTips.getTextDisplay(), oneLevelId, moduleName, oneLevelIdEle, 
				toolTips.getEntityName(), toolTips.getDomainObjectId());
		OperationDesc desc = new OperationDesc();
		desc.setTextDisplay(textDisplay);
		desc.setProcDesc(toolTips.getProcDesc());
		//获取图片
		desc.setModuleName(ToolTips.MODULE_ID);
		desc.setBusiType(ToolTips.BUSI_TYPE);
		desc.setOneLevelId(toolTips.getTipsId());
		return desc;
	}
	
	/**
	 * 保存或修改对象
	 * @param user
	 * @param toolTips
	 * @param valueMap
	 * @return
	 */
	public ToolTips saveOrUpdate(UserProfile user, ToolTips toolTips, Map<String, Object> valueMap) {
		PPMService.getScheduleStatusService().saveScheduleStatus("OAREPORT", "投议标备案提交预览", user);
		if (toolTips.getAdd()) {
			this.toolTipsDao.save(toolTips);
			//保存用印/备案附件
//			this.saveFile(toolTips ,user);
		} else {
			// 设置更新时间
			toolTips.setModifiedBy(user.getName());
			toolTips.setModifiedById(user.getPersonId());
			toolTips.setModifiedDate(new Date());
			//toolTips.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.toolTipsDao.update(toolTips);
		}
		// 保存金额汇总值
		//setPlanAmount(toolTips.getId());
		return toolTips;
	}
	public ToolTips saveOrUpdate(ToolTips toolTips) {
		this.toolTipsDao.save(toolTips);
		return toolTips;
	}

	/**
	 * 保存提交
	 * @param user
	 * @param toolTips
	 * @param valueMap
	 * @return
	 */
	public ToolTips commit(UserProfile user, ToolTips toolTips, Map<String, Object> valueMap) {
		saveOrUpdate(user, toolTips, valueMap);
		toolTips.setStatus(ToolTips.COMPLETED);
		this.toolTipsDao.update(toolTips);
		// 记录日志
		EIPService.getLogService("TOOLTIPS").info(user, "生效", toolTips.getTipsId()+"生效成功", toolTips, null);
		return toolTips;
	}

	/**
	 * 生成销售合同编号
	 * @return
	 */
	public synchronized String generatorContractNo() {
		String contractNo = null;
		Calendar date = Calendar.getInstance();
		String NoHead = "CNEEC" + String.valueOf(date.get(Calendar.YEAR)) + String.valueOf(date.get(Calendar.MONTH) + 1) + String.valueOf(date.get(Calendar.MINUTE));
		Integer count = date.get(Calendar.MILLISECOND);
		String NoEnd = String.format("%03d", (count));
		contractNo = NoHead + "-" + NoEnd;
		return contractNo;
	}

	
	/**
	 * 批量删除对象
	 * @param user
	 * @param ids
	 */
	public void delete(UserProfile user, String ids) {
		if (StringUtils.isNotBlank(ids)) {
			ToolTips toolTips;
			for (String id : ids.split(",")) {
				toolTips = this.get(id);
				if (toolTips == null) continue;
				//先删除相关附件
				deleteFile(toolTips);
				this.toolTipsDao.delete(toolTips);
			}
		}
	}
	/**
	 * 批量删除对象
	 * @param user
	 * @param ids
	 */
	public void cancel(UserProfile user, String ids) {
		if (StringUtils.isNotBlank(ids)) {
			ToolTips toolTips;
			for (String id : ids.split(",")) {
				toolTips = this.get(id);
				if (toolTips == null) continue;
				//修改状态
				toolTips.setStatus(ToolTips.DRAFT);
				this.toolTipsDao.update(toolTips);
			}
		}
	}
	
	/**
	 * 验证所属应用下标签编码是否重复
	 * @param labelNo
	 * @param moduleId
	 * @param tipsId
	 * @return
	 */
	public String checkIsRepeatByLabelNoAndModuleId(String labelNo, String moduleId, String tipsId) {
		
		String hql = " from "+ ToolTips.class.getName() + " where labelNo = ? and moduleId = ? and tipsId <> ?";
		List<Integer> list = this.toolTipsDao.find(hql, labelNo, moduleId, tipsId);
		if(list != null && !list.isEmpty()) {
			EIPService.getLogService(ToolTips.LOG_TYPE).info(
					null, "验证标签编码","标签编码：" + labelNo+"，应用编码："+moduleId+"，主键ID："+tipsId+"，存在相同标签，验证不通过",
					null, null);
			return "所属应用存在当前标签编码，请重新定义标的编码";
		}
		return "";
	}
	
	
	/**
	 * 三级单位用户部门经办人
	 * 所负责组织
	 * @param user
	 * @return
	 */
	public Map<String, String> getTypeCodeTables(UserProfile user){
		/*Role role = EIPService.getRoleService().getRole("THREE_AGENT");
		List<String> deptIds = EIPService.getRoleService().getDeptIdsInResp(user, role);
		Map<String, String> map = new HashMap<String, String>();
		if(deptIds != null) {
			for(String deptId : deptIds) {
				Dept dept = EIPService.getDeptService().getDept(deptId);
				map.put(deptId, dept.getName());
			}
		}*/
		Map<String, String> map = new HashMap<String, String>();
		map.put(ToolTips.TOOLTIPS, "提示语");
		map.put(ToolTips.OPERATIONDESC, "操作说明");
		return map;
	}

	/**
	 * 删除相关附件
	 * @param recordFilingId
	 */
	public void deleteFile(ToolTips toolTips){
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList(ToolTips.MODULE_ID, ToolTips.BUSI_TYPE, toolTips.getTipsId());
		if (CollectionUtils.isNotEmpty(list)){
			for(IFile file:list){
				fileUploadService.deleteFile(file.getFileId());
			}
		}
	}
	
	/**
	 * 替换掉字符串中的变量.
	 * 模板注册中使用
	 * @param proc
	 * @param str
	 * @return
	 */
	public String replaceVarBy(String varName, String oneLevelId, String moduleId, String oneLevelIdEle, 
			String entityName, String domainObjectId, String busiTypeField, String busiType) {
		if (varName == null || varName.length() == 0)return varName;
		
			String varValue = "";
			
    		//获取hqlName
    		String varHqlName = getProcVarHqlName(moduleId, varName, domainObjectId);
    		//IPropRetriever propRetriever = EIPService.getBusiEntityService().getPropRetriever(0, entityName, entityId);
    		//业务对象，ID，元数据hql
    		String hql = " select "+ varHqlName + " from "+entityName + " where " + oneLevelIdEle + " = ? ";
    		List<Object> objList = null;
    		if(StringUtils.isNotBlank(busiType)){
    			hql = " select "+ varHqlName + " from "+entityName + " where " + oneLevelIdEle + " = ? " +
    					"and " + busiTypeField + " = ?";
    			objList = this.toolTipsDao.find(hql, oneLevelId, busiType);
    		}else{
    			objList = this.toolTipsDao.find(hql, oneLevelId);
    		}
    		//Object obj = this.toolTipsDao.find(hql, oneLevelId);
    		//List<Object> objList = this.toolTipsDao.find(hql, oneLevelId);
			//Object obj = execContext.getProcVar(varHqlName);
    		String tempValue = "";
    		if(objList != null && !objList.isEmpty()){
    			for(Object obj : objList){
					if (obj != null) {
						if (obj instanceof Date) {
							Date date = (Date) obj;
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
							tempValue = sdf.format(date);
						} else {
							tempValue = obj.toString();
						}	
					}
					if(varValue.length() > 0){
						varValue += ",";
					}
					varValue += tempValue;
    			}
    		}
    		EIPService.getLogService(TemplateRegister.LOG_TYPE).info(
					null, "获取数据读入","执行数据库：" + hql+"，参数："+oneLevelId+",返回值："+varValue,
					null, null);
			
			return varValue;
	}
	
	/**
	 * 替换掉字符串中的变量.
	 * @param proc
	 * @param str
	 * @return
	 */
	public String replaceVar(String str, String oneLevelId, String moduleId, String oneLevelIdEle, 
			String entityName, String domainObjectId) {
		if (str == null || str.length() == 0)return str;
		
		final int NUMBER_2 = 2;
		int firstIndex = str.indexOf("#{");
		int endIndex = str.indexOf("}");
		if (firstIndex >= 0 && endIndex >= 0 && endIndex > firstIndex){
			String varName = CommonUtil.trim(str.substring(firstIndex + NUMBER_2, endIndex));
			String varValue = "";
			
    		//获取hqlName
    		String varHqlName = getProcVarHqlName(moduleId, varName, domainObjectId);
    		//IPropRetriever propRetriever = EIPService.getBusiEntityService().getPropRetriever(0, entityName, entityId);
    		//业务对象，ID，元数据hql
    		String hql = " select "+ varHqlName + " from "+entityName + " where " + oneLevelIdEle + " = ? ";
    		//System.out.println("hql="+hql);
    		Object obj = this.toolTipsDao.find(hql, oneLevelId);
			//Object obj = execContext.getProcVar(varHqlName);
			if (obj != null) {
				if (obj instanceof Date) {
					Date date = (Date) obj;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					varValue = sdf.format(date);
				} else {
					varValue = obj.toString();
				}	
			}
			
			str = str.replace("#{" + varName + "}", varValue);
			return replaceVar(str, oneLevelId, moduleId, oneLevelIdEle, entityName, domainObjectId);
		} else {
			return str;
		}
	}
	
	/**
	 * 获取变量的hqlName.
	 * @param moduleId
	 * @param varName
	 * @return
	*/
	public static String getProcVarHqlName(String moduleId, String varName, String domainObjectId) {
		//获取应用对象id
		//String domainObjectId = CommonUtil.trim((String) execContext.getVar(ISharedVar.DOMAIN_OBJECT_ID));
		if (domainObjectId.length() == 0) {
			EIPService.getLogService("TOOLTIPS").error("domainObjectId is null");
		}
		IDomainObject  domainObject = EIPService.getModuleService().getDomainObject(domainObjectId);
		if (domainObject == null) {
			EIPService.getLogService("TOOLTIPS").error("domainObject is null");
			return varName;
		}
		
		String varHqlName = "";
		IDomainObjectAttr attr = EIPService.getModuleService().getDomainObjectAttr(domainObject, varName);
		if (attr != null) {
			varHqlName = CommonUtil.trim(attr.getHqlName());
			if (varHqlName.length() == 0) {
				varHqlName = varName;
			}
		} else {
			//EIPService.getLogService().error("failed to find attr, moduleId:" + moduleId + ", varName:" + varName);
			varHqlName = varName;
		}
		return varHqlName;
	} 
		
	/**
	 * 流程更新对象
	 * @param useSeal
	 * @return
	 */
	public ToolTips update(ToolTips toolTips) {
		this.toolTipsDao.update(toolTips);
		return toolTips;
	}

	/** 以下是选择采购合同方法 

	public ListPage<ToolTips> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		ListPage<ToolTips> listPage = new ListPage<ToolTips>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.toolTipsDao.getListPage(listPage, parameters);
		return listPage;
	}**/
	
	@SuppressWarnings("unchecked")
	public void export(int sheetNo, int colNo, int beginRow,
			ExcelExport excelExport, ToolTips module)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Map<String, Object> map = BeanUtils.describe(module);
//		map.put("shownInTreeName",
//				ModuleUtils.boolean2String(module.getShownInTree()));
//		map.put("authManageTypeName",
//				ToolTips.AuthManageType.getValueByKey(module.getAuthManageType()));
		excelExport.createVerticalSheet(sheetNo, colNo, beginRow, new String[] {
				"moduleName", "displayNameZh", "displayNameEn", "displayName3",
				"displayName4", "parentModuleId", "subsystem", "moduleVersion",
				"displayOrder", "moduleIcon", "shownInTreeName",// 需计算
				"authManageTypeName"// 需计算
		}, map);
	}

}
