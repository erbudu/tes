package com.supporter.prj.ppm.poa.power_attorney.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**   
*
* @author GuoXiansheng
* @date 2019-09-25 09:57:37
* @version V1.0   
*
*/
@MappedSuperclass
public class BasePowerAttorneyPerson  implements java.io.Serializable {
    // Fields
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String laPersonId;   //主键
	private String laId;   //外键（授权书主键）
	private String personType;   //人员类型（10：内部人员 20：外部人员）
	private String authPersonName;   //姓名（只有内部人员才有值）
	private String passportNo;   //护照号码
	private String authPersonLastname;   //国际姓氏
	private String authPersonFirstname;   //国际名字
	private String gender;   //性别
    
    // Constructors

    /** default constructor */
    public BasePowerAttorneyPerson() {
    }

	/** minimal constructor */
    public BasePowerAttorneyPerson(String laPersonId) {
        this.laPersonId = laPersonId;
    }
    
    /** full constructor */
    public BasePowerAttorneyPerson(String laPersonId, String laId, String personType, String authPersonName,
			String passportNo, String authPersonLastname, String authPersonFirstname, String gender) {
		super();
		this.laPersonId = laPersonId;
		this.laId = laId;
		this.personType = personType;
		this.authPersonName = authPersonName;
		this.passportNo = passportNo;
		this.authPersonLastname = authPersonLastname;
		this.authPersonFirstname = authPersonFirstname;
		this.gender = gender;
	}
    
    // Property accessors
    @Id 
    @Column(name="LA_PERSON_ID", unique=true, nullable=false, length=32)
    public String getLaPersonId() {
		return laPersonId;
	}
	public void setLaPersonId(String laPersonId) {
		this.laPersonId = laPersonId;
	}
	@Column(name="LA_ID", length=32)
	public String getLaId() {
		return laId;
	}
	public void setLaId(String laId) {
		this.laId = laId;
	}
	@Column(name="PERSON_TYPE", length=2)
	public String getPersonType() {
		return personType;
	}
	public void setPersonType(String personType) {
		this.personType = personType;
	}
	@Column(name="AUTH_PERSON_NAME", length=128)
	public String getAuthPersonName() {
		return authPersonName;
	}
	public void setAuthPersonName(String authPersonName) {
		this.authPersonName = authPersonName;
	}
	@Column(name="PASSPORT_NO", length=32)
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	@Column(name="AUTH_PERSON_LASTNAME", length=128)
	public String getAuthPersonLastname() {
		return authPersonLastname;
	}
	public void setAuthPersonLastname(String authPersonLastname) {
		this.authPersonLastname = authPersonLastname;
	}
	@Column(name="AUTH_PERSON_FIRSTNAME", length=128)
	public String getAuthPersonFirstname() {
		return authPersonFirstname;
	}
	public void setAuthPersonFirstname(String authPersonFirstname) {
		this.authPersonFirstname = authPersonFirstname;
	}
	@Column(name="GENDER", length=3)
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
}