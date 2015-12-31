/**
 * Project Name: zbusdemo
 * File Name: ProducerAsync.java
 * Package Name: cn.gnux.zbus.mq
 * Date: 2015年12月31日下午3:41:38
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
 * 異步生產消息
 * ClassName:ProducerAsync <br/>
 * Date: 2015年12月31日 下午3:41:38 <br/>
 * 
 * @author lenovo
 * @version
 * @since JDK 1.7
 * @see
 */
public class ProducerAsync {
	public static void main(String[] args) throws Exception {
		// 創建Broker代理
		BrokerConfig config = new BrokerConfig();
		config.setServerAddress("127.0.0.1:15555");
		final Broker broker = new SingleBroker(config);

		// 生產者
		Producer producer = new Producer(broker, "MyMQ");
		producer.createMQ(); // 如果已經確定存在，不需要創建

		// 創建消息，消息體可以是任意的binary，引用協議交給使用者
		for (int i = 0; i < 100000; i++) {
			Message msg = new Message();
			producer.sendAsync(msg, new ResultCallback<Message>() {

				public void onReturn(Message result) {
					// TODO Auto-generated method stub
					System.out.println(result);
				}

			});
		}
		System.out.println("---done---");
		// 销毁Broker, 异步发送需要Sleep等待
		// broker.close();
	}

}
