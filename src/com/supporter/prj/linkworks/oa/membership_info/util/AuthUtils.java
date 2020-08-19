package com.supporter.prj.linkworks.oa.membership_info.util;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.authority.service.IAuthorityService;
import com.supporter.prj.eip_service.exception.UnauthorizedAccessException;
import com.supporter.prj.eip_service.module.entity.IModule;
import com.supporter.prj.eip_service.module.entity.IOper;
import com.supporter.prj.eip_service.module.service.IModuleService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.membership_info.constants.MembershipConstant;

public class AuthUtils
{
  public static void canAccess(UserProfile user, String operInnerName, String entityId, Object entityObj)
  {
    IModule module = EIPService.getModuleService().getModule(MembershipConstant.MODULE_ID);
    IOper oper = EIPService.getModuleService().getOper(operInnerName, module);
    boolean canAccess = EIPService.getAuthorityService().canAccess(user, oper, entityId, entityObj);
    if (!canAccess) {
      throw new UnauthorizedAccessException("AUTH-001");
    }
  }
  
  public static void canExecute(UserProfile user, String operInnerName, String entityId, Object entityObj)
  {
    IModule module = EIPService.getModuleService().getModule(MembershipConstant.MODULE_ID);
    IOper oper = EIPService.getModuleService().getOper(operInnerName, module);
    boolean canAccess = EIPService.getAuthorityService().canAccess(user, oper, entityId, entityObj);
    if (!canAccess) {
      throw new UnauthorizedAccessException("AUTH-002");
    }
  }
}
