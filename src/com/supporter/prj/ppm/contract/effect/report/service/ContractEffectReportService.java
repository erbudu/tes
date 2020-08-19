package com.supporter.prj.ppm.contract.effect.report.service;

import java.util.*;

import com.supporter.prj.ppm.fileTree.entity.FilesTree;
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
import com.supporter.prj.ppm.contract.effect.report.dao.ContractEffectReportDao;
import com.supporter.prj.ppm.contract.effect.report.entity.ContractEffectReport;
import com.supporter.prj.ppm.contract.effect.report.entity.ContractEffectReportBfd;
import com.supporter.prj.ppm.contract.effect.report.entity.ContractEffectReportBfdF;
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
public class ContractEffectReportService {

    @Autowired
    private ContractEffectReportDao contractEffectReportDao;
    @Autowired
    private ContractEffectReportBfdService effectReportBfdService;
    @Autowired
    private ContractEffectReportBfdFService effectReportBfdFService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private BaseInfoService prjService;

    /**
     * 根据主键获取主合同签约报审表.
     *
     * @param contractEffectId 主键
     * @return ContractEffectReport
     */
    public ContractEffectReport get(String contractEffectId) {
        return contractEffectReportDao.get(contractEffectId);
    }

    /**
     * 分页表格展示数据.
     *
     * @param user      用户信息
     * @param jqGridReq jqgrid请求对象
     * @param moduleIds 多个逗号分隔
     * @return JqGrid
     */
    public List<ContractEffectReport> getGrid(UserProfile user, JqGrid jqGrid, ContractEffectReport contractEffectReport) {
        return contractEffectReportDao.findPage(jqGrid, contractEffectReport);
    }

    /**
     * 进入新建或编辑或查看页面需要加载的信息
     *
     * @param user
     * @param contractEffectId
     * @return
     */
    public ContractEffectReport initEditOrViewPage(String contractEffectId) {
        if (StringUtils.isBlank(contractEffectId)) {// 新建
            ContractEffectReport entity = new ContractEffectReport();
            return entity;
        } else {// 编辑
            ContractEffectReport entity = contractEffectReportDao.get(contractEffectId);
            return entity;
        }
    }

    /**
     * 保存或更新.
     *
     * @param user               用户信息
     * @param contractEffectReport 实体类
     * @return
     */
    public ContractEffectReport saveOrUpdate(UserProfile user, ContractEffectReport contractEffectReport) {
        if (StringUtils.isBlank(contractEffectReport.getContractEffectId())) {// 新建
            return this.save(user, contractEffectReport);
        } else {// 编辑
            return this.update(user, contractEffectReport);
        }
    }

    /**
     * 保存.
     *
     * @param user               用户信息
     * @param contractEffectReport 实体类
     * @return
     */
    private ContractEffectReport save(UserProfile user, ContractEffectReport contractEffectReport) {
        contractEffectReport.setCreatedBy(user.getName());
        contractEffectReport.setCreatedById(user.getPersonId());
        contractEffectReport.setCreatedDate(new Date());
        this.contractEffectReportDao.save(contractEffectReport);
        return contractEffectReport;
    }

    /**
     * 更新.
     *
     * @param user               用户信息
     * @param contractEffectReport 实体类
     * @return
     */
    private ContractEffectReport update(UserProfile user, ContractEffectReport contractEffectReport) {
        contractEffectReport.setModifiedBy(user.getName());
        contractEffectReport.setModifiedById(user.getPersonId());
        contractEffectReport.setModifiedDate(new Date());
        ContractEffectReport contractEffectReportDb = this.contractEffectReportDao.get(contractEffectReport.getContractEffectId());
        if (contractEffectReportDb == null) {
            this.save(user, contractEffectReport);
            contractEffectReportDb = contractEffectReport;
        } else {
            ModuleUtils.copyPropertiesExcludeNullProperty(contractEffectReport, contractEffectReportDb);
            this.contractEffectReportDao.update(contractEffectReportDb);
        }
        //保存资料清单
        List<ContractEffectReportBfd> bfds = contractEffectReport.getBfds();
        //删除该单据下的资料文件记录
        this.effectReportBfdFService.deleteByContractEffectId(contractEffectReport.getContractEffectId());
        for (ContractEffectReportBfd bfd : bfds
        ) {
            bfd = this.effectReportBfdService.saveOrUpdate(user, bfd);
            //保存具体的资料文件
            //根据附件表 获取最新的文件信息并保存
            List<FileUpload> files = fileUploadService.getList(FilesTree.MODULE_ID, FilesTree.BUSI_TYPE, bfd.getContractEffectId(), bfd.getBfdTypeId());
            for (FileUpload file:files
                 ) {
                ContractEffectReportBfdF bfdF = new ContractEffectReportBfdF();
                bfdF.setContractEffectId(bfd.getContractEffectId());
                bfdF.setBfdId(bfd.getBfdId());
                bfdF.setFuFileId(file.getFileId());
                bfdF.setDisplayOrder(file.getDisplayOrder());
                //bfdF.setIsUseSeal(); 是否需要用印
                this.effectReportBfdFService.saveOrUpdate(user,bfdF);
            }
        }
        return contractEffectReportDb;
    }

