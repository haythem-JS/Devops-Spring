package com.example.devops_spring.repository;


import com.example.devops_spring.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
