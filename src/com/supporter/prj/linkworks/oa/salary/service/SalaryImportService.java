package com.supporter.prj.linkworks.oa.salary.service;

import java.io.File;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.account.service.AccountEntityService;
import com.supporter.prj.eip.dept.dao.CompanyDao;
import com.supporter.prj.eip.dept.service.ContextListenerService;
import com.supporter.prj.eip.dept.service.IDepartmentService;

import com.supporter.prj.eip.emp.service.DeptPostEmpBusiService;

import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.excel.handler.IExcelImportHandler;
import com.supporter.prj.eip_service.excel.vo.ExcelImportParam;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.membership_dues.service.MembershipDuesService;
import com.supporter.prj.linkworks.oa.report.constant.LogConstant;
import com.supporter.prj.linkworks.oa.salary.dao.SalaryDao;
import com.supporter.prj.linkworks.oa.salary.entity.Salary;
import com.supporter.prj.linkworks.oa.salary.util.AuthConstant;
import com.supporter.prj.linkworks.oa.salary.util.AuthUtil;
import com.supporter.prj.log4j.XLogger;
import com.supporter.util.CommonUtil;

// ~ File Information
// ====================================================================================================================
/**
 * 工资导入service实现类
 * 
 * <pre>
 * 工资导入service实现类
 * </pre>
 * 
 * @author jiaotilei
 * @createDate 2017-2-22
 * @since TODO: 无
 * @modifier jiaotilei
 * @modifiedDate 2017-2-22
 */
@Service("salaryImportService")
public class SalaryImportService implements IExcelImportHandler {

	// ~ Static Fields
	// ================================================================================================================
	public static final String POST_TYPE_NAME_COMMON = "普通职务";

	public static final String POST_TYPE_NAME_MR = "管理职务";

	public static final String IS_YES = "是";

	public static final String IS_NO = "否";

	private static final String ROOTPID = "0";

	private static final int POST_TYPE_NAME_COMMON_KEY = 0;

	public static final String FILE_SPLIT = "/";

	private static final String ACTIVE_START = "启用";

	private static final String ACTIVE_STOP = "停用";

	private static final int START_ROW = 2;
	
	public static final String DEPT_MANAGER_ID = "4028e3f75f5237c9015f530afaf40003";
	// ~ Fields
	// ================================================================================================================
	@SuppressWarnings("unused")
	@Autowired
	private DeptPostEmpBusiService deptPostEmpBusiService;
	@SuppressWarnings("unused")
	@Autowired
	private AccountEntityService accountService;

	@SuppressWarnings("unused")
	@Autowired
	private IDepartmentService eipDepartmentService;

	@SuppressWarnings("unused")
	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private SalaryDao salaryDao;

	private ContextListenerService contextListenerService;
	
	
	@Autowired
	private MembershipDuesService membershipDuesService;

	private ContextListenerService getContextListenerCacheService() {
		if (contextListenerService == null) {
			contextListenerService = SpringContextHolder
					.getBean(ContextListenerService.class);
		}
		return contextListenerService;
	}

	// ~ Constructors
	// ================================================================================================================

	// ~ Methods
	// ================================================================================================================

	/**
	 * 自定义导入
	 */
	public Map<Integer, String> customExcelImport(UserProfile arg0, File arg1,
			ExcelImportParam arg2) {
		return null;
	}

