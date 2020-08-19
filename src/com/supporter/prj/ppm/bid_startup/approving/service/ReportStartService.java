/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.approving.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.role.entity.Role;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.prj.ppm.bid_startup.approving.constant.ReportStartConstant;
import com.supporter.prj.ppm.bid_startup.approving.dao.ReportStartDao;
import com.supporter.prj.ppm.bid_startup.approving.entity.ReportStartEntity;
import com.supporter.prj.ppm.bid_startup.preparation.entity.StartEntity;
import com.supporter.prj.ppm.bid_startup.preparation.service.StartService;
import com.supporter.prj.ppm.ecc.group_review.util.AuthUtils;
import com.supporter.prj.ppm.poa.use_seal.service.UseSealStartService;
import com.supporter.prj.ppm.prj_org.base_info.common.PrjInfo;
import com.supporter.prj.ppm.prj_org.base_info.constant.BaseInfoConstant;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.service.PPMService;
import com.supporter.util.UUIDHex;

/**
 *<p>Title: ReportStartService</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月2日
 * 
 */
@Service
public class ReportStartService {
	
	@Autowired
	private ReportStartDao reportStartDao;

	@Autowired
	private PrjInfo prjInfo;
	
	@Autowired
	private StartService startService;
	
	@Autowired
	private UseSealStartService useSealStartService;
	
	/**
	 * <pre>发送通知</pre>
	 * @param prjId 项目主键
	 * @param reportStartId 启动对外提报主键
	 * @return 实体类
	 */
	public ReportStartEntity confirmNotice(String prjId, String reportStartId,UserProfile user) {
		//获取项目信息通知项目负责人
		Prj prj = prjInfo.PrjInformation(prjId);
		String prjLeader = prj.getCreatedById();//项目负责人id
		
		/*获取启动对外提报业务单实体类数据 -1.设流程id 2.改流程状态*/
		ReportStartEntity startEntity = reportStartDao.get(reportStartId);//启动对外提报业务单实体类
		startEntity.setProcId(UUIDHex.newId());
		startEntity.setStatus(ReportStartConstant.NOTICEED);//状态-已通知
		
		/*发送待办需要的相关信息*/
		String title1 = "知会:"+"您负责的项目【对外提报及获批】已受理";//项目负责人待办知会标题
		String title2 = "知会:"+prj.getDeptName()+"-"+prj.getCreatedBy()+"负责的"+"\""+prj.getPrjCName()+"\""+"的项目【对外提报及获批】已受理";//经营管理部外派事业部经理待办知会标题
		String url = "ppm/bid_startup/approving/approving_notice_WFtodo.html?prjId="+prjId+"&reportStartId="+reportStartId;//待办知会页面路径
		String procId = startEntity.getProcId();
		
		/*发送待办给项目负责人*/
		sendNoticeToPrjLeader(prjLeader,title1,url,startEntity.getProcId());
		
		/*发送待办给经营管理部-外派事业部经理*/
		sendNoticeToexpatriateManagers(title2,url,procId);
		
		/*保存更改后的启动对外提报业务表单信息*/
		reportStartDao.update(startEntity);
		PPMService.getScheduleStatusService().saveScheduleStatus(prjId, "approving03", user);
		return startEntity;
	}
	
	/**
	 * <pre>发送待办给经营管理部-外派事业经理</pre>
	 * @param title2 待办标题
	 * @param url 页面路径
	 * @param procId 流程id
	 */
	private void sendNoticeToexpatriateManagers(String title2, String url, String procId) {
		String department = BaseInfoConstant.MANAGEMENT_DEPARTMENT;//经营管理部-部门-部门
		String expatriateManagers = BaseInfoConstant.ROLE_ExpatriateManagers;//经营管理部-外派事业部经理-角色
		/* 通知-经营管理部-外派事业部经理 */
		List<Person> personList_expatriate = getPersonListByRole(department,expatriateManagers);
		for (Person person_expatr : personList_expatriate) {
			String personId = person_expatr.getPersonId();
			sendNotice(personId,title2,url,procId);
		}
	}
	/**
	 * <pre>根据部门和角色获取角色下的人员信息</pre>
	 * @param deptId 部门编号
	 * @param roleId 角色编号
	 * @return 角色下的人员信息
	 */
	private List<Person> getPersonListByRole(String deptId,String roleId) {
		
		Dept dept = EIPService.getDeptService().getDept(deptId);
		Role role = EIPService.getRoleService().getRole(roleId);
		List<Person> personList = EIPService.getRoleService().getPersonsForDept(role, dept);
		return personList;
		
	}

