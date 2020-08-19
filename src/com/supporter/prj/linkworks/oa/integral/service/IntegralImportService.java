package com.supporter.prj.linkworks.oa.integral.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.account.service.AccountEntityService;
import com.supporter.prj.eip.dept.dao.CompanyDao;
import com.supporter.prj.eip.dept.dao.DepartmentDao;
import com.supporter.prj.eip.dept.dao.PostDao;
import com.supporter.prj.eip.dept.dao.TreeNodeDao;
import com.supporter.prj.eip.dept.entity.Company;
import com.supporter.prj.eip.dept.entity.ContactsVO;
import com.supporter.prj.eip.dept.entity.Department;
import com.supporter.prj.eip.dept.entity.DeptExpVO;
import com.supporter.prj.eip.dept.entity.LogConstant;
import com.supporter.prj.eip.dept.entity.Post;
import com.supporter.prj.eip.dept.entity.Tree;
import com.supporter.prj.eip.dept.entity.TreeNode;
import com.supporter.prj.eip.dept.entity.TreeNodeVO;
import com.supporter.prj.eip.dept.service.ContextListenerService;
import com.supporter.prj.eip.dept.service.IDepartmentService;
import com.supporter.prj.eip.dept.service.IPostService;
import com.supporter.prj.eip.dept.service.TreeNodeService;
import com.supporter.prj.eip.dept.util.ConvertUtils;
import com.supporter.prj.eip.emp.entity.Employee;
import com.supporter.prj.eip.emp.entity.VDeptPostEmp;
import com.supporter.prj.eip.emp.service.DeptPostEmpBusiService;
import com.supporter.prj.eip.emp.service.DeptPostEmpService;
import com.supporter.prj.eip.emp.service.EmployeeService;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.dept.entity.ITreeNode;
import com.supporter.prj.eip_service.emp.entity.IDeptPostEmp;
import com.supporter.prj.eip_service.excel.handler.IExcelImportHandler;
import com.supporter.prj.eip_service.excel.vo.ExcelImportParam;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.integral.dao.PersonIntegralDao;
import com.supporter.prj.linkworks.oa.integral.entity.PersonIntegral;
import com.supporter.prj.log4j.XLogger;
import com.supporter.util.CommonUtil;

// ~ File Information
// ====================================================================================================================
/**
 * 单位通讯录导入service实现类
 * 
 * <pre>
 * 单位通讯录导入service实现类
 * </pre>
 * 
 * @author duancunming
 * @createDate 2017-2-22
 * @since TODO: 无
 * @modifier duancunming
 * @modifiedDate 2017-2-22
 */
@Service("integralImportService")
public class IntegralImportService implements IExcelImportHandler {

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
	
	public static final String DEPT_MANAGER_ID = "CNEEC_DEPT_MANAGER_ID";
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
	private PersonIntegralDao integralDao;

