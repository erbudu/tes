package com.supporter.prj.ppm.ecc.project_ecc.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.ecc.base_info.customer.dao.EccCustomerDao;
import com.supporter.prj.ppm.ecc.base_info.customer.dao.EccCustomerFsoDao;
import com.supporter.prj.ppm.ecc.base_info.customer.dao.EccCustomerPartnerDao;
import com.supporter.prj.ppm.ecc.base_info.customer.dao.EccCustomerWarnDao;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomer;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomerFso;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomerPartner;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomerWarn;
import com.supporter.prj.ppm.ecc.base_info.owner.dao.EccOwnerDao;
import com.supporter.prj.ppm.ecc.base_info.owner.dao.EccOwnerFsoDao;
import com.supporter.prj.ppm.ecc.base_info.owner.dao.EccOwnerPartnerDao;
import com.supporter.prj.ppm.ecc.base_info.owner.entity.EccOwner;
import com.supporter.prj.ppm.ecc.base_info.owner.entity.EccOwnerFso;
import com.supporter.prj.ppm.ecc.base_info.owner.entity.EccOwnerPartner;
import com.supporter.prj.ppm.ecc.base_info.partner.dao.EccPartnerDao;
import com.supporter.prj.ppm.ecc.base_info.partner.dao.EccPartnerFsoDao;
import com.supporter.prj.ppm.ecc.base_info.partner.dao.EccPartnerPDao;
import com.supporter.prj.ppm.ecc.base_info.partner.dao.EccPartnerWarnDao;
import com.supporter.prj.ppm.ecc.base_info.partner.entity.EccPartner;
import com.supporter.prj.ppm.ecc.base_info.partner.entity.EccPartnerFso;
import com.supporter.prj.ppm.ecc.base_info.partner.entity.EccPartnerP;
import com.supporter.prj.ppm.ecc.base_info.partner.entity.EccPartnerWarn;
import com.supporter.prj.ppm.ecc.base_info.product.dao.EccProductDao;
import com.supporter.prj.ppm.ecc.base_info.product.entity.EccProduct;
import com.supporter.prj.ppm.ecc.cac_review.dao.EccCacRevieConDao;
import com.supporter.prj.ppm.ecc.cac_review.entity.EccCacRevieCon;
import com.supporter.prj.ppm.ecc.dept_review.dao.EccDeptRevieConDao;
import com.supporter.prj.ppm.ecc.dept_review.entity.EccDeptRevieCon;
import com.supporter.prj.ppm.ecc.group_review.dao.EccGroupRevieConDao;
import com.supporter.prj.ppm.ecc.group_review.entity.EccGroupRevieCon;
import com.supporter.prj.ppm.ecc.project_ecc.dao.EccDao;
import com.supporter.prj.ppm.ecc.project_ecc.entity.Ecc;
import com.supporter.prj.ppm.ecc.project_ecc.util.ExcelUtils;
import com.supporter.prj.ppm.ecc.wg_review.dao.EccWgRevieConDao;
import com.supporter.prj.ppm.ecc.wg_review.entity.EccWgRevieCon;
import com.supporter.prj.ppm.prj_org.base_info.dao.PrjAddrDao;
import com.supporter.prj.ppm.prj_org.base_info.dao.PrjDao;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.entity.PrjAddr;
import com.supporter.util.CommonUtil;

/**
 * @Title: Service
 * @Description: 出口管制.
 * @author YAN
 * @date 2019-08-16 18:23:05
 * @version V1.0
 */
@Service
public class EccService {

	@Autowired
	private EccDao eccDao;
	@Autowired
	private PrjDao prjDao;
	@Autowired
	private EccCustomerDao eccCustomerDao;
	@Autowired
	private EccPartnerDao eccPartnerDao;
	@Autowired
	private EccOwnerDao eccOwnerDao;
	@Autowired
	private EccProductDao eccProductDao;
	@Autowired
	private PrjAddrDao prjAddrDao;
	@Autowired
	private EccCustomerPartnerDao eccCustomerPartnerDao;
	@Autowired
	private EccCustomerFsoDao eccCustomerFsoDao;
	@Autowired
	private EccCustomerWarnDao eccCustomerWarnDao;
	@Autowired
	private EccPartnerPDao eccPartnerPDao;
	@Autowired
	private EccPartnerFsoDao eccPartnerFsoDao;
	@Autowired
	private EccPartnerWarnDao eccPartnerWarnDao;
	@Autowired
	private EccOwnerPartnerDao eccOwnerPartnerDao;
	@Autowired
	private EccOwnerFsoDao eccOwnerFsoDao;
	@Autowired
	private EccDeptRevieConDao eccDeptRevieConDao;
	@Autowired
	private EccWgRevieConDao eccWgRevieConDao;
	@Autowired
	private EccCacRevieConDao eccCacRevieConDao;
	@Autowired
	private EccGroupRevieConDao eccGroupRevieConDao;

