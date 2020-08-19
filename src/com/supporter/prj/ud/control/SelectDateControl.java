package com.supporter.prj.ud.control;

import com.supporter.prj.ud.func.entity.UdFuncPageCell;

public class SelectDateControl extends InputControl {

	@Override
	public String getSuppTagCode(UdFuncPageCell paramFuncPageCell) {
		String p = "";
		if (paramFuncPageCell != null) {
			String v = paramFuncPageCell.getDefaultValueSetting();
			p += " <div class=\"form-group\">"
					+ "<label class=\"col-xs-4 col-sm-4 col-md-4 col-lg-4 supp-form-label\">"
					+ paramFuncPageCell.getLabelName() + "</label>"
					+ "<div class=\"col-xs-7 col-sm-7 col-md-7 col-lg-7\">";
			if (v.equals("当前日期")) {
				p += " <div class=\"input-group date form_date at\">"
						+ "<input class=\"form-control\" type=\"text\" name=\""
						+ paramFuncPageCell.getInputMode()
						+ paramFuncPageCell.getListId() + "\" >"
						+ "<span class=\"input-group-addon-remove\">"
						+ "<span class=\"glyphicon glyphicon-remove\"></span>"
						+ "</span>" + "</div>";
			} else {
				p += "<div class=\"input-group date form_date_hour at\">"
						+ "<input class=\"form-control\" type=\"text\" name=\""
						+ paramFuncPageCell.getInputMode()
						+ paramFuncPageCell.getListId() + "\">"
						+ "<span class=\"input-group-addon-remove\">"
						+ "<span class=\"glyphicon glyphicon-remove\"></span>"
						+ "</span>" + " </div>";
			}

			p += "</div>" + " </div>";
		}
		return p;
	}
}
