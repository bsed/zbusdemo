/**
 * Project Name: zbusdemo
 * File Name: QueryMqExample.java
 * Package Name: cn.gnux.zbus.mq
 * Date: 2015年12月31日下午4:52:25
 * Copyright (c) 2015, kelvin@gnux.cn All Rights Reserved.
 *
*/

package cn.gnux.zbus.mq;

import org.zbus.broker.Broker;
import org.zbus.broker.BrokerConfig;
import org.zbus.broker.SingleBroker;
import org.zbus.mq.MqAdmin;
import org.zbus.net.http.Message;

/**
 * 查詢MQ的例子
 * ClassName:QueryMqExample <br/>
 * Date:     2015年12月31日 下午4:52:25 <br/>
 * @author   lenovo
 * @version  
 * @since    JDK 1.7
 * @see      
 */
public class QueryMqExample {
	public static void main(String[] args)throws Exception {
		//創建Broker代理
		BrokerConfig config = new BrokerConfig();
		config.setServerAddress("127.0.0.1:15555");
		final Broker broker = new SingleBroker(config);
		
		MqAdmin admin = new MqAdmin(broker,"MyMQ");
		Message res = admin.queryMQ();//保持zbus底层不依赖json包，使用者再解释json
		System.out.println(res);
		broker.close();
	}
}
