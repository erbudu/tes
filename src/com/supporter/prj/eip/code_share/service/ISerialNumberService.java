package com.supporter.prj.eip.code_share.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.eip.code_share.entity.CsSerialNumber;
import com.supporter.prj.eip.code_share.entity.CsSerialNumberRecord;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.module.entity.IDomainObjectAttr;
import com.supporter.prj.eip_service.security.entity.UserProfile;

// ~ File Information
// ====================================================================================================================

/**
 * 编号规则的service接口.
 * 
 * @author 康博
 * @createDate 2017-5-5
 * @since 6.0
 *
 */
public interface ISerialNumberService {

    // ~ Static Fields
    // ================================================================================================================

    // ~ Methods
    // ================================================================================================================

    /**
     * 保存或更新实体及明细信息.
     * 
     * @param serialNumber 编号规则实例
     * @param serialNumberRecords 规则明细字符串
     * @param user 登陆用户
     * @return
     */
    OperResult < CsSerialNumber > saveOrUpdate(CsSerialNumber serialNumber, String serialNumberRecordsJson,
            UserProfile user, boolean clearNumbering);

    /**
     * 获得编号规则列表数据.
     * 
     * @return
     */
    ListPage < CsSerialNumber > getSerialNumberListByPage(UserProfile user, ListPage < CsSerialNumber > listPage, 
            Map < String, Object > searchParam);

    /**
     * 根据编号规则ID获得编号实例.
     * 
     * @param serialNumberId 编号规则ID
     * @return
     */
    OperResult < CsSerialNumber > getSerialNumberById(String serialNumberId);

    /**
     * 获得编号规则实例.
     * 
     * @param serialNumberId
     * @return
     */
    CsSerialNumber getSerialNumber(String serialNumberId);

    /**
     * 根据所属应用与二级分类编号获取编号规则实例.
     * 
     * @param moduleNo 所属应用编号
     * @param category2No 二级分类编号
     * @return
     */
    OperResult < CsSerialNumber > getEntityByModuleNoAndCategory2No(String moduleNo, String category2No);

    /**
     * 根据所属应用与二级分类编号获取编号规则实例.
     * 
     * @param moduleNo 所属应用编号
     * @param category2No 二级分类编号
     * @return
     */
    CsSerialNumber getSerialNumberByModuleNoAndCategory2No(String moduleNo, String category2No);

    /**
     * 批量删除.
     * 
     * @param ids 编号规则id集合
     * @return
     */
    OperResult < String > delete(UserProfile user, List < String > listId);

    /**
     * 根据编号规则id获取明细集合.
     * 
     * @param serialNumberId 编号规则id
     * @return
     */
    List < CsSerialNumberRecord > getSerialNumberRecords(String serialNumberId);
    /**
     * 根据业务对象获取主表元数据信息
     */
    List < IDomainObjectAttr> getDomainObjectAttr(String objectId);
}
