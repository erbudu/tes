package com.supporter.prj.linkworks.oa.bank_account.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountChangeApplyDao;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountOpenApplyEffecDao;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountRevokeApplyDao;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountChangeApply;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApply;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApplyEffec;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountRevokeApply;
import com.supporter.prj.linkworks.oa.salary.util.ExportUtils;
import com.supporter.util.CommonUtil;


@Service
public class BankAccountOpenApplyEffecService {
	@Autowired
	private BankAccountOpenApplyEffecDao bankAccountOpenApplyEffecDao;
	@Autowired
	private BankAccountChangeApplyDao bankAccountChangeApplyDao;
	@Autowired
	private BankAccountRevokeApplyDao bankAccountRevokeApplyDao;	



	public BankAccountOpenApplyEffec get(String moduleId) {
		return bankAccountOpenApplyEffecDao.get(moduleId);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public BankAccountOpenApplyEffec initEditOrViewPage(JqGrid jqGrid,String effectiveId, UserProfile user) {
		if (StringUtils.isBlank(effectiveId)) {// 新建
			return null;
		} else {// 编辑
			//获得主表
			BankAccountOpenApplyEffec bankAccountOpenApplyEffec =  bankAccountOpenApplyEffecDao.get(effectiveId);	
			return bankAccountOpenApplyEffec;
		}
	}
	
	
	/**
	 * 进入查看页面.
	 * @param moduleId
	 * @return
	 */
	public BankAccountOpenApplyEffec viewPage(String effectiveId, UserProfile user) {
			BankAccountOpenApplyEffec bankAccountOpenApplyEffec =  bankAccountOpenApplyEffecDao.get(effectiveId);
			//如果有旧系统的流程，则获取旧系统的procId
			//long oldProcId = cneecExamService.getProcIdByRecord(bankAccountOpenApplyEffec);
			//if (oldProcId > 0)bankAccountOpenApplyEffec.setOldProcId(oldProcId);
//			bankAccountOpenApplyEffec.setBankAccountOpenApplyContent(bankAccountOpenApplyEffecContentDao.get(effectiveId));
			//bankAccountOpenApplyEffec.setFiles(getFiles(bankAccountOpenApplyEffec));
			return bankAccountOpenApplyEffec;
	}
	
	
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public BankAccountOpenApplyEffec newBankAccountOpenApplyEffec(UserProfile auserprf_U){
    	BankAccountOpenApplyEffec lbankAccountOpenApplyEffec_N = new BankAccountOpenApplyEffec();
    	lbankAccountOpenApplyEffec_N.setApplyId(com.supporter.util.UUIDHex.newId());
    	lbankAccountOpenApplyEffec_N.setCreatedById(auserprf_U.getPersonId());
    	lbankAccountOpenApplyEffec_N.setCreatedBy(auserprf_U.getName()); 
    	String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
    	lbankAccountOpenApplyEffec_N.setCreatedDate(date);
    	lbankAccountOpenApplyEffec_N.setAccountOpenerId(auserprf_U.getDeptId());
    	if(auserprf_U.getDept()==null){
    		lbankAccountOpenApplyEffec_N.setAccountOpener("无所属部门");
	   	}else{
	   		lbankAccountOpenApplyEffec_N.setAccountOpener(auserprf_U.getDept().getName());
	   	}
//    	//单号
    	lbankAccountOpenApplyEffec_N.setApplicationNumber(getCurrentNo());
    	lbankAccountOpenApplyEffec_N.setStatus(Long.valueOf(BankAccountOpenApply.DRAFT+""));
        return lbankAccountOpenApplyEffec_N;
    }
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<BankAccountOpenApplyEffec> getGrid(UserProfile user, JqGrid jqGrid, BankAccountOpenApplyEffec bankAccountOpenApplyEffec) {
		List<BankAccountOpenApplyEffec> list=this.bankAccountOpenApplyEffecDao.findPage(user,jqGrid, bankAccountOpenApplyEffec);//根据输入的查询条件查询列表
		if(list!=null&&list.size()>0){
			for(BankAccountOpenApplyEffec bankAccountOpenApplyEffecZ :list){
				long status=bankAccountOpenApplyEffecZ.getStatus();
//				if(status==1){//开立完成未备案
//					String targetDate=bankAccountOpenApplyEffecZ.getTargetDate();//审批完成日期
//					Boolean isWarning=getTimesOfMS(targetDate,10);//是否符合预警条件
//					if(!isWarning){
//						bankAccountOpenApplyEffecZ.setColorOfLight("y,开立申请已完成，请备案！");//显示黄灯
//					}else{
//						bankAccountOpenApplyEffecZ.setColorOfLight("r,当前时间距开立申请完成时间超过10天，请备案！");//显示红灯
//					}
//				}else
					if(status==3){//变更完成未备案
					String chanTargetDate=bankAccountOpenApplyEffecZ.getChanTargetDate();//审批完成日期
					Boolean isWarning=getTimesOfMS(chanTargetDate,5);//是否符合预警条件
					if(!isWarning){
						bankAccountOpenApplyEffecZ.setColorOfLight("y,变更申请已完成，请备案！");//显示黄灯
					}else{
						bankAccountOpenApplyEffecZ.setColorOfLight("r,当前时间距变更申请完成时间超过5天，请备案！");//显示红灯
					}
					
				}
				
				String authdateTo=bankAccountOpenApplyEffecZ.getAuthdateTo();//授权截止日期
				String authdateTofmt=CommonUtil.format(authdateTo,"yyyy-MM-dd HH:mm:ss");
				if(authdateTofmt!=null){
					Date authdateToDate=CommonUtil.parseDateTime(authdateTofmt);
					if(authdateToDate!=null) {
						long authdateToMs=authdateToDate.getTime();//授权期限的毫秒数
						long tremMouth=1000l*60l*60l*24l*30l;//一个月默认为30天（这是30天的毫秒数）
						long dateNowMS=new Date().getTime();
						if(dateNowMS>(authdateToMs-tremMouth)&&dateNowMS<authdateToMs){//说明距离授权期限小于30天
							if(bankAccountOpenApplyEffecZ.getColorOfLight()!=null&&bankAccountOpenApplyEffecZ.getColorOfLight().indexOf("r")==-1){//还没有红灯预警
								bankAccountOpenApplyEffecZ.setColorOfLight("y,距离授权截日期还有不到30天");//显示黄灯
							}
						}else if(authdateToMs<=dateNowMS){//已经到授权期限或者已经超过授权期限
							bankAccountOpenApplyEffecZ.setColorOfLight("r,授权截止日期已过，授权已失效");//显示红灯
						}
					}
				}

			}			
		}

			return list;			
	}
	
	//获取两个时间段的毫秒数
	public boolean getTimesOfMS(String date,int days){
		if(date!=null){
		    Date dateFrom=CommonUtil.parseDateTime(date);
		    Date dateNow=new Date(); 
	        long between = dateNow.getTime() - dateFrom.getTime();//两个日期间的毫秒数
	        long term=1000*60*60*24;//1天的毫秒数
	        if(between>term*days){//超过预定的期限
	        	return true;
	        }else{
	        	return false;
	        }
        }else{
        	return false;
        }
 
	}
	
	
	
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public BankAccountOpenApplyEffec saveOrUpdate(UserProfile user, BankAccountOpenApplyEffec bankAccountOpenApplyEffec, Map< String, Object > valueMap) {
		BankAccountOpenApplyEffec ret = null;
//		if (bankAccountOpenApplyEffec.getAdd()) {// 新建
//			//保存主表
//			this.bankAccountOpenApplyEffecDao.save(bankAccountOpenApplyEffec);			
//			ret = bankAccountOpenApplyEffec;
//		} else {// 编辑
			bankAccountOpenApplyEffec.setModifiedBy(user.getName());
			bankAccountOpenApplyEffec.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			bankAccountOpenApplyEffec.setModifiedDate(date);
			//编辑之后保存主表
			this.bankAccountOpenApplyEffecDao.update(bankAccountOpenApplyEffec);

			ret = bankAccountOpenApplyEffec;			
//		}
		return ret;

	}

	
	 public  String getCurrentNo(){
		 		String no=	bankAccountOpenApplyEffecDao.getApplicationNumber();
			    SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
				String year=sdf.format(new Date());
	    		if(year.equals(no.substring(0,4))){
	    			//2017-002
	    			String nm = no.substring(5);
	    			String number = String.valueOf((Integer.parseInt(nm)+1));
	    			if(number.length()<nm.length()){
	    				no = "";
	    				for (int i = 0; i < nm.length()-number.length(); i++) {
							no += "0";
						}
	    				no += number;
	    			}else{
	    				no = number;
	    			}
	    			no = year+"-"+no;
	    		}else{
	    			no = year+"-001";
	    		}
	    	return no;
	    }
	
	
	
    /**
	 * 保存提交
	 * 
	 * @param user 用户信息
	 * @param apply 实体类
	 * @return
	 */
	public BankAccountOpenApplyEffec commit(UserProfile user, BankAccountOpenApplyEffec bankAccountOpenApplyEffec, Map< String, Object > valueMap) {
		BankAccountOpenApplyEffec ret = null;
//		boolean isNew=bankAccountOpenApplyEffec.getAdd();
//		if(isNew){
//			 
//			//状态（审批完成未备案）
//			bankAccountOpenApplyEffec.setStatus(Long.valueOf(BankAccountOpenApply.COMPLETED_NORE+""));
//			//保存主表
//			this.bankAccountOpenApplyEffecDao.save(bankAccountOpenApplyEffec);
//			
//			ret = bankAccountOpenApplyEffec;
//			} else {// 编辑
			
			bankAccountOpenApplyEffec.setModifiedBy(user.getName());
			bankAccountOpenApplyEffec.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			bankAccountOpenApplyEffec.setModifiedDate(date);
			//状态（审批完成未备案）
			bankAccountOpenApplyEffec.setStatus(Long.valueOf(BankAccountOpenApply.COMPLETED_NORE+""));


			//编辑之后保存主表
			this.bankAccountOpenApplyEffecDao.update(bankAccountOpenApplyEffec);;
			ret = bankAccountOpenApplyEffec;
//			}
		return ret;
	}
	

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String effectiveIds) {
		if (StringUtils.isNotBlank(effectiveIds)) {
			for (String effectiveId : effectiveIds.split(",")) {
				BankAccountOpenApplyEffec bankAccountOpenApplyEffec=bankAccountOpenApplyEffecDao.get(effectiveId);
				if(bankAccountOpenApplyEffec==null){
					continue;
				}
				//权限验证(判断是不是有编辑信息系统维护申请的权限)
				//AuthUtil.canExecute(user, BankaccountOpenAuthConstant.AUTH_OPER_NAME_AUTHMAINTENANCEOFBTN, bankAccountOpenApplyEffec.getBankAccountOpenApplyId(), bankAccountOpenApplyEffec);	
				//删除主表
				this.bankAccountOpenApplyEffecDao.delete(bankAccountOpenApplyEffec);
				
				//删除content
				//BankAccountOpenApplyContent bankAccountOpenApplyEffecContent=bankAccountOpenApplyEffecContentDao.get(effectiveId);
//				if(bankAccountOpenApplyEffecContent!=null){
//				    this.bankAccountOpenApplyEffecContentDao.delete(bankAccountOpenApplyEffecContent);
//				}
				

			}
			// 记录日志
//			String logMessage = MessageFormat.format(
//					LogConstant.DELETE_MAINTENANCE_LOG_MESSAGE, "删除的主键id为："+effectiveIds);
//			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
//					user, LogConstant.DELETE_MAINTENANCE_LOG_ACTION, logMessage,
//					null, null);

		}
	}
	
	
	/**
	 * 附件下载部分
	 * @param bankAccountOpenApplyEffec
	 * @return
	 */
	public String getFiles(BankAccountOpenApply bankAccountOpenApplyEffec){
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OAMAINTENAN", "OAMAINTENAN2", bankAccountOpenApplyEffec.getApplyId());
		for(IFile file:list){
			sb.append("<a onclick=\"downloads('"+ file.getFileId() +"');\">" + file.getFileName() +"</a><br/>");
		}
		return sb.toString();
	}
	
	
	
