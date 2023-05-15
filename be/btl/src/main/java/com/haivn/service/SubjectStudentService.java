package com.haivn.service;

import com.haivn.common_api.SubjectStudent;
import com.haivn.common_api.Subjects;
import com.haivn.dto.SubjectDto;
import com.haivn.dto.SubjectStudentDto;
import com.haivn.handler.Utils;
import com.haivn.mapper.SubjectMapper;
import com.haivn.mapper.SubjectStudentMapper;
import com.haivn.mapper.TblUserMapper;
import com.haivn.repository.SubjectRepository;
import com.haivn.repository.SubjectStudentRepository;
import com.llq.springfilter.boot.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.util.*;

@Slf4j
@Service
@Transactional
public class SubjectStudentService {
    private final SubjectStudentRepository repository;
    private final SubjectStudentMapper subjectMapper;

    @Value("${aam.upload.dir}")
    private String attachmentPath;

    @Value("${localhost.path}")
    private String hostPath;

    public SubjectStudentService(SubjectStudentRepository repository, SubjectStudentMapper subjectMapper) {
        this.repository = repository;
        this.subjectMapper = subjectMapper;

    }

    public SubjectStudentDto save(SubjectStudentDto subjectStudentDto) {
        SubjectStudent entity = subjectMapper.toEntity(subjectStudentDto);
        return subjectMapper.toDto(repository.save(entity));
    }

    public void saveAll(List<SubjectStudent> subjectStudentList) {
        repository.saveAll(subjectStudentList);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public SubjectStudentDto findById(Long id) {
        return subjectMapper.toDto(repository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Item Not Found! ID: " + id)
        ));
    }

    public Page<SubjectStudentDto> findByCondition(@Filter Specification<SubjectStudent> spec, Pageable pageable) {
        Page<SubjectStudent> entityPage = repository.findAll(spec, pageable);
        List<SubjectStudent> entities = entityPage.getContent();

        return new PageImpl<>(subjectMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public SubjectStudentDto dangKiNhom(SubjectStudentDto subjectStudentDto, Long id) {
        Optional<SubjectStudent> subjectStudent = repository.findById(id);
//        SubjectStudentDto data = findById(id);
//        SubjectStudent entity = subjectMapper.toEntity(subjectStudentDto);
//        entity.setGroupId(subjectStudentDto.getGroupId());
//        //BeanUtils.copyProperties(entity, data, Utils.getNullPropertyNames(entity));
//        SubjectStudent subjectStudent = repository.save(entity);

        subjectStudent.get().setGroupId(subjectStudentDto.getGroupId());
        SubjectStudent subjectStudent1 = repository.saveAndFlush(subjectStudent.get());
        return subjectMapper.toDto(subjectStudent1);
    }

    public SubjectStudentDto chamDiem(SubjectStudentDto subjectStudentDto, Long id) {
        Optional<SubjectStudent> subjectStudent = repository.findById(id);


        subjectStudent.get().setGroupId(subjectStudentDto.getGroupId());
        subjectStudent.get().setScore(subjectStudentDto.getScore());
        subjectStudent.get().setContent(subjectStudentDto.getContent());
        SubjectStudent subjectStudent1 = repository.saveAndFlush(subjectStudent.get());
        return subjectMapper.toDto(subjectStudent1);
    }


    public SubjectStudentDto update(SubjectStudentDto subjectStudentDto, Long id) {
        SubjectStudentDto data = findById(id);
        SubjectStudent entity = subjectMapper.toEntity(subjectStudentDto);
        entity.setGroupId(subjectStudentDto.getGroupId());
        BeanUtils.copyProperties(entity, data, Utils.getNullPropertyNames(entity));
        return save(subjectMapper.toDto(entity));
    }


    public SubjectStudentDto updateImgae(String mainImageUrl, Long id) {
        Optional<SubjectStudent> subjectStudent = repository.findById(id);
        subjectStudent.get().setFileName(mainImageUrl);
        subjectStudent.get().setStatus(1L);
        subjectStudent.get().setSubDate(new Date());
        SubjectStudent subjectStudentUpdate = repository.save(subjectStudent.get());
        return subjectMapper.toDto(subjectStudentUpdate);
    }


    public SubjectStudentDto uploadBT(MultipartFile uploadFile, Long subjectStudentId) {
        String absolutePath = this.attachmentPath + "/";
        String fileName = uploadFile.getOriginalFilename().split("\\.(?=[^\\.]+$)")[0];
        String extension = uploadFile.getOriginalFilename().split("\\.(?=[^\\.]+$)")[1];

        UUID fileNameImage = UUID.randomUUID();
        try {
            File fileToBeSaved = new File(absolutePath, fileNameImage + "." + extension);
            uploadFile.transferTo(fileToBeSaved);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String mainImageUrl = this.hostPath + "/api/subject-student/upload/getImage/" + fileNameImage + "." + extension;
        SubjectStudentDto subjectStudentDto = updateImgae(mainImageUrl, subjectStudentId);
        return subjectStudentDto;
    }

    public boolean existsBySubjectId(Long subjectId){
        return repository.existsBySubjectId(subjectId);
    }

    public boolean existsByStudentId(Long studentId){
        return repository.existsByStudentId(studentId);
    }

    public SubjectStudentDto findBySubjectIdAndStudentId(Long subjectId,Long studentId){
        Optional<SubjectStudent> subjectStudent = repository.findBySubjectIdAndStudentId(subjectId,studentId);
        if(subjectStudent.isEmpty()){
            return null;
        }
        SubjectStudentDto subjectStudentDto = subjectMapper.toDto(repository.findBySubjectIdAndStudentId(subjectId,studentId).get());
        return subjectStudentDto;
    }

}