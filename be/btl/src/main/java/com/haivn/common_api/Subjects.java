package com.haivn.common_api;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "subjects")
@Getter
@Setter
@DynamicUpdate
public class Subjects extends BaseEntity{

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "criteria")
    private String criteria;

    @Column(name = "gvgd_id")
    private Long gvgdId;

    @Column(name = "status")
    private Long status;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name="gvgd_id",referencedColumnName="id", nullable = false, insertable = false, updatable = false)
    private TblUser gVGD;

    @Column(name = "gv1_id")
    private Long gv1Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="gv1_id",referencedColumnName="id", nullable = false, insertable = false, updatable = false)
    private TblUser gV1;

    @Column(name = "gv2_id")
    private Long gv2Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="gv2_id",referencedColumnName="id", nullable = false, insertable = false, updatable = false)
    private TblUser gV2;


    @Column(name = "expire_date")
    private Date expireDate;

//    @JsonBackReference
//    @OneToMany(mappedBy="subject")
//    private Set<SubjectStudent> studentSubjectSet;


}