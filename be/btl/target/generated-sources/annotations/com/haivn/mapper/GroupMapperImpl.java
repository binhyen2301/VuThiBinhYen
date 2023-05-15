package com.haivn.mapper;

import com.haivn.common_api.Group;
import com.haivn.dto.GroupDto;
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
public class GroupMapperImpl implements GroupMapper {

    @Override
    public Group toEntity(GroupDto dto) {
        if ( dto == null ) {
            return null;
        }

        Group group = new Group();

        group.setId( dto.getId() );
        group.setModifiedUser( dto.getModifiedUser() );
        group.setModifiedDate( dto.getModifiedDate() );
        group.setCreatedUser( dto.getCreatedUser() );
        group.setCreatedDate( dto.getCreatedDate() );
        group.setDeleted( dto.isDeleted() );
        group.setName( dto.getName() );
        group.setTille( dto.getTille() );
        group.setSubjectId( dto.getSubjectId() );
        group.setSubjects( dto.getSubjects() );

        return group;
    }

    @Override
    public GroupDto toDto(Group entity) {
        if ( entity == null ) {
            return null;
        }

        GroupDto groupDto = new GroupDto();

        groupDto.setId( entity.getId() );
        groupDto.setModifiedUser( entity.getModifiedUser() );
        if ( entity.getModifiedDate() != null ) {
            groupDto.setModifiedDate( new Timestamp( entity.getModifiedDate().getTime() ) );
        }
        groupDto.setCreatedUser( entity.getCreatedUser() );
        if ( entity.getCreatedDate() != null ) {
            groupDto.setCreatedDate( new Timestamp( entity.getCreatedDate().getTime() ) );
        }
        groupDto.setDeleted( entity.isDeleted() );
        groupDto.setSubjects( entity.getSubjects() );
        groupDto.setName( entity.getName() );
        groupDto.setTille( entity.getTille() );
        groupDto.setSubjectId( entity.getSubjectId() );

        return groupDto;
    }

    @Override
    public List<Group> toEntity(List<GroupDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Group> list = new ArrayList<Group>( dtoList.size() );
        for ( GroupDto groupDto : dtoList ) {
            list.add( toEntity( groupDto ) );
        }

        return list;
    }

    @Override
    public List<GroupDto> toDto(List<Group> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<GroupDto> list = new ArrayList<GroupDto>( entityList.size() );
        for ( Group group : entityList ) {
            list.add( toDto( group ) );
        }

        return list;
    }

    @Override
    public Set<GroupDto> toDto(Set<Group> entityList) {
        if ( entityList == null ) {
            return null;
        }

        Set<GroupDto> set = new LinkedHashSet<GroupDto>( Math.max( (int) ( entityList.size() / .75f ) + 1, 16 ) );
        for ( Group group : entityList ) {
            set.add( toDto( group ) );
        }

        return set;
    }
}
