package com.supporter.prj.cneec.tpc.util;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.exception.UnauthorizedAccessException;
import com.supporter.prj.eip_service.module.entity.IModule;
import com.supporter.prj.eip_service.module.entity.IOper;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**
 * @Title: AuthUtil
 * @Description: 权限控制操作类
 * @author: yanweichao
 * @date: 2017-12-11
 * @version: V1.0
 */
public class AuthUtil {

	/**
	 * 判断是否有权限（无权限则跳转到无访问权限页面）
	 * @param user 当前操作用户
	 * @param operInnerName
	 * @param moduleId 应用id（应用编号）
	 * @param entityId 对象ID
	 * @param entityObj 对象
	 */
	public static void canAccess(UserProfile user, String operInnerName, String moduleId, String entityId, Object entityObj) {
		IModule module = EIPService.getModuleService().getModule(moduleId);
		IOper oper = EIPService.getModuleService().getOper(operInnerName, module);
		boolean canAccess = EIPService.getAuthorityService().canAccess(user, oper, entityId, entityObj);
		if (!canAccess) {
			throw new UnauthorizedAccessException("AUTH-001");
		}
	}

	/**
	 * 判断是否有权限（无权限则返回无访问权限提示）
	 * @param user 当前操作用户
	 * @param operInnerName
	 * @param moduleId 应用id（应用编号）
	 * @param entityId 对象ID
	 * @param entityObj 对象
	 */
	public static void canExecute(UserProfile user, String operInnerName, String moduleId, String entityId, Object entityObj) {
		IModule module = EIPService.getModuleService().getModule(moduleId);
		IOper oper = EIPService.getModuleService().getOper(operInnerName, module);
		boolean canAccess = EIPService.getAuthorityService().canAccess(user, oper, entityId, entityObj);
		if (!canAccess) {
			throw new UnauthorizedAccessException("AUTH-002");
		}
	}

	/**
	 * 数据显示权限过滤（判断获取数据范围的权限）
	 * @param user 当前操作用户
	 * @param moduleId 应用id（应用编号）
	 * @param authOperName 操作授权编号
	 * @return
	 */
	public static String getAuthFilter(UserProfile user, String moduleId, String authOperName) {
		String authFilter = EIPService.getAuthorityService().getHqlFilter(user, moduleId, authOperName);
		return authFilter;
	}

}
