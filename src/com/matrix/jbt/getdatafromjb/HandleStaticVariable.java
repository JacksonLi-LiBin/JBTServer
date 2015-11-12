package com.matrix.jbt.getdatafromjb;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.matrix.jbt.servlet.GetDataFromJBServlet;

public class HandleStaticVariable implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub

		System.out.println("recovery data.......");
		// GetDataFromJBServlet.currentTime = "firstTime";
		GetDataFromJBServlet.executeCount = 0;
		// GetDataFromJBServlet.isExecuting = 0;
	}
}
