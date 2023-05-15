package com.haivn.repository;

import com.haivn.common_api.Classrooms;
import com.haivn.common_api.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassromsRepository extends JpaRepository<Classrooms, Long>, JpaSpecificationExecutor<Classrooms> {
    Classrooms findByclassCode(String classCode);

}