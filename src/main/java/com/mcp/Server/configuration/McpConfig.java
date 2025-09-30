package com.mcp.Server.configuration;

import com.mcp.Server.service.CourseService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class McpConfig {

    private final CourseService courseService;

    public McpConfig(CourseService courseService) {
        this.courseService = courseService;
    }

    @Bean
    public List<ToolCallback> toolCallbacks() {
        ToolCallback[] toolCallbacks = ToolCallbacks.from(courseService);
        return Arrays.stream(toolCallbacks).toList();
    }

    @Bean
    public ChatClient chatClient(ChatModel chatModel, List<ToolCallback> toolCallbacks) {
        return ChatClient.builder(chatModel)
                .defaultToolCallbacks(toolCallbacks)
                .build();
    }

}
