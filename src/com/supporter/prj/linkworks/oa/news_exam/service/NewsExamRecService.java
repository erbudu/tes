package com.supporter.prj.linkworks.oa.news_exam.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.emp.entity.IEmployee;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.role.entity.Role;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.news_exam.dao.NewsExamRecDao;
import com.supporter.prj.linkworks.oa.news_exam.entity.NewsExamRec;
import com.supporter.util.CommonUtil;

/**
 * 主题新闻审批服务类.
 * @author linda
 *
 */
@Service
public class NewsExamRecService {
	@Autowired
	private NewsExamRecDao newExamRecDao;
	
	//应用名
	public static final String APP_NAME = "OANEWSWF";
	
	/**
	 * 获取实体对象.
	 * @param recId
	 * @return
	 */
	public NewsExamRec getNewsExamRec(String recId){
		NewsExamRec rec = newExamRecDao.get(recId);

		return rec;
	}
	
	public NewsExamRec newNewsExamRec(UserProfile user){
		NewsExamRec rec = new NewsExamRec();
		if (user != null){
			Dept dept = user.getDept();
			if (dept != null){
				rec.setDeptId(dept.getDeptId());
				rec.setDeptName(dept.getName());
			}
			IEmployee emp = (IEmployee)user.getPerson();
			if (emp != null){
				rec.setProposerId(emp.getPersonId());
				rec.setProposerName(emp.getName());
				rec.setPhone(emp.getPhoneNo());
			}
		}
		rec.setApplyDate(new Date());
		rec.setPublishTo(NewsExamRec.PublishTo.INNER_WEB);
		rec.setInnerPublishTo(NewsExamRec.PublishTo.TOPIC_NEWS);
		rec.setRecStatus(NewsExamRec.Status.DRAFT);

		return rec;
	}
	
	/**
	 * 保存(新增或更新).
	 * @param rec
	 */
	@Transactional(transactionManager = TransManager.APP)
	public NewsExamRec saveOrUpdate(NewsExamRec rec){
		if (rec == null)return rec;
		
		//给分管领导赋值(流程变量)
		if (CommonUtil.trim(rec.getSignerIds()).length() == 0){
			Role role = EIPService.getRoleService().getRole(WfRole.COMPANYLEADERS);
			Dept dept = EIPService.getDeptService().getDept(rec.getDeptId());
			
			List < Person > compManagers = EIPService.getRoleService().getPersonsForDept(role, dept);
			String empIds = "";
			String empNames = "";
			if (compManagers != null && compManagers.size() > 0){
				for (int i = 0; i < compManagers.size(); i++){
					Person emp = compManagers.get(i);
					if (empIds.length() > 0){
						empIds += ",";
						empNames += ",";
					}
					empIds += emp.getPersonId();
					empNames += emp.getName();
				}
				rec.setSignerIds(empIds);
				rec.setSignerNames(empNames);
			}
		}
		
		if (CommonUtil.trim(rec.getRecId()).length() == 0){
			if (rec.getApplyDate() == null)rec.setApplyDate(new Date());
			newExamRecDao.save(rec);
		} else {
			newExamRecDao.update(rec);
		}
		
		return rec;
	}
	
	/**
	 * 删除.
	 * @param recIds
	 */
	@Transactional(transactionManager = TransManager.APP)
	public void delete(String recIds){
		recIds = CommonUtil.trim(recIds);
		if (recIds.length() == 0)return;
		
		String[] idArray = recIds.split(",");
		for (int i = 0; i < idArray.length; i++){
			String recId = CommonUtil.trim(idArray[i]);
			if (recId.length() == 0)continue;
			newExamRecDao.delete(recId);
		}
	}
	
	/**
	 * 获取JqGrid表格.
	 * @param jqGrid
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List < NewsExamRec > getGrid(UserProfile user, JqGrid jqGrid, Map < String, Object > params){
		return newExamRecDao.getGrid(user, jqGrid, params);
	}
	
	/**
	 * 根据主管部门ID判断公司主管领导是否为总裁.
	 * @param deptId
	 * @return
	 */
	public boolean isCompLeader(NewsExamRec rec){
		String compManagerIds = CommonUtil.trim(rec.getSignerIds());
		if (compManagerIds.length() == 0)return false;
		
		String[] arrayIds = compManagerIds.split(",");
		
		String empNameKeyWord = "";
		List < Person > zongcai = EIPService.getRoleService().getPersonFromRole(WfRole.PRESIDENT, empNameKeyWord);
		if (zongcai == null || zongcai.size() == 0 || zongcai.size() > 1)return false;
		
		String zcId = CommonUtil.trim(zongcai.get(0).getPersonId());
		for (int i = 0; i < arrayIds.length; i++){
			String empId = CommonUtil.trim(arrayIds[i]);
			if (zcId.equals(empId)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 权限.
	 * @author linda
	 *
	 */
	public static final class Auth {
		public static final String MANAGE = "manage"; //增加、修改、删除权限
		public static final String LIST = "list"; //列表浏览权限
	}
	
	/**
	 * 角色.
	 * @author linda
	 *
	 */
	public static final class WfRole {
		public static final String PRESIDENT = "PRESIDENT"; //总裁
		public static final String COMPANYLEADERS = "zhuguanlingdao"; //公司领导
	}
}
