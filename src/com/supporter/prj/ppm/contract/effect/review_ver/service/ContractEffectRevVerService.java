package com.supporter.prj.ppm.contract.effect.review_ver.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.ppm.contract.effect.review.entity.ContractEffectReviewCon;
import com.supporter.prj.ppm.contract.effect.review.service.ContractEffectReviewConService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.contract.effect.review_ver.dao.ContractEffectRevVerDao;
import com.supporter.prj.ppm.contract.effect.review_ver.entity.ContractEffectRevVer;

/**
 * @author YAN
 * @version V1.0
 * @Title: Service
 * @Description: 评审验证表.
 * @date 2019-09-09 10:46:27
 */
@Service
public class ContractEffectRevVerService {

    @Autowired
    private ContractEffectRevVerDao contractEffectRevVerDao;
    @Autowired
    private ContractEffectReviewConService contractEffectReviewConService;

    /**
     * 根据主键获取评审验证表.
     *
     * @param reviewVerId 主键
     * @return ContractEffectRevVer
     */
    public ContractEffectRevVer get(String reviewVerId) {
        return contractEffectRevVerDao.get(reviewVerId);
    }

    /**
     * 分页表格展示数据.
     *
     * @param user      用户信息
     * @param jqGridReq jqgrid请求对象
     * @param moduleIds 多个逗号分隔
     * @return JqGrid
     */
    public List<ContractEffectRevVer> getGrid(UserProfile user, JqGrid jqGrid, ContractEffectRevVer contractEffectRevVer) {
        return contractEffectRevVerDao.findPage(jqGrid, contractEffectRevVer);
    }

    /**
     * 进入新建或编辑或查看页面需要加载的信息
     *
     * @param user
     * @param reviewVerId
     * @return
     */
    public ContractEffectRevVer initEditOrViewPage(String reviewVerId) {
        if (StringUtils.isBlank(reviewVerId)) {// 新建
            ContractEffectRevVer entity = new ContractEffectRevVer();
            return entity;
        } else {// 编辑
            ContractEffectRevVer entity = contractEffectRevVerDao.get(reviewVerId);
            return entity;
        }
    }

    /**
     * 保存或更新.
     *
     * @param user               用户信息
     * @param contractEffectRevVer 实体类
     * @return
     */
    public ContractEffectRevVer saveOrUpdate(UserProfile user, ContractEffectRevVer contractEffectRevVer) {
        if (StringUtils.isBlank(contractEffectRevVer.getReviewVerId())) {// 新建
            contractEffectRevVer.setCreatedBy(user.getName());
            contractEffectRevVer.setCreatedById(user.getPersonId());
            contractEffectRevVer.setCreatedDate(new Date());
            contractEffectRevVer.setDeptId(user.getDeptId());
            contractEffectRevVer.setDeptName(user.getDept()==null?"":user.getDept().getName());
            return this.save(user, contractEffectRevVer);
        } else {// 编辑
            contractEffectRevVer.setModifiedBy(user.getName());
            contractEffectRevVer.setModifiedById(user.getPersonId());
            contractEffectRevVer.setModifiedDate(new Date());
            return this.update(user, contractEffectRevVer);
        }
    }

    /**
     * 保存.
     *
     * @param user               用户信息
     * @param contractEffectRevVer 实体类
     * @return
     */
    private ContractEffectRevVer save(UserProfile user, ContractEffectRevVer contractEffectRevVer) {
        this.contractEffectRevVerDao.save(contractEffectRevVer);
        return contractEffectRevVer;
    }

    /**
     * 更新.
     *
     * @param user               用户信息
     * @param contractEffectRevVer 实体类
     * @return
     */
    private ContractEffectRevVer update(UserProfile user, ContractEffectRevVer contractEffectRevVer) {
        ContractEffectRevVer contractEffectRevVerDb = this.contractEffectRevVerDao.get(contractEffectRevVer.getReviewVerId());
        if (contractEffectRevVerDb == null) {
            return this.save(user, contractEffectRevVer);
        } else {
            ModuleUtils.copyPropertiesExcludeNullProperty(contractEffectRevVer, contractEffectRevVerDb);
            this.contractEffectRevVerDao.update(contractEffectRevVerDb);
            return contractEffectRevVerDb;
        }

    }

    /**
     * 删除
     *
     * @param user         用户信息
     * @param reviewVerIds 主键集合，多个以逗号分隔
     */
    public void delete(UserProfile user, String reviewVerIds) {
        if (StringUtils.isNotBlank(reviewVerIds)) {
            for (String reviewVerId : reviewVerIds.split(",")) {
                ContractEffectRevVer contractEffectRevVerDb = this.contractEffectRevVerDao.get(reviewVerId);
                this.contractEffectRevVerDao.delete(reviewVerId);
                //删除附件类型表
            }
        }
    }

    /**
     * 判断名字是否重复
     *
     * @param reviewVerId
     * @param reviewVerName
     * @return
     */
    public boolean nameIsValid(String reviewVerId, String reviewVerName) {
        return this.contractEffectRevVerDao.nameIsValid(reviewVerId, reviewVerName);
    }

    /**
     * 根据项目id初始化
     *
     * @param prjId
     * @return
     */
    public ContractEffectRevVer initPageByReviewId(String contractEffectReviewId) {
        if (StringUtils.isNotBlank(contractEffectReviewId)) {
            //初始化
            List<ContractEffectRevVer> vers = this.contractEffectRevVerDao.findBy("contractEffectReviewId", contractEffectReviewId);
            if (vers != null && vers.size() > 0) {
                return vers.get(0);
            }
        }
        ContractEffectRevVer ver = new ContractEffectRevVer();
        ver.setReviewVerId(com.supporter.util.UUIDHex.newId());
        ver.setContractEffectReviewId(contractEffectReviewId);
        //获取评审结论
        ContractEffectReviewCon con = this.contractEffectReviewConService.getByReviwId(contractEffectReviewId);
        if (con!=null){
            ver.setResult(con.getRvConBussinesStatus());
        }
        return ver;
    }

    public ContractEffectRevVer commit(UserProfile user, ContractEffectRevVer contractEffectRevVer) {
        String id = contractEffectRevVer.getReviewVerId();
        ContractEffectRevVer ver = this.contractEffectRevVerDao.get(id);
        if (ver!=null){
            ver.setStatus(2);
            this.contractEffectRevVerDao.update(ver);
        }
        return ver;
     }
}

