package com.supporter.prj.pm.enginee_negotiate.service;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.supporter.prj.eip.dept.util.ExportUtils;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.enginee_negotiate.dao.EngineeVisaSiteDao;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisaSite;
import com.supporter.util.CommonUtil;

@Service
@Transactional(TransManager.APP)
public class ExportEngineeVisaService {
	
	@Autowired
	private EngineeVisaSiteDao siteDao;
	
	 public HSSFWorkbook getWorkbook(String nodeId, UserProfile userProfile) {
	    String excelFileName = "engineeVisa.xls";
	    HSSFWorkbook wb = null;
	    try {
	      ExportUtils util = new ExportUtils();
	      File file = util.getExcelDemoFile(File.separator + "template_excel_def" + File.separator + excelFileName);
	      wb = util.getExcelWorkbook(file);

	      HSSFCellStyle cellStyle = wb.createCellStyle();
 
	      //cellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
	      //cellStyle.setBorderLeft(BorderStyle.THIN);		// 左边框
	      //cellStyle.setBorderTop(BorderStyle.THIN);		// 上边框
	      //cellStyle.setBorderRight(BorderStyle.THIN);	// 右边框
	      
	      List<EngineeVisaSite> list = siteDao.getSiteList(nodeId);
	      //树层次排序(level+isLeaf+rootParentNull)
		  list = orderTreeLevel(list);
	      
	      Sheet sheet = wb.getSheetAt(0);
	      Row row = null;
	      Cell cell = null;
	      //row = sheet.createRow(1);

	      int length = list.size();
	        for (int i = 0; i < length; i++) {
	          //从第二行开始
	          row = sheet.createRow(i + 2);

	          cell = row.createCell(0, 1);
	          cell.setCellValue(list.get(i).getWbsNameDesc());//工程部位
	          cell.setCellStyle(cellStyle);

	          cell = row.createCell(1, 1);
	          cell.setCellValue(list.get(i).getDrawingNo());//施工图号
	          cell.setCellStyle(cellStyle);

	          cell = row.createCell(2, 1);
	          cell.setCellValue(list.get(i).getInputWorkTime());//工时
	          cell.setCellStyle(cellStyle);

	          cell = row.createCell(3, 1);
	          cell.setCellValue(list.get(i).getInputDevice());//机械
	          cell.setCellStyle(cellStyle);

	          cell = row.createCell(4, 1);
	          cell.setCellValue(list.get(i).getInputMaterial());//材料
	          cell.setCellStyle(cellStyle);

	          cell = row.createCell(5, 1);
	          cell.setCellValue(list.get(i).getPriceUnit());//综合单价-单位
	          cell.setCellStyle(cellStyle);

	          cell = row.createCell(6, 1);
	          cell.setCellValue(list.get(i).getPrice());//综合单价-数量
	          cell.setCellStyle(cellStyle);
	          
	          cell = row.createCell(7, 1);
	          cell.setCellValue(list.get(i).getCountUnit());//变更工程量-单位
	          cell.setCellStyle(cellStyle);
	          
	          cell = row.createCell(8, 1);
	          cell.setCellValue(list.get(i).getBuyCount());//变更工程量-数量
	          cell.setCellStyle(cellStyle);
	          
	          cell = row.createCell(9, 1);
	          cell.setCellValue(list.get(i).getTotal());//变更金额
	          cell.setCellStyle(cellStyle);
	          

	        }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return wb;
	  }
	 
	 
	 /**
	 * 树层次排序(level+isLeaf+rootParentNull)
	 * @param list 树节点列表
	 * @return List<EngineeVisaSite>
	 */
	private List<EngineeVisaSite> orderTreeLevel(List<EngineeVisaSite> list) {
		if (list == null || list.size() == 0) {
			return null;
		}
		
		int listSize = list.size();
		List < EngineeVisaSite > treeNodes = new ArrayList < EngineeVisaSite >();
		Deque < EngineeVisaSite > nodeDeque = new ArrayDeque < EngineeVisaSite >();
		
		//找根节点
		for (int i = 0; i < listSize; i++) {
			EngineeVisaSite site = list.get(i);
			if (StringUtils.isBlank(site.getParentSiteId())) {
				site.setWbsNameDesc(site.getWbsName());
				site.setLevel(0);
				site.setIsLeaf(true);
				site.setParentSiteId(null);
				nodeDeque.add(site);
				break;
			}
		}
		//遍历生成子树节点
		while (!nodeDeque.isEmpty()) {
			EngineeVisaSite node = nodeDeque.peekFirst();
			String id = node.getSiteId();
			//获得节点的子节点
			for (int i = 0; i < listSize; i++) {
				EngineeVisaSite temp = list.get(i);
				String parentId = temp.getParentSiteId();
				
				if (StringUtils.isBlank(parentId)) {
					continue;
				}
				if (id.equals(parentId)) {
					node.setIsLeaf(false);
					temp.setWbsNameDesc(getWbsNameExcel(temp));
					temp.setLevel(node.getLevel() + 1);
					temp.setIsLeaf(true);
					nodeDeque.add(temp);
				}
			}
			treeNodes.add(node);
	        nodeDeque.pollFirst();
	    }
		return treeNodes;
	}
	 	
	//获取层级
	public int getIndex(int index,EngineeVisaSite site){
		int i = index;
		String parentId = site.getParentSiteId();
		if(!CommonUtil.trim(parentId).equals("")){
			index ++;
			EngineeVisaSite parentSite = siteDao.getSiteByParentId(parentId);
			if(parentSite != null){
				i = getIndex(index,parentSite);
			}
		}
		return i;
	}
	
	//处理wbsName
	public String getWbsNameExcel(EngineeVisaSite site){
		int index = getIndex(0,site);
		String wbsName = site.getWbsName();
		for(int x = 0;x < index;x++){
			wbsName ="  "+ wbsName;
		}
		return wbsName;
	}	
}
