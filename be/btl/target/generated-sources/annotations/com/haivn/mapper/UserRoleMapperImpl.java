package com.haivn.mapper;

import com.haivn.common_api.UserRole;
import com.haivn.dto.UserRoleDto;
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
public class UserRoleMapperImpl implements UserRoleMapper {

    @Override
    public UserRole toEntity(UserRoleDto dto) {
        if ( dto == null ) {
            return null;
        }

        UserRole userRole = new UserRole();

        userRole.setId( dto.getId() );
        userRole.setModifiedUser( dto.getModifiedUser() );
        userRole.setModifiedDate( dto.getModifiedDate() );
        userRole.setCreatedUser( dto.getCreatedUser() );
        userRole.setCreatedDate( dto.getCreatedDate() );
        userRole.setDeleted( dto.isDeleted() );
        userRole.setUserId( dto.getUserId() );
        userRole.setUser( dto.getUser() );
        userRole.setRoleId( dto.getRoleId() );
        userRole.setRole( dto.getRole() );
        userRole.setStatus( dto.getStatus() );

        return userRole;
    }

    @Override
    public UserRoleDto toDto(UserRole entity) {
        if ( entity == null ) {
            return null;
        }

        UserRoleDto userRoleDto = new UserRoleDto();

        userRoleDto.setId( entity.getId() );
        userRoleDto.setModifiedUser( entity.getModifiedUser() );
        if ( entity.getModifiedDate() != null ) {
            userRoleDto.setModifiedDate( new Timestamp( entity.getModifiedDate().getTime() ) );
        }
        userRoleDto.setCreatedUser( entity.getCreatedUser() );
        if ( entity.getCreatedDate() != null ) {
            userRoleDto.setCreatedDate( new Timestamp( entity.getCreatedDate().getTime() ) );
        }
        userRoleDto.setDeleted( entity.isDeleted() );
        userRoleDto.setUserId( entity.getUserId() );
        userRoleDto.setUser( entity.getUser() );
        userRoleDto.setRoleId( entity.getRoleId() );
        userRoleDto.setRole( entity.getRole() );
        userRoleDto.setStatus( entity.getStatus() );

        return userRoleDto;
    }

    @Override
    public List<UserRole> toEntity(List<UserRoleDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<UserRole> list = new ArrayList<UserRole>( dtoList.size() );
        for ( UserRoleDto userRoleDto : dtoList ) {
            list.add( toEntity( userRoleDto ) );
        }

        return list;
    }

    @Override
    public List<UserRoleDto> toDto(List<UserRole> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserRoleDto> list = new ArrayList<UserRoleDto>( entityList.size() );
        for ( UserRole userRole : entityList ) {
            list.add( toDto( userRole ) );
        }

        return list;
    }

    @Override
    public Set<UserRoleDto> toDto(Set<UserRole> entityList) {
        if ( entityList == null ) {
            return null;
        }

        Set<UserRoleDto> set = new LinkedHashSet<UserRoleDto>( Math.max( (int) ( entityList.size() / .75f ) + 1, 16 ) );
        for ( UserRole userRole : entityList ) {
            set.add( toDto( userRole ) );
        }

        return set;
    }
}
