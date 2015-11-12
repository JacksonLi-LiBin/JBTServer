package com.matrix.jbt.test;

import static org.junit.Assert.fail;

import java.util.Scanner;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.matrix.jbt.tool.GetURLUtil;

public class JobTest {

	Scanner input = new Scanner(System.in);

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void jobSessionTimeout() {
		System.out.print("input token: ");
		String token = input.nextLine();
		
		String ret = GetURLUtil.getService().path("rest").path("Job")
				.path(token).path("jobListRequest")
				.accept(MediaType.TEXT_PLAIN).get(String.class);
		
		System.out.println(ret);

	}

}
