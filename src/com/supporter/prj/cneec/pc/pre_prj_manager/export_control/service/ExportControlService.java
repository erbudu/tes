package com.supporter.prj.cneec.pc.pre_prj_manager.export_control.service;
import com.supporter.prj.cneec.pc.pre_prj_manager.export_control.constant.AuthConstant;
import com.supporter.prj.cneec.pc.pre_prj_manager.export_control.constant.LogConstant;
import com.supporter.prj.cneec.pc.pre_prj_manager.export_control.constant.OperResultConstant;
import com.supporter.prj.cneec.pc.pre_prj_manager.export_control.dao.ExportControlDao;
import com.supporter.prj.cneec.pc.pre_prj_manager.export_control.entity.ExportControl;
import com.supporter.prj.cneec.pc.pre_prj_manager.export_control.util.AuthUtils;
import com.supporter.prj.cneec.pc.pre_prj_manager.export_control.util.BeanUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**   
 * @Title: Service
 * @Description: 出口管制.
 * @author kangh_000
 * @date 2018-12-20 17:53:29
 * @version V1.0   
 *
 */
@Service
public class ExportControlService {
	private static final String IDS_SPLIT = ",";
	@Autowired
	private ExportControlDao exportControlDao;
	
	
	/**
	 * 根据主键获取出口管制.
	 * 
	 * @param exportControlId 主键
	 * @return ExportControl
	 */
	public ExportControl get(String exportControlId){
		return  exportControlDao.get(exportControlId);
	}
	
	
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @return JqGrid
	 */
	public List< ExportControl > getGrid(UserProfile user, JqGrid jqGrid, ExportControl exportControl) {
		return exportControlDao.findPage(user, jqGrid, exportControl);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param exportControlId
	 * @return
	 */
	public ExportControl initEditOrViewPage(String exportControlId) {
		// 新建
		if (StringUtils.isBlank(exportControlId)) {
			ExportControl entity = new ExportControl();
			return entity;
		} else {
			// 编辑
			ExportControl entity = exportControlDao.get(exportControlId);
			if(entity != null){
				
			}
			return entity;
		}
	}
	
	
	/**
	 * 保存.
	 * @param user 用户信息
	 * @param exportControl 实体类
	 * @return
	 */
	@Transactional(transactionManager=TransManager.APP, rollbackFor=Exception.class)
	public OperResult< ExportControl > save(UserProfile user, ExportControl exportControl){
		//主键
		String exportControlId = exportControl.getExportControlId();
		
		//权限校验
		AuthUtils.check(user, AuthConstant.MODULE_ID, AuthConstant.AUTH_OPER_NAME,
				exportControlId, exportControl, ExceptionCode.Auth.NOT_EXECUTABLE);
		
		//保存数据库
		this.exportControlDao.save(exportControl);
		
		//记录操作日志
		String logMessage = MessageFormat.format(LogConstant.OP_LOG_MSG_, "");
		EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(user, 
				LogConstant.OP_LOG_ACTION_, logMessage, exportControl, null);
				
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, exportControl);
	}
	/**
	 * 更新.
	 * @param user 用户信息
	 * @param exportControl 实体类
	 * @return
	 */
	@Transactional(transactionManager=TransManager.APP, rollbackFor=Exception.class) 
	public OperResult< ExportControl > update(UserProfile user, ExportControl exportControl, Map<String, Object> valueMap){
		//根据主键查询数据库
		ExportControl exportControlDb = this.exportControlDao.get(exportControl.getExportControlId());
		//如果不存在，则执行保存操作
		if(exportControlDb == null){
			return this.save(user, exportControl);
		}else{
			BeanUtils.copyProperties(exportControl, exportControlDb, valueMap);
			
			//主键
			String exportControlId = exportControl.getExportControlId();
			//权限校验
			AuthUtils.check(user, AuthConstant.MODULE_ID, AuthConstant.AUTH_OPER_NAME,
					exportControlId, exportControlDb, ExceptionCode.Auth.NOT_EXECUTABLE);
					
			//更新数据库
			this.exportControlDao.update(exportControlDb);
			
			//记录操作日志
			String logMessage = MessageFormat.format(LogConstant.OP_LOG_MSG_, "");
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(user, 
					LogConstant.OP_LOG_ACTION_, logMessage, exportControlDb, null);
					
			return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, exportControlDb);
		}
		
	}
	
	/**
	 * 删除前判断是否可以删除.
	 * @param user
	 * @param exportControlIds 主键集合，多个以逗号分隔
	 * @return
	 */
	public  List< OperResult< ? > > checkBatchDelCondition(UserProfile user, String exportControlIds){
		List< OperResult< ? > > list = new ArrayList<OperResult<?>>();
		if(StringUtils.isNotBlank(exportControlIds)){
			for(String exportControlId : exportControlIds.split(IDS_SPLIT)){
				ExportControl exportControlDb = this.exportControlDao.get(exportControlId);
				if(exportControlDb != null){
					if(true){
						OperResult< String > result = new OperResult< String >(false);
						result.setBody(exportControlId);
						result.setMsgCode(OperResultConstant.CODE_OPER_SUCCESS);
						result.setMessage("**的记录不能删除！");
						list.add(result);	
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 删除.
	 * 
	 * @param user 用户信息
	 * @param exportControlIds 主键集合，多个以逗号分隔
	 */
	@Transactional(transactionManager=TransManager.APP, rollbackFor=Exception.class)
	public OperResult< ? > delete(UserProfile user, String exportControlIds){
		if(StringUtils.isNotBlank(exportControlIds)){
			for(String exportControlId : exportControlIds.split(IDS_SPLIT)){
				ExportControl exportControlDb = this.exportControlDao.get(exportControlId);
				if (exportControlDb == null) {
					continue;
				}
				
				//权限校验
				AuthUtils.check(user, AuthConstant.MODULE_ID, AuthConstant.AUTH_OPER_NAME,
						exportControlId, exportControlDb, ExceptionCode.Auth.NOT_EXECUTABLE);
						
				//删除记录
				this.exportControlDao.delete(exportControlId);
				
				//记录操作日志
				String logMessage = MessageFormat.format(LogConstant.OP_LOG_MSG_, "");
				EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(user, 
						LogConstant.OP_LOG_ACTION_, logMessage, exportControlDb, null);
			}
		}
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}
	
	/**
	 * 判断指定字段唯一性
	 * 
	 * @param exportControlId
	 * @param propertyName 属性名称
	 * @param propertyName 属性值
	 * @return
	 */
	public boolean checkPropertyUniquenes(String exportControlId, String propertyName, String propertyValue){
		return this.exportControlDao.checkPropertyUniquenes(exportControlId, propertyName, propertyValue);
	}
}

