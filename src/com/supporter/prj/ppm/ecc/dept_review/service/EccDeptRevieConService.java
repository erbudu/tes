package com.supporter.prj.ppm.ecc.dept_review.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.ppm.ecc.dept_review.entity.EccDeptReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.ecc.dept_review.dao.EccDeptRevieConDao;
import com.supporter.prj.ppm.ecc.dept_review.entity.EccDeptRevieCon;

/**
 * @Title: Service
 * @Description: 部门出口管制结论.
 * @author YAN
 * @date 2019-08-16 18:43:21
 * @version V1.0
 */
@Service
public class EccDeptRevieConService {

	@Autowired
	private EccDeptRevieConDao eccDeptRevieConDao;
	@Autowired
	private EccDeptReviewService deptReviewService;

	/**
	 * 根据主键获取部门出口管制结论.
	 * 
	 * @param deptEccRvConId 主键
	 * @return EccDeptRevieCon
	 */
	public EccDeptRevieCon get(String deptEccRvConId) {
		return eccDeptRevieConDao.get(deptEccRvConId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGrid jqgrid请求对象
	 * @param eccDeptRevieCon 多个逗号分隔
	 * @return JqGrid
	 */
	public List< EccDeptRevieCon > getGrid(UserProfile user, JqGrid jqGrid, EccDeptRevieCon eccDeptRevieCon) {
		return eccDeptRevieConDao.findPage(jqGrid, eccDeptRevieCon);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param prjId
	 * @return
	 */
	public EccDeptRevieCon initEditOrViewPage(String prjId) {
		if (StringUtils.isBlank(prjId)) {// 新建
			EccDeptRevieCon entity = new EccDeptRevieCon();
			return entity;
		} else {// 编辑
			EccDeptRevieCon entity = initEditOrViewPageByPrjId(prjId);
			return entity;
		}
	}
	public EccDeptRevieCon initEditOrViewPageByPrjId(String prjId) {
		EccDeptReview eccDeptReview =this.deptReviewService.getByPrjId(prjId);
		EccDeptRevieCon con = initEditOrViewPageByDeptEccId(eccDeptReview.getDeptEccId());
		return con;
	}
	public EccDeptRevieCon initEditOrViewPageByDeptEccId(String deptEccId) {
		List <EccDeptRevieCon> cons = this.eccDeptRevieConDao.findBy("deptEccId",deptEccId);
		if (cons!=null&&cons.size()>0){
			return  cons.get(0);
		}else{
			EccDeptRevieCon entity = new EccDeptRevieCon();
			entity.setDeptEccId(deptEccId);
			return entity;
		}
	}
	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param eccDeptRevieCon 实体类
	 * @return
	 */
	public EccDeptRevieCon saveOrUpdate(UserProfile user, EccDeptRevieCon eccDeptRevieCon) {
		if (StringUtils.isBlank(eccDeptRevieCon.getDeptEccRvConId())) {// 新建
			return this.save(user, eccDeptRevieCon);
		} else {// 编辑
			return this.update(user, eccDeptRevieCon);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param eccDeptRevieCon 实体类
	 * @return
	 */
	private EccDeptRevieCon save(UserProfile user, EccDeptRevieCon eccDeptRevieCon) {
		this.eccDeptRevieConDao.save(eccDeptRevieCon);
		return eccDeptRevieCon;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param eccDeptRevieCon 实体类
	 * @return
	 */
	private EccDeptRevieCon update(UserProfile user, EccDeptRevieCon eccDeptRevieCon) {
		EccDeptRevieCon eccDeptRevieConDb = this.eccDeptRevieConDao.get(eccDeptRevieCon.getDeptEccRvConId());
		if(eccDeptRevieConDb == null) {
			return this.save(user, eccDeptRevieCon);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(eccDeptRevieCon, eccDeptRevieConDb);
			this.eccDeptRevieConDao.update(eccDeptRevieConDb);
			return eccDeptRevieConDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param deptEccRvConIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String deptEccRvConIds) {
		if(StringUtils.isNotBlank(deptEccRvConIds)) {
			for(String deptEccRvConId : deptEccRvConIds.split(",")) {
				EccDeptRevieCon eccDeptRevieConDb = this.eccDeptRevieConDao.get(deptEccRvConId);
				this.eccDeptRevieConDao.delete(deptEccRvConId);
			}
		}
	}
	/**
	 * 删除
	 *
	 * @param user 用户信息
	 * @param deptEccId 主键集合，多个以逗号分隔
	 */
	public void deleteByDeptEccId(UserProfile user, String deptEccId) {
		if(StringUtils.isNotBlank(deptEccId)) {
			List<EccDeptRevieCon> list = this.eccDeptRevieConDao.findBy("deptEccId",deptEccId);
			if (list!=null&&list.size()>0){
				this.eccDeptRevieConDao.delete(list);
			}
		}
	}
	/**
	 * 判断名字是否重复
	 * 
	 * @param deptEccRvConId
	 * @param deptEccRvConName
	 * @return
	 */
	public boolean nameIsValid(String deptEccRvConId,String deptEccRvConName) {
		return this.eccDeptRevieConDao.nameIsValid( deptEccRvConId, deptEccRvConName);
	}

}

