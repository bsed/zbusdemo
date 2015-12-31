/**
 * Project Name: zbusdemo
 * File Name: BlockingQueuePerf.java
 * Package Name: cn.gnux.zbus.mq.diskq
 * Date: 2015年12月30日下午3:40:52
 * Copyright (c) 2015, kelvin@gnux.cn All Rights Reserved.
 *
*/

package cn.gnux.zbus.mq.diskq;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 阻塞隊列性能
 * ClassName:BlockingQueuePerf <br/>
 * Date:     2015年12月30日 下午3:40:52 <br/>
 * @author   lenovo
 * @version  
 * @since    JDK 1.7
 * @see      
 */
public class BlockingQueuePerf {
	static class Task extends Thread {
		int loopCount = 10000; //循環次數
		long startTime; //啟動時間
		AtomicLong counter; //原子計數
		BlockingQueue<byte[]> q; //阻塞隊列
		
		public void run() {
			for(int i=0;i<loopCount;i++) {
				try {
					q.offer(new byte[10]);
					long count = counter.incrementAndGet();
					if(count%20000 ==0) {
						long end = System.currentTimeMillis();
						System.out.format("QPS: %.2f\n", count*1000.0/(end-startTime));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		BlockingQueue<byte[]> q = new LinkedBlockingDeque<byte[]>();//阻塞栈, 后入先出
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
			tasks[i].q = q;
		}
		for(Task task: tasks) {
			task.start();
		}
		for(Task task:tasks) {
			task.join();
		}
		System.out.println("===done===");
	}
}
