/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.preparation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.ppm.bid_startup.preparation.entity.BFDEntity;
import com.supporter.prj.ppm.bid_startup.preparation.service.BFDService;
import com.supporter.spring_mvc.AbstractController;

/**
 *<p>Title: BFDController</p>
 *<p>Description: 资料清单前端控制器</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月6日
 * 
 */
@Controller
@RequestMapping("ppm/bid_startup/preparation/bfd")
public class BFDController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private BFDService bfdService;
	/**
	 * <pre>标记用印文件</pre>
	 * @param chkValueStr 用印文件主键
	 * @param bidIngUpId 业务单主键
	 * @return
	 */
	@RequestMapping("markUsePrintFile")
	public @ResponseBody OperResult markUsePrintFile(String chkValueStr,String bidIngUpId) {
		if(chkValueStr == null || chkValueStr == "") return OperResult.succeed(null, null, null);
		bfdService.markUsePrintFile(chkValueStr,bidIngUpId);
		return OperResult.succeed("success", null, null);
	}
	
	@RequestMapping("initPrintFile")
	public @ResponseBody List<BFDEntity> initPrintFile(String bidIngUpId){
		if(bidIngUpId == "" || bidIngUpId == null) {
			return null;
		}
		List<BFDEntity> list = bfdService.initPrintFile(bidIngUpId);
		return list;
	}
	
	@RequestMapping("deleteBFD")//String moduleName,String basiType,String entityId,String twolevelid
	public @ResponseBody OperResult delteBFD(String  filesId,String bidIngUpId,String bfdTypeId) {
		bfdService.deleteBFD(filesId,bidIngUpId,bfdTypeId);//moduleName,basiType,entityId,twolevelid
		return null;
	}
	
	/**
	 * <pre>保存资料清单</pre>
	 * @param bfdEntity 资料清单业务实体类
	 * @return
	 */
	@RequestMapping("saveBFD")
	public @ResponseBody OperResult<BFDEntity> saveBFD(BFDEntity bfdEntity){
		if(bfdEntity == null) {
			return null;
		}
		BFDEntity backEntity = bfdService.saveBFD(bfdEntity);
		if(backEntity == null) {
			return OperResult.fail("保存失败", null);
		}
		return OperResult.succeed("保存成功", null,backEntity);
	}

}
