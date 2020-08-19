package com.supporter.prj.cneec.tpc.trade_homepage.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: TPC_TRADE_HOMEPAGE_PROGRESS,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2018-02-02
 * @version V1.0
 */
@MappedSuperclass
public class BaseTradeProgress implements Serializable {

	private static final long serialVersionUID = 1L;
	private String progressId;
	private String projectId;
	private String activedOneLevelId;
	private String activedTwoLevelId;
	private String activedThreeLevelId;
	private String otherParams;
	private String createdById;
	private String createdBy;
	private String createdDate;
	private String modifiedDate;

	/**
	 * 无参构造函数.
	 */
	public BaseTradeProgress() {
	}

	/**
	 * 构造函数.
	 *
	 * @param progressId
	 */
	public BaseTradeProgress(String progressId) {
		this.progressId = progressId;
	}

	/**
	 * GET方法: 取得PROGRESS_ID.
	 * 
	 * @return: String PROGRESS_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "PROGRESS_ID", nullable = false, length = 32)
	public String getProgressId() {
		return this.progressId;
	}

	/**
	 * SET方法: 设置PROGRESS_ID.
	 * 
	 * @param: String PROGRESS_ID
	 */
	public void setProgressId(String progressId) {
		this.progressId = progressId;
	}

	/**
	 * GET方法: 取得PROJECT_ID.
	 * 
	 * @return: String PROJECT_ID
	 */
	@Column(name = "PROJECT_ID", nullable = true, length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	/**
	 * SET方法: 设置PROJECT_ID.
	 * 
	 * @param: String PROJECT_ID
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * GET方法: 取得ACTIVED_ONE_LEVEL_ID.
	 * 
	 * @return: String ACTIVED_ONE_LEVEL_ID
	 */
	@Column(name = "ACTIVED_ONE_LEVEL_ID", nullable = true, length = 32)
	public String getActivedOneLevelId() {
		return this.activedOneLevelId;
	}

	/**
	 * SET方法: 设置ACTIVED_ONE_LEVEL_ID.
	 * 
	 * @param: String ACTIVED_ONE_LEVEL_ID
	 */
	public void setActivedOneLevelId(String activedOneLevelId) {
		this.activedOneLevelId = activedOneLevelId;
	}

	/**
	 * GET方法: 取得ACTIVED_TWO_LEVEL_ID.
	 * 
	 * @return: String ACTIVED_TWO_LEVEL_ID
	 */
	@Column(name = "ACTIVED_TWO_LEVEL_ID", nullable = true, length = 32)
	public String getActivedTwoLevelId() {
		return this.activedTwoLevelId;
	}

	/**
	 * SET方法: 设置ACTIVED_TWO_LEVEL_ID.
	 * 
	 * @param: String ACTIVED_TWO_LEVEL_ID
	 */
	public void setActivedTwoLevelId(String activedTwoLevelId) {
		this.activedTwoLevelId = activedTwoLevelId;
	}

	/**
	 * GET方法: 取得ACTIVED_THREE_LEVEL_ID.
	 * 
	 * @return: String ACTIVED_THREE_LEVEL_ID
	 */
	@Column(name = "ACTIVED_THREE_LEVEL_ID", nullable = true, length = 32)
	public String getActivedThreeLevelId() {
		return this.activedThreeLevelId;
	}

	/**
	 * SET方法: 设置ACTIVED_THREE_LEVEL_ID.
	 * 
	 * @param: String ACTIVED_THREE_LEVEL_ID
	 */
	public void setActivedThreeLevelId(String activedThreeLevelId) {
		this.activedThreeLevelId = activedThreeLevelId;
	}

	/**
	 * GET方法: 取得OTHER_PARAMS.
	 * 
	 * @return: String OTHER_PARAMS
	 */
	@Column(name = "OTHER_PARAMS", nullable = true, length = 255)
	public String getOtherParams() {
		return this.otherParams;
	}

	/**
	 * SET方法: 设置OTHER_PARAMS.
	 * 
	 * @param: String OTHER_PARAMS
	 */
	public void setOtherParams(String otherParams) {
		this.otherParams = otherParams;
	}

	/**
	 * GET方法: 取得CREATED_BY_ID.
	 * 
	 * @return: String CREATED_BY_ID
	 */
	@Column(name = "CREATED_BY_ID", nullable = true, length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	/**
	 * SET方法: 设置CREATED_BY_ID.
	 * 
	 * @param: String CREATED_BY_ID
	 */
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	/**
	 * GET方法: 取得CREATED_BY.
	 * 
	 * @return: String CREATED_BY
	 */
	@Column(name = "CREATED_BY", nullable = true, length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * SET方法: 设置CREATED_BY.
	 * 
	 * @param: String CREATED_BY
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * GET方法: 取得CREATED_DATE.
	 * 
	 * @return: String CREATED_DATE
	 */
	@Column(name = "CREATED_DATE", nullable = true, length = 27)
	public String getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * SET方法: 设置CREATED_DATE.
	 * 
	 * @param: String CREATED_DATE
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * GET方法: 取得MODIFIED_DATE.
	 *
	 * @return: String  MODIFIED_DATE
	 */
	@Column(name = "MODIFIED_DATE", nullable = true, length = 27)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	/**
	 * SET方法: 设置MODIFIED_DATE.
	 *
	 * @param: String  MODIFIED_DATE
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
