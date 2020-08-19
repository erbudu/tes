package com.supporter.prj.ppm.prj_org.base_info.controller;



import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.module.constant.ModuleConstant;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.prj_org.base_info.constant.BaseInfoConstant;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.entity.PrjAddr;
import com.supporter.prj.ppm.prj_org.base_info.entity.PrjBidInf;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;
import com.supporter.prj.ppm.prj_org.dev_org.entity.PrjDeOrgEmp;
import com.supporter.prj.ppm.prj_org.util.PrjConstant;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;


@Controller
@RequestMapping("ppm/base_info/")
public class BaseInfoController extends AbstractController{
	private static final long serialVersionUID = 1L;

	
	@Autowired
	private BaseInfoService baseInfoService;
	
	/**
	 * <pre>
	 * 		描述：查看当前登录用户拥有的一级菜单的权限
	 * </pre>
	 * @param prjId  项目主键
	 * @return 一级菜单id。码表编号
	 */
	@RequestMapping("oneLevelMenuAuth")
	public @ResponseBody Map<String,String> oneLevelMenuAuth(String prjId){
		UserProfile userProfile = getUserProfile();
		Map<String,String> oneLevelMenuAuth = baseInfoService.oneLevelMenuAuth(userProfile,prjId);
		return oneLevelMenuAuth;
	}
	/**
	 * <pre>
	 * 	 描述： 
	 *      获取状态  项目列表更多先择用
	 * </pre>
	 * 
	 * @return 状态集合
	 */
	@RequestMapping("getStatus")
	public @ResponseBody Map<Integer,String> getStatus(){	
		return BaseInfoConstant.getActiveDesc();
	}
	
	/**
	 * <pre>需出口管制时获取相关通知人员</pre>
	 * @return  通知人员集合
	 */
	@RequestMapping("getNoticePersonY")
	public @ResponseBody List<Map<String,String>> getNoticePersonY(){
		
		List<Map<String,String>> list = baseInfoService.getNoticePersonY();
		return list;
	}
	
	
	/**
	 * <pre>无需出口管制时获取相关通知人员</pre>
	 * @return  通知人员集合
	 */
	@RequestMapping("getNoticePersonN")
	public @ResponseBody List<Map<String,String>> getNoticePersonN(){
		
		List<Map<String,String>> list = baseInfoService.getNoticePersonN();
		return list;
	}
	
	/**
	 * <pre>通知发送待办</pre>
	 * @param prjId 项目主键
	 * @return 项目信息
	 */
	@RequestMapping("confirmNotice")
	public @ResponseBody Prj confirmNotice(String prjId) {//确认通知
		if(prjId == null || prjId ==  "") {return null;}
		
		UserProfile user = this.getUserProfile();
		Prj prj = baseInfoService.confirmNotice(prjId,user);
		if(prj == null) {return null;}
		return prj;
	}
	
	/**
	 * <pre>管制确认后台判断方法</pre>
	 * <p>管制确认页面加载的时候执行了此方法</p>
	 * @param prjId Project UUID
	 * @return Project information
	 */
	@RequestMapping("isEccConfirm")
	public @ResponseBody Prj isEccConfirm(String prjId) {
		System.out.println(prjId);
		if(prjId == null || prjId == "") {return null;}
		Prj prjInfo = baseInfoService.isEccConfirm(prjId);
		return prjInfo;
		
	}
	
	/**
	 * 判断制裁等级，是否是全面制裁
	 * @param countryId 国家地区ID
	 * @return boolean   true：是全面制裁  false：不是全面制裁 有限制裁
	 */
	@RequestMapping("checkEccLevel")
	public @ResponseBody boolean checkEccLevel(String countryId) {
		
		return baseInfoService.checkEccLevel(countryId);
	}
	
	/**
	 <pre>【提交预览页面】【提交按钮执行方法】</pre>
	 <pre>状态：0草稿1提交未通知2提交已通知</pre>
	 */
	@RequestMapping("effect")
	public @ResponseBody Prj effect(String prjId){
		
		UserProfile user = this.getUserProfile();
		Prj prj = baseInfoService.effect(prjId,user);
		return prj;
	}
	
	/**
	 * <pre>获取开发模式码表数据</pre>
	 * @return 开发模式码表数据
	 */
	@RequestMapping("selectDevelopmentMode")
	public @ResponseBody Map<String,String> selectDevelopmentMode(){
		return PrjConstant.selectDevelopmentMode();
		
	} 
	
	/**
	 * <pre>
	 * 【开拓项目初始登录】-【基本信息】工程地址，项目所在州下拉选码表数据
	 * </pre>
	 * @return 城市码表数据
	 */
	@RequestMapping("selectCity")
	public @ResponseBody Map<String,String> selectCity(){
		String itmeId = CommonUtil.trim(this.getRequestPara("value"));
		return BaseInfoConstant.getCityMap(itmeId);
		
	}
	
