package com.supporter.prj.linkworks.oa.critical_incident_remind.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.icu.util.Calendar;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.module.util.MessageFormat;
import com.supporter.prj.eip.todo.entity.Todo;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.prj.linkworks.oa.critical_incident_remind.dao.CriticalIncidentRemindDao;
import com.supporter.prj.linkworks.oa.critical_incident_remind.entity.CriticalIncidentRemind;
import com.supporter.prj.linkworks.oa.critical_incident_remind.util.AuthConstant;
import com.supporter.prj.linkworks.oa.critical_incident_remind.util.AuthUtil;
import com.supporter.prj.linkworks.oa.critical_incident_remind.util.LogConstant;
import com.supporter.util.CommonUtil;


@Service
public class CriticalIncidentRemindService {
	@Autowired
	private CriticalIncidentRemindDao criticalIncidentRemindDao;

	public CriticalIncidentRemind get(String moduleId) {
		return criticalIncidentRemindDao.get(moduleId);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public CriticalIncidentRemind initEditOrViewPage(JqGrid jqGrid,String incidentId, UserProfile user) {
		if (StringUtils.isBlank(incidentId)) {// 新建
			CriticalIncidentRemind criticalIncidentRemind = newCriticalIncidentRemind(user);
			criticalIncidentRemind.setIsRepeatRemind("1");//默认不重复
			criticalIncidentRemind.setAdd(true);
			return criticalIncidentRemind;
		} else {// 编辑			
			//获得主表
			CriticalIncidentRemind criticalIncidentRemind =  criticalIncidentRemindDao.get(incidentId);
			if(criticalIncidentRemind==null){
				return null;
			}
			criticalIncidentRemind.setFiles(getFiles(criticalIncidentRemind));
			criticalIncidentRemind.setAdd(false);	
			return criticalIncidentRemind;
		}
	}
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public CriticalIncidentRemind newCriticalIncidentRemind(UserProfile auserprf_U){
    	CriticalIncidentRemind lcriticalIncidentRemind_N = new CriticalIncidentRemind();
    	lcriticalIncidentRemind_N.setIncidentId(com.supporter.util.UUIDHex.newId());
    	lcriticalIncidentRemind_N.setCreatedById(auserprf_U.getPersonId());
    	lcriticalIncidentRemind_N.setCreatedBy(auserprf_U.getName());    	
    	//创建部门id
    	lcriticalIncidentRemind_N.setDeptId(auserprf_U.getDeptId());
    	//创建部门名称
	    if(auserprf_U.getPersonId().equals("1")){
	    	lcriticalIncidentRemind_N.setDeptName("管理员所属部门");
	    }else{
	    	lcriticalIncidentRemind_N.setDeptName(auserprf_U.getDept().getName());
	    }
    	String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
    	lcriticalIncidentRemind_N.setCreatedDate(date);
    	lcriticalIncidentRemind_N.setReminderStatus(Long.valueOf(CriticalIncidentRemind.NOTREMINDED));
    	lcriticalIncidentRemind_N.setStatus(Long.valueOf(CriticalIncidentRemind.DRAFT));
    	lcriticalIncidentRemind_N.setRemindingTimes(0L);
        return lcriticalIncidentRemind_N;
    }
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<CriticalIncidentRemind> getGrid(UserProfile user, JqGrid jqGrid, CriticalIncidentRemind criticalIncidentRemind) {
		List<CriticalIncidentRemind> list=this.criticalIncidentRemindDao.findPage(user, jqGrid, criticalIncidentRemind);//根据输入的查询条件查询列表
		return list;	
		
	}
	
	
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public CriticalIncidentRemind saveOrUpdate(UserProfile user, CriticalIncidentRemind criticalIncidentRemind, Map< String, Object > valueMap) {
		CriticalIncidentRemind ret = null;
		if (criticalIncidentRemind.getAdd()) {// 新建
			//保存主表
			if(CommonUtil.trim(criticalIncidentRemind.getIsRepeatRemind()).equals("1")){//不重复
				criticalIncidentRemind.setRepeatSpace(0L);
			}else{
				if(criticalIncidentRemind.getRepeatSpace()==null){
					criticalIncidentRemind.setRepeatSpace(0L);
				}
			}
			//有效截止时间
			criticalIncidentRemind.setExpireDate(CommonUtil.format(criticalIncidentRemind.getExpireDate(),"yyyy-MM-dd"));			
			//提醒时间
			criticalIncidentRemind.setReminderDate( CommonUtil.format(criticalIncidentRemind.getReminderDate(),"yyyy-MM-dd"));
			//set提醒人和提醒部门
			CriticalIncidentRemind criticalIncidentRemindZ=setPersonalOrDept(criticalIncidentRemind);
			this.criticalIncidentRemindDao.save(criticalIncidentRemindZ);		
			ret = criticalIncidentRemindZ;
			// 记录日志
			/*MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
					MaterialCode.LogOper.MATERIALCODE_ADD.getOperName(), null);*/
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.ADD_CCREMIND_LOG_MESSAGE, criticalIncidentRemindZ.getIncidentName());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					user, LogConstant.ADD_CCREMIND_LOG_ACTION, logMessage,
					criticalIncidentRemindZ, null);
		} else {// 编辑
			
			//权限验证(判断是不是有编辑关键事件提醒的权限)
			AuthUtil.canExecute(user, AuthConstant.AUTH_OPER_NAME_AUTHCRITICALINCIDENTREMINDOFBTN, criticalIncidentRemind.getIncidentId(), criticalIncidentRemind);						
			if(CommonUtil.trim(criticalIncidentRemind.getIsRepeatRemind()).equals("1")){//不重复
				criticalIncidentRemind.setRepeatSpace(0L);
			}else{
				if(criticalIncidentRemind.getRepeatSpace()==null){
					criticalIncidentRemind.setRepeatSpace(0L);
				}
			}
			//有效截止时间
			criticalIncidentRemind.setExpireDate(CommonUtil.format(criticalIncidentRemind.getExpireDate(),"yyyy-MM-dd"));			
			//提醒时间
			criticalIncidentRemind.setReminderDate( CommonUtil.format(criticalIncidentRemind.getReminderDate(),"yyyy-MM-dd"));
			criticalIncidentRemind.setModifiedBy(user.getName());
			criticalIncidentRemind.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			criticalIncidentRemind.setModifiedDate(date);
			//set提醒人和提醒部门
			CriticalIncidentRemind criticalIncidentRemindZ=setPersonalOrDept(criticalIncidentRemind);
			//编辑之后保存主表
			this.criticalIncidentRemindDao.update(criticalIncidentRemindZ);
			ret = criticalIncidentRemindZ;			
			// 记录日志
/*			MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
					MaterialCode.LogOper.MATERIALCODE_EDIT.getOperName(), null);*/			
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.EDIT_CCREMIND_LOG_MESSAGE, criticalIncidentRemindZ.getIncidentName());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					user, LogConstant.EDIT_CCREMIND_LOG_ACTION, logMessage,
					criticalIncidentRemindZ, null);
		}
		return ret;

	}
	//根据部门id取出该部门的所有人员id和name的拼接串
	public String[] getAllPersonByDeptId(String deptId){
		String[] idsAndNames=new String[2];
		Dept dept=EIPService.getDeptService().getDept(deptId);
		if(dept!=null){
			List<Person> persons = EIPService.getEmpService().getEmps(dept, true);
			if(persons!=null&&persons.size()>0){
				String generalPersonIds="";
				String generalPersonNames="";
				for(Person person : persons){
					generalPersonIds=generalPersonIds+person.getPersonId()+",";	
					generalPersonNames=generalPersonNames+person.getName()+",";
                }	
				if(generalPersonIds.length()>0){
					generalPersonIds.substring(0,(generalPersonIds.length()-1));
					idsAndNames[0]=generalPersonIds;
				}
				if(generalPersonNames.length()>0){
					generalPersonNames.substring(0,(generalPersonNames.length()-1));
					idsAndNames[1]=generalPersonNames;
				}
				return idsAndNames;
            }else{
            	return null;
            }
		}else{
			return null;
		}
	}
	
	
	
	
	
	
    /**
	 * 生效
	 * 
	 * @param user 用户信息
	 * @param apply 实体类
	 * @return
	 */
	public CriticalIncidentRemind commit(UserProfile user, CriticalIncidentRemind criticalIncidentRemind, Map< String, Object > valueMap) {
		CriticalIncidentRemind ret = null;
		boolean isNew=criticalIncidentRemind.getAdd();
		if(isNew){
			if(CommonUtil.trim(criticalIncidentRemind.getIsRepeatRemind()).equals("1")){//不重复
				criticalIncidentRemind.setRepeatSpace(0L);
			}else{
				if(criticalIncidentRemind.getRepeatSpace()==null){
					criticalIncidentRemind.setRepeatSpace(0L);
				}
			}
			//有效截止时间
			criticalIncidentRemind.setExpireDate(CommonUtil.format(criticalIncidentRemind.getExpireDate(),"yyyy-MM-dd"));			
			//提醒时间
			criticalIncidentRemind.setReminderDate( CommonUtil.format(criticalIncidentRemind.getReminderDate(),"yyyy-MM-dd"));
			
			//状态（生效）
			criticalIncidentRemind.setStatus(Long.valueOf(CriticalIncidentRemind.EFFECTIVE));
			//set提醒人和提醒部门
			CriticalIncidentRemind criticalIncidentRemindZ=setPersonalOrDept(criticalIncidentRemind);
			//保存主表
			this.criticalIncidentRemindDao.save(criticalIncidentRemindZ);		
			ret = criticalIncidentRemindZ;
			//日志
			//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{AddApplication : " + apply + "}", null, null);
		} else {// 编辑
			
			if(CommonUtil.trim(criticalIncidentRemind.getIsRepeatRemind()).equals("1")){//不重复
				criticalIncidentRemind.setRepeatSpace(0L);
			}else{
				if(criticalIncidentRemind.getRepeatSpace()==null){
					criticalIncidentRemind.setRepeatSpace(0L);
				}
			}
			criticalIncidentRemind.setModifiedBy(user.getName());
			criticalIncidentRemind.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			criticalIncidentRemind.setModifiedDate(date);
			
			//有效截止时间
			criticalIncidentRemind.setExpireDate(CommonUtil.format(criticalIncidentRemind.getExpireDate(),"yyyy-MM-dd"));			
			//提醒时间
			criticalIncidentRemind.setReminderDate( CommonUtil.format(criticalIncidentRemind.getReminderDate(),"yyyy-MM-dd"));
			//状态（生效）
			criticalIncidentRemind.setStatus(Long.valueOf(CriticalIncidentRemind.EFFECTIVE));
			//set提醒人和提醒部门
			CriticalIncidentRemind criticalIncidentRemindZ=setPersonalOrDept(criticalIncidentRemind);
			//编辑之后保存主表
			this.criticalIncidentRemindDao.update(criticalIncidentRemindZ);
			ret = criticalIncidentRemindZ;
			//日志
			//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{EditApplication : " + apply + "}", null, null);
		}
		return ret;
	}
	
	
	/**
	 * 根据提醒类型保存提醒人员和提醒部门
	 * 
	 * @param criticalIncidentRemind
	 */	
	public CriticalIncidentRemind setPersonalOrDept(CriticalIncidentRemind criticalIncidentRemind){
		//根据提醒类型判断需要保存那些被提醒人
		if(criticalIncidentRemind.getReminderType()!=null&&!CommonUtil.trim(criticalIncidentRemind.getReminderType()).equals("")){
			if(CommonUtil.trim(criticalIncidentRemind.getReminderType()).equals("1")){//选择了按人员提醒
				criticalIncidentRemind.setNotifyPersonIds(criticalIncidentRemind.getNotifyPersonIds());
				criticalIncidentRemind.setNotifyPersonNames(criticalIncidentRemind.getNotifyPersonNames());
				criticalIncidentRemind.setNotifyDeptIds("");
				criticalIncidentRemind.setNotifyDeptNames("");
			}else if(CommonUtil.trim(criticalIncidentRemind.getReminderType()).equals("2")){//选择了按部门提醒
				criticalIncidentRemind.setNotifyDeptIds(criticalIncidentRemind.getNotifyDeptIds());
				criticalIncidentRemind.setNotifyDeptNames(criticalIncidentRemind.getNotifyDeptNames());
				criticalIncidentRemind.setNotifyPersonIds("");
				criticalIncidentRemind.setNotifyPersonNames("");				
			}else if(CommonUtil.trim(criticalIncidentRemind.getReminderType()).equals("1,2")){//两种都选择了
				criticalIncidentRemind.setNotifyPersonIds(criticalIncidentRemind.getNotifyPersonIds());
				criticalIncidentRemind.setNotifyPersonNames(criticalIncidentRemind.getNotifyPersonNames());
				criticalIncidentRemind.setNotifyDeptIds(criticalIncidentRemind.getNotifyDeptIds());
				criticalIncidentRemind.setNotifyDeptNames(criticalIncidentRemind.getNotifyDeptNames());		
			}
		}
		return criticalIncidentRemind;
	}
	
	/**
	 * 根据提醒类型获取所有的被提醒人
	 * 
	 * @param criticalIncidentRemind
	 */	
	public CriticalIncidentRemind getPersonalOfRemind(CriticalIncidentRemind criticalIncidentRemind){
		//根据提醒类型判断需要保存那些被提醒人
		if(criticalIncidentRemind.getReminderType()!=null&&!CommonUtil.trim(criticalIncidentRemind.getReminderType()).equals("")){
			if(CommonUtil.trim(criticalIncidentRemind.getReminderType()).equals("1")){//选择了按人员提醒				
				//保存所有的被提醒人(就是选择的所有人员)
				criticalIncidentRemind.setGeneralPersonIds(criticalIncidentRemind.getNotifyPersonIds());
				criticalIncidentRemind.setGeneralPersonNames(criticalIncidentRemind.getNotifyPersonNames());
			}else if(CommonUtil.trim(criticalIncidentRemind.getReminderType()).equals("2")){//选择了按部门提醒	
				//保存所有的被提醒人(就是选择的部门中的所有人员)					
				String[] generalPersonOfDept=getAllPersonByDeptId(criticalIncidentRemind.getNotifyDeptIds());
				if(generalPersonOfDept!=null&&generalPersonOfDept.length>0){
					criticalIncidentRemind.setGeneralPersonIds(generalPersonOfDept[0]);
					criticalIncidentRemind.setGeneralPersonNames(generalPersonOfDept[1]);	
				}				
			}else if(CommonUtil.trim(criticalIncidentRemind.getReminderType()).equals("1,2")){//两种都选择了		
			    //保存所有的被提醒人（选择的人员和部门下所有人员取并集）
				//1.首先根据部门id取出该部门下所有的人员										
				String[] generalPersonOfDept=getAllPersonByDeptId(criticalIncidentRemind.getNotifyDeptIds());

				List<String>  strListIdsOfDept=new ArrayList<String>();
				List<String>  strListNamesOfDept=new ArrayList<String>();
				
				if(generalPersonOfDept!=null&&generalPersonOfDept.length>0){					
					//(1)将部门中的所有的人员id放入一个集合
					strListIdsOfDept=getListByStr(generalPersonOfDept[0]);
					//(2)将部门中的所有的人员Name放入一个集合
					strListNamesOfDept=getListByStr(generalPersonOfDept[1]);

				}

				
				//2.1 获取人员选择框选择的人员的Ids,将其放入一个集合
				String generalPersonIdsOfPerson=criticalIncidentRemind.getNotifyPersonIds();
				List<String>  strListIdsOfPerson=getListByStr(generalPersonIdsOfPerson);
				
				//2.2 获取人员选择框选择的人员的Names,将其放入一个集合
				String generalPersonNameOfPerson=criticalIncidentRemind.getNotifyPersonNames();
				List<String>  strListNamesOfPerson=getListByStr(generalPersonNameOfPerson);
				
				
				
			    //部门中的人员ids和选择的人员ids取并集
				//3.1 通过set集合去重之后获取应发提醒的所有ids
				strListIdsOfPerson.addAll(strListIdsOfDept);					
				Set<String> setIds = new  HashSet<String>(); 
				List<String> listGeneralPersonIds = new  ArrayList<String>(); 
				setIds.addAll(strListIdsOfPerson);
				listGeneralPersonIds.addAll(setIds);
				String generalPersonIds ="";
				//通过遍历最终的集合得到所有被提醒人的id
				if(listGeneralPersonIds!=null&&listGeneralPersonIds.size()>0){
					for(String ids : listGeneralPersonIds){
						generalPersonIds=generalPersonIds+ids+",";
					}
					criticalIncidentRemind.setGeneralPersonIds(generalPersonIds.substring(0,(generalPersonIds.length()-1)));
				}
				
			    //部门中的人员Names和选择的人员Names取并集
				//3.2 通过set集合去重之后获取应发提醒的所有Names
				strListNamesOfPerson.addAll(strListNamesOfDept);					
				Set<String> setNames = new  HashSet<String>(); 
				List<String> listGeneralPersonNames = new  ArrayList<String>(); 
				setNames.addAll(strListNamesOfPerson);
				listGeneralPersonNames.addAll(setNames);
				String generalPersonNames ="";
				//通过遍历最终的集合得到所有被提醒人的id
				if(listGeneralPersonNames!=null&&listGeneralPersonNames.size()>0){
					for(String names : listGeneralPersonNames){
						generalPersonNames=generalPersonNames+names+",";
					}
					criticalIncidentRemind.setGeneralPersonNames(generalPersonNames.substring(0,(generalPersonNames.length()-1)));
				}
				
				
				
			}
		}
		return criticalIncidentRemind;
	}
	
	/**
	 * 将字符串转成list
	 */
	public List<String> getListByStr(String str){
		List<String>  strList=new ArrayList<String>();
		if(str!=null&&str.length()>0){
			if(str.indexOf(",")!=-1){
				String[] strs=str.split(",");						
				if(strs.length>0){
					for(int i=0;i<strs.length;i++){
						strList.add(strs[i]);
					}
				}
			}else{
				strList.add(CommonUtil.trim(str));
			}
		}
		return strList;
	}
	
	
	
	

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String incidentIds) {
		if (StringUtils.isNotBlank(incidentIds)) {
			for (String incidentId : incidentIds.split(",")) {
				CriticalIncidentRemind criticalIncidentRemind=criticalIncidentRemindDao.get(incidentId);
				if(criticalIncidentRemind!=null){
					//权限验证(判断是不是有编辑关键事件提醒的权限)
					AuthUtil.canExecute(user, AuthConstant.AUTH_OPER_NAME_AUTHCRITICALINCIDENTREMINDOFBTN, incidentId, criticalIncidentRemind);						
					
					//删除关键事件提醒主表
					this.criticalIncidentRemindDao.delete(criticalIncidentRemind);
				}

			}
//			EIPService.getLogService("MPM_MCA").info(user, Contract.LogOper.MODULE_DEL.getOperName(), "{delContractIds : " + moduleIds + "}", null, null);
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.DELETE_CCREMIND_LOG_MESSAGE, "删除的主键id为："+incidentIds);
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					user, LogConstant.DELETE_CCREMIND_LOG_ACTION, logMessage,
					null, null);
		}
	}
	
	
	/**
	 * 撤销
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void revokeStatus(UserProfile user, String incidentId) {
		if (StringUtils.isNotBlank(incidentId)) {
			CriticalIncidentRemind criticalIncidentRemind =  criticalIncidentRemindDao.get(incidentId);
			if(criticalIncidentRemind!=null){
				//有发送的message的id(代表已经发送了待办) 
				if(criticalIncidentRemind.getMessageId()!=null&&!CommonUtil.trim(criticalIncidentRemind.getMessageId()).equals("")){
					String messageIds=CommonUtil.trim(criticalIncidentRemind.getMessageId());
					if(messageIds.indexOf(",")!=-1){						
						String[] str=messageIds.split(",");
						if(str!=null&&str.length>0){
							for(int i=0;i<str.length;i++){
								//撤回待办
								EIPService.getBMSService().removeMessage(str[i]);					
							}
						}
					}else{
						//撤回待办
						EIPService.getBMSService().removeMessage(messageIds);							
					}
					
				}
				//修改状态
				criticalIncidentRemind.setReminderStatus(Long.valueOf(CriticalIncidentRemind.NOTREMINDED));//设置提醒状态为未提醒
				criticalIncidentRemind.setStatus(Long.valueOf(CriticalIncidentRemind.DRAFT));//设置状态为草稿
			}
			
			//修改
			this.criticalIncidentRemindDao.update(criticalIncidentRemind);

//			EIPService.getLogService("MPM_MCA").info(user, Contract.LogOper.MODULE_DEL.getOperName(), "{delContractIds : " + moduleIds + "}", null, null);
		}
	}
	
	/**
	 * 办结完之后更改状态
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
/*	public void updateStatus(UserProfile user, String incidentId,String type) {
		if (StringUtils.isNotBlank(incidentId)) {
			CriticalIncidentRemind criticalIncidentRemind =  criticalIncidentRemindDao.get(incidentId);
			if(CommonUtil.trim(type).equals("know")){//好的 我知道了
				if(CommonUtil.trim(criticalIncidentRemind.getIsRepeatRemind()).equals("1")){//不重复
					criticalIncidentRemind.setReminderStatus(Long.valueOf(CriticalIncidentRemind.FINISH));//设置提醒状态为已办结
				}else if(CommonUtil.trim(criticalIncidentRemind.getIsRepeatRemind()).equals("2")){//重复
					criticalIncidentRemind.setReminderStatus(Long.valueOf(CriticalIncidentRemind.REMINDING_02));//设置提醒状态为提醒中（待办已处理）
				}			
			}else if(CommonUtil.trim(type).equals("stop")){//好的 不用再提醒我了
				criticalIncidentRemind.setReminderStatus(Long.valueOf(CriticalIncidentRemind.FINISH));//设置提醒状态：已办结
			}
			
			//修改
			this.criticalIncidentRemindDao.update(criticalIncidentRemind);

//			EIPService.getLogService("OA_CCREMIND").info(user, Contract.LogOper.MODULE_DEL.getOperName(), "{delContractIds : " + moduleIds + "}", null, null);
		}
	}*/
	
	
	 	 
	 // 定时刷新数据并发送代办
	public void RefreshAndSendAgent() {
		// 获取符合条件的数据（//筛选出已经状态已经生效的，提醒状态为未提醒的）
		List<CriticalIncidentRemind> list = this.criticalIncidentRemindDao
				.getCriticalIncidentRemind();
		if (list != null && list.size() > 0) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			for (CriticalIncidentRemind cIncidentRemind : list) {
				if (cIncidentRemind != null) {
					
					// 进行符合条件的过滤（1、当前日期在开始提醒日期、结束提醒日期之内 2、提醒状态为未提醒 3、业务状态为生效）
					
					if (cIncidentRemind.getStatus() != null
							&& cIncidentRemind.getStatus() == 1) {//状态等于生效
						if (cIncidentRemind.getReminderStatus() != null
								&& cIncidentRemind.getReminderStatus() == 0) {//提醒状态等于未提醒
							if (cIncidentRemind.getReminderDate() != null
									&& !cIncidentRemind.getReminderDate()
											.equals("")) {//提醒日期不为null并且不等于空

								try {
									Date dt1 = df.parse(cIncidentRemind
											.getReminderDate());
									Date dateOfNow = df.parse(CommonUtil
											.format(new Date(), "yyyy-MM-dd"));// 当前日期

									

									if (dt1.getTime() <= dateOfNow.getTime()) {// 提醒日期小于或者等于当前日期的时候
										
										//发送待办的时候保存所有的被提醒人
										CriticalIncidentRemind criticalIncidentRemind=getPersonalOfRemind(cIncidentRemind);
										
										
										
										
										// 发代办提示
										String messageIds="";
										long remindingTimes=0L;
										String generalPersonIds=CommonUtil.trim(criticalIncidentRemind.getGeneralPersonIds());
										if(generalPersonIds.length()>0){											
											if(generalPersonIds.indexOf(",")!=-1){
												String[] str=CommonUtil.trim(criticalIncidentRemind.getGeneralPersonIds()).split(",");						
												if(str.length>0){
													for(int i=0;i<str.length;i++){
														Message message=SendAgent(criticalIncidentRemind,str[i]);
														messageIds=messageIds+message.getId()+",";
														remindingTimes=remindingTimes+1L;
													}
												}												
											}else{
												Message message=SendAgent(criticalIncidentRemind,generalPersonIds);
												messageIds=messageIds+message.getId()+",";
												remindingTimes=remindingTimes+1L;												
											}

										}
						                //待办发送时间
										String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
										criticalIncidentRemind.setLatestAgencyDate(date);
						                //将待办发送次数改成了待办发送成功条数（待办发送次数）
										criticalIncidentRemind.setRemindingTimes(remindingTimes);	
										// 代办发出之后更改该条数据的提醒状态
										criticalIncidentRemind
												.setReminderStatus(Long
														.valueOf(CriticalIncidentRemind.FINISH));// 提醒状态改为：已发送
										
						                
//						                //待办发出去之后将发送次数加1
//						               
//						                long remindingTimesZ=remindingTimes+1L;
//						                cIncidentRemind.setRemindingTimes(remindingTimesZ);
						                
										 //记录业务日志
										String logMessage = MessageFormat.format("关键事件提醒记录日志：事件名称:"+criticalIncidentRemind.getIncidentName()+",待办接收人："+criticalIncidentRemind.getGeneralPersonNames()
												//, new Object[] {}
										);
								        EIPService.getLogService("OA_CCREMIND").info(null, "关键事件提醒待办已发送", logMessage, criticalIncidentRemind, null);
										//EIPService.getLogService("OA_CCREMIND").in(user, Contract.LogOper.MODULE_DEL.getOperName(), "{delContractIds : " + moduleIds + "}", null, null);
								      //保存发送的待办信息的id
										if(messageIds.length()>0){
											criticalIncidentRemind.setMessageId(messageIds.substring(0,(messageIds.length()-1)));
										}
										this.criticalIncidentRemindDao
												.update(criticalIncidentRemind);
									}

								} catch (Exception exception) {
									exception.printStackTrace();
								}

							}
						}
					}

				}

			}
		}

	}
	
	/**
	 * 发送待办
	 */
	public Message SendAgent(CriticalIncidentRemind cIncidentRemind,String personIds){
		Message messageCreat =new Todo();
	    messageCreat.setPersonId(personIds);
		String title = "关键事件提醒：" + cIncidentRemind.getIncidentName();
		messageCreat.setEventTitle(title);
		messageCreat.setNotifyTime(new Date());
		messageCreat.setWebPageURL("oa/critical_incident_remind/criticalIncidentRemind_view_zh.html?incidentId="
				+ CommonUtil.trim(cIncidentRemind.getIncidentId()));
		messageCreat.setMessageType(ITodo.MsgType.CC);	
		messageCreat.setRelatedRecordTable(EIPService.getWebappService().getWebappName());
		Message message=EIPService.getBMSService().addMessage(messageCreat);
        return message;
	}
	
	
	
