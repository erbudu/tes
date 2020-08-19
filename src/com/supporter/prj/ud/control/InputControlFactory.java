package com.supporter.prj.ud.control;

public class InputControlFactory {
	public static InputControl createInputControl(String as_InputControlType) {
		if (as_InputControlType.equals(InputControlType.TEXTINPUT)) {
			return new TextInputControl();
		}
		if (as_InputControlType.equals("NUMBERINPUT")) {
			return new NumberInputControl();
		}
		if (as_InputControlType.equals(InputControlType.TEXTAREA)) {
			return new TextAreaControl();
		}
		if (as_InputControlType.equals(InputControlType.RADIO)) {
			return new RadioControl();
		}
		if (as_InputControlType.equals(InputControlType.CHECKBOX)) {
			return new CheckboxControl();
		}
		if (as_InputControlType.equals(InputControlType.SELECT_CODETABLE)) {
			return new SelectCodeTableControl();
		}
		if (as_InputControlType.equals(InputControlType.SELECT_DATE)) {
			return new SelectDateControl();
		}
		if (as_InputControlType.equals(InputControlType.SELECT_EMP)) {
			return new SelectEmpControl();
		}
		if (as_InputControlType.equals(InputControlType.SELECT_ORG)) {
			return new SelectOrgControl();
		}
		return null;
	}
}
