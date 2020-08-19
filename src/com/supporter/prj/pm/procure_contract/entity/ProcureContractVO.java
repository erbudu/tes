package com.supporter.prj.pm.procure_contract.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 采购合同VO.
 * @author Administrator
 *
 */
public class ProcureContractVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ProcureContract procureContract;
	public ProcureContract getProcureContract() {
		return this.procureContract;
	}
	public void setProcureContract(ProcureContract procureContract) {
		this.procureContract = procureContract;
	}

	//付款条款
	private List<ProcureContractPay> collectionTermsList;
	private String delTermsIds;
	public List<ProcureContractPay> getCollectionTermsList() {
		return collectionTermsList;
	}
	public void setCollectionTermsList(List<ProcureContractPay> collectionTermsList) {
		this.collectionTermsList = collectionTermsList;
	}
	public String getDelTermsIds() {
		return delTermsIds;
	}
	public void setDelTermsIds(String delTermsIds) {
		this.delTermsIds = delTermsIds;
	}

	//采购内容
	private List<ProcureContractContent> goodsList;
	private String delGoodsIds;
	public List<ProcureContractContent> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<ProcureContractContent> goodsList) {
		this.goodsList = goodsList;
	}
	public String getDelGoodsIds() {
		return delGoodsIds;
	}
	public void setDelGoodsIds(String delGoodsIds) {
		this.delGoodsIds = delGoodsIds;
	}
	
	//工程部位
	private List<ProcureContractSite> sitesList;
	private String delSitesIds;
	public List<ProcureContractSite> getSitesList() {
		return sitesList;
	}
	public void setSitesList(List<ProcureContractSite> sitesList) {
		this.sitesList = sitesList;
	}
	public String getDelSitesIds() {
		return delSitesIds;
	}
	public void setDelSitesIds(String delSitesIds) {
		this.delSitesIds = delSitesIds;
	}

}
