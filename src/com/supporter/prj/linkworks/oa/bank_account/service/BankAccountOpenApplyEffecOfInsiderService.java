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
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.role.entity.Role;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountOpenApplyEffecOfInsiderDao;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankMaintenanceLogDao;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApply;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApplyEffec;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankMaintenanceLog;
import com.supporter.prj.linkworks.oa.salary.util.ExportUtils;
import com.supporter.util.CommonUtil;


@Service
public class BankAccountOpenApplyEffecOfInsiderService {
	@Autowired
	private BankAccountOpenApplyEffecOfInsiderDao bankAccountOpenApplyEffecOfInsiderDao;
	
	@Autowired
	private BankMaintenanceLogDao bankMaintenanceLogDao;




	public BankAccountOpenApplyEffec get(String moduleId) {
		return bankAccountOpenApplyEffecOfInsiderDao.get(moduleId);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public BankAccountOpenApplyEffec initEditOrViewPage(JqGrid jqGrid,String effectiveId, UserProfile user) {
		if (StringUtils.isBlank(effectiveId)) {// 新建
			BankAccountOpenApplyEffec BankAccountOpenApplyEffec = newBankAccountOpenApplyEffec(user);
			BankAccountOpenApplyEffec.setAdd(true);
			return BankAccountOpenApplyEffec;			
		} else {// 编辑
			//获得主表
			BankAccountOpenApplyEffec bankAccountOpenApplyEffec =  bankAccountOpenApplyEffecOfInsiderDao.get(effectiveId);				
			return bankAccountOpenApplyEffec;
		}
	}
	/**
	 * 进入查看页面.
	 * @param moduleId
	 * @return
	 */
	public BankAccountOpenApplyEffec viewPage(String effectiveId, UserProfile user) {
			BankAccountOpenApplyEffec bankAccountOpenApplyEffec =  bankAccountOpenApplyEffecOfInsiderDao.get(effectiveId);
			return bankAccountOpenApplyEffec;
	}
	
	
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public BankAccountOpenApplyEffec newBankAccountOpenApplyEffec(UserProfile auserprf_U){
    	BankAccountOpenApplyEffec lbankAccountOpenApplyEffec_N = new BankAccountOpenApplyEffec();
    	lbankAccountOpenApplyEffec_N.setEffectiveId(com.supporter.util.UUIDHex.newId());
    	lbankAccountOpenApplyEffec_N.setApplyId(com.supporter.util.UUIDHex.newId());
    	lbankAccountOpenApplyEffec_N.setCreatedById(auserprf_U.getPersonId());
    	lbankAccountOpenApplyEffec_N.setCreatedBy(auserprf_U.getName()); 
    	String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
    	lbankAccountOpenApplyEffec_N.setCreatedDate(date);
    	
    	lbankAccountOpenApplyEffec_N.setMaintenanceBy(auserprf_U.getName());
    	lbankAccountOpenApplyEffec_N.setMaintenanceById(auserprf_U.getPersonId());
		lbankAccountOpenApplyEffec_N.setMaintenanceDate(date);
    	
    	lbankAccountOpenApplyEffec_N.setAccountOpenerId(auserprf_U.getDeptId());
    	lbankAccountOpenApplyEffec_N.setAccountOpenerId(auserprf_U.getDeptId());
    	if(auserprf_U.getDept()==null){
    		lbankAccountOpenApplyEffec_N.setAccountOpener("无所属部门");
	   	}else{
	   		lbankAccountOpenApplyEffec_N.setAccountOpener(auserprf_U.getDept().getName());
			//1获取部门正职这个角色 
			Role role=EIPService.getRoleService().getRole("DEPTLEADER");
			//2获取创建人所在的部门
			Dept dept=auserprf_U.getDept();
			Person person=null;
			List<Person> persons=EIPService.getRoleService().getPersonsForDept(role, dept);
			if(persons!=null&&persons.size()>0){
				person=persons.get(0);
			}
			if(person!=null){
				lbankAccountOpenApplyEffec_N.setControlledCompanyId(person.getPersonId());
				lbankAccountOpenApplyEffec_N.setControlledCompany(person.getName());
	   		}
	   		

	   	}
//    	//单号
    	lbankAccountOpenApplyEffec_N.setApplicationNumber(getCurrentNo());
    	lbankAccountOpenApplyEffec_N.setStatus(Long.valueOf(BankAccountOpenApplyEffec.COMPLETED_RE+""));
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
		List<BankAccountOpenApplyEffec> list=this.bankAccountOpenApplyEffecOfInsiderDao.findPage(user,jqGrid, bankAccountOpenApplyEffec);//根据输入的查询条件查询列表
		if(list!=null&&list.size()>0){
			for(BankAccountOpenApplyEffec bankAccountOpenApplyEffecZ :list){
				long status=bankAccountOpenApplyEffecZ.getStatus();
				if(status==1){//开立完成未备案
					String targetDate=bankAccountOpenApplyEffecZ.getTargetDate();//审批完成日期
					Boolean isWarning=getTimesOfMS(targetDate,10);//是否符合预警条件
					if(!isWarning){
						bankAccountOpenApplyEffecZ.setColorOfLight("y,开立申请已完成，请备案！");//显示黄灯
					}else{
						bankAccountOpenApplyEffecZ.setColorOfLight("r,当前时间距开立申请完成时间超过10天，请备案！");//显示红灯
					}
				}else if(status==3){//变更完成未备案
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
							bankAccountOpenApplyEffecZ.setColorOfLight("y,距离授权截日期还有不到30天");//显示黄灯
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
		
	 public  String getCurrentNo(){
	 		String no=	bankAccountOpenApplyEffecOfInsiderDao.getApplicationNumber();
	 		SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
			String year=sdf.format(new Date());
 		if(year.equals(no.substring(10,14))){
 			//2017-002
 			//开立（申）001号(2018);
 			String nm = no.substring(5,8);
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
 			no="开立（维）"+no+"号("+year+")";
 		}else{
 			no="开立（维）001号("+year+")";
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
			
			//根据码表id获取码表对应项
			//国别
			String nationality = EIPService.getComCodeTableService().getCodeTable("ALL_COUNTRIES").getDisplay(bankAccountOpenApplyEffec.getNationalityId());
			bankAccountOpenApplyEffec.setNationality(nationality);
			//账户单位性质
			String accountProperty = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_COMPANY").getDisplay(bankAccountOpenApplyEffec.getAccountPropertyId());
			bankAccountOpenApplyEffec.setAccountProperty(accountProperty);				
			//账户性质
			String accountNature = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_ACCOUNT").getDisplay(bankAccountOpenApplyEffec.getAccountNatureId());
			bankAccountOpenApplyEffec.setAccountNature(accountNature);	
			//币别
			String accountCurrency = EIPService.getComCodeTableService().getCodeTable("CURRENCY_CATEGORY").getDisplay(bankAccountOpenApplyEffec.getAccountCurrencyId());
			bankAccountOpenApplyEffec.setAccountCurrency(accountCurrency);	
			//开户银行一级名称
			String openBankFirstName = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_FRIST_N").getDisplay(bankAccountOpenApplyEffec.getOpenBankFirstNameId());
			bankAccountOpenApplyEffec.setOpenBankFirstName(openBankFirstName);	
			//账户属性
			String accountPro = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_PRO").getDisplay(bankAccountOpenApplyEffec.getAccountProId());
			bankAccountOpenApplyEffec.setAccountPro(accountPro);	
			
			if(bankAccountOpenApplyEffec.getNationalityId()!=null&&bankAccountOpenApplyEffec.getNationalityId().equals("BANK-C-001")){//国别等于中国
				bankAccountOpenApplyEffec.setIsOutside("no");
			}else{//国别不等于中国
				bankAccountOpenApplyEffec.setIsOutside("yes");
			}
			//初始化变更次数
			bankAccountOpenApplyEffec.setChangeTimes(0l);
			bankAccountOpenApplyEffec.setModifiedBy(user.getName());
			bankAccountOpenApplyEffec.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");			
			bankAccountOpenApplyEffec.setModifiedDate(date);
			
			bankAccountOpenApplyEffec.setMaintenanceById(user.getPersonId());
			bankAccountOpenApplyEffec.setMaintenanceBy(user.getName());	
			//String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			bankAccountOpenApplyEffec.setMaintenanceDate(date);			

			//编辑之后保存主表
			this.bankAccountOpenApplyEffecOfInsiderDao.saveOrUpdate(bankAccountOpenApplyEffec);
			
			//保存日志信息
			BankMaintenanceLog bankMaintenanceLog=new BankMaintenanceLog();
			bankMaintenanceLog.setLogId(com.supporter.util.UUIDHex.newId());
			bankMaintenanceLog.setLogOperId(user.getPersonId());
			bankMaintenanceLog.setLogOperName(user.getName());
			bankMaintenanceLog.setLogOperDate(date);
			bankMaintenanceLog.setLogContent(mapToString(bankAccountOpenApplyEffec));
			bankMaintenanceLog.setEffectiveId(bankAccountOpenApplyEffec.getEffectiveId());
			
			if(bankAccountOpenApplyEffec.getAdd()){
				bankMaintenanceLog.setLogType("新增");
			}else{
				bankMaintenanceLog.setLogType("修改");
			}
			bankMaintenanceLogDao.save(bankMaintenanceLog);
			
			ret = bankAccountOpenApplyEffec;
			return ret;
	}
	
	/**
	 * 将map转换为String
	 * @return
	 */
    public 	String  mapToString(BankAccountOpenApplyEffec bankAccountOpenApplyEffec){
/*    	Map < String, Object > map = beanToMap(bankAccountOpenApplyEffec);
		JSONObject jsonObject=JSONObject.fromObject(map);
		if(jsonObject!=null){
			String s=jsonObject.toString();	
			if(s!=null){
				String content =s.replaceAll("\"", "");
				//String content = s.replaceAll(",", "\n");
			    return content;
			}else{
				return "";
			}
			
		}else{
			return "";			
		}*/
    	
    	String contentDes ="";
    	if(bankAccountOpenApplyEffec.getIsRelevanceprj()!=null&&bankAccountOpenApplyEffec.getIsRelevanceprj().equals("mail")){
    		contentDes=contentDes+"是否关联项目：是";
    	}else if(bankAccountOpenApplyEffec.getIsRelevanceprj()!=null&&bankAccountOpenApplyEffec.getIsRelevanceprj().equals("femail")){
    		contentDes=contentDes+"是否关联项目：否";
    	}
    	if(bankAccountOpenApplyEffec.getIsPrjSystemInOrOut()!=null&&bankAccountOpenApplyEffec.getIsPrjSystemInOrOut().equals("mail")){
    		contentDes=contentDes+" 项目来源：系统内";
    	}else if(bankAccountOpenApplyEffec.getIsPrjSystemInOrOut()!=null&&bankAccountOpenApplyEffec.getIsPrjSystemInOrOut().equals("femail")){
    		contentDes=contentDes+" 项目来源：系统外";
    	}
    	if(bankAccountOpenApplyEffec.getProjectName()!=null){
    		contentDes=contentDes+" 项目名称："+bankAccountOpenApplyEffec.getProjectName();
    							  
    	}
    	if(bankAccountOpenApplyEffec.getProjectManager()!=null){
    		contentDes=contentDes+" 项目经理："+bankAccountOpenApplyEffec.getProjectManager();
    	}
    	contentDes=contentDes+
    	" 国别Id："+bankAccountOpenApplyEffec.getNationalityId()+
    	" 国别："+bankAccountOpenApplyEffec.getNationality()+
    	" 账户单位性质Id："+bankAccountOpenApplyEffec.getAccountPropertyId()+
    	" 账户单位性质："+bankAccountOpenApplyEffec.getAccountProperty();
    	if(bankAccountOpenApplyEffec.getIsCooperation()!=null&&bankAccountOpenApplyEffec.getIsCooperation().equals("yes")){
    		contentDes=contentDes+" 是否合作银行：是"+" 开户银行Id："+bankAccountOpenApplyEffec.getOpeningBankId();
    	}else if(bankAccountOpenApplyEffec.getIsCooperation()!=null&&bankAccountOpenApplyEffec.getIsCooperation().equals("no")){
    		contentDes=contentDes+" 是否合作银行：否";
    	} 	
    	contentDes=contentDes+
    	" 开户银行："+bankAccountOpenApplyEffec.getOpeningBank()+
    	" 银行地址："+bankAccountOpenApplyEffec.getBankAddress()+
    	" 银行账户名称："+bankAccountOpenApplyEffec.getBankAccountName()+
    	" 开户银行一级名称Id："+bankAccountOpenApplyEffec.getOpenBankFirstNameId()+
    	" 开户银行一级名称："+bankAccountOpenApplyEffec.getOpenBankFirstName()+
    	" 账户属性Id："+bankAccountOpenApplyEffec.getAccountProId()+
    	" 账户属性："+bankAccountOpenApplyEffec.getAccountPro()+
    	" 币别Id："+bankAccountOpenApplyEffec.getAccountCurrencyId()+
    	" 币别："+bankAccountOpenApplyEffec.getAccountCurrency()+
    	" 账户性质Id："+bankAccountOpenApplyEffec.getAccountNatureId()+
    	" 账户性质："+bankAccountOpenApplyEffec.getAccountNature()+
    	" 开户时间："+bankAccountOpenApplyEffec.getOpenTime()+
    	" 账户期限："+bankAccountOpenApplyEffec.getAccountTerm()+
    	" 银行账号："+bankAccountOpenApplyEffec.getThebankAccount()+
    	" 项目情况："+bankAccountOpenApplyEffec.getProjectStatus()+
    	" 授权人Id："+bankAccountOpenApplyEffec.getAuthorizationPersonids()+
    	" 授权人姓名："+bankAccountOpenApplyEffec.getAuthorizationPersonnames()+
    	" 授权开始日期："+bankAccountOpenApplyEffec.getAuthdateFrom()+
    	" 授权截止日期："+bankAccountOpenApplyEffec.getAuthdateTo()+
    	" 权限："+bankAccountOpenApplyEffec.getAuthorization()+
    	" 预留印鉴："+bankAccountOpenApplyEffec.getAuthorizationPeriod()+
    	" 项目所在国外汇管制及税收情况："+bankAccountOpenApplyEffec.getCondition()+
    	" 银行资信情况："+bankAccountOpenApplyEffec.getCredibilityLetter()+
    	" 责任人联系方式："+bankAccountOpenApplyEffec.getContact();//责任人联系方式
    	return contentDes;
    }
    
	/**
	 * 将javabean实体类转为map类型，然后返回一个map类型的值.
	 * @param obj
	 * @return
	 */
/*    private Map < String, Object > beanToMap(Object obj) { 
    	Map < String, Object > params = new HashMap < String, Object >(0); 
        try { 
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj); 
            for (int i = 0; i < descriptors.length; i++) { 
                String name = descriptors[i].getName(); 
                if (!"class".equals(name)) { 
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name)); 
                } 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return params; 
    }*/    

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String effectiveIds) {
		if (StringUtils.isNotBlank(effectiveIds)) {
			for (String effectiveId : effectiveIds.split(",")) {
				BankAccountOpenApplyEffec bankAccountOpenApplyEffec=bankAccountOpenApplyEffecOfInsiderDao.get(effectiveId);
				if(bankAccountOpenApplyEffec==null){
					continue;
				}
				//保存日志信息
				BankMaintenanceLog bankMaintenanceLog=new BankMaintenanceLog();
				bankMaintenanceLog.setLogId(com.supporter.util.UUIDHex.newId());
				bankMaintenanceLog.setLogOperId(user.getPersonId());
				bankMaintenanceLog.setLogOperName(user.getName());
				String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");			
				bankMaintenanceLog.setLogOperDate(date);
				bankMaintenanceLog.setLogContent(mapToString(bankAccountOpenApplyEffec));
				bankMaintenanceLog.setEffectiveId(bankAccountOpenApplyEffec.getEffectiveId());				
				bankMaintenanceLog.setLogType("删除");
				bankMaintenanceLogDao.save(bankMaintenanceLog);				
				//删除主表
				this.bankAccountOpenApplyEffecOfInsiderDao.delete(bankAccountOpenApplyEffec);
				
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
		this.bankAccountOpenApplyEffecOfInsiderDao.update(bankAccountOpenApplyEffec);
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
			List<BankAccountOpenApplyEffec> list = bankAccountOpenApplyEffecOfInsiderDao.findPage(user,jqGrid, bankAccountOpenApplyEffec);
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
