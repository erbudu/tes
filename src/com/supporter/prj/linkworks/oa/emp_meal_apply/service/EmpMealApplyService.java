package com.supporter.prj.linkworks.oa.emp_meal_apply.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.doc_in.util.AuthUtil;
import com.supporter.prj.linkworks.oa.emp_meal_apply.constants.EmpMealAuthConstant;
import com.supporter.prj.linkworks.oa.emp_meal_apply.dao.EmpCustormerMealRecDao;
import com.supporter.prj.linkworks.oa.emp_meal_apply.dao.EmpMealApplyDao;
import com.supporter.prj.linkworks.oa.emp_meal_apply.dao.EmpMealRecDao;
import com.supporter.prj.linkworks.oa.emp_meal_apply.dao.NonEmpMealRecDao;
import com.supporter.prj.linkworks.oa.emp_meal_apply.entity.EmpCustormerMealRec;
import com.supporter.prj.linkworks.oa.emp_meal_apply.entity.EmpMealApply;
import com.supporter.prj.linkworks.oa.emp_meal_apply.entity.EmpMealRec;
import com.supporter.prj.linkworks.oa.emp_meal_apply.entity.NonEmpMealRec;
import com.supporter.prj.linkworks.oa.history_exam_record.service.VCneecExamService;
import com.supporter.util.CommonUtil;

