package com.supporter.prj.cneec.tpc.deliver.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.deliver.dao.DeliverDao;
import com.supporter.prj.cneec.tpc.deliver.dao.DeliverInformationDao;
import com.supporter.prj.cneec.tpc.deliver.entity.Deliver;
import com.supporter.prj.cneec.tpc.deliver.entity.DeliverInformation;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * @Title: DeliverService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2017-12-20
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class DeliverService {

	@Autowired
	private DeliverDao deliverDao;
	@Autowired
	private DeliverInformationDao deliverInformationDao;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, Deliver.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param deliver
	 */
	public void getAuthCanExecute(UserProfile userProfile, Deliver deliver) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, Deliver.MODULE_ID, deliver.getDeliverId(), deliver);
	}

	/**
	 * 获取交付对象集合
	 * @param user
	 * @param jqGrid
	 * @param deliver
	 * @return
	 */
	public List<Deliver> getGrid(UserProfile user, JqGrid jqGrid, Deliver deliver) {
		String authFilter = getAuthFilter(user);
		return this.deliverDao.findPage(jqGrid, deliver, authFilter);
	}

	public List<DeliverInformation> getInfoGrid(UserProfile user, JqGrid jqGrid, String deliverId) {
		return this.deliverInformationDao.findPage(jqGrid, deliverId);
	}

	/**
	 * 获取所有支付明细信息（新建时增加一条空记录/编辑时取最后一条记录判断是否完成决定是否新加空记录）
	 * @param deliverId
	 * @return
	 */
	public Map<String, Object> getInfoMap(String deliverId, boolean edit) {
		// 定义MAP
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 所有支付信息
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 查询参数
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("deliverId", deliverId);
		Map<String, Boolean> orderByMap = new LinkedHashMap<String, Boolean>();
		orderByMap.put("serialNumber", true);
		List<DeliverInformation> infoList = this.deliverInformationDao.find(params, orderByMap);
		Map<String, Object> infoMap;
		for (DeliverInformation info : infoList) {
			infoMap = new LinkedHashMap<String, Object>();
			infoMap.put("informationId", info.getInformationId());
			infoMap.put("isNew", info.getIsNew());
			infoMap.put("serialNumber", info.getSerialNumber());
			infoMap.put("status", info.getStatus());
			list.add(infoMap);
		}
		// 编辑
		if (edit) {
			// 判断是否需要新加空记录
			boolean add = (infoList == null || infoList.size() == 0) ? true : false;
			// 最后一个已完成则添加空记录
			if (infoList.size() > 0 && infoList.get(infoList.size() - 1).getStatus() == DeliverInformation.FINISH) {
				add = true;
			}
			if (add) {
				infoMap = new LinkedHashMap<String, Object>();
				infoMap.put("informationId", UUIDHex.newId());
				infoMap.put("isNew", true);
				infoMap.put("serialNumber", infoList.size() + 1);
				infoMap.put("status", DeliverInformation.DRAFT);
				list.add(infoMap);
			}
		}
		String lastId = "";
		if (list.size() > 0) {
			lastId = list.get(list.size() - 1).get("informationId").toString();
		}
		map.put("lastId", lastId);
		map.put("size", list.size());
		map.put("list", list);
		return map;
	}

	/**
	 * 获取单个交付对象
	 * @param deliverId
	 * @return
	 */
	public Deliver get(String deliverId) {
		return this.deliverDao.get(deliverId);
	}

	/**
	 * 新建交付对象
	 * @param user
	 * @return
	 */
	public Deliver newDeliver(UserProfile user) {
		Deliver deliver = new Deliver();
		loadDeliver(deliver, user);
		deliver.setSwfStatus(Deliver.DRAFT);
		return deliver;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public Deliver loadDeliver(Deliver deliver, UserProfile user) {
		deliver.setCreatedBy(user.getName());
		deliver.setCreatedById(user.getPersonId());
		deliver.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		deliver.setModifiedBy(user.getName());
		deliver.setModifiedById(user.getPersonId());
		deliver.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			deliver.setDeptName(dept.getName());
			deliver.setDeptId(dept.getDeptId());
		}
		// 设置状态
		deliver.setSwfStatus(Deliver.DRAFT);
		return deliver;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param deliverId
	 * @param user
	 * @return
	 */
	public Deliver initEditOrViewPage(String deliverId, UserProfile user) {
		Deliver deliver;
		if (StringUtils.isBlank(deliverId)) {
			deliver = newDeliver(user);
			deliver.setDeliverId(UUIDHex.newId());
			deliver.setAdd(true);
			//deliver.setInformation(newDeliverInformation());
		} else {
			deliver = this.deliverDao.get(deliverId);
			deliver.setAdd(false);
			deliver.setInformation(getLastDeliverInformation(deliverId));
		}
		return deliver;
	}

	/**
	 * 初始化一个支付明细信息
	 * @return
	 */
	public DeliverInformation newDeliverInformation() {
		DeliverInformation deliverInformation = new DeliverInformation();
		deliverInformation.setInformationId(UUIDHex.newId());
		deliverInformation.setIsNew(true);
		return deliverInformation;
	}

	/**
	 * 获取最后一个支付明细信息
	 * @param deliverId
	 * @return
	 */
	public DeliverInformation getLastDeliverInformation(String deliverId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deliverId", deliverId);
		Map<String, Boolean> orderByMap = new HashMap<String, Boolean>();
		orderByMap.put("serialNumber", false);
		List<DeliverInformation> infoList = this.deliverInformationDao.find(params, orderByMap);
		DeliverInformation deliverInformation = null;
		if (infoList != null && infoList.size() > 0) {
			deliverInformation = infoList.get(0);
		}
		if (deliverInformation == null) deliverInformation = newDeliverInformation();
		return deliverInformation;
	}

	/**
	 * 初始化从表
	 * @param informationId
	 * @return
	 */
	public DeliverInformation initEditOrViewInfoPage(String informationId) {
		DeliverInformation deliverInformation;
		if (StringUtils.isBlank(informationId)) {
			deliverInformation = new DeliverInformation();
			deliverInformation.setIsNew(true);
		} else {
			deliverInformation = this.deliverInformationDao.get(informationId);
			deliverInformation.setIsNew(false);
		}
		return deliverInformation;
	}

	/**
	 * 保存或修改对象
	 * @param user
	 * @param deliver
	 * @param valueMap
	 * @return
	 */
	public Deliver saveOrUpdate(UserProfile user, Deliver deliver, Map<String, Object> valueMap) {
		if (deliver.getAdd()) {
			// 装配基础信息
			loadDeliver(deliver, user);
			// 设置为进行中
			deliver.setSwfStatus(Deliver.PROCESSING);
			this.deliverDao.save(deliver);
		} else {
			// 设置更新时间
			deliver.setModifiedBy(user.getName());
			deliver.setModifiedById(user.getPersonId());
			deliver.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.deliverDao.update(deliver);
		}
		if (deliver.getInformation() != null) {
			saveOrUpdateInfo(deliver.getInformation(), deliver.getDeliverId());
		}
		return deliver;
	}

	/**
	 * 保存交付明细
	 * @param deliverInformation
	 * @param deliverId
	 * @return
	 */
	public DeliverInformation saveOrUpdateInfo(DeliverInformation deliverInformation, String deliverId) {
		if (deliverId != null) {
			deliverInformation.setDeliverId(deliverId);
			if (StringUtils.isNotBlank(deliverInformation.getDeliveryTime())) {
				deliverInformation.setStatus(DeliverInformation.FINISH);
			}
		}
		if (deliverInformation.getIsNew()) {
			this.deliverInformationDao.save(deliverInformation);
		} else {
			this.deliverInformationDao.update(deliverInformation);
		}
		//this.deliverInformationDao.saveOrUpdate(deliverInformation);
		return deliverInformation;
	}

	/**
	 * 保存提交
	 * @param user
	 * @param deliver
	 * @param valueMap
	 * @return
	 */
	public Deliver commit(UserProfile user, Deliver deliver, Map<String, Object> valueMap) {
		saveOrUpdate(user, deliver, valueMap);
		// 设置状态
		deliver.setSwfStatus(Deliver.COMPLETED);
		this.deliverDao.update(deliver);
		// 记录日志
		//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{EditApplication : " + deliver + "}", null, null);
		return deliver;
	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param deliverIds
	 */
	public void delete(UserProfile user, String deliverIds) {
		if (StringUtils.isNotBlank(deliverIds)) {
			Deliver deliver;
			for (String deliverId : deliverIds.split(",")) {
				deliver = this.get(deliverId);
				if (deliver == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, deliver);
				this.deliverDao.delete(deliver);
				this.deliverInformationDao.deleteByProperty("deliverId", deliverId);
			}
		}
	}

	public void deleteInfo(String informationIds) {
		if (StringUtils.isNotBlank(informationIds)) {
			for (String informationId : informationIds.split(",")) {
				this.deliverInformationDao.delete(informationId);
			}
		}
	}

}
