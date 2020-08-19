package com.supporter.prj.linkworks.oa.roll_information.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.roll_information.entity.base.BaseRollInformation;
import com.supporter.util.CodeTable;



/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @version V1.0   
 * @author linxiaosong
 */
@Entity
@Table(name="OA_ROLL_INFORMATION",schema="")
public class RollInformation extends BaseRollInformation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//信息状态
	public static final int DRAFT = 0; //草稿
    public static final int PUBLISHED = 1; //已发布
    
    
    public static final int HANDLER = 0; //手动创建
    public static final int AUTO = 1; //自抓取创建.
    
    //标题颜色
    public static final String RED = "red";
    public static final String BLUE = "blue";
    public static final String FUCHSIA = "fuchsia";
    public static final String GREEN = "green";
    public static final String BLACK = "black";
    public static final String PURPLE = "purple";
    
    
    /**
	 * 获取标题颜色码表.
	 * @return
	 */
	public static CodeTable getInforTitleCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(RED, "红色");
		lcdtbl_Return.insertItem(BLUE, "蓝色");
		lcdtbl_Return.insertItem(FUCHSIA, "紫色");
		lcdtbl_Return.insertItem(GREEN, "绿色");
		lcdtbl_Return.insertItem(BLACK, "黑色");
		lcdtbl_Return.insertItem(PURPLE, "粉色");
		return lcdtbl_Return;
	}
	
	/**
	 * 获取标题颜色码表.
	 * @return
	 */
	public static Map<String, String> getInforTitleCodetable(){
    	Map<String, String> map = new LinkedHashMap<String, String>();
    	map.put(RED, "红色");
    	map.put(BLUE, "蓝色");
    	map.put(FUCHSIA, "紫色");
    	map.put(GREEN, "绿色");
    	map.put(BLACK, "黑色");
    	map.put(PURPLE, "粉色");
		return map;
	}
    
    //信息内容
    @Transient
	private RollInformationContent rollInformationContent;
    //状态
    @Transient
	private String status;
    //发布部门
    @Transient
    private String deptName;
    //详情页中下载文件
    @Transient
    private String filesInView;
    //图片
    @Transient
    private List<String> images;
    //滚动信息预览时，判断图片样式
    @Transient
    private boolean isNew;
    
    
	public RollInformationContent getRollInformationContent() {
		return rollInformationContent;
	}

	public void setRollInformationContent(
			RollInformationContent rollInformationContent) {
		this.rollInformationContent = rollInformationContent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getFilesInView() {
		return filesInView;
	}

	public void setFilesInView(String filesInView) {
		this.filesInView = filesInView;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
    
    

}
