package com.supporter.prj.ppm.poa.bor.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.role.entity.Role;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.prj.hibernate.HibernateUtil;
import com.supporter.prj.ppm.poa.bor.dao.BidCurrencyDao;
import com.supporter.prj.ppm.poa.bor.dao.BidInfDao;
import com.supporter.prj.ppm.poa.bor.dao.BidNotifyDao;
import com.supporter.prj.ppm.poa.bor.dao.BidOpenResultDao;
import com.supporter.prj.ppm.poa.bor.dao.BidPriceDao;
import com.supporter.prj.ppm.poa.bor.dao.NotifyDao;
import com.supporter.prj.ppm.poa.bor.entity.BidCurrency;
import com.supporter.prj.ppm.poa.bor.entity.BidInf;
import com.supporter.prj.ppm.poa.bor.entity.BidNotify;
import com.supporter.prj.ppm.poa.bor.entity.BidOpenResult;
import com.supporter.prj.ppm.poa.bor.entity.BidPrice;
import com.supporter.prj.ppm.poa.bor.entity.Notify;
import com.supporter.prj.ppm.poa.icc.entity.Coordination;
import com.supporter.prj.ppm.prj_org.base_info.common.PrjInfo;
import com.supporter.prj.ppm.prj_org.base_info.constant.BaseInfoConstant;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.entity.PrjBidInf;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

@Service
public class BidOpenResultService {
	@Autowired
	private BidOpenResultDao bidOpenResultDao;
	@Autowired
	private BidCurrencyDao bidCurrencyDao;
	@Autowired
	private BidInfDao bidInfDao;
	@Autowired
	private BidPriceDao bidPriceDao;
	@Autowired
	private NotifyDao notifyDao;
	@Autowired
	private BidNotifyDao bidNotifyDao;
	@Autowired
	private PrjInfo prjInfo;
	
	

	public List<BidCurrency> getGridCurrency(UserProfile user, JqGrid jqGrid, String borId) {
		return this.bidOpenResultDao.findPageCurrency(user, jqGrid, borId);
	}
	public BidOpenResult getBor(UserProfile user, String borId) {
		return this.bidOpenResultDao.get( borId);
	}

	public List<BidInf> getGridInf(UserProfile user, JqGrid jqGrid, String borId) {
		StringBuffer json = new StringBuffer();
		List<BidInf> byBorId = bidInfDao.getByBorId(borId);
		if(byBorId!=null)
		{		
		for (int i = 0; i < byBorId.size(); i++) {
			List<BidPrice> price = this.bidPriceDao.getByBidInfId(byBorId.get(i).getBidInfId());
			for (int j = 0; j < price.size(); j++) {
				BidCurrency currency = bidCurrencyDao.get(price.get(j).getBidCurrencyId());
				//String currencyId = currency.getCurrencyId();
				String amount = price.get(j).getQuotedAmount();
				// json.append('"'+currencyId+'"'+':'+'"'+amount+'"'+",");
				if (j == price.size() - 1) {
					json.append(amount);
				} else if (j == 0) {
					json.append(amount + ",");
				} else {
					json.append(amount + ",");
				}


			}
			byBorId.get(i).setCurrencyIds(json.toString());
			json.delete(0, json.length());
		}}
		
		return byBorId;
		
	}

