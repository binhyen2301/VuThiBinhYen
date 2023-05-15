package com.haivn.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@ApiModel()
@Getter
@Setter
public class RoleDto extends BaseDto {
    @Schema(description = "Mô tả quyền")
    private String description;
    @Schema(description = "Trạng thái quyền. 1 là active, 0 là unactive")
    private short status;
    @Size(max = 255)
    @Schema(description = "Tên quyền")
    private String name;
    @Schema(description = "Mã quyền")
    private String code;
    private Map<String, String> funcPer;

    public RoleDto() {
    }
}