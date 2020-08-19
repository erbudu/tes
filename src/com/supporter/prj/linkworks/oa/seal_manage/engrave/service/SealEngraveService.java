package com.supporter.prj.linkworks.oa.seal_manage.engrave.service;
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
//import com.supporter.prj.linkworks.oa.seal_manage.engrave.util.EngraveUtils;
import com.supporter.prj.linkworks.oa.seal_manage.engrave.dao.SealEngraveDao;
import com.supporter.prj.linkworks.oa.seal_manage.engrave.entity.SealEngrave;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;


/**   
 * @Title: Service
 * @Description: 印章销毁数据表.
 * @author z
 * @date 2019-12-12 17:52:29
 * @version V1.0   
 *
 */
@Service
public class SealEngraveService {
	
	@Autowired
	private SealEngraveDao sealEngraveDao;
	
	
	/**
	 * 根据主键获取印章销毁数据表.
	 * 
	 * @param applyId 主键
	 * @return SealEngrave
	 */
	public SealEngrave get(String applyId){
		return  sealEngraveDao.get(applyId);
	}
	
	
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<SealEngrave> getGrid(JqGrid jqGrid, SealEngrave sealEngrave,UserProfile user) {
		return sealEngraveDao.findPage(jqGrid, sealEngrave,user);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param applyId
	 * @return
	 */
	public SealEngrave initEditOrViewPage(String applyId,UserProfile user) {
		SealEngrave sealEngrave = null;
		if (StringUtils.isNotBlank(applyId)) {
			sealEngrave = (SealEngrave) this.sealEngraveDao.get(applyId);
		}
		if (StringUtils.isBlank(applyId)) {// 新建
			SealEngrave entity = new SealEngrave();
			entity.setApplyId(UUIDHex.newId());
			entity.setCreatedBy(user.getName());
			entity.setCreatedById(user.getPersonId());
			entity.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			entity.setApplyDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			entity.setIsNew(true);
			entity.setStatus(SealEngrave.DRAFT);
			entity.setIsPresident(2);//设置是否送总裁为否
			Dept dept = user.getDept();
			if (dept != null) {
				entity.setDeptName(dept.getName());
				entity.setDeptId(dept.getDeptId());
			}
			return entity;
		} else {// 编辑
			SealEngrave entity = sealEngraveDao.get(applyId);
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
	 * @param sealEngrave 实体类
	 * @return
	 */
	public SealEngrave saveOrUpdate(UserProfile user, SealEngrave sealEngrave) {
		if (user != null) {
			sealEngrave.setModifiedBy(user.getName());
			sealEngrave.setModifiedById(user.getPersonId());
		}
		if (sealEngrave.getIsNew()) {// 新建
			if (user != null) {
				sealEngrave.setCreatedBy(user.getName());
				sealEngrave.setCreatedById(user.getPersonId());
				Dept dept = user.getDept();
				if (dept != null) {
				sealEngrave.setDeptId(dept.getDeptId());
				sealEngrave.setDeptName(dept.getName());
				}
			}
			sealEngrave.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			sealEngrave.setIsNew(false);
			sealEngraveDao.save(sealEngrave);
		} else {// 编辑
			sealEngrave.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			sealEngraveDao.update(sealEngrave);
		}
		return sealEngrave;
	}
	
	/**
	 * 保存.
	 * @param user 用户信息
	 * @param sealEngrave 实体类
	 * @return
	 */
	private SealEngrave save(UserProfile user, SealEngrave sealEngrave){
		this.sealEngraveDao.save(sealEngrave);
		return sealEngrave;
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
				SealEngrave sealEngraveDb = this.sealEngraveDao.get(applyId);
				this.sealEngraveDao.delete(applyId);
			}
		}
	}
	
	/**
	 * 流程更新对象
	 * @param SealEngrave
	 * @return
	 */
	public SealEngrave update(SealEngrave sealEngrave) {
		this.sealEngraveDao.update(sealEngrave);
		return sealEngrave;
	}

	/**
	 * 进入查看页面需要加载的信息
	 * 
	 * @param applyId
	 * @return
	 */
	public SealEngrave ViewSealEngrave(String applyId) {
		return sealEngraveDao.get(applyId);
	}
	
	/**
	 * 获取知会人员
	 * @return
	 */
	public String getNotifyPersonIds(SealEngrave sealEngrave) {
		String notifyPersonIds = "";
		//申请人ID
		String createdById = sealEngrave.getCreatedById();
		notifyPersonIds += createdById;
		
		Dept dept = EIPService.getDeptService().getDept(sealEngrave.getDeptId());
		//部门经理ID
		List<Person> deptManagers = RoleUtil.getRolePersonsByDept("DEPTLEADER", dept);
		notifyPersonIds += getRolePersonIds(deptManagers);
		//部门办公室主任ID
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

	public SealEngrave savePrintMessage(String applyId, String remarks) {
		SealEngrave sealEngrave= (SealEngrave) this.sealEngraveDao.get(applyId);
		sealEngrave.setRemarks(remarks);;
		this.sealEngraveDao.update(sealEngrave);
		return sealEngrave;
	}


	public SealEngrave ViewPage(String applyId, UserProfile userProfile) {
		SealEngrave sealEngrave=sealEngraveDao.get(applyId);
		return sealEngrave;
	}


	public SealEngrave saveIsPresident(String applyId, int isPresident) {
		SealEngrave sealEngrave= (SealEngrave) this.sealEngraveDao.get(applyId);
		sealEngrave.setIsPresident(isPresident);;
		this.sealEngraveDao.update(sealEngrave);
		return sealEngrave;
	}


	public SealEngrave savePresident(String applyId) {
		SealEngrave sealEngrave= (SealEngrave) this.sealEngraveDao.get(applyId);
		sealEngrave.setIsPresident(3);;
		this.sealEngraveDao.update(sealEngrave);
		return sealEngrave;
	}
}

