package com.supporter.prj.cneec.tpc.order_change.service;

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

import com.supporter.prj.cneec.tpc.collection_confirmation.dao.CollectionDetailDao;
import com.supporter.prj.cneec.tpc.collection_confirmation.entity.CollectionDetail;
import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChange;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractSeal;
import com.supporter.prj.cneec.tpc.contract_stamp_tax_amount.service.ContractStampTaxAmountService;
import com.supporter.prj.cneec.tpc.order_change.dao.ContractChOrderDao;
import com.supporter.prj.cneec.tpc.order_change.dao.OrderChangeCollectionTermsDao;
import com.supporter.prj.cneec.tpc.order_change.dao.OrderChangeContractAmountDao;
import com.supporter.prj.cneec.tpc.order_change.dao.OrderChangeDao;
import com.supporter.prj.cneec.tpc.order_change.dao.OrderChangeGoodsChildDao;
import com.supporter.prj.cneec.tpc.order_change.dao.OrderChangeGoodsDao;
import com.supporter.prj.cneec.tpc.order_change.dao.OrderSealDao;
import com.supporter.prj.cneec.tpc.order_change.entity.ContractChOrder;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderChange;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderChangeCollectionTerms;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderChangeContractAmount;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderChangeGoods;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderChangeGoodsChild;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderSeal;
import com.supporter.prj.cneec.tpc.order_change.util.OrderChangeConstant;
import com.supporter.prj.cneec.tpc.prj_contract_table.dao.PrjContractAmountDao;
import com.supporter.prj.cneec.tpc.prj_contract_table.dao.PrjContractCollectionTermsDao;
import com.supporter.prj.cneec.tpc.prj_contract_table.dao.PrjContractGoodsDao;
import com.supporter.prj.cneec.tpc.prj_contract_table.dao.PrjContractTableDao;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractAmount;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractCollectionTerms;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractGoods;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractTable;
import com.supporter.prj.cneec.tpc.prj_contract_table.service.PrjContractTableService;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileManageService;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.eip.todo.entity.Todo;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * @Title: OrderChangeService
 * @Description: 业务操作类
 */
@Service
@Transactional(TransManager.APP)
public class OrderChangeService {