	/**
	 * 修改（流程处理类调用的方法，用于修改状态和保存流程id）
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public void update(BankAccountOpenApplyEffec bankAccountOpenApplyEffec) {	
		this.bankAccountOpenApplyEffecDao.update(bankAccountOpenApplyEffec);
	}

	/**
	 * 修改（流程处理类调用的方法，用于修改状态和保存流程id）
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public String queryHaveData(String effectiveId) {	
		int dataSum=0;
		List<BankAccountChangeApply> bankAccountChangeApplyList=this.bankAccountChangeApplyDao.getBankAccountChangeApplyListByEffectiveId(effectiveId);
		
		if(bankAccountChangeApplyList!=null){
			dataSum=dataSum+bankAccountChangeApplyList.size();
		}
		List<BankAccountRevokeApply> bankAccountRevokeApplyList=this.bankAccountRevokeApplyDao.getBankAccountRevokeApplyListByEffectiveId(effectiveId);
		
		if(bankAccountRevokeApplyList!=null){
			dataSum=dataSum+bankAccountRevokeApplyList.size();
		}
		if(dataSum>0){
			return "yes";
		}else{
			return "no";
		}
	}
	
	
	
	
	/**
	 *   导出Excel：查询结果
	 */ 
	public HSSFWorkbook getWorkbook(BankAccountOpenApplyEffec bankAccountOpenApplyEffec, UserProfile user,JqGrid jqGrid) {
		String excelFileName = "bankAccountOpenApplyEffec.xls";
		HSSFWorkbook wb = null;
		try {
			ExportUtils util = new ExportUtils();
			File file = util.getExcelDemoFile(File.separator
					+ "template_excel_def" + File.separator + excelFileName);
			wb = util.getExcelWorkbook(file);
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setBorderBottom((short) 1);
			cellStyle.setBorderLeft((short) 1);
			cellStyle.setBorderTop((short) 1);
			cellStyle.setBorderRight((short) 1);
			List<BankAccountOpenApplyEffec> list = bankAccountOpenApplyEffecDao.findPage(user,jqGrid, bankAccountOpenApplyEffec);
			if ((list != null) && (list.size() != 0)) {
				int length = list.size();
				Sheet sheet = wb.getSheetAt(0);

				Row row = null;
				Cell cell = null;
				for (int i = 0; i < length; i++) {
					row = sheet.createRow(i + 2);

					cell = row.createCell(0, 1);
					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
							.getAccountOpener());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(1, 1);
					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
							.getOpeningBank());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(2, 1);
					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
							.getThebankAccount());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(3, 1);
					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
							.getOpenTime());
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(4, 1);
					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
							.getAccountCurrency());
					cell.setCellStyle(cellStyle);
	
					cell = row.createCell(5, 1);
					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
							.getAuthorizationPersonnames()); 
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(6, 1);
					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
							.getAuthdateFrom());//授权开始日期
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(7, 1);
					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
							.getAuthdateTo());//授权结束日期
					cell.setCellStyle(cellStyle);
					
					cell = row.createCell(8, 1);
					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
							.getStatusDesc());//状态
					cell.setCellStyle(cellStyle);
