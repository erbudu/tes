package com.supporter.prj.ppm.prj_org.base_info.entity.base;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * PpmPrjBidInf entity.
 * @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class BasePrjBidInf  implements java.io.Serializable {



    // Fields

     private String bidInfId;
     private String prjId;
     private String bidNo;
     private String bidTitle;
     private String preqDate;
     private String preqConDate;
     private Date bidCloseTime;
     private String preAwardDate;
     private String bondType;
     private String bondTypeId;
     private String amountOrRatio;


    // Constructors

    /** default constructor */
    public BasePrjBidInf() {
    }

    
    /** full constructor */
    public BasePrjBidInf(String prjId, String bidNo, String bidTitle, String preqDate, String preqConDate, Date bidCloseTime, String preAwardDate, String bondType, String bondTypeId, String amountOrRatio) {
        this.prjId = prjId;
        this.bidNo = bidNo;
        this.bidTitle = bidTitle;
        this.preqDate = preqDate;
        this.preqConDate = preqConDate;
        this.bidCloseTime = bidCloseTime;
        this.preAwardDate = preAwardDate;
        this.bondType = bondType;
        this.bondTypeId = bondTypeId;
        this.amountOrRatio = amountOrRatio;
    }

   
    // Property accessors
    @Id
    @Column(name="BID_INF_ID", unique=true, nullable=false, length=32)

    public String getBidInfId() {
        return this.bidInfId;
    }
    
    public void setBidInfId(String bidInfId) {
        this.bidInfId = bidInfId;
    }
    
    @Column(name="PRJ_ID", length=32)

    public String getPrjId() {
        return this.prjId;
    }
    
    public void setPrjId(String prjId) {
        this.prjId = prjId;
    }
    
    @Column(name="BID_NO", length=64)

    public String getBidNo() {
        return this.bidNo;
    }
    
    public void setBidNo(String bidNo) {
        this.bidNo = bidNo;
    }
    
    @Column(name="BID_TITLE", length=256)

    public String getBidTitle() {
        return this.bidTitle;
    }
    
    public void setBidTitle(String bidTitle) {
        this.bidTitle = bidTitle;
    }
    
    @Column(name="PREQ_DATE", length=64)

    public String getPreqDate() {
        return this.preqDate;
    }
    
    public void setPreqDate(String preqDate) {
        this.preqDate = preqDate;
    }
    
    @Column(name="PREQ_CON_DATE", length=64)

    public String getPreqConDate() {
        return this.preqConDate;
    }
    
    public void setPreqConDate(String preqConDate) {
        this.preqConDate = preqConDate;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="BID_CLOSE_TIME", length=11)

    public Date getBidCloseTime() {
        return this.bidCloseTime;
    }
    
    public void setBidCloseTime(Date bidCloseTime) {
        this.bidCloseTime = bidCloseTime;
    }
    
    @Column(name="PRE_AWARD_DATE", length=64)

    public String getPreAwardDate() {
        return this.preAwardDate;
    }
    
    public void setPreAwardDate(String preAwardDate) {
        this.preAwardDate = preAwardDate;
    }
    
    @Column(name="BOND_TYPE", length=32)

    public String getBondType() {
        return this.bondType;
    }
    
    public void setBondType(String bondType) {
        this.bondType = bondType;
    }
    
    @Column(name="BOND_TYPE_ID", length=256)

    public String getBondTypeId() {
        return this.bondTypeId;
    }
    
    public void setBondTypeId(String bondTypeId) {
        this.bondTypeId = bondTypeId;
    }
    
    @Column(name="AMOUNT_OR_RATIO", length=128)

    public String getAmountOrRatio() {
        return this.amountOrRatio;
    }
    
    public void setAmountOrRatio(String amountOrRatio) {
        this.amountOrRatio = amountOrRatio;
    }
}