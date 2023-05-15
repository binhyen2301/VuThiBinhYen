package com.haivn.dto;

import com.haivn.common_api.Role;
import com.haivn.common_api.TblUser;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel()
@Getter
@Setter
public class UserRoleDto extends BaseDto {
    private Long userId;
    private TblUser user;
    private Long roleId;
    private Role role;
    private short status;

    public UserRoleDto() {
    }
}