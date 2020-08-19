package com.supporter.prj.ppm.prebid.seal_bfd.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.file_upload.dao.FileUploadDao;
import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.role.entity.Role;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.prj.ppm.bid_startup.preparation.constant.StartContant;
import com.supporter.prj.ppm.poa.power_attorney.service.PowerAttorneyService;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReport;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReportBfd;
import com.supporter.prj.ppm.prebid.review_ver.entity.PpmPrebidReviewVer;
import com.supporter.prj.ppm.prebid.review_ver.entity.PpmPrebidReviewVerBfd;
import com.supporter.prj.ppm.prebid.review_ver.service.PpmPrebidReviewVerService;
import com.supporter.prj.ppm.prebid.seal_bfd.dao.PrebidSealBfdDao;
import com.supporter.prj.ppm.prebid.seal_bfd.dao.PrebidSealBfdFDao;
import com.supporter.prj.ppm.prebid.seal_bfd.dao.PrebidSealDao;
import com.supporter.prj.ppm.prebid.seal_bfd.entity.PpmPrebidSeal;
import com.supporter.prj.ppm.prebid.seal_bfd.entity.PpmPrebidSealBfd;
import com.supporter.prj.ppm.prebid.seal_bfd.entity.PpmPrebidSealBfdF;
import com.supporter.prj.ppm.service.PPMService;
import com.supporter.util.UUIDHex;
/**
 * @author 王康
 *
 */

@Service
@Transactional(TransManager.APP)
public class PrebidSealBfdService {

	@Autowired
	private PrebidSealBfdDao bfdDao;
	@Autowired
	private PrebidSealBfdFDao bfdFDao;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private FileUploadDao fileUploadDao;
	@Autowired
	private PrebidSealBfdFService bfdFileService;
	@Autowired
	private PrebidSealDao prebidSealDao;
	@Autowired
	private PowerAttorneyService powerAttorneyService;
	@Autowired
	private PpmPrebidReviewVerService prebidReviewVerService;
	
	//新建/编辑或查看页面加载数据
	public List<PpmPrebidSealBfd> initEditOrViewPageSealbfd(Boolean result,
			String entityId, UserProfile user, String prjId) {

		List<PpmPrebidSealBfd> dataList = bfdDao.findBy("prbidReportId",
				entityId);
		dataList = getBfdDateListInit(entityId);// 资料清单
		if (dataList.size() > 0) {
			if (result) {
				dataList = getDataListEdit(result,entityId, user);

			} else {
				dataList = getDataListEdit(result,entityId, user);
			}
		}
		return dataList;
	}
	public PpmPrebidSealBfd initEditOrViewPagebfd(String sealId ,String prbidReportId,UserProfile user,String prjId,Boolean result) {
		PpmPrebidSeal ppmPrebidSeal;
		PpmPrebidSealBfd ppmPrebidSealBfd = new PpmPrebidSealBfd();
		if (StringUtils.isBlank(sealId)) {// 新建
			ppmPrebidSeal = new PpmPrebidSeal();
			List<PpmPrebidSealBfd> dataList = new ArrayList<PpmPrebidSealBfd>();
			ppmPrebidSeal.setSealId(UUIDHex.newId());
			ppmPrebidSeal.setPrjId(prjId);
			ppmPrebidSeal.setStatus(0);
			if(result) {
				String reviewVerId  = prebidReviewVerService.getMaxReviewVerId(prjId);
				ppmPrebidSeal.setPrbidReportId(reviewVerId);
				ppmPrebidSealBfd.setPrbidReportId(reviewVerId);
				dataList = initEditOrViewPageSealbfd(result,reviewVerId,user,prjId);
				ppmPrebidSealBfd.setDataList(dataList);
			}else {
				ppmPrebidSeal.setPrbidReportId(prbidReportId);
				ppmPrebidSealBfd.setPrbidReportId(prbidReportId);
				dataList = initEditOrViewPageSealbfd(result,prbidReportId,user,prjId);
				ppmPrebidSealBfd.setDataList(dataList);
			}
			prebidSealDao.save(ppmPrebidSeal);
			PPMService.getScheduleStatusService().saveScheduleStatus(prjId, "prebid_sealBfd01", user);
		}else {
			ppmPrebidSeal = prebidSealDao.get(sealId);
			List<PpmPrebidSealBfd> dataList = new ArrayList<PpmPrebidSealBfd>();
			dataList = bfdFileService.getListByPublishId(prbidReportId);
			ppmPrebidSealBfd.setDataList(dataList);
		}
		return ppmPrebidSealBfd;
	}
	
