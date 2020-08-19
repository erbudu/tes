package com.supporter.prj.cneec.tpc.prj_contract_modify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.prj_contract_modify.dao.PrjContractAmountBMDao;
import com.supporter.prj.cneec.tpc.prj_contract_modify.dao.PrjContractCollectionTermsBMDao;
import com.supporter.prj.cneec.tpc.prj_contract_modify.dao.PrjContractGoodsBMDao;
import com.supporter.prj.cneec.tpc.prj_contract_modify.dao.PrjContractTableBMDao;
import com.supporter.prj.cneec.tpc.prj_contract_modify.dao.SettlementPlanBMDao;
import com.supporter.prj.cneec.tpc.prj_contract_modify.entity.PrjContractAmountBM;
import com.supporter.prj.cneec.tpc.prj_contract_modify.entity.PrjContractCollectionTermsBM;
import com.supporter.prj.cneec.tpc.prj_contract_modify.entity.PrjContractGoodsBM;
import com.supporter.prj.cneec.tpc.prj_contract_modify.entity.PrjContractTableBM;
import com.supporter.prj.cneec.tpc.prj_contract_modify.entity.SettlementPlanBM;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.FilesUtil;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;

@Service
@Transactional(TransManager.APP)
public class PrjContractTableBMService {

	@Autowired
	private PrjContractTableBMDao contractTableBMDao;
	@Autowired
	private PrjContractAmountBMDao contractAmountBMDao;
	@Autowired
	private PrjContractGoodsBMDao contractGoodsBMDao;
	@Autowired
	private PrjContractCollectionTermsBMDao collectionTermsBMDao;
	@Autowired
	private SettlementPlanBMDao settlementPlanBMDao;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, PrjContractTableBM.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param contractOnline
	 */
	public void getAuthCanExecute(UserProfile userProfile, PrjContractTableBM contractBM) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, PrjContractTableBM.MODULE_ID, contractBM.getBmId(), contractBM);
	}

	/**
	 * 获取销售合同上线对象集合
	 * @param user
	 * @param jqGrid
	 * @param contractOnline
	 * @return
	 */
	public List<PrjContractTableBM> getGrid(UserProfile user, JqGrid jqGrid, PrjContractTableBM contractBM) {
		String authFilter = getAuthFilter(user);
		return this.contractTableBMDao.findPage(jqGrid, contractBM, authFilter);
	}

	/**
	 * 获取合同额集合
	 * @param user
	 * @param jqGrid
	 * @param contractId
	 * @return
	 */
	public List<PrjContractAmountBM> getContractAmountGrid(UserProfile user, JqGrid jqGrid, String changeId) {
		return this.contractAmountBMDao.findPage(jqGrid, changeId);
	}

	/**
	 * 获取货物及服务明细集合
	 * @param user
	 * @param jqGrid
	 * @param contractId
	 * @return
	 */
	public List<PrjContractGoodsBM> getGoodsGrid(UserProfile user, JqGrid jqGrid, String changeId) {
		return this.contractGoodsBMDao.findPage(jqGrid, changeId);
	}

	/**
	 * 获取收款条件集合
	 * @param user
	 * @param jqGrid
	 * @param contractId
	 * @return
	 */
	public List<PrjContractCollectionTermsBM> getCollectionTermsGrid(UserProfile user, JqGrid jqGrid, String changeId) {
		return this.collectionTermsBMDao.findPage(jqGrid, changeId);
	}

	/**
	 * 获取单个销售合同上线对象
	 * @param contractId
	 * @return
	 */
	public PrjContractTableBM get(String changeId) {
		PrjContractTableBM contractBM = this.contractTableBMDao.get(changeId);
		List<PrjContractAmountBM> contractAmountList = this.contractAmountBMDao.findBy("changeId", changeId);
		List<PrjContractGoodsBM> contractGoodsList = this.contractGoodsBMDao.findBy("changeId", changeId);
		List<PrjContractCollectionTermsBM> collectionTermsList = this.collectionTermsBMDao.findBy("changeId", changeId);
		List<SettlementPlanBM> settlementPlanBMList = this.settlementPlanBMDao.findBy("changeId", changeId);
		if (contractBM != null) {
			contractBM.setContractAmountBMList(contractAmountList);
			contractBM.setContractGoodsBMList(contractGoodsList);
			contractBM.setCollectionTermsBMList(collectionTermsList);
			contractBM.setSettlementPlanBMList(settlementPlanBMList);
		}
		return contractBM;
	}

	/**
	 * 删除变更前记录表
	 * @param changeId
	 */
	public void delete(String changeId) {
		this.contractTableBMDao.delete(changeId);
		this.contractAmountBMDao.deleteByProperty("changeId", changeId);
		this.contractGoodsBMDao.deleteByProperty("changeId", changeId);
		this.collectionTermsBMDao.deleteByProperty("changeId", changeId);
		this.settlementPlanBMDao.deleteByProperty("changeId", changeId);
		String[] deleteModule = new String[] { PrjContractTableBM.MODULE_ID, PrjContractTableBM.BUSI_TYPE, changeId, "" };
		FilesUtil.deleteFiles(deleteModule);
	}

}
