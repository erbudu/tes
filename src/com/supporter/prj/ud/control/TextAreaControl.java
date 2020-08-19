package com.supporter.prj.ud.control;

import com.supporter.prj.ud.func.entity.UdFuncPageCell;

public class TextAreaControl extends InputControl {

	@Override
	public String getSuppTagCode(UdFuncPageCell paramFuncPageCell) {
		String p = "";
		if (paramFuncPageCell != null) {
			p += "<div class=\"form-group\">"
					+ "<label class=\"supp-form-label col-xs-4 col-sm-4 col-md-4 col-lg-4\">"
					+ paramFuncPageCell.getLabelName() + "</label>"
					+ "<div class=\" col-xs-7 col-sm-7 col-md-7 col-lg-7\">"
					+ "<textarea class=\"form-control at\" name=\""
					+ paramFuncPageCell.getInputMode()
					+ paramFuncPageCell.getListId()
					+ "\" rows=\"2\"></textarea>" + "</div>" + "</div>";
		}

		return p;
	}
}
