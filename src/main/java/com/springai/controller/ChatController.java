package com.springai.controller;

import com.springai.dto.ApiResponse;
import com.springai.dto.ChatRequest;
import com.springai.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/query")
    public ResponseEntity<ApiResponse<?>> sendMessage(@RequestBody ChatRequest request) {
        log.info("Chat API 요청 받음: model={}", request.getModel());

        if (request.getQuery() == null || request.getQuery().isBlank()) {
            log.warn("빈 질의가 요청됨");
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, null, "질의가 비어있습니다.")
            );
        }

        try {
            String systemMessage = "You are a helpful AI assistant.";

            var response = chatService.openAiChat(
                    request.getQuery(),
                    systemMessage,
                    request.getModel()
            );

            if (response != null) {
                log.debug("LLM 응답 생성: {}", Optional.ofNullable(response));

                return ResponseEntity.ok(
                        new ApiResponse<>(
                                true,
                                java.util.Map.of("answer", response.getResult().getOutput().getText()),
                                null
                        )
                );
            } else {
                log.error("LLM 응답 생성 실패");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        new ApiResponse<>(false, null, "LLM 응답 생성 중 오류 발생")
                );
            }

        } catch (Exception e) {
            log.error("Chat API 처리 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(false, null, e.getMessage() != null ? e.getMessage() : "알 수 없는 오류 발생")
            );
        }
    }
}