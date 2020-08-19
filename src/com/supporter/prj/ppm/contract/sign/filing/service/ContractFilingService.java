package com.supporter.prj.ppm.contract.sign.filing.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.supporter.prj.ppm.contract.sign.filing.entity.ContractEffectCondition;
import com.supporter.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.contract.sign.filing.dao.ContractFilingDao;
import com.supporter.prj.ppm.contract.sign.filing.entity.ContractFiling;
import com.supporter.prj.ppm.contract.sign.filing.entity.ContractFilingBfd;
import com.supporter.prj.ppm.contract.sign.seal_bfd.service.ContractSignSealService;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;

/**
 * @Title: Service
 * @Description: PPM_CONTRACT_FILING.
 * @author YAN
 * @date 2019-09-17 11:37:24
 * @version V1.0
 */
@Service
public class ContractFilingService {

	@Autowired
	private ContractFilingDao contractFilingDao;
	@Autowired
	private ContractFilingBfdService filingBfdService;
	@Autowired
	private ContractSignSealService contractSignSealService;
	@Autowired
	private BaseInfoService prjService;
	@Autowired
	private ContractEffectConditionService conditionService;

	/**
	 * 根据主键获取PPM_CONTRACT_FILING.
	 * 
	 * @param filingId 主键
	 * @return ContractFiling
	 */
	public ContractFiling get(String filingId) {
		return contractFilingDao.get(filingId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractFiling > getGrid(UserProfile user, JqGrid jqGrid, ContractFiling contractFiling) {
		return contractFilingDao.findPage(jqGrid, contractFiling);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param filingId
	 * @return
	 */
	public ContractFiling initEditOrViewPage(String filingId) {
		if (StringUtils.isBlank(filingId)) {// 新建
			ContractFiling entity = new ContractFiling();
			return entity;
		} else {// 编辑
			ContractFiling entity = contractFilingDao.get(filingId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractFiling 实体类
	 * @return
	 */
	public ContractFiling saveOrUpdate(UserProfile user, ContractFiling contractFiling) {
		if (StringUtils.isBlank(contractFiling.getFilingId())) {// 新建
			contractFiling = this.save(user, contractFiling);
		} else {// 编辑
			contractFiling = this.update(user, contractFiling);
		}
		//保存或更新文件类型
		List<ContractFilingBfd> bfds = contractFiling.getBfds();
		if (bfds!=null){
			for (ContractFilingBfd bfd:bfds
			) {
				//保存或更新类型信息
				this.filingBfdService.saveOrUpdate(user,bfd);
			}
		}
		//保存签约条件
		List<ContractEffectCondition> list = contractFiling.getConditionList();
		if (!CommonUtil.isNullOrEmpty(list)) {
			for (ContractEffectCondition conditon : list
			) {
				conditon.setPrjId(contractFiling.getPrjId());
				conditon.setSignFilingId(contractFiling.getFilingId());
				this.conditionService.saveOrUpdate(user,conditon);
			}
		}
		return contractFiling;
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractFiling 实体类
	 * @return
	 */
	private ContractFiling save(UserProfile user, ContractFiling contractFiling) {
		contractFiling.setCreatedBy(user.getName());
		contractFiling.setCreatedById(user.getPersonId());
		contractFiling.setCreatedDate(new Date());
		this.contractFilingDao.save(contractFiling);
		return contractFiling;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractFiling 实体类
	 * @return
	 */
	private ContractFiling update(UserProfile user, ContractFiling contractFiling) {
		contractFiling.setModifiedBy(user.getName());
		contractFiling.setModifiedById(user.getPersonId());
		contractFiling.setModifiedDate(new Date());
		ContractFiling contractFilingDb = this.contractFilingDao.get(contractFiling.getFilingId());
		if(contractFilingDb == null) {
			return this.save(user, contractFiling);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractFiling, contractFilingDb);
			this.contractFilingDao.update(contractFilingDb);
			return contractFilingDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param filingIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String filingIds) {
		if(StringUtils.isNotBlank(filingIds)) {
			for(String filingId : filingIds.split(",")) {
				ContractFiling contractFilingDb = this.contractFilingDao.get(filingId);
				this.contractFilingDao.delete(filingId);
				//删除资料清单
				this.filingBfdService.deleteByFilingId(filingId);
				//删除生效条件
				this.conditionService.deleteByPrjId(contractFilingDb.getPrjId());
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param filingId
	 * @param filingName
	 * @return
	 */
	public boolean nameIsValid(String filingId,String filingName) {
		return this.contractFilingDao.nameIsValid( filingId, filingName);
	}

	public ContractFiling initPageByPrjId(String prjId) {
		ContractFiling entity = null;
		if (StringUtils.isNotBlank(prjId)){
			List<ContractFiling> list = this.contractFilingDao.findBy("prjId",prjId);
			if (list!=null&&list.size()>0){
				entity = list.get(0);
			}else {
				entity = new ContractFiling();
				entity.setPrjId(prjId);
				entity.setFilingId(com.supporter.util.UUIDHex.newId());
			}
			entity = initPrjInfo(prjId,entity);
		}
		//获取对应的出版对象
		entity.setContractSignSeal(contractSignSealService.initPageByPrjId(prjId));
		//初始化资料清单
		List<ContractFilingBfd> bfds = initBfds(entity.getFilingId());
		entity.setBfds(bfds);
		return entity;
	}
	private List<ContractFilingBfd> initBfds(String filingId) {
		//初始化资料清单
		List<ContractFilingBfd> bfds = this.filingBfdService.getListByFilingId(filingId);
		if (bfds==null||bfds.size()<1){
			bfds = new ArrayList<ContractFilingBfd>();
			//根据码表获取对应的需求
			List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PPM_CONTRACT_SIGN_FILING");
			for (IComCodeTableItem item : items
			) {
				ContractFilingBfd bfd = new ContractFilingBfd();
				bfd.setBfdTypeId(item.getItemId());
				bfd.setBfdTypeName(item.getItemValue());
				bfd.setBfdId(com.supporter.util.UUIDHex.newId());
				bfd.setFilingId(filingId);
				bfds.add(bfd);
			}
		}
		return bfds;
	}
	/**
	 * 初始化项目信息
	 * @param prjId
	 * @param entity
	 */
	private ContractFiling initPrjInfo(String prjId, ContractFiling entity) {
		//查询对应的项目 赋值
		Prj prj = this.prjService.initBaseInfoView(prjId);
		if (prj!=null){
			entity.setPrjCName(prj.getPrjCName());
			entity.setPrjEName(prj.getPrjEName());
			entity.setPrjNo(prj.getPrjNo());
		}
		return entity;
	}

    public ContractFiling getByPrjId(String prjId) {
		List<ContractFiling> list = this.contractFilingDao.findBy("prjId",prjId);
		if (list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
    }

	/**
	* 流程处理类更新
	* @param entity 实体类
	* @return
	*/
	public ContractFiling updateSimply(ContractFiling entity) {
		if (entity != null) {
			contractFilingDao.update(entity);
		}
		return entity;
	}
}

