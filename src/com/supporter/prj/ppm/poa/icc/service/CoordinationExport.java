package com.supporter.prj.ppm.poa.icc.service;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.online_user.util.ExportUtils;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.poa.icc.dao.CoordinationDao;
import com.supporter.prj.ppm.poa.icc.entity.Coordination;
import com.supporter.util.JsonUtils;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@Service
@Transactional(TransManager.APP)
@SuppressWarnings({ "unchecked","unused","deprecation" })
public class CoordinationExport {
	@Autowired
	private CoordinationDao coordinationDao;
	public HSSFWorkbook getCoordinationImp(String iccIds) {
		
		String excelFileName = "Coordination_EXP.xls";
		HSSFWorkbook wb = null;
		try {
			ExportUtils util = new ExportUtils();
			File file = util.getExcelDemoFile(File.separator + "template_excel_def" + File.separator + excelFileName);
			wb = util.getExcelWorkbook(file);
			
			int s1 = 1;
			int s2 = 1;
			int s3 = 1;
			int s4 = 1;
			int s5 = 1;
			HSSFCellStyle cellStyle = wb.createCellStyle();
//			cellStyle.setBorderBottom(BorderStyle.THIN);
//			cellStyle.setBorderLeft(BorderStyle.THIN);
//			cellStyle.setBorderTop(BorderStyle.THIN);
//			cellStyle.setBorderRight(BorderStyle.THIN);
			// ��һ��sheet,��������
			List<Coordination> list = new ArrayList<Coordination>();
			if (StringUtils.isNotBlank(iccIds)) {
				for (String codeId : iccIds.split(",")) {
					Coordination codeDb = this.coordinationDao.get(codeId);
					if (codeDb == null) {
						continue;
					}
					list.add(codeDb);
				}
				if ((list != null) && (list.size() != 0)) {
					int length = list.size();
					HSSFSheet sheet = wb.getSheetAt(0);

					HSSFRow row = null;
					HSSFCell cell = null;
					for (int i = 0; i < length; i++) {
						row = sheet.createRow(i + 1);

						cell = row.createCell(0, 1);
						cell.setCellValue(((Coordination) list.get(i)).getProNo());//��Ŀ���
						cell.setCellStyle(cellStyle);

						cell = row.createCell(1, 1);
						cell.setCellValue(((Coordination) list.get(i)).getPrjCName());//��Ŀ��������
						cell.setCellStyle(cellStyle);

						cell = row.createCell(2, 1);
						cell.setCellValue(((Coordination) list.get(i)).getPrjEName());// ��ĿӢ������
						cell.setCellStyle(cellStyle);

						cell = row.createCell(3, 1);
						cell.setCellValue(((Coordination) list.get(i)).getCoTitle());// ֤������
						cell.setCellStyle(cellStyle);

						cell = row.createCell(4, 1);
						cell.setCellValue(((Coordination) list.get(i)).getCoTypeName());// ֤������
						cell.setCellStyle(cellStyle);

						cell = row.createCell(5, 1);
						cell.setCellValue(((Coordination) list.get(i)).getCoDesc());// �ڲ�����ժҪ

						cell.setCellStyle(cellStyle);

						cell = row.createCell(6, 1);
						cell.setCellValue(((Coordination) list.get(i)).getPrjSrcDept());// ��Ŀԭ����
						cell.setCellStyle(cellStyle);

						cell = row.createCell(7, 1);
						cell.setCellValue(((Coordination) list.get(i)).getPrjTrgDept());// ������ⲿ��
						cell.setCellStyle(cellStyle);

						cell = row.createCell(8, 1);
						cell.setCellValue(((Coordination) list.get(i)).getLinkmanName());// ��ϵ������
						cell.setCellStyle(cellStyle);
						
						cell = row.createCell(10, 1);
						cell.setCellValue(((Coordination) list.get(i)).getLinkmanTel());// ��ϵ�˵绰
						cell.setCellStyle(cellStyle);
						

						
					
						
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}
}
