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
import com.supporter.prj.linkworks.oa.user_defined.constants.NetMaintainConstant;
import com.supporter.prj.linkworks.oa.user_defined.dao.NetMaintainDao;
import com.supporter.prj.linkworks.oa.user_defined.entity.NetMaintain;
import com.supporter.util.CommonUtil;

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
public class NetMaintainService {
	@Autowired
	private NetMaintainDao netMaintainDao;
	@Autowired
	private ExtractFiles extractFiles;	
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public NetMaintain initEditOrViewPage(String netMaintainId, UserProfile user) {
		if (StringUtils.isBlank(netMaintainId)) {// 新建
			NetMaintain NetMaintain = newNetMaintain(user);
			return NetMaintain;
		} else {// 编辑
			NetMaintain NetMaintain =  netMaintainDao.get(netMaintainId);
			return NetMaintain;
		}
	}
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public NetMaintain newNetMaintain(UserProfile auserprf_U){
        NetMaintain lNetMaintain_N = new NetMaintain();
        lNetMaintain_N.setId(com.supporter.util.UUIDHex.newId());
        lNetMaintain_N.setCreatedBy(auserprf_U.getName());
        lNetMaintain_N.setCreatedById(auserprf_U.getPersonId());
        lNetMaintain_N.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        lNetMaintain_N.setApplyDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        lNetMaintain_N.setAdd(true);
        return lNetMaintain_N;
    }
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<NetMaintain> getGrid(UserProfile user, JqGrid jqGrid, NetMaintain entity) {
		return netMaintainDao.findPage(jqGrid, entity);
	}

	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public NetMaintain saveOrUpdate(UserProfile user, NetMaintain entity, Map< String, Object > valueMap) {
		if (entity.getAdd()) {//
			this.netMaintainDao.save(entity);
			//记录日志
//			ModuleUtils.saveModuleOperateLog(user, module, Contract.LogOper.MODULE_ADD.getOperName(), null);
		} else {// 编辑
			
			this.netMaintainDao.update(entity);
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
	public void delete(UserProfile user, String netMaintainIds) {
		if (StringUtils.isNotBlank(netMaintainIds)) {
			for (String netMaintainId : netMaintainIds.split(",")) {
				this.netMaintainDao.delete(netMaintainId);
			}
//			EIPService.getLogService("MPM_MCA").info(user, Contract.LogOper.MODULE_DEL.getOperName(), "{delContractIds : " + moduleIds + "}", null, null);
		}
	}
	public NetMaintain get(String id){
		return netMaintainDao.get(id);
	}
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String extractFiles(String id, UserProfile userProfile){
		NetMaintain entity =  this.get(id);
		return extractFiles.extractFiles(entity.getId(), entity.getRelatedFiles(),
				NetMaintainConstant.File_PATH, NetMaintainConstant.MODULE_ID, NetMaintainConstant.FUJIAN_ID, userProfile);
	}
	
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String batchExtractFiles(UserProfile userProfile){
		String returnStr = "success";
		List <NetMaintain> entitys= netMaintainDao.getAllList();
		for(NetMaintain entity:entitys){
			returnStr = extractFiles.extractFiles(entity.getId(), entity.getRelatedFiles(),
					NetMaintainConstant.File_PATH, NetMaintainConstant.MODULE_ID, NetMaintainConstant.FUJIAN_ID, userProfile);
			
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