	public BidOpenResult saveOrUpdate(BidOpenResult bidOpenResult, BidCurrency bidCurrency, UserProfile user,
			HttpServletRequest request, HttpServletResponse response) {
		//BidOpenResult
		if (bidOpenResult.getBorId()=="") {
			bidOpenResult.setBorId((UUIDHex.newId()));
			bidOpenResult.setStatus(Integer.valueOf(0));
			bidOpenResult.setCreatedBy(user.getName());
			if (user.getDept() != null) {
				bidOpenResult.setCreatedByDept(user.getDept().getDeptId());
			} else {
				bidOpenResult.setCreatedByDept("");
			}

			bidOpenResult.setCreatedById(user.getPersonId());
			bidOpenResult.setCreatedDate(new Date());
			bidOpenResult.setModifiedDate(new Date());
			bidOpenResult.setModifiedName(user.getAccount().getName());
			bidOpenResult.setModifiedId(user.getAccount().getAccountId());
			this.bidOpenResultDao.save(bidOpenResult);
		} else {
			BidOpenResult bidOpenResults=this.bidOpenResultDao.get(bidOpenResult.getBorId());
			bidOpenResults.setStatus(Integer.valueOf(0));
			bidOpenResults.setModifiedDate(new Date());
			bidOpenResults.setModifiedName(user.getName());
			bidOpenResults.setModifiedId(user.getPersonId());
			this.bidOpenResultDao.update(bidOpenResults);
		}
		if (bidCurrency != null&&bidCurrency.getCurrencyId()!=null&&bidOpenResult.getBorId()!="") {
			String currencyId = bidCurrency.getCurrencyId();
			String[] array = currencyId.split(",");
			boolean isIn = checkRepeat(array);
			if (!isIn) {

				BidOpenResult bidOpenResults = new BidOpenResult();
				bidOpenResults.setBorId("");
				return bidOpenResults;
			} 
			else  {
				saveOrUpdateCurrency(request, response, user, bidOpenResult, bidOpenResult.getBorId());
			}
		}
		else if(bidOpenResult.getBorId()=="") {
			BidOpenResult bidOpenResultsss = new BidOpenResult();
			bidOpenResultsss.setBorId("Null");
			return bidOpenResultsss;
		}
		else {
			BidOpenResult bidOpenResultss = new BidOpenResult();
			bidOpenResultss.setBorId("F");
			return bidOpenResultss;
		}
		return bidOpenResult;
	}/**币别下一步*/
	public BidOpenResult saveOrUpdates(BidOpenResult bidOpenResult, BidCurrency bidCurrency, UserProfile user,
			HttpServletRequest request, HttpServletResponse response) {
		if (bidOpenResult.getBorId()=="") {
			bidOpenResult.setBorId((UUIDHex.newId()));
			bidOpenResult.setStatus(Integer.valueOf(1));
			bidOpenResult.setCreatedBy(user.getName());
			if (user.getDept() != null) {
				bidOpenResult.setCreatedByDept(user.getDept().getDeptId());
			} else {
				bidOpenResult.setCreatedByDept("");
			}

			bidOpenResult.setCreatedById(user.getPersonId());
			bidOpenResult.setCreatedDate(new Date());
			bidOpenResult.setModifiedDate(new Date());
			bidOpenResult.setModifiedName(user.getAccount().getName());
			bidOpenResult.setModifiedId(user.getAccount().getAccountId());
			this.bidOpenResultDao.save(bidOpenResult);
		} else {
			BidOpenResult bidOpenResults=this.bidOpenResultDao.get(bidOpenResult.getBorId());
			bidOpenResults.setStatus(Integer.valueOf(1));
			bidOpenResults.setModifiedDate(new Date());
			bidOpenResults.setModifiedName(user.getName());
			bidOpenResults.setModifiedId(user.getPersonId());
			this.bidOpenResultDao.update(bidOpenResults);
		}
		if (bidCurrency != null&&bidCurrency.getBorCurrencyId()!=null&&bidOpenResult.getBorId()!="") {
			String currencyId = bidCurrency.getCurrencyId();
			String[] array = currencyId.split(",");
			boolean isIn = checkRepeat(array);
			if (!isIn) {

				BidOpenResult bidOpenResults = new BidOpenResult();
				bidOpenResults.setBorId("");
				return bidOpenResults;
			} else {
				saveOrUpdateCurrency(request, response, user, bidOpenResult, bidOpenResult.getBorId());
			}
		}
		else {
			BidOpenResult bidOpenResultss = new BidOpenResult();
			bidOpenResultss.setBorId("F");
			return bidOpenResultss;
		}
		return bidOpenResult;
	}

