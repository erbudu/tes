package com.supporter.prj.ppm.prj_org.util;

/**
 * 相似度检测
 * @author YUEYUN
 */
public final class SimilarityUtils {

	private SimilarityUtils() {
	}

	
	public static int ld(String src, String tar) {
		
		if(src.matches("[a-zA-Z]+") && tar.matches("[a-zA-Z]+")){
		src.toLowerCase();
		tar.toLowerCase();
		}
		int d[][];
		int sLen = src.length();
		int tLen = tar.length();
		int si;
		int ti;
		char ch1;
		char ch2;
		int cost;
		
		if (sLen == 0) {
			return tLen;
		}
		if (tLen == 0) {
			return sLen;
		}
		d = new int[sLen + 1][tLen + 1];
		for (si = 0; si <= sLen; si++) {
			d[si][0] = si;
		}
		for (ti = 0; ti <= tLen; ti++) {
			d[0][ti] = ti;
		}
		for (si = 1; si <= sLen; si++) {
			ch1 = src.charAt(si - 1);
			for (ti = 1; ti <= tLen; ti++) {
				ch2 = tar.charAt(ti - 1);
				if (ch1 == ch2) {
					cost = 0;
				} else {
					cost = 1;
				}
				d[si][ti] = Math.min(Math.min(d[si - 1][ti] + 1, d[si][ti - 1] + 1), d[si - 1][ti - 1] + cost);
			}
		}
		return d[sLen][tLen];
	}
	
	/**
	 * <pre>
	 * 编辑距离计算相似度
	 * 说明：值越接近1标识相似度越高，等于1，则完全相等
	 * </pre>
	 * @param src 源对象
	 * @param tar 比较对象
	 * @return 相似度double类型
	 */
	public static double similarity(String src, String tar) {
		int ld = ld(src, tar);
		return 1 - (double) ld / Math.max(src.length(), tar.length());
	}

//	public static void main(String[] args) {//测试代码
//		
//		String src = "济南舜泰电力项目";
//		String tar = "济南舜泰电力";
//		System.out.println(SimilarityUtils.similarity(src, tar));
//		//System.out.println("sim=" + SimilarityUtils.similarity(src, tar)*100+"%");
//	}
}
