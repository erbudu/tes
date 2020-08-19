package com.supporter.prj.eip.code_share.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.code_share.constant.DateTypeValueEnum;
import com.supporter.prj.eip.code_share.constant.DeptTypeValueEnum;
import com.supporter.prj.eip.code_share.constant.NumberingTypeEnum;
import com.supporter.prj.eip.code_share.constant.PersonTypeValueEnum;
import com.supporter.prj.eip.code_share.constant.RecordTypeEnum;
import com.supporter.prj.eip.code_share.constant.RegenerateTypeEnum;
import com.supporter.prj.eip.code_share.constant.SerialNumberModeEnum;
import com.supporter.prj.eip.code_share.constant.SystemLabelEnum;
import com.supporter.prj.eip.code_share.entity.CsSerialNumber;
import com.supporter.prj.eip.code_share.service.ISerialNumberService;
import com.supporter.prj.eip.code_share.service.CsSerialNumberBusinessService;
import com.supporter.prj.eip.code_share.util.SerialNumberCacheUtil;
import com.supporter.prj.eip.code_share.util.SerialNumberThread;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.module.entity.IDomainObjectAttr;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

// ~ File Information
// ====================================================================================================================

/**
 * 编号规则controller类.
 * 
 * @author 康博
 * @createDate 2017-5-3
 * @since 6.0
 *
 */
@Controller
@RequestMapping("/eip/code_share")
public class CsSerialNumberController extends AbstractController {

    // ~ Static Fields
    // ================================================================================================================
	
    /**
     * UID.
     */
    private static final long serialVersionUID = 201705031500L;

    // ~ Fields
    // ================================================================================================================

    /**
     * {@link ISerialNumberService}.
     */
    @Autowired
    private ISerialNumberService service;

    /**
     * {@link SerialNumberBusinessService}.
     */
    @Autowired
    private CsSerialNumberBusinessService serialNumberBusinessService;

    // ~ Constructors
    // ================================================================================================================

    // ~ Methods
    // ================================================================================================================

