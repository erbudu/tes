package com.supporter.prj.eip.code_share.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.code_share.constant.NumberingTypeEnum;
import com.supporter.prj.eip.code_share.entity.CsSerialNumber;
import com.supporter.prj.eip.code_share.entity.CsSerialNumberRecord;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.common.entity.OperResult;

// ~ File Information
// ====================================================================================================================

/**
 * 编号规则缓存工具类.
 * 
 * @author 康博
 * @createDate 2017-5-19
 * @since 6.0
 *
 */
public class SerialNumberCacheUtil {

    // ~ Static Fields
    // ================================================================================================================

    /**
     * 编号规则缓存前缀.
     */
    public static final String CACHE_PRE = "EIPSERIALNUMBER-";

    /**
     * 获取编号规则流水号缓存key.
     * 
     * @param year
     * @param month
     * @param serialNumberId
     * @param deptId
     * @param numberingType
     * @param regenerateType 重复类型
     * @return
     */
    public static String getSerialNoCacheKey(int year, int month, String serialNumberId, String deptId, 
            String numberingType, String regenerateType) {

        String key = numberingType + "-";

        if (NumberingTypeEnum.ROUND_NUMBER.getInnerName().equals(numberingType)) {//随机数
            return key + serialNumberId;
        } else if (NumberingTypeEnum.COMPANY.getInnerName().equals(numberingType) && regenerateType.equals("NEVER_VALUE")) {//按单位编号（重复类型为从不）
            return key + regenerateType + serialNumberId + "-" + deptId;
        } else if (NumberingTypeEnum.DEPT.getInnerName().equals(numberingType) && regenerateType.equals("NEVER_VALUE")) {//按部门编号（重复类型为从不）
            return key + regenerateType + serialNumberId + "-" + deptId;
        } else if (NumberingTypeEnum.UNIFORM_NUMBER.getInnerName().equals(numberingType) && regenerateType.equals("NEVER_VALUE")) {//统一编号（重复类型为从不）
            return key + regenerateType + "-" + serialNumberId;
        } else if(NumberingTypeEnum.UNIFORM_NUMBER.getInnerName().equals(numberingType) && regenerateType.equals("YEAR_VALUE")) {//统一编号（重复类型为每年）
            return key + regenerateType + year + "-" + serialNumberId;
        } else if(NumberingTypeEnum.UNIFORM_NUMBER.getInnerName().equals(numberingType) && regenerateType.equals("YEAR_VALUE")) {//统一编号（重复类型为每月）
            return key + regenerateType +  year + "-" + month + "-" + serialNumberId;
        }else if (NumberingTypeEnum.COMPANY.getInnerName().equals(numberingType) && regenerateType.equals("YEAR_VALUE")) {//按单位编号（重复类型为每年）
            return key + regenerateType + year + "-" + serialNumberId + "-" + deptId;
        } else if (NumberingTypeEnum.DEPT.getInnerName().equals(numberingType) && regenerateType.equals("YEAR_VALUE")) {//按部门编号（重复类型为每年）
            return key + regenerateType + year + "-" + serialNumberId + "-" + deptId;
        } else if (NumberingTypeEnum.COMPANY.getInnerName().equals(numberingType) && regenerateType.equals("MONTH_VALUE")) {//按单位编号（重复类型为每月）
            return key + regenerateType +  year + "-" + month + "-" + serialNumberId + "-" + deptId;
        } else if (NumberingTypeEnum.DEPT.getInnerName().equals(numberingType)&& regenerateType.equals("MONTH_VALUE")) {//按部门编号（重复类型为每月）
            return key + regenerateType + year + "-" + month + "-" + serialNumberId + "-" + deptId;
        } else {
            return "";
        }
    }

    /**
     * 将编号规则流水号放入缓存.
     * 
     * @param cacheKey
     * @param indexNo
     */
    public static void putIndexNoIntoCache(String serialNumberId, String cacheKey, int indexNo) {
        String key = CACHE_PRE + cacheKey;

        EIPService.getCacheService().put(key, indexNo);

        //将缓存key存入缓存中，以便重置流水号及删除编号规则时清空缓存
        List < String > listIndexNoCacheKey = getAllIndexNoBySerialNumberIdFromCache(serialNumberId);
        listIndexNoCacheKey.add(key);
        putAllIndexNoBySerialNumberIdInfoCache(serialNumberId, listIndexNoCacheKey);
    }

