package com.supporter.prj.pm.procure_contract.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.supporter.prj.pm.procure_contract.entity.base.BaseProcureContractPay;

/**   
 * @Title: 付款条款
 * @Description: PM_PROCURE_CONTRACT_PAY.
 * @author Administrator
 * @date 2018-07-04 18:04:17
 * @version V1.0   
 *
 */
@Entity
@Table(name = "PM_PROCURE_CONTRACT_PAY", schema = "")
public class ProcureContractPay extends BaseProcureContractPay {

	private static final long serialVersionUID = 1L;
	
	//----------------------------实体类扩展属性定义,注意属性前加@Transient注解标示非数据库字段----------------------------

	/**
	 * 无参构造函数.
	 */
	public ProcureContractPay() {
		super();
	}
	
	/**
	 * 构造函数.
	 * @param payId
	 */
	public ProcureContractPay(String payId) {
		super(payId);
	} 
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ProcureContractPay)) {
			return false;
		}
		ProcureContractPay castOther = (ProcureContractPay) other;
		return StringUtils.equals(this.getPayId(), castOther.getPayId());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getPayId()).toHashCode();
	}
	
	/**
	 * 付款项.
	 */
	public static final class PayItem {
		/**
		 * 获取码表.
		 * @return Map<String, String>
		 */
		public static Map<String, String> getMap() {
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("一般", "一般");
			map.put("重大", "重大");
			return map;
		}
		
		private PayItem() {
		}
	}
	
	public static final class DeductionRules {
		public static final String DENGE = "1"; // 等额
		public static final String DENGBILI = "2"; // 等比例
		public static final String BUDINGQIBUDINGE  = "3"; // 不定期不定额
		
		/**
		 * 获取采购类型码表.
		 */
		public static Map<String, String> getMap() {
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put(DENGE, "等额");
			map.put(DENGBILI, "等比例");
			map.put(BUDINGQIBUDINGE, "不定期不定额");
			return map;
		}
	}
	
	// 状态
	@Transient
	public String getDeductionRuleDesc() {
		if(getDeductionRule() != null && !getDeductionRule().equals("")){
			return DeductionRules.getMap().get(this.getDeductionRule());
		}else{
			return "";
		}
	}
	
}
