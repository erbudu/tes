package com.supporter.prj.linkworks.oa.membership_info.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.membership_info.constants.AuthConstant;
import com.supporter.prj.linkworks.oa.membership_info.entity.MembershipInfo;
import com.supporter.prj.linkworks.oa.membership_info.service.MembershipInfoService;
import com.supporter.prj.linkworks.oa.membership_info.util.AuthUtils;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Controller
 * @Description: 报告.
 * @author liyinfeng
 * @date 2016-12-06 15:25:34
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("oa/membership_info")
public class MembershipInfoController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private MembershipInfoService membershipInfoService;
	//以下为列表中方法
    private String is_PrevPageURL = ""; 

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, MembershipInfo entity) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.membershipInfoService.getGrid(user, jqGrid, entity);
		return jqGrid;
	}
    
    public String getPrevPageURL(){
        if (is_PrevPageURL.length() > 0) return is_PrevPageURL;
        
        String ls_PrevPageURL = CommonUtil.trim(this.getRequestPara("prevPageURL"));
        if (ls_PrevPageURL.length() == 0)
           ls_PrevPageURL = CommonUtil.trim(this.getRequestPara("CURRENT_PAGE_URL"));
        
        is_PrevPageURL = ls_PrevPageURL;
        return is_PrevPageURL;
    }
	
	//以上为列表中方法
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param MembershipInfoId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody MembershipInfo initEditOrViewPage(String membershipInfoId) {
		MembershipInfo entity = membershipInfoService.initEditOrViewPage(membershipInfoId,this.getUserProfile());
		return entity;
	}
	@RequestMapping("changeMembershipType")
	public @ResponseBody OperResult<MembershipInfo> changeMembershipType(String membershipInfoId,String type) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(MembershipInfo.class);
		MembershipInfo re = this.membershipInfoService.changeMembershipType(user, membershipInfoId,type);
		return OperResult.succeed("saveSuccess", null, re);
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param MembershipInfo 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<MembershipInfo> saveOrUpdate(MembershipInfo entity) {
		UserProfile user = this.getUserProfile();
		AuthUtils.canExecute(user, AuthConstant.AUTH_OPER_NAME_SETVALMEMBERSHIP, entity.getRecId(), entity);
		Map< String, Object > valueMap = this.getPropValues(MembershipInfo.class);
		MembershipInfo re = this.membershipInfoService.saveOrUpdate(user, entity, valueMap);
		return OperResult.succeed("saveSuccess", null, re);
	}

	/**
	 * 删除操作
	 * 
	 * @param membershipInfoIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String membershipInfoIds) {
		UserProfile user = this.getUserProfile();
		this.membershipInfoService.delete(user, membershipInfoIds);
		return OperResult.succeed("deleteSuccess");
	}
	/**
	 * 判断名字是否重复
	 * 
	 * @param reportId
	 * @param reportName
	 * @return
	 */
	@RequestMapping("checkNameIsValid")
	public @ResponseBody Map checkNameIsValid(String membershipInfoId, String empNos) {
		return this.membershipInfoService.checkNameIsValid(membershipInfoId, empNos);
	}
	
	
}
