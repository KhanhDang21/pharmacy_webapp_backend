package pharmacy_webapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pharmacy_webapp.dto.ApiResponse;
import pharmacy_webapp.service.ChatbotService;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatbotController {

    private final ChatbotService chatbotService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> chat(@RequestHeader(value = "X-Session-Id", defaultValue = "default") String sessionId,
                                            @RequestParam String message) {
        try {
            if (message == null || message.isBlank()) {
                return ResponseEntity.badRequest().body(
                        ApiResponse.error("Something went wrong")
                );
            }
            String reply = chatbotService.chat(sessionId, message);
            return ResponseEntity.ok(
                    ApiResponse.success(reply, "Chatbot successfully reply")
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    @DeleteMapping("/history")
    public ResponseEntity<ApiResponse<String>> clearHistory(
            @RequestHeader(value = "X-Session-Id", defaultValue = "default") String sessionId) {
        chatbotService.clearHistory(sessionId);
        return ResponseEntity.ok(
                ApiResponse.success("message", "Deleted history message")
        );
    }
}