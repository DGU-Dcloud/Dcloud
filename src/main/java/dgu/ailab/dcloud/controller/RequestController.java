package dgu.ailab.dcloud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@CrossOrigin
public class RequestController {

    private final String SLACK_WEBHOOK_URL = "https://hooks.slack.com/services/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";

    @PostMapping("/api/request")
    public void handlePostRequest(@RequestBody String requestBody) {
        // Process the incoming request as needed

        // Send message to Slack
        sendMessageToSlack(requestBody);
    }

    private void sendMessageToSlack(String message) {
        RestTemplate restTemplate = new RestTemplate();

        // Create JSON payload using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = null;
        try {
            payload = objectMapper.writeValueAsString(Map.of("text", message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // Handle JSON processing exception
        }

        restTemplate.postForEntity(SLACK_WEBHOOK_URL, payload, String.class);
    }
}
