package com.supporter.prj.cneec.pc.pm_prj.dao;



import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.pc.pm_prj.entity.VPmProjectOrgMemberE;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;


/**
 * @Title: DAO
 * @Description: 功能模块表
 * @version V1.0
 * 
 */
@Repository
public class VPmProjectOrgMemberEDao extends MainDaoSupport < VPmProjectOrgMemberE , String > {
	
	/**
	 *获取符合条件的项目id
	 * 
	 * @param 
	 * @return List <CneecVPcPrj>
	 */
	public List<String> getProjectIdListByPersonId(String personId) {
		String hql="from "+VPmProjectOrgMemberE.class.getName()+" where personId = '"+personId+"'";
		List<VPmProjectOrgMemberE> list =  this.find(hql);
		//System.out.println("人员权限查出的数据条数："+list.size());
		List<String> listOfProjectId=new ArrayList<String>();
		if(list!=null&&list.size()>0){
			for(VPmProjectOrgMemberE vPmProjectOrgMemberE : list){
				listOfProjectId.add(vPmProjectOrgMemberE.getProjectId());
			}
		}
		return listOfProjectId;
	}
	
	
	
	
}