	/**
	 * <pre>发送待办给项目负责人</pre>
	 * @param prjLeader 项目负责人id
	 * @param title1 待办标题
	 * @param url 待办页面
	 */
	private void sendNoticeToPrjLeader(String prjLeader, String title1, String url,String procId) {
		sendNotice(prjLeader,title1,url,procId);
	}

	/**
	 * <pre>发送待办通知</pre>
	 * @param personId 通知人主键
	 * @param title  待办标题
	 * @param url 待办页面路径
	 * @param procId 流程id
	 */
	private void sendNotice(String personId,String title,String url,String procId) {
		
		Message message = EIPService.getBMSService().newMessage();//--------------------------------获取待办通知服务内容
		message.setPersonId(personId);//------------------------------------------------------------通知人
		//String title = "知会："+purchaseDemand.getProcTitle();
		//待办标题
		message.setEventTitle(title);//-------------------------------------------------------------通知待办标题
		message.setNotifyTime(new Date());//--------------------------------------------------------通知待办日期
		//待办地址"/cpp/purchase_demand/demand/purchase_demand_audit_notify.html?id="+id
		message.setWebPageURL(url);//---------------------------------------------------------------通知待办地址
		message.setModuleId(BaseInfoConstant.MODULE_ID);//------------------------------------------应用编号
		//默认地指定为“待办”类型
		message.setMessageType(ITodo.MsgType.CC);//-------------------------------------------------待办类型
		message.setWebappName("BM");
		//message.setWfProcId(execContext.getProcId());
		message.setWfProcId(procId);
		//加入待办事宜（BMS）
		EIPService.getBMSService().addMessage(message);//-------------------------------------------获取待办服务发送待办
		
	}
	
	
	/**
	 * <p>单项删除</p>
	 * @param reportStartId 启动对外提报业务表单主键
	 */
	public void deleteSingle(String reportStartId) {
		reportStartDao.delete(reportStartId);
	}
	
	/**
	 * <p>启动申报 保存更新</p>
	 * @param user 当前登录用户
	 * @param reportStartEntity 启动申报业务表单实体类
	 * @return 保存后的业务表单实体类
	 */
	public ReportStartEntity saveOrUpdate(UserProfile user, ReportStartEntity reportStartEntity) {
		if(reportStartEntity.getNewBuild()) {//newBuild == true 新建
			setCreatorInformation(user,reportStartEntity);//set制单人信息
			reportStartEntity.setStatus(ReportStartConstant.NOT_EFFECT);//未生效
			//权限验证
			AuthUtils.canExecute(user,ReportStartConstant.MODULE_ID, ReportStartConstant.AUTH_EDIT,reportStartEntity.getReportStartId(),reportStartDao.get(reportStartEntity.getReportStartId()));
			reportStartDao.save(reportStartEntity);
			PPMService.getScheduleStatusService().saveScheduleStatus(reportStartEntity.getPrjId(), "approving01", user);
			reportStartEntity.setNewBuild(false);//保存完成后，是否新建标识为false
			return reportStartEntity;
		}else {//newBuild == false 更新
			setModifierInformation(user,reportStartEntity);//set最后修改人信息
			//权限验证
			AuthUtils.canExecute(user,ReportStartConstant.MODULE_ID, ReportStartConstant.AUTH_EDIT,reportStartEntity.getReportStartId(),reportStartDao.get(reportStartEntity.getReportStartId()));
			reportStartDao.update(reportStartEntity);
			return reportStartEntity;
		}
	}
	
