/**
 * Project Name: zbusdemo
 * File Name: ServicePerf.java
 * Package Name: cn.gnux.zbus.mq.diskq
 * Date: 2015年12月31日下午3:07:40
 * Copyright (c) 2015, kelvin@gnux.cn All Rights Reserved.
 *
*/

package cn.gnux.zbus.mq.diskq;

import java.util.concurrent.atomic.AtomicLong;

import org.zbus.broker.Broker;
import org.zbus.broker.BrokerConfig;
import org.zbus.broker.SingleBroker;
import org.zbus.net.http.Message;
import org.zbus.net.http.Message.MessageInvoker;
import org.zbus.rpc.mq.MqInvoker;

/**
 * ClassName:ServicePerf <br/>
 * Date:     2015年12月31日 下午3:07:40 <br/>
 * @author   lenovo
 * @version  
 * @since    JDK 1.7
 * @see      
 */
public class ServicePerf {
	public static void main(String[] args) throws Exception {
		BrokerConfig config = new BrokerConfig();
		config.setMaxTotal(200);
		config.setMaxIdle(200);
		
		final Broker broker = new SingleBroker(config);
		final int loopCount = 10000;
		final int threadCount = 100;
		
		AtomicLong counter = new AtomicLong(0);
		final long start = System.currentTimeMillis();
		MyTask[] tasks = new MyTask[threadCount];
		for(int i=0;i<tasks.length;i++){
			tasks[i] = new MyTask();
			tasks[i].broker = broker;
			tasks[i].loopCount = loopCount;
			tasks[i].startTime = start;
			tasks[i].counter = counter;
		}
		
	}

}

class MyTask extends Thread {
	Broker broker;
	int loopCount = 10000;
	String service = "MyService";
	long startTime;
	AtomicLong counter;
	@Override
	public void run() { 
		MessageInvoker p = new MqInvoker(broker, service);
		for(int i=0;i<loopCount;i++){ 
			try {
				Message msg = new Message();
				msg.setBody("hello");
				p.invokeSync(msg, 2500); 
				long count = counter.incrementAndGet();
				if(count%2000 == 0){
					long end = System.currentTimeMillis();
					System.out.format("QPS: %.2f\n", count*1000.0/(end-startTime));
				}
				
			} catch (Exception e) { 
				e.printStackTrace();
			}
			
		}
	}
	
}
