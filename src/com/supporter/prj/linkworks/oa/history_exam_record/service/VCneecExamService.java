package com.supporter.prj.linkworks.oa.history_exam_record.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.account.entity.AccountEntity;
import com.supporter.prj.eip.busi_entity.IBusiEntity;
import com.supporter.prj.eip.swf.runtime.dao.WfTaskPfmResultDao;
import com.supporter.prj.eip.swf.runtime.entity.WfExam;
import com.supporter.prj.eip.swf.runtime.entity.WfTaskPfmResult;
import com.supporter.prj.eip.swf.runtime.service.WfExamService;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.account.entity.Account;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.linkworks.oa.history_exam_record.dao.VCneecExamDao;
import com.supporter.prj.linkworks.oa.history_exam_record.entity.VCneecExam;
import com.supporter.util.CommonUtil;

/**
 * @Title: Service
 * @Description: OA_HISTORY_SWF_EXAM.
 * @author T
 * @date 2017-09-30 10:27:57
 * @version V1.0
 * 
 */
@Service
public class VCneecExamService {

	@Autowired
	private VCneecExamDao hisExamDao;
	@Autowired
	private WfTaskPfmResultDao wfTaskPfmResultDao;
	@Autowired
	private WfExamService wfExamService;

	/**
	 * 分页表格展示数据.
	 * 
	 * @param jqGrid
	 * @param procId
	 * @return
	 */
	public List<VCneecExam> getGrid(JqGrid jqGrid, long procId) {
		return hisExamDao.findPage(jqGrid, procId);
	}

	/**
	 * 获取流程实例历史审批意见.
	 * 
	 * @param tableName
	 * @param idFieldName
	 * @param idFieldValue
	 * @param orderByTimeAsc
	 * @return
	 */
	public List<VCneecExam> getHisExamList(String tableName, String idFieldName, int idFieldValue, boolean orderByTimeAsc) {
		List<VCneecExam> list = hisExamDao.getHisExamList(tableName, idFieldName, idFieldValue, orderByTimeAsc);
		if (list != null && list.size() > 0) {
			int size = list.size();
			for (int i = 0; i < size; i++) {
				VCneecExam exam = list.get(i);
				Person person = EIPService.getEmpService().getEmp(Integer.toString(exam.getEmpId()));
				List<Account> accounts = EIPService.getAccountService().getAccounts(person);
				String electronicSignature = "";
				if (accounts != null && accounts.size() > 0) {
					for (int j = 0; j < accounts.size(); j++) {
						AccountEntity account = (AccountEntity) accounts.get(j);
						electronicSignature = CommonUtil.trim(account.getElectronicSignature());
						if (electronicSignature.length() > 0)
							break;
					}
				}
				exam.setElectronicSignature(electronicSignature);
			}
		}
		return list;
	}

	/**
	 * 根据业务对象获取旧系统中的procId.
	 * 
	 * @param entity
	 * @return
	 */
	public long getProcIdByRecord(IBusiEntity entity) {
		return hisExamDao.getProcIdByRecord(entity);
	}

	/**
	 * 根据AssignerId判断是不是加签人
	 * 
	 * @param entity
	 * @return jiao 20180628
	 */
	public boolean getSignerByAssignerId(WfExam wfExam) {
		boolean isSigner = false;
		WfTaskPfmResult result = wfTaskPfmResultDao.get(wfExam.getRecordId());
		if (result != null) {
			if (CommonUtil.trim(result.getAssignerId()).length() > 0) {// 说明是加签人
				isSigner = true;
			}
		}
		return isSigner;
	}

	/**
	 * 获取审核结果
	 * @param wfExam
	 * @return
	 */
	public WfTaskPfmResult getWfTaskPfmResult(WfExam wfExam) {
		WfTaskPfmResult result = wfTaskPfmResultDao.get(wfExam.getRecordId());
		return result;
	}

	/**
	 * 获取流程实例审批意见
	 * 
	 * @param procId
	 * @param orderByTimeAsc
	 * @return
	 */
	public List<Map<String, Object>> getExamHistoryForPrint(String procId, boolean orderByTimeAsc) {
		// 根据实例ID获取审批意见
		List<WfExam> wfExams = wfExamService.getHisExamList(procId, orderByTimeAsc);
		if (wfExams == null || wfExams.size() == 0) return null;

		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		int size = wfExams.size();
		for (int i = 0; i < size; i++) {
			WfExam wfExam = wfExams.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("wfExam", wfExam);

			//判断还是不是加签人。如果是加签人，那么审批意见的节点名称改为“会签人”
			WfTaskPfmResult result = getWfTaskPfmResult(wfExam);
			boolean isSinger = result != null && CommonUtil.trim(result.getAssignerId()).length() > 0;
			if (isSinger) {//是加签人
				String wfActName = wfExam.getWfActName();
				// 更改加签人节点名称 wfActName
				map.put("wfActName", wfActName + "[加签]");
			}

			// 添加审核结果
			map.put("actResultContent", CommonUtil.trim(result.getActResultContent()));

			// 获取审核人签名图片
			Person person = EIPService.getEmpService().getEmp(CommonUtil.trim(wfExam.getEmpId()));
			// 取账户（按默认靠前排序）
			List<Account> accounts = EIPService.getAccountService().getAccounts(person);
			if (accounts != null && accounts.size() > 0) {
				AccountEntity account = (AccountEntity) accounts.get(0);
				// 添加审核人签名
				map.put("electronicSignature", account.getElectronicSignature());
			}

			maps.add(map);
		}
		return maps;
	}

