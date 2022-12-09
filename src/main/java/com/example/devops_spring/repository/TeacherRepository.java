package com.example.devops_spring.repository;


import com.example.devops_spring.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
}
