package com.supporter.prj.cneec.tpc.util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.supporter.prj.eip_service.EIPService;

/**
 * @Title: ExportUtil
 * @Description: 导出文件工具(使用poi自动生成Excel时默认字体为Arial，行高12.75)
 * @author: yanweichao
 * @date: 2018-5-12
 * @version: V1.0
 */
public class ExportUtil {

	/** Excel格式 **/
	public static final String excel2003L = ".xls";
	public static final String excel2007U = ".xlsx";

	/** Excel单元格格式 **/
	private static final String format_currency = "¥#,##0.00";
	private static final String format_numeric = "#,##0.00";

	/**
	 * 获取项目根目录
	 * 
	 * @return
	 */
	public static String getRootDir() {
		return EIPService.getContextService().getRootDirPath();
	}

	/**
	 * 获取导出模板
	 * 
	 * @param filePath
	 *            模板文件所在相对路径
	 * @return
	 * @throws Exception
	 */
	public static File getExcelDemoFile(String filePath) throws Exception {
		String rootDir = getRootDir();
		File file = new File(rootDir + filePath);
		if (!file.exists()) {
			throw new Exception("模板文件不存在！");
		}
		return file;
	}

	/**
	 * 获取导出模板文件工作表(XLS)
	 * 
	 * @param filePath
	 *            模板文件所在相对路径
	 * @return
	 * @throws Exception
	 */
	public static HSSFWorkbook getHSSFWorkbook(String filePath) throws Exception {
		File file = getExcelDemoFile(filePath);
		FileInputStream fis = new FileInputStream(file);
		HSSFWorkbook wb = new HSSFWorkbook(fis);
		fis.close();
		return wb;
	}

	/**
	 * 获取导出模板文件工作表(XLSX)
	 * 
	 * @param filePath
	 *            模板文件所在相对路径
	 * @return
	 * @throws Exception
	 */
	public static XSSFWorkbook getXSSFWorkbook(String filePath) throws Exception {
		File file = getExcelDemoFile(filePath);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		fis.close();
		return wb;
	}

	/**
	 * 获取导出文件工作表(根据文件格式不同)
	 * 
	 * @param filePath
	 *            模板文件所在相对路径
	 * @param suffix
	 *            模板后缀
	 * @return
	 * @throws Exception
	 */
	public static Workbook getWorkbook(String filePath, String suffix) throws Exception {
		Workbook wb = null;
		if (excel2003L.equals(suffix)) {
			wb = getHSSFWorkbook(filePath);
		} else if (excel2007U.equals(suffix)) {
			wb = getXSSFWorkbook(filePath);
		} else {
			throw new Exception("解析的文件格式有误！");
		}
		return wb;
	}

	/**
	 * 
	 * @param suffix
	 * @return
	 * @throws Exception
	 */
	public static Workbook getWorkbook(String suffix) throws Exception {
		Workbook wb = null;
		if (excel2003L.equals(suffix)) {
			wb = new HSSFWorkbook();
		} else if (excel2007U.equals(suffix)) {
			wb = new XSSFWorkbook();
		} else {
			throw new Exception("解析的文件格式有误！");
		}
		return wb;
	}

	/**
	 * 设置单元格样式(边框)
	 * 
	 * @param wb
	 * @return
	 */
	public static CellStyle getSimpleCellStyle(Workbook wb) {
		CellStyle cs = wb.createCellStyle();
		cs.setBorderBottom(CellStyle.BORDER_THIN);
		cs.setBorderLeft(CellStyle.BORDER_THIN);
		cs.setBorderTop(CellStyle.BORDER_THIN);
		cs.setBorderRight(CellStyle.BORDER_THIN);
		return cs;
	}

