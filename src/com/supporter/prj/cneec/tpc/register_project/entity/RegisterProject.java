package com.supporter.prj.cneec.tpc.register_project.entity;

// default package

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.register_project.entity.base.BaseRegisterProject;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.util.CommonUtil;

/**
 * @Title: RegisterProject
 * @Description: 注册项目实体类
 * @author: yanweichao
 * @date: 2017-8-30
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_REGISTER_PROJECT", schema = "")
public class RegisterProject extends BaseRegisterProject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCREGISTPRJ";

	// Constructors

	/** default constructor */
	public RegisterProject() {
		super();
	}

	/** full constructor */
	public RegisterProject(String projectId) {
		super(projectId);
	}

	private boolean add;
	private String keyword;// 搜索关键字
	private boolean isExceedCompleteDate = false;// 当前是否超过完成日期

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	@Transient
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * 超过规定完成日期
	 *
	 * @return
	 */
	@Transient
	public boolean isExceedCompleteDate() {
		String completeDate = CommonUtil.trim(super.getCompleteDate());
		if (completeDate.length() > 0) {
			long todayTime = new Date().getTime();
			long completeDateTime = CommonUtil.parseDate(completeDate).getTime();
			// 当前日期已超过规定的完成日期（完成日期即为当前按未完成）
			if (todayTime > completeDateTime) {
				this.isExceedCompleteDate = true;
			}
		}
		return this.isExceedCompleteDate;
	}

	public static final int DRAFT = 0; // 草稿
	public static final int PROCESSING = 10; // 进行中
	public static final int FINISHED = 20;// 完毕
	public static final int TERMINATION = 30;// 终止

	/**
	 * 获取状态码表.
	 * @return
	 */
	public static Map<Integer, String> getSwfStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		map.put(PROCESSING, "进行中");
		map.put(FINISHED, "完毕");
		map.put(TERMINATION, "终止");
		return map;
	}

	// 状态
	@Transient
	public String getSwfStatusDesc() {
		if (this.getSwfStatus() != null) {
			return getSwfStatusMap().get(this.getSwfStatus());
		}
		return "";
	}

	// 是否有框架协议
	@Transient
	public String getHasFrameworkAgreementDesc() {
		return this.isHasFrameworkAgreement() ? "有" : "无";
	}

	// 单一类型
	@Transient
	public String getSingleCategory() {
		return TpcConstant.getSingleCategoryMap().get(this.getSingleCategoryCode());
	}
	
	//业务编号
	@Transient
	public String getModuleId() {
		return MODULE_ID;
	}

}