	/**
	 * <pre>新建或编辑页面数据初始化</pre>
	 * <pre>业务逻辑：
	 * 	项目跟对外提报为一对一的关系，这里根据项目主键查询启动对外提报业务单实体类数据
	 * 	1.没查到数据，新建
	 * 	2.查到数据，编辑或查看，返回查到的业务单数据
	 * </pre>
	 * @param prjId 项目主键
	 * @return 业务表单实体类
	 */
	public ReportStartEntity initAddOrEditPage(String prjId) {
		if(prjId == "" || prjId == null) {
			return null;
		}
		Prj prjInformation = prjInfo.PrjInformation(prjId);//项目信息
		ReportStartEntity entityByPrjId = reportStartDao.getEntityByPrjId(prjId);
		if(entityByPrjId == null) {
			ReportStartEntity entity = new ReportStartEntity();
			entity.setReportStartId(UUIDHex.newId());//
			entity.setPrjId(prjInformation.getPrjId());//项目主键
			entity.setPrjNo(prjInformation.getPrjNo());//项目编号
			entity.setPrjNameC(prjInformation.getPrjCName());//项目中文名称
			entity.setPrjNameE(prjInformation.getPrjEName());//项目 英文名称
			entity.setNewBuild(true);//标识为新建 
			return entity;
		}else {
			return entityByPrjId;
		}
	}

	/**
	 * <P> set制单人相关信息 </P>
	 * @param user 当前登录用户
	 * @param reportStartEntity 启动申报业务表单实体类
	 */
	public void setCreatorInformation(UserProfile user,ReportStartEntity reportStartEntity) {
		reportStartEntity.setCreatedBy(user.getName());
		reportStartEntity.setCreatedById(user.getPersonId());
		Dept dept = user.getDept();
		if(dept != null) {
			reportStartEntity.setDeptName(dept.getName());
			reportStartEntity.setDeptId(dept.getDeptId());
		}
		reportStartEntity.setCreatedDate(new Date());
	}
	
	/**
	 * <P> set 最后修改人相关信息</P>
	 * @param user 当前登录用户
	 * @param reportStartEntity 启动申报业务表单实体类
	 */
	public void setModifierInformation(UserProfile user,ReportStartEntity reportStartEntity) {
		reportStartEntity.setModifiedBy(user.getName());
		reportStartEntity.setModifiedById(user.getPersonId());
		reportStartEntity.setModifiedDate(new Date());
	}
	
	public ReportStartEntity get(String reportStartId) {
		if(reportStartId == null || reportStartId == "") {
			return null;
		}
		return reportStartDao.get(reportStartId);
	}
	
	public void update(ReportStartEntity reportStartEntity) {
		reportStartDao.update(reportStartEntity);
	}

	/**
	 * 查看是否有权限进入对外提报及获批
	 * @param user
	 * @param prjId 项目id
	 * @return true 可以进入 false 不可以进入
	 */
	
	public boolean validRole(UserProfile user,String prjId){
			
		boolean result = false;
		
		Role role = EIPService.getRoleService().getRole(ReportStartConstant.ROLE_BIDFILING);
		
		List<Person> list = EIPService.getRoleService().getPersonsForDept(role,null);
			
		for(Person person : list) {
			
			if(person.getPersonId().equals(user.getPersonId()) || user.getPersonId().equals("1")) {
				
				StartEntity startEntity = startService.initEditOrViewPage(prjId);
				if(startEntity.getIsNew() == false) {
					if(useSealStartService.getByBusinessId(startEntity.getBidIngUpId()) != null && useSealStartService.getByBusinessId(startEntity.getBidIngUpId()).getStatus() == 4) {
						result = true;
					}
				}
				break;
			}
		}

		return result;
	}

}
