package com.coderman.bizedu.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：zhangyukang
 * @date ：2023/10/17 15:21
 */
public class WebSocketServerSupport implements IWebSocketServer {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketServerSupport.class);
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static final ConcurrentHashMap<String, WebSocketServerSupport> WEB_SOCKET_MAP = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收userId
     */
    private String userId;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        if (WEB_SOCKET_MAP.containsKey(userId)) {
            WEB_SOCKET_MAP.remove(userId);
            WEB_SOCKET_MAP.put(userId, this);
        } else {
            WEB_SOCKET_MAP.put(userId, this);
            addOnlineCount();
        }
        LOG.info("用户连接:" + userId + ",当前在线人数为:" + getOnlineCount());
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            LOG.error("用户:" + userId + ",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (WEB_SOCKET_MAP.containsKey(userId)) {
            WEB_SOCKET_MAP.remove(userId);
            //从set中删除
            subOnlineCount();
        }
        LOG.info("用户退出:" + userId + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        LOG.info("用户消息:" + userId + ",报文:" + message);
        //可以群发消息
        //消息保存到数据库、redis
        if (StringUtils.isNotBlank(message)) {
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                //追加发送人(防止串改)
                jsonObject.put("fromUserId", this.userId);
                String toUserId = jsonObject.getString("toUserId");
                //传送给对应toUserId用户的websocket
                if (StringUtils.isNotBlank(toUserId) && WEB_SOCKET_MAP.containsKey(toUserId)) {
                    WEB_SOCKET_MAP.get(toUserId).sendMessage(jsonObject.toJSONString());
                } else {

                    LOG.error("请求的userId:" + toUserId + "不在该服务器上");

                    // TODO 广播发送到主题
                }
            } catch (Exception e) {
                LOG.error("消息解析错误 error:{}", e.getMessage() , e);
            }
        }
    }

    /**
     * 用户异常调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        LOG.error("用户错误:" + this.userId + ",原因:" + error.getMessage(), error);
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     */
    public static void sendInfo(String message, @PathParam("userId") String userId) throws IOException {
        LOG.info("发送消息到:" + userId + "，报文:" + message);
        if (StringUtils.isNotBlank(userId) && WEB_SOCKET_MAP.containsKey(userId)) {
            WEB_SOCKET_MAP.get(userId).sendMessage(message);
        } else {
            LOG.error("用户" + userId + ",不在线！");
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServerSupport.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServerSupport.onlineCount--;
    }
}
