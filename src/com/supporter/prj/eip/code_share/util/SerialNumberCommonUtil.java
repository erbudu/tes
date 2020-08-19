package com.supporter.prj.eip.code_share.util;

import java.util.Locale;

import com.supporter.prj.eip.code_share.entity.CsSerialNumber;
import com.supporter.prj.eip_service.EIPService;

// ~ File Information
// ====================================================================================================================

/**
 * 编号规则公共工具类.
 * 
 * @author 康博
 * @createDate 2017-5-12
 * @since 6.0
 *
 */
public class SerialNumberCommonUtil {

	// ~ Static Fields
	// ================================================================================================================

	// ~ Fields
	// ================================================================================================================

	// ~ Constructors
	// ================================================================================================================

	// ~ Methods
	// ================================================================================================================
	
	/**
	 * 获取当前语言对应的属性名称.
	 * 
	 * @return
	 */
	public static String getLocalLanguageProp() {
		Locale local = EIPService.getContextService().getLocale();
		
		String languageCode = "";
		int languageIndex = 0;
		
		if (local != null) {
			languageCode = local.getLanguage();
			languageIndex = EIPService.getSysRegistryService().getLanguageIndex(languageCode);
		} else {
			return CsSerialNumber.PROP_SERIAL_NUMBER_NAME_ZH;
		}
		
		if (languageIndex == 1) {
			return CsSerialNumber.PROP_SERIAL_NUMBER_NAME_ZH;
		} else if (languageIndex == 2) {
			return CsSerialNumber.PROP_SERIAL_NUMBER_NAME_EN;
		} else if (languageIndex == 3) {
			return CsSerialNumber.PROP_SERIAL_NUMBER_NAME_3;
		} else if (languageIndex == 4) {
			return CsSerialNumber.PROP_SERIAL_NUMBER_NAME_4;
		} else {
			return CsSerialNumber.PROP_SERIAL_NUMBER_NAME_ZH;
		}
	}

}
