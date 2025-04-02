package com.coderman.admin.config;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.segment.TextSegment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：zhangyukang
 * @date ：2025/04/02 18:32
 */
public class RagDocumentSplitter implements DocumentSplitter {
    @Override
    public List<TextSegment> split(Document document) {
        List<TextSegment> segments = new ArrayList<>();
        String[] parts = document.text().split("\\s*\\R\\s*\\R\\s*");
        for (String part : parts) {
            segments.add(TextSegment.from(part));
        }
        return segments;
    }
}
