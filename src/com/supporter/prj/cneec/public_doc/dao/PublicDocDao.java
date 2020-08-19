package com.supporter.prj.cneec.public_doc.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.cneec.public_doc.entity.PublicDoc;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**   
 * @Title: Entity
 * @Description: OA_PUBLIC_DOC.
 * @author Administrator
 * @date 2018-06-04 16:05:00
 * @version V1.0   
 *
 */
@Repository
public class PublicDocDao extends MainDaoSupport< PublicDoc, String > {
	/**
	 * 分页查询.
	 * @param jqGrid 表格请求参数
	 * @param publicDoc 实体对象参数
	 * @param onlyFile 是否只有文件
	 * @return List< PublicDoc >
	 */
	public List< PublicDoc > findPage(JqGrid jqGrid, PublicDoc publicDoc, boolean onlyFile) {
		if (publicDoc != null) {
			//搜索关键字
			String keyword = publicDoc.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				String likeKeyword = "%" + keyword + "%";
				String hql = "docName like ?";
				jqGrid.addHqlFilter(hql, likeKeyword);
			}
			
			String categoryId = publicDoc.getCategoryId();
			if (StringUtils.isNotBlank(categoryId)) {
				String hql = "categoryId=?";
				jqGrid.addHqlFilter(hql, categoryId);
			}
			
			String parentDocId = publicDoc.getParentDocId();
			if (StringUtils.isNotBlank(parentDocId)) {
				String hql = "parentDocId=?";
				jqGrid.addHqlFilter(hql, parentDocId);
			} else {
				String hql = "parentDocId is null";
				jqGrid.addHqlFilter(hql);
			}
		}
		if (onlyFile) {
			jqGrid.addHqlFilter("file=?", true);
		}
		
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 获取指定类别和目录下的子目录列表，或文件列表 （按照名称进行过滤）.
	 * @param categoryId 分类ID
	 * @param parentDocId 父目录ID
	 * @param docNames 文档名
	 * @param isFile 是否文件
	 * @return List < PublicDoc >
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public List < PublicDoc > getPublicDocs(String categoryId, String parentDocId,
			String docNames, boolean isFile) {
		if (StringUtils.isBlank(docNames)) {
			return null;
		}
		
		if (StringUtils.isBlank(categoryId) && StringUtils.isBlank(parentDocId)) {
			return null;
		}
		
		Map < String, Object > params = new HashMap < String, Object >();
		
		params.put("file", isFile);
		String hql = "from " + PublicDoc.class.getName() + " where file=:file";
		if (StringUtils.isBlank(parentDocId)) {
			hql += " and parentDocId is null";
		} else {
			params.put("parentDocId", parentDocId);
			hql += " and parentDocId=:parentDocId";
		}
		if (StringUtils.isNotBlank(categoryId)) {
			params.put("categoryId", categoryId);
			hql += " and categoryId=:categoryId";
		}
		
		String[] nameArray = docNames.split(",");
		String nameFilter = "";
		for (int i = 0; i < nameArray.length; i++) {
			if (StringUtils.isNotBlank(nameArray[i])) {
				if (nameFilter.length() > 0) {
					nameFilter += " or ";
				}
				params.put("docName" + i, nameArray[i]);
				nameFilter += " docName=:docName" + i;
			}
		}
		if (nameFilter.length() > 0) {
			hql += " and (" + nameFilter + ")";
		}
		
		return this.retrieve(hql, params);
	}
	
	/**
	 * 获取某个分类下的所有文档.
	 * @param categoryId 分类ID
	 * @return  List < PublicDoc >
	 */
	public List < PublicDoc > getDocsByCategoryId(String categoryId) {
		return this.findBy("categoryId", categoryId);
	}
	
	/**
	 * 获取某个分类下的所有文件（不含文件夹）.
	 * @param categoryId 分类ID
	 * @return  List < PublicDoc >
	 */
	public List < PublicDoc > getFilesByCategoryId(String categoryId) {
		Map < String, Object > params = new HashMap < String, Object >();
		params.put("categoryId", categoryId);
		params.put("file", true);
		String hql = "from " + PublicDoc.class.getName()
				+ " where categoryId=:categoryId and file=:file and parentDocId is null order by displayOrder";
		return this.find(hql, params);
	}
	
