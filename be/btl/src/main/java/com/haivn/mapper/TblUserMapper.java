package com.haivn.mapper;

import com.haivn.common_api.TblUser;
import com.haivn.dto.TblUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TblUserMapper extends EntityMapper<TblUserDto, TblUser> {
}