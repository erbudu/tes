package com.supporter.prj.pm;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: 系统公共Controller
 */
@Controller
@RequestMapping("pm/pm_public")
public class PmContractController extends AbstractController {

	private static final long serialVersionUID = 1L;

	/**
	 * 获取项目名称-码表
	 * @return 项目名称map
	 */
	@RequestMapping("/getPrjName")
	@ResponseBody
	public Map<String, String> getPrjName() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems("PROJECT_CODE");
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getExtA());
		}
		return map;
	}

}