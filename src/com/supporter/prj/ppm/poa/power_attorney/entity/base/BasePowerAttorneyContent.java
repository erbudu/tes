package com.supporter.prj.ppm.poa.power_attorney.entity.base;

import java.sql.Clob;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**   
*
* @author GuoXiansheng
* @date 2019-09-25 09:40:04
* @version V1.0   
*
*/
@MappedSuperclass
public class BasePowerAttorneyContent  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

     private String laId;   //主键
     private Clob laReasonC;   //授权书中文内容
     private Clob laReasonE;    //授权书英文内容
     private Clob laDesc;   //备注

	// Constructors
    /** default constructor */
    public BasePowerAttorneyContent() {
    }

	/** minimal constructor */
    public BasePowerAttorneyContent(String laId) {
        this.laId = laId;
    }
    
    /** full constructor */
    public BasePowerAttorneyContent(String laId, Clob laReasonC, Clob laReasonE, Clob laDesc) {
		super();
		this.laId = laId;
		this.laReasonC = laReasonC;
		this.laReasonE = laReasonE;
		this.laDesc = laDesc;
	}
    // Property accessors
    @Id 
    @Column(name="LA_ID", unique=true, nullable=false, length=32)
    public String getLaId() {
		return laId;
	}
	public void setLaId(String laId) {
		this.laId = laId;
	}
	@Column(name="LA_REASON_C")
	public Clob getLaReasonC() {
		return laReasonC;
	}
	public void setLaReasonC(Clob laReasonC) {
		this.laReasonC = laReasonC;
	}
	@Column(name="LA_REASON_E")
	public Clob getLaReasonE() {
		return laReasonE;
	}
	public void setLaReasonE(Clob laReasonE) {
		this.laReasonE = laReasonE;
	}
	@Column(name="LA_DESC")
	public Clob getLaDesc() {
		return laDesc;
	}
	public void setLaDesc(Clob laDesc) {
		this.laDesc = laDesc;
	}

}