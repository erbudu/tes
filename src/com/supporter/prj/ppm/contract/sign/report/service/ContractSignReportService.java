package com.supporter.prj.ppm.contract.sign.report.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.contract.sign.report.dao.ContractSignReportDao;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReport;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReportBfd;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReportBfdF;
import com.supporter.prj.ppm.prebid.report.dao.ProjectReportDao;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReport;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;

/**
 * @author YAN
 * @version V1.0
 * @Title: Service
 * @Description: 主合同签约报审表.
 * @date 2019-09-05 17:09:55
 */
@Service
public class ContractSignReportService {

    @Autowired
    private ContractSignReportDao contractSignReportDao;
    @Autowired
    private ContractSignReportBfdService signReportBfdService;
    @Autowired
    private ContractSignReportBfdFService signReportBfdFService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private BaseInfoService prjService;
    @Autowired
    private ProjectReportDao projectReportDao;

    /**
     * 根据主键获取主合同签约报审表.
     *
     * @param contractSignId 主键
     * @return ContractSignReport
     */
    public ContractSignReport get(String contractSignId) {
        return contractSignReportDao.get(contractSignId);
    }

    /**
     * 分页表格展示数据.
     *
     * @param user      用户信息
     * @param jqGridReq jqgrid请求对象
     * @param moduleIds 多个逗号分隔
     * @return JqGrid
     */
    public List<ContractSignReport> getGrid(UserProfile user, JqGrid jqGrid, ContractSignReport contractSignReport) {
        return contractSignReportDao.findPage(jqGrid, contractSignReport);
    }

    /**
     * 进入新建或编辑或查看页面需要加载的信息
     *
     * @param user
     * @param contractSignId
     * @return
     */
    public ContractSignReport initEditOrViewPage(String contractSignId) {
        if (StringUtils.isBlank(contractSignId)) {// 新建
            ContractSignReport entity = new ContractSignReport();
            return entity;
        } else {// 编辑
            ContractSignReport entity = contractSignReportDao.get(contractSignId);
            return entity;
        }
    }

    /**
     * 保存或更新.
     *
     * @param user               用户信息
     * @param contractSignReport 实体类
     * @return
     */
    public ContractSignReport saveOrUpdate(UserProfile user, ContractSignReport contractSignReport) {
        if (StringUtils.isBlank(contractSignReport.getContractSignId())) {// 新建
            return this.save(user, contractSignReport);
        } else {// 编辑
            return this.update(user, contractSignReport);
        }
    }

    /**
     * 保存.
     *
     * @param user               用户信息
     * @param contractSignReport 实体类
     * @return
     */
    private ContractSignReport save(UserProfile user, ContractSignReport contractSignReport) {
        contractSignReport.setCreatedBy(user.getName());
        contractSignReport.setCreatedById(user.getPersonId());
        contractSignReport.setCreatedDate(new Date());
        this.contractSignReportDao.save(contractSignReport);
        return contractSignReport;
    }

    /**
     * 更新.
     *
     * @param user               用户信息
     * @param contractSignReport 实体类
     * @return
     */
    private ContractSignReport update(UserProfile user, ContractSignReport contractSignReport) {
        contractSignReport.setModifiedBy(user.getName());
        contractSignReport.setCreatedById(user.getPersonId());
        contractSignReport.setModifiedDate(new Date());
        ContractSignReport contractSignReportDb = this.contractSignReportDao.get(contractSignReport.getContractSignId());
        if (contractSignReportDb == null) {
            this.save(user, contractSignReport);
            contractSignReportDb = contractSignReport;
        } else {
            ModuleUtils.copyPropertiesExcludeNullProperty(contractSignReport, contractSignReportDb);
            this.contractSignReportDao.update(contractSignReportDb);
        }
        //保存资料清单
        List<ContractSignReportBfd> bfds = contractSignReport.getBfds();
        //删除该单据下的资料文件记录
        this.signReportBfdFService.deleteByContractSignId(contractSignReport.getContractSignId());
        for (ContractSignReportBfd bfd : bfds
        ) {
            bfd = this.signReportBfdService.saveOrUpdate(user, bfd);
            //保存具体的资料文件
            //根据附件表 获取最新的文件信息并保存
            List<FileUpload> files = fileUploadService.getList("PpmFileTree", "FilesTree",  bfd.getContractSignId(),bfd.getBfdTypeId());
            for (FileUpload file:files
                 ) {
                ContractSignReportBfdF bfdF = new ContractSignReportBfdF();
                bfdF.setContractSignId(bfd.getContractSignId());
                bfdF.setBfdId(bfd.getBfdId());
                bfdF.setFuFileId(file.getFileId());
                bfdF.setDisplayOrder(file.getDisplayOrder());
                //bfdF.setIsUseSeal(); 是否需要用印
                this.signReportBfdFService.saveOrUpdate(user,bfdF);
            }
        }

        return contractSignReportDb;
    }

