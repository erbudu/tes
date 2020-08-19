package com.supporter.prj.ppm.prj_org.dev_org.service;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.ecc.group_review.util.AuthUtils;
import com.supporter.prj.ppm.prj_org.base_info.constant.BaseInfoConstant;
import com.supporter.prj.ppm.prj_org.base_info.dao.PrjDao;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.entity.PrjPfi;
import com.supporter.prj.ppm.prj_org.base_info.entity.PrjSof;
import com.supporter.prj.ppm.prj_org.dev_org.dao.PrjCoreEquipmentDao;
import com.supporter.prj.ppm.prj_org.dev_org.dao.PrjDeOrgAgentDao;
import com.supporter.prj.ppm.prj_org.dev_org.dao.PrjDeOrgComboDao;
import com.supporter.prj.ppm.prj_org.dev_org.dao.PrjDeOrgCoopDao;
import com.supporter.prj.ppm.prj_org.dev_org.dao.PrjDeOrgDeptDao;
import com.supporter.prj.ppm.prj_org.dev_org.dao.PrjDeOrgEmpDao;
import com.supporter.prj.ppm.prj_org.dev_org.entity.PrjCoreEquipmentEntity;
import com.supporter.prj.ppm.prj_org.dev_org.entity.PrjDeOrgAgent;
import com.supporter.prj.ppm.prj_org.dev_org.entity.PrjDeOrgCombo;
import com.supporter.prj.ppm.prj_org.dev_org.entity.PrjDeOrgCoop;
import com.supporter.prj.ppm.prj_org.dev_org.entity.PrjDeOrgDept;
import com.supporter.prj.ppm.prj_org.dev_org.entity.PrjDeOrgEmp;
import com.supporter.prj.ppm.service.PPMService;

import net.sf.json.JSONArray;


@Service
public class DevOrgService {
	@Autowired
	private PrjDao prjDao;
	@Autowired
	private PrjDeOrgDeptDao prjDeOrgDeptDao;
	@Autowired
	private PrjDeOrgEmpDao prjDeOrgEmpDao;
	@Autowired
	private PrjDeOrgCoopDao prjDeOrgCoopDao;
	@Autowired
	private PrjDeOrgAgentDao prjDeOrgAgentDao;
	@Autowired
	private PrjDeOrgComboDao prjDeOrgComboDao;
	
	@Autowired
	private PrjCoreEquipmentDao equipmentDao;
	
	public PrjDeOrgDept initEditPayPage(String payId, String contractId,
			UserProfile user) {
		if (StringUtils.isBlank(payId)) {// 新建
			PrjDeOrgDept rec = new PrjDeOrgDept();
			return rec;
		} else {// 编辑
			PrjDeOrgDept rec = prjDeOrgDeptDao.get(payId);
			return rec;
		}
	}



	public List<PrjDeOrgDept> getDevOrgDeptGrid(UserProfile user, JqGrid jqGrid, String prjId) {
		return prjDeOrgDeptDao.findPage(user, jqGrid, prjId);
	}



	public List<PrjDeOrgEmp> getDevOrgEmpGrid(UserProfile user, JqGrid jqGrid, String prjId) {
		return prjDeOrgEmpDao.findPage(user, jqGrid, prjId);
		
	}
	
	public List<PrjDeOrgCoop> getDevOrgCoopGrid(UserProfile user, JqGrid jqGrid, String prjId) {
		return prjDeOrgCoopDao.findPage(user, jqGrid, prjId);
		
	}
	
	public List<PrjDeOrgAgent> getDevOrgAgentGrid(UserProfile user, JqGrid jqGrid, String prjId) {
		return prjDeOrgAgentDao.findPage(user, jqGrid, prjId);
		
	}


	/**
	 * <pre>获取核心供应商列表数据</pre>
	 * @param user 当前登录用户
	 * @param jqGrid 列表
	 * @param prjId   项目主键
	 */
	public List<PrjCoreEquipmentEntity> getEquipmentGrid(UserProfile user, JqGrid jqGrid, String prjId) {
		return this.equipmentDao.findPage(user,jqGrid,prjId);
	}
	
	/**
	 * <pre>获取联合体成员列表数据</pre>
	 * @param user 当前登录用户
	 * @param jqGrid 列表
	 * @param prjId   项目主键
	 */
	public List<PrjDeOrgCombo> getComboGrid(UserProfile user, JqGrid jqGrid, String prjId) {
		return this.prjDeOrgComboDao.findPage(user,jqGrid,prjId);
	}

