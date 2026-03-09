package pharmacy_webapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pharmacy_webapp.service.ChatbotService;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatbotController {

    private final ChatbotService chatbotService;

    @PostMapping
    public ResponseEntity<?> chat(@RequestHeader(value = "X-Session-Id", defaultValue = "default") String sessionId,
                                  @RequestParam String message) {
        try {
            if (message == null || message.isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Message not null"));
            }
            String reply = chatbotService.chat(sessionId, message);
            return ResponseEntity.ok(Map.of("reply", reply));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/history")
    public ResponseEntity<?> clearHistory(
            @RequestHeader(value = "X-Session-Id", defaultValue = "default") String sessionId) {
        chatbotService.clearHistory(sessionId);
        return ResponseEntity.ok(Map.of("message", "Deleted history message"));
    }
}