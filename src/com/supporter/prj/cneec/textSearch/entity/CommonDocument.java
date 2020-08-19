package com.supporter.prj.cneec.textSearch.entity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * �ļ���ʵ����.
 * @author qy
 *
 */
public class CommonDocument {

	private static final long serialVersionUID = 1L;
	
	//主题新闻
	public static final  int NEWS = 6;
	//公司公告
	public static final  int BULLETIN = 7;
	//文章栏
	public static final int ARTICLE = 8;
	//文件柜
	public static final int FILEBOX = 9;
	//所有
	public static final int ALL = 10;
	
	public static Map<Integer, String> getSourceCodetable() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(ALL, "全部");
		map.put(NEWS, "主题新闻");
		map.put(BULLETIN, "公司公告");
		map.put(ARTICLE, "文章栏");
		map.put(FILEBOX, "文件柜");
		return map;
	}
	
	/*[CONSTRUCTOR MARKER BEGIN]*/
	public CommonDocument () {
		initialize();
	}
	
	protected void initialize () {}
	/*[CONSTRUCTOR MARKER END]*/
	
	private int hashCode = Integer.MIN_VALUE;
	
	private String fileId;
	private String is_Name;
	private String is_Path;
	private String is_Content;
	private String display;
	private String folder;
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId){
		this.fileId = fileId;
	}
	public String getName() {
		return is_Name;
	}
	public void setName(String as_Name) {
		this.is_Name = as_Name;
	}
	public String getPath(){
		return is_Path;
	}
	public void setPath(String as_Path){
		this.is_Path = as_Path;
	}
	public String getContent() {
		return is_Content;
	}
	public void setContent(String as_Content) {
		this.is_Content = as_Content;
	}
	
	
	
	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getPath()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getPath().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}
}
