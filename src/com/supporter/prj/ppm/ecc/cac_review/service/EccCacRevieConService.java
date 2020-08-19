package com.supporter.prj.ppm.ecc.cac_review.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.ppm.ecc.cac_review.entity.EccCacReview;
import com.supporter.prj.ppm.ecc.dept_review.service.EccDeptReviewService;
import com.supporter.prj.ppm.ecc.wg_review.entity.EccWgRevieCon;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.ecc.cac_review.dao.EccCacRevieConDao;
import com.supporter.prj.ppm.ecc.cac_review.entity.EccCacRevieCon;

/**
 * @Title: Service
 * @Description: 出口管制委员会评审结论.
 * @author YAN
 * @date 2019-08-16 18:45:23
 * @version V1.0
 */
@Service
public class EccCacRevieConService {

	@Autowired
	private EccCacRevieConDao eccCacRevieConDao;
	@Autowired
	private EccCacReviewService eccCacReviewService;

	/**
	 * 根据主键获取出口管制委员会评审结论.
	 * 
	 * @param cacEccRvConId 主键
	 * @return EccCacRevieCon
	 */
	public EccCacRevieCon get(String cacEccRvConId) {
		return eccCacRevieConDao.get(cacEccRvConId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< EccCacRevieCon > getGrid(UserProfile user, JqGrid jqGrid, EccCacRevieCon eccCacRevieCon) {
		return eccCacRevieConDao.findPage(jqGrid, eccCacRevieCon);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param cacEccRvConId
	 * @return
	 */
	public EccCacRevieCon initEditOrViewPage(String eccId) {
		if (StringUtils.isBlank(eccId)) {// 新建
			EccCacRevieCon entity = new EccCacRevieCon();
			return entity;
		} else {// 编辑
			EccCacRevieCon entity = getByEccId(eccId);
			return entity;
		}
	}
	public EccCacRevieCon getByEccId(String eccId){
		//
		List<EccCacRevieCon> cons = this.eccCacRevieConDao.findBy("eccId",eccId);
		if (cons!=null&&cons.size()>0){
			return cons.get(0);
		}else{
			return  new EccCacRevieCon();
		}
	}
	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param eccCacRevieCon 实体类
	 * @return
	 */
	public EccCacRevieCon saveOrUpdate(UserProfile user, EccCacRevieCon eccCacRevieCon) {
		//如果确认结果 发送 代办
		if(eccCacRevieCon.getRvConBussinesStatus() == EccCacRevieCon.BUSINESS_SURE){
			eccCacReviewService.sendMessage(eccCacRevieCon.getCacEccId(),eccCacRevieCon.getRvConStatus());

		}
		if (StringUtils.isBlank(eccCacRevieCon.getCacEccRvConId())) {// 新建
			return this.save(user, eccCacRevieCon);
		} else {// 编辑
			return this.update(user, eccCacRevieCon);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param eccCacRevieCon 实体类
	 * @return
	 */
	private EccCacRevieCon save(UserProfile user, EccCacRevieCon eccCacRevieCon) {
		this.eccCacRevieConDao.save(eccCacRevieCon);
		return eccCacRevieCon;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param eccCacRevieCon 实体类
	 * @return
	 */
	private EccCacRevieCon update(UserProfile user, EccCacRevieCon eccCacRevieCon) {
		EccCacRevieCon eccCacRevieConDb = this.eccCacRevieConDao.get(eccCacRevieCon.getCacEccRvConId());
		if(eccCacRevieConDb == null) {
			return this.save(user, eccCacRevieCon);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(eccCacRevieCon, eccCacRevieConDb);
			this.eccCacRevieConDao.update(eccCacRevieConDb);
			return eccCacRevieConDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param cacEccRvConIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String cacEccRvConIds) {
		if(StringUtils.isNotBlank(cacEccRvConIds)) {
			for(String cacEccRvConId : cacEccRvConIds.split(",")) {
				EccCacRevieCon eccCacRevieConDb = this.eccCacRevieConDao.get(cacEccRvConId);
				this.eccCacRevieConDao.delete(cacEccRvConId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param cacEccRvConId
	 * @param cacEccRvConName
	 * @return
	 */
	public boolean nameIsValid(String cacEccRvConId,String cacEccRvConName) {
		return this.eccCacRevieConDao.nameIsValid( cacEccRvConId, cacEccRvConName);
	}

}

