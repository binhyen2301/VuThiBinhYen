package com.haivn.controller;

import com.haivn.common_api.Students;
import com.haivn.dto.StudentsDto;
import com.haivn.dto.SubjectStudentDto;
import com.haivn.dto.TblUserDto;
import com.haivn.service.ClassroomsService;
import com.haivn.service.StudentsService;
import com.llq.springfilter.boot.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.nio.file.FileSystemNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/api/students")
@RestController
@Slf4j
public class StudentController extends TextWebSocketHandler {

    private final StudentsService studentsService;
    public StudentController(StudentsService studentsService){
        this.studentsService = studentsService;
    }

    @GetMapping("/get/page")
    public ResponseEntity<Map<String, Object>> pageQuery(@Filter Specification<Students> spec, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Map<String, Object> result =new HashMap<String, Object>();
        try {
            Page<StudentsDto> tblUserPage = studentsService.findByCondition(spec, pageable);

            result.put("result",tblUserPage);
            result.put("success",true);
        } catch (Exception e) {
            result.put("result",e.getMessage());
            result.put("success",false);
        }
        return ResponseEntity.ok(result);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable("id") Long id) {
        Map<String, Object> result =new HashMap<String, Object>();
        try {
            StudentsDto studentsDto = studentsService.findById(id);
            result.put("result",studentsDto);
            result.put("success",true);
        } catch (Exception e) {
            result.put("result","Không tồn tại bản ghi");
            result.put("success",false);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(studentsService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        studentsService.deleteById(id);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Object> update(@RequestBody @Validated StudentsDto studentsDto, @PathVariable("id") Long id) {
        Map<String, Object> result =new HashMap<String, Object>();
        Optional.ofNullable(studentsService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        Students studentsDto1 =  studentsService.update(studentsDto,id);
        if (studentsDto1 != null) {
            result.put("result", studentsDto1);
            result.put("success",true);
        }
        return ResponseEntity.ok(result);
    }


    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody @Validated StudentsDto studentsDto) {
        Map<String, Object> result =new HashMap<String, Object>();
        Students studentsDto1 =  studentsService.save(studentsDto);
        if(studentsDto1 != null){
            result.put("result", studentsDto1);
            result.put("success",true);
        }
        return ResponseEntity.ok(result);
    }


    @PutMapping(value = "/upload-anh/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> nopBai(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Students students = studentsService.updateImg(file, id);
            result.put("result", students);

        } catch (Exception e) {
            result.put("result", e.toString());
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/khoa-tai-khoan/{id}")
    public ResponseEntity<Object> khoaTaiKhoan(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        Optional.ofNullable(studentsService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        StudentsDto studentsDto = studentsService.khoaTaiKhoan(id);
        if (studentsDto != null) {
            result.put("result", studentsDto);
            result.put("success",true);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/mo-tai-khoan/{id}")
    public ResponseEntity<Object> moTaiKhoan(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        Optional.ofNullable(studentsService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        StudentsDto studentsDto = studentsService.moTaiKhoan(id);
        if (studentsDto != null) {
            result.put("result", studentsDto);
            result.put("success",true);
        }
        return ResponseEntity.ok(result);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Object> khoaTaiKhoan(
            @RequestParam("name") String name,
            @RequestParam("password") String password,
            @RequestParam("fullName") String fullName,
            @RequestParam("studentCode") String studentCode,
            @RequestParam("classId") String classId,
            @RequestParam(value = "file",required = false) MultipartFile file,
            @PathVariable("id") Long id
    ) {
        Map<String, Object> result = new HashMap<String, Object>();
        Optional.ofNullable(studentsService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        StudentsDto studentsDto = studentsService.update(name,password,fullName,studentCode,classId,file,id);
        if (studentsDto != null) {
            result.put("result", studentsDto);
            result.put("success",true);
        }
        return ResponseEntity.ok(result);
    }
}
