package com.haivn.common_api;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "groups")
@Getter
@Setter
@DynamicUpdate
public class Group extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "tille")
    private String tille;

    @Column(name = "subject_id")
    private Long subjectId;

    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name="subject_id",referencedColumnName="id", nullable = false, insertable = false, updatable = false)
    private Subjects subjects;

//    @JsonBackReference
//    @OneToMany(mappedBy="students")
//    private Set<SubjectStudent> studentSubjectSet;

}