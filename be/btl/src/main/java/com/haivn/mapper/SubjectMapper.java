package com.haivn.mapper;

import com.haivn.common_api.Subjects;
import com.haivn.dto.SubjectDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper extends EntityMapper<SubjectDto, Subjects> {
}