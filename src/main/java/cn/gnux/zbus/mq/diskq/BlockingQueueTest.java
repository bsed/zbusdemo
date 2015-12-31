/**
 * Project Name: zbusdemo
 * File Name: BlockingQueueTest.java
 * Package Name: cn.gnux.zbus.mq.diskq
 * Date: 2015年12月30日下午4:08:00
 * Copyright (c) 2015, kelvin@gnux.cn All Rights Reserved.
 *
*/

package cn.gnux.zbus.mq.diskq;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 阻塞隊列測試
 * ClassName:BlockingQueueTest <br/>
 * Date: 2015年12月30日 下午4:08:00 <br/>
 * 
 * @author lenovo
 * @version
 * @since JDK 1.7
 * @see
 */
public class BlockingQueueTest {
	public static void main(String[] args) {
		BlockingQueue<byte[]> q = new LinkedBlockingQueue<byte[]>(1000);// 阻塞堆棧

		long start = System.currentTimeMillis();
		final int N = 100000000; // 一個億

		for (int i = 0; i < N; i++) {
			q.offer(new byte[10]);
		}
		long end = System.currentTimeMillis();
		System.out.println(N*1000.0/(end-start));
	}
}
