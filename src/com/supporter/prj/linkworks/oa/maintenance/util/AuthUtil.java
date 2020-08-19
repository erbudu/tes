package com.supporter.prj.linkworks.oa.maintenance.util;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.exception.UnauthorizedAccessException;
import com.supporter.prj.eip_service.module.entity.IModule;
import com.supporter.prj.eip_service.module.entity.IOper;
import com.supporter.prj.eip_service.security.entity.UserProfile;
public class AuthUtil {
	  public static void canAccess(UserProfile user, String operInnerName, String entityId, Object entityObj)
	  {
	    IModule module = EIPService.getModuleService().getModule("OAMAINTENAN");
	    IOper oper = EIPService.getModuleService().getOper(operInnerName, module);
	    boolean canAccess = EIPService.getAuthorityService().canAccess(user, oper, entityId, entityObj);
	    if (!canAccess) {
	      throw new UnauthorizedAccessException("AUTH-001");
	    }
	  }
	  
	  public static void canExecute(UserProfile user, String operInnerName, String entityId, Object entityObj)
	  {
	    IModule module = EIPService.getModuleService().getModule("OAMAINTENAN");
	    IOper oper = EIPService.getModuleService().getOper(operInnerName, module);
	    boolean canAccess = EIPService.getAuthorityService().canAccess(user, oper, entityId, entityObj);
	    if (!canAccess) {
	      throw new UnauthorizedAccessException("AUTH-002");
	    }
	  }
}
