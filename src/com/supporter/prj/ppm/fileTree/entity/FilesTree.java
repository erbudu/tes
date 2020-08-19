package com.supporter.prj.ppm.fileTree.entity;

/**
 * @autor 颜丙超
 * @date 2019/11/14
 */
public class FilesTree {
    public static String MODULE_ID = "PpmFileTree";
    public static String BUSI_TYPE = "FilesTree";
    private String fileType;//文件类型
    private String fileName;//附件名称
    private String id;//id
    private String fileId;
    private String parent;//父id
    private boolean isLeaf;//是否是叶子节点
    private int level;//级别
    private boolean must ; //是否必须上传
    private boolean needFile;//是否需要附件
    public FilesTree() {
    }

    public FilesTree(String fileType, String fileName, String id, String parent, boolean isLeaf, int level, boolean must, boolean needFile) {
        this.fileType = fileType;
        this.fileName = fileName;
        this.id = id;
        this.parent = parent;
        this.isLeaf = isLeaf;
        this.level = level;
        this.must = must;
        this.needFile = needFile;
    }

    public boolean getNeedFile() {
        return needFile;
    }

    public void setNeedFile(boolean needFile) {
        this.needFile = needFile;
    }



    public boolean getMust() {
        return must;
    }

    public void setMust(boolean must) {
        this.must = must;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
