package com.supporter.prj.ppm.getInform.controller;


import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.supporter.prj.ppm.getInform.service.GetInformService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("ppm/getInform/")
public class GetInformController extends AbstractController {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	  private GetInformService getInformService ;
	
	/**
	 * 根据proc_id获取被知会的人的信息
	 * @param proc_id  流程id
	 * @return
	 */
	@RequestMapping(value = "searchByProcId")
	@ResponseBody
	public  Map<Integer, List<Map<String,String>>> searchByProcId(String procId) {
		return getInformService.searchByProcId(procId);
	}

}
