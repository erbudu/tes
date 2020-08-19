/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.approving.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.role.entity.Role;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.bid_startup.approving.constant.ReportStartConstant;
import com.supporter.prj.ppm.bid_startup.approving.dao.ReportResultDao;
import com.supporter.prj.ppm.bid_startup.approving.entity.ReportResultEntity;
import com.supporter.prj.ppm.bid_startup.approving.entity.ReportStartEntity;
import com.supporter.prj.ppm.prj_org.base_info.common.PrjInfo;
import com.supporter.prj.ppm.prj_org.base_info.constant.BaseInfoConstant;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.service.PPMService;
import com.supporter.util.UUIDHex;

/**
 *<p>Title: ReportResultService</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月3日
 * 
 */
@Service
public class ReportResultService {
	
	@Autowired
	private ReportResultDao reportResultDao;
	
	@Autowired
	private ReportStartService reportStartService;

	@Autowired
	public  PrjInfo prjInfo;
	
	/**
	 * <pre>描述：此方法用于根据项目主键获取对外提报的报审结果</pre>
	 * @param prjId 项目主键
	 * @return Boolean true 通过
	 */
	public boolean reportResult(String prjId) {
		
		ReportResultEntity result = reportResultDao.findUniqueResult("prjId", prjId);
		if(result!=null) {
			Integer resultTypeID = result.getResultTypeID();
			if(resultTypeID == 0) {//0通过 1 不通过
				return true;
			}
		}
		return false;
	}
	
	/**
	 * <pre>获取通知人员列表</pre>
	 * @param prjId 项目主键
	 * @return 通知人员列表
	 */
	public List<Map<String, String>> initNoticePerson(String prjId) {
		
		if(prjId == null || prjId == "") {
			return null;
		}
		
		ReportStartEntity entity = reportStartService.initAddOrEditPage(prjId);
		
		String status =  entity.getStatus().toString();
		System.out.println("status:"+status);
		Role role2Entity = EIPService.getRoleService().getRole(BaseInfoConstant.ROLE_ExpatriateManagers);//角色：经营管理部-经营管理部外派事业部经理
		String department = BaseInfoConstant.MANAGEMENT_DEPARTMENT;//经营管理部:部门编号
		
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String, String>();
		String role1 = "项目开发负责人";
		String role1Person = prjInfo.PrjInformation(prjId).getCreatedBy();//创建人=项目负责人
		map.put("role1", role1);
		map.put("role1Person", role1Person);
		
		
		String role2 = role2Entity.getName();
		map.put("role2", role2);
		/*角色下人员*/
		String role2Person = "";
		List<Person> role2PersList = getPersonListByRole(department,role2Entity.getRoleId());
		for (int i = 0; i < role2PersList.size(); i++) {
			role2Person += role2PersList.get(i).getName()+"、";
		}
		map.put("role2Person", role2Person);
		map.put("status", status);
		list.add(map);
		return list;
	}

	private List<Person> getPersonListByRole(String deptId, String roleId) {

		Dept dept = EIPService.getDeptService().getDept(deptId);
		Role role = EIPService.getRoleService().getRole(roleId);
		List<Person> personList = EIPService.getRoleService().getPersonsForDept(role, dept);
		return personList;

	}
	
	/**
	 * 业务表单删除
	 * @param reportResultId
	 */
	public void delete(String reportResultId) {
		reportResultDao.delete(reportResultId);
		
	}
	
	/**
	 * @param reportStartId 启动对外提报业务表单主键
	 * @param prjId 项目主键
	 * @return 审批结果业务表单实体类
	 */
	public ReportResultEntity initAddOrEditPage(String reportStartId, String prjId) {
		Prj prjInformation = prjInfo.PrjInformation(prjId);//项目信息
		ReportResultEntity entity =reportResultDao.getEntityByRepStartId(reportStartId);
		if(entity == null) {
			ReportResultEntity resultEntity = new ReportResultEntity();
			resultEntity.setReportResultId(UUIDHex.newId());
			resultEntity.setReportStartId(reportStartId);//外键
			resultEntity.setPrjId(prjInformation.getPrjId());//项目主键
			resultEntity.setPrjNo(prjInformation.getPrjNo());//项目编号
			resultEntity.setPrjNameC(prjInformation.getPrjCName());//项目中文名称
			resultEntity.setPrjNameE(prjInformation.getPrjEName());//项目 英文名称
			resultEntity.setNewBuild(true);//标识为新建
			return resultEntity;
		}
		return entity;
	}

	/**
	 * 		保存并生效
	 * @param entity
	 * @return
	 */
	public ReportResultEntity effect(ReportResultEntity entity,UserProfile user) {
		
		if (entity == null) return null;

		if (entity.getNewBuild()) {
			entity.setNewBuild(false);
			reportResultDao.save(entity);
			PPMService.getScheduleStatusService().saveScheduleStatus(entity.getPrjId(), "approving02", user);
			ReportStartEntity reportStartEntity = reportStartService.get(entity.getReportStartId());
			reportStartEntity.setStatus(ReportStartConstant.EFFECT);
			reportStartService.update(reportStartEntity);
			return entity;
		} else {
			reportResultDao.update(entity);
			ReportStartEntity reportStartEntity = reportStartService.get(entity.getReportStartId());
			reportStartEntity.setStatus(ReportStartConstant.EFFECT);
			reportStartService.update(reportStartEntity);
			return entity;
		}
	}

	/**
	 * @param entity
	 * @return
	 */
	public ReportResultEntity saveOrUpdate(ReportResultEntity entity,UserProfile user) {
		if(entity == null) return null;
		
		if(entity.getNewBuild()) {
			entity.setNewBuild(false);
			reportResultDao.save(entity);
			PPMService.getScheduleStatusService().saveScheduleStatus(entity.getPrjId(), "approving02", user);
			return entity;
		}else {
			reportResultDao.update(entity);
			return entity;
		}
	}

	/**
	 * <P>根据启动对外提报业务表单主键删除对外提报审批结果业务表单</P>
	 * @param reportStartId 启动对外提报业务表单主键
	 */
	public void deleteByRrpStartId(String reportStartId) {
		reportResultDao.deleteByProperty("reportStartId", reportStartId);
	}

	

	
	
}
