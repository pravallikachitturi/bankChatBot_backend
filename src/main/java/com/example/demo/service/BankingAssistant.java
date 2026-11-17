package com.example.demo.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;


public interface BankingAssistant {

    @SystemMessage("""
       You are a helpful banking assistant for JavaBank app. You help customers with accounts, transactions, loans and deposits. Remember what the user tells you during the conversation. Be concise and friendly.
        """)
    String chat(@UserMessage String userMessage);
}