    /**
     * 删除
     *
     * @param user            用户信息
     * @param contractEffectIds 主键集合，多个以逗号分隔
     */
    public void delete(UserProfile user, String contractEffectIds) {
        if (StringUtils.isNotBlank(contractEffectIds)) {
            for (String contractEffectId : contractEffectIds.split(",")) {
                ContractEffectReport contractEffectReportDb = this.contractEffectReportDao.get(contractEffectId);
                this.contractEffectReportDao.delete(contractEffectId);
            }
        }
    }

    /**
     * 判断名字是否重复
     *
     * @param contractEffectId
     * @param contractEffectName
     * @return
     */
    public boolean nameIsValid(String contractEffectId, String contractEffectName) {
        return this.contractEffectReportDao.nameIsValid(contractEffectId, contractEffectName);
    }

    /**
     * 根据项目id初始化 获得实体类
     *
     * @param prjId
     * @return
     */
    public ContractEffectReport initPageByPrjId(String prjId) {
        ContractEffectReport entity;
        entity = getEntityByPrjId(prjId);
        //初始化资料清单列表
        List<ContractEffectReportBfd> bfds = initBfdsByReport(entity.getContractEffectId());
        entity.setBfds(bfds);
        return entity;
    }

    //初始化资料清单类型
    private List<ContractEffectReportBfd> initBfdsByReport(String contractEffectId) {
        List<ContractEffectReportBfd> list = this.effectReportBfdService.getListBtEffectReportId(contractEffectId);
        if (list == null || list.size() < 1) {
            list = new ArrayList<ContractEffectReportBfd>();
            //根据码表获取对应的需求
            List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PPM_CONTRACT_EFFECT_BFD");
            for (IComCodeTableItem item : items
            ) {
                ContractEffectReportBfd bfd = new ContractEffectReportBfd();
                bfd.setBfdTypeId(item.getItemId());
                bfd.setBfdTypeName(item.getItemValue());
                bfd.setContractEffectId(contractEffectId);
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
    public List<ContractEffectReport> getListByPrjId(String prjId) {
        return this.contractEffectReportDao.findBy("prjId", prjId);
    }

    /**
     * 初始化项目相关信息
     *
     * @param prjId
     * @param entity
     * @return
     */
    private ContractEffectReport initPrjInfo(String prjId, ContractEffectReport entity) {
        entity.setPrjId(prjId);
        Prj prj = this.prjService.initBaseInfoView(prjId);
        if (prj!=null){
            entity.setPrjNameZh(prj.getPrjCName());
            entity.setPrjNameEn(prj.getPrjEName());
            entity.setPrjNo(prj.getPrjNo());
        }
        return entity;
    }

    /**
     * 根据项目id获取对象
     * @param prjId
     * @return
     */
    public ContractEffectReport getEntityByPrjId(String prjId) {
        ContractEffectReport entity;
        if (StringUtils.isNotBlank(prjId)) {
            List<ContractEffectReport> list = getListByPrjId(prjId);
            if (list != null && list.size() > 0) {
                entity = list.get(0);
                entity = initPrjInfo(prjId, entity);
                return entity;
            }
        }

        entity = new ContractEffectReport();
        entity.setContractEffectId(com.supporter.util.UUIDHex.newId());
        entity = initPrjInfo(prjId, entity);

        return  entity;
    }

    public Map<String, Object> validEffect(String prjId, String message, boolean result) {
        //判断项目是否生效
        Prj prj = this.prjService.initBaseInfoView(prjId);
        String nextPage = "view";
        //如果项目不是生效状态
        if (prj.getStatus()!=1&&prj.getStatus()!=2){
            message = "该项目尚未生效！";
        }else{
            //判断主合同没有变更并且从签订日期到生效日期未超过12个自然月
            if (false){
                message  = "该项目可直接生效！" ;
            }else {
                result = true ;
            }
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
	public ContractEffectReport updateSimply(ContractEffectReport entity) {
		if (entity != null) {
			contractEffectReportDao.update(entity);
		}
		return entity;
	}
}

