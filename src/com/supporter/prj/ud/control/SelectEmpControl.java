package com.supporter.prj.ud.control;

import com.supporter.prj.ud.func.entity.UdFuncPageCell;

public class SelectEmpControl extends InputControl {

	@Override
	public String getSuppTagCode(UdFuncPageCell paramFuncPageCell) {
		String p = "";
		if (paramFuncPageCell != null) {
			p += "<div class='form-group' id='emp'>"
					+ "<label class='supp-form-label col-xs-4 col-sm-4 col-md-4 col-lg-4'>"
					+ paramFuncPageCell.getLabelName() + "</label>"
					+ "<div class='col-xs-7 col-sm-7 col-md-7 col-lg-7'>"
					+ "<div data-element='" + paramFuncPageCell.getInputMode()
					+ paramFuncPageCell.getListId() + "_id"
					+ ":hidden,employeeNo:hidden,"
					+ paramFuncPageCell.getInputMode()
					+ paramFuncPageCell.getListId()
					+ "' class='employee '>" + "</div>" + "</div>"
					+ " </div>";
		}
		// data-casecadedeptid=\"123\" data-rootdeptid=\"rootDeptId\"

		return p;
	}
}
