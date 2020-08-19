package com.supporter.prj.eip.code_share.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.code_share.constant.NumberingTypeEnum;
import com.supporter.prj.eip.code_share.constant.SerialNumberConstant;
import com.supporter.prj.eip.code_share.dao.CsSerialCompanyNoDao;
import com.supporter.prj.eip.code_share.dao.CsSerialDeptNoDao;
import com.supporter.prj.eip.code_share.dao.CsSerialEntityNoDao;
import com.supporter.prj.eip.code_share.dao.CsSerialMonCompanyNoDao;
import com.supporter.prj.eip.code_share.dao.CsSerialMonDeptNoDao;
import com.supporter.prj.eip.code_share.dao.CsSerialMonEntityNoDao;
import com.supporter.prj.eip.code_share.dao.CsSerialMonUniformNoDao;
import com.supporter.prj.eip.code_share.dao.CsSerialRandomNoDao;
import com.supporter.prj.eip.code_share.dao.CsSerialUniformNoDao;
import com.supporter.prj.eip.code_share.dao.CsSerialYearCompanyNoDao;
import com.supporter.prj.eip.code_share.dao.CsSerialYearDeptNoDao;
import com.supporter.prj.eip.code_share.dao.CsSerialYearEntityNoDao;
import com.supporter.prj.eip.code_share.dao.CsSerialYearUniformNoDao;
import com.supporter.prj.eip.code_share.entity.CsSerialCompanyNo;
import com.supporter.prj.eip.code_share.entity.CsSerialDeptNo;
import com.supporter.prj.eip.code_share.entity.CsSerialEntityNo;
import com.supporter.prj.eip.code_share.entity.CsSerialMonCompanyNo;
import com.supporter.prj.eip.code_share.entity.CsSerialMonDeptNo;
import com.supporter.prj.eip.code_share.entity.CsSerialMonEntityNo;
import com.supporter.prj.eip.code_share.entity.CsSerialMonUniformNo;
import com.supporter.prj.eip.code_share.entity.CsSerialNumber;
import com.supporter.prj.eip.code_share.entity.CsSerialRandomNo;
import com.supporter.prj.eip.code_share.entity.CsSerialUniformNo;
import com.supporter.prj.eip.code_share.entity.CsSerialYearCompanyNo;
import com.supporter.prj.eip.code_share.entity.CsSerialYearDeptNo;
import com.supporter.prj.eip.code_share.entity.CsSerialYearEntityNo;
import com.supporter.prj.eip.code_share.entity.CsSerialYearUniformNo;
import com.supporter.prj.eip.code_share.entity.base.CsSerialCompanyNoKey;
import com.supporter.prj.eip.code_share.entity.base.CsSerialDeptNoKey;
import com.supporter.prj.eip.code_share.entity.base.CsSerialEntityNoKey;
import com.supporter.prj.eip.code_share.entity.base.CsSerialMonCompanyNoKey;
import com.supporter.prj.eip.code_share.entity.base.CsSerialMonDeptNoKey;
import com.supporter.prj.eip.code_share.entity.base.CsSerialMonEntityNoKey;
import com.supporter.prj.eip.code_share.entity.base.CsSerialMonUniformNoKey;
import com.supporter.prj.eip.code_share.entity.base.CsSerialYearCompanyNoKey;
import com.supporter.prj.eip.code_share.entity.base.CsSerialYearDeptNoKey;
import com.supporter.prj.eip.code_share.entity.base.CsSerialYearEntityNoKey;
import com.supporter.prj.eip.code_share.entity.base.CsSerialYearUniformNoKey;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;

/**
 * 流水号的公共service类.
 * 
 * @author 康博
 * @createDate 2017-5-22
 * @since 6.0
 *
 */
@Service
@Transactional(TransManager.APP)
public class CsSerialNoCommonService {
    @Autowired
    private CsSerialCompanyNoDao companyNoDao;
    @Autowired
    private CsSerialEntityNoDao entityNoDao;
    @Autowired
    private CsSerialDeptNoDao deptNoDao;
    @Autowired
    private CsSerialUniformNoDao uniformNoDao;
    @Autowired
    private CsSerialMonUniformNoDao monUniformNoDao;
    @Autowired
    private CsSerialYearUniformNoDao yearUniformNoDao;
    @Autowired
    private CsSerialMonCompanyNoDao monCompanyNoDao;
    @Autowired
    private CsSerialMonEntityNoDao monEntityNoDao;
    @Autowired
    private CsSerialMonDeptNoDao monDeptNoDao;
    @Autowired
    private CsSerialYearCompanyNoDao yearCompanyNoDao;
    @Autowired
    private CsSerialYearEntityNoDao yearEntityNoDao;
    @Autowired
    private CsSerialYearDeptNoDao yearDeptNoDao;
    @Autowired
    private CsSerialRandomNoDao randomNoDao;


