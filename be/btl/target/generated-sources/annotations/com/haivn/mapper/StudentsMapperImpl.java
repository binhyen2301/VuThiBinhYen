package com.haivn.mapper;

import com.haivn.common_api.Students;
import com.haivn.dto.StudentsDto;
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
public class StudentsMapperImpl implements StudentsMapper {

    @Override
    public Students toEntity(StudentsDto dto) {
        if ( dto == null ) {
            return null;
        }

        Students students = new Students();

        students.setId( dto.getId() );
        students.setModifiedUser( dto.getModifiedUser() );
        students.setModifiedDate( dto.getModifiedDate() );
        students.setCreatedUser( dto.getCreatedUser() );
        students.setCreatedDate( dto.getCreatedDate() );
        students.setDeleted( dto.isDeleted() );
        students.setName( dto.getName() );
        students.setPassword( dto.getPassword() );
        students.setFullName( dto.getFullName() );
        students.setStatus( dto.getStatus() );
        students.setStudentCode( dto.getStudentCode() );
        students.setClassId( dto.getClassId() );
        students.setDob( dto.getDob() );
        students.setUrlImage( dto.getUrlImage() );
        students.setClassrooms( dto.getClassrooms() );
        students.setRole_id( dto.getRole_id() );
        students.setRole( dto.getRole() );

        return students;
    }

    @Override
    public StudentsDto toDto(Students entity) {
        if ( entity == null ) {
            return null;
        }

        StudentsDto studentsDto = new StudentsDto();

        studentsDto.setId( entity.getId() );
        studentsDto.setModifiedUser( entity.getModifiedUser() );
        if ( entity.getModifiedDate() != null ) {
            studentsDto.setModifiedDate( new Timestamp( entity.getModifiedDate().getTime() ) );
        }
        studentsDto.setCreatedUser( entity.getCreatedUser() );
        if ( entity.getCreatedDate() != null ) {
            studentsDto.setCreatedDate( new Timestamp( entity.getCreatedDate().getTime() ) );
        }
        studentsDto.setDeleted( entity.isDeleted() );
        studentsDto.setName( entity.getName() );
        studentsDto.setPassword( entity.getPassword() );
        studentsDto.setFullName( entity.getFullName() );
        studentsDto.setStudentCode( entity.getStudentCode() );
        studentsDto.setStatus( entity.getStatus() );
        studentsDto.setClassId( entity.getClassId() );
        studentsDto.setDob( entity.getDob() );
        studentsDto.setClassrooms( entity.getClassrooms() );
        studentsDto.setRole_id( entity.getRole_id() );
        studentsDto.setRole( entity.getRole() );
        studentsDto.setUrlImage( entity.getUrlImage() );

        return studentsDto;
    }

    @Override
    public List<Students> toEntity(List<StudentsDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Students> list = new ArrayList<Students>( dtoList.size() );
        for ( StudentsDto studentsDto : dtoList ) {
            list.add( toEntity( studentsDto ) );
        }

        return list;
    }

    @Override
    public List<StudentsDto> toDto(List<Students> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StudentsDto> list = new ArrayList<StudentsDto>( entityList.size() );
        for ( Students students : entityList ) {
            list.add( toDto( students ) );
        }

        return list;
    }

    @Override
    public Set<StudentsDto> toDto(Set<Students> entityList) {
        if ( entityList == null ) {
            return null;
        }

        Set<StudentsDto> set = new LinkedHashSet<StudentsDto>( Math.max( (int) ( entityList.size() / .75f ) + 1, 16 ) );
        for ( Students students : entityList ) {
            set.add( toDto( students ) );
        }

        return set;
    }
}
