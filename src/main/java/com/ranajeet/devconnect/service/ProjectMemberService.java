
package com.ranajeet.devconnect.service;

import com.ranajeet.devconnect.entity.*;
import com.ranajeet.devconnect.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectMemberService {

    private final ProjectMemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectMemberService(
            ProjectMemberRepository memberRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository) {

        this.memberRepository = memberRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public ProjectMember addMember(
            Long projectId,
            Long userId,
            Role role) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Project not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        if (project.getOwner().getId().equals(userId)) {
            throw new IllegalArgumentException(
                    "Project owner is already associated with the project");
        }

        if (memberRepository.existsByProject_IdAndUser_Id(
                projectId, userId)) {
            throw new IllegalArgumentException(
                    "User is already a project member");
        }

        ProjectMember member = new ProjectMember();
        member.setProject(project);
        member.setUser(user);
        member.setRole(role);

        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public List<ProjectMember> getProjectMembers(Long projectId) {
        return memberRepository.findByProject_Id(projectId);
    }

    @Transactional(readOnly = true)
    public List<ProjectMember> getUserMemberships(Long userId) {
        return memberRepository.findByUser_Id(userId);
    }

    public void removeMember(Long projectId, Long userId) {
        ProjectMember member = memberRepository
                .findByProject_IdAndUser_Id(projectId, userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Project membership not found"));

        memberRepository.delete(member);
    }
}
