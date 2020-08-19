package com.supporter.prj.eip.code_share.code.constant;


/**
 * @Description: 常量类.
 * @author Administrator
 * @date 2018-05-14 10:10:04
 * @version V1.0
 *
 */
public interface ContractConstant {
	int SUB_CONTRACT_COUNT_INIT = 100; //一般采购合同按项目和年度自动生成100个
	int SUB_CONTRACT_COUNT_MIN = 5; //如果剩余少于5个可用编号时，系统自动生成。
	int SUB_CONTRACT_COUNT_GROW = 20; //系统自动以20个为基数自动生成
	
	int SUB_ATTACH_COUNT_INIT = 2; //附属合同按项目和年度自动生成2个
	int SUB_ATTACH_COUNT_MIN = 1; //如果剩余少于1个可用编号时，系统自动生成。
	int SUB_ATTACH_COUNT_GROW = 2; //系统自动以2个为基数自动生成
	
	int SPARE_PARTS_COUNT_INIT = 50; //备品备件合同按项目和年度自动生成50个
	int SPARE_PARTS_COUNT_MIN = 5; //如果剩余少于5个可用编号时，系统自动生成。
	int SPARE_PARTS_COUNT_GROW = 10; //系统自动以10个为基数自动生成
	
	int SPORADIC_COUNT_INIT = 50; //零星采购合同按项目和年度自动生成50个
	int SPORADIC_COUNT_MIN = 5; //如果剩余少于5个可用编号时，系统自动生成。
	int SPORADIC_COUNT_GROW = 10; //系统自动以10个为基数自动生成
	
	String MODULE_ID_CONTRACT = "NOCONTRACT"; //合同
	String CATEGORY2_SUB_CONTRACT = "subContract"; //合同二级分类-一般分包合同
	String CATEGORY2_SUB_ATTACH = "subAttach"; //合同二级分类-一般分包合同.附属合同
	String CATEGORY2_SPARE_PARTS = "spareParts"; //合同二级分类-备品备件合同
	String CATEGORY2_SPORADIC = "sporadic"; //合同二级分类-零星采购合同

}
