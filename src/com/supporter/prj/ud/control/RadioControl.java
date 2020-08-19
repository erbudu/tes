package com.supporter.prj.ud.control;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.ud.func.entity.UdFuncPageCell;

public class RadioControl extends InputControl {

	@Override
	public String getSuppTagCode(UdFuncPageCell paramFuncPageCell) {
		String p = "";
		if (paramFuncPageCell != null) {
			p += "<div class=\"form-group\">"
					+ " <label for=\"email\" class=\"supp-form-label col-xs-4 col-sm-4 col-md-4 col-lg-4\">"
					+ paramFuncPageCell.getLabelName() + "</label>"
					+ "<div class=\"col-xs-8 col-sm-8 col-md-8 col-lg-8\">"
					+ "<div class=\"radio-group radio-inline\">";
			String v = paramFuncPageCell.getSelectValues();
			if (StringUtils.isNotBlank(v)) {
				int i = 1;
				for (String checkValue : v.split(",")) {
					String radioId = "radio" + String.valueOf(i);
					p += "<div class=\"radioDiv\">"
							+ "<input type=\"radio\" id=\"" + radioId
							+ "\" name='" + paramFuncPageCell.getInputMode()
							+ paramFuncPageCell.getListId() + "' value=\""
							+ checkValue + "\">" + "<label for=\"" + radioId
							+ "\">" + "<span class=\"radioSpan\">" + checkValue
							+ "</span>" + "</label>" + "</div>";
					i++;
				}
			}
			p += "</div>" + "</div>" + "</div>";
		}

		return p;
	}
}