@Service
public class EmpMealApplyService {
	@Autowired
	private EmpMealApplyDao codeDao;
	@Autowired
	private EmpCustormerMealRecService custormerService;
	@Autowired
	private NonEmpMealRecService nonMealRecService;
	@Autowired
	private EmpMealRecService mealRecService;
	@Autowired
	private EmpMealRecDao mealRecDao;
	@Autowired
	private NonEmpMealRecDao nonMealRecDao;
	@Autowired
	private EmpCustormerMealRecDao custormerDao;
	@Autowired
	private VCneecExamService cneecExamService;
	/**
	 * 根据主键获取信息.
	 * 
	 * @param codeId
	 *            主键
	 * @return WmsEmpMealApply
	 */
	public EmpMealApply get(String codeId) {
		return codeDao.get(codeId);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param codeId
	 * @return
	 */
	public EmpMealApply initEditOrViewPage(String codeId, String docClassify,
			UserProfile user) {
		if (StringUtils.isBlank(codeId)) {// 新建
			EmpMealApply code = new EmpMealApply();
			code.setApplyId(com.supporter.util.UUIDHex.newId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String d = sdf.format(new Date());
			code.setCreatedDate(d);
			code.setApplyDate(d);
			code.setAdd(true);
			//初始化流程状态
			code.setStatus(EmpMealApply.DRAFT);
			int year = CommonUtil.parseInt(CommonUtil
					.format(new Date(), "yyyy"), 0);
			int month = CommonUtil.parseInt(
					CommonUtil.format(new Date(), "MM"), 0);
			if (month == 12) {
				code.setYear(year + 1);
				code.setMonth(month + 1);
			} else {
				code.setYear(year);
				code.setMonth(month + 1);
			}
			if (user != null) {
				code.setCreatedBy(user.getPersonId());
				Dept dept = user.getDept();
				if (dept != null) {
					code.setDeptId(user.getDeptId());
					code.setDeptName(dept.getName());
				} else {
					code.setDeptId("1");
					code.setDeptName("测试系统");
				}
			}
			return code;
		} else {// 编辑
			EmpMealApply code = codeDao.get(codeId);
			code.setAdd(false);
			return code;
		}

	}
	/**
	 * 进入查看页面.
	 * @param moduleId
	 * @return
	 */
	public EmpMealApply viewPage(String docId, UserProfile user) {
		EmpMealApply entity =  codeDao.get(docId);
			//如果有旧系统的流程，则获取旧系统的procId
			long oldProcId = cneecExamService.getProcIdByRecord(entity);
			if (oldProcId > 0)entity.setOldProcId(oldProcId);
			return entity;
	}
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user
	 *            用户信息
	 * @param jqGridReq
	 *            jqgrid请求对象
	 * @param codeIds
	 *            多个逗号分隔
	 * @return JqGrid
	 */
	public List<EmpMealApply> getGrid(UserProfile user, JqGrid jqGrid,
			EmpMealApply code) {
		return codeDao.findPage(user,jqGrid, code);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param codeId
	 * @param materialName
	 * @return
	 */
	public boolean checkNameIsValid(EmpMealApply entity) {
		return this.codeDao.checkNameIsValid(entity);
	}

	/**
	 * 删除
	 * 
	 * @param user
	 *            用户信息
	 * @param moduleIds
	 *            主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String docIds) {
		if (StringUtils.isNotBlank(docIds)) {
			for (String docId : docIds.split(",")) {
				EmpMealApply codeDb = this.getWmsEmpMealApplyFromBuffer(docId);
				if (codeDb == null) {
					continue;
				}
				//权限验证
				AuthUtil.canExecute(user, EmpMealAuthConstant.AUTH_OPER_NAME_SETVALPPLY, docId, codeDb);
				this.recursiveDelete(user, codeDb);
			}
			// 记录日志
			/*
			 * EmpMealApplyUtils.saveEmpMealApplyOperateLog(user, null,
			 * EmpMealApply.LogOper.EmpMealApply_DEL.getOperName(),
			 * "{delEmpMealApplyIds : " + codeIds + "}");
			 */
		}
	}

	/**
	 * 保存或更新
	 * 
	 * @param user
	 *            用户信息
	 * @param materialCode
	 *            实体类
	 * @return
	 */
	public EmpMealApply saveOrUpdate(UserProfile user, EmpMealApply code,
			Map<String, Object> valueMap) {
		EmpMealApply ret = null;
		String delRecId = code.getDelIdRec();
		String delNonRecId = code.getDelIdNonRec();
		String delCustormerId = code.getDelIdCustormer();
		int a = 0;
		double b = 0;
		if (code.getAdd()) {// 新建
			code.setEmpId(user.getPersonId());
			code.setEmpName(user.getName());
			this.codeDao.save(code);
			ret = code;
			// 记录日志
			/*
			 * MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
			 * MaterialCode.LogOper.MATERIALCODE_ADD.getOperName(), null);
			 */
		} else {// 编辑
			code.setEmpId(user.getPersonId());
			code.setEmpName(user.getName());
			code.setModifiedBy(user.getPersonId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			code.setModifiedDate(sdf.format(new Date()));
			this.codeDao.update(code);
			ret = code;
			// 记录日志
			/*
			 * MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
			 * MaterialCode.LogOper.MATERIALCODE_EDIT.getOperName(), null);
			 */
		}
		a += saveOrUpdateRecs(user, code, ret, delRecId, a);
		a += saveOrUpdateNonRecs(user, code, ret, delNonRecId, 0);
		b += saveOrUpdateCustormerRecs(user, code, ret, delCustormerId, b);
		if (code.getRecList() != null || code.getNonList() != null) {

			code.setEmpCount(Long.valueOf(a));
			// 取出部门经费乘以员工人数
			code.setEmpInAmount(a * code.getRecList().get(0).getMealAmount());
		}

		if (code.getCustormerList() != null) {
			code.setEmpOutAmount(b);
		}

		this.codeDao.update(code);
		return ret;

	}

	@Autowired
	private EmpMealRecDao recDao;
	@Autowired
	private NonEmpMealRecDao nonRecDao;
	@Autowired
	private EmpCustormerMealRecDao custormerRecDao;

	// 从历史数据库新建
	public EmpMealApply saveFromHistory(UserProfile user, EmpMealApply code,
			Map<String, Object> valueMap) {
		EmpMealApply ret = null;
		String id = code.getApplyId();
		String delRecId = code.getDelIdRec();
		String delNonRecId = code.getDelIdNonRec();
		String delCustormerId = code.getDelIdCustormer();
		int a = 0;
		double b = 0;
		ret = codeDao.get(code.getApplyId());
		ret.setApplyId(com.supporter.util.UUIDHex.newId());
		ret.setYear(code.getYear());
		ret.setMonth(code.getMonth());
		ret.setStatus(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(new Date());
		ret.setCreatedDate(time);
		ret.setCreatedBy(user.getPersonId());
		ret.setApplyDate(time);
		code = ret;
		// 保存
		this.codeDao.save(code);
		List<EmpMealRec> rec = recDao.getPersonsByApply(id);
		if (rec != null && rec.size() > 0) {
			for (EmpMealRec empMealRec : rec) {
				empMealRec.setRecId(com.supporter.util.UUIDHex.newId());
			}
		}
		ret.setRecList(rec);
		List<NonEmpMealRec> rec2 = nonRecDao.getPersonsByApply(id);
		if (rec2 != null) {
			for (NonEmpMealRec nonEmpMealRec : rec2) {
				nonEmpMealRec.setRecId(com.supporter.util.UUIDHex.newId());
			}
		}
		ret.setNonList(rec2);
		List<EmpCustormerMealRec> rec3 = custormerRecDao.getPersonsByApply(id);
		if (rec3 != null) {
			for (EmpCustormerMealRec empCustormerMealRec : rec3) {
				empCustormerMealRec
						.setRecId(com.supporter.util.UUIDHex.newId());
			}
		}
		ret.setCustormerList(rec3);
		code = ret;
		// 记录日志
		/*
		 * MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
		 * MaterialCode.LogOper.MATERIALCODE_EDIT.getOperName(), null);
		 */
		saveOrUpdateRecs(user, code, ret, delRecId, a);
		saveOrUpdateNonRecs(user, code, ret, delNonRecId, a);
		saveOrUpdateCustormerRecs(user, code, ret, delCustormerId, b);
		return ret;

	}

	// 保存从表系统内职工餐标明细
	private int saveOrUpdateRecs(UserProfile user, EmpMealApply clcReport,
			EmpMealApply ret, String delIds, int a) {
		List<EmpMealRec> list = clcReport.getRecList();
		if (list != null) {
			for (EmpMealRec rec : list) {
				rec.setApplyId(ret.getApplyId());
				if (StringUtils.isNotBlank(rec.getEmpName())) {
					mealRecDao.saveOrUpdate(rec);
					a += 1;
				}

			}
		}
		mealRecService.deleteRec(clcReport.getApplyId(), delIds);
		return a;

	}

	// 保存外职工餐标明细
	private int saveOrUpdateNonRecs(UserProfile user, EmpMealApply clcReport,
			EmpMealApply ret, String delIds, int a) {
		List<NonEmpMealRec> list = clcReport.getNonList();
		if (list != null) {
			for (NonEmpMealRec rec : list) {
				rec.setApplyId(ret.getApplyId());
				if (StringUtils.isNotBlank(rec.getEmpName())) {
					nonMealRecDao.saveOrUpdate(rec);
					a += 1;
				}

			}
		}
		nonMealRecService.deleteRec(clcReport.getApplyId(), delIds);
		return a;
	}

	// 保存客餐餐标明细
	private double saveOrUpdateCustormerRecs(UserProfile user,
			EmpMealApply clcReport, EmpMealApply ret, String delIds, double b) {
		List<EmpCustormerMealRec> list = clcReport.getCustormerList();
		if (list != null) {
			for (EmpCustormerMealRec rec : list) {
				rec.setApplyId(ret.getApplyId());
				if (StringUtils.isNotBlank(rec.getCustormerName())
						&& !rec.getCustormerName().equals("-1")) {
					custormerDao.saveOrUpdate(rec);
					b += rec.getMealAmount();
				}

			}
		}
		custormerService.deleteRec(clcReport.getApplyId(), delIds);
		return b;
	}

	/**
	 * 递归删除
	 * 
	 * @param EmpMealApply
	 */
	private void recursiveDelete(UserProfile user, EmpMealApply code) {
		if (true) {// 可删除
			String codeId = code.getApplyId();
			this.codeDao.delete(codeId);
			mealRecService.deleteRecByPlcId(codeId);
			nonMealRecService.deleteRecByPlcId(codeId);
			custormerService.deleteRecByPlcId(codeId);
		}

	}

	public EmpMealApply getWmsEmpMealApplyFromBuffer(String codeIdOrName) {
		if (StringUtils.isBlank(codeIdOrName)) {
			return null;
		}
		EmpMealApply code = this.get(codeIdOrName);
		return code;
	}

	public void update(EmpMealApply entity) {
		this.codeDao.update(entity);

	}

}
