package com.supporter.prj.linkworks.oa.salary_pwd.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;


/**
 * BaseSalaryPwd entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@MappedSuperclass
public class BaseSalaryPwd  implements java.io.Serializable {


    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String salaryId;
     private String salaryPwd;
     private String salaryName;
     private String userName;


    // Constructors

    /** default constructor */
    public BaseSalaryPwd() {
    }

	/** minimal constructor */
    public BaseSalaryPwd(String salaryId) {
        this.salaryId = salaryId;
    }
    
    /** full constructor */
    public BaseSalaryPwd(String salaryId, String salaryPwd, String salaryName, String userName) {
        this.salaryId = salaryId;
        this.salaryPwd = salaryPwd;
        this.salaryName = salaryName;
        this.userName = userName;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="SALARY_ID", unique=true, nullable=false, length=32)

    public String getSalaryId() {
        return this.salaryId;
    }
    
    public void setSalaryId(String salaryId) {
        this.salaryId = salaryId;
    }
    
    @Column(name="SALARY_PWD", length=50)

    public String getSalaryPwd() {
        return this.salaryPwd;
    }
    
    public void setSalaryPwd(String salaryPwd) {
        this.salaryPwd = salaryPwd;
    }
    
    @Column(name="SALARY_NAME", length=30)

    public String getSalaryName() {
        return this.salaryName;
    }
    
    public void setSalaryName(String salaryName) {
        this.salaryName = salaryName;
    }
    
    @Column(name="USER_NAME", length=64)

    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
   








}