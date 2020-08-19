package com.supporter.prj.linkworks.oa.abroadPublicity.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.abroad.entity.Abroad;
import com.supporter.prj.linkworks.oa.abroadPublicity.entity.AbroadBack;
import com.supporter.prj.linkworks.oa.abroadPublicity.entity.AbroadPublicity;
import com.supporter.prj.linkworks.oa.abroadPublicity.util.CommonUtils;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Repository
public class AbroadPublicityDao extends MainDaoSupport < AbroadPublicity, String >{

	@Autowired
	private AbroadBackDao abroadBackDao;
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param attr
	 * @return
	 */
	public List<AbroadPublicity> findPage(JqGrid jqGrid,String attr){
		List<AbroadPublicity> retList = new ArrayList<AbroadPublicity>();
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		String today = CommonUtils.dateString();
		sb.append(" from ").append(AbroadPublicity.class.getName()).append(" t1 ,").append(Abroad.class.getName()).append(" t2 ").append(" where t1.abroadId = t2.recordId and  t1.pubEndDate >='"+today+"' ");
		if (StringUtils.isNotBlank(attr)) {
			sb.append(
					" and (t2.applierName like ? or t2.applierDept like ?  or t2.prjName like ? or t2.tgtCountries like ?  ) ");
			CollectionUtils.addAll(params, new String[] {
					"%" + attr + "%",
					"%" + attr + "%",
					"%" + attr + "%",
					"%" + attr + "%"
				});
		}
		//sb.append(" order by pubStartDate desc ");
		this.retrievePage(jqGrid, sb.toString(), params.toArray());
		List<Object[]> list = jqGrid.getRows();
		for (Object[] obj : list) {
			AbroadPublicity abroadPublicity = (AbroadPublicity)obj[0];
			Abroad abroad = (Abroad)obj[1];
			abroadPublicity.setAbroad(abroad);
			abroadPublicity.setPrjName(abroad.getPrjName());
			
			//是否填写出国公示
			AbroadBack back = abroadBackDao.getByPubId(abroadPublicity.getPubId());
			if(back != null && back.getSwfStatus() == 2){
				abroadPublicity.setIsReport("已完成");
			}else if (back != null && back.getSwfStatus() == 1){
				abroadPublicity.setIsReport("审批中");
			}else{
				abroadPublicity.setIsReport("未提交");
			}
			retList.add(abroadPublicity);
		}
		jqGrid.setRows(retList);
		return retList;
	}
	
	
	/**
	 * 通过abroadId获取公示
	 * @param abroadId
	 * @return
	 */
	public AbroadPublicity getByAbroadId(String abroadId){
		String hql = "from " + AbroadPublicity.class.getName() + " where abroadId like ?";
		AbroadPublicity entity = (AbroadPublicity) this.find(hql, "%" + CommonUtil.trim(abroadId)+ "%").get(0);
		return entity;
	}
}
