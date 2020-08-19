package com.supporter.prj.cneec.tpc.prj_contract_settlement.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BasePrjContractSettlementProve implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "PROVE_ID", unique = true, nullable = false, length = 32)
	private String proveId;
	
	//采购合同付款id
	@Column(name="SETTLEMENT_ID" ,length=64, nullable = true)
	private String settlementId;
	
	//类型id,未获取
	@Column(name = "TYPE_ID", precision = 10, scale = 0, nullable = true)
	private int typeId;
	
	//类型名称，未获取
	@Column(name="TYPE_NAME" ,length=128, nullable = true)
	private String typeName;

	//文件名
	@Column(name="FILE_NAME" ,length=128, nullable = true)
	private String fileName;

	//文件类型
	@Column(name="FILE_CONTENT_TYPE" ,length=128, nullable = true)
	private String fileContentType;

	//文件大小
	@Column(name="FILE_SIZE" ,precision = 10, scale = 0, nullable = true)
	private double fileSize;


	public BasePrjContractSettlementProve() {
		super();
	}


	public BasePrjContractSettlementProve(String proveId, String settlementId,
			int typeId, String typeName, String fileName,
			String fileContentType, double fileSize) {
		super();
		this.proveId = proveId;
		this.settlementId = settlementId;
		this.typeId = typeId;
		this.typeName = typeName;
		this.fileName = fileName;
		this.fileContentType = fileContentType;
		this.fileSize = fileSize;
	}


	public String getProveId() {
		return proveId;
	}


	public void setProveId(String proveId) {
		this.proveId = proveId;
	}


	public String getSettlementId() {
		return settlementId;
	}


	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}


	public int getTypeId() {
		return typeId;
	}


	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}


	public String getTypeName() {
		return typeName;
	}


	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getFileContentType() {
		return fileContentType;
	}


	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}


	public double getFileSize() {
		return fileSize;
	}


	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}
	
	
	
}
