package com.supporter.prj.ud.control;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.ud.func.entity.UdFuncPageCell;

public class SelectCodeTableControl extends InputControl {

	@Override
	public String getSuppTagCode(UdFuncPageCell paramFuncPageCell) {
		String p = "";
		if (paramFuncPageCell != null) {
			p += "<div class=\"form-group\">"
					+ "<label class=\"supp-form-label col-xs-4 col-sm-4 col-md-4 col-lg-4\">"
					+ paramFuncPageCell.getLabelName()
					+ "</label>"
					+ "<div class=\"col-xs-7 col-sm-7 col-md-7 col-lg-7\">"
					+ "<select  class=\"selectpicker at\" id=\"testId1\" title=\"请选择\" name=\""
					+ paramFuncPageCell.getInputMode()
					+ paramFuncPageCell.getListId() + "\">";
			String v = paramFuncPageCell.getSelectValues();
			if (StringUtils.isNotBlank(v)) {
				for (String checkValue : v.split(",")) {
					p += "<option value=\"checkValue\">" + checkValue
							+ "</option>";
				}
			}
			p += "</select>" + "</div>" + "</div>";
		}

		return p;
	}
}
