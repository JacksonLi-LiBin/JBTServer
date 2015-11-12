package com.matrix.jbt.test;

import java.util.concurrent.*;

import com.matrix.jbt.tool.GetDataFromWeb;

public class ThreadReturnValue {
	public static void main(String[] args) {
		String url1 = "http://webapp.johnbryce.co.il/mobileapp/Service/MobileService.svc/getjobs";
		String url2 = "http://webapp.johnbryce.co.il/mobileapp/Service/MobileService.svc/getgrades";
		try {
			// create a thread pool
			ExecutorService pool = Executors.newFixedThreadPool(2);
			// create two tasks with returned value
			MyCallable c1 = new MyCallable(url1);
			MyCallable c2 = new MyCallable(url2);
			// execute task an return object Future
			Future<?> f1 = pool.submit(c1);
			Future<?> f2 = pool.submit(c2);

			// shut down pool
			pool.shutdown();

			while (true) {
				if (pool.isTerminated()) {
					// get returned value from object Future
					System.out.println(f1.get().toString());
					System.out.println(f2.get().toString());
					break;
				}
				TimeUnit.MILLISECONDS.sleep(100);
			}

			System.out.println("------------------------------");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class MyCallable implements Callable<String> {
	private String url;

	public MyCallable(String url) {
		super();
		this.url = url;
	}

	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		String result = GetDataFromWeb.doHttpGet(url, 1);
		return result;
	}

}