//															
//					cell = row.createCell(9, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getFangBu());
//					cell.setCellStyle(cellStyle);
//
//					cell = row.createCell(10, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getQuNuanBuTie());
//					cell.setCellStyle(cellStyle);
//
//					cell = row.createCell(11, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getGangWeiGongZi());
//					cell.setCellStyle(cellStyle);
//
//					cell = row.createCell(12, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getBuChongYangLao());
//					cell.setCellStyle(cellStyle);
//					
//					cell = row.createCell(13, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getBuFaYangLao());
//					cell.setCellStyle(cellStyle);
//					
//					cell = row.createCell(14, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getYuZhiJiangJin());
//					cell.setCellStyle(cellStyle);
//					
//					cell = row.createCell(15, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getBaoXiaoYaoFei());
//					cell.setCellStyle(cellStyle);
//					
//					cell = row.createCell(16, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getQiTaBuFa());
//					cell.setCellStyle(cellStyle);
//																					
//					cell = row.createCell(17, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getYingFaHeJi());
//					cell.setCellStyle(cellStyle);
//
//					cell = row.createCell(18, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getGongZiKouKuan());
//					cell.setCellStyle(cellStyle);
//
//					cell = row.createCell(19, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getWuCanKouKuan());
//					cell.setCellStyle(cellStyle);
//
//					cell = row.createCell(20, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getFangZu());
//					cell.setCellStyle(cellStyle);
//
//					cell = row.createCell(21, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getZhuFangGongJiJin());
//					cell.setCellStyle(cellStyle);
//					
//					cell = row.createCell(22, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getKouBuChongYangLao());
//					cell.setCellStyle(cellStyle);
//					
//					cell = row.createCell(23, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getYangLaoBaoXian());
//					cell.setCellStyle(cellStyle);
//					
//					cell = row.createCell(24, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getShiYeBaoXian());
//					cell.setCellStyle(cellStyle);
//					
//					cell = row.createCell(25, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getYiLiaoBaoXian());
//					cell.setCellStyle(cellStyle);
//					
//					cell = row.createCell(26, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getQiTaKouKuan());
//					cell.setCellStyle(cellStyle);
//					
//					cell = row.createCell(27, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getYaoFei());
//					cell.setCellStyle(cellStyle);
//					
//					cell = row.createCell(28, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getDaiKouShui());
//					cell.setCellStyle(cellStyle);
//					
//					cell = row.createCell(29, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getKouKuanHeJi());
//					cell.setCellStyle(cellStyle);
//					
//					cell = row.createCell(30, 1);
//					cell.setCellValue(((BankAccountOpenApplyEffec) list.get(i))
//							.getShiFaHeJi());
//					cell.setCellStyle(cellStyle);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}




	
	
	
}
