package com.supporter.prj.ud.control;

import com.supporter.prj.ud.func.entity.UdFuncPageCell;

public class NumberInputControl extends InputControl {

	@Override
	public String getSuppTagCode(UdFuncPageCell paramFuncPageCell) {
		String p = "";
		if (paramFuncPageCell != null) {
			p = "<div class=\"form-group \">"
					+ "<label class=\"supp-form-label col-xs-4 col-sm-4 col-md-4 col-lg-4\">"
					+ paramFuncPageCell.getLabelName()
					+ "</label>"
					+ "<div class=\"col-xs-8 col-sm-8 col-md-8 col-lg-8\">"
					+ "<div class=\"supp-input-group-lg\">"
					+ "<input class=\"form-control NumericInput\" type=\"text\" name=\""
					+ paramFuncPageCell.getInputMode()
					+ paramFuncPageCell.getListId()
					+ "\" style=\"text-align:right\">" + "</div>" + "</div>"
					+ "</div>";
		}

		return p;
	}

}