    /**
     * 获取编号规则流水号缓存
     * 
     * @param cacheKey
     * @return
     */
    public static Integer getIndexNoFromCache(String cacheKey) {
        String key = CACHE_PRE + cacheKey;
        Object obj = EIPService.getCacheService().get(key);

        if (obj != null) {
            return Integer.valueOf(obj.toString());
        }

        return null;
    }

    /**
     * 将编号规则流水号最大值放入缓存.
     * 
     * @param cacheKey
     * @param maxIndexNo
     */
    public static void putMaxIndexNoIntoCache(String cacheKey, int maxIndexNo) {
        String key = CACHE_PRE + "MAX_" + cacheKey;

        EIPService.getCacheService().put(key, maxIndexNo);
    }

    /**
     * 获取编号规则流水号最大值.
     * 
     * @param cacheKey
     * @return
     */
    public static Integer getMaxIndexNoFromCache(String cacheKey) {
        String key = CACHE_PRE + "MAX_" + cacheKey;

        Object obj = EIPService.getCacheService().get(key);

        if (obj != null) {
            return Integer.valueOf(obj.toString());
        }

        return null;
    }

    /**
     * 获得指定编号规则下的所有流水号缓存的缓存key.
     * 
     * @param serialNumberId
     * @return
     */
    private static String getAllIndexNoBySerialNumberIdCacheKey(String serialNumberId) {
        return CACHE_PRE + "AllIndexNoBySerialNumberId-" + serialNumberId;
    }

    /**
     * 将指定编号规则下的所有流水号的缓存key集合存入缓存.
     * 
     * @param serialNumberId
     * @param listIndexNoCacheKey
     */
    private static void putAllIndexNoBySerialNumberIdInfoCache(String serialNumberId, 
            List < String > listIndexNoCacheKey) {

        String key = getAllIndexNoBySerialNumberIdCacheKey(serialNumberId);
        EIPService.getCacheService().put(key, (Serializable) listIndexNoCacheKey);
    }

    /**
     * 获得指定编号规则下的所有流水号的缓存key集合.
     * 
     * @param serialNumberId
     * @return
     */
    @SuppressWarnings({
        "unchecked", "rawtypes"
    })
    private static List < String > getAllIndexNoBySerialNumberIdFromCache(String serialNumberId) {
        String key = getAllIndexNoBySerialNumberIdCacheKey(serialNumberId);
        Object obj = EIPService.getCacheService().get(key);

        if (obj != null && obj instanceof List) {
            return (List) obj;
        }

        return new ArrayList < String > ();
    }

    /**
     * 删除指定编号规则下的所有流水号的缓存集合
     * 
     * @param serialNumberId
     */
    private static void removeAllIndexNoBySerialNumberIdCache(String serialNumberId) {
        List < String > listIndexNoCacheKey = getAllIndexNoBySerialNumberIdFromCache(serialNumberId);

        for (String key : listIndexNoCacheKey) {
            EIPService.getCacheService().remove(key);
        }
    }

    /**
     * 从缓存中读取预置数据.
     * 
     * @return
     */
    @SuppressWarnings({
        "unchecked", "rawtypes"
    })
    public static OperResult < Map < String, Object > > getPresetDataFromCache() {
        String key = getPresetDataCacheKey();
        Object obj = EIPService.getCacheService().get(key);

        if (obj != null && obj instanceof OperResult) {
            return (OperResult) obj;
        }

        return null;
    }

    /**
     * 将预置数据放入缓存.
     * 
     * @param presetData
     */
    public static void putPresetDataInfoCache(OperResult < Map < String, Object > > presetData) {
        String key = getPresetDataCacheKey();
        EIPService.getCacheService().put(key, presetData);
    }

    /**
     * 获取预置数据缓存key.
     * 
     * @return
     */
    private static String getPresetDataCacheKey() {
        return CACHE_PRE + "PresetData";
    }

    /**
     * 从缓存中获得编号规则实例.
     * 
     * @param serialNumberId
     * @return
     */
    public static CsSerialNumber getSerialNumberFromCache(String serialNumberId) {
        String key = getSerialNumberCacheKey(serialNumberId);
        Object obj = EIPService.getCacheService().get(key);

        if (obj != null && obj instanceof CsSerialNumber) {
            return (CsSerialNumber) obj;
        }

        return null;
    }

    /**
     * 将编号规则实例放入缓存中.
     * 
     * @param serialNumber
     */
    public static void putSerialNumberIntoCache(CsSerialNumber serialNumber) {
        if (serialNumber == null) {
            return;
        }

        String key = getSerialNumberCacheKey(serialNumber.getSerialNumberId());

        EIPService.getCacheService().put(key, serialNumber);
    }