	/**
	 * 根据主键获取出口管制.
	 * 
	 * @param eccId 主键
	 * @return Ecc
	 */
	public Ecc get(String eccId) {
		return eccDao.get(eccId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user      用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<Ecc> getGrid(UserProfile user, JqGrid jqGrid, Ecc ecc) {
		return eccDao.findPage(jqGrid, ecc);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param eccId
	 * @return
	 */
	public Ecc initEditOrViewPage(String eccId) {
		if (StringUtils.isBlank(eccId)) {// 新建
			Ecc entity = new Ecc();
			return entity;
		} else {// 编辑
			Ecc entity = eccDao.get(eccId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param ecc  实体类
	 * @return
	 */
	public Ecc saveOrUpdate(UserProfile user, Ecc ecc) {
		if (StringUtils.isBlank(ecc.getEccId())) {// 新建
			return this.save(user, ecc);
		} else {// 编辑
			return this.update(user, ecc);
		}
	}

	/**
	 * 保存.
	 * 
	 * @param user 用户信息
	 * @param ecc  实体类
	 * @return
	 */
	private Ecc save(UserProfile user, Ecc ecc) {
		this.eccDao.save(ecc);
		return ecc;
	}

	/**
	 * 更新.
	 * 
	 * @param user 用户信息
	 * @param ecc  实体类
	 * @return
	 */
	private Ecc update(UserProfile user, Ecc ecc) {
		Ecc eccDb = this.eccDao.get(ecc.getEccId());
		if (eccDb == null) {
			return this.save(user, ecc);
		} else {
			ModuleUtils.copyPropertiesExcludeNullProperty(ecc, eccDb);
			this.eccDao.update(eccDb);
			return eccDb;
		}

	}

	/**
	 * 删除
	 * 
	 * @param user   用户信息
	 * @param eccIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String eccIds) {
		if (StringUtils.isNotBlank(eccIds)) {
			for (String eccId : eccIds.split(",")) {
				Ecc eccDb = this.eccDao.get(eccId);
				this.eccDao.delete(eccId);
			}
		}
	}

	/**
	 * 根据项目id保存.
	 * 
	 * @param prjId 项目id
	 * @param isEcc 是否管制 0需要 1不需要
	 * @return
	 */
	public Ecc saveByPrjId(String prjId, int isEcc) {
		List<Ecc> ecc = this.eccDao.findBy("prjId", prjId);
		Ecc e = new Ecc();
		if (ecc != null && ecc.size() > 0) {
			e = ecc.get(0);
			e.setIsEcc(isEcc);
			this.eccDao.update(e);
			return ecc.get(0);
		}
		e.setEccId(com.supporter.util.UUIDHex.newId());
		e.setPrjId(prjId);
		e.setIsEcc(isEcc);
		this.eccDao.save(e);
		return e;
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param eccId
	 * @param eccName
	 * @return
	 */
	public boolean nameIsValid(String eccId, String eccName) {
		return this.eccDao.nameIsValid(eccId, eccName);
	}

	/**
	 * 获取某项目是否出口管制完成
	 * 
	 * @param prjId 项目id
	 * @return true-完成，false-未完成
	 */
	public boolean prjIsEccComplete(String prjId) {
		List<Ecc> eccList = this.eccDao.findBy("prjId", prjId);
		if (CollectionUtils.isNotEmpty(eccList)) {
			int status = eccList.get(0).getStatus();
			if (status == Ecc.StatusCodeTable.COMPLETE) {
				return true;
			}
		}
		return false;
	}

	public List<Ecc> findBy(String prop, String value) {
		return this.eccDao.findBy(prop, value);
	}

	/**
	 * 根据项目id获取管制信息
	 * 
	 * @param prjId
	 * @return
	 */
	public Ecc initByPrjId(String prjId) {
		List<Ecc> eccs = this.findBy("prjId", prjId);
		if (eccs != null && eccs.size() > 0) {
			return eccs.get(0);
		}
		return null;
	}

	public void update(Ecc ecc) {
		this.eccDao.update(ecc);
	}

	public void updateEcc(String eccId, int status, String result) {
		Ecc ecc = this.eccDao.get(eccId);
		if (ecc != null) {
			ecc.setStatus(status);
			if (StringUtils.isNotBlank(result)) {
				ecc.setResult(result);
			}
			this.eccDao.update(ecc);
		}
	}

	/**
	 * @author chenxiaobin
	 * @param response
	 * @param request
	 * @param prjId
	 * @param formType 表单类型，如：敏感地区订单出口管制审核表（ReviewFormForOrderExportControl.xlsx）
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void exportExcel(HttpServletResponse response, HttpServletRequest request, String prjId, String formType)
			throws Exception {
		ServletOutputStream sops = response.getOutputStream();
		// 获取项目
		Prj prj = prjDao.get(prjId);
		PrjAddr prjAddr = prjAddrDao.findUniqueResult("prjId", prjId);
		// 根据prjId获取出口管制
		Ecc ecc = eccDao.findUniqueResult("prjId", prjId);
		// 根据eccId获取客户list集合
		List<EccCustomer> ls_EccCustomer = eccCustomerDao.findBy("eccId", CommonUtil.trim(ecc.getEccId()));
		System.out.println("客户：" + ls_EccCustomer.size());
		// 根据eccId获取中间商、代理和运输公司list集合
		List<EccPartner> ls_EccPartner = eccPartnerDao.findBy("eccId", CommonUtil.trim(ecc.getEccId()));
		System.out.println("中间商：" + ls_EccPartner.size());
		// 根据eccId获取最终用户list集合
		List<EccOwner> ls_EccOwner = eccOwnerDao.findBy("eccId", CommonUtil.trim(ecc.getEccId()));
		System.out.println("最终用户：" + ls_EccOwner.size());
		// 根据eccId获取产品list集合
		List<EccProduct> ls_EccProduct = eccProductDao.findBy("eccId", CommonUtil.trim(ecc.getEccId()));
		System.out.println("产品：" + ls_EccProduct.size());

		// 获取工程根路径
		String realPath = request.getRealPath(File.separator);
		System.out.println("工程路径：" + realPath);
		// 模板文件夹路径
		String srcDirPath = realPath + "/ppm/ecc/templates";
		// 临时文件路径
		String tempDirPath = realPath + "/ppm/ecc/templates/temp";
		// 获取模板文件
		String templateFileName = formType + ".xlsx";
		File srcFile = new File(srcDirPath + "/" + templateFileName);
		File tempFile = new File(tempDirPath + "/" + formType + System.currentTimeMillis() + ".xlsx");
		// 赋值模板
		FileUtils.copyFile(srcFile, tempFile);
		// POI读取模板文件
		if (srcFile.exists()) {
			// 读取模板文件，xlsx文件
			XSSFWorkbook srcWb = new XSSFWorkbook(new FileInputStream(tempFile));
			// 读取第一个sheet
			XSSFSheet srcSheet = srcWb.getSheetAt(0);
			// 创建新的sheet
			XSSFSheet destSheet = srcWb.createSheet("敏感地区订单出口管制审核表");
			destSheet.setDefaultColumnWidth(20);

			// 根据模板绘制新的dest_Sheet
			// 绘制表头
			this.createHeader(srcSheet, destSheet);

			// 绘制项目基本信息
			this.createPrj(prj, prjAddr, srcSheet, destSheet);

			// 绘制客户基本情况
			int eccCustomerSrcStartRowNum = 11;
			int eccCustomerDestStartRowNum = 11;
			int eccCustomerRowCount = 23;
			this.createEccCustomer(ls_EccCustomer, srcSheet, destSheet, eccCustomerSrcStartRowNum,
					eccCustomerDestStartRowNum, eccCustomerRowCount);

			// 绘制中间商、代理和运输公司的基本情况（如有）
			int eccPartnerSrcStartRowNum = 34;
			int eccPartnerDestStartRowNum = ls_EccCustomer.size() * eccCustomerRowCount + eccCustomerDestStartRowNum;
			int eccPartnerRowCount = 23;
			this.createEccPartner(ls_EccPartner, srcSheet, destSheet, eccPartnerSrcStartRowNum,
					eccPartnerDestStartRowNum, eccPartnerRowCount);

			// 最终用户（业主）
			int eccOwnerSrcStartRowNum = 57;
			int eccOwnerDestStartRowNum = eccPartnerDestStartRowNum + ls_EccPartner.size() * eccPartnerRowCount;
			int eccOwnerRowCount = 18;
			this.createEccOwner(ls_EccOwner, srcSheet, destSheet, eccOwnerSrcStartRowNum, eccOwnerDestStartRowNum,
					eccOwnerRowCount);

			// 产品说明
			int eccProductSrcStartRowNum = 75;
			int eccProductDestStartRowNum = eccOwnerDestStartRowNum + ls_EccOwner.size() * eccOwnerRowCount;
			int eccProductRowCount = 14;
			this.createEccProduct(ls_EccProduct, srcSheet, destSheet, eccProductSrcStartRowNum,
					eccProductDestStartRowNum, eccProductRowCount);

			// 绘制底部
			int bottomSrcStartRowNum = 89;
			int bottomDestStartRowNum = eccProductDestStartRowNum + ls_EccProduct.size() * eccProductRowCount;
			int bottomRowCount = 20;
			this.createBottom(ecc, srcSheet, destSheet, bottomSrcStartRowNum, bottomDestStartRowNum, bottomRowCount);

			// 删除第一个sheet
			srcWb.removeSheetAt(0);
			// 写入sheet到文件
			srcWb.write(new FileOutputStream(tempFile));
			// 去掉第一个sheet
			XSSFWorkbook srcWbFinal = new XSSFWorkbook(new FileInputStream(tempFile));
			srcWbFinal.removeSheetAt(0);
			srcWbFinal.write(new FileOutputStream(tempFile));
			// 下载excel
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-disposition",
					"attachment;filename=" + URLEncoder.encode("敏感地区订单出口管制审核表.xlsx", "utf-8"));
			FileInputStream lfis_Source = new FileInputStream(tempFile);
			byte[] larrbyte_Buffer = new byte[1024];
			int li_Bytes = 0;
			while ((li_Bytes = lfis_Source.read(larrbyte_Buffer)) != -1) {
				sops.write(larrbyte_Buffer, 0, li_Bytes);
			}
			if (lfis_Source != null) {
				lfis_Source.close();
			}
			if (sops != null) {
				sops.flush();
				sops.close();
			}
		}
	}

	/**
	 * @param ecc
	 * @Description 创建底部表单
	 * @param srcSheet
	 * @param destSheet
	 * @param bottomStartRowNum
	 * @param bottomStartRowCount
	 */
	public void createBottom(Ecc ecc, XSSFSheet srcSheet, XSSFSheet destSheet, int srcStartRowNum, int destStartRowNum,
			int rowCount) {
		int srcStartRowIndex = srcStartRowNum - 1;
		int destStartRowIndex = destStartRowNum - 1;
		this.createBottomForm(srcSheet, destSheet, rowCount, srcStartRowIndex, destStartRowIndex);
		insertBottomFormValues(destSheet, destStartRowIndex, ecc);
	}

	/**
	 * @Description 赋值底部表单
	 * @param destSheet
	 * @param destStartRowIndex
	 * @param ecc
	 */
	public void insertBottomFormValues(XSSFSheet destSheet, int destStartRowIndex, Ecc ecc) {
		String eccId = CommonUtil.trim(ecc.getEccId());
		EccDeptRevieCon eccDeptRevieCon = eccDeptRevieConDao.findUniqueResult("eccId", eccId);
		EccWgRevieCon eccWgRevieCon = eccWgRevieConDao.findUniqueResult("eccId", eccId);
		EccCacRevieCon eccCacRevieCon = eccCacRevieConDao.findUniqueResult("eccId", eccId);
		EccGroupRevieCon eccGroupRevieCon = eccGroupRevieConDao.findUniqueResult("eccId", eccId);

		// CNEEC业务部门自查评审结论
		if (eccDeptRevieCon != null) {
			XSSFCell eccDeptRevieCon_RvConStatus_Cell = destSheet.getRow(destStartRowIndex + 1).getCell(1);// B90
			eccDeptRevieCon_RvConStatus_Cell.setCellValue(getRvConStatusStr(eccDeptRevieCon.getRvConStatus()));
		}
		// CNEEC出口管制工作组会商意见
		if (eccWgRevieCon != null) {
			XSSFCell eccWgRevieCon_RvConStatus_Cell = destSheet.getRow(destStartRowIndex + 7).getCell(2);// C96
			eccWgRevieCon_RvConStatus_Cell.setCellValue(getRvConStatusStr(eccWgRevieCon.getRvConStatus()));
			XSSFCell eccWgRevieCon_RvConBussinesStatus_Cell = destSheet.getRow(destStartRowIndex + 9).getCell(2);// C98
			eccWgRevieCon_RvConBussinesStatus_Cell
					.setCellValue(getRvConBussinesStatusStr(eccWgRevieCon.getRvConBussinesStatus()));
		}
		// CNEEC出口管制委员会意见
		if (eccCacRevieCon != null) {
			XSSFCell eccCacRevieCon_RvConStatus_Cell = destSheet.getRow(destStartRowIndex + 13).getCell(1);// B102
			eccCacRevieCon_RvConStatus_Cell.setCellValue(getRvConStatusStr(eccCacRevieCon.getRvConStatus()));
		}
		// CNEEC出口管制委员会意见
		if (eccGroupRevieCon != null) {
			XSSFCell eccGroupRevieCon_RvConStatus_Cell = destSheet.getRow(destStartRowIndex + 17).getCell(1);// B106
			eccGroupRevieCon_RvConStatus_Cell.setCellValue(getRvConStatusStr(eccGroupRevieCon.getRvConStatus()));
		}
	}

	public void createBottomForm(XSSFSheet srcSheet, XSSFSheet destSheet, int rowCount, int srcStartRowIndex,
			int destStartRowIndex) {
		// 第89行
		CellRangeAddress cra_B89 = new CellRangeAddress(destStartRowIndex, destStartRowIndex, 1, 4);// 合并单元格
		destSheet.addMergedRegion(cra_B89);
		// 第90、91行
		CellRangeAddress cra_B90 = new CellRangeAddress(destStartRowIndex + 1, destStartRowIndex + 2, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B90);
		// 第92行
		CellRangeAddress cra_B92 = new CellRangeAddress(destStartRowIndex + 3, destStartRowIndex + 3, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B92);
		CellRangeAddress cra_D92 = new CellRangeAddress(destStartRowIndex + 3, destStartRowIndex + 3, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D92);
		// 第94行
		CellRangeAddress cra_D94 = new CellRangeAddress(destStartRowIndex + 5, destStartRowIndex + 5, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D94);
		// 第95行
		CellRangeAddress cra_B95 = new CellRangeAddress(destStartRowIndex + 6, destStartRowIndex + 6, 1, 4);// 合并单元格
		destSheet.addMergedRegion(cra_B95);
		// 第100行
		CellRangeAddress cra_D100 = new CellRangeAddress(destStartRowIndex + 11, destStartRowIndex + 11, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D100);
		// 第101行
		CellRangeAddress cra_B101 = new CellRangeAddress(destStartRowIndex + 12, destStartRowIndex + 12, 1, 4);// 合并单元格
		destSheet.addMergedRegion(cra_B101);
		// 第102行
		CellRangeAddress cra_B102 = new CellRangeAddress(destStartRowIndex + 13, destStartRowIndex + 13, 1, 3);// 合并单元格
		destSheet.addMergedRegion(cra_B102);
		// 第104行
		CellRangeAddress cra_D104 = new CellRangeAddress(destStartRowIndex + 15, destStartRowIndex + 15, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D104);
		// 第105行
		CellRangeAddress cra_B105 = new CellRangeAddress(destStartRowIndex + 16, destStartRowIndex + 16, 1, 3);// 合并单元格
		destSheet.addMergedRegion(cra_B105);
		// 第106行
		CellRangeAddress cra_B106 = new CellRangeAddress(destStartRowIndex + 17, destStartRowIndex + 17, 1, 3);// 合并单元格
		destSheet.addMergedRegion(cra_B106);
		// 第108行
		CellRangeAddress cra_D108 = new CellRangeAddress(destStartRowIndex + 19, destStartRowIndex + 19, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D108);

		// 复制行
		this.copyRow(srcSheet, destSheet, srcStartRowIndex, destStartRowIndex, rowCount);
	}

	/**
	 * @Description 产品说明
	 * @param ls_EccProduct
	 * @param srcSheet
	 * @param destSheet
	 * @param eccProductStartRowCount
	 * @param eccProductStartRowNum
	 */
	public void createEccProduct(List<EccProduct> ls_EccProduct, XSSFSheet srcSheet, XSSFSheet destSheet,
			int srcStartRowNum, int destStartRowNum, int rowCount) {
		int srcStartRowIndex = srcStartRowNum - 1;
		for (int i = 0; i < ls_EccProduct.size(); i++) {
			// 起始行：第75行，终止行：第86行，总计12行
			// 目标表格起始行索引
			int destStartRowIndex = destStartRowNum - 1 + rowCount * i;
			// 绘制表格
			this.createEccProductForm(srcSheet, destSheet, srcStartRowIndex, destStartRowIndex, rowCount);
			EccProduct eccProduct = ls_EccProduct.get(i);
			insertEccProductFormValues(destSheet, destStartRowIndex, eccProduct);
		}
	}

	/**
	 * @Description 赋值产品说明表格
	 * @param destSheet
	 * @param destStartRowIndex
	 * @param eccProduct
	 */
	public void insertEccProductFormValues(XSSFSheet destSheet, int destStartRowIndex, EccProduct eccProduct) {
		// 产品说明------------------------------------------------
		// 产品的中文名称
		if (eccProduct != null) {
			XSSFCell eccProduct_ProductC_Cell = destSheet.getRow(destStartRowIndex + 1).getCell(3);// D76
			eccProduct_ProductC_Cell.setCellValue(CommonUtil.trim(eccProduct.getProductC()));
			// 产品的英文名称
			XSSFCell eccProduct_ProductE_Cell = destSheet.getRow(destStartRowIndex + 2).getCell(3);// D77
			eccProduct_ProductE_Cell.setCellValue(CommonUtil.trim(eccProduct.getProductE()));
			// 海关编码
			XSSFCell eccProduct_ProductHs_Cell = destSheet.getRow(destStartRowIndex + 3).getCell(3);// D78
			eccProduct_ProductHs_Cell.setCellValue(CommonUtil.trim(eccProduct.getProductHs()));
			// 原产地（中文）
			XSSFCell eccProduct_ProductAddC_Cell = destSheet.getRow(destStartRowIndex + 4).getCell(3);// D79
			eccProduct_ProductAddC_Cell.setCellValue(CommonUtil.trim(eccProduct.getProductAddC()));
			// 原产地（英文）
			XSSFCell eccProduct_ProductAddE_Cell = destSheet.getRow(destStartRowIndex + 5).getCell(3);// D80
			eccProduct_ProductAddE_Cell.setCellValue(CommonUtil.trim(eccProduct.getProductAddE()));
			// 是否属两用范畴的敏感物项或技术 Yes或NO？请选择
			XSSFCell eccProduct_IsProductTwoE_Cell = destSheet.getRow(destStartRowIndex + 6).getCell(3);// D81
			eccProduct_IsProductTwoE_Cell.setCellValue(isYesOrNoStr(CommonUtil.trim(eccProduct.getIsProductTwoE())));// !!!!!!
			// 产品的最终用途（中文）
			XSSFCell eccProduct_EdnPuC_Cell = destSheet.getRow(destStartRowIndex + 7).getCell(3);// D82
			eccProduct_EdnPuC_Cell.setCellValue(CommonUtil.trim(eccProduct.getEdnPuC()));
			// 产品的最终用途（英文）
			XSSFCell eccProduct_EdnPuE_Cell = destSheet.getRow(destStartRowIndex + 8).getCell(3);// D83
			eccProduct_EdnPuE_Cell.setCellValue(CommonUtil.trim(eccProduct.getEdnPuE()));
			// 出口产品性能与客户经营范围是否相符 Yes或NO？请选择
			XSSFCell eccProduct_IsEdnPpCuY_Cell = destSheet.getRow(destStartRowIndex + 9).getCell(3);// D84
			eccProduct_IsEdnPpCuY_Cell.setCellValue(isYesOrNoStr(CommonUtil.trim(eccProduct.getIsEdnPpCuY())));// !!!!!!
			// 出口产品技术特征与最终用途是否相符 Yes或NO？请选择
			XSSFCell eccProduct_IsEdnPtPuY_Cell = destSheet.getRow(destStartRowIndex + 10).getCell(3);// D85
			eccProduct_IsEdnPtPuY_Cell.setCellValue(isYesOrNoStr(CommonUtil.trim(eccProduct.getIsEdnPtPuY())));// !!!!!!
			// 项目是否涉及美国因素(包括产品、技术、分包商、参与人员等，人员不能为美国公民或美国绿卡持有者) Yes或NO？请选择
			XSSFCell eccProduct_IsRelateUsa_Cell = destSheet.getRow(destStartRowIndex + 11).getCell(3);// D86
			eccProduct_IsRelateUsa_Cell.setCellValue(isYesOrNoStr(CommonUtil.trim(eccProduct.getIsRelateUsa())));// !!!!!!
			// 项目是否涉及美国因素(包括产品、技术、分包商、参与人员等，人员不能为美国公民或美国绿卡持有者) Yes或NO？请选择
			XSSFCell eccProduct_CapitalSourceZh_Cell = destSheet.getRow(destStartRowIndex + 13).getCell(2);// B88
			eccProduct_CapitalSourceZh_Cell
					.setCellValue(isYesOrNoStr(CommonUtil.trim(eccProduct.getCapitalSourceZh())));// !!!!!!
		}
	}

	/**
	 * @Description 绘制产品说明表格
	 * @param srcSheet
	 * @param destSheet
	 * @param srcStartRowIndex
	 * @param destStartRowIndex
	 * @param rowCount
	 */
	private void createEccProductForm(XSSFSheet srcSheet, XSSFSheet destSheet, int srcStartRowIndex,
			int destStartRowIndex, int rowCount) {
		// 第75行
		CellRangeAddress cra_B75 = new CellRangeAddress(destStartRowIndex, destStartRowIndex, 1, 4);// 合并单元格
		destSheet.addMergedRegion(cra_B75);
		// 第76、77行
		CellRangeAddress cra_B76 = new CellRangeAddress(destStartRowIndex + 1, destStartRowIndex + 2, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B76);
		CellRangeAddress cra_D76 = new CellRangeAddress(destStartRowIndex + 1, destStartRowIndex + 1, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D76);
		CellRangeAddress cra_D77 = new CellRangeAddress(destStartRowIndex + 2, destStartRowIndex + 2, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D77);
		// 第78行
		CellRangeAddress cra_B78 = new CellRangeAddress(destStartRowIndex + 3, destStartRowIndex + 3, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B78);
		CellRangeAddress cra_D78 = new CellRangeAddress(destStartRowIndex + 3, destStartRowIndex + 3, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D78);
		// 第79、80行
		CellRangeAddress cra_B79 = new CellRangeAddress(destStartRowIndex + 4, destStartRowIndex + 5, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B79);
		CellRangeAddress cra_D79 = new CellRangeAddress(destStartRowIndex + 4, destStartRowIndex + 4, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D79);
		CellRangeAddress cra_D80 = new CellRangeAddress(destStartRowIndex + 5, destStartRowIndex + 5, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D80);
		// 第81行
		CellRangeAddress cra_B81 = new CellRangeAddress(destStartRowIndex + 6, destStartRowIndex + 6, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B81);
		CellRangeAddress cra_D81 = new CellRangeAddress(destStartRowIndex + 6, destStartRowIndex + 6, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D81);
		// 第82、83行
		CellRangeAddress cra_B82 = new CellRangeAddress(destStartRowIndex + 7, destStartRowIndex + 8, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B82);
		CellRangeAddress cra_D82 = new CellRangeAddress(destStartRowIndex + 7, destStartRowIndex + 7, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D82);
		CellRangeAddress cra_D83 = new CellRangeAddress(destStartRowIndex + 8, destStartRowIndex + 8, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D83);
		// 第84行
		CellRangeAddress cra_B84 = new CellRangeAddress(destStartRowIndex + 9, destStartRowIndex + 9, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B84);
		CellRangeAddress cra_D84 = new CellRangeAddress(destStartRowIndex + 9, destStartRowIndex + 9, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D84);
		// 第85行
		CellRangeAddress cra_B85 = new CellRangeAddress(destStartRowIndex + 10, destStartRowIndex + 10, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B85);
		CellRangeAddress cra_D85 = new CellRangeAddress(destStartRowIndex + 10, destStartRowIndex + 10, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D85);
		// 第86行
		CellRangeAddress cra_B86 = new CellRangeAddress(destStartRowIndex + 11, destStartRowIndex + 11, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B86);
		CellRangeAddress cra_D86 = new CellRangeAddress(destStartRowIndex + 11, destStartRowIndex + 11, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D86);
		// 第87行
		CellRangeAddress cra_B87 = new CellRangeAddress(destStartRowIndex + 12, destStartRowIndex + 12, 1, 4);// 合并单元格
		destSheet.addMergedRegion(cra_B87);
		// 第88行
		CellRangeAddress cra_B88 = new CellRangeAddress(destStartRowIndex + 13, destStartRowIndex + 13, 1, 4);// 合并单元格
		destSheet.addMergedRegion(cra_B88);
		// 复制行
		this.copyRow(srcSheet, destSheet, srcStartRowIndex, destStartRowIndex, rowCount);
	}

	/**
	 * @Description 最终用户（业主）
	 * @param ls_EccOwner
	 * @param srcSheet
	 * @param destSheet
	 * @param eccOwnerStartRowCount
	 * @param eccOwnerStartRowNum
	 */
	public void createEccOwner(List<EccOwner> ls_EccOwner, XSSFSheet srcSheet, XSSFSheet destSheet, int srcStartRowNum,
			int destStartRowNum, int rowCount) {
		int srcStartRowIndex = srcStartRowNum - 1;
		for (int i = 0; i < ls_EccOwner.size(); i++) {
			// 起始行：第57行，终止行：第74行，总计18行
			// 目标表格起始行索引
			int destStartRowIndex = destStartRowNum - 1 + rowCount * i;
			// 绘制表格
			this.createEccOwnerForm(srcSheet, destSheet, srcStartRowIndex, destStartRowIndex, rowCount);
			EccOwner eccOwner = ls_EccOwner.get(i);
			insertEccOwnerFormValues(destSheet, eccOwner, destStartRowIndex);
		}
	}

	/**
	 * @Description 绘制最终用户（业主）表格
	 * @param srcSheet
	 * @param destSheet
	 * @param srcStartRowIndex
	 * @param destStartRowIndex
	 * @param rowCount
	 */
	private void createEccOwnerForm(XSSFSheet srcSheet, XSSFSheet destSheet, int srcStartRowIndex,
			int destStartRowIndex, int rowCount) {
		// 第57行
		CellRangeAddress cra_B57 = new CellRangeAddress(destStartRowIndex, destStartRowIndex, 1, 4);// 合并单元格
		destSheet.addMergedRegion(cra_B57);
		// 第58、59行
		CellRangeAddress cra_B58 = new CellRangeAddress(destStartRowIndex + 1, destStartRowIndex + 2, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B58);
		CellRangeAddress cra_D58 = new CellRangeAddress(destStartRowIndex + 1, destStartRowIndex + 1, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D58);
		CellRangeAddress cra_D59 = new CellRangeAddress(destStartRowIndex + 2, destStartRowIndex + 2, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D59);
		// 第60、61行
		CellRangeAddress cra_B60 = new CellRangeAddress(destStartRowIndex + 3, destStartRowIndex + 4, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B60);
		CellRangeAddress cra_D60 = new CellRangeAddress(destStartRowIndex + 3, destStartRowIndex + 3, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D60);
		CellRangeAddress cra_D61 = new CellRangeAddress(destStartRowIndex + 4, destStartRowIndex + 4, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D61);
		// 第62、63行
		CellRangeAddress cra_B62 = new CellRangeAddress(destStartRowIndex + 5, destStartRowIndex + 6, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B62);
		CellRangeAddress cra_D62 = new CellRangeAddress(destStartRowIndex + 5, destStartRowIndex + 5, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D62);
		CellRangeAddress cra_D63 = new CellRangeAddress(destStartRowIndex + 6, destStartRowIndex + 6, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D63);
		// 第64、65行
		CellRangeAddress cra_B64 = new CellRangeAddress(destStartRowIndex + 7, destStartRowIndex + 8, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B64);
		CellRangeAddress cra_D64 = new CellRangeAddress(destStartRowIndex + 7, destStartRowIndex + 7, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D64);
		CellRangeAddress cra_D65 = new CellRangeAddress(destStartRowIndex + 8, destStartRowIndex + 8, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D65);
		// 第66行
		CellRangeAddress cra_B66 = new CellRangeAddress(destStartRowIndex + 9, destStartRowIndex + 9, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B66);
		CellRangeAddress cra_D66 = new CellRangeAddress(destStartRowIndex + 9, destStartRowIndex + 9, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D66);
		// 第67行
		CellRangeAddress cra_B67 = new CellRangeAddress(destStartRowIndex + 10, destStartRowIndex + 10, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B67);
		CellRangeAddress cra_D67 = new CellRangeAddress(destStartRowIndex + 10, destStartRowIndex + 10, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D67);
		// 第68、69行
		CellRangeAddress cra_B68 = new CellRangeAddress(destStartRowIndex + 11, destStartRowIndex + 12, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B68);
		CellRangeAddress cra_D68 = new CellRangeAddress(destStartRowIndex + 11, destStartRowIndex + 11, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D68);
		CellRangeAddress cra_D69 = new CellRangeAddress(destStartRowIndex + 12, destStartRowIndex + 12, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D69);
		// 第70行
		CellRangeAddress cra_B70 = new CellRangeAddress(destStartRowIndex + 13, destStartRowIndex + 13, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B70);
		CellRangeAddress cra_D70 = new CellRangeAddress(destStartRowIndex + 13, destStartRowIndex + 13, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D70);
		// 第71、72行
		CellRangeAddress cra_B71 = new CellRangeAddress(destStartRowIndex + 14, destStartRowIndex + 15, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B71);
		CellRangeAddress cra_D71 = new CellRangeAddress(destStartRowIndex + 14, destStartRowIndex + 14, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D71);
		CellRangeAddress cra_D72 = new CellRangeAddress(destStartRowIndex + 15, destStartRowIndex + 15, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D72);
		// 第73行
		CellRangeAddress cra_B73 = new CellRangeAddress(destStartRowIndex + 16, destStartRowIndex + 16, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B73);
		CellRangeAddress cra_D73 = new CellRangeAddress(destStartRowIndex + 16, destStartRowIndex + 16, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D73);
		// 第74行
		CellRangeAddress cra_B74 = new CellRangeAddress(destStartRowIndex + 17, destStartRowIndex + 17, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B74);
		CellRangeAddress cra_D74 = new CellRangeAddress(destStartRowIndex + 17, destStartRowIndex + 17, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D74);
		// 复制行
		this.copyRow(srcSheet, destSheet, srcStartRowIndex, destStartRowIndex, rowCount);
	}

	/**
	 * @Description 赋值最终用户（业主）表格
	 * @param destSheet
	 * @param eccOwner
	 * @param destStartRowIndex
	 */
	public void insertEccOwnerFormValues(XSSFSheet destSheet, EccOwner eccOwner, int destStartRowIndex) {
		if (eccOwner != null) {
			String ownerId = CommonUtil.trim(eccOwner.getOwnerId());
			EccOwnerPartner eccOwnerPartner = eccOwnerPartnerDao.findUniqueResult("ownerId", ownerId);
			EccOwnerFso eccOwnerFso = eccOwnerFsoDao.findUniqueResult("ownerId", ownerId);

			// 最终用户（业主）-------------------------------------------------------------
			// 最终用户的中文名称
			XSSFCell eccOwner_OwnerCName_Cell = destSheet.getRow(destStartRowIndex + 1).getCell(3);// D58
			eccOwner_OwnerCName_Cell.setCellValue(CommonUtil.trim(eccOwner.getOwnerCName()));
			// 最终用户的英文名称
			XSSFCell eccOwner_OwnerEName_Cell = destSheet.getRow(destStartRowIndex + 2).getCell(3);// D59
			eccOwner_OwnerEName_Cell.setCellValue(CommonUtil.trim(eccOwner.getOwnerEName()));
			// 公司组织形式（中文）
			XSSFCell eccOwner_OwnerCCofName_Cell = destSheet.getRow(destStartRowIndex + 3).getCell(3);// D60
			eccOwner_OwnerCCofName_Cell.setCellValue(CommonUtil.trim(eccOwner.getOwnerCCofName()));
			// 公司组织形式（英文）
			XSSFCell eccOwner_OwnerECofName_Cell = destSheet.getRow(destStartRowIndex + 4).getCell(3);// D61
			eccOwner_OwnerECofName_Cell.setCellValue(CommonUtil.trim(eccOwner.getOwnerECofName()));
			// 公司主营业务范围（中文）
			XSSFCell eccOwner_OwnerCMbuc_Cell = destSheet.getRow(destStartRowIndex + 5).getCell(3);// D62
			eccOwner_OwnerCMbuc_Cell.setCellValue(CommonUtil.trim(eccOwner.getOwnerCMbuc()));
			// 公司主营业务范围（英文）
			XSSFCell eccOwner_OwnerEMbuc_Cell = destSheet.getRow(destStartRowIndex + 6).getCell(3);// D63
			eccOwner_OwnerEMbuc_Cell.setCellValue(CommonUtil.trim(eccOwner.getOwnerEMbuc()));
			// 注册地（中文）
			XSSFCell eccOwner_OwnerCAdd_Cell = destSheet.getRow(destStartRowIndex + 11).getCell(3);// D68
			eccOwner_OwnerCAdd_Cell.setCellValue(CommonUtil.trim(eccOwner.getOwnerCAdd()));
			// 注册地（英文）
			XSSFCell eccOwner_OwnerEAdd_Cell = destSheet.getRow(destStartRowIndex + 12).getCell(3);// D69
			eccOwner_OwnerEAdd_Cell.setCellValue(CommonUtil.trim(eccOwner.getOwnerEAdd()));
			// 注册地是否为敏感或受制裁国家或地区 Yes或NO？请选择
			XSSFCell eccOwner_OwnerAddE_Cell = destSheet.getRow(destStartRowIndex + 13).getCell(3);// D70
			eccOwner_OwnerAddE_Cell.setCellValue(isYesOrNoInt(eccOwner.getOwnerAddE()));// !!!!!!!!!
			// 是否与拥有大规模杀伤性武器或大规模杀伤性武器发展计划的国家和地区有密切的贸易往来 Yes或NO？请选择
			XSSFCell eccOwner_IsOwnerSpecialE_Cell = destSheet.getRow(destStartRowIndex + 17).getCell(3);// D74
			eccOwner_IsOwnerSpecialE_Cell.setCellValue(isYesOrNoInt(eccOwner.getIsOwnerSpecialE()));// !!!!!!!!!
			if (eccOwnerPartner != null) {
				// 相关权利人名称（中文）
				XSSFCell eccOwnerPartner_OwnerPartnerCName_Cell = destSheet.getRow(destStartRowIndex + 7).getCell(3);// D64
				eccOwnerPartner_OwnerPartnerCName_Cell
						.setCellValue(CommonUtil.trim(eccOwnerPartner.getOwnerPartnerCName()));
				// 相关权利人名称（英文）
				XSSFCell eccOwnerPartner_OwnerPartnerEName_Cell = destSheet.getRow(destStartRowIndex + 8).getCell(3);// D65
				eccOwnerPartner_OwnerPartnerEName_Cell
						.setCellValue(CommonUtil.trim(eccOwnerPartner.getOwnerPartnerEName()));
				// 相关权利人（股东、合伙人等）是否为被制裁或受限制 Yes或NO？请选择

				// 是否为被制裁或受限制实体 Yes或NO？请选择
				XSSFCell eccOwnerPartner_IsEcc_Cell = destSheet.getRow(destStartRowIndex + 10).getCell(3);// D67
				eccOwnerPartner_IsEcc_Cell.setCellValue(isYesOrNoInt(eccOwnerPartner.getIsEcc()));// !!!!!!!!!!
			}
			if (eccOwnerFso != null) {
				// 机构所在地（中文）
				XSSFCell eccOwnerFso_FsoCName_Cell = destSheet.getRow(destStartRowIndex + 14).getCell(3);// D71
				eccOwnerFso_FsoCName_Cell.setCellValue(CommonUtil.trim(eccOwnerFso.getFsoCName()));
				// 机构所在地（英文）
				XSSFCell eccOwnerFso_FsoEName_Cell = destSheet.getRow(destStartRowIndex + 15).getCell(3);// D72
				eccOwnerFso_FsoEName_Cell.setCellValue(CommonUtil.trim(eccOwnerFso.getFsoEName()));
				// 所在地是否为敏感或受制裁国家或地区 Yes或NO？请选择
				XSSFCell eccOwnerFso_FsoAddE_Cell = destSheet.getRow(destStartRowIndex + 16).getCell(3);// D73
				eccOwnerFso_FsoAddE_Cell.setCellValue(isYesOrNoInt(eccOwnerFso.getFsoAddE()));// !!!!!!!!!!
			}
		}
	}

	/**
	 * @Description 中间商、代理和运输公司的基本情况（如有）
	 * @param ls_EccPartner
	 * @param srcSheet
	 * @param destSheet     eccPartnerSrcStartRowNum, eccPartnerDestStartRowNum,
	 *                      eccPartnerRowCount
	 */
	public void createEccPartner(List<EccPartner> ls_EccPartner, XSSFSheet srcSheet, XSSFSheet destSheet,
			int srcStartRowNum, int destStartRowNum, int rowCount) {
		int srcStartRowIndex = srcStartRowNum - 1;
		for (int i = 0; i < ls_EccPartner.size(); i++) {
			// 起始行：第34行，终止行：第56行，总计23行
			// 目标表格起始行索引
			int destStartRowIndex = destStartRowNum - 1 + rowCount * i;
			// 绘制表格
			createEccCustomerOrEccPartnerForm(srcSheet, destSheet, srcStartRowIndex, destStartRowIndex, rowCount);
			// 赋值表格
			EccPartner eccPartner = ls_EccPartner.get(i);
			this.insertEccPartnerFormValues(destSheet, eccPartner, destStartRowIndex);
		}
	}

	/**
	 * @Description 赋值中间商、代理和运输公司的基本情况（如有）表格
	 * @param destSheet
	 * @param eccPartner
	 * @param destStartRowIndex
	 */
	public void insertEccPartnerFormValues(XSSFSheet destSheet, EccPartner eccPartner, int destStartRowIndex) {
		if (eccPartner != null) {
			String partnerId = CommonUtil.trim(eccPartner.getPartnerId());
			EccPartnerP eccPartnerP = eccPartnerPDao.findUniqueResult("partnerId", partnerId);
			EccPartnerFso eccPartnerFso = eccPartnerFsoDao.findUniqueResult("partnerId", partnerId);
			EccPartnerWarn eccPartnerWarn = eccPartnerWarnDao.findUniqueResult("partnerId", partnerId);
			// 中间商、代理和运输公司的基本情况-----------------------------------------------
			// 中间商、代理和运输公司的中文名称
			XSSFCell eccPartner_PartnerCName_Cell = destSheet.getRow(destStartRowIndex + 1).getCell(3);// D35
			eccPartner_PartnerCName_Cell.setCellValue(CommonUtil.trim(eccPartner.getPartnerCName()));
			// 中间商、代理和运输公司的英文名称
			XSSFCell eccPartner_PartnerEName_Cell = destSheet.getRow(destStartRowIndex + 2).getCell(3);// D36
			eccPartner_PartnerEName_Cell.setCellValue(CommonUtil.trim(eccPartner.getPartnerEName()));
			// 公司组织形式（中文）
			XSSFCell eccPartner_PartnerCCofName_Cell = destSheet.getRow(destStartRowIndex + 3).getCell(3);// D37
			eccPartner_PartnerCCofName_Cell.setCellValue(CommonUtil.trim(eccPartner.getPartnerCCofName()));
			// 公司组织形式（英文）
			XSSFCell eccPartner_PartnerECofName_Cell = destSheet.getRow(destStartRowIndex + 4).getCell(3);// D38
			eccPartner_PartnerECofName_Cell.setCellValue(CommonUtil.trim(eccPartner.getPartnerECofName()));
			// 公司主营业务范围（中文）
			XSSFCell eccPartner_PartnerCMbuc_Cell = destSheet.getRow(destStartRowIndex + 5).getCell(3);// D39
			eccPartner_PartnerCMbuc_Cell.setCellValue(CommonUtil.trim(eccPartner.getPartnerCMbuc()));
			// 公司主营业务范围（英文）
			XSSFCell eccPartner_PartnerEMbuc_Cell = destSheet.getRow(destStartRowIndex + 6).getCell(3);// D40
			eccPartner_PartnerEMbuc_Cell.setCellValue(CommonUtil.trim(eccPartner.getPartnerEMbuc()));
			// 注册地（中文）
			XSSFCell eccPartner_PartnerCAdd_Cell = destSheet.getRow(destStartRowIndex + 11).getCell(3);// D45
			eccPartner_PartnerCAdd_Cell.setCellValue(CommonUtil.trim(eccPartner.getPartnerCAdd()));
			// 注册地（英文）
			XSSFCell eccPartner_PartnerEAdd_Cell = destSheet.getRow(destStartRowIndex + 12).getCell(3);// D46
			eccPartner_PartnerEAdd_Cell.setCellValue(CommonUtil.trim(eccPartner.getPartnerEAdd()));
			// 注册地是否为敏感或受制裁国家或地区 Yes或NO？请选择
			XSSFCell eccPartner_PartnerAddE_Cell = destSheet.getRow(destStartRowIndex + 13).getCell(3);// D47
			eccPartner_PartnerAddE_Cell.setCellValue(isYesOrNoInt(eccPartner.getPartnerAddE()));// !!!!!!!!
			if (eccPartnerP != null) {
				// 相关权利人名称（中文）
				XSSFCell eccPartnerP_PartnerPCName_Cell = destSheet.getRow(destStartRowIndex + 7).getCell(3);// D41
				eccPartnerP_PartnerPCName_Cell.setCellValue(CommonUtil.trim(eccPartnerP.getPartnerPCName()));
				// 相关权利人名称（英文）
				XSSFCell eccPartnerP_PartnerPEName_Cell = destSheet.getRow(destStartRowIndex + 8).getCell(3);// D42
				eccPartnerP_PartnerPEName_Cell.setCellValue(CommonUtil.trim(eccPartnerP.getPartnerPEName()));
				// 相关权利人（股东、合伙人等）是否为被制裁或受限制 Yes或NO？请选择

				// 是否为被制裁或受限制实体 Yes或NO？请选择
				XSSFCell eccPartnerP_IsEcc_Cell = destSheet.getRow(destStartRowIndex + 10).getCell(3);// D44
				eccPartnerP_IsEcc_Cell.setCellValue(isYesOrNoInt(eccPartnerP.getIsEcc()));// !!!!!!!!!!!!!
			}
			if (eccPartnerFso != null) {
				// 机构所在地（中文）
				XSSFCell eccPartnerFso_FsoCName_Cell = destSheet.getRow(destStartRowIndex + 14).getCell(3);// D48
				eccPartnerFso_FsoCName_Cell.setCellValue(CommonUtil.trim(eccPartnerFso.getFsoCName()));
				// 机构所在地（英文）
				XSSFCell eccPartnerFso_FsoEName_Cell = destSheet.getRow(destStartRowIndex + 15).getCell(3);// D49
				eccPartnerFso_FsoEName_Cell.setCellValue(CommonUtil.trim(eccPartnerFso.getFsoEName()));
				// 所在地是否为敏感或受制裁国家或地区 Yes或NO？请选择
				XSSFCell eccPartnerFso_FsoAddE_Cell = destSheet.getRow(destStartRowIndex + 16).getCell(3);// D50
				eccPartnerFso_FsoAddE_Cell.setCellValue(isYesOrNoInt(eccPartnerFso.getFsoAddE()));// !!!!!!!!!!
			}
			if (eccPartnerWarn != null) {
				// 是否身份不明 Yes或NO？请选择
				XSSFCell eccPartnerWarn_IsIdNd_Cell = destSheet.getRow(destStartRowIndex + 18).getCell(3);// D52
				eccPartnerWarn_IsIdNd_Cell.setCellValue(isYesOrNoInt(eccPartnerWarn.getIsIdNd()));// !!!!!!
				// 是否回避某些商务或技术问题 Yes或NO？请选择
				XSSFCell eccPartnerWarn_IsAvoidBti_Cell = destSheet.getRow(destStartRowIndex + 19).getCell(3);// D53
				eccPartnerWarn_IsAvoidBti_Cell.setCellValue(isYesOrNoInt(eccPartnerWarn.getIsAvoidBti()));// !!!!!!
				// 支付方式是否符合一般商业习惯 Yes或NO？请选择
				XSSFCell eccPartnerWarn_IsPayMOk_Cell = destSheet.getRow(destStartRowIndex + 20).getCell(3);// D54
				eccPartnerWarn_IsPayMOk_Cell.setCellValue(isYesOrNoInt(eccPartnerWarn.getIsPayMOk()));// !!!!!!
				// 是否在不了解产品性能的情况下执意购买产品 Yes或NO？请选择
				XSSFCell eccPartnerWarn_IsUnProductBy_Cell = destSheet.getRow(destStartRowIndex + 21).getCell(3);// D55
				eccPartnerWarn_IsUnProductBy_Cell.setCellValue(isYesOrNoInt(eccPartnerWarn.getIsUnProductBy()));// !!!!!!
				// 价格是否合理 Yes或NO？请选择
			}
		}
	}

	/**
	 * @Description 客户基本情况
	 * @param ls_EccCustomer
	 * @param srcSheet
	 * @param destSheet
	 * @param eccCustomerRowCount
	 */
	public void createEccCustomer(List<EccCustomer> ls_EccCustomer, XSSFSheet srcSheet, XSSFSheet destSheet,
			int srcStartRowNum, int destStartRowNum, int rowCount) {
		int srcStartRowIndex = srcStartRowNum - 1;
		for (int i = 0; i < ls_EccCustomer.size(); i++) {
			// 起始行：第11行，终止行：第33行，总计23行
			// 目标表格起始行索引
			int destStartRowIndex = destStartRowNum - 1 + rowCount * i;
			// 绘制表格
			createEccCustomerOrEccPartnerForm(srcSheet, destSheet, srcStartRowIndex, destStartRowIndex, rowCount);
			// 赋值表格
			EccCustomer eccCustomer = ls_EccCustomer.get(i);
			insertEccCustomerFormValues(destSheet, eccCustomer, destStartRowIndex);
		}
	}

	/**
	 * @Description 赋值客户基本情况表格
	 * @param ls_EccCustomer
	 * @param destSheet
	 * @param i
	 * @param destStartRowIndex
	 */
	public void insertEccCustomerFormValues(XSSFSheet destSheet, EccCustomer eccCustomer, int destStartRowIndex) {
		if (eccCustomer != null) {
			// 给表格赋值
			String customerId = CommonUtil.trim(eccCustomer.getCustomerId());
			EccCustomerPartner eccCustomerPartner = eccCustomerPartnerDao.get(customerId);
			EccCustomerFso eccCustomerFso = eccCustomerFsoDao.findUniqueResult("customerId", customerId);
			EccCustomerWarn eccCustomerWarn = eccCustomerWarnDao.findUniqueResult("customerId", customerId);
			// 客户中文名称
			XSSFCell eccCustomer_CustomerCName_Cell = destSheet.getRow(destStartRowIndex + 1).getCell(3);// D12
			eccCustomer_CustomerCName_Cell.setCellValue(CommonUtil.trim(eccCustomer.getCustomerCName()));
			// 客户英文名称
			XSSFCell eccCustomer_CustomerEName_Cell = destSheet.getRow(destStartRowIndex + 2).getCell(3);// D13
			eccCustomer_CustomerEName_Cell.setCellValue(CommonUtil.trim(eccCustomer.getCustomerEName()));
			// 公司组织形式（中文）
			XSSFCell eccCustomer_CustomerCCofName_Cell = destSheet.getRow(destStartRowIndex + 3).getCell(3);// D14
			eccCustomer_CustomerCCofName_Cell.setCellValue(CommonUtil.trim(eccCustomer.getCustomerCCofName()));
			// 公司组织形式（英文）
			XSSFCell eccCustomer_CustomerECofName_Cell = destSheet.getRow(destStartRowIndex + 4).getCell(3);// D15
			eccCustomer_CustomerECofName_Cell.setCellValue(CommonUtil.trim(eccCustomer.getCustomerECofName()));
			// 公司主营业务范围（中文）
			XSSFCell eccCustomer_CustomerCMbuc_Cell = destSheet.getRow(destStartRowIndex + 5).getCell(3);// D16
			eccCustomer_CustomerCMbuc_Cell.setCellValue(CommonUtil.trim(eccCustomer.getCustomerCMbuc()));
			// 公司主营业务范围（英文）
			XSSFCell eccCustomer_CustomerEMbuc_Cell = destSheet.getRow(destStartRowIndex + 6).getCell(3);// D17
			eccCustomer_CustomerEMbuc_Cell.setCellValue(CommonUtil.trim(eccCustomer.getCustomerEMbuc()));
			// 注册地（中文）
			XSSFCell eccCustomer_CustomerCAdd_Cell = destSheet.getRow(destStartRowIndex + 11).getCell(3);// D22
			eccCustomer_CustomerCAdd_Cell.setCellValue(CommonUtil.trim(eccCustomer.getCustomerCAdd()));
			// 注册地（英文）
			XSSFCell eccCustomer_CustomerEAdd_Cell = destSheet.getRow(destStartRowIndex + 12).getCell(3);// D23
			eccCustomer_CustomerEAdd_Cell.setCellValue(CommonUtil.trim(eccCustomer.getCustomerEAdd()));
			// 注册地是否为敏感或受制裁国家或地区 Yes或NO？请选择
			XSSFCell eccCustomer_CustomerAddE_Cell = destSheet.getRow(destStartRowIndex + 13).getCell(3);// D24
			eccCustomer_CustomerAddE_Cell.setCellValue(isYesOrNoInt(eccCustomer.getCustomerAddE()));// !!!!!!!!!!!!!!!
			// 价格是否合理 Yes或NO？请选择
			if (eccCustomerPartner != null) {
				// 相关权利人名称（中文）
				XSSFCell eccCustomerPartner_PartnerCName_Cell = destSheet.getRow(destStartRowIndex + 7).getCell(3);// D18
				eccCustomerPartner_PartnerCName_Cell
						.setCellValue(CommonUtil.trim(eccCustomerPartner.getPartnerCName()));
				// 相关权利人名称（英文）
				XSSFCell eccCustomerPartner_PartnerEName_Cell = destSheet.getRow(destStartRowIndex + 8).getCell(3);// D19
				eccCustomerPartner_PartnerEName_Cell
						.setCellValue(CommonUtil.trim(eccCustomerPartner.getPartnerEName()));
				// 相关权利人（股东、合伙人等）是否为被制裁或受限制 Yes或NO？请选择???????????????????????
				// 是否为被制裁或受限制实体 Yes或NO？请选择
				XSSFCell eccCustomerPartner_IsEcc_Cell = destSheet.getRow(destStartRowIndex + 10).getCell(3);// D21
				eccCustomerPartner_IsEcc_Cell.setCellValue(isYesOrNoInt(eccCustomerPartner.getIsEcc()));// !!!!!!!!!
			}
			if (eccCustomerFso != null) {
				// 办事机构所在地（中文）
				XSSFCell eccCustomerFso_FsoCAdd_Cell = destSheet.getRow(destStartRowIndex + 14).getCell(3);// D25
				eccCustomerFso_FsoCAdd_Cell.setCellValue(CommonUtil.trim(eccCustomerFso.getFsoCAdd()));
				// 办事机构所在地（英文）
				XSSFCell eccCustomerFso_FsoEAdd_Cell = destSheet.getRow(destStartRowIndex + 15).getCell(3);// D26
				eccCustomerFso_FsoEAdd_Cell.setCellValue(CommonUtil.trim(eccCustomerFso.getFsoEAdd()));
				// 所在地是否为敏感或受制裁国家或地区 Yes或NO？请选择
				XSSFCell eccCustomerFso_FsoAddE_Cell = destSheet.getRow(destStartRowIndex + 16).getCell(3);// D27
				eccCustomerFso_FsoAddE_Cell.setCellValue(isYesOrNoInt(eccCustomerFso.getFsoAddE()));// !!!!!!!!!!
			}
			if (eccCustomerWarn != null) {
				// 是否身份不明 Yes或NO？请选择
				XSSFCell eccCustomerWarn_IsIdNd_Cell = destSheet.getRow(destStartRowIndex + 18).getCell(3);// D29
				eccCustomerWarn_IsIdNd_Cell.setCellValue(isYesOrNoInt(eccCustomerWarn.getIsIdNd()));// !!!!!!!!!!
				// 是否回避某些商务或技术问题 Yes或NO？请选择
				XSSFCell eccCustomerWarn_IsAvoidBti_Cell = destSheet.getRow(destStartRowIndex + 19).getCell(3);// D30
				eccCustomerWarn_IsAvoidBti_Cell.setCellValue(isYesOrNoInt(eccCustomerWarn.getIsAvoidBti()));// !!!!!!!!!!
				// 支付方式是否符合一般商业习惯 Yes或NO？请选择
				XSSFCell eccCustomerWarn_IsPayMOk_Cell = destSheet.getRow(destStartRowIndex + 20).getCell(3);// D31
				eccCustomerWarn_IsPayMOk_Cell.setCellValue(isYesOrNoInt(eccCustomerWarn.getIsPayMOk()));// !!!!!!!!!!
				// 是否在不了解产品性能的情况下执意购买产品 Yes或NO？请选择
				XSSFCell eccCustomerWarn_IsUnProductBuy_Cell = destSheet.getRow(destStartRowIndex + 21).getCell(3);// D32
				eccCustomerWarn_IsUnProductBuy_Cell.setCellValue(isYesOrNoInt(eccCustomerWarn.getIsUnProductBuy()));// !!!!!!!!!!
				// 价格是否合理?????????
			}
		}
	}

	/**
	 * @Description 绘制客户基本情况表格或中间商、代理和运输公司的基本情况（如有）表格
	 * @param srcSheet
	 * @param destSheet
	 * @param srcStartRowIndex
	 * @param destStartRowIndex
	 * @param rowCount
	 */
	public void createEccCustomerOrEccPartnerForm(XSSFSheet srcSheet, XSSFSheet destSheet, int srcStartRowIndex,
			int destStartRowIndex, int rowCount) {
		// 第11行
		CellRangeAddress cra_B11 = new CellRangeAddress(destStartRowIndex, destStartRowIndex, 1, 4);// 合并单元格
		destSheet.addMergedRegion(cra_B11);
		// 第12、13行
		CellRangeAddress cra_B12 = new CellRangeAddress(destStartRowIndex + 1, destStartRowIndex + 2, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B12);
		CellRangeAddress cra_D12 = new CellRangeAddress(destStartRowIndex + 1, destStartRowIndex + 1, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D12);
		CellRangeAddress cra_D13 = new CellRangeAddress(destStartRowIndex + 2, destStartRowIndex + 2, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D13);
		// 第14、15行
		CellRangeAddress cra_B14 = new CellRangeAddress(destStartRowIndex + 3, destStartRowIndex + 4, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B14);
		CellRangeAddress cra_D14 = new CellRangeAddress(destStartRowIndex + 3, destStartRowIndex + 3, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D14);
		CellRangeAddress cra_D15 = new CellRangeAddress(destStartRowIndex + 4, destStartRowIndex + 4, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D15);
		// 第16、17行
		CellRangeAddress cra_B16 = new CellRangeAddress(destStartRowIndex + 5, destStartRowIndex + 6, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B16);
		CellRangeAddress cra_D16 = new CellRangeAddress(destStartRowIndex + 5, destStartRowIndex + 5, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D16);
		CellRangeAddress cra_D17 = new CellRangeAddress(destStartRowIndex + 6, destStartRowIndex + 6, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D17);
		// 第18、19行
		CellRangeAddress cra_B18 = new CellRangeAddress(destStartRowIndex + 7, destStartRowIndex + 8, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B18);
		CellRangeAddress cra_D18 = new CellRangeAddress(destStartRowIndex + 7, destStartRowIndex + 7, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D18);
		CellRangeAddress cra_D19 = new CellRangeAddress(destStartRowIndex + 8, destStartRowIndex + 8, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D19);
		// 第20行
		CellRangeAddress cra_B20 = new CellRangeAddress(destStartRowIndex + 9, destStartRowIndex + 9, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B20);
		CellRangeAddress cra_D20 = new CellRangeAddress(destStartRowIndex + 9, destStartRowIndex + 9, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D20);
		// 第21行
		CellRangeAddress cra_B21 = new CellRangeAddress(destStartRowIndex + 10, destStartRowIndex + 10, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B21);
		CellRangeAddress cra_D21 = new CellRangeAddress(destStartRowIndex + 10, destStartRowIndex + 10, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D21);
		// 第22、23行
		CellRangeAddress cra_B22 = new CellRangeAddress(destStartRowIndex + 11, destStartRowIndex + 12, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B22);
		CellRangeAddress cra_D22 = new CellRangeAddress(destStartRowIndex + 11, destStartRowIndex + 11, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D22);
		CellRangeAddress cra_D23 = new CellRangeAddress(destStartRowIndex + 12, destStartRowIndex + 12, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D23);
		// 第24行
		CellRangeAddress cra_B24 = new CellRangeAddress(destStartRowIndex + 13, destStartRowIndex + 13, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B24);
		CellRangeAddress cra_D24 = new CellRangeAddress(destStartRowIndex + 13, destStartRowIndex + 13, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D24);
		// 第25、26行
		CellRangeAddress cra_B25 = new CellRangeAddress(destStartRowIndex + 14, destStartRowIndex + 15, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B25);
		CellRangeAddress cra_D25 = new CellRangeAddress(destStartRowIndex + 14, destStartRowIndex + 14, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D25);
		CellRangeAddress cra_D26 = new CellRangeAddress(destStartRowIndex + 15, destStartRowIndex + 15, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D26);
		// 第27行
		CellRangeAddress cra_B27 = new CellRangeAddress(destStartRowIndex + 16, destStartRowIndex + 16, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B27);
		CellRangeAddress cra_D27 = new CellRangeAddress(destStartRowIndex + 16, destStartRowIndex + 16, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D27);
		// 第28行
		CellRangeAddress cra_B28 = new CellRangeAddress(destStartRowIndex + 17, destStartRowIndex + 17, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B28);
		CellRangeAddress cra_D28 = new CellRangeAddress(destStartRowIndex + 17, destStartRowIndex + 17, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D28);
		// 第29行
		CellRangeAddress cra_B29 = new CellRangeAddress(destStartRowIndex + 18, destStartRowIndex + 18, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B29);
		CellRangeAddress cra_D29 = new CellRangeAddress(destStartRowIndex + 18, destStartRowIndex + 18, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D29);
		// 第30行
		CellRangeAddress cra_B30 = new CellRangeAddress(destStartRowIndex + 19, destStartRowIndex + 19, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B30);
		CellRangeAddress cra_D30 = new CellRangeAddress(destStartRowIndex + 19, destStartRowIndex + 19, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D30);
		// 第31行
		CellRangeAddress cra_B31 = new CellRangeAddress(destStartRowIndex + 20, destStartRowIndex + 20, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B31);
		CellRangeAddress cra_D31 = new CellRangeAddress(destStartRowIndex + 20, destStartRowIndex + 20, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D31);
		// 第32行
		CellRangeAddress cra_B32 = new CellRangeAddress(destStartRowIndex + 21, destStartRowIndex + 21, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B32);
		CellRangeAddress cra_D32 = new CellRangeAddress(destStartRowIndex + 21, destStartRowIndex + 21, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D32);
		// 第33行
		CellRangeAddress cra_B33 = new CellRangeAddress(destStartRowIndex + 22, destStartRowIndex + 22, 1, 2);// 合并单元格
		destSheet.addMergedRegion(cra_B33);
		CellRangeAddress cra_D33 = new CellRangeAddress(destStartRowIndex + 22, destStartRowIndex + 22, 3, 4);// 合并单元格
		destSheet.addMergedRegion(cra_D33);
		// 复制行
		this.copyRow(srcSheet, destSheet, srcStartRowIndex, destStartRowIndex, rowCount);

	}

	/**
	 * @Description 表头
	 * @param srcSheet
	 * @param destSheet
	 */
	public void createHeader(XSSFSheet srcSheet, XSSFSheet destSheet) {
		// 表头
		CellRangeAddress rffoecCra = new CellRangeAddress(0, 0, 1, 3);// 合并单元格
		destSheet.addMergedRegion(rffoecCra);
		// 复制行
		copyRow(srcSheet, destSheet, 0, 0, 2);
	}

	/**
	 * @Description 项目基本信息
	 * @param prj
	 * @param prjAddr
	 * @param srcSheet
	 * @param destSheet
	 */
	public void createPrj(Prj prj, PrjAddr prjAddr, XSSFSheet srcSheet, XSSFSheet destSheet) {
		// 绘制表格
		this.createPrjForm(srcSheet, destSheet);
		// 赋值表格
		insertPrjFormValues(prj, prjAddr, destSheet);
	}

	/**
	 * @Description 赋值项目基本信息表格
	 * @param prj
	 * @param prjAddr
	 * @param destSheet
	 */
	public void insertPrjFormValues(Prj prj, PrjAddr prjAddr, XSSFSheet destSheet) {
		if (prj != null) {
			// 项目中文名称
			XSSFCell prj_PrjCName_Cell = destSheet.getRow(3).getCell(2);// C4
			prj_PrjCName_Cell.setCellValue(CommonUtil.trim(prj.getPrjCName()));
			// 项目英文名称
			XSSFCell prj_PrjEName_Cell = destSheet.getRow(4).getCell(2);// C5
			prj_PrjEName_Cell.setCellValue(CommonUtil.trim(prj.getPrjEName()));
			// 项目性质（中文）
			XSSFCell prj_PrjCNature_Cell = destSheet.getRow(5).getCell(2);// C6
			prj_PrjCNature_Cell.setCellValue(CommonUtil.trim(prj.getPrjNature()));
			// 项目性质（英文）
			XSSFCell prj_PrjENature_Cell = destSheet.getRow(5).getCell(3);// D6
			prj_PrjENature_Cell.setCellValue(CommonUtil.trim(prj.getPrjNature()));
			// 项目金额
			XSSFCell prj_PrjPlanAmount_Cell = destSheet.getRow(6).getCell(2);// C7
			prj_PrjPlanAmount_Cell.setCellValue(CommonUtil.trim(prj.getPrjPlanAmount()));
			// 币种？？？？？？？？？？？？？？？？？

			if (prjAddr != null) {
				// 国家名称（中文）
				XSSFCell prjAddr_CountryCName_Cell = destSheet.getRow(7).getCell(2);// C8
				prjAddr_CountryCName_Cell.setCellValue(CommonUtil.trim(prjAddr.getCountryCName()));
				// 国家名称（英文）
				XSSFCell prjAddr_CountryEName_Cell = destSheet.getRow(8).getCell(2);// C9
				prjAddr_CountryEName_Cell.setCellValue(CommonUtil.trim(prjAddr.getCountryEName()));
				// 地区（中文）
				XSSFCell prjAddr_ProvinceCName_Cell = destSheet.getRow(7).getCell(3);// D8
				prjAddr_ProvinceCName_Cell.setCellValue(CommonUtil.trim(prjAddr.getProvinceCName()));
				// 地区（英文）
				XSSFCell prjAddr_ProvinceEName_Cell = destSheet.getRow(8).getCell(3);// D9
				prjAddr_ProvinceEName_Cell.setCellValue(CommonUtil.trim(prjAddr.getProvinceEName()));
			}
		}
	}

	/**
	 * @Description 绘制项目基本信息表格
	 * @param srcSheet
	 * @param destSheet
	 */
	public void createPrjForm(XSSFSheet srcSheet, XSSFSheet destSheet) {
		// 第3行
		CellRangeAddress oftpCra = new CellRangeAddress(2, 2, 1, 4);// 合并单元格
		destSheet.addMergedRegion(oftpCra);
		// 第4、5行
		CellRangeAddress pnCra = new CellRangeAddress(3, 4, 1, 1);// 合并单元格
		destSheet.addMergedRegion(pnCra);
		CellRangeAddress prjCNameCra = new CellRangeAddress(3, 3, 2, 4);// 合并单元格
		destSheet.addMergedRegion(prjCNameCra);
		CellRangeAddress prjENameCra = new CellRangeAddress(4, 4, 2, 4);// 合并单元格
		destSheet.addMergedRegion(prjENameCra);
		// 第6行
		CellRangeAddress prjENatureCra = new CellRangeAddress(5, 5, 3, 4);// 合并单元格
		destSheet.addMergedRegion(prjENatureCra);
		// 第7行
		CellRangeAddress currencyCra = new CellRangeAddress(6, 6, 3, 4);// 合并单元格
		destSheet.addMergedRegion(currencyCra);
		// 第8、9行
		CellRangeAddress plCra = new CellRangeAddress(7, 8, 1, 1);// 合并单元格
		destSheet.addMergedRegion(plCra);
		CellRangeAddress provinceCNameCra = new CellRangeAddress(7, 7, 3, 4);// 合并单元格
		destSheet.addMergedRegion(provinceCNameCra);
		CellRangeAddress provinceENameCra = new CellRangeAddress(8, 8, 3, 4);// 合并单元格
		destSheet.addMergedRegion(provinceENameCra);
		// 第10行
		CellRangeAddress noteCra = new CellRangeAddress(9, 9, 1, 4);// 合并单元格
		destSheet.addMergedRegion(noteCra);
		// 复制行
		copyRow(srcSheet, destSheet, 2, 2, 8);
	}

	/**
	 * @Description 复制行
	 * @param srcSheet
	 * @param destSheet
	 * @param srcStartRowIndex
	 * @param rowCount
	 * @param destStartRowIndex
	 * @param size
	 */
	private void copyRow(XSSFSheet srcSheet, XSSFSheet destSheet, int srcStartRowIndex, int destStartRowIndex,
			int rowCount) {
		for (int i = 0; i < rowCount; i++) {
			ExcelUtils.copyRow(srcSheet.getRow(srcStartRowIndex + i), destSheet.createRow(destStartRowIndex + i));
		}
	}

	/**
	 * @Description 判定Yes或NO
	 * @param flag
	 * @return
	 */
	public String isYesOrNoInt(int flag) {
		return flag == 1 ? "Yes" : "NO";
	}

	/**
	 * @Description 判定Yes或NO
	 * @param flag
	 * @return
	 */
	public String isYesOrNoStr(String flag) {
		return "是".equals(flag) ? "Yes" : "NO";
	}

	public String getRvConStatusStr(int flag) {
		return flag == 1 ? "自查通过" : "自查未通过";
	}

	public String getRvConBussinesStatusStr(int flag) {
		if (flag == 1) {
			return "情况清晰有限制制裁";
		} else if (flag == 2) {
			return " 情况复杂的有限制制裁\\全面制裁";
		} else {
			return "";
		}
	}
}