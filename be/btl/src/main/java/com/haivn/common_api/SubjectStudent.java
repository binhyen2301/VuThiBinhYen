package com.haivn.common_api;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "student_subjects")
@Getter
@Setter
@DynamicUpdate
public class SubjectStudent extends BaseEntity {

    @Column(name = "student_id")
    private Long studentId;

    //    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.MERGE})
    @JoinColumn(name = "student_id", referencedColumnName = "id",nullable = true, insertable = false, updatable = false)
    private Students students;

    @Column(name = "subject_id")
    private Long subjectId;

    //    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "subject_id", referencedColumnName = "id",nullable = true, insertable = false, updatable = false)
    private Subjects subject;



    @Column(name = "group_id")
    private Long groupId;

    @ManyToOne(fetch = FetchType.LAZY,cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.MERGE})
    @JoinColumn(name="group_id",referencedColumnName="id", nullable = true, insertable = false, updatable = false)
    private Group group;

    @Column(name = "score")
    private Long score;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private Long status;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "sub_date")
    private Date subDate;

}