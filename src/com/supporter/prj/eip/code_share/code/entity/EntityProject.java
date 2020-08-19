package com.supporter.prj.eip.code_share.code.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.eip.busi_entity.IBusiEntity;
import com.supporter.prj.eip.code_share.code.entity.base.BaseEntityProject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;

/**
 * @Title: Entity
 * @Description: CS_ENITY_PROJECT.
 * @author Administrator
 * @date 2019-07-17 16:46:49
 * @version V1.0
 *
 */
@Entity
@Table(name = "CS_ENTITY_PROJECT", schema = "")
public class EntityProject extends BaseEntityProject implements IBusiEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 搜索关键词.
	 */
	@Transient
	private String keyword;

	/**
	 * 无参构造函数.
	 */
	public EntityProject() {
		super();
	}

	/**
	 * 构造函数.
	 * 
	 * @param prjRecId
	 */
	public EntityProject(String prjRecId) {
		super(prjRecId);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof EntityProject)) {
			return false;
		}
		EntityProject castOther = (EntityProject) other;
		return StringUtils.equals(this.getPrjRecId(), castOther.getPrjRecId());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getPrjRecId()).toHashCode();
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
		return "prjRecId";
	}

	@Override
	public String getKeyValues() {
		return this.getPrjRecId();
	}

}
