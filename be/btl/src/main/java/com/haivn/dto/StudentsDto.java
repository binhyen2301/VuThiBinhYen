package com.haivn.dto;

import com.haivn.common_api.Classrooms;
import com.haivn.common_api.Role;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@ApiModel()
@Getter
@Setter
public class StudentsDto extends BaseDto {

    private String name;

    private String password;


    private String fullName;


    private String studentCode;

    private Long status;

    private Long classId;


    private String dob;

    private Classrooms classrooms;

    private Long role_id;

    private Role role;

    private String urlImage;

    public StudentsDto(){

    }
}