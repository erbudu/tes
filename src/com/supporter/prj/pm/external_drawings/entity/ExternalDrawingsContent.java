package com.supporter.prj.pm.external_drawings.entity;

import com.supporter.prj.pm.external_drawings.entity.base.BaseExternalDrawingsContent;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pm_external_drawings_content", schema = "")
public class ExternalDrawingsContent extends BaseExternalDrawingsContent{
	public static String TYPE_ONE = "Ⅰ类";
	public static String TYPE_TWO = "Ⅱ类";
	public static String TYPE_THREE = "Ⅲ类";
	public static String TYPE_FOUR = "Ⅳ类";

	public ExternalDrawingsContent() {
		super();
	}
	
}
