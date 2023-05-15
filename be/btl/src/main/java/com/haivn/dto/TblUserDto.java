package com.haivn.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haivn.annotation.CheckEmail;
import com.haivn.common_api.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.util.List;

@ApiModel()
@Getter
@Setter
public class TblUserDto extends BaseDto {

    private String fullName;

    private String username;

    private String email;

    private String password;

    private String phone;

    private String teacher_code;

    private Integer is_super_admin;

    private Integer is_teacher;

    private Long role_id;

    private Role role;

    private Integer status;
}