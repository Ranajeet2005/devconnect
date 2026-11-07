
package com.ranajeet.devconnect.controller;

import com.ranajeet.devconnect.dto.TaskRequest;
import com.ranajeet.devconnect.entity.Task;
import com.ranajeet.devconnect.entity.TaskStatus;
import com.ranajeet.devconnect.service.TaskService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(
            @Valid @RequestBody TaskRequest request) {

        Task task = taskService.createTask(
                request.getTitle(),
                request.getDescription(),
                request.getProjectId(),
                request.getAssigneeId(),
                request.getCreatorId(),
                request.getDueDate()
        );

        return ResponseEntity.ok(task);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Task>> getTasksByProject(
            @PathVariable Long projectId) {

        return ResponseEntity.ok(
                taskService.getTasksByProject(projectId)
        );
    }

    @GetMapping("/assignee/{userId}")
    public ResponseEntity<List<Task>> getTasksByAssignee(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                taskService.getTasksByAssignee(userId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(
            @PathVariable Long id) {

        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateTaskStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status) {

        return ResponseEntity.ok(
                taskService.updateTaskStatus(id, status)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id) {

        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
