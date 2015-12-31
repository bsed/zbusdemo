/**
 * Project Name: zbusdemo
 * File Name: ProducerPerf.java
 * Package Name: cn.gnux.zbus.mq.diskq
 * Date: 2015年12月31日下午2:06:47
 * Copyright (c) 2015, kelvin@gnux.cn All Rights Reserved.
 *
*/

package cn.gnux.zbus.mq.diskq;

import java.util.concurrent.atomic.AtomicLong;

import org.zbus.broker.Broker;
import org.zbus.broker.BrokerConfig;
import org.zbus.broker.SingleBroker;
import org.zbus.kit.ConfigKit;
import org.zbus.mq.Producer;
import org.zbus.net.core.Dispatcher;
import org.zbus.net.http.Message;

import com.sun.security.ntlm.Client;

/**
 * ClassName:ProducerPerf <br/>
 * Date: 2015年12月31日 下午2:06:47 <br/>
 * 
 * @author lenovo
 * @version
 * @since JDK 1.7
 * @see
 */
public class ProducerPerf {
	static class Task extends Thread {
		private final Producer producer; // 生產者
		private final AtomicLong counter;
		private final long startTime;
		private final long N;

		public Task(Producer producer, AtomicLong counter, long startTime, long N) {
			this.producer = producer;
			this.counter = counter;
			this.startTime = startTime;
			this.N = N;
		}

		public void run() {
			Message msg = new Message();
			msg.setBody("hello");
			// if((i+1)%1000 == 0){
			// try {
			// sleep(50);
			// } catch (InterruptedException e) {
			// }
			// }
			try {
				producer.sendSync(msg, 10000);
				counter.incrementAndGet();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			if (counter.get() % 5000 == 0) {
				double qps = counter.get() * 1000.0 / (System.currentTimeMillis() - startTime);
				System.out.format("QPS: %.2f\n", qps);
			}
		}

	}

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		int selectorCount = ConfigKit.option(args, "-selector", 1);
		int executorCount = ConfigKit.option(args, "-executor", 128);
		final long N = ConfigKit.option(args, "-N", 1000000);
		final int threadCount = ConfigKit.option(args, "-thread", 16);
		final String serverAddress = ConfigKit.option(args, "-s", "127.0.0.1:15555");

		//事件分發器
		Dispatcher dispatcher = new Dispatcher().selectorCount(selectorCount).executorCount(executorCount);
		dispatcher.start();
		
		//中介配置文件
		BrokerConfig config = new BrokerConfig();
		config.setServerAddress(serverAddress);
		config.setDispatcher(dispatcher);
		
		//啟動單個中介
		final Broker broker = new SingleBroker(config);
		final AtomicLong counter = new AtomicLong(0);

		Producer[] clients = new Producer[threadCount];

		for (int i = 0; i < clients.length; i++) {
			clients[i] = new Producer(broker, "MyMQ");
			clients[i].createMQ();
		}
		
		final long startTime = System.currentTimeMillis();
		Task[] tasks = new Task[threadCount];
		for(int i=0;i<threadCount;i++) {
			tasks[i] = new Task(clients[i],counter,startTime,N);
		}
		
		for(Task task:tasks) {
			task.start();
		}
		
		//dispatcher.close();
	}
}
