package com.haivn.controller;

import com.haivn.common_api.Subjects;
import com.haivn.dto.SubjectDto;
import com.haivn.service.SubjectService;
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
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.nio.file.FileSystemNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/api/subject")
@RestController
@Slf4j
public class SubjectController extends TextWebSocketHandler {

    private final SubjectService subjectService;
    public SubjectController(SubjectService subjectService){
        this.subjectService = subjectService;
    }

    @GetMapping("/get/page")
    public ResponseEntity<Map<String, Object>> pageQuery(@Filter Specification<Subjects> spec, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Map<String, Object> result =new HashMap<String, Object>();
        try {
            Page<SubjectDto> tblUserPage = subjectService.findByCondition(spec, pageable);

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
            SubjectDto subjectDto = subjectService.findById(id);
            result.put("result",subjectDto);
            result.put("success",true);
        } catch (Exception e) {
            result.put("result","Không tồn tại bản ghi");
            result.put("success",false);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(subjectService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        subjectService.deleteById(id);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Object> update(@RequestBody @Validated SubjectDto subjectDto, @PathVariable("id") Long id) {
        Map<String, Object> result =new HashMap<String, Object>();
        Optional.ofNullable(subjectService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        SubjectDto subjectDto1 =  subjectService.update(subjectDto,id);
        if (subjectDto1 != null) {
            result.put("result", subjectDto1);
            result.put("success",true);
        }
        return ResponseEntity.ok(result);
    }


    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody @Validated SubjectDto subjectDto) {
        Map<String, Object> result =new HashMap<String, Object>();
        SubjectDto subjectDto1 =  subjectService.save(subjectDto);
        if(subjectDto1 != null){
            result.put("result", subjectDto1);
            result.put("success",true);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/ket-thuc-hoc-phan/{id}")
    public ResponseEntity<Object> ketThucHocPhan(@RequestBody @Validated SubjectDto subjectDto, @PathVariable("id") Long id) {
        Map<String, Object> result =new HashMap<String, Object>();
        Optional.ofNullable(subjectService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        SubjectDto subjectDto1 =  subjectService.ketThucHocPhan(subjectDto,id);
        if (subjectDto1 != null) {
            result.put("result", subjectDto1);
            result.put("success",true);
        }
        return ResponseEntity.ok(result);
    }
}
