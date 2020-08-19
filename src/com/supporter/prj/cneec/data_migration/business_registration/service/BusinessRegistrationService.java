package com.supporter.prj.cneec.data_migration.business_registration.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.data_migration.business_registration.dao.BusinessRegistrationDao;
import com.supporter.prj.cneec.data_migration.business_registration.entity.BusinessRegistration;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

@Service
public class BusinessRegistrationService {
	@Autowired
	private BusinessRegistrationDao codeDao;
	/**
	 * 根据主键获取信息.
	 * 
	 * @param codeId
	 *            主键
	 * @return WmsBusinessRegistration
	 */
	public BusinessRegistration get(String codeId) {
		return codeDao.get(codeId);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param codeId
	 * @return
	 */
	public BusinessRegistration initEditOrViewPage(String codeId, String docClassify,
			UserProfile user) {
		if (StringUtils.isBlank(codeId)) {// 新建
			BusinessRegistration code = new BusinessRegistration();
			code.setBusinessRegistrationId(com.supporter.util.UUIDHex.newId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			code.setCreatedDate(sdf.format(new Date()));
			code.setAdd(true);
			return code;
		} else {// 编辑
			BusinessRegistration code = codeDao.get(codeId);
			code.setAdd(false);
			return code;
		}
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
	public List<BusinessRegistration> getGrid(UserProfile user, JqGrid jqGrid, BusinessRegistration code) {
		return codeDao.findPage(jqGrid, code);
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
				BusinessRegistration codeDb = this.getWmsBusinessRegistrationFromBuffer(docId);
				if (codeDb == null) {
					continue;
				}
				this.recursiveDelete(user, codeDb);
			}
			// 记录日志
			/*
			 * BusinessRegistrationUtils.saveBusinessRegistrationOperateLog(user, null,
			 * BusinessRegistration.LogOper.BusinessRegistration_DEL.getOperName(), "{delBusinessRegistrationIds : " +
			 * codeIds + "}");
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
	public BusinessRegistration saveOrUpdate(UserProfile user, BusinessRegistration code,
			Map<String, Object> valueMap) {
		BusinessRegistration ret = null;
		if (code.getAdd()) {// 新建
			if(user!=null){
				code.setCreatedById(user.getPersonId());
				code.setCreatedBy(user.getName());
			}
			this.codeDao.save(code);
			ret = code;
			// 记录日志
			/*
			 * MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
			 * MaterialCode.LogOper.MATERIALCODE_ADD.getOperName(), null);
			 */
		} else {// 编辑
			code.setModifiedById(user.getPersonId());
			code.setModifiedBy(user.getName());
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			code.setModifiedDate(sdf.format(new Date()));
			this.codeDao.update(code);
			ret = code;
			// 记录日志
			/*
			 * MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
			 * MaterialCode.LogOper.MATERIALCODE_EDIT.getOperName(), null);
			 */
		}

		return ret;

	}
	/**
	 * 递归删除
	 * 
	 * @param BusinessRegistration
	 */
	private void recursiveDelete(UserProfile user, BusinessRegistration code) {
		if (true) {// 可删除
			String codeId = code.getBusinessRegistrationId();
			this.codeDao.delete(codeId);
		}

	}

	public BusinessRegistration getWmsBusinessRegistrationFromBuffer(String codeIdOrName) {
		if (StringUtils.isBlank(codeIdOrName)) {
			return null;
		}
		BusinessRegistration code = this.get(codeIdOrName);
		return code;
	}

}
