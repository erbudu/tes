package com.supporter.prj.ppm.ecc.base_info.product.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 出口管制产品,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-08-19
 * @version: V1.0
 */
@MappedSuperclass
public class BaseEccProduct implements Serializable {

	private static final long serialVersionUID = 1L;
	private String productId;
	private String eccId;
	private String prjId;
	private String productC;
	private String productE;
	private String productHs;
	private String productAddC;
	private String productAddE;
	private String isProductTwoE;
	private String ednPuC;
	private String ednPuE;
	private String isEdnPpCuY;
	private String isEdnPtPuY;
	private String isRelateUsa;
	private String capitalSourceZh;
	private String capitalSourceEh;
	private String selfReview;

	/**
	 * 无参构造函数.
	 */
	public BaseEccProduct() {
	}

	/**
	 * 构造函数.
	 *
	 * @param productId
	 */
	public BaseEccProduct(String productId) {
		this.productId = productId;
	}

	/**
	 * 方法: 取得PRODUCT_ID.
	 * @return: java.lang.String PRODUCT_ID
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "PRODUCT_ID", nullable = false, length = 32)
	public String getProductId() {
		return this.productId;
	}

	/**
	 * 方法: 设置PRODUCT_ID.
	 * @param: java.lang.String PRODUCT_ID
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * 方法: 取得管制id.
	 * @return: java.lang.String 管制id
	 */
	@Column(name = "ECC_ID", nullable = true, length = 32)
	public String getEccId() {
		return this.eccId;
	}

	/**
	 * 方法: 设置管制id.
	 * @param: java.lang.String 管制id
	 */
	public void setEccId(String eccId) {
		this.eccId = eccId;
	}

	/**
	 * 方法: 取得项目id.
	 * @return: java.lang.String 项目id
	 */
	@Column(name = "PRJ_ID", nullable = true, length = 32)
	public String getPrjId() {
		return this.prjId;
	}

	/**
	 * 方法: 设置项目id.
	 * @param: java.lang.String 项目id
	 */
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	/**
	 * 方法: 取得产品名称 中文.
	 * @return: java.lang.String 产品名称 中文
	 */
	@Column(name = "PRODUCT_C", nullable = true, length = 256)
	public String getProductC() {
		return this.productC;
	}

	/**
	 * 方法: 设置产品名称 中文.
	 * @param: java.lang.String 产品名称 中文
	 */
	public void setProductC(String productC) {
		this.productC = productC;
	}

	/**
	 * 方法: 取得产品说明 产品名称 英文.
	 * @return: java.lang.String 产品说明 产品名称 英文
	 */
	@Column(name = "PRODUCT_E", nullable = true, length = 256)
	public String getProductE() {
		return this.productE;
	}

	/**
	 * 方法: 设置产品说明 产品名称 英文.
	 * @param: java.lang.String 产品说明 产品名称 英文
	 */
	public void setProductE(String productE) {
		this.productE = productE;
	}

	/**
	 * 方法: 取得产品说明 海关编码.
	 * @return: java.lang.String 产品说明 海关编码
	 */
	@Column(name = "PRODUCT_HS", nullable = true, length = 32)
	public String getProductHs() {
		return this.productHs;
	}

	/**
	 * 方法: 设置产品说明 海关编码.
	 * @param: java.lang.String 产品说明 海关编码
	 */
	public void setProductHs(String productHs) {
		this.productHs = productHs;
	}

	/**
	 * 方法: 取得零部件产地 中文.
	 * @return: java.lang.String 零部件产地 中文
	 */
	@Column(name = "PRODUCT_ADD_C", nullable = true, length = 256)
	public String getProductAddC() {
		return this.productAddC;
	}

	/**
	 * 方法: 设置零部件产地 中文.
	 * @param: java.lang.String 零部件产地 中文
	 */
	public void setProductAddC(String productAddC) {
		this.productAddC = productAddC;
	}

	/**
	 * 方法: 取得零部件产地 英文.
	 * @return: java.lang.String 零部件产地 英文
	 */
	@Column(name = "PRODUCT_ADD_E", nullable = true, length = 256)
	public String getProductAddE() {
		return this.productAddE;
	}

	/**
	 * 方法: 设置零部件产地 英文.
	 * @param: java.lang.String 零部件产地 英文
	 */
	public void setProductAddE(String productAddE) {
		this.productAddE = productAddE;
	}

	/**
	 * 方法: 取得产品说明-是否属两用范畴的敏感物项或技术.
	 * @return: java.lang.String 产品说明-是否属两用范畴的敏感物项或技术
	 */
	@Column(name = "IS_PRODUCT_TWO_E", nullable = true, length = 32)
	public String getIsProductTwoE() {
		return this.isProductTwoE;
	}

	/**
	 * 方法: 设置产品说明-是否属两用范畴的敏感物项或技术.
	 * @param: java.lang.String 产品说明-是否属两用范畴的敏感物项或技术
	 */
	public void setIsProductTwoE(String isProductTwoE) {
		this.isProductTwoE = isProductTwoE;
	}

	/**
	 * 方法: 取得最终用途 中文.
	 * @return: java.lang.String 最终用途 中文
	 */
	@Column(name = "EDN_PU_C", nullable = true, length = 256)
	public String getEdnPuC() {
		return this.ednPuC;
	}

