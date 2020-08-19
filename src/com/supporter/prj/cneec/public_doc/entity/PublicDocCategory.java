package com.supporter.prj.cneec.public_doc.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.supporter.prj.cneec.public_doc.entity.base.BasePublicDocCategory;
import com.supporter.util.CommonUtil;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;

/**   
 * @Title: 文档分类
 * @Description: OA_PUBLIC_DOC_CATEGORY.
 * @author Administrator
 * @date 2018-06-04 16:05:05
 * @version V1.0   
 *
 */
@Entity
@Table(name = "OA_PUBLIC_DOC_CATEGORY", schema = "")
public class PublicDocCategory extends BasePublicDocCategory {

	private static final long serialVersionUID = 1L;
	
	//----------------------------实体类扩展属性定义,注意属性前加@Transient注解标示非数据库字段----------------------------
	/**
	 *搜索关键词.
	 */
	@Transient
	private String keyword;
	
	@Transient
	private String parentCategoryName;
	
	@Transient
	private boolean finalShow = false; //是否可以显示在首页(要结合上级分类进行确认)
	
	/**
	 * 无参构造函数.
	 */
	public PublicDocCategory() {
		super();
	}
	
	/**
	 * 构造函数.
	 * @param categoryId
	 */
	public PublicDocCategory(String categoryId) {
		super(categoryId);
	} 
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof PublicDocCategory)) {
			return false;
		}
		PublicDocCategory castOther = (PublicDocCategory) other;
		return StringUtils.equals(this.getCategoryId(), castOther.getCategoryId());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getCategoryId()).toHashCode();
	}
	
	
	/**
	 *方法: 取得keyword.
	 *@return String  
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * 方法: 设置keyword.
	 * @param keyword 查询关键字
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getParentCategoryName() {
		return this.parentCategoryName;
	}
	public void setParentCategoryName(String parentCategoryName) {
		this.parentCategoryName = parentCategoryName;
	}
	
	/**
	 * 是否启用.
	 * @return String
	 */
	@Transient
	public String getActivedDisp() {
		if (this.isActived()) {
			return "启用";
		} else {
			return "不启用";
		}
	}
	
	/**
	 * 是否首页显示.
	 * @return String
	 */
	@Transient
	public String getInHomeDisp() {
		if (this.isInHome()) {
			return "是";
		} else {
			return "否";
		}
	}
	
	/**
	 * 是否默认展开
	 * @return String
	 */
	@Transient
	public String getExpandedDisp() {
		if (this.getExpanded()) {
			return "是";
		} else {
			return "否";
		}
	}
	
	/**
	 * 获取图片展示途径.
	 * @return String
	 */
	@Transient
	public String getImgShow() {
		if (CommonUtil.trim(this.getImgUrl()).length() == 0) {
			return "oa/public_doc/img/no_photo.gif";
		} else {
			return this.getImgUrl();
		}
	}
	
	/**
	 * 获取展示模板名
	 * @return String
	 */
	@Transient
	public String getInnerNameDesc() {
		if (StringUtils.isBlank(this.getInnerName())) {
			return "";
		} else {
			return TemplateInnerName.getMap().get(this.getInnerName());
		}
	}
	
	public boolean getFinalShow() {
		return finalShow;
	}
	public void setFinalShow(boolean finalShow) {
		this.finalShow = finalShow;
	}
	
	/**
	 * 文件展示模板类
	 * @author Administrator
	 *
	 */
	public static final class TemplateInnerName {
		private static final String SUB_TREES = "SUB_TREES"; //二级分类平铺展示
		private static final String COMP_QUAL = "COMP_QUAL"; //三级分类平铺展示
		private static final String COMMON = "common"; //二级分类下文件平铺展示
		private static final String SUB_FOLDER_TREE = "SUB_FOLDER_TREE"; //无二级分类平铺展示
		private static final String AWARD_CERT = "AWARD_CERT"; //三级分类下文件平铺展示(仅用于获奖证书)
		private static final String LOGO = "LOGO"; //二级分类下图片平铺展示(仅用于徽标)
		
		/**
		 * MAP码表
		 * @return Map < String, String >
		 */
		public static Map < String, String > getMap() {
			Map < String, String > map = new LinkedHashMap< String, String >();
			map.put(SUB_TREES, "二级分类平铺展示");
			map.put(COMP_QUAL, "三级分类平铺展示");
			map.put(COMMON, "二级分类下文件平铺展示");
			map.put(SUB_FOLDER_TREE, "无二级分类平铺展示");
			map.put(AWARD_CERT, "三级分类下文件平铺展示(仅用于获奖证书)");
			map.put(LOGO, "二级分类下图片平铺展示(仅用于徽标)");
			return map;
		}
	}
	
}
