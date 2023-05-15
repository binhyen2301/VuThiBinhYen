package com.haivn.mapper;

import com.haivn.common_api.Classrooms;
import com.haivn.common_api.TblUser;
import com.haivn.dto.ClassroomsDto;
import com.haivn.dto.TblUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassroomsMapper extends EntityMapper<ClassroomsDto, Classrooms> {
}