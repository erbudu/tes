package com.supporter.prj.ppm.contract.sign.seal_bfd.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReportBfdF;
import com.supporter.prj.ppm.contract.sign.seal_bfd.entity.ContractSignSealBfdF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.ppm.contract.sign.seal_bfd.dao.ContractSignSealBfdDao;
import com.supporter.prj.ppm.contract.sign.seal_bfd.entity.ContractSignSealBfd;

/**
 * @author YAN
 * @version V1.0
 * @Title: Service
 * @Description: 主合同签约出版资料清单.
 * @date 2019-09-10 14:57:15
 */
@Service
public class ContractSignSealBfdService {

    @Autowired
    private ContractSignSealBfdDao contractSignSealBfdDao;
    @Autowired
    private ContractSignSealBfdFService signSealBfdFService;
    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 根据主键获取主合同签约出版资料清单.
     *
     * @param bfdId 主键
     * @return ContractSignSealBfd
     */
    public ContractSignSealBfd get(String bfdId) {
        return contractSignSealBfdDao.get(bfdId);
    }

    /**
     * 分页表格展示数据.
     *
     * @param user      用户信息
     * @param jqGridReq jqgrid请求对象
     * @param moduleIds 多个逗号分隔
     * @return JqGrid
     */
    public List<ContractSignSealBfd> getGrid(UserProfile user, JqGrid jqGrid, ContractSignSealBfd contractSignSealBfd) {
        return contractSignSealBfdDao.findPage(jqGrid, contractSignSealBfd);
    }

    /**
     * 进入新建或编辑或查看页面需要加载的信息
     *
     * @param user
     * @param bfdId
     * @return
     */
    public ContractSignSealBfd initEditOrViewPage(String bfdId) {
        if (StringUtils.isBlank(bfdId)) {// 新建
            ContractSignSealBfd entity = new ContractSignSealBfd();
            return entity;
        } else {// 编辑
            ContractSignSealBfd entity = contractSignSealBfdDao.get(bfdId);
            return entity;
        }
    }

    /**
     * 保存或更新.
     *
     * @param user                用户信息
     * @param contractSignSealBfd 实体类
     * @return
     */
    public ContractSignSealBfd saveOrUpdate(UserProfile user, ContractSignSealBfd contractSignSealBfd) {
        if (StringUtils.isBlank(contractSignSealBfd.getBfdId())) {// 新建
            contractSignSealBfd = this.save(user, contractSignSealBfd);
        } else {// 编辑
            contractSignSealBfd = this.update(user, contractSignSealBfd);
        }
        //保存或更新文件信息
        this.saveFiles(contractSignSealBfd.getBfdId(), contractSignSealBfd.getContractSignId());
        return contractSignSealBfd;
    }

    /**
     * 保存文件新
     *
     * @param bfdId
     * @param signSealId
     */
    private void saveFiles(String bfdId, String contractSignId) {
        //删除该单据下的资料文件记录
        this.signSealBfdFService.deleteByBfdId(bfdId);

        //保存具体的资料文件
        //根据附件表 获取最新的文件信息并保存
        List<FileUpload> files = fileUploadService.getList("CONTSIGNREP", "SEALBFD" +
                "", bfdId,contractSignId);
        for (FileUpload file : files
        ) {
            ContractSignSealBfdF bfdF = new ContractSignSealBfdF();
            bfdF.setContractSignId(contractSignId);
            bfdF.setBfdId(bfdId);
            bfdF.setFuSealFileId(file.getFileId());
            bfdF.setDisplayOrder(file.getDisplayOrder());
            //bfdF.setIsUseSeal(); 是否需要用印
            this.signSealBfdFService.saveOrUpdate(null, bfdF);
        }

    }

    /**
     * 保存.
     *
     * @param user                用户信息
     * @param contractSignSealBfd 实体类
     * @return
     */
    private ContractSignSealBfd save(UserProfile user, ContractSignSealBfd contractSignSealBfd) {
        this.contractSignSealBfdDao.save(contractSignSealBfd);
        return contractSignSealBfd;
    }

    /**
     * 更新.
     *
     * @param user                用户信息
     * @param contractSignSealBfd 实体类
     * @return
     */
    private ContractSignSealBfd update(UserProfile user, ContractSignSealBfd contractSignSealBfd) {
        ContractSignSealBfd contractSignSealBfdDb = this.contractSignSealBfdDao.get(contractSignSealBfd.getBfdId());
        if (contractSignSealBfdDb == null) {
            return this.save(user, contractSignSealBfd);
        } else {
            ModuleUtils.copyPropertiesExcludeNullProperty(contractSignSealBfd, contractSignSealBfdDb);
            this.contractSignSealBfdDao.update(contractSignSealBfdDb);
            return contractSignSealBfdDb;
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
                ContractSignSealBfd contractSignSealBfdDb = this.contractSignSealBfdDao.get(bfdId);
                this.contractSignSealBfdDao.delete(bfdId);
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
        return this.contractSignSealBfdDao.nameIsValid(bfdId, bfdName);
    }

    /**
     * @return
     */
    public List<ContractSignSealBfd> getListBySignSealId(String signSealId) {
        return this.contractSignSealBfdDao.findBy("signSealId", signSealId);
    }

    public void deleteBySignSealId(String signSealId) {
        List<ContractSignSealBfd> list = getListBySignSealId(signSealId);
        if (list != null && list.size() > 0) {
            this.contractSignSealBfdDao.delete(list);
            for (ContractSignSealBfd bfd : list
            ) {
                //删除对应的附件文件
                signSealBfdFService.deleteByBfdId(bfd.getBfdId());
            }
        }
    }
}

