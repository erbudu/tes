package com.supporter.prj.ppm.contract.effect.review.service;

import java.util.*;

import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.ppm.contract.effect.report.dao.ContractEffectReportBfdDao;
import com.supporter.prj.ppm.contract.effect.report.dao.ContractEffectReportBfdFDao;
import com.supporter.prj.ppm.contract.effect.report.entity.ContractEffectReportBfd;
import com.supporter.prj.ppm.contract.effect.report.entity.ContractEffectReportBfdF;
import com.supporter.prj.ppm.contract.sign.filing.entity.ContractFiling;
import com.supporter.prj.ppm.contract.sign.filing.service.ContractFilingService;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReport;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReportBfd;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReportBfdF;
import com.supporter.prj.ppm.contract.sign.review.entity.PpmContractSignReview;
import com.supporter.prj.ppm.fileTree.entity.FilesTree;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.contract.effect.report.entity.ContractEffectReport;
import com.supporter.prj.ppm.contract.effect.report.service.ContractEffectReportService;
import com.supporter.prj.ppm.contract.effect.review.dao.PpmContractEffectReviewDao;
import com.supporter.prj.ppm.contract.effect.review.entity.ContractEffectReviewCon;
import com.supporter.prj.ppm.contract.effect.review.entity.PpmContractEffectReview;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;

/**
 * @author YAN
 * @version V1.0
 * @Title: Service
 * @Description: 生效评审表.
 * @date 2019-09-06 18:35:28
 */
@Service("PpmContractEffectReviewService")
public class PpmContractEffectReviewService {

    @Autowired
    private PpmContractEffectReviewDao contractEffectReviewDao;
    @Autowired
    private BaseInfoService prjService;
    @Autowired
    private ContractEffectReportService reportService;
    @Autowired
    private ContractEffectReviewConService contractEffectReviewConService;
    @Autowired
    private ContractFilingService filingService;
    @Autowired
    private ContractEffectReportBfdFDao bfdFDao;
    @Autowired
    private ContractEffectReportBfdDao bfdDao;
    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 根据主键获取生效评审表.
     *
     * @param PpmContractEffectReviewId 主键
     * @return ContractEffectReview
     */
    public PpmContractEffectReview get(String contractEffectReviewId) {
        return contractEffectReviewDao.get(contractEffectReviewId);
    }

    /**
     * 分页表格展示数据.
     *
     * @param user      用户信息
     * @param jqGridReq jqgrid请求对象
     * @param moduleIds 多个逗号分隔
     * @return JqGrid
     */
    public List<PpmContractEffectReview> getGrid(UserProfile user, JqGrid jqGrid, PpmContractEffectReview contractEffectReview) {
        return contractEffectReviewDao.findPage(jqGrid, contractEffectReview);
    }

    /**
     * 进入新建或编辑或查看页面需要加载的信息
     *
     * @param user
     * @param contractEffectReviewId
     * @return
     */
    public PpmContractEffectReview initEditOrViewPage(String contractEffectReviewId) {
        if (StringUtils.isBlank(contractEffectReviewId)) {// 新建
            PpmContractEffectReview entity = new PpmContractEffectReview();
            return entity;
        } else {// 编辑
            PpmContractEffectReview entity = contractEffectReviewDao.get(contractEffectReviewId);
            return entity;
        }
    }

    /**
     * 保存或更新.
     *
     * @param user                 用户信息
     * @param contractEffectReview 实体类
     * @return
     */
    public PpmContractEffectReview saveOrUpdate(UserProfile user, PpmContractEffectReview contractEffectReview) {
        if (StringUtils.isBlank(contractEffectReview.getReviewNo())) {
            String no = "KF";
            no += contractEffectReview.getPrjCode();
            no += "SX";
            no += contractEffectReview.getReviewNumber();
            contractEffectReview.setReviewNo(no);
        }
        if (StringUtils.isBlank(contractEffectReview.getContractEffectReviewId())) {// 新建
            contractEffectReview.setContractEffectReviewId(com.supporter.util.UUIDHex.newId());
            return this.save(user, contractEffectReview);
        } else {// 编辑
            return this.update(user, contractEffectReview);
        }
    }

