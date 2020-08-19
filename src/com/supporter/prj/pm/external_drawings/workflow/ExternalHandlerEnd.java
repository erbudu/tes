package com.supporter.prj.pm.external_drawings.workflow;

import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;


public class ExternalHandlerEnd extends AbstractExecHandler {
	
	@Override
	public String getDesc() {
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
//		ExternalDrawingsService externalDrawingsService = SpringContextHolder.getBean(ExternalDrawingsService.class);
//		String externalId = (String) execContext.getProcVar("externalId");
//		ExternalDrawings entity =  externalDrawingsService.getExternalDrawingsById(externalId);
//		entity.setStatus(ExternalDrawings.StatusCodeTable.EFFECT);
//		externalDrawingsService.update(entity);
//		//审批完成后改变图纸状态
//		externalDrawingsService.updateDrawingStatus(externalId, DrawingLibraryVersion.COMPLETE_OUT);
//		//进行数据报盘
//		IOfferRec rec = externalOfferService.initOfferRec(externalDrawings.getExternalId());
//		//没有http请求时可能为空
//		UserProfile user =  externalController.userProfile;
//		//新增一个报盘记录
//		offerService.addOffer(user, rec);
		return null;
	}

}
