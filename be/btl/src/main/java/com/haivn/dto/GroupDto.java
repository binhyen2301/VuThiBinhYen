package com.haivn.dto;

import com.haivn.common_api.Classrooms;
import com.haivn.common_api.Group;
import com.haivn.common_api.Subjects;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@ApiModel()
@Getter
@Setter
public class GroupDto extends BaseDto {

    private String name;


    private String tille;


    private Long subjectId;

    private Subjects subjects;

    private Long groupId;

    public Subjects getSubjects() {
        return subjects;
    }

    public void setSubjects(Subjects subjects) {
        this.subjects = subjects;
    }

    public GroupDto() {
    }

}