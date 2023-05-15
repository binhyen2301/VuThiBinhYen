package com.haivn.repository;

import com.haivn.common_api.SubjectStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectStudentRepository extends JpaRepository<SubjectStudent, Long>, JpaSpecificationExecutor<SubjectStudent> {
//    List<UserRole> findByUserId(Long userId);

    boolean existsBySubjectId(Long subjectId);

    boolean existsByStudentId(Long studentId);

    @Query(
            value = "SELECT * FROM student_subjects ss WHERE ss.subject_id = :subjectId AND ss.student_id = :studentId",
            nativeQuery = true)
    Optional<SubjectStudent> findBySubjectIdAndStudentId(Long subjectId,Long studentId);
}