package com.imooc.zkjavaapi.callback;

import org.apache.zookeeper.AsyncCallback.VoidCallback;

/**
 * 描述：     删除后运行的方法
 */
public class DeleteCallBack implements VoidCallback {

    @Override
    public void processResult(int rc, String path, Object ctx) {
        System.out.println("删除节点" + path);
        System.out.println((String)ctx);
    }
}
