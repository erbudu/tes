package com.supporter.prj.ppm.ecc.group_review.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.ppm.ecc.group_review.entity.EccGroupReview;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;
import com.supporter.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.ecc.group_review.dao.EccGroupRevieConDao;
import com.supporter.prj.ppm.ecc.group_review.entity.EccGroupRevieCon;

/**
 * @Title: Service
 * @Description: 集团评审结论.
 * @author YAN
 * @date 2019-08-16 18:46:30
 * @version V1.0
 */
@Service
public class EccGroupRevieConService {

	@Autowired
	private EccGroupRevieConDao eccGroupRevieConDao;
	@Autowired
	private EccGroupReviewService groupReviewService;
	@Autowired
	private BaseInfoService prjService;

	/**
	 * 根据主键获取集团评审结论.
	 * 
	 * @param groupEccRvConId 主键
	 * @return EccGroupRevieCon
	 */
	public EccGroupRevieCon get(String groupEccRvConId) {
		return eccGroupRevieConDao.get(groupEccRvConId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< EccGroupRevieCon > getGrid(UserProfile user, JqGrid jqGrid, EccGroupRevieCon eccGroupRevieCon) {
		return eccGroupRevieConDao.findPage(jqGrid, eccGroupRevieCon);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param groupEccRvConId
	 * @return
	 */
	public EccGroupRevieCon initEditOrViewPage(String prjId,String eccId) {
		if (StringUtils.isBlank(prjId)||StringUtils.isBlank(eccId)) {// 新建
			EccGroupRevieCon entity = new EccGroupRevieCon();
			return entity;
		} else {// 编辑
			EccGroupRevieCon entity = getByPrjId(prjId,eccId);
			return entity;
		}
	}
	public EccGroupRevieCon getByPrjId(String prjId,String eccId){
		List<EccGroupRevieCon> entitys= this.eccGroupRevieConDao.findBy("eccId",eccId);
		EccGroupRevieCon entity = new EccGroupRevieCon();
		if (entitys!=null&&entitys.size()>0){
			entity = entitys.get(0);
		}else{
			entity.setEccId(eccId);
			entity.setGroupEccRvConId(com.supporter.util.UUIDHex.newId());
		}
		entity = initPrjInfo(prjId,entity);
		return entity;
	}
	//初始化项目信息
	private EccGroupRevieCon initPrjInfo(String prjId, EccGroupRevieCon entity) {
		Prj prj = this.prjService.initBaseInfoView(prjId);
		if (prj!=null){
			entity.setPrjCName(prj.getPrjCName());
			entity.setPrjEName(prj.getPrjEName());
			entity.setPrjNo(prj.getPrjNo());
		}
		return entity;
	}
	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param eccGroupRevieCon 实体类
	 * @return
	 */
	public EccGroupRevieCon saveOrUpdate(UserProfile user, EccGroupRevieCon eccGroupRevieCon,Integer status) {
		//如果是生效
		if (!CommonUtil.isNullOrEmpty(status)&&status == 2){
			this.groupReviewService.effect(eccGroupRevieCon.getGroupEccId());
		}
		if (StringUtils.isBlank(eccGroupRevieCon.getGroupEccRvConId())) {// 新建
			return this.save(user, eccGroupRevieCon);
		} else {// 编辑
			return this.update(user, eccGroupRevieCon);
		}


	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param eccGroupRevieCon 实体类
	 * @return
	 */
	private EccGroupRevieCon save(UserProfile user, EccGroupRevieCon eccGroupRevieCon) {
		this.eccGroupRevieConDao.save(eccGroupRevieCon);
		return eccGroupRevieCon;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param eccGroupRevieCon 实体类
	 * @return
	 */
	private EccGroupRevieCon update(UserProfile user, EccGroupRevieCon eccGroupRevieCon) {
		EccGroupRevieCon eccGroupRevieConDb = this.eccGroupRevieConDao.get(eccGroupRevieCon.getGroupEccRvConId());
		if(eccGroupRevieConDb == null) {
			return this.save(user, eccGroupRevieCon);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(eccGroupRevieCon, eccGroupRevieConDb);
			this.eccGroupRevieConDao.update(eccGroupRevieConDb);
			return eccGroupRevieConDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param groupEccRvConIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String groupEccRvConIds) {
		if(StringUtils.isNotBlank(groupEccRvConIds)) {
			for(String groupEccRvConId : groupEccRvConIds.split(",")) {
				EccGroupRevieCon eccGroupRevieConDb = this.eccGroupRevieConDao.get(groupEccRvConId);
				this.eccGroupRevieConDao.delete(groupEccRvConId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param groupEccRvConId
	 * @param groupEccRvConName
	 * @return
	 */
	public boolean nameIsValid(String groupEccRvConId,String groupEccRvConName) {
		return this.eccGroupRevieConDao.nameIsValid( groupEccRvConId, groupEccRvConName);
	}

}

