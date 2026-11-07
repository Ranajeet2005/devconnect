
package com.ranajeet.devconnect.repository;

import com.ranajeet.devconnect.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByOwner_Id(Long ownerId);
}
