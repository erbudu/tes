package com.supporter.prj.cneec.pc.pre_prj_manager.develop_agreement.controller;

import com.supporter.prj.cneec.pc.pre_prj_manager.develop_agreement.constant.DevelopAgreementConstant;
import com.supporter.prj.cneec.pc.pre_prj_manager.develop_agreement.entity.DevelopAgreement;
import com.supporter.prj.cneec.pc.pre_prj_manager.develop_agreement.service.DevelopAgreementService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**   
 * @Title: Controller
 * @Description: 开发合作.
 * @author kangh_000
 * @date 2018-12-14 17:09:39
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("pre_prj_manager/develop_agreement/developAgreement")
public class DevelopAgreementController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private DevelopAgreementService developAgreementService;
	
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param developAgreementId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody DevelopAgreement initEditOrViewPage(String developAgreementId){
		UserProfile user = this.getUserProfile();
		DevelopAgreement entity = developAgreementService.initEditOrViewPage(developAgreementId, user);
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, DevelopAgreement developAgreement) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.developAgreementService.getGrid(user, jqGrid, developAgreement);
		return jqGrid;
	}
	
	/**
	 * 保存数据.
	 * 
	 * @param developAgreement 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("save")
	public @ResponseBody OperResult< DevelopAgreement > save(DevelopAgreement developAgreement){
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(DevelopAgreement.class);
		OperResult< DevelopAgreement > op = this.developAgreementService.save(user, developAgreement, valueMap);
		return op;
	}
	
	/**
	 * 更新数据.
	 * 
	 * @param developAgreement 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("update")
	public @ResponseBody OperResult< DevelopAgreement > update(DevelopAgreement developAgreement){
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(DevelopAgreement.class);
		OperResult< DevelopAgreement > op = this.developAgreementService.update(user, developAgreement, valueMap);
		return op;
	}
	
	/**
	 * 删除前判断是否可以删除.
	 * @return
	 */
	@RequestMapping("checkBatchDelCondition")
	public  @ResponseBody List< OperResult< ? > > checkBatchDelCondition(String developAgreementIds){
		return this.developAgreementService.checkBatchDelCondition(this.getUserProfile(), developAgreementIds);
	}
	
	/**
	 * 删除操作.
	 * 
	 * @param developAgreementIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult< ? > batchDel(String developAgreementIds) {
		UserProfile user = this.getUserProfile();
		OperResult< ? > op = this.developAgreementService.delete(user, developAgreementIds);
		return op;
	}
	
	/**
	 * 判断指定字段唯一性
	 * 
	 * @param developAgreementId
	 * @param propertyName 属性名称
	 * @param propertyName 属性值
	 * @return
	 */
	@RequestMapping("checkPropertyUniquenes")
	public @ResponseBody Boolean checkPropertyUniquenes(String developAgreementId, String propertyName, String propertyValue){
		return this.developAgreementService.checkPropertyUniquenes(developAgreementId, propertyName, propertyValue);
	}

	/**
	 * 终止协议
	 *
	 * @param developAgreementId
	 * @return
	 */
	@RequestMapping("stopAgreement")
	public @ResponseBody OperResult< ? > stopAgreement(String developAgreementId){
		return this.developAgreementService.stopAgreement(developAgreementId);
	}

	/**
	 * 获取协议类型下拉数据
	 *
	 * @return
	 */
	@RequestMapping("agreementTypeData")
	public @ResponseBody Map<String, Object> getAgreementTypeData(){
//		return this.developAgreementService.stopAgreement(developAgreementId);
		List<IComCodeTableItem> comCodeTableItemList =
				EIPService.getComCodeTableService().getCodeTableItems(DevelopAgreementConstant.AGREEMENT_TYPE_INNERNAME);

		Map<String, Object> result = new HashMap<String, Object>();

		if (comCodeTableItemList == null) {
			return result;
		}

		for (IComCodeTableItem comCodeTableItem : comCodeTableItemList) {
			result.put(comCodeTableItem.getItemId(), comCodeTableItem.getDisplayName());
		}

		return result;
	}
}
