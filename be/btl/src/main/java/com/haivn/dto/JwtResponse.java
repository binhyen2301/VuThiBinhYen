package com.haivn.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel()
@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";

    private Long id;

    @JsonProperty(value = "full_name")
    private String fullName;

    private String username;

    private String code;

    @JsonProperty(value = "role_id")
    private Long roleId;


    public JwtResponse(String token, TblUserDto tblUserDto, Long roleId) {
        this.token = token;
        this.id = tblUserDto.getId();
        this.fullName = tblUserDto.getFullName();
        this.username = tblUserDto.getUsername();
        this.roleId = roleId;
    }

    public JwtResponse(String token, StudentsDto tblUserDto, Long roleId) {
        this.token = token;
        this.id = tblUserDto.getId();
        this.fullName = tblUserDto.getFullName();
        this.username = tblUserDto.getName();
        this.roleId = roleId;
    }
}
