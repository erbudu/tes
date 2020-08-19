package com.supporter.prj.linkworks.oa.outside_person.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.outside_person.entity.base.BaseOutsidePerson;

/**
 * OaOutsidePerson entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OA_OUTSIDE_PERSON", schema = "SUPP_APP")
public class OutsidePerson extends BaseOutsidePerson implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public OutsidePerson() {
	}

	/** minimal constructor */
	public OutsidePerson(String id) {
		super(id);
	}

	/** full constructor */
	public OutsidePerson(String id, String name, String nameSpell,
			String sex, String account, String mail, String deptId,
			String deptName, String confirmDate, String status) {
		super(id, name, nameSpell, sex, account, mail, deptId, deptName,
				confirmDate, status);
	}
	
	public static final String ON_JOB = "在职";
	public static final String FORMAL = "转为正式";
	public static final String QUIT = "离职";
	
	public static Map<String, String> getStatusMap(){
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(ON_JOB, ON_JOB);
		map.put(FORMAL, FORMAL);
		map.put(QUIT, QUIT);
		return map;
	}
	
	// 搜索关键字
	private String keyword;
	
	@Transient
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	private boolean isNew;
	
	@Transient
	public boolean getIsNew(){
		return isNew;
	}
	
	public void setIsNew(boolean isNew){
		this.isNew = isNew;
	}

}
