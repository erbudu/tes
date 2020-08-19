package com.supporter.prj.eip.code_share.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.eip.code_share.constant.RecordTypeEnum;
import com.supporter.prj.eip.code_share.constant.SerialNumberConstant;
import com.supporter.prj.eip.code_share.constant.SerialNumberModeEnum;
import com.supporter.prj.eip.code_share.constant.SerialNumberMsgCode;
import com.supporter.prj.eip.code_share.dao.ISerialNumberDao;
import com.supporter.prj.eip.code_share.dao.ISerialNumberRecordDao;
import com.supporter.prj.eip.code_share.dao.CsSerialNumberRecordDao;
import com.supporter.prj.eip.code_share.entity.CsSerialNumber;
import com.supporter.prj.eip.code_share.entity.CsSerialNumberRecord;
import com.supporter.prj.eip.code_share.util.AuthUtils;
import com.supporter.prj.eip.code_share.util.SerialNumberCacheUtil;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.module.entity.IDomainObject;
import com.supporter.prj.eip_service.module.entity.IDomainObjectAttr;
import com.supporter.prj.eip_service.module.entity.IDomainObjectTable;
import com.supporter.prj.eip_service.module.entity.IModule;
import com.supporter.prj.eip_service.security.entity.UserProfile;

// ~ File Information
// ====================================================================================================================

/**
 * 编号规则service类.
 * 
 * @author 康博
 * @createDate 2017-5-5
 * @since 6.0
 *
 */
@Service
@Transactional(TransManager.APP)
public class CsSerialNumberService implements ISerialNumberService {

    // ~ Static Fields
    // ================================================================================================================

    // ~ Fields
    // ================================================================================================================

    /**
     * {@link ISerialNumberDao}.
     */
    @Autowired
    private ISerialNumberDao dao;

    /**
     * {@link ISerialNumberRecordDao}.
     */
    @Autowired
    private CsSerialNumberRecordDao recordDao;

    /**
     * {@link SerialNoCommonService}.
     */
    @Autowired
    private CsSerialNoCommonService serialNoCommonService;

    // ~ Constructors
    // ================================================================================================================

    // ~ Methods
    // ================================================================================================================

    /**
     * @see com.supporter.prj.eip.code_share.service.ISerialNumberService#
     * saveOrUpdate(com.supporter.prj.eip.code_share.entity.CsSerialNumber, java.lang.String, 
     * com.supporter.prj.eip_service.security.entity.UserProfile)
     * clearNumbering unused
     */
    @Override
    @SuppressWarnings("unchecked")
    public OperResult < CsSerialNumber > saveOrUpdate(CsSerialNumber serialNumber, String serialNumberRecordsJson, 
            UserProfile user, boolean clearNumbering) {

        OperResult < CsSerialNumber > result = new OperResult < CsSerialNumber > ();

        if (StringUtils.isEmpty(serialNumber.getSerialNumberId())) {
            serialNumber.setSerialNumberId(null);
        } else {
            CsSerialNumber oldSerialNumber = this.getSerialNumber(serialNumber.getSerialNumberId());
            String numberingType = oldSerialNumber.getNumberingType();

            //当重置流水号或者流水号类型修改时删除流水号实例
            if (oldSerialNumber.getSerialNumberMode()==1 && serialNumber.getSerialNumberMode() ==1 && !numberingType.equals(serialNumber.getNumberingType())) {

                serialNoCommonService.deleteEntityBySerialNumberId(numberingType, serialNumber.getSerialNumberId(),oldSerialNumber.getRegenerateType());
            }

            recordDao.deleteByProperty(CsSerialNumberRecord.PROP_SERIAL_NUMBER_ID, serialNumber.getSerialNumberId());

            //清除缓存
            SerialNumberCacheUtil.removeAllSerialNumberCache(oldSerialNumber);
        }

        dao.clearData();
        //验证权限
        AuthUtils.canExecute(user, SerialNumberConstant.MODULE_ID, SerialNumberConstant.AUTH_OPER_NAME, serialNumber.getSerialNumberId(), serialNumber);
        dao.saveOrUpdate(serialNumber);

        //当为规则定义时保存明细
        if (StringUtils.isNotEmpty(serialNumberRecordsJson) 
                && serialNumber.getSerialNumberMode() == SerialNumberModeEnum.RULE_DEFINITION.getInnerName()) {

            List < CsSerialNumberRecord > listRecord = (List < CsSerialNumberRecord >)
                    JSONArray.toCollection(JSONArray.fromObject(serialNumberRecordsJson), CsSerialNumberRecord.class);

            for (int i = 0; i < listRecord.size(); i++) {
                CsSerialNumberRecord record = listRecord.get(i);
                record.setDisplayOrder(i);
                record.setSerialNumberId(serialNumber.getSerialNumberId());
            }

            recordDao.save(listRecord);
        }

        result.setBody(serialNumber);

        return result;
    }

