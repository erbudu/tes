package com.supporter.prj.linkworks.oa.salary.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.integral.entity.PersonIntegral;
import com.supporter.prj.linkworks.oa.salary.entity.Salary;
import com.supporter.prj.linkworks.oa.salary.util.AuthConstant;
import com.supporter.prj.linkworks.oa.salary.util.SalaryConstant;
import com.supporter.util.CommonUtil;


/**
 * @Title: Entity
 * @Description: 功能模块
 * @author jiaotilei
 * @date 2017-03-15 16:25:07
 * @version V1.0
 * 
 */
@Repository
public class SalaryDao extends MainDaoSupport<Salary, String> {

	/**
	 * 分页查询
	 */
	public List<Salary> findPage(UserProfile user,JqGrid jqGrid, Salary salary) {
		if (salary != null) {
			String name = salary.getEmpName();
			if (StringUtils.isNotBlank(name)) {
				jqGrid.addHqlFilter(" empName like ?  or deptName like ?", "%" + name + "%","%" + name + "%");
			}
			String dateFrom = salary.getDateFrom();		
			if (StringUtils.isNotBlank(dateFrom)) {
				String year=dateFrom.substring(0, 4);
				String month=dateFrom.substring(5, 7);
				String salaryMonth=year+"年"+month+"月";
				jqGrid.addHqlFilter(" salaryMonth >= ? ", salaryMonth);
			}
			String dateTo = salary.getDateTo();			
			if (StringUtils.isNotBlank(dateTo)) {
				String year=dateTo.substring(0, 4);
				String month=dateTo.substring(5, 7);
				String salaryMonth=year+"年"+month+"月";
				jqGrid.addHqlFilter(" salaryMonth <=?  ", salaryMonth);
			}
			
		}
		//按年份月份倒叙排序
		jqGrid.addSortPropertyDesc("salaryMonth");
		//权限过滤（判断获取数据范围的权限）
		String authHql="";
		authHql = EIPService.getAuthorityService().getHqlFilter(user,SalaryConstant.MODULE_ID,AuthConstant.AUTH_OPER_NAME_AUTHSALARYOFLIST );
		//System.out.println("authHql==="+authHql);
		jqGrid.addHqlFilter(authHql);		
		return this.retrievePage(jqGrid);
	}
	
	
	/**
	 *根据当前登录人的id查看工资（自己查看自己的工资）
	 */
	public List<Salary> findPageByOwnerId(JqGrid jqGrid,String ownerId,String dateFrom,String dateTo) {
			if (StringUtils.isNotBlank(ownerId)) {
				jqGrid.addHqlFilter(" empId = ?", ownerId);
				
				if (StringUtils.isNotBlank(dateFrom)) {
					String year=dateFrom.substring(0, 4);
					String month=dateFrom.substring(5, 7);
					String salaryMonth=year+"年"+month+"月";
					jqGrid.addHqlFilter(" salaryMonth >= ? ", salaryMonth);
				}		
				if (StringUtils.isNotBlank(dateTo)) {
					String year=dateTo.substring(0, 4);
					String month=dateTo.substring(5, 7);
					String salaryMonth=year+"年"+month+"月";
					jqGrid.addHqlFilter(" salaryMonth <=?  ", salaryMonth);
				}
				//按年份月份倒叙排序
				jqGrid.addSortPropertyDesc("salaryMonth");
			    return this.retrievePage(jqGrid);
			}else{
				return null;
			}	
				
	}

	/**
	 **按月删除工资. 
	 */
	public List<Salary>  deleteMonthSalary(String as_SalaryMonth) {
        String ls_SalaryMonth = CommonUtil.format(as_SalaryMonth,"yyyy年MM月");	
		String hql = " from " + Salary.class.getName()+" where salaryMonth= '"+ls_SalaryMonth+"'";
	    List<Salary> list = this.find(hql);
	    if(list!=null){
	    	return list;
	    }else{
	    	return null;
	    }
	}
	
	/**
	 * 按查询条件过滤数据，然后导出数据
	 * @param entity
	 * @return
	 */
	public List<Salary> findBySearch(Salary entity) {
		String hql = "from " + Salary.class.getName()
		+ " where 1 =1 ";
		//Map<String,String> map = new HashMap<String,String>();
		String name = entity.getEmpName();
		if (StringUtils.isNotBlank(name)) {
			//hql+= " and empName like '"+name+"'  or deptName like '"+name+"'";
			hql+= " and empName like '%"+name+"%'";			
			//map.put("name",name);
		}
		String deptId = entity.getDeptId();
		if (StringUtils.isNotBlank(deptId)) {
			hql+= " and deptId = '"+deptId+"'";
			//map.put("deptId",deptId);
		}
		String dateFrom = entity.getDateFrom();	
		if (StringUtils.isNotBlank(dateFrom)) {
			String year=dateFrom.substring(0, 4);
			String month=dateFrom.substring(5, 7);
			String salaryMonth=year+"年"+month+"月";
			//System.out.println("salaryMonth小="+salaryMonth);
			hql+= " and  salaryMonth >= '"+salaryMonth+"'";
			//map.put("salaryMonth",salaryMonth);
		}
		String dateTo = entity.getDateTo();	
		if (StringUtils.isNotBlank(dateTo)) {
			String year=dateTo.substring(0, 4);
			String month=dateTo.substring(5, 7);
			String salaryMonth=year+"年"+month+"月";
			//System.out.println("salaryMonth大="+salaryMonth);
			hql+= " and  salaryMonth <= '"+salaryMonth+"'";
			//map.put("salaryMonth",salaryMonth);
		}
		hql+= " order by salaryMonth desc";

		return find(hql);
	}

}
