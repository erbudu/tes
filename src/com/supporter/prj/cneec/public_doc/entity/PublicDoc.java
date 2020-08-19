package com.supporter.prj.cneec.public_doc.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.supporter.prj.cneec.public_doc.entity.base.BasePublicDoc;
import com.supporter.util.CodeTable;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;

/**   
 * @Title: Entity
 * @Description: OA_PUBLIC_DOC.
 * @author Administrator
 * @date 2018-06-04 16:05:03
 * @version V1.0   
 *
 */
@Entity
@Table(name = "OA_PUBLIC_DOC", schema = "")
public class PublicDoc extends BasePublicDoc {

	private static final long serialVersionUID = 1L;
	
	public static String FILE = "FILE";
	public static String URL = "URL";
	
	public static CodeTable getStatusCodeTable(){
		CodeTable ct = new CodeTable();
		ct.insertItem(FILE, FILE);
		ct.insertItem(URL, URL);
		return ct;
	}
	
	public static Map<String, String> getStatusMap(){
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(FILE, FILE);
		map.put(URL, URL);
		return map;
	}
	
	//----------------------------实体类扩展属性定义,注意属性前加@Transient注解标示非数据库字段----------------------------
	/**
	 *搜索关键词.
	 */
	@Transient
	private String keyword;
	
	/**
	 * 无参构造函数.
	 */
	public PublicDoc() {
		super();
	}
	
	/**
	 * 构造函数.
	 * @param docId
	 */
	public PublicDoc(String docId) {
		super(docId);
	} 
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof PublicDoc)) {
			return false;
		}
		PublicDoc castOther = (PublicDoc) other;
		return StringUtils.equals(this.getDocId(), castOther.getDocId());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getDocId()).toHashCode();
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
	
	
}
