package com.supporter.prj.ud.control;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.ud.func.entity.UdFuncPageCell;

public class CheckboxControl extends InputControl {

	@Override
	public String getSuppTagCode(UdFuncPageCell paramFuncPageCell) {
		String p = "";
		if (paramFuncPageCell != null) {
			p = " <div class=\"form-group\">"
					+ "<label for=\"email\" class=\"supp-form-label col-xs-4 col-sm-4 col-md-4 col-lg-4\">"
					+ paramFuncPageCell.getLabelName() + "</label>"
					+ "<div class=\"col-xs-8 col-sm-8 col-md-8 col-lg-8\">"
					+ "<div class=\"checkbox-group checkbox-inline\">";

			String v = paramFuncPageCell.getSelectValues();
			if (StringUtils.isNotBlank(v)) {
				int i = 1;
				for (String checkValue : v.split(",")) {
					String checkId = "checkbox" + String.valueOf(i);
					p += "<div class=\"checkboxDiv\">"
							+ "<input type=\"checkbox\" id=\"" + checkId
							+ "\" name='" + paramFuncPageCell.getInputMode()
							+ paramFuncPageCell.getListId() + "' value='"
							+ checkValue + "'>" + "<label for=\"" + checkId
							+ "\">" + " <span class=\"checkboxSpan\">"
							+ checkValue + "</span>" + "</label>" + " </div>";
					i++;
				}
			}
			p += " </div>" + "</div>" + " </div>";
		}

		return p;
	}

}
