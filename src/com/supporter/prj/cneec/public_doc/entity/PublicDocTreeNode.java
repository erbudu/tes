package com.supporter.prj.cneec.public_doc.entity;

/**
 * 文档树节点.
 * @author Administrator
 *
 */
public class PublicDocTreeNode {
	private int nodeType = 1; //节点类型:1=分类节点；2：文件夹节点；3：文件节点；4：链接节点
	private String id;
	private String pid;
	private String name;
	private String url;
	
	private String key; //关键字，类似于innerName
	private boolean checked;
	private boolean open;
	
	private boolean nocheck; //是否隐藏树节点的checkbox
	private boolean disabled; //节点是否可用
	
	private String icon;
	private String uploadFileId;
	private boolean auth = false;
	private boolean expanded = false;
	
	public PublicDocTreeNode(String id, String key, String pid, String name, String url,
			boolean checked, boolean open, boolean nocheck, boolean disabled, int nodeType) {
		super();
		this.id = id;
		this.key = key;
		this.pid = pid;
		this.name = name;
		this.url = url;
		this.checked = checked;
		this.open = open;
		this.nocheck = nocheck;
		this.disabled = disabled;
		this.nodeType = nodeType;
	} 

	public PublicDocTreeNode() {
		super();
	}
	
	
	public int getNodeType() {
		return this.nodeType;
	}
	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public boolean getNocheck() {
		return nocheck;
	}
	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}


	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public boolean getDisabled() {
		return disabled;
	}

	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}

	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public boolean getOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getIcon() {
		return this.icon;
	}
	public void setIcon(String aicon) {
		this.icon = aicon;
	}
	
	public boolean getChkDisabled() {
		return false;
	}
	
	public java.lang.String getUploadFileId() {
		return this.uploadFileId;
	}
	public void setUploadFileId(java.lang.String uploadFileId) {
		this.uploadFileId = uploadFileId;
	}
	
	public boolean getAuth() {
		return auth;
	}
	public void setAuth(boolean auth) {
		this.auth = auth;
	}
	
	public boolean getExpanded() {
		return this.expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	
	/**
	 * 节点类型.
	 * @author Administrator
	 *
	 */
	public static final class NodeType {
		public static final int CATEGORY = 1; // 分类
		public static final int FOLDER = 2; // 文件夹
		public static final int FILE = 3; // 文件
		public static final int URL = 4; // 文件
	}
}
