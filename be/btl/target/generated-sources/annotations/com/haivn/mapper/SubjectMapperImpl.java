package com.haivn.mapper;

import com.haivn.common_api.Subjects;
import com.haivn.common_api.TblUser;
import com.haivn.dto.SubjectDto;
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
public class SubjectMapperImpl implements SubjectMapper {

    @Override
    public Subjects toEntity(SubjectDto dto) {
        if ( dto == null ) {
            return null;
        }

        Subjects subjects = new Subjects();

        subjects.setId( dto.getId() );
        subjects.setModifiedUser( dto.getModifiedUser() );
        subjects.setModifiedDate( dto.getModifiedDate() );
        subjects.setCreatedUser( dto.getCreatedUser() );
        subjects.setCreatedDate( dto.getCreatedDate() );
        subjects.setDeleted( dto.isDeleted() );
        subjects.setCode( dto.getCode() );
        subjects.setName( dto.getName() );
        subjects.setGvgdId( dto.getGvgdId() );
        subjects.setStatus( dto.getStatus() );
        subjects.setGVGD( tblUserDtoToTblUser( dto.getGVGD() ) );
        subjects.setGv1Id( dto.getGv1Id() );
        subjects.setGV1( tblUserDtoToTblUser( dto.getGV1() ) );
        subjects.setGv2Id( dto.getGv2Id() );
        subjects.setGV2( dto.getGV2() );
        subjects.setExpireDate( dto.getExpireDate() );

        return subjects;
    }

    @Override
    public SubjectDto toDto(Subjects entity) {
        if ( entity == null ) {
            return null;
        }

        SubjectDto subjectDto = new SubjectDto();

        subjectDto.setId( entity.getId() );
        subjectDto.setModifiedUser( entity.getModifiedUser() );
        if ( entity.getModifiedDate() != null ) {
            subjectDto.setModifiedDate( new Timestamp( entity.getModifiedDate().getTime() ) );
        }
        subjectDto.setCreatedUser( entity.getCreatedUser() );
        if ( entity.getCreatedDate() != null ) {
            subjectDto.setCreatedDate( new Timestamp( entity.getCreatedDate().getTime() ) );
        }
        subjectDto.setDeleted( entity.isDeleted() );
        subjectDto.setCode( entity.getCode() );
        subjectDto.setName( entity.getName() );
        subjectDto.setGvgdId( entity.getGvgdId() );
        subjectDto.setGVGD( tblUserToTblUserDto( entity.getGVGD() ) );
        subjectDto.setGv1Id( entity.getGv1Id() );
        subjectDto.setGV1( tblUserToTblUserDto( entity.getGV1() ) );
        subjectDto.setGv2Id( entity.getGv2Id() );
        subjectDto.setGV2( entity.getGV2() );
        subjectDto.setStatus( entity.getStatus() );
        subjectDto.setExpireDate( entity.getExpireDate() );

        return subjectDto;
    }

    @Override
    public List<Subjects> toEntity(List<SubjectDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Subjects> list = new ArrayList<Subjects>( dtoList.size() );
        for ( SubjectDto subjectDto : dtoList ) {
            list.add( toEntity( subjectDto ) );
        }

        return list;
    }

    @Override
    public List<SubjectDto> toDto(List<Subjects> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SubjectDto> list = new ArrayList<SubjectDto>( entityList.size() );
        for ( Subjects subjects : entityList ) {
            list.add( toDto( subjects ) );
        }

        return list;
    }

    @Override
    public Set<SubjectDto> toDto(Set<Subjects> entityList) {
        if ( entityList == null ) {
            return null;
        }

        Set<SubjectDto> set = new LinkedHashSet<SubjectDto>( Math.max( (int) ( entityList.size() / .75f ) + 1, 16 ) );
        for ( Subjects subjects : entityList ) {
            set.add( toDto( subjects ) );
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
}
