package com.supporter.prj.ppm.contract.sign.review.service;

import java.util.*;

import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.ppm.bid_startup.preparation.service.BFDService;
import com.supporter.prj.ppm.contract.sign.report.dao.ContractSignReportBfdDao;
import com.supporter.prj.ppm.contract.sign.report.dao.ContractSignReportBfdFDao;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReport;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReportBfd;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReportBfdF;
import com.supporter.prj.ppm.contract.sign.report.service.ContractSignReportService;
import com.supporter.prj.ppm.fileTree.entity.FilesTree;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReportBfd;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReportBfdF;
import com.supporter.prj.ppm.util.PpmSyncFilesUtil;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.contract.sign.review.dao.PpmContractSignReviewDao;
import com.supporter.prj.ppm.contract.sign.review.entity.ContractSignReviewCon;
import com.supporter.prj.ppm.contract.sign.review.entity.PpmContractSignReview;
import com.supporter.prj.ppm.prj_org.base_info.dao.PrjSofDao;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.entity.PrjSof;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;

/**
 * @author YAN
 * @version V1.0
 * @Title: Service
 * @Description: 签约评审表.
 * @date 2019-09-06 18:35:28
 */
@Service("PpmContractSignReviewService")
public class PpmContractSignReviewService {

    @Autowired
    private PpmContractSignReviewDao contractSignReviewDao;
    @Autowired
    private BaseInfoService prjService;
    @Autowired
    private PrjSofDao prjSofDao;//项目资金来源
    @Autowired
    private ContractSignReviewConService contractSignReviewConService;
    @Autowired
    private ContractSignReportBfdFDao bfdFDao;
    @Autowired
    private ContractSignReportBfdDao bfdDao;
    @Autowired
    private ContractSignReportService reportService;
    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 根据主键获取签约评审表.
     *
     * @param PpmContractSignReviewId 主键
     * @return ContractSignReview
     */
    public PpmContractSignReview get(String contractSignReviewId) {
        return contractSignReviewDao.get(contractSignReviewId);
    }

    /**
     * 分页表格展示数据.
     *
     * @param user      用户信息
     * @param jqGridReq jqgrid请求对象
     * @param moduleIds 多个逗号分隔
     * @return JqGrid
     */
    public List<PpmContractSignReview> getGrid(UserProfile user, JqGrid jqGrid, PpmContractSignReview contractSignReview) {
        return contractSignReviewDao.findPage(jqGrid, contractSignReview);
    }

    /**
     * 获取需要评审验证的评审列表
     *
     * @param user
     * @param jqGrid
     * @param contractSignReview
     */
    public List<PpmContractSignReview> getVerGrid(UserProfile user, JqGrid jqGrid,
                                                  PpmContractSignReview contractSignReview) {
        return contractSignReviewDao.findPageTwo(jqGrid, contractSignReview);

    }

    /**
     * 进入新建或编辑或查看页面需要加载的信息
     *
     * @param user
     * @param contractSignReviewId
     * @return
     */
    public PpmContractSignReview initEditOrViewPage(String contractSignReviewId) {
        if (StringUtils.isBlank(contractSignReviewId)) {// 新建
            PpmContractSignReview entity = new PpmContractSignReview();
            return entity;
        } else {// 编辑
            PpmContractSignReview entity = contractSignReviewDao.get(contractSignReviewId);
            return entity;
        }
    }

    /**
     * 保存或更新.
     *
     * @param user               用户信息
     * @param contractSignReview 实体类
     * @return
     */
    public PpmContractSignReview saveOrUpdate(UserProfile user, PpmContractSignReview contractSignReview) {
        if (StringUtils.isBlank(contractSignReview.getReviewNo())) {
            String no = "KF";
            no += contractSignReview.getPrjCode();
            no += "QY";
            no += contractSignReview.getReviewNumber();
            contractSignReview.setReviewNo(no);
        }
        if (contractSignReview.getStatus() != 0) {
            contractSignReview.setReviewSubmitDate(new Date());
        }
        if (StringUtils.isBlank(contractSignReview.getContractSignReviewId())) {// 新建
            return this.save(user, contractSignReview);
        } else {// 编辑
            return this.update(user, contractSignReview);
        }
    }

