package com.supporter.prj.ppm.prj_op.prj_close.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.contract.pact.beian.service.ContractPactBeianService;
import com.supporter.prj.ppm.contract.pact.report.entity.ContractPactReport;
import com.supporter.prj.ppm.contract.pact.report.service.ContractPactReportService;
import com.supporter.prj.ppm.prj_op.prj_close.dao.PrjCloseDao;
import com.supporter.prj.ppm.prj_op.prj_close.entity.PrjClose;
import com.supporter.prj.ppm.prj_org.base_info.common.PrjInfo;
import com.supporter.prj.ppm.prj_org.base_info.constant.BaseInfoConstant;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;

@Service
@Transactional(TransManager.APP)
public class PrjCloseService {

	@Autowired
	private PrjCloseDao dao;
	@Autowired
	private BaseInfoService baseInfoService;
	@Autowired
	private PrjInfo prjInfo;
	@Autowired
	private ContractPactBeianService pactBeianService;
	@Autowired
	private ContractPactReportService pactReportService;

	/**
	 * 获取项目激活列表
	 * @param jqGrid 列表
	 * @param PrjClose 实体对象
	 * @param user 当前登录用户
	 * @return 项目激活列表
	 */
	public List<PrjClose> getGrid(JqGrid jqGrid, PrjClose prjClose, UserProfile user) {
		return dao.findPage(jqGrid, prjClose, user);
	}

	/**
	 *  进入新建或编辑或查看页面需要加载的信息.
	 * @param id 主键
	 * @param user 当前登录用户
	 * @return 实体对象
	 */
	public PrjClose initEditOrViewPage(String id, String prjId, UserProfile user) {
		PrjClose prjClose = new PrjClose();
		if (StringUtils.isBlank(id)) {
			prjClose.setId(com.supporter.util.UUIDHex.newId());
			prjClose.setIsNew(true);
			prjClose.setStatus(PrjClose.StatusCodeTable.DRAFT);
			prjClose.setCreatorId(user.getPersonId());
			prjClose.setCreator(user.getName());
			prjClose.setCreationDate(new Date());
			// 若角色存在所属部门，则设置部门信息
			if (user.getDept() != null) {
				prjClose.setDeptId(user.getDeptId());
				prjClose.setDeptName(user.getDept().getName());
			}
			prjClose.setContactNameId(user.getPersonId());
			prjClose.setContactName(user.getName());
			// 获取左侧项目信息栏选中项目的信息
			Prj prj = baseInfoService.PrjInformation(prjId);
			prjClose.setPrjId(prj.getPrjId());
			prjClose.setPrjNameZh(prj.getPrjCName());
			prjClose.setPrjNameEn(prj.getPrjEName());

			// 判断该项目是否签订过合同及协议
			List<String> repIdList = pactBeianService.getBeianPassRepId(prjId);
			if (CollectionUtils.isEmpty(repIdList)) {// 未签订过合同及协议
				prjClose.setHasSigned(PrjClose.NOT_SIGNED);
			} else {
				prjClose.setHasSigned(PrjClose.HAS_SIGNED);
			}
			return prjClose;
		} else {
			prjClose = dao.get(id);
			prjClose.setIsNew(false);
			prjClose.setModifierId(user.getPersonId());
			prjClose.setModifier(user.getName());
			prjClose.setModificationDate(new Date());
			return prjClose;
		}
	}

	/**
	 * 保存或更新
	 * @param PrjClose 实体对象
	 * @return 操作结果
	 */
	public PrjClose saveOrUpdate(PrjClose prjClose, UserProfile user) {
		if (prjClose == null) {
			return null;
		}
		PrjClose ret = null;
		if (prjClose.getIsNew()) {
			prjClose.setIsNew(false);
			dao.save(prjClose);
			ret = prjClose;
		} else {
			dao.update(prjClose);
			ret = prjClose;
		}
		return ret;
	}

	/**
	 * 删除
	 * @param id 主键
	 * @return	操作结果
	 */
	public void delete(String id) {
		PrjClose prjClose = dao.get(id);
		dao.delete(prjClose);
	}

	/**
	* 根据主键获取实体对象
	* @param id 模块主键
	* @return 实体对象
	*/
	public PrjClose get(String id) {
		return dao.get(id);
	}

	/**
	 * 流程处理类更新
	 * @param PrjClose
	 * @return
	 */
	public PrjClose update(PrjClose prjClose) {
		if (prjClose == null) {
			return prjClose;
		}
		dao.update(prjClose);
		return prjClose;
	}

	/**
	 *新建时检查当前项目是否可以进行关闭
	 * @param prjId 项目id
	 * @return 检查结果
	 */
	// public boolean checkPrjStatus(String prjId) {
	// // 检查项目是否处于激活状态
	// Prj prj = prjInfo.PrjInformation(prjId);
	// if (prj != null) {
	// int prjStatus = prj.getPrjActiveStatus();
	// if (prjStatus == BaseInfoConstant.ACTIVE) {
	// return true;
	// }
	// }
	// return false;
	// }

	public int checkPrjStatus(String prjId) {
		// 检查项目是否处于激活状态
		Prj prj = prjInfo.PrjInformation(prjId);
		// 设置返回值为一个任意无效值
		int prjStatus = 100;
		if (prj != null) {
			prjStatus = prj.getPrjActiveStatus();
		}
		return prjStatus;
	}

	/**
	 * 检测是否满足新建条件
	 * @param prjId 项目id
	 * @return true-可以新建, false-不可以新建
	 */
	public boolean checkNewCondition(String prjId) {
		// 检查该项目是否正在关闭(草稿/审批中)
		List<PrjClose> list = dao.getClosingPrj(prjId);
		if (CollectionUtils.isEmpty(list)) {
			return true;
		}
		return false;
	}

	/**
	 * 设置项目激活状态为关闭
	 * @param prjId 项目id
	 */
	public void updatePrjActiveStatus(String prjId) {
		prjInfo.updatePrjActiveStauts(prjId, BaseInfoConstant.CLOSE);
	}

	/**
	 * 检查项目下是否存在生效的合同
	 * @param prjId 项目id
	 * @returns 操作结果 true-有，false-没有
	 */
	public boolean checkExistEffectPact(String prjId) {
		// 获取某项目下所有已备案完成的报审对象，且该报审处于生效(审批完成)状态
		List<ContractPactReport> reportList = pactReportService.getBeianPassReports(prjId);
		if (CollectionUtils.isNotEmpty(reportList)) {
			return true;
		}
		return false;
	}


}