	/** 判断是否有重复 */
	public static boolean checkRepeat(String[] array) {
		Set<String> set = new HashSet<String>();
		for (String str : array) {
			set.add(str);
		}
		if (set.size() != array.length) {
			return false;// 有重复
		} else {
			return true;// 不重复
		}
	}
	/**点击暂存*/
	public BidOpenResult saveOrUpdateBidInfo(BidOpenResult bidOpenResult, BidCurrency bidCurrency, BidInf bidInf,
			UserProfile user, HttpServletRequest request, HttpServletResponse response) {
		BidOpenResult bidOpenResults = this.bidOpenResultDao.get(bidOpenResult.getBorId());
		
		bidOpenResults.setStatus(2);
		bidOpenResults.setModifiedDate(new Date());
		bidOpenResults.setModifiedId(user.getPersonId());
		bidOpenResults.setModifiedName(user.getName());
		this.bidOpenResultDao.update(bidOpenResults);
		if (bidOpenResults != null) {
			saveOrUpdateBidInf(request, response, user, bidOpenResult, bidOpenResult.getBorId());
			saveOrUpdateBidPrice(request, response, user, bidOpenResult, bidOpenResult.getBorId(),
					bidInf.getBidInfId());
			boolean ok=calculateDiscount(bidOpenResult, bidOpenResult.getBorId());
			if(ok) {
			sort(bidOpenResult.getBorId());}
			else {
				bidOpenResult.setBorId("isNotNum");
			}
		}
		return bidOpenResult;
	}
	//判断是不是数字
	public boolean isNumeric(String str) {
		String bigStr;
		try {
			bigStr = new BigDecimal(str).toString();
		} catch (Exception e) {
			return false;// 异常 说明包含非数字。
		}
		return true;
	}
/**点击提交修改状态*/
	public BidOpenResult saveOrUpdateBidInfos(BidOpenResult bidOpenResult, BidCurrency bidCurrency, BidInf bidInf,
			UserProfile user, HttpServletRequest request, HttpServletResponse response) {
		BidOpenResult bidOpenResults = this.bidOpenResultDao.get(bidOpenResult.getBorId());
		if (bidOpenResults != null) {
			bidOpenResults.setStatus(2);
			this.bidOpenResultDao.update(bidOpenResults);
			saveOrUpdateBidInf(request, response, user, bidOpenResult, bidOpenResult.getBorId());
			saveOrUpdateBidPrice(request, response, user, bidOpenResult, bidOpenResult.getBorId(),
					bidInf.getBidInfId());
			boolean ok=calculateDiscount(bidOpenResult, bidOpenResult.getBorId());
			if(ok) {
			sort(bidOpenResult.getBorId());}
			else {
				bidOpenResult.setBorId("isNotNum");
			}
		}
		
		return bidOpenResult;
	}

	public void sort(String borId) {
		List<BidInf> bidInf = bidInfDao.getByOrder(borId);

		int no = 1;
		for (int i = 0; i < bidInf.size(); i++) {
			bidInf.get(i).setOrderNo(String.valueOf(no));
			Double deferences = ((Double.parseDouble(bidInf.get(i).getEquivalentDollars())
					/ Double.parseDouble(bidInf.get(0).getEquivalentDollars())) * 100 - 100);
			bidInf.get(i).setBidPriceDeference(String.valueOf(deferences));
			this.bidInfDao.update(bidInf.get(i));
			no += 1;
		}
	}

	public boolean calculateDiscount(BidOpenResult bidOpenResult, String borId) {
		//是否存入数据库
		boolean Ok=true;
		//是否跳出循环，如果取出字符不是数字，则不进行存储并且ok置为false
		boolean flag=true;
		List<BidInf> listBidInf = bidInfDao.getByBorId(borId);

		for (int i = 0; i < listBidInf.size(); i++) {
			double sum = 0.00;
			List<BidPrice> listBidPrice = this.bidPriceDao.getByBidInfId(listBidInf.get(i).getBidInfId());
			for (int j = 0; j < listBidPrice.size(); j++) {
				// 取得投标金额
				String amount = listBidPrice.get(j).getQuotedAmount();
				System.out.println(amount);
				boolean isnum =	isNumeric(amount);
				System.out.println(isnum+"00");
				if((isnum&&flag)) {
					// 取得相关币别的汇率
					BidCurrency currency = this.bidCurrencyDao.get(listBidPrice.get(j).getBidCurrencyId());
					String exchangeRate = currency.getExchangeRate();
					// System.out.println(exchangeRate+"汇率");
					// 计算折合美元
					Double dis = Double.parseDouble(amount) * Double.parseDouble(exchangeRate);
					// System.out.println(dis+"折合美元");
					sum += dis;
				}
				else {
					flag=false;
					sum=-1;
					break;
				}
				
			}
			if(sum>0) {
			listBidInf.get(i).setEquivalentDollars(String.valueOf(sum));
			this.bidInfDao.update(listBidInf.get(i));
			Ok=true;
			}
			else {
				Ok=false;
				
			}
		}
return Ok;
	}

