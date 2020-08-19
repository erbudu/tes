package com.supporter.prj.cneec.tpc.trade_homepage.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

/**
 * @Title: Entity
 * @Description: TPC_TRADE_HOMEPAGE,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2018-01-25
 * @version V1.0
 */
@MappedSuperclass
public class BaseTradeHomepage implements Serializable {

	private static final long serialVersionUID = 1L;
	private String tradeHomepageId;
	private String operName;
	private String navigationId;
	private int pagePathType;
	private String parentId;
	private String iconPath;
	private String pagePath;
	private int displayOrder;
	private int lowerStyle;
	private boolean isActive;
	private String moduleId;
	private int homepageType;
	private int menuLevel;
	private int openMode;

	/**
	 * 无参构造函数.
	 */
	public BaseTradeHomepage() {
	}

	/**
	 * 构造函数.
	 * 
	 * @param tradeHomepageId
	 */
	public BaseTradeHomepage(String tradeHomepageId) {
		this.tradeHomepageId = tradeHomepageId;
	}

	/**
	 * GET方法: 取得TRADE_HOMEPAGE_ID.
	 * 
	 * @return: String TRADE_HOMEPAGE_ID
	 */
	@Id
	@Column(name = "TRADE_HOMEPAGE_ID", nullable = false, length = 32)
	public String getTradeHomepageId() {
		return this.tradeHomepageId;
	}

	/**
	 * SET方法: 设置TRADE_HOMEPAGE_ID.
	 * 
	 * @param: String TRADE_HOMEPAGE_ID
	 */
	public void setTradeHomepageId(String tradeHomepageId) {
		this.tradeHomepageId = tradeHomepageId;
	}

	/**
	 * GET方法: 取得OPER_NAME.
	 * 
	 * @return: String OPER_NAME
	 */
	@Column(name = "OPER_NAME", nullable = true, length = 120)
	public String getOperName() {
		return this.operName;
	}

	/**
	 * SET方法: 设置OPER_NAME.
	 * 
	 * @param: String OPER_NAME
	 */
	public void setOperName(String operName) {
		this.operName = operName;
	}

	/**
	 * GET方法: 取得NAVIGATION_ID.
	 * 
	 * @return: String NAVIGATION_ID
	 */
	@Column(name = "NAVIGATION_ID", nullable = true, length = 50)
	public String getNavigationId() {
		return this.navigationId;
	}

	/**
	 * SET方法: 设置NAVIGATION_ID.
	 * 
	 * @param: String NAVIGATION_ID
	 */
	public void setNavigationId(String navigationId) {
		this.navigationId = navigationId;
	}

	/**
	 * GET方法: 取得PAGE_PATH_TYPE.
	 * 
	 * @return: int PAGE_PATH_TYPE
	 */
	@Column(name = "PAGE_PATH_TYPE", nullable = true, precision = 10)
	public int getPagePathType() {
		return this.pagePathType;
	}

	/**
	 * SET方法: 设置PAGE_PATH_TYPE.
	 * 
	 * @param: int PAGE_PATH_TYPE
	 */
	public void setPagePathType(int pagePathType) {
		this.pagePathType = pagePathType;
	}

	/**
	 * GET方法: 取得PARENT_ID.
	 * 
	 * @return: String PARENT_ID
	 */
	@Column(name = "PARENT_ID", nullable = true, length = 32)
	public String getParentId() {
		return this.parentId;
	}

	/**
	 * SET方法: 设置PARENT_ID.
	 * 
	 * @param: String PARENT_ID
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * GET方法: 取得ICON_PATH.
	 * 
	 * @return: String ICON_PATH
	 */
	@Column(name = "ICON_PATH", nullable = true, length = 512)
	public String getIconPath() {
		return this.iconPath;
	}

	/**
	 * SET方法: 设置ICON_PATH.
	 * 
	 * @param: String ICON_PATH
	 */
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	/**
	 * GET方法: 取得PAGE_PATH.
	 * 
	 * @return: String PAGE_PATH
	 */
	@Column(name = "PAGE_PATH", nullable = true, length = 255)
	public String getPagePath() {
		return this.pagePath;
	}

	/**
	 * SET方法: 设置PAGE_PATH.
	 * 
	 * @param: String PAGE_PATH
	 */
	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}

	/**
	 * GET方法: 取得DISPLAY_ORDER.
	 * 
	 * @return: int DISPLAY_ORDER
	 */
	@Column(name = "DISPLAY_ORDER", nullable = true, precision = 10)
	public int getDisplayOrder() {
		return this.displayOrder;
	}

	/**
	 * SET方法: 设置DISPLAY_ORDER.
	 * 
	 * @param: int DISPLAY_ORDER
	 */
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * GET方法: 取得LOWER_STYLE.
	 * 
	 * @return: int LOWER_STYLE
	 */
	@Column(name = "LOWER_STYLE", nullable = true, precision = 10)
	public int getLowerStyle() {
		return this.lowerStyle;
	}

	/**
	 * SET方法: 设置LOWER_STYLE.
	 * 
	 * @param: int LOWER_STYLE
	 */
	public void setLowerStyle(int lowerStyle) {
		this.lowerStyle = lowerStyle;
	}

	/**
	 * GET方法: 取得IS_ACTIVE.
	 * 
	 * @return: boolean IS_ACTIVE
	 */
	@Type(type = "true_false")
	@Column(name = "IS_ACTIVE")
	public boolean getIsActive() {
		return this.isActive;
	}

	/**
	 * SET方法: 设置IS_ACTIVE.
	 * 
	 * @param: boolean IS_ACTIVE
	 */
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * GET方法: 取得MODULE_ID.
	 * 
	 * @return: String MODULE_ID
	 */
	@Column(name = "MODULE_ID", nullable = true, length = 12)
	public String getModuleId() {
		return this.moduleId;
	}

	/**
	 * SET方法: 设置MODULE_ID.
	 * 
	 * @param: String MODULE_ID
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * GET方法: 取得HOMEPAGE_TYPE.
	 * 
	 * @return: int HOMEPAGE_TYPE
	 */
	@Column(name = "HOMEPAGE_TYPE", nullable = true, precision = 10)
	public int getHomepageType() {
		return this.homepageType;
	}

	/**
	 * SET方法: 设置HOMEPAGE_TYPE.
	 * 
	 * @param: int HOMEPAGE_TYPE
	 */
	public void setHomepageType(int homepageType) {
		this.homepageType = homepageType;
	}

	/**
	 * GET方法: 取得MENU_LEVEL.
	 * 
	 * @return: int MENU_LEVEL
	 */
	@Column(name = "MENU_LEVEL", nullable = true, precision = 10)
	public int getMenuLevel() {
		return this.menuLevel;
	}

	/**
	 * SET方法: 设置MENU_LEVEL.
	 * 
	 * @param: int MENU_LEVEL
	 */
	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}

	/**
	 * GET方法: 取得OPEN_MODE.
	 * 
	 * @return: int OPEN_MODE
	 */
	@Column(name = "OPEN_MODE", nullable = true, precision = 10)
	public int getOpenMode() {
		return this.openMode;
	}

	/**
	 * SET方法: 设置OPEN_MODE.
	 * 
	 * @param: int OPEN_MODE
	 */
	public void setOpenMode(int openMode) {
		this.openMode = openMode;
	}

}
