package com.coderman.admin.config;

import com.coderman.admin.service.common.Assistant;
import com.coderman.api.constant.CommonConstant;
import com.coderman.service.util.DesUtil;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.dashscope.QwenEmbeddingModel;
import dev.langchain4j.model.dashscope.QwenStreamingChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：zhangyukang
 * @date ：2025/04/02 11:12
 */
@Configuration
@Slf4j
public class ChatAiConfigure {

    @Resource
    private RedisProperties redisProperties;

    @Bean
    public Assistant assistant() {
        EmbeddingModel embeddingModel =  QwenEmbeddingModel.builder()
                .apiKey(this.getApiKey())
                .build();
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                // 最相似的3个结果
                .maxResults(3)
                // 只找相似度在0.8以上的内容
                .minScore(0.8)
                .build();

        // 初始化知识库
        this.loadEmbedding(embeddingModel, embeddingStore);

        // 自定义存储方式
        RedisChatMemoryStore redisChatMemoryStore = RedisChatMemoryStore.builder()
                .port(redisProperties.getPort())
                .password(redisProperties.getPassword())
                .host(redisProperties.getHost())
                .build();
        // 构建模型
        StreamingChatLanguageModel model = QwenStreamingChatModel.builder()
                .apiKey(this.getApiKey())
                .modelName("qwen-max")
                .maxTokens(1024)
                .enableSearch(true)
                .build();
        // 构建服务
        return AiServices.builder(Assistant.class)
                .streamingChatLanguageModel(model)
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(10)
                        .chatMemoryStore(redisChatMemoryStore)
                        .build())
                .contentRetriever(contentRetriever)
                .build();
    }

    /**
     * 初始化RAG向量
     * @param embeddingModel 模型
     * @param embeddingStore 向量存储
     */
    private void loadEmbedding(EmbeddingModel embeddingModel, InMemoryEmbeddingStore<TextSegment> embeddingStore) {
        try {
            Document document = FileSystemDocumentLoader.loadDocument("/opt/rag.txt", new TextDocumentParser());
            List<TextSegment> textSegments = new RagDocumentSplitter().split(document);
            Response<List<Embedding>> response = embeddingModel.embedAll(textSegments);
            List<Embedding> embeddings = response.content();
            embeddingStore.addAll(embeddings, textSegments);
        } catch (Exception e) {
            log.error("初始化知识库失败:{}", e.getMessage(), e);
        }
    }

    private String getApiKey() {
        try {
            final String API_KEY_ENCRYPTED = "345D37C0A831EA3BF82562BE4758A70285A77A4AB8E0685299C5246B3DD67F3F74843A274F180E0C";
            return DesUtil.decrypt(API_KEY_ENCRYPTED, CommonConstant.SECRET_KEY);
        } catch (Exception e) {
            log.error("API Key 解密失败", e);
            throw new RuntimeException("无法解密 API Key");
        }
    }
}
