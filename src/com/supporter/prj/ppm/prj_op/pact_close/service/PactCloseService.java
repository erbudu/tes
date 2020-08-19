package com.supporter.prj.ppm.prj_op.pact_close.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.prj_op.pact_close.dao.PactCloseDao;
import com.supporter.prj.ppm.prj_op.pact_close.entity.PactClose;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;

@Service
@Transactional(TransManager.APP)
public class PactCloseService {

	@Autowired
	private PactCloseDao dao;
	@Autowired
	private BaseInfoService baseInfoService;

	/**
	 * 根据主键获取PPM_PACT_CLOSE.
	 * @param id 主键
	 * @return PactClose
	 */
	public PactClose get(String id) {
		return dao.get(id);
	}

	/**
	 * 分页表格展示数据.
	 * @param user 用户信息
	 * @param jqGrid jqgrid对象
	 * @return JqGrid
	 */
	public List< PactClose > getGrid(UserProfile user, JqGrid jqGrid, PactClose pactClose) {
		return dao.findPage(jqGrid, pactClose);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * @param id
	 * @return
	 */
	public PactClose initEditOrViewPage(String id, String prjId, UserProfile user) {
		if (StringUtils.isBlank(id)) {// 新建
			PactClose entity = new PactClose();
			entity.setId(com.supporter.util.UUIDHex.newId());
			entity.setIsNew(true);
			entity.setStatus(PactClose.StatusCodeTable.DRAFT);
			entity.setCreatorId(user.getPersonId());
			entity.setCreator(user.getName());
			entity.setCreationDate(new Date());
			// 若角色存在所属部门，则设置部门信息
			if (user.getDept() != null) {
				entity.setDeptId(user.getDeptId());
				entity.setDeptName(user.getDept().getName());
			}
			entity.setContactId(user.getPersonId());
			entity.setContactName(user.getName());
			// 获取左侧项目信息栏选中项目的信息
			Prj prj = baseInfoService.PrjInformation(prjId);
			entity.setPrjId(prj.getPrjId());
			entity.setPrjNameZh(prj.getPrjCName());
			entity.setPrjNameEn(prj.getPrjEName());
			return entity;
		} else {// 编辑
			PactClose entity = dao.get(id);
			entity.setIsNew(false);
			entity.setModifierId(user.getPersonId());
			entity.setModifier(user.getName());
			entity.setModificationDate(new Date());
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * @param user 用户信息
	 * @param pactClose 实体类
	 * @return
	 */
	public PactClose saveOrUpdate(UserProfile user, PactClose entity) {
		if (entity == null) {
			return null;
		}
		if (entity.getIsNew()) {
			entity.setIsNew(false);
			dao.save(entity);
		} else {
			dao.update(entity);
		}
		return entity;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param entity 实体类
	 * @return
	 */
	public PactClose update(PactClose entity) {
		if (entity == null) {
			return entity;
		}
		dao.update(entity);
		return entity;
	}

	/**
	 * 删除
	 * @param user 用户信息
	 * @param id主键
	 */
	public void delete(UserProfile user, String id) {
		if (StringUtils.isNotBlank(id)) {
			this.dao.delete(id);
		}
	}

	/**
	 * 提交操作
	 * @param id 主键id
	 * @param reportIds 合同及协议报审id
	 */
	public void valid(String id, String reportIds, UserProfile user) {
		if (StringUtils.isNotBlank(id)) {
			PactClose entity = dao.get(id);
			if (entity != null) {
				// 设置提交信息
				entity.setSubmitter(user.getName());
				entity.setSubmitterId(user.getPersonId());
				entity.setSubmitDate(new Date());
			}
		}
	}


}

