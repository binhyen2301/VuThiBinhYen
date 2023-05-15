package com.haivn.mapper;

import com.haivn.common_api.Role;
import com.haivn.dto.RoleDto;
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
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role toEntity(RoleDto dto) {
        if ( dto == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( dto.getId() );
        role.setModifiedUser( dto.getModifiedUser() );
        role.setModifiedDate( dto.getModifiedDate() );
        role.setCreatedUser( dto.getCreatedUser() );
        role.setCreatedDate( dto.getCreatedDate() );
        role.setDeleted( dto.isDeleted() );
        role.setDescription( dto.getDescription() );
        role.setStatus( dto.getStatus() );
        role.setName( dto.getName() );
        role.setCode( dto.getCode() );

        return role;
    }

    @Override
    public RoleDto toDto(Role entity) {
        if ( entity == null ) {
            return null;
        }

        RoleDto roleDto = new RoleDto();

        roleDto.setId( entity.getId() );
        roleDto.setModifiedUser( entity.getModifiedUser() );
        if ( entity.getModifiedDate() != null ) {
            roleDto.setModifiedDate( new Timestamp( entity.getModifiedDate().getTime() ) );
        }
        roleDto.setCreatedUser( entity.getCreatedUser() );
        if ( entity.getCreatedDate() != null ) {
            roleDto.setCreatedDate( new Timestamp( entity.getCreatedDate().getTime() ) );
        }
        roleDto.setDeleted( entity.isDeleted() );
        roleDto.setDescription( entity.getDescription() );
        roleDto.setStatus( entity.getStatus() );
        roleDto.setName( entity.getName() );
        roleDto.setCode( entity.getCode() );

        return roleDto;
    }

    @Override
    public List<Role> toEntity(List<RoleDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Role> list = new ArrayList<Role>( dtoList.size() );
        for ( RoleDto roleDto : dtoList ) {
            list.add( toEntity( roleDto ) );
        }

        return list;
    }

    @Override
    public List<RoleDto> toDto(List<Role> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RoleDto> list = new ArrayList<RoleDto>( entityList.size() );
        for ( Role role : entityList ) {
            list.add( toDto( role ) );
        }

        return list;
    }

    @Override
    public Set<RoleDto> toDto(Set<Role> entityList) {
        if ( entityList == null ) {
            return null;
        }

        Set<RoleDto> set = new LinkedHashSet<RoleDto>( Math.max( (int) ( entityList.size() / .75f ) + 1, 16 ) );
        for ( Role role : entityList ) {
            set.add( toDto( role ) );
        }

        return set;
    }
}