    /**
     * 删除编号规则实例缓存.
     * 
     * @param serialNumberId
     */
    private static void removeSerialNumberCache(String serialNumberId) {
        String key = getSerialNumberCacheKey(serialNumberId);
        EIPService.getCacheService().remove(key);
    }

    /**
     * 获得编号规则的缓存key.
     * 
     * @param serialNumberId
     * @return
     */
    private static String getSerialNumberCacheKey(String serialNumberId) {
        return CACHE_PRE + "CsSerialNumber-" + serialNumberId;
    }

    /**
     * 从缓存中获得编号规则实例.
     * 
     * @param serialNumberId
     * @return
     */
    public static CsSerialNumber getEntityByModuleNoAndCategory2NoFromCache(String moduleNo, String category2No) {
        String key = getEntityByModuleNoAndCategory2NoCacheKey(moduleNo, category2No);
        Object obj = EIPService.getCacheService().get(key);

        if (obj != null && obj instanceof CsSerialNumber) {
            return (CsSerialNumber) obj;
        }

        return null;
    }

    /**
     * 将编号规则实例放入缓存中.
     * 
     * @param serialNumber
     */
    public static void putEntityByModuleNoAndCategory2NoIntoCache(CsSerialNumber serialNumber) {
        if (serialNumber == null) {
            return;
        }

        String key = getEntityByModuleNoAndCategory2NoCacheKey(serialNumber.getModuleNo(), 
                serialNumber.getCategory2No());

        EIPService.getCacheService().put(key, serialNumber);
    }

    /**
     * 删除编号规则实例缓存.
     * 
     * @param serialNumberId
     */
    private static void removeEntityByModuleNoAndCategory2NoCache(String moduleNo, String category2No) {
        String key = getEntityByModuleNoAndCategory2NoCacheKey(moduleNo, category2No);
        EIPService.getCacheService().remove(key);
    }

    /**
     * 获得编号规则的缓存key.
     * 
     * @param serialNumberId
     * @return
     */
    private static String getEntityByModuleNoAndCategory2NoCacheKey(String moduleNo, String category2No) {
        return CACHE_PRE + "CsSerialNumber-moduleNo-category2No-" + moduleNo + "-" + category2No;
    }

    /**
     * 删除编号规则实例、明细、流水号的所有缓存.
     * 
     * @param serialNumber 编号规则实例
     */
    public static void removeAllSerialNumberCache(CsSerialNumber serialNumber) {
        if (serialNumber == null) {
            return;
        }

        removeSerialNumberCache(serialNumber.getSerialNumberId());
        removeRecordsCache(serialNumber.getSerialNumberId());
        removeEntityByModuleNoAndCategory2NoCache(serialNumber.getModuleNo(), serialNumber.getCategory2No());
        removeAllIndexNoBySerialNumberIdCache(serialNumber.getSerialNumberId());
    }

    /**
     * 从缓存中获取编号规则明细集合.
     * 
     * @param serialNumberId
     * @return
     */
    @SuppressWarnings({
        "unchecked", "rawtypes"
    })
    public static List < CsSerialNumberRecord > getRecordsFromCache(String serialNumberId) {
        String key = getRecordsCacheKey(serialNumberId);
        Object obj = EIPService.getCacheService().get(key);

        if (obj != null && obj instanceof List) {
            return (List) obj;
        }

        return null;
    }

    /**
     * 将编号规则明细集合放入缓存中.
     * 
     * @param serialNumberId
     * @param listRecord
     */
    public static void putRecordsInfoCache(String serialNumberId, List < CsSerialNumberRecord > listRecord) {
        String key = getRecordsCacheKey(serialNumberId);
        EIPService.getCacheService().put(key, (Serializable) listRecord);
    }

    /**
     * 删除编号规则明细集合的缓存.
     * 
     * @param serialNumberId
     */
    private static void removeRecordsCache(String serialNumberId) {
        String key = getRecordsCacheKey(serialNumberId);
        EIPService.getCacheService().remove(key);
    }

    /**
     * 获取编号规则明细集合的缓存key.
     * 
     * @param serialNumberId
     * @return
     */
    private static String getRecordsCacheKey(String serialNumberId) {
        return CACHE_PRE + "SerialNumberRecords-" + serialNumberId;
    }

    // ~ Fields
    // ================================================================================================================

    // ~ Constructors
    // ================================================================================================================

    // ~ Methods
    // ================================================================================================================

}
