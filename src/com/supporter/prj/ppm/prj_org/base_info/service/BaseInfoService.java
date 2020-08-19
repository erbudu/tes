package com.supporter.prj.ppm.prj_org.base_info.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.role.entity.Role;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.prj.ppm.ecc.group_review.util.AuthUtils;
import com.supporter.prj.ppm.ecc.project_ecc.service.EccService;
import com.supporter.prj.ppm.prj_org.base_info.common.PrjInfo;
import com.supporter.prj.ppm.prj_org.base_info.constant.BaseInfoConstant;
import com.supporter.prj.ppm.prj_org.base_info.dao.PrjAddrDao;
import com.supporter.prj.ppm.prj_org.base_info.dao.PrjBidInfDao;
import com.supporter.prj.ppm.prj_org.base_info.dao.PrjDao;
import com.supporter.prj.ppm.prj_org.base_info.dao.PrjPfiDao;
import com.supporter.prj.ppm.prj_org.base_info.dao.PrjSofDao;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj.StatusCodeTable;
import com.supporter.prj.ppm.prj_org.base_info.entity.PrjAddr;
import com.supporter.prj.ppm.prj_org.base_info.entity.PrjBidInf;
import com.supporter.prj.ppm.prj_org.base_info.entity.PrjPfi;
import com.supporter.prj.ppm.prj_org.base_info.entity.PrjSof;
import com.supporter.prj.ppm.prj_org.dev_org.dao.PrjCoreEquipmentDao;
import com.supporter.prj.ppm.prj_org.dev_org.dao.PrjDeOrgComboDao;
import com.supporter.prj.ppm.prj_org.dev_org.dao.PrjDeOrgEmpDao;
import com.supporter.prj.ppm.prj_org.dev_org.entity.PrjCoreEquipmentEntity;
import com.supporter.prj.ppm.prj_org.dev_org.entity.PrjDeOrgEmp;
import com.supporter.prj.ppm.prj_org.member_duty.service.MemberDutyService;
import com.supporter.prj.ppm.prj_org.util.SimilarityUtils;
import com.supporter.prj.ppm.service.PPMService;
import com.supporter.util.UUIDHex;

import edu.emory.mathcs.backport.java.util.Collections;
import net.sf.json.JSONObject;

@Service
public class BaseInfoService implements PrjInfo {// svn 已更
	
	@Autowired
	private PrjDao prjDao;
	@Autowired
	private PrjAddrDao prjAddrDao;
	@Autowired
	private PrjDeOrgEmpDao prjDeOrgEmpDao;
	@Autowired
	private PrjSofDao prjSofDao;
	@Autowired
	private PrjPfiDao prjPfiDao;
	@Autowired
	private PrjDeOrgComboDao prjDeOrgComboDao;
	@Autowired
	private PrjBidInfDao prjBidInfDao;

	@Autowired
	private PrjCoreEquipmentDao equipmentDao;

	@Autowired
	private PrjAddrService prjAddrService;

	@Autowired
	private EccService eccService;// 出口管制

	@Autowired
	private MemberDutyService  memberService;
	