	/**
	 * 
	 * 数据导入
	 * 
	 * <pre>
	 * 数据导入
	 * </pre>
	 * 
	 */
	@Transactional(TransManager.SYS)
	public Map<Integer, String> semiAutomaticImport(UserProfile userProfile,
			List<Map<String, Object>> dataList,
			ExcelImportParam excelImportParam) {		
		Map<Integer, String> returnMap = new HashMap<Integer, String>();
		try {
			// 返回每行的错误信息Map
			int allCount = dataList.size();
			
			
//			if (allCount >= 2) {
//				allCount = allCount - 2;
//			}
			
			
			returnMap.put(ExcelImportParam.IMPORT_RESULT_KEY_TOTAL_COUNT,
					String.valueOf(allCount));
			// String nodeId = excelImportParam.getParam();
			// Map<String, TreeNode> orgTreeNodeMap = this.getOrgTreeMap();
			for (int i = 0; i < dataList.size(); i++) {
				Map<String, Object> data = dataList.get(i);
				Salary salary = new Salary();
				// errorMsg = errorMsg+"";
				int rowIndex = (Integer) data.get("rowIndex");
				if(!this.initSalary(salary, data,rowIndex, returnMap)){
					continue;
				}
				if (!this.checkPerson(salary.getEmpNo(),salary,rowIndex, returnMap)) {
					continue;
				}
//				if (!this.checkIsPartyMember(salary.getIsPartyMember(),rowIndex,returnMap)) {
//					continue;
//				}
				
				//保存之前进行权限验证
				//权限验证(判断是不是有导入的权限)
				AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_AUTHSALARYOFBTN, salary.getRecordId(), salary);									
				
				salaryDao.saveOrUpdate(salary);
				//判断是工资还是奖金（如果是工资就根据是否是党员计算党费）
				if(salary.getXueLiZhiCheng()!=0||salary.getGongLing()!=0||salary.getSiLing()!=0||salary.getGangWeiGongZi()!=0){
					//计算党费
					membershipDuesService.saveDues(salary);
				}
				
								
			}
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.PUBLISH_REPORT_LOG_MESSAGE,"工资导入完成");
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					userProfile, LogConstant.PUBLISH_REPORT_LOG_ACTION, logMessage,
					null, null);
		   

			// EIPService.getLogService(LogConstant.LOGTYPE).info(userProfile,
			// LogConstant.ACTION_TYPE_TREE_IMP,content, null, null);
		} catch (Exception e) {
			XLogger.getLogger().myWarn(
					"[执行semiAutomaticImport异常： " + e.toString() + "]");
			e.printStackTrace();
		} finally {
			// 刷新缓存
			getContextListenerCacheService().initDepartmentCache();
			getContextListenerCacheService().initDepartmentCategoryCache();
			getContextListenerCacheService().initPostCache();
			getContextListenerCacheService().initTreeNodeCache();
		}

		return returnMap;
	}

	/**
	 * 
	 * <pre>
	 * 赋值
	 * </pre>
	 * 
	 * @param salary
	 * @param data
	 */
	public boolean initSalary(Salary salary, Map<String, Object> data,int rowIndex, Map<Integer, String> returnMap) {
		boolean flag=true;
		String errorMsg="填写的";
		if (data == null || salary == null) {
			flag=false;
		}
		
		String empNo = CommonUtil.trim((String) data.get("empNo"));
		//String empName = CommonUtil.trim((String) data.get("empName"));

		String salaryMonth = CommonUtil.trim((String) data.get("salaryMonth"));
		Person person = EIPService.getEmpService().getEmpByNo(empNo);
		if(person != null){
			salary.setRecordId(com.supporter.util.UUIDHex.newId());
			salary.setEmpId(CommonUtil.trim(person.getPersonId()));
		    salary.setEmpNo(person.getPersonNo());
		    salary.setEmpName(person.getName());
		}
		if(salaryMonth.length()==8){
			salary.setSalaryMonth(salaryMonth);
		}else{
			errorMsg = errorMsg+"  月份不合法；请按照“XXXX年XX月”输入";
     		returnMap.put(rowIndex, errorMsg);
     		flag=false;
		}
		
	    // /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/
	    //(([1-9][\d]*)(\.[\d]{1,4})?)|(0\.[\d]{1,4})
	    // [\d]+(\.[\d]{1,4})?
		Pattern pattern = Pattern.compile("^\\d+(\\.\\d*)?$");//
//		Pattern pattern = Pattern.compile("[0-9]*");
		 
         Matcher isNum = null;
         //基础工资
         String xueLiZhiCheng=CommonUtil.trim((String) data.get("xueLiZhiCheng"));
		
         if(xueLiZhiCheng!=null&&!xueLiZhiCheng.equals("")){
			isNum = pattern.matcher(xueLiZhiCheng);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(xueLiZhiCheng)); 
				salary.setXueLiZhiCheng(Double.parseDouble(df));
	         }else{
	        	//不合法有两种选择：
	        	//1.如果不合法，报异常，本条记录导入不成功
	        	errorMsg = errorMsg+"  基础工资不合法";
	     		returnMap.put(rowIndex, errorMsg);
	     		flag=false;
	     		//2.如果不合法， set一个0
	     		//salary.setXueLiZhiCheng(0D);
	     		
	         }
			
		}else{
			salary.setXueLiZhiCheng(0D);
			
		}
         //资格补贴
         String ziGeBuTie=CommonUtil.trim((String) data.get("ziGeBuTie"));
 		
         if(ziGeBuTie!=null&&!ziGeBuTie.equals("")){
			isNum = pattern.matcher(ziGeBuTie);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(ziGeBuTie)); 
				salary.setZiGeBuTie(Double.parseDouble(df));
				
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  资格补贴不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setZiGeBuTie(0D);//如果填的不是数字 （set一个零）
	        	 
	         }
			
		}else{
			salary.setZiGeBuTie(0D);
		}
         //工龄
         String gongLing=CommonUtil.trim((String) data.get("gongLing"));
 		
         if(gongLing!=null&&!gongLing.equals("")){
			isNum = pattern.matcher(gongLing);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(gongLing)); 
				salary.setGongLing(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  工龄不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setGongLing(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setGongLing(0D);
		}
         //司龄
         String siLing=CommonUtil.trim((String) data.get("siLing"));
 		
         if(siLing!=null&&!siLing.equals("")){
			isNum = pattern.matcher(siLing);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(siLing)); 
				salary.setSiLing(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  司龄不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setSiLing(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setSiLing(0D);
		}
         //福利补贴
         String fuLiBuTile=CommonUtil.trim((String) data.get("fuLiBuTile"));
 		
         if(fuLiBuTile!=null&&!fuLiBuTile.equals("")){
			isNum = pattern.matcher(fuLiBuTile);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(fuLiBuTile)); 
				salary.setFuLiBuTile(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  福利补贴不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setFuLiBuTile(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setFuLiBuTile(0D);
		}
         //房补
         String fangBu=CommonUtil.trim((String) data.get("fangBu"));
 		
         if(fangBu!=null&&!fangBu.equals("")){
			isNum = pattern.matcher(fangBu);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(fangBu)); 
				salary.setFangBu(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  房补不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setFangBu(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setFangBu(0D);
		}
         //取暖补贴
         String quNuanBuTie=CommonUtil.trim((String) data.get("quNuanBuTie"));
 		
         if(quNuanBuTie!=null&&!quNuanBuTie.equals("")){
			isNum = pattern.matcher(quNuanBuTie);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(quNuanBuTie)); 
				salary.setQuNuanBuTie(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  取暖补贴不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setQuNuanBuTie(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setQuNuanBuTie(0D);
		}
         //岗位工资
         String gangWeiGongZi=CommonUtil.trim((String) data.get("gangWeiGongZi"));
 		
         if(gangWeiGongZi!=null&&!gangWeiGongZi.equals("")){
			isNum = pattern.matcher(gangWeiGongZi);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(gangWeiGongZi)); 
				salary.setGangWeiGongZi(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  岗位工资不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setGangWeiGongZi(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setGangWeiGongZi(0D);
		}
         //工会福利
         String buChongYangLao=CommonUtil.trim((String) data.get("buChongYangLao"));
 		
         if(buChongYangLao!=null&&!buChongYangLao.equals("")){
			isNum = pattern.matcher(buChongYangLao);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(buChongYangLao)); 
				salary.setBuChongYangLao(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  工会福利不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setBuChongYangLao(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setBuChongYangLao(0D);
		}
         //补发养老
         String buFaYangLao=CommonUtil.trim((String) data.get("buFaYangLao"));
 		
         if(buFaYangLao!=null&&!buFaYangLao.equals("")){
			isNum = pattern.matcher(buFaYangLao);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(buFaYangLao)); 
				salary.setBuFaYangLao(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  补发养老不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setBuFaYangLao(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setBuFaYangLao(0D);
		}
         //预支奖金
         String yuZhiJiangJin=CommonUtil.trim((String) data.get("yuZhiJiangJin"));
 		
         if(yuZhiJiangJin!=null&&!yuZhiJiangJin.equals("")){
			isNum = pattern.matcher(yuZhiJiangJin);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(yuZhiJiangJin)); 
				salary.setYuZhiJiangJin(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  预支奖金不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setYuZhiJiangJin(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setYuZhiJiangJin(0D);
		}
//         //一次性奖金
//         String yiCiXingJiangJin=CommonUtil.trim((String) data.get("yiCiXingJiangJin"));
// 		
//         if(yiCiXingJiangJin!=null&&!yiCiXingJiangJin.equals("")){
//			isNum = pattern.matcher(yiCiXingJiangJin);
//			if( isNum.matches() ){
//				salary.setYiCiXingJiangJin(Double.parseDouble(yiCiXingJiangJin));
//	         }else{
//	        		//不合法有两种选择：
//		        	//1.如果不合法，报异常，本条记录导入不成功
//		        	errorMsg = errorMsg+"  一次性奖金不合法";
//		     		returnMap.put(rowIndex, errorMsg);
//		     		flag=false;
//		     		//2.如果不合法， set一个0
//		     		//salary.setYiCiXingJiangJin(0D);//如果填的不是数字 （set一个零）
//	         }
//			
//		}else{
//			salary.setYiCiXingJiangJin(0D);
//		}
         //报销药费
         String baoXiaoYaoFei=CommonUtil.trim((String) data.get("baoXiaoYaoFei"));
 		
         if(baoXiaoYaoFei!=null&&!baoXiaoYaoFei.equals("")){
			isNum = pattern.matcher(baoXiaoYaoFei);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(baoXiaoYaoFei)); 
				salary.setBaoXiaoYaoFei(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  报销药费不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setBaoXiaoYaoFei(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setBaoXiaoYaoFei(0D);
		}
         //其他补发
         String qiTaBuFa=CommonUtil.trim((String) data.get("qiTaBuFa"));
 		
         if(qiTaBuFa!=null&&!qiTaBuFa.equals("")){
			isNum = pattern.matcher(qiTaBuFa);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(qiTaBuFa)); 
				salary.setQiTaBuFa(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  其他补发不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setQiTaBuFa(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setQiTaBuFa(0D);
		}
         //应发合计
         String yingFaHeJi=CommonUtil.trim((String) data.get("yingFaHeJi"));
 		
         if(yingFaHeJi!=null&&!yingFaHeJi.equals("")){
			isNum = pattern.matcher(yingFaHeJi);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(yingFaHeJi)); 
				salary.setYingFaHeJi(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  应发合计不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setYingFaHeJi(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setYingFaHeJi(0D);
		}
         //工资扣款
         String gongZiKouKuan=CommonUtil.trim((String) data.get("gongZiKouKuan"));
 		
         if(gongZiKouKuan!=null&&!gongZiKouKuan.equals("")){
			isNum = pattern.matcher(gongZiKouKuan);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(gongZiKouKuan)); 
				salary.setGongZiKouKuan(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  工资扣款不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setGongZiKouKuan(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setGongZiKouKuan(0D);
		}
         //误餐扣款
         String wuCanKouKuan=CommonUtil.trim((String) data.get("wuCanKouKuan"));
 		
         if(wuCanKouKuan!=null&&!wuCanKouKuan.equals("")){
			isNum = pattern.matcher(wuCanKouKuan);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(wuCanKouKuan)); 
				salary.setWuCanKouKuan(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  误餐扣款不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setWuCanKouKuan(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setWuCanKouKuan(0D);
		}
         
         //房租
         String fangZu=CommonUtil.trim((String) data.get("fangZu"));
 		
         if(fangZu!=null&&!fangZu.equals("")){
			isNum = pattern.matcher(fangZu);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(fangZu)); 
				salary.setFangZu(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  房租不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setFangZu(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setFangZu(0D);
		}
         
         //住房公积金
         String zhuFangGongJiJin=CommonUtil.trim((String) data.get("zhuFangGongJiJin"));
 		
         if(zhuFangGongJiJin!=null&&!zhuFangGongJiJin.equals("")){
			isNum = pattern.matcher(zhuFangGongJiJin);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(zhuFangGongJiJin)); 
				salary.setZhuFangGongJiJin(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  住房公积金不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setZhuFangGongJiJin(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setZhuFangGongJiJin(0D);
		}
         
         //企业年金
         String kouBuChongYangLao=CommonUtil.trim((String) data.get("kouBuChongYangLao"));
 		
         if(kouBuChongYangLao!=null&&!kouBuChongYangLao.equals("")){
			isNum = pattern.matcher(kouBuChongYangLao);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(kouBuChongYangLao)); 
				salary.setKouBuChongYangLao(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  企业年金不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setKouBuChongYangLao(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setKouBuChongYangLao(0D);
		}
         
         //养老保险
         String yangLaoBaoXian=CommonUtil.trim((String) data.get("yangLaoBaoXian"));
 		
         if(yangLaoBaoXian!=null&&!yangLaoBaoXian.equals("")){
			isNum = pattern.matcher(yangLaoBaoXian);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(yangLaoBaoXian)); 
				salary.setYangLaoBaoXian(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  养老保险不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setYangLaoBaoXian(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setYangLaoBaoXian(0D);
		}
         
         //失业保险
         String shiYeBaoXian=CommonUtil.trim((String) data.get("shiYeBaoXian"));
 		
         if(shiYeBaoXian!=null&&!shiYeBaoXian.equals("")){
			isNum = pattern.matcher(shiYeBaoXian);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(shiYeBaoXian)); 
				salary.setShiYeBaoXian(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  失业保险不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setShiYeBaoXian(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setShiYeBaoXian(0D);
		}
         
         //医疗保险
         String yiLiaoBaoXian=CommonUtil.trim((String) data.get("yiLiaoBaoXian"));
 		
         if(yiLiaoBaoXian!=null&&!yiLiaoBaoXian.equals("")){
			isNum = pattern.matcher(yiLiaoBaoXian);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(yiLiaoBaoXian)); 
				salary.setYiLiaoBaoXian(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  医疗保险不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setYiLiaoBaoXian(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setYiLiaoBaoXian(0D);
		}
         
         //其他扣款
         String qiTaKouKuan=CommonUtil.trim((String) data.get("qiTaKouKuan"));
 		
         if(qiTaKouKuan!=null&&!qiTaKouKuan.equals("")){
			isNum = pattern.matcher(qiTaKouKuan);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(qiTaKouKuan)); 
				salary.setQiTaKouKuan(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  其他扣款不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setQiTaKouKuan(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setQiTaKouKuan(0D);
		}
         
         //药费
         String yaoFei=CommonUtil.trim((String) data.get("yaoFei"));
 		
         if(yaoFei!=null&&!yaoFei.equals("")){
			isNum = pattern.matcher(yaoFei);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(yaoFei)); 
				salary.setYaoFei(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  药费不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setYaoFei(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setYaoFei(0D);
		}
         
         //代扣税
         String daiKouShui=CommonUtil.trim((String) data.get("daiKouShui"));
 		
         if(daiKouShui!=null&&!daiKouShui.equals("")){
			isNum = pattern.matcher(daiKouShui);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(daiKouShui)); 
				salary.setDaiKouShui(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  代扣税不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setDaiKouShui(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setDaiKouShui(0D);
		}
         
         //扣款合计
         String kouKuanHeJi=CommonUtil.trim((String) data.get("kouKuanHeJi"));
 		
         if(kouKuanHeJi!=null&&!kouKuanHeJi.equals("")){
			isNum = pattern.matcher(kouKuanHeJi);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(kouKuanHeJi)); 
				salary.setKouKuanHeJi(Double.parseDouble(df));
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  扣款合计不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setKouKuanHeJi(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setKouKuanHeJi(0D);
		}
         
         //实发合计
         String shiFaHeJi=CommonUtil.trim((String) data.get("shiFaHeJi"));
 		
         if(shiFaHeJi!=null&&!shiFaHeJi.equals("")){
			isNum = pattern.matcher(shiFaHeJi);
			if( isNum.matches() ){
				String df  =new DecimalFormat("#.000").format(Double.parseDouble(shiFaHeJi));  
				salary.setShiFaHeJi(Double.parseDouble(df));
				
	         }else{
	        		//不合法有两种选择：
		        	//1.如果不合法，报异常，本条记录导入不成功
		        	errorMsg = errorMsg+"  实发合计不合法";
		     		returnMap.put(rowIndex, errorMsg);
		     		flag=false;
		     		//2.如果不合法， set一个0
		     		//salary.setShiFaHeJi(0D);//如果填的不是数字 （set一个零）
	         }
			
		}else{
			salary.setShiFaHeJi(0D);
		}         
         
         //交通费
         String jiaoTongFei=CommonUtil.trim((String) data.get("jiaoTongFei"));
         if(jiaoTongFei!=null&&!jiaoTongFei.equals("")){
        	 isNum = pattern.matcher(jiaoTongFei);
        	 if( isNum.matches() ){
        		 String df  =new DecimalFormat("#.000").format(Double.parseDouble(jiaoTongFei));  
        		 salary.setJiaoTongFei(Double.parseDouble(df));
        		 
        	 }else{
        		 //不合法有两种选择：
        		 //1.如果不合法，报异常，本条记录导入不成功
        		 errorMsg = errorMsg+"  交通费不合法";
        		 returnMap.put(rowIndex, errorMsg);
        		 flag=false;
        		 //2.如果不合法， set一个0
        		 //salary.setShiFaHeJi(0D);//如果填的不是数字 （set一个零）
        	 }
        	 
         }else{
        	 salary.setShiFaHeJi(0D);
         }
         
         //通讯费
         String tongXunFei=CommonUtil.trim((String) data.get("tongXunFei"));
         if(tongXunFei!=null&&!tongXunFei.equals("")){
        	 isNum = pattern.matcher(tongXunFei);
        	 if( isNum.matches() ){
        		 String df  =new DecimalFormat("#.000").format(Double.parseDouble(tongXunFei));  
        		 salary.setTongXunFei(Double.parseDouble(df));
        	 }else{
        		 //不合法有两种选择：
        		 //1.如果不合法，报异常，本条记录导入不成功
        		 errorMsg = errorMsg+"  通讯费不合法";
        		 returnMap.put(rowIndex, errorMsg);
        		 flag=false;
        		 //2.如果不合法， set一个0
        		 //salary.setShiFaHeJi(0D);//如果填的不是数字 （set一个零）
        	 }
         }else{
        	 salary.setShiFaHeJi(0D);
         }
         return flag;
	}
	// 校验人员
	private boolean checkPerson(String empNo, Salary salary ,int rowIndex, Map<Integer, String> returnMap) {
		boolean flag = true;
		String errorMsg = "";
		if (StringUtils.isBlank(empNo)) {
			flag = false;
			errorMsg = "人员编号为空或者人员编号不存在！";
			returnMap.put(rowIndex, errorMsg);
		} else {
			//Person emp = EIPService.getEmpService().getEmp(empId);
			Person emp = EIPService.getEmpService().getEmpByNo(empNo);
			if (emp == null) {
				flag = false;
				errorMsg = "人员编号不存在！";
				returnMap.put(rowIndex, errorMsg);
			} else {
				String empId = emp.getPersonId();
				salary.setEmpId(empId);
//				salary.setPersonName(emp.getName());
//				List<IDeptPostEmp> list = EIPService.getEmpAssignService()
//						.findDeptPostEmpsByEmpId(personId);
				String deptId = emp.getDeptId();
				salary.setDeptId(deptId);
				Dept dept = EIPService.getDeptService().getDept(deptId);
				salary.setDeptName(dept.getName());
//				String chiefId = EIPService.getRegistryService().get(DEPT_MANAGER_ID);
//				String isChief = "F";
//				for (IDeptPostEmp deptPostEmp : list) {
//					if(deptPostEmp.getPostNo().equals(chiefId)){
//						isChief = "T";
//					}
//				}
//				salary.setIsChief(isChief);
			}
		}
		return flag;
	}


//	// 校验导入部门是否为空
//	private boolean checkIsPartyMember(String isPartyMember, int rowIndex, Map<Integer, String> returnMap) {
//		boolean flag = true;
//		if (StringUtils.isBlank(isPartyMember)) {
//			flag = false;
//			String errorMsg = "是否是党员不能为空！";
//			returnMap.put(rowIndex, errorMsg);
//		} else{
//			if(!isPartyMember.equals("是")&&!isPartyMember.equals("否")){
//				flag = false;
//				String errorMsg = "是否是党员只能填写”是“或”否“！";
//				returnMap.put(rowIndex, errorMsg);
//			}
//			
//		}
//		return flag;
//	}

//	// 校验人员是否重复
//	private boolean checkExist(String personId, String year, int rowIndex,
//			Map<Integer, String> returnMap) {
//		boolean flag = true;
//		Integer i = salaryDao.getLastYearIntegral("", personId, Integer
//				.valueOf(year));
//		if (i == -1) {
//			flag = false;
//			errorMsg = errorMsg+"该人员该年份记录已存在，请手动维护！";
//			returnMap.put(rowIndex, errorMsg);
//		}
//		return flag;
//	}


}
