/**
 * Project Name:zbusdemo
 * File Name:ConsumerExample.java
 * Package Name:cn.gnux.zbus.mq
 * Date:2015年12月29日下午5:16:19
 * Copyright (c) 2015, kelvin@gnux.cn All Rights Reserved.
 *
*/

package cn.gnux.zbus.mq;

import org.zbus.net.core.Dispatcher;
import org.zbus.net.http.Message;
import org.zbus.net.http.MessageClient;

/**
 * ClassName:ConsumerExample <br/>
 * Date:     2015年12月29日 下午5:16:19 <br/>
 * @author   kelvin
 * @version  
 * @since    JDK 1.7
 * @see      
 */
public class ConsumerExample {
	public static void main(String[] args) throws Exception{
		Dispatcher dispatcher = new Dispatcher();
		MessageClient client = new MessageClient("127.0.0.1:80", dispatcher);
		Message req = new Message();
		req.setCmd("hello");
		req.setBody("hello");
		
		for(int i=0;i<100000;i++){
			Message res = client.invokeSync(req);
			System.out.println(">>> "+i+"\n"+res);
		}
		client.close();
		dispatcher.close();
	}
}