	/**
	 * <pre>
	 * 		一级菜单权限过滤方法
	 * 		注：当前登录人项目列表可见项目：1.项目创建人 2.项目商务负责人3.项目技术负责人4.项目具体业务负责人
	 * </pre>
	 * @param userProfile  当前登录用户
	 * @param prjId  项目主键
	 * @return  map
	 */
	public Map<String, String> oneLevelMenuAuth(UserProfile userProfile, String prjId) {

		if (prjId == "" || prjId == null) {
			return null;
		}

		Prj prj = prjDao.get(prjId);
		// ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String, String>();

		/* 如果当前登录人是创建人 或 项目技术负责人 或 项目商务负责人 或 管理员 则不过滤一级菜单权限 */
		if (userProfile.getPersonId().equals("1") || userProfile.getPersonId().equals(prj.getCreatedById())
				|| userProfile.getPersonId().equals(prj.getBusinessLeader())
				|| userProfile.getPersonId().equals(prj.getTechnologyLeaderId())) {
			map.put("showInf", null);
			return map;
		}

		/* 若当前登录人不是创建人、项目技术负责人、项目商务负责人 查找当前登录人负责的业务 */
		String responsibles = memberService.getResponsibleByParams(prjId, userProfile.getPersonId());
		/*如果是经营管理部投议标备案及许可岗位人员-添加投议标备案及许可一级菜单权限*/
		Role role = EIPService.getRoleService().getRole(BaseInfoConstant.ROLE_BIDFILING);
		List<Person> list = EIPService.getRoleService().getPersonsForDept(role, null);
		for (Person person : list) {
			if (person.getPersonId().equals(userProfile.getPersonId())) {
				responsibles = responsibles == null ? "PPM_PRJ_BUSINESS_TYBBAJXK" : responsibles + ",PPM_PRJ_BUSINESS_TYBBAJXK";
			}
		}
		map.put("showInf", responsibles);
		return map;
	}
	
	
	/**
	 * <pre>
	 * 获取无需出口管制 通知人员信息
	 * </pre>
	 * 
	 * @return
	 */
	public List<Map<String, String>> getNoticePersonY() {

		String department = BaseInfoConstant.MANAGEMENT_DEPARTMENT;// 经营管理部:部门编号
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		Role role1 = EIPService.getRoleService().getRole(BaseInfoConstant.ROLE_RECORDER);// 角色：经营管理部-项目信息记录员
		Role role2 = EIPService.getRoleService().getRole(BaseInfoConstant.ROLE_EXPORTCONTROL);// 角色：出口管制专员
		Role role3 = EIPService.getRoleService().getRole(BaseInfoConstant.ROLE_ExpatriateManagers);// 角色：经营管理部-经营管理部外派事业部经理

		/* 角色 */
		Map<String, String> mapRole = new HashMap<String, String>();
		mapRole.put("role1", role1 == null ? " " : role1.getName());
		mapRole.put("role2", role2 == null ? " " : role2.getName());
		mapRole.put("role3", role3 == null ? " " : role3.getName());

		/* 角色下人员 */
		String role1Person = "";
		List<Person> role1PersList = getPersonListByRole(department,
				role1.getRoleId() == null ? null : role1.getRoleId());
		if (role1PersList != null) {
			for (int i = 0; i < role1PersList.size(); i++) {
				role1Person += role1PersList.get(i).getName() + "、";
			}
		}
		mapRole.put("role1Person", role1Person);

		/* 角色下人员 */
		String role2Person = "";
		List<Person> role2PersList = getPersonListByRole(department,
				role2.getRoleId() == null ? null : role2.getRoleId());
		if (role2PersList != null) {
			for (int i = 0; i < role2PersList.size(); i++) {
				role2Person += role2PersList.get(i).getName() + "、";
			}
		}
		mapRole.put("role2Person", role2Person);

		/* 角色下人员 */
		String role3Person = "";
		List<Person> role3PersList = getPersonListByRole(department,
				role3.getRoleId() == null ? null : role3.getRoleId());
		if (role3PersList != null) {
			for (int i = 0; i < role3PersList.size(); i++) {
				role3Person += role3PersList.get(i).getName() + "、";
			}
		}
		mapRole.put("role3Person", role3Person);

		list.add(mapRole);
		return list;
	}

	/**
	 * <pre>
	 * 获取无需出口管制 通知人员信息
	 * </pre>
	 * 
	 * @return
	 */
	public List<Map<String, String>> getNoticePersonN() {

		String department = BaseInfoConstant.MANAGEMENT_DEPARTMENT;// 经营管理部:部门编号
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		Role role1 = EIPService.getRoleService().getRole(BaseInfoConstant.ROLE_RECORDER);// 角色：经营管理部-项目信息记录员
		Role role2 = EIPService.getRoleService().getRole(BaseInfoConstant.ROLE_BIDFILING);// 角色：经营管理部-投议标备案及许可人员
		Role role3 = EIPService.getRoleService().getRole(BaseInfoConstant.ROLE_ExpatriateManagers);// 角色：经营管理部-经营管理部外派事业部经理

		/* 角色 */
		Map<String, String> mapRole = new HashMap<String, String>();
		mapRole.put("role1", role1 == null ? " " : role1.getName());
		mapRole.put("role2", role2 == null ? " " : role2.getName());
		mapRole.put("role3", role3 == null ? " " : role3.getName());

		/* 角色下人员 */
		String role1Person = "";
		List<Person> role1PersList = getPersonListByRole(department,
				role1.getRoleId() == null ? null : role1.getRoleId());
		if (role1PersList != null) {
			for (int i = 0; i < role1PersList.size(); i++) {
				role1Person += role1PersList.get(i).getName() + "、";
			}
		}
		mapRole.put("role1Person", role1Person);

		/* 角色下人员 */
		String role2Person = "";
		List<Person> role2PersList = getPersonListByRole(department,
				role2.getRoleId() == null ? null : role2.getRoleId());
		if (role2PersList != null) {
			for (int i = 0; i < role2PersList.size(); i++) {
				role2Person += role2PersList.get(i).getName() + "、";
			}
		}
		mapRole.put("role2Person", role2Person);

		/* 角色下人员 */
		String role3Person = "";
		List<Person> role3PersList = getPersonListByRole(department,
				role3.getRoleId() == null ? null : role3.getRoleId());
		if (role3Person != null) {
			for (int i = 0; i < role3PersList.size(); i++) {
				role3Person += role3PersList.get(i).getName() + "、";
			}
		}
		mapRole.put("role3Person", role3Person);
		list.add(mapRole);
		return list;
	}