	/**
	 * 审核人角色TD
	 * @return
	 */
	private String constructActNameTD(Map<String, Object> exam) {
		String wfActName = "&nbsp;";
		WfExam wfExam = exam != null ? (WfExam) exam.get("wfExam") : null;
		if (wfExam != null) {
			wfActName = (String) exam.get("wfActName");
			if (wfActName == null) {
				wfActName = wfExam.getWfActName();
			}
		}
		return "<td class='inner-td-actName'>" + CommonUtil.trim(wfActName) + "</td>";
	}

	/**
	 * 审核结果意见签名TABLE
	 * @return
	 */
	private String constructExamContent(String basePath, String resultOption, String empNameDisplay, String electronicSignature, String examDateStr) {
		String examContentHtml = "<table class='inner-table-examContent' width='100%' border='0' cellpadding='0' cellspacing='0'>"
	            +    " <tr class='inner-tr-actResult'>"
	            +    "  <td class='inner-td-actResult '>" + resultOption + "<td>"
	            +    " </tr>"
	            +    " <tr class='inner-tr-empName'>"
	            +    "  <td class='inner-td-empName'>";

		if (electronicSignature != null && electronicSignature.length() > (electronicSignature.lastIndexOf("=") + 1)) {
			examContentHtml += "<img class='inner-img-electronicSignature' title='" + empNameDisplay + "' src='" + basePath + electronicSignature + "'>";
		} else {
			examContentHtml += "<span class='inner-span-empNameDisplay'>" + empNameDisplay + "</span>";
		}
		examContentHtml += "<br>" + examDateStr;

        examContentHtml += " </td>"
                +    " </tr>"
                +    "</table>";

        return examContentHtml;
    }

	/**
	 * 审核人结果意见签名TD
	 * @return
	 */
	private String constractExamContentTD(String basePath, Map<String, Object> exam) {
		String constructExamContent = "&nbsp;";
		WfExam wfExam = exam != null ? (WfExam) exam.get("wfExam") : null;
		if (wfExam != null) {
			String actResultContent = (String) exam.get("actResultContent");// 结果
			String opinionDesc = CommonUtil.trim(wfExam.getOpinionDesc());// 意见
			String ls_resultOption = "<span class='inner-span-actResultTitle'>结果：</span>"
					+ "<span class='inner-span-actResultContent'>" + actResultContent + "</span>";
			if (!opinionDesc.equals(actResultContent) && opinionDesc.length() > 0) {
				ls_resultOption += "<br><span class='inner-span-actOpinionTitle'>意见：</span>"
						+ "<span class='inner-span-actOpinionContent'>" + CommonUtil.getEncodedStringForHTML(opinionDesc) + "</span>";
			}

			String ls_empNameDisplay = wfExam.getEmpNameDisplay();// 审核人
			String ls_electronicSignature = (String) exam.get("electronicSignature");// 签名地址
			String ls_examDateStr = "<span class='inner-span-examDate'>" + wfExam.getExamDateStr() + "</span>";// 日期

			constructExamContent = constructExamContent(basePath, ls_resultOption, ls_empNameDisplay, ls_electronicSignature, ls_examDateStr);
		}
		return "<td class='inner-td-actExam'>" + constructExamContent + "</td>";
	}

	/**
	 * 构造审核意见table
	 * 
	 * @param procId
	 * @param orderByTimeAsc
	 * @return
	 */
	public String getPrintExamList(String basePath, String procId, boolean orderByTimeAsc) {
		StringBuffer result = new StringBuffer();
		List<Map<String, Object>> examList = this.getExamHistoryForPrint(procId, orderByTimeAsc);
		if (examList != null && examList.size() > 0) {
			result.append("<table class='hisExam-table' width='100%' border='1' cellpadding='3' cellspacing='1' bgcolor='#FFFFFF'>");
			Map<String, Object> exam = null;
			for (int i = 1; i <= examList.size(); i++) {
				if ((i % 2) == 0) continue; // 双数的跳过，因为已经在单数的时候生成相应的TD了.
				result.append("<tr class='hisExam-tr' bgcolor='#FFFFFF'>");
				exam = examList.get(i - 1);
				result.append(constructActNameTD(exam));
				result.append(constractExamContentTD(basePath, exam));
				exam = (i < examList.size()) ? examList.get(i) : null;
				result.append(constructActNameTD(exam));
				result.append(constractExamContentTD(basePath, exam));
				result.append("</tr>");
			}
			result.append("</table>");
		}
		return result.toString();
	}

}
