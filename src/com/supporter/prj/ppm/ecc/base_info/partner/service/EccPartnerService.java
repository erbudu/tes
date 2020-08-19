package com.supporter.prj.ppm.ecc.base_info.partner.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomerFso;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomerPartner;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomerWarn;
import com.supporter.prj.ppm.ecc.base_info.owner.entity.EccOwner;
import com.supporter.prj.ppm.ecc.base_info.partner.entity.EccPartnerFso;
import com.supporter.prj.ppm.ecc.base_info.partner.entity.EccPartnerP;
import com.supporter.prj.ppm.ecc.base_info.partner.entity.EccPartnerWarn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.ecc.base_info.partner.dao.EccPartnerDao;
import com.supporter.prj.ppm.ecc.base_info.partner.entity.EccPartner;

/**
 * @author YAN
 * @version V1.0
 * @Title: Service
 * @Description: 出口管制项目合作伙伴.
 * @date 2019-08-16 18:34:18
 */
@Service
public class EccPartnerService {

    @Autowired
    private EccPartnerDao eccPartnerDao;
    @Autowired
    private EccPartnerFsoService fsoService;
    @Autowired
    private EccPartnerWarnService warnService;
    @Autowired
    private EccPartnerPService partnerService;

    /**
     * 根据主键获取出口管制项目合作伙伴.
     *
     * @param partnerId 主键
     * @return EccPartner
     */
    public EccPartner get(String partnerId) {
        return eccPartnerDao.get(partnerId);
    }

    /**
     * 分页表格展示数据.
     *
     * @param user      用户信息
     * @param jqGridReq jqgrid请求对象
     * @param moduleIds 多个逗号分隔
     * @return JqGrid
     */
    public List<EccPartner> getGrid(UserProfile user, JqGrid jqGrid, EccPartner eccPartner) {
        return eccPartnerDao.findPage(jqGrid, eccPartner);
    }

    /**
     * 进入新建或编辑或查看页面需要加载的信息
     *
     * @param user
     * @param partnerId
     * @return
     */
    public EccPartner initEditOrViewPage(String partnerId) {
        EccPartner entity = null;
        if (StringUtils.isBlank(partnerId)) {// 新建
            entity = new EccPartner();
            partnerId = com.supporter.util.UUIDHex.newId();
            entity.setPartnerId(partnerId);
        } else {// 编辑
            entity = eccPartnerDao.get(partnerId);
        }
        entity = initEditOrViewPage(partnerId, entity);
        return entity;

    }

    public EccPartner initEditOrViewPage(String partnerId, EccPartner entity) {
        if (entity == null) {
            entity = new EccPartner();
            entity.setPartnerId(partnerId);
        }
        //初始化从表信息
        //获取客户所在地对象
        EccPartnerFso fso = fsoService.findByPartnerId(partnerId);
        //后去客户合作伙伴对象
        EccPartnerP partner = partnerService.findByPartnerId(partnerId);
        //获取客户警告对象
        EccPartnerWarn warn = warnService.findByPartnerId(partnerId);
        entity.setFso(fso);
        entity.setPartner(partner);
        entity.setWarn(warn);
        return entity;
    }

    /**
     * 保存或更新.
     *
     * @param user       用户信息
     * @param eccPartner 实体类
     * @return
     */
    public EccPartner saveOrUpdate(UserProfile user, EccPartner eccPartner) {
        if (StringUtils.isBlank(eccPartner.getPartnerId())) {// 新建
            return this.save(user, eccPartner);
        } else {// 编辑
            return this.update(user, eccPartner);
        }
    }

    /**
     * 保存.
     *
     * @param user       用户信息
     * @param eccPartner 实体类
     * @return
     */
    private EccPartner save(UserProfile user, EccPartner eccPartner) {
        this.eccPartnerDao.save(eccPartner);
        return eccPartner;
    }

    /**
     * 更新.
     *
     * @param user       用户信息
     * @param eccPartner 实体类
     * @return
     */
    private EccPartner update(UserProfile user, EccPartner eccPartner) {
        EccPartner eccPartnerDb = this.eccPartnerDao.get(eccPartner.getPartnerId());
        if (eccPartnerDb == null) {
            this.save(user, eccPartner);
        } else {
            ModuleUtils.copyPropertiesExcludeNullProperty(eccPartner, eccPartnerDb);
            this.eccPartnerDao.update(eccPartnerDb);
        }
        //保存或更新 客户所在地、客户警告、合作伙伴信息
        EccPartnerFso fso = eccPartner.getFso();
        //后去客户合作伙伴对象
        EccPartnerP partner = eccPartner.getPartner();
        //获取客户警告对象
        EccPartnerWarn warn = eccPartner.getWarn();

        fso = this.fsoService.saveOrUpdate(user, fso);
        partner = this.partnerService.saveOrUpdate(user, partner);
        warn = this.warnService.saveOrUpdate(user, warn);
        eccPartner.setFso(fso);
        eccPartner.setPartner(partner);
        eccPartner.setWarn(warn);
        return eccPartner;
    }

    /**
     * 删除
     *
     * @param user       用户信息
     * @param partnerIds 主键集合，多个以逗号分隔
     */
    public void delete(UserProfile user, String partnerIds) {
        if (StringUtils.isNotBlank(partnerIds)) {
            for (String partnerId : partnerIds.split(",")) {
                EccPartner eccPartnerDb = this.eccPartnerDao.get(partnerId);
                this.eccPartnerDao.delete(partnerId);
                //删除从信息
                this.partnerService.deleteByPartnerId(partnerId);
                this.fsoService.deleteByPartnerId(partnerId);
                this.warnService.deleteByPartnerId(partnerId);
            }
        }
    }
    /**
     * 全部删除
     * @param user
     * @param eccId
     */
    public void batchDelAll(UserProfile user, String eccId) {
        List<EccPartner> entitys = this.eccPartnerDao.findBy("eccId",eccId);
        if (entitys!=null&&entitys.size()>0){
            for (EccPartner c:entitys
            ) {
                delete(user,c.getPartnerId());
            }
        }

    }
    /**
     * 判断名字是否重复
     *
     * @param partnerId
     * @param partnerName
     * @return
     */
    public boolean nameIsValid(String partnerId, String partnerName) {
        return this.eccPartnerDao.nameIsValid(partnerId, partnerName);
    }

}

