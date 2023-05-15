package com.haivn.service;

import com.haivn.common_api.Role;
import com.haivn.dto.RoleDto;
import com.haivn.enums.ERole;
import com.haivn.handler.Utils;
import com.haivn.mapper.RoleMapper;
import com.haivn.mapper.TblUserMapper;
import com.haivn.repository.RoleRepository;
import com.haivn.repository.RoleRepository;
import com.llq.springfilter.boot.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class RoleService {
    private final RoleRepository repository;
    private final RoleMapper classroomsMapper;
    private final TblUserMapper tblUserMapper;
    @Autowired
    private JavaMailSender javaMailSender;



    @Autowired
    private UserRoleService userRoleService;

    public RoleService(RoleRepository repository, RoleMapper classroomsMapper, TblUserMapper tblUserMapper) {
        this.repository = repository;
        this.classroomsMapper = classroomsMapper;
        this.tblUserMapper =tblUserMapper;
    }

    public RoleDto save(RoleDto roleDto) {
        Role entity = classroomsMapper.toEntity(roleDto);
        return classroomsMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public RoleDto findById(Long id) {
        return classroomsMapper.toDto(repository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Item Not Found! ID: " + id)
        ));
    }

    public RoleDto findByName(ERole name) {
        Role classrooms = repository.findByName(name).get();
        RoleDto roleDto = classroomsMapper.toDto(classrooms);
        return roleDto;
    }

    public Page<RoleDto> findByCondition(@Filter Specification<Role> spec, Pageable pageable) {
        Page<Role> entityPage = repository.findAll(spec, pageable);
        List<Role> entities = entityPage.getContent();
        List<RoleDto> classroomsDtos = new ArrayList<>();

        return new PageImpl<>(classroomsMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public RoleDto update(RoleDto roleDto, Long id) {

        RoleDto data = findById(id);
        Role entity = classroomsMapper.toEntity(roleDto);
        BeanUtils.copyProperties(entity, data, Utils.getNullPropertyNames(entity));
        return save(classroomsMapper.toDto(entity));
    }

}