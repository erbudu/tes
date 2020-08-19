/**
 * 
 */
package com.supporter.prj.ppm.poa.use_seal.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;																										
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.role.entity.Role;														  													  
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.prj.ppm.bid_startup.approving.constant.ReportStartConstant;
import com.supporter.prj.ppm.bid_startup.preparation.constant.StartContant;
import com.supporter.prj.ppm.bid_startup.preparation.entity.StartEntity;
import com.supporter.prj.ppm.bid_startup.preparation.service.StartService;
import com.supporter.prj.ppm.poa.use_seal.constant.UseSealConstant;
import com.supporter.prj.ppm.poa.use_seal.dao.UseSealStartDao;
import com.supporter.prj.ppm.poa.use_seal.entity.UseSealFileEntity;
import com.supporter.prj.ppm.poa.use_seal.entity.UseSealStartEntity;
import com.supporter.prj.ppm.prj_org.base_info.common.PrjInfo;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.util.UUIDHex;

/**
 * <p>
 * Title: 启动用印业务层
 * </p>
 * <p>
 * Description: 处理业务逻辑
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author YUEYUN
 * @date 2019年9月18日
 * 
 */
@Service
public class UseSealStartService {
	@Autowired
	private UseSealStartDao useSealStartDao;

	@Autowired
	private UseSealFileService useSealStartService;

	@Autowired
	private PrjInfo prjInfo;
	
	@Autowired
	private StartService startService;

	/**
	 * <pre>
	 * save or update
	 * </pre>
	 * 
	 * @param useSealStartEntity Business form information
	 * @param user               The current user information
	 * @return Saved business form information
	 */
	public UseSealStartEntity saveOrUpdate(UseSealStartEntity useSealStartEntity, UserProfile user) {
		if (useSealStartEntity.getIsNew()) {// true save
			setCreatorInfo(useSealStartEntity, user);
			useSealStartDao.save(useSealStartEntity);
			useSealStartEntity.setIsNew(false);
			useSealStartService.save(useSealStartEntity.getUseSealId(), useSealStartEntity.getBusinessUUID(),
					useSealStartEntity.getPrjId(), useSealStartEntity.getUseSealFileGrid());
			return useSealStartEntity;
		} else {// false update
			setModifierInfo(useSealStartEntity, user);
			useSealStartService.update(useSealStartEntity.getUseSealId(), useSealStartEntity.getBusinessUUID(),
					useSealStartEntity.getPrjId(), useSealStartEntity.getUseSealFileGrid());
			useSealStartDao.update(useSealStartEntity);
			return useSealStartEntity;
		}

	}

	/**
	 * <pre>
	 * This nethod is set modifier information
	 * </pre>
	 * 
	 * @param useSealStartEntity Business form information
	 * @param user               The current user information
	 */
	private void setModifierInfo(UseSealStartEntity useSealStartEntity, UserProfile user) {
		useSealStartEntity.setModifiedBy(user.getName());
		useSealStartEntity.setModifiedById(user.getPersonId());
		useSealStartEntity.setModifiedDate(new Date());

	}

	/**
	 * <pre>
	 * This method is set creator information
	 * </pre>
	 * 
	 * @param useSealStartEntity Business form information
	 * @param user               The current user information
	 */
	private void setCreatorInfo(UseSealStartEntity useSealStartEntity, UserProfile user) {
		Dept dept = user.getDept();
		useSealStartEntity.setCreatedBy(user.getName());
		useSealStartEntity.setCreatedById(user.getPersonId());
		useSealStartEntity.setCreatedDate(new Date());
		if (dept == null) {
			useSealStartEntity.setDeptName("管理员");
			useSealStartEntity.setDeptId("1");
		} else {
			useSealStartEntity.setDeptName(dept.getName());
			useSealStartEntity.setDeptId(dept.getDeptId());
		}
	}

	/**
	 * <pre>
	 * 初始化页面数据
	 * </pre>
	 * 
	 * @param prjId           项目主键
	 * @param useSealBusiness 用印业务
	 * @param UUID            启动用印的业务单主键
	 * @param backPath        上传盖章文件完成后的数据处理路径
	 * @return 用印业务表单实体类
	 */
	public UseSealStartEntity initAddOrEditPage(UserProfile user, String prjId, String useSealBusiness,
			String businessUUID, String backPath) {
		if (prjId == "" || prjId == null) {
			return null;
		}
		System.out.println("传进来的业务单实体类主键："+businessUUID);
		Prj prjInformation = prjInfo.PrjInformation(prjId);// 项目信息
		UseSealStartEntity useSealStartEntity = useSealStartDao.findUniqueResult("businessUUID", businessUUID);// 根据项目主键获取业务表单信息
		if (useSealStartEntity == null) {// 没数据--新建
			UseSealStartEntity newEntity = new UseSealStartEntity();
			newEntity.setUseSealId(UUIDHex.newId());// 主键
			newEntity.setPrjId(prjId);// 项目主键
			newEntity.setBusinessUUID(businessUUID);// 调用用印的业务表单主键
			newEntity.setUseSealBusiness(useSealBusiness);// 用印业务
			newEntity.setBackPath(backPath);// 上传盖章文件完成后的数据处理路径
			newEntity.setPrjNo(prjInformation.getPrjNo());// 项目编号
			newEntity.setPrjNameC(prjInformation.getPrjCName());// 项目中文名称
			newEntity.setPrjNameE(prjInformation.getPrjEName());// 项目英文名称
			newEntity.setUseSealPerson(user.getName());// 用印人
			Dept dept = user.getDept();
			if (dept != null) {
				newEntity.setUseSealDepartment(dept.getName());// 用印人部门
			}
			newEntity.setStatus(0);
			newEntity.setIsNew(true);
			return newEntity;
		}
		return useSealStartEntity;
	}

