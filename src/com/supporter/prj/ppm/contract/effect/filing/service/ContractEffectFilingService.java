package com.supporter.prj.ppm.contract.effect.filing.service;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.contract.effect.filing.dao.ContractEffectFilingDao;
import com.supporter.prj.ppm.contract.effect.filing.entity.ContractEffectFiling;
import com.supporter.prj.ppm.contract.effect.filing.entity.ContractEffectFilingBfd;
import com.supporter.prj.ppm.contract.effect.seal_bfd.service.ContractEffectSealService;
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
public class ContractEffectFilingService {

	@Autowired
	private ContractEffectFilingDao contractFilingDao;
	@Autowired
	private ContractEffectFilingBfdService filingBfdService;
	@Autowired
	private ContractEffectSealService contractEffectSealService;
	@Autowired
	private BaseInfoService prjService;

	/**
	 * 根据主键获取PPM_CONTRACT_FILING.
	 * 
	 * @param filingId 主键
	 * @return ContractEffectFiling
	 */
	public ContractEffectFiling get(String filingId) {
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
	public List< ContractEffectFiling > getGrid(UserProfile user, JqGrid jqGrid, ContractEffectFiling contractFiling) {
		return contractFilingDao.findPage(jqGrid, contractFiling);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param filingId
	 * @return
	 */
	public ContractEffectFiling initEditOrViewPage(String filingId) {
		if (StringUtils.isBlank(filingId)) {// 新建
			ContractEffectFiling entity = new ContractEffectFiling();
			return entity;
		} else {// 编辑
			ContractEffectFiling entity = contractFilingDao.get(filingId);
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
	public ContractEffectFiling saveOrUpdate(UserProfile user, ContractEffectFiling contractFiling) {
		if (StringUtils.isBlank(contractFiling.getFilingId())) {// 新建
			contractFiling = this.save(user, contractFiling);
		} else {// 编辑
			contractFiling = this.update(user, contractFiling);
		}
		//保存或更新文件类型
		List<ContractEffectFilingBfd> bfds = contractFiling.getBfds();
		if (bfds!=null){
			for (ContractEffectFilingBfd bfd:bfds
			) {
				//保存或更新类型信息
				this.filingBfdService.saveOrUpdate(user,bfd);
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
	private ContractEffectFiling save(UserProfile user, ContractEffectFiling contractFiling) {
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
	private ContractEffectFiling update(UserProfile user, ContractEffectFiling contractFiling) {
		contractFiling.setModifiedBy(user.getName());
		contractFiling.setModifiedById(user.getPersonId());
		contractFiling.setModifiedDate(new Date());
		ContractEffectFiling contractFilingDb = this.contractFilingDao.get(contractFiling.getFilingId());
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
				ContractEffectFiling contractFilingDb = this.contractFilingDao.get(filingId);
				this.contractFilingDao.delete(filingId);
				//删除资料清单
				this.filingBfdService.deleteByFilingId(filingId);
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

	public ContractEffectFiling initPageByPrjId(String prjId) {
		ContractEffectFiling entity = null;
		if (StringUtils.isNotBlank(prjId)){
			List<ContractEffectFiling> list = this.contractFilingDao.findBy("prjId",prjId);
			if (list!=null&&list.size()>0){
				entity = list.get(0);
			}else {
				entity = new ContractEffectFiling();
				entity.setPrjId(prjId);
				entity.setFilingId(com.supporter.util.UUIDHex.newId());
			}
			entity = initPrjInfo(prjId,entity);
		}
		//获取对应的出版对象
		entity.setContractEffectSeal(contractEffectSealService.initPageByPrjId(prjId));
		//初始化资料清单
		List<ContractEffectFilingBfd> bfds = initBfds(entity.getFilingId());
		entity.setBfds(bfds);
		return entity;
	}
	private List<ContractEffectFilingBfd> initBfds(String filingId) {
		//初始化资料清单
		List<ContractEffectFilingBfd> bfds = this.filingBfdService.getListByFilingId(filingId);
		if (bfds==null||bfds.size()<1){
			bfds = new ArrayList<ContractEffectFilingBfd>();
			//根据码表获取对应的需求
			List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PPM_CONTRACT_EFFECT_BFD");
			for (IComCodeTableItem item : items
			) {
				ContractEffectFilingBfd bfd = new ContractEffectFilingBfd();
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
	private ContractEffectFiling initPrjInfo(String prjId, ContractEffectFiling entity) {
		//查询对应的项目 赋值
		Prj prj =this.prjService.initBaseInfoView(prjId);
		if (prj!=null){
			entity.setPrjCName(prj.getPrjCName());
			entity.setPrjEName(prj.getPrjEName());
			entity.setPrjNo(prj.getPrjNo());
		}
		return entity;
	}

    public ContractEffectFiling getByPrjId(String prjId) {
		List<ContractEffectFiling> list = this.contractFilingDao.findBy("prjId",prjId);
		if (list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
    }

    public Map<String, Object> validEffect(String prjId, String message, boolean result) {
		//判断项目是否生效
		Prj prj = this.prjService.initBaseInfoView(prjId);
		//如果项目不是生效状态
		if (prj.getStatus()!=1&&prj.getStatus()!=2){
			message = "该项目尚未生效！";
		}else{
			result = true ;
		}
		Map <String , Object> map = new HashMap<String, Object>();
		map.put("msg",message);
		map.put("result",result);
		return map;
    }

	/**
	* 流程处理类更新
	* @param entity 实体类
	* @return
	*/
	public ContractEffectFiling updateSimply(ContractEffectFiling entity) {
		if (entity != null) {
			contractFilingDao.update(entity);
		}
		return entity;
	}
}

