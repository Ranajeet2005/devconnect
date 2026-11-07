
package com.ranajeet.devconnect.service;

import com.ranajeet.devconnect.entity.Project;
import com.ranajeet.devconnect.entity.User;
import com.ranajeet.devconnect.repository.ProjectRepository;
import com.ranajeet.devconnect.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(
            ProjectRepository projectRepository,
            UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Project createProject(
            String name,
            String description,
            Long ownerId) {

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Owner not found"));

        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setOwner(owner);

        return projectRepository.save(project);
    }

    @Transactional(readOnly = true)
    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Project not found"));
    }

    @Transactional(readOnly = true)
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Project> getProjectsByOwner(Long ownerId) {
        return projectRepository.findByOwner_Id(ownerId);
    }

    public Project updateProject(
            Long projectId,
            String name,
            String description) {

        Project project = getProjectById(projectId);

        project.setName(name);
        project.setDescription(description);

        return projectRepository.save(project);
    }

    public void deleteProject(Long projectId) {
        Project project = getProjectById(projectId);
        projectRepository.delete(project);
    }
}