    /**
     * 保存.
     *
     * @param user               用户信息
     * @param contractSignReview 实体类
     * @return
     */
    private PpmContractSignReview save(UserProfile user, PpmContractSignReview contractSignReview) {
        contractSignReview.setCreatedBy(user.getName());
        contractSignReview.setCreatedById(user.getPersonId());
        contractSignReview.setCreatedDate(new Date());
        this.contractSignReviewDao.save(contractSignReview);
        //当是新建的时候 复制报审的资料清单附件
        System.out.println("复制附件");
        syncFiles(contractSignReview, user);
        return contractSignReview;
    }

    private void syncFiles(PpmContractSignReview contractSignReview, UserProfile user) {
        //获取申报对象
        ContractSignReport report = this.reportService.initPageByPrjId(contractSignReview.getPrjId());
        //根据申报对象获取资料清单
        List<ContractSignReportBfd> bfds = report.getBfds();
        System.out.println("获取资料清单");
        if (CommonUtil.isNullOrEmpty(bfds)) {
            return;
        }
        System.out.println("获取资料清单"+bfds.size());
        for (ContractSignReportBfd bfd : bfds
        ) {
            PpmSyncFilesUtil.syncFiles(FilesTree.MODULE_ID, FilesTree.BUSI_TYPE, report.getContractSignId(),
                    bfd.getBfdTypeId(), FilesTree.MODULE_ID, FilesTree.BUSI_TYPE, contractSignReview.getContractSignReviewId(),
                    bfd.getBfdTypeId(), user);
        }
    }

    /**
     * 更新.
     *
     * @param user               用户信息
     * @param contractSignReview 实体类
     * @return
     */
    private PpmContractSignReview update(UserProfile user, PpmContractSignReview contractSignReview) {
        contractSignReview.setModifiedBy(user.getName());
        contractSignReview.setCreatedById(user.getPersonId());
        contractSignReview.setModifiedDate(new Date());
        PpmContractSignReview contractSignReviewDb = this.contractSignReviewDao.get(contractSignReview.getContractSignReviewId());
        if (contractSignReviewDb == null) {
            return this.save(user, contractSignReview);
        } else {
            ModuleUtils.copyPropertiesExcludeNullProperty(contractSignReview, contractSignReviewDb);
            this.contractSignReviewDao.update(contractSignReviewDb);
            return contractSignReviewDb;
        }

    }

    /**
     * 删除
     *
     * @param user                  用户信息
     * @param contractSignReviewIds 主键集合，多个以逗号分隔
     */
    public void delete(UserProfile user, String contractSignReviewIds) {
        if (StringUtils.isNotBlank(contractSignReviewIds)) {
            for (String contractSignReviewId : contractSignReviewIds.split(",")) {
                PpmContractSignReview contractSignReviewDb = this.contractSignReviewDao.get(contractSignReviewId);
                this.contractSignReviewDao.delete(contractSignReviewId);
            }
        }
    }

    /**
     * 判断名字是否重复
     *
     * @param contractSignReviewId
     * @param contractSignReviewName
     * @return
     */
    public boolean nameIsValid(String contractSignReviewId, String contractSignReviewName) {
        return this.contractSignReviewDao.nameIsValid(contractSignReviewId, contractSignReviewName);
    }

    /**
     * 根据项目id初始化实体类
     *
     * @param prjId
     * @return
     */
    public PpmContractSignReview initPageByPrjId(String prjId, String contractSignReviewId) {
        if (StringUtils.isNotBlank(prjId)) {
            //List<PpmContractSignReview> list = this.contractSignReviewDao.findBy("prjId",prjId);
            PpmContractSignReview entity = null;
            if (StringUtils.isNotBlank(contractSignReviewId)) {
                entity = this.get(contractSignReviewId);
            } else {
                entity = new PpmContractSignReview();
                entity.setContractSignReviewId(com.supporter.util.UUIDHex.newId());
            }
            //初始化项目信息
            entity = initPrjInfo(entity, prjId);
            return entity;
        } else {
            return new PpmContractSignReview();
        }
    }

