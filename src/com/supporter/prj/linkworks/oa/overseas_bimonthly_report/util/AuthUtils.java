/**
 * 
 */
package com.supporter.prj.linkworks.oa.overseas_bimonthly_report.util;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.exception.ExceptionCode;
import com.supporter.prj.eip_service.exception.UnauthorizedAccessException;
import com.supporter.prj.eip_service.module.entity.IModule;
import com.supporter.prj.eip_service.module.entity.IOper;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**
 *<p>Title: AuthUtils</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2020年1月9日
 * 
 */
public class AuthUtils {
	
	public static void canAccess(UserProfile user, String moduleId, String operInnerName, String entityId, Object entityObj) {
		check(user, moduleId, operInnerName, entityId, entityObj, ExceptionCode.Auth.NOT_ACCESSIBLE);
	}

	public static void canExecute(UserProfile user, String moduleId, String operInnerName, String entityId, Object entityObj) {
		check(user, moduleId, operInnerName, entityId, entityObj, ExceptionCode.Auth.NOT_EXECUTABLE);
	}

	public static void check(UserProfile user, String moduleId, String operInnerName, String entityId, Object entityObj,
			String exceptionCode) {
		// 判断有无权限访问
		IModule module = EIPService.getModuleService().getModule(moduleId);
		IOper oper = EIPService.getModuleService().getOper(operInnerName, module);
		boolean canAccess = EIPService.getAuthorityService().canAccess(user, oper, entityId, entityObj);
		if (!canAccess) {
			throw new UnauthorizedAccessException(exceptionCode);
		}
	}

}
