package com.supporter.prj.ppm.prj_org.base_info.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.prj_org.base_info.constant.BaseInfoConstant;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.member_duty.dao.MemberDutyDao;
import com.supporter.prj.ppm.prj_org.member_duty.entity.MemberDutyEntity;
import com.supporter.prj.ppm.prj_org.util.PrjConstant;
import com.supporter.util.CommonUtil;

@Repository
public class PrjDao extends MainDaoSupport < Prj, String >{	
	
	
				/**项目组织成员持久层*/
	@Autowired private  MemberDutyDao memberDutyDao;
	
	/**
	 * 项目列表分页查询
	 */
	public List<Prj> findPage(UserProfile user, JqGrid jqGrid, Prj Prj) {
		
		if(Prj != null){
			//模糊查询：项目名称,项目编号
			String PrjName = CommonUtil.trim(Prj.getKeyword());
			if(StringUtils.isNotBlank(PrjName) ){
				//只能传一个参数 把prjName看成参数
				jqGrid.addHqlFilter("prjNo like ? or  prjCName like ? or prjEName like ? ",
						"%" + PrjName + "%","%" + PrjName + "%","%" + PrjName + "%");
			}
			if(Prj.getPrjActiveStatus() != 80){
				jqGrid.addHqlFilter("prjActiveStatus = ?",Prj.getPrjActiveStatus());
			}
		}
		//权限过滤EIP
		String authHql = EIPService.getAuthorityService().getHqlFilter(user,BaseInfoConstant.MODULE_ID,BaseInfoConstant.MODULE_AUTH);
		if(authHql.equals("1=1")) {
			/*
			 * true
			 * 1=1表示查询所有数据，若EIP权限为1=1，说明没有限定条件，不做其他过滤显示所有项目数据 
			 */
			jqGrid.addHqlFilter(authHql);
			System.out.println("项目列表数据过滤权限："+authHql);
		}else {
			/*
			 *false
			 *	有限定条件，在限定条件后边追加上所负责业务的项目
			 */
			StringBuffer strBuffer = new StringBuffer(authHql);
			/*以下过滤当前登录人员在项目成员组中涉及的负责业务的项目*/
			String str = "from " + MemberDutyEntity.class.getName() +" where status = 1 and personId = ? ";
			List<MemberDutyEntity> aboutPrjPerson = memberDutyDao.find(str, user.getPersonId());
			String ot = "";
			if(aboutPrjPerson != null) {
				for (MemberDutyEntity memberDutyEntity : aboutPrjPerson) {
					ot += " or (prjId = " +"'"+ memberDutyEntity.getPrjId()+"'"+" )";
				}
			}
			strBuffer.insert(strBuffer.length()-1, ot);
			System.out.println("项目列表查询过滤："+strBuffer.toString());
			jqGrid.addHqlFilter(strBuffer.toString());
		}
		jqGrid.addSortPropertyDesc("createdDate");
		return this.retrievePage(jqGrid);
		
	}

	/**
	 * <p>根据主导部门ID取项目集合</p>
	 * <pre>说明：
	 * 			用于service中获取本部门项目编号流水号的最大值
	 * </pre>
	 * @param leadingDeptId 主导部门id
	 * @return 项目信息集合
	 */
	public List<Prj> getPrjInfoByLeadingDeptID(String leadingDeptId) {
		if(leadingDeptId == "" || leadingDeptId == null) {
			return null;
		}
		List<Prj> listResutlt = this.findBy("leadingDeptId", leadingDeptId);//根据主导部门ID获取到的项目信息集合
		return listResutlt;
	}
	
	/**
	 * <pre>查询所有</pre>
	 * @return 所有信息
	 * @author YUEYUN
	 */
	public List<Prj> findAll() {
		return this.getAllObjects();
	}

	
}
