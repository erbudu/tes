package com.supporter.prj.linkworks.oa.seal_manage.engrave.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.linkworks.oa.seal_manage.destruction.service.SealDestructionService;
import com.supporter.prj.linkworks.oa.seal_manage.engrave.entity.base.BaseSealEngrave;
import com.supporter.prj.linkworks.oa.seal_manage.engrave.service.SealEngraveService;

/**   
 * @Title: Entity
 * @Description: 印章销毁数据表.
 * @author z
 * @date 2019-12-12 17:52:31
 * @version V1.0   
 *
 */
@Entity
@Table(name = "OA_SEAL_ENGRAVE", schema = "")
public class SealEngrave extends BaseSealEngrave {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "ENGRAVE";
	public static final String DOMAIN_OBJECT_ID = "SealEngrave";
	public static final int DB_YEAR = 0;
	
	public static final String AUTH_DATASHOWAUTH = "DATASHOWAUTH";//列表权限
	public static final String AUTH_MENUAUTH = "MENUAUTH";//菜单授权
	public static final String AUTH_SETVALAUTH = "SETVALAUTH";//维护权限
	
	//扩展数据库以外的其他属性，注意属性前加@Transient注解标示非数据库字段.
	
	/**
	 * 无参构造函数.
	 */
	public SealEngrave(){
		super();
	}
	
	/**
	 * 构造函数.
	 * @param applyId
	 */
	public SealEngrave(String applyId){
		super(applyId);
	} 
	@Transient
	private boolean isNew;
	
	@Transient
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	
	/**
	 * 获取知会人员
	 * @return
	 */
//	@Transient
//	private String notifyPersonIds;
//
//	@Transient
//	public String getNotifyPersonIds() {
//		String notifyPersonIds = "";
//		SealEngraveService service = (SealEngraveService) SpringContextHolder.getBean(SealEngraveService.class);
//		notifyPersonIds = service.getNotifyPersonIds(this);
//		return notifyPersonIds;
//	}
	
	// 状态
	public static final int DRAFT = 0; // 草稿
	public static final int PROCESSING = 10; // 审核中
	public static final int COMPLETED = 20;// 审批完成

	/**
	 * 获取状态码表.
	 * 
	 * @return
	 */
	public static Map<Integer, String> getStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		map.put(PROCESSING, "审核中");
		map.put(COMPLETED, "审批完成");
		return map;
	}

	@Transient
	public String getStatusDesc() {
		if (this.getStatus() != null) {
			return getStatusMap().get(this.getStatus());
		}
		return "";
	}
	//流程中用到的参数
	@Transient
	public String getModuleId() {
		return MODULE_ID;
	}

	@Transient
	public int getDbYear() {
		return DB_YEAR;
	}

	@Transient
	public String getDomainObjectId() {
		return DOMAIN_OBJECT_ID;
	}

	@Transient
	public String getEntityName() {
		return getClass().getName();
	}

	@Transient
	public String getModuleBusiType() {
		return "";
	}

	@Transient
	public String getCompanyNo() {
		return "";
	}

}
