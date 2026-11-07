package com.ranajeet.devconnect.service;

import com.ranajeet.devconnect.entity.Project;
import com.ranajeet.devconnect.entity.ProjectMember;
import com.ranajeet.devconnect.entity.Task;
import com.ranajeet.devconnect.entity.TaskStatus;
import com.ranajeet.devconnect.entity.User;

import com.ranajeet.devconnect.repository.ProjectMemberRepository;
import com.ranajeet.devconnect.repository.ProjectRepository;
import com.ranajeet.devconnect.repository.TaskRepository;
import com.ranajeet.devconnect.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public TaskService(
            TaskRepository taskRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository,
            ProjectMemberRepository projectMemberRepository) {

        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectMemberRepository = projectMemberRepository;
    }

    // Create a new task
    public Task createTask(
            String title,
            String description,
            Long projectId,
            Long assigneeId,
            Long creatorId,
            LocalDate dueDate) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Project not found"));

        User creator = userRepository.findById(creatorId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Creator not found"));

        Task task = new Task();

        task.setTitle(title);
        task.setDescription(description);
        task.setProject(project);
        task.setCreatedBy(creator);
        task.setDueDate(dueDate);
        task.setStatus(TaskStatus.TODO);

        // Assign task to a project member or owner
        if (assigneeId != null) {

            User assignee = userRepository.findById(assigneeId)
                    .orElseThrow(() ->
                            new IllegalArgumentException("Assignee not found"));

            boolean isOwner = project.getOwner()
                    .getId().equals(assigneeId);

            boolean isMember =
                    projectMemberRepository
                            .existsByProject_IdAndUser_Id(
                                    projectId, assigneeId);

            if (!isOwner && !isMember) {
                throw new IllegalArgumentException(
                        "Assignee must be a project member or owner");
            }

            task.setAssignee(assignee);
        }

        return taskRepository.save(task);
    }

    // Get all tasks belonging to a project
    @Transactional(readOnly = true)
    public List<Task> getTasksByProject(Long projectId) {

        if (!projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("Project not found");
        }

        return taskRepository.findByProject_Id(projectId);
    }

    // Get all tasks assigned to a user
    @Transactional(readOnly = true)
    public List<Task> getTasksByAssignee(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }

        return taskRepository.findByAssignee_Id(userId);
    }

    // Get tasks by project and status
    @Transactional(readOnly = true)
    public List<Task> getTasksByProjectAndStatus(
            Long projectId,
            TaskStatus status) {

        return taskRepository.findByProject_IdAndStatus(
                projectId, status);
    }

    // Get a task by ID
    @Transactional(readOnly = true)
    public Task getTaskById(Long taskId) {

        return taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Task not found"));
    }

    // Update task status
    public Task updateTaskStatus(
            Long taskId,
            TaskStatus status) {

        Task task = getTaskById(taskId);

        task.setStatus(status);

        return taskRepository.save(task);
    }

    // Delete task
    public void deleteTask(Long taskId) {

        Task task = getTaskById(taskId);

        taskRepository.delete(task);
    }
}

