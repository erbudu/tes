package com.supporter.prj.eip.code_share.code.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.cxf.JsonDateValueProcessor;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.supporter.prj.eip_service.common.entity.OperResult;

import org.apache.commons.lang.StringUtils;
import com.supporter.prj.eip.code_share.code.dao.EntityProjectDao;
import com.supporter.prj.eip.code_share.code.entity.EntityProject;
import com.supporter.prj.eip.transaction.TransManager;

/**
 * @Title: Service
 * @Description: CS_ENITY_PROJECT.
 * @author Administrator
 * @date 2019-07-17 16:46:42
 * @version V1.0
 *
 */
@Service
public class EntityProjectService {
	@Autowired
	private EntityProjectDao projectDao;
	
	private static final int ERROR_RESULT_1 = 1;
	private static final int ERROR_RESULT_2 = 2;
	private static final int ERROR_RESULT_3 = 3;
	
	/**
	 * 推送项目信息(此处不用加事务)
	 * @param json 项目信息JSON串
	 * @return 如果成功返回0, 其他为失败
	 */
	public int sendPrj(String json) {
		if (StringUtils.isBlank(json)) {
			return ERROR_RESULT_1;
		}
		/**
		 * JSON结构说明：
			{
			prjId:"项目ID",
			prjName:"项目名称",
			prjNo:"项目编号",
			prjLib: "项目库", //项目库:ENGINEE-工程项目; TRADE-贸易项目
			sourceFrom:"数据来源业务系统"
			}
		 */

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		JSONObject jsonObj = JSONObject.fromObject(json, jsonConfig);
		
		String prjLib = CommonUtil.trim(jsonObj.getString("prjLib"));
		if (StringUtils.isBlank(prjLib)) {
			return ERROR_RESULT_2;
		}
		String prjId = CommonUtil.trim(jsonObj.getString("prjId"));
		if (StringUtils.isBlank(prjId)) {
			return ERROR_RESULT_3;
		}
		
		String prjRecId = prjLib + "_" + prjId;
		EntityProject entity = projectDao.get(prjRecId);
		
		boolean isNew = false;
		if (entity == null) {
			isNew = true;
			entity = new EntityProject();
			entity.setPrjRecId(prjRecId);
		}
		entity.setPrjId(prjId);
		entity.setPrjLib(prjLib);
		entity.setPrjName(jsonObj.getString("prjName"));
		entity.setPrjNo(jsonObj.getString("prjNo"));
		entity.setSourceFrom(jsonObj.getString("sourceFrom"));
		if (isNew) {
			projectDao.save(entity);
		} else {
			projectDao.update(entity);
		}
		
		return 0;
	}

	/**
	 * 根据主键获取CS_ENITY_PROJECT.
	 * 
	 * @param prjRecId
	 *            主键
	 * @return EnityProject
	 */
	public EntityProject get(String prjRecId) {
		return projectDao.get(prjRecId);
	}
	
	/**
	 * 根据项目库和项目ID获取项目对象
	 * @param prjLib 项目库
	 * @param prjId 项目ID
	 * @return EntityProject
	 */
	public EntityProject getPrjInLibById(String prjLib, String prjId) {
		String recId = CommonUtil.trim(prjLib)  + "_" + CommonUtil.trim(prjId);
		EntityProject prj = this.get(recId);
		return prj;
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user
	 * @param jqGrid
	 * @param enityProject
	 * @return
	 */
	public List<EntityProject> getGrid(UserProfile user, JqGrid jqGrid, EntityProject enityProject) {
		return projectDao.findPage(user, jqGrid, enityProject);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param prjRecId
	 *            项目记录ID
	 * @return EnityProject
	 */
	public EntityProject initEditOrViewPage(String prjRecId) {
		if (StringUtils.isBlank(prjRecId)) {
			EntityProject entity = new EntityProject();
			return entity;
		} else {
			EntityProject entity = projectDao.get(prjRecId);
			if (entity != null) {

			}
			return entity;
		}
	}

	/**
	 * 保存.
	 * 
	 * @param user
	 *            用户信息
	 * @param enityProject
	 *            实体类
	 * @return
	 */
	@Transactional(transactionManager = TransManager.APP, rollbackFor = Exception.class)
	public OperResult<EntityProject> saveOrUpdate(UserProfile user, EntityProject enityProject) {
		// 主键
		String prjRecId = enityProject.getPrjRecId();
		// 保存数据库
		this.projectDao.save(enityProject);
		return OperResult.succeed("success", null, enityProject);
	}

	/**
	 * 删除.
	 * 
	 * @param user 用户信息
	 * @param prjRecIds 主键集合，多个以逗号分隔
	 */
	@Transactional(transactionManager = TransManager.APP, rollbackFor = Exception.class)
	public OperResult<?> delete(UserProfile user, String prjRecIds) {
		if (StringUtils.isNotBlank(prjRecIds)) {
			for (String prjRecId : prjRecIds.split(",")) {
				EntityProject enityProjectDb = this.projectDao.get(prjRecId);
				if (enityProjectDb == null) {
					continue;
				}
				// 删除记录
				this.projectDao.delete(prjRecId);
			}
		}
		return OperResult.succeed("success", null, null);
	}

}
