package com.supporter.prj.ppm.template_register.entity;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.template_register.entity.base.BaseTemplateRegisterDetail;
import com.supporter.prj.eip_service.module.entity.IModule;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author gongjiwen
 * @date 2016-12-05 10:25:07
 * @version V1.0   
 *
 */
@Entity
@Table(name = "PPM_TEMPLATE_REGISTER_DETAIL", schema = "")
public class TemplateRegisterDetail extends BaseTemplateRegisterDetail {

	private static final long serialVersionUID = 1L;
	
	private List<TemplateRegisterTable> tableList;
	private String delItemIds;

	private List<TemplateRegisterDetail> reviewKeyPoint;
	private boolean add;// 是否新增
	private String examResult;
	private String opinionDesc; 
	
	@Transient
	public String getExamResult() {
		return examResult;
	}

	public void setExamResult(String examResult) {
		this.examResult = examResult;
	}
	
	@Transient
	public String getOpinionDesc() {
		return opinionDesc;
	}

	public void setOpinionDesc(String opinionDesc) {
		this.opinionDesc = opinionDesc;
	}
	
	@Transient
	public String getDelItemIds() {
		return delItemIds;
	}

	public void setDelItemIds(String delItemIds) {
		this.delItemIds = delItemIds;
	}
	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}
	/**
	 * 无参构造函数
	 */
	public TemplateRegisterDetail() {
		super();
	}
	
	
	public enum AuthManageType{
		normal(IModule.AuthManageType.NORMAL,"普通角色&权限项方式"),byAdmin(IModule.AuthManageType.BY_ADMIN,"管理员权限"),none(IModule.AuthManageType.NONE,"不控制");
		private int key;
		private String value;
		AuthManageType(int key, String value){
			this.key = key;
			this.value = value;
		}
		public static  int getKeyByValue(String value){
			AuthManageType[] types = AuthManageType.values();
			for(AuthManageType type : types){
				if(StringUtils.equals(value, type.getValue())){
					return type.getKey();
				}
			}
			return 0;
		}
		public static String getValueByKey(int key){
			AuthManageType[] types = AuthManageType.values();
			for(AuthManageType type : types){
				if(key == type.getKey()){
					return type.getValue();
				}
			}
			return null;
		}
		public int getKey() {
			return key;
		}
		public void setKey(int key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	/**
	 * 
	 * 日志操作类型
	 * @author gongjiwen
	 * @createDate 2017年3月7日
	 * @since TODO: 来源版本
	 * @modifier gongjiwen
	 * @modifiedDate 2017年3月7日
	 */
	public enum LogOper{
		MODULE_ADD("新建应用"),MODULE_EDIT("编辑应用"),MODULE_DEL("删除应用"),MODULE_CHANGE_DISPLAY_ORDER("调整应用显示顺序");
		private String operName;
		LogOper(String operName){
			this.operName = operName;
		}
		public String getOperName() {
			return operName;
		}
		public void setOperName(String operName) {
			this.operName = operName;
		}
		
	}
	/**
	 * 构造函数
	 * @param detailId
	 */
	public TemplateRegisterDetail(String detailId) {
		super(detailId);
	}

	public boolean equals(Object other) {
		if (!(other instanceof TemplateRegisterDetail)) {
			return false;
		}
		TemplateRegisterDetail castOther = (TemplateRegisterDetail) other;
		return StringUtils.equals(this.getDetailId(), castOther.getDetailId());
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getDetailId()).toHashCode();
	}
	
	public static Map<String, String> getContentTypeCodeTable() {
		/*List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PPM_CONTENT_TYPE");
		Map<String, String> map = new HashMap<String, String>();
		if(items != null && !items.isEmpty()) {
			for(IComCodeTableItem item : items) {
				map.put(item.getExtField1(), item.getDisplayName());
			}
		}*/
		//输入、列表点选、日期控件、自动识别、读入、数据读入、无
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("INPUT", "输入");
		map.put("LIST", "列表点选");
		map.put("DATE", "日期控件");
		map.put("LISTORINPUT", "选项或输入");
		//map.put("AUTO", "自动识别");
		//map.put("READ", "读入");
		map.put("DATAREAD", "数据读入");
		map.put("NOTHING", "无");
		return map;
	}
	
	/**
	 * 
	 * @return
	 */
	public static Map<String, String> getContentTypeByTableCodeTable() {
		/*List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PPM_CONTENT_TYPE");
		Map<String, String> map = new HashMap<String, String>();
		if(items != null && !items.isEmpty()) {
			for(IComCodeTableItem item : items) {
				map.put(item.getExtField1(), item.getDisplayName());
			}
		}*/
		//输入、列表点选、日期控件、自动识别、读入、数据读入、无
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("INPUT", "输入");
		map.put("LIST", "列表点选");
		map.put("DATE", "日期控件");
		//map.put("AUTO", "自动识别");
		//map.put("DATAREAD", "数据读入");
		//map.put("NOTHING", "无");
		return map;
	}
	
	public static Map<Integer, String> getDirectoryStructureCodeTable() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		/**map.put("正文文本", "正文文本");
		map.put("1级", "1级");
		map.put("2级", "2级");
		map.put("3级", "3级");
		map.put("4级", "4级");
		map.put("5级", "5级");
		map.put("6级", "6级");
		map.put("7级", "7级");
		map.put("8级", "8级");
		map.put("9级", "9级");*/
		map.put(10, "正文文本");
		map.put(1, "1级");
		map.put(2, "2级");
		map.put(3, "3级");
		map.put(4, "4级");
		map.put(5, "5级");
		map.put(6, "6级");
		map.put(7, "7级");
		map.put(8, "8级");
		map.put(9, "9级");
		return map;
	}

	@Transient
	public String getDirectoryStructureDesc() {
		return getDirectoryStructureCodeTable().get(this.getDirectoryStructure());
	}
	
	@Transient
	public String getContentTypeDesc() {
		return getContentTypeCodeTable().get(this.getContentType());
	}
	
	@Transient
	public List<TemplateRegisterTable> getTableList() {
		return tableList;
	}

	public void setTableList(List<TemplateRegisterTable> tableList) {
		this.tableList = tableList;
	}
	
	@Transient
	public List<TemplateRegisterDetail> getReviewKeyPoint() {
		return reviewKeyPoint;
	}

	public void setReviewKeyPoint(List<TemplateRegisterDetail> reviewKeyPoint) {
		this.reviewKeyPoint = reviewKeyPoint;
	}

}