	/**
	 * <pre>填充项目信息查看页面</pre>
	 * @param prjId 项目id
	 * @return entity
	 * @author YUEYUN
	 */
	@RequestMapping("initBaseInfoView")
	public @ResponseBody Prj initBaseInfoView(String prjId) {
		if(prjId == null || prjId == "") {
			return null;
		}
		Prj prjEntity = baseInfoService.initBaseInfoView(prjId);
		return prjEntity;
	}
	
	
	
	/**
	 * initEditOrViewEmpPage
	 */
	@RequestMapping("initEditOrViewEmpPage")
	public @ResponseBody PrjDeOrgEmp initEditOrViewEmpPage(String prjId) {
		UserProfile user = this.getUserProfile();
		PrjDeOrgEmp entity = baseInfoService.initEditOrViewEmpPage(prjId,user);
		return entity;
	}
	
	/**
	 * initPageAddress
	 */
	//@RequestMapping("initPageAddress")
//	public @ResponseBody PrjAddr initPageAddress(String addrId,String prjId) {
//		UserProfile user = this.getUserProfile();
//		PrjAddr entity = baseInfoService.initPageAddress(addrId,prjId,user);
//		return entity;
//	}
	
	/**
	 * initBidInfPage
	 */
	@RequestMapping("initBidInfPage")
	public @ResponseBody PrjBidInf initBidInfPage(String prjId) {
		UserProfile user = this.getUserProfile();
		PrjBidInf entity = baseInfoService.initBidInfPage(prjId,user);
		return entity;
	}
	
	/**
	 * 项目列表
	 * getPrjGrid
	 */
	
	@RequestMapping("getPrjGrid")
	public @ResponseBody
	JqGrid getPrjGrid(HttpServletRequest request, JqGridReq jqGridReq,
			Prj prj) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.baseInfoService.getPrjGrid(user, jqGrid, prj);
		return jqGrid;
	}
	
	/**
	 * 项目工程地址列表
	 * getAddressGrid
	 */
	@RequestMapping("getAddressGrid")
	public @ResponseBody
	JqGrid getAddressGrid(HttpServletRequest request, JqGridReq jqGridReq,String prjId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.baseInfoService.getAddressGrid(user, jqGrid, prjId);
		return jqGrid;
	}
	
	/**
	 * 项目资金来源列表
	 * getSofGrid
	 */
	
	@RequestMapping("getSofGrid")
	public @ResponseBody
	JqGrid getSofGrid(HttpServletRequest request, JqGridReq jqGridReq,String prjId) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.baseInfoService.getSofGrid(user, jqGrid, prjId);
		return jqGrid;
	}
	
	
	/*  ||：  此方法 弃用			↓↓↓↓↓↓↓↓↓↓↓↓
	
	@RequestMapping("detectionMethod")
	public @ResponseBody boolean detectionMethod(String prjCName,String prjEName) {//boolean
		return this.baseInfoService.detectionMethod(prjCName,prjEName);
	}
	
	*/
	
	/**
	 *  <pre>项目名称相似度比较</pre>
	 *  <pre>说明：前端用结果集判断，大于0则说明重复，小于0则说明不重复</pre>
	 * @param prjCName Project Chinese name
	 * @param prjEName Project English name
	 * @return Project collection 
	 * @author YUEYUN
	 */
	@RequestMapping("detectionMethod")
	public @ResponseBody Set<Prj> detectionMethod(String prjCName,String prjEName){
		
		Set<Prj> resultCollection = this.baseInfoService.detectionMethod(prjCName, prjEName);
		return resultCollection;
		
	}
	
	/**
	 * <pre>
	 * 		Description:This method is save or update projection entity information.(controller)
	 * </pre>
	 */
	@RequestMapping("confirmSaveOrUpdate")
	public @ResponseBody OperResult saveOrUpdate(Prj prj) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(Prj.class);
		Prj entity = this.baseInfoService.confirmSaveOrUpdate(user, prj);
		return OperResult.succeed("保存成功",null,entity);
	}
	
	/**
	 * 	项目信息保存-节点3
	 * prjSaveOrUpdate
	 */
	@RequestMapping("prjSaveOrUpdate")
	public @ResponseBody OperResult prjSaveOrUpdate(Prj prj) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(Prj.class);
		Prj entity = this.baseInfoService.prjSaveOrUpdate(user, prj);
		return OperResult.succeed("保存成功",null,entity);
	}
	
	
	/**
	 * saveOrUpdateAddress
	 */
	@RequestMapping("saveOrUpdateAddress")
	public @ResponseBody OperResult saveOrUpdateAddress(PrjAddr prjAddr) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(PrjAddr.class);
		PrjAddr entity = this.baseInfoService.saveOrUpdateAddress(user, prjAddr);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS,"",entity);
	}
	
	/**
	 * saveOrUpdateBidInf
	 */
	@RequestMapping("saveOrUpdateBidInf")
	public @ResponseBody OperResult saveOrUpdateBidInf(PrjBidInf prjBidInf) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(PrjBidInf.class);
		PrjBidInf entity = this.baseInfoService.saveOrUpdateBidInf(user, prjBidInf);
		return OperResult.succeed("保存成功",null,entity);
	}
	
	/**
	 * 启动开发暂存
	 * startStaging
	 */
	@RequestMapping("startStaging")
	public @ResponseBody OperResult startStaging(String prjId,Integer prjDewStatus,String createdBy,String createdById) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(Prj.class);
		Prj entity = this.baseInfoService.startStaging(user,prjId,prjDewStatus,createdBy,createdById);
		return OperResult.succeed("保存成功",null,entity);
	}
	
	/**
	 * 启动开发确认
	 * startConfirm
	 */
	
	@RequestMapping("startConfirm")
	public @ResponseBody OperResult startConfirm(String prjId,Integer prjDewStatus,String empId,String empName) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(Prj.class);
		Prj entity = this.baseInfoService.startStaging(user,prjId,prjDewStatus,empId,empName);
		return OperResult.succeed("保存成功",null,entity);
	}
	
	/**
	 * 出口管制确认 保存
	 * confirmContinue
	 */
	@RequestMapping("confirmContinue")
	public @ResponseBody OperResult confirmContinue(String prjId,boolean isEccConfirm) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(Prj.class);
		Prj entity = this.baseInfoService.confirmContinue(user,prjId,isEccConfirm);
		return OperResult.succeed("保存成功",null,entity);
	}
	
	
