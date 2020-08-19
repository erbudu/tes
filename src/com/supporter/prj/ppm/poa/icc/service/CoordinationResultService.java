package com.supporter.prj.ppm.poa.icc.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.role.entity.Role;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.prj.ppm.poa.icc.dao.CoordinationDao;
import com.supporter.prj.ppm.poa.icc.dao.CoordinationResultDao;
import com.supporter.prj.ppm.poa.icc.entity.Coordination;
import com.supporter.prj.ppm.poa.icc.entity.CoordinationResult;
import com.supporter.util.UUIDHex;

@Service
public class CoordinationResultService {
	@Autowired
	private CoordinationResultDao coordinationResultDao;
	@Autowired
	private CoordinationDao coordinationDao;
	public CoordinationResult saveOrUpdate(CoordinationResult coordinationResult, UserProfile user) {
		if(coordinationResult.getIccId().isEmpty()) {
			return null;
		}
		CoordinationResult coordinationResultIsIn=coordinationResultDao.getByIccId(coordinationResult.getIccId(), user);
		if (coordinationResult.getIsNew()&&coordinationResultIsIn==null) {
			coordinationResult.setRecordId((UUIDHex.newId()));
			coordinationResult.setIccResultStatus(Integer.valueOf(0));
			coordinationResult.setCreatedBy(user.getName());
			if (user.getDept() != null) {
				coordinationResult.setCreatedByDept(user.getDept().getDeptId());
			} else {
				coordinationResult.setCreatedByDept("");
			}

			coordinationResult.setCreatedById(user.getPersonId());
			if (user.getDept() != null) {
				coordinationResult.setCreateDeptId(user.getDeptId());
			} else {
				coordinationResult.setCreateDeptId("");
			}
			coordinationResult.setCreatedDate(new Date());
			coordinationResult.setModifiedDate(new Date());
			coordinationResult.setModifiedName(user.getAccount().getName());
			coordinationResult.setModifiedId(user.getAccount().getAccountId());
			
			this.coordinationResultDao.save(coordinationResult);
		} else {CoordinationResult coordinationResults=coordinationResultDao.get(coordinationResult.getRecordId());
		coordinationResults.setIccResultStatus(Integer.valueOf(0));
		coordinationResults.setIccResultDesc(coordinationResult.getIccResultDesc());
		coordinationResults.setModifiedDate(new Date());
		coordinationResults.setModifiedName(user.getName());
		coordinationResults.setModifiedId(user.getPersonId());
			
			this.coordinationResultDao.update(coordinationResults);
		}
		
		
		return coordinationResult;
	}
	public  CoordinationResult getByIccId (String iccId ,UserProfile user) {
		CoordinationResult coordinationResult=this.coordinationResultDao.getByIccId(iccId, user);
		if(coordinationResult!=null) {
         return coordinationResult;	
		}
		else {
			CoordinationResult coordinationResults =new CoordinationResult();
			return coordinationResults;
		}
	}
	
