/**
 * Project Name: zbusdemo
 * File Name: ServiceExample.java
 * Package Name: cn.gnux.zbus.mq.servicemq
 * Date: 2015年12月31日下午3:23:23
 * Copyright (c) 2015, kelvin@gnux.cn All Rights Reserved.
 *
*/

package cn.gnux.zbus.mq.servicemq;

import java.io.IOException;

import org.zbus.broker.Broker;
import org.zbus.broker.BrokerConfig;
import org.zbus.broker.SingleBroker;
import org.zbus.net.http.Message;
import org.zbus.net.http.Message.MessageProcessor;
import org.zbus.rpc.mq.Service;
import org.zbus.rpc.mq.ServiceConfig;



/**
 * ClassName:ServiceExample <br/>
 * Date:     2015年12月31日 下午3:23:23 <br/>
 * @author   lenovo
 * @version  
 * @since    JDK 1.7
 * @see      
 */
public class ServiceExample {
	public static void main(String[] args) throws IOException,Exception {
		//配置Broker
		BrokerConfig brokerConfig = new BrokerConfig();
		brokerConfig.setServerAddress("127.0.0.1:15555");
		Broker broker = new SingleBroker(brokerConfig);
		
		ServiceConfig config = new ServiceConfig();
		config.setMq("MyService");
		config.setBroker(broker);
		config.setConsumerCount(2);
		
		//處理邏輯
		config.setMessageProcessor(new MessageProcessor(){

			public Message process(Message request) {
				Message result = new Message();
				result.setResponseStatus("200");
				result.setBody("Server time: "+System.currentTimeMillis());	
				
				return result;
			}
			
		});
		
		//启动服务
		Service serivce = new Service(config);
		serivce.start();
	}
}
