package com.supporter.prj.cneec.tpc.custom.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.custom.dao.CustomDao;
import com.supporter.prj.cneec.tpc.custom.dao.CustomLinkmanDao;
import com.supporter.prj.cneec.tpc.custom.entity.Custom;
import com.supporter.prj.cneec.tpc.custom.entity.CustomLinkman;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

@Service
@Transactional(TransManager.APP)
public class CustomService {
	
	@Autowired
	private CustomDao customDao;
	
	@Autowired
	private CustomLinkmanDao customLinkmanDao;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, Custom.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param custom
	 */
	public void getAuthCanExecute(UserProfile userProfile, Custom custom) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, Custom.MODULE_ID, custom.getCustomId(), custom);
	}

	/**
	 * 进入新建、编辑或查看页面需要加载的信息。
	 */
	public Custom initEditOrviewPage(String customId, UserProfile user){
		Custom custom;
		if (StringUtils.isBlank(customId)){//新建
			custom = newCustom(user);
			custom.setCustomId(UUIDHex.newId());
			custom.setIsNew(true);
		}else {//编辑
			custom = this.customDao.get(customId);
			custom.setIsNew(false);
		}
		return custom;
	}
	
	/**
	 * 初始化新建客户
	 * 
	 * @param auserprf_U 用户信息
	 */
	public Custom newCustom(UserProfile user){
		Custom custom = new Custom();
		loadingCustom(custom, user);
		return custom;
	}
	
	/**
	 * 装填基础信息
	 * 
	 * @param auserprf_U 用户信息
	 */
	public Custom loadingCustom(Custom lcustom_N, UserProfile user){
		lcustom_N.setCreatedBy(user.getName());
		lcustom_N.setCreatedById(user.getPersonId());
		lcustom_N.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		lcustom_N.setModifiedBy(user.getName());
		lcustom_N.setModifiedById(user.getPersonId());
		lcustom_N.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		lcustom_N.setStatus(Custom.DRAFT);
		lcustom_N.setCustomControlStatusCode(Custom.EFFECTIV);
		lcustom_N.setCustomControlStatus(Custom.EFFECTIV_DESC);
		return lcustom_N;
	}
	
	/**
	 * 分页展示客户数据
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 */
	public List<Custom> getGrid(UserProfile user, JqGrid jqGrid, Custom custom){
		String authFilter = getAuthFilter(user);
		return customDao.findPage(jqGrid, custom, authFilter);
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
	public Custom saveOrUpdate(UserProfile user, Custom custom, Map<String, Object> valueMap){
		if(custom.getIsNew()){
			custom = loadingCustom(custom, user);
			this.customDao.save(custom);
		}else{
			custom.setModifiedBy(user.getName());
			custom.setModifiedById(user.getPersonId());
			custom.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.customDao.update(custom);
		}
		//保存或更新客户联系人
//		saveLinkmanList(custom);
		return custom;
	}
	
	/**
	 * 保存提交
	 * @param user
	 * @param registerProject
	 * @param valueMap
	 * @return
	 */
	public Custom commit(UserProfile user, Custom custom, Map<String, Object> valueMap) {
		if(custom.getIsNew()){
			custom = loadingCustom(custom, user);
			this.customDao.save(custom);
		}else{
			custom.setModifiedBy(user.getName());
			custom.setModifiedById(user.getPersonId());
			custom.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			custom.setStatus(Custom.CLOSED);
			this.customDao.update(custom);
		}
		//保存或更新客户联系人
//		saveLinkmanList(custom);
		return custom;
	}
	
	/**
	 * 保存或更新客户联系人
	 */
	public void saveLinkmanList(Custom custom){
		//保存或更新客户联系人
		List<CustomLinkman> linkmanList = custom.getLinkmanList();
		if (CollectionUtils.isNotEmpty(custom.getLinkmanList())){
			for (CustomLinkman linkman : linkmanList){
				//如果联系人ID为空新建
				if(StringUtils.isBlank(linkman.getLinkmanId())){
					linkman.setLinkmanId(UUIDHex.newId());
					linkman.setCustomId(custom.getCustomId());
					this.customLinkmanDao.save(linkman);
				}else{//联系人ID不为空则更新
					linkman.setCustomId(custom.getCustomId());
					this.customLinkmanDao.update(linkman);
				}
			}
		}
		//清除已被删除的联系人
		String delIds = custom.getDelIds();
		if (StringUtils.isNotBlank(delIds)){
			for (String id : delIds.split(",")){
				CustomLinkman linkman = new CustomLinkman(id);
				if (linkman!=null){
					this.customLinkmanDao.delete(linkman);
				}
			}
		}
	}
	
	/**
	 * 删除
	 */
	public void delete(UserProfile user, String customIds) {
		if (StringUtils.isNotBlank(customIds)) {
			Custom custom;
			for (String customId : customIds.split(",")) {
				custom = this.get(customId);
				if (custom == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, custom);
				//先删除客户下的所有联系人信息
				this.customLinkmanDao.deleteInner(get(customId));
				//再删除客户信息
				this.customDao.delete(customId);
			}
		}
	}
	
	/**
	 * 失效
	 */
	public void batchLnvalid(UserProfile user, String customIds) {
		if (StringUtils.isNotBlank(customIds)) {
			for (String customId : customIds.split(",")) {
				Custom custom = get(customId);
				custom.setModifiedBy(user.getName());
				custom.setModifiedById(user.getPersonId());
				custom.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
				custom.setCustomControlStatusCode(Custom.FAILURE);
				custom.setCustomControlStatus(Custom.FAILURE_DESC);
				this.customDao.update(custom);
			}
		}
	}
	
	/**
	 * 获取单个客户对象
	 * @param customId
	 * @return
	 */
	public Custom get(String customId) {
		return this.customDao.get(customId);
	}
	
	/**
	 * 判断名字是否重复
	 */
	public boolean checkNameIsValid(String moduleId, String moduleName) {
		return this.customDao.checkNameIsValid(moduleId, moduleName);
	}
	
	/**
	 * 判断是否国内
	 */
	public List<CheckBoxVO> getIsForeignData(String customId) {
		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
		int choose = 0;
		if (customId.length() > 0) {
			Custom custom = this.get(customId);
			if (custom != null) {
				choose = ((Custom.IS_FOREIGN + "").equals(custom.getIsForeign())) ? 1 : 0;
			}
		}
		Map<Integer, String> map = Custom.getForeignStatusMap();
		for (Integer key : map.keySet()) {
			CheckBoxVO vo = new CheckBoxVO("isForeign_" + key, "isForeign", key.toString(), map.get(key), key == choose);
			list.add(vo);
		}
		return list;
	}
	
	/**
	 * 判断是上市
	 */
	public List<CheckBoxVO> getIsListedData(String customId) {
		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
		int choose = 0;
		if (customId.length() > 0) {
			Custom custom = this.get(customId);
			if (custom != null) {
				choose = ((Custom.IS_LISTED + "").equals(custom.getIsListed())) ? 1 : 0;
			}
		}
		Map<Integer, String> map = Custom.getListedStatusMap();
		for (Integer key : map.keySet()) {
			CheckBoxVO vo = new CheckBoxVO("isListed_" + key, "isListed", key.toString(), map.get(key), key == choose);
			list.add(vo);
		}
		return list;
	}
	
	/**
	 * 获取币别码表
	 * @return
	 */
	public JSONObject getCurrencyData(){
		Map map = TpcConstant.getCurrencyMap();
		return JSONObject.fromObject(map);
	}
}