	/**
	 * 获取某个分类下的所有文件夹.
	 * @param categoryId 分类ID
	 * @return  List < PublicDoc >
	 */
	public List < PublicDoc > getFoldersByCategoryId(String categoryId) {
		Map < String, Object > params = new HashMap < String, Object >();
		params.put("categoryId", categoryId);
		params.put("file", false);
		String hql = "from " + PublicDoc.class.getName()
				+ " where categoryId=:categoryId and file=:file and parentDocId is null order by displayOrder";
		return this.find(hql, params);
	}
	
	/**
	 * 获取多个分类下的所有文件（不含文件夹）.
	 * @param categoryIds 分类ID
	 * @return  List < PublicDoc >
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public List < PublicDoc > getFilesByCategoryIds(String[] categoryIds) {
		if (categoryIds == null || categoryIds.length == 0) {
			return null;
		}
		
		Map < String, Object > params = new HashMap < String, Object >();
		
		StringBuffer filter = new StringBuffer("");
		int length = categoryIds.length;
		for (int i = 0; i < length; i++) {
			String id = categoryIds[i];
			if (StringUtils.isBlank(id)) {
				continue;
			}
			if (filter.length() > 0) {
				filter.append(" or ");
			}
			params.put("categoryId" + i, id);
			filter.append("categoryId=:categoryId" + i);
		}
		if (filter.length() == 0) {
			return null;
		}
				
		
		String hql = "from " + PublicDoc.class.getName() + " where file=true and ("
				+ filter.toString() + ") order by displayOrder";
		return this.retrieve(hql, params);
	}
	
	/**
	 * 获取多个分类下的所有文档（包含文件夹）.
	 * @param categoryIds 分类ID
	 * @return  List < PublicDoc >
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public List < PublicDoc > getDocsByCategoryIds(String[] categoryIds) {
		if (categoryIds == null || categoryIds.length == 0) {
			return null;
		}
		
		Map < String, Object > params = new HashMap < String, Object >();
		
		StringBuffer filter = new StringBuffer("");
		int length = categoryIds.length;
		for (int i = 0; i < length; i++) {
			String id = categoryIds[i];
			if (StringUtils.isBlank(id)) {
				continue;
			}
			if (filter.length() > 0) {
				filter.append(" or ");
			}
			params.put("categoryId" + i, id);
			filter.append("categoryId=:categoryId" + i);
		}
		if (filter.length() == 0) {
			return null;
		}
				
		
		String hql = "from " + PublicDoc.class.getName()
				+ " where " + filter.toString() + " order by displayOrder";
		return this.retrieve(hql, params);
	}
	
	/**
	 * 判断指定字段唯一性
	 * 
	 * @param docId 文档ID
	 * @param categoryId 分类ID
	 * @param parentDocId 文件夹ID
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return boolean
	 */
	public boolean checkPropertyUniquenes(String docId, String categoryId, String parentDocId,
			String propertyName, String propertyValue) {
		String hql = null;
		List retList = null;
		
		Map < String, Object > params = new HashMap < String, Object >();
		params.put("propertyValue", propertyValue);
		if (StringUtils.isBlank(docId)) { //新建时
			
			hql = "from " + PublicDoc.class.getName() + " where  " + propertyName + "=:propertyValue";
			
		} else { //编辑时
			params.put("docId", docId);
			hql = "from " + PublicDoc.class.getName()
					+ " where docId != :docId and " + propertyName + "=:propertyValue";
		}
		
		if (StringUtils.isNotBlank(parentDocId)) {
			params.put("parentDocId", parentDocId);
			hql += " and parentDocId=:parentDocId";
		} else {
			hql += " and parentDocId = null";
			if (StringUtils.isNotBlank(categoryId)) {
				params.put("categoryId", categoryId);
				hql += " and categoryId=:categoryId";
			}
		}
		
		retList = this.retrieve(hql, params);
		
		return (CollectionUtils.isEmpty(retList));
	}
}

