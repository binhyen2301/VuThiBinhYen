package com.haivn.mapper;

import com.haivn.common_api.SubjectStudent;
import com.haivn.dto.SubjectStudentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectStudentMapper extends EntityMapper<SubjectStudentDto, SubjectStudent> {
}