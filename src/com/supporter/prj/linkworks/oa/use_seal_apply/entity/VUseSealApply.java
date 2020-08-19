package com.supporter.prj.linkworks.oa.use_seal_apply.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.use_seal_apply.entity.base.BaseVUseSealApply;
import com.supporter.util.CodeTable;
import com.supporter.util.CommonUtil;
@Entity
@Table(name="OA_V_USE_SEAL_APPLY"
    ,schema=""
)
public class VUseSealApply  extends BaseVUseSealApply{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int DRAFT = 0; 		//草稿
	public static final int PROCESSING = 1;	//审核中
	public static final int EFFECTIVE = 2;	//审批完成
	public static final int REJECTED = 3; //驳回
	public static final int ALL_STATUS = 9;	//全部
	 
	//用印种类2 	 
	 public static final String LEGS = "1";	//只盖法人章
	 public static final String LEGANDGS = "2";	//盖法人章和公章
	 public static final String GZS = "3";	//只盖公章不需要经营管理部预审
	 public static final String GANDJYS = "4";	//只盖公章需要经营管理部预审
	 public static final String GANDSQS = "5";	//盖公章和代理报关授权人手签章
	 public static final String JWZS = "6";  //纪委章
	 
	 
	 /**
	    * 获取用印的状态码表.
	    * @return
	    */
	   public static CodeTable getApplyStatusCodeTable() {
	       CodeTable lcdtbl_Return = new CodeTable();
	       lcdtbl_Return.insertItem(DRAFT,"草稿");
	       lcdtbl_Return.insertItem(PROCESSING,"审核中");
	       lcdtbl_Return.insertItem(EFFECTIVE,"审批完成");
	       lcdtbl_Return.insertItem(REJECTED, "驳回");
	       return lcdtbl_Return;
	   }
	   
		
	   	   
		//用印种类描述
	   private String sealTypeDesc;
	   public void setSealTypeDesc(String sealTypeDesc) {
		this.sealTypeDesc = sealTypeDesc;
	   }

	   @Transient
		public String getSealTypeDesc(){
			String sealTypeDesc = "";
			if(CommonUtil.trim(String.valueOf(this.getSealTypeId())).equals(CommonUtil.trim(LEGS))){
				sealTypeDesc = "法人章";
			}else if(CommonUtil.trim(String.valueOf(this.getSealTypeId())).equals(CommonUtil.trim(LEGANDGS))){
				sealTypeDesc = "法人章和公章";
			}else if(CommonUtil.trim(String.valueOf(this.getSealTypeId())).equals(CommonUtil.trim(GZS))){
				sealTypeDesc = "公章";
			}else if(CommonUtil.trim(String.valueOf(this.getSealTypeId())).equals(CommonUtil.trim(GANDJYS))){
				sealTypeDesc = "公章";
			}else if(CommonUtil.trim(String.valueOf(this.getSealTypeId())).equals(CommonUtil.trim(JWZS))){
				sealTypeDesc = "纪委章";
			}else{
				sealTypeDesc = "公章和代理报关授权人手签章";
			}
			
			
			return CommonUtil.trim(sealTypeDesc);
		}
	   
	
	private String applyStatusDesc;
	@Transient
	public String getApplyStatusDesc() {
		return getApplyStatusCodeTable().getDisplay(this.getApplyStatus());
	}

	public void setApplyStatusDesc(String applyStatusDesc) {
		this.applyStatusDesc = applyStatusDesc;
	}
	

}