    /**
     * 获得明细类型.
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/presetData", method = RequestMethod.GET)
    public OperResult < Map < String, Object > > getPresetData() {

        //优先从缓存中读取
        OperResult < Map < String, Object > > cacheResult = SerialNumberCacheUtil.getPresetDataFromCache();

        if (cacheResult != null) {
            return cacheResult;
        }

        OperResult < Map < String, Object > > operResult = new OperResult < Map < String, Object > > ();
        Map < String, Object > result = new HashMap < String, Object > ();

        //明细类型
        List < Map < String, Object > > listRecordType = new ArrayList < Map < String, Object > > ();

        for (RecordTypeEnum recordTypeEnum : RecordTypeEnum.values()) {
            Map < String, Object > mapRecordType = new HashMap < String, Object > ();
            mapRecordType.put("innerName", recordTypeEnum.getInnerName());
            mapRecordType.put("displayName", recordTypeEnum.getDisplayName());
            listRecordType.add(mapRecordType);
        }

        result.put("recordType", listRecordType);

        //系统值类型对应的label
        List < Map < String, Object > > listSystemLabel = new ArrayList < Map < String, Object > > ();

        for (SystemLabelEnum systemLabelEnum : SystemLabelEnum.values()) {
            Map < String, Object > mapSystemLabel = new HashMap < String, Object > ();
            mapSystemLabel.put("innerName", systemLabelEnum.getInnerName());
            mapSystemLabel.put("displayName", systemLabelEnum.getDisplayName());
            mapSystemLabel.put("valueType", systemLabelEnum.getValueType());
            listSystemLabel.add(mapSystemLabel);
        }

        result.put("systemLabel", listSystemLabel);

        //日期类型对应的值
        List < Map < String, Object > > listDateTypeValue = new ArrayList < Map < String, Object > > ();

        for (DateTypeValueEnum dateTypeValueEnum : DateTypeValueEnum.values()) {
            Map < String, Object > mapDateTypeValue = new HashMap < String, Object > ();
            mapDateTypeValue.put("innerName", dateTypeValueEnum.getInnerName());
            mapDateTypeValue.put("displayName", dateTypeValueEnum.getDisplayName());
            listDateTypeValue.add(mapDateTypeValue);
        }

        result.put("dateTypeValue", listDateTypeValue);

        //部门类型对应的值
        List < Map < String, Object > > listDeptTypeValue = new ArrayList < Map < String, Object > > ();

        for (DeptTypeValueEnum deptTypeValueEnum : DeptTypeValueEnum.values()) {
            Map < String, Object > mapDeptTypeValue = new HashMap < String, Object > ();
            mapDeptTypeValue.put("innerName", deptTypeValueEnum.getInnerName());
            mapDeptTypeValue.put("displayName", deptTypeValueEnum.getDisplayName());
            listDeptTypeValue.add(mapDeptTypeValue);
        }

        result.put("deptTypeValue", listDeptTypeValue);

        //人员类型对应的值
        List < Map < String, Object > > listPersonTypeValue = new ArrayList < Map < String, Object > > ();

        for (PersonTypeValueEnum personTypeValueEnum : PersonTypeValueEnum.values()) {
            Map < String, Object > mapPersonTypeValue = new HashMap < String, Object > ();
            mapPersonTypeValue.put("innerName", personTypeValueEnum.getInnerName());
            mapPersonTypeValue.put("displayName", personTypeValueEnum.getDisplayName());
            listPersonTypeValue.add(mapPersonTypeValue);
        }

        result.put("personTypeValue", listPersonTypeValue);

        //流水号类型(用于下拉框使用的json)
        Map < String, Object > mapNumberingTypeSelect = new LinkedHashMap < String, Object > ();

        //流水号类型
        Map < String, Object > mapNumberingType = new LinkedHashMap < String, Object > ();

        for (NumberingTypeEnum numberingTypeEnum : NumberingTypeEnum.values()) {
            mapNumberingTypeSelect.put(numberingTypeEnum.getInnerName(), numberingTypeEnum.getDisplayName());
            mapNumberingType.put("innerName", numberingTypeEnum.getInnerName());
            mapNumberingType.put("displayName", numberingTypeEnum.getDisplayName());
            mapNumberingType.put("dataType", numberingTypeEnum.getDataType());
        }

        result.put("numberingTypeSelect", mapNumberingTypeSelect);
        result.put("numberingType", mapNumberingType);
        //重新生成类型
        Map < String, Object > mapRegenerateType = new LinkedHashMap < String, Object > ();

        for (RegenerateTypeEnum regenerateTypeEnum : RegenerateTypeEnum.values()) {

            mapRegenerateType.put(regenerateTypeEnum.getInnerName(), regenerateTypeEnum.getDisplayName());
        }

        result.put("regenerateTypeSelect", mapRegenerateType);
        operResult.setBody(result);

        SerialNumberCacheUtil.putPresetDataInfoCache(operResult);

        return operResult;
    }

    /**
     * 保存或更新实体及明细信息.
     * 
     * @param serialNumber 编号规则实例
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/entity", method = RequestMethod.POST)
    public OperResult < CsSerialNumber > saveOrUpdate(CsSerialNumber serialNumber) {
        String serialNumberRecordsJson = this.getRequestPara("serialNumberRecordsJson");
        boolean clearNumbering = Boolean.valueOf(this.getRequestPara("clearNumbering"));

        return service.saveOrUpdate(serialNumber, serialNumberRecordsJson, getUserProfile(), clearNumbering);
    }

    /**
     * 根据编号规则id获得编号规则实例.
     * 
     * @param serialNumberId 编号规则id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/entity/{serialNumberId}", method = RequestMethod.GET)
    public OperResult < CsSerialNumber > getSerialNumber(@PathVariable String serialNumberId) {
        return service.getSerialNumberById(serialNumberId);
    }

    /**
     * 获得编号规则列表数据.
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/serialNumberList", method = RequestMethod.GET)
    public ListPage < CsSerialNumber > getSerialNumberList(JqGridReq jqGridReq, HttpServletRequest request) {
    	UserProfile user = getUserProfile();
        JqGrid jqGrid = jqGridReq.initJqGrid(request);
        Map < String, Object > searchParam = this.getRequestParameters();

        
        return service.getSerialNumberListByPage(user, jqGrid, searchParam);
    }

    /**
     * 根据所属应用与二级分类编号获取编号规则实例.
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/entity/moduleNo/category2No", method = RequestMethod.GET)
    public OperResult < CsSerialNumber > getEntityByModuleNoAndCategory2No() {
        String moduleNo = this.getRequestPara("moduleNo");
        String category2No = this.getRequestPara("category2No");

        return service.getEntityByModuleNoAndCategory2No(moduleNo, category2No);
    }

    /**
     * 批量删除.
     * 
     * @param ids 编号规则id集合
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/entity/delete/{ids}", method = RequestMethod.POST)
    public OperResult < String > delete(@PathVariable String ids) {
        List < String > listId = Arrays.asList(ids.split(","));
        UserProfile user = getUserProfile();
        return service.delete(user, listId);
    }

    /**
     * 获得编号规则方式，用于前台展现.
     * 
     * @param ids 编号规则id集合
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/serialNumberMode")
    public Map < String, String > getSerialNumberMode() {
        Map < String, String > result = new HashMap < String, String > ();

        for (SerialNumberModeEnum serialNumberModeEnum : SerialNumberModeEnum.values()) {
            result.put(String.valueOf(serialNumberModeEnum.getInnerName()), serialNumberModeEnum.getDisplayName());
        }

        return result;
    }

    @RequestMapping(value = "/generateNumber")
    @ResponseBody
    public OperResult < String > generateNumber() {
        String moduleNo = this.getRequestPara("moduleNo");
        String category2No = this.getRequestPara("category2No");

        UserProfile user = getUserProfile();
        int year = user.getDBYear();
        int month = new Date().getMonth() + 1;

        String indexNo = 
                serialNumberBusinessService.generateNumber(year, month, moduleNo, category2No, new Object(), user);

        OperResult < String > result = new OperResult < String > ();
        result.setBody(indexNo);

        return result;
    }

    @RequestMapping(value = "/generateNumbers")
    @ResponseBody
    public OperResult < List < String > > generateNumbers() {
        String moduleNo = this.getRequestPara("moduleNo");
        String category2No = this.getRequestPara("category2No");

        UserProfile user = getUserProfile();
        int year = user.getDBYear();
        int month = new Date().getMonth() + 1;
        List < String > listNo = new ArrayList < String > ();

        for (int i = 0; i < 30; i++) {
            SerialNumberThread st = new SerialNumberThread(year, month, moduleNo, category2No, user);
            Thread thread = new Thread(st);
            thread.start();
        }

        OperResult < List < String > > result = new OperResult < List < String > > ();
        result.setBody(listNo);

        return result;
    }
    @RequestMapping(value = "/getDomainObjectAttr", method = RequestMethod.GET)
    @ResponseBody
    public OperResult< List< IDomainObjectAttr>> getDomainObjectAttr(String objectId){
        OperResult < List < IDomainObjectAttr > > result = new OperResult < List < IDomainObjectAttr > > ();
        result.setBody(service.getDomainObjectAttr(objectId));
        return result;
    }
}
