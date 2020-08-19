package com.supporter.prj.cneec.tpc.tariff_vat_payment.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.tariff_vat_payment.entity.TariffVatPayment;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

/**
 * @Title: TariffVatPaymentViewerProvide
 * @Description: 示例性质的IBusiEntityViewerProvider实现类.
 * @author: yanweichao
 * @date: 2017-12-20
 * @version: V1.0
 */
@Service
public class TariffVatPaymentViewerProvider implements IBusiEntityViewerProvider {

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return TariffVatPayment.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/tariff_vat_payment/tariff_vat_payment_view.html?paymentId=" + entityId;
	}

}
