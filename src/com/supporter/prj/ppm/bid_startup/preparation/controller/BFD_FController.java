/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.preparation.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.ppm.bid_startup.preparation.service.BFD_FService;
import com.supporter.spring_mvc.AbstractController;

/**
 *<p>Title: BFD_FController</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月16日
 * 
 */
@Controller
@RequestMapping("ppm/bid_startup/preparation/bfd_f/")
public class BFD_FController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private BFD_FService bfd_FService;
	
	
	//set盖章文件id
	@RequestMapping("updateFuFileInfo")
	public @ResponseBody String  updateFuFileInfo(String promaryKey,String fuSealFileId,String fuSealFileName) {
		System.out.println(promaryKey);
		String result = bfd_FService.updateFuFileInfo(promaryKey,fuSealFileId,fuSealFileName);
		return result;
	}
	
	@RequestMapping("getUseSealGrid")
	public @ResponseBody List<Map<String,String>> getUseSealGrid(String businessUUID){
		List<Map<String,String>> useSealFileGrid = bfd_FService.getUseSealGrid(businessUUID);
		return useSealFileGrid;
	}

}
