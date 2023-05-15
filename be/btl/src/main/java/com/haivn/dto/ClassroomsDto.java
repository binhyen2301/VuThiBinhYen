package com.haivn.dto;

import com.haivn.annotation.CheckEmail;
import com.haivn.common_api.TblUser;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.util.List;

@ApiModel()
@Getter
@Setter
public class ClassroomsDto extends BaseDto {
    @Size(max = 255)
    @Column(name = "name")
    @Schema(description = "Họ tên người dùng")
    private String name;

    @Size(max = 255)
    @Schema(description = "mã lớp")
    private String classCode;

    @Schema(description = "mã lớp")
    private Long teacherId;

    @Schema(description = "Người dùng")
    private TblUserDto user;

    public TblUserDto getUser() {
        return user;
    }

    public void setUser(TblUserDto user) {
        this.user = user;
    }

    public ClassroomsDto() {
    }

}