	public void saveOrUpdateCurrency(HttpServletRequest request, HttpServletResponse response, UserProfile user,
			BidOpenResult bidOpenResult, String borId) {
		List<BidCurrency> bidCurrencys = bidOpenResult.getBidCurrencyList();
		if (bidCurrencys != null && bidCurrencys.size() > 0) {
			for (BidCurrency bidCurrency1 : bidCurrencys) {
				if (bidCurrency1.getBorCurrencyId() != null && bidCurrency1.getBorCurrencyId() != "") {
					BidCurrency isIn = this.bidCurrencyDao.get(bidCurrency1.getBorCurrencyId());
					if (isIn != null) {
						String CurrencyName = EIPService.getComCodeTableService()
								.getCodetableItem(bidCurrency1.getCurrencyId()).getItemValue();
						isIn.setCurrencyName(CurrencyName);
						isIn.setExchangeRate(bidCurrency1.getExchangeRate());
						this.bidCurrencyDao.update(isIn);
					} else {
						bidCurrency1.setBorId(bidOpenResult.getBorId());
						bidCurrency1.setBorCurrencyId(bidCurrency1.getBorCurrencyId());
						if (bidCurrency1.getBorCurrencyId() != null) {
							String CurrencyName = EIPService.getComCodeTableService()
									.getCodetableItem(bidCurrency1.getCurrencyId()).getItemValue();
							bidCurrency1.setCurrencyName(CurrencyName);

						}
						this.bidCurrencyDao.save(bidCurrency1);
					}

				}
			}
		}

	}

	public void saveOrUpdateBidPrice(HttpServletRequest request, HttpServletResponse response, UserProfile user,
			BidOpenResult bidOpenResult, String borId, String BidInfId) {
		List<BidCurrency> bidCurrencys = bidOpenResult.getBidCurrencyList();
		if (bidCurrencys != null && bidCurrencys.size() > 0) {
			for (int j = 0; j < bidCurrencys.size(); j++) {
				String[] amount = bidCurrencys.get(j).getCurrencyId().split(",");
				String[] bidCurrencyId = bidCurrencys.get(j).getBorCurrencyId().split(",");
				String[] bidinfids = BidInfId.split(",");
				for (int i = 0; i < amount.length; i++) {
					BidPrice isIn = this.bidPriceDao.getByThreeId(bidCurrencyId[i], borId, bidinfids[j]);
					if (isIn != null) {
						isIn.setQuotedAmount(amount[i]);
						this.bidPriceDao.update(isIn);
					} else {
						System.out.println(amount[i]);
						BidPrice bidPrice = new BidPrice();
						bidPrice.setRecordId((UUIDHex.newId()));
						bidPrice.setBorId(borId);
						bidPrice.setBidInfId(bidinfids[j]);
						bidPrice.setQuotedAmount(amount[i]);// 这个字段只会存循环的最后一个值
						bidPrice.setBidCurrencyId(bidCurrencyId[i]);
						this.bidPriceDao.save(bidPrice);

					}
				}

			}
		}

	}