	@Autowired
	private OrderChangeDao orderChangeDao;
	@Autowired
	private ContractChOrderDao contractChOrderDao;
	@Autowired
	private OrderChangeContractAmountDao amountDao;
	@Autowired
	private OrderChangeGoodsDao goodsDao;
	@Autowired
	private OrderChangeGoodsChildDao goodsChildDao;
	@Autowired
	private OrderChangeCollectionTermsDao termsDao;
	@Autowired
	private OrderSealDao orderSealDao;
	@Autowired
	private PrjContractTableDao prjContractTableDao;
	@Autowired
	private PrjContractAmountDao prjContractAmountDao;
	@Autowired
	private PrjContractGoodsDao prjContractGoodsDao;
	@Autowired
	private CollectionDetailDao collectionDetailDao;
	@Autowired
	private PrjContractCollectionTermsDao prjContractCollectionTermsDao;
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
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, OrderChange.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param orderChange
	 */
	public void getAuthCanExecute(UserProfile userProfile, OrderChange orderChange) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, OrderChange.MODULE_ID, orderChange.getChangeId(), orderChange);
	}

	/**
	 * 获取销售合同变更对象集合
	 * @param user
	 * @param jqGrid
	 * @param orderChange
	 * @return
	 */
	public List<OrderChange> getGrid(UserProfile user, JqGrid jqGrid, OrderChange orderChange,String contractId) {
		String authFilter = getAuthFilter(user);
		return this.orderChangeDao.findPage(jqGrid, orderChange, contractId,authFilter);
	}

	/**
	 * 获取合同额集合
	 * @param user
	 * @param jqGrid
	 * @param changeId
	 * @return
	 */
	public List<OrderChangeContractAmount> getContractAmountGrid(UserProfile user, JqGrid jqGrid, String chId) {
		return this.amountDao.findPage(jqGrid, chId);
	}

	/**
	 * 获取货物及服务明细集合
	 * @param user
	 * @param jqGrid
	 * @param changeId
	 * @return
	 */
	public List<OrderChangeGoods> getGoodsGrid(UserProfile user, JqGrid jqGrid, String chId) {
		return this.goodsDao.findPage(jqGrid, chId);
	}

	/**
	 * 获取货物及服务明细集合
	 * @param user
	 * @param jqGrid
	 * @param changeId
	 * @return
	 */
	public List<OrderChangeGoodsChild> getGoodsChildGrid(UserProfile user, JqGrid jqGrid, String goodsId) {
		return this.goodsChildDao.findPage(jqGrid, goodsId);
	}
	
	public List<OrderChangeGoods> getGoodsGridSingle(UserProfile user, JqGrid jqGrid, String goodsId) {
		return this.goodsDao.getGoodsGridSingle(jqGrid, goodsId);
	}
	/**
	 * 获取收款条件集合
	 * @param user
	 * @param jqGrid
	 * @param changeId
	 * @return
	 */
	public List<OrderChangeCollectionTerms> getCollectionTermsGrid(UserProfile user, JqGrid jqGrid, String chId) {
		return this.termsDao.findPage(jqGrid, chId);
	}

	

	/**
	 * 获取单个销售合同变更对象
	 * @param changeId
	 * @return
	 */
	public OrderChange get(String changeId) {
		return this.orderChangeDao.get(changeId);
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param changeId
	 * @param user
	 * @return
	 */
	public ContractChOrder initEditOrViewPage(String changeId, UserProfile user) {
		ContractChOrder contractChOrder = null;
		if (StringUtils.isNotBlank(changeId)) {
			contractChOrder = contractChOrderDao.getChByChangeId(changeId);
			if (contractChOrder != null) {
				contractChOrder.setModifiedById(user.getName());
				contractChOrder.setModifiedBy(user.getPersonId());
			} else {
				OrderChange change  = orderChangeDao.get(changeId);
				//通过changeId获取contractId   
				String contractId =change.getContractId();
				//通过contractId  获取原合同  复制到contractOrder中
				PrjContractTable contract = this.prjContractTableDao.get(contractId);
				if(contract == null) {
					return null;
				}
				try {
					contractChOrder = new ContractChOrder();
					BeanUtils.copyProperties(contractChOrder, contract);
					//复制主表信息，并保存
					contractChOrder.setChId(com.supporter.util.UUIDHex.newId());
					contractChOrder.setChangeId(changeId);
//					contractOrder.setContractId(contractId);
//					contractOrder.setContractNo(contract.getContractNo());
					contractChOrder.setSwfStatus(ContractChOrder.DRAFT);
					contractChOrder.setContractRmbAmount(contract.getContractRmbAmount());
//					contractOrder.setProjectId(contract.getProjectId());
//					contractOrder.setProjectNo(contract.getProjectNo());
//					contractOrder.setProjectName(contract.getProjectName());
//					contractOrder.setContractId(contract.getContractId());
//					contractOrder.setContractNo(contract.getContractNo());
//					contractOrder.setContractName(contract.getContractName());
//					contractOrder.setDeptId(contract.getDeptId());
//					contractOrder.setDeptName(contract.getDeptName());
					
//					contractOrder.setProcId(null);
//					contractOrder.setSigningDate(contract.getSigningDate());
//					contractOrder.setEffectiveDate(contract.getEffectiveDate());
//					contractOrder.setExpirationDate(contract.getExpirationDate());
					contractChOrder.setCreatedById(user.getPersonId());
					contractChOrder.setCreatedBy(user.getName());
					contractChOrder.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd"));
					contractChOrder.setTelephone(change.getTelephone());
					contractChOrder.setBusinessNo(change.getBusinessNo());
					//保存主表
					contractChOrderDao.save(contractChOrder);
					// 设置合同额
		            List<PrjContractAmount> amountList = prjContractAmountDao.getByContractId(contractId);
		            if(amountList != null) {
						for(PrjContractAmount amount : amountList) {
							if (amount == null) {
								continue;
							}
							OrderChangeContractAmount changeAmount = new OrderChangeContractAmount();
							BeanUtils.copyProperties(changeAmount, amount);
							changeAmount.setAmountId(com.supporter.util.UUIDHex.newId());
							changeAmount.setAmountOldId(amount.getAmountId());
							changeAmount.setChId(contractChOrder.getChId());
							//保存
							amountDao.save(changeAmount);
						}
					}
					// 设置货物及服务明细
		            List<PrjContractGoods> goodsList = prjContractGoodsDao.getByContractId(contractId);
		            if(goodsList != null) {
						for(PrjContractGoods goods : goodsList) {
							if (goods == null) {
								continue;
							}
							OrderChangeGoods changeGoods = new OrderChangeGoods();
							BeanUtils.copyProperties(changeGoods, goods);
							changeGoods.setGoodsId(com.supporter.util.UUIDHex.newId());
							changeGoods.setGoodsOldId(goods.getGoodsId());
							changeGoods.setChId(contractChOrder.getChId());
							//保存
							goodsDao.save(changeGoods);
						}
					}
					// 设置收款条件
		            List<PrjContractCollectionTerms> termsList = prjContractCollectionTermsDao.getByContractId(contractId);
		            if(termsList != null) {
						for(PrjContractCollectionTerms terms : termsList) {
							if (terms == null) {
								continue;
							}
							OrderChangeCollectionTerms changeterms = new OrderChangeCollectionTerms();
							BeanUtils.copyProperties(changeterms, terms);
							changeterms.setTermsId(com.supporter.util.UUIDHex.newId());
							changeterms.setTermsOldId(terms.getTermsId());
							changeterms.setChId(contractChOrder.getChId());
							//保存
							termsDao.save(changeterms);
						}
					}
					//复制附件信息
					File(PrjContractTable.MODULE_ID,"file",contract.getContractId(),"",contractChOrder.getChId(),"","file",user);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return contractChOrder;
			}
		}
		return contractChOrder;
	}
	
	/**
	 * 同步附件
	 * @param moduleName
	 * @param busiType
	 * @param oneLevelId
	 * @param twoLevelId
	 * @param newOneId
	 * @param newTwoId
	 * @param newType
	 * @param user
	 */
	public void File(String moduleName,String busiType,String oneLevelId,String twoLevelId,String newOneId,String newTwoId,String newType,UserProfile user) {
		List<FileUpload> list =  fileUploadService.getList(moduleName, busiType, oneLevelId, twoLevelId);
		if(list!=null&&list.size()>0) {
			for (FileUpload fileUpload : list) {
				if (fileUpload == null) {
					continue;
				}
				FileUpload file = new FileUpload();
				try {
					String newFileId = com.supporter.util.UUIDHex.newId();
					BeanUtils.copyProperties(file, fileUpload);
					file.setModuleName(ContractChOrder.MODULE_ID);
					file.setCreatedDate(fileUpload.getCreatedDate());
					file.setFileId(newFileId);
					file.setOneLevelId(newOneId);
					file.setTwoLevelId(newTwoId);
					file.setBusiType(newType);
					file.setFileSize(fileUpload.getFileSize());
					fileUploadService.save(file);
					String pathOld = fileManageService.getStoragePath(fileUpload.getTenantNo(), fileUpload.getModuleName(), fileUpload.getBusiType(), fileUpload.getCreatedDate());
		            String root = pathOld + File.separator;
					File sourceFile = new File(root, fileUpload.getFileId());
					
					String path = fileManageService.getStoragePath(file.getTenantNo(), file.getModuleName(), file.getBusiType(), file.getCreatedDate());
					// 先创建文件夹
					File fileM = new File(path);
					fileM.mkdirs();
					// 然后复制
					File newFile = new File(path, newFileId);
		            
		            //读文件内容到新文件
		            this.copy(sourceFile, newFile);
		            
//					fileUploadService.saveFile(newFile, moduleName, newType, user, file.getFileId());
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
	 * 保存或修改对象
	 * @param user
	 * @param ContractChOrder
	 * @param valueMap
	 * @return
	 */
	public ContractChOrder saveOrUpdate(UserProfile user, ContractChOrder contractChOrder, Map<String, Object> valueMap) {
		if (contractChOrder.getAdd()) {
			// 装配基础信息
			loadContractChOrder(contractChOrder, user);
			// 设置流水号
//			contractChOrder.setSerialNumber(generatorSerialNumber());
			this.contractChOrderDao.save(contractChOrder);
		} else {
			// 设置更新时间
			contractChOrder.setModifiedBy(user.getName());
			contractChOrder.setModifiedById(user.getPersonId());
			contractChOrder.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.contractChOrderDao.update(contractChOrder);
		
		// 保存或更新合同额
		List<OrderChangeContractAmount> contractAmountList = contractChOrder.getContractAmountList();
		if(CollectionUtils.isNotEmpty(contractAmountList)){
			saveOrUpdateContractAmountList(contractChOrder.getChId(), contractAmountList);
		}
		// 删除合同额
		deleteContractAmount(contractChOrder.getDelAmountIds());
		// 保存或更新货物及服务明细
		List<OrderChangeGoods> goodsList = contractChOrder.getGoodsList();
		if(CollectionUtils.isNotEmpty(goodsList)){
			saveOrUpdateGoodsList(contractChOrder.getChId(), goodsList);
		}
		// 删除货物及服务明细
		deleteGoods(contractChOrder.getDelGoodsIds());
		// 保存或更新收款条件
		List<OrderChangeCollectionTerms> collectionTermsList = contractChOrder.getCollectionTermsList();
		if(CollectionUtils.isNotEmpty(collectionTermsList)){
			saveOrUpdateCollectionTermsList(contractChOrder.getChId(), collectionTermsList);
		}
		// 删除收款条件
		deleteCollectionTerms(contractChOrder.getDelTermsIds());
		}
		return contractChOrder;
	}
	/**
	 * 新建销售合同变更对象
	 * @param user
	 * @return
	 */
	public ContractChOrder newContractChOrder(UserProfile user) {
		ContractChOrder contractChOrder = new ContractChOrder();
		loadContractChOrder(contractChOrder, user);
		contractChOrder.setSwfStatus(OrderChange.DRAFT);
		return contractChOrder;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public ContractChOrder loadContractChOrder(ContractChOrder contractChOrder, UserProfile user) {
		contractChOrder.setCreatedBy(user.getName());
		contractChOrder.setCreatedById(user.getPersonId());
		contractChOrder.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		contractChOrder.setModifiedBy(user.getName());
		contractChOrder.setModifiedById(user.getPersonId());
		contractChOrder.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			contractChOrder.setDeptName(dept.getName());
			contractChOrder.setDeptId(dept.getDeptId());
		}
		// 设置状态
		contractChOrder.setSwfStatus(ContractChOrder.DRAFT);
		return contractChOrder;
	}

	/**
	 * 删除合同额
	 * @param delAmountIds
	 */
	public void deleteContractAmount(String delAmountIds) {
		if (StringUtils.isNotBlank(delAmountIds)) {
			for (String amountId : delAmountIds.split(",")) {
				// 根据ID字段删除（删除主键方法有问题）
				this.amountDao.deleteByProperty("amountId", amountId);
			}
		}
	}

	/**
	 * 保存或更新合同额
	 * @param changeId
	 * @param contractAmountList
	 */
	public void saveOrUpdateContractAmountList(String chId, List<OrderChangeContractAmount> contractAmountList) {
		for (OrderChangeContractAmount amount : contractAmountList) {
			amount.setChId(chId);
			amount.setCurrency(TpcConstant.getCommonCurrencyMap().get(amount.getCurrencyId()));
			if (StringUtils.isBlank(amount.getAmountId()) || amount.getAdd()) {
				this.amountDao.save(amount);
			} else {
				this.amountDao.update(amount);
			}
		}
	}
	
	/**
	 * 删除货物及服务明细
	 * @param delGoodsIds
	 */
	public void deleteGoods(String delGoodsIds) {
		if (StringUtils.isNotBlank(delGoodsIds)) {
			for (String goodsId : delGoodsIds.split(",")) {
				this.goodsDao.deleteByProperty("goodsId", goodsId);
			}
		}
	}

	/**
	 * 保存或更新货物及服务明细
	 * @param changeId
	 * @param goodsList
	 */
	public void saveOrUpdateGoodsList(String chId, List<OrderChangeGoods> goodsList) {
		for (OrderChangeGoods goods : goodsList) {
			goods.setChId(chId);
			goods.setCurrency(TpcConstant.getCommonCurrencyMap().get(goods.getCurrencyId()));
			if (StringUtils.isBlank(goods.getGoodsId()) || goods.getAdd()) {
				this.goodsDao.save(goods);
			} else {
				this.goodsDao.update(goods);
			}
		}
	}

	/**
	 * 删除收款条件
	 * @param delTermsIds
	 */
	public void deleteCollectionTerms(String delTermsIds) {
		if (StringUtils.isNotBlank(delTermsIds)) {
			for (String termsId : delTermsIds.split(",")) {
				this.termsDao.deleteByProperty("termsId", termsId);
			}
		}
	}

	/**
	 * 保存或更新收款条件
	 * @param chId
	 * @param collectionTermsList
	 */
	public void saveOrUpdateCollectionTermsList(String chId, List<OrderChangeCollectionTerms> collectionTermsList) {
		for (OrderChangeCollectionTerms terms : collectionTermsList) {
			terms.setChId(chId);
			if (StringUtils.isBlank(terms.getTermsId()) || terms.getAdd()) {
				this.termsDao.save(terms);
			} else {
				this.termsDao.update(terms);
			}
		}
	}

	/**
	 * 保存提交
	 * @param user
	 * @param orderChange
	 * @param valueMap
	 * @return
	 */
//	public OrderChange commit(UserProfile user, OrderChange orderChange, Map<String, Object> valueMap) {
//		saveOrUpdate(user, orderChange, valueMap);
//		// 记录日志
//		String logMessage = MessageFormat.format(LogConstant.PUBLISH_LOG_MESSAGE_ORDERCHANGE, "(" + orderChange.getOrderNo() + ")" + orderChange.getOrderName());
//		EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS_ORDERCHANGE).info(user, LogConstant.PUBLISH_LOG_ACTION_ORDERCHANGE, logMessage, orderChange, null);
//		return orderChange;
//	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param changeIds
	 */
	public void delete(UserProfile user, String changeIds) {
		OrderChange orderChange = orderChangeDao.get(changeIds);
		orderChangeDao.delete(orderChange);
	}
//	public void delete(UserProfile user, String changeIds) {
//		if (StringUtils.isNotBlank(changeIds)) {
//			OrderChange orderChange;
//			for (String changeId : changeIds.split(",")) {
//				orderChange = this.get(changeId);
//				if (orderChange == null) continue;
//				// 权限验证
//				this.getAuthCanExecute(user, orderChange);
				// 修改合同状态为原始状态
//				PrjContractTable contract = this.prjContractTableService.get(orderChange.getOrderId());
//				this.prjContractTableService.updateToChange(contract, false, user);
//				this.amountDao.deleteByProperty("changeId", changeId);
//				this.goodsDao.deleteByProperty("changeId", changeId);
//				this.termsDao.deleteByProperty("changeId", changeId);
//				this.orderChangeDao.delete(orderChange);
//			}
//		}
//	}

	/**
	 * 流程更新对象
	 * @param OrderChange
	 * @return
	 */
	public OrderChange update(OrderChange orderChange) {
		this.orderChangeDao.update(orderChange);
		return orderChange;
	}
	/**
	 * 流程更新对象
	 * @param Seal
	 * @return
	 */
	public OrderSeal update(OrderSeal orderSeal) {
		if (orderSeal==null){
			return orderSeal;
		}
//		orderSeal.setBusinessNo(generatorSetBusinessNo());
		this.orderSealDao.update(orderSeal);
		return orderSeal;
	}
	/**
	 * 流程更新对象
	 * @param ContractChOrder
	 * @return
	 */
	public ContractChOrder update(ContractChOrder contractChOrder) {
		if (contractChOrder==null){
			return contractChOrder;
		}
		this.contractChOrderDao.update(contractChOrder);
		return contractChOrder;
	}
	/**
	 * 生成协议编号
	 * @return
	 */
	public synchronized String generatorSetBusinessNo() {
		String businessNo;
		String NoHead = CommonUtil.format(new Date(), "yyyyMM");
		Integer count = this.orderSealDao.getBusinessNoIndex(NoHead);
		String NoEnd = String.format("%03d", count);
		businessNo = NoHead + NoEnd;
		return businessNo;
	}
	/**
	 * 生称流水号
	 * @return
	 */
//	public synchronized String generatorSerialNumber() {
//		String serialNumber;
//		String NoHead = CommonUtil.format(new Date(), "yyyyMM");
//		Integer count = this.orderChangeDao.getSerialNumberIndex(NoHead);
//		String NoEnd = String.format("%03d", count);
//		serialNumber = NoHead + NoEnd;
//		return serialNumber;
//	}

	/**
	 * 调整性质单/复选框
	 * @param projectId
	 * @return
	 */
	public List<CheckBoxVO> getAdjustmentNatureList(String changeId,String chType) {
		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
		String[] arr = new String[] {};
		if (changeId.length() > 0) {
			OrderChange orderChange = this.get(changeId);
			if (orderChange != null) {
				arr = CommonUtil.trim(orderChange.getAdjustmentNatureId()).split(",");
			}
		}
	if(StringUtils.isNotBlank(chType)) {
		Map<Integer, String> map = OrderChangeConstant.getAdjustmentNatureMap(Integer.parseInt(chType));
		for (Integer key : map.keySet()) {
			CheckBoxVO vo = new CheckBoxVO("adjustmentNatureId_" + key, "adjustmentNatureId", key.toString(), map.get(key), existByLoop(arr, key.toString()));
			for (String string : arr) {
				if(string.equals(String.valueOf(key))) {
					vo.setChecked(true);
				}
			}
			list.add(vo);
		}
	   }
		return list;
	}

	/**
	 * 是否有协议
	 * @param changeId
	 * @return
	 */
//	public List<CheckBoxVO> getHasProtocolList(String changeId) {
//		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
//		String choose = "";// 不设置默认
//		if (changeId.length() > 0) {
//			OrderChange orderChange = this.get(changeId);
//			if (orderChange != null) {
//				choose = orderChange.isHasProtocol() ? "true" : "false";
//			}
//		}
//		Map<String, String> map = OrderChangeConstant.getHasProtocolMap();
//		for (String key : map.keySet()) {
//			CheckBoxVO vo = new CheckBoxVO("hasProtocol_" + key, "hasProtocol", key, map.get(key), key.equals(choose));
//			vo.setChecked(true);
//			list.add(vo);
//		}
//		return list;
//	}

	/**
	 * 判断字符串数组是否包含特定值
	 * @param arr
	 * @param tarVal
	 * @return
	 */
	public static boolean existByLoop(String[] arr, String tarVal) {
		for (String s : arr) {
			if (s.equals(tarVal)) return true;
		}
		return false;
	}

	/** 以下是选择销售合同合同变更方法 **/

	public ListPage<OrderChange> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<OrderChange> listPage = new ListPage<OrderChange>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.orderChangeDao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}
		/**
		 * 保存或更新
		 */
	@Transactional(TransManager.APP)
	public OrderChange saveChangeOrder(UserProfile user, OrderChange orderChange) {
			if (user != null) {
				orderChange.setModifiedBy(user.getName());
				orderChange.setModifiedById(user.getPersonId());
			}
			//更新调整性质
			Map<Integer, String> map = OrderChangeConstant.getAdjustmentNatureMap(orderChange.getChType());
			String adjustmentNatureIds = orderChange.getAdjustmentNatureId();
			if(StringUtils.isNotBlank(adjustmentNatureIds)) {
				String adjustmentNature = "";
				for (String adjustmentNatureId : adjustmentNatureIds.split(",")) {
					adjustmentNature += map.get(CommonUtil.parseInt(adjustmentNatureId))+",";
				}
				if(adjustmentNature.length()>0) {
					adjustmentNature = adjustmentNature.substring(0,adjustmentNature.length()-1);
					orderChange.setAdjustmentNature(adjustmentNature);
				}
			}
			double changeRate = orderChange.getChangeAmount() / orderChange.getOnlineRmbAmount();
			
			if (orderChange.getIsNew()) {
				if (user != null) {
					orderChange.setCreatedBy(user.getName());
					orderChange.setCreatedById(user.getPersonId());
					Dept dept = user.getDept();
					if (dept != null) {
						orderChange.setDeptId(dept.getDeptId());
						orderChange.setDeptName(dept.getName());
					}
				}
				orderChange.setChangeRate(changeRate);
				orderChange.setCreatedDate(new Date());
				orderChange.setIsNew(false);
				orderChangeDao.save(orderChange);
			} else { // 设置更新时间
				orderChange.setChangeRate(changeRate);
				orderChange.setModifiedDate(new Date());
				orderChangeDao.update(orderChange);
			}
			return orderChange;
	}
	/**
	 * 为编辑或查看页面初始化对象
	 */
public OrderChange initChangeOrder(String changeId, String contractId, int chType, UserProfile user) {
	OrderChange orderChange = null;
		if (StringUtils.isNotBlank(changeId)) {
			orderChange = (OrderChange) this.orderChangeDao.get(changeId);
		}
		if (orderChange == null) {
			orderChange = new OrderChange();
			if (StringUtils.isBlank(changeId)) {
				changeId = UUIDHex.newId();
			} 
			orderChange.setChangeId(changeId);
			orderChange.setChType(chType);
			orderChange.setIsNew(true);
			orderChange.setSwfStatus(ContractChange.DRAFT);
			orderChange.setIsAccusation(2);
			orderChange.setIsStop(2);
//			OrderOnline contract = orderOnlineService.get(contractId);
			PrjContractTable contract = prjContractTableService.get(contractId);
			if (contract != null) {
				orderChange.setContractId(contract.getContractId());
				orderChange.setContractName(contract.getContractName());
				orderChange.setContractNo(contract.getContractNo());
				orderChange.setProjectId(contract.getProjectId());
				orderChange.setProjectName(contract.getProjectName());
				orderChange.setDeptId(contract.getDeptId());
				orderChange.setDeptName(contract.getDeptName());
				orderChange.setCreatedDate(new Date());
				if(!CommonUtil.isNullOrEmpty(contract.getContractAmountList())) {
					double amount = 0 ;
					for (PrjContractAmount om : contract.getContractAmountList()) {
						amount += om.getRmbAmount();
					}
					orderChange.setOnlineRmbAmount(amount);
				}
			}
			orderChange.setCreatedBy(user.getName());		
			orderChange.setCreatedById(user.getPersonId());	
		} else {
			orderChange.setIsNew(false);
			orderChange.setModifiedById(user.getName());
			orderChange.setModifiedBy(user.getPersonId());
		}
		return orderChange;
	}
//变更单审批查看
	public OrderChange initOrder(String changeId) {
			return orderChangeDao.get(changeId);
	}
	//合同审批查看
	public ContractChOrder initContract(String chId) {
			return contractChOrderDao.get(chId);
		}
	//用印审批查看
	public OrderSeal initSeal(String sealId) {
			return orderSealDao.get(sealId);
	}
	//用印审批查看
	public OrderSeal getContractSealByChangeId(String changeId) {
		return orderSealDao.getSealByChangeId(changeId);
	}
	//合同导航查看
	public ContractChOrder getContractOrderByChangeId(String changeId) {
		return contractChOrderDao.getChByChangeId(changeId);
	}

	public OrderSeal initSealPage(String changeId, UserProfile user) {
		OrderSeal orderSeal = null;
		if (StringUtils.isNotBlank(changeId)) {
			orderSeal = orderSealDao.getSealByChangeId(changeId);
			if (orderSeal != null) {
				orderSeal.setModifiedById(user.getName());
				orderSeal.setModifiedBy(user.getPersonId());
			} else {
				OrderChange change  = orderChangeDao.get(changeId);
				int chType = change.getChType();
				if(change != null) {
					orderSeal = new OrderSeal();
					orderSeal.setSealId(UUIDHex.newId());
					orderSeal.setProjectId(change.getProjectId());
					orderSeal.setProjectName(change.getProjectName());
					orderSeal.setContractId(change.getContractId());
					orderSeal.setContractNo(change.getContractNo());
					orderSeal.setContractName(change.getContractName());
					orderSeal.setDeptId(change.getDeptId());
					orderSeal.setDeptName(change.getDeptName());
					orderSeal.setCreatedDate(new Date());
					//用印初始化生成协议编号//生成新的合同编号
					if(chType == 1) {
					String businessNo;
					String NoHead = change.getContractNo();
					String Con = "e";
					String NoCon = NoHead+Con;
					Integer count = this.contractChOrderDao.getBusinessNoIndex(NoCon);
					String NoEnd = String.format("%03d", count);
					businessNo = NoCon + NoEnd;
					orderSeal.setBusinessNo(businessNo);
					}else if(chType == 2){
						int hasProtocol = change.getHasProtocol();
						if(hasProtocol == 1) {
							String businessNo;
							String NoHead = change.getContractNo();
							String Con = "e";
							String NoCon = NoHead+Con;
							Integer count = this.contractChOrderDao.getBusinessNoIndex(NoCon);
							String NoEnd = String.format("%03d", count);
							businessNo = NoCon + NoEnd;
							orderSeal.setBusinessNo(businessNo);	
						}
					}
				}
				orderSeal.setChangeId(changeId);
				orderSeal.setSwfStatus(ContractSeal.DRAFT);
				orderSeal.setIsNew(true);
				orderSeal.setCreatedBy(user.getName());		
				orderSeal.setCreatedById(user.getPersonId());	
//				if(user.getDept() != null){
//					orderSeal.setCreatedBy(user.getDept().getName());
//					orderSeal.setCreatedById(user.getDeptId());
//				}
			}
		}
		return orderSeal;
	}

	public OrderSeal saveChangeSeal(UserProfile user, OrderSeal orderSeal) {
		if (user != null) {
			orderSeal.setModifiedBy(user.getName());
			orderSeal.setModifiedById(user.getPersonId());
		}
		if (orderSeal.getIsNew()) {
			if (user != null) {
				orderSeal.setCreatedBy(user.getName());
				orderSeal.setCreatedById(user.getPersonId());
				Dept dept = user.getDept();
				if (dept != null) {
					orderSeal.setDeptId(dept.getDeptId());
					orderSeal.setDeptName(dept.getName());
				}
			}
			orderSeal.setCreatedDate(new Date());
			orderSeal.setIsNew(false);
			orderSealDao.save(orderSeal);
		} else { // 设置更新时间
			orderSeal.setModifiedDate(new Date());
			orderSealDao.update(orderSeal);
		}
		return orderSeal;
	}
	
	public OrderSeal getContractSealBySealId(String sealId) {
		return this.orderSealDao.get(sealId);
	}
	public ContractChOrder getContractOrderByChId(String chId) {
		return this.contractChOrderDao.get(chId);
	}

	public String checkOrderChange(String contractId) {
		return this.orderChangeDao.checkOrderChange(contractId);
	}
	
	public boolean checkContractChOrder(String changeId) {
		return this.contractChOrderDao.checkContractChOrder(changeId);
	}
	
//	public void completeExam(ContractChOrder contractChOrder) {
//			// 将合同信息录入合同正式表中
//			this.orderOnlineService.createContractByOrderChange(contractChOrder);
//	}

	public void change(UserProfile user, ContractChOrder contractChOrder) {
        //变更合同主键chId
		String chId = contractChOrder.getChId();
		//覆盖原合同的主键是contractId
		String contractId = contractChOrder.getContractId();
		PrjContractTable prjContractTable = prjContractTableService.get(contractId);
		// 复制合同上线信息至合同正式表
		try {
			//创建印花税
			this.contractStampTaxAmountService.completedOrderChange(contractChOrder, prjContractTable);
			
			BeanUtils.copyProperties(prjContractTable, contractChOrder);
			this.prjContractTableDao.update(prjContractTable);
			//金额
			// 原合同old
			List<PrjContractAmount> oldAmountList = prjContractAmountDao.findBy("contractId", contractId);
			// 变更合同new
			List<OrderChangeContractAmount> newAmountList = amountDao.findBy("chId", chId);
			int newAmountSize = newAmountList.size();
			for (int i = 0; i < newAmountSize; i++) {
				String[] ignores = { "amountId", "chId" };
				OrderChangeContractAmount newContent = newAmountList.get(i);
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
			//terms表增删改
			//原合同old
			List<PrjContractCollectionTerms> oldTermsList = prjContractCollectionTermsDao.findBy("contractId", contractId);
			//变更合同new
			List<OrderChangeCollectionTerms> newTermsList = termsDao.findBy("chId", chId);
			int newSize = newTermsList.size();
			for (int i = 0; i < newSize; i++) {
				String[] ignores = {"termsId", "chId"};
				OrderChangeCollectionTerms newTerms = newTermsList.get(i);
				PrjContractCollectionTerms oldTerms = findBySiteId(oldTermsList,newTerms.getTermsOldId());
				if(oldTerms != null) {
					//更新
					com.supporter.prj.pm.util.BeanUtils.copyProperties(newTerms, oldTerms, ignores);
					prjContractCollectionTermsDao.update(oldTerms);
					//从原表中删除
					oldTermsList.remove(oldTerms);
				}else {
					//新增
					oldTerms = new PrjContractCollectionTerms();
					com.supporter.prj.pm.util.BeanUtils.copyProperties(newTerms, oldTerms, ignores);
					oldTerms.setContractId(contractId);
					prjContractCollectionTermsDao.save(oldTerms);
				}
				
			}
			if (oldTermsList.size() > 0) {
				prjContractCollectionTermsDao.delete(oldTermsList);
			}
			
			//Goods
			// 原合同old
			List<PrjContractGoods> oldGoodsList = prjContractGoodsDao.findBy("contractId", contractId);
			// 变更合同new
			List<OrderChangeGoods> newGoodsList = goodsDao.findBy("chId", chId);
			int newGoodsSize = newGoodsList.size();
			for (int i = 0; i < newGoodsSize; i++) {
				String[] ignores = {"goodsId","chId"};
				OrderChangeGoods newGoods = newGoodsList.get(i);
				PrjContractGoods oldGoods = findByGoodsId(oldGoodsList, newGoods.getGoodsOldId());
				if (oldGoods != null) {
					// 更新
					com.supporter.prj.pm.util.BeanUtils.copyProperties(newGoods, oldGoods, ignores);
					prjContractGoodsDao.update(oldGoods);
					// 从原表中删除
					oldGoodsList.remove(oldGoods);
				} else {
					// 新增
					oldGoods = new PrjContractGoods();
					com.supporter.prj.pm.util.BeanUtils.copyProperties(newGoods, oldGoods, ignores);
					oldGoods.setGoodsId(null);
					oldGoods.setContractId(contractId);
					prjContractGoodsDao.save(oldGoods);
				}
			}
			if (oldGoodsList.size() > 0) {
				prjContractGoodsDao.delete(oldGoodsList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
	
	private PrjContractGoods findByGoodsId(List<PrjContractGoods> oldGoodsList, String GoodsOldId) {
		if (StringUtils.isNotBlank(GoodsOldId)) {
		int len = oldGoodsList.size();
		for (int i = 0; i < len; i++) {
			PrjContractGoods oldGoods = oldGoodsList.get(i);
			if (GoodsOldId.equals(oldGoods.getGoodsId())) {
				return oldGoods;
			}
		}
		}
		return null;
	}

	public void saveFirstDetail(OrderChangeGoods entity) {
		if (entity == null) {
			return;
		}
		String goodsId = entity.getGoodsId();
		//
		OrderChangeGoods dbGoods = this.goodsDao.get(goodsId);
		if(dbGoods!=null) {
			ModuleUtils.copyPropertiesExcludeNullProperty(entity, dbGoods);
			//更新币别中文
			dbGoods.setCurrency(TpcConstant.getCommonCurrencyMap().get(dbGoods.getCurrencyId()));
			this.goodsDao.update(dbGoods);
		}else {
			//更新币别中文
			entity.setCurrency(TpcConstant.getCommonCurrencyMap().get(entity.getCurrencyId()));
			this.goodsDao.save(entity);
		}
		
	}
	public void saveTwoDetail(OrderChangeGoods entity) {
		if (entity == null) {
			return;
		}
		goodsDao.update(entity);
		List<OrderChangeGoodsChild> child = entity.getGooodsChild();
		if(CollectionUtils.isNotEmpty(child)) {
			saveOrUpdateGoodsChildList(entity.getGoodsId(), child);
		}
		deleteGoodsTerms(entity.getDelTermsIds());
	}
	
	public void saveOrUpdateGoodsChildList(String goodsId, List<OrderChangeGoodsChild> goodsChildList) {
		for (OrderChangeGoodsChild goods : goodsChildList) {
			goods.setGoodsId(goodsId);
			goods.setCurrency(TpcConstant.getCommonCurrencyMap().get(goods.getCurrencyId()));
			if (StringUtils.isBlank(goods.getGoodsId()) || goods.getAdd()) {
				this.goodsChildDao.save(goods);
			} else {
				this.goodsChildDao.update(goods);
			}
		}
	}
	
	public void deleteGoodsTerms(String delTermsIds) {
		if (StringUtils.isNotBlank(delTermsIds)) {
			for (String termsId : delTermsIds.split(",")) {
				this.goodsChildDao.deleteByProperty("childId", termsId);
			}
		}
	}

	public OrderSeal saveStamp(String sealId, String stampPersonId, String stampPerson, Date stampDate) {
		// TODO Auto-generated method stub
		OrderSeal orderSeal= (OrderSeal) this.orderSealDao.get(sealId);
		orderSeal.setStampDate(stampDate);
		orderSeal.setStampPerson(stampPerson);
		orderSeal.setStampPersonId(stampPersonId);
		this.orderSealDao.update(orderSeal);
		return orderSeal;
	}

	public void callPaper(String chId) {
		if (StringUtils.isNotBlank(chId)) {
			ContractChOrder contractChOrder = contractChOrderDao.get(chId);	
			if(contractChOrder != null && StringUtils.isNotBlank(contractChOrder.getCreatedById())) {
				Message messageCreat =new Todo();
				messageCreat.setPersonId(contractChOrder.getCreatedById());
				messageCreat.setEventTitle("请及时送达纸质版合同：" + contractChOrder.getContractName());
				messageCreat.setNotifyTime(new Date());
				messageCreat.setWebPageURL("/tpc/order_change/contract_callPaperView.html?chId="
						+ CommonUtil.trim(chId));
				messageCreat.setMessageType(ITodo.MsgType.CC);
				messageCreat.setRelatedRecordTable(EIPService.getWebappService().getWebappName());
				EIPService.getBMSService().addMessage(messageCreat);
			}
		}else {
			System.out.println("ContractChOrder 未找到经办人！");
		}
	}

	public boolean saveDetail(ContractChOrder order) {
		List<OrderChangeContractAmount> contractAmountList = order.getContractAmountList();
		// 保存或更新合同额
		if(CollectionUtils.isNotEmpty(contractAmountList)){
			for (OrderChangeContractAmount amount : contractAmountList) {
				amount.setCurrency(TpcConstant.getCommonCurrencyMap().get(amount.getCurrencyId()));
				if (StringUtils.isBlank(amount.getAmountId()) || amount.getAdd()) {
					this.amountDao.save(amount);
				} else {
					this.amountDao.update(amount);
				}
			}
			return true;
		}
		return false;
	}
	//判断销售合同收款条款能不能被删除
	public String canDelTent(String termsId) {
		//通过termsId获取变更收款内容的termsOldId
		OrderChangeCollectionTerms termsC = termsDao.get(termsId);
		String termsOldId = termsC.getTermsOldId();
		System.out.println("termsOldId"+termsOldId);
		//通过termsOldId获取收款条款列表
		List<CollectionDetail> materialContentList = collectionDetailDao.findBy("collectionClauseId",termsOldId);
		if(materialContentList != null) {
			for(CollectionDetail maList : materialContentList) {
				if(maList.getCollectionClauseId().equals(termsOldId)) {
					return "materError";
				}
			}
		}
		return "success";
	}
}