/*	 //定时刷新数据并发送代办 重复状态为：重复
	 public void RefreshAndSendAgentOfRepeatRemind (){
		 //获取符合条件的数据（//筛选出已经状态已经生效的，提醒状态为未提醒的，重复状态为 重复 的）
		 List<CriticalIncidentRemind> list=this.criticalIncidentRemindDao.getCriticalIncidentRemindOfRepeatRemind();
		 
		 if(list!=null&&list.size()>0){
			 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			 
			 for(CriticalIncidentRemind cIncidentRemind: list){
				//首先判断是不是重复提醒
				 if(cIncidentRemind!=null){
					 if(cIncidentRemind.getIsRepeatRemind()!=null){						 
		                if(CommonUtil.trim(cIncidentRemind.getIsRepeatRemind()).equals("2")){//重复提醒							 
							        try {	
							        	//两次或者两次以上发送待办
							        	if(cIncidentRemind.getLatestAgencyDate()!=null&&!(CommonUtil.trim(cIncidentRemind.getLatestAgencyDate()).equals(""))){//最新发送待办时间不为空，说明不是第一次发送代办
							            	 //最新发送代办时间加上重复天数
							            	 String repeatDate= plusDay(Integer.parseInt(String.valueOf(cIncidentRemind.getRepeatSpace())),cIncidentRemind.getLatestAgencyDate());
							            	 Date dt3 = df.parse(repeatDate);//最新发送代办日期加重复天数之后的日期
							            	 Date dateOfNow = df.parse(CommonUtil.format(new Date(), "yyyy-MM-dd"));//当前日期
							            	 if(dt3.getTime()== dateOfNow.getTime()) {//最新发送代办的日期加重复天数等于当前日期

									              //发代办提示
									        		//Message msg= EIPService.getBMSService().newMessage();
							            		    Message msg=new Todo();
									        		msg.setCreatedDate(new Date());
									        		msg.setEventTitle("关键事件提醒："+cIncidentRemind.getIncidentName());
									        		msg.setPersonId(cIncidentRemind.getCreatedById());
									        		msg.setWebappName(EIPService.getWebappService().getWebappName());
									        		msg.setWebPageURL("oa/critical_incident_remind/criticalIncidentRemind_view_zh.html?incidentId=" + CommonUtil.trim(cIncidentRemind.getIncidentId()));
									        		Message message=EIPService.getBMSService().addMessage(msg);
									        		
													// 发代办提示:222222222222222222222222222
													Message messageCreat =new Todo();
												    messageCreat.setPersonId(cIncidentRemind.getCreatedById());
													String title = "关键事件提醒：" + cIncidentRemind.getIncidentName();
													messageCreat.setEventTitle(title);
													messageCreat.setNotifyTime(new Date());
													messageCreat.setWebPageURL("oa/critical_incident_remind/criticalIncidentRemind_view_zh.html?incidentId="
															+ CommonUtil
															.trim(cIncidentRemind
																	.getIncidentId()));
													messageCreat.setMessageType(ITodo.MsgType.CC 

												);	
													messageCreat.setRelatedRecordTable(EIPService
															.getWebappService()
															.getWebappName());
													Message message=EIPService.getBMSService().addMessage(messageCreat);							            		 							      							            		 
							            		    //保存发送的待办信息的id
									                cIncidentRemind.setMessageId(message.getId());
									                //待办发出去之后将发送次数加1
									                long remindingTimes=cIncidentRemind.getRemindingTimes();
									                long remindingTimesZ=remindingTimes+1L;
									                cIncidentRemind.setRemindingTimes(remindingTimesZ);
									                SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									                cIncidentRemind.setLatestAgencyDate(sdf.format(new Date()));
									                //代办发出之后更改该条数据的状态
									                cIncidentRemind.setReminderStatus(Long.valueOf(CriticalIncidentRemind.REMINDING_01));//提醒状态改为：提醒中（待办未处理）
									                //记录业务日志
													String logMessage = MessageFormat.format("关键事件提醒记录日志：事件名称:"+cIncidentRemind.getIncidentName()
															//, new Object[] {}
													);
											        EIPService.getLogService("OA_CCREMIND").info(null, "关键事件提醒待办已发送", logMessage, cIncidentRemind, null);
									                
									                
									                this.criticalIncidentRemindDao.update(cIncidentRemind);
									                //修改完成之后刷新一下列表（执行getgrid的方法）
									                
									                
									            } 
							            }else{ //说明是第一次发送代办	  
							            	if(cIncidentRemind.getReminderDate()!=null&&!CommonUtil.trim(cIncidentRemind.getReminderDate()).equals("")){//提醒日期不为空
								            	Date dt1 = df.parse(cIncidentRemind.getReminderDate());//提醒日期
								            	Date dateOfNow = df.parse(CommonUtil.format(new Date(), "yyyy-MM-dd"));//当前日期
								            	if(dt1.getTime()== dateOfNow.getTime()) {//提醒日期等于当前日期
	
										              //发代办提示
										        		//Message msg= EIPService.getBMSService().newMessage();	
								            		    Message msg=new Todo();
										        		msg.setCreatedDate(new Date());
										        		msg.setEventTitle("关键事件提醒："+cIncidentRemind.getIncidentName());
										        		msg.setPersonId(cIncidentRemind.getCreatedById());
										        		msg.setWebappName(EIPService.getWebappService().getWebappName());
										        		msg.setWebPageURL("oa/critical_incident_remind/criticalIncidentRemind_view_zh.html?incidentId=" + CommonUtil.trim(cIncidentRemind.getIncidentId()));
										                Message message=EIPService.getBMSService().addMessage(msg);
										               
										                
										                
														// 发代办提示:222222222222222222222222222
														Message messageCreat =new Todo();
													    messageCreat.setPersonId(cIncidentRemind.getCreatedById());
														String title = "关键事件提醒：" + cIncidentRemind.getIncidentName();
														messageCreat.setEventTitle(title);
														messageCreat.setNotifyTime(new Date());
														messageCreat.setWebPageURL("oa/critical_incident_remind/criticalIncidentRemind_view_zh.html?incidentId="
																+ CommonUtil
																.trim(cIncidentRemind
																		.getIncidentId()));
														messageCreat.setMessageType(ITodo.MsgType.CC 

													);	
														messageCreat.setRelatedRecordTable(EIPService
																.getWebappService()
																.getWebappName());
														Message message=EIPService.getBMSService().addMessage(messageCreat);
										                
										                //保存发送的待办信息的id
										                cIncidentRemind.setMessageId(message.getId());
										                //待办发出去之后将发送次数加1
										                long remindingTimes=cIncidentRemind.getRemindingTimes();
										                long remindingTimesZ=remindingTimes+1L;
										                cIncidentRemind.setRemindingTimes(remindingTimesZ);
										                //待办发出去之后将最新待办发送时间保存到数据库
										                SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										                cIncidentRemind.setLatestAgencyDate(sdf.format(new Date()));
										                //代办发出之后更改该条数据的状态
										                cIncidentRemind.setReminderStatus(Long.valueOf(CriticalIncidentRemind.REMINDING_01));//提醒状态改为：提醒中（待办未处理）
										                //记录业务日志
														String logMessage = MessageFormat.format("关键事件提醒记录日志：事件名称:"+cIncidentRemind.getIncidentName()
																//, new Object[] {}
														);
												        EIPService.getLogService("OA_CCREMIND").info(null, "关键事件提醒待办已发送", logMessage, cIncidentRemind, null);
										                this.criticalIncidentRemindDao.update(cIncidentRemind);
										            } 
							            	}
							            }
							            
							        } catch (Exception exception) {
							            exception.printStackTrace();
							        }
						 }
					 }
					 
				 }

			 }
		 }
			 
	 }
*/	 
	
	 
	 
	 
	 
	 
	 
	 
	 
	 

	 /**
	       * 指定日期加上天数后的日期
	       * @param num 为增加的天数
	       * @param newDate 创建时间
	       * @return
	       * @throws ParseException 
	       */
	      public  String plusDay(int num,String newDate) throws ParseException{
	         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	         Date  currdate = format.parse(newDate);
	          Calendar ca = Calendar.getInstance();
	          ca.setTime(currdate);
	          ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
	          currdate = ca.getTime();
	          String enddate = format.format(currdate);
	          return enddate;
	     }
	       
		
		/**
		 * 附件下载部分
		 * @param maintenance
		 * @return
		 */
		public String getFiles( CriticalIncidentRemind criticalIncidentRemind){
			StringBuffer sb = new StringBuffer();
			IFileUploadService fileUploadService = EIPService.getFileUploadService();
			List<IFile> list  = fileUploadService.getFileList("OACCREMIND", "INCIDENTREM2", criticalIncidentRemind.getIncidentId());
			for(IFile file:list){
				sb.append("<a onclick=\"downloads('"+ file.getFileId() +"');\">" + file.getFileName() +"</a><br/>");
			}
			return sb.toString();
		}
		
}