    /**
     * 删除
     *
     * @param user            用户信息
     * @param contractSignIds 主键集合，多个以逗号分隔
     */
    public void delete(UserProfile user, String contractSignIds) {
        if (StringUtils.isNotBlank(contractSignIds)) {
            for (String contractSignId : contractSignIds.split(",")) {
                ContractSignReport contractSignReportDb = this.contractSignReportDao.get(contractSignId);
                this.contractSignReportDao.delete(contractSignId);
            }
        }
    }

    /**
     * 判断名字是否重复
     *
     * @param contractSignId
     * @param contractSignName
     * @return
     */
    public boolean nameIsValid(String contractSignId, String contractSignName) {
        return this.contractSignReportDao.nameIsValid(contractSignId, contractSignName);
    }

    /**
     * 根据项目id初始化 获得实体类
     *
     * @param prjId
     * @return
     */
    public ContractSignReport initPageByPrjId(String prjId) {
        ContractSignReport entity;
        entity = getEntityByPrjId(prjId);
        //初始化资料清单列表
        List<ContractSignReportBfd> bfds = initBfdsByReport(entity.getContractSignId());
        entity.setBfds(bfds);
        return entity;
    }

    //初始化资料清单类型
    private List<ContractSignReportBfd> initBfdsByReport(String contractSignId) {
        List<ContractSignReportBfd> list = this.signReportBfdService.getListBySignReportId(contractSignId);
        if (list == null || list.size() < 1) {
            list = new ArrayList<ContractSignReportBfd>();
            //根据码表获取对应的需求
            List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PPM_CONTRACT_SIGN_BFD");
            for (IComCodeTableItem item : items
            ) {
                ContractSignReportBfd bfd = new ContractSignReportBfd();
                bfd.setBfdTypeId(item.getItemId());
                bfd.setBfdTypeName(item.getItemValue());
                bfd.setContractSignId(contractSignId);
                bfd.setBfdId(com.supporter.util.UUIDHex.newId());
                list.add(bfd);
            }
            return list;
        }
        return list;
    }

    /**
     * 根据项目id获取列表
     *
     * @param prjId
     * @return
     */
    public List<ContractSignReport> getListByPrjId(String prjId) {
        return this.contractSignReportDao.findBy("prjId", prjId);
    }

    /**
     * 初始化项目相关信息
     *
     * @param prjId
     * @param entity
     * @return
     */
    private ContractSignReport initPrjInfo(String prjId, ContractSignReport entity) {
        entity.setPrjId(prjId);
        Prj prj = this.prjService.initBaseInfoView(prjId);
        if (prj!=null){
            entity.setPrjNo(prj.getPrjNo());
            entity.setPrjNameEn(prj.getPrjEName());
            entity.setPrjNameZh(prj.getPrjCName());
        }
        //初始化项目估算金额
        List<PpmPrebidReport> list = this.projectReportDao.findBy("prjId",prjId);
        //如果估算金额为0
        if (entity.getPrjPlanAmount() ==0){
            if (list!=null&&list.size()>0){
                // 投标前评审修改PrjPlanAmount字段类型为Double
                // Integer amount = list.get(0).getPrjPlanAmount();
                // if (amount!=null){
                entity.setPrjPlanAmount(list.get(0).getPrjPlanAmount());
                // }
            }
        }
        return entity;
    }

    /**
     * 根据项目id获取对象
     * @param prjId
     * @return
     */
    public ContractSignReport getEntityByPrjId(String prjId) {
        ContractSignReport entity = null;
        if (StringUtils.isNotBlank(prjId)) {
            List<ContractSignReport> list = getListByPrjId(prjId);
            if (list != null && list.size() > 0) {
                entity = list.get(0);
            }else{
                entity = new ContractSignReport();
                entity.setContractSignId(com.supporter.util.UUIDHex.newId());
            }
        }else{
            entity = new ContractSignReport();
            entity.setContractSignId(com.supporter.util.UUIDHex.newId());
        }

        entity = initPrjInfo(prjId, entity);

        return  entity;
    }

    public Map<String, Object> validSign(String prjId, String message, boolean result) {
        ContractSignReport report = null;
        List<ContractSignReport> list = getListByPrjId(prjId);
        if (list != null && list.size() > 0) {
            report = list.get(0);
            if (report.getStatus()!=2){
                message = "该项目签约报审流程未完成！";
            }else{
                result = true ;
            }
        }else{
            message = "该项目尚未进行签约报审！";
        }
        Map <String ,Object> map = new HashMap<String, Object>();
        map.put("msg",message);
        map.put("result",result);
        return map;
    }

	/**
	* 流程处理类更新
	* @param entity 实体类
	* @return
	*/
	public ContractSignReport updateSimply(ContractSignReport entity) {
		if (entity != null) {
			contractSignReportDao.update(entity);
		}
		return entity;
	}
}