    /**
     * 获得顺序号.
     * 1、先从缓存中获取当前流水号以及最大流水号
     * 2、若缓存不存在或者当前流水号超过最大流水号的80%，则从数据库中获取流水号实例，将indexNo +20，更新数据库，并更新缓存
     * 
     * @param serialNumber
     * @return
     */
    public synchronized String getNumbering(int year, int month, CsSerialNumber serialNumber, String orderById) {
        String indexNoStr = "";
        String serialNumberId = serialNumber.getSerialNumberId();
        String numberingType = serialNumber.getNumberingType();
        String regenerateType = serialNumber.getRegenerateType();

        int indexNo = 0;
        if (NumberingTypeEnum.ROUND_NUMBER.getInnerName().equals(numberingType)) {
        	//随机数
            CsSerialRandomNo entity = randomNoDao.get(serialNumberId);

            if (entity == null) {
                entity = new CsSerialRandomNo();
                entity.setSerialNumberId(serialNumberId);
                entity.setIndexNo(serialNumber.getNumberingStart());
                randomNoDao.save(entity);
            } else {
            	entity.setIndexNo(entity.getIndexNo() + 1);
                randomNoDao.update(entity);
            }
            indexNo = entity.getIndexNo();
            
        } else if (NumberingTypeEnum.COMPANY.getInnerName().equals(numberingType) && regenerateType.equals("NEVER_VALUE") ) {
        	//按单位编号（重复类型为从不）
            CsSerialCompanyNoKey entityKey = new CsSerialCompanyNoKey(serialNumberId, orderById);
            CsSerialCompanyNo entity = companyNoDao.get(entityKey);

            if (entity == null) {
                entity = new CsSerialCompanyNo(entityKey);
                entity.setIndexNo(serialNumber.getNumberingStart());
                companyNoDao.save(entity);
            } else {
            	entity.setIndexNo(entity.getIndexNo() + 1);
                companyNoDao.update(entity);
            }
            indexNo = entity.getIndexNo();
            
        } else if (NumberingTypeEnum.ENTITY.getInnerName().equals(numberingType) && regenerateType.equals("NEVER_VALUE") ) {
        	//按业务对象编号（重复类型为从不）
            CsSerialEntityNoKey entityKey = new CsSerialEntityNoKey(serialNumberId, orderById);
            CsSerialEntityNo entity = entityNoDao.get(entityKey);

            if (entity == null) {
                entity = new CsSerialEntityNo(entityKey);
                entity.setIndexNo(serialNumber.getNumberingStart());
                entityNoDao.save(entity);
            } else {
            	entity.setIndexNo(entity.getIndexNo() + 1);
            	entityNoDao.update(entity);
            }
            indexNo = entity.getIndexNo();

        } else if (NumberingTypeEnum.DEPT.getInnerName().equals(numberingType) && regenerateType.equals("NEVER_VALUE")) {
        	//按部门编号（重复类型为从不）
            CsSerialDeptNoKey entityKey = new CsSerialDeptNoKey(serialNumberId, orderById);
            CsSerialDeptNo entity = deptNoDao.get(entityKey);

            if (entity == null) {
                entity = new CsSerialDeptNo(entityKey);
                entity.setIndexNo(serialNumber.getNumberingStart());
                deptNoDao.save(entity);
            } else {
            	entity.setIndexNo(entity.getIndexNo() + 1);
            	deptNoDao.update(entity);
            }
            indexNo = entity.getIndexNo();
            
        } else if (NumberingTypeEnum.COMPANY.getInnerName().equals(numberingType) && regenerateType.equals("YEAR_VALUE")) {
        	//按单位编号（重复类型 为 按年）
            CsSerialYearCompanyNoKey entityKey = new CsSerialYearCompanyNoKey(year, serialNumberId, orderById);
            CsSerialYearCompanyNo entity = yearCompanyNoDao.get(entityKey);

            if (entity == null) {
                entity = new CsSerialYearCompanyNo(entityKey);
                entity.setIndexNo(serialNumber.getNumberingStart());
                yearCompanyNoDao.save(entity);
            } else {
            	entity.setIndexNo(entity.getIndexNo() + 1);
            	yearCompanyNoDao.update(entity);
            }
            indexNo = entity.getIndexNo();
            
        } else if (NumberingTypeEnum.ENTITY.getInnerName().equals(numberingType) && regenerateType.equals("YEAR_VALUE")) {
        	//按业务对象编号（重复类型 为 按年）
            CsSerialYearEntityNoKey entityKey = new CsSerialYearEntityNoKey(year, serialNumberId, orderById);
            CsSerialYearEntityNo entity = yearEntityNoDao.get(entityKey);

            if (entity == null) {
                entity = new CsSerialYearEntityNo(entityKey);
                entity.setIndexNo(serialNumber.getNumberingStart());
                yearEntityNoDao.save(entity);
            } else {
            	entity.setIndexNo(entity.getIndexNo() + 1);
            	yearEntityNoDao.update(entity);
            }
            indexNo = entity.getIndexNo();
            
        } else if (NumberingTypeEnum.DEPT.getInnerName().equals(numberingType) && regenerateType.equals("YEAR_VALUE")) {
        	//按部门编号（重复类型 为 按年）
            CsSerialYearDeptNoKey entityKey = new CsSerialYearDeptNoKey(year, serialNumberId, orderById);
            CsSerialYearDeptNo entity = yearDeptNoDao.get(entityKey);

            if (entity == null) {
                entity = new CsSerialYearDeptNo(entityKey);
                entity.setIndexNo(serialNumber.getNumberingStart());
                yearDeptNoDao.save(entity);
            } else {
            	entity.setIndexNo(entity.getIndexNo() + 1);
            	yearDeptNoDao.update(entity);
            }
            indexNo = entity.getIndexNo();
            
        } else if (NumberingTypeEnum.UNIFORM_NUMBER.getInnerName().equals(numberingType)&& regenerateType.equals("NEVER_VALUE")) {
        	//按统一编号（重复类型为从不）
            CsSerialUniformNo entity = uniformNoDao.get(serialNumberId);

            if (entity == null) {
                entity = new CsSerialUniformNo();
                entity.setSerialNumberId(serialNumberId);
                entity.setIndexNo(serialNumber.getNumberingStart());
                uniformNoDao.save(entity);
            } else {
            	entity.setIndexNo(entity.getIndexNo() + 1);
            	uniformNoDao.update(entity);
            }
            indexNo = entity.getIndexNo();
            
        } else if (NumberingTypeEnum.UNIFORM_NUMBER.getInnerName().equals(numberingType)&& regenerateType.equals("YEAR_VALUE")) {
        	//按统一编号（重复类型为按年）
            CsSerialYearUniformNoKey entityKey = new CsSerialYearUniformNoKey(year, serialNumberId);
            CsSerialYearUniformNo entity = yearUniformNoDao.get(entityKey);

            if (entity == null) {
                entity = new CsSerialYearUniformNo(entityKey);
                entity.setIndexNo(serialNumber.getNumberingStart());
                yearUniformNoDao.save(entity);
            } else {
            	entity.setIndexNo(entity.getIndexNo() + 1);
            	yearUniformNoDao.update(entity);
            }
            indexNo = entity.getIndexNo();
            
        }  else if (NumberingTypeEnum.UNIFORM_NUMBER.getInnerName().equals(numberingType)&& regenerateType.equals("MONTH_VALUE")) {
        	//按统一编号（重复类型为按月）
            CsSerialMonUniformNoKey entityKey = new CsSerialMonUniformNoKey(year,month, serialNumberId);
            CsSerialMonUniformNo entity = monUniformNoDao.get(entityKey);

            if (entity == null) {
                entity = new CsSerialMonUniformNo(entityKey);
                entity.setIndexNo(serialNumber.getNumberingStart());
                monUniformNoDao.save(entity);
            } else {
            	entity.setIndexNo(entity.getIndexNo() + 1);
            	monUniformNoDao.update(entity);
            }
            indexNo = entity.getIndexNo();
            
        } else if (NumberingTypeEnum.COMPANY.getInnerName().equals(numberingType) && regenerateType.equals("MONTH_VALUE") ) {
        	//按单位编号（重复类型为按月）
            CsSerialMonCompanyNoKey entityKey = new CsSerialMonCompanyNoKey(year, month, serialNumberId, orderById);
            CsSerialMonCompanyNo entity = monCompanyNoDao.get(entityKey);

            if (entity == null) {
                entity = new CsSerialMonCompanyNo(entityKey);
                entity.setIndexNo(serialNumber.getNumberingStart());
                monCompanyNoDao.save(entity);
            } else {
            	entity.setIndexNo(entity.getIndexNo() + 1);
            	monCompanyNoDao.update(entity);
            }
            indexNo = entity.getIndexNo();

        } else if (NumberingTypeEnum.ENTITY.getInnerName().equals(numberingType) && regenerateType.equals("MONTH_VALUE") ) {
        	//按业务对象编号（重复类型为按月）
            CsSerialMonEntityNoKey entityKey = new CsSerialMonEntityNoKey(year, month, serialNumberId, orderById);
            CsSerialMonEntityNo entity = monEntityNoDao.get(entityKey);

            if (entity == null) {
                entity = new CsSerialMonEntityNo(entityKey);
                entity.setIndexNo(serialNumber.getNumberingStart());
                monEntityNoDao.save(entity);
            } else {
            	entity.setIndexNo(entity.getIndexNo() + 1);
            	monEntityNoDao.update(entity);
            }
            indexNo = entity.getIndexNo();

        } else if (NumberingTypeEnum.DEPT.getInnerName().equals(numberingType)&& regenerateType.equals("MONTH_VALUE") ) {
        	//按部门编号（重复类型为按月）
            CsSerialMonDeptNoKey entityKey = new CsSerialMonDeptNoKey(year, month, serialNumberId, orderById);
            CsSerialMonDeptNo entity = monDeptNoDao.get(entityKey);

            if (entity == null) {
                entity = new CsSerialMonDeptNo(entityKey);
                entity.setIndexNo(serialNumber.getNumberingStart());
                monDeptNoDao.save(entity);
            } else {
            	entity.setIndexNo(entity.getIndexNo() + 1);
            	monDeptNoDao.update(entity);
            }
            indexNo = entity.getIndexNo();
            
        } else {
            return "";
        }

        indexNoStr = String.valueOf(indexNo);

        int remain = serialNumber.getNumberingLength() - indexNoStr.length();

        if (remain < 0) {
            EIPService.getLogService(SerialNumberConstant.LOG_TYPE_DEBUG).error("编号规则流水号长度不足，请调整！");

            return "";
        }

        for (int i = 0; i < remain; i++) {
            indexNoStr = "0" + indexNoStr;
        }

        return indexNoStr;
    }

