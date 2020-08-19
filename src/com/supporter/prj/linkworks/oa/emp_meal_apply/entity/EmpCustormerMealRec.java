package com.supporter.prj.linkworks.oa.emp_meal_apply.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.emp_meal_apply.entity.base.BaseEmpCustormerMealRec;
import com.supporter.util.CodeTable;
import com.supporter.util.CommonUtil;
@Entity
@Table(name = "OA_EMP_CUSTORMER_MEAL_REC", schema = "")
public class EmpCustormerMealRec extends BaseEmpCustormerMealRec{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 public static String CUSTOMER_CARD = "1";
	    public static String PUBLIC_CARD = "2";
	    public static String CUSTOMER_CARD_DESC = "客饭卡";
	    public static String PUBLIC_CARD_DESC = "公用卡";
	    /**
	     * 人员类型。
	     * @return
	     */
	    @Transient
	    public static CodeTable getEmpTypeCodeTable(){
	    	CodeTable empTypeCode = new CodeTable();
	    	empTypeCode.insertItem(CUSTOMER_CARD,CUSTOMER_CARD_DESC,0);
	    	empTypeCode.insertItem(PUBLIC_CARD,PUBLIC_CARD_DESC,1);
	    	return empTypeCode;
	    }
	    @Transient
	    public String  getEmpTypeDesc(){ 
	    	if(CommonUtil.trim(this.getEmpType()).equals(CUSTOMER_CARD)){
	    		return CUSTOMER_CARD_DESC;
	    	}else{
	    		return PUBLIC_CARD_DESC;
	    	}
	    }
}
