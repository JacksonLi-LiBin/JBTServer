package com.matrix.jbt.test;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

import com.matrix.jbt.entity.Maslul;
import com.matrix.jbt.tool.GetURLUtil;

/**
 * 
 * @author JacksonLi
 * @date 2014/3/10
 */
public class ForgotPasswordTest {
	Scanner input = new Scanner(System.in);
	private List<Maslul> masluls = new ArrayList<>();

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void forgotPassword() {
		System.out.println("userid");
		String userid = input.nextLine();
		String json = "";
		if (userid.trim().length() > 0) {
			try {
				json = getJsonString("http://webapp.johnbryce.co.il/mobileapp/Service/MobileService.svc/getdata?id="
						+ userid);
				JSONObject jsonObject = JSONObject.fromObject(json);
				if (jsonObject.get("d").equals("\"Empty\"")) {
					System.out.println("userid does not exist");
					return;
				} else {
					JSONArray jsonArray = JSONArray.fromObject(jsonObject
							.get("d"));
					Maslul maslul = null;
					for (int i = 0; i < jsonArray.size(); i++) {
						maslul = new Maslul();
						maslul.setMaslulNo(JSONObject
								.fromObject(jsonArray.get(i))
								.get("CycleNumber").toString());
						maslul.setUserId(Integer.parseInt(userid));
						masluls.add(maslul);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String getStr = GetURLUtil.getService()
					.path("/rest/CommonMethods/sendEmail")
					.queryParam("userId", userid).accept(MediaType.TEXT_PLAIN)
					.get(String.class);
			System.out.println(getStr);
		} else {

		}
	}

	private String getJsonString(String urlPath) throws Exception {
		URL url = new URL(urlPath);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.connect();
		InputStream inputStream = connection.getInputStream();
		// The corresponding character encoding conversion
		Reader reader = new InputStreamReader(inputStream, "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(reader);
		String str = null;
		StringBuffer sb = new StringBuffer();
		while ((str = bufferedReader.readLine()) != null) {
			sb.append(str);
		}
		reader.close();
		connection.disconnect();
		return sb.toString();
	}
}
