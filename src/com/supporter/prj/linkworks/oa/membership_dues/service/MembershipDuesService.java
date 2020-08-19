package com.supporter.prj.linkworks.oa.membership_dues.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.membership_dues.dao.MembershipDuesDao;
import com.supporter.prj.linkworks.oa.membership_dues.entity.MembershipDues;
import com.supporter.prj.linkworks.oa.membership_info.dao.MembershipInfoDao;
import com.supporter.prj.linkworks.oa.membership_info.entity.MembershipInfo;
import com.supporter.prj.linkworks.oa.salary.dao.SalaryDao;
import com.supporter.prj.linkworks.oa.salary.entity.Salary;

/**
 * @Title: Service
 * @Description: 报告
 * @author liyinfeng
 * @date 2017-7-05 10:25:07
 * @version V1.0
 * 
 */
@Service
@Transactional(TransManager.APP)
public class MembershipDuesService {
	@Autowired
	private MembershipDuesDao membershipDuesDao;
	@Autowired
	private SalaryDao salaryDao;
	@Autowired
	private MembershipInfoDao membershipInfoDao;

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param moduleId
	 * @return
	 */
	public MembershipDues initEditOrViewPage(String recId, UserProfile user) {
		if (StringUtils.isBlank(recId)) {// 新建
			MembershipDues entity = newMembershipDues(user);
			return entity;
		} else {// 编辑
			MembershipDues MembershipDues = membershipDuesDao.get(recId);
			return MembershipDues;
		}
	}

	/**
	 * 新建实例,并初始化必要的属性.
	 * 
	 * @param auserprf_U
	 * @return
	 */
	public MembershipDues newMembershipDues(UserProfile auserprf_U) {
		MembershipDues lMembershipDues_N = new MembershipDues();
		lMembershipDues_N.setRecId(com.supporter.util.UUIDHex.newId());
		return lMembershipDues_N;
	}

	public double findMembershipDues(Salary salary) {
		boolean bo = checkMember(salary.getEmpNo());
		if (bo) {
			MembershipDues m = membershipDuesDao.findMembershipDues(salary);
			if (m != null) {
				return m.getPayableAmount();
			}
		}
		return -1.00;
	}

