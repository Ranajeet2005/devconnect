
package com.ranajeet.devconnect.repository;

import com.ranajeet.devconnect.entity.Task;
import com.ranajeet.devconnect.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProject_Id(Long projectId);

    List<Task> findByAssignee_Id(Long assigneeId);

    List<Task> findByProject_IdAndStatus(
            Long projectId,
            TaskStatus status
    );
}
