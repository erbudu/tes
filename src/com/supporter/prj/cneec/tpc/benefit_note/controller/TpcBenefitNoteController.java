package com.supporter.prj.cneec.tpc.benefit_note.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.supporter.prj.cneec.tpc.benefit_note.entity.BenefitNote;
import com.supporter.prj.cneec.tpc.benefit_note.service.BenefitNoteService;
import com.supporter.prj.cneec.tpc.control.util.JsonParseUtils;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.HttpUtil;

/**
 * @Title: TpcBenefitNoteController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2018-06-11
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/benefitNote/build")
public class TpcBenefitNoteController extends AbstractController {

	private static final long serialVersionUID = 1L;

	private static final String PARA_PAGE = "page";
	private static final String PARA_LIMIT = "rows";

	@Autowired
	private BenefitNoteService benefitNoteService;

	@RequestMapping("/getJsonData")
	public void getJsonData(JqGridReq jqGridReq, HttpServletResponse response) {
		UserProfile userProfile = getUserProfile();
		try {
			ListPage<BenefitNote> listPage = this.benefitNoteService.getListPage(
					getRequestParaInt(PARA_PAGE, 0), 
					getRequestParaInt(PARA_LIMIT, 20), 
					getRequestParameters(), 
					userProfile);
			String json = JsonParseUtils.getJsonFromListPage(listPage);
			HttpUtil.write(response, json);
		} catch (Exception e) {
			System.out.println(TpcBenefitNoteController.class.getName() + ".getJsonData() Error:" + e.getMessage());
		}
	}

}