package com.imooc.zkjavaapi;

import com.imooc.zkjavaapi.callback.DeleteCallBack;
import java.io.IOException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 描述： 演示对节点的操作，包含创建、读取、删除等。
 */
public class ZKOperator implements Watcher {

	public static final String SERVER_PATH = "192.168.174.129:2181";

	public static final Integer TIMEOUT = 5000;

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		/**
		 * 客户端和服务端他们是异步连接，连接成功之后，客户端会收到watcher通知。
		 * connectString：服务器的IP+端口号，比如127.0.0.1:2181 sessionTimeout：超时时间 watcher：通知事件
		 */
		ZooKeeper zk = new ZooKeeper(SERVER_PATH, TIMEOUT, new ZKOperator());
		System.out.println("客户端开始连接ZK服务器了");
		System.out.println(zk.getState());
		Thread.sleep(2000);

		/**
		 * path:创建的路径 data：存储的数据 acl：权限，开放 createMode：永久、临时、顺序。
		 */
//		zk.create("/imooc-create-node", "imooc".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		// 注意version的版本
//		zk.setData("/imooc-create-node", "imoocdddd".getBytes(), 3);
		byte[] data = zk.getData("/imooc-create-node", null, null);
		String ctx = "删除节点数据了";
		zk.delete("/imooc-create-node", 4, new DeleteCallBack(), ctx);
		Thread.sleep(2000);
		System.out.println(new String(data));
	}

	@Override
	public void process(WatchedEvent event) {
	
	}
}
