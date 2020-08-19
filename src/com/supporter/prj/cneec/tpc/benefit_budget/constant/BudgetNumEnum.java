package com.supporter.prj.cneec.tpc.benefit_budget.constant;

/**
 * @Title: BudgetNumberEnum
 * @Description: 数字对应
 * @author: yanweichao
 * @date: 2018-5-8
 * @version: V1.0
 */
public enum BudgetNumEnum {

	ONE(0, "一、"), TWO(1, "二、"), THREE(2, "三、"), FOUR(3, "四、"), FIVE(4, "五、"),
	SIX(5, "六、"), SEVEN(6, "七、"), EIGHT(7, "八、"), NINE(8, "九、"), TEN(9, "十、"),
	ELEVEN(10, "十一、"), TWELVE(11, "十二、"), THIRTEEN(12, "十三、"), FOURTEEN(13, "十四、");

	private int innerName;
	private String displayName;

	private BudgetNumEnum(int innerName, String displayName) {
		this.innerName = innerName;
		this.displayName = displayName;
	}

	public int getInnerName() {
		return innerName;
	}

	public void setInnerName(int innerName) {
		this.innerName = innerName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public static BudgetNumEnum[] values = BudgetNumEnum.values();

	public static BudgetNumEnum valueOf(int i) {
		return values[i];
	}

}
