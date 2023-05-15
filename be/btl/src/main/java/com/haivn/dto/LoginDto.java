package com.haivn.dto;

import com.haivn.common_api.Subjects;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@ApiModel()
@Getter
@Setter
@Data
public class LoginDto implements Serializable {
    private String username;

    private String password;

    public LoginDto() {
    }

}