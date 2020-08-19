package com.supporter.prj.cneec.pc.pm_prj.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseVPmPrjViewDEmps {

    // Fields

     private String memberId;


    // Constructors

    /** default constructor */
    public BaseVPmPrjViewDEmps() {
    }

    
    /** full constructor */
    public BaseVPmPrjViewDEmps(String  memberId) {
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
