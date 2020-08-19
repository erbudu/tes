package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_adjust.service;


import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_adjust.dao.PrePrjInfoAdjustDao;
import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_adjust.entity.PrePrjInfoAdjust;
import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_adjust.entity.PrePrjInfoAdjustComp;
import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_adjust.entity.PrePrjInfoAdjustFund;
import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_filing.entity.PrePrjManagerFiling;
import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_filing.service.PrePrjManagerFilingService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**
 * @Title: Service
 * @Description: 功能模块表
 * @author tiansen
 * 
 */
@Service
public class PrePrjInfoAdjustService {
	@Autowired
	private PrePrjInfoAdjustDao adjuestDao;
	@Autowired
	private PrePrjManagerFilingService filingService;
	@Autowired
	private PrePrjInfoAdjustCompService compService;
	@Autowired
	private PrePrjInfoAdjustFundService fundService;
	
	/**
	 * 分页查询
	 * @param user
	 * @param jqGrid
	 * @param adjuest
	 * @return
	 */
	public List<PrePrjInfoAdjust> getGrid(UserProfile user, JqGrid jqGrid, PrePrjInfoAdjust adjuest){
		return adjuestDao.findPage(user, jqGrid, adjuest);
	}
	
	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	public PrePrjInfoAdjust get(String id){
		return adjuestDao.get(id);
	}
	
	/**
	 * 初始化备案信息
	 * @param id
	 * @param user
	 * @return
	 */
	public PrePrjInfoAdjust initEditOrViewPage(String id,String filingId,UserProfile user){
		PrePrjInfoAdjust adjuest =null;
		if(StringUtils.isBlank(id)){
			adjuest = newFiling(filingId,user);
		}else{
			adjuest =get(id);
		}
		adjuest.setOldAmount(adjuest.getEstimatedInvestment());
		return adjuest;
	}
	/**
	 * 新建初始化备案信息
	 * @param user
	 * @return
	 */
	public PrePrjInfoAdjust newFiling(String filingId,UserProfile user){
		PrePrjInfoAdjust adjuest = new PrePrjInfoAdjust();
		PrePrjManagerFiling filing = filingService.get(filingId);
		//将filing的值赋值给更新的记录
		adjuest.setFilingId(filing.getFilingId());
		adjuest.setPrjNameZh(filing.getPrjNameZh());
		adjuest.setPrjNameEn(filing.getPrjNameEn());
		adjuest.setAdd(true);
		adjuest.setId(com.supporter.util.UUIDHex.newId());
		adjuest.setCreatedBy(user.getName());
		adjuest.setOwnerNameEn(filing.getOwnerNameEn());
		adjuest.setOwnerNameZh(filing.getOwnerNameZh());
		adjuest.setPrjNameZh(filing.getPrjNameZh());
		adjuest.setPrjNameEn(filing.getPrjNameEn());
		adjuest.setPrjCompanyNameEn(filing.getPrjCompanyNameEn());
		adjuest.setPrjCompanyNameZh(filing.getPrjCompanyNameZh());
		adjuest.setMarket(filing.getMarket());
		adjuest.setPrjLevel(filing.getPrjLevel());
		adjuest.setArea(filing.getArea());
		adjuest.setProvince(filing.getProvince());
		adjuest.setEngineeringLocalZh(filing.getEngineeringLocalZh());
		adjuest.setEngineeringLocalEn(filing.getEngineeringLocalEn());
		adjuest.setPrjNature(filing.getPrjNature());
		adjuest.setEngineeringScale(filing.getEngineeringScale());
		adjuest.setPrjAreaId(filing.getPrjAreaId());
		adjuest.setPrjArea(filing.getPrjArea());
		adjuest.setEstimatedInvestment(filing.getEstimatedInvestment());
		adjuest.setPrjOverview(filing.getPrjOverview());
		adjuest.setForeignAgents(filing.getForeignAgents());
		
		Dept dept = user.getDept();
		if(dept!=null){
			adjuest.setDeptId(dept.getDeptId());
			adjuest.setDeptName(dept.getName());
			adjuest.setModifiedDeptId(dept.getDeptId());
			adjuest.setModifiedDeptName(dept.getName());
		}
		adjuest.setAdd(true);
		Date date = new Date();
		adjuest.setCreatedBy(user.getName());
		adjuest.setCreatedById(user.getPersonId());
		adjuest.setCreatedDate(date);
		adjuest.setModifiedBy(user.getName());
		adjuest.setModifiedById(user.getPersonId());
		adjuest.setModifiedDate(date);
		adjuest.setStatus(0);
		return adjuest;
	}
	
	/**
	 * 保存或更细实体类
	 * @param adjuest
	 * @param user
	 * @return
	 */
	public PrePrjInfoAdjust saveOrUpdate(PrePrjInfoAdjust adjuest,UserProfile user){
		String content = "项目预计总投资（"+adjuest.getOldAmount()+"万美元:"+adjuest.getEstimatedInvestment()+"万美元）";
		adjuest.setModifiedContent(content);
		if(adjuest.getAdd()){
			adjuestDao.save(adjuest);
		}else{
			adjuestDao.update(adjuest);
		}
		//保存或更新从表
		List<PrePrjInfoAdjustFund> funds = adjuest.getFunds();
		List<PrePrjInfoAdjustComp> comps = adjuest.getComps();
		if(funds!=null&&funds.size()>0) {
			for (PrePrjInfoAdjustFund prePrjInfoAdjustFund : funds) {
				String fundId = prePrjInfoAdjustFund.getFundId();
				prePrjInfoAdjustFund.setAdjustId(adjuest.getId());
				if(StringUtils.isBlank(fundId)||fundId.length()!=32) {
					fundService.save(prePrjInfoAdjustFund);
				}else {
					fundService.update(prePrjInfoAdjustFund);
				}
			}
			String delIds = adjuest.getDelFunds();
			if(StringUtils.isNotBlank(delIds)) {
				fundService.delete(delIds, user);
			}
		}
		if(comps!=null&&comps.size()>0) {
			for (PrePrjInfoAdjustComp prePrjInfoAdjustComp : comps) {
				String compId = prePrjInfoAdjustComp.getCompetitorId();
				prePrjInfoAdjustComp.setAdjustId(adjuest.getId());
				if(StringUtils.isBlank(compId)||compId.length()!=32) {
					compService.save(prePrjInfoAdjustComp);
				}else {
					compService.update(prePrjInfoAdjustComp);
				}
			}
			String delIds= adjuest.getDelComps();
			if(StringUtils.isNotBlank(delIds)) {
				compService.delete(delIds, user);
			}
		}
		return adjuest;
	}
	
	/**
	 * 删除
	 * @param ids
	 * @param user
	 */
	public void delete (String ids,UserProfile user){
		if(StringUtils.isNotBlank(ids)){
			for (String id : ids.split(",")) {
				//删除主表
				adjuestDao.delete(id);
				//删除从表
				compService.deleteByAdjust(id, user);
				fundService.deleteByAdjust(id, user);
			}
		}
	}
}
