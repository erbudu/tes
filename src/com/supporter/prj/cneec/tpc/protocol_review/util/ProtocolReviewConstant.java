package com.supporter.prj.cneec.tpc.protocol_review.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Title: ProtocolReviewConstant
 * @Description: 框架协议评审常量
 * @author: yanweichao
 * @date: 2017-9-6
 * @version: V1.0
 */
public class ProtocolReviewConstant {

	/** 评审结论 **/
	public static final int REVIEW_CONCLUSION_AGREE = 101;
	public static final int REVIEW_CONCLUSION_CONTINUE = 102;
	public static final int REVIEW_CONCLUSION_DISAGREE = 103;

	public static Map<Integer, String> getReviewConclusionMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(REVIEW_CONCLUSION_AGREE, "同意签约");
		map.put(REVIEW_CONCLUSION_CONTINUE, "继续谈判");
		map.put(REVIEW_CONCLUSION_DISAGREE, "不同意签约");
		return map;
	}

}
