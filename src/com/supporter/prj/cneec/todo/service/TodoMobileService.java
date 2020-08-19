package com.supporter.prj.cneec.todo.service;
     
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.todo.dao.TodoMobileDao;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.todo.entity.VTodo;
import com.supporter.prj.eip.todo.util.TodoQueryParams;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil; 
  
/**
 * 系统待办.
 * @author linda
 *
 */
@Service
public class TodoMobileService {
    
    @Autowired
    private TodoMobileDao todoDao;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List < VTodo  > getTodos(JqGrid jqGrid, UserProfile user, TodoQueryParams queryParams, int rowCount){
    	if (user == null)return null;

		List < VTodo > todos = todoDao.getMobileTodos(jqGrid, user.getPersonId(), queryParams);
		if (todos == null || todos.size() == 0)return null;
		
		List < VTodo  > todosReturn = new ArrayList < VTodo  >();
		
		List < String > oaPcfilteringCharacters = this.getOaPcDefFilteringCharacters();
		List < String >  amfilteringCharacters = this.getAMDefFilteringCharacters();
		List < String >  plfilteringCharacters = this.getPLDefFilteringCharacters();
		List < String >  cmfilteringCharacters = this.getCMDefFilteringCharacters();
		
		int size = todos.size();
		for (int i = 0; i < size; i++){
			if (todosReturn.size() == rowCount)break;
			
			VTodo todo = todos.get(i);
			String webappName = CommonUtil.trim(todo.getWebappName());
			String webPageUrl = todo.getWebPageUrl();
			if (CommonUtil.isEmpty(webPageUrl))continue;
			
			if (webappName.equals("BM") || webappName.equals("CNEEC_OA") || webappName.equals("CNEEC_PC")){
				if (existsInList(oaPcfilteringCharacters, webPageUrl)){
					todosReturn.add(todo);
				}
			} else if (webappName.equals("AM")){
				if (existsInList(amfilteringCharacters, webPageUrl)){
					todosReturn.add(todo);
				}
			} else if (webappName.equals("CNEEC_PL")){
				if (existsInList(plfilteringCharacters, webPageUrl)){
					todosReturn.add(todo);
				}
			} else if (webappName.equals("CM")){
				if (existsInList(cmfilteringCharacters, webPageUrl)){
					todosReturn.add(todo);
				}
			}
		}
    	return todosReturn;
    }
    
    /**
     * 在DB中进行过滤后读出数据(用于手机分页显示,耗DB性能).
     * @param jqGrid
     * @param user
     * @param queryParams
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List < VTodo  > getTodosPage(JqGrid jqGrid, UserProfile user, TodoQueryParams queryParams){
    	if (user == null)return null;
    	
    	List < String > urlFilters = new ArrayList < String >();
    	urlFilters.addAll(getOaPcDefFilteringCharacters());
    	urlFilters.addAll(getAMDefFilteringCharacters());
    	urlFilters.addAll(getPLDefFilteringCharacters());
    	urlFilters.addAll(getCMDefFilteringCharacters());

		List < VTodo > todos = todoDao.getMobileTodosPage(jqGrid, user.getPersonId(), queryParams, urlFilters);
		return todos;
    }
    /**
	 * 获取记录总个数.
	 * @param personId
	 * @param queryParams
	 * @param urlFilters
	 * @return
	 */
	public int getTotalCount(UserProfile user, TodoQueryParams queryParams){
		if (user == null)return 0;
    	
    	List < String > urlFilters = new ArrayList < String >();
    	urlFilters.addAll(getOaPcDefFilteringCharacters());
    	urlFilters.addAll(getAMDefFilteringCharacters());
    	urlFilters.addAll(getPLDefFilteringCharacters());
    	urlFilters.addAll(getCMDefFilteringCharacters());
		return todoDao.getTotalCount(user.getPersonId(), queryParams, urlFilters);
	}
    
    public VTodo getTodo(String personId, String messageId, String webappName){
    	return todoDao.getTodo(personId, messageId, webappName);
    }
    
    private boolean existsInList(List < String > filters, String webPageUrl){
    	int size = filters.size();
    	for (int i = 0; i < size; i++){
    		if (webPageUrl.contains(filters.get(i))){
    			return true;
    		}
    	}
    	return false;
    }