	/**
	 * <pre>
	 * 		描述：此方法用于初始话页面数据
	 * </pre>
	 * @param prjId
	 * @param user
	 * @return
	 */
	public Prj initEditOrViewPage(String prjId, UserProfile user) {
		if (StringUtils.isBlank(prjId)) {// 新建
			Prj rec = new Prj();
			rec.setCreatedDate(new Date());
			rec.setCreatedById(user.getPersonId());
			rec.setCreatedBy(user.getName());
			Dept dept = user.getDept();

			rec.setIsNew(true);
			rec.setPrjActiveStatus(BaseInfoConstant.DRFT);// 项目状态：草稿
			return rec;
		} else {// 编辑
			Prj rec = prjDao.get(prjId);
			initPrjAddrInfo(rec);// 初始化工程地址信息

			PrjPfi prjPfi = prjPfiDao.getByPrjId(prjId);
			if (prjPfi != null) {
				rec.setPrjPfiData(prjPfi);
			}
			PrjDeOrgEmp deOrgEmp = prjDeOrgEmpDao.getByPrjId(prjId);
			if (deOrgEmp != null) {
				rec.setDePrgEmpData(deOrgEmp);
			}
//			PrjDeOrgCombo prjDeOrgCombo = prjDeOrgComboDao.getByPrjId(prjId);
//			if (prjDeOrgCombo != null) {
//				rec.setDeOrgComboData(prjDeOrgCombo);
//			}

			PrjCoreEquipmentEntity coreEquipmentEntity = equipmentDao.getByPrjId(prjId);
			rec.setEquipmentData(coreEquipmentEntity);
			return rec;
		}
	}

	/**
	 * 初始化工程地址信息
	 * 
	 * @param rec
	 */
	private void initPrjAddrInfo(Prj rec) {

		PrjAddr prjAddr = prjAddrService.getEntityByPrjId(rec.getPrjId());
		if (prjAddr != null) {
			rec.setCountryId(prjAddr.getCountryId());
			rec.setCountryEName(prjAddr.getCountryEName());
			rec.setCountryCName(prjAddr.getCountryCName());
			rec.setProvinceId(prjAddr.getProvinceId());
			rec.setProvinceEName(prjAddr.getProvinceEName());
			rec.setProvinceCName(prjAddr.getProvinceCName());
			rec.setAddrC(prjAddr.getAddrC());
			rec.setAddrE(prjAddr.getAddrE());
		}
	}

	public PrjDeOrgEmp initEditOrViewEmpPage(String prjId, UserProfile user) {
		PrjDeOrgEmp prjDeOrgEmp = this.prjDeOrgEmpDao.getByPrjId(prjId);
		if (prjDeOrgEmp != null) {// 编辑
			return prjDeOrgEmp;
		} else {// 新建
			prjDeOrgEmp = new PrjDeOrgEmp();
			prjDeOrgEmp.setPrjId(prjId);
			return prjDeOrgEmp;
		}
	}

	public PrjAddr initPageAddress(String addrId, String prjId, UserProfile user) {
		PrjAddr prjAddr = this.prjAddrDao.get(addrId);
		if (prjAddr != null) {// 编辑
			return prjAddr;
		} else {// 新建
			prjAddr = new PrjAddr();
			prjAddr.setPrjId(prjId);
			return prjAddr;
		}
	}

	public PrjBidInf initBidInfPage(String prjId, UserProfile user) {
		PrjBidInf prjBidInf = this.prjBidInfDao.getByPrjId(prjId);
		if (prjBidInf != null) {// 编辑
			return prjBidInf;
		} else {// 新建
			prjBidInf = new PrjBidInf();
			prjBidInf.setPrjId(prjId);
			return prjBidInf;
		}
	}

	public List<Prj> getPrjGrid(UserProfile user, JqGrid jqGrid, Prj prj) {
		return prjDao.findPage(user, jqGrid, prj);
	}

	public List<PrjAddr> getAddressGrid(UserProfile user, JqGrid jqGrid, String prjId) {
		return prjAddrDao.findPage(user, jqGrid, prjId);
	}

	public List<PrjSof> getSofGrid(UserProfile user, JqGrid jqGrid, String prjId) {
		return prjSofDao.findPage(user, jqGrid, prjId);
		
	}

