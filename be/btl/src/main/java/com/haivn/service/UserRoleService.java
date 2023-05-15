package com.haivn.service;

import com.haivn.common_api.UserRole;
import com.haivn.dto.UserRoleDto;
import com.haivn.handler.Utils;
import com.haivn.mapper.UserRoleMapper;
import com.haivn.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserRoleService {
    private final UserRoleRepository repository;
    private final UserRoleMapper userRoleMapper;

    public UserRoleService(UserRoleRepository repository, UserRoleMapper userRoleMapper) {
        this.repository = repository;
        this.userRoleMapper = userRoleMapper;
    }

    public UserRoleDto save(UserRoleDto userRoleDto) {
        UserRole entity = userRoleMapper.toEntity(userRoleDto);
        return userRoleMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public UserRoleDto findById(Long id) {
        return userRoleMapper.toDto(repository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Item Not Found! ID: " + id)
        ));
    }

    public Page<UserRoleDto> findByCondition(UserRoleDto userRoleDto, Pageable pageable) {
        Page<UserRole> entityPage = repository.findAll(pageable);
        List<UserRole> entities = entityPage.getContent();
        return new PageImpl<>(userRoleMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public UserRoleDto update(UserRoleDto userRoleDto, Long id) {
        UserRoleDto data = findById(id);
        UserRole entity = userRoleMapper.toEntity(userRoleDto);
        BeanUtils.copyProperties(entity, data, Utils.getNullPropertyNames(entity));
        return save(userRoleMapper.toDto(entity));
    }

    public List<UserRoleDto> findByUserId(Long userId){
        return userRoleMapper.toDto(repository.findByUserId(userId));
    }

    public List<Long> findByRoleIdByUserId(Long userId){
        List<Long> result = new ArrayList<>();

        List<UserRole> userRoleList = repository.findByUserId(userId);
        for (UserRole item:userRoleList) {
            result.add(item.getRoleId());
        }

        return result;
    }
}