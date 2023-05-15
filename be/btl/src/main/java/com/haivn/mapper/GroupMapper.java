package com.haivn.mapper;

import com.haivn.common_api.Classrooms;
import com.haivn.common_api.Group;
import com.haivn.dto.ClassroomsDto;
import com.haivn.dto.GroupDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper extends EntityMapper<GroupDto, Group> {
}