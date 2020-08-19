package com.supporter.prj.eip.code_share.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.busi_entity.IBusiEntity;
import com.supporter.prj.eip.code_share.constant.NumberingTypeEnum;
import com.supporter.prj.eip.code_share.constant.RecordTypeEnum;
import com.supporter.prj.eip.code_share.constant.SerialNumberConstant;
import com.supporter.prj.eip.code_share.constant.SerialNumberModeEnum;
import com.supporter.prj.eip.code_share.entity.CsSerialNumber;
import com.supporter.prj.eip.code_share.entity.CsSerialNumberRecord;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.module.entity.IDomainObjectAttr;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;

/**
 * 编号规则实现对外接口的service。
 * 
 * @author 康博
 * @createDate 2017-5-18
 * @since 6.0
 *
 */
@Service
@Transactional(TransManager.APP)
public class CsSerialNumberBusinessService {
	@Autowired
	private ISerialNumberService service;
	@Autowired
	private CsSerialNoCommonService serialNoCommonService;

	/**
	 * 按照规则生成编号
	 * 
	 * @param year 年
	 * @param month 月
	 * @param moduleNo 应用编号
	 * @param category2No 二级分类编号
	 * @param entity 业务实例
	 * @param user 当前登陆用户
	 * @return
	 */
	public String generateNumber(int year, int month, String moduleNo, String category2No, Object entity,
			UserProfile user) {

		if (StringUtils.isEmpty(moduleNo) || user == null) {
			return "";
		}

		CsSerialNumber serialNumber = service.getSerialNumberByModuleNoAndCategory2No(moduleNo, category2No);

		if (serialNumber == null) {
			EIPService.getLogService(SerialNumberConstant.LOG_TYPE_DEBUG).error("数据库中没有找到编号规则记录");
			return "";
		}

		// 手动输入时返回空
		if (serialNumber.getSerialNumberMode() == SerialNumberModeEnum.MANUAL_INPUT.getInnerName()) {
			return "";
		}

		List<CsSerialNumberRecord> listRecord = service.getSerialNumberRecords(serialNumber.getSerialNumberId());

		if (listRecord == null) {
			return "";
		}

		StringBuilder result = new StringBuilder();
		for (int i = 0; i < listRecord.size(); i++) {
			CsSerialNumberRecord serialNumberRecord = listRecord.get(i);
			// 根据明细获得对应的规则值
			result.append(getInfoByRecord(year, month, serialNumber, serialNumberRecord, entity));
		}

		return result.toString();
	}

	/**
	 * 根据明细获得对应的规则值.
	 * @param year 年
	 * @param month 月
	 * @param serialNumber 规则对象
	 * @param record 规则明细
	 * @param entity 业务对象
	 * @return String 编号某个组成项的实际值 
	 */
	private String getInfoByRecord(int year, int month, CsSerialNumber serialNumber,
			CsSerialNumberRecord record, Object entity) {
		String recordType = record.getRecordType();

		if (StringUtils.isEmpty(recordType)) {
			return "";
		}

		String recordTypeLabel = record.getRecordTypeLabel();
		String recordTypeValue = record.getRecordTypeValue();

		if (recordType.equals(RecordTypeEnum.FIXED_VALUE.getInnerName())) {
			//固定值
			return recordTypeValue;
		} else if (recordType.equals(RecordTypeEnum.DATE_VALUE.getInnerName())) {
			//日期
			if (recordTypeLabel.equals("CURRENT_DATETIME")) {
				return CommonUtil.format(new Date(), recordTypeValue);
			} else {
				return getBusinessValueInfo(record, entity, recordTypeLabel, recordTypeValue);
			}

		} else if (recordType.equals(RecordTypeEnum.BUSINESS_VALUE.getInnerName())) {
			//元数据
			return getBusinessValueInfo(record, entity, recordTypeLabel, recordTypeValue);
		} else if (recordType.equals(RecordTypeEnum.NUMBER_VALUE.getInnerName())) {
			//顺序号
			if (StringUtils.isNotEmpty(serialNumber.getNumberingType())) {
				String orderById = serialNumber.getNumberingBaseDeptId();
				if (serialNumber.getNumberingType().equals(NumberingTypeEnum.ENTITY.getInnerName())) {
					orderById = ((IBusiEntity) entity).getKeyValues();
				}
				return serialNoCommonService.getNumbering(year, month, serialNumber, orderById);
			}
		}

		return "";
	}

	/**
	 * 获得业务元素对象对应的规则值(元数据类型的).
	 * 
	 * @param entity 业务对象
	 * @param recordTypeLabel 组成项标签
	 * @param recordTypeValue 组成项值
	 * @return String 该组成项的实际编码
	 */
	private String getBusinessValueInfo(CsSerialNumberRecord record, Object entity,
			String recordTypeLabel, String recordTypeValue) {

		IDomainObjectAttr attr = EIPService.getModuleService().getDomainObjectAttr(recordTypeLabel);
		if (attr == null) {
			return "";
		}
		// 获取元数据属性名
		String attrName = attr.getHqlName();
		Object attrValue = null;

		try {
			attrValue = PropertyUtils.getProperty(entity, attrName);
		} catch (Exception e) {
			EIPService.getLogService(SerialNumberConstant.LOG_TYPE_DEBUG).error("生成编号规则时获取业务对象属性值时失败", e);
		}
		if (attrValue == null) {
			return "";
		}

		// 元数据的数据类型
		int dataType = attr.getDataType();
		record.setDataType(dataType);

		if (dataType == IDomainObjectAttr.DataType.DATETIME) {
			return CommonUtil.format((Date) attrValue, recordTypeValue);
		} else if (dataType == IDomainObjectAttr.DataType.COMPANY || dataType == IDomainObjectAttr.DataType.ORG_NODE) {
			Dept dept = EIPService.getDeptService().getDept(attrValue.toString());
			if (dept == null) {
				EIPService.getLogService(SerialNumberConstant.LOG_TYPE_DEBUG).error("根据业务对象属性值找不到对应的部门或单位");
				return "";
			}
			return getValueInfo(dept, recordTypeValue);
		} else if (dataType == IDomainObjectAttr.DataType.PERSON) {
			Person person = EIPService.getEmpService().getEmp(attrValue.toString());
			if (person == null) {
				return "";
			}
			return getValueInfo(person, recordTypeValue);
		} else if (dataType == IDomainObjectAttr.DataType.DOMAIN_OBJECT) {
			return "";
		} else {
			return attrValue.toString();
		}
	}

	/**
	 * 获得单位或者部门的规则值.
	 * @param entity 业务对象
	 * @param recordTypeValue 组成项类型
	 * @return String
	 */
	private String getValueInfo(Object entity, String recordTypeValue) {
		// 获取元数据属性信息
		IDomainObjectAttr attr = EIPService.getModuleService().getDomainObjectAttr(recordTypeValue);

		if (attr == null) {
			return "";
		}
		// 获取当前所属单位对应的属性
		try {
			Object value = PropertyUtils.getProperty(entity, attr.getHqlName());
			if (value == null) {
				return "";
			} else {
				return value.toString();
			}
		} catch (Exception e) {
			EIPService.getLogService(SerialNumberConstant.LOG_TYPE_DEBUG).error("生成编号规则时获取业务对象属性值时失败", e);
		}
		return "";
	}
}
