package com.supporter.prj.linkworks.oa.user_defined.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.ExtractFiles;
import com.supporter.prj.linkworks.oa.history_exam_record.service.VCneecExamService;
import com.supporter.prj.linkworks.oa.user_defined.constants.EmpchangeConstant;
import com.supporter.prj.linkworks.oa.user_defined.constants.NetInConstant;
import com.supporter.prj.linkworks.oa.user_defined.dao.EmpchangeDao;
import com.supporter.prj.linkworks.oa.user_defined.entity.Empchange;
import com.supporter.prj.linkworks.oa.user_defined.entity.UNetin;

/**   
 * @Title: Service
 * @Description: 报告
 * @author liyinfeng
 * @date 2017-7-05 10:25:07
 * @version V1.0   
 *
 */
@Service
@Transactional(TransManager.APP)
public class EmpchangeService {
	@Autowired
	private EmpchangeDao dao;
	@Autowired
	private VCneecExamService cneecExamService;
	@Autowired
	private ExtractFiles extractFiles;	
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public Empchange initEditOrViewPage(String empchangeId, UserProfile user) {
		if (StringUtils.isBlank(empchangeId)) {// 新建
			Empchange Empchange = newEmpchange(user);
			return Empchange;
		} else {// 编辑
			Empchange Empchange =  dao.get(empchangeId);
			return Empchange;
		}
	}
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public Empchange newEmpchange(UserProfile auserprf_U){
        Empchange lEmpchange_N = new Empchange();
        lEmpchange_N.setId(com.supporter.util.UUIDHex.newId());
        lEmpchange_N.setChangeNo("（    ）人调令第   号");
        lEmpchange_N.setCreatedBy(auserprf_U.getName());
        lEmpchange_N.setDeptId(auserprf_U.getDeptId());
        lEmpchange_N.setCreatedById(auserprf_U.getPersonId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        lEmpchange_N.setCreatedDate(sdf.format(new Date()));
        lEmpchange_N.setStatus(Empchange.DRAFT);
        lEmpchange_N.setAdd(true);
        return lEmpchange_N;
    }
    
    /**
	 * 进入查看页面.
	 * @param moduleId
	 * @return
	 */
	public Empchange viewPage(String id, UserProfile user) {
		Empchange entity =  dao.get(id);
			//如果有旧系统的流程，则获取旧系统的procId
			long oldProcId = cneecExamService.getProcIdByRecord(entity);
			if (oldProcId > 0)entity.setOldProcId(oldProcId);
			return entity;
	}
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<Empchange> getGrid(UserProfile user, JqGrid jqGrid, Empchange entity) {
		return dao.findPage(user,jqGrid, entity);
	}

	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public Empchange saveOrUpdate(UserProfile user, Empchange entity, Map< String, Object > valueMap) {
		if (entity.getAdd()) {//
			this.dao.save(entity);
			//记录日志
//			ModuleUtils.saveModuleOperateLog(user, module, Contract.LogOper.MODULE_ADD.getOperName(), null);
		} else {// 编辑
			
			this.dao.update(entity);
			//记录日志
//			ModuleUtils.saveModuleOperateLog(user, module, Contract.LogOper.MODULE_EDIT.getOperName(), null);
		}

		return entity;

	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String empchangeIds) {
		if (StringUtils.isNotBlank(empchangeIds)) {
			for (String empchangeId : empchangeIds.split(",")) {
				this.dao.delete(empchangeId);
			}
//			EIPService.getLogService("MPM_MCA").info(user, Contract.LogOper.MODULE_DEL.getOperName(), "{delContractIds : " + moduleIds + "}", null, null);
		}
	}
	public Empchange commit(UserProfile user, String empchangeId) {
		Empchange u = dao.get(empchangeId);
		if(u!=null){
			u.setStatus(1);
		}
		dao.update(u);
		return u;
	}
	public Empchange get(String id) {
		// TODO Auto-generated method stub
		return dao.get(id);
	}
	public void update(Empchange entity) {
		this.dao.update(entity);
		
	}
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String extractFiles(String id, UserProfile userProfile){
		Empchange entity =  this.get(id);
		return extractFiles.extractFiles(entity.getId(), entity.getRelatedFiles(),
				EmpchangeConstant.File_PATH, EmpchangeConstant.MODULE_ID, EmpchangeConstant.FUJIAN_ID, userProfile);
	}
	
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String batchExtractFiles(UserProfile userProfile){
		String returnStr = "success";
		List <Empchange> entitys= dao.getAllList();
		for(Empchange entity:entitys){
			returnStr = extractFiles.extractFiles(entity.getId(), entity.getRelatedFiles(),
					EmpchangeConstant.File_PATH, EmpchangeConstant.MODULE_ID, EmpchangeConstant.FUJIAN_ID, userProfile);
			
			// 记录日志
			/*String logMessage = MessageFormat.format(
					LogConstant.EXTRACT_FILES_LOG_MESSAGE+":"+returnStr, CommonUtil.trim(report.getReportTitle()));
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					userProfile, LogConstant.EXTRACT_FILES_LOG_ACTION, logMessage,
					report, null);*/
		}
		return returnStr;
		//return reportDao.batchExtractFiles(userProfile);
	}
}
