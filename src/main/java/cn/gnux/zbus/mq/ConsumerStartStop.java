/**
 * Project Name: zbusdemo
 * File Name: ConsumerStartStop.java
 * Package Name: cn.gnux.zbus.mq
 * Date: 2015年12月31日下午4:42:15
 * Copyright (c) 2015, kelvin@gnux.cn All Rights Reserved.
 *
*/

package cn.gnux.zbus.mq;

import java.io.IOException;

import org.zbus.broker.Broker;
import org.zbus.broker.BrokerConfig;
import org.zbus.broker.SingleBroker;
import org.zbus.mq.Consumer;
import org.zbus.mq.MqConfig;
import org.zbus.net.core.Session;
import org.zbus.net.http.Message;
import org.zbus.net.http.Message.MessageHandler;

/**
 * ClassName:ConsumerStartStop <br/>
 * Date:     2015年12月31日 下午4:42:15 <br/>
 * @author   lenovo
 * @version  
 * @since    JDK 1.7
 * @see      
 */
public class ConsumerStartStop {
	public static void main(String[] args) throws Exception {
		//創建Broker代表
		BrokerConfig brokerConfig = new BrokerConfig();
		brokerConfig.setServerAddress("127.0.0.1:15555");
		Broker broker = new SingleBroker(brokerConfig);
		
		MqConfig config = new MqConfig(); 
		config.setBroker(broker);
		config.setMq("MyMQ");
		
		//創建消費者
		@SuppressWarnings("resource")
		Consumer c = new Consumer(config);
		c.onMessage(new MessageHandler() {

			public void handle(Message msg, Session sess) throws IOException {
				// TODO Auto-generated method stub
				System.out.println(msg);
			}
			
		});
		
		while(true) {
			c.start();
			Thread.sleep(4000);
			c.stop();
			Thread.sleep(4000);
		}
	}
}
