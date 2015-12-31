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
		
	}
}
