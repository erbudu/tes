package com.supporter.prj.cneec.tpc.util;

/**
 * @Title: IModuleEntity
 * @Description: 贸易项目模块带流程基础属性(其他字段可映射)
 * @author: yanweichao
 * @date: 2018-9-10
 * @version: V1.0
 */
public interface IModuleEntity {

	/**
	 * 流程状态（表字段）
	 * @return
	 */
	public int getSwfStatus();

	/**
	 * 流程ID（表字段）
	 * @return
	 */
	public String getProcId();

	/**
	 * 流程标题（表中不需定义字段）
	 * @return
	 */
	public String getProcTitle();

	/** 以下是EIP流程需要属性 **/
	/**
	 * 年度（贸易不需要，即为0）
	 * @return
	 */
	public int getDbYear();

	/**
	 * 应用编号，最长12位
	 * @return
	 */
	public String getModuleId();

	/**
	 * 业务对象编号，最长15位
	 * @return
	 */
	public String getDomainObjectId();

	/**
	 * 业务记录主键值
	 * @return
	 */
	public String getEntityId();

	/**
	 * 所属部门/公司ID
	 * @return
	 */
	public String getCompanyNo();

	/**
	 * 模块业务类型，可以为空字符串
	 * @return
	 */
	public String getModuleBusiType();

	/**
	 * 业务对象名（this.getClass().getName()）
	 * @return
	 */
	public String getEntityName();

}