	public void saveOrUpdateBidInf(HttpServletRequest request, HttpServletResponse response, UserProfile user,
			BidOpenResult bidOpenResult, String borId) {
		List<BidInf> BidInfs = bidOpenResult.getBidInfoList();
		if (BidInfs != null && BidInfs.size() > 0) {
			for (BidInf bidInf1 : BidInfs) {
				if (bidInf1.getBidInfId() != null && bidInf1.getBidInfId() != "") {
					BidInf isIn = this.bidInfDao.get(bidInf1.getBidInfId());
					if (isIn != null) {
						isIn.setBidAgainstName(bidInf1.getBidAgainstName());
						isIn.setBidNo(bidInf1.getBidNo());
						isIn.setBidPriceDeference(bidInf1.getBidPriceDeference());
						isIn.setBidValidityId(bidInf1.getBidValidityId());
						isIn.setDiscountRate(bidInf1.getDiscountRate());
						isIn.setEquivalentDollars(bidInf1.getEquivalentDollars());
						// isIn.setOrderNo(orderNo);
						if (bidInf1.getBidValidityId() != null) {
							String BidValidityName = EIPService.getComCodeTableService()
									.getCodetableItem(bidInf1.getBidValidityId()).getItemValue();
							bidInf1.setBidValidityName(BidValidityName);
						}
						this.bidInfDao.update(isIn);
					} else {
						bidInf1.setBorId(bidOpenResult.getBorId());
						bidInf1.setBidInfId(bidInf1.getBidInfId());
						if (bidInf1.getBidValidityId() != null || bidInf1.getBidValidityId() != "undefined") {
							String BidValidityName = EIPService.getComCodeTableService()
									.getCodetableItem(bidInf1.getBidValidityId()).getItemValue();
							bidInf1.setBidValidityName(BidValidityName);

						}
						this.bidInfDao.save(bidInf1);
					}

				}
			}
		}

	}
	public  BidNotify initEditOrViewPageNotify(UserProfile user,String prjId) {
		BidNotify bidNotify=notifyDao.getByPrjId(prjId);
		
		if(bidNotify!=null&&bidNotify.getRecordId()!="F") {
			
			return bidNotify;
		}
		
		else {
			Prj prj= prjInfo.PrjInformation(prjId);
			
			BidNotify bidNotifys=new BidNotify();
			bidNotifys.setRecordId("F");
			bidNotifys.setPrjName(prj.getPrjCName());
			bidNotifys.setPrjNo(prj.getPrjNo());
			bidNotifys.setPrjId(prjId);
			bidNotifys.setStatusDesc("否");
			PrjBidInf prjBidInf=prjInfo.getPrjBidInfInf(prjId);
			System.out.println(prjBidInf);
			if(prjBidInf!=null) {
			bidNotifys.setBidEndTime(prjBidInf.getBidCloseTime());}
			
			bidNotifys.setNotifyPerson(prj.getCreatedBy());
			
	    	return bidNotifys;
		}
	}
	public  BidOpenResult initEditOrViewCurrency(UserProfile user,String prjId) {
		BidOpenResult bidOpenResult=bidOpenResultDao.getByPrjId(prjId);
		if(bidOpenResult!=null&&bidOpenResult.getBorId()!="F") {
			return bidOpenResult;
		}
		else {
			BidOpenResult bidOpenResults=new BidOpenResult();
			bidOpenResults.setBorId("F");
			Prj prj= prjInfo.PrjInformation(prjId);
			PrjBidInf prjBidInf=prjInfo.getPrjBidInfInf(prjId);
			bidOpenResults.setPrjName(prj.getPrjCName());
			bidOpenResults.setPrjNo(prj.getPrjNo());
			bidOpenResults.setPrjId(prjId);
			bidOpenResults.setBidEndTime(prjBidInf.getBidCloseTime());
	    	return bidOpenResults;
		}
	
	}
	
	public BidOpenResult confirmNotice(String borId,String prjId) {
		BidOpenResult bidOpenResult = bidOpenResultDao.get(borId);//项目信息
		//prj.setProcId(UUIDHex.newId());
		bidOpenResult.setStatus(4);
		String procId=UUIDHex.newId();
		String title = "知会:"+bidOpenResult.getCreatedByDept()+" -"+bidOpenResult.getCreatedBy()+"统计的开标结果";
		String url = "/ppm/poa/bor/bor_price_notify.html?borId="+borId+"&prjId="+prjId;//可以直接在写一个页面用来做知会
		//String procId = coordination.getProcId();
			
			String department = BaseInfoConstant.MANAGEMENT_DEPARTMENT;//经营管理部-部门
			// recorder = BaseInfoConstant.ROLE_RECORDER;//经营管理部项目信息登记管理岗位人员
			//String bidFiling = BaseInfoConstant.ROLE_BIDFILING;//经营管理部投议标备案及许可岗位人员
			String trgDept=bidOpenResult.getCreatedDeptId();//项目原部门
			String expatriateManagers = BaseInfoConstant.ROLE_ExpatriateManagers;//经营管理部-外派事业部经理
			
			
			/* 通知-经营管理部-外派事业部经理原部门*/
			List<Person> personList_expatriateSrc = getPersonListByRole(trgDept,expatriateManagers);
			for (Person person_expatrSrc : personList_expatriateSrc) {
				String personId = person_expatrSrc.getPersonId();
				sendNotice(personId,title,url,procId);
			}
			
		
			bidOpenResultDao.update(bidOpenResult);//通知完成后改变状态为已通知
		return bidOpenResult;
	}
private List<Person> getPersonListByRole(String deptId,String roleId) {
		
		Dept dept = EIPService.getDeptService().getDept(deptId);
		Role role = EIPService.getRoleService().getRole(roleId);
		List<Person> personList = EIPService.getRoleService().getPersonsForDept(role, dept);
		return personList;
		
	}

public List<Map<String, String>> getNoticePerson() {
	
	String department = BaseInfoConstant.MANAGEMENT_DEPARTMENT;//经营管理部:部门编号
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	
	//Role role1 = EIPService.getRoleService().getRole(BaseInfoConstant.ROLE_RECORDER);//角色：经营管理部-项目信息记录员
	//Role role2 = EIPService.getRoleService().getRole(BaseInfoConstant.ROLE_BIDFILING);//角色：经营管理部-投议标备案及许可人员
	Role role3 = EIPService.getRoleService().getRole(BidOpenResult.ROLE_ExpatriateManagers);//角色：经营管理部-经营管理部外派事业部经理
	
	/*角色*/
	Map<String,String> mapRole = new HashMap<String, String>();
	//mapRole.put("role1", role1.getName());
	//mapRole.put("role2", role2.getName());
	mapRole.put("role3", role3.getName());
	
	/*角色下人员*/
	/*String role1Person = "";
	List<Person> role1PersList = getPersonListByRole(department,role1.getRoleId());
	for (int i = 0; i < role1PersList.size(); i++) {
		role1Person += role1PersList.get(i).getName()+"、";
	}
	mapRole.put("role1Person",role1Person);
	
	
	String role2Person = "";
	List<Person> role2PersList = getPersonListByRole(department,role2.getRoleId());
	for (int i = 0; i < role2PersList.size(); i++) {
		role2Person += role2PersList.get(i).getName()+"、";
	}
	mapRole.put("role2Person",role2Person);*/
	
	/*角色下人员*/
	String role3Person = "";
	List<Person> role3PersList = getPersonListByRole(department,role3.getRoleId());
	for (int i = 0; i < role3PersList.size(); i++) {
		role3Person += role3PersList.get(i).getName()+"、";
	}
	mapRole.put("role3Person",role3Person);
	list.add(mapRole);
	return list;
}
	