    /**
     * 初始化项目信息
     *
     * @param entity
     * @param prjId
     * @return
     */
    private PpmContractSignReview initPrjInfo(PpmContractSignReview entity, String prjId) {
        entity.setPrjId(prjId);
        Prj prj = this.prjService.initEditOrViewPage(prjId, null);
        if (entity != null) {
            entity.setCountryName(prj.getCountryCName());
            entity.setAddress(prj.getAddrC());
            entity.setPrjName(prj.getPrjCName());
            entity.setPrjNature(prj.getPrjNature());
            //资金来源赋值
            List<PrjSof> sofs = this.prjSofDao.findBy("projectId", prjId);

            if (sofs != null && sofs.size() > 0) {
                for (PrjSof sof : sofs
                ) {
                    //
                    if (sof.getSofType().indexOf("商业贷款") > 0) {
                        entity.setShangye(sof.getRatio() + "%");
                    } else if (sof.getSofType().indexOf("政府优惠贷款") > 0) {
                        entity.setZhengfu(sof.getRatio() + "%");
                    } else if (sof.getSofType().indexOf("国际金融组织贷款") > 0) {
                        entity.setGuoji(sof.getRatio() + "%");
                    } else {
                        entity.setZichou(sof.getRatio() + "%");
                    }
                }
            }

        }
        return entity;
    }

    public PpmContractSignReview commit(UserProfile user, PpmContractSignReview contractSignReview) {
        String id = contractSignReview.getContractSignReviewId();
        PpmContractSignReview entity = this.get(id);
        if (entity != null) {
            entity.setStatus(2);
        }
        entity = this.update(user, entity);
        //保存审核结果
        ContractSignReviewCon con = new ContractSignReviewCon();
        con.setContractSignRevConId(com.supporter.util.UUIDHex.newId());
        con.setContractSignReviewId(id);
        con.setRvConStatus(1);
        // con.setRvConBussinesStatus(1);
        // contractSignReviewConService.saveOrUpdate(user,con);
        return entity;
    }

    /**
     * 流程处理类更新
     *
     * @param entity 实体类
     * @return
     */
    public PpmContractSignReview updateSimply(PpmContractSignReview entity) {
        if (entity != null) {
            contractSignReviewDao.update(entity);
        }
        return entity;
    }
    /**
     * <p>用印文件列表</p>
     * @param prbidReportId 业务主键
     * @return
     */
    public List<Map<String, String>> getUseSealGrid(String contractSignReviewId) {
        if(contractSignReviewId == null || contractSignReviewId == "") {
            return null;
        }
        String contractSignId = "";
        PpmContractSignReview review = this.contractSignReviewDao.get(contractSignReviewId);
        if (review!=null){
            String prjId = review.getPrjId();
            ContractSignReport report = this.reportService.getListByPrjId(prjId).get(0);
            contractSignId = report.getContractSignId();
        }
        List<Map<String,String>> li = new ArrayList<Map<String,String>>();
        String hql = "from "+ ContractSignReportBfdF.class.getName()+" where contractSignId = ? and isUseSeal = ?";
        List<ContractSignReportBfdF> list = bfdFDao.find(hql,contractSignId,1);
        for(int i = 0;i<list.size();i++) {
            Map<String,String> map = new HashMap<String, String>();
            ContractSignReportBfd bfd = this.bfdDao.get(list.get(i).getBfdId());
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
     * @param chkValueStr 用印文件类型id
     * @param contractSignId 报审主键
     */
    public void markUsePrintFile(String chkValueStr, String contractSignId) {
        if (StringUtils.isNotBlank(chkValueStr)&&StringUtils.isNotBlank(contractSignId)){
            for (String fileTypeId:chkValueStr.split(",")
                 ) {
                String hql = "from "+ContractSignReportBfd.class.getName() +" where contractSignId = ? and bfdTypeId = ? ";
                List<ContractSignReportBfd> bfds = this.bfdDao.find(hql,contractSignId,fileTypeId);
                if (!CommonUtil.isNullOrEmpty(bfds)){
                    String bfdId = bfds.get(0).getBfdId();
                    List<ContractSignReportBfdF> bfdFS = this.bfdFDao.findBy("bfdId",bfdId);
                    if (!CommonUtil.isNullOrEmpty(bfdFS)){
                        ContractSignReportBfdF bfdF = bfdFS.get(0);
                        //需要用印
                        bfdF.setIsUseSeal(1);
                        this.bfdFDao.update(bfdF);
                    }
                }
            }
        }
    }
}

