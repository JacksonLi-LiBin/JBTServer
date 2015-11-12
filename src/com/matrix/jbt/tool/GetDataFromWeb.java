package com.matrix.jbt.tool;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

/**
 * get data from web
 * 
 * @author JacksonLi
 * @date 2014/5/23
 */
public class GetDataFromWeb {
	/**
	 * 
	 * @param URL
	 * @param sendTimes
	 * @return
	 * @author JacksonLi String
	 */
	public static String doHttpGet(String URL, int sendTimes) {
		InputStream instr = null;

		try {
			URL url = new URL(URL);
			HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
			urlCon.setDoOutput(true);
			urlCon.setDoInput(true);
			urlCon.setUseCaches(false);

			urlCon.setRequestMethod("GET");

			urlCon.setReadTimeout(3600000);

			urlCon.setRequestProperty("Content-Type", "application/json");

			instr = urlCon.getInputStream();

			byte[] bis = IOUtils.toByteArray(instr);

			String ResponseString = new String(bis, "UTF-8");

			if ((ResponseString == null) || ("".equals(ResponseString.trim()))) {
				System.out.println("return null");
			}

			return ResponseString;
		} catch (Exception e) {
			// System.out.println("Time " + (sendTimes + 1));
			// // e.printStackTrace();
			// if (sendTimes < 10) {
			// sendTimes++;
			// doHttpGet(URL, sendTimes);
			// } else {
			// System.out.println("timeout");
			// return "false";
			// }
			return "fasle";
		} finally {
			try {
				if (instr != null) {
					instr.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
