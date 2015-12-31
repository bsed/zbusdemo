/**
 * Project Name: zbusdemo
 * File Name: HttpClientConsumer.java
 * Package Name: cn.gnux.zbus.httpclient
 * Date: 2015年12月29日下午5:27:15
 * Copyright (c) 2015, kelvin@gnux.cn All Rights Reserved.
 *
*/

package cn.gnux.zbus.httpclient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.zbus.mq.Protocol;
import org.zbus.net.http.Message;

/**
 * ClassName:HttpClientConsumer <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2015年12月29日 下午5:27:15 <br/>
 * @author   kelvin
 * @version 0.1
 * @since    JDK 1.7
 * @see      
 */
public class HttpClientConsumer {
	public static void main(String[] args) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost http = new HttpPost("http://localhost:15555/"); 
		
		//zbus扩展HTTP头部协议，主要两个MQ和命令CMD
		http.addHeader(Message.CMD,Protocol.Consume);
		http.addHeader(Message.MQ,"MyMQ");
		
		CloseableHttpResponse resp = httpClient.execute(http);
		HttpKit.printResponse(resp);
		resp.close();
		
		httpClient.close();
	}
}
