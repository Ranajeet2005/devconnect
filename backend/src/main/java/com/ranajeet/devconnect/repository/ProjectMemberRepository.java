package com.ranajeet.devconnect.repository;

import com.ranajeet.devconnect.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectMemberRepository
        extends JpaRepository<ProjectMember, Long> {

    List<ProjectMember> findByProject_Id(Long projectId);

    List<ProjectMember> findByUser_Id(Long userId);

    boolean existsByProject_IdAndUser_Id(
            Long projectId,
            Long userId
    );

    Optional<ProjectMember> findByProject_IdAndUser_Id(
            Long projectId,
            Long userId
    );
}

