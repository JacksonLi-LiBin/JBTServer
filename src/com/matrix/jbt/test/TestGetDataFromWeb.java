package com.matrix.jbt.test;

import org.junit.Test;

import com.matrix.jbt.tool.GetDataFromWeb;

public class TestGetDataFromWeb {

	@Test
	public void testDoHttpGet() {
		GetDataFromWeb
				.doHttpGet(
						"http://webapp.johnbryce.co.il/mobileapp/Service/MobileService.svc/getjobs",
						1);
	}

}
