package com.supporter.prj.cneec.pc.pre_prj_manager.develop_agreement.service;
import com.supporter.prj.cneec.pc.pre_prj_manager.develop_agreement.constant.*;
import com.supporter.prj.cneec.pc.pre_prj_manager.develop_agreement.dao.DevelopAgreementDao;
import com.supporter.prj.cneec.pc.pre_prj_manager.develop_agreement.entity.DevelopAgreement;
import com.supporter.prj.cneec.pc.pre_prj_manager.develop_agreement.util.AuthUtils;
import com.supporter.prj.cneec.pc.pre_prj_manager.develop_agreement.util.BeanUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
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
 * @Description: 开发合作.
 * @author kangh_000
 * @date 2018-12-14 17:09:39
 * @version V1.0   
 *
 */
@Service
public class DevelopAgreementService {
	private static final String IDS_SPLIT = ",";
	@Autowired
	private DevelopAgreementDao developAgreementDao;
	
	
	/**
	 * 根据主键获取开发合作.
	 * 
	 * @param developAgreementId 主键
	 * @return DevelopAgreement
	 */
	public DevelopAgreement get(String developAgreementId){
		return  developAgreementDao.get(developAgreementId);
	}
	
	
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @return JqGrid
	 */
	public List< DevelopAgreement > getGrid(UserProfile user, JqGrid jqGrid, DevelopAgreement developAgreement) {
		return developAgreementDao.findPage(user, jqGrid, developAgreement);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param developAgreementId
	 * @param user
	 * @return
	 */
	public DevelopAgreement initEditOrViewPage(String developAgreementId, UserProfile user) {
		// 新建
		if (StringUtils.isBlank(developAgreementId)) {
			DevelopAgreement entity = new DevelopAgreement();
			entity.setApplierId(user.getPersonId());
			entity.setApplierName(user.getName());
			Dept dept = user.getDept();
			String entityId = UUID.randomUUID().toString().replace("-", "");
			entity.setDevelopAgreementId(entityId);

			if (dept != null) {
				entity.setDeptId(dept.getDeptId());
				entity.setDeptName(dept.getName());
			}

			return entity;
		} else {
			// 编辑
			DevelopAgreement entity = developAgreementDao.get(developAgreementId);
			if(entity != null){
				
			}
			return entity;
		}
	}
	
	
	/**
	 * 保存.
	 * @param user 用户信息
	 * @param developAgreement 实体类
	 * @return
	 */
	@Transactional(transactionManager=TransManager.APP, rollbackFor=Exception.class)
	public OperResult< DevelopAgreement > save(UserProfile user, DevelopAgreement developAgreement, Map<String, Object> valueMap){
		setAgreementTypeName(developAgreement);

		//根据主键查询数据库
		DevelopAgreement developAgreementDb = this.developAgreementDao.get(developAgreement.getDevelopAgreementId());
		//如果不存在，则执行保存操作
		if(developAgreementDb == null){
			//主键
			String developAgreementId = developAgreement.getDevelopAgreementId();

			//权限校验
			AuthUtils.check(user, AuthConstant.MODULE_ID, AuthConstant.AUTH_OPER_NAME,
					developAgreementId, developAgreement, ExceptionCode.Auth.NOT_EXECUTABLE);

			String agreementNo = generateNo();
			developAgreement.setAgreementNo(agreementNo);
			developAgreement.setApplyDate(new Date());
			
			//保存数据库
			this.developAgreementDao.save(developAgreement);

			//记录操作日志
			String logMessage = MessageFormat.format(LogConstant.OP_LOG_MSG_, "");
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(user,
					LogConstant.OP_LOG_ACTION_, logMessage, developAgreement, null);

			return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, developAgreement);
		}else{
			BeanUtils.copyProperties(developAgreement, developAgreementDb, valueMap);

			//主键
			String developAgreementId = developAgreement.getDevelopAgreementId();
			//权限校验
			AuthUtils.check(user, AuthConstant.MODULE_ID, AuthConstant.AUTH_OPER_NAME,
					developAgreementId, developAgreementDb, ExceptionCode.Auth.NOT_EXECUTABLE);

			//更新数据库
			this.developAgreementDao.update(developAgreementDb);

			//记录操作日志
			String logMessage = MessageFormat.format(LogConstant.OP_LOG_MSG_, "");
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(user,
					LogConstant.OP_LOG_ACTION_, logMessage, developAgreementDb, null);

			return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, developAgreementDb);
		}
	}

	private void setAgreementTypeName(DevelopAgreement developAgreement) {
		String agreementTypeId = developAgreement.getAgreementTypeId();

		if (StringUtils.isEmpty(agreementTypeId)) {
			return;
		}

		List<IComCodeTableItem> comCodeTableItemList =
				EIPService.getComCodeTableService().getCodeTableItems(DevelopAgreementConstant.AGREEMENT_TYPE_INNERNAME);

		if (comCodeTableItemList == null) {
			return;
		}

		for (IComCodeTableItem comCodeTableItem : comCodeTableItemList) {
			if (comCodeTableItem.getItemId().equals(agreementTypeId)) {
				developAgreement.setAgreementTypeName(comCodeTableItem.getDisplayName());

				return;
			}
		}
	}

	private String generateNo() {
		String agreementNo = "";
		String recType = "XY";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		Format f0 = new DecimalFormat("00");
		String noPre = recType + year + f0.format(month);

		Integer maxNo = developAgreementDao.getMaxNo(noPre);

		if (maxNo != null) {
			Format f1 = new DecimalFormat("000");
			agreementNo = noPre + f1.format(maxNo + 1);
		} else {
			agreementNo = noPre + "001";
		}

		return agreementNo;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param developAgreement 实体类
	 * @return
	 */
	@Transactional(transactionManager=TransManager.APP, rollbackFor=Exception.class) 
	public OperResult< DevelopAgreement > update(UserProfile user, DevelopAgreement developAgreement, Map<String, Object> valueMap){
//		//根据主键查询数据库
//		DevelopAgreement developAgreementDb = this.developAgreementDao.get(developAgreement.getDevelopAgreementId());
//		//如果不存在，则执行保存操作
//		if(developAgreementDb == null){
//			return this.save(user, developAgreement);
//		}else{
//			BeanUtils.copyProperties(developAgreement, developAgreementDb, valueMap);
//
//			//主键
//			String developAgreementId = developAgreement.getDevelopAgreementId();
//			//权限校验
//			AuthUtils.check(user, AuthConstant.MODULE_ID, AuthConstant.AUTH_OPER_NAME,
//					developAgreementId, developAgreementDb, ExceptionCode.Auth.NOT_EXECUTABLE);
//
//			//更新数据库
//			this.developAgreementDao.update(developAgreementDb);
//
//			//记录操作日志
//			String logMessage = MessageFormat.format(LogConstant.OP_LOG_MSG_, "");
//			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(user,
//					LogConstant.OP_LOG_ACTION_, logMessage, developAgreementDb, null);
//
//			return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, developAgreementDb);
//		}

		return null;
		
	}
	
	/**
	 * 删除前判断是否可以删除.
	 * @param user
	 * @param developAgreementIds 主键集合，多个以逗号分隔
	 * @return
	 */
	public  List< OperResult< ? > > checkBatchDelCondition(UserProfile user, String developAgreementIds){
		List< OperResult< ? > > list = new ArrayList<OperResult<?>>();
		if(StringUtils.isNotBlank(developAgreementIds)){
			for(String developAgreementId : developAgreementIds.split(IDS_SPLIT)){
				DevelopAgreement developAgreementDb = this.developAgreementDao.get(developAgreementId);
				if(developAgreementDb != null){
					if(true){
						OperResult< String > result = new OperResult< String >(false);
						result.setBody(developAgreementId);
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
	 * @param developAgreementIds 主键集合，多个以逗号分隔
	 */
	@Transactional(transactionManager=TransManager.APP, rollbackFor=Exception.class)
	public OperResult< ? > delete(UserProfile user, String developAgreementIds){
		if(StringUtils.isNotBlank(developAgreementIds)){
			for(String developAgreementId : developAgreementIds.split(IDS_SPLIT)){
				DevelopAgreement developAgreementDb = this.developAgreementDao.get(developAgreementId);
				if (developAgreementDb == null) {
					continue;
				}
				
				//权限校验
				AuthUtils.check(user, AuthConstant.MODULE_ID, AuthConstant.AUTH_OPER_NAME,
						developAgreementId, developAgreementDb, ExceptionCode.Auth.NOT_EXECUTABLE);
						
				//删除记录
				this.developAgreementDao.delete(developAgreementId);
				
				//记录操作日志
				String logMessage = MessageFormat.format(LogConstant.OP_LOG_MSG_, "");
				EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(user, 
						LogConstant.OP_LOG_ACTION_, logMessage, developAgreementDb, null);
			}
		}
		return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
	}
	
	/**
	 * 判断指定字段唯一性
	 * 
	 * @param developAgreementId
	 * @param propertyName 属性名称
	 * @param propertyName 属性值
	 * @return
	 */
	public boolean checkPropertyUniquenes(String developAgreementId, String propertyName, String propertyValue){
		return this.developAgreementDao.checkPropertyUniquenes(developAgreementId, propertyName, propertyValue);
	}

	public OperResult<?> stopAgreement(String developAgreementId) {
		if (StringUtils.isEmpty(developAgreementId)) {
			return null;
		}

		DevelopAgreement developAgreement = developAgreementDao.get(developAgreementId);

		if (developAgreement == null) {
			return null;
		}

		developAgreement.setAgreementStatus(AgreementStatusEnum.STOP.getInnerName());
		developAgreementDao.update(developAgreement);

		return OperResult.succeed(null);
	}
}

