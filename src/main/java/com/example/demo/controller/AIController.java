package com.example.demo.controller;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.BankingAssistant;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final Map<String, BankingAssistant> assistants = new ConcurrentHashMap<>();
    private final OpenAiChatModel chatModel;

    public AIController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chat(
            @RequestBody String message,
            @RequestHeader("Session-Id") String sessionId) {

        BankingAssistant assistant = assistants.computeIfAbsent(sessionId, id ->
            AiServices.builder(BankingAssistant.class)
                .chatLanguageModel(chatModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .build()
        );

        String response = assistant.chat(message);
        return ResponseEntity.ok(response);
    }
}