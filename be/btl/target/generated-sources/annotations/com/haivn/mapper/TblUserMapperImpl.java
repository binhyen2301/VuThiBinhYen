package com.haivn.mapper;

import com.haivn.common_api.TblUser;
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
public class TblUserMapperImpl implements TblUserMapper {

    @Override
    public TblUser toEntity(TblUserDto dto) {
        if ( dto == null ) {
            return null;
        }

        TblUser tblUser = new TblUser();

        tblUser.setId( dto.getId() );
        tblUser.setModifiedUser( dto.getModifiedUser() );
        tblUser.setModifiedDate( dto.getModifiedDate() );
        tblUser.setCreatedUser( dto.getCreatedUser() );
        tblUser.setCreatedDate( dto.getCreatedDate() );
        tblUser.setDeleted( dto.isDeleted() );
        tblUser.setFullName( dto.getFullName() );
        tblUser.setUsername( dto.getUsername() );
        tblUser.setEmail( dto.getEmail() );
        tblUser.setPassword( dto.getPassword() );
        tblUser.setPhone( dto.getPhone() );
        tblUser.setTeacher_code( dto.getTeacher_code() );
        tblUser.setIs_super_admin( dto.getIs_super_admin() );
        tblUser.setIs_teacher( dto.getIs_teacher() );
        tblUser.setRole_id( dto.getRole_id() );
        tblUser.setRole( dto.getRole() );
        tblUser.setStatus( dto.getStatus() );

        return tblUser;
    }

    @Override
    public TblUserDto toDto(TblUser entity) {
        if ( entity == null ) {
            return null;
        }

        TblUserDto tblUserDto = new TblUserDto();

        tblUserDto.setId( entity.getId() );
        tblUserDto.setModifiedUser( entity.getModifiedUser() );
        if ( entity.getModifiedDate() != null ) {
            tblUserDto.setModifiedDate( new Timestamp( entity.getModifiedDate().getTime() ) );
        }
        tblUserDto.setCreatedUser( entity.getCreatedUser() );
        if ( entity.getCreatedDate() != null ) {
            tblUserDto.setCreatedDate( new Timestamp( entity.getCreatedDate().getTime() ) );
        }
        tblUserDto.setDeleted( entity.isDeleted() );
        tblUserDto.setFullName( entity.getFullName() );
        tblUserDto.setUsername( entity.getUsername() );
        tblUserDto.setEmail( entity.getEmail() );
        tblUserDto.setPassword( entity.getPassword() );
        tblUserDto.setPhone( entity.getPhone() );
        tblUserDto.setTeacher_code( entity.getTeacher_code() );
        tblUserDto.setIs_super_admin( entity.getIs_super_admin() );
        tblUserDto.setIs_teacher( entity.getIs_teacher() );
        tblUserDto.setRole_id( entity.getRole_id() );
        tblUserDto.setRole( entity.getRole() );
        tblUserDto.setStatus( entity.getStatus() );

        return tblUserDto;
    }

    @Override
    public List<TblUser> toEntity(List<TblUserDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TblUser> list = new ArrayList<TblUser>( dtoList.size() );
        for ( TblUserDto tblUserDto : dtoList ) {
            list.add( toEntity( tblUserDto ) );
        }

        return list;
    }

    @Override
    public List<TblUserDto> toDto(List<TblUser> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TblUserDto> list = new ArrayList<TblUserDto>( entityList.size() );
        for ( TblUser tblUser : entityList ) {
            list.add( toDto( tblUser ) );
        }

        return list;
    }

    @Override
    public Set<TblUserDto> toDto(Set<TblUser> entityList) {
        if ( entityList == null ) {
            return null;
        }

        Set<TblUserDto> set = new LinkedHashSet<TblUserDto>( Math.max( (int) ( entityList.size() / .75f ) + 1, 16 ) );
        for ( TblUser tblUser : entityList ) {
            set.add( toDto( tblUser ) );
        }

        return set;
    }
}
