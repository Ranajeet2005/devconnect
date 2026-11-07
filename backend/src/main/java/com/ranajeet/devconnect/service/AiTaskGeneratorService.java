
package com.ranajeet.devconnect.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
public class AiTaskGeneratorService {

    private final RestClient restClient;

    @Value("${openai.api-key:}")
    private String apiKey;

    @Value("${openai.model:gpt-4.1-mini}")
    private String model;

    public AiTaskGeneratorService(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    public String generateTasks(String description) {

        if (apiKey == null || apiKey.isBlank()) {
            throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "OpenAI API key is not configured."
            );
        }

        String instructions = """
            You are DevConnect AI Task Generator.

            Convert software project descriptions into practical
            development tasks.

            Generate tasks in a clear numbered format.

            For every task include:
            1. Task title
            2. Detailed description
            3. Priority: LOW, MEDIUM, HIGH or CRITICAL
            4. Estimated complexity: EASY, MEDIUM or HARD
            5. Suggested time required
            6. Dependencies, if any

            Organize tasks into development phases:
            - Planning
            - Backend Development
            - Frontend Development
            - Database
            - Testing
            - Deployment

            Make tasks specific, actionable and beginner-friendly.
            Do not claim that any task has already been completed.
            """;

        Map<String, Object> requestBody = Map.of(
            "model", model,
            "instructions", instructions,
            "input", description,
            "max_output_tokens", 2000,
            "store", false
        );

        try {
            JsonNode response = restClient.post()
                .uri("https://api.openai.com/v1/responses")
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(JsonNode.class);

            if (response == null) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Empty response from AI provider."
                );
            }

            StringBuilder result = new StringBuilder();

            for (JsonNode item : response.path("output")) {
                if ("message".equals(item.path("type").asText())) {
                    for (JsonNode content : item.path("content")) {
                        if ("output_text".equals(
                                content.path("type").asText())) {
                            result.append(
                                content.path("text").asText()
                            );
                        }
                    }
                }
            }

            if (result.isEmpty()) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "AI provider returned no task suggestions."
                );
            }

            return result.toString();

        } catch (RestClientResponseException ex) {
            if (ex.getStatusCode().value() == 429) {
                throw new ResponseStatusException(
                    HttpStatus.TOO_MANY_REQUESTS,
                    "AI request limit or API quota reached."
                );
            }

            throw new ResponseStatusException(
                HttpStatus.BAD_GATEWAY,
                "AI provider request failed. Check API configuration."
            );
        }
    }
}
