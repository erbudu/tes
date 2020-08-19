package com.supporter.prj.pm.util;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.exception.ExceptionCode;
import com.supporter.prj.eip_service.exception.UnauthorizedAccessException;
import com.supporter.prj.eip_service.module.entity.IModule;
import com.supporter.prj.eip_service.module.entity.IOper;
import com.supporter.prj.eip_service.security.entity.UserProfile;
/**   
 * @Description: 权限工具类.
 * @author Administrator
 * @date 2018-05-14 10:10:04
 * @version V1.0   
 *
 */
public final class AuthUtils {
	private AuthUtils() {
		
	}
	
	/**
	 * 是否可访问.
	 * @param user 用户
	 * @param moduleId 应用ID
	 * @param operInnerName 操作
	 * @param entityId 业务对象ID值
	 * @param entityObj 业务对象
	 */
	public static void canAccess(UserProfile user, String moduleId,
			String operInnerName, String entityId, Object entityObj) {
		check(user, moduleId, operInnerName, entityId, entityObj, ExceptionCode.Auth.NOT_ACCESSIBLE);
	}

	/**
	 * 是否可执行.
	 * @param user 用户
	 * @param moduleId 应用ID
	 * @param operInnerName 操作
	 * @param entityId 业务对象ID值
	 * @param entityObj 业务对象
	 */
	public static void canExecute(UserProfile user, String moduleId,
			String operInnerName, String entityId, Object entityObj) {
		check(user, moduleId, operInnerName, entityId, entityObj, ExceptionCode.Auth.NOT_EXECUTABLE);
	}

	/**
	 * 操作权限校验.
	 * @param user 用户
	 * @param moduleId 应用ID
	 * @param operInnerName 操作
	 * @param entityId 业务对象ID值
	 * @param entityObj 业务对象
	 * @param exceptionCode 异常代码
	 */
	public static void check(UserProfile user, String moduleId,
			String operInnerName, String entityId, Object entityObj, String exceptionCode) {
		// 判断有无权限访问
		IModule module = EIPService.getModuleService().getModule(moduleId);
		IOper oper = EIPService.getModuleService().getOper(operInnerName, module);
		boolean canAccess = EIPService.getAuthorityService().canAccess(user, oper, entityId, entityObj);
		if (!canAccess) {
			throw new UnauthorizedAccessException(exceptionCode);
		}
	}
}
