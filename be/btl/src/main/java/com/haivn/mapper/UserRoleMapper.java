package com.haivn.mapper;

import com.haivn.common_api.UserRole;
import com.haivn.dto.UserRoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRoleMapper extends EntityMapper<UserRoleDto, UserRole> {
}