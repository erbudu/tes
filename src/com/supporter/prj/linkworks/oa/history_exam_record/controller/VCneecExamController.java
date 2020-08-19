package com.supporter.prj.linkworks.oa.history_exam_record.controller;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.account.entity.AccountEntity;
import com.supporter.prj.eip.swf.dao.MyWfExamDao;
import com.supporter.prj.eip.swf.runtime.entity.WfExam;
import com.supporter.prj.eip.swf.runtime.service.WfExamService;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.account.entity.Account;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.linkworks.oa.history_exam_record.entity.VCneecExam;
import com.supporter.prj.linkworks.oa.history_exam_record.service.VCneecExamService;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**
 * @Title: OLD历史审批意见Controller
 * @Description: OA_HISTORY_SWF_EXAM.
 * @author T
 * @date 2017-09-30 10:27:57
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/oa/his_exam")
public class VCneecExamController extends AbstractController {
	private static final long serialVersionUID = 1L;

	@Autowired
	private VCneecExamService hisExamService;
	@Autowired
	private WfExamService wfExamService;
	@Autowired
	private MyWfExamDao myWfExamDao;

	/**
	 * 分页表格展示数据.
	 * 
	 * @param jqGridReq
	 * @param procId
	 *            流程实例ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getGrid")
	@ResponseBody
	public JqGrid getGrid(JqGridReq jqGridReq, long procId) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(this.getRequest());
		this.hisExamService.getGrid(jqGrid, procId);
		return jqGrid;
	}

	/**
	 * 获取流程实例历史审批意见.
	 * 
	 * @param tableName
	 * @param idFieldName
	 * @param idFieldValue
	 * @param orderByTimeAsc
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getHisExamList")
	@ResponseBody
	public List<VCneecExam> getHisExamList(String tableName, String idFieldName, int idFieldValue, boolean orderByTimeAsc) throws Exception {
		return hisExamService.getHisExamList(tableName, idFieldName, idFieldValue, orderByTimeAsc);
	}

	/**
	 * 获取流程实例历史审批意见.
	 * 
	 * @param procId 流程实例ID
	 * @param showAll 是否显示所有的审批意见
	 * @param entityId 如果showAll=1,则需要业务对象ID
	 * @param orderByTimeAsc 排序
	 * @return List<Map<String, Object>>
	 */
	@RequestMapping("/getExamList")
	@ResponseBody
	public List<Map<String, Object>> getExamList(String procId, int showAll, String entityId, boolean orderByTimeAsc) {
		List<WfExam> recs = null;
		if (showAll == 0) {
			recs = wfExamService.getHisExamList(procId, orderByTimeAsc);
		} else {
			recs = myWfExamDao.getExamByEntityId(entityId, orderByTimeAsc);
		}
		
		if (recs == null || recs.size() == 0) {
			return null;
		}

		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		int size = recs.size();
		for (int i = 0; i < size; i++) {
			WfExam rec = recs.get(i);
			String empId = CommonUtil.trim(rec.getEmpId());

			Map<String, Object> map = beanToMap(rec);
			Person person = EIPService.getEmpService().getEmp(empId);
			List<Account> accounts = EIPService.getAccountService().getAccounts(person);
			if (accounts != null && accounts.size() > 0) {
				AccountEntity account = (AccountEntity) accounts.get(0);
				map.put("electronicSignature", account.getElectronicSignature());
			}

			// 判断还是不是加签人。如果是加签人，那么审批意见的节点名称改为“会签人” jiao 20180628
			boolean isSinger = hisExamService.getSignerByAssignerId(rec);
			if (isSinger) { // 是加签人
				Object obj = new Object();
				obj = map.get("wfActName");
				if (obj != null) {
					String wfActName1 = obj.toString();
					//更改加签人节点名称 wfActName
					//（这里拼接加签的目的是在前台能判断出哪一个是加签节点，然后再根据客户要求在前台进行拼接处理,
					//前台修改不用重启服务）
					map.put("wfActName", wfActName1 + "[加签]");
				}
			}
			maps.add(map);
		}
		return maps;
	}

	/**
	 * 获取审核意见TABLE(打印页面)
	 * @param procId
	 * @param orderByTimeAsc
	 * @return
	 */
	@RequestMapping("/getPrintExamList")
	public @ResponseBody
	String getPrintExamList(String basePath, String procId, boolean orderByTimeAsc) {
		return this.hisExamService.getPrintExamList(basePath, procId, orderByTimeAsc);
	}

	/**
	 * 获取审核意见对象(打印页面)
	 * @param procId
	 * @param orderByTimeAsc
	 * @return
	 */
	@RequestMapping("/getExamHistoryForPrint")
	public @ResponseBody
	List<Map<String, Object>> getExamHistoryForPrint(String procId, boolean orderByTimeAsc) {
		return this.hisExamService.getExamHistoryForPrint(procId, orderByTimeAsc);
	}

	/**
	 * 将javabean实体类转为map类型，然后返回一个map类型的值.
	 * 
	 * @param obj
	 * @return
	 */
	private Map<String, Object> beanToMap(Object obj) {
		Map<String, Object> params = new HashMap<String, Object>(0);
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
	}

}
