package com.haivn.common_api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_user")
@Getter
@Setter
@DynamicUpdate
public class TblUser extends BaseEntity{

    @JsonProperty(value = "full_name")
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @JsonProperty(value = "user_name")
    @Column(name = "user_name", nullable = false)
    private String username;

    @JsonProperty(value = "email")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "teacher_code", nullable = false)
    private String teacher_code;

//    @Column(name = "department_id", nullable = false)
//    private Long department_id;

    @Column(name = "is_super_admin", nullable = false)
    private Integer is_super_admin;

    @Column(name = "is_teacher", nullable = false)
    private Integer is_teacher;

    @Column(name = "role_id", nullable = false)
    private Long role_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id",referencedColumnName="id", nullable = false, insertable = false, updatable = false)
    private Role role;

    @Column(name = "status", nullable = false)
    private Integer status;

}