	/**
	 * <pre>保存资料清单</pre>
	 * @param PpmPrebidReportBfd 资料清单业务表单实体类
	 * @return 保存成功后的业务表单实体类数据
	 */
	public PpmPrebidSealBfd saveBfd(PpmPrebidSealBfd ppmPrebidSealBfd) {
		if(ppmPrebidSealBfd.getBfdId() == null || ppmPrebidSealBfd.getBfdId() == "" ) return null;
		PpmPrebidSealBfd entity = bfdDao.findUniqueResult("bfdId", ppmPrebidSealBfd.getBfdId());
		if(entity==null) {
        //ppmPrebidSealBfd.setBfdTypeName(StartContant.getBfdTypeName(ppmPrebidSealBfd.getBfdTypeId()));
			bfdDao.save(ppmPrebidSealBfd);
		}
		saveBfdF(ppmPrebidSealBfd.getBfdId(),ppmPrebidSealBfd.getPrbidReportId(),ppmPrebidSealBfd.getFileInfo());
		return ppmPrebidSealBfd;
	}
	/**
	 * <pre>保存清单附件</pre>
	 * @param bfdId 资料清业务实体类主键
	 * @param prbidReportId 启动申报业务实体类主键
	 * @param fileInfo 附件信息 [附件上传成功后的附件主键   上传附件的名称]
	 */
	public void saveBfdF(String bfdId, String prbidReportId, String fileInfo) {
		String[] fileInfoArr = fileInfo.split(",");
		String fileId = "";
		String fileName = "";
		for(int i=0;i<fileInfoArr.length;i++) {
			fileId = fileInfoArr[0];
			fileName = fileInfoArr[1];
		}
		Integer maxDisplayOrder = this.bfdFDao.getMaxDisplayOrder(prbidReportId,bfdId);
		
		PpmPrebidSealBfdF ppmPrebidReportBfdF = new PpmPrebidSealBfdF();
		ppmPrebidReportBfdF.setRecordId(UUIDHex.newId());
		ppmPrebidReportBfdF.setBfdId(bfdId);
		ppmPrebidReportBfdF.setPrbidReportId(prbidReportId);
		ppmPrebidReportBfdF.setFuSealFileId(fileId);//实际上传附件ID
		ppmPrebidReportBfdF.setDisplayOrder(maxDisplayOrder+1);
		ppmPrebidReportBfdF.setFuFileName(fileName);//实际上传附件的名称(资料清单文件类型对应的文件名称)
		//ppmPrebidReportBfdF.setIsUseSeal(0);//是否用印 码表：0否1是
		bfdFDao.save(ppmPrebidReportBfdF);
	}
	/**
	 * 初始化资料清单列表数据-初始化新建页面
	 * @return 资料清单实体类
	 */
	private List<PpmPrebidSealBfd> getBfdDateListInit(String entityId) {
		List<PpmPrebidSealBfd> list = new ArrayList<PpmPrebidSealBfd>();

		List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PREBID_REPORT_BFD");//资料清单码表
		for (IComCodeTableItem item : items) {
			PpmPrebidSealBfd bfd = new PpmPrebidSealBfd();
			bfd.setBfdTypeId(item.getItemId());
			bfd.setBfdTypeName(item.getItemValue());
			bfd.setPrbidReportId(entityId);
			bfd.setBfdId(com.supporter.util.UUIDHex.newId());
			list.add(bfd);
		}
		return list;
	}
	/**
	 * <pre>功能:初始化资料清单无序列表数据-编辑页面</pre>
	 * <pre>描述：根据资料清单码表ID和启动申报主键获取资料清单数据，获取到数据则返回</pre>
	 * @return 资料清单实体类数据
	 */
	private List<PpmPrebidSealBfd> getDataListEdit(Boolean result,String entity,UserProfile user) {
		List<PpmPrebidSealBfd> list = new ArrayList<PpmPrebidSealBfd>();
		List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PREBID_REPORT_BFD");//码表数据
		for (IComCodeTableItem item : items) {
			if(result) {
				List<PpmPrebidReviewVerBfd> ppmPrebidReportBfd = this.getVerDataListEdit(entity,item.getItemId());
				for(int i= 0;i<ppmPrebidReportBfd.size();i++) {
					PpmPrebidSealBfd bfd = new PpmPrebidSealBfd(); 
					bfd.setBfdId(ppmPrebidReportBfd.get(i).getBfdId());
					bfd.setPrbidReportId(ppmPrebidReportBfd.get(i).getPrbidReportId());
					bfd.setBfdTypeId(ppmPrebidReportBfd.get(i).getBfdTypeId());
					bfd.setBfdTypeName(ppmPrebidReportBfd.get(i).getBfdTypeName());
					bfdDao.save(bfd);
					list.add(bfd);
					
					// 获取评审模块资料清单文件
					List<FileUpload> files = fileUploadService.getList(PpmPrebidReviewVer.MODULE_ID,  PpmPrebidReviewVer.BUSI_TYPE,
							ppmPrebidReportBfd.get(i).getBfdId(), "");
					copyBfdFile(files, ppmPrebidReportBfd.get(i).getBfdId(), entity, user);
				}
			}else {
				List<PpmPrebidReportBfd> ppmPrebidReportBfd = this.getDataListEdit(entity,item.getItemId());//
				for(int i= 0;i<ppmPrebidReportBfd.size();i++) {
					PpmPrebidSealBfd bfd = new PpmPrebidSealBfd(); 
					bfd.setBfdId(ppmPrebidReportBfd.get(i).getBfdId());
					bfd.setPrbidReportId(ppmPrebidReportBfd.get(i).getPrbidReportId());
					bfd.setBfdTypeId(ppmPrebidReportBfd.get(i).getBfdTypeId());
					bfd.setBfdTypeName(ppmPrebidReportBfd.get(i).getBfdTypeName());
					bfdDao.save(bfd);
					list.add(bfd);
					
					// 获取评审模块资料清单文件
					List<FileUpload> files = fileUploadService.getList(PpmPrebidReport.MODULE_ID,  PpmPrebidReport.BUSI_TYPE,
							ppmPrebidReportBfd.get(i).getBfdId(), "");
					copyBfdFile(files, ppmPrebidReportBfd.get(i).getBfdId(), entity, user);
				}
			}
		}
		return list;
	}
	/**
	 * 将评审模块资料清单文件复制到评审验证模块
	 * @param files 评审模块资料清单文件集合
	 * @param bfdId 评审验证模块资料清单id
	 * @param revVerId 评审验证模块主键
	 * @param user 当前登录用户
	 */
	private void copyBfdFile(List<FileUpload> files, String bfdId, String prbidReportId, UserProfile user) {
		if (CollectionUtils.isNotEmpty(files)) {
			for (int i = 0; i < files.size(); i++) {
				// 资料清单文件信息保存到附件数据表并复制磁盘中的资料清单文件
				FileUpload sourceFile = files.get(i);
				String[] levelId = { bfdId, "" };
				File file = fileUploadService.getFile(sourceFile.getFileId());
				String newfileId = fileUploadService.saveFile(file, PpmPrebidSealBfd.MODULE_ID, PpmPrebidSealBfd.BUSI_TYPE,
						sourceFile.getFileName(), user, levelId);
				FileUpload targetFile = fileUploadDao.get(newfileId);
				// 初始化评审验证资料清单文件
				PpmPrebidSealBfdF bfdFile = new PpmPrebidSealBfdF();
				bfdFile.setRecordId(com.supporter.util.UUIDHex.newId());
				bfdFile.setBfdId(bfdId);
				bfdFile.setPrbidReportId(prbidReportId);
				bfdFile.setFuSealFileId(targetFile.getFileId());
				bfdFile.setFuFileName(targetFile.getFileName());
				bfdFile.setDisplayOrder(targetFile.getDisplayOrder());
				bfdFileService.save(bfdFile);
			}
		}
	}
	/**
	 * <pre>功能:根据启动申报主键和码表中资料清单的ID获取资料清单数据</pre>
	 * <pre>描述:根据启动申报主键和码表资料清单项的id可以确定到对应的而且唯一的，已经保存过的资料清单项</pre>
	 * <pre>调用:1.启动申报编辑页面初始化数据</pre>
	 * @param prbidReportId 资料清单业务表单实体类主键
	 * @param itemId 资料清单id
	 * @return 资料清单业务表单实体类数据
	 */
	public List<PpmPrebidReportBfd> getDataListEdit(String entityId, String bfdTypeId) {
		if(entityId == null || entityId == "" && bfdTypeId == null || bfdTypeId == "") {
			return null;
		}
		String hql = "from "+PpmPrebidReportBfd.class.getName()+" where prbidReportId = ? and bfdTypeId = ?";
		List<PpmPrebidReportBfd> list = bfdDao.find(hql,entityId,bfdTypeId);
		return list;
	}
	public List<PpmPrebidReviewVerBfd> getVerDataListEdit(String entityId, String bfdTypeId) {
		if(entityId == null || entityId == "" && bfdTypeId == null || bfdTypeId == "") {
			return null;
		}
		String hql = "from "+PpmPrebidReviewVerBfd.class.getName()+" where reviewVerId = ? and bfdTypeId = ?";
		List<PpmPrebidReviewVerBfd> list = bfdDao.find(hql,entityId,bfdTypeId);
		return list;
	}
	/**
	 * <pre>删除资料清单</pre>
	 * @param moduleName 应用编号 
	 * @param basiType 业务编号
	 * @param entityId 业务实体类
	 * @param twolevelid 二级业务编号
	 */
	public void deleteBfd(String filesId,String prbidReportId,String bfdTypeId) {
		

		String[] split = filesId.split(",");
		for (int i = 0;i<split.length;i++) {
			bfdFDao.deleteByProperty("fuFileId", split[i]);
		}
		String hql = "delete " + PpmPrebidSealBfd.class.getName() + " where prbidReportId = ? and bfdTypeId = ?";
		bfdFDao.execUpdate(hql, prbidReportId,bfdTypeId);
	}
	/**
	 * 获取Seal实体
	 * @param prjId
	 * @return
	 */
	public PpmPrebidSeal getSeal(String prjId) {
		List<PpmPrebidSeal> sealIdlist = prebidSealDao.findBy("prjId",prjId);
		PpmPrebidSeal prebidSeal = new PpmPrebidSeal();
		if (sealIdlist != null && sealIdlist.size() > 0) {
			for (int i = 0; i < sealIdlist.size(); i++) {
				prebidSeal= sealIdlist.get(i);
			}
		}
		return prebidSeal;
	}
	/**
	 * 判断是否可以出版
	 * @param laBusinessTypeId
	 * @param prjId
	 * @return
	 */
	public boolean checkSealIsOrNotPass(String laBusinessTypeId ,String prjId) {
		boolean stuta = false;
		if(powerAttorneyService.havePass(laBusinessTypeId,prjId)) {
			stuta = true;
		}
		return stuta;
	}
	/**
	 * 更新Seal表
	 * @param user
	 * @param prebidSeal
	 * @param valueMap
	 * @return
	 */
	public PpmPrebidSeal updateSeal(UserProfile user, PpmPrebidSeal prebidSeal,
			Map<String, Object> valueMap) {
		prebidSeal.setStatus(1);
		//设置确认人 确认日期
		prebidSeal.setCreatedBy(user.getName());
		prebidSeal.setCreatedDate(new Date());
		prebidSeal.setCreatedId(user.getPersonId());
		prebidSealDao.update(prebidSeal);
		PPMService.getScheduleStatusService().saveScheduleStatus(prebidSeal.getPrjId(), "prebid_sealBfd02", user);
		return prebidSeal;
	}
	/**
	 * 发送待办知会
	 * @param prjId
	 * @return
	 */
	public PpmPrebidSeal confirmNotice(String sealId,String prjId,UserProfile user) {

		PpmPrebidSeal prebidSeal = prebidSealDao.get(sealId);//项目信息
		prebidSeal.setProcId(UUIDHex.newId());
		prebidSeal.setStatus(2);
		String title = "知会:"+prebidSeal.getCreatedBy()+"提交的【投标前评审文件清单出版】";
		String url = "ppm/prebid/seal_bfd/sealBfd_publish_notice_view.html?prjId="+prjId;
		String procId = prebidSeal.getProcId();
		needControls(title,url,procId);
		prebidSealDao.update(prebidSeal);//通知完成后改变状态为已通知
		PPMService.getScheduleStatusService().saveScheduleStatus(prjId, "prebid_sealBfd03", user);
		return prebidSeal;
	}
	/**
	 * <pre>通知相关人员</pre>
	 * @param title 待办标题
	 * @param url 待办页面路径
	 * @param procId 流程id
	 */
	private void needControls(String title, String url, String procId) {
		String department = PpmPrebidSeal.MANAGEMENT_DEPARTMENT;
		String expatriateManagers = PpmPrebidSeal.ROLE_ExpatriateManagers;//经营管理部-外派事业部经理-角色
		
		//通知-经营管理部-外派事业部经理
		List<Person> personList_expatriate = getPersonListByRole(department,expatriateManagers);
		for (Person person_expatr : personList_expatriate) {
			String personId = person_expatr.getPersonId();
			sendNotice(personId,title,url,procId);
		}
		
	}

