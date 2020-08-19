package com.supporter.prj.linkworks.oa.salary.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * BaseSalary entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@MappedSuperclass
public class BaseSalary  implements java.io.Serializable {


    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String recordId;
     private String empId;
     private String empName;
     private String deptId;
     private String deptName;
     private String salaryMonth;
     private Double xueLiZhiCheng;
     private Double ziGeBuTie;
     private Double gongLing;
     private Double siLing;
     private Double fuLiBuTile;
     private Double fangBu;
     private Double quNuanBuTie;
     private Double gangWeiGongZi;
     private Double buChongYangLao;
     private Double buFaYangLao;
     private Double yuZhiJiangJin;
     private Double baoXiaoYaoFei;
     private Double qiTaBuFa;
     private Double yingFaHeJi;
     private Double gongZiKouKuan;
     private Double wuCanKouKuan;
     private Double fangZu;
     private Double zhuFangGongJiJin;
     private Double kouBuChongYangLao;
     private Double yangLaoBaoXian;
     private Double shiYeBaoXian;
     private Double yiLiaoBaoXian;
     private Double qiTaKouKuan;
     private Double yaoFei;
     private Double daiKouShui;
     private Double kouKuanHeJi;
     private Double shiFaHeJi;
     private Double yiCiXingJiangJin;//项目一次性奖金
     private String empNo;
     private String salaryMonth1;
     private String isPartyMember;//是否是党员
     private Double jiaoTongFei;//交通费
     private Double tongXunFei;//通讯费


    // Constructors

    /** default constructor */
    public BaseSalary() {
    }

	/** minimal constructor */
    public BaseSalary(String recordId) {
        this.recordId = recordId;
    }
    
    /** full constructor */
    public BaseSalary(String recordId, String empId, String empName, String deptId, String deptName, String salaryMonth, Double xueLiZhiCheng, Double ziGeBuTie, Double gongLing, Double siLing, Double fuLiBuTile, Double fangBu, Double quNuanBuTie, Double gangWeiGongZi, Double buChongYangLao, Double buFaYangLao, Double yuZhiJiangJin, Double baoXiaoYaoFei, Double qiTaBuFa, Double yingFaHeJi, Double gongZiKouKuan, Double wuCanKouKuan, Double fangZu, Double zhuFangGongJiJin, Double kouBuChongYangLao, Double yangLaoBaoXian, Double shiYeBaoXian, Double yiLiaoBaoXian, Double qiTaKouKuan, Double yaoFei, Double daiKouShui, Double kouKuanHeJi, Double shiFaHeJi,Double yiCiXingJiangJin, String empNo, String salaryMonth1,String isPartyMember,Double jiaoTongFei,Double tongXunFei) {
        this.recordId = recordId;
        this.empId = empId;
        this.empName = empName;
        this.deptId = deptId;
        this.deptName = deptName;
        this.salaryMonth = salaryMonth;
        this.xueLiZhiCheng = xueLiZhiCheng;
        this.ziGeBuTie = ziGeBuTie;
        this.gongLing = gongLing;
        this.siLing = siLing;
        this.fuLiBuTile = fuLiBuTile;
        this.fangBu = fangBu;
        this.quNuanBuTie = quNuanBuTie;
        this.gangWeiGongZi = gangWeiGongZi;
        this.buChongYangLao = buChongYangLao;
        this.buFaYangLao = buFaYangLao;
        this.yuZhiJiangJin = yuZhiJiangJin;
        this.baoXiaoYaoFei = baoXiaoYaoFei;
        this.qiTaBuFa = qiTaBuFa;
        this.yingFaHeJi = yingFaHeJi;
        this.gongZiKouKuan = gongZiKouKuan;
        this.wuCanKouKuan = wuCanKouKuan;
        this.fangZu = fangZu;
        this.zhuFangGongJiJin = zhuFangGongJiJin;
        this.kouBuChongYangLao = kouBuChongYangLao;
        this.yangLaoBaoXian = yangLaoBaoXian;
        this.shiYeBaoXian = shiYeBaoXian;
        this.yiLiaoBaoXian = yiLiaoBaoXian;
        this.qiTaKouKuan = qiTaKouKuan;
        this.yaoFei = yaoFei;
        this.daiKouShui = daiKouShui;
        this.kouKuanHeJi = kouKuanHeJi;
        this.shiFaHeJi = shiFaHeJi;
        this.yiCiXingJiangJin=yiCiXingJiangJin;//项目一次性奖金
        this.empNo = empNo;
        this.salaryMonth1 = salaryMonth1;
        this.isPartyMember=isPartyMember;//是否是党员
        this.jiaoTongFei=jiaoTongFei;//是否是党员
        this.tongXunFei=tongXunFei;//是否是党员
    }

   
    // Property accessors
    @Id 
    
