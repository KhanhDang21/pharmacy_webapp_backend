package pharmacy_webapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.stereotype.Service;
import pharmacy_webapp.config.GeminiConfig;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ChatbotService {

    private final GeminiConfig config;
    private final ObjectMapper mapper = new ObjectMapper();
    private final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    private final Map<String, List<Map<String, Object>>> sessionHistories = new ConcurrentHashMap<>();
    private static final int MAX_HISTORY_TURNS = 10;

    public String chat(String sessionId, String message) throws Exception {
        String url = config.getApiUrl() + config.getModel() + ":generateContent?key=" + config.getApiKey();

        List<Map<String, Object>> history = sessionHistories.computeIfAbsent(sessionId, k -> new ArrayList<>());

        history.add(Map.of(
                "role", "user",
                "parts", List.of(Map.of("text", message))
        ));

        trimHistory(history);

        String body = mapper.writeValueAsString(Map.of(
                "system_instruction", Map.of(
                        "parts", List.of(Map.of("text", config.getSystemPrompt()))
                ),
                "contents", history,
                "generationConfig", Map.of(
                        "maxOutputTokens", config.getMaxTokens(),
                        "temperature", config.getTemperature()
                )
        ));

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(body, MediaType.parse("application/json")))
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();

            if (!response.isSuccessful()) {
                history.remove(history.size() - 1);
                throw new RuntimeException("Gemini API Error: " + response.code() + " - " + responseBody);
            }

            JsonNode json = mapper.readTree(responseBody);
            String reply = json.path("candidates").get(0)
                    .path("content")
                    .path("parts").get(0)
                    .path("text").asText();

            history.add(Map.of(
                    "role", "model",
                    "parts", List.of(Map.of("text", reply))
            ));

            return reply;
        }
    }

    private void trimHistory(List<Map<String, Object>> history) {
        int maxMessages = MAX_HISTORY_TURNS * 2;
        while (history.size() > maxMessages) {
            history.remove(0);
        }
    }

    public void clearHistory(String sessionId) {
        sessionHistories.remove(sessionId);
    }

    public String chat(String message) throws Exception {
        return chat("default", message);
    }
}