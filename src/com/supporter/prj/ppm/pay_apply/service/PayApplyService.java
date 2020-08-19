package com.supporter.prj.ppm.pay_apply.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.pay_apply.dao.PayApplyDao;
import com.supporter.prj.ppm.pay_apply.entity.PayApply;
import com.supporter.prj.ppm.prj_op.prj_active.entity.PrjActive;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;

/**
 * @Title: PayApplyService
 * @Description: 业务操作类
 * @author: 
 * @date: 2018-07-13
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class PayApplyService {

	@Autowired
	private PayApplyDao payApplyDao;
	@Autowired
	BaseInfoService baseInfoService;
	/**
	 * 获取支付申请列表
	 * @param jqGrid 列表
	 * @param PayApply 实体对象
	 * @param user 当前登录用户
	 * @return 支付申请列表
	 */
	public List<PayApply> getGrid(JqGrid jqGrid, PayApply payApply, UserProfile user) {
		return payApplyDao.findPage(jqGrid, payApply, user);
	}
	
	/**
	 * 根据主键获取列表.
	 * @param Id 主键
	 * @return PayApply
	 */
	public PayApply get(String id) {
		return payApplyDao.get(id);
	}
	/**
	 * 为编辑或查看页面初始化对象
	 * @param id
	 * @param user
	 * @return
	 */
	public PayApply initEditOrViewPage(String id, UserProfile user,String prjId) {
		PayApply payApply = new PayApply();
		if (StringUtils.isBlank(id)) {
			payApply.setId(com.supporter.util.UUIDHex.newId());
			payApply.setIsNew(true);
			payApply.setStatus(PrjActive.StatusCodeTable.DRAFT);
			payApply.setCreatedById(user.getPersonId());
			payApply.setCreatedBy(user.getName());
			payApply.setCreatedDate(new Date());
			Prj prj = baseInfoService.PrjInformation(prjId);
			payApply.setPrjId(prj.getPrjId());
			payApply.setPrjNo(prj.getPrjNo());
			payApply.setPrjNameC(prj.getPrjCName());
			payApply.setPrjNameE(prj.getPrjEName());
			// payApply.setAgreConId("123");
			// payApply.setAgreConName("协议");
			payApply.setPayNo(generatePayNo());
			return payApply;
		} else {
			payApply = payApplyDao.get(id);
			payApply.setIsNew(false);
			return payApply;
		}
	}
	
	/**
	 * 支付申请编号生成规则
	 * @return 支付申请编号
	 */
	private String generatePayNo() {
		Date date = new Date();
		SimpleDateFormat regx = new SimpleDateFormat("yyyy");
		List<PayApply> list = payApplyDao.generatePayNo();
		if (CollectionUtils.isEmpty(list)) {
			return "付开字" + regx.format(date) + "第000号";
		} else {
			String no = list.get(0).getPayNo();
			int maxNo = Integer.parseInt(no.substring(no.lastIndexOf("第") + 1, no.lastIndexOf("第") + 4)) + 1;
			String maxNoStr = String.format("%03d", maxNo);
			return "付开字" + regx.format(date) + "第" + maxNoStr + "号";
		}
	}

	/**
	 * 保存或更新
	 * @param payApply 实体对象
	 * @return 操作结果
	 */
	public PayApply saveOrUpdate(PayApply payApply, UserProfile user) {
		if (user != null) {
			payApply.setModifiedBy(user.getName());
			payApply.setModifiedById(user.getPersonId());
		}
		if(StringUtils.isNotBlank(payApply.getPayCurrencyId())) {
			payApply.setPayCurrency(EIPService.getComCodeTableService()
					.getCodetableItem(payApply.getPayCurrencyId())
					.getItemValue());
		}
		if(StringUtils.isNotBlank(payApply.getFundUseId())) {
			payApply.setFundUse(EIPService.getComCodeTableService()
					.getCodetableItem(payApply.getFundUseId())
					.getItemValue());
		}
		if (payApply.getIsNew()) {
			if (user != null) {
				payApply.setCreatedBy(user.getName());
				payApply.setCreatedById(user.getPersonId());
				Dept dept = user.getDept();
				if (dept != null) {
					payApply.setDeptId(dept.getDeptId());
					payApply.setDeptName(dept.getName());
				}
			}
			payApply.setCreatedDate(new Date());
			payApply.setIsNew(false);
			payApplyDao.save(payApply);
		} else { // 设置更新时间
			payApply.setModifiedDate(new Date());
			payApplyDao.update(payApply);
		}
		return payApply;
	}
	/**
	 * 删除
	 * @param id 主键
	 * @return	操作结果
	 */
	public void delete(String id) {
		PayApply payApply = payApplyDao.get(id);
		payApplyDao.delete(payApply);
	}
	/**
	 * 流程处理类更新
	 * @param PayApply
	 * @return
	 */
	public PayApply update(PayApply payApply) {
			if (payApply==null){
				return payApply;
			}
			this.payApplyDao.update(payApply);
			return payApply;
	}
	
	/**
	 * 设置提交人等信息
	 * @param id 支付申请主键
	 * @param user 当前登录用户
	 */
	public void valid(String id, UserProfile user) {
		if (StringUtils.isNotBlank(id)) {
			PayApply apply = payApplyDao.get(id);
			apply.setSubmitterId(user.getPersonId());
			apply.setSubmitter(user.getName());
			apply.setSubmissionDate(new Date());
			payApplyDao.update(apply);
		}
	}

	/**
	 * 检查是否满足新建条件
	 * @param prjId 项目id
	 * @return 检查结果
	 */
	// public Boolean checkNewCondition(String prjId) {
	// if (StringUtils.isNotBlank(prjId)) {
	// // 检查项目是否完成项目初始登录
	// Prj prj = baseInfoService.PrjInformation(prjId);
	// if (prj != null) {
	// if (prj.getStatus() == 1 || prj.getStatus() == 2) {// 项目完成初始登录
	// return true;
	// }
	// }
	// }
	// return false;
	// }

	/**
	 * 项目是否满足款项支付条件
	 * @param prjId 项目id
	 * @returns true-满足，false-不满足
	 */
	public boolean checkPrjStatus(String prjId) {
		if (StringUtils.isNotBlank(prjId)) {
			// 检查项目是否完成项目初始登录
			Prj prj = baseInfoService.PrjInformation(prjId);
			if (prj != null) {
				if (prj.getStatus() == 1 || prj.getStatus() == 2) {// 项目完成初始登录
					return true;
				}
			}
		}
		return false;
	}

}
