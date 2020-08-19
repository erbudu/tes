package com.supporter.prj.ppm.poa.bor.controller;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.log.controller.AbstractController;
import com.supporter.prj.ppm.poa.bor.dao.BidCurrencyDao;
import com.supporter.prj.ppm.poa.bor.dao.BidInfDao;
import com.supporter.prj.ppm.poa.bor.dao.BidOpenResultDao;
import com.supporter.prj.ppm.poa.bor.dao.BidPriceDao;
import com.supporter.prj.ppm.poa.bor.dao.NotifyDao;
import com.supporter.prj.ppm.poa.bor.entity.BidCurrency;
import com.supporter.prj.ppm.poa.bor.entity.BidInf;
import com.supporter.prj.ppm.poa.bor.entity.BidNotify;
import com.supporter.prj.ppm.poa.bor.entity.BidOpenResult;
import com.supporter.prj.ppm.poa.bor.entity.BidPrice;
import com.supporter.prj.ppm.poa.bor.entity.Notify;
import com.supporter.prj.ppm.poa.bor.service.BidCurrencyService;
import com.supporter.prj.ppm.poa.bor.service.BidOpenResultService;

@Controller
@RequestMapping("/ppm/poa/bor")
public class BidOpenResultController extends AbstractController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private BidOpenResultService bidOpenResultService;
	@Autowired
	private BidOpenResultDao bidOpenResultDao;
	@Autowired
	private BidInfDao bidInfDao;
	@Autowired
	private BidPriceDao bidPriceDao;
	
	
	@Autowired
	private BidCurrencyDao bidCurrencyDao;
	@Autowired
	private BidCurrencyService bidCurrencyService;
	@Autowired
	private NotifyDao notifyDao;
	
	
	/*
	 * 加载列表信息
	 * 
	 * */
	@RequestMapping("/getGridCurrency")
	@ResponseBody
	public JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, String  borId) throws Exception {
		UserProfile user = getUserProfile();
    JqGrid jqGrid = jqGridReq.initJqGrid(request);
    List list = this.bidOpenResultService.getGridCurrency(user, jqGrid, borId);
    
    jqGrid.setRows(list);
    jqGrid.setRowCount(list.size());
    return jqGrid;
  }
	@RequestMapping("/getBor")
	@ResponseBody
	public BidOpenResult getBor(HttpServletRequest request, String  borId) throws Exception {
		UserProfile user = getUserProfile();
		BidOpenResult bidOpenResult = this.bidOpenResultService.getBor(user, borId);
    
    
    return bidOpenResult;
  }
	/**
	 * 点击查看按钮时查询所选择列的信息并返回
	 * 
	 * */
	@RequestMapping("/getGridInf")
	@ResponseBody
	public JqGrid getGridInf(HttpServletRequest request, JqGridReq jqGridReq, String  borId) throws Exception {
		UserProfile user = getUserProfile();
    JqGrid jqGrid = jqGridReq.initJqGrid(request);
    List list = this.bidOpenResultService.getGridInf(user, jqGrid, borId);
    if(list!=null) {
    jqGrid.setRows(list);
    jqGrid.setRowCount(list.size());}
    else {
    	jqGrid.setRows(null);
        jqGrid.setRowCount(0);
    }
    return jqGrid;
  }
	@RequestMapping("/initEditOrViewPageNotify")
	@ResponseBody
	public BidNotify initEditOrViewPageNotify(HttpServletRequest request, String prjId) throws Exception {
		UserProfile user = getUserProfile();
    BidNotify bidNotify = this.bidOpenResultService.initEditOrViewPageNotify(user,prjId);
   if(bidNotify!=null&&bidNotify.getRecordId()!="F") {
	   Notify notify=notifyDao.getByBidNotifyId(bidNotify.getRecordId());
	   if(!notify.getNotifyId().equals(user.getPersonId())) {
		   BidNotify bidNotify2=new BidNotify();
		   bidNotify2.setRecordId("noSelf");
	   }
	   bidNotify.setStatusDesc("是");
	   bidNotify.setNotifyPerson(notify.getNotifyName());
	   return bidNotify;
   }
   
   	return bidNotify;
  
	}
	@RequestMapping("/initEditOrViewCurrency")
	@ResponseBody
	public BidOpenResult initEditOrViewCurrency(HttpServletRequest request, String prjId) throws Exception {
		UserProfile user = getUserProfile();
		BidOpenResult bidOpenResult = this.bidOpenResultService.initEditOrViewCurrency(user,prjId);
  if(bidOpenResult!=null&&bidOpenResult.getBorId()!="F") {
	  //bidOpenResult.setStatusDesc("是");
	   return bidOpenResult;
  }
  
  	return bidOpenResult;
  
   
    
  }
	
	
	/**
	 * 新增记录币别记录
	 * */
	@RequestMapping("/saveOrUpdate")
	public @ResponseBody
	OperResult<BidOpenResult> saveOrUpdate (HttpServletRequest request , HttpServletResponse response,BidOpenResult bidOpenResult , BidCurrency bidCurrency) throws ParseException {
		UserProfile user = this.getUserProfile();
		BidOpenResult entity = this.bidOpenResultService.saveOrUpdate(bidOpenResult,bidCurrency, user,request,response);
		if(entity.getBorId()=="") {
			 return OperResult.fail("请输入不同币别",null);
		}
		else if(entity.getBorId()=="F") {
			 return OperResult.fail("请输入币别信息", null);
		}
		else if(entity.getBorId()=="Null") {
			 return OperResult.fail("主键是空", null);
		}
		else {
	    return OperResult.succeed("saveSuccess", null, entity);
		}
		
	}
	/*点击下一步进行保存同时修稿状态*/
	@RequestMapping("/saveOrUpdates")
	public @ResponseBody
	OperResult<BidOpenResult> saveOrUpdates (HttpServletRequest request , HttpServletResponse response,BidOpenResult bidOpenResult , BidCurrency bidCurrency) throws ParseException {
		UserProfile user = this.getUserProfile();
		BidOpenResult entity = this.bidOpenResultService.saveOrUpdates(bidOpenResult,bidCurrency, user,request,response);
		if(entity.getBorId()=="") {
			 return OperResult.fail("请输入不同币别",null);
		}
		else if(entity.getBorId()=="F") {
			 return OperResult.fail("输入相关数据",null);
		}
		else {
	    return OperResult.succeed("saveSuccess", null, entity);
		}
	    
		
		
	}
	/**
	 * 新增开标结果详细记录
	 * */
	@RequestMapping("/saveOrUpdateBidInfo")
	public @ResponseBody
	OperResult<BidOpenResult> saveOrUpdateBidInfo (HttpServletRequest request , HttpServletResponse response,BidOpenResult bidOpenResult , BidCurrency bidCurrency,BidInf bidInf) throws ParseException {
		UserProfile user = this.getUserProfile();
		//System.out.println(bidOpenResult.getBidInfoList());
		if(bidOpenResult.getBidInfoList()==null) {
			return OperResult.fail("","请输入竞价及排名标的详细信息" );
		}else {
		BidOpenResult entity = this.bidOpenResultService.saveOrUpdateBidInfo(bidOpenResult,bidCurrency,bidInf, user,request,response);
		
		if(entity.getBorId()=="isnull") {
			 return OperResult.fail("请输入投标金额",null);
		}
		else if(entity.getBorId()=="isNotNum") {
			 return OperResult.fail("","币别金额输入了非数字或者折合美元计算为0");
		}
		else {
			
	    return OperResult.succeed("saveSuccess", null, entity);
		}
		}
		
	}
	@RequestMapping("/saveOrUpdateBidInfos")
	public @ResponseBody
	OperResult<BidOpenResult> saveOrUpdateBidInfoAndStatus (HttpServletRequest request , HttpServletResponse response,BidOpenResult bidOpenResult , BidCurrency bidCurrency,BidInf bidInf) throws ParseException {
		UserProfile user = this.getUserProfile();
		if(bidOpenResult.getBidInfoList()==null) {
			return OperResult.fail("","请输入竞价及排名标的详细信息");
		}else {
		BidOpenResult entity = this.bidOpenResultService.saveOrUpdateBidInfos(bidOpenResult,bidCurrency,bidInf, user,request,response);
	   // return OperResult.succeed("saveSuccess", null, entity);
	    if(entity.getBorId()=="isnull") {
			 return OperResult.fail("请输入投标金额",null);
		}
		else if(entity.getBorId()=="isNotNum") {
			 return OperResult.fail("","币别金额输入了非数字或者折合美元计算为0");
		}
		else {
			
	    return OperResult.succeed("", null, entity);
		}}
		
		
	}
	/**
	 * 点击确认按钮时，状态变为生效
	 * **/
	/*@RequestMapping("/changeStatus")
	public @ResponseBody
	OperResult<BidOpenResult> changeStatus (BidOpenResult bidOpenResult) throws ParseException {
		UserProfile user = this.getUserProfile();
		BidOpenResult entity = this.bidOpenResultService.changeStatus(bidOpenResult, user);
	    return OperResult.succeed("saveSuccess", null, entity);
		
		
	}*/
	/**
	 * 删除所选中的列
	 * */
	@RequestMapping("/batchDel")
	public @ResponseBody OperResult<BidCurrency> batchDel(String bidCurrencyId) {
		UserProfile user = this.getUserProfile();
		if(bidCurrencyId!=null) {
		this.bidCurrencyService.delete(user, bidCurrencyId);
		return OperResult.succeed("deleteSuccess", null, null);}
		else {
			return OperResult.fail("deleteFail", null, null);
		}

	}
	@RequestMapping("/batchDelBidInf")
	public @ResponseBody OperResult<BidInf> batchDelBidInf(String borId) {
		UserProfile user = this.getUserProfile();
		if(borId!=null) {
			//删除开标结果信息表相关信息
			deleteInf( borId);
			//删除开标结果开标金额表相关信息
			deletePrice(borId);
		return OperResult.succeed("deleteSuccess", null, null);}
		else {
			return OperResult.fail("deleteFail", null, null);
		}

	}
	public void deleteInf(String borId) {
		List<BidInf> listBidInf=this.bidInfDao.getByBorId(borId);
		for(int i=0 ;i<listBidInf.size();i++) {
			this.bidInfDao.delete(listBidInf.get(i).getBidInfId());
		}
	}
	public void deletePrice(String borId) {
		List<BidPrice> listBidPrice=this.bidPriceDao.getByBorId(borId);
		for(int i=0 ;i<listBidPrice.size();i++) {
			this.bidPriceDao.delete(listBidPrice.get(i).getRecordId());
		}
	}
	/**
	币别码表
	*/
	@RequestMapping("/getCurrency")
	public @ResponseBody Map<String, String> getCountryCodetable() {
		
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems("Currency");
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;

	}
	/**
	 * 投标有效性码表*/
	@RequestMapping("/getBidValidate")
	public @ResponseBody Map<String, String> getBidValidateCodetable() {
		
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems("BidValidate");
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;

	}
	@RequestMapping("/confirmNotice")
	public @ResponseBody BidOpenResult confirmNotice(String borId,String prjId) {//确认通知
		if(borId == null || borId ==  "") {return null;}
		
		BidOpenResult bidOpenResult = bidOpenResultService.confirmNotice(borId,prjId);
		if(bidOpenResult == null) {return null;}
		return bidOpenResult;
	}
	@RequestMapping("getNoticePerson")
	public @ResponseBody List<Map<String,String>> getNoticePerson(){
		
		List<Map<String,String>> list = bidOpenResultService.getNoticePerson();
		return list;
	}
	
}
