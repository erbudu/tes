package com.supporter.prj.ppm.contract.effect.seal_bfd.service;

import java.util.*;

import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.ppm.contract.effect.report.entity.ContractEffectReport;
import com.supporter.prj.ppm.contract.effect.report.entity.ContractEffectReportBfd;
import com.supporter.prj.ppm.contract.effect.report.service.ContractEffectReportService;
import com.supporter.prj.ppm.contract.effect.seal_bfd.entity.ContractEffectSealBfd;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.contract.effect.seal_bfd.dao.ContractEffectSealDao;
import com.supporter.prj.ppm.contract.effect.seal_bfd.entity.ContractEffectSeal;

/**
 * @author YAN
 * @version V1.0
 * @Title: Service
 * @Description: 主合同出版.
 * @date 2019-09-10 14:57:13
 */
@Service
public class ContractEffectSealService {

    @Autowired
    private ContractEffectSealDao contractEffectSealDao;
    @Autowired
    private ContractEffectSealBfdService effectSealBfdService;
    @Autowired
    private ContractEffectReportService effectReportService;
    @Autowired
    private BaseInfoService prjService;

    /**
     * 根据主键获取主合同出版.
     *
     * @param effectSealId 主键
     * @return ContractEffectSeal
     */
    public ContractEffectSeal get(String effectSealId) {
        return contractEffectSealDao.get(effectSealId);
    }

    /**
     * 分页表格展示数据.
     *
     * @param user      用户信息
     * @param jqGridReq jqgrid请求对象
     * @param moduleIds 多个逗号分隔
     * @return JqGrid
     */
    public List<ContractEffectSeal> getGrid(UserProfile user, JqGrid jqGrid, ContractEffectSeal contractEffectSeal) {
        return contractEffectSealDao.findPage(jqGrid, contractEffectSeal);
    }

    /**
     * 进入新建或编辑或查看页面需要加载的信息
     *
     * @param user
     * @param effectSealId
     * @return
     */
    public ContractEffectSeal initEditOrViewPage(String effectSealId) {
        if (StringUtils.isBlank(effectSealId)) {// 新建
            ContractEffectSeal entity = new ContractEffectSeal();
            return entity;
        } else {// 编辑
            ContractEffectSeal entity = contractEffectSealDao.get(effectSealId);
            return entity;
        }
    }

    /**
     * 保存或更新.
     *
     * @param user               用户信息
     * @param contractEffectSeal 实体类
     * @return
     */
    public ContractEffectSeal saveOrUpdate(UserProfile user, ContractEffectSeal contractEffectSeal) {
        if (StringUtils.isBlank(contractEffectSeal.getEffectSealId())) {// 新建
            contractEffectSeal = this.save(user, contractEffectSeal);
        } else {// 编辑
            contractEffectSeal = this.update(user, contractEffectSeal);
        }
        //保存或更新文件类型
        List<ContractEffectSealBfd> bfds = contractEffectSeal.getBfds();
        if (bfds != null) {
            for (ContractEffectSealBfd bfd : bfds
            ) {
                //保存或更新类型信息
                this.effectSealBfdService.saveOrUpdate(user, bfd);
            }
        }
        return contractEffectSeal;
    }

    /**
     * 保存.
     *
     * @param user               用户信息
     * @param contractEffectSeal 实体类
     * @return
     */
    private ContractEffectSeal save(UserProfile user, ContractEffectSeal contractEffectSeal) {
        contractEffectSeal.setCreatedBy(user.getName());
        contractEffectSeal.setCreatedById(user.getPersonId());
        contractEffectSeal.setCreatedDate(new Date());
        this.contractEffectSealDao.save(contractEffectSeal);
        return contractEffectSeal;
    }

    /**
     * 更新.
     *
     * @param user               用户信息
     * @param contractEffectSeal 实体类
     * @return
     */
    private ContractEffectSeal update(UserProfile user, ContractEffectSeal contractEffectSeal) {
        contractEffectSeal.setModifiedBy(user.getName());
        contractEffectSeal.setModifiedById(user.getPersonId());
        contractEffectSeal.setModifiedDate(new Date());
        ContractEffectSeal contractEffectSealDb = this.contractEffectSealDao.get(contractEffectSeal.getEffectSealId());
        if (contractEffectSealDb == null) {
            return this.save(user, contractEffectSeal);
        } else {
            ModuleUtils.copyPropertiesExcludeNullProperty(contractEffectSeal, contractEffectSealDb);
            this.contractEffectSealDao.update(contractEffectSealDb);
            return contractEffectSealDb;
        }

    }

