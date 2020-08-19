package com.supporter.prj.pm.payment_onsite.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.pm.constant.PmSwfBusiEntity;
import com.supporter.prj.pm.payment_onsite.entity.base.BasePaymentOnsiteSwf;
import com.supporter.util.CommonUtil;

/**   
 * @Title: 付款申请流程
 * @Description: pm_payment_onsite_swf.
 * @author Administrator
 * @date 2018-07-04 18:08:56
 * @version V1.0   
 *
 */
@Entity
@Table(name = "pm_payment_onsite_swf", schema = "")
public class PaymentOnsiteSwf extends BasePaymentOnsiteSwf implements PmSwfBusiEntity {

	private static final long serialVersionUID = 1L;

	public static final String APP_NAME = "PaymentOn"; //应用编号
	public static final String DOMAIN_OBJECT_ID = "PaySwf"; //业务对象编号
	/**
	 *搜索关键词.
	 */
	@Transient
	private String keyword;
	
	/**
	 * 无参构造函数.
	 */
	public PaymentOnsiteSwf() {
		super();
	}
	
	/**
	 * 构造函数.
	 * @param id
	 */
	public PaymentOnsiteSwf(String id) {
		super(id);
	}
	
	@Transient
	public String getOaExamStatusDesc() {
		return OAExamStatus.getMap().get(getOaExamStatus());
	}
	
	@Transient
	private PaymentOnsite paymentOnsite;
	public PaymentOnsite getPaymentOnsite() {
		return this.paymentOnsite;
	}
	/**
	 * 设置付款申请对象
	 * @param paymentOnsite 付款申请
	 */
	public void setPaymentOnsite(PaymentOnsite paymentOnsite) {
		this.paymentOnsite = paymentOnsite;
		if (paymentOnsite != null) {
			this.setId(paymentOnsite.getId());
			this.setPaymentNo(paymentOnsite.getPaymentNo());
			this.setPrjId(paymentOnsite.getPrjId());
			this.setPrjName(paymentOnsite.getPrjName());
		}
	}

	/**
	 * 合同名称
	 * @return String
	 */
	@Transient
	public String getContractName() {
		if (this.paymentOnsite == null) {
			return null;
		} else {
			return paymentOnsite.getContractName();
		}
	}

	/**
	 * 获取付款性质
	 * @return int
	 */
	@Transient
	public int getPaymentNature() {
		if (this.paymentOnsite == null) {
			return -1;
		} else {
			return paymentOnsite.getPaymentNature();
		}
	}
	
	/**
	 * 获取款项用途
	 * @return int
	 */
	@Transient
	public int getPurpose() {
		if (this.paymentOnsite == null) {
			return -1;
		} else {
			return paymentOnsite.getPurpose();
		}
	}
	
	/**
	 * 付款金额
	 * @return double
	 */
	@Transient
	public double getPaymentAmount() {
		if (this.paymentOnsite == null) {
			return 0;
		} else {
			return paymentOnsite.getPaymentAmount();
		}
	}
	
	/**
	 * 美元付款金额
	 * @return double
	 */
	@Transient
	public double getUsdAmount() {
		if (this.paymentOnsite == null) {
			return 0;
		} else {
			return paymentOnsite.getUsdAmount();
		}
	}
	
	@Transient
	private String prjManagerId;
	public String getPrjManagerId() {
		return this.prjManagerId;
	}
	public void setPrjManagerId(String prjManagerId) {
		this.prjManagerId = prjManagerId;
	}
	@Transient
	private String prjManagerName;
	public String getPrjManagerName() {
		return this.prjManagerName;
	}
	public void setPrjManagerName(String prjManagerName) {
		this.prjManagerName = prjManagerName;
	}
	@Transient
	private String prjDeptId;
	public String getPrjDeptId() {
		return this.prjDeptId;
	}
	public void setPrjDeptId(String prjDeptId) {
		this.prjDeptId = prjDeptId;
	}
	
	/**
	 * 项目经理是否在国内
	 * @return boolean
	 */
	@Transient
	public boolean isInHome() {
		IComCodeTableItem item = EIPService.getComCodeTableService().getCodetableItem(this.getPrjId());
		if (item == null) {
			return true;
		} else {
			return CommonUtil.parseInt(item.getExtField2(), 1) == 1;
		}
		
	}
	
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof PaymentOnsiteSwf)) {
			return false;
		}
		PaymentOnsiteSwf castOther = (PaymentOnsiteSwf) other;
		return StringUtils.equals(this.getId(), castOther.getId());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Transient
	@Override
	public String getProcTitle() {
		String procTitle = super.getPrjName() + "的" + StringUtils.trimToEmpty(this.getContractName()) + "（" + this.getPaymentNo() + "）";
		return procTitle;
	}
	
	//获取项目代码
	@Transient
	public String getPrjCode() {
		return EIPService.getComCodeTableService().getCodeTable("PROJECT_CODE").getDisplay(this.getPrjId());
	}

	@Transient
	@Override
	public int getDbYear() {
		return 0;
	}
	
	@Transient
	@Override
	public String getDomainObjectId() {	
		return DOMAIN_OBJECT_ID;
	}
	//获取主键ID，用于公共流程页面的传参
	@Transient
	@Override
	public String getEntityId() {
		return this.getId();
	}
	
	@Transient
	@Override
	public String getEntityName() {
		return this.getClass().getName();
	}
	
	@Transient
	@Override
	public String getModuleId() {
		return APP_NAME;
	}
	
	@Transient
	@Override
	public String getModuleBusiType() {
		return "";
	}
	
	@Transient
	@Override
	public String getCompanyNo() {
		return "";
	}

	@Transient
	@Override
	public String getKeyProps() {
		return "id";
	}

	@Transient
	@Override
	public String getKeyValues() {
		return this.getId();
	}

	@Transient
	@Override
	public double getAmount() {
		if (this.paymentOnsite == null) {
			return 0;
		} else {
			return this.paymentOnsite.getPaymentAmount();
		}
		
	}

	@Transient
	@Override
	public String getDeptId() {
		return null;
	}

	@Transient
	@Override
	public String getOperatorId() {
		return null;
	}

	@Transient
	@Override
	public String getProjectId() {
		return this.getPrjId();
	}
	
	/**
	 * OA审批状态
	 * @author Administrator
	 *
	 */
	public static final class OAExamStatus {
		public static final int DRAFT = 0; // 草稿
		public static final int EXAM = 1; // 审批中
		public static final int COMPLETE  = 2; // 审批完成
		public static final int FAIL  = 3; // 驳回给现场
		
		
		/**
		 * 获取采购类型码表.
		 * @return Map<Integer, String>
		 */
		public static Map<Integer, String> getMap() {
			Map<Integer, String> map = new LinkedHashMap<Integer, String>();
			map.put(DRAFT, "草稿");
			map.put(EXAM, "审批中");
			map.put(COMPLETE, "审批完成");
			map.put(FAIL, "驳回");
			return map;
		}
		
		private OAExamStatus() {

		}
	}
	
	/**
	 * OA审批状态
	 * @author Administrator
	 *
	 */
	public static final class OAExamResult {
		public static final int FAILED = 0; // 未通过
		public static final int SUCCESS = 1; // 通过
		
		
		/**
		 * 获取审批状态码表.
		 * @return Map<Integer, String>
		 */
		public static Map<Integer, String> getMap() {
			Map<Integer, String> map = new LinkedHashMap<Integer, String>();
			map.put(FAILED, "未通过");
			map.put(SUCCESS, "通过");
			return map;
		}
		
		private OAExamResult() {

		}
	}
}
