package com.supporter.prj.ud.func.service;

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
import com.supporter.prj.ud.func.dao.UdFuncPageDao;
import com.supporter.prj.ud.func.entity.UdFuncPage;

@Service
public class UdFuncPageService {
	@Autowired
	private UdFuncPageDao codeDao;

	/**
	 * 根据主键获取信息.
	 * 
	 * @param codeId
	 *            主键
	 * @return WmsUdFuncPage
	 */
	public UdFuncPage get(String codeId) {
		return codeDao.get(codeId);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param codeId
	 * @return
	 */
	public UdFuncPage initEditOrViewPage(String codeId,
			UserProfile user) {
		if (StringUtils.isBlank(codeId)) {// 新建
			UdFuncPage code = new UdFuncPage();
			code.setPageId(com.supporter.util.UUIDHex.newId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			code.setAdd(true);
			if (user != null) {
				code.setCreatedBy(user.getPersonId());
				Dept dept = user.getDept();
				if (dept != null) {
					code.setDeptId(user.getDeptId());
				} else {
					code.setDeptId("1");
				}
			}
			return code;
		} else {// 编辑
			UdFuncPage code = codeDao.get(codeId);
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
	public List<UdFuncPage> getGrid(UserProfile user, JqGrid jqGrid,
			UdFuncPage code) {
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
				UdFuncPage codeDb = this
						.getWmsUdFuncPageFromBuffer(docId);
				if (codeDb == null) {
					continue;
				}
				this.recursiveDelete(user, codeDb);
			}
			// 记录日志
			/*
			 * UdFuncPageUtils.saveUdFuncPageOperateLog(user,
			 * null,
			 * UdFuncPage.LogOper.UdFuncPage_DEL.getOperName(),
			 * "{delUdFuncPageIds : " + codeIds + "}");
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
	public UdFuncPage saveOrUpdate(UserProfile user,
			UdFuncPage code, Map<String, Object> valueMap) {
		UdFuncPage ret = null;
		if (code.getAdd()) {// 新建
			this.codeDao.save(code);
			ret = code;
			// 记录日志
			/*
			 * MaterialCodeUtils.saveMaterialCodeOperateLog(user, code,
			 * MaterialCode.LogOper.MATERIALCODE_ADD.getOperName(), null);
			 */
		} else {// 编辑
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
	 * @param UdFuncPage
	 */
	private void recursiveDelete(UserProfile user, UdFuncPage code) {
		if (true) {// 可删除
			String codeId = code.getPageId();
			this.codeDao.delete(codeId);
		}

	}

	public UdFuncPage getWmsUdFuncPageFromBuffer(
			String codeIdOrName) {
		if (StringUtils.isBlank(codeIdOrName)) {
			return null;
		}
		UdFuncPage code = this.get(codeIdOrName);
		return code;
	}

}
