package com.supporter.prj.cneec.tpc.trade_homepage.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Title: TradeHomepageUtil
 * @Description: 读取贸易导航页JSON
 * @author: yanweichao
 * @date: 2018-1-25
 * @version: V1.0
 */
public class TradeHomepageUtil {

	/**
	 * 读取json文件为字符串
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String readFileFromData(String fileName) throws IOException {
		StringBuffer buffer = new StringBuffer();
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				buffer.append(line);
			}
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		}
		return buffer.toString();
	}

}
