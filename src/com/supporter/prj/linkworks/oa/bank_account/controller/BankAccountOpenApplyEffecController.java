package com.supporter.prj.linkworks.oa.bank_account.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApplyEffec;
import com.supporter.prj.linkworks.oa.bank_account.service.BankAccountOpenApplyEffecService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("oa/bankAccountOpenApplyEffec")
public class BankAccountOpenApplyEffecController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	private BankAccountOpenApplyEffecService bankAccountOpenApplyEffecService; 

    /**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, BankAccountOpenApplyEffec bankAccountOpenApplyEffec) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.bankAccountOpenApplyEffecService.getGrid(user, jqGrid, bankAccountOpenApplyEffec);		
		return jqGrid;
	}
    

	/**
	 * 根据主键获取功能模块
	 * 
	 * @param effectiveId 主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody BankAccountOpenApplyEffec get(String effectiveId) {
		BankAccountOpenApplyEffec bankAccountOpenApplyEffec = bankAccountOpenApplyEffecService.get(effectiveId);
		return bankAccountOpenApplyEffec;

	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param effectiveId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody BankAccountOpenApplyEffec initEditOrViewPage(HttpServletRequest request,JqGridReq jqGridReq,String effectiveId) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		BankAccountOpenApplyEffec entity = bankAccountOpenApplyEffecService.initEditOrViewPage(jqGrid,effectiveId,this.getUserProfile());
		return entity;
	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息.
	 * 
	 * @param effectiveId 主键
	 * @return
	 */
	@RequestMapping("viewPage")
	@ResponseBody
	public BankAccountOpenApplyEffec viewPage(String effectiveId) {
		BankAccountOpenApplyEffec entity = bankAccountOpenApplyEffecService.viewPage(effectiveId,this.getUserProfile());
		return entity;
	}
	
	
	
	
	
	
	
	/**
	 * 保存或更新数据
	 * 
	 * @param bankAccountOpenApplyEffec 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<BankAccountOpenApplyEffec> saveOrUpdate(BankAccountOpenApplyEffec bankAccountOpenApplyEffec,String notifierIds,String notifierNames,String examIds,String examNames) {
		UserProfile user = this.getUserProfile();
		Map< String, Object > valueMap = this.getPropValues(BankAccountOpenApplyEffec.class);		
		BankAccountOpenApplyEffec entity = this.bankAccountOpenApplyEffecService.saveOrUpdate(user,bankAccountOpenApplyEffec,valueMap);
//		return OperResult.succeed(SalaryConstant.I18nKey.SAVE_SUCCESS, null, null);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	
	/**
	 * 保存提交.
	 * 
	 * @param apply
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<BankAccountOpenApplyEffec> commit(BankAccountOpenApplyEffec bankAccountOpenApplyEffec) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this
				.getPropValues(BankAccountOpenApplyEffec.class);
		BankAccountOpenApplyEffec entity = this.bankAccountOpenApplyEffecService.commit(user,
				bankAccountOpenApplyEffec, valueMap);
//		return OperResult.succeed(SalaryConstant.I18nKey.SAVE_SUCCESS, null, null);
		return OperResult.succeed("saveSuccess", null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param effectiveIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String effectiveIds) {
		UserProfile user = this.getUserProfile();
		this.bankAccountOpenApplyEffecService.delete(user, effectiveIds);
//		return OperResult.succeed(SalaryConstant.I18nKey.DELETE_SUCCESS, null, null);
		return OperResult.succeed("deleteSuccess", null, null);
	}
	/**
	 * 获取字典数据-用于列表页面状态下拉
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/selectStatusOfListData")
	public Map<Integer, String> selectStatusOfListData()
			throws IOException {
		return BankAccountOpenApplyEffec.getStatusCodeTable();
	}
	
	
	/**
	 * 获取字典数据-用于列表页面币别下拉
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCurrencyMap")
	public Map<String, String> selectCurrencyMap()
			throws IOException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 取码表项管理中“币别”
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems("CURRENCY_CATEGORY");
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				// 币别编码+币别名称
				map.put("", "--请选择--");
				map.put(item.getDisplayName(), item.getDisplayName());
			}
		}
		return map;
	}
	
	
	@RequestMapping("downExcel")
	public @ResponseBody void downExcel(HttpServletRequest request, HttpServletResponse response, JqGridReq jqGridReq,BankAccountOpenApplyEffec bankAccountOpenApplyEffec) throws Exception {
		String thebankAccount = new String(request.getParameter("thebankAccount").getBytes("ISO8859-1"), "UTF-8");
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		bankAccountOpenApplyEffec.setThebankAccount(thebankAccount);
		OutputStream os = null;
		UserProfile user = this.getUserProfile();
		try {
			String fileName = "账户开立生效表.xls";
//			if (salary != null) {
//				String year = salary.getYear();
//				if (StringUtils.isNotBlank(year)) {
//					fileName = year + "年人员积分.xls";
//				} else {
//					return null;
//				}
//			}
			HSSFWorkbook wb = this.bankAccountOpenApplyEffecService.getWorkbook(bankAccountOpenApplyEffec,user,jqGrid);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ URLEncoder.encode(fileName, "utf-8"));
			os = response.getOutputStream();
			wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			os.flush();
			os.close();
		}
	}
	/**
	 * 查询生效表当前第一行有没有变更或者撤销的记录
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryHaveData")
	public String queryHaveData(String effectiveId)
			throws IOException {
		return this.bankAccountOpenApplyEffecService.queryHaveData(effectiveId);
	}	
	
	
}
