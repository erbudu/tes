package com.supporter.prj.cneec.tpc.custom.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.custom.entity.base.BaseCustomLinkman;
import com.supporter.util.CodeTable;

@Entity
@Table(name = "TPC_CUSTOM_LINKMAN", schema = "")
public class CustomLinkman extends BaseCustomLinkman {
	private static final long serialVersionUID = 1L;
	
	// Constructors

    /** default constructor */
    public CustomLinkman() {
    }

	/** minimal constructor */
    public CustomLinkman(String linkmanId) {
        super(linkmanId);        
    }
    
    /** full constructor */
    public CustomLinkman(String linkmanId, String customId, String name, String post, String telephone, String mobilePhone, String fax, String mail, String isMainLinkman, String remark) {
        super(linkmanId, customId, name, post, telephone, mobilePhone, fax, mail, isMainLinkman, remark);        
    }
    
    /**
	 * 获取是否主联系人
	 * @return
	 */
    public static final int IS_MAIN_LINKMAN = 1;
	public static final int IS_NOT_MAIN_LINKMAN = 0;
	
	public static CodeTable getMainLinkmanTable(){
		CodeTable ct = new CodeTable();
		ct.insertItem(IS_MAIN_LINKMAN, "是");
		ct.insertItem(IS_NOT_MAIN_LINKMAN, "否");
		return ct;
	}
	
	public static Map<Integer, String> getMainLinkmanMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(IS_MAIN_LINKMAN, "是");
		map.put(IS_NOT_MAIN_LINKMAN, "否");
		return map;
	}
	
	// 获取主联系人状态
	@Transient
	public String getMainLinkmanDesc() {
		if (this.getIsMainLinkman()!=null) {
			return getMainLinkmanTable().getDisplay(this.getIsMainLinkman());
		}
		return "";
	}
	
	// 是否为新建联系人
	private boolean isNew;
	@Transient
	public boolean getIsNew(){
		return this.isNew;
	}
	
	public void setIsNew(boolean isNew){
		this.isNew = isNew;
	}
	
	// 主联系人值
	private String isMainLinkmanVal;
	
	@Transient
	public String getIsMainLinkmanVal() {
		return isMainLinkmanVal;
	}

	public void setIsMainLinkmanVal(String isMainLinkmanVal) {
		this.isMainLinkmanVal = isMainLinkmanVal;
	}
	
	
}
