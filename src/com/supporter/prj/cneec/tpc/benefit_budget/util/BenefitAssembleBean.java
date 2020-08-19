package com.supporter.prj.cneec.tpc.benefit_budget.util;


/**
 * @Title: BenefitAssembleBean
 * @Description: 项目效益测算/预算表预算项集合接口
 * @author: yanweichao
 * @date: 2018-6-7
 * @version: V1.0
 */
public interface BenefitAssembleBean {

	/**
	 * 获取序号
	 * 
	 * @return
	 */
	public String getSerialNumber();

	/**
	 * 获取预算ID
	 * 
	 * @return
	 */
	public String getBudgetId();

	/**
	 * 获取预算名称
	 * 
	 * @return
	 */
	public String getBudgetName();

	/**
	 * 预算总额
	 * 
	 * @return
	 */
	public double getSumTotalAmount();

	/**
	 * 节点级别(0、1、2...)
	 * 
	 * @return
	 */
	public int getLevel();

	/**
	 * 是否叶节点
	 * 
	 * @return
	 */
	public boolean getIsLeaf();

	/**
	 * 上级节点ID
	 * 
	 * @return
	 */
	public String getParent();

	/**
	 * 是否默认折叠
	 * 
	 * @return
	 */
	public boolean isExpanded();

}
