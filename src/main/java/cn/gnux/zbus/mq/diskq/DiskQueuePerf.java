/**
 * Project Name: zbusdemo
 * File Name: DiskQueuePerf.java
 * Package Name: cn.gnux.zbus.mq.diskq
 * Date: 2015年12月31日上午9:12:41
 * Copyright (c) 2015, kelvin@gnux.cn All Rights Reserved.
 *
*/

package cn.gnux.zbus.mq.diskq;

import java.util.concurrent.atomic.AtomicLong;

import org.zbus.mq.server.support.DiskQueuePool;
import org.zbus.mq.server.support.DiskQueuePool.DiskQueue;

/**
 * ClassName:DiskQueuePerf <br/>
 * Date:     2015年12月31日 上午9:12:41 <br/>
 * @author   lenovo
 * @version  
 * @since    JDK 1.7
 * @see      
 */
public class DiskQueuePerf {
	static class Task extends Thread {
		int loopCount = 10000;
		long startTime;
		AtomicLong counter;
		public void run() {
			DiskQueue q = DiskQueuePool.getDiskQueue("test");
			for(int i=0;i<loopCount;i++) {
				try {
					q.offer(new byte[1024]);
					long count = counter.incrementAndGet();
					if(count%20000==0) {
						long end = System.currentTimeMillis();
						System.out.format("QPS: %.2f\n", count*1000.0/(end-startTime));
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		DiskQueuePool.init("C:\\MFQ");
		final int loopCount = 10000000; 
		final int threadCount = 16;
		AtomicLong counter = new AtomicLong(0);
		final long start = System.currentTimeMillis();
		Task[] tasks = new Task[threadCount];
		for(int i=0;i<tasks.length;i++) {
			tasks[i] = new Task();
			tasks[i].loopCount = loopCount;
			tasks[i].startTime = start;
			tasks[i].counter = counter;
		}
		
		for(Task task :tasks) {
			task.start();
		}

		DiskQueuePool.destory();
		System.out.println("===done==="); 
	}
}