    /**
     * @see com.supporter.prj.eip.code_share.service.ISerialNumberService#
     * getSerialNumberListByPage(com.supporter.prj.core.orm.hibernate.ListPage, java.util.Map)
     */
    @Override
    public ListPage < CsSerialNumber > getSerialNumberListByPage(UserProfile user, ListPage < CsSerialNumber > listPage,
            Map < String, Object > searchParam) {

        ListPage < CsSerialNumber > result = dao.getSerialNumberListByPage(user, listPage, searchParam);
        List < CsSerialNumber > listSerialNumber = result.getRows();

        for (CsSerialNumber serialNumber : listSerialNumber) {
            setModuleName(serialNumber);

            int serialNumberMode = serialNumber.getSerialNumberMode();

            for (SerialNumberModeEnum serialNumberModeEnum : SerialNumberModeEnum.values()) {
                if (serialNumberModeEnum.getInnerName() == serialNumberMode) {
                    serialNumber.setSerialNumberModeDesc(serialNumberModeEnum.getDisplayName());

                    break;
                }
            }
        }

        return result;
    }

    /**
     * @see com.supporter.prj.eip.code_share.service.ISerialNumberService#getSerialNumberById(java.lang.String)
     */
    @Override
    public OperResult < CsSerialNumber > getSerialNumberById(String serialNumberId) {
        OperResult < CsSerialNumber > result = new OperResult < CsSerialNumber > ();
        CsSerialNumber serialNumber = getSerialNumber(serialNumberId);

        if (serialNumber == null) {
            result.setSuccessful(false);
            result.setMsgCode(SerialNumberMsgCode.SERIAL_NUMBER_404);

            return result;
        }

        setModuleName(serialNumber);
        List < CsSerialNumberRecord > listRecord = getSerialNumberRecords(serialNumberId);

        //若明细为业务对象元数据，则需获取业务对象元数据的显示名称以及数据类型，用于前台显示
        for (CsSerialNumberRecord serialNumberRecord : listRecord) {
            if (serialNumberRecord.getRecordType().equals(RecordTypeEnum.BUSINESS_VALUE.getInnerName())) {
                IDomainObjectAttr domain = EIPService.getModuleService()
                        .getDomainObjectAttr(serialNumberRecord.getRecordTypeLabel());

                if (domain != null) {
                    serialNumberRecord.setDataType(domain.getDataType());
                    serialNumberRecord.setRecordTypeLabelName(domain.getDisplayName());
                }
            }
        }

        serialNumber.setListRecord(listRecord);

        result.setBody(serialNumber);

        return result;
    }

    /**
     * @see com.supporter.prj.eip.code_share.service.ISerialNumberService#getSerialNumberRecords(java.lang.String)
     */
    @Override
    public List < CsSerialNumberRecord > getSerialNumberRecords(String serialNumberId) {
        List < CsSerialNumberRecord > records = SerialNumberCacheUtil.getRecordsFromCache(serialNumberId);

        if (records != null) {
            return records;
        }

        records = recordDao.getListRecordBySerialNumberId(serialNumberId);

        SerialNumberCacheUtil.putRecordsInfoCache(serialNumberId, records);

        return records;
    }

    /**
     * @see com.supporter.prj.eip.code_share.service.ISerialNumberService#getSerialNumber(java.lang.String)
     */
    @Override
    public CsSerialNumber getSerialNumber(String serialNumberId) {
        if (StringUtils.isEmpty(serialNumberId)) {
            return null;
        }

        CsSerialNumber serialNumber = SerialNumberCacheUtil.getSerialNumberFromCache(serialNumberId);

        if (serialNumber != null) {
            return serialNumber;
        }

        serialNumber = dao.get(serialNumberId);
        SerialNumberCacheUtil.putSerialNumberIntoCache(serialNumber);

        return serialNumber;
    }

    /**
     * 设置所属应用名称.
     * 
     * @param serialNumber 编号规则实例
     */
    private void setModuleName(CsSerialNumber serialNumber) {
        String moduleNo = serialNumber.getModuleNo();

        if (StringUtils.isNotEmpty(moduleNo)) {
            IModule module = EIPService.getModuleService().getModule(moduleNo);

            if (module != null) {
                serialNumber.setModuleName(module.getDisplayName());
            }
        }
    }

