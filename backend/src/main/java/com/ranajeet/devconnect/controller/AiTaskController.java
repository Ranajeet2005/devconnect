
package com.ranajeet.devconnect.controller;

import com.ranajeet.devconnect.dto.TaskGenerationRequest;
import com.ranajeet.devconnect.service.AiTaskGeneratorService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiTaskController {

    private final AiTaskGeneratorService taskGeneratorService;

    public AiTaskController(
            AiTaskGeneratorService taskGeneratorService) {
        this.taskGeneratorService = taskGeneratorService;
    }

    @PostMapping("/generate-tasks")
    public ResponseEntity<String> generateTasks(
            @Valid @RequestBody TaskGenerationRequest request) {

        String tasks = taskGeneratorService.generateTasks(
            request.getDescription()
        );

        return ResponseEntity.ok(tasks);
    }
}
