package com.supporter.prj.pm.enginee_negotiate.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.pm.enginee_negotiate.entity.base.BaseEngineeVisaMeet;

@Entity
@Table(name = "PM_ENGINEE_VISA_MEET", schema = "")
public class EngineeVisaMeet extends BaseEngineeVisaMeet {
	private static final long serialVersionUID = 1L;

	public EngineeVisaMeet() {
		super();
	}

	// 获取会议结论中文显示
	public String getMeetingResultDesc() {
		return MeetingResultCode.getMap().get(getMeetingResult());
	}

	/**
	 * 获取会议结论中文显示
	 */
	public static final class MeetingResultCode {
		public static final int DISAGREE = 1; // 不同意
		public static final int AGREE = 0; // 同意

		/**
		 * 获取会议结论码表.
		 * @return Map<Integer, String>
		 */
		public static Map<Integer, String> getMap() {
			Map<Integer, String> map = new LinkedHashMap<Integer, String>();
			map.put(DISAGREE, "不同意");
			map.put(AGREE, "同意");
			return map;
		}

	}

}
