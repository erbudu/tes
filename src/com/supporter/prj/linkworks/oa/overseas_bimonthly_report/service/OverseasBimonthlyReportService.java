/**
 * 
 */
package com.supporter.prj.linkworks.oa.overseas_bimonthly_report.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.prj.linkworks.oa.overseas_bimonthly_report.constant.OverseasBimonthlyReportConstant;
import com.supporter.prj.linkworks.oa.overseas_bimonthly_report.dao.OverseasBimonthlyReportDao;
import com.supporter.prj.linkworks.oa.overseas_bimonthly_report.entity.OverseasBimonthlyReportEntity;
import com.supporter.prj.linkworks.oa.overseas_bimonthly_report.util.AuthUtils;
import com.supporter.util.UUIDHex;

/**
 *<p>Title: OverseasBimonthlyreportService</p>
 *<p>Description: 境外机构双月报审批 业务层</p>
 *<p>Company: 东华厚盾</p>
 * @author YUEYUN
 * @date 2019年12月23日
 * 
 */
@Service
public class OverseasBimonthlyReportService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**本业务持久层*/
	@Autowired
	private OverseasBimonthlyReportDao baseDao;

	SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * <pre>
	 * 	Description:
	 * 		This method is initialize edit or view page.(service)
	 * </pre>
	 * @param user
	 * @param overseasBimonthlyReportEntity
	 * @return
	 */
	public OverseasBimonthlyReportEntity initEditOrViewPage(UserProfile userProfile, String reportId) {
		
		OverseasBimonthlyReportEntity entity = null;
		if(StringUtils.isBlank(reportId)) {//新建
			entity = new OverseasBimonthlyReportEntity();
			entity.setReportId(UUIDHex.newId());
			entity.setStatus(OverseasBimonthlyReportConstant.DRAFT);//草稿
			setcreateInfo(userProfile,entity);
			return entity;
		}
		
		//编辑
		entity = baseDao.get(reportId);
		return entity;
	}

	/**
	 * <pre>
	 * 		This method is set creator information .
	 * </pre>
	 * @param userProfile Log on to people.
	 */
	private void setcreateInfo(UserProfile userProfile,OverseasBimonthlyReportEntity entity) {
		Dept dept = userProfile.getDept();
		
		entity.setCreatedByName(userProfile.getName());
		entity.setCreatedById(userProfile.getPersonId());
		entity.setCreatedByDate(simpleDate.format(new Date()));
		if(dept!= null) {
			entity.setDeptId(dept.getDeptId());
			entity.setDeptName(dept.getName());
		}
	}

	/**
	 * <pre>
	 * 		Description:
	 * 			This method is save or update entity class information.(service)
	 * </pre>
	 * @param userProfile Log on to people
	 * @param entity Entity Class
	 * @return Entity class
	 */
	public OverseasBimonthlyReportEntity saveOrUpdate(UserProfile userProfile, OverseasBimonthlyReportEntity entity) {
		OverseasBimonthlyReportEntity overseasBimonthlyReportEntity = baseDao.get(entity.getReportId());
		if(overseasBimonthlyReportEntity == null) {//保存 
			baseDao.save(entity);
			return entity;
		}
		
		entity.setModifiedById(userProfile.getPersonId());
		entity.setModifiedByName(userProfile.getName());
		entity.setModifiedDate(simpleDate.format(new Date()));
		//执行权限
		AuthUtils.canExecute(userProfile,OverseasBimonthlyReportConstant.MODULE_ID, OverseasBimonthlyReportConstant.EDIT_AUTH,entity.getReportId(),entity);
		baseDao.update(entity);
		
		return entity;
		
	}

	/**
	 * <pre>
	 * 	Description :
	 * 		This method is used batch delete entity information.(service)
	 * </pre>
	 * @param reportIds Primary keys.
	 * @return null
	 */
	public void batchDelete(UserProfile userProfile, String reportIds) {
		
		if(reportIds.length()<0) {
			return ;
		}
		
		String[] ids = reportIds.split(",");
		for (String id : ids) {
			baseDao.delete(id);
		}
		
	}
	
	/**
	 * <pre>
	 * 	Description:
	 * 		This method is used a single delete form information.
	 * </pre>
	 * 
	 * @param userProfile Log on people.
	 * @param reportId Primary key.
	 */
	public void singleDelete(UserProfile userProfile, String reportId) {
		
		if(reportId !="" && reportId != null) {
			batchDeleteFile(reportId);
			baseDao.delete(reportId);
		}
		
	}

	/**
	 * 	批量删除附件
	 * @param reportId 
	 */
	private void batchDeleteFile(String reportId) {
		
		for (int i = 0; i < 6; i++) {
			String busiType = "G"+(i+1);
			List<IFile> list = EIPService.getFileUploadService().getFileList(OverseasBimonthlyReportConstant.MODULE_ID, busiType, reportId);
			deleteFile(list);
		}
		
	}
	

	/**
	 * 		单个删除附件
	 * @param list 附件集合
	 */
	private void deleteFile(List<IFile> list) {
		
		if(list.size()>0) {
			for (IFile iFile : list) {
				EIPService.getFileUploadService().deleteFile(iFile.getFileId());
			}
		}
		
	}

	/**
	 * <pre>
	 * 		Description:
	 * 			This method is used initialize the 'jaGrid' data.(controller)
	 * </pre>
	 * @param user Log on to  people.
	 * @param jqGrid
	 * @param entity 
	 * @return
	 */
	public List<OverseasBimonthlyReportEntity> getGrid(UserProfile user, JqGrid jqGrid, OverseasBimonthlyReportEntity entity) {
		return baseDao.findPage(user, jqGrid, entity);
	}
	
	/**
	 * <pre>
	 * 	Description:
	 *		This method is used to  send notifications to relevant personnel.
	 * </pre>
	 * @param noticePersons
	 */
	public void sendNotice(String noticePersons,String reportId) {
		
		OverseasBimonthlyReportEntity entity = baseDao.get(reportId);
		String[] notiftPersonIds = noticePersons.split(",");
		
		for (String notiftPersonId : notiftPersonIds) {
			notice(notiftPersonId,entity.getDeptName(),entity.getProcId(),reportId,entity.getReportTitle());
		}
		
		
	}

	/**
	 * <pre>
	 * 	Description:
	 * 		Send notifications.
	 * </pre>
	 * @param notiftPersonId 
	 */
	private void notice(String notiftPersonId,String deptName,String procId,String reportId,String reportTitle) {
		
		//获取待办通知服务内容
		Message message = EIPService.getBMSService().newMessage();
		//通知人
		message.setPersonId(notiftPersonId);
		 String title = "知会  境外机构双月报："+reportTitle+" 审批完成！"+'('+deptName+')';
		// 待办标题
		message.setEventTitle(title);
		//通知待办日期
		message.setNotifyTime(new Date());
		// 待办地址"/cpp/purchase_demand/demand/purchase_demand_audit_notify.html?id="+id
		message.setWebPageURL("/oa/overseas_bimonthly_report/overseas_bimonthly_report_view.html?reportId="+reportId+"&cc=true");
		//应用编号
		message.setModuleId(OverseasBimonthlyReportConstant.MODULE_ID);
		// 默认地指定为“待办”类型
		message.setMessageType(ITodo.MsgType.CC);
		message.setWebappName("BM");
		// message.setWfProcId(execContext.getProcId());
		message.setWfProcId(procId);
		// 加入待办事宜（BMS）
		EIPService.getBMSService().addMessage(message);
		
	}

	/**
	 * <pre>
	 * 	Description:
	 * 		This method is used to get entity information by primary key.
	 * </pre>
	 * @param primarkKey 
	 * @return Entity information
	 */
	public OverseasBimonthlyReportEntity get(String primarkKey) {
		return baseDao.get(primarkKey);
	}
	

	/**
	 * <pre>
	 * 	Description:
	 * 		This method is used to update entity information.
	 * </pre>
	 * @param entity
	 */
	public void update(OverseasBimonthlyReportEntity entity) {
		baseDao.update(entity);
	}

	/**
	 * @return
	 */
	public static Integer validationFile(String reportId) {
		
		for (int i = 0; i < 6; i++) {
			String busiType = "G"+(i+1);
			List<IFile> list = EIPService.getFileUploadService().getFileList(OverseasBimonthlyReportConstant.MODULE_ID, busiType, reportId);
			if(list.size() < 2) {
				return i+1;
			}
		}
		
		return 0;
	}
	
	
	
	
}
