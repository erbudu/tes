package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_adjust.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_adjust.dao.PrePrjInfoAdjustFundDao;
import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_adjust.entity.PrePrjInfoAdjustFund;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**
 * @Title: Service
 * @Description: 功能模块表
 * @author tiansen
 * 
 */
@Service
public class PrePrjInfoAdjustFundService {
	@Autowired
	private PrePrjInfoAdjustFundDao fundDao;
	
	/**
	 * 分页查询
	 * @param user
	 * @param jqGrid
	 * @param filing
	 * @return
	 */
	public List<PrePrjInfoAdjustFund> getGrid(UserProfile user, JqGrid jqGrid, PrePrjInfoAdjustFund filing){
		List<PrePrjInfoAdjustFund> list=  fundDao.findPage(user, jqGrid, filing);
		if(list==null||list.size()==0) {
			list = new ArrayList<PrePrjInfoAdjustFund> ();
			PrePrjInfoAdjustFund fund = newFiling(user);
			fund.setFundId("NEW_FUND_"+new Date().getTime());
			list.add(fund);
			jqGrid.setRows(list);
			jqGrid.setRowCount(1);
		}
		return list;
	}
	
	/**
	 * 根据id获取对象
	 * @param fundId
	 * @return
	 */
	public PrePrjInfoAdjustFund get(String fundId){
		return fundDao.get(fundId);
	}
	
	/**
	 * 初始化备案信息
	 * @param fundId
	 * @param user
	 * @return
	 */
	public PrePrjInfoAdjustFund initEditOrViewPage(String fundId,UserProfile user){
		PrePrjInfoAdjustFund filing =null;
		if(StringUtils.isBlank(fundId)){
			filing = newFiling(user);
		}else{
			filing =get(fundId);
		}
		return filing;
	}
	/**
	 * 新建初始化备案信息
	 * @param user
	 * @return
	 */
	public PrePrjInfoAdjustFund newFiling(UserProfile user){
		PrePrjInfoAdjustFund filing = new PrePrjInfoAdjustFund();
		return filing;
	}
	
	/**
	 * 保存或更细实体类
	 * @param filing
	 * @param user
	 * @return
	 */
	public PrePrjInfoAdjustFund saveOrUpdate(PrePrjInfoAdjustFund fund,UserProfile user){
		if(StringUtils.isBlank(fund.getFundId())){
			fundDao.save(fund);
		}else{
			fundDao.update(fund);
		}
		return fund;
	}
	
	/**
	 * 删除
	 * @param fundIds
	 * @param user
	 */
	public void delete (String fundIds,UserProfile user){
		if(StringUtils.isNotBlank(fundIds)){
			for (String fundId : fundIds.split(",")) {
				if(fundId.length()!=32) {
					return;
				}
				PrePrjInfoAdjustFund fund =get(fundId);
				if(fund!=null) {
					fundDao.delete(fund);
				}
			}
		}
	}

	/**
	 * 新建保存
	 * @param prePrjInfoAdjustFund
	 */
	public void save(PrePrjInfoAdjustFund prePrjInfoAdjustFund) {
		fundDao.save(prePrjInfoAdjustFund);
		
	}

	public void update(PrePrjInfoAdjustFund prePrjInfoAdjustFund) {
		// TODO Auto-generated method stub
		fundDao.update(prePrjInfoAdjustFund);
	}

	/**
	 * 根据主表id删除从表
	 * @param id
	 * @param user
	 */
	public void deleteByAdjust(String id, UserProfile user) {
		List<PrePrjInfoAdjustFund> list = fundDao.findBy("adjustId", id);
		if(list!=null&&list.size()>0) {
			fundDao.delete(list);
		}
		
	}
}