	private ContextListenerService contextListenerService;

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
/*			if (allCount >= 3) {
				allCount = allCount - 3;
			}*/
			returnMap.put(ExcelImportParam.IMPORT_RESULT_KEY_TOTAL_COUNT,
					String.valueOf(allCount));
			// String nodeId = excelImportParam.getParam();
			// Map<String, TreeNode> orgTreeNodeMap = this.getOrgTreeMap();
			for (int i = 0; i < dataList.size(); i++) {
				Map<String, Object> data = dataList.get(i);
				PersonIntegral vo = new PersonIntegral();
				// String errorMsg = "";
				int rowIndex = (Integer) data.get("rowIndex");
				this.initVo(vo, data);
				if (!this.checkPerson(vo.getPersonName(), vo.getPersonNo(), vo,
						rowIndex, returnMap)) {
					continue;
				}
				if (!this.checkExist(vo.getPersonId(), vo.getYear(), rowIndex,
						returnMap)) {
					continue;
				}

				integralDao.saveOrUpdate(vo);
			}

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
	 * @param vo
	 * @param data
	 */
	public void initVo(PersonIntegral vo, Map<String, Object> data) {
		if (data == null || vo == null) {
			return;
		}
		String deptName = CommonUtil.trim((String) data.get("deptName"));
		String personName = CommonUtil.trim((String) data.get("personName"));
		String personNo = CommonUtil.trim((String) data.get("personNo"));
		String year = CommonUtil.trim((String) data.get("year"));
		String lastYearIntegral = CommonUtil.trim((String) data
				.get("lastYearIntegral"));
		String basicIntegral = CommonUtil.trim((String) data
				.get("basicIntegral"));
		String rewardIntegral = CommonUtil.trim((String) data
				.get("rewardIntegral"));
		String deductionIntegral = CommonUtil.trim((String) data
				.get("deductionIntegral"));
		String finalIntegral = CommonUtil.trim((String) data
				.get("finalIntegral"));
		vo.setId(com.supporter.util.UUIDHex.newId());
		vo.setDeptName(deptName);
		vo.setPersonName(personName);
		vo.setPersonNo(personNo);
		vo.setYear(year);
		 Pattern pattern = Pattern.compile("^[-+]?\\d+(\\.\\d+)?$");
         Matcher isNum = null;
         
		if(lastYearIntegral!=null&&!lastYearIntegral.equals("")){
			isNum = pattern.matcher(lastYearIntegral);
			if( isNum.matches() ){
				vo.setLastYearIntegral(Integer.valueOf(lastYearIntegral));
	         }
		}
		if(basicIntegral!=null&&!basicIntegral.equals("")){
			isNum = pattern.matcher(basicIntegral);
			if( isNum.matches() ){
				vo.setBasicIntegral(Integer.valueOf(basicIntegral));
	         }
		}
		if(rewardIntegral!=null&&!rewardIntegral.equals("")){
			isNum = pattern.matcher(rewardIntegral);
			if( isNum.matches() ){
				vo.setRewardIntegral(Integer.valueOf(rewardIntegral));
	         }
		}
		if(deductionIntegral!=null&&!deductionIntegral.equals("")){
			isNum = pattern.matcher(deductionIntegral);
			if( isNum.matches() ){
				vo.setDeductionIntegral(Integer.valueOf(deductionIntegral));
	         }
		}
		if(finalIntegral!=null&&!finalIntegral.equals("")){
			isNum = pattern.matcher(finalIntegral);
			if( isNum.matches() ){
				vo.setFinalIntegral(Integer.valueOf(finalIntegral));
	         }
		}		
	}
	// 校验人员
	private boolean checkPerson(String personName, String personNo,
			PersonIntegral vo, int rowIndex, Map<Integer, String> returnMap) {
		boolean flag = true;
		String errorMsg = "";
		if (StringUtils.isBlank(personNo)) {
			flag = false;
			errorMsg = "人员编号不能为空！";
			returnMap.put(rowIndex, errorMsg);
		} else {
			Person emp = EIPService.getEmpService().getEmpByNo(personNo);
			if (emp == null) {
				flag = false;
				errorMsg = "人员编号不存在！";
				returnMap.put(rowIndex, errorMsg);
			} else {
				String personId = emp.getPersonId();
				vo.setPersonId(personId);
				vo.setPersonName(emp.getName());
				List<IDeptPostEmp> list = EIPService.getEmpAssignService()
						.findDeptPostEmpsByEmpId(personId);
				List<Dept> depts = EIPService.getEmpService().getDepts(emp);
				//获取部门正职NO
				String chiefId = EIPService.getRegistryService().get(DEPT_MANAGER_ID);
				String isChief = "F";
				for (Dept dept : depts) {
					if(dept.getDeptNo().equals(chiefId)){
						isChief = "T";
					}
				}
				String deptId = emp.getDeptId();
				vo.setDeptId(deptId);
				Dept dept = EIPService.getDeptService().getDept(deptId);
				vo.setDeptNo(dept.getDeptNo());
				vo.setDeptName(dept.getName());
				/*for (IDeptPostEmp deptPostEmp : list) {
					if(deptPostEmp.getPostNo().equals(chiefId)){
						isChief = "T";
					}
				}*/
				vo.setIsChief(isChief);
			}
		}
		return flag;
	}

	// 校验导入部门是否为空
	private boolean checkDept(String deptNo, String deptName,
			PersonIntegral vo, int rowIndex, Map<Integer, String> returnMap) {
		boolean flag = true;
		if (StringUtils.isBlank(deptNo)) {
			flag = false;
			String errorMsg = "部门编号不能为空！";
			returnMap.put(rowIndex, errorMsg);
		} else {
			Dept dept = EIPService.getDeptService().getDeptByNo(deptNo);
			if (dept == null) {
				flag = false;
				String errorMsg = "部门编号不存在！";
				returnMap.put(rowIndex, errorMsg);
			} else {
				vo.setDeptId(dept.getDeptId());
				vo.setDeptName(vo.getDeptName());
			}
		}
		if (StringUtils.isBlank(deptName)) {
			flag = false;
			String errorMsg = "部门名称不能为空！";
			returnMap.put(rowIndex, errorMsg);
		}
		return flag;
	}

	// 校验人员是否重复
	private boolean checkExist(String personId, String year, int rowIndex,
			Map<Integer, String> returnMap) {
		boolean flag = true;
		Integer i = integralDao.getLastYearIntegral("", personId, Integer
				.valueOf(year));
		if (i == -1) {
			flag = false;
			String errorMsg = "该人员该年份记录已存在，请手动维护！";
			returnMap.put(rowIndex, errorMsg);
		}
		return flag;
	}
}
