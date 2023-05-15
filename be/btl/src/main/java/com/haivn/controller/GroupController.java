package com.haivn.controller;

import com.haivn.common_api.Group;
import com.haivn.dto.GroupDto;
import com.haivn.service.GroupService;
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

@RequestMapping("/api/groups")
@RestController
@Slf4j
public class GroupController extends TextWebSocketHandler {

    private final GroupService groupService;
    public GroupController(GroupService groupService){
        this.groupService = groupService;
    }

    @GetMapping("/get/page")
    public ResponseEntity<Map<String, Object>> pageQuery(@Filter Specification<Group> spec, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Map<String, Object> result =new HashMap<String, Object>();
        try {
            Page<GroupDto> tblUserPage = groupService.findByCondition(spec, pageable);

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
            GroupDto classroomsDto = groupService.findById(id);
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
        Optional.ofNullable(groupService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        groupService.deleteById(id);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Object> update(@RequestBody @Validated GroupDto classroomsDto, @PathVariable("id") Long id) {
        Map<String, Object> result =new HashMap<String, Object>();
        Optional.ofNullable(groupService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        GroupDto classroomsDto1 =  groupService.update(classroomsDto,id);
        if (classroomsDto1 != null) {
            result.put("result", classroomsDto1);
            result.put("success",true);
        }
        return ResponseEntity.ok(result);
    }


    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody @Validated GroupDto classroomsDto) {
        Map<String, Object> result =new HashMap<String, Object>();
        GroupDto classroomsDto1 =  groupService.save(classroomsDto);
        if(classroomsDto1 != null){
            result.put("result", classroomsDto1);
            result.put("success",true);
        }
        return ResponseEntity.ok(result);
    }

}