	/**
	 * <pre>
	 * 项目中文名称、英文名称相似度比较
	 * </pre>
	 * 
	 * <pre>
	 * 说明：
	 * 		1.先比较项目中文名称，项目中文名称相似度大于60%添加到集合中；
	 * 		2.若项目中文名称小于60%，则再比较项目英文名称，若项目英文名称大于60%则添加到集合中。
	 * </pre>
	 * 
	 * @param prjCName Project Chinese name
	 * @param prjEName Project English name
	 * @return 'Prj' entity collection
	 * @author YUEYUN
	 */
	public Set<Prj> detectionMethod(String prjCName, String prjEName) {
		Set<Prj> result = new HashSet<Prj>();// 返回相似度高的项目集合

		List<Prj> all = prjDao.findAll();// 所有项目信息·后期项目太多执行可能会变慢后期需要优化
		if (all.size() > 0) {
			for (Prj prj : all) {// 遍历所有项目
				double similarityCName = SimilarityUtils.similarity(prj.getPrjCName(), prjCName);// 中文名-相似度比较结果
				if (similarityCName > 0.6) {// 1.相似度大于0.6视为重复项目
					result.add(prj);
					break;
				} else {// 2.中文名不相似-比较英文名
					double similarityEName = SimilarityUtils.similarity(prj.getPrjEName(), prjEName);// 英文名-相似度比较结果
					if (similarityEName > 0.6) {// 英文名>0.6也视为重复项目
						result.add(prj);
						break;
					}
				}
			}
		}
		return result;
	}

	/**
	 * <pre>
	 * 		Description : This method is save or update project entity information.(service)
	 * </pre>
	 */
	@Transactional(transactionManager = TransManager.APP)
	public Prj confirmSaveOrUpdate(UserProfile user, Prj prj) {
		Prj ret = null;
		prj.setModifiedBy(user.getName());
		prj.setModifiedById(user.getPersonId());
		prj.setModifiedDate(new Date());
		if (prj.getIsNew()) {// 新建
			setCreatorInfo(user, prj);// 创建人信息
			prj.setStatus(0);// 草稿
			
			this.prjDao.save(prj);
			PPMService.getScheduleStatusService().saveScheduleStatus(prj.getPrjId(), "base_info01", user);
			ret = prj;
		} else {// 编辑-更新
			// setModifierInfo(user,prj);//修改人信息,如果是项目基本信息编辑的更新，重新设置项目编号
			//编辑权限
			AuthUtils.canExecute(user,BaseInfoConstant.MODULE_ID, BaseInfoConstant.MODULE_AUTH_EDIT,prj.getPrjId(),prj);
			this.prjDao.update(prj);
			this.prjAddrService.saveOrUpdate(prj);
			ret = prj;
		}
		return ret;
	}

	/**
	 * @param user Currently logged in user
	 * @param prj  Project information
	 */
	private void setModifierInfo(UserProfile user, Prj prj) {
		prj.setModifiedBy(user.getName());
		prj.setModifiedById(user.getPersonId());
		prj.setModifiedDate(new Date());
	}

	/**
	 * <pre>
	 * This method is used to set the creator information
	 * </pre>
	 * 
	 * @param prj Project information
	 */
	private void setCreatorInfo(UserProfile user, Prj prj) {
		prj.setPrjId(com.supporter.util.UUIDHex.newId());
		prj.setCreatedById(user.getPersonId());
		prj.setCreatedBy(user.getName());
		prj.setCreatedDate(new Date());
		Dept dept = user.getDept();// 创建人部门信息
		if (dept == null) {
			prj.setDeptId("1");
			prj.setDeptName("admin");
			prj.setDeptNo("1");
		} else {
			prj.setDeptId(dept.getDeptId());// 创建人部门id
			prj.setDeptName(dept.getName());// 创建人部门名称
			prj.setDeptNo(dept.getDeptNo());// 创建人部门的系统编号
		}
		String deptId = prj.getLeadingDeptId();// 主导部门系统编号
		String deptNoSub_2 = deptId.substring(0, 2);// 主导部门的系统编码prjNo 前两位
		// String prjCodeNameSub5 = prj.getPrjCodeName().substring(0, 5);// 项目代号前5位
		String prjCodeNameSub5 = prj.getPrjCodeName();
		// 生成项目编号
		prj.setPrjNo(this.createdPrjNo(prj.getLeadingDeptId(), prjCodeNameSub5, deptNoSub_2));// prj.getDeptId()
																								// 现在主导部门的编号
	}

	/**
	 * 保存项目信息
	 * 
	 * @param user
	 * @param prj
	 * @return
	 */
	@Transactional(transactionManager = TransManager.APP)
	public Prj prjSaveOrUpdate(UserProfile user, Prj prj) {
		String prjId = prj.getPrjId();
		Prj entity = prjDao.get(prjId);
		entity.setPrjCCompany(prj.getPrjCCompany());
		entity.setPrjECompany(prj.getPrjECompany());
		entity.setCbdAmount(prj.getCbdAmount());
		entity.setCbdWay(prj.getCbdWay());
		entity.setPrjApproGovOrg(prj.getPrjApproGovOrg());// 立项政府部门
		entity.setSettlementWayId(prj.getSettlementWayId());// 是否人名币结算id
		entity.setSettlementWayName(prj.getSettlementWayName());// 是否人名币结算显示名称
		// 融资信息
		PrjPfi prjPfi = prj.getPrjPfiData();
		// 资金来源
		List<PrjSof> prjSofs = prj.getSofListData();
		
		//编辑权限
		AuthUtils.canExecute(user,BaseInfoConstant.MODULE_ID, BaseInfoConstant.MODULE_AUTH_EDIT,prj.getPrjId(),entity);
		this.prjDao.update(entity);
		this.saveSof(prjSofs);
		this.saveOrUpdatePfi(prjPfi, prjId);
		return entity;
	}

