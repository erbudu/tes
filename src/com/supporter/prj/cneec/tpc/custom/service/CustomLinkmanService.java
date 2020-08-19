package com.supporter.prj.cneec.tpc.custom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.custom.dao.CustomLinkmanDao;
import com.supporter.prj.cneec.tpc.custom.entity.CustomLinkman;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.UUIDHex;

@Service
@Transactional(TransManager.APP)
public class CustomLinkmanService {

	@Autowired
	private CustomLinkmanDao customLinkmanDao;
	
	/**
	 * 进入新建、编辑或查看页面需要加载的信息。
	 */
	public CustomLinkman initEditOrviewPage(String linkmanId,String customId, UserProfile user){
		CustomLinkman customLinkman;
		if (StringUtils.isBlank(linkmanId)){//新建
			customLinkman = newCustomLinkman(user);
			customLinkman.setLinkmanId(UUIDHex.newId());
			customLinkman.setCustomId(customId);
			List<CustomLinkman> list = customLinkmanDao.getIsMainLinkmanList(customId,customLinkman.getLinkmanId());
			if (list == null){
				customLinkman.setIsMainLinkmanVal(CustomLinkman.IS_MAIN_LINKMAN + "");
			}else {
				customLinkman.setIsMainLinkmanVal(CustomLinkman.IS_NOT_MAIN_LINKMAN + "");
			}
			customLinkman.setIsNew(true);
		}else {//编辑
			customLinkman = this.customLinkmanDao.get(linkmanId);
			customLinkman.setIsNew(false);
			customLinkman.setIsMainLinkmanVal(customLinkman.getIsMainLinkman());
		}
		return customLinkman;
	}
	
	
	/**
	 * 初始化新建联系人
	 * 
	 * @param auserprf_U 用户信息
	 */
	public CustomLinkman newCustomLinkman(UserProfile user){
		CustomLinkman customLinkman = new CustomLinkman();
		loadingCustomLinkman(customLinkman, user);
		return customLinkman;
	}
	
	/**
	 * 装填基础信息
	 * 
	 * @param auserprf_U 用户信息
	 */
	public CustomLinkman loadingCustomLinkman(CustomLinkman lcustomLinkman_N, UserProfile user){
		//没有预装基础信息
		return lcustomLinkman_N;
	}
	
	/**
	 * 获取单个客户对象
	 * @param customId
	 * @return
	 */
	public CustomLinkman get(String linkmanId) {
		return this.customLinkmanDao.get(linkmanId);
	}
	
	/**
	 * 分页展示联系人数据
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 */
	public List<CustomLinkman> getLinkGrid(UserProfile user, JqGrid jqGrid, String customId, String pagetype){
		List<CustomLinkman> linkmans = customLinkmanDao.findPage(jqGrid,customId);
		if (pagetype != null && pagetype.equals("view")) {
			return linkmans;
		}
		return linkmans;
	}
	
	/**
	 * 保存或更新
	 */
	public CustomLinkman saveOrUpdate(UserProfile user, CustomLinkman customLinkman, Map<String, Object> valueMap){
		List<CustomLinkman> list = customLinkmanDao.getIsMainLinkmanList(customLinkman.getCustomId(),customLinkman.getLinkmanId());
		if ((CustomLinkman.IS_MAIN_LINKMAN + "").equals(customLinkman.getIsMainLinkman()) && list != null) {
			//如果该联系人被设为主联系人，则把已有的主联系人取消
			ReconsitutionMainLinkman(list);
		} else if ((CustomLinkman.IS_NOT_MAIN_LINKMAN + "").equals(customLinkman.getIsMainLinkman()) && (list == null)){
			//如果没有主联系人，则强制将该联系人设为主联系人
			customLinkman.setIsMainLinkman(CustomLinkman.IS_MAIN_LINKMAN + "");
		}
		if(customLinkman.getIsNew()){
			customLinkman = loadingCustomLinkman(customLinkman, user);
			customLinkmanDao.save(customLinkman);
		}else{
			customLinkmanDao.update(customLinkman);
		}
		return customLinkman;
	}
	
	/**
	 * 删除
	 */
	public void delete(UserProfile user, String linkmanId) {
		if (StringUtils.isNotBlank(linkmanId)) {
			CustomLinkman customLinkman = get(linkmanId);
			String customId = customLinkman.getCustomId();
			boolean isMainLinkman = (CustomLinkman.IS_MAIN_LINKMAN + "").equals(customLinkman.getIsMainLinkman());
			customLinkmanDao.delete(customLinkman);
			//如果删除的联系人为主联系人，则重新设定主联系人
			if (isMainLinkman) ResetMainLinkman(customId);
		}
	}
	
	/**
	 * 判断是否主联系人
	 */
	public List<CheckBoxVO> getIsMainLinkmanData(String isMainLinkmanVal) {
		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
		int choose = 0;
		if (isMainLinkmanVal.length() > 0) {
			choose = ((CustomLinkman.IS_MAIN_LINKMAN + "").equals(isMainLinkmanVal)) ? 1 : 0;
		}
		Map<Integer, String> map = CustomLinkman.getMainLinkmanMap();
		for (Integer key : map.keySet()) {
			CheckBoxVO vo = new CheckBoxVO("isMainLinkman_" + key, "isMainLinkman", key.toString(), map.get(key), key == choose);
			list.add(vo);
		}
		return list;
	}
	
	/**
	 * 把已有的主联系人设为否
	 * @param list
	 */
	public void ReconsitutionMainLinkman(List<CustomLinkman> list){
		if (list != null){
			for (CustomLinkman linkman : list){
				linkman.setIsMainLinkman(CustomLinkman.IS_NOT_MAIN_LINKMAN + "");
				customLinkmanDao.update(linkman);
			}
		}
	}
	
	/**
	 * 重新设置主联系人
	 * @param customId
	 */
	public void ResetMainLinkman (String customId){
		List<CustomLinkman> list = customLinkmanDao.getLinkmanByCustomId(customId);
		if (list == null){
			return;
		}else {
			//如果联系人集合不为空，则默认取第一个联系人作为主联系人
			CustomLinkman linkman = list.get(0);
			linkman.setIsMainLinkman(CustomLinkman.IS_MAIN_LINKMAN + "");
			customLinkmanDao.update(linkman);
		}
	}
}
