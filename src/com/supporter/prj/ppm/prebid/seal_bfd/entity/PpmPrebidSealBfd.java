package com.supporter.prj.ppm.prebid.seal_bfd.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.prebid.seal_bfd.entity.base.BasePpmPrebidSealBfd;

/**
 * 标前评审盖章版的资料清单
 * @author 王康
 *
 */
@Entity
@Table(name = "PPM_PREBID_SEAL_BFD", catalog = "")
public class PpmPrebidSealBfd extends BasePpmPrebidSealBfd{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String MODULE_ID = "SEALBFD"; // 应用模块的id
	public static final String DOMAIN_OBJECT_ID = "SEALBFD"; // 业务对象id
	public static final String BUSI_TYPE = "sealBfdFile"; // 附件注册
	private String fileInfo;//上传附件成功后返回的附件信息

	@Transient
	public String getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}

	private List<PpmPrebidSealBfd> dataList;
	

	@Transient
	public List<PpmPrebidSealBfd> getDataList() {
		return dataList;
	}

	public void setDataList(List<PpmPrebidSealBfd> dataList) {
		this.dataList = dataList;
	}
}