	@Transactional(transactionManager = TransManager.APP)
	public void saveOrUpdatePfi(PrjPfi prjPfi, String prjId) {
		if (prjPfi != null) {
			PrjPfi entity = prjPfiDao.getByPrjId(prjId);
			prjPfiDao.clear();
			if (entity != null) {
				prjPfi.setPrjId(prjId);
				prjPfiDao.update(prjPfi);
			} else {
				prjPfi.setPrjId(prjId);
				prjPfi.setPfId(com.supporter.util.UUIDHex.newId());
				prjPfiDao.save(prjPfi);
			}
		}
	}

	@Transactional(transactionManager = TransManager.APP)
	public void saveSof(List<PrjSof> prjSofs) {
		if (prjSofs != null && prjSofs.size() > 0) {
			for (PrjSof prjSof : prjSofs) {
				String sofId = prjSof.getSofId();
				PrjSof entity = prjSofDao.get(sofId);
				prjSofDao.clear();
				if (entity != null) {
					prjSofDao.update(prjSof);
				} else {
					prjSofDao.save(prjSof);
					;
				}
			}
		}
	}

	/**
	 * 保存或更新地址信息
	 */
	@Transactional(transactionManager = TransManager.APP)
	public PrjAddr saveOrUpdateAddress(UserProfile user, PrjAddr prjAddr) {
		String addrId = prjAddr.getAddrId();
		if (StringUtils.isNotBlank(addrId)) {
			prjAddr.setAddrC(prjAddr.getCountryAddrC() + prjAddr.getProvinceAddrC());
			prjAddr.setAddrE(prjAddr.getCountryAddrE() + prjAddr.getProvinceAddrE());
			prjAddrDao.update(prjAddr);
		} else {
			prjAddr.setAddrId(com.supporter.util.UUIDHex.newId());
			prjAddrDao.save(prjAddr);
		}
		return null;
	}

	/**
	 * 保存或更新招标信息
	 */
	public PrjBidInf saveOrUpdateBidInf(UserProfile user, PrjBidInf prjBidInf) {
		String bidInfId = prjBidInf.getBidInfId();
		if (StringUtils.isNotBlank(bidInfId)) {
			//编辑权限
			AuthUtils.canExecute(user,BaseInfoConstant.MODULE_ID, BaseInfoConstant.MODULE_AUTH_EDIT,prjBidInf.getPrjId(),prjDao.get(prjBidInf.getPrjId()));
			prjBidInfDao.update(prjBidInf);
		} else {
			prjBidInf.setBidInfId(com.supporter.util.UUIDHex.newId());
			System.out.println("entity=" + JSONObject.fromObject(prjBidInf));
			prjBidInfDao.save(prjBidInf);
		}

		return null;
	}

	/**
	 * 启动开发暂存 startStaging
	 */
	@Transactional(transactionManager = TransManager.APP)
	public Prj startStaging(UserProfile user, String prjId, Integer prjDewStatus, String createdBy,
			String createdById) {
		Prj prj = this.prjDao.get(prjId);// 获取项目信息
		if (prj != null) {
			prj.setPrjDewStatus(prjDewStatus);
			if (prjDewStatus == StatusCodeTable.FOCUS) {// 0表示关注，若启动状态为关注是直接激活项目
				prj.setPrjActiveStatus(BaseInfoConstant.ACTIVE);// 1表示项目激活状态
			} else {
				prj.setPrjActiveStatus(BaseInfoConstant.CLOSE);// 0表示项目关闭状态
			}
			prj.setCreatedBy(createdBy);
			prj.setCreatedById(createdById);
			//编辑权限
			AuthUtils.canExecute(user,BaseInfoConstant.MODULE_ID, BaseInfoConstant.MODULE_AUTH_EDIT,prj.getPrjId(),prj);
			this.prjDao.update(prj);
			// PrjDeOrgEmp emp = this.prjDeOrgEmpDao.getByPrjId(prjId);//开发工作组织人员
//			if(emp != null ) {
//				emp.setEmpId(empId);
//				emp.setEmpName(empName);
//				prjDeOrgEmpDao.update(emp);
//			}
//			else {//新建-保存到开发工作组织人员
//				emp =  new PrjDeOrgEmp();
//				emp.setDeOrgEmpId(com.supporter.util.UUIDHex.newId());
//				emp.setEmpId(empId);
//				emp.setEmpName(empName);
//				emp.setEmpRoleId("EMP_ROLE_6039847125");
//				emp.setEmpRoleName("开发负责人");
//				emp.setPrjId(prjId);
//				prjDeOrgEmpDao.save(emp);
//			}
			return prj;
		}
		return null;
	}

