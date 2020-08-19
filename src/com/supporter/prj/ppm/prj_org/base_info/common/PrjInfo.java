/**
 * 
 */
package com.supporter.prj.ppm.prj_org.base_info.common;

import java.util.List;

import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.entity.PrjBidInf;

/**
 *<p>Title: PrjInfo</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月16日
 * 
 */
public  interface PrjInfo {
	
	/**
	 * <pre>更新项目开发状态</pre>
	 * @param prjId 项目主键
	 * @param prjActiveStatus 项目开发状态
	 * @return 项目开发状态值
	 */
	Prj updatePrjActiveStauts(String prjId,int prjActiveStatus);
	
	/**
	 * <pre>获取招标信息</pre>
	 * @param prjId 项目主键
	 * @return 招标信息
	 */
	PrjBidInf getPrjBidInfInf(String prjId);
	
	/**
	 * <P>获取项目基本信息</P>
	 * @author YUEYUN
	 * @param prjId 项目主键
	 * @return 项目信息
	 */
	 Prj PrjInformation(String prjId);
	 
	 /**
	  * <pre>获取所有项目信息</pre>
	  * @author YUEYUN
	  * @return 项目信息列表
	  */
	 List<Prj> getAllPrjInformation(); 

}