    /**
     * 保存.
     *
     * @param user                 用户信息
     * @param contractEffectReview 实体类
     * @return
     */
    private PpmContractEffectReview save(UserProfile user, PpmContractEffectReview contractEffectReview) {
        contractEffectReview.setCreatedBy(user.getName());
        contractEffectReview.setCreatedById(user.getPersonId());
        contractEffectReview.setCreatedDate(new Date());
        this.contractEffectReviewDao.save(contractEffectReview);
        return contractEffectReview;
    }

    /**
     * 更新.
     *
     * @param user                 用户信息
     * @param contractEffectReview 实体类
     * @return
     */
    private PpmContractEffectReview update(UserProfile user, PpmContractEffectReview contractEffectReview) {
        contractEffectReview.setModifiedBy(user.getName());
        contractEffectReview.setModifiedById(user.getPersonId());
        contractEffectReview.setModifiedDate(new Date());
        PpmContractEffectReview contractEffectReviewDb = this.contractEffectReviewDao.get(contractEffectReview.getContractEffectReviewId());
        if (contractEffectReviewDb == null) {
            return this.save(user, contractEffectReview);
        } else {
            ModuleUtils.copyPropertiesExcludeNullProperty(contractEffectReview, contractEffectReviewDb);
            this.contractEffectReviewDao.update(contractEffectReviewDb);
            return contractEffectReviewDb;
        }

    }

    /**
     * 删除
     *
     * @param user                    用户信息
     * @param contractEffectReviewIds 主键集合，多个以逗号分隔
     */
    public void delete(UserProfile user, String contractEffectReviewIds) {
        if (StringUtils.isNotBlank(contractEffectReviewIds)) {
            for (String contractEffectReviewId : contractEffectReviewIds.split(",")) {
                PpmContractEffectReview contractEffectReviewDb = this.contractEffectReviewDao.get(contractEffectReviewId);
                this.contractEffectReviewDao.delete(contractEffectReviewId);
            }
        }
    }

    /**
     * 判断名字是否重复
     *
     * @param contractEffectReviewId
     * @param contractEffectReviewName
     * @return
     */
    public boolean nameIsValid(String contractEffectReviewId, String contractEffectReviewName) {
        return this.contractEffectReviewDao.nameIsValid(contractEffectReviewId, contractEffectReviewName);
    }

    /**
     * 根据项目id初始化实体类
     *
     * @param prjId
     * @return
     */
    public PpmContractEffectReview initPageByPrjId(String contractEffectReviewId, String prjId) {
        if (StringUtils.isNotBlank(prjId)) {
            if (StringUtils.isNotBlank(contractEffectReviewId)) {
                PpmContractEffectReview entity = this.contractEffectReviewDao.get(contractEffectReviewId);
                entity = initPrjInfo(entity, prjId);
                return entity;
            } else {
                PpmContractEffectReview entity = new PpmContractEffectReview();
                entity.setContractEffectReviewId(com.supporter.util.UUIDHex.newId());
                //初始化项目信息
                entity = initPrjInfo(entity, prjId);
                return entity;

            }
        } else {
            return new PpmContractEffectReview();
        }
    }

    /**
     * 初始化项目信息
     *
     * @param entity
     * @param prjId
     * @return
     */
    private PpmContractEffectReview initPrjInfo(PpmContractEffectReview entity, String prjId) {
        entity.setPrjId(prjId);
        Prj prj = this.prjService.initEditOrViewPage(prjId, null);
        if (prj != null) {
            entity.setCountryName(prj.getCountryCName());
            entity.setAddress(prj.getAddrC());
            entity.setPrjNature(prj.getPrjNature());
            entity.setPrjName(prj.getPrjCName());
        }
        //初始化合同基本信息
        ContractFiling filing = filingService.getByPrjId(prjId);
        if (filing != null) {
            entity.setContractSignDate(filing.getSignDate());
            entity.setContractAmount(filing.getContractAmount());
            entity.setContractRange(filing.getContractRange());
        }
        return entity;
    }

