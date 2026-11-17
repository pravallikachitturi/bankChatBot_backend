package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.service.BankingAssistant;
import com.example.demo.service.BankingTools;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

@Configuration
public class AiConfig {

    private final BankingTools bankingTools;

    public AiConfig(BankingTools bankingTools) {
        this.bankingTools = bankingTools;
    }

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.withMaxMessages(10);
    }

    @Bean
    public BankingAssistant bankingAssistant(OpenAiChatModel chatModel,
                                              ChatMemory chatMemory) {
        return AiServices.builder(BankingAssistant.class)
            .chatLanguageModel(chatModel)
            .chatMemory(chatMemory)
            .tools(bankingTools)
            .build();
    }
}