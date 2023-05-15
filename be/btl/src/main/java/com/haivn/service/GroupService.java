package com.haivn.service;

import com.haivn.common_api.Group;
import com.haivn.dto.GroupDto;
import com.haivn.dto.GroupDto;
import com.haivn.handler.Utils;
import com.haivn.mapper.ClassroomsMapper;
import com.haivn.mapper.GroupMapper;
import com.haivn.mapper.TblUserMapper;
import com.haivn.repository.ClassromsRepository;
import com.haivn.repository.GroupRepository;
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
public class GroupService {
    private final GroupRepository repository;
    private final GroupMapper groupMapper;





    @Autowired
    private UserRoleService userRoleService;

    public GroupService(GroupRepository repository, GroupMapper groupMapper) {
        this.repository = repository;
        this.groupMapper = groupMapper;

    }

    public GroupDto save(GroupDto groupDto) {
        Group entity = groupMapper.toEntity(groupDto);
        return groupMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public GroupDto findById(Long id) {
        return groupMapper.toDto(repository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Item Not Found! ID: " + id)
        ));
    }



    public Page<GroupDto> findByCondition(@Filter Specification<Group> spec, Pageable pageable) {
        Page<Group> entityPage = repository.findAll(spec, pageable);
        List<Group> entities = entityPage.getContent();
        List<GroupDto> groupDtoList = new ArrayList<>();
        for (var item :entities) {
            GroupDto groupDto=groupMapper.toDto(item);
            groupDtoList.add(groupDto);
        }
        return new PageImpl<>(groupDtoList, pageable, entityPage.getTotalElements());
    }

    public GroupDto update(GroupDto tblUserDto, Long id) {

        GroupDto data = findById(id);
        Group entity = groupMapper.toEntity(tblUserDto);
        BeanUtils.copyProperties(entity, data, Utils.getNullPropertyNames(entity));
        return save(groupMapper.toDto(entity));
    }

}