/**
 * Project Name: zbusdemo
 * File Name: MessageDiskQueuePerf.java
 * Package Name: cn.gnux.zbus.mq.diskq
 * Date: 2015年12月31日下午1:56:09
 * Copyright (c) 2015, kelvin@gnux.cn All Rights Reserved.
 *
*/

package cn.gnux.zbus.mq.diskq;

import java.util.AbstractQueue;

import org.zbus.mq.server.support.DiskQueuePool;
import org.zbus.mq.server.support.MessageDiskQueue;
import org.zbus.net.http.Message;

/**
 * 消息磁盤隊列性能測試
 * ClassName:MessageDiskQueuePerf <br/>
 * Date:     2015年12月31日 下午1:56:09 <br/>
 * @author   lenovo
 * @version  
 * @since    JDK 1.7
 * @see      
 */
public class MessageDiskQueuePerf {
	public static void main(String[] args) {
		String path = "mq";
		DiskQueuePool.init(path);
		
		AbstractQueue<Message> q = null;
		q = new MessageDiskQueue("test");
		
		long start = System.currentTimeMillis();
		final int N = 10000000;
		int count = 10;
		byte[] data = new byte[count];
		Message msg = new Message();
		msg.setBody(data);
		for(int i=0;i<N;i++) {
			q.offer(msg);
		}
		
		/**
		 * result:
		 * QPS: 238578.07
		 * MPS: 2.28M/s
		 */
		long end = System.currentTimeMillis();
		System.out.format("QPS: %.2f\n", N*1000.0/(end-start));
		System.out.format("MPS: %.2fM/s\n", count*N*1000.0/(end-start)/1024/1024);
	}
}
