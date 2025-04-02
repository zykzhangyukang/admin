package com.coderman.admin.config;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.internal.ValidationUtils;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用 Redis 存储聊天记录的 MemoryStore
 */
public class RedisChatMemoryStore implements ChatMemoryStore {

    private final JedisPool jedisPool;
    private final static String KEY_PREFIX = "chat_memory:";

    public RedisChatMemoryStore(String host, Integer port, String password) {
        // 配置 Redis 连接池
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(2);

        // 这里 JedisPool 支持传入密码
        this.jedisPool = new JedisPool(poolConfig, host, port, 2000, password);
    }

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        try (Jedis jedis = jedisPool.getResource()) {
            String json = jedis.get(KEY_PREFIX+ toMemoryIdString(memoryId));
            return json == null ? new ArrayList<>() : ChatMessageDeserializer.messagesFromJson(json);
        }
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        try (Jedis jedis = jedisPool.getResource()) {
            String json = ChatMessageSerializer.messagesToJson(ValidationUtils.ensureNotEmpty(messages, "messages"));
            String res = jedis.set(KEY_PREFIX + toMemoryIdString(memoryId), json);
            if (!"OK".equals(res)) {
                throw new RuntimeException("Redis set memory error: " + res);
            }
        }
    }

    @Override
    public void deleteMessages(Object memoryId) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(KEY_PREFIX + toMemoryIdString(memoryId));
        }
    }

    private static String toMemoryIdString(Object memoryId) {
        if (memoryId == null || memoryId.toString().trim().isEmpty()) {
            throw new IllegalArgumentException("memoryId cannot be null or empty");
        }
        return memoryId.toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder 模式，方便配置 Redis 连接
     */
    public static class Builder {
        private String host = "127.0.0.1";
        private Integer port = 6379;
        private String password = null;

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder port(Integer port) {
            this.port = port;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public RedisChatMemoryStore build() {
            return new RedisChatMemoryStore(host, port, password);
        }
    }
}
