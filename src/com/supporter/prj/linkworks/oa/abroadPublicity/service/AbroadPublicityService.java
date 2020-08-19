package com.supporter.prj.linkworks.oa.abroadPublicity.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.SimpleDateFormat;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.abroad.dao.AbroadDAO;
import com.supporter.prj.linkworks.oa.abroad.entity.Abroad;
import com.supporter.prj.linkworks.oa.abroadPublicity.dao.AbroadPublicityDao;
import com.supporter.prj.linkworks.oa.abroadPublicity.entity.AbroadPublicity;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Service
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Service
public class AbroadPublicityService {

	@Autowired
	private AbroadPublicityDao abroadPublicityDao;
	
	@Autowired
	private AbroadDAO abroadDAO;

	
	
	/**
	 * 分页表格展示数据.
	 * @param user
	 * @param jqGrid
	 * @param abroad
	 * @return
	 */
	public List<AbroadPublicity> getGrid(UserProfile user, JqGrid jqGrid, String attr) {
		return  abroadPublicityDao.findPage(jqGrid, attr);
	}
	
	
//	/**
//	 * 获取添加公示时的审批下拉数据
//	 * @return
//	 */
//	public String getSelectList(){
//		String hql = "from " + Abroad.class.getName() + " where recordStatus = 2 ";
//		Map<String, String> map = new HashMap<String, String>();
//		List<Abroad> list = abroadDAO.find(hql, map);
//		StringBuffer sb = new StringBuffer();
//		sb.append("{");
//		for(Abroad abroad :list){
//			boolean check = isPublicity(abroad.getRecordId());
//			if(check){
//				sb.append("\"" + abroad.getRecordId() + "\":");
//				sb.append("\"" + abroad.getPrjName()+ "\",");					
//			}
//		}
//		if(sb.length() > 1){
//			sb.deleteCharAt(sb.length() - 1);			
//		}
//		sb.append("}");
//		return sb.toString();
//	}


	/**
	 * 保存出国公示
	 * @param user
	 * @param abroadPublicity
	 * @param valueMap
	 * @return
	 */
	public AbroadPublicity save(UserProfile user, AbroadPublicity abroadPublicity,Map<String, Object> valueMap) {
		abroadPublicity.setPubId(com.supporter.util.UUIDHex.newId());
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		//临时将公示天数设置为五天，加入流程后修改
//		int pubDay = abroadPublicity.getPubDay();
		int pubDay = 5;
		Date d=new Date();   
		abroadPublicity.setPubStartDate(df.format(d));
		abroadPublicity.setPubEndDate(df.format(new Date(d.getTime() + pubDay * 24 * 60 * 60 * 1000)));
		this.abroadPublicityDao.save(abroadPublicity);
		return abroadPublicity;
	}
	
	
	/**
	 * 查询是否已经公示.
	 * 
	 * @param budgetYear
	 * @param keyword
	 * @return List < Contract >
	 */
	public boolean isPublicity(String abroadId) {
		String hql = "from " + AbroadPublicity.class.getName()
				+ " where abroadId like ?";
		List<AbroadPublicity> entities = this.abroadPublicityDao.find(hql, "%" + CommonUtil.trim(abroadId)+ "%");
		if(CollectionUtils.isNotEmpty(entities)){
			return false;
		}
		return true;
	}
	
	/**
	 * 查询是否已经公示.
	 * 
	 * @param budgetYear
	 * @param keyword
	 * @return List < Contract >
	 */
	public AbroadPublicity getByAbroadId(String babroadIdroadId) {
		String hql = "from " + AbroadPublicity.class.getName()
				+ " where abroadId like ?";
		List<AbroadPublicity> entities = this.abroadPublicityDao.find(hql, "%" + CommonUtil.trim(babroadIdroadId)+ "%");
		if(entities != null && entities.size()>0){
			return entities.get(0);
		}
		return null;
	}
	
	/**
	 * 删除
	 * @param abroadPublicity
	 */
	public void delete(AbroadPublicity abroadPublicity){
		if(abroadPublicity != null){
			abroadPublicityDao.delete(abroadPublicity);
		}
	}
	
	/**
	 * 通过出国公示id获取出国审批对象
	 * @param pubId
	 * @return
	 */
	public Abroad getBypubId(String pubId){
		AbroadPublicity publicity = this.abroadPublicityDao.get(pubId);
		if(publicity != null){
			String recordId = publicity.getAbroadId();
			Abroad abroad = this.abroadDAO.get(recordId);
			return abroad;
		}else{
			return null;
		}
		
	}
}
