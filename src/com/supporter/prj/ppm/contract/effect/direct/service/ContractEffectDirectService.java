package com.supporter.prj.ppm.contract.effect.direct.service;

import java.util.*;

import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.ppm.contract.effect.filing.entity.ContractEffectFiling;
import com.supporter.prj.ppm.contract.effect.filing.service.ContractEffectFilingService;
import com.supporter.prj.ppm.contract.sign.filing.entity.ContractEffectCondition;
import com.supporter.prj.ppm.contract.sign.filing.entity.ContractFiling;
import com.supporter.prj.ppm.contract.sign.filing.service.ContractEffectConditionService;
import com.supporter.prj.ppm.contract.sign.filing.service.ContractFilingService;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;
import com.supporter.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.contract.effect.direct.dao.ContractEffectDirectDao;
import com.supporter.prj.ppm.contract.effect.direct.entity.ContractEffectDirect;

/**
 * @author YAN
 * @version V1.0
 * @Title: Service
 * @Description: 主合同直接生效.
 * @date 2019-09-18 10:30:48
 */
@Service
public class ContractEffectDirectService {

    @Autowired
    private ContractEffectDirectDao ContractEffectDirectDao;
    @Autowired
    private ContractFilingService filingService;
    @Autowired
    private BaseInfoService prjService;
    @Autowired
    private ContractEffectConditionService conditionService;
    @Autowired
    private ContractFilingService signFilingService;
    @Autowired
    private ContractEffectFilingService effectFilingService;

    /**
     * 根据主键获取主合同直接生效.
     *
     * @param directId 主键
     * @return ContractEffectDirect
     */
    public ContractEffectDirect get(String directId) {
        return ContractEffectDirectDao.get(directId);
    }

    /**
     * 分页表格展示数据.
     *
     * @param user      用户信息
     * @param jqGridReq jqgrid请求对象
     * @param moduleIds 多个逗号分隔
     * @return JqGrid
     */
    public List<ContractEffectDirect> getGrid(UserProfile user, JqGrid jqGrid, ContractEffectDirect ContractEffectDirect) {
        return ContractEffectDirectDao.findPage(jqGrid, ContractEffectDirect);
    }

    /**
     * 进入新建或编辑或查看页面需要加载的信息
     *
     * @param user
     * @param directId
     * @return
     */
    public ContractEffectDirect initEditOrViewPage(String directId) {
        if (StringUtils.isBlank(directId)) {// 新建
            ContractEffectDirect entity = new ContractEffectDirect();
            return entity;
        } else {// 编辑
            ContractEffectDirect entity = ContractEffectDirectDao.get(directId);
            return entity;
        }
    }

    /**
     * 保存或更新.
     *
     * @param user                 用户信息
     * @param ContractEffectDirect 实体类
     * @return
     */
    public ContractEffectDirect saveOrUpdate(UserProfile user, ContractEffectDirect contractEffectDirect) {

        if (StringUtils.isBlank(contractEffectDirect.getDirectId())) {// 新建
            contractEffectDirect = this.save(user, contractEffectDirect);
        } else {// 编辑
            contractEffectDirect = this.update(user, contractEffectDirect);
        }
        List<ContractEffectCondition> list = contractEffectDirect.getConditionList();
        if (!CommonUtil.isNullOrEmpty(list)) {
            for (ContractEffectCondition conditon : list
            ) {
                conditon.setPrjId(contractEffectDirect.getPrjId());
				this.conditionService.saveOrUpdate(user,conditon);
            }
        }
        return contractEffectDirect;
    }

    /**
     * 保存.
     *
     * @param user                 用户信息
     * @param ContractEffectDirect 实体类
     * @return
     */
    private ContractEffectDirect save(UserProfile user, ContractEffectDirect ContractEffectDirect) {
        ContractEffectDirect.setCreatedBy(user.getName());
        ContractEffectDirect.setCreatedById(user.getPersonId());
        ContractEffectDirect.setCreatedDate(new Date());
        this.ContractEffectDirectDao.save(ContractEffectDirect);
        return ContractEffectDirect;
    }

    /**
     * 更新.
     *
     * @param user                 用户信息
     * @param ContractEffectDirect 实体类
     * @return
     */
    private ContractEffectDirect update(UserProfile user, ContractEffectDirect ContractEffectDirect) {
        ContractEffectDirect.setModifiedBy(user.getName());
        ContractEffectDirect.setModifiedById(user.getPersonId());
        ContractEffectDirect.setModifiedDate(new Date());
        ContractEffectDirect ContractEffectDirectDb = this.ContractEffectDirectDao.get(ContractEffectDirect.getDirectId());
        if (ContractEffectDirectDb == null) {
            return this.save(user, ContractEffectDirect);
        } else {
            ModuleUtils.copyPropertiesExcludeNullProperty(ContractEffectDirect, ContractEffectDirectDb);
            this.ContractEffectDirectDao.update(ContractEffectDirectDb);
            return ContractEffectDirectDb;
        }

    }

