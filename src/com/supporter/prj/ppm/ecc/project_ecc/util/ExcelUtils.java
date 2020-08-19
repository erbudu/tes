package com.supporter.prj.ppm.ecc.project_ecc.util;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;

/**
 * @Description 处理Excel文件的工具类。
 * @author chenxiaobin
 *
 */
public class ExcelUtils {
	
	/**
	 * @Description 复制行，xlsx格式
	 * @param srcRow
	 * @param destRow
	 */
	public static void copyRow(XSSFRow srcRow, XSSFRow destRow) {
		destRow.setHeight(srcRow.getHeight());
		// 只复制B-E列
		for (int i = 1; i < 5; i++) {
			XSSFCell srcCell = srcRow.getCell(i);
			if (srcCell != null) {
				XSSFCell destCell = destRow.createCell(i);
				copyCell(srcCell, destCell);
			}
		}
	}
	
	/**
	 * @Description 复制单元格，xlsx格式
	 */
	public static void copyCell(XSSFCell srcCell, XSSFCell destCell) {
		// 复制单元格样式
		XSSFCellStyle srcCellStyle = srcCell.getCellStyle();
		XSSFCellStyle destCellStyle = destCell.getRow().getSheet().getWorkbook().createCellStyle();
		destCellStyle.cloneStyleFrom(srcCellStyle);
		destCellStyle.setFont(srcCellStyle.getFont());
		destCell.setCellStyle(destCellStyle);
		// 复制单元格内容
		destCell.setCellValue(srcCell.getStringCellValue());
	}
}