    @Column(name="RECORD_ID", unique=true, nullable=false, length=32)

    public String getRecordId() {
        return this.recordId;
    }
    
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
    
    @Column(name="EMP_ID", length=32)

    public String getEmpId() {
        return this.empId;
    }
    
    public void setEmpId(String empId) {
        this.empId = empId;
    }
    
    @Column(name="EMP_NAME", length=32)

    public String getEmpName() {
        return this.empName;
    }
    
    public void setEmpName(String empName) {
        this.empName = empName;
    }
    
    @Column(name="DEPT_ID", length=32)

    public String getDeptId() {
        return this.deptId;
    }
    
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    
    @Column(name="DEPT_NAME", length=64)

    public String getDeptName() {
        return this.deptName;
    }
    
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    
    @Column(name="SALARY_MONTH", length=128)

    public String getSalaryMonth() {
        return this.salaryMonth;
    }
    
    public void setSalaryMonth(String salaryMonth) {
        this.salaryMonth = salaryMonth;
    }
    
    @Column(name="XUE_LI_ZHI_CHENG", precision=18, scale=3)

    public Double getXueLiZhiCheng() {
        return this.xueLiZhiCheng;
    }
    
    public void setXueLiZhiCheng(Double xueLiZhiCheng) {
        this.xueLiZhiCheng = xueLiZhiCheng;
    }
    
    @Column(name="ZI_GE_BU_TIE", precision=18, scale=3)

    public Double getZiGeBuTie() {
        return this.ziGeBuTie;
    }
    
    public void setZiGeBuTie(Double ziGeBuTie) {
        this.ziGeBuTie = ziGeBuTie;
    }
    
    @Column(name="GONG_LING", precision=18, scale=3)

    public Double getGongLing() {
        return this.gongLing;
    }
    
    public void setGongLing(Double gongLing) {
        this.gongLing = gongLing;
    }
    
    @Column(name="SI_LING", precision=18, scale=3)

    public Double getSiLing() {
        return this.siLing;
    }
    
    public void setSiLing(Double siLing) {
        this.siLing = siLing;
    }
    
    @Column(name="FU_LI_BU_TILE", precision=18, scale=3)

    public Double getFuLiBuTile() {
        return this.fuLiBuTile;
    }
    
    public void setFuLiBuTile(Double fuLiBuTile) {
        this.fuLiBuTile = fuLiBuTile;
    }
    
    @Column(name="FANG_BU", precision=18, scale=3)

    public Double getFangBu() {
        return this.fangBu;
    }
    
    public void setFangBu(Double fangBu) {
        this.fangBu = fangBu;
    }
    
    @Column(name="QU_NUAN_BU_TIE", precision=18, scale=3)

    public Double getQuNuanBuTie() {
        return this.quNuanBuTie;
    }
    
    public void setQuNuanBuTie(Double quNuanBuTie) {
        this.quNuanBuTie = quNuanBuTie;
    }
    
    @Column(name="GANG_WEI_GONG_ZI", precision=18, scale=3)

    public Double getGangWeiGongZi() {
        return this.gangWeiGongZi;
    }
    
    public void setGangWeiGongZi(Double gangWeiGongZi) {
        this.gangWeiGongZi = gangWeiGongZi;
    }
    
    @Column(name="BU_CHONG_YANG_LAO", precision=18, scale=3)

    public Double getBuChongYangLao() {
        return this.buChongYangLao;
    }
    
    public void setBuChongYangLao(Double buChongYangLao) {
        this.buChongYangLao = buChongYangLao;
    }
    
    @Column(name="BU_FA_YANG_LAO", precision=18, scale=3)

    public Double getBuFaYangLao() {
        return this.buFaYangLao;
    }
    
    public void setBuFaYangLao(Double buFaYangLao) {
        this.buFaYangLao = buFaYangLao;
    }
    
    @Column(name="YU_ZHI_JIANG_JIN", precision=18, scale=3)

    public Double getYuZhiJiangJin() {
        return this.yuZhiJiangJin;
    }
    
    public void setYuZhiJiangJin(Double yuZhiJiangJin) {
        this.yuZhiJiangJin = yuZhiJiangJin;
    }
    
    @Column(name="BAO_XIAO_YAO_FEI", precision=18, scale=3)

    public Double getBaoXiaoYaoFei() {
        return this.baoXiaoYaoFei;
    }
    
    public void setBaoXiaoYaoFei(Double baoXiaoYaoFei) {
        this.baoXiaoYaoFei = baoXiaoYaoFei;
    }
    
    @Column(name="QI_TA_BU_FA", precision=18, scale=3)

    public Double getQiTaBuFa() {
        return this.qiTaBuFa;
    }
    
    public void setQiTaBuFa(Double qiTaBuFa) {
        this.qiTaBuFa = qiTaBuFa;
    }
    
    @Column(name="YING_FA_HE_JI", precision=18, scale=3)

    public Double getYingFaHeJi() {
        return this.yingFaHeJi;
    }
    
    public void setYingFaHeJi(Double yingFaHeJi) {
        this.yingFaHeJi = yingFaHeJi;
    }
    
    @Column(name="GONG_ZI_KOU_KUAN", precision=18, scale=3)

    public Double getGongZiKouKuan() {
        return this.gongZiKouKuan;
    }
    
    public void setGongZiKouKuan(Double gongZiKouKuan) {
        this.gongZiKouKuan = gongZiKouKuan;
    }
    
    @Column(name="WU_CAN_KOU_KUAN", precision=18, scale=3)

    public Double getWuCanKouKuan() {
        return this.wuCanKouKuan;
    }
    
    public void setWuCanKouKuan(Double wuCanKouKuan) {
        this.wuCanKouKuan = wuCanKouKuan;
    }
    
    @Column(name="FANG_ZU", precision=18, scale=3)

    public Double getFangZu() {
        return this.fangZu;
    }
    
    public void setFangZu(Double fangZu) {
        this.fangZu = fangZu;
    }
    
    @Column(name="ZHU_FANG_GONG_JI_JIN", precision=18, scale=3)

    public Double getZhuFangGongJiJin() {
        return this.zhuFangGongJiJin;
    }
    
    public void setZhuFangGongJiJin(Double zhuFangGongJiJin) {
        this.zhuFangGongJiJin = zhuFangGongJiJin;
    }
    
    @Column(name="KOU_BU_CHONG_YANG_LAO", precision=18, scale=3)

    public Double getKouBuChongYangLao() {
        return this.kouBuChongYangLao;
    }
    
    public void setKouBuChongYangLao(Double kouBuChongYangLao) {
        this.kouBuChongYangLao = kouBuChongYangLao;
    }
    
    @Column(name="YANG_LAO_BAO_XIAN", precision=18, scale=3)

    public Double getYangLaoBaoXian() {
        return this.yangLaoBaoXian;
    }
    
    public void setYangLaoBaoXian(Double yangLaoBaoXian) {
        this.yangLaoBaoXian = yangLaoBaoXian;
    }
    
    @Column(name="SHI_YE_BAO_XIAN", precision=18, scale=3)

    public Double getShiYeBaoXian() {
        return this.shiYeBaoXian;
    }
    
    public void setShiYeBaoXian(Double shiYeBaoXian) {
        this.shiYeBaoXian = shiYeBaoXian;
    }
    
    @Column(name="YI_LIAO_BAO_XIAN", precision=18, scale=3)

    public Double getYiLiaoBaoXian() {
        return this.yiLiaoBaoXian;
    }
    
    public void setYiLiaoBaoXian(Double yiLiaoBaoXian) {
        this.yiLiaoBaoXian = yiLiaoBaoXian;
    }
    
    @Column(name="QI_TA_KOU_KUAN", precision=18, scale=3)

    public Double getQiTaKouKuan() {
        return this.qiTaKouKuan;
    }
    
    public void setQiTaKouKuan(Double qiTaKouKuan) {
        this.qiTaKouKuan = qiTaKouKuan;
    }
    
    @Column(name="YAO_FEI", precision=18, scale=3)

    public Double getYaoFei() {
        return this.yaoFei;
    }
    
    public void setYaoFei(Double yaoFei) {
        this.yaoFei = yaoFei;
    }
    
    @Column(name="DAI_KOU_SHUI", precision=18, scale=3)

    public Double getDaiKouShui() {
        return this.daiKouShui;
    }
    
    public void setDaiKouShui(Double daiKouShui) {
        this.daiKouShui = daiKouShui;
    }
    
    @Column(name="KOU_KUAN_HE_JI", precision=18, scale=3)

    public Double getKouKuanHeJi() {
        return this.kouKuanHeJi;
    }
    
    public void setKouKuanHeJi(Double kouKuanHeJi) {
        this.kouKuanHeJi = kouKuanHeJi;
    }
    
    @Column(name="SHI_FA_HE_JI", precision=18, scale=3)

    public Double getShiFaHeJi() {
        return this.shiFaHeJi;
    }
    
    public void setShiFaHeJi(Double shiFaHeJi) {
        this.shiFaHeJi = shiFaHeJi;
    }
    
    @Column(name="EMP_NO", length=32)

    public String getEmpNo() {
        return this.empNo;
    }
    
    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }
    
    @Column(name="SALARY_MONTH1")

    public String getSalaryMonth1() {
        return this.salaryMonth1;
    }
    
    public void setSalaryMonth1(String salaryMonth1) {
        this.salaryMonth1 = salaryMonth1;
    }
    @Column(name="YI_CI_XING_JIANG_JIN", precision=18, scale=3)
	public Double getYiCiXingJiangJin() {
		return yiCiXingJiangJin;
	}

	public void setYiCiXingJiangJin(Double yiCiXingJiangJin) {
		this.yiCiXingJiangJin = yiCiXingJiangJin;
	}
	@Column(name="IS_PARTY_MEMBER")
	public String getIsPartyMember() {
		return isPartyMember;
	}

	public void setIsPartyMember(String isPartyMember) {
		this.isPartyMember = isPartyMember;
	}

	@Column(name="JIAO_TONG_FEI", precision=18, scale=3)
	
    public Double getJiaoTongFei() {
		return jiaoTongFei;
	}

	public void setJiaoTongFei(Double jiaoTongFei) {
		this.jiaoTongFei = jiaoTongFei;
	}

	@Column(name="TONG_XUN_FEI", precision=18, scale=3)
	
	public Double getTongXunFei() {
		return tongXunFei;
	}

	public void setTongXunFei(Double tongXunFei) {
		this.tongXunFei = tongXunFei;
	}
	

   








}