	private void sendNotice(String personId,String title,String url,String procId) {
		
		Message message = EIPService.getBMSService().newMessage();//--------------------------------获取待办通知服务内容
		message.setPersonId(personId);//------------------------------------------------------------通知人
		//String title = "知会："+purchaseDemand.getProcTitle();
		//待办标题
		message.setEventTitle(title);//-------------------------------------------------------------通知待办标题
		message.setNotifyTime(new Date());//--------------------------------------------------------通知待办日期
		//待办地址"/cpp/purchase_demand/demand/purchase_demand_audit_notify.html?id="+id
		message.setWebPageURL(url);//------------------------------------------------------------------通知待办地址
		message.setModuleId(BidOpenResult.MODULE_ID);//------------------------------------------------------应用编号
		//默认地指定为“待办”类型
		message.setMessageType(ITodo.MsgType.CC);//-------------------------------------------------待办类型
		message.setWebappName("BM");
		//message.setWfProcId(execContext.getProcId());
		message.setWfProcId(procId);
		//加入待办事宜（BMS）
		EIPService.getBMSService().addMessage(message);//-------------------------------------------获取待办服务发送待办
	}/*
	通知项目开发负责人填写开标结果
	*/
public void sendNotices(String personId,String recordId ,String prjName,String prjId) {
		Message message = EIPService.getBMSService().newMessage();//--------------------------------获取待办通知服务内容
		message.setPersonId(personId);//------------------------------------------------------------通知人
		String title = "知会：您负责的项目"+prjName+"已经可以填写开标结果";
		//待办标题
		message.setEventTitle(title);//-------------------------------------------------------------通知待办标题
		message.setNotifyTime(new Date());//--------------------------------------------------------通知待办日期
		//待办地址
		String url="/ppm/poa/bor/bor_notification_proc.html?recordId="+recordId+"&prjId="+prjId;
		message.setWebPageURL(url);//------------------------------------------------------------------通知待办地址
		message.setModuleId(BidOpenResult.MODULE_ID);//------------------------------------------------------应用编号
		//默认地指定为“待办”类型
		message.setMessageType(ITodo.MsgType.CC);//-------------------------------------------------待办类型
		message.setWebappName("BM");
		//message.setWfProcId(execContext.getProcId());
		message.setWfProcId(UUIDHex.newId());
		//加入待办事宜（BMS）
		EIPService.getBMSService().addMessage(message);//-------------------------------------------获取待办服务发送待办
	}
}
