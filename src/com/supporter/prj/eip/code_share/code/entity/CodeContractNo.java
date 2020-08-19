package com.supporter.prj.eip.code_share.code.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.eip.busi_entity.IBusiEntity;
import com.supporter.prj.eip.code_share.code.entity.base.BaseCodeContractNo;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;

/**
 * @Title: Entity
 * @Description: CS_CODE_CONTRACT_NO.
 * @author Administrator
 * @date 2019-07-17 16:46:50
 * @version V1.0
 *
 */
@Entity
@Table(name = "CS_CODE_CONTRACT_NO", schema = "")
public class CodeContractNo extends BaseCodeContractNo implements IBusiEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 搜索关键词.
	 */
	@Transient
	private String keyword;
	

	/**
	 * 无参构造函数.
	 */
	public CodeContractNo() {
		super();
	}

	/**
	 * 构造函数.
	 * 
	 * @param codeId
	 */
	public CodeContractNo(String codeId) {
		super(codeId);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof CodeContractNo)) {
			return false;
		}
		CodeContractNo castOther = (CodeContractNo) other;
		return StringUtils.equals(this.getCodeId(), castOther.getCodeId());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getCodeId()).toHashCode();
	}

	/**
	 * 方法: 取得keyword.
	 * 
	 * @return String
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * 方法: 设置keyword.
	 * @param keyword 查询参数
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	@Transient
	public String getCodeTypeDisp() {
		return CodeType.getMap().get(this.getCodeType());
	}
	
	/**
	 * 编号类型
	 * @author Administrator
	 *
	 */
	public static final class CodeType {
		public static final int SUB_CONTRACT = 1; //一般分包合同
		public static final int SUB_CONTRACT_ATTACH = 2; //分包合同附属合同
		public static final int SPARE_PARTS_CONTRACT = 3; //备品备件采购合同
		public static final int SPORADIC_CONTRACT = 4; //零星采购合同
		
		/**
		 * MAP码表
		 * @return Map < Integer, String >
		 */
		public static Map < Integer, String > getMap() {
			Map < Integer, String > map = new LinkedHashMap< Integer, String >();
			map.put(SUB_CONTRACT, "一般分包合同");
			map.put(SUB_CONTRACT_ATTACH, "分包合同附属合同");
			map.put(SPARE_PARTS_CONTRACT, "备品备件采购合同");
			map.put(SPORADIC_CONTRACT, "零星采购合同");
			return map;
		}
	}

	@Override
	public String getEntityName() {
		return this.getClass().getName();
	}

	@Override
	public String getKeyProps() {
		return "codeId";
	}

	@Override
	public String getKeyValues() {
		return this.getCodeId();
	}

}
