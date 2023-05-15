package com.haivn.service;

import com.haivn.common_api.Subjects;
import com.haivn.dto.SubjectDto;
import com.haivn.handler.Utils;
import com.haivn.mapper.SubjectMapper;
import com.haivn.mapper.TblUserMapper;
import com.haivn.repository.SubjectRepository;
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
import javax.security.auth.Subject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class SubjectService {
    private final SubjectRepository repository;
    private final SubjectMapper subjectMapper;
    private final TblUserMapper tblUserMapper;
    @Autowired
    private JavaMailSender javaMailSender;



    @Autowired
    private UserRoleService userRoleService;

    public SubjectService(SubjectRepository repository, SubjectMapper subjectMapper, TblUserMapper tblUserMapper) {
        this.repository = repository;
        this.subjectMapper = subjectMapper;
        this.tblUserMapper =tblUserMapper;
    }

    public SubjectDto save(SubjectDto subjectDto) {
        Subjects entity = subjectMapper.toEntity(subjectDto);
        return subjectMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public SubjectDto findById(Long id) {
        return subjectMapper.toDto(repository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Item Not Found! ID: " + id)
        ));
    }

    public Page<SubjectDto> findByCondition(@Filter Specification<Subjects> spec, Pageable pageable) {
        Page<Subjects> entityPage = repository.findAll(spec, pageable);
        List<Subjects> entities = entityPage.getContent();
        List<SubjectDto> subjectDtos = new ArrayList<>();
//        for (var item :entities) {
//            ClassroomsDto classroomsDto=classroomsMapper.toDto(item);
//            classroomsDto.setUser(tblUserMapper.toDto(item.getUser()));
//            classroomsDtos.add(classroomsDto);
//        }
        return new PageImpl<>(subjectMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public SubjectDto update(SubjectDto subjectDto, Long id) {

        SubjectDto data = findById(id);
        Subjects entity = subjectMapper.toEntity(subjectDto);
        BeanUtils.copyProperties(entity, data, Utils.getNullPropertyNames(entity));
        return save(subjectMapper.toDto(entity));
    }

    public SubjectDto ketThucHocPhan(SubjectDto subjectDto, Long id) {
        Optional<Subjects> subject = repository.findById(id);
        subject.get().setStatus(1L);
        Subjects subjectUpdate = repository.save(subject.get());
        return subjectMapper.toDto(subjectUpdate);
    }

}