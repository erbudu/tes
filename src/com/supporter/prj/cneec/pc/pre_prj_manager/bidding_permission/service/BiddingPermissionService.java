package com.supporter.prj.cneec.pc.pre_prj_manager.bidding_permission.service;

import com.supporter.prj.cneec.pc.pre_prj_manager.bidding_permission.constant.AuthConstant;
import com.supporter.prj.cneec.pc.pre_prj_manager.bidding_permission.constant.LogConstant;
import com.supporter.prj.cneec.pc.pre_prj_manager.bidding_permission.constant.OperResultConstant;
import com.supporter.prj.cneec.pc.pre_prj_manager.bidding_permission.dao.BiddingPermissionDao;
import com.supporter.prj.cneec.pc.pre_prj_manager.bidding_permission.entity.BiddingPermission;
import com.supporter.prj.cneec.pc.pre_prj_manager.bidding_permission.util.AuthUtils;
import com.supporter.prj.cneec.pc.pre_prj_manager.bidding_permission.util.BeanUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.exception.ExceptionCode;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.util.*;

/**   
 * @Title: Service
 * @Description: 投标许可.
 * @author kangh_000
 * @date 2018-12-11 09:56:38
 * @version V1.0   
 *
 */
@Service
public class BiddingPermissionService {
	private static final String IDS_SPLIT = ",";
	@Autowired
	private BiddingPermissionDao biddingPermissionDao;
	
	
	/**
	 * 根据主键获取投标许可.
	 * 
	 * @param biddingPermissionId 主键
	 * @return BiddingPermission
	 */
	public BiddingPermission get(String biddingPermissionId){
		return  biddingPermissionDao.get(biddingPermissionId);
	}
	
	
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @return JqGrid
	 */
	public List< BiddingPermission > getGrid(UserProfile user, JqGrid jqGrid, BiddingPermission biddingPermission) {
		return biddingPermissionDao.findPage(user, jqGrid, biddingPermission);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param biddingPermissionId
	 * @param user
	 * @return
	 */
	public BiddingPermission initEditOrViewPage(String biddingPermissionId, UserProfile user) {
		// 新建
		if (StringUtils.isBlank(biddingPermissionId)) {
			BiddingPermission entity = new BiddingPermission();
			entity.setApplierId(user.getPersonId());
			entity.setApplierName(user.getName());
			Dept dept = user.getDept();
			String permissionId = UUID.randomUUID().toString().replace("-", "");
			entity.setBiddingPermissionId(permissionId);

			if (dept != null) {
				entity.setDeptId(dept.getDeptId());
				entity.setDeptName(dept.getName());
			}

			return entity;
		} else {
			// 编辑
			BiddingPermission entity = biddingPermissionDao.get(biddingPermissionId);
			if(entity != null){
				
			}
			return entity;
		}
	}
	
	
	/**
	 * 保存.
	 * @param user 用户信息
	 * @param biddingPermission 实体类
	 * @return
	 */
	@Transactional(transactionManager=TransManager.APP, rollbackFor=Exception.class)
	public OperResult< BiddingPermission > save(UserProfile user, BiddingPermission biddingPermission,  Map<String, Object> valueMap){
		//主键
		String biddingPermissionId = biddingPermission.getBiddingPermissionId();

		BiddingPermission biddingPermissionDb = this.biddingPermissionDao.get(biddingPermissionId);
		//如果不存在，则执行保存操作
		if(biddingPermissionDb == null){
			//权限校验
			AuthUtils.check(user, AuthConstant.MODULE_ID, AuthConstant.AUTH_OPER_NAME,
					biddingPermissionId, biddingPermission, ExceptionCode.Auth.NOT_EXECUTABLE);

			String biddingPermissionNo = generatePermissionNo();
			biddingPermission.setBiddingPermissionNo(biddingPermissionNo);
			biddingPermission.setApplyDate(new Date());

			//保存数据库
			this.biddingPermissionDao.save(biddingPermission);

			//记录操作日志
			String logMessage = MessageFormat.format(LogConstant.OP_LOG_MSG_, "");
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(user,
					LogConstant.OP_LOG_ACTION_, logMessage, biddingPermission, null);

			return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, biddingPermission);
		}else{
			BeanUtils.copyProperties(biddingPermission, biddingPermissionDb, valueMap);

			//权限校验
			AuthUtils.check(user, AuthConstant.MODULE_ID, AuthConstant.AUTH_OPER_NAME,
					biddingPermissionId, biddingPermissionDb, ExceptionCode.Auth.NOT_EXECUTABLE);

			//更新数据库
			this.biddingPermissionDao.update(biddingPermissionDb);

			//记录操作日志
			String logMessage = MessageFormat.format(LogConstant.OP_LOG_MSG_, "");
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(user,
					LogConstant.OP_LOG_ACTION_, logMessage, biddingPermissionDb, null);

			return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, biddingPermissionDb);
		}
	}

	private String generatePermissionNo() {
		String permissionNo = "";
		String recType = "投许（字）";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
//		String year = CommonUtil.format(new Date(), "yyyyMM");
		Integer maxNo = biddingPermissionDao.getMaxPermissionNo((recType + year)
				.toString());

		if (maxNo != null) {
			Format f1 = new DecimalFormat("000");
			permissionNo = recType + year + "年第" + f1.format(maxNo + 1) + "号";
		} else {
			permissionNo = recType + year + "年第001号";
		}

		return permissionNo;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param biddingPermission 实体类
	 * @return
	 */
	@Transactional(transactionManager=TransManager.APP, rollbackFor=Exception.class) 
	public OperResult< BiddingPermission > update(UserProfile user, BiddingPermission biddingPermission, Map<String, Object> valueMap){
		//根据主键查询数据库
		BiddingPermission biddingPermissionDb = this.biddingPermissionDao.get(biddingPermission.getBiddingPermissionId());
		//如果不存在，则执行保存操作
		if(biddingPermissionDb == null){
//			return this.save(user, biddingPermission);
			return null;
		}else{
			BeanUtils.copyProperties(biddingPermission, biddingPermissionDb, valueMap);
			
			//主键
			String biddingPermissionId = biddingPermission.getBiddingPermissionId();
			//权限校验
			AuthUtils.check(user, AuthConstant.MODULE_ID, AuthConstant.AUTH_OPER_NAME,
					biddingPermissionId, biddingPermissionDb, ExceptionCode.Auth.NOT_EXECUTABLE);
					
			//更新数据库
			this.biddingPermissionDao.update(biddingPermissionDb);
			
			//记录操作日志
			String logMessage = MessageFormat.format(LogConstant.OP_LOG_MSG_, "");
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(user, 
					LogConstant.OP_LOG_ACTION_, logMessage, biddingPermissionDb, null);
					
			return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, biddingPermissionDb);
		}
		
	}
	
	/**
	 * 删除前判断是否可以删除.
	 * @param user
	 * @param biddingPermissionIds 主键集合，多个以逗号分隔
	 * @return
	 */
	public  List< OperResult< ? > > checkBatchDelCondition(UserProfile user, String biddingPermissionIds){
		List< OperResult< ? > > list = new ArrayList<OperResult<?>>();
		if(StringUtils.isNotBlank(biddingPermissionIds)){
			for(String biddingPermissionId : biddingPermissionIds.split(IDS_SPLIT)){
				BiddingPermission biddingPermissionDb = this.biddingPermissionDao.get(biddingPermissionId);
				if(biddingPermissionDb != null){
					if(true){
						OperResult< String > result = new OperResult< String >(false);
						result.setBody(biddingPermissionId);
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
	 * @param biddingPermissionIds 主键集合，多个以逗号分隔
	 */
	@Transactional(transactionManager=TransManager.APP, rollbackFor=Exception.class)
	public OperResult< ? > delete(UserProfile user, String biddingPermissionIds){
		if(StringUtils.isNotBlank(biddingPermissionIds)){
			for(String biddingPermissionId : biddingPermissionIds.split(IDS_SPLIT)){
				BiddingPermission biddingPermissionDb = this.biddingPermissionDao.get(biddingPermissionId);
				if (biddingPermissionDb == null) {
					continue;
				}
				
				//权限校验
				AuthUtils.check(user, AuthConstant.MODULE_ID, AuthConstant.AUTH_OPER_NAME,
						biddingPermissionId, biddingPermissionDb, ExceptionCode.Auth.NOT_EXECUTABLE);
						
				//删除记录
				this.biddingPermissionDao.delete(biddingPermissionId);
				
				//记录操作日志
				String logMessage = MessageFormat.format(LogConstant.OP_LOG_MSG_, "");
				EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(user, 
						LogConstant.OP_LOG_ACTION_, logMessage, biddingPermissionDb, null);
			}
		}
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}
	
	/**
	 * 判断指定字段唯一性
	 * 
	 * @param biddingPermissionId
	 * @param propertyName 属性名称
	 * @param propertyName 属性值
	 * @return
	 */
	public boolean checkPropertyUniquenes(String biddingPermissionId, String propertyName, String propertyValue){
		return this.biddingPermissionDao.checkPropertyUniquenes(biddingPermissionId, propertyName, propertyValue);
	}
}

