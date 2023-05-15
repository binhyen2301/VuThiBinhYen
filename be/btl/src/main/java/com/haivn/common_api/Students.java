package com.haivn.common_api;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "students")
@Getter
@Setter
@DynamicUpdate
public class Students extends BaseEntity{

    @Column(name = "user_name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "status")
    private Long status;

    @Column(name = "student_code")
    private String studentCode;

    @Column(name = "class_id")
    private Long classId;

    @Column(name = "dob")
    private String dob;

    @Column(name = "urlImage")
    private String urlImage;

    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name="class_id",referencedColumnName="id", nullable = false, insertable = false, updatable = false)
    private Classrooms classrooms;

    @Column(name = "role_id", nullable = false)
    private Long role_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id",referencedColumnName="id", nullable = false, insertable = false, updatable = false)
    private Role role;
//    @JsonBackReference
//    @OneToMany(mappedBy="students")
//    private Set<SubjectStudent> studentSubjectSet;

}