package com.supporter.prj.ppm.ecc.wg_review.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.ecc.wg_review.dao.EccWgRevieConDao;
import com.supporter.prj.ppm.ecc.wg_review.entity.EccWgRevieCon;

/**
 * @Title: Service
 * @Description: 出口管制工作组评审结论.
 * @author YAN
 * @date 2019-08-16 18:44:49
 * @version V1.0
 */
@Service
public class EccWgRevieConService {

	@Autowired
	private EccWgRevieConDao eccWgRevieConDao;

	/**
	 * 根据主键获取出口管制工作组评审结论.
	 * 
	 * @param wgEccRvConId 主键
	 * @return EccWgRevieCon
	 */
	public EccWgRevieCon get(String wgEccRvConId) {
		return eccWgRevieConDao.get(wgEccRvConId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< EccWgRevieCon > getGrid(UserProfile user, JqGrid jqGrid, EccWgRevieCon eccWgRevieCon) {
		return eccWgRevieConDao.findPage(jqGrid, eccWgRevieCon);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param wgEccRvConId
	 * @return
	 */
	public EccWgRevieCon initEditOrViewPage(String eccId) {
		if (StringUtils.isBlank(eccId)) {// 新建
			EccWgRevieCon entity = new EccWgRevieCon();
			return entity;
		} else {// 编辑
			EccWgRevieCon entity = getByEccId(eccId);
			return entity;
		}
	}

	public EccWgRevieCon getByEccId(String eccId){
		//
		List<EccWgRevieCon> cons = this.eccWgRevieConDao.findBy("eccId",eccId);
		if (cons!=null&&cons.size()>0){
			return cons.get(0);
		}else{
			return  new EccWgRevieCon();
		}
	}
	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param eccWgRevieCon 实体类
	 * @return
	 */
	public EccWgRevieCon saveOrUpdate(UserProfile user, EccWgRevieCon eccWgRevieCon) {
		if (StringUtils.isBlank(eccWgRevieCon.getWgEccRvConId())) {// 新建
			return this.save(user, eccWgRevieCon);
		} else {// 编辑
			return this.update(user, eccWgRevieCon);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param eccWgRevieCon 实体类
	 * @return
	 */
	private EccWgRevieCon save(UserProfile user, EccWgRevieCon eccWgRevieCon) {
		this.eccWgRevieConDao.save(eccWgRevieCon);
		return eccWgRevieCon;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param eccWgRevieCon 实体类
	 * @return
	 */
	private EccWgRevieCon update(UserProfile user, EccWgRevieCon eccWgRevieCon) {
		EccWgRevieCon eccWgRevieConDb = this.eccWgRevieConDao.get(eccWgRevieCon.getWgEccRvConId());
		if(eccWgRevieConDb == null) {
			return this.save(user, eccWgRevieCon);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(eccWgRevieCon, eccWgRevieConDb);
			this.eccWgRevieConDao.update(eccWgRevieConDb);
			return eccWgRevieConDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param wgEccRvConIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String wgEccRvConIds) {
		if(StringUtils.isNotBlank(wgEccRvConIds)) {
			for(String wgEccRvConId : wgEccRvConIds.split(",")) {
				EccWgRevieCon eccWgRevieConDb = this.eccWgRevieConDao.get(wgEccRvConId);
				this.eccWgRevieConDao.delete(wgEccRvConId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param wgEccRvConId
	 * @param wgEccRvConName
	 * @return
	 */
	public boolean nameIsValid(String wgEccRvConId,String wgEccRvConName) {
		return this.eccWgRevieConDao.nameIsValid( wgEccRvConId, wgEccRvConName);
	}

}