    /**
     * 删除
     *
     * @param user      用户信息
     * @param directIds 主键集合，多个以逗号分隔
     */
    public void delete(UserProfile user, String directIds) {
        if (StringUtils.isNotBlank(directIds)) {
            for (String directId : directIds.split(",")) {
                ContractEffectDirect ContractEffectDirectDb = this.ContractEffectDirectDao.get(directId);
                this.ContractEffectDirectDao.delete(directId);
            }
        }
    }

    /**
     * 判断名字是否重复
     *
     * @param directId
     * @param directName
     * @return
     */
    public boolean nameIsValid(String directId, String directName) {
        return this.ContractEffectDirectDao.nameIsValid(directId, directName);
    }

    /**
     * 根据项目id初始化
     *
     * @param prjId
     * @return
     */
    public ContractEffectDirect initPageByPrjId(String prjId) {
        ContractEffectDirect entity;
        if (StringUtils.isNotBlank(prjId)) {
            List<ContractEffectDirect> list = this.ContractEffectDirectDao.findBy("prjId", prjId);
            if (list != null && list.size() > 0) {
                entity = list.get(0);
                initInfo(entity);
                return entity;
            }
        }
        entity = new ContractEffectDirect();
        entity.setDirectId(com.supporter.util.UUIDHex.newId());
        entity.setPrjId(prjId);
        initInfo(entity);
        return entity;
    }

    /**
     * 初始化关联其他表的信息
     *
     * @param entity
     * @return
     */
    public ContractEffectDirect initInfo(ContractEffectDirect entity) {
        String prjId = entity.getPrjId();
        //初始化项目信息
        Prj prj = this.prjService.initEditOrViewPage(prjId, null);
        if (prj != null) {
            entity.setPrjCName(prj.getPrjCName());
            entity.setPrjEName(prj.getPrjEName());
            entity.setPrjNo(prj.getPrjNo());
        }
        //初始化合同信息
        //初始化备案信息
        if (StringUtils.isBlank(entity.getFilingId())) {
            ContractFiling filing = filingService.getByPrjId(prjId);
            if (filing != null) {
                entity.setFilingId(filing.getFilingId());
                entity.setFilingNo(filing.getFilingNo());
                entity.setContractSignDate(filing.getSignDate());
                entity.setContractAmout(filing.getContractAmount());
                entity.setContractRange(filing.getContractRange());
            }
        } else {
            ContractFiling filing = filingService.get(entity.getFilingId());
            if (filing != null) {
                entity.setFilingNo(filing.getFilingNo());
                entity.setContractSignDate(filing.getSignDate());
                entity.setContractAmout(filing.getContractAmount());
                entity.setContractRange(filing.getContractRange());
            }
        }
        return entity;
    }

    public Map<String, Object> validEffect(String prjId, String message, boolean result) {
        //判断项目是否生效
        Prj prj = this.prjService.initBaseInfoView(prjId);
        String nextPage = "view";
        //如果项目不是生效状态
        if (prj.getStatus() != 1 && prj.getStatus() != 2) {
            message = "该项目尚未生效！";
        } else {
            //判断主合同没有变更并且从签订日期到生效日期未超过12个自然月
            //判断有没有超过12个月、获取主合同签约时间
            ContractFiling filing = this.signFilingService.getByPrjId(prjId);
            int m = 0;
            if (filing!=null){
                Date signDate = filing.getSignDate();

                Calendar sign = Calendar.getInstance();
                Calendar now = Calendar.getInstance();
                sign.setTime(signDate);
                now.setTime(new Date());
                int surplus = now.get(Calendar.DATE) - sign.get(Calendar.DATE);
                int r = now.get(Calendar.MONTH) - sign.get(Calendar.MONTH);
                int month = (now.get(Calendar.YEAR) - sign.get(Calendar.YEAR)) * 12;
                surplus = surplus <= 0 ? 1 : 0;
                m = (Math.abs(month + r) + surplus);
            }
            if (m>12) {
                //获取是否生效备案完成
                ContractEffectFiling effectFiling = this.effectFilingService.initPageByPrjId(prjId);
                if (effectFiling!=null&&effectFiling.getStatus()==ContractEffectFiling.StatusCodeTable.COMPLETE){
                    result = true;
                    //判断是编辑还是查看页面
                    ContractEffectDirect entity = this.initPageByPrjId(prjId);
                    if (entity.getStatus() == ContractEffectDirect.StatusCodeTable.DRAFT){
                        nextPage = "edit";
                    }
                }else{
                    message = "该项目无法直接生效，请走报审、评审流程！";
                }
            } else {
                result = true;
                //判断是编辑还是查看页面
                ContractEffectDirect entity = this.initPageByPrjId(prjId);
                if (entity.getStatus() == ContractEffectDirect.StatusCodeTable.DRAFT){
                    nextPage = "edit";
                }

            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("msg", message);
        map.put("nextPage",nextPage);
        map.put("result", result);
        return map;
    }
}

