package com.supporter.prj.pm.contract_balance.entity;

/**
 * 特殊指定的结算项
 * @author Administrator
 *
 */
public class SpecialBalanceItem {
	public static final String WBS_ALIAS_1 = "工程进度款支付项"; //WBS的别名，用于显示

	private String wbsId;
	private String parentWbsId;
	private String wbsName;
	private boolean isIncrease = true; //是否调增
	
	public String getWbsId() {
		return this.wbsId;
	}
	public void setWbsId(String wbsId) {
		this.wbsId = wbsId;
	}
	
	public String getWbsName() {
		return this.wbsName;
	}
	public void setWbsName(String wbsName) {
		this.wbsName = wbsName;
	}
	
	public String getParentWbsId() {
		return this.parentWbsId;
	}
	public void setParentWbsId(String parentWbsId) {
		this.parentWbsId = parentWbsId;
	}
	
	public boolean getIsIncrease() {
		return this.isIncrease;
	}
	public void setIsIncrease(boolean isIncrease) {
		this.isIncrease = isIncrease;
	}
	
	public SpecialBalanceItem(String itemId) {
		if (itemId.equals(ItemId.HTZFX)) {
			newBalanceItem(itemId, "合同支付项", null, true);
		} else if (itemId.equals(ItemId.HTZFX_OTHER)) {
			newBalanceItem(itemId, "其它款项", ItemId.HTZFX, true);
		} else if (itemId.equals(ItemId.HTZFX_OTHER_AGENT)) {
			newBalanceItem(itemId, "委托代收", ItemId.HTZFX, true);
		} else if (itemId.equals(ItemId.HTZFX_OTHER_VISA)) {
			newBalanceItem(itemId, "与实体工程无关的施工签证", ItemId.HTZFX, true);
		} else if (itemId.equals(ItemId.HTZFX_OTHER_OTHER)) {
			newBalanceItem(itemId, "其它", ItemId.HTZFX, true);
		} else if (itemId.equals(ItemId.KJX)) {
			newBalanceItem(itemId, "扣减项", null, false);
		} else if (itemId.equals(ItemId.KJX_ADVANCE)) {
			newBalanceItem(itemId, "预付款", ItemId.KJX, false);
		} else if (itemId.equals(ItemId.KJX_OTHER)) {
			newBalanceItem(itemId, "其它应扣款项", ItemId.KJX, false);
		} else if (itemId.equals(ItemId.KJX_OTHER_RETENTION)) {
			newBalanceItem(itemId, "质保金", ItemId.KJX_OTHER, false);
		} else if (itemId.equals(ItemId.KJX_OTHER_PRODUCTION)) {
			//安全生产措施费保证金提取
			newBalanceItem(itemId, "安全生产措施费保证金提取", ItemId.KJX_OTHER, false);
		} else if (itemId.equals(ItemId.KJX_OTHER_PEASANT)) {
			//农民工工资保证金提取
			newBalanceItem(itemId, "农民工工资保证金提取", ItemId.KJX_OTHER, false);
		} else if (itemId.equals(ItemId.KJX_OTHER_AGENT_PAY)) {
			newBalanceItem(itemId, "委托代付", ItemId.KJX_OTHER, false);
		} else if (itemId.equals(ItemId.KJX_OTHER_AGENT_PUR)) {
			newBalanceItem(itemId, "委托代采", ItemId.KJX_OTHER, false);
		} else if (itemId.equals(ItemId.KJX_OTHER_OTHER)) {
			newBalanceItem(itemId, "其它", ItemId.KJX_OTHER, false);
		} else if (itemId.equals(ItemId.HTXXSGFSP)) {
			newBalanceItem(itemId, "合同项下施工方索赔", null, true);
		} else if (itemId.equals(ItemId.DYJHBZJSF)) {
			newBalanceItem(itemId, "抵押金或保证金释放", null, true);
		} else if (itemId.equals(ItemId.DYJHBZJSF_RETENTION)) {
			//质保金释放
			newBalanceItem(itemId, "质保金释放", ItemId.DYJHBZJSF, true);
		} else if (itemId.equals(ItemId.DYJHBZJSF_PRODUCTION)) {
			//安全生产措施费释放
			newBalanceItem(itemId, "安全生产措施费释放", ItemId.DYJHBZJSF, true);
		} else if (itemId.equals(ItemId.DYJHBZJSF_PEASANT)) {
			//农民工工资保证金释放
			newBalanceItem(itemId, "农民工工资保证金释放", ItemId.DYJHBZJSF, true);
		}
	}
	
	/**
	 * 给对象赋值
	 * @param wbsId wbsId
	 * @param wbsName wbsName
	 * @param parentWbsId parentWbsId
	 */
	private void newBalanceItem(String wbsId, String wbsName, String parentWbsId, boolean isIncrease) {
		this.setWbsId(wbsId);
		this.setWbsName(wbsName);
		this.setParentWbsId(parentWbsId);
		this.setIsIncrease(isIncrease);
	}
	
	
	/**
	 * 结算项固定ID.
	 */
	public static final class ItemId {
		public static final String HTZFX = "heTongZhiFuXiang"; // 合同支付项
		public static final String HTZFX_OTHER = "heTongZhiFuXiang_other"; // 合同支付项-其它款项
		public static final String HTZFX_OTHER_AGENT = "heTongZhiFuXiang_otherAgent"; // 合同支付项-其它款项-委托代收
		public static final String HTZFX_OTHER_VISA = "heTongZhiFuXiang_otherVisa"; // 合同支付项-其它款项-与实体工程无关的施工签证
		public static final String HTZFX_OTHER_OTHER = "heTongZhiFuXiang_otherOther"; // 合同支付项-其它款项-其它
		
		public static final String KJX = "kouJianXiang"; // 扣减项
		public static final String KJX_ADVANCE = "kouJianXiang_advance"; // 扣减项-预付款
		public static final String KJX_OTHER = "kouJianXiang_other"; // 扣减项-其他应扣款项
		
		public static final String KJX_OTHER_RETENTION = "kouJianXiang_otherRetention"; // 扣减项-其他应扣款项-质保金
		public static final String KJX_OTHER_PRODUCTION = "kouJianXiang_otherProduction"; // 扣减项-其他应扣款项-安全生产措施费保证金提取
		public static final String KJX_OTHER_PEASANT = "kouJianXiang_otherPeasant"; // 扣减项-其他应扣款项-农民工工资保证金提取
		
		public static final String KJX_OTHER_AGENT_PAY = "kouJianXiang_otherAgentPay"; // 扣减项-其他应扣款项-委托代付
		public static final String KJX_OTHER_AGENT_PUR = "kouJianXiang_otherAgentPur"; // 扣减项-其他应扣款项-委托代采
		public static final String KJX_OTHER_OTHER = "kouJianXiang_otherOther"; // 扣减项-其他应扣款项-其它
		
		public static final String HTXXSGFSP = "shiGongFangSuoPei"; // 合同项下施工方索赔
		public static final String DYJHBZJSF = "baoZhengJinShiFang"; // 抵押金或保证金释放
		public static final String DYJHBZJSF_RETENTION = "baoZhengJinShiFang_retention"; // 抵押金或保证金释放-质保金释放
		public static final String DYJHBZJSF_PRODUCTION = "baoZhengJinShiFang_production"; // 抵押金或保证金释放-安全生产措施费释放 
		public static final String DYJHBZJSF_PEASANT = "baoZhengJinShiFang_peasant"; // 抵押金或保证金释放-农民工工资保证金释放 
		
		private ItemId() {
			
		}
	}
}
