package com.matrix.jbt.tool;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;

/**
 * send push message to google
 * @author JacksonLi
 * @date 2014/4/23
 */
public class SendPushToGoogle {
	private static String url="";
	private static String sendJson="";
	
	/**
	 * 
	 * @param msgType private or common
	 * @param msg msg contents
	 * @author JacksonLi
	 * void
	 */
	@SuppressWarnings("rawtypes")
	public String sendPushMessage(String msgType,String msg,Map<Integer, String> map){
		url = ReadProperties.read("url", "gpurl");
		//calculate how many times server has pushed message to google cloud
		int pushTimesCount=1;
		sendJson = findXmlInfo();
		
		if(msgType.equals("maslulNo")){
			JSONArray jsonArray=JSONArray.fromObject(msg);
			Iterator iter = map.entrySet().iterator();
			//push every device a job message when gcm service is running
			String registionId="";
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				registionId= (String) entry.getValue();
			}
			sendJson = "{\"collapse_key\":\"score_update\",\"time_to_live\":2419200,\"delay_while_idle\":true,\"data\":{\"type\":\""+msgType+"\",\"dataPush\":"+jsonArray.toString()+"},\"registration_ids\":[\""
					+registionId+"\"]}";
		}else{
			JSONObject jsonObject=JSONObject.fromObject(msg);
			List<String> idsList=new ArrayList<>();
			Iterator iter = map.entrySet().iterator();
			//push every device a job message when gcm service is running
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				idsList.add((String) entry.getValue());
			}
			JSONArray jsonArray=JSONArray.fromObject(idsList);
			sendJson = "{\"collapse_key\":\"score_update\",\"time_to_live\":2419200,\"delay_while_idle\":true,\"data\":{\"type\":\""+msgType+"\",\"dataPush\":"+jsonObject.toString()+"},\"registration_ids\":"+jsonArray.toString()+"}";
		}

		String responseStr=doHttpPost(sendJson, url, pushTimesCount);
		
		return responseStr;
	}
	
	private String findXmlInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public String doHttpPost(String xmlInfo, String URL,int sendTimes) {
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
			
			urlCon.setRequestProperty("Authorization","key=AIzaSyD9sdzcKpNuWI03vlfvrMEo8IQY_dK1XVY");
			
			urlCon.setRequestProperty("Content-length",String.valueOf(xmlData.length));

			//System.out.println("data length:"+String.valueOf(xmlData.length));

			DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());

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
			System.out.println("send push again-----Time  "+(sendTimes+1));
			//e.printStackTrace();
			if(sendTimes<10){
				sendTimes++;
				doHttpPost( xmlInfo,  URL, sendTimes);
			}else{
				System.out.println("send push message timeout");
				return "false";
			}
		}finally {
			try {
				if(instr!=null){
					instr.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return "false";
	}
}
