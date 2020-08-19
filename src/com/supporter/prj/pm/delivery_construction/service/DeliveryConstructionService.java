package com.supporter.prj.pm.delivery_construction.service;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.delivery_construction.dao.DeliveryConstructionDao;
import com.supporter.prj.pm.delivery_construction.dao.DeliveryDrawingsDao;
import com.supporter.prj.pm.delivery_construction.entity.DeliveryConstruction;
import com.supporter.prj.pm.delivery_construction.entity.DeliveryDrawings;
import com.supporter.util.CommonUtil;

@Service
@Transactional(TransManager.APP)
public class DeliveryConstructionService {
	
	public static final String MODULE_ID = "DELIVERY"; //应用ID
	
	@Autowired
	private DeliveryConstructionDao dao;
	@Autowired
	private DeliveryDrawingsDao contentDao;
	@Autowired
	private DeliveryDrawingsService contentService;
	// @Autowired
	// private DrawingLibraryService drawingLibraryService;
	// @Autowired
	// private DeliveryConstructionOfferService constructionOfferService;
	// @Autowired
	// private OfferService offerService;//报盘模块service方法
	
	
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 */
	public DeliveryConstruction initEditOrViewPage(String deliveryId,UserProfile user) {
		DeliveryConstruction deliveryConstruction = null;
		if (StringUtils.isNotBlank(deliveryId)) {			
			deliveryConstruction =  dao.get(deliveryId);
		}
		if (deliveryConstruction != null) {			
			deliveryConstruction.setIsNew(false);
			deliveryConstruction.setModifiedByName(user.getName());
			deliveryConstruction.setModifiedById(user.getPersonId());
			deliveryConstruction.setModifiedDate(new Date());
			return deliveryConstruction;
		}else{
			deliveryConstruction = new DeliveryConstruction();
			deliveryConstruction.setIsNew(true);
			deliveryConstruction.setStatus(0);
			deliveryConstruction.setCreatedDate(new Date());
			deliveryConstruction.setCreatedById(user.getPersonId());
			deliveryConstruction.setCreatedByName(user.getName());
			if(user.getDept() != null){
				deliveryConstruction.setCreatedDeptName(user.getDept().getName());
				deliveryConstruction.setCreatedDeptId(user.getDeptId());
			}	
			deliveryConstruction.setDeliveryId(com.supporter.util.UUIDHex.newId());
			return deliveryConstruction;
		}	
	}
	
	/**
	 * 分页表格展示数据.
	 * @param user 用户信息
	 * @param jqGrid jqgrid请求对象
	 * @return JqGrid
	 */
	public List<DeliveryConstruction> getGrid(JqGrid jqGrid, DeliveryConstruction deliveryConstruction,UserProfile user, Map<String, Object> map) {
		return dao.findPage(jqGrid, deliveryConstruction,user,map);
	}
	
	public List<DeliveryDrawings> getContentGrid(UserProfile user, JqGrid jqGrid,DeliveryDrawings content,String deliveryId) {
		return contentDao.findContentPage(user,jqGrid,content,deliveryId);
	}
	
	public DeliveryConstruction updateRewinding(UserProfile user, String deliveryId) {
		DeliveryConstruction deliveryConstruction = this.dao.get(deliveryId);
		dao.update(deliveryConstruction);
		return deliveryConstruction;
	}
	
	public DeliveryConstruction getDeliveryConstructionById(String deliveryId) {
		return dao.get(deliveryId);
	}

	//  BL-GC(交底单位)-TID-HHJ(接收单位)-001/2018
	public String getDeliveryNo(DeliveryConstruction drawings) {
		String foldNo = "";
		String foldDeptNo = EIPService.getDeptService().getDept(drawings.getDeliveryDeptId()).getDeptNo();
		String receiveDeptNo = EIPService.getDeptService().getDept(drawings.getReceiveDeptId()).getDeptNo();
		if(foldDeptNo != null && receiveDeptNo != null) {
			Integer year = Integer.parseInt(CommonUtil.format(new Date(), "yyyy"));
			String number = dao.getMaxNumber();
			foldNo = "BL-"+foldDeptNo+"-DC-"+receiveDeptNo+"-";
			if(number != null) {
				String maxNumber = number.substring(number.length()-8,number.length()-5);
				Integer orderNo = Integer.parseInt(maxNumber);
				Format f1 = new DecimalFormat("000");
				foldNo += f1.format(orderNo+1);
			}else {
				foldNo += "001";
			}
			foldNo += "/"+year;
			return foldNo;
		}else {
			return null;
		}
	}

	//根据主键获取实体对象
	public DeliveryConstruction get(String deliveryId) {
		return dao.get(deliveryId);
	}

}