	/**
	 * 方法: 设置最终用途 中文.
	 * @param: java.lang.String 最终用途 中文
	 */
	public void setEdnPuC(String ednPuC) {
		this.ednPuC = ednPuC;
	}

	/**
	 * 方法: 取得最终用途 英文.
	 * @return: java.lang.String 最终用途 英文
	 */
	@Column(name = "EDN_PU_E", nullable = true, length = 256)
	public String getEdnPuE() {
		return this.ednPuE;
	}

	/**
	 * 方法: 设置最终用途 英文.
	 * @param: java.lang.String 最终用途 英文
	 */
	public void setEdnPuE(String ednPuE) {
		this.ednPuE = ednPuE;
	}

	/**
	 * 方法: 取得产品说明-最终用途-出口产品性能与客户经营范围是否相符.
	 * @return: java.lang.String 产品说明-最终用途-出口产品性能与客户经营范围是否相符
	 */
	@Column(name = "IS_EDN_PP_CU_Y", nullable = true, length = 32)
	public String getIsEdnPpCuY() {
		return this.isEdnPpCuY;
	}

	/**
	 * 方法: 设置产品说明-最终用途-出口产品性能与客户经营范围是否相符.
	 * @param: java.lang.String 产品说明-最终用途-出口产品性能与客户经营范围是否相符
	 */
	public void setIsEdnPpCuY(String isEdnPpCuY) {
		this.isEdnPpCuY = isEdnPpCuY;
	}

	/**
	 * 方法: 取得产品说明-最终用途-出口产品技术特征与最终用途是否相符.
	 * @return: java.lang.String 产品说明-最终用途-出口产品技术特征与最终用途是否相符
	 */
	@Column(name = "IS_EDN_PT_PU_Y", nullable = true, length = 32)
	public String getIsEdnPtPuY() {
		return this.isEdnPtPuY;
	}

	/**
	 * 方法: 设置产品说明-最终用途-出口产品技术特征与最终用途是否相符.
	 * @param: java.lang.String 产品说明-最终用途-出口产品技术特征与最终用途是否相符
	 */
	public void setIsEdnPtPuY(String isEdnPtPuY) {
		this.isEdnPtPuY = isEdnPtPuY;
	}

	/**
	 * 方法: 取得产品说明-项目是否涉及美国因素(包括产品、技术、分包商、参与人员等，人员不能为美国公民或美国绿卡持有者).
	 * @return: java.lang.String 产品说明-项目是否涉及美国因素(包括产品、技术、分包商、参与人员等，人员不能为美国公民或美国绿卡持有者)
	 */
	@Column(name = "IS_RELATE_USA", nullable = true, length = 256)
	public String getIsRelateUsa() {
		return this.isRelateUsa;
	}

	/**
	 * 方法: 设置产品说明-项目是否涉及美国因素(包括产品、技术、分包商、参与人员等，人员不能为美国公民或美国绿卡持有者).
	 * @param: java.lang.String 产品说明-项目是否涉及美国因素(包括产品、技术、分包商、参与人员等，人员不能为美国公民或美国绿卡持有者)
	 */
	public void setIsRelateUsa(String isRelateUsa) {
		this.isRelateUsa = isRelateUsa;
	}

	/**
	 * 方法: 取得产品说明-资金来源以及汇款路径安排-中文.
	 * @return: java.lang.String 产品说明-资金来源以及汇款路径安排-中文
	 */
	@Column(name = "CAPITAL_SOURCE_ZH", nullable = true, length = 256)
	public String getCapitalSourceZh() {
		return this.capitalSourceZh;
	}

	/**
	 * 方法: 设置产品说明-资金来源以及汇款路径安排-中文.
	 * @param: java.lang.String 产品说明-资金来源以及汇款路径安排-中文
	 */
	public void setCapitalSourceZh(String capitalSourceZh) {
		this.capitalSourceZh = capitalSourceZh;
	}

	/**
	 * 方法: 取得产品说明-资金来源以及汇款路径安排-英文.
	 * @return: java.lang.String 产品说明-资金来源以及汇款路径安排-英文
	 */
	@Column(name = "CAPITAL_SOURCE_EH", nullable = true, length = 256)
	public String getCapitalSourceEh() {
		return this.capitalSourceEh;
	}

	/**
	 * 方法: 设置产品说明-资金来源以及汇款路径安排-英文.
	 * @param: java.lang.String 产品说明-资金来源以及汇款路径安排-英文
	 */
	public void setCapitalSourceEh(String capitalSourceEh) {
		this.capitalSourceEh = capitalSourceEh;
	}

	/**
	 * 方法: 取得产品说明-自我评审结果.
	 * @return: java.lang.String 产品说明-自我评审结果
	 */
	@Column(name = "SELF_REVIEW", nullable = true, length = 2048)
	public String getSelfReview() {
		return this.selfReview;
	}

	/**
	 * 方法: 设置产品说明-自我评审结果.
	 * @param: java.lang.String 产品说明-自我评审结果
	 */
	public void setSelfReview(String selfReview) {
		this.selfReview = selfReview;
	}

}