	@Transactional(transactionManager = TransManager.APP)
	public void saveOrUpdate(UserProfile user, Prj prj) {
		String prjId = prj.getPrjId();
		
		Prj prjEntity = prjDao.get(prjId);
		//保存项目信息的字段
		if(prjEntity != null) {
			prjEntity.setBusinessLeader(prj.getBusinessLeader());//商务负责人
			prjEntity.setBusinessLeaderId(prj.getBusinessLeaderId());//商务负责人ID
			prjEntity.setTechnologyLeader(prj.getTechnologyLeader());//技术负责人
			prjEntity.setTechnologyLeaderId(prj.getTechnologyLeaderId());//技术负责人ID
			prjEntity.setCoopDept(prj.getCoopDept());//合作开发部门
			prjEntity.setCoopDeptId(prj.getCoopDeptId());//合作开发部门ID
			
			//编辑权限
			AuthUtils.canExecute(user,BaseInfoConstant.MODULE_ID, BaseInfoConstant.MODULE_AUTH_EDIT,prj.getPrjId(),prjEntity);
			prjDao.update(prjEntity);
		}
		//联合体成员
		//PrjDeOrgCombo  prjDeOrgCombo = prj.getDeOrgComboData();
		//this.saveOrUpdateCombo(prjDeOrgCombo,prjId);
		//工作组织部门
		//List<PrjDeOrgDept> prjDeOrgDepts = prj.getDeOrgDeptListData();
		//this.saveDept(prjDeOrgDepts);
		//工作组织人员
		//List<PrjDeOrgEmp> prjDeOrgEmps = prj.getDeOrgEmpListData();
		//this.saveOrgEmp(prjDeOrgEmps);
		
		//保存联合体成员
		List<PrjDeOrgCombo> deOrgCombos = prj.getDeOrgComboDataList();
		this.saveCombo(deOrgCombos);
		
		//保存工作组织合作方
		List<PrjDeOrgCoop> prjDeOrgCoops = prj.getDeOrgCoopListData();
		this.saveOrgCoop(prjDeOrgCoops);
		//保存工作组织代理商
		List<PrjDeOrgAgent> prjDeOrgAgents = prj.getDeOrgAgentListData();
		this.saveOrgAgent(prjDeOrgAgents);
		//保存核心供应商
		List<PrjCoreEquipmentEntity> equipments = prj.getEquipmentListData();
		this.saveEquipment(equipments);
		
	}
	
	/**
	 * <pre>保存联合体成员</pre>
	 * @param deOrgCombos
	 */
	private void saveCombo(List<PrjDeOrgCombo> deOrgCombos) {

		if(deOrgCombos != null && deOrgCombos.size() > 0) {
			for(PrjDeOrgCombo combo : deOrgCombos) {
				String comBoId = combo.getComboId();
				PrjDeOrgCombo comboEntity = prjDeOrgComboDao.get(comBoId);	
				prjDeOrgComboDao.clear();//清楚缓存
				if(comboEntity != null ) {
					prjDeOrgComboDao.update(combo);
				}
				else {
					prjDeOrgComboDao.save(combo);
				}
			}
		}
		
	}



	/**
	 * <pre>核心设备供应商保存更新</pre>
	 * @param equipments
	 */
	private void saveEquipment(List<PrjCoreEquipmentEntity> equipments) {
		if(equipments != null && equipments.size() > 0) {
			for(PrjCoreEquipmentEntity equipment : equipments) {
				String equipmentId = equipment.getEquipmentId();
				PrjCoreEquipmentEntity entity = this.equipmentDao.get(equipmentId);
				equipmentDao.clear();//清楚缓存
				if(entity != null ) {
					equipmentDao.update(equipment);
				}
				else {
					equipmentDao.save(equipment);
				}
			}
		}
	}



	//联合体成员
	@Transactional(transactionManager = TransManager.APP)
	public void saveOrUpdateCombo(PrjDeOrgCombo prjDeOrgCombo,String prjId) {
		if(prjDeOrgCombo != null ) {
			PrjDeOrgCombo entity = prjDeOrgComboDao.getByPrjId(prjId);
			prjDeOrgComboDao.clear();
			if(entity != null ) {
				entity.setComboCName(prjDeOrgCombo.getComboCName());
				entity.setComboEName(prjDeOrgCombo.getComboEName());
				entity.setProportion(prjDeOrgCombo.getProportion());
				entity.setDivisionOfLabor(prjDeOrgCombo.getDivisionOfLabor());
				entity.setPrjId(prjId);
				prjDeOrgComboDao.update(entity);
			}
			else {
				
				prjDeOrgCombo.setComboId(com.supporter.util.UUIDHex.newId());
				prjDeOrgCombo.setPrjId(prjId);
				prjDeOrgComboDao.save(prjDeOrgCombo);
			}
		}
	}
	
	//工作组织部门
	@Transactional(transactionManager = TransManager.APP)
	public void saveDept(List<PrjDeOrgDept> prjDeOrgDepts ) {
		if(prjDeOrgDepts != null && prjDeOrgDepts.size() > 0 ) {
			for(PrjDeOrgDept prjDeOrgDept : prjDeOrgDepts) {
				String deOrgId = prjDeOrgDept.getDeOrgId();
				PrjDeOrgDept entity = this.prjDeOrgDeptDao.get(deOrgId);
				prjDeOrgDeptDao.clear();//清楚缓存
				if(entity != null ) {
					prjDeOrgDeptDao.update(prjDeOrgDept);
				}
				else {
					prjDeOrgDeptDao.save(prjDeOrgDept);;
				}
			}
		}
	}
	
	//工作组织人员
	@Transactional(transactionManager = TransManager.APP)
	public void saveOrgEmp(List<PrjDeOrgEmp> prjDeOrgEmps ) {
		if(prjDeOrgEmps != null && prjDeOrgEmps.size() > 0 ) {
			for(PrjDeOrgEmp prjDePrjDeOrgEmp : prjDeOrgEmps) {
				String dePrgEmpId = prjDePrjDeOrgEmp.getDeOrgEmpId();
				PrjDeOrgEmp entity = prjDeOrgEmpDao.get(dePrgEmpId);
				prjDeOrgEmpDao.clear();
				if(entity != null ) {
					prjDeOrgEmpDao.update(prjDePrjDeOrgEmp);
				}
				else {
					prjDeOrgEmpDao.save(prjDePrjDeOrgEmp);;
				}
			}
		}
	}
	
	//工作组织合作方
	@Transactional(transactionManager = TransManager.APP)
	public void saveOrgCoop(List<PrjDeOrgCoop> prjDeOrgCoops ) {
		if(prjDeOrgCoops != null && prjDeOrgCoops.size() > 0 ) {
			for(PrjDeOrgCoop prjDeOrgCoop : prjDeOrgCoops) {
				String coopId = prjDeOrgCoop.getCoopId();
				PrjDeOrgCoop entity = prjDeOrgCoopDao.get(coopId);
				prjDeOrgCoopDao.clear();
				if(entity != null ) {
					prjDeOrgCoopDao.update(prjDeOrgCoop);
				}
				else {
					prjDeOrgCoopDao.save(prjDeOrgCoop);;
				}
			}
		}
	}
	
	//工作组织代理商
	@Transactional(transactionManager = TransManager.APP)
	public void saveOrgAgent(List<PrjDeOrgAgent> prjDeOrgAgents ) {
		if(prjDeOrgAgents != null && prjDeOrgAgents.size() > 0 ) {
			for(PrjDeOrgAgent prjDeOrgAgent : prjDeOrgAgents) {
				String agentId = prjDeOrgAgent.getAgentId();
				PrjDeOrgAgent entity = prjDeOrgAgentDao.get(agentId);
				prjDeOrgAgentDao.clear();
				if(entity != null ) {
					prjDeOrgAgentDao.update(prjDeOrgAgent);
				}
				else {
					prjDeOrgAgentDao.save(prjDeOrgAgent);;
				}
			}
		}
	}



	/**
	 * <pre>删除组织部门</pre>
	 * @param id 主键
	 * @return String
	 */
	public String deleteOrgDept(String id) {
		if(id == null || id == "") {return null;}
		prjDeOrgDeptDao.delete(id);
		return "true";
	}


	/**
	<pre>删除组织人员</pre>
	 */
	public String deleteOrgEmp(String id) {
		if(id == null || id == "") {return null;}
		prjDeOrgEmpDao.delete(id);
		return "true";
	}
	
	/**删除组织合作方*/
	public String deleteOrgCoop(String id) {
		if(id == null || id == "" || prjDeOrgCoopDao.get(id) == null) {return null;}
		prjDeOrgCoopDao.delete(id);
		return "true";
	}

	/**删除代理商*/
	public String deleteOrgAgent(String id) {
		if(id == null || id == "" || prjDeOrgAgentDao.get(id) == null) {return null;}
		prjDeOrgAgentDao.delete(id);
		return "true";
	}

	/**删除核心供应商*/
	public String deleteOrgequipment(String id) {
		if(id == null || id == "" || equipmentDao.get(id) == null) {return null;}
		equipmentDao.delete(id);
		return "true";
	}



	public String deleteOrgCombo(String id) {
		if(id == null || id == "" || prjDeOrgComboDao.get(id) == null) {return null;}
		prjDeOrgComboDao.delete(id);
		return "true";
	}
}
