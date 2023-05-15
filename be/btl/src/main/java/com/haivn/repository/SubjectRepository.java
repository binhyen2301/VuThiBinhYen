package com.haivn.repository;

import com.haivn.common_api.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subjects, Long>, JpaSpecificationExecutor<Subjects> {
//    List<UserRole> findByUserId(Long userId);
}