	/**
	 * 出口管制确认保存方法 confirmContinue
	 */
	public Prj confirmContinue(UserProfile user, String prjId, boolean isEccConfirm) {
		Prj prj = prjDao.get(prjId);
		if (prj != null) {
			prj.setIsEccConfirm(isEccConfirm);
			if (isEccConfirm) {// true
				eccService.saveByPrjId(prjId, 0);// 管制确认状态保存在出口管制 0 无需出口管制
			} else {// false
				eccService.saveByPrjId(prjId, 1);// 1表示需要出口管制
			}
			prjDao.update(prj);
		}
		return prj;
	}

	/**
	 * <pre>
	 * 出口管制判断方法
	 * </pre>
	 * 
	 * @param prjId 项目主键
	 * @return 项目信息
	 */
	public Prj isEccConfirm(String prjId) {
		if (prjId == null || prjId == "") {
			return null;
		}

		Map<String, String> controlsCountryMap = BaseInfoConstant.getControlsCountryMap();// 制裁国家码表信息
		Prj prj = initEditOrViewPage(prjId, null);

		for (String itemId : controlsCountryMap.values()) {// getDisplayName制裁国家英文名称
			
			if(itemId.equals(prj.getCountryId())) {//一致 受管制 需要进行出口管制
				prj.setIsEccConfirm(true);
				return prj;//结束循环
			}
			/*不一致，设为false继续循环，循环结束还是为false说明不在制裁名单内*/
			prj.setIsEccConfirm(false);
		}
		
		return prj;
	}

	/**
	 * <pre>
	 * 生成项目编号
	 * </pre>
	 * 
	 * @author YUEYUN
	 * @param deptId      部门id
	 * @param prjCodeName 项目代码前5位
	 * @param deptSub2    部门代码前两位
	 * @return 部门代码(2位)+项目代号(5位)+登记年份(4位)+流水号(3位)
	 */
	public String createdPrjNo(String leadingDeptId, String prjCodeName_5, String deptSub_2) {// 部门代码前两位deptSub2

		String yearStr = new SimpleDateFormat("yyyy").format(new Date());// 年份
		List<Prj> prjInfoByLeadingDeptID = this.prjDao.getPrjInfoByLeadingDeptID(leadingDeptId);// 根据主导部门id获取到的项目信息集合
		String prjNoSub = deptSub_2 + prjCodeName_5 + yearStr;//
		String number = this.getPrjNoLast_3(prjInfoByLeadingDeptID);// 项目编号后3位
		return prjNoSub + number;

	}

	/**
	 * <p>
	 * 获取项目编号后三位
	 * </p>
	 * 
	 * <pre>
	 * 说明：后三位的流水号是根据主导部门当年所建项目递增
	 * </pre>
	 * 
	 * @param prjInfoByLeadingDeptID 主导部门项目信息集合
	 * @return 流水号最大值
	 */
	private String getPrjNoLast_3(List<Prj> prjInfoByLeadingDeptID) {

		List<Integer> numList_3 = new ArrayList<Integer>();// 编号后三位集合用来排序取最大值
		if (prjInfoByLeadingDeptID == null || prjInfoByLeadingDeptID.size() == 0) {// 根据部门查到的信息为空，说明此主导部门本年新建项目
			return "001";
		}

		for (Prj prj : prjInfoByLeadingDeptID) {
			String prjNo = prj.getPrjNo();
			Integer integerLast_3 = Integer.valueOf(prjNo.substring(prjNo.length() - 3, prjNo.length()));// 取后三位转Integer，如果是个位数的话
																											// 没有0
			numList_3.add(integerLast_3);// 添加到集合中取最大值
		}

		Integer maxNumberLast_3_int = (Integer) Collections.max(numList_3);// 最大值integer
		String maxNumberLast_3_str = Integer.toString(maxNumberLast_3_int + 1);// 最大值+1转String再判断

		if (maxNumberLast_3_str.length() == 1) {// 判断补0
			return "00" + maxNumberLast_3_str;
		} else if (maxNumberLast_3_str.length() == 2) {
			return "0" + maxNumberLast_3_str;
		} else {
			return maxNumberLast_3_str;
		}
	}

	@Override
	public Prj PrjInformation(String prjId) {

		return prjDao.get(prjId);
	}

	/**
	 * <pre>
	 * 初始化新建 项目基本信息
	 * </pre>
	 * 
	 * @return
	 */
	public Prj initBaseInfoView(String prjId) {
		Prj uniqueResult = prjDao.findUniqueResult("prjId", prjId);
		return uniqueResult;
	}

