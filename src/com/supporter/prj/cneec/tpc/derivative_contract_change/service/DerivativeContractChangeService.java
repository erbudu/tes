package com.supporter.prj.cneec.tpc.derivative_contract_change.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChange;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChangeCollectionTerms;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChangeContractAmount;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractOrder;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractSeal;
import com.supporter.prj.cneec.tpc.contract_online_prepare.entity.ContractOnlinePrepare;
import com.supporter.prj.cneec.tpc.contract_online_prepare.service.ContractOnlinePrepareService;
import com.supporter.prj.cneec.tpc.contract_stamp_tax_amount.service.ContractStampTaxAmountService;
import com.supporter.prj.cneec.tpc.derivative_contract_change.dao.DerivativeConChangeDao;
import com.supporter.prj.cneec.tpc.derivative_contract_change.dao.DerivativeContractChangeAmountDao;
import com.supporter.prj.cneec.tpc.derivative_contract_change.dao.DerivativeContractChangeDao;
import com.supporter.prj.cneec.tpc.derivative_contract_change.dao.DerivativeContractChangeTermsDao;
import com.supporter.prj.cneec.tpc.derivative_contract_change.dao.DerivativeSealDao;
import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeConChange;
import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeContractChange;
import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeContractChangeAmount;
import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeContractChangeTerms;
import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeSeal;
import com.supporter.prj.cneec.tpc.derivative_contract_change.util.DeriveChangeConstant;
import com.supporter.prj.cneec.tpc.derivative_contract_online.entity.DerivativeContractOnline;
import com.supporter.prj.cneec.tpc.derivative_contract_online.service.DerivativeContractOnlineService;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.dao.PrjContractSettlementRecDao;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.entity.PrjContractSettlementRec;
import com.supporter.prj.cneec.tpc.prj_contract_table.dao.PrjContractAmountDao;
import com.supporter.prj.cneec.tpc.prj_contract_table.dao.PrjContractCollectionTermsDao;
import com.supporter.prj.cneec.tpc.prj_contract_table.dao.PrjContractTableDao;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractAmount;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractCollectionTerms;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractTable;
import com.supporter.prj.cneec.tpc.prj_contract_table.service.PrjContractTableService;
import com.supporter.prj.cneec.tpc.record_filing_manager.dao.RecordFilingManagerDao;
import com.supporter.prj.cneec.tpc.record_filing_manager.entity.RecordFilingManager;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.cneec.tpc.util.TpcBudgetUtil;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileManageService;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.todo.entity.Todo;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.account.entity.Account;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * @Title: ContractOnlineService
 * @Description: 业务操作类
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class DerivativeContractChangeService {

	@Autowired
	private DerivativeContractChangeDao derivativeContractChangeDao;
	@Autowired
	private DerivativeContractChangeAmountDao changeAmountDao;
	@Autowired
	private DerivativeContractChangeTermsDao changeTermsDao;
	@Autowired
	private DerivativeSealDao derivativeSealDao;
	@Autowired
	private DerivativeConChangeDao derivativeConChangeDao;
	@Autowired
	private PrjContractTableDao prjContractTableDao;
	@Autowired
	private PrjContractAmountDao prjContractAmountDao;
	@Autowired
	private PrjContractCollectionTermsDao prjContractCollectionTermsDao;
	@Autowired
	private ContractOnlinePrepareService contractOnlinePrepareService;
	@Autowired
	private DerivativeContractOnlineService derivativeContractOnlineService;
	@Autowired
	private RecordFilingManagerDao filingManagerDao;
	@Autowired
	private PrjContractSettlementRecDao prjContractSettlementRecDao;
	@Autowired
	private PrjContractTableService prjContractTableService;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private FileManageService fileManageService;
	@Autowired
	private ContractStampTaxAmountService contractStampTaxAmountService;

	/**
	 * 数据显示权限过滤
	 * 
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, DerivativeContractChange.MODULE_ID,
				AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * 
	 * @param userProfile
	 * @param contractChange
	 */
	public void getAuthCanExecute(UserProfile userProfile, DerivativeContractChange contractChange) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, DerivativeContractChange.MODULE_ID,
				contractChange.getChangeId(), contractChange);
	}

	/**
	 * 获取销售合同变更对象集合
	 * 
	 * @param user
	 * @param jqGrid
	 * @param contractChange
	 * @return
	 */
	public List<DerivativeContractChange> getGrid(UserProfile user, JqGrid jqGrid,
			DerivativeContractChange contractChange, String contractId) {
		String authFilter = getAuthFilter(user);
		return this.derivativeContractChangeDao.findPage(jqGrid, contractChange, contractId, authFilter);
	}

	/**
	 * 获取合同额集合
	 * 
	 * @param user
	 * @param jqGrid
	 * @param changeId
	 * @return
	 */
	public List<DerivativeContractChangeAmount> getContractAmountGrid(UserProfile user, JqGrid jqGrid, String chId) {
		return this.changeAmountDao.findPage(jqGrid, chId);
	}

	/**
	 * 获取货物及服务明细集合
	 * 
	 * @param user
	 * @param jqGrid
	 * @param contractId
	 * @return
	 */
	// public List<DerivativeContractGoods> getGoodsGrid(UserProfile user, JqGrid
	// jqGrid, String contractId) {
	// return this.goodsDao.findPage(jqGrid, contractId);
	// }

	/**
	 * 获取付款条件集合
	 * 
	 * @param user
	 * @param jqGrid
	 * @param changeId
	 * @return
	 */
	public List<DerivativeContractChangeTerms> getCollectionTermsGrid(UserProfile user, JqGrid jqGrid, String chId) {
		return this.changeTermsDao.findPage(jqGrid, chId);
	}

	/**
	 * 获取单个合同上线对象
	 * 
	 * @param changeId
	 * @return
	 */
	public DerivativeContractChange get(String changeId) {
		return this.derivativeContractChangeDao.get(changeId);
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * 
	 * @param contractId
	 * @param user
	 * @return
	 */
	// public DerivativeContractChange initEditOrViewPage(String changeId,
	// UserProfile user) {
	// DerivativeContractChange contractChange;
	// if (StringUtils.isBlank(changeId)) {
	// contractChange = newContractChange(user);
	// contractChange.setContractId(UUIDHex.newId());
	// contractChange.setAdd(true);
	// } else {
	// contractChange = (DerivativeContractChange)
	// this.derivativeContractChangeDao.get(changeId);
	// contractChange.setAdd(false);
	// }
	// return contractChange;
	// }

	/**
	 * 保存或修改对象
	 * 
	 * @param user
	 * @param contractChange
	 * @param valueMap
	 * @return
	 */
	public DerivativeConChange saveOrUpdate(UserProfile user, DerivativeConChange derivativeConChange,
			Map<String, Object> valueMap) {
		if (derivativeConChange.getAdd()) {
			// 装配基础信息
			loadDerivativeChange(derivativeConChange, user);
			this.derivativeConChangeDao.save(derivativeConChange);
			// 保存用印/备案合同附件
			this.saveFile(derivativeConChange, user);
			updatePrepareProcess(derivativeConChange);
		} else {
			// 设置更新时间
			derivativeConChange.setModifiedBy(user.getName());
			derivativeConChange.setModifiedById(user.getPersonId());
			derivativeConChange.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.derivativeConChangeDao.update(derivativeConChange);

			// 保存或更新合同额
			List<DerivativeContractChangeAmount> contractAmountList = derivativeConChange.getContractAmountList();
			if (CollectionUtils.isNotEmpty(contractAmountList)) {
				saveOrUpdateContractAmountList(derivativeConChange.getChId(), contractAmountList);
			}
			// 删除合同额
			deleteContractAmount(derivativeConChange.getDelAmountIds());
			// 保存或更新收款条件
			List<DerivativeContractChangeTerms> collectionTermsList = derivativeConChange.getCollectionTermsList();
			if (CollectionUtils.isNotEmpty(collectionTermsList)) {
				saveOrUpdateCollectionTermsList(derivativeConChange.getChId(), collectionTermsList);
			}
			// 删除收款条件
			deleteCollectionTerms(derivativeConChange.getDelTermsIds());
		}
		return derivativeConChange;
	}

	/**
	 * 新建销售合同上线对象
	 * 
	 * @param user
	 * @return
	 */
	public DerivativeConChange newDerivativeConChange(UserProfile user) {
		DerivativeConChange derivativeConChange = new DerivativeConChange();
		loadDerivativeChange(derivativeConChange, user);
		derivativeConChange.setSwfStatus(DerivativeConChange.DRAFT);
		return derivativeConChange;
	}

	/**
	 * 添加基础信息
	 * 
	 * @return
	 */
	public DerivativeConChange loadDerivativeChange(DerivativeConChange derivativeConChange, UserProfile user) {
		derivativeConChange.setCreatedBy(user.getName());
		derivativeConChange.setCreatedById(user.getPersonId());
		derivativeConChange.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		derivativeConChange.setModifiedBy(user.getName());
		derivativeConChange.setModifiedById(user.getPersonId());
		derivativeConChange.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			derivativeConChange.setDeptName(dept.getName());
			derivativeConChange.setDeptId(dept.getDeptId());
		}
		// 设置状态
		derivativeConChange.setSwfStatus(DerivativeConChange.DRAFT);
		return derivativeConChange;
	}

	/**
	 * 将用印/备案的附件保存到衍生合同上线的附件中
	 * 
	 * @param contractChange
	 */
	private void saveFile(DerivativeConChange derivativeConChange, UserProfile user) {
		RecordFilingManager recordFiling = this.filingManagerDao.findUniqueResult("preformId",
				derivativeConChange.getPreformId());
		if (recordFiling != null) {
			List<IFile> fileList = EIPService.getFileUploadService().getFileList(RecordFilingManager.MODULE_ID,
					RecordFilingManager.BUSI_TYPE_CONTRACT, recordFiling.getRecordFilingId());
			for (int i = 0; i < fileList.size(); i++) {
				File file = EIPService.getFileUploadService().getFile(fileList.get(i).getFileId());
				String fileName = fileList.get(i).getFileName();
				EIPService.getFileUploadService().saveFile(file, DerivativeContractChange.MODULE_ID,
						DerivativeConChange.BUSI_TYPE, fileName, i, user, derivativeConChange.getContractId());
			}
		}
	}

	/**
	 * 删除合同额
	 * 
	 * @param delAmountIds
	 */
	public void deleteContractAmount(String delAmountIds) {
		if (StringUtils.isNotBlank(delAmountIds)) {
			for (String amountId : delAmountIds.split(",")) {
				// 根据ID字段删除
				this.changeAmountDao.deleteByProperty("amountId", amountId);
			}
		}
	}

	/**
	 * 保存或更新合同额
	 * 
	 * @param contractId
	 * @param contractAmountList
	 */
	public void saveOrUpdateContractAmountList(String chId, List<DerivativeContractChangeAmount> contractAmountList) {
		for (DerivativeContractChangeAmount amount : contractAmountList) {
			amount.setChId(chId);
			if (StringUtils.isBlank(amount.getAmountId()) || amount.getAdd()) {
				this.changeAmountDao.save(amount);
			} else {
				this.changeAmountDao.update(amount);
			}
		}
	}

	/**
	 * 保存或更新合同
	 * 
	 * @param contractId
	 * @param goodsList
	 */
	public DerivativeConChange initEditOrViewPage(String changeId, UserProfile user) {
		DerivativeConChange contractOrder = null;
		if (StringUtils.isNotBlank(changeId)) {
			contractOrder = derivativeConChangeDao.getOrderByChangeId(changeId);
			if (contractOrder != null) {
				contractOrder.setModifiedById(user.getName());
				contractOrder.setModifiedBy(user.getPersonId());
			} else {
				DerivativeContractChange change = derivativeContractChangeDao.get(changeId);
				// 通过changeId获取contractId
				String contractId = change.getContractId();
				// 通过contractId 获取原合同 复制到contractOrder中
				PrjContractTable contract = this.prjContractTableDao.get(contractId);
				if (contract == null) {
					return null;
				}
				try {
					contractOrder = new DerivativeConChange();
					BeanUtils.copyProperties(contractOrder, contract);
					// 复制主表信息，并保存
					contractOrder.setChId(com.supporter.util.UUIDHex.newId());
					contractOrder.setChangeId(changeId);
					contractOrder.setSwfStatus(ContractChange.DRAFT);

					contractOrder.setCreatedById(user.getPersonId());
					contractOrder.setCreatedBy(user.getName());
					contractOrder.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd"));
					contractOrder.setTelephone(change.getTelephone());
					contractOrder.setBusinessNo(change.getBusinessNo());
					// 保存主表
					derivativeConChangeDao.save(contractOrder);
					// 设置合同额
					List<PrjContractAmount> amountList = prjContractAmountDao.getByContractId(contractId);
					if (amountList != null) {
						for (PrjContractAmount amount : amountList) {
							if (amount == null) {
								continue;
							}
							DerivativeContractChangeAmount changeAmount = new DerivativeContractChangeAmount();
							BeanUtils.copyProperties(changeAmount, amount);
							changeAmount.setAmountId(com.supporter.util.UUIDHex.newId());
							changeAmount.setAmountOldId(amount.getAmountId());
							changeAmount.setChId(contractOrder.getChId());
							// 保存
							changeAmountDao.save(changeAmount);
						}
					}
					// 设置收款条件
					List<PrjContractCollectionTerms> termsList = prjContractCollectionTermsDao
							.getByContractId(contractId);
					if (termsList != null) {
						for (PrjContractCollectionTerms terms : termsList) {
							if (terms == null) {
								continue;
							}
							DerivativeContractChangeTerms changeterms = new DerivativeContractChangeTerms();
							BeanUtils.copyProperties(changeterms, terms);
							changeterms.setTermsId(com.supporter.util.UUIDHex.newId());
							changeterms.setTermsOldId(terms.getTermsId());
							changeterms.setChId(contractOrder.getChId());
							// 保存
							changeTermsDao.save(changeterms);
						}
					}
					// 复制附件信息
					File(PrjContractTable.MODULE_ID, "file", contract.getContractId(), "", contractOrder.getChId(), "",
							"file", user);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return contractOrder;
			}
		}
		return contractOrder;
	}

	/**
	 * 同步附件
	 * 
	 * @param moduleName
	 * @param busiType
	 * @param oneLevelId
	 * @param twoLevelId
	 * @param newOneId
	 * @param newTwoId
	 * @param newType
	 * @param user
	 */
	public void File(String moduleName, String busiType, String oneLevelId, String twoLevelId, String newOneId,
			String newTwoId, String newType, UserProfile user) {
		List<FileUpload> list = fileUploadService.getList(moduleName, busiType, oneLevelId, twoLevelId);
		if (list != null && list.size() > 0) {
			for (FileUpload fileUpload : list) {
				if (fileUpload == null) {
					continue;
				}
				FileUpload file = new FileUpload();
				try {
					String newFileId = com.supporter.util.UUIDHex.newId();
					BeanUtils.copyProperties(file, fileUpload);
					file.setModuleName(ContractOrder.MODULE_ID);
					file.setCreatedDate(fileUpload.getCreatedDate());
					file.setFileId(newFileId);
					file.setOneLevelId(newOneId);
					file.setTwoLevelId(newTwoId);
					file.setBusiType(newType);
					file.setFileSize(fileUpload.getFileSize());
					fileUploadService.save(file);
					String pathOld = fileManageService.getStoragePath(fileUpload.getTenantNo(),
							fileUpload.getModuleName(), fileUpload.getBusiType(), fileUpload.getCreatedDate());
					String root = pathOld + File.separator;
					File sourceFile = new File(root, fileUpload.getFileId());

					String path = fileManageService.getStoragePath(file.getTenantNo(), file.getModuleName(),
							file.getBusiType(), file.getCreatedDate());
					// 先创建文件夹
					File fileM = new File(path);
					fileM.mkdirs();
					// 然后复制
					File newFile = new File(path, newFileId);

					// 读文件内容到新文件
					this.copy(sourceFile, newFile);

					// fileUploadService.saveFile(newFile, moduleName, newType, user,
					// file.getFileId());
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void copy(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src), 16 * 1024);
				out = new BufferedOutputStream(new FileOutputStream(dst), 16 * 1024);
				byte[] buffer = new byte[16 * 1024];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除收款条件
	 * 
	 * @param delTermsIds
	 */
	public void deleteCollectionTerms(String delTermsIds) {
		if (StringUtils.isNotBlank(delTermsIds)) {
			for (String termsId : delTermsIds.split(",")) {
				this.changeTermsDao.deleteByProperty("termsId", termsId);
			}
		}
	}

	/**
	 * 保存或更新收款条件
	 * 
	 * @param contractId
	 * @param collectionTermsList
	 */
	public void saveOrUpdateCollectionTermsList(String chId, List<DerivativeContractChangeTerms> collectionTermsList) {
		for (DerivativeContractChangeTerms terms : collectionTermsList) {
			terms.setChId(chId);
			if (StringUtils.isBlank(terms.getTermsId()) || terms.getAdd()) {
				this.changeTermsDao.save(terms);
			} else {
				this.changeTermsDao.update(terms);
			}
		}
	}

	/**
	 * 保存提交
	 * 
	 * @param user
	 * @param contractChange
	 * @param valueMap
	 * @return
	 */
	// public DerivativeContractChange commit(UserProfile user,
	// DerivativeContractChange contractChange, Map<String, Object> valueMap) {
	// saveOrUpdate(user, contractChange, valueMap);
	// // 记录日志
	// String logMessage =
	// MessageFormat.format(LogConstant.PUBLISH_LOG_MESSAGE_DERIVATIVECONTRACTONLINE,
	// "(" + contractChange.getSaleContractNo() + ")" +
	// contractChange.getSaleContractName());
	// EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS_DERIVATIVECONTRACTONLINE).info(user,
	// LogConstant.PUBLISH_LOG_ACTION_DERIVATIVECONTRACTONLINE, logMessage,
	// contractChange, null);
	// return contractChange;
	// }

	/**
	 * 审批完成操作
	 * 
	 * @param contractChange
	 */
	// public void completeExam(DerivativeConChange contractChange) {
	// // 将合同信息录入合同正式表中
	// this.derivativeContractOnlineService.createTpcPrjContractTableByDerivativeContractChange(contractChange);
	// // 修改合同前置表的上线状态，为已上线。
	// updatePrepareFinish(contractChange);
	//
	// // 在途金额转执行
	// Person person =
	// EIPService.getEmpService().getEmp(contractChange.getCreatedById());
	// Account account = EIPService.getAccountService().getDefaultAccount(person);
	// UserProfile user = EIPService.getLogonService().getUserProfile(account);
	// String projectId = contractChange.getProjectId();
	// // 衍生合同扣除预算即为费用合计下衍生合同付款类型
	// String budgetId = contractChange.getPaymentType();
	// List<DerivativeContractChangeAmount> contractAmounts =
	// contractChange.getContractAmountList();
	// if (contractAmounts == null || contractAmounts.size() == 0) {
	// contractAmounts = this.changeAmountDao.findBy("contractId",
	// contractChange.getContractId());
	// }
	// for (DerivativeContractChangeAmount contractAmount : contractAmounts) {
	// this.transferToExecute(user, projectId, budgetId,
	// contractAmount.getRmbAmount());
	// }
	// }

	/**
	 * 删除操作设置前置单为草稿
	 */
	// public void updatePrepareDraft(DerivativeContractChange contractChange) {
	// // 删除记录后将前置单合同上线状态置为默认状态
	// String preformId = CommonUtil.trim(contractChange.getPreformId());
	// this.contractOnlinePrepareService.updateStatusByDerivativeId(preformId,
	// "onlineStatus", ContractOnlinePrepare.ONLINE_DRAFT);
	// }

	/**
	 * 保存记录后设置前置单为进行中
	 */
	public void updatePrepareProcess(DerivativeConChange derivativeConChange) {
		// 生成记录后将前置单合同上线状态置为进行中状态
		String preformId = CommonUtil.trim(derivativeConChange.getPreformId());
		this.contractOnlinePrepareService.updateStatusByDerivativeId(preformId, "onlineStatus",
				ContractOnlinePrepare.ONLINE_PROCESS);
	}

	/**
	 * 审批完成设置前置单为完成状态
	 */
	public void updatePrepareFinish(DerivativeConChange contractChange) {
		// 记录审批完成后将前置单合同上线状态置为已完成状态
		String preformId = CommonUtil.trim(contractChange.getPreformId());
		this.contractOnlinePrepareService.updateStatusByDerivativeId(preformId, "onlineStatus",
				ContractOnlinePrepare.ONLINE_FINISH);
	}

	/**
	 * 批量删除对象
	 * 
	 * @param user
	 * @param changeIds
	 */
	public void delete(UserProfile user, String changeIds) {
		DerivativeContractChange derivativeContractChange = derivativeContractChangeDao.get(changeIds);
		derivativeContractChangeDao.delete(derivativeContractChange);
	}
	// public void delete(UserProfile user, String changeIds) {
	// if (StringUtils.isNotBlank(changeIds)) {
	// DerivativeContractChange contractChange;
	// for (String changeId : changeIds.split(",")) {
	// contractChange = this.get(changeId);
	// if (contractChange == null) continue;
	// 权限验证
	// this.getAuthCanExecute(user, contractChange);
	// 重置前置单状态
	// updatePrepareDraft(contractChange);
	// 先删除相关附件
	// deleteFile(contractChange);
	// this.derivativeContractChangeDao.delete(contractChange);
	// this.changeAmountDao.deleteByProperty("changeId", changeId);
	// this.goodsDao.deleteByProperty("changeId", changeId);
	// this.changeTermsDao.deleteByProperty("changeId", changeId);
	// }
	// }
	// }

	/**
	 * 删除相关附件
	 * 
	 * @param recordFilingId
	 */
	// public void deleteFile(DerivativeContractChange contractChange){
	// IFileUploadService fileUploadService = EIPService.getFileUploadService();
	//// List<IFile> list =
	// fileUploadService.getFileList(DerivativeContractChange.MODULE_ID,
	// DerivativeContractChange.BUSI_TYPE, contractChange.getContractId());
	// if (CollectionUtils.isNotEmpty(list)){
	// for(IFile file:list){
	// fileUploadService.deleteFile(file.getFileId());
	// }
	// }
	// }

	/**
	 * 根据ID获取付款金额
	 * 
	 * @param orderId
	 * @return
	 */
	public Map<String, Double> getPayPlanAmounts(String contractId) {
		return this.changeTermsDao.getPayPlanAmounts(contractId);
	}

	/** 以下是选择采购合同方法 **/

	public ListPage<DerivativeContractChange> getListPage(int page, int pageSize, Map<String, Object> parameters,
			UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<DerivativeContractChange> listPage = new ListPage<DerivativeContractChange>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.derivativeContractChangeDao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}
	/*
	 * * 选择衍生合同上线信息将其copy至当前对象中
	 * 
	 * @param changeId
	 * 
	 * @param user
	 * 
	 * @return
	 */
	// public DerivativeContractChange
	// selectDerivativeContractToInit(DerivativeContractChange
	// contractChange,UserProfile user) {
	//
	// String changeId = contractChange.getChangeId();
	// String contractId = contractChange.getContractId();
	//
	// // 先删除之前已经选择保存的从表记录
	// this.changeAmountDao.deleteByProperty("changeId", changeId);
	//// this.goodsDao.deleteByProperty("changeId", changeId);
	// this.changeTermsDao.deleteByProperty("changeId", changeId);
	//
	// // 定义返回对象
	// DerivativeContractChange retContract = new DerivativeContractChange();
	// PrjContractTable contract = this.prjContractTableService.get(contractId);
	// try {
	// // 复制采购合同上线信息至返回对象
	// BeanUtils.copyProperties(retContract, contract);
	// // 复制采购合同上线信息至合同变更前记录表（保存最近一次修改前合同信息）
	// // 采购合同变更独有属性(保存已改动属性)
	// retContract.setChangeId(changeId);
	// retContract.setAdjustmentNature(contractChange.getAdjustmentNature());
	// retContract.setAdjustmentNatureId(contractChange.getAdjustmentNatureId());
	// retContract.setChangeModeId(contractChange.getChangeModeId());
	// retContract.setChangeMode(contractChange.getChangeMode());
	// retContract.setChangeAmount(contractChange.getChangeAmount());
	// retContract.setChangeTypeId(contractChange.getChangeTypeId());
	// retContract.setChangeType(contractChange.getChangeType());
	// retContract.setAdjustmentReasons(contractChange.getAdjustmentReasons());
	// retContract.setProtocolFiles(contractChange.getProtocolFiles());
	// retContract.setSupportingFiles(contractChange.getSupportingFiles());
	// // 保存采购合同上线合同金额
	// retContract.setOnlineRmbAmount(contract.getContractRmbAmount());
	// // 根据增减类型设置合同金额
	// if
	// (contractChange.getChangeTypeId().equals(ContractChangeConstant.CHANGE_TYPE2))
	// {
	// retContract.setContractRmbAmount(retContract.getContractRmbAmount() +
	// contractChange.getChangeAmount());
	// } else if
	// (contractChange.getChangeTypeId().equals(ContractChangeConstant.CHANGE_TYPE3))
	// {
	// retContract.setContractRmbAmount(retContract.getContractRmbAmount() -
	// contractChange.getChangeAmount());
	// }
	// // 采购合同变更对象与采购合同上线对象不一致的属性
	// retContract.setAdd(contractChange.getAdd());
	// retContract.setProcId(null);
	// retContract.setPaymentType(contractChange.getPaymentType());
	// retContract.setSwfStatus(contractChange.getSwfStatus());
	// retContract.setDeptName(contractChange.getDeptName());
	// retContract.setDeptId(contractChange.getDeptId());
	// retContract.setCreatedBy(contractChange.getCreatedBy());
	// retContract.setCreatedById(contractChange.getCreatedById());
	// retContract.setCreatedDate(contractChange.getCreatedDate());
	// contractChange.setModifiedBy(user.getName());
	// contractChange.setModifiedById(user.getPersonId());
	// contractChange.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd
	// HH:mm:ss"));
	// // 设置合同额
	// List<DerivativeContractChangeAmount> changeAmountList = new
	// ArrayList<DerivativeContractChangeAmount>();
	// List<PrjContractAmount> amountList = contract.getContractAmountList();
	// if (CollectionUtils.isNotEmpty(amountList)) {
	// DerivativeContractChangeAmount changeAmount;
	// for (PrjContractAmount amount : amountList) {
	// changeAmount = new DerivativeContractChangeAmount();
	// BeanUtils.copyProperties(changeAmount, amount);
	// changeAmount.setAmountId(null);
	// changeAmount.setAdd(true);
	// changeAmountList.add(changeAmount);
	// }
	// }
	// 设置货物及服务明细
	// List<ContractChangeGoods> changeGoodsList = new
	// ArrayList<ContractChangeGoods>();
	// List<PrjContractGoods> goodsList = contract.getContractGoodsList();
	// if (CollectionUtils.isNotEmpty(goodsList)) {
	// ContractChangeGoods changeGoods;
	// for (PrjContractGoods goods : goodsList) {
	// changeGoods = new ContractChangeGoods();
	// BeanUtils.copyProperties(changeGoods, goods);
	// changeGoods.setGoodsId(null);
	// changeGoods.setAdd(true);
	// changeGoodsList.add(changeGoods);
	// }
	// }
	// 设置付款条件
	// List<DerivativeContractChangeTerms> changeTermsList = new
	// ArrayList<DerivativeContractChangeTerms>();
	// List<PrjContractCollectionTerms> termsList =
	// contract.getCollectionTermsList();
	// if (CollectionUtils.isNotEmpty(termsList)) {
	// DerivativeContractChangeTerms changeTerms;
	// for (PrjContractCollectionTerms terms : termsList) {
	// changeTerms = new DerivativeContractChangeTerms();
	// BeanUtils.copyProperties(changeTerms, terms);
	// changeTerms.setTermsId(null);
	// changeTerms.setAdd(true);
	// changeTermsList.add(changeTerms);
	// }
	// }
	// retContract.setContractAmountList(changeAmountList);
	// retContract.setGoodsList(changeGoodsList);
	// retContract.setCollectionTermsList(changeTermsList);
	// saveOrUpdate(user, retContract, null);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// // 修改合同状态为变更中
	// this.prjContractTableService.updateToChange(contract, true, user);
	// return retContract;
	// }

	/**
	 * 调整性质单/复选框
	 * 
	 * @param projectId
	 * @return
	 */
	public List<CheckBoxVO> getAdjustmentNatureList(String changeId, String chType) {
		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
		String[] arr = new String[] {};
		if (StringUtils.isNotBlank(changeId)) {
			DerivativeContractChange contractChange = this.get(changeId);
			if (contractChange != null) {
				arr = CommonUtil.trim(contractChange.getAdjustmentNatureId()).split(",");
			}
		}
		if (StringUtils.isNotBlank(chType)) {
			Map<Integer, String> map = DeriveChangeConstant.getAdjustmentNatureMap(Integer.parseInt(chType));
			for (Integer key : map.keySet()) {
				CheckBoxVO vo = new CheckBoxVO("adjustmentNatureId_" + key, "adjustmentNatureId", key.toString(),
						map.get(key), existByLoop(arr, key.toString()));
				for (String string : arr) {
					if (string.equals(String.valueOf(key))) {
						vo.setChecked(true);
					}
				}
				list.add(vo);
			}
		}
		return list;
	}

	/**
	 * 判断字符串数组是否包含特定值
	 * 
	 * @param arr
	 * @param tarVal
	 * @return
	 */
	public static boolean existByLoop(String[] arr, String tarVal) {
		for (String s : arr) {
			if (s.equals(tarVal))
				return true;
		}
		return false;
	}

	/**
	 * 是否有协议
	 * 
	 * @param changeId
	 * @return
	 */
	// public List<CheckBoxVO> getHasProtocolList(String changeId) {
	// List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
	// String choose = "";// 不设置默认
	// if (changeId.length() > 0) {
	// DerivativeContractChange contractChange = this.get(changeId);
	// if (contractChange != null) {
	// choose = contractChange.isHasProtocol() ? "true" : "false";
	// }
	// }
	// Map<String, String> map = ContractChangeConstant.getHasProtocolMap();
	// for (String key : map.keySet()) {
	// CheckBoxVO vo = new CheckBoxVO("hasProtocol_" + key, "hasProtocol", key,
	// map.get(key), key.equals(choose));
	// vo.setChecked(true);
	// list.add(vo);
	// }
	// return list;
	// }
	public DerivativeSeal getContractSealBySealId(String sealId) {
		return this.derivativeSealDao.get(sealId);
	}

	public DerivativeConChange getContractOrderByChId(String chId) {
		return this.derivativeConChangeDao.get(chId);
	}

	/**
	 * 流程更新对象
	 * 
	 * @param
	 * @return
	 */
	public DerivativeContractChange update(DerivativeContractChange derivativeContractChange) {
		if (derivativeContractChange == null) {
			return derivativeContractChange;
		}
		this.derivativeContractChangeDao.update(derivativeContractChange);
		return derivativeContractChange;
	}

	/**
	 * 流程更新对象
	 * 
	 * @param Seal
	 * @return
	 */
	public DerivativeSeal update(DerivativeSeal derivativeSeal) {
		if (derivativeSeal == null) {
			return derivativeSeal;
		}
		// derivativeSeal.setBusinessNo(generatorSetBusinessNo());
		this.derivativeSealDao.update(derivativeSeal);
		return derivativeSeal;
	}

	/**
	 * 流程更新对象
	 * 
	 * @param
	 * @return
	 */
	public DerivativeConChange update(DerivativeConChange derivativeConChange) {
		if (derivativeConChange == null) {
			return derivativeConChange;
		}
		this.derivativeConChangeDao.update(derivativeConChange);
		return derivativeConChange;
	}

	/**
	 * 生成协议编号
	 * 
	 * @return
	 */
	public synchronized String generatorSetBusinessNo() {
		String businessNo;
		String NoHead = CommonUtil.format(new Date(), "yyyyMM");
		Integer count = this.derivativeSealDao.getBusinessNoIndex(NoHead);
		String NoEnd = String.format("%03d", count);
		businessNo = NoHead + NoEnd;
		return businessNo;
	}

	public DerivativeContractChange saveChangeOrder(UserProfile user,
			DerivativeContractChange derivativeContractChange) {
		if (user != null) {
			derivativeContractChange.setModifiedBy(user.getName());
			derivativeContractChange.setModifiedById(user.getPersonId());
		}
		// 更新调整性质
		Map<Integer, String> map = DeriveChangeConstant.getAdjustmentNatureMap(derivativeContractChange.getChType());
		String adjustmentNatureIds = derivativeContractChange.getAdjustmentNatureId();
		if (StringUtils.isNotBlank(adjustmentNatureIds)) {
			String adjustmentNature = "";
			for (String adjustmentNatureId : adjustmentNatureIds.split(",")) {
				adjustmentNature += map.get(CommonUtil.parseInt(adjustmentNatureId)) + ",";
			}
			if (adjustmentNature.length() > 0) {
				adjustmentNature = adjustmentNature.substring(0, adjustmentNature.length() - 1);
				derivativeContractChange.setAdjustmentNature(adjustmentNature);
			}
		}
		if (derivativeContractChange.getIsNew()) {
			if (user != null) {
				derivativeContractChange.setCreatedBy(user.getName());
				derivativeContractChange.setCreatedById(user.getPersonId());
				Dept dept = user.getDept();
				if (dept != null) {
					derivativeContractChange.setDeptId(dept.getDeptId());
					derivativeContractChange.setDeptName(dept.getName());
				}
			}
			derivativeContractChange.setCreatedDate(new Date());
			derivativeContractChange.setIsNew(false);
			derivativeContractChangeDao.save(derivativeContractChange);
		} else { // 设置更新时间
			derivativeContractChange.setModifiedDate(new Date());
			derivativeContractChangeDao.update(derivativeContractChange);
		}
		return derivativeContractChange;
	}

	public DerivativeContractChange initChangeOrder(String changeId, String contractId, int chType, UserProfile user) {
		DerivativeContractChange derivativeContractChange = null;
		if (StringUtils.isNotBlank(changeId)) {
			derivativeContractChange = (DerivativeContractChange) this.derivativeContractChangeDao.get(changeId);
		}

		if (derivativeContractChange == null) {
			derivativeContractChange = new DerivativeContractChange();
			if (StringUtils.isBlank(changeId)) {
				changeId = UUIDHex.newId();
			}
			derivativeContractChange.setChangeId(changeId);
			derivativeContractChange.setChType(chType);
			derivativeContractChange.setIsNew(true);
			derivativeContractChange.setSwfStatus(ContractChange.DRAFT);
			// DerivativeContractOnline contract =
			// derivativeContractOnlineService.get(contractId);
			PrjContractTable contract = prjContractTableService.get(contractId);
			if (contract != null) {
				derivativeContractChange.setContractId(contract.getContractId());
				derivativeContractChange.setContractName(contract.getContractName());
				derivativeContractChange.setContractNo(contract.getContractNo());
				derivativeContractChange.setProjectId(contract.getProjectId());
				derivativeContractChange.setProjectName(contract.getProjectName());
				derivativeContractChange.setDeptId(contract.getDeptId());
				derivativeContractChange.setDeptName(contract.getDeptName());
				derivativeContractChange.setCreatedDate(new Date());
				if (!CommonUtil.isNullOrEmpty(contract.getContractAmountList())) {
					double amount = 0;
					for (PrjContractAmount om : contract.getContractAmountList()) {
						amount += om.getRmbAmount();
					}
					derivativeContractChange.setOnlineRmbAmount(amount);
				}
			}
			derivativeContractChange.setCreatedBy(user.getName());
			derivativeContractChange.setCreatedById(user.getPersonId());
			// if(user.getDept() != null){
			// derivativeContractChange.setCreatedBy(user.getDept().getName());
			// derivativeContractChange.setCreatedById(user.getDeptId());
			// }
		} else {
			derivativeContractChange.setIsNew(false);
			derivativeContractChange.setModifiedById(user.getName());
			derivativeContractChange.setModifiedBy(user.getPersonId());
		}

		return derivativeContractChange;
	}

	// 变更单审批查看
	public DerivativeContractChange initOrder(String changeId) {
		return derivativeContractChangeDao.get(changeId);
	}

	// 用印审批查看
	public DerivativeSeal initSeal(String sealId) {
		return derivativeSealDao.get(sealId);
	}

	// 用印审批查看
	public DerivativeSeal getContractSealByChangeId(String changeId) {
		return derivativeSealDao.getSealByChangeId(changeId);
	}

	// 合同导航查看
	public DerivativeConChange getContractOrderByChangeId(String changeId) {
		return derivativeConChangeDao.getOrderByChangeId(changeId);
	}

	// 合同审批查看
	public DerivativeConChange initContract(String chId) {
		return derivativeConChangeDao.get(chId);
	}

	public DerivativeSeal initSealPage(String changeId, UserProfile user) {
		DerivativeSeal derivativeSeal = null;
		if (StringUtils.isNotBlank(changeId)) {
			derivativeSeal = derivativeSealDao.getSealByChangeId(changeId);
			if (derivativeSeal != null) {
				derivativeSeal.setModifiedById(user.getName());
				derivativeSeal.setModifiedBy(user.getPersonId());
			} else {
				DerivativeContractChange change = derivativeContractChangeDao.get(changeId);
				int chType = change.getChType();
				if (change != null) {
					derivativeSeal = new DerivativeSeal();
					derivativeSeal.setSealId(UUIDHex.newId());
					derivativeSeal.setProjectId(change.getProjectId());
					derivativeSeal.setProjectName(change.getProjectName());
					derivativeSeal.setContractId(change.getContractId());
					derivativeSeal.setContractNo(change.getContractNo());
					derivativeSeal.setContractName(change.getContractName());
					derivativeSeal.setDeptId(change.getDeptId());
					derivativeSeal.setDeptName(change.getDeptName());
					derivativeSeal.setCreatedDate(new Date());
					//用印初始化生成协议编号//生成新的合同编号
					if(chType == 1) {
					String businessNo;
					String NoHead = change.getContractNo();
					String Con = "e";
					String NoCon = NoHead+Con;
					Integer count = this.derivativeConChangeDao.getBusinessNoIndex(NoCon);
					String NoEnd = String.format("%03d", count);
					businessNo = NoCon + NoEnd;
					derivativeSeal.setBusinessNo(businessNo);
					}else if(chType == 2){
						int hasProtocol = change.getHasProtocol();
						if (hasProtocol == 1) {
							String businessNo;
							String NoHead = change.getContractNo();
							String Con = "e";
							String NoCon = NoHead + Con;
							Integer count = this.derivativeConChangeDao.getBusinessNoIndex(NoCon);
							String NoEnd = String.format("%03d", count);
							businessNo = NoCon + NoEnd;
							derivativeSeal.setBusinessNo(businessNo);
						}
					}
				}
				derivativeSeal.setChangeId(changeId);
				derivativeSeal.setSwfStatus(ContractSeal.DRAFT);
				derivativeSeal.setIsNew(true);
				derivativeSeal.setCreatedBy(user.getName());
				derivativeSeal.setCreatedById(user.getPersonId());
				// if(user.getDept() != null){
				// derivativeSeal.setCreatedBy(user.getDept().getName());
				// derivativeSeal.setCreatedById(user.getDeptId());
				// }
			}
		}
		return derivativeSeal;
	}

	public DerivativeSeal saveChangeSeal(UserProfile user, DerivativeSeal derivativeSeal) {
		if (user != null) {
			derivativeSeal.setModifiedBy(user.getName());
			derivativeSeal.setModifiedById(user.getPersonId());
		}
		if (derivativeSeal.getIsNew()) {
			if (user != null) {
				derivativeSeal.setCreatedBy(user.getName());
				derivativeSeal.setCreatedById(user.getPersonId());
				Dept dept = user.getDept();
				if (dept != null) {
					derivativeSeal.setDeptId(dept.getDeptId());
					derivativeSeal.setDeptName(dept.getName());
				}
			}
			derivativeSeal.setCreatedDate(new Date());
			derivativeSeal.setIsNew(false);
			derivativeSealDao.save(derivativeSeal);
		} else { // 设置更新时间
			derivativeSeal.setModifiedDate(new Date());
			derivativeSealDao.update(derivativeSeal);
		}
		return derivativeSeal;
	}

	/**
	 * 衍生合同上线审批完成后，调整预算项目预算的差值。
	 * 
	 * @param derivativeConChange
	 */
	public void adjustPrjBenefitBudget(DerivativeConChange derivativeConChange) {
		// 需要调整的预算金额
		double adjustPrjBenfiBudgetAmount = getAdjustPrjBenfiBudgetAmount(derivativeConChange);
		// adjustPrjBenfiBudgetAmount 不等于零才需要调整金额.
		if (adjustPrjBenfiBudgetAmount != 0) {
			// 项目ID
			String projectId = derivativeConChange.getProjectId();
			// 衍生合同ID
			String derivativeContractId = derivativeConChange.getContractId();
			DerivativeContractOnline derivativeContractOnline = derivativeContractOnlineService
					.get(derivativeContractId);
			// 预算ID(衍生合同上线时的付款类型即为预算ID)
			String budgetId = derivativeContractOnline.getPaymentType();
			// 在途金额转执行
			Person person = EIPService.getEmpService().getEmp(derivativeConChange.getCreatedById());
			Account account = EIPService.getAccountService().getDefaultAccount(person);
			UserProfile user = EIPService.getLogonService().getUserProfile(account);
			// 注意衍生合同变更申请时如果金额时正数要锁在途。
			if (adjustPrjBenfiBudgetAmount > 0) {
				// 转执行
				this.transferToExecute(user, projectId, budgetId, adjustPrjBenfiBudgetAmount);
			} else {
				// 负数时取绝对值.
				double adjustPrjBenfiBudgetAmount_ABS = Math.abs(adjustPrjBenfiBudgetAmount);
				// 扣减执行值（注意：如何合同变更额度调减不需要锁预算，合同上线完成后，直接扣减执行预算并增加可用预算即可）
				this.removeExecuteBudgetAmount(user, projectId, budgetId, adjustPrjBenfiBudgetAmount_ABS);
			}
		}
	}

	/**
	 * 衍生合同上线完成金额转执行
	 *
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param amount
	 */
	public synchronized void transferToExecute(UserProfile user, String projectId, String budgetId, double amount) {
		TpcBudgetUtil.transferToExecute(TpcBudgetUtil.TPC_DERIVATIVE_CONTRACT_ONLINE, user, projectId, budgetId,
				amount);
	}

	/**
	 * 衍生合同上线完成后扣减执行预算。
	 *
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param amount
	 */
	public synchronized void removeExecuteBudgetAmount(UserProfile user, String projectId, String budgetId,
			double amount) {
		TpcBudgetUtil.removeExecuteBudgetAmount(TpcBudgetUtil.TPC_DERIVATIVE_CONTRACT_CHANGE_ONLINE, user, projectId,
				budgetId, amount);
	}

	/**
	 * 本次变更需要调整预算的差额。 如果返回金额为正数：解锁途金额，转执行。 如何返回金额为负数：直接扣减预算执行数值。
	 * 
	 * @param derivativeConChange
	 * @return
	 */
	public double getAdjustPrjBenfiBudgetAmount(DerivativeConChange derivativeConChange) {
		String contractId = derivativeConChange.getContractId();
		PrjContractTable prjContractTable = prjContractTableService.get(contractId);
		// 衍生合同折合人民币金额。
		double prjContractTableRmbAmount = prjContractTable.getContractRmbAmount();
		// 衍生合同变更后折合人民币金额
		double derivativeConChangeRbmAmount = derivativeConChange.getContractRmbAmount();
		// 如果两个金额不相等代表合同金额发生的变更，则需要调整预算
		if (prjContractTableRmbAmount != derivativeConChangeRbmAmount) {
			return derivativeConChangeRbmAmount - prjContractTableRmbAmount;
		} else {
			return 0;
		}
	}

	// 流程结束操作
	public void change(UserProfile user, DerivativeConChange contractChOrder) {
		// 变更合同主键chId
		String chId = contractChOrder.getChId();
		// 覆盖原合同online的主键是ContractId
		String contractId = contractChOrder.getContractId();
		PrjContractTable prjContractTable = prjContractTableService.get(contractId);
		// 复制合同上线信息至合同正式表
		try {
			//创建印花税
			this.contractStampTaxAmountService.completedDerivativeChange(contractChOrder, prjContractTable);
			
			BeanUtils.copyProperties(prjContractTable, contractChOrder);
			this.prjContractTableService.update(prjContractTable);
			// 金额
			// 原合同old
			List<PrjContractAmount> oldAmountList = prjContractAmountDao.findBy("contractId", contractId);
			// 变更合同new
			List<DerivativeContractChangeAmount> newAmountList = changeAmountDao.findBy("chId", chId);
			int newAmountSize = newAmountList.size();
			for (int i = 0; i < newAmountSize; i++) {
				String[] ignores = { "amountId", "chId" };
				DerivativeContractChangeAmount newContent = newAmountList.get(i);
				PrjContractAmount oldContent = findByRecId(oldAmountList, newContent.getAmountOldId());
				if (oldContent != null) {
					// 更新
					com.supporter.prj.pm.util.BeanUtils.copyProperties(newContent, oldContent, ignores);
					prjContractAmountDao.update(oldContent);
					// 从原表中删除
					oldAmountList.remove(oldContent);
				} else {
					// 新增
					oldContent = new PrjContractAmount();
					com.supporter.prj.pm.util.BeanUtils.copyProperties(newContent, oldContent, ignores);
					oldContent.setAmountId(null);
					oldContent.setContractId(contractId);
					prjContractAmountDao.save(oldContent);
				}
			}
			if (oldAmountList.size() > 0) {
				prjContractAmountDao.delete(oldAmountList);
			}
			// terms表增删改
			// 原合同old
			List<PrjContractCollectionTerms> oldTermsList = prjContractCollectionTermsDao.findBy("contractId",
					contractId);
			// 变更合同new
			List<DerivativeContractChangeTerms> newTermsList = changeTermsDao.findBy("chId", chId);
			int newSize = newTermsList.size();
			for (int i = 0; i < newSize; i++) {
				String[] ignores = { "termsId", "chId" };
				DerivativeContractChangeTerms newTerms = newTermsList.get(i);
				PrjContractCollectionTerms oldTerms = findBySiteId(oldTermsList, newTerms.getTermsId());
				if (oldTerms != null) {
					// 更新
					com.supporter.prj.pm.util.BeanUtils.copyProperties(newTerms, oldTerms, ignores);
					prjContractCollectionTermsDao.update(oldTerms);
					// 从原表中删除
					oldTermsList.remove(oldTerms);
				} else {
					// 新增
					oldTerms = new PrjContractCollectionTerms();
					com.supporter.prj.pm.util.BeanUtils.copyProperties(newTerms, oldTerms, ignores);
					oldTerms.setContractId(contractId);
					prjContractCollectionTermsDao.save(oldTerms);
				}

			}
			if (oldTermsList.size() > 0) {
				prjContractCollectionTermsDao.delete(oldTermsList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 流程提交后调用(衍生合同调整单及变更单提交)
	 * 
	 * @param user
	 * @param DerivativeContractChange
	 */
	public synchronized void WsfSubmitAferExcuteByDerivativeContractChange(UserProfile user,
			DerivativeContractChange derivativeContractChange) {
		String changeTypeId = derivativeContractChange.getChangeTypeId();
		String projectId = derivativeContractChange.getProjectId();
		// 衍生合同ID
		String derivativeContractId = derivativeContractChange.getContractId();
		DerivativeContractOnline derivativeContractOnline = derivativeContractOnlineService.get(derivativeContractId);
		// 预算ID(衍生合同上线时的付款类型即为预算ID)
		String budgetId = derivativeContractOnline.getPaymentType();
		if (changeTypeId.endsWith("ADD")) {
			double amount = derivativeContractChange.getChangeAmount();
			this.addOnwayBudgetAmount(user, projectId, budgetId, amount);
		} else {
			// 如果变更金额不变或是减额不用处理
		}
	}

	/**
	 * 流程中止后调用(衍生合同调整单及变更单流程中止调用)
	 * 
	 * @param user
	 * @param DerivativeContractChange
	 */
	public synchronized void WsfAbortAferExcuteByDerivativeContractChange(
			DerivativeContractChange derivativeContractChange) {
		// 增减类型
		String changeTypeId = derivativeContractChange.getChangeTypeId();
		String projectId = derivativeContractChange.getProjectId();
		// 衍生合同ID
		String derivativeContractId = derivativeContractChange.getContractId();
		DerivativeContractOnline derivativeContractOnline = derivativeContractOnlineService.get(derivativeContractId);
		// 预算ID(衍生合同上线时的付款类型即为预算ID)
		String budgetId = derivativeContractOnline.getPaymentType();
		if (changeTypeId.endsWith("ADD")) {
			double amount = derivativeContractChange.getChangeAmount();
			Person person = EIPService.getEmpService().getEmp(derivativeContractChange.getCreatedById());
			Account account = EIPService.getAccountService().getDefaultAccount(person);
			UserProfile user = EIPService.getLogonService().getUserProfile(account);
			this.removeOnwayBudgetAmount(user, projectId, budgetId, amount);
		} else {
			// 如果变更金额不变或是减额不用处理
		}
	}

	/**
	 * 检查预算可用金额
	 * 
	 * @param derivativeContractChange
	 * @return true 有可用预算，false 无可用预算
	 */
	public synchronized String checkBudgetAvailableAmount(DerivativeContractChange derivativeContractChange) {
		// 增减类型
		String changeTypeId = derivativeContractChange.getChangeTypeId();
		if (changeTypeId.endsWith("ADD")) {
			String projectId = derivativeContractChange.getProjectId();
			// 增加金额
			double changeAmount = derivativeContractChange.getChangeAmount();
			// 衍生合同ID
			String derivativeContractId = derivativeContractChange.getContractId();
			DerivativeContractOnline derivativeContractOnline = derivativeContractOnlineService
					.get(derivativeContractId);
			// 预算ID(衍生合同上线时的付款类型即为预算ID)
			String budgetId = derivativeContractOnline.getPaymentType();
			// 可用预算
			Double budgetAvailableAmoun = TpcBudgetUtil.getBudgetAvailableAmount(projectId, budgetId);
			// 可用预算大于增加金额
			if (budgetAvailableAmoun > changeAmount) {
				return "true";
			} else {
				return budgetAvailableAmoun.toString();
			}
		} else {
			return "true";
		}
	}

	/**
	 * 添加在途金额(提交调整单或时提交变更后执行)
	 *
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param amount
	 */
	public synchronized void addOnwayBudgetAmount(UserProfile user, String projectId, String budgetId, double amount) {
		TpcBudgetUtil.addOnwayBudgetAmount(TpcBudgetUtil.TPC_DERIVATIVE_CONTRACT_CHANGE, user, projectId, budgetId,
				amount);
	}

	/**
	 * 解锁在途金额(提交调整单或时提交变更后执行)
	 *
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param amount
	 */
	public synchronized void removeOnwayBudgetAmount(UserProfile user, String projectId, String budgetId,
			double amount) {
		TpcBudgetUtil.removeOnwayBudgetAmount(TpcBudgetUtil.TPC_DERIVATIVE_CONTRACT_CHANGE, user, projectId, budgetId,
				amount);
	}

	private PrjContractCollectionTerms findBySiteId(List<PrjContractCollectionTerms> oldTermsList, String termsOldId) {
		if (StringUtils.isNotBlank(termsOldId)) {
			int len = oldTermsList.size();
			for (int i = 0; i < len; i++) {
				PrjContractCollectionTerms oldSite = oldTermsList.get(i);
				if (termsOldId.equals(oldSite.getTermsId())) {
					return oldSite;
				}
			}
		}
		return null;
	}

	private PrjContractAmount findByRecId(List<PrjContractAmount> oldAmountList, String amountOldId) {
		if (StringUtils.isNotBlank(amountOldId)) {
			int len = oldAmountList.size();
			for (int i = 0; i < len; i++) {
				PrjContractAmount oldContent = oldAmountList.get(i);
				if (amountOldId.equals(oldContent.getAmountId())) {
					return oldContent;
				}
			}
		}
		return null;
	}

	public String checkOrderChange(String contractId) {
		return this.derivativeContractChangeDao.checkOrderChange(contractId);
	}

	public boolean checkContractChOrder(String changeId) {
		return this.derivativeConChangeDao.checkContractChOrder(changeId);
	}

	public DerivativeSeal saveStamp(String sealId, String stampPersonId, String stampPerson, Date stampDate) {
		// TODO Auto-generated method stub
		DerivativeSeal derivativeSeal = (DerivativeSeal) this.derivativeSealDao.get(sealId);
		derivativeSeal.setStampDate(stampDate);
		derivativeSeal.setStampPerson(stampPerson);
		derivativeSeal.setStampPersonId(stampPersonId);
		this.derivativeSealDao.update(derivativeSeal);
		return derivativeSeal;
	}

	public DerivativeContractChange commit(UserProfile user, DerivativeContractChange contractChange) {
	
		DerivativeContractChange ret = null;
		boolean isNew = contractChange.getIsNew();
		if (isNew) {
			contractChange.setSwfStatus(20);
			derivativeContractChangeDao.save(contractChange);
			ret = contractChange;
		} else {
			contractChange.setSwfStatus(20);
			derivativeContractChangeDao.update(contractChange);
			ret = contractChange;
		}
		return ret;
	}

	public void callPaper(String chId) {
		if (StringUtils.isNotBlank(chId)) {
			DerivativeConChange contractChOrder = derivativeConChangeDao.get(chId);	
			if(contractChOrder != null && StringUtils.isNotBlank(contractChOrder.getCreatedById())) {
				Message messageCreat =new Todo();
				messageCreat.setPersonId(contractChOrder.getCreatedById());
				messageCreat.setEventTitle("请及时送达纸质版合同：" + contractChOrder.getContractName());
				messageCreat.setNotifyTime(new Date());
				messageCreat.setWebPageURL("/tpc/derivative_contract_change/contract_callPaperView.html?chId="
						+ CommonUtil.trim(chId));
				messageCreat.setMessageType(ITodo.MsgType.CC);
				messageCreat.setRelatedRecordTable(EIPService.getWebappService().getWebappName());
				EIPService.getBMSService().addMessage(messageCreat);
			}
		}else {
			System.out.println("DerivativeConChange 未找到经办人！");
		}
	}

	public boolean saveDetail(DerivativeConChange order) {
			List<DerivativeContractChangeAmount> contractAmountList = order.getContractAmountList();
			// 保存或更新合同额
			if(CollectionUtils.isNotEmpty(contractAmountList)){
				for (DerivativeContractChangeAmount amount : contractAmountList) {
					amount.setCurrency(TpcConstant.getCommonCurrencyMap().get(amount.getCurrencyId()));
					if (StringUtils.isBlank(amount.getAmountId()) || amount.getAdd()) {
						this.changeAmountDao.save(amount);
					} else {
						this.changeAmountDao.update(amount);
					}
				}
				return true;
			}
			return false;
		}

	//判断衍生合同付款条款能不能被删除
	public String canDelTent(String termsId) {
		//通过termsId获取变更付款内容的termsOldId
		DerivativeContractChangeTerms termsC = changeTermsDao.get(termsId);
		String termsOldId = termsC.getTermsOldId();
		//通过termsOldId获取付款条款列表
		List<PrjContractSettlementRec> materialContentList = prjContractSettlementRecDao.findBy("paymentTermsId",termsOldId);
		if(materialContentList != null) {
			for(PrjContractSettlementRec maList : materialContentList) {
				if(maList.getPaymentTermsId().equals(termsOldId)) {
					return "materError";
				}
			}
		}
		return "success";
	}
}
