package com.supporter.prj.linkworks.oa.salary.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.salary.entity.base.BaseSalary;

@Entity
@Table(name="OA_SALARY"
    ,schema=""
)
public class Salary extends BaseSalary {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private boolean add;
	@Transient
    public boolean isAdd() {
		return add;
	}
	public void setAdd(boolean add) {
		this.add = add;
	}
	
	private String dateFrom;
	private String dateTo;
	@Transient
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	@Transient
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
    //党费应缴额（用于个人主页我的工资列表）
	private double dangfeiyingjiaoe;
	@Transient
	public double getDangfeiyingjiaoe() {
		//double dangfeiyingjiaoe=(this.getXueLiZhiCheng()+this.getGangWeiGongZi())*0.005;
		return dangfeiyingjiaoe;
	}
	public void setDangfeiyingjiaoe(double dangfeiyingjiaoe) {
		this.dangfeiyingjiaoe = dangfeiyingjiaoe;
	}
	//工会会费应缴额（用于个人主页我的工资列表）
	private double gonghuihuifeiyingjiaoe;
	@Transient
	public double getGonghuihuifeiyingjiaoe() {
		double gonghuihuifeiyingjiaoe=(this.getXueLiZhiCheng()+this.getGangWeiGongZi())*0.005;
		return gonghuihuifeiyingjiaoe;
	}

	
}
