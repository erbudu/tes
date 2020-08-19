package com.supporter.prj.ppm.prebid.seal_bfd.controller;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.util.OperResultConstant;
import com.supporter.prj.ppm.prebid.seal_bfd.entity.PpmPrebidSeal;
import com.supporter.prj.ppm.prebid.seal_bfd.entity.PpmPrebidSealBfd;
import com.supporter.prj.ppm.prebid.seal_bfd.service.PrebidSealBfdService;
import com.supporter.spring_mvc.AbstractController;


@Controller
@RequestMapping("ppm/prebid/sealbfd")
public class PrebidSealBfdController extends AbstractController {
	private static final long serialVersionUID = 1L;

	@Autowired
	private PrebidSealBfdService prebidSealBfdService;
	/**
	 * 查看页面加载对象
	 * 
	 * @param prbidReportId
	 * @return
	 */
	@RequestMapping("initEditOrViewPageSealbfd")
	public @ResponseBody PpmPrebidSealBfd initEditOrViewPageReport(String sealId,
			String prbidReportId,String prjId,Boolean result) {
		UserProfile user = this.getUserProfile();
		PpmPrebidSealBfd ppmPrebidSealBfd = this.prebidSealBfdService
				.initEditOrViewPagebfd(sealId,prbidReportId, user,prjId , result);
		return ppmPrebidSealBfd;
	}
	
	/**
	 * <pre>保存资料清单</pre>
	 * @param ppmPrebidReportBfd 资料清单业务实体类
	 * @return
	 */
	@RequestMapping("saveSealBfd")
	public @ResponseBody OperResult<PpmPrebidSealBfd> saveBFD(PpmPrebidSealBfd ppmPrebidSealBfd){
		if(ppmPrebidSealBfd == null) {
			return null;
		}
		PpmPrebidSealBfd backEntity = prebidSealBfdService.saveBfd(ppmPrebidSealBfd);
		if(backEntity == null) {
			return OperResult.fail("保存失败", null);
		}
		return OperResult.succeed("保存成功", null,backEntity);
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param ppmPrebidReport
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("updateSeal")
	public @ResponseBody OperResult<PpmPrebidSeal> updateSeal(
			PpmPrebidSeal prebidSeal) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		Map<String, Object> valueMap = this
				.getPropValues(PpmPrebidSeal.class);
		PpmPrebidSeal entity = this.prebidSealBfdService
				.updateSeal(user, prebidSeal, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS,
				"保存成功", entity);
	}

	/**
	 * <pre>通知发送待办</pre>
	 * @param prjId 项目主键
	 * @return 项目信息
	 */
	@RequestMapping("confirmNotice")
	public @ResponseBody PpmPrebidSeal confirmNotice(String sealId,String prjId) {//确认通知
		UserProfile user = this.getUserProfile();
		if (sealId == null || sealId == "") {
			return null;
		}

		PpmPrebidSeal prebidSeal = prebidSealBfdService.confirmNotice(sealId,prjId,user);
		if (prebidSeal == null) {
			return null;
		}
		return prebidSeal;
	}
	/**
	 *  资料清单删除
	 * @param moduleName
	 * @param basiType
	 * @param entityId
	 * @param twolevelid
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("deleteSealBfd")
	public @ResponseBody OperResult delteBFD(String  filesId,String prbidReportId,String bfdTypeId) {
		prebidSealBfdService.deleteBfd(filesId,prbidReportId,bfdTypeId);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}
	
	/**
	 * 根据prjId获取prbidReportId
	 * @param prjId
	 * @return
	 */
	@RequestMapping("getSeal")
	public @ResponseBody PpmPrebidSeal getSeal(String prjId){
		PpmPrebidSeal PpmPrebidSeal = this.prebidSealBfdService.getSeal(prjId);
		return PpmPrebidSeal;
	}
	/**
	 * 检查所选项目是否可以启动出版
	 * @param prjId 项目id
	 * @return 检查结果
	 */
	@RequestMapping("/checkSealIsOrNotPass")
	@ResponseBody
	public boolean checkPrjStatus(String laBusinessTypeId,String prjId) {
		return prebidSealBfdService.checkSealIsOrNotPass(laBusinessTypeId,prjId);
	}
}