	public MembershipDues findMembershipDuesJiShu(Salary salary) {
		boolean bo = checkMember(salary.getEmpNo());
		if (bo) {
			MembershipDues m = membershipDuesDao.findMembershipDues(salary);
			if (m != null) {
				return m;
			}
		}
		return null;
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user
	 *            用户信息
	 * @param jqGridReq
	 *            jqgrid请求对象
	 * @param moduleIds
	 *            多个逗号分隔
	 * @return JqGrid
	 */
	public List<MembershipDues> getGrid(UserProfile user, JqGrid jqGrid,
			MembershipDues entity) {
		return membershipDuesDao.findPage(jqGrid, entity);
	}

	/**
	 * 保存或更新
	 * 
	 * @param user
	 *            用户信息
	 * @param module
	 *            实体类
	 * @return
	 */
	public MembershipDues saveOrUpdate(UserProfile user, MembershipDues entity,
			Map<String, Object> valueMap) {
		if (entity.getAdd()) {// 获取是否是正职
			this.membershipDuesDao.save(entity);
			// 记录日志
			// ModuleUtils.saveModuleOperateLog(user, module,
			// Contract.LogOper.MODULE_ADD.getOperName(), null);
		} else {// 编辑
			this.membershipDuesDao.update(entity);
			// 记录日志
			// ModuleUtils.saveModuleOperateLog(user, module,
			// Contract.LogOper.MODULE_EDIT.getOperName(), null);
		}

		return entity;

	}

	/**
	 * 删除
	 * 
	 * @param user
	 *            用户信息
	 * @param moduleIds
	 *            主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, Salary salary) {
		if (salary != null) {
			String empNo = salary.getEmpNo();
			String yearMonth = salary.getSalaryMonth();
			String[] array = yearMonth.split("年");
			String year = "";
			String month = "";
			if (array != null && array.length > 0) {
				year = array[0];
			}
			String[] array2 = array[1].split("月");
			if (array2 != null && array2.length > 0) {
				if (array2[0].length() > 1) {
					month = array2[0];
				} else {
					month = "0" + array2[0];
				}
			}
			String hql = "from " + MembershipDues.class.getName()
					+ " where empNo = ? and dbYear = ? and dbMonth = ?";
			String[] values = new String[] { empNo, year, month };
			List<MembershipDues> entities = this.membershipDuesDao.find(hql,
					values);
			this.membershipDuesDao.delete(entities);
			// EIPService.getLogService("MPM_MCA").info(user,
			// Contract.LogOper.MODULE_DEL.getOperName(), "{delContractIds : " +
			// moduleIds + "}", null, null);
		}
	}

	// 按月刪除
	public void delete(UserProfile user, List<Salary> list) {
		for (Salary salary : list) {
			if (salary != null) {
				delete(user, salary);
			}
		}
	}

	public boolean checkMember(String empNo) {
		boolean bo = false;
		String hql = "from " + MembershipInfo.class.getName()
				+ " where empNo = ? ";
		List<MembershipInfo> list = membershipInfoDao.find(hql, empNo);
		if (list != null && list.size() > 0) {
			MembershipInfo info = list.get(0);
			if (info.getIsMember().equals("1")) {
				bo = true;
			}
		}
		return bo;
	}

	public void saveDues(Salary salary) {
		boolean bo = checkMember(salary.getEmpNo());
		if (bo) {
			MembershipDues dues = new MembershipDues();
			dues.setDeptId(salary.getDeptId());
			dues.setDeptName(salary.getDeptName());
			dues.setEmpId(salary.getEmpId());
			dues.setEmpName(salary.getEmpName());
			dues.setEmpNo(salary.getEmpNo());
			dues.setRecId(com.supporter.util.UUIDHex.newId());
			String yearMonth = salary.getSalaryMonth();
			String newYearMonth = "";
			String[] array = yearMonth.split("年");
			if (array != null && array.length > 0) {
				newYearMonth = array[0] + "-";
				dues.setDbYear(array[0]);
			}
			String[] array2 = array[1].split("月");
			if (array2 != null && array2.length > 0) {
				if (array2[0].length() > 1) {
					newYearMonth += array2[0];
					dues.setDbMonth(array2[0]);
				} else {
					newYearMonth += "0" + array2[0];
					dues.setDbMonth("0" + array2[0]);
				}

			}
			//getDuesBase(salary, dues);	
			
			//判断是工资还是奖金（如果是工资就根据是否是党员计算党费）
			if(salary.getXueLiZhiCheng()!=0||salary.getGongLing()!=0||salary.getSiLing()!=0||salary.getGangWeiGongZi()!=0){
				//计算党费			
				getDuesBase(salary, dues);
				dues.setYearMonth(newYearMonth);
				this.membershipDuesDao.save(dues);
			}

		}
	}

	public void saveDues(String entityName, String date) {
		String hql = "from " + entityName + " where salaryMonth = ? ";
		List<Salary> list = this.salaryDao.find(hql, date);
		if (list != null && list.size() > 0) {
			for (Salary salary : list) {
				saveDues(salary);
			}
		}
	}

	/**
	 * 计算党费
	 * @param salary
	 * @param dues
	 */
	private void getDuesBase(Salary salary, MembershipDues dues) {
		BigDecimal dfjs = new BigDecimal(0.00);//党费基数
		BigDecimal dfyje = new BigDecimal(0.00);//党费应缴额
		
		//党费基数 = 岗位工资 + 基本工资 + 奖金
		dfjs = new BigDecimal(salary.getGangWeiGongZi())//岗位工资
				.add(new BigDecimal(salary.getXueLiZhiCheng()))//基本工资
				.add(new BigDecimal(salary.getYuZhiJiangJin()));//奖金
		//党费基数 = 党费基数 - 养老保险 - 失业保险 - 企业年金 - 个人所得税 - 医疗保险 - 住房公积金
		dfjs = dfjs.subtract(new BigDecimal(salary.getYangLaoBaoXian()))//养老保险
				.subtract(new BigDecimal(salary.getShiYeBaoXian()))//失业保险
				.subtract(new BigDecimal(salary.getKouBuChongYangLao()))//企业年金
				.subtract(new BigDecimal(salary.getDaiKouShui()))//个人所得税
				.subtract(new BigDecimal(salary.getYiLiaoBaoXian()))//医疗保险
				.subtract(new BigDecimal(salary.getZhuFangGongJiJin()));//住房公积金
		
		//计算党费应缴额
		if (dfjs.doubleValue() <= 3000) {
			dfyje = dfjs.multiply(new BigDecimal(0.005));
		}else if (dfjs.doubleValue() > 3000 & dfjs.doubleValue() <= 5000) {
			dfyje = dfjs.multiply(new BigDecimal(0.01));
		}else if (dfjs.doubleValue() > 5000 & dfjs.doubleValue() <= 10000) {
			dfyje = dfjs.multiply(new BigDecimal(0.015));
		}else if (dfjs.doubleValue() > 10000) {
			dfyje = dfjs.multiply(new BigDecimal(0.02));
		}

		dues.setDuesBase(dfjs.doubleValue());//设置党费基数
		dues.setPayableAmount(dfyje.doubleValue());//设置党费应缴额
	}

}
