package com.supporter.prj.eip.code_share.code.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.eip.busi_entity.IBusiEntity;
import com.supporter.prj.eip.code_share.code.entity.base.BaseEntitySalesContract;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;

/**
 * @Title: Entity
 * @Description: CS_ENTITY_SALES_CONTRACT.
 * @author Administrator
 * @date 2019-07-17 16:46:46
 * @version V1.0
 *
 */
@Entity
@Table(name = "CS_ENTITY_SALES_CONTRACT", schema = "")
public class EntitySalesContract extends BaseEntitySalesContract implements IBusiEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 搜索关键词.
	 */
	@Transient
	private String keyword;

	/**
	 * 无参构造函数.
	 */
	public EntitySalesContract() {
		super();
	}

	/**
	 * 构造函数.
	 * 
	 * @param contractRecId
	 */
	public EntitySalesContract(String contractRecId) {
		super(contractRecId);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof EntitySalesContract)) {
			return false;
		}
		EntitySalesContract castOther = (EntitySalesContract) other;
		return StringUtils.equals(this.getContractRecId(), castOther.getContractRecId());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getContractRecId()).toHashCode();
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

	@Override
	public String getEntityName() {
		return this.getClass().getName();
	}

	@Override
	public String getKeyProps() {
		return "contractRecId";
	}

	@Override
	public String getKeyValues() {
		return this.getContractRecId();
	}

}
