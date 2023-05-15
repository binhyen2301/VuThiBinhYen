package com.haivn.controller;

import com.google.zxing.WriterException;
import com.haivn.common_api.Classrooms;
import com.haivn.common_api.TblUser;
import com.haivn.dto.ClassroomsDto;
import com.haivn.dto.TblUserDto;
import com.haivn.handler.NotificationHandler;
import com.haivn.service.ClassroomsService;
import com.haivn.service.TblUserService;
import com.llq.springfilter.boot.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/api/classroom")
@RestController
@Slf4j
public class ClassromsController extends TextWebSocketHandler {

    private final ClassroomsService classroomsService;
    public ClassromsController(ClassroomsService classroomsService){
        this.classroomsService = classroomsService;
    }

    @GetMapping("/get/page")
    public ResponseEntity<Map<String, Object>> pageQuery(@Filter Specification<Classrooms> spec, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Map<String, Object> result =new HashMap<String, Object>();
        try {
            Page<ClassroomsDto> tblUserPage = classroomsService.findByCondition(spec, pageable);

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
            ClassroomsDto classroomsDto = classroomsService.findById(id);
            result.put("result",classroomsDto);
            result.put("success",true);
        } catch (Exception e) {
            result.put("result","Không tồn tại bản ghi");
            result.put("success",false);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(classroomsService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        classroomsService.deleteById(id);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Object> update(@RequestBody @Validated ClassroomsDto classroomsDto, @PathVariable("id") Long id) {
        Map<String, Object> result =new HashMap<String, Object>();
        Optional.ofNullable(classroomsService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        ClassroomsDto classroomsDto1 =  classroomsService.update(classroomsDto,id);
        if (classroomsDto1 != null) {
            result.put("result", classroomsDto1);
            result.put("success",true);
        }
        return ResponseEntity.ok(result);
    }


    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody @Validated ClassroomsDto classroomsDto) {
        Map<String, Object> result =new HashMap<String, Object>();
        ClassroomsDto classroomsDto1 =  classroomsService.save(classroomsDto);
        if(classroomsDto1 != null){
            result.put("result", classroomsDto1);
            result.put("success",true);
        }
        return ResponseEntity.ok(result);
    }

}
