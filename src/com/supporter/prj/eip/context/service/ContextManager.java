package com.supporter.prj.eip.context.service;

import java.util.Locale;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.tenant.entity.ITenant;
import com.supporter.util.CommonUtil;

/**
 * 上下文的管理器，目前只记录语言环境、租户等.
 * @author Arnold
 *
 */
public final class ContextManager {
	
	public static final String DEFAULT_SCHEMA_PREFIX = "supp";
	
	/**
	 * 构造方法.
	 */
	private ContextManager() {}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ThreadLocal < String > tenantNo = new ThreadLocal();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ThreadLocal < String > schemaPrefix = new ThreadLocal();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ThreadLocal < ITenant > tenant = new ThreadLocal();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ThreadLocal < Locale > locale = new ThreadLocal();

	/**
	 * Web根目录.
	 * 例如：d:/app/bmv6
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ThreadLocal < String > rootDirPath = new ThreadLocal();
	
	/**
	 * 设置当前租户No.
	 * @param paramVal
	 */
	public static void setTenantNo(String paramVal) {
		String currTenantNo = CommonUtil.trim(tenantNo.get());
		String newTenantNo = CommonUtil.trim(paramVal);
		if (currTenantNo.equals(newTenantNo)) {
			return; //没有发生改变，不需要做什么
		} else {
			tenantNo.set(newTenantNo);
			schemaPrefix.set(null);
			tenant.set(null);
		}
		
				
	}
	
	public static String getTenantNo() {
		return tenantNo.get();
	}
	
	public static ITenant getTenant() {
		ITenant t = tenant.get();
		if (t != null) return t; //已经初始化过
		
		String tNo = tenantNo.get();
		if (CommonUtil.trim(tNo).length() == 0) {
			//currentTenantNo尚未被设置过，也就无法获取租户实例
			return null;
		}
		
		//调用租户服务，获取租户实例，并加入ThreadLocal中
		t = EIPService.getTenantService().getTenantByNo(tNo);
		tenant.set(t);
		
		return t;
	}
	
	public static Locale getLocale() {
		return locale.get();
	}
	
	public static void setLocale(Locale paramVal) {
		locale.set(paramVal);
	}

	public static String getSchemaPrefix() {
		String prefix = schemaPrefix.get();
		if (prefix != null) return prefix;
		
		//从tenant中获取看看
		ITenant t = tenant.get();
		if (t != null) {
			prefix = CommonUtil.trim(t.getSchemaPrefix());
			if (prefix.length() > 0) {
				schemaPrefix.set(prefix);
				return prefix;
			}
		}
		
		//根据tenantNo获取
		String tNo = tenantNo.get();
		if (tNo != null) {
			prefix = CommonUtil.trim(EIPService.getTenantService().getSchemaPrefixByTenantNo(tNo));
			if (prefix.length() > 0) {
				schemaPrefix.set(prefix);
				return prefix;
			}
		}
		
		//EIPService.getLogService().error("both tenant and tenantNo are null, defautl schema prefix is returned:" + DEFAULT_SCHEMA_PREFIX);
		return DEFAULT_SCHEMA_PREFIX;
	}

	/**
	 * @return ThreadLocal < String > - 返回  {@link #rootDirPath}。
	 */
	public static String getRootDirPath() {
		String strRootDirPath = rootDirPath.get();
		
		if (strRootDirPath == null || "".equals(strRootDirPath)) {
			final String WEB_ROOT_DIR_START_SIGN = "jar:file:/";
			final String WEB_ROOT_DIR_END_SIGN = "/WEB-INF";
			String classDirPath = ContextManager.class.getResource("").toString();
			strRootDirPath = classDirPath.substring(WEB_ROOT_DIR_START_SIGN.length(), classDirPath.indexOf(WEB_ROOT_DIR_END_SIGN));
			setRootDirPath(strRootDirPath);
		}
		
		return strRootDirPath;
	}

	/**
	 * @param rootDirPath 要设置的 {@link #rootDirPath}。
	 */
	public static void setRootDirPath(String strRootDirPath) {
		rootDirPath.set(strRootDirPath);
	} 
}

