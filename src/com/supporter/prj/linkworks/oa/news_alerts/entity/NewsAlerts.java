package com.supporter.prj.linkworks.oa.news_alerts.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.news_alerts.entity.base.BaseNewsAlerts;
import com.supporter.util.CodeTable;


/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @version V1.0   
 * @author linxiaosong
 */
@Entity
@Table(name="OA_NEWS_ALERTS",schema="")
public class NewsAlerts extends BaseNewsAlerts{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int DRAFT = 0; //草稿
    public static final int PUBLISHED = 1; //已发布
    
    public static final int PIAOFU = 0; //飘浮
    public static final int TANCHU = 1; //自由弹出.

	/**
	 * 获取报告的状态码表.
	 * @return
	 */
	public static CodeTable getPublishStatusCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(DRAFT,"草稿");
        lcdtbl_Return.insertItem(PUBLISHED,"已发布");
		return lcdtbl_Return;
	}
	
	public static Map<Integer, String> getStatusCodeTable(){
    	Map<Integer, String> map = new LinkedHashMap<Integer, String>();
    	map.put(DRAFT, "草稿");
    	map.put(PUBLISHED, "已发布");
		return map;
	}
	
    /**
     * 获取发布状态码表.
     * @return
     */
    public static CodeTable getDisplayTypeCodeTable() {
        CodeTable lcdtbl_Return = new CodeTable();
        lcdtbl_Return.insertItem(PIAOFU,"自由飘浮");
        lcdtbl_Return.insertItem(TANCHU,"自由弹出");
        
        return lcdtbl_Return;
    }
    
    public static Map<Integer, String> getTypeCodeTable(){
    	Map<Integer, String> map = new LinkedHashMap<Integer, String>();
    	map.put(PIAOFU, "自由漂浮");
    	map.put(TANCHU, "自由弹出");
		return map;
	}

    
    @Transient
	private NewsAlertsContent newsAlertsContent;

    //发布状态
    @Transient
	private String publishStatusDesc;
    //显示方式
    @Transient
	private String displayTypeDesc;
    //部门
    @Transient
	private String deptName;
    
    //图片附件
    @Transient
    private List<String> images;
    
	//详情页显示可下载附件
    @Transient
    private String filesInView;
    
	public String getPublishStatusDesc() {
		publishStatusDesc = getPublishStatusCodeTable().getDisplay(this.getPublishStatus());
		return publishStatusDesc;
	}

	public void setPublishStatusDesc(String publishStatusDesc) {
		this.publishStatusDesc = publishStatusDesc;
	}

	public String getDisplayTypeDesc() {
		displayTypeDesc = getDisplayTypeCodeTable().getDisplay(this.getDisplayType());
		return displayTypeDesc;
	}

	public void setDisplayTypeDesc(String displayTypeDesc) {
		this.displayTypeDesc = displayTypeDesc;
	}

	public NewsAlertsContent getNewsAlertsContent() {
		return newsAlertsContent;
	}

	public void setNewsAlertsContent(NewsAlertsContent newsAlertsContent) {
		this.newsAlertsContent = newsAlertsContent;
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
    
    
    
}
