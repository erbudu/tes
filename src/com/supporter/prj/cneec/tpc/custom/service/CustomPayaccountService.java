package com.supporter.prj.cneec.tpc.custom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.custom.dao.CustomPayaccountDao;
import com.supporter.prj.cneec.tpc.custom.entity.Custom;
import com.supporter.prj.cneec.tpc.custom.entity.CustomPayaccount;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip.wechat.mp.constant.WxMpEventConstants.CustomerService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.UUIDHex;

@Service
@Transactional(TransManager.APP)
public class CustomPayaccountService {

	@Autowired
	private CustomPayaccountDao customPayaccountDao;
	@Autowired
	private CustomService customService;
	/**
	 * 进入新建、编辑或查看页面需要加载的信息。
	 */
	public CustomPayaccount initEditOrviewPage(String payaccountId,String customId, UserProfile user){
		CustomPayaccount customPayaccount;
		if (StringUtils.isBlank(payaccountId)){//新建
			customPayaccount = newCustomPayaccount(user);
			customPayaccount.setPayaccountId(UUIDHex.newId());
			customPayaccount.setCustomId(customId);
			customPayaccount.setIsOtherPay(true);
			customPayaccount.setIsNew(true);
		}else {//编辑
			customPayaccount = this.customPayaccountDao.get(payaccountId);
			customPayaccount.setIsNew(false);
		}
		return customPayaccount;
	}
	
	
	/**
	 * 初始化新建付款账号
	 * 
	 * @param auserprf_U 用户信息
	 */
	public CustomPayaccount newCustomPayaccount(UserProfile user){
		CustomPayaccount customPayaccount = new CustomPayaccount();
		loadingCustomPayaccount(customPayaccount, user);
		return customPayaccount;
	}
	
	/**
	 * 装填基础信息
	 * 
	 * @param auserprf_U 用户信息
	 */
	public CustomPayaccount loadingCustomPayaccount(CustomPayaccount lCustomPayaccount_N, UserProfile user){
		//没有预装基础信息
		return lCustomPayaccount_N;
	}
	
	/**
	 * 获取单个客户对象
	 * @param customId
	 * @return
	 */
	public CustomPayaccount get(String payaccountId) {
		return this.customPayaccountDao.get(payaccountId);
	}
	
	/**
	 * 分页展示付款账号数据
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 */
	public List<CustomPayaccount> getLinkGrid(UserProfile user, JqGrid jqGrid, String customId, String pagetype){
		List<CustomPayaccount> payaccounts = customPayaccountDao.findPage(jqGrid,customId);
		if (pagetype != null && pagetype.equals("view")) {
			return payaccounts;
		}
		return payaccounts;
	}
	
	/**
	 * 保存或更新
	 */
	public CustomPayaccount saveOrUpdate(UserProfile user, CustomPayaccount customPayaccount, Map<String, Object> valueMap){
		if(!customPayaccount.getIsOtherPay()) {
			List<CustomPayaccount> list = customPayaccountDao.findNotOtherPay(customPayaccount.getCustomId(),customPayaccount.getPayaccountId());
			if(list!=null&&list.size()>0) {
				customPayaccount.setCompanyname("保存失败！非第三方支付已存在。");
				return customPayaccount;
			}else {
				Custom custom = customService.get(customPayaccount.getCustomId());
				if(custom!=null) {
					customPayaccount.setCompanyname(custom.getCustomerName());
				}
			}
		} 
		if(customPayaccount.getIsNew()){
			customPayaccount = loadingCustomPayaccount(customPayaccount, user);
			customPayaccountDao.save(customPayaccount);
		}else{
			customPayaccountDao.update(customPayaccount);
		}
		return customPayaccount;
	}
	
	/**
	 * 删除
	 */
	public void delete(UserProfile user, String payaccountId) {
		if (StringUtils.isNotBlank(payaccountId)) {
			CustomPayaccount customPayaccount = get(payaccountId);
			customPayaccountDao.delete(customPayaccount);
		}
	}


	public List<CheckBoxVO> getIsPayAccountData(String trim) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * 验证非第三方支付
	 * @param payaccountId
	 * @param customId
	 * @return
	 */
	public Boolean checkPayType(String payaccountId, String customId) {
		List<CustomPayaccount> list = customPayaccountDao.findNotOtherPay(customId,payaccountId);
		if(list!=null&&list.size()>0) {
			return false;
		}
		return true;
	}
	
	
}
