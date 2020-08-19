package com.supporter.prj.cneec.tpc.entityIdMipping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.cneec.tpc.entityIdMipping.service.EntityIdMippingService;
import com.supporter.prj.cneec.tpc.entityIdMipping.entity.EntityIdMipping;
import com.supporter.prj.eip_service.common.entity.OperResult;

/**
 * @Title: Controller
 * @Description: 贸易主键映射表.
 * @date 2019-07-16 17:04:35
 * @version V1.0
 */
@Controller
@RequestMapping("tpc/entityIdMipping/entityIdMipping")
public class EntityIdMippingController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EntityIdMippingService entityIdMippingService;

	/**
	 * 创建主键映射
	 * @param tpcEntityId
	 * @param moduleName
	 * @return
	 */
	@RequestMapping("createEntityIdMipping")
	public @ResponseBody OperResult<EntityIdMipping> createEntityIdMipping(String tpcEntityId, String moduleName) {
		EntityIdMipping entity = entityIdMippingService.createEntityIdMipping(tpcEntityId, moduleName);
		if (entity != null) {
			return OperResult.succeed("操作成功！", null, entity);
		}else {
			return OperResult.fail("操作失败！", null);
		}
	}

}
