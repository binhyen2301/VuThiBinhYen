package com.haivn.common_api;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "role")
@Getter
@Setter
@DynamicUpdate
public class Role extends BaseEntity{
    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private short status;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

}
