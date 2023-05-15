package com.haivn.dto;

import com.haivn.common_api.SubjectStudent;
import com.haivn.common_api.TblUser;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@ApiModel()
@Getter
@Setter
public class SubjectDto extends BaseDto {
    private String code;
    private String name;
    private Long gvgdId;
    private TblUserDto gVGD;
    private Long gv1Id;
    private TblUserDto gV1;
    private Long gv2Id;
    private TblUser gV2;
    private Set<SubjectStudent> studentSubjectSet;
    private Long status;
    private Date expireDate;;
}