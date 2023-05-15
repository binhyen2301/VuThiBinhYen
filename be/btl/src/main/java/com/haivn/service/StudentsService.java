package com.haivn.service;

import com.haivn.common_api.Role;
import com.haivn.common_api.Students;
import com.haivn.common_api.SubjectStudent;
import com.haivn.common_api.TblUser;
import com.haivn.dto.StudentsDto;
import com.haivn.dto.SubjectDto;
import com.haivn.dto.SubjectStudentDto;
import com.haivn.dto.TblUserDto;
import com.haivn.handler.Utils;
import com.haivn.mapper.StudentsMapper;
import com.haivn.mapper.TblUserMapper;
import com.haivn.repository.RoleRepository;
import com.haivn.repository.StudentsRepository;
import com.llq.springfilter.boot.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.util.*;

@Slf4j
@Service
@Transactional
public class StudentsService {

    private final StudentsRepository repository;
    private final RoleRepository roleRepository;
    private final StudentsMapper studentsMapper;
    private final TblUserMapper tblUserMapper;

    private PasswordEncoder bcryptEncoder;

    @Value("${aam.upload.dir}")
    private String attachmentPath;

    @Value("${localhost.path}")
    private String hostPath;

    public StudentsService(StudentsRepository repository,RoleRepository roleRepository, StudentsMapper studentsMapper, TblUserMapper tblUserMapper,PasswordEncoder bcryptEncoder) {
        this.repository = repository;
        this.studentsMapper = studentsMapper;
        this.tblUserMapper =tblUserMapper;
        this.bcryptEncoder = bcryptEncoder;
        this.roleRepository = roleRepository;
    }

    public Students save(StudentsDto studentsDto) {
        Students entity = new Students();
        entity.setName(studentsDto.getName());
        entity.setPassword(bcryptEncoder.encode(studentsDto.getDob()));
        entity.setFullName(studentsDto.getFullName());
        entity.setStudentCode(studentsDto.getStudentCode());
        entity.setClassId(studentsDto.getClassId());
        entity.setDob(studentsDto.getDob());
        entity.setRole_id(2L);
        entity.setStatus(1L);
        repository.save(entity);
        return entity;
    }


    public boolean existsByStudentCode(String studentCode){
        return repository.existsByStudentCode(studentCode);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public StudentsDto findById(Long id) {
        return studentsMapper.toDto(repository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Item Not Found! ID: " + id)
        ));
    }

    public Page<StudentsDto> findByCondition(@Filter Specification<Students> spec, Pageable pageable) {
        Page<Students> entityPage = repository.findAll(spec, pageable);
        List<Students> entities = entityPage.getContent();
        List<SubjectDto> subjectDtos = new ArrayList<>();

        return new PageImpl<>(studentsMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public Students update(StudentsDto studentsDto, Long id) {
        StudentsDto data = findById(id);
        Students entity = studentsMapper.toEntity(studentsDto);
        BeanUtils.copyProperties(entity, data, Utils.getNullPropertyNames(entity));
        return save(studentsMapper.toDto(entity));
    }



    public Students updateImg(MultipartFile uploadFile, Long id) {
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
        Students subjectStudentDto = updateImgae(mainImageUrl, id);
        return subjectStudentDto;
    }

    public String uploadImg(MultipartFile uploadFile){
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

        return mainImageUrl;
    }

    public Students updateImgae(String mainImageUrl, Long id) {
        Optional<Students> subjectStudent = repository.findById(id);
        subjectStudent.get().setUrlImage(mainImageUrl);

        Students students = repository.save(subjectStudent.get());
        return students;
    }



    public StudentsDto findByUserName(String userName){
        Students data = repository.findByName(userName);
        StudentsDto studentsDto = studentsMapper.toDto(data);
        return studentsDto;
    }

    public StudentsDto khoaTaiKhoan(Long id) {
        Optional<Students> tblUser = repository.findById(id);
        tblUser.get().setStatus(0L);
        Students tblUser1 = repository.save(tblUser.get());
        return studentsMapper.toDto(tblUser1);
    }

    public StudentsDto moTaiKhoan(Long id) {
        Optional<Students> tblUser = repository.findById(id);
        tblUser.get().setStatus(1L);
        Students tblUser1 = repository.save(tblUser.get());
        return studentsMapper.toDto(tblUser1);
    }
    /*
    * @RequestParam("name1") String name,
            @RequestParam("password") String password,
            @RequestParam("fullName") String fullName,
            @RequestParam("studentCode") String studentCode,
            @RequestParam("classId") String classId,
            @RequestParam("file") MultipartFile file,
            @PathVariable("id") Long id
    *
    * */

    public StudentsDto update( String name,String password,String fullName,String studentCode,String classId,MultipartFile file, Long id) {
        Optional<Students> students = repository.findById(id);
        students.get().setName(name);
        if(!password.equals(students.get().getPassword()) && !BCrypt.checkpw(password, students.get().getPassword())){
            students.get().setPassword(bcryptEncoder.encode(password));
        }
        students.get().setFullName(fullName);
        students.get().setStudentCode(studentCode);
        students.get().setClassId(Long.parseLong(classId));
        students.get().setName(name);
        if(file != null){
            students.get().setUrlImage(uploadImg(file));
        }
        Students entity =  repository.save(students.get());
        return studentsMapper.toDto(entity);
    }

}