    /**
     * 删除
     *
     * @param user          用户信息
     * @param effectSealIds 主键集合，多个以逗号分隔
     */
    public void delete(UserProfile user, String effectSealIds) {
        if (StringUtils.isNotBlank(effectSealIds)) {
            for (String effectSealId : effectSealIds.split(",")) {
                ContractEffectSeal contractEffectSealDb = this.contractEffectSealDao.get(effectSealId);
                this.contractEffectSealDao.delete(effectSealId);
                //删除对应的附件类型
                this.effectSealBfdService.deleteByEffectSealId(effectSealId);
            }
        }
    }

    /**
     * 判断名字是否重复
     *
     * @param effectSealId
     * @param effectSealName
     * @return
     */
    public boolean nameIsValid(String effectSealId, String effectSealName) {
        return this.contractEffectSealDao.nameIsValid(effectSealId, effectSealName);
    }

    /**
     * 根据项目id初始化
     *
     * @param prjId
     * @return
     */
    public ContractEffectSeal initPageByPrjId(String prjId) {
        ContractEffectSeal entity = null;
        if (StringUtils.isNotBlank(prjId)) {
            List<ContractEffectSeal> list = this.contractEffectSealDao.findBy("prjId", prjId);
            if (list != null && list.size() > 0) {
                entity = list.get(0);
            } else {
                entity = new ContractEffectSeal();
                entity.setPrjId(prjId);
                entity.setEffectSealId(com.supporter.util.UUIDHex.newId());
                ContractEffectReport effectReport = effectReportService.getEntityByPrjId(prjId);
                entity.setContractEffectId(effectReport.getContractEffectId());
            }
            entity = initPrjInfo(prjId, entity);
        }
        //初始化资料清单
        List<ContractEffectSealBfd> bfds = initBfds(entity.getEffectSealId(), entity.getContractEffectId());
        entity.setBfds(bfds);
        return entity;
    }

    private List<ContractEffectSealBfd> initBfds(String effectSealId, String contractEffectId) {
        //初始化资料清单
        List<ContractEffectSealBfd> bfds = this.effectSealBfdService.getListByEffectSealId(effectSealId);
        if (bfds == null || bfds.size() < 1) {
            bfds = new ArrayList<ContractEffectSealBfd>();
            //根据码表获取对应的需求
            List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PPM_CONTRACT_EFFECT_BFD");
            for (IComCodeTableItem item : items
            ) {
                ContractEffectSealBfd bfd = new ContractEffectSealBfd();
                bfd.setBfdTypeId(item.getItemId());
                bfd.setBfdTypeName(item.getItemValue());
                bfd.setContractEffectId(contractEffectId);
                bfd.setEffectSealId(effectSealId);
                bfd.setBfdId(com.supporter.util.UUIDHex.newId());
                bfds.add(bfd);
            }
        }
        return bfds;
    }

    /**
     * 初始化项目信息
     *
     * @param prjId
     * @param entity
     */
    private ContractEffectSeal initPrjInfo(String prjId, ContractEffectSeal entity) {
        //查询对应的项目 赋值
        Prj prj = this.prjService.initBaseInfoView(prjId);
        if (prj != null) {
            entity.setPrjCName(prj.getPrjCName());
            entity.setPrjEName(prj.getPrjEName());
            entity.setPrjNo(prj.getPrjNo());
        }
        return entity;
    }


    public Map<String, Object> validEffect(String prjId, String message, boolean result) {
            //判断项目是否生效
        Prj prj = this.prjService.initBaseInfoView(prjId);
        //如果项目不是生效状态
        if (prj.getStatus() != 1 && prj.getStatus() != 2) {
            message = "该项目尚未生效！";
        } else {
             result = true;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("msg", message);
        map.put("result", result);
        return map;
    }
}