	/**
	 * 标题样式
	 * 
	 * @param wb
	 * @return
	 */
	public static CellStyle getTitleCellStyle(Workbook wb) {
		CellStyle cs = getSimpleCellStyle(wb);
		cs.setAlignment(CellStyle.ALIGN_CENTER); // 水平居中
		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 垂直居中
		cs.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());// 设置背景颜色(灰色)
		cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cs.setWrapText(true);
		// 字体样式
		Font font = wb.createFont();
		// font.setFontName("微软雅黑");// 字体
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);// 加粗
		cs.setFont(font);
		return cs;
	}

	/**
	 * 货币样式
	 *
	 * @param wb
	 * @return
	 */
	public static CellStyle getCurrencyCellStyle(Workbook wb, String format) {
		CellStyle cs = getSimpleCellStyle(wb);
		cs.setAlignment(CellStyle.ALIGN_RIGHT); // 水平居右
		format = format != null ? format : format_currency;
		cs.setDataFormat(wb.createDataFormat().getFormat(format));
		return cs;
	}

	/**
	 * 数字样式
	 *
	 * @param wb
	 * @return
	 */
	public static CellStyle getNumericCellStyle(Workbook wb, String format) {
		CellStyle cs = getSimpleCellStyle(wb);
		cs.setAlignment(CellStyle.ALIGN_RIGHT); // 水平居右
		format = format != null ? format : format_numeric;
		cs.setDataFormat(wb.createDataFormat().getFormat(format));
		return cs;
	}

	/**
	 * 根据位置判断是否合并单元格
	 * 
	 * @param sheet
	 *            工作表
	 * @param row_index
	 *            起始行
	 * @param col_index
	 *            起始列
	 * @return
	 */
	public static CellRangeAddress getMergedRegion(Sheet sheet, int row_index, int col_index) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstCol = range.getFirstColumn();
			int lastCol = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row_index >= firstRow && row_index <= lastRow) {
				if (col_index >= firstCol && col_index <= lastCol) {
					return new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
				}
			}
		}
		return null;
	}

	/**
	 * 设置单个合并单元格样式(边框)
	 * 
	 * @param sheet
	 *            工作表
	 * @param region
	 *            合并单元格区域
	 * @param style
	 *            单元格样式
	 */
	public static void setMergedRegionStyle(Sheet sheet, CellRangeAddress region, CellStyle style) {
		// 遍历行
		for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {
			Row row = sheet.getRow(i);
			if (row == null)
				row = sheet.createRow(i);
			// 遍历列
			for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
				Cell cell = row.getCell(j);
				if (cell == null) {
					cell = row.createCell(j);
					cell.setCellValue("");
				}
				cell.setCellStyle(style);
			}
		}
	}

	/**
	 * 设置工作表中所有合并单元格样式(边框，背景色)
	 * 
	 * @param sheet
	 *            工作表
	 * @param style
	 *            单元格样式
	 */
	public static void setMergedRegionsStyle(Sheet sheet, CellStyle style) {
		int sheetMergeCount = sheet.getNumMergedRegions();// 获取所有合并单元格数量
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress region = sheet.getMergedRegion(i);
			setMergedRegionStyle(sheet, region, style);
		}
	}

	/**
	 * 根据单元格位置设置内容及样式
	 * 
	 * @param sheet
	 *            工作表
	 * @param row_index
	 *            起始行
	 * @param col_index
	 *            起始列
	 * @param value
	 *            要设置的内容
	 * @param style
	 *            单元格样式
	 */
	public static void setValueByRowAndCol(Sheet sheet, int row_index, int col_index, Object value, CellStyle style) {
		Row row = sheet.getRow(row_index);
		Cell cell = null;
		if (row == null) {
			row = sheet.createRow(row_index);
		}
		cell = row.createCell(col_index);
		if (value != null && StringUtils.isNotBlank(value.toString())) {
			if (value instanceof String) {
				cell.setCellType(Cell.CELL_TYPE_STRING);// 设置内容为字符串格式
				cell.setCellValue((String) value);
			} else if (value instanceof Double) {
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);// 设置内容为数字格式
				cell.setCellValue(Double.parseDouble(value.toString()));
			}
		}
		cell.setCellStyle(style);
	}

	/**
	 * 设置表头单元格内容及样式
	 * 
	 * @param sheet
	 *            工作表
	 * @param isMerged
	 *            是否是合并单元格
	 * @param row_index
	 *            起始行
	 * @param col_index
	 *            起始列
	 * @param value
	 *            要设置的内容
	 * @param style
	 *            单元格样式
	 */
	public static void setTitleCell(Sheet sheet, Boolean isMerged, int row_index, int col_index, String value, CellStyle style) {
		if (isMerged != null && isMerged) {
			CellRangeAddress region = getMergedRegion(sheet, row_index, col_index);
			if (region != null) {
				setMergedRegionStyle(sheet, region, style);
			}
		}
		setValueByRowAndCol(sheet, row_index, col_index, value, style);
	}

}
