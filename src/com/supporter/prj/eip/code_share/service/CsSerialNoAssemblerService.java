package com.supporter.prj.eip.code_share.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.code_share.entity.CsSerialNumber;
import com.supporter.prj.eip.code_share.entity.CsSerialNumberRecord;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.module.service.IModuleAssembler;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.binding.JsonUtil;
/**
 * 编号规则应用装配服务
 * @author lisq
 *
 */
@Service
public class CsSerialNoAssemblerService implements IModuleAssembler {

    @Autowired
    private CsSerialNumberService sNoService;
    /**
     * 包检查.
     * @param arg0 参数
     * @return 返回结果
     */
    @Override
    public OperResult < ? > checkBeforePackage(String arg0) {
        return OperResult.succeed(null);
    }

    /**
     * 生成数据文件.
     * @param userProfile 用户信息
     * @param moduleId 应用id
     */
    @Override
    public OperResult < List < File > > getDataFiles(UserProfile userProfile, String moduleId) {
        OperResult<List<File>> op = null;
        List<File> list = new ArrayList<File>();

        try {
            //编码规则基础信息
            List <CsSerialNumber> listno=this.sNoService.getSerialNumberByModuleNo(moduleId);
            if(listno != null && listno.size() > 0){
                File snoFile = this.getFile(moduleId, "SERIALNO/BASE");
                String snoJson=JsonUtil.toJson(listno);
                FileUtils.writeStringToFile(snoFile, snoJson, "UTF-8");
                list.add(snoFile);

                //编码规则明细信息
                List<CsSerialNumberRecord> listrecord = new ArrayList<CsSerialNumberRecord>();
                for(CsSerialNumber sno:listno){
                    List<CsSerialNumberRecord> listrecord_t = this.sNoService.getSerialNumberRecords(sno.getSerialNumberId());
                    CollectionUtils.addAll(listrecord, listrecord_t.toArray());
                }
                File recordFile = this.getFile(moduleId, "SERIALNO/RECORD");
                String recordJson=JsonUtil.toJson(listrecord);
                FileUtils.writeStringToFile(recordFile, recordJson, "UTF-8");
                list.add(recordFile);
            }
        } catch (Exception e) {
            op = OperResult.fail("sno_json_error", "导出编号规则装配文件失败"
                    + e.toString());
            return op;
        }
        op = OperResult.succeed(list);
        return op;
    }

    /**
     * 安装.
     * @param user 用户信息
     * @param moduleId 应用id
     */
    @Override
    public OperResult < ? > install(UserProfile user, String moduleId) {
        try {
            //解析编码规则信息
            File snoFile = this.getFile(moduleId, "SERIALNO/BASE");
            if(snoFile.exists()){
                String snoJson = FileUtils.readFileToString(snoFile, "UTF-8");
                List < CsSerialNumber > snolist = JsonUtil.toList(snoJson, CsSerialNumber.class);
                //解析编码规则明细信息
                File recordFile = this.getFile(moduleId, "SERIALNO/RECORD");
                List < CsSerialNumberRecord > recordList = null;
                if(recordFile.exists()){
                    String recordJson = FileUtils.readFileToString(recordFile, "UTF-8");
                    recordList = JsonUtil.toList(recordJson, CsSerialNumberRecord.class);
                }
                if (snolist != null) {
                    for (CsSerialNumber sno : snolist) {
                        String snoid=sno.getSerialNumberId();
                        sno.setSerialNumberId("");
                        List<CsSerialNumberRecord> subrecordList = new ArrayList<CsSerialNumberRecord>();
                        if(recordList != null){
                            for(CsSerialNumberRecord record:recordList){
                                if(record.getSerialNumberId().equals(snoid)){
                                    //record.setRecordId("");
                                    subrecordList.add(record);
                                }
                            }
                        }
                        this.sNoService.saveOrUpdate(sno, JsonUtil.toJson(subrecordList), user, true);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return OperResult.succeed(null);
    }

    /**.
     * 根据应用id卸载应用
     * @param moduleId 应用id
     * @param user 用户 信息
     * @return 返回存储位置
     */
    @Override
    public OperResult < ? > uninstall(UserProfile user, String moduleId) {

        List <CsSerialNumber> listno = this.sNoService.getSerialNumberByModuleNo(moduleId);
        if(listno !=null && listno.size()>0){
            List<String> snoidList = new ArrayList<String>();
            for(CsSerialNumber sno : listno){
                snoidList.add(sno.getSerialNumberId());
            }
            this.sNoService.delete(user, snoidList);
        }
        return OperResult.succeed(null);
    }

    /**.
     * 根据应用id获取存储位置
     * @param moduleId 应用id
     * @return 返回存储位置
     */
    public File getFile(String moduleId, String position) {
        File file = new File(EIPService.getModuleService()
                .getAssembleDirectory(moduleId), position);
        return file;
    }

    @Override
    public OperResult<?> checkBeforeInstall(UserProfile paramUserProfile,
            String paramString) {
        return OperResult.succeed(null);
    }

    @Override
    public OperResult<?> checkBeforeUninstall(UserProfile paramUserProfile,
            String paramString) {
        return OperResult.succeed(null);
    }

    @Override
    public OperResult<?> checkInService(UserProfile paramUserProfile,
            String paramString) {
        return OperResult.succeed(null);
    }

}
