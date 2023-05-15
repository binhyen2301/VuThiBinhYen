package com.haivn.mapper;

import com.haivn.common_api.SubjectStudent;
import com.haivn.common_api.Subjects;
import com.haivn.common_api.TblUser;
import com.haivn.dto.SubjectDto;
import com.haivn.dto.SubjectStudentDto;
import com.haivn.dto.TblUserDto;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-05T18:05:33+0700",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.16.1 (Oracle Corporation)"
)
@Component
public class SubjectStudentMapperImpl implements SubjectStudentMapper {

    @Override
    public SubjectStudent toEntity(SubjectStudentDto dto) {
        if ( dto == null ) {
            return null;
        }

        SubjectStudent subjectStudent = new SubjectStudent();

        subjectStudent.setId( dto.getId() );
        subjectStudent.setModifiedUser( dto.getModifiedUser() );
        subjectStudent.setModifiedDate( dto.getModifiedDate() );
        subjectStudent.setCreatedUser( dto.getCreatedUser() );
        subjectStudent.setCreatedDate( dto.getCreatedDate() );
        subjectStudent.setDeleted( dto.isDeleted() );
        subjectStudent.setStudentId( dto.getStudentId() );
        subjectStudent.setStudents( dto.getStudents() );
        subjectStudent.setSubjectId( dto.getSubjectId() );
        subjectStudent.setSubject( subjectDtoToSubjects( dto.getSubject() ) );
        subjectStudent.setGroupId( dto.getGroupId() );
        subjectStudent.setGroup( dto.getGroup() );
        subjectStudent.setScore( dto.getScore() );
        subjectStudent.setContent( dto.getContent() );
        subjectStudent.setStatus( dto.getStatus() );
        subjectStudent.setFileName( dto.getFileName() );
        subjectStudent.setSubDate( dto.getSubDate() );

        return subjectStudent;
    }

    @Override
    public SubjectStudentDto toDto(SubjectStudent entity) {
        if ( entity == null ) {
            return null;
        }

        SubjectStudentDto subjectStudentDto = new SubjectStudentDto();

        subjectStudentDto.setId( entity.getId() );
        subjectStudentDto.setModifiedUser( entity.getModifiedUser() );
        if ( entity.getModifiedDate() != null ) {
            subjectStudentDto.setModifiedDate( new Timestamp( entity.getModifiedDate().getTime() ) );
        }
        subjectStudentDto.setCreatedUser( entity.getCreatedUser() );
        if ( entity.getCreatedDate() != null ) {
            subjectStudentDto.setCreatedDate( new Timestamp( entity.getCreatedDate().getTime() ) );
        }
        subjectStudentDto.setDeleted( entity.isDeleted() );
        subjectStudentDto.setGroupId( entity.getGroupId() );
        subjectStudentDto.setStudentId( entity.getStudentId() );
        subjectStudentDto.setSubjectId( entity.getSubjectId() );
        subjectStudentDto.setStudents( entity.getStudents() );
        subjectStudentDto.setSubject( subjectsToSubjectDto( entity.getSubject() ) );
        subjectStudentDto.setGroup( entity.getGroup() );
        subjectStudentDto.setScore( entity.getScore() );
        subjectStudentDto.setContent( entity.getContent() );
        subjectStudentDto.setStatus( entity.getStatus() );
        subjectStudentDto.setFileName( entity.getFileName() );
        subjectStudentDto.setSubDate( entity.getSubDate() );

        return subjectStudentDto;
    }

    @Override
    public List<SubjectStudent> toEntity(List<SubjectStudentDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<SubjectStudent> list = new ArrayList<SubjectStudent>( dtoList.size() );
        for ( SubjectStudentDto subjectStudentDto : dtoList ) {
            list.add( toEntity( subjectStudentDto ) );
        }

        return list;
    }

    @Override
    public List<SubjectStudentDto> toDto(List<SubjectStudent> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SubjectStudentDto> list = new ArrayList<SubjectStudentDto>( entityList.size() );
        for ( SubjectStudent subjectStudent : entityList ) {
            list.add( toDto( subjectStudent ) );
        }

        return list;
    }

    @Override
    public Set<SubjectStudentDto> toDto(Set<SubjectStudent> entityList) {
        if ( entityList == null ) {
            return null;
        }

        Set<SubjectStudentDto> set = new LinkedHashSet<SubjectStudentDto>( Math.max( (int) ( entityList.size() / .75f ) + 1, 16 ) );
        for ( SubjectStudent subjectStudent : entityList ) {
            set.add( toDto( subjectStudent ) );
        }

        return set;
    }

