package com.haivn.repository;

import com.haivn.common_api.Classrooms;
import com.haivn.common_api.Students;
import com.haivn.common_api.TblUser;
import com.haivn.common_api.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentsRepository extends JpaRepository<Students, Long>, JpaSpecificationExecutor<Students> {
//    List<UserRole> findByUserId(Long userId);
    Students findByName(String username);

    boolean existsByStudentCode(String studentCode);
}