//	/**
//	 * 项目编号生成规则
//	 */
//	@RequestMapping("createdPrjNo")
//	public @ResponseBody String createdPrjNo(String deptId) {
//		return this.baseInfoService.createdPrjNo(deptId);
//	}
	
	
	/**
	 * <pre>
	 * 【开拓项目初始登录】-【基本信息】工程地址，项目性质下拉选码表数据
	 * </pre>
	 * @author YUEYUN
	 * @return 项目性质码表数据
	 */
	@RequestMapping("selectPrjNature")
	public @ResponseBody Map<String, String> selectPrjNature() {
		return PrjConstant.selectPrjNature();
	}
	
	/**
	 * <pre>
	 * 【开拓项目初始登录】-【基本信息】工程地址，项目领域下拉选码表数据
	 * </pre>
	 * @author YUEYUN
	 * @return 项目领域码表信息
	 */
	@RequestMapping("selectPrjField")
	public @ResponseBody Map<String, String> selectPrjField() {
		return PrjConstant.selectPrjField();
	}
	
	/**
	 * <pre>
	 * 保证金形式
	 * </pre>
	 * @author YUEYUN
	 * @return 项目领域码表信息
	 */
	@RequestMapping("selectMarginWay")
	public @ResponseBody Map<String, String> selectMarginWay() {
		return PrjConstant.selectMarginWay();
	}
	
	/**
	 * <pre>资金来源类型</pre>
	 * @return 码表数据
	 */
	@RequestMapping("selectMoneySourceType")
	public @ResponseBody Map<String, String> selectMoneySourceType() {
		return PrjConstant.selectMoneySourceType();
	}
	
	/**
	 * <pre>承揽方式下下拉选码表数据</pre>
	 * @return 
	 */
	@RequestMapping("selectPrjPrjMod")
	public @ResponseBody Map<String, String> selectPrjPrjMod() {
		return PrjConstant.selectPrjPrjMod();
	}
	
	@RequestMapping("selectPrjDewStatus")
	public @ResponseBody Map<Integer, String> selectPrjDewStatus() {
		return PrjConstant.selectPrjDewStatus();
	}
	
	/**
	 * 融资要求
	 * selectPfType
	 */
	@RequestMapping("selectPfType")
	public @ResponseBody Map<String, String> selectPfType() {
		return PrjConstant.selectPfType();
	}
	
	/**
	 * 融资担保
	 * selectPfgMod
	 */
	@RequestMapping("selectPfgMod")
	public @ResponseBody Map<String, String> selectPfgMod() {
		return PrjConstant.selectPfgMod();
	}
	
	@RequestMapping("selectPrjSettlementWay")
	public @ResponseBody Map<String,String> selectPrjSettlementWay(){
		return PrjConstant.selectPrjSettlementWay();
	}
	
	/**
	 * 投/议标方式
	 * selectBidWay
	 */
	@RequestMapping("selectBidWay")
	public @ResponseBody Map<String, String> selectBidWay() {
		return PrjConstant.selectBidWay();
	}
	
	
	/**
	 * <pre>
	 * 【开拓项目初始登录】-【基本信息】工程地址，项目所在国下拉选码表数据
	 * </pre>
	 * @author YUEYUN
	 * @return 国家码表数据
	 */
	@RequestMapping("selectCountry")
	public @ResponseBody Map<String,String> selectCountry(){
		return BaseInfoConstant.getCountryMap();
		
	}
	
	/**
	 * <pre>
	 * 		Describe : This method is used initialize page data.
	 * </pre>
	 * @param prjId Project primary key
	 * @return Project entity
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody Prj initEditOrViewPage(String prjId) {
		UserProfile user = this.getUserProfile();
		Prj entity = baseInfoService.initEditOrViewPage(prjId,user);
		return entity;
	}
	
}
