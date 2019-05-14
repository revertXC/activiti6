package com.revert.xc.chat;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * xiecong
 */
//@ServerEndpoint(value = "/chat")
//@Component
public class MyWebSocket {

    //线程安全 技术器
    private static AtomicInteger count = new AtomicInteger(0);

    //线程安全
    private static ConcurrentMap<String, Session> sessionMap = new ConcurrentHashMap<>(100);

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session) {
        //以原子方式将当前值加1，注意这里返回的是自增前的值
        count.getAndIncrement();
        System.out.println("有新连接加入！当前在线人数为" + count.get());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        count.decrementAndGet();
        System.out.println("有一连接关闭！当前在线人数为" + count.get());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
//        //群发消息
//        for (MyWebSocket item : webSocketSet) {
//            try {
//                item.sendMessage(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

}
