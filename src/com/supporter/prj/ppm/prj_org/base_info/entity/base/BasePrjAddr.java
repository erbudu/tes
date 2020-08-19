package com.supporter.prj.ppm.prj_org.base_info.entity.base;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * AbstractPpmPrjAddr entity provides the base persistence definition of the
 * PpmPrjAddr entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class BasePrjAddr  implements java.io.Serializable {


	 // Fields

    private String addrId;//主键
    private String prjId;//外键-项目主键
    private String countryId;//国家ID
    private String provinceId;//省份或州ID
    private String addrC;//地址中文
    private String addrE;//地址英文
    private String countryEName;//国家英文名称
    private String countryCName;//国家中文名称
    private String provinceCName;//州中文名称
    private String provinceEName;//州英文名称
    
  
    
   // Constructors

   /** default constructor */
   public BasePrjAddr() {
   }

   
   /** full constructor */
   public BasePrjAddr(String prjId, String countryId, String provinceId, String addrC, String addrE) {
       this.prjId = prjId;
       this.countryId = countryId;
       this.provinceId = provinceId;
       this.addrC = addrC;
       this.addrE = addrE;
   }

  
   // Property accessors
   @Id
   @Column(name="ADDR_ID", unique=true, nullable=false, length=32)

   public String getAddrId() {
       return this.addrId;
   }
   
   public void setAddrId(String addrId) {
       this.addrId = addrId;
   }
   
   @Column(name="PRJ_ID", length=32)

   public String getPrjId() {
       return this.prjId;
   }
   
   public void setPrjId(String prjId) {
       this.prjId = prjId;
   }
   
   @Column(name="COUNTRY_ID", length=32)

   public String getCountryId() {
       return this.countryId;
   }
   
   public void setCountryId(String countryId) {
       this.countryId = countryId;
   }
   
   @Column(name="PROVINCE_ID", length=32)

   public String getProvinceId() {
       return this.provinceId;
   }
   
   public void setProvinceId(String provinceId) {
       this.provinceId = provinceId;
   }
   
   @Column(name="ADDR_C", length=512)

   public String getAddrC() {
       return this.addrC;
   }
   
   public void setAddrC(String addrC) {
       this.addrC = addrC;
   }
   
   @Column(name="ADDR_E", length=512)

   public String getAddrE() {
       return this.addrE;
   }
   
   public void setAddrE(String addrE) {
       this.addrE = addrE;
   }


/**
 * @return the countryEName
 */
@Column(name="COUNTRY_E_NAME",length=64)
public String getCountryEName() {
	return countryEName;
}


/**
 * @param countryEName the countryEName to set
 */
public void setCountryEName(String countryEName) {
	this.countryEName = countryEName;
}


/**
 * @return the provinceEName
 */
@Column(name="PROVINCE_E_NAME",length=64)
public String getProvinceEName() {
	return provinceEName;
}


/**
 * @param provinceEName the provinceEName to set
 */
public void setProvinceEName(String provinceEName) {
	this.provinceEName = provinceEName;
}


/**
 * @return the countryCName
 */
@Column(name="COUNTRY_C_NAME",length=64)
public String getCountryCName() {
	return countryCName;
}


/**
 * @param countryCName the countryCName to set
 */
public void setCountryCName(String countryCName) {
	this.countryCName = countryCName;
}


/**
 * @return the provinceCName
 */
@Column(name="PROVINCE_C_NAME",length=64)
public String getProvinceCName() {
	return provinceCName;
}


/**
 * @param provinceCName the provinceCName to set
 */
public void setProvinceCName(String provinceCName) {
	this.provinceCName = provinceCName;
}

	
   
   

   
}