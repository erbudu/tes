package com.supporter.prj.ppm.contract.sign.seal_bfd.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReport;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReportBfd;
import com.supporter.prj.ppm.contract.sign.report.service.ContractSignReportService;
import com.supporter.prj.ppm.contract.sign.seal_bfd.entity.ContractSignSealBfd;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.contract.sign.seal_bfd.dao.ContractSignSealDao;
import com.supporter.prj.ppm.contract.sign.seal_bfd.entity.ContractSignSeal;

/**
 * @Title: Service
 * @Description: 主合同出版.
 * @author YAN
 * @date 2019-09-10 14:57:13
 * @version V1.0
 */
@Service
public class ContractSignSealService {

	@Autowired
	private ContractSignSealDao contractSignSealDao;
	@Autowired
	private ContractSignSealBfdService signSealBfdService;
	@Autowired
	private ContractSignReportService signReportService;
	@Autowired
	private BaseInfoService prjService;

	/**
	 * 根据主键获取主合同出版.
	 * 
	 * @param signSealId 主键
	 * @return ContractSignSeal
	 */
	public ContractSignSeal get(String signSealId) {
		return contractSignSealDao.get(signSealId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractSignSeal > getGrid(UserProfile user, JqGrid jqGrid, ContractSignSeal contractSignSeal) {
		return contractSignSealDao.findPage(jqGrid, contractSignSeal);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param signSealId
	 * @return
	 */
	public ContractSignSeal initEditOrViewPage(String signSealId) {
		if (StringUtils.isBlank(signSealId)) {// 新建
			ContractSignSeal entity = new ContractSignSeal();
			return entity;
		} else {// 编辑
			ContractSignSeal entity = contractSignSealDao.get(signSealId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractSignSeal 实体类
	 * @return
	 */
	public ContractSignSeal saveOrUpdate(UserProfile user, ContractSignSeal contractSignSeal) {
		if (StringUtils.isBlank(contractSignSeal.getSignSealId())) {// 新建
			contractSignSeal = this.save(user, contractSignSeal);
		} else {// 编辑
			contractSignSeal = this.update(user, contractSignSeal);
		}
		//保存或更新文件类型
		List<ContractSignSealBfd> bfds = contractSignSeal.getBfds();
		if (bfds!=null){
			for (ContractSignSealBfd bfd:bfds
				 ) {
				//保存或更新类型信息
				this.signSealBfdService.saveOrUpdate(user,bfd);
			}
		}
		return contractSignSeal;
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractSignSeal 实体类
	 * @return
	 */
	private ContractSignSeal save(UserProfile user, ContractSignSeal contractSignSeal) {
		contractSignSeal.setCreatedBy(user.getName());
		contractSignSeal.setCreatedById(user.getPersonId());
		contractSignSeal.setCreatedDate(new Date());
		this.contractSignSealDao.save(contractSignSeal);
		return contractSignSeal;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractSignSeal 实体类
	 * @return
	 */
	private ContractSignSeal update(UserProfile user, ContractSignSeal contractSignSeal) {
		contractSignSeal.setModifiedBy(user.getName());
		contractSignSeal.setModifiedById(user.getPersonId());
		contractSignSeal.setModifiedDate(new Date());
		ContractSignSeal contractSignSealDb = this.contractSignSealDao.get(contractSignSeal.getSignSealId());
		if(contractSignSealDb == null) {
			return this.save(user, contractSignSeal);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractSignSeal, contractSignSealDb);
			this.contractSignSealDao.update(contractSignSealDb);
			return contractSignSealDb;
		}

	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param signSealIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String signSealIds) {
		if(StringUtils.isNotBlank(signSealIds)) {
			for(String signSealId : signSealIds.split(",")) {
				ContractSignSeal contractSignSealDb = this.contractSignSealDao.get(signSealId);
				this.contractSignSealDao.delete(signSealId);
				//删除对应的附件类型
				this.signSealBfdService.deleteBySignSealId(signSealId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param signSealId
	 * @param signSealName
	 * @return
	 */
	public boolean nameIsValid(String signSealId,String signSealName) {
		return this.contractSignSealDao.nameIsValid( signSealId, signSealName);
	}

	/**
	 * 根据项目id初始化
	 * @param prjId
	 * @return
	 */
	public ContractSignSeal initPageByPrjId(String prjId) {
		ContractSignSeal entity = null;
		if (StringUtils.isNotBlank(prjId)){
			List<ContractSignSeal> list = this.contractSignSealDao.findBy("prjId",prjId);
			if (list!=null&&list.size()>0){
				entity = list.get(0);
			}else {
				entity = new ContractSignSeal();
				entity.setPrjId(prjId);
				entity.setSignSealId(com.supporter.util.UUIDHex.newId());
				ContractSignReport signReport = signReportService.getEntityByPrjId(prjId);
				entity.setContractSignId(signReport.getContractSignId());
			}
			entity = initPrjInfo(prjId,entity);
		}
		//初始化资料清单
		List<ContractSignSealBfd> bfds = initBfds(entity.getSignSealId(),entity.getContractSignId());
		entity.setBfds(bfds);
		return entity;
	}

	private List<ContractSignSealBfd> initBfds(String signSealId,String contractSignId) {
		//初始化资料清单
		List<ContractSignSealBfd> bfds = this.signSealBfdService.getListBySignSealId(signSealId);
		if (bfds==null||bfds.size()<1){
			bfds = new ArrayList<ContractSignSealBfd>();
			//根据码表获取对应的需求
			List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PPM_CONTRACT_SIGN_SEAL");
			for (IComCodeTableItem item : items
			) {
				ContractSignSealBfd bfd = new ContractSignSealBfd();
				bfd.setBfdTypeId(item.getItemId());
				bfd.setBfdTypeName(item.getItemValue());
				bfd.setContractSignId(contractSignId);
				bfd.setSignSealId(signSealId);
				bfd.setBfdId(com.supporter.util.UUIDHex.newId());
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
	private ContractSignSeal initPrjInfo(String prjId, ContractSignSeal entity) {
		//查询对应的项目 赋值
		Prj prj = this.prjService.initBaseInfoView(prjId);
		if (prj!=null){
			entity.setPrjCName(prj.getPrjCName());
			entity.setPrjEName(prj.getPrjEName());
			entity.setPrjNo(prj.getPrjNo());
		}
		return entity;
	}


}

