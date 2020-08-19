package com.supporter.prj.linkworks.oa.outside_account_manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.role.entity.RoleMember;
import com.supporter.prj.eip.role.service.RoleMemberService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.outside_account_manager.dao.OutsideAccountManagerRecDao;
import com.supporter.prj.linkworks.oa.outside_account_manager.entity.OutsideAccountManagerRec;
import com.supporter.prj.linkworks.oa.outside_person.entity.OutsidePerson;
import com.supporter.util.CommonUtil;

@Service
public class OutsideAccountManagerRecService {
	@Autowired
	private OutsideAccountManagerRecDao outsideAccountManagerRecDao;
	
	@Autowired
	private RoleMemberService roleMemberService;
	
	/**
	 * 根据主键id获得实体
	 * @param id
	 * @return
	 */
	public OutsideAccountManagerRec get(String id){
		return outsideAccountManagerRecDao.get(id);
	}
	
	/**
	 * 获取人员列表
	 * @param jqGrid
	 * @param netin
	 * @return
	 */
	public List<OutsideAccountManagerRec> getGrid(JqGrid jqGrid, String managerId, UserProfile user ){
		return outsideAccountManagerRecDao.findPage(jqGrid, managerId);
	}
	
	/**
	 * 审批中获取人员列表
	 * @param jqGrid
	 * @param netin
	 * @return
	 */
	public List<OutsideAccountManagerRec> getExamGrid(JqGrid jqGrid, String managerId, UserProfile user ){
		boolean isInManagerRole = isInRole(user, "OUTSIDE_ACCOUNT_MANAGER");
		if (isInManagerRole){//如果是外聘人员账号管理员则返回全部人员列表
			return outsideAccountManagerRecDao.findPage(jqGrid, managerId);
		}else{//不是外聘人员账号管理员返回部门内人员列表
			String deptId = CommonUtil.trim(user.getDept().getDeptId());
			return outsideAccountManagerRecDao.findPageByDeptId(jqGrid, managerId, deptId);
		}
	}
	
	/**
	 * 新建或编辑维护单时初始化明细列表
	 * @param managerId
	 * @return
	 */
	public List<OutsideAccountManagerRec> getInitGrid(JqGrid jqGrid, String managerId){
		//如果维护单下没有明细，那么先初始化明细
		List<OutsideAccountManagerRec> recList = outsideAccountManagerRecDao.findPage(jqGrid, managerId);
		if (recList.size() > 0){
			return recList;
		}
		initRecByManagerId(managerId);
		return outsideAccountManagerRecDao.findPage(jqGrid, managerId);
	}
	
	/**
	 * 初始化维护单下的人员明细
	 * @param managerId
	 */
	public void initRecByManagerId(String managerId){
		//从外聘人员管理中获取所有在职状态的外聘人员
		List<OutsidePerson> outsidePersons = outsideAccountManagerRecDao.getOutsidePersonList();
		if (outsidePersons != null){
			for (OutsidePerson person : outsidePersons){
				OutsideAccountManagerRec rec = new OutsideAccountManagerRec();
				rec.setId(com.supporter.util.UUIDHex.newId());
				rec.setManagerId(managerId);
				rec.setOutsidePersonId(person.getId());
				rec.setName(person.getName());
				rec.setNameSpell(person.getNameSpell());
				rec.setSex(person.getSex());
				rec.setDeptId(person.getDeptId());
				rec.setDeptName(person.getDeptName());
				rec.setUserAccount(person.getAccount());
				rec.setMail(person.getMail());
				rec.setThisTimeStatus(person.getStatus());
				//持久化到数据库中
				this.outsideAccountManagerRecDao.save(rec);
			}
		}
	}
	
	/**
	 * 审批过程中改变了人员状态
	 * @param id
	 * @param status
	 * @return
	 */
	public OutsideAccountManagerRec updateStatus(String id, String status){
		OutsideAccountManagerRec rec = this.get(id);
		rec.setThisTimeStatus(status);
		this.outsideAccountManagerRecDao.update(rec);
		return rec;
	}
	
	/**
	 * 删除维护单下所有明细
	 * @param managerId
	 */
	public void deletedInner(String managerId){
		this.outsideAccountManagerRecDao.deletedInner(managerId);
	}
	
	/**
	 * 判断当前人员是否在某个角色中
	 * @param user
	 * @param roleId
	 * @return
	 */
	public boolean isInRole(UserProfile user, String roleId){
		String personId = CommonUtil.trim(user.getPersonId());
		List<RoleMember> roleMembers = roleMemberService.getList(roleId);
		if (roleMembers.size()>0){
			for (RoleMember member : roleMembers){
				String memberId = CommonUtil.trim(member.getMemberId());
				if (personId.equals(memberId)){
					return true;
				}
			}
		}
		return false;
	}
}
