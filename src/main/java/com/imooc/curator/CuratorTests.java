package com.imooc.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;

/**
 * 描述：     用Curator来操作ZK
 */
public class CuratorTests {

    public static void main(String[] args) throws Exception {
        String connectString = "127.0.0.1:2181";
        String path = "/curator2";

        RetryPolicy retry = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retry);
        client.start();
        client.getCuratorListenable().addListener((CuratorFramework c, CuratorEvent event) -> {
            switch (event.getType()) {
                case WATCHED:
                    WatchedEvent watchedEvent = event.getWatchedEvent();
                    if (watchedEvent.getType() == EventType.NodeDataChanged) {
                        System.out.println(new String(c.getData().forPath(path)));
                    }
            }
        });
        String data = "test";
        String data2 = "test2";
        client.create().withMode(CreateMode.PERSISTENT).forPath(path, data.getBytes());

        byte[] bytes = client.getData().watched().forPath(path);
        System.out.println(new String(bytes));
        client.setData().forPath(path, data2.getBytes());
        client.delete().forPath(path);
        Thread.sleep(2000);
    }
}
