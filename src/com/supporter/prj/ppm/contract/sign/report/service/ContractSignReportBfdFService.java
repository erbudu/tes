package com.supporter.prj.ppm.contract.sign.report.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.ppm.contract.sign.report.dao.ContractSignReportBfdDao;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReportBfd;
import com.supporter.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.contract.sign.report.dao.ContractSignReportBfdFDao;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReportBfdF;

import javax.persistence.Transient;

/**
 * @Title: Service
 * @Description: 主合同签约评审资料清单单文件.
 * @author YAN
 * @date 2019-09-05 17:09:58
 * @version V1.0
 */
@Service
public class ContractSignReportBfdFService {

	@Autowired
	private ContractSignReportBfdFDao contractSignReportBfdFDao;
	@Autowired
	private ContractSignReportBfdDao contractSignReportBfdDao;

	/**
	 * 根据主键获取主合同签约评审资料清单单文件.
	 * 
	 * @param recordId 主键
	 * @return ContractSignReportBfdF
	 */
	public ContractSignReportBfdF get(String recordId) {
		return contractSignReportBfdFDao.get(recordId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractSignReportBfdF > getGrid(UserProfile user, JqGrid jqGrid, ContractSignReportBfdF contractSignReportBfdF) {
		return contractSignReportBfdFDao.findPage(jqGrid, contractSignReportBfdF);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param recordId
	 * @return
	 */
	public ContractSignReportBfdF initEditOrViewPage(String recordId) {
		if (StringUtils.isBlank(recordId)) {// 新建
			ContractSignReportBfdF entity = new ContractSignReportBfdF();
			return entity;
		} else {// 编辑
			ContractSignReportBfdF entity = contractSignReportBfdFDao.get(recordId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractSignReportBfdF 实体类
	 * @return
	 */
	@Transient
	public ContractSignReportBfdF saveOrUpdate(UserProfile user, ContractSignReportBfdF contractSignReportBfdF) {
		if (StringUtils.isBlank(contractSignReportBfdF.getRecordId())) {// 新建
			return this.save(user, contractSignReportBfdF);
		} else {// 编辑
			return this.update(user, contractSignReportBfdF);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractSignReportBfdF 实体类
	 * @return
	 */
	private ContractSignReportBfdF save(UserProfile user, ContractSignReportBfdF contractSignReportBfdF) {
		this.contractSignReportBfdFDao.save(contractSignReportBfdF);
		return contractSignReportBfdF;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractSignReportBfdF 实体类
	 * @return
	 */
	private ContractSignReportBfdF update(UserProfile user, ContractSignReportBfdF contractSignReportBfdF) {
		ContractSignReportBfdF contractSignReportBfdFDb = this.contractSignReportBfdFDao.get(contractSignReportBfdF.getRecordId());
		if(contractSignReportBfdFDb == null) {
			return this.save(user, contractSignReportBfdF);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractSignReportBfdF, contractSignReportBfdFDb);
			this.contractSignReportBfdFDao.update(contractSignReportBfdFDb);
			return contractSignReportBfdFDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param recordIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String recordIds) {
		if(StringUtils.isNotBlank(recordIds)) {
			for(String recordId : recordIds.split(",")) {
				ContractSignReportBfdF contractSignReportBfdFDb = this.contractSignReportBfdFDao.get(recordId);
				this.contractSignReportBfdFDao.delete(recordId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param recordId
	 * @param recordName
	 * @return
	 */
	public boolean nameIsValid(String recordId,String recordName) {
		return this.contractSignReportBfdFDao.nameIsValid( recordId, recordName);
	}

	/**
	 * 根据签约 报审id 删除对应的附件记录
	 * @param contractSignId
	 */
	public void deleteByContractSignId(String contractSignId) {
		this.contractSignReportBfdFDao.deleteByProperty("contractSignId",contractSignId);
	}

	public void batchDelFiles(UserProfile user, String filesId) {
		/*String hql = "from  "+ContractSignReportBfd.class.getName()+" where contractSignId = ? and bfdTypeId = ?";
		List<ContractSignReportBfd> list = this.contractSignReportBfdDao.find(hql,oneId,twoId);*/
		if (StringUtils.isNotBlank(filesId)){
			for (String  fileId:filesId.split(",")
			) {
				this.contractSignReportBfdFDao.deleteByProperty("fuFileId",fileId);
			}
		}
	}
	//保存文件
	public ContractSignReportBfdF saveFile(UserProfile user, ContractSignReportBfdF contractSignReportBfdF) {
		String hql = "from  "+ ContractSignReportBfd.class.getName()+" where contractSignId = ? and bfdTypeId = ?";
		List<ContractSignReportBfd> list = this.contractSignReportBfdDao.find(hql,contractSignReportBfdF.getContractSignId(),contractSignReportBfdF.getBfdId());
		if (!CommonUtil.isNullOrEmpty(list)){
			if (StringUtils.isNotBlank(contractSignReportBfdF.getFuFileId())){
				for (String fuFileId:contractSignReportBfdF.getFuFileId().split(",")
					 ) {
					ContractSignReportBfdF bfdf = new ContractSignReportBfdF();
					bfdf.setBfdId(list.get(0).getBfdId());
					bfdf.setContractSignId(contractSignReportBfdF.getContractSignId());
					bfdf.setFuFileId(fuFileId);
					this.save(user,bfdf);
				}
			}
		}
		return contractSignReportBfdF;
	}
}

