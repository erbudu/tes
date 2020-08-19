package com.supporter.prj.linkworks.oa.consignment.util;

import java.util.ArrayList;
import java.util.List;

public class ConsignmentUtil {

	private List<ProcDef> getList(){
		List<ProcDef> ProcDefIds = new ArrayList<ProcDef>();
		for(int i = 1;i<=10;i++){
			ProcDef def = new ProcDef();
			def.setProc_def_id("ProcDefIds_id"+i);
			def.setProc_name("ProcDefIds_name"+i);
			ProcDefIds.add(def);
		}
		return ProcDefIds;
	}
	
	private List<ProcDef> getListPc(){
		List<ProcDef> ProcDefPcIds = new ArrayList<ProcDef>();
		for(int i = 1;i<=10;i++){
			ProcDef def = new ProcDef();
			def.setProc_def_id("ProcDefPcIds_id"+i);
			def.setProc_name("ProcDefPcIds_name"+i);
			ProcDefPcIds.add(def);
		}
		return ProcDefPcIds;
	}
	
	private List<ProcDef> getListAm(){
		List<ProcDef> ProcDefAmIds = new ArrayList<ProcDef>();
		for(int i = 1;i<=10;i++){
			ProcDef def = new ProcDef();
			def.setProc_def_id("ProcDefAmIds_id"+i);
			def.setProc_name("ProcDefAmIds_name"+i);
			ProcDefAmIds.add(def);
		}
		return ProcDefAmIds;
	}
	
	private List<ProcDef> getListPl(){
		List<ProcDef> ProcDefPlIds = new ArrayList<ProcDef>();
		for(int i = 1;i<=10;i++){
			ProcDef def = new ProcDef();
			def.setProc_def_id("ProcDefPlIds_id"+i);
			def.setProc_name("ProcDefPlIds_name"+i);
			ProcDefPlIds.add(def);
		}
		return ProcDefPlIds;
	}
	
	private List<ProcDef> getListBm(){
		List<ProcDef> ProcDefBmIds = new ArrayList<ProcDef>();
		for(int i = 1;i<=10;i++){
			ProcDef def = new ProcDef();
			def.setProc_def_id("ProcDefBmIds_id"+i);
			def.setProc_name("ProcDefBmIds_name"+i);
			ProcDefBmIds.add(def);
		}
		return ProcDefBmIds;
	}
	
	private List<ProcDef> getListDm(){
		List<ProcDef> ProcDefDmIds = new ArrayList<ProcDef>();
		for(int i = 1;i<=10;i++){
			ProcDef def = new ProcDef();
			def.setProc_def_id("ProcDefDmIds_id"+i);
			def.setProc_name("ProcDefDmIds_name"+i);
			ProcDefDmIds.add(def);
		}
		return ProcDefDmIds;
	}
	
	private List<ProcDef> getListCm(){
		List<ProcDef> ProcDefCmIds = new ArrayList<ProcDef>();
		for(int i = 1;i<=10;i++){
			ProcDef def = new ProcDef();
			def.setProc_def_id("ProcDefCmIds_id"+i);
			def.setProc_name("ProcDefCmIds_name"+i);
			ProcDefCmIds.add(def);
		}
		return ProcDefCmIds;
	}
	
	public List<ProcDef> getList(int id){
		List<ProcDef> list = null;
		switch(id){
		case 1:
			list = getList();
			break;
		case 2:
			list = getListPc();
			break;
		case 3:
			list = getListAm();
			break;
		case 4:
			list = getListPl();
			break;
		case 5:
			list = getListBm();
			break;
		case 6:
			list = getListDm();
			break;
		default: 
			list = getListCm();
			break;
		}
		return list;
	}
	
}
