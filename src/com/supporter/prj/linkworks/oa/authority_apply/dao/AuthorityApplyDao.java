package com.supporter.prj.linkworks.oa.authority_apply.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApply;
import com.supporter.prj.linkworks.oa.authority_apply.util.AuthConstant;
import com.supporter.prj.linkworks.oa.authority_apply.util.AuthorityApplyConstant;
import com.supporter.prj.linkworks.oa.use_seal_apply.entity.UseSealApply;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Entity
 * @Description: 功能模块�?
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class AuthorityApplyDao extends MainDaoSupport < AuthorityApply, String > {
	
/*	public Integer getCount() {
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
		String date=sdf.format(new Date());
		String hql = "select count(*) from "
				+ AuthorityApply.class.getName()+" where createdDate like '%"+date+"%'";
		List<Long> list = this.find(hql);
		if (list.size() > 0) {
			if (list.get(0) != null && !list.get(0).equals("")) {
				return list.get(0).intValue();
			} else {
				return Integer.valueOf(0);
			}
		}
		 return null;
	}*/

	
	
	public String getSignNo() {
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
		String year=sdf.format(new Date());
//		String hql = "select max(signNo) from "
//			+ UseSealApply.class.getName()+" where signNo like '印%号（"+year+"）%'";
		String hql = "select max(signNo) from "
				+ AuthorityApply.class.getName()+" where signNo like '中电授字【"+year+"】第%号'";
		List<String> list = this.find(hql);
		if(list!=null){
			if (list.size() > 0) {
				if (list.get(0) != null && !list.get(0).equals("")) {
					return list.get(0).toString();
				} else {					
					return "中电授字【"+year+"】第0000号";
				}
			}else{
				return "中电授字【"+year+"】第0000号";
			}
		}else{
			return "中电授字【"+year+"】第0000号";
		}
	}
	
	
	
	
	public AuthorityApply getContractByIdOrName(String contractIdOrName) {
		String hql = "from " + AuthorityApply.class.getName() + " where reportId = ? or contractName = ?";
		List < AuthorityApply > contracts = this.find(hql, contractIdOrName, contractIdOrName);
		if (contracts == null || contracts.size() == 0) return null;
		
		if (contracts.size() > 1) {
			EIPService.getLogService().error("找到 " + contracts.size() + " 个模块：" + contractIdOrName);
		}
		
		return contracts.get(0);
	}
	

	/**
	 * 查询操作. 
	 * @param budgetYear
	 * @param keyword
	 * @return List < Report >
	 */
	public List < AuthorityApply > findByKeyword(String keyword) {
		String hql = "from " + AuthorityApply.class.getName() + " where contractName like ?";
		List < AuthorityApply > entities = this.find(hql, "%" + CommonUtil.trim(keyword) + "%");
		return entities;
	}


//	/**
//	 * 分页查询
//	 * @param jqGrid
//	 * @param contractIds 模块ids
//	 * @return
//	 */
//	public List<AuthorityApply> findPage(JqGrid jqGrid, AuthorityApply authorityApply) {
//	        //查询
//		    if (authorityApply.getApplyDept()!=null && authorityApply.getApplyDept().length() > 0) {
//		    	jqGrid.addHqlFilter( " ( "
//		        	+ "applyDept like '%" + authorityApply.getApplyDept() + "%'"
//					 + " or " + "createdBy like '%" + authorityApply.getCreatedBy() + "%'"
//					 + " or " + "signNo like '%" + authorityApply.getSignNo() + "%'"
//		        	+ ")");  
//		    }    
//		return this.retrievePage(jqGrid);
//	}
	
	
//	/**
//	 * 分页查询
//	 * 
//	 * @param jqGrid
//	 * @param contractIds
//	 *            模块ids
//	 * @return
//	 */
//	public List<AuthorityApply> findPage(JqGrid jqGrid, AuthorityApply authorityApply) {
//		if (authorityApply != null) {
//
//			// //申请单位
//			String applyDept = authorityApply.getApplyDept();
//			if (StringUtils.isNotBlank(applyDept)) {
//
//				jqGrid.addHqlFilter("applyDept like ?  ", "%" + applyDept
//						+ "%");
//
//			}
//			// //编号
//			String signNo = authorityApply.getSignNo();
//			if (StringUtils.isNotBlank(signNo)) {
//
//				jqGrid.addHqlFilter("signNo like ?  ", "%" + signNo
//						+ "%");
//
//			}
//
//			// //申请人
//			String createdBy= authorityApply.getCreatedBy();
//			if (StringUtils.isNotBlank(createdBy)) {
//
//				jqGrid.addHqlFilter("createdBy like ?  ", "%" + createdBy
//						+ "%");
//
//			}
//		}
//		return this.retrievePage(jqGrid);
//	}





	/**
	 * 分页查询
	 */
	public List<AuthorityApply> findPage(UserProfile user,JqGrid jqGrid, AuthorityApply authorityApply) {
		if (!user.getPersonId().equals("1")){//如果不是系统管理员
			String deptName = CommonUtil.trim(user.getDept().getName());
			String deptId = CommonUtil.trim(user.getDeptId());
			if (deptName.equals("北京兴侨国际工程技术有限公司") || deptId.equals("1000000099")){
				System.out.println("北京兴侨国际工程技术有限公司人员不允许查看授权书，返回null！");
				return null;
			}//2018年4月26日
		}
		if (authorityApply != null) {
			String applyDept = authorityApply.getApplyDept();
			if (StringUtils.isNotBlank(applyDept)) {
				jqGrid
						.addHqlFilter(
								" applyDept like ? or signNo like ? or createdBy like ? ",
								"%" + applyDept + "%", "%" + applyDept + "%", "%"
										+ applyDept + "%");
			}
			// 状态过滤
			if (authorityApply.getApplyStatus() != null) {
				//System.out.println("11111："+authorityApply.getApplyStatus());
				jqGrid.addHqlFilter(" applyStatus= ? ",authorityApply.getApplyStatus());
			}
			jqGrid.addSortPropertyDesc("createdDate");
		}
		
		//权限过滤（判断获取数据范围的权限）
		String authHql="";
		authHql = EIPService.getAuthorityService().getHqlFilter(user,AuthorityApplyConstant.MODULE_ID,AuthConstant.AUTH_OPER_NAME_AUTHORITYAPPLYOFLIST );
		//System.out.println("authHql==="+authHql);
		jqGrid.addHqlFilter(authHql);
		return this.retrievePage(jqGrid);
	}
	public List<AuthorityApply> getAuthorityApplyList() {
		StringBuffer hql = new StringBuffer("from "+AuthorityApply.class.getName()+" where 1=1 ");
		List<AuthorityApply> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
	
	/**
	 * 判断编号是否重复
	 * @param signNo
	 * @return
	 */
	public boolean isRepeatBySignNo(String applyId, String signNo){
		String hql = "from " + AuthorityApply.class.getName() + " where applyId <> ? and signNo like ?";
		List < AuthorityApply > list = this.find(hql, applyId, "%" + CommonUtil.trim(signNo) + "%");
		if (list.size() > 0){
			return true;
		}
		return false;
	}
}
