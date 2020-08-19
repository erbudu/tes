package com.supporter.prj.linkworks.oa.integral.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportUtils
{
  @SuppressWarnings("unused")
private static final String excel2003L = ".xls";
  @SuppressWarnings("unused")
private static final String excel2007U = ".xlsx";
  
  public File getExcelDemoFile(String fileDir)
    throws Exception
  {
    String classDir = null;
    String fileBaseDir = null;
    File file = null;
    classDir = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
    fileBaseDir = classDir.substring(0, classDir.lastIndexOf("classes"));
    
    file = new File(fileBaseDir + fileDir);
    if (!file.exists()) {
      throw new Exception("模板文件不存在！");
    }
    return file;
  }
  
  public HSSFWorkbook getExcelWorkbook(File file)
    throws Exception
  {
    HSSFWorkbook wb = null;
    FileInputStream fis = new FileInputStream(file);
    wb = new HSSFWorkbook(fis);
    return wb;
  }
  
  public Workbook getWorkbook(InputStream inStr, String fileName)
    throws Exception
  {
    Workbook wb = null;
    String fileType = fileName.substring(fileName.lastIndexOf("."));
    if (".xls".equals(fileType)) {
      wb = new HSSFWorkbook(inStr);
    } else if (".xlsx".equals(fileType)) {
      wb = new XSSFWorkbook(inStr);
    } else {
      throw new Exception("解析的文件格式有误！");
    }
    return wb;
  }
  
  public CellStyle setSimpleCellStyle(Workbook wb)
  {
    CellStyle cs = wb.createCellStyle();
    
    cs.setBorderBottom((short)1);
    cs.setBorderLeft((short)1);
    cs.setBorderTop((short)1);
    cs.setBorderRight((short)1);
    
    cs.setAlignment((short)2);
    
    return cs;
  }
}
