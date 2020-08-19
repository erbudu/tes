package com.supporter.prj.ppm.contract.pact.review_ver.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.ppm.contract.pact.review_ver.dao.ContractPactRevVerConDao;
import com.supporter.prj.ppm.contract.pact.review_ver.dao.ContractPactRevVerDao;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVer;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVerCon;
import com.supporter.prj.ppm.ecc.EccUtils;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;

@Service
@Transactional(TransManager.APP)
public class ContractPactRevVerConService {

	@Autowired
	private ContractPactRevVerConDao verContentDao;
	@Autowired
	private ContractPactRevVerDao dao;
	@Autowired
	private BaseInfoService baseInfoService;

	/**
	 * 保存审批结果
	 * @param revVerId  评审验证主键
	 * @param revConStatus 评审验证结论
	 */
	public void saveSwfResult(String revVerId, String revConStatus) {
		ContractPactRevVerCon entity;
		List<ContractPactRevVerCon> list = verContentDao.findBy("revVerId", revVerId);
		if (CollectionUtils.isEmpty(list)) {
			entity = new ContractPactRevVerCon();
			entity.setRevConId(com.supporter.util.UUIDHex.newId());
			entity.setRevVerId(revVerId);
			entity.setRevConStatus(Integer.parseInt(revConStatus));
			verContentDao.save(entity);
		} else {
			entity = list.get(0);
			entity.setRevConStatus(Integer.parseInt(revConStatus));
			verContentDao.update(entity);
		}
		// 发送知会
		sendInform(revVerId);
	}

	/**
	 * 发送知会
	 * @param id-主表主键
	 */
	public void sendInform(String revVerId) {
		// 获取审批结论
		ContractPactRevVerCon revVerResult = getSwfResult(revVerId);
		// 根据审批结论发送知会
		ContractPactRevVer revVer = dao.get(revVerId);
		String roleNo = "PRJDEVLPMTDIRECTOR";// 角色编号(项目开发负责人)
		String title = "（知会）项目开发负责人：" + revVer.getRevVerNo() + "协议签署前评审验证未通过，请修改后重新提交验证";// 代办标题
		String url = "ppm/contract/pact/review_ver/review_ver_swf.html?id=" + revVerId + "&pageType=2";// 待办标题指向的知会页面路径
		if (revVerResult.getRevConStatus() == ContractPactRevVerCon.ConStatusCodeTable.NOTPASS) {
			// 知会项目开发负责人
//			EccUtils.sendMessages(roleNo, title, url, ContractPactRevVer.MODULE_ID, revVer.getProcId(), "");
			sendMessageToPrjM(revVer.getPrjId(), title, url, ContractPactRevVer.MODULE_ID, revVer.getProcId(), "项目开发负责人");
		} else if (revVerResult.getRevConStatus() == ContractPactRevVerCon.ConStatusCodeTable.PASS) {
			// 知会经营管理部合同管理员
			roleNo = "LYGLBHTGLY";// 角色编号
			title = "（知会）经营管理部合同管理员：已完成" + revVer.getRevVerNo() + "评审验证，请申请用印并安排正式协议文件的出版";// 代办标题
			EccUtils.sendMessages(roleNo, title, url, ContractPactRevVer.MODULE_ID, revVer.getProcId(), "");
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
	 * 获取评审验证的评审结果
	 * @param revVerId 评审验证主键
	 * @return 评审验证结果对象
	 */
	public ContractPactRevVerCon getSwfResult(String revVerId) {
		List<ContractPactRevVerCon> list = verContentDao.findBy("revVerId", revVerId);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 *  删除某评审验证的审批结果
	 * @param revVerId 评审验证主键
	 */
	public void delSwfResult(String revVerId) {
		verContentDao.deleteByProperty("revVerId", revVerId);
	}

	/**
	 * 获取所有验证结果为通过的评审验证
	 * @return 评审验证对象集合
	 */
	public List<ContractPactRevVerCon> getPassRev() {
		return verContentDao.findBy("revConStatus", ContractPactRevVerCon.ConStatusCodeTable.PASS);
	}

	/**
	 * 获取所有验证结果为通过的评审验证ID集合
	 * @return 评审验证ID集合
	 */
	public List<String> getPassRevVerId() {
		return verContentDao.getPassRevVerId();
	}

}

