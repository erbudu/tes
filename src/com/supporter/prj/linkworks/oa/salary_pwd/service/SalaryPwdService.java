package com.supporter.prj.linkworks.oa.salary_pwd.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.salary_pwd.dao.SalaryPwdDao;
import com.supporter.prj.linkworks.oa.salary_pwd.entity.SalaryPwd;


@Service
public class SalaryPwdService {
	@Autowired
	private SalaryPwdDao salaryPwdDao;

	public SalaryPwd get(String moduleId) {
		return salaryPwdDao.get(moduleId);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param moduleId
	 * @return
	 */
	public SalaryPwd initEditOrViewPage(String salaryId, UserProfile user) {
		if (StringUtils.isBlank(salaryId)) {// 新建
			SalaryPwd salaryPwd = newSalaryPwd(user);
			salaryPwd.setAdd(true);
			return salaryPwd;
		} else {// 编辑
			// 获得主表
			SalaryPwd salaryPwd = salaryPwdDao.get(salaryId);
			salaryPwd.setAdd(false);
			return salaryPwd;
		}
	}

	/**
	 * 新建实例,并初始化必要的属性.
	 * 
	 * @param auserprf_U
	 * @return
	 */
	public SalaryPwd newSalaryPwd(UserProfile auserprf_U) {
		SalaryPwd lsalaryPwd_N = new SalaryPwd();		
		lsalaryPwd_N.setSalaryId(com.supporter.util.UUIDHex.newId());
		return lsalaryPwd_N;
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
	public boolean getGrid(UserProfile user, JqGrid jqGrid,
			SalaryPwd salaryPwd) {
		List<SalaryPwd> list = this.salaryPwdDao.findPage(jqGrid,
				salaryPwd);
		if(list!=null){
			return list.size()>0;
		}else{
			 return false;
		}
		

	}
	
	
	
	//判断该用户是不是第一次查询工资（如果是第一次，首先设置查询密码）
	public boolean getBySalaryName(JqGrid jqGrid,SalaryPwd salaryPwd) {
		List<SalaryPwd> list = this.salaryPwdDao.getBySalaryName(jqGrid,
				salaryPwd);
		if(list!=null){
			return list.size()>0;
		}else{
			 return false;
		}
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
	public SalaryPwd saveOrUpdate(UserProfile user, SalaryPwd salaryPwd,
			Map<String, Object> valueMap) {
		SalaryPwd ret = null;
		if (salaryPwd.isAdd()) {// 新建
			salaryPwd.setSalaryName(user.getName());
			salaryPwd.setUserName(user.getAccountLogin());
			String password = (salaryPwd.getSalaryPwd().split(","))[0];
			salaryPwd.setSalaryPwd(password);
			// 保存主表
			this.salaryPwdDao.save(salaryPwd);
			ret = salaryPwd;
			// 记录日志
			/*
			 * MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
			 * MaterialCode.LogOper.MATERIALCODE_ADD.getOperName(), null);
			 */
		} else {// 编辑
			// 编辑之后保存主表
			this.salaryPwdDao.update(salaryPwd);
			ret = salaryPwd;
			// 记录日志
			/*
			 * MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
			 * MaterialCode.LogOper.MATERIALCODE_EDIT.getOperName(), null);
			 */
		}
		return ret;

	}

//	/**
//	 * 删除
//	 * 
//	 * @param user
//	 *            用户信息
//	 * @param moduleIds
//	 *            主键集合，多个以逗号分隔
//	 */
//	public void delete(UserProfile user, String salaryIds) {
//		if (StringUtils.isNotBlank(salaryIds)) {
//			for (String salaryId : salaryIds.split(",")) {
//				// 删除主表
//				this.salaryPwdDao.delete(salaryPwdDao.get(salaryId));
//			}
//			// EIPService.getLogService("MPM_MCA").info(user,
//			// Contract.LogOper.MODULE_DEL.getOperName(), "{delContractIds : " +
//			// moduleIds + "}", null, null);
//		}
//	}


}
