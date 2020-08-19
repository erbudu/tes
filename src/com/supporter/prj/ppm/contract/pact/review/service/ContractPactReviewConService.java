package com.supporter.prj.ppm.contract.pact.review.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.ppm.contract.pact.review.dao.ContractPactReviewConDao;
import com.supporter.prj.ppm.contract.pact.review.dao.ContractPactReviewDao;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReview;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReviewCon;
import com.supporter.prj.ppm.ecc.EccUtils;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;

@Service
@Transactional(TransManager.APP)
public class ContractPactReviewConService {
	@Autowired
	private ContractPactReviewConDao contentDao;
	@Autowired
	private ContractPactReviewDao dao;
	@Autowired
	private BaseInfoService baseInfoService;

	/**
	 * 保存审批结果
	 * @param reviewId 评审主键
	 * @param reviewConStatus 评审结论
	 * @param reviewVerContent 评审验证内容
	 */
	public void saveSwfResult(String reviewId, String reviewConStatus, String reviewVerContent) {
		ContractPactReviewCon entity;
		List<ContractPactReviewCon> list = contentDao.findBy("reviewId", reviewId);
		if (CollectionUtils.isEmpty(list)) {
			entity = new ContractPactReviewCon();
			entity.setReviewConId(com.supporter.util.UUIDHex.newId());
			entity.setReviewId(reviewId);
			entity.setReviewConStatus(Integer.parseInt(reviewConStatus));
			entity.setReviewVerContent(reviewVerContent);
			contentDao.save(entity);
		} else {
			entity = list.get(0);
			entity.setReviewConStatus(Integer.parseInt(reviewConStatus));
			entity.setReviewVerContent(reviewVerContent);
			contentDao.update(entity);
		}
		// 发送知会
		sendInform(reviewId);
	}

	/**
	 * 发送知会
	 * @param id-主表主键
	 */
	public void sendInform(String reviewId) {
		// 获取审批结论
		ContractPactReviewCon reviewResult = getSwfResult(reviewId);
		// 根据审批结论发送知会
		ContractPactReview review = dao.get(reviewId);
		String roleNo = "PRJDEVLPMTDIRECTOR";// 角色编号(项目开发负责人)
		String title = "（知会）项目开发负责人：" + review.getReviewNo() + "会签评审未通过，不允许签署协议";// 代办标题
		String url = "ppm/contract/pact/review/review_start_swf.html?id=" + reviewId + "&pageType=2";// 待办标题指向的知会页面路径
		if (reviewResult.getReviewConStatus() == ContractPactReviewCon.ConStatusCodeTable.NOTPASS) {
			// 知会项目开发负责人
//			EccUtils.sendMessages(roleNo, title, url, ContractPactReview.MODULE_ID, review.getProcId(), "项目开发负责人");
			sendMessageToPrjM(review.getPrjId(), title, url, ContractPactReview.MODULE_ID, review.getProcId(), "项目开发负责人");
			// 知会经营管理部外派事业部经理
			roleNo = "BUSINESSMANAGERTOBUSINESS";
			title = "（知会）经营管理部外派事业部经理：" + review.getReviewNo() + "会签评审未通过，不允许签署协议";// 代办标题
			EccUtils.sendMessages(roleNo, title, url, ContractPactReview.MODULE_ID, review.getProcId(), "经营管理部外派事业部经理");
		} else if (reviewResult.getReviewConStatus() == ContractPactReviewCon.ConStatusCodeTable.CONDITIONPASS) {
			// 知会项目开发负责人
			title = "（知会）项目开发负责人：请根据评审" + review.getReviewNo() + "会签审核结论，补充完善相关信息、资料，进入‘评审验证’流程";// 代办标题
//			EccUtils.sendMessages(roleNo, title, url, ContractPactReview.MODULE_ID, review.getProcId(), "项目开发负责人");
			sendMessageToPrjM(review.getPrjId(), title, url, ContractPactReview.MODULE_ID, review.getProcId(), "项目开发负责人");
		}
	}

	/**
	 * 知会项目开发负责人
	 * @param prjId 项目id
	 * @param title 知会标题
	 * @param url 知会页面url
	 * @param moduleId 模块编号
	 * @param procId 流程id
	 * @param nodeName 知会节点名称
	 */
	public void sendMessageToPrjM(String prjId,String title,String url,String moduleId,String procId,String nodeName){
		Prj prj = baseInfoService.initBaseInfoView(prjId);
		if (prj != null) {
		    String personId = EIPService.getEmpService().getEmployee(prj.getCreatedById()).getPersonId();
		    //发送知会 给项目开发负责人
		    EccUtils.sendMessage(personId, title, url, moduleId, procId,nodeName);
		}
	}

	/**
	 * 获取评审的评审结果
	 * @param reviewId 评审主键
	 * @return 评审结果对象
	 */
	public ContractPactReviewCon getSwfResult(String reviewId) {
		List<ContractPactReviewCon> list = contentDao.findBy("reviewId", reviewId);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 *  删除评审的审批结果
	 * @param reviewId 评审主键
	 */
	public void delSwfResult(String reviewId) {
		contentDao.deleteByProperty("reviewId", reviewId);
	}

	/**
	 *  获取所有评审结果为有条件通过的评审
	 * @return 评审集合
	 */
	public List<ContractPactReviewCon> getConditionReview() {
		return contentDao.findBy("reviewConStatus", ContractPactReviewCon.ConStatusCodeTable.CONDITIONPASS);
	}

	/**
	 *  获取所有评审结果为通过的评审
	 * @return 评审集合
	 */
	public List<ContractPactReviewCon> getPassReview() {
		return contentDao.findBy("reviewConStatus", ContractPactReviewCon.ConStatusCodeTable.PASS);
	}

}
