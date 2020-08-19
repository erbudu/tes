package com.supporter.prj.linkworks.oa.netin.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.netin.entity.base.BaseOaNetinPerson;


/**
 * OaNetinPerson entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="OA_NETIN_PERSON"
    ,schema="SUPP_APP"
)
public class OaNetinPerson extends BaseOaNetinPerson implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public OaNetinPerson() {
    }

	/** minimal constructor */
    public OaNetinPerson(String id) {
        super(id);        
    }
    
    /** full constructor */
    public OaNetinPerson(String id, String netinId, String name, String nameSpell, String sex, String userType, String accountType, String userAccount, String mail) {
        super(id, netinId, name, nameSpell, sex, userType, accountType, userAccount, mail);        
    }
    
    private boolean isNew;
	
	@Transient
	public boolean getIsNew(){
		return this.isNew;
	}
	
	public void setIsNew(boolean isNew){
		this.isNew = isNew;
	}
	
}
