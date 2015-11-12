package com.matrix.jbt.tool;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

/**
 * post data to web server
 * 
 * @author JacksonLi
 * @date 2014/5/23
 */
public class PostDataToWeb {
	/**
	 * send data
	 * 
	 * @param xmlInfo
	 *            send data
	 * @param URL
	 *            send url
	 * @param sendTimes
	 *            calculate send times
	 * @return
	 * @author JacksonLi String
	 */
	public static String doHttpPost(String xmlInfo, String URL, int sendTimes) {
		System.out.println("send data:" + xmlInfo);
		byte[] xmlData = xmlInfo.getBytes();
		InputStream instr = null;

		try {
			URL url = new URL(URL);
			HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
			urlCon.setDoOutput(true);
			urlCon.setDoInput(true);
			urlCon.setUseCaches(false);

			urlCon.setRequestMethod("POST");

			urlCon.setReadTimeout(30000);

			urlCon.setRequestProperty("Content-Type", "application/json");

			urlCon.setRequestProperty("Content-length",
					String.valueOf(xmlData.length));

			// System.out.println("data length:"+String.valueOf(xmlData.length));

			DataOutputStream printout = new DataOutputStream(
					urlCon.getOutputStream());

			printout.write(xmlData);

			printout.flush();

			printout.close();

			instr = urlCon.getInputStream();

			byte[] bis = IOUtils.toByteArray(instr);

			String ResponseString = new String(bis, "UTF-8");

			if ((ResponseString == null) || ("".equals(ResponseString.trim()))) {
				System.out.println("return null");
			}

			System.out.println("return data:" + ResponseString);
			return ResponseString;
		} catch (Exception e) {
			System.out.println("Time " + (sendTimes + 1));
			// e.printStackTrace();
			if (sendTimes < 10) {
				sendTimes++;
				doHttpPost(xmlInfo, URL, sendTimes);
			} else {
				System.out.println("timeout");
				return "false";
			}
		} finally {
			try {
				if (instr != null) {
					instr.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return "false";
	}
}
