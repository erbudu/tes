package com.supporter.prj.linkworks.oa.seal_manage.destruction.service;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.util.RoleUtil;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.security.entity.UserProfile;
//import com.supporter.prj.linkworks.oa.seal_manage.destruction.util.DestructionUtils;
import com.supporter.prj.linkworks.oa.seal_manage.destruction.dao.SealDestructionDao;
import com.supporter.prj.linkworks.oa.seal_manage.destruction.entity.SealDestruction;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;


/**   
 * @Title: Service
 * @Description: 印章销毁数据表.
 * @author z
 * @date 2019-12-12 16:59:13
 * @version V1.0   
 *
 */
@Service
public class SealDestructionService {
	@Autowired
	private SealDestructionDao sealDestructionDao;
	
	
	/**
	 * 根据主键获取请假单数据表.
	 * 
	 * @param applyId 主键
	 * @return SealDestruction
	 */
	public SealDestruction get(String applyId){
		return  sealDestructionDao.get(applyId);
	}
	
	
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< SealDestruction > getGrid(JqGrid jqGrid, SealDestruction sealDestruction,UserProfile user) {
		return sealDestructionDao.findPage(jqGrid, sealDestruction,user);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param applyId
	 * @return
	 */
	public SealDestruction initEditOrViewPage(String applyId,UserProfile user) {
		SealDestruction sealDestruction = null;
		if (StringUtils.isNotBlank(applyId)) {
			sealDestruction = (SealDestruction) this.sealDestructionDao.get(applyId);
		}
		if (StringUtils.isBlank(applyId)) {// 新建
			SealDestruction entity = new SealDestruction();
			entity.setApplyId(UUIDHex.newId());
			entity.setCreatedBy(user.getName());
			entity.setCreatedById(user.getPersonId());
			entity.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			entity.setApplyDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			entity.setIsNew(true);
			entity.setStatus(SealDestruction.DRAFT);
			Dept dept = user.getDept();
			if (dept != null) {
				entity.setDeptName(dept.getName());
				entity.setDeptId(dept.getDeptId());
			}
			return entity;
		} else {// 编辑
			SealDestruction entity = sealDestructionDao.get(applyId);
			entity.setIsNew(false);
			entity.setModifiedBy(user.getName());
			entity.setModifiedById(user.getPersonId());
			entity.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			return entity;
		}
	}
	
	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param sealDestruction 实体类
	 * @return
	 */
	public SealDestruction saveOrUpdate(UserProfile user, SealDestruction sealDestruction) {
		if (user != null) {
			sealDestruction.setModifiedBy(user.getName());
			sealDestruction.setModifiedById(user.getPersonId());
		}
		if (sealDestruction.getIsNew()) {// 新建
			if (user != null) {
				sealDestruction.setCreatedBy(user.getName());
				sealDestruction.setCreatedById(user.getPersonId());
				Dept dept = user.getDept();
				if (dept != null) {
					sealDestruction.setDeptId(dept.getDeptId());
					sealDestruction.setDeptName(dept.getName());
				}
			}
			sealDestruction.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			sealDestruction.setIsNew(false);
			sealDestructionDao.save(sealDestruction);
		} else {// 编辑
			sealDestruction.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			sealDestructionDao.update(sealDestruction);
		}
		return sealDestruction;
	}
	
	/**
	 * 保存.
	 * @param user 用户信息
	 * @param sealDestruction 实体类
	 * @return
	 */
	private SealDestruction save(UserProfile user, SealDestruction sealDestruction){
		this.sealDestructionDao.save(sealDestruction);
		return sealDestruction;
	}
	
	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param applyIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String applyIds){
		if(StringUtils.isNotBlank(applyIds)){
			for(String applyId : applyIds.split(",")){
				SealDestruction sealDestructionDb = this.sealDestructionDao.get(applyId);
				this.sealDestructionDao.delete(applyId);
			}
		}
	}
	
	/**
	 * 进入查看页面需要加载的信息
	 * 
	 * @param applyId
	 * @return
	 */
	public SealDestruction ViewSealDestruction(String applyId) {
		return sealDestructionDao.get(applyId);
	}
	
	/**
	 * 流程更新对象
	 * @param SealEngrave
	 * @return
	 */
	public SealDestruction update(SealDestruction sealDestruction) {
		this.sealDestructionDao.update(sealDestruction);
		return sealDestruction;
	}


	/**
	 * 获取知会人员
	 * @return
	 */
	public String getNotifyPersonIds(SealDestruction sealDestruction) {
		String notifyPersonIds = "";
		//申请人ID
		String createdById = sealDestruction.getCreatedById();
		notifyPersonIds += createdById;
		
		Dept dept = EIPService.getDeptService().getDept(sealDestruction.getDeptId());
		//部门经理ID
		List<Person> deptManagers = RoleUtil.getRolePersonsByDept("DEPTLEADER", dept);
		notifyPersonIds += getRolePersonIds(deptManagers);
		//事业部办公室主任ID
		List<Person> bmzr = RoleUtil.getRolePersonsByDept("Director_of_theOffice", dept);
		notifyPersonIds += getRolePersonIds(bmzr);
		//综合管理部总经理ID
		List<Person> zhbzjl = RoleUtil.getRolePersonsByRoleId("INTEGRATEDMANAGER");
		notifyPersonIds += getRolePersonIds(zhbzjl);
		//秘书处处长ID
		List<Person> msccz = RoleUtil.getRolePersonsByRoleId("MISHUCHU");
		notifyPersonIds += getRolePersonIds(msccz);
		
		//HashSet去重
		String[] personIds = notifyPersonIds.split(",");
		HashSet<String> set = new HashSet<String>();
		for (String personId : personIds) {
			set.add(personId);
		}
		notifyPersonIds = StringUtils.join(set.toArray(), ",");
		return notifyPersonIds;
	}
	
	/**
	 * 获取通过角色获取的人员IDS
	 * @param persons
	 * @return
	 */
	public String getRolePersonIds(List<Person> persons) {
		String personIds = "";
		if (persons != null && persons.size() > 0) {
			for (Person person:persons) {
				personIds += "," + (person.getPersonId());
			}
		}
		return personIds;
	}
	/**
	 * 保存归档备注
	 */
	public SealDestruction savePrintMessage(String applyId, String remarks) {
		SealDestruction sealDestruction= (SealDestruction) this.sealDestructionDao.get(applyId);
		sealDestruction.setRemarks(remarks);;
		this.sealDestructionDao.update(sealDestruction);
		return sealDestruction;
	}
}

