package com.matrix.jbt.test;

import static org.junit.Assert.fail;

import java.util.Scanner;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.matrix.jbt.entity.Friend;
import com.matrix.jbt.tool.GetURLUtil;

public class RecommendTest {
	Scanner input = new Scanner(System.in);
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void recommend() {
		System.out.println("token:");
		String token = input.nextLine();		
		System.out.println("name:");
		String name = input.nextLine();
		System.out.println("phone:");
		String phone = input.nextLine();
		System.out.print("email: ");
		String email = input.nextLine();	
		email = ("".equals(email) ? "NONAME@NONAME.COM":email);
		System.out.print("interest: ");
		String interest = input.nextLine();
		
		Friend friend=new Friend(112233, name , name,
				phone, email, "jbt",
				"02/11/2013 03:15:27", "448", "894",
				"1", "0", interest,
				304826563, 0);

		if (token.trim().length() > 0 && name.trim().length() > 0
				&& phone.trim().length() > 0 && email.trim().length() > 0 && interest.trim().length() > 0) {
			String str = GetURLUtil.getService().path("/rest/Friend").path(token).path("add").entity(friend,MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN).post(String.class);
			
			System.out.println("--return-->" + str);
		}
	}

}
