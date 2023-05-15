package com.haivn.mapper;

import com.haivn.common_api.Classrooms;
import com.haivn.common_api.TblUser;
import com.haivn.dto.ClassroomsDto;
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
public class ClassroomsMapperImpl implements ClassroomsMapper {

    @Override
    public Classrooms toEntity(ClassroomsDto dto) {
        if ( dto == null ) {
            return null;
        }

        Classrooms classrooms = new Classrooms();

        classrooms.setId( dto.getId() );
        classrooms.setModifiedUser( dto.getModifiedUser() );
        classrooms.setModifiedDate( dto.getModifiedDate() );
        classrooms.setCreatedUser( dto.getCreatedUser() );
        classrooms.setCreatedDate( dto.getCreatedDate() );
        classrooms.setDeleted( dto.isDeleted() );
        classrooms.setUser( tblUserDtoToTblUser( dto.getUser() ) );
        classrooms.setName( dto.getName() );
        classrooms.setClassCode( dto.getClassCode() );
        classrooms.setTeacherId( dto.getTeacherId() );

        return classrooms;
    }

    @Override
    public ClassroomsDto toDto(Classrooms entity) {
        if ( entity == null ) {
            return null;
        }

        ClassroomsDto classroomsDto = new ClassroomsDto();

        classroomsDto.setId( entity.getId() );
        classroomsDto.setModifiedUser( entity.getModifiedUser() );
        if ( entity.getModifiedDate() != null ) {
            classroomsDto.setModifiedDate( new Timestamp( entity.getModifiedDate().getTime() ) );
        }
        classroomsDto.setCreatedUser( entity.getCreatedUser() );
        if ( entity.getCreatedDate() != null ) {
            classroomsDto.setCreatedDate( new Timestamp( entity.getCreatedDate().getTime() ) );
        }
        classroomsDto.setDeleted( entity.isDeleted() );
        classroomsDto.setUser( tblUserToTblUserDto( entity.getUser() ) );
        classroomsDto.setName( entity.getName() );
        classroomsDto.setClassCode( entity.getClassCode() );
        classroomsDto.setTeacherId( entity.getTeacherId() );

        return classroomsDto;
    }

    @Override
    public List<Classrooms> toEntity(List<ClassroomsDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Classrooms> list = new ArrayList<Classrooms>( dtoList.size() );
        for ( ClassroomsDto classroomsDto : dtoList ) {
            list.add( toEntity( classroomsDto ) );
        }

        return list;
    }

    @Override
    public List<ClassroomsDto> toDto(List<Classrooms> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ClassroomsDto> list = new ArrayList<ClassroomsDto>( entityList.size() );
        for ( Classrooms classrooms : entityList ) {
            list.add( toDto( classrooms ) );
        }

        return list;
    }

    @Override
    public Set<ClassroomsDto> toDto(Set<Classrooms> entityList) {
        if ( entityList == null ) {
            return null;
        }

        Set<ClassroomsDto> set = new LinkedHashSet<ClassroomsDto>( Math.max( (int) ( entityList.size() / .75f ) + 1, 16 ) );
        for ( Classrooms classrooms : entityList ) {
            set.add( toDto( classrooms ) );
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