	/**
	 * <pre>
	 * 初始化用印界面
	 * </pre>
	 * 
	 * @param user
	 * @param jqGrid 
	 * @param prjId 
	 * @return 用印列表数据
	 */
	public void getGridData(UserProfile user, JqGrid jqGrid, String prjId) {
		
		this.useSealStartDao.findPage(jqGrid, prjId);
	}

	/**
	 * <pre>
	 * Get business form entity class information by primary key
	 * </pre>
	 * 
	 * @param useSealId Primary key
	 * @return Business form information
	 */
	public UseSealStartEntity get(String useSealId) {

		return useSealStartDao.get(useSealId);
	}

	/**
	 * <pre>
	 * Update business  form information
	 * </pre>
	 * 
	 * @param useSealStartEntity Business form entity class
	 */
	public void update(UseSealStartEntity useSealStartEntity) {

		useSealStartDao.update(useSealStartEntity);

	}

	/**
	 * <pre>Initialized 'user_sealUpload.html' page data</pre>
	 * @param useSealId Use seal primary key
	 * @return Use seal information
	 */
	public UseSealStartEntity initPageData(String useSealId) {
		
		UseSealStartEntity useSealStartEntity = useSealStartDao.get(useSealId);
		return useSealStartEntity;
	}

	/**
	 * <pre>Initialized 'preparation_startUpPrintV.html' page data</pre>
	 * @param businessId 业务id
	 * @return Use seal information
	 */
	public UseSealStartEntity getByBusinessId(String businessId) {
		if(businessId == null || businessId == "") {
			return null;
		}
		
		return useSealStartDao.findUniqueResult("businessUUID", businessId);
	}

	/**
	 * 提交盖章文件后改变流程状态
	 * @param useSealId
	 * @return
	 */
	public UseSealStartEntity updateStatus(String useSealId) {
		
		if(useSealId == "" || useSealId == null) {
			return null;
		}
		
		UseSealStartEntity startEntity = useSealStartDao.get(useSealId);
		
		startEntity.setStatus(UseSealConstant.WF_UPLOAD);
		
		useSealStartDao.update(startEntity);
		
		return startEntity;
	}

	/**
	 * <pre>
	 * @Title : 删除用印单信息
	 * @param useSealId  用印单主键
	 * @return
	 * </pre>
	 */
	
	public UseSealStartEntity deleteUseSeal(String useSealId) {
		
		if(useSealId == "" || useSealId == null) {
			
			return null;
		}
		
		UseSealStartEntity entity = useSealStartDao.get(useSealId);
		
		useSealStartDao.delete(entity);
		useSealStartService.deleteFile(useSealId);
		
		return entity;
	}

	public void sendNotice(String useSealId) {
		
		if(!useSealId.isEmpty()) {
			
			if(useSealStartService.isSendNotice(useSealId)) {
				
				UseSealStartEntity entity = useSealStartDao.get(useSealId);

				String title = entity.getDeptName()+"-"+entity.getCreatedBy()+"申请使用公章";
				String url = "ppm/poa/user_seal/use_sealAduit.html?useSealId="+useSealId+"&isAudit=false";
				String procId = UUIDHex.newId();
				
				Role role = EIPService.getRoleService().getRole(UseSealConstant.USE_SEAL_ADMIN);
				
				List<Person> list = EIPService.getRoleService().getPersonsForDept(role,null);
				
				for(Person person : list) {
					
					sendNotice(person.getPersonId(), title, url, procId);
				}
			}
		}
	}
	
	/**
	 * <pre>
	 * 发送待办通知
	 * </pre>
	 * 
	 * @param personId 通知人主键
	 * @param title    待办标题
	 * @param url      待办页面路径
	 * @param procId   流程id
	 */
	private void sendNotice(String personId, String title, String url, String procId) {

		Message message = EIPService.getBMSService().newMessage();// --------------------------------获取待办通知服务内容
		
		message.setPersonId(personId);// ------------------------------------------------------------通知人
		message.setEventTitle(title);// -------------------------------------------------------------通知待办标题
		message.setNotifyTime(new Date());// --------------------------------------------------------通知待办日期
		message.setWebPageURL(url);// ---------------------------------------------------------------通知待办地址
		message.setModuleId(UseSealConstant.MODULE_ID);// -----------------------------------------应用编号
		message.setMessageType(ITodo.MsgType.CC);// -------------------------------------------------待办类型（默认地指定为“待办”类型）
		message.setWebappName("BM");
		message.setWfProcId(procId);
		
		EIPService.getBMSService().addMessage(message);// -------------------------------------------获取待办服务发送待办

	}

	/**
	 * 将流程状态设为草稿
	 * @param user 
	 * @param entity
	 */
	public void clearProcId(UserProfile user, UseSealStartEntity entity) {
		if(entity != null) {
		
			entity.setStatus(UseSealConstant.DRAFT);
			useSealStartDao.update(entity);
			
			StartEntity start = startService.get(entity.getBusinessUUID());
			start.setStatus(StartContant.COMPLETE);
			startService.saveOrUpdate(user, start);
		}
	}
}
