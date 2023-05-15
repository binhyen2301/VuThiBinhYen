package com.haivn.common_api;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "classroms")
@Getter
@Setter
@DynamicUpdate
public class Classrooms extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "class_code")
    private String classCode;

    @Column(name = "teacher_id")
    private Long teacherId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="teacher_id",referencedColumnName="id", nullable = false, insertable = false, updatable = false)
    private TblUser user;

    public TblUser getUser() {
        return user;
    }

    public void setUser(TblUser user) {
        this.user = user;
    }
}