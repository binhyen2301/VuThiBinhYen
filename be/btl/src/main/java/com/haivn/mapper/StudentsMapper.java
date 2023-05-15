package com.haivn.mapper;

import com.haivn.common_api.Students;
import com.haivn.common_api.SubjectStudent;
import com.haivn.dto.StudentsDto;
import com.haivn.dto.SubjectStudentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentsMapper extends EntityMapper<StudentsDto, Students> {
}