    protected TblUser tblUserDtoToTblUser(TblUserDto tblUserDto) {
        if ( tblUserDto == null ) {
            return null;
        }

        TblUser tblUser = new TblUser();

        tblUser.setId( tblUserDto.getId() );
        tblUser.setModifiedUser( tblUserDto.getModifiedUser() );
        tblUser.setModifiedDate( tblUserDto.getModifiedDate() );
        tblUser.setCreatedUser( tblUserDto.getCreatedUser() );
        tblUser.setCreatedDate( tblUserDto.getCreatedDate() );
        tblUser.setDeleted( tblUserDto.isDeleted() );
        tblUser.setFullName( tblUserDto.getFullName() );
        tblUser.setUsername( tblUserDto.getUsername() );
        tblUser.setEmail( tblUserDto.getEmail() );
        tblUser.setPassword( tblUserDto.getPassword() );
        tblUser.setPhone( tblUserDto.getPhone() );
        tblUser.setTeacher_code( tblUserDto.getTeacher_code() );
        tblUser.setIs_super_admin( tblUserDto.getIs_super_admin() );
        tblUser.setIs_teacher( tblUserDto.getIs_teacher() );
        tblUser.setRole_id( tblUserDto.getRole_id() );
        tblUser.setRole( tblUserDto.getRole() );
        tblUser.setStatus( tblUserDto.getStatus() );

        return tblUser;
    }

    protected Subjects subjectDtoToSubjects(SubjectDto subjectDto) {
        if ( subjectDto == null ) {
            return null;
        }

        Subjects subjects = new Subjects();

        subjects.setId( subjectDto.getId() );
        subjects.setModifiedUser( subjectDto.getModifiedUser() );
        subjects.setModifiedDate( subjectDto.getModifiedDate() );
        subjects.setCreatedUser( subjectDto.getCreatedUser() );
        subjects.setCreatedDate( subjectDto.getCreatedDate() );
        subjects.setDeleted( subjectDto.isDeleted() );
        subjects.setCode( subjectDto.getCode() );
        subjects.setName( subjectDto.getName() );
        subjects.setGvgdId( subjectDto.getGvgdId() );
        subjects.setStatus( subjectDto.getStatus() );
        subjects.setGVGD( tblUserDtoToTblUser( subjectDto.getGVGD() ) );
        subjects.setGv1Id( subjectDto.getGv1Id() );
        subjects.setGV1( tblUserDtoToTblUser( subjectDto.getGV1() ) );
        subjects.setGv2Id( subjectDto.getGv2Id() );
        subjects.setGV2( subjectDto.getGV2() );
        subjects.setExpireDate( subjectDto.getExpireDate() );

        return subjects;
    }

    protected TblUserDto tblUserToTblUserDto(TblUser tblUser) {
        if ( tblUser == null ) {
            return null;
        }

        TblUserDto tblUserDto = new TblUserDto();

        tblUserDto.setId( tblUser.getId() );
        tblUserDto.setModifiedUser( tblUser.getModifiedUser() );
        if ( tblUser.getModifiedDate() != null ) {
            tblUserDto.setModifiedDate( new Timestamp( tblUser.getModifiedDate().getTime() ) );
        }
        tblUserDto.setCreatedUser( tblUser.getCreatedUser() );
        if ( tblUser.getCreatedDate() != null ) {
            tblUserDto.setCreatedDate( new Timestamp( tblUser.getCreatedDate().getTime() ) );
        }
        tblUserDto.setDeleted( tblUser.isDeleted() );
        tblUserDto.setFullName( tblUser.getFullName() );
        tblUserDto.setUsername( tblUser.getUsername() );
        tblUserDto.setEmail( tblUser.getEmail() );
        tblUserDto.setPassword( tblUser.getPassword() );
        tblUserDto.setPhone( tblUser.getPhone() );
        tblUserDto.setTeacher_code( tblUser.getTeacher_code() );
        tblUserDto.setIs_super_admin( tblUser.getIs_super_admin() );
        tblUserDto.setIs_teacher( tblUser.getIs_teacher() );
        tblUserDto.setRole_id( tblUser.getRole_id() );
        tblUserDto.setRole( tblUser.getRole() );
        tblUserDto.setStatus( tblUser.getStatus() );

        return tblUserDto;
    }

    protected SubjectDto subjectsToSubjectDto(Subjects subjects) {
        if ( subjects == null ) {
            return null;
        }

        SubjectDto subjectDto = new SubjectDto();

        subjectDto.setId( subjects.getId() );
        subjectDto.setModifiedUser( subjects.getModifiedUser() );
        if ( subjects.getModifiedDate() != null ) {
            subjectDto.setModifiedDate( new Timestamp( subjects.getModifiedDate().getTime() ) );
        }
        subjectDto.setCreatedUser( subjects.getCreatedUser() );
        if ( subjects.getCreatedDate() != null ) {
            subjectDto.setCreatedDate( new Timestamp( subjects.getCreatedDate().getTime() ) );
        }
        subjectDto.setDeleted( subjects.isDeleted() );
        subjectDto.setCode( subjects.getCode() );
        subjectDto.setName( subjects.getName() );
        subjectDto.setGvgdId( subjects.getGvgdId() );
        subjectDto.setGVGD( tblUserToTblUserDto( subjects.getGVGD() ) );
        subjectDto.setGv1Id( subjects.getGv1Id() );
        subjectDto.setGV1( tblUserToTblUserDto( subjects.getGV1() ) );
        subjectDto.setGv2Id( subjects.getGv2Id() );
        subjectDto.setGV2( subjects.getGV2() );
        subjectDto.setStatus( subjects.getStatus() );
        subjectDto.setExpireDate( subjects.getExpireDate() );

        return subjectDto;
    }
}
