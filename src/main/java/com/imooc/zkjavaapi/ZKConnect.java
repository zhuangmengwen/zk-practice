package com.imooc.zkjavaapi;

import java.io.IOException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * 描述： 连接到ZK服务端，打印连接状态
 */
public class ZKConnect implements Watcher {

	public static final String SERVER_PATH = "192.168.10.132:2181";

	public static final Integer TIMEOUT = 5000;

	public static void main(String[] args) throws IOException, InterruptedException {
		/**
		 * 客户端和服务端他们是异步连接，连接成功之后，客户端会收到watcher通知。
		 * connectString：服务器的IP+端口号，比如127.0.0.1:2181 sessionTimeout：超时时间 watcher：通知事件
		 */
		ZooKeeper zk = new ZooKeeper(SERVER_PATH, TIMEOUT, new ZKConnect());
		System.out.println("客户端开始连接ZK服务器了");
		System.out.println(zk.getState());
		Thread.sleep(2000);
		System.out.println("客户端彻底连接ZK服务器了");
		System.out.println(zk.getState());
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println("收到了通知" + event);
	}
}
