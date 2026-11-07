package com.ranajeet.devconnect.controller;

import com.ranajeet.devconnect.dto.ProjectPlanRequest;
import com.ranajeet.devconnect.dto.ProjectPlanResponse;
import com.ranajeet.devconnect.service.AiProjectCopilotService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiProjectCopilotService aiProjectCopilotService;

    public AiController(AiProjectCopilotService aiProjectCopilotService) {
        this.aiProjectCopilotService = aiProjectCopilotService;
    }

    @PostMapping("/project-plan")
    public ResponseEntity<ProjectPlanResponse> generateProjectPlan(
            @Valid @RequestBody ProjectPlanRequest request) {

        String plan = aiProjectCopilotService.generatePlan(request.getIdea());

        return ResponseEntity.ok(new ProjectPlanResponse(plan));
    }
}