    /**
     * 根据编号规则ID删除流水号实例.
     * 
     * @param numberingType
     * @param serialNumberId
     * @param regenerateType重新生成类型
     */
    public void deleteEntityBySerialNumberId(String numberingType, String serialNumberId, String regenerateType) {
        if (NumberingTypeEnum.ROUND_NUMBER.getInnerName().equals(numberingType) ) {
            randomNoDao.deleteByProperty("serialNumberId", serialNumberId);

            return;
        } else if (NumberingTypeEnum.ENTITY.getInnerName().equals(numberingType) && regenerateType.equals("NEVER_VALUE") ) {
            randomNoDao.deleteByProperty("serialNumberId", serialNumberId);

            return;
        } else if (NumberingTypeEnum.ENTITY.getInnerName().equals(numberingType) && regenerateType.equals("YEAR_VALUE") ) {
            randomNoDao.deleteByProperty("serialNumberId", serialNumberId);

            return;
        } else if (NumberingTypeEnum.ENTITY.getInnerName().equals(numberingType) && regenerateType.equals("MONTH_VALUE") ) {
            randomNoDao.deleteByProperty("serialNumberId", serialNumberId);

            return;
        } else if (NumberingTypeEnum.COMPANY.getInnerName().equals(numberingType) && regenerateType.equals("NEVER_VALUE")) {
            companyNoDao.deleteByProperty("serialNumberId", serialNumberId);

            return;
        } else if (NumberingTypeEnum.DEPT.getInnerName().equals(numberingType) && regenerateType.equals("NEVER_VALUE")) {
            deptNoDao.deleteByProperty("serialNumberId", serialNumberId);

            return;
        } else if (NumberingTypeEnum.UNIFORM_NUMBER.getInnerName().equals(numberingType) && regenerateType.equals("NEVER_VALUE")) {
            uniformNoDao.deleteByProperty("serialNumberId", serialNumberId);

            return;
        } else if (NumberingTypeEnum.DEPT.getInnerName().equals(numberingType) && regenerateType.equals("YEAR_VALUE")) {
            yearDeptNoDao.deleteByProperty("serialNumberId", serialNumberId);

            return;
        } else if (NumberingTypeEnum.COMPANY.getInnerName().equals(numberingType) && regenerateType.equals("YEAR_VALUE")) {
            yearCompanyNoDao.deleteByProperty("serialNumberId", serialNumberId);

            return;
        } else if (NumberingTypeEnum.DEPT.getInnerName().equals(numberingType) && regenerateType.equals("MONTH_VALUE")) {
            monDeptNoDao.deleteByProperty("serialNumberId", serialNumberId);

            return;
        } else if (NumberingTypeEnum.COMPANY.getInnerName().equals(numberingType) && regenerateType.equals("MONTH_VALUE")) {
            monCompanyNoDao.deleteByProperty("serialNumberId", serialNumberId);

            return;
        } else if(NumberingTypeEnum.UNIFORM_NUMBER.getInnerName().equals(numberingType) && regenerateType.equals("MONTH_VALUE")){
            monUniformNoDao.deleteByProperty("serialNumberId", serialNumberId);
        }else if(NumberingTypeEnum.UNIFORM_NUMBER.getInnerName().equals(numberingType) && regenerateType.equals("YEAR_VALUE")){
            yearUniformNoDao.deleteByProperty("serialNumberId", serialNumberId);
        }else{
            return;
        }
    }

}
