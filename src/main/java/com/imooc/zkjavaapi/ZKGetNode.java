package com.imooc.zkjavaapi;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 描述： 和节点相关：是否存在，获取数据，加上watch
 */
public class ZKGetNode implements Watcher {

	public static final String SERVER_PATH = "192.168.10.130:2181";

	public static final Integer TIMEOUT = 5000;

	private static CountDownLatch countDownLatch = new CountDownLatch(1);

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		/**
		 * 客户端和服务端他们是异步连接，连接成功之后，客户端会收到watcher通知。
		 * connectString：服务器的IP+端口号，比如127.0.0.1:2181 sessionTimeout：超时时间 watcher：通知事件
		 */
		ZooKeeper zk = new ZooKeeper(SERVER_PATH, TIMEOUT, new ZKGetNode());
		System.out.println("客户端开始连接ZK服务器了");
		System.out.println(zk.getState());
		Thread.sleep(2000);
		System.out.println(zk.getState());

//        Stat exists = zk.exists("/java", false);
//        if (exists != null) {
//            System.out.println("节点的版本为：" + exists.getVersion());
//        }else {
//            System.out.println("该节点不存在");
//        }
		zk.getData("/java", true, null);
		countDownLatch.await();
	}

	@Override
	public void process(WatchedEvent event) {
		if (event.getType() == EventType.NodeDataChanged) {
			System.out.println("数据被改变");
			countDownLatch.countDown();
		}
		System.out.println("收到了通知" + event);
	}
}
