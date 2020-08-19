package com.supporter.prj.linkworks.oa.membership_info.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.membership_info.constants.AuthConstant;
import com.supporter.prj.linkworks.oa.membership_info.dao.MembershipInfoDao;
import com.supporter.prj.linkworks.oa.membership_info.entity.MembershipInfo;
import com.supporter.prj.linkworks.oa.membership_info.util.AuthUtils;

/**
 * @Title: Service
 * @Description: 报告
 * @author liyinfeng
 * @date 2017-7-05 10:25:07
 * @version V1.0
 * 
 */
@Service
@Transactional(TransManager.APP)
public class MembershipInfoService {
	@Autowired
	private MembershipInfoDao membershipInfoDao;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param moduleId
	 * @return
	 */
	public MembershipInfo initEditOrViewPage(String membershipInfoId,
			UserProfile user) {
		if (StringUtils.isBlank(membershipInfoId)) {// 新建
			MembershipInfo membershipInfo = newMembershipInfo(user);
			return membershipInfo;
		} else {// 编辑
			MembershipInfo MembershipInfo = membershipInfoDao
					.get(membershipInfoId);
			return MembershipInfo;
		}
	}

	/**
	 * 新建实例,并初始化必要的属性.
	 * 
	 * @param auserprf_U
	 * @return
	 */
	public MembershipInfo newMembershipInfo(UserProfile auserprf_U) {
		MembershipInfo lMembershipInfo_N = new MembershipInfo();
		lMembershipInfo_N.setCreatedBy(auserprf_U.getName());
		lMembershipInfo_N.setCreatedById(auserprf_U.getPersonId());
		lMembershipInfo_N.setCreatedDate(sdf.format(new Date()));
		lMembershipInfo_N.setIsMember("1");
		return lMembershipInfo_N;
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user
	 *            用户信息
	 * @param jqGridReq
	 *            jqgrid请求对象
	 * @param moduleIds
	 *            多个逗号分隔
	 * @return JqGrid
	 */
	public List<MembershipInfo> getGrid(UserProfile user, JqGrid jqGrid,
			MembershipInfo entity) {
		List<MembershipInfo> list = membershipInfoDao.findPage(user, jqGrid,
				entity);
		for (MembershipInfo membershipInfo : list) {
			Person person = EIPService.getEmpService().getEmployee(
					membershipInfo.getEmpId());
			if (person != null) {
				Dept dept = person.getDept();
				if (dept != null) {
					membershipInfo.setDeptName(dept.getName());
				}
			}
		}
		jqGrid.setRows(list);
		return list;
	}

	/**
	 * 保存或更新
	 * 
	 * @param user
	 *            用户信息
	 * @param module
	 *            实体类
	 * @return
	 */
	public MembershipInfo saveOrUpdate(UserProfile user, MembershipInfo entity,
			Map<String, Object> valueMap) {
		if (StringUtils.isBlank(entity.getRecId())) {//
			//String empNos = entity.getEmpNo();
			String empIds = entity.getEmpId();
			/*if (StringUtils.isNotBlank(empNos)) {
				for (String empNo : empNos.split(",")) {
					Person p = EIPService.getEmpService().getEmpByNo(empNo);
					if (p != null) {
						MembershipInfo info = new MembershipInfo();
						info.setEmpId(p.getPersonId());
						info.setEmpName(p.getName());
						info.setEmpNo(p.getPersonNo());
						info.setCreatedBy(entity.getCreatedBy());
						info.setCreatedById(entity.getCreatedById());
						info.setCreatedDate(entity.getCreatedDate());
						info.setIsMember(entity.getIsMember());
						this.membershipInfoDao.save(info);
					} else {
						return null;
					}
				}
			} else */if (StringUtils.isNotBlank(empIds)) {
				for (String empId : empIds.split(",")) {
					Person p = EIPService.getEmpService().getEmployee(empId);
					if (p != null) {
						MembershipInfo info = new MembershipInfo();
						info.setEmpId(p.getPersonId());
						info.setEmpName(p.getName());
						info.setEmpNo(p.getPersonNo());
						info.setCreatedBy(entity.getCreatedBy());
						info.setCreatedById(entity.getCreatedById());
						info.setCreatedDate(entity.getCreatedDate());
						info.setIsMember(entity.getIsMember());
						this.membershipInfoDao.save(info);
					} else {
						return null;
					}
				}
			}
			// 记录日志
			// ModuleUtils.saveModuleOperateLog(user, module,
			// Contract.LogOper.MODULE_ADD.getOperName(), null);

		} else {// 编辑

			entity.setModifiedBy(user.getName());
			entity.setModifiedById(user.getPersonId());
			entity.setModifiedDate(sdf.format(new Date()));
			this.membershipInfoDao.update(entity);
			// 记录日志
			// ModuleUtils.saveModuleOperateLog(user, module,
			// Contract.LogOper.MODULE_EDIT.getOperName(), null);
		}

		return entity;

	}

	/**
	 * 删除
	 * 
	 * @param user
	 *            用户信息
	 * @param moduleIds
	 *            主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String membershipInfoIds) {
		if (StringUtils.isNotBlank(membershipInfoIds)) {
			for (String membershipInfoId : membershipInfoIds.split(",")) {
				AuthUtils.canExecute(user,
						AuthConstant.AUTH_OPER_NAME_SETVALMEMBERSHIP,
						membershipInfoId, membershipInfoDao
								.get(membershipInfoId));
				this.membershipInfoDao.delete(membershipInfoId);
			}
			// EIPService.getLogService("MPM_MCA").info(user,
			// Contract.LogOper.MODULE_DEL.getOperName(), "{delContractIds : " +
			// moduleIds + "}", null, null);
		}
	}

	public MembershipInfo changeMembershipType(UserProfile user,
			String membershipInfoId, String type) {
		MembershipInfo entity = membershipInfoDao.get(membershipInfoId);
		entity.setIsMember(type);
		membershipInfoDao.update(entity);
		return entity;
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param moduleId
	 * @param moduleName
	 * @return
	 */
	public Map checkNameIsValid(String membershipInfoId, String empNos) {
		boolean bo = true;
		Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
		if (StringUtils.isNotBlank(empNos)) {
			int i = 0;
			for (String empNo : empNos.split(",")) {
				bo = this.membershipInfoDao.checkNameIsValid(membershipInfoId,
						empNo);
				map.put(i, bo);
				i++;
			}
		}
		return map;
	}
}
