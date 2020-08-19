package com.supporter.prj.eip.code_share.util;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.exception.ExceptionCode;
import com.supporter.prj.eip_service.exception.UnauthorizedAccessException;
import com.supporter.prj.eip_service.module.entity.IModule;
import com.supporter.prj.eip_service.module.entity.IOper;
import com.supporter.prj.eip_service.security.entity.UserProfile;

public class AuthUtils {
	public static void canAccess(UserProfile user, String moduleId, String operInnerName, String entityId, Object entityObj){
		//判断有无权限访问
		IModule module = EIPService.getModuleService().getModule(moduleId);
		IOper oper = EIPService.getModuleService().getOper(operInnerName, module);
		boolean canAccess = EIPService.getAuthorityService().canAccess(user, oper , entityId, entityObj);
		if(!canAccess){
			throw new UnauthorizedAccessException(ExceptionCode.Auth.NOT_ACCESSIBLE);
		}
	}
	
	public static void canExecute(UserProfile user, String moduleId, String operInnerName, String entityId, Object entityObj){
		//判断有无权限访问
		IModule module = EIPService.getModuleService().getModule(moduleId);
		IOper oper = EIPService.getModuleService().getOper(operInnerName, module);
		boolean canAccess = EIPService.getAuthorityService().canAccess(user, oper , entityId, entityObj);
		if(!canAccess){
			throw new UnauthorizedAccessException(ExceptionCode.Auth.NOT_EXECUTABLE);
		}
	}
}
