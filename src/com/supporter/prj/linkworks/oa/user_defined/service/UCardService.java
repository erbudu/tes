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
import com.supporter.prj.linkworks.oa.user_defined.constants.NetInConstant;
import com.supporter.prj.linkworks.oa.user_defined.constants.UCardConstant;
import com.supporter.prj.linkworks.oa.user_defined.dao.UCardDao;
import com.supporter.prj.linkworks.oa.user_defined.entity.UCard;

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
public class UCardService {
	@Autowired
	private UCardDao uCardDao;
	
	@Autowired
	private ExtractFiles extractFiles;	
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public UCard initEditOrViewPage(String uCardId, UserProfile user) {
		if (StringUtils.isBlank(uCardId)) {// 新建
			UCard UCard = newUCard(user);
			return UCard;
		} else {// 编辑
			UCard UCard =  uCardDao.get(uCardId);
			return UCard;
		}
	}
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public UCard newUCard(UserProfile auserprf_U){
        UCard lUCard_N = new UCard();
        lUCard_N.setId(com.supporter.util.UUIDHex.newId());
        lUCard_N.setCreatedBy(auserprf_U.getName());
        lUCard_N.setCreatedById(auserprf_U.getPersonId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        lUCard_N.setCreatedDate(sdf.format(new Date()));
        lUCard_N.setApplyDate(sdf.format(new Date()));
        String deptId = auserprf_U.getDeptId();
        lUCard_N.setDeptId(deptId);
        lUCard_N.setStatus(0);
        if(StringUtils.isNotBlank(deptId)){
        	lUCard_N.setDeptName(auserprf_U.getDept().getName());
        }
        lUCard_N.setAdd(true);
        return lUCard_N;
    }
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<UCard> getGrid(UserProfile user, JqGrid jqGrid, UCard entity) {
		return uCardDao.findPage(jqGrid, entity);
	}

	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public UCard saveOrUpdate(UserProfile user, UCard entity, Map< String, Object > valueMap) {
		if (entity.getAdd()) {//
			this.uCardDao.save(entity);
			//记录日志
//			ModuleUtils.saveModuleOperateLog(user, module, Contract.LogOper.MODULE_ADD.getOperName(), null);
		} else {// 编辑
			
			this.uCardDao.update(entity);
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
	public void delete(UserProfile user, String uCardIds) {
		if (StringUtils.isNotBlank(uCardIds)) {
			for (String uCardId : uCardIds.split(",")) {
				this.uCardDao.delete(uCardId);
			}
//			EIPService.getLogService("MPM_MCA").info(user, Contract.LogOper.MODULE_DEL.getOperName(), "{delContractIds : " + moduleIds + "}", null, null);
		}
	}
	public UCard get(String id) {
		return uCardDao.get(id);
	}
	public void update(UCard entity) {
		this.uCardDao.update(entity);
		
	}
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String extractFiles(String id, UserProfile userProfile){
		UCard entity =  this.get(id);
		return extractFiles.extractFiles(entity.getId(), entity.getRelatedFiles(),
				UCardConstant.File_PATH, UCardConstant.MODULE_ID, UCardConstant.FUJIAN_ID, userProfile);
	}
	
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String batchExtractFiles(UserProfile userProfile){
		String returnStr = "success";
		List <UCard> entitys= uCardDao.getAllList();
		for(UCard entity:entitys){
			returnStr = extractFiles.extractFiles(entity.getId(), entity.getRelatedFiles(),
					UCardConstant.File_PATH, UCardConstant.MODULE_ID, UCardConstant.FUJIAN_ID, userProfile);
			
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
