package com.ranajeet.devconnect.service;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;

@Service
public class AiProjectCopilotService {

    private final RestClient restClient;

    @Value("${openai.api-key:}")
    private String apiKey;

    @Value("${openai.model:gpt-4.1-mini}")
    private String model;

    public AiProjectCopilotService(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    public String generatePlan(String idea) {

        if (apiKey == null || apiKey.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "OpenAI API key is not configured."
            );
        }

        String instructions = """
                You are DevConnect AI Project Copilot.
                Convert developer project ideas into practical development plans.

                Include:
                1. Project Overview
                2. Recommended Technology Stack
                3. Core Features
                4. Database Design Suggestions
                5. Development Milestones
                6. Suggested Development Tasks
                7. Security Considerations

                Give beginner-friendly and technically accurate advice.
                Use clear headings and bullet points.
                Never claim that suggested features are already implemented.
                """;

        Map<String, Object> requestBody = Map.of(
                "model", model,
                "instructions", instructions,
                "input", idea,
                "max_output_tokens", 1500,
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
                        if ("output_text".equals(content.path("type").asText())) {
                            result.append(content.path("text").asText());
                        }
                    }
                }
            }

            if (result.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_GATEWAY,
                        "AI provider returned no text."
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

        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Could not connect to the AI provider."
            );
        }
    }
}