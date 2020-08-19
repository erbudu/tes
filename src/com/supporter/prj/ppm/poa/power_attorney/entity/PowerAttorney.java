package com.supporter.prj.ppm.poa.power_attorney.entity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.supporter.prj.ppm.poa.power_attorney.entity.base.BasePowerAttorney;
import com.supporter.util.CodeTable;

/**   
 * @Title: Entity
 * @Description: 功能模块
 * @author GuoXiansheng
 * @date 2019-09-25 09:25:07
 * @version V1.0   
 *
 */
@Entity
@Table(name="PPM_LETTER_AUTHORITY",schema="")
public class PowerAttorney extends BasePowerAttorney {

	private static final long serialVersionUID = 1L;

	private String personType;
	@Transient
	public String getPersonType() {
		return personType;
	}
	public void setPersonType(String personType) {
		this.personType = personType;
	}

	//被授权人姓名格式化
	private String personName;
	private String personNameDesc;
	private String personNameCardDesc;

	@Transient
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	@Transient
	public String getPersonNameDesc() {
		return personNameDesc;
	}
	public void setPersonNameDesc(String personNameDesc) {
		this.personNameDesc = personNameDesc;
	}
	@Transient
	public String getPersonNameCardDesc() {
		return personNameCardDesc;
	}
	public void setPersonNameCardDesc(String personNameCardDesc) {
		this.personNameCardDesc = personNameCardDesc;
	}

	public static final int INITIAL = 0; //上传盖章文件初始化
	public static final int UPLOADED = 1; //上传盖章文件已上传

	public static final int DRAFT = 0; //草稿
	public static final int PROCESSING = 1; //审核中 
	public static final int COMPLETE = 2; //审核完成
	public static final int REJECTED = 3; //驳回

	/**
	 * 获取状态码表.
	 * @return
	 */
	public static CodeTable getApplyStatusCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(DRAFT, "草稿");
		lcdtbl_Return.insertItem(PROCESSING, "审核中");
		lcdtbl_Return.insertItem(COMPLETE, "审核完成");
		return lcdtbl_Return;
	}

	public static Map<Integer, String> getStatusCodeTable(){
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		map.put(PROCESSING, "审核中");
		map.put(COMPLETE, "审核完成");
		return map;
	}
	//业务对照map
	public static Map<String, String> getLaBusinessTypeTable(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("xmcsdl", "项目初始登陆");
		map.put("ckgzsc", "出口管制审查");
		map.put("tybbajxk", "投议标备案及许可");
		map.put("tbqps", "投标前评审");
		map.put("zhtqy", "主合同签约");
		map.put("zhtsx", "主合同生效");
		map.put("htjxy", "合同及协议");
		map.put("kxzf", "款项支付");
		map.put("zyhd", "重要活动");
		map.put("xmgbjjh", "项目关闭及激活");
		return map;
	}

	private String laReasonC;//
	private String laReasonE;//
	private String laDesc;//
	private String laReason;//

	@Transient
	public String getLaReasonC() {
		return laReasonC;
	}
	public void setLaReasonC(String laReasonC) {
		this.laReasonC = laReasonC;
	}
	@Transient
	public String getLaReasonE() {
		return laReasonE;
	}
	public void setLaReasonE(String laReasonE) {
		this.laReasonE = laReasonE;
	}
	@Transient
	public String getLaDesc() {
		return laDesc;
	}
	public void setLaDesc(String laDesc) {
		this.laDesc = laDesc;
	}
	@Transient
	public String getLaReason() {
		return laReason;
	}
	public void setLaReason(String laReason) {
		this.laReason = laReason;
	}

	//流程参数
	@Transient
	public int getDbYear(){
		return 0;
	}

	@Transient
	public String getModuleId(){//应用编号
		return "PWRATTORNEY";
	}

	@Transient
	public String getDomainObjectId(){//业务对象
		return "LAENTITY";
	}

	@Transient
	public String getEntityId(){//业务主键
		return this.getLaId();
	}

	/*@Transient
	public String getId() {//业务主键
		return this.getLaId();
	}*/

	@Transient
	public String getCompanyNo(){//公司编码

		return "";
	}

	@Transient
	public String getModuleBusiType(){//应用业务类型
		return "";
	}

	@Transient
	public String getEntityName(){//业务实体类名称
		return this.getClass().getName();
	}
}
