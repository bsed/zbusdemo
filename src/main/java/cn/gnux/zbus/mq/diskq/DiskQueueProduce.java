/**
 * Project Name: zbusdemo
 * File Name: DiskQueueProduce.java
 * Package Name: cn.gnux.zbus.mq.diskq
 * Date: 2015年12月30日下午5:28:06
 * Copyright (c) 2015, kelvin@gnux.cn All Rights Reserved.
 *
*/

package cn.gnux.zbus.mq.diskq;

import org.zbus.mq.server.support.DiskQueuePool;
import org.zbus.mq.server.support.DiskQueuePool.DiskQueue;

/**
 * 磁盤隊列生產者
 * ClassName:DiskQueueProduce <br/>
 * Date:     2015年12月30日 下午5:28:06 <br/>
 * @author   lenovo
 * @version  
 * @since    JDK 1.7
 * @see      
 */
public class DiskQueueProduce {
	public static void main(String[] args) {
		String path = "mq";
		DiskQueuePool.init(path);
		DiskQueue q = DiskQueuePool.getDiskQueue("test");
		
		long start = System.currentTimeMillis();
		final int N = 10000000;
		
		int count = 100; 
		for(int i=0;i<N;i++){   
			q.offer(new byte[count]);
		}
		
		long end = System.currentTimeMillis();
		System.out.format("QPS: %.2f\n", N*1000.0/(end-start));
		System.out.format("MPS: %.2fM/s\n", count*N*1000.0/(end-start)/1024/1024);
		
		DiskQueuePool.destory(); 
		
	}
}