    public Map<String, Object> validEffect(String prjId, String message, boolean result) {
        //判断项目是否生效
        Prj prj = this.prjService.initBaseInfoView(prjId);
        //如果项目不是生效状态
        if (prj.getStatus() != 1 && prj.getStatus() != 2) {
            message = "该项目尚未生效！";
        }
        //判断是否报审完成
        List<ContractEffectReport> report = this.reportService.getListByPrjId(prjId);
        if (report != null && report.size() > 0) {
            if (report.get(0).getStatus() == 2) {
                result = true;
            } else {
                message = "该项目尚未报审完成！";
            }
        } else {
            message = "该项目尚未进行报审！";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("msg", message);
        map.put("result", result);
        return map;
    }

    public PpmContractEffectReview commit(UserProfile user, PpmContractEffectReview contractEffectReview) {
        String id = contractEffectReview.getContractEffectReviewId();
        PpmContractEffectReview review = this.contractEffectReviewDao.get(id);
        if (review != null) {
            review.setStatus(2);
            this.contractEffectReviewDao.update(review);
            //创建一条评审结论
            ContractEffectReviewCon con = new ContractEffectReviewCon();
            con.setContractEffectRevConId(com.supporter.util.UUIDHex.newId());
            con.setContractEffectReviewId(review.getContractEffectReviewId());
            con.setRvConBussinesStatus(2);
            con.setRvConStatus(1);
            this.contractEffectReviewConService.saveOrUpdate(user, con);
        }
        return review;
    }

    /**
     * 流程处理类更新
     *
     * @param entity 实体类
     * @return
     */
    public PpmContractEffectReview updateSimply(PpmContractEffectReview entity) {
        if (entity != null) {
            contractEffectReviewDao.update(entity);
        }
        return entity;
    }

    /**
     * <p>用印文件列表</p>
     *
     * @param prbidReportId 业务主键
     * @return
     */
    public List<Map<String, String>> getUseSealGrid(String contractEffectReviewId) {
        if (contractEffectReviewId == null || contractEffectReviewId == "") {
            return null;
        }
        String contractEffectId = "";
        PpmContractEffectReview review = this.contractEffectReviewDao.get(contractEffectReviewId);
        if (review != null) {
            String prjId = review.getPrjId();
            ContractEffectReport report = this.reportService.getListByPrjId(prjId).get(0);
            contractEffectId = report.getContractEffectId();
        }
        List<Map<String, String>> li = new ArrayList<Map<String, String>>();
        String hql = "from " + ContractEffectReportBfdF.class.getName() + " where contractEffectId = ? and isUseSeal = ?";
        List<ContractEffectReportBfdF> list = bfdFDao.find(hql, contractEffectId, 1);
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> map = new HashMap<String, String>();
            ContractEffectReportBfd bfd = this.bfdDao.get(list.get(i).getBfdId());
            map.put("sealFileId", UUIDHex.newId());//用印文件主键
            map.put("fileUpBusinessId", list.get(i).getFuFileId());//附件上传用的一级分类id
            map.put("bfdTypeName", bfd.getBfdTypeName());//文件类型
            map.put("fuFileName", fileUploadService.get(list.get(i).getFuFileId()).getFileName());//文件名称
            map.put("fuFileId", list.get(i).getFuFileId());//文件id
            map.put("moduleName", FilesTree.MODULE_ID);//应用编号
            map.put("busiType", FilesTree.BUSI_TYPE);//附件上传业务一级分类
            li.add(map);
        }
        return li;
    }

    /**
     * 确认用印附件
     *
     * @param chkValueStr    用印文件类型id
     * @param contractSignId 报审主键
     */
    public void markUsePrintFile(String chkValueStr, String contractEffectId) {
        if (StringUtils.isNotBlank(chkValueStr) && StringUtils.isNotBlank(contractEffectId)) {
            for (String fileTypeId : chkValueStr.split(",")
            ) {
                String hql = "from " + ContractEffectReportBfd.class.getName() + " where contractEffectId = ? and bfdTypeId = ? ";
                List<ContractEffectReportBfd> bfds = this.bfdDao.find(hql, contractEffectId, fileTypeId);
                if (!CommonUtil.isNullOrEmpty(bfds)) {
                    String bfdId = bfds.get(0).getBfdId();
                    List<ContractEffectReportBfdF> bfdFS = this.bfdFDao.findBy("bfdId", bfdId);
                    if (!CommonUtil.isNullOrEmpty(bfdFS)) {
                        ContractEffectReportBfdF bfdF = bfdFS.get(0);
                        //需要用印
                        bfdF.setIsUseSeal(1);
                        this.bfdFDao.update(bfdF);
                    }
                }
            }
        }
    }
}

