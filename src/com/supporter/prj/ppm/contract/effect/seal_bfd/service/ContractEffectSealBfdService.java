package com.supporter.prj.ppm.contract.effect.seal_bfd.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.ppm.contract.effect.report.entity.ContractEffectReportBfdF;
import com.supporter.prj.ppm.contract.effect.seal_bfd.entity.ContractEffectSealBfdF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.ppm.contract.effect.seal_bfd.dao.ContractEffectSealBfdDao;
import com.supporter.prj.ppm.contract.effect.seal_bfd.entity.ContractEffectSealBfd;

/**
 * @author YAN
 * @version V1.0
 * @Title: Service
 * @Description: 主合同签约出版资料清单.
 * @date 2019-09-10 14:57:15
 */
@Service
public class ContractEffectSealBfdService {

    @Autowired
    private ContractEffectSealBfdDao contractEffectSealBfdDao;
    @Autowired
    private ContractEffectSealBfdFService effectSealBfdFService;
    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 根据主键获取主合同签约出版资料清单.
     *
     * @param bfdId 主键
     * @return ContractEffectSealBfd
     */
    public ContractEffectSealBfd get(String bfdId) {
        return contractEffectSealBfdDao.get(bfdId);
    }

    /**
     * 分页表格展示数据.
     *
     * @param user      用户信息
     * @param jqGridReq jqgrid请求对象
     * @param moduleIds 多个逗号分隔
     * @return JqGrid
     */
    public List<ContractEffectSealBfd> getGrid(UserProfile user, JqGrid jqGrid, ContractEffectSealBfd contractEffectSealBfd) {
        return contractEffectSealBfdDao.findPage(jqGrid, contractEffectSealBfd);
    }

    /**
     * 进入新建或编辑或查看页面需要加载的信息
     *
     * @param user
     * @param bfdId
     * @return
     */
    public ContractEffectSealBfd initEditOrViewPage(String bfdId) {
        if (StringUtils.isBlank(bfdId)) {// 新建
            ContractEffectSealBfd entity = new ContractEffectSealBfd();
            return entity;
        } else {// 编辑
            ContractEffectSealBfd entity = contractEffectSealBfdDao.get(bfdId);
            return entity;
        }
    }

    /**
     * 保存或更新.
     *
     * @param user                用户信息
     * @param contractEffectSealBfd 实体类
     * @return
     */
    public ContractEffectSealBfd saveOrUpdate(UserProfile user, ContractEffectSealBfd contractEffectSealBfd) {
        if (StringUtils.isBlank(contractEffectSealBfd.getBfdId())) {// 新建
            contractEffectSealBfd = this.save(user, contractEffectSealBfd);
        } else {// 编辑
            contractEffectSealBfd = this.update(user, contractEffectSealBfd);
        }
        //保存或更新文件信息
        this.saveFiles(contractEffectSealBfd.getBfdId(), contractEffectSealBfd.getContractEffectId());
        return contractEffectSealBfd;
    }

    /**
     * 保存文件新
     *
     * @param bfdId
     * @param effectSealId
     */
    private void saveFiles(String bfdId, String contractEffectId) {
        //删除该单据下的资料文件记录
        this.effectSealBfdFService.deleteByBfdId(bfdId);

        //保存具体的资料文件
        //根据附件表 获取最新的文件信息并保存
        List<FileUpload> files = fileUploadService.getList("CONTEFFECTREP", "SEALBFD" +
                "", bfdId,contractEffectId);
        for (FileUpload file : files
        ) {
            ContractEffectSealBfdF bfdF = new ContractEffectSealBfdF();
            bfdF.setContractEffectId(contractEffectId);
            bfdF.setBfdId(bfdId);
            bfdF.setFuSealFileId(file.getFileId());
            bfdF.setDisplayOrder(file.getDisplayOrder());
            //bfdF.setIsUseSeal(); 是否需要用印
            this.effectSealBfdFService.saveOrUpdate(null, bfdF);
        }

    }

    /**
     * 保存.
     *
     * @param user                用户信息
     * @param contractEffectSealBfd 实体类
     * @return
     */
    private ContractEffectSealBfd save(UserProfile user, ContractEffectSealBfd contractEffectSealBfd) {
        this.contractEffectSealBfdDao.save(contractEffectSealBfd);
        return contractEffectSealBfd;
    }

    /**
     * 更新.
     *
     * @param user                用户信息
     * @param contractEffectSealBfd 实体类
     * @return
     */
    private ContractEffectSealBfd update(UserProfile user, ContractEffectSealBfd contractEffectSealBfd) {
        ContractEffectSealBfd contractEffectSealBfdDb = this.contractEffectSealBfdDao.get(contractEffectSealBfd.getBfdId());
        if (contractEffectSealBfdDb == null) {
            return this.save(user, contractEffectSealBfd);
        } else {
            ModuleUtils.copyPropertiesExcludeNullProperty(contractEffectSealBfd, contractEffectSealBfdDb);
            this.contractEffectSealBfdDao.update(contractEffectSealBfdDb);
            return contractEffectSealBfdDb;
        }

    }

    /**
     * 删除
     *
     * @param user   用户信息
     * @param bfdIds 主键集合，多个以逗号分隔
     */
    public void delete(UserProfile user, String bfdIds) {
        if (StringUtils.isNotBlank(bfdIds)) {
            for (String bfdId : bfdIds.split(",")) {
                ContractEffectSealBfd contractEffectSealBfdDb = this.contractEffectSealBfdDao.get(bfdId);
                this.contractEffectSealBfdDao.delete(bfdId);
            }
        }
    }

    /**
     * 判断名字是否重复
     *
     * @param bfdIdreview_verStart
     * @param bfdName
     * @return
     */
    public boolean nameIsValid(String bfdId, String bfdName) {
        return this.contractEffectSealBfdDao.nameIsValid(bfdId, bfdName);
    }

    /**
     * @return
     */
    public List<ContractEffectSealBfd> getListByEffectSealId(String effectSealId) {
        return this.contractEffectSealBfdDao.findBy("effectSealId", effectSealId);
    }

    public void deleteByEffectSealId(String effectSealId) {
        List<ContractEffectSealBfd> list = getListByEffectSealId(effectSealId);
        if (list != null && list.size() > 0) {
            this.contractEffectSealBfdDao.delete(list);
            for (ContractEffectSealBfd bfd : list
            ) {
                //删除对应的附件文件
                effectSealBfdFService.deleteByBfdId(bfd.getBfdId());
            }
        }
    }
}

