package com.supporter.prj.cneec.pc.pm_prj.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass

public class BaseVPmPrjViewAllEmps {

    // Fields

     private String memberId;


    // Constructors

    /** default constructor */
    public BaseVPmPrjViewAllEmps() {
    }

    
    /** full constructor */
    public BaseVPmPrjViewAllEmps(String memberId) {
        this.memberId = memberId;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MEMBER_ID")

    public String getMemberId() {
        return this.memberId;
    }
    
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
   



}
