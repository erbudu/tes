package com.supporter.prj.ppm.prj_org.member_duty.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.dept.service.IDeptService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.prj_org.base_info.common.PrjInfo;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.member_duty.dao.MemberDutyDao;
import com.supporter.prj.ppm.prj_org.member_duty.entity.MemberDutyEntity;
import com.supporter.util.UUIDHex;

/**
 * MemberDutyService
 * @author CHENHAO
 * @date 2019年12月2日
 */

@Service
public class MemberDutyService {

	@Autowired
	private MemberDutyDao memberDutyDao;
	
	@Autowired
	PrjInfo prjInfo;
	
	@Autowired
	IDeptService deptService;
	
	/**
	 * <pre>
	 *		 根据项目主键和当前登录人查询 当前登录人所负责业务
	 * </pre>
	 * @param prjId 项目主键
	 * @param personId 当前登录人ID
	 * @return 当前登录人所负责业务ID集合
	 */
	public String getResponsibleByParams(String prjId,String personId){
		
		String hql = "from " + MemberDutyEntity.class.getName() +" where prjId = ? and personId = ?";
		List<MemberDutyEntity> list = memberDutyDao.find(hql, prjId,personId);
		if(list != null && list.size()>0) {
			 return list.get(0).getResponsible();
		}
		return null;
		
	}
	
	/**
	 * 获取分页的各业务负责人列表
	 * @param jqGrid
	 * @param prjId 项目id
	 */
	public void getGrid(JqGrid jqGrid, String prjId) {
		
		if(!prjId.isEmpty()) {
			memberDutyDao.getGrid(jqGrid, prjId);
		}
		
	}

	/**
	 * 保存或修改各业务负责人信息
	 * @param entity
	 * @param user
	 * @return
	 */
	public OperResult<MemberDutyEntity> saveOrUpdate(MemberDutyEntity entity, UserProfile user) {
		
		if(entity.getIsNew()) {
			setCreaetInfo(user, entity);
			entity.setRecordId(UUIDHex.newId());
			memberDutyDao.save(entity);
			entity.setIsNew(false);
		}else{
			setMOdifiedInfo(user, entity);
			memberDutyDao.update(entity);
		}
		return OperResult.succeed("保存成功", null, entity);
		
	}

	/**
	 * 删除各业务负责人信息
	 * @param recordId  主键
	 */
	public void delMember(String recordId) {
		
		if(recordId != null && recordId != "") {
			memberDutyDao.delete(recordId);
		}
		
	}
	
/**
 * 初始化页面信息
 * @param recordId
 * @param prjId
 * @return
 */
	public MemberDutyEntity initPageData(String recordId, String prjId) {
		if(StringUtils.isBlank(recordId)){
			if(StringUtils.isNotBlank(prjId)) {
				MemberDutyEntity entity = new MemberDutyEntity();
				Prj prjInformation = prjInfo.PrjInformation(prjId);
				entity.setPrjId(prjId);
				entity.setPrjCName(prjInformation.getPrjCName());
				entity.setPrjEName(prjInformation.getPrjEName());
				entity.setIsNew(true);
				return entity;
			}
		}
		return memberDutyDao.get(recordId);		
	}
	
	
	
	
	/**
	 * <pre>
	 * 给实体类添加创建人信息
	 * @param user
	 * @param entity
	 * </pre>
	 */
	
	private void setCreaetInfo(UserProfile user, MemberDutyEntity entity) {
		
		entity.setCreatedBy(user.getName());
		entity.setCreatedById(user.getPersonId());
		entity.setCreatedDate(new Date());
		Dept dept = user.getDept();
		if(dept != null) {
			entity.setDeptName(dept.getName());
			entity.setDeptId(dept.getDeptId());
		}else {
			entity.setDeptName("admin");
			entity.setDeptId("1");
		}
	}
	
	/**
	 * <pre>
	 * 给实体类添加修改人信息
	 * @param user
	 * @param entity
	 * </pre>
	 */
	
	private void setMOdifiedInfo(UserProfile user, MemberDutyEntity entity) {

		entity.setModifiedBy(user.getName());
		entity.setModifiedById(user.getPersonId());
		entity.setModifiedDate(new Date());
		
	}

	/**
	 * 根据部门编号返回部门名称
	 * @param deptId	部门id
	 * @return	部门名称
	 */
	public String getDeptName(String deptId) {

		if(deptId == null || deptId == "") {
			return null;
		}
		
		return deptService.getDept(deptId).getName();
	}
	
	/**
	 * 根据人员id获取项目
	 * @param personId
	 * @return
	 */
	public List<String> getPrj(String personId) {
		
		if(personId == "" || personId == null) {
			return null;
		}
		
		List<String> reslut = new ArrayList<String>();
		for(MemberDutyEntity entity : memberDutyDao.findBy("personId", personId)) {
		reslut.add(entity.getPrjId());
		}
		
		return reslut;
	}

	public boolean isRepeat(String prjId, String personId, String recordId) {
		
		if(prjId == "" || prjId == null || personId == "" || personId == null) {
			return false;
		}
		return  memberDutyDao.isRepeat(prjId, personId, recordId);
	}

}