	public CoordinationResult changeStatus(CoordinationResult coordinationResult, UserProfile user) {
		//System.out.println(coordinationResult.getIccId()+"000");
		if(coordinationResult.getIccId().isEmpty()) {
			return null;
		}
		CoordinationResult coordinationResultIsIn=coordinationResultDao.getByIccId(coordinationResult.getIccId(), user);
		if (coordinationResult.getIsNew()&&coordinationResultIsIn==null) {
			//System.out.println(coordinationResult.getIccId());
			coordinationResult.setRecordId((UUIDHex.newId()));
			coordinationResult.setIccResultStatus(Integer.valueOf(1));
			coordinationResult.setCreatedBy(user.getName());
			if (user.getDept() != null) {
				coordinationResult.setCreatedByDept(user.getDept().getDeptId());
			} else {
				coordinationResult.setCreatedByDept("");
			}

			coordinationResult.setCreatedById(user.getPersonId());
			if (user.getDept() != null) {
				coordinationResult.setCreateDeptId(user.getDeptId());
			} else {
				coordinationResult.setCreateDeptId("");
			}
			coordinationResult.setCreatedDate(new Date());
			coordinationResult.setModifiedDate(new Date());
			coordinationResult.setModifiedName(user.getAccount().getName());
			coordinationResult.setModifiedId(user.getAccount().getAccountId());
			
			this.coordinationResultDao.save(coordinationResult);
		} else {CoordinationResult coordinationResults=coordinationResultDao.get(coordinationResult.getRecordId());
		coordinationResults.setIccResultStatus(Integer.valueOf(1));
		coordinationResults.setIccResultDesc(coordinationResult.getIccResultDesc());
		coordinationResults.setModifiedDate(new Date());
		coordinationResults.setModifiedName(user.getName());
		coordinationResults.setModifiedId(user.getPersonId());
			
			this.coordinationResultDao.update(coordinationResults);
		}
		
		
		return coordinationResult;
	}
	public Coordination confirmNotice(String iccId) {
		Coordination coordination = coordinationDao.get(iccId);//项目信息
		//prj.setProcId(UUIDHex.newId());
		coordination.setStatus(4);
		
		String title = "知会:"+coordination.getCreatedByDept()+" -"+coordination.getCreatedBy()+"提交的内部协调申请已通过";
		String url = "ppm/poa/icc/icc_coordination_result_notify.html?iccId="+iccId;
		String procId = coordination.getProcId();
			
			String department = Coordination.MANAGEMENT_DEPARTMENT;//经营管理部-部门
			String recorder = Coordination.ROLE_RECORDER;//经营管理部项目信息登记管理岗位人员
			//String bidFiling = BaseInfoConstant.ROLE_BIDFILING;//经营管理部投议标备案及许可岗位人员
			String trgDept=coordination.getCreateDeptId();//内部协调申请人员部门
			String srcDept =coordination.getPrjSrcDeptNo();//项目原部门
			String expatriateManagers = Coordination.ROLE_ExpatriateManagers;//经营管理部-外派事业部经理
			
			/* 通知经营管理部项目信息登记管理岗位人员 */
			List<Person> personList = getPersonListByRole(department,recorder);
			for (Person person : personList) {
				String personId = person.getPersonId();
				System.out.println(personId+"信息登记");
				sendNotice(personId,title,url,procId);
			}
			/* 通知-经营管理部-外派事业部经理原部门*/
			List<Person> personList_expatriateSrc = getPersonListByRole(srcDept,expatriateManagers);
			for (Person person_expatrSrc : personList_expatriateSrc) {
				String personId = person_expatrSrc.getPersonId();
				System.out.println(personId+"外派事业部");
				sendNotice(personId,title,url,procId);
			}
			/* 通知-经营管理部-外派事业部经理申请部门部门*/
			List<Person> personList_expatriateTrg = getPersonListByRole(trgDept,expatriateManagers);
			for (Person person_expatrTrg : personList_expatriateTrg) {
				String personId = person_expatrTrg.getPersonId();
				System.out.println(personId+"外派事业部申请");
				sendNotice(personId,title,url,procId);
			}
		
			coordinationDao.update(coordination);//通知完成后改变状态为已通知
		return coordination;
	}
private List<Person> getPersonListByRole(String deptId,String roleId) {
		
		Dept dept = EIPService.getDeptService().getDept(deptId);
		Role role = EIPService.getRoleService().getRole(roleId);
		List<Person> personList = EIPService.getRoleService().getPersonsForDept(role, dept);
		return personList;
		
	}

	
	private void sendNotice(String personId,String title,String url,String procId) {
		
		Message message = EIPService.getBMSService().newMessage();//--------------------------------获取待办通知服务内容
		message.setPersonId(personId);//------------------------------------------------------------通知人
		//String title = "知会："+purchaseDemand.getProcTitle();
		//待办标题
		message.setEventTitle(title);//-------------------------------------------------------------通知待办标题
		message.setNotifyTime(new Date());//--------------------------------------------------------通知待办日期
		//待办地址"/cpp/purchase_demand/demand/purchase_demand_audit_notify.html?id="+id
		message.setWebPageURL(url);//------------------------------------------------------------------通知待办地址
		message.setModuleId(Coordination.MODULE_ID);//------------------------------------------------------应用编号
		//默认地指定为“待办”类型
		message.setMessageType(ITodo.MsgType.CC);//-------------------------------------------------待办类型
		message.setWebappName("BM");
		//message.setWfProcId(execContext.getProcId());
		message.setWfProcId(procId);
		//加入待办事宜（BMS）
		EIPService.getBMSService().addMessage(message);//-------------------------------------------获取待办服务发送待办
		
	}
	
}
