/**
 * Project Name: zbusdemo
 * File Name: Producer.java
 * Package Name: cn.gnux.zbus.mq
 * Date: 2015年12月29日下午6:00:38
 * Copyright (c) 2015, kelvin@gnux.cn All Rights Reserved.
 *
*/

package cn.gnux.zbus.mq;

import org.zbus.broker.Broker;
import org.zbus.broker.BrokerConfig;
import org.zbus.broker.SingleBroker;
import org.zbus.mq.Producer;
import org.zbus.net.Sync.ResultCallback;
import org.zbus.net.http.Message;

/**
 * ClassName:Producer <br/>
 * Date:     2015年12月29日 下午6:00:38 <br/>
 * @author   kelvin
 * @version  
 * @since    JDK 1.7
 * @see      
 */
public class ProducerSyncDemo {
	public static void main(String[] args) throws Exception {
		//创建Broker代理
		BrokerConfig config = new BrokerConfig();
		config.setServerAddress("127.0.0.1:15555");
		final Broker broker = new SingleBroker(config);
		Producer producer = new Producer(broker, "MyMQ");
		producer.createMQ();// 如果已经确定存在，不需要创建
		
		//创建消息，消息体可以是任意binary，应用协议交给使用者
		for(int i=0; i< 100000;i++) {
			Message msg = new Message();
			msg.setBody("hello world"+i);
			producer.sendAsync(msg,new ResultCallback<Message>() {

				public void onReturn(Message result) {
					// TODO Auto-generated method stub
					System.out.println(result);
				}
			});
		}
		
		System.out.println("---done---");
		//销毁Broker, 异步发送需要Sleep等待
		//broker.close();
	}
}
