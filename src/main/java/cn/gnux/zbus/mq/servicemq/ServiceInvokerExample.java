/**
 * Project Name: zbusdemo
 * File Name: ServiceInvokerExample.java
 * Package Name: cn.gnux.zbus.mq.servicemq
 * Date: 2015年12月31日下午3:34:20
 * Copyright (c) 2015, kelvin@gnux.cn All Rights Reserved.
 *
*/

package cn.gnux.zbus.mq.servicemq;

import org.zbus.broker.Broker;
import org.zbus.broker.BrokerConfig;
import org.zbus.broker.SingleBroker;
import org.zbus.net.http.Message;
import org.zbus.net.http.Message.MessageInvoker;
import org.zbus.rpc.mq.MqInvoker;

/**
 * 服務調用例子
 * ClassName:ServiceInvokerExample <br/>
 * Date:     2015年12月31日 下午3:34:20 <br/>
 * @author   lenovo
 * @version  
 * @since    JDK 1.7
 * @see      
 */
public class ServiceInvokerExample {
	public static void main(String[] args) throws Exception {
		//配置Broker
		BrokerConfig brokerConfig = new BrokerConfig();
		brokerConfig.setServerAddress("127.0.0.1:15555");
		Broker broker = new SingleBroker(brokerConfig);
		
		//基於MQ的調用
		MessageInvoker invoker = new MqInvoker(broker, "MyService");
		
		Message req = new Message();
		req.setBody("hello, world");
		Message res = invoker.invokeSync(req, 2500);
		System.out.println(res);
		
		broker.close();
		
	}
}
