package com.haivn.common_api;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
@Getter
@Setter
@DynamicUpdate
public class UserRole extends BaseEntity{

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",referencedColumnName="id", nullable = false, insertable = false, updatable = false)
    private TblUser user;

    @Column(name = "role_id")
    private Long roleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id",referencedColumnName="id", nullable = false, insertable = false, updatable = false)
    private Role role;

    @Column(name = "status")
    private short status;

}