	/**
	 * <pre>根据部门和角色获取角色下的人员信息</pre>
	 * @param deptId 部门编号
	 * @param roleId 角色编号
	 * @return 角色下的人员信息
	 */
	private List<Person> getPersonListByRole(String deptId,String roleId) {
		
		Dept dept = EIPService.getDeptService().getDept(deptId);
		Role role = EIPService.getRoleService().getRole(roleId);
		List<Person> personList = EIPService.getRoleService().getPersonsForDept(role, dept);
		return personList;
		
	}
	/**
	 * <pre>发送待办通知</pre>
	 * @param personId 通知人主键
	 * @param title  待办标题
	 * @param url 待办页面路径
	 * @param procId 流程id
	 */
	private void sendNotice(String personId,String title,String url,String procId) {
		
		Message message = EIPService.getBMSService().newMessage();//获取待办通知服务内容
		message.setPersonId(personId);//通知人
		//String title = "知会："+purchaseDemand.getProcTitle();
		//待办标题
		message.setEventTitle(title);//通知待办标题
		message.setNotifyTime(new Date());//通知待办日期
		//待办地址"/cpp/purchase_demand/demand/purchase_demand_audit_notify.html?id="+id
		message.setWebPageURL(url);//通知待办地址
		message.setModuleId(PpmPrebidSealBfd.MODULE_ID);//应用编号
		//默认地指定为“待办”类型
		message.setMessageType(ITodo.MsgType.CC);//待办类型
		message.setWebappName("BM");
		//message.setWfProcId(execContext.getProcId());
		message.setWfProcId(procId);
		//加入待办事宜（BMS）
		EIPService.getBMSService().addMessage(message);//获取待办服务发送待办
		
	}
}
