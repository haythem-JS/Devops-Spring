package com.example.devops_spring.repository;


import com.example.devops_spring.entity.ClasseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ClasseRepository extends JpaRepository<ClasseEntity, Long> {
}
