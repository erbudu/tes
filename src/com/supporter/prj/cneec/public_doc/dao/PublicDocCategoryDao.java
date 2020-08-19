package com.supporter.prj.cneec.public_doc.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.cneec.public_doc.entity.PublicDocCategory;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**   
 * @Title: Entity
 * @Description: OA_PUBLIC_DOC_CATEGORY.
 * @author Administrator
 * @date 2018-06-04 16:05:02
 * @version V1.0   
 *
 */
@Repository
public class PublicDocCategoryDao extends MainDaoSupport< PublicDocCategory, String > {
	/**
	 * 判断指定字段唯一性.
	 * @param categoryId 分类ID
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return boolean
	 */
	public boolean checkPropertyUniquenes(String categoryId, String propertyName, String propertyValue) {
		String hql = null;
		List retList = null;
		
		if (StringUtils.isBlank(categoryId)) { //新建时
			hql = "from " + PublicDocCategory.class.getName() + " where  " + propertyName + "= ?";
			retList = this.retrieve(hql, propertyValue);
		} else { //编辑时
			hql = "from " + PublicDocCategory.class.getName()
					+ " where categoryId != ? and " + propertyName + "= ?";
			retList = this.retrieve(hql, categoryId, propertyValue);
		}
		return (CollectionUtils.isEmpty(retList));
	}
	
	/**
	 * 获取排序后的所有分类列表.
	 * @return List < PublicDocCategory >
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public List < PublicDocCategory > getAllCategorys() {
		String hql = "from " + PublicDocCategory.class.getName() + " order by displayOrder";
		return this.retrieve(hql);
	}
	
	/**
	 * 得到根节点.
	 * @return PublicDocCategory
	 */
	public PublicDocCategory getRootCategory() {
		String hql = "from " + PublicDocCategory.class.getName()
				+ " where (parentCategoryId is null) or (parentCategoryId=?)";
		return (PublicDocCategory) this.retrieveFirst(hql, "0");
	}

}

