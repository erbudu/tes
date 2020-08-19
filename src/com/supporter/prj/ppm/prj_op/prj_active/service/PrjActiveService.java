package com.supporter.prj.ppm.prj_op.prj_active.service;

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
import com.supporter.prj.ppm.prj_op.prj_active.dao.PrjActiveDao;
import com.supporter.prj.ppm.prj_op.prj_active.entity.PrjActive;
import com.supporter.prj.ppm.prj_org.base_info.common.PrjInfo;
import com.supporter.prj.ppm.prj_org.base_info.constant.BaseInfoConstant;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;

@Service
@Transactional(TransManager.APP)
public class PrjActiveService {

	@Autowired
	private PrjActiveDao dao;
	@Autowired
	private BaseInfoService baseInfoService;
	@Autowired
	PrjInfo prjInfo;

	/**
	 * 获取项目激活列表
	 * @param jqGrid 列表
	 * @param prjActive 实体对象
	 * @param user 当前登录用户
	 * @return 项目激活列表
	 */
	public List<PrjActive> getGrid(JqGrid jqGrid, PrjActive prjActive, UserProfile user) {
		return dao.findPage(jqGrid, prjActive, user);
	}

	/**
	 *  进入新建或编辑或查看页面需要加载的信息.
	 * @param id 主键
	 * @param user 当前登录用户
	 * @return 实体对象
	 */
	public PrjActive initEditOrViewPage(String id, String prjId, UserProfile user) {
		PrjActive prjActive = new PrjActive();
		if (StringUtils.isBlank(id)) {
			prjActive.setId(com.supporter.util.UUIDHex.newId());
			prjActive.setIsNew(true);
			prjActive.setStatus(PrjActive.StatusCodeTable.DRAFT);
			prjActive.setCreatorId(user.getPersonId());
			prjActive.setCreator(user.getName());
			prjActive.setCreationDate(new Date());
			// 若角色存在所属部门，则设置部门信息
			if (user.getDept() != null) {
				prjActive.setDeptId(user.getDeptId());
				prjActive.setDeptName(user.getDept().getName());
			}
			prjActive.setContactNameId(user.getPersonId());
			prjActive.setContactName(user.getName());
			// 获取左侧项目信息栏选中项目的信息
			Prj prj = baseInfoService.PrjInformation(prjId);
			prjActive.setPrjId(prj.getPrjId());
			prjActive.setPrjNameZh(prj.getPrjCName());
			prjActive.setPrjNameEn(prj.getPrjEName());
			return prjActive;
		} else {
			prjActive = dao.get(id);
			prjActive.setIsNew(false);
			return prjActive;
		}
	}

	/**
	 * 保存或更新
	 * @param prjActive 实体对象
	 * @return 操作结果
	 */
	public PrjActive saveOrUpdate(PrjActive prjActive, UserProfile user) {
		if (prjActive == null) {
			return null;
		}
		PrjActive ret = null;
		if (prjActive.getIsNew()) {
			prjActive.setIsNew(false);
			dao.save(prjActive);
			ret = prjActive;
		} else {
			dao.update(prjActive);
			ret = prjActive;
		}
		return ret;
	}

	/**
	 * 删除
	 * @param id 主键
	 * @return	操作结果
	 */
	public void delete(String id) {
		PrjActive prjActive = dao.get(id);
		dao.delete(prjActive);
	}

	/**
	 * 根据主键获取实体对象
	 * @param id 模块主键
	 * @return 实体对象
	 */
	public PrjActive get(String id) {
		return dao.get(id);
	}

	/**
	 * 流程处理类更新
	 * @param prjActive
	 * @return
	 */
	public PrjActive update(PrjActive prjActive) {
		if (prjActive != null) {
			dao.update(prjActive);
		}
		return prjActive;
	}

	/**
	 * 检查当前项目开发状态
	 * @param prjId 项目id
	 */
	// public boolean checkPrjStatus(String prjId) {
	// Prj prj = prjInfo.PrjInformation(prjId);
	// if (prj != null) {
	// int prjStatus = prj.getPrjActiveStatus();
	// if (prjStatus == BaseInfoConstant.CLOSE) {
	// return true;
	// }
	// }
	// return false;
	// }

	/**
	 * 检测是否满足新建条件
	 * @param prjId 项目id
	 * @return true-可以新建, false-不可以新建
	 */
	public boolean checkNewCondition(String prjId) {
		// 检查该项目是否正在激活(草稿/审批中)
		List<PrjActive> list = dao.getActingPrj(prjId);
		if (CollectionUtils.isEmpty(list)) {
			return true;
		}
		return false;
	}


	/**
	 * 提交时设置项目激活状态为激活
	 * @param prjId 项目id
	 */
	public void updatePrjActiveStatus(String prjId) {
		prjInfo.updatePrjActiveStauts(prjId, BaseInfoConstant.ACTIVE);
	}


}
