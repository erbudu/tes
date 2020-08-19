package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_adjust.service;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_adjust.dao.PrePrjInfoAdjustCompDao;
import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_adjust.entity.PrePrjInfoAdjustComp;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**
 * @Title: Service
 * @Description: 功能模块表
 * @author tiansen
 * 
 */
@Service
public class PrePrjInfoAdjustCompService {
	@Autowired
	private PrePrjInfoAdjustCompDao compDao;
	
	/**
	 * 分页查询
	 * @param user
	 * @param jqGrid
	 * @param filing
	 * @return
	 */
	public List<PrePrjInfoAdjustComp> getGrid(UserProfile user, JqGrid jqGrid, PrePrjInfoAdjustComp filing){
		return compDao.findPage(user, jqGrid, filing);
	}
	
	/**
	 * 根据id获取对象
	 * @param competitorId
	 * @return
	 */
	public PrePrjInfoAdjustComp get(String competitorId){
		return compDao.get(competitorId);
	}
	
	/**
	 * 初始化备案信息
	 * @param competitorId
	 * @param user
	 * @return
	 */
	public PrePrjInfoAdjustComp initEditOrViewPage(String competitorId,UserProfile user){
		PrePrjInfoAdjustComp filing =null;
		if(StringUtils.isBlank(competitorId)){
			filing = newFiling(user);
		}else{
			filing =get(competitorId);
		}
		return filing;
	}
	/**
	 * 新建初始化备案信息
	 * @param user
	 * @return
	 */
	public PrePrjInfoAdjustComp newFiling(UserProfile user){
		PrePrjInfoAdjustComp filing = new PrePrjInfoAdjustComp();
		return filing;
	}
	
	/**
	 * 保存或更细实体类
	 * @param filing
	 * @param user
	 * @return
	 */
	public PrePrjInfoAdjustComp saveOrUpdate(PrePrjInfoAdjustComp fund,UserProfile user){
		if(StringUtils.isBlank(fund.getCompetitorId())){
			compDao.save(fund);
		}else{
			compDao.update(fund);
		}
		return fund;
	}
	
	/**
	 * 删除
	 * @param competitorIds
	 * @param user
	 */
	public void delete (String competitorIds,UserProfile user){
		if(StringUtils.isNotBlank(competitorIds)){
			for (String competitorId : competitorIds.split(",")) {
				if(competitorId.length()!=32) {
					return;
				}
				PrePrjInfoAdjustComp comp = get(competitorId);
				if(comp!=null) {
					compDao.delete(comp);
				}
			}
		}
	}

	/**
	 * 保存
	 * @param prePrjInfoAdjustComp
	 */
	public void save(PrePrjInfoAdjustComp prePrjInfoAdjustComp) {
		// TODO Auto-generated method stub
		compDao.save(prePrjInfoAdjustComp);
	}

	public void update(PrePrjInfoAdjustComp prePrjInfoAdjustComp) {
		// TODO Auto-generated method stub
		compDao.update(prePrjInfoAdjustComp);
	}

	/**根据主表id删除从表
	 * @param id
	 * @param user
	 */
	public void deleteByAdjust(String id, UserProfile user) {
		List<PrePrjInfoAdjustComp> list = compDao.findBy("adjustId", id);
		if(list!=null&&list.size()>0) {
			compDao.delete(list);
		}
		
	}
}
