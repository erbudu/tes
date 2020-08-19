package com.supporter.prj.ppm.contract.effect.filing.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.ppm.contract.effect.filing.entity.ContractEffectFilingBfdF;
import com.supporter.prj.ppm.contract.effect.seal_bfd.entity.ContractEffectSealBfdF;
import com.supporter.prj.ppm.contract.effect.seal_bfd.service.ContractEffectSealBfdFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.contract.effect.filing.dao.ContractEffectFilingBfdDao;
import com.supporter.prj.ppm.contract.effect.filing.entity.ContractEffectFilingBfd;

/**
 * @Title: Service
 * @Description: PPM_CONTRACT_FILING_BFD.
 * @author YAN
 * @date 2019-09-17 11:37:25
 * @version V1.0
 */
@Service
public class ContractEffectFilingBfdService {

	@Autowired
	private ContractEffectFilingBfdDao contractFilingBfdDao;
	@Autowired
	private ContractEffectFilingBfdFService filingBfdFService;
	@Autowired
	private FileUploadService fileUploadService;

	/**
	 * 根据主键获取PPM_CONTRACT_FILING_BFD.
	 * 
	 * @param bfdId 主键
	 * @return ContractEffectFilingBfd
	 */
	public ContractEffectFilingBfd get(String bfdId) {
		return contractFilingBfdDao.get(bfdId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractEffectFilingBfd > getGrid(UserProfile user, JqGrid jqGrid, ContractEffectFilingBfd contractFilingBfd) {
		return contractFilingBfdDao.findPage(jqGrid, contractFilingBfd);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param bfdId
	 * @return
	 */
	public ContractEffectFilingBfd initEditOrViewPage(String bfdId) {
		if (StringUtils.isBlank(bfdId)) {// 新建
			ContractEffectFilingBfd entity = new ContractEffectFilingBfd();
			return entity;
		} else {// 编辑
			ContractEffectFilingBfd entity = contractFilingBfdDao.get(bfdId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractFilingBfd 实体类
	 * @return
	 */
	public ContractEffectFilingBfd saveOrUpdate(UserProfile user, ContractEffectFilingBfd contractFilingBfd) {
		if (StringUtils.isBlank(contractFilingBfd.getBfdId())) {// 新建
			contractFilingBfd = this.save(user, contractFilingBfd);
		} else {// 编辑
			contractFilingBfd = this.update(user, contractFilingBfd);
		}
		//保存资料文件
		//保存或更新文件信息
		this.saveFiles(contractFilingBfd.getBfdId(), contractFilingBfd.getFilingId());
		return contractFilingBfd;
	}
	/**
	 * 保存文件新
	 *
	 * @param bfdId
	 * @param effectSealId
	 */
	private void saveFiles(String bfdId, String filingId) {
		//删除该单据下的资料文件记录
		this.filingBfdFService.deleteByBfdId(bfdId);

		//保存具体的资料文件
		//根据附件表 获取最新的文件信息并保存
		List<FileUpload> files = fileUploadService.getList("CONTEFFECTREP", "FILING" +
				"", bfdId,filingId);
		for (FileUpload file : files
		) {
			ContractEffectFilingBfdF bfdF = new ContractEffectFilingBfdF();
			bfdF.setBfdId(bfdId);
			bfdF.setFilingId(filingId);
			bfdF.setFileId(file.getFileId());
			bfdF.setDisplayOrder(file.getDisplayOrder());
			//bfdF.setIsUseSeal(); 是否需要用印
			this.filingBfdFService.saveOrUpdate(null, bfdF);
		}

	}
	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractFilingBfd 实体类
	 * @return
	 */
	private ContractEffectFilingBfd save(UserProfile user, ContractEffectFilingBfd contractFilingBfd) {
		this.contractFilingBfdDao.save(contractFilingBfd);
		return contractFilingBfd;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractFilingBfd 实体类
	 * @return
	 */
	private ContractEffectFilingBfd update(UserProfile user, ContractEffectFilingBfd contractFilingBfd) {
		ContractEffectFilingBfd contractFilingBfdDb = this.contractFilingBfdDao.get(contractFilingBfd.getBfdId());
		if(contractFilingBfdDb == null) {
			return this.save(user, contractFilingBfd);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractFilingBfd, contractFilingBfdDb);
			this.contractFilingBfdDao.update(contractFilingBfdDb);
			return contractFilingBfdDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param bfdIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String bfdIds) {
		if(StringUtils.isNotBlank(bfdIds)) {
			for(String bfdId : bfdIds.split(",")) {
				ContractEffectFilingBfd contractFilingBfdDb = this.contractFilingBfdDao.get(bfdId);
				this.contractFilingBfdDao.delete(bfdId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param bfdId
	 * @param bfdName
	 * @return
	 */
	public boolean nameIsValid(String bfdId,String bfdName) {
		return this.contractFilingBfdDao.nameIsValid( bfdId, bfdName);
	}

	public List<ContractEffectFilingBfd> getListByFilingId(String filingId) {
		return this.contractFilingBfdDao.findBy("filingId",filingId);
	}

	/**
	 * 根据filing 合同备案id删除资料清单及资料文件信息
	 * @param filingId
	 */
	public void deleteByFilingId(String filingId) {
		this.contractFilingBfdDao.deleteByProperty("filingId",filingId);
		//删除资料文件
		this.filingBfdFService.delteByFilingId(filingId);
	}
}

