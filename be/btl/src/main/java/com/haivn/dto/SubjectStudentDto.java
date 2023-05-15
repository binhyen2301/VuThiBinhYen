package com.haivn.dto;

import com.haivn.common_api.Group;
import com.haivn.common_api.Students;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@ApiModel()
@Getter
@Setter
public class SubjectStudentDto extends BaseDto {
    private Long groupId;
    private Long studentId;
    private Long subjectId;
    private Students students;
    private SubjectDto subject;
    private Group group;
    private Long score;
    private String content;
    private Long status;
    private String fileName;
    private Date subDate;
}