	/*
	 * 获取所有项目信息
	 * 
	 * @see
	 * com.supporter.prj.ppm.prj_org.base_info.common.PrjInfo#getAllPrjInformation()
	 */
	@Override
	public List<Prj> getAllPrjInformation() {
		return prjDao.findAll();
	}

	/**
	 * <pre>
	 * 提交生效：状态设为1提交未通知
	 * </pre>
	 * 
	 * @param prjId 项目主键
	 * @return 项目信息
	 */
	public Prj effect(String prjId, UserProfile user) {
		Prj prj = this.prjDao.get(prjId);
		prj.setPrjActiveStatus(BaseInfoConstant.ACTIVE);// 项目状态：激活
		prj.setStatus(1);// 启用-数据不可更改 0草稿1提交未通知2提交已通知
		this.prjDao.update(prj);
		PPMService.getScheduleStatusService().saveScheduleStatus(prjId, "base_info02", user);
		return prj;
	}

	/**
	 * <pre>
	 * 通知发送待办
	 * </pre>
	 * 
	 * @author YUEYUN
	 * @return 项目信息
	 */
	public Prj confirmNotice(String prjId, UserProfile user) {
		Prj prj = prjDao.get(prjId);// 项目信息
		prj.setProcId(UUIDHex.newId());
		prj.setStatus(2);

		String title = "知会:" + prj.getDeptName() + " -" + prj.getCreatedBy() + "提交的【新开拓项目】";
		String url = "ppm/prj_reg/base_info/baseInfo_previewV.html?prjId=" + prjId + "&notice=true";
		String procId = prj.getProcId();

		if (prj.getIsEccConfirm()) {// true 需要出口管制
			needControls(title, url, procId);
		} else {// false 无需出口管制
			notControls(title, url, procId);
		}

		prjDao.update(prj);// 通知完成后改变状态为已通知
		PPMService.getScheduleStatusService().saveScheduleStatus(prjId, "base_info03", user);
		return prj;
	}

	/**
	 * <pre>
	 * 需要出口管制的情况下通知相关人员
	 * </pre>
	 * 
	 * @param title  待办标题
	 * @param url    待办页面路径
	 * @param procId 流程id
	 */
	private void needControls(String title, String url, String procId) {

		String department = BaseInfoConstant.MANAGEMENT_DEPARTMENT;
		String recorder = BaseInfoConstant.ROLE_RECORDER;// 经营管理部项目信息登记管理岗位人员-角色
		String expatriateManagers = BaseInfoConstant.ROLE_ExpatriateManagers;// 经营管理部-外派事业部经理-角色
		String exportControl = BaseInfoConstant.ROLE_EXPORTCONTROL;// 事业部出口管制专员-角色

		/* 通知经营管理部项目信息登记管理岗位人员 */
		List<Person> personList = getPersonListByRole(department, recorder);
		for (Person person : personList) {
			String personId = person.getPersonId();
			sendNotice(personId, title, url, procId);
		}

		/* 通知-经营管理部-外派事业部经理 */
		List<Person> personList_expatriate = getPersonListByRole(department, expatriateManagers);
		for (Person person_expatr : personList_expatriate) {
			String personId = person_expatr.getPersonId();
			sendNotice(personId, title, url, procId);
		}

		/* 通知-事业部出口管制专员 */
		List<Person> personList_exportControl = getPersonListByRole(department, exportControl);
		for (Person person_exportControl : personList_exportControl) {
			String personId = person_exportControl.getPersonId();
			sendNotice(personId, title, url, procId);
		}
	}
	/**
	 * <pre>
	 * 无需出口管制的情况下通知相关人员
	 * </pre>
	 * 
	 * @param title  通知待办的标题
	 * @param url    通知待办页面路径
	 * @param procId 通知待办流程id
	 */
	private void notControls(String title, String url, String procId) {

		String department = BaseInfoConstant.MANAGEMENT_DEPARTMENT;// 经营管理部-部门-部门
		String recorder = BaseInfoConstant.ROLE_RECORDER;// 经营管理部项目信息登记管理岗位人员-角色
		String bidFiling = BaseInfoConstant.ROLE_BIDFILING;// 经营管理部投议标备案及许可岗位人员-角色
		String expatriateManagers = BaseInfoConstant.ROLE_ExpatriateManagers;// 经营管理部-外派事业部经理-角色

		/* 通知经营管理部项目信息登记管理岗位人员 */
		List<Person> personList = getPersonListByRole(department, recorder);
		for (Person person : personList) {
			String personId = person.getPersonId();
			sendNotice(personId, title, url, procId);
		}

		/* 通知-经营管理部投议标备案及许可岗位人员 */
		List<Person> personList_bid = getPersonListByRole(department, bidFiling);
		for (Person person_bid : personList_bid) {
			String personId = person_bid.getPersonId();
			sendNotice(personId, title, url, procId);
		}

		/* 通知-经营管理部-外派事业部经理 */
		List<Person> personList_expatriate = getPersonListByRole(department, expatriateManagers);
		for (Person person_expatr : personList_expatriate) {
			String personId = person_expatr.getPersonId();
			sendNotice(personId, title, url, procId);
		}

	}

	/**
	 * <pre>
	 * 根据部门和角色获取角色下的人员信息
	 * </pre>
	 * 
	 * @param deptId 部门编号
	 * @param roleId 角色编号
	 * @return 角色下的人员信息
	 */
	private List<Person> getPersonListByRole(String deptId, String roleId) {
		if (deptId == null || roleId == null) {
			return null;
		}
		Dept dept = EIPService.getDeptService().getDept(deptId);
		Role role = EIPService.getRoleService().getRole(roleId);
		List<Person> personList = EIPService.getRoleService().getPersonsForDept(role, dept);
		EIPService.getRoleService().getPersonFromRole("sss","sss");
		return personList;

	}

	/**
	 * <pre>
	 * 发送待办通知
	 * </pre>
	 * 
	 * @param personId 通知人主键
	 * @param title    待办标题
	 * @param url      待办页面路径
	 * @param procId   流程id
	 */
	private void sendNotice(String personId, String title, String url, String procId) {

		Message message = EIPService.getBMSService().newMessage();// --------------------------------获取待办通知服务内容
		message.setPersonId(personId);// ------------------------------------------------------------通知人
		// String title = "知会："+purchaseDemand.getProcTitle();
		// 待办标题
		message.setEventTitle(title);// -------------------------------------------------------------通知待办标题
		message.setNotifyTime(new Date());// --------------------------------------------------------通知待办日期
		// 待办地址"/cpp/purchase_demand/demand/purchase_demand_audit_notify.html?id="+id
		message.setWebPageURL(url);// ------------------------------------------------------------------通知待办地址
		message.setModuleId(BaseInfoConstant.MODULE_ID);// ------------------------------------------------------应用编号
		// 默认地指定为“待办”类型
		message.setMessageType(ITodo.MsgType.CC);// -------------------------------------------------待办类型
		message.setWebappName("BM");
		// message.setWfProcId(execContext.getProcId());
		message.setWfProcId(procId);
		// 加入待办事宜（BMS）
		EIPService.getBMSService().addMessage(message);// -------------------------------------------获取待办服务发送待办

	}

	/**
	 * <pre>
	 *  Get 'PrjBidInf' information by project primary key
	 * </pre>
	 */
	@Override
	public PrjBidInf getPrjBidInfInf(String prjId) {
		if (prjId == "" || prjId == null) {
			return null;
		}
		return prjBidInfDao.getByPrjId(prjId);
	}

	/*
	 * (更新项目开发状态)
	 * 
	 * @see
	 * com.supporter.prj.ppm.prj_org.base_info.common.PrjInfo#updatePrjActiveStauts(
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public Prj updatePrjActiveStauts(String prjId, int prjActiveStatus) {// prjActiveStatus项目开发状态 激活、关闭
		if (prjId == null || prjId == "") {
			return null;
		}
		Prj prj = prjDao.get(prjId);
		prj.setPrjActiveStatus(prjActiveStatus);
		this.prjDao.update(prj);
		return prj;
	}

	/**
	 * 判断制裁等级
	 * @param countryId 国家或地区ID
	 * @return true：是全面制裁  false：不是全面制裁 有限制裁
	 */
	public boolean checkEccLevel(String countryId) {
		
		List<IComCodeTableItem> codeTableItems = EIPService.getComCodeTableService().getCodeTableItems(BaseInfoConstant.CODETABLE_PPM_CONTROLS_COUNTRY);
		for (IComCodeTableItem item : codeTableItems) {
			if(item.getDisplayName().equals(countryId)) {
				if(item.getExtField1().equals("0")) {//制裁等级：0全面制裁1有限制裁
					return true;
				}
			}
		}
		return false;
	}


	/**
	 * <pre>
	 * 		description:
	 * 					This method is get entity information by primary Key .
	 * 
	 * </pre>
	 * @param prjId  primary key
	 * @return entity information
	 */
	public Object get(String prjId) {
		return prjDao.get(prjId);
	}


	/*
	 * ↓ 此方法为重名检测的方法-弃用重写 ↓
	 * ___________________________________________________________________________
	 * |public boolean detectionMethod(String prjCName, String prjEName) { | |
	 * double similarity = SimilarityUtils.similarity(prjCName, prjEName); | |
	 * if(similarity > 0.6) { | | return false; | | } | | else { | | return true; |
	 * | } | | } |
	 * |___________________________________________________________________________|
	 */

}
