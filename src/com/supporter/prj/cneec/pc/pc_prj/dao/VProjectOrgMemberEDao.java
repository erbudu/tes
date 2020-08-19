package com.supporter.prj.cneec.pc.pc_prj.dao;



import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.pc.pc_prj.entity.VProjectOrgMemberE;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;


/**
 * @Title: DAO
 * @Description: 功能模块表
 * @version V1.0
 * 
 */
@Repository
public class VProjectOrgMemberEDao extends MainDaoSupport < VProjectOrgMemberE , String > {
	
	/**
	 *获取符合条件的项目id
	 * 
	 * @param 
	 * @return List <CneecVPcPrj>
	 */
	public List<String> getProjectIdListByPersonId(String personId) {
		String hql="from "+VProjectOrgMemberE.class.getName()+" where personId = '"+personId+"'";
		List<VProjectOrgMemberE> list =  this.find(hql);
		//System.out.println("人员权限查出的数据条数："+list.size());
		List<String> listOfProjectId=new ArrayList<String>();
		if(list!=null&&list.size()>0){
			for(VProjectOrgMemberE vProjectOrgMemberE : list){
				listOfProjectId.add(vProjectOrgMemberE.getProjectId());
			}
		}
		return listOfProjectId;
	}
	
	
	
	
}
