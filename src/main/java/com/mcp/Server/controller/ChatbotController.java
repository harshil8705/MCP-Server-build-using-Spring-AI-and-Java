package com.mcp.Server.controller;

import com.mcp.Server.service.ChatbotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam(value = "question") String question) {

        return new ResponseEntity<>(chatbotService.chat(question), HttpStatus.OK);

    }

}
