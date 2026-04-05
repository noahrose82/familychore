package com.gcu.familychore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gcu.familychore.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}