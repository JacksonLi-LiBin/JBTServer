package com.matrix.jbt.test;

import static org.junit.Assert.fail;

import java.util.Scanner;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.matrix.jbt.tool.GetURLUtil;

public class InquiryTest {
	Scanner input = new Scanner(System.in);

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void inquiry() {
		System.out.println("token:");
		String token = input.nextLine();
		System.out.println("subjectnum:");
		String subjectnum = input.nextLine();
		System.out.println("details:");
		String details = input.nextLine();
		System.out.print("email: ");
		String email = input.nextLine();
		System.out.print("phone: ");
		String phone = input.nextLine();

		if (token.trim().length() > 0 && subjectnum.trim().length() > 0
				&& details.trim().length() > 0 && email.trim().length() > 0&& phone.trim().length() > 0) {
			String str = GetURLUtil.getService().path("/rest/inquiry")
					.path(token).path("create").queryParam("fullname", "darren").queryParam("id", "304826563")
					.queryParam("coursename", "english").queryParam("coursenum", "123")
					.queryParam("cyclenum", "456").queryParam("branch", "1").queryParam("phone", phone)
					.queryParam("email", email).queryParam("subjectnum", subjectnum).queryParam("details", details)
					.accept(MediaType.TEXT_PLAIN).get(String.class);

			System.out.println("--return-->" + str);
		}
	}
}