    /**
     * @see com.supporter.prj.eip.code_share.service.ISerialNumberService#
     * getEntityByModuleNoAndCategory2No(java.lang.String, java.lang.String)
     */
    @Override
    public OperResult < CsSerialNumber > getEntityByModuleNoAndCategory2No(String moduleNo, String category2No) {
        OperResult < CsSerialNumber > result = new OperResult < CsSerialNumber > ();
        CsSerialNumber serialNumber = getSerialNumberByModuleNoAndCategory2No(moduleNo, category2No);

        if (serialNumber == null) {
            result.setSuccessful(false);
            result.setMsgCode(SerialNumberMsgCode.SERIAL_NUMBER_404);
        } else {
            result.setBody(serialNumber);
        }

        return result;
    }

    /**
     * @see com.supporter.prj.eip.code_share.service.ISerialNumberService#
     * getSerialNumberByModuleNoAndCategory2No(java.lang.String, java.lang.String)
     */
    @Override
    public CsSerialNumber getSerialNumberByModuleNoAndCategory2No(String moduleNo, String category2No) {
        CsSerialNumber serialNumber = 
                SerialNumberCacheUtil.getEntityByModuleNoAndCategory2NoFromCache(moduleNo, category2No);

        if (serialNumber != null) {
            return serialNumber;
        }

        Map < String, Object > params = new HashMap < String, Object > ();
        params.put(CsSerialNumber.PROP_MODULE_NO, moduleNo);

        if (StringUtils.isNotEmpty(category2No)) {
            params.put(CsSerialNumber.PROP_CATEGORY2_NO, category2No);
        }

        List < CsSerialNumber > listSerialNumber = dao.queryByParam(params, new HashMap < String, Boolean > ());

        if (listSerialNumber != null && listSerialNumber.size() > 0) {
            SerialNumberCacheUtil.putEntityByModuleNoAndCategory2NoIntoCache(listSerialNumber.get(0));

            return listSerialNumber.get(0);
        }

        return null;
    }
    /**
     * 根据moduleNo查询所有编码规则
     * @param moduleNo 应用id
     * @return
     */
    public List < CsSerialNumber > getSerialNumberByModuleNo(String moduleNo) {

        Map < String, Object > params = new HashMap < String, Object > ();
        params.put(CsSerialNumber.PROP_MODULE_NO, moduleNo);

        List < CsSerialNumber > listSerialNumber = dao.queryByParam(params, new HashMap < String, Boolean > ());

        if (listSerialNumber != null && listSerialNumber.size() > 0) {
            return listSerialNumber;
        }

        return null;
    }

    /**
     * @see com.supporter.prj.eip.code_share.service.ISerialNumberService#delete(java.util.List)
     */
    @Override
    public OperResult < String > delete(UserProfile user, List < String > listId) {
        OperResult < String > result = new OperResult < String > ();

        if (listId == null) {
            return result;
        }

        for (String serialNumberId : listId) {
            CsSerialNumber serialNumber = this.getSerialNumber(serialNumberId);
            //验证权限
            AuthUtils.canExecute(user, SerialNumberConstant.MODULE_ID, SerialNumberConstant.AUTH_OPER_NAME, serialNumber.getSerialNumberId(), serialNumber);
            //删除缓存
            SerialNumberCacheUtil.removeAllSerialNumberCache(serialNumber);

            //删除流水号记录
            serialNoCommonService.deleteEntityBySerialNumberId(serialNumber.getNumberingType(), serialNumberId, serialNumber.getRegenerateType());
        }

        try {
            dao.batchDelete(listId);
            recordDao.batchDelete(listId);
        } catch (Exception e) {
            result.setSuccessful(false);
            result.setMsgCode("500");
        }

        return result;
    }

    @Override
    public List<IDomainObjectAttr> getDomainObjectAttr(String objectId) {
        if(StringUtils.isNotEmpty(objectId)){
            IDomainObject object = EIPService.getModuleService().getDomainObject(objectId);
            if(object != null){
                List <IDomainObjectTable> listtable = EIPService.getModuleService().getDomainObjectTables(object);
                if(listtable != null && listtable.size() > 0){
                    IDomainObjectTable table_temp = null;
                    for(IDomainObjectTable table : listtable){
                        if(table.getRelationType() == IDomainObjectTable.RelationType.MASTER){
                            table_temp = table;
                            break;
                        }
                    }
                    if(table_temp != null){
                        return EIPService.getModuleService().getDomainObjectAttrs(table_temp);
                    }
                }
            }
        }
        return null;
    }
}
