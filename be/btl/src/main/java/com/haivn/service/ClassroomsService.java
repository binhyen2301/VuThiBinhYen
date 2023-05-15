package com.haivn.service;

import com.haivn.common_api.Classrooms;
import com.haivn.common_api.TblUser;
import com.haivn.dto.ClassroomsDto;
import com.haivn.dto.TblUserDto;
import com.haivn.handler.Utils;
import com.haivn.mapper.ClassroomsMapper;
import com.haivn.mapper.TblUserMapper;
import com.haivn.repository.ClassromsRepository;
import com.haivn.repository.TblUserRepository;
import com.llq.springfilter.boot.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class ClassroomsService {
    private final ClassromsRepository repository;
    private final ClassroomsMapper classroomsMapper;
    private final TblUserMapper tblUserMapper;
    @Autowired
    private JavaMailSender javaMailSender;



    @Autowired
    private UserRoleService userRoleService;

    public ClassroomsService(ClassromsRepository repository, ClassroomsMapper classroomsMapper,TblUserMapper tblUserMapper) {
        this.repository = repository;
        this.classroomsMapper = classroomsMapper;
        this.tblUserMapper =tblUserMapper;
    }

    public ClassroomsDto save(ClassroomsDto classroomsDto) {
        Classrooms entity = classroomsMapper.toEntity(classroomsDto);
        return classroomsMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public ClassroomsDto findById(Long id) {
        return classroomsMapper.toDto(repository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Item Not Found! ID: " + id)
        ));
    }

    public ClassroomsDto findByCode(String code) {
        Classrooms classrooms = repository.findByclassCode(code);
        ClassroomsDto classroomsDto = classroomsMapper.toDto(classrooms);
        return classroomsDto;
    }

    public Page<ClassroomsDto> findByCondition(@Filter Specification<Classrooms> spec, Pageable pageable) {
        Page<Classrooms> entityPage = repository.findAll(spec, pageable);
        List<Classrooms> entities = entityPage.getContent();
        List<ClassroomsDto> classroomsDtos = new ArrayList<>();
        for (var item :entities) {
            ClassroomsDto classroomsDto=classroomsMapper.toDto(item);
            classroomsDto.setUser(tblUserMapper.toDto(item.getUser()));
            classroomsDtos.add(classroomsDto);
        }
        return new PageImpl<>(classroomsDtos, pageable, entityPage.getTotalElements());
    }

    public ClassroomsDto update(ClassroomsDto tblUserDto, Long id) {

        ClassroomsDto data = findById(id);
        Classrooms entity = classroomsMapper.toEntity(tblUserDto);
        BeanUtils.copyProperties(entity, data, Utils.getNullPropertyNames(entity));
        return save(classroomsMapper.toDto(entity));
    }

}