    /**
     * 获取指定业务(OA)的过滤字符
     * @return
     */
    private List < String > getOaPcDefFilteringCharacters(){
    	String doc_in = "/doc_in/";
    	String doc_out= "/doc_out/";
    	String report = "/report/";
    	//签报
    	String signed_report = "/signed_report/";
    	//用印
    	String use_seal_apply = "/use_seal_apply/";
    	//出国
    	String abroad = "/abroad/";
    	//邀请外国人来华
    	String invitation_f = "/invitation_f/";

    	String prj_contract_settlement = "/prj_contract_settlement/"; 
    	String prj_foreign_contract_settlement = "/prj_foreign_contract_settlement/";
    	//授权书
    	String authority_apply = "/authority_apply/";
    	//销售合同审批
    	String sale_contract_exam = "/sale_contract_exam/";
    	
    	//现场拨款
    	String prj_virtual_pay = "/prj_virtual_pay/";
    	//非合同付款
    	String prj_settlement = "/prj_settlement/";
    	
    	List < String > filteringCharacters = new ArrayList < String >();
    	filteringCharacters.add(doc_in);
    	filteringCharacters.add(doc_out);
    	filteringCharacters.add(prj_contract_settlement);
    	filteringCharacters.add(prj_foreign_contract_settlement);
    	filteringCharacters.add(report);
    	filteringCharacters.add(signed_report);
    	filteringCharacters.add(use_seal_apply);
    	filteringCharacters.add(abroad);
    	filteringCharacters.add(invitation_f);
    	filteringCharacters.add(authority_apply);
    	filteringCharacters.add(sale_contract_exam);
    	filteringCharacters.add(prj_virtual_pay);
    	filteringCharacters.add(prj_settlement);
    	return filteringCharacters;
    }
    /**
     * 获取指定业务(PL)的过滤字符
     * @return
     */
    private List < String > getPLDefFilteringCharacters(){
    	String purchase_contract_exam = "/purchase_contract_exam/";
    	//普通采购
    	String purchasing_plan = "/purchasing_plan/";
    	//备品备件
    	String purchasing_spare = "/purchasing_spare/";
    	//零星物资
    	String purchasing_sporadic = "/purchasing_sporadic/";
    	//分包合同变更调整
    	String contract_change = "/contract_change/";
    	//采购过程信息上线
    	String purchasing_contract_filing = "/purchasing_contract_filing/";
    	//分包合同变更信息上线
    	String contract_change_online = "/contract_change_online/";
    	List < String > filteringCharacters = new ArrayList < String >();
    	filteringCharacters.add(purchase_contract_exam);
    	filteringCharacters.add(purchasing_plan);
    	filteringCharacters.add(purchasing_spare);
    	filteringCharacters.add(purchasing_sporadic);
    	filteringCharacters.add(contract_change);
    	filteringCharacters.add(purchasing_contract_filing);
    	filteringCharacters.add(contract_change_online);
    	return filteringCharacters;
    }
    /**
     * 获取指定业务(CM)的过滤字符
     * @return
     */
    private List < String > getCMDefFilteringCharacters(){
    	//月报
    	String month_report = "/monthly_report/";
    	//保函开立
    	String guarantee_apply = "/guarantee_apply/";
    	//风险确认书
    	String bank_risk_confirm = "/bank_risk_confirm/";
    	//保函撤销
    	String guarantee_cancel = "/guarantee_cancel/";
    	//保函修改
    	String guarantee_modify = "/guarantee_modify/";
    	//分包保函收取申请
    	String sub_guarantee_apply = "/sub_guarantee_apply/";
    	//分包保函修改收取
    	String sub_guarantee_modify = "/sub_guarantee_modify/";
    	List < String > filteringCharacters = new ArrayList < String >();
    	filteringCharacters.add(month_report);
    	filteringCharacters.add(guarantee_apply);
    	filteringCharacters.add(bank_risk_confirm);
    	filteringCharacters.add(guarantee_cancel);
    	filteringCharacters.add(guarantee_modify);
    	filteringCharacters.add(sub_guarantee_apply);
    	filteringCharacters.add(sub_guarantee_modify);
    	return filteringCharacters;
    }
    /**
     * 获取指定业务(AM)的过滤字符
     * @return
     */
    private List < String > getAMDefFilteringCharacters(){
    	String asset_purchase_reimburse = "/asset_purchase_reimburse/";
    	List < String > filteringCharacters = new ArrayList < String >();
    	filteringCharacters.add(asset_purchase_reimburse);
    	return filteringCharacters;
    }
}
