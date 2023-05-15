package com.haivn.dto;

import com.haivn.annotation.CheckDate;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;

@ApiModel()
@Getter
@Setter
public class HethongNguoidungTokenDto extends AbstractDto<Long> {
    private Long id;
    private String token;
    private Timestamp tokenexpdate;
    private Long createdUser;
    @CheckDate
    private Date createdDate;

    public HethongNguoidungTokenDto() {
    }
}