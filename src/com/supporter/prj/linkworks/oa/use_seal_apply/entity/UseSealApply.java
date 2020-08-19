package com.supporter.prj.linkworks.oa.use_seal_apply.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.util.CodeTable;
import com.supporter.util.CommonUtil;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.util.CommonUtils;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.linkworks.oa.use_seal_apply.dao.UseSealApplyContentDao;
import com.supporter.prj.linkworks.oa.use_seal_apply.entity.base.BaseUseSealApply;

@Entity
@Table(name="OA_USE_SEAL_APPLY"
    ,schema=""
)
public class UseSealApply  extends BaseUseSealApply{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static final int CHANGE = 1;	//流程变化
	public static final int NOCHANGE = 0;	//流程未变化
	
	public static final String CHEN = "1";
	public static final String LU = "2";
	public static final String YANG = "3";
	
	public static final int DRAFT = 0; 		//草稿
	public static final int PROCESSING = 1;	//审核中
	public static final int COMPLETE = 2;	//审批完成
	public static final int REJECTED = 3; //驳回
	public static final int ALL_STATUS = 9;	//全部
	//用印种类1
	 public static final int COMPANY = 1;	//公司公章
	 public static final int LEGAL = 2;	//法定代表人名章
	 public static final int BUSSINES= 3;////经营管理部预审人员
	 public static final int INITDATA= 0;//初始化值
	 
	 public static CodeTable getCodeTable1(){
		 CodeTable ct = new CodeTable();
		 ct.insertItem(0, "");
		 ct.insertItem(COMPANY, "公司公章");
		 ct.insertItem(LEGAL, "法定代表人名章");
		 return ct;
	 }
	 
	 
	//用印种类2 
	 public static final int LEG = 1;	//只盖法人章
	 public static final int LEGANDG = 2;	//盖法人章和公章
	 public static final int GZ = 3;	//只盖公章不需要经营管理部预审
	 public static final int GANDJY = 4;	//只盖公章需要经营管理部预审
	 public static final int GANDSQ = 5;	//盖公章和代理报关授权人手签章   20180314改为 储运工作用印
	 public static final int JWZ = 6;	//纪委章
	 
	 public static final String LEGS = "1";	//只盖法人章
	 public static final String LEGANDGS = "2";	//盖法人章和公章
	 public static final String GZS = "3";	//只盖公章不需要经营管理部预审
	 public static final String GANDJYS = "4";	//只盖公章需要经营管理部预审
	 public static final String GANDSQS = "5";	//盖公章和代理报关授权人手签章 20180314改为 储运工作用印
	 public static final String JWZS = "6";	//纪委章
	 
	 public static CodeTable getCodeTable2(){
		 CodeTable ct = new CodeTable();
		 ct.insertItem(LEG, "只盖法人章");
		 ct.insertItem(LEGANDG, "盖法人章和公章");
		 ct.insertItem(GZ, "只盖公章不需要经营管理部预审");
		 ct.insertItem(GANDJY, "只盖公章需要经营管理部预审");
		 ct.insertItem(GANDSQ, "盖公章和代理报关授权人手签章");
		 ct.insertItem(JWZ, "纪委章");
		 //ct.insertItem(GANDSQ, "储运工作用印");
		 return ct;
	 }
	 
	 /**
	    * 获取用印的状态码表.
	    * @return
	    */
	   public static CodeTable getApplyStatusCodeTable() {
	       CodeTable lcdtbl_Return = new CodeTable();
	       lcdtbl_Return.insertItem(DRAFT,"草稿");
	       lcdtbl_Return.insertItem(PROCESSING,"审核中");
	       lcdtbl_Return.insertItem(COMPLETE,"审批完成");
	       lcdtbl_Return.insertItem(REJECTED, "驳回");
	       return lcdtbl_Return;
	   }
	   
		
		public static Map<Integer, String> getStatusCodeTable(){
	    	Map<Integer, String> map = new LinkedHashMap<Integer, String>();
	    	map.put(DRAFT, "草稿");
	    	map.put(PROCESSING, "审核中");
	    	map.put(COMPLETE, "审核完成");
	    	map.put(REJECTED, "驳回");
			return map;
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
				if (CommonUtil.trim(String.valueOf(this.getStorageAndTransPretrial())).equals("1")){
					sealTypeDesc = "盖公章、报关专用章、代理报关授权人手签章";
				}else if (CommonUtil.trim(String.valueOf(this.getStorageAndTransPretrial())).equals("2")){
					sealTypeDesc = "网签代理报关委托协议";
				}else if (CommonUtil.trim(String.valueOf(this.getStorageAndTransPretrial())).equals("3")){
					sealTypeDesc = "盖公章和公司中英文名称章";
				}else if (CommonUtil.trim(String.valueOf(this.getStorageAndTransPretrial())).equals("4")){
					sealTypeDesc = "公章(储运业务文件)";
				}else {
					sealTypeDesc = "公章和代理报关授权人手签章";
				}
				//sealTypeDesc = "储运工作用印";
				
			}
			
			
			return CommonUtil.trim(sealTypeDesc);
		}
	   
	   
	   
	   
	   
	private boolean add;
	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}	
	//从表1
	private UseSealApplyContent useSealApplyContent;
	@Transient
	public UseSealApplyContent getUseSealApplyContent() {
		return useSealApplyContent;
	}

	public void setUseSealApplyContent(UseSealApplyContent useSealApplyContent) {
		this.useSealApplyContent = useSealApplyContent;
	}
	//从表2
	private String  useSealApplyBoardDesc;
	@Transient
	public String getUseSealApplyBoardDesc() {
		return useSealApplyBoardDesc;
	}

	public void setUseSealApplyBoardDesc(String useSealApplyBoardDesc) {
		this.useSealApplyBoardDesc = useSealApplyBoardDesc;
	}
	
	private String applyStatusDesc;
	@Transient
	public String getApplyStatusDesc() {
		return getApplyStatusCodeTable().getDisplay(this.getApplyStatus());
	}

	public void setApplyStatusDesc(String applyStatusDesc) {
		this.applyStatusDesc = applyStatusDesc;
	}
	//用印种类（用于编辑回显）
	private String sealTypeIds = "";
	@Transient
	public String getSealTypeIds() {
		return sealTypeIds;
	}

	public void setSealTypeIds(String sealTypeIds) {
		this.sealTypeIds = sealTypeIds;
	}
	private String businessPretrials = "";
	@Transient
	public String getBusinessPretrials() {
		return businessPretrials;
	}

	public void setBusinessPretrials(String businessPretrials) {
		this.businessPretrials = businessPretrials;
	}
	
	private String storageAndTransPretrials = "";
	@Transient
	public String getStorageAndTransPretrials() {
		return storageAndTransPretrials;
	}

	public void setStorageAndTransPretrials(String storageAndTransPretrials) {
		this.storageAndTransPretrials = storageAndTransPretrials;
	}
	
	
	//会签人（用于编辑回显）
	private String notifierIds = "";
	private String notifierNames = "";
	
	//分管公司领导（用于编辑回显）
	private String examIds = "";
	private String examNames = "";
	@Transient
	public String getNotifierIds() {
		return notifierIds;
	}

	public void setNotifierIds(String notifierIds) {
		this.notifierIds = notifierIds;
	}
	@Transient
	public String getNotifierNames() {
		return notifierNames;
	}

	public void setNotifierNames(String notifierNames) {
		this.notifierNames = notifierNames;
	}
	@Transient
	public String getExamIds() {
		return examIds;
	}

	public void setExamIds(String examIds) {
		this.examIds = examIds;
	}
	@Transient
	public String getExamNames() {
		return examNames;
	}

	public void setExamNames(String examNames) {
		this.examNames = examNames;
	}
	
	//用印事由
	private String useSealReasonDesc;
	@Transient
	public String getUseSealReasonDesc() {
		return useSealReasonDesc;
	}
	
	//流程标题用的用印事由
	@Transient
	public String getUseSealReasonTitle(){
		UseSealApplyContentDao useSealApplyContentDao = (UseSealApplyContentDao)SpringContextHolder.getBean(UseSealApplyContentDao.class);
		UseSealApplyContent content = useSealApplyContentDao.get(this.getApplyId());
		if (content != null){
			String reason = content.getUseSealReason();
			if (reason != null){
				if (reason.length() <= 30){
					return reason;
				}else{
					return reason.substring(0, 30) + "...";
				}
			}
		}
		return "";
	}

	public void setUseSealReasonDesc(String useSealReasonDesc) {
		this.useSealReasonDesc = useSealReasonDesc;
	}
	
	//申请部门加申请人
	private String deptAndApplyPersonDesc;
	@Transient
	public String getDeptAndApplyPersonDesc() {
		return deptAndApplyPersonDesc;
	}

	public void setDeptAndApplyPersonDesc(String deptAndApplyPersonDesc) {
		this.deptAndApplyPersonDesc = deptAndApplyPersonDesc;
	}
	
	//附件查看能用到
	private String files;

	@Transient
	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}
	

	//声明流程用到的参数
	@Transient
	public int getDbYear()
    {
        return 0;
    }
	@Transient
    public String getDomainObjectId()
    {
        return "UseSealApply";
    }
    @Transient
    public String getEntityName()
    {
        return getClass().getName();
    }
    @Transient
    public String getModuleId()
    {
        return "OAUSAPPLY";
    }
    @Transient
    public String getModuleBusiType()
    {
        return "";
    }
    @Transient
    public String getCompanyNo()
    {
        return getApplyDeptId();
    }


    




	
	
	
}
