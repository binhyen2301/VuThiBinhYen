package com.haivn.controller;

import com.haivn.common_api.Role;
import com.haivn.common_api.TblUser;
import com.haivn.dto.RoleDto;
import com.haivn.dto.RoleDto;
import com.haivn.service.RoleService;
import com.haivn.service.TblUserService;
import com.llq.springfilter.boot.Filter;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.file.FileSystemNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/api/roles")
@RestController
@Slf4j
@Api("roles")
@CrossOrigin("*")
public class RoleController {


    private RoleService roleService;


    private final int LOGIN = 1;

    public RoleController(RoleService roleService) {

        this.roleService = roleService;
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            RoleDto roleDto = roleService.findById(id);
            //tblUser.setRole_id(userRoleService.findByRoleIdByUserId(tblUser.getId()));
            result.put("result", roleDto);
            result.put("success", true);
        } catch (Exception e) {
            result.put("result", "Không tồn tại bản ghi");
            result.put("success", false);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(roleService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        roleService.deleteById(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/get/page")
    public ResponseEntity<Map<String, Object>> pageQuery(@Filter Specification<Role> spec, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Page<RoleDto> tblUserPage = roleService.findByCondition(spec, pageable);
            result.put("result", tblUserPage);
            result.put("success", true);
        } catch (Exception e) {
            result.put("result", e.getMessage());
            result.put("success", false);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody @Validated RoleDto roleDto) {
        Map<String, Object> result =new HashMap<String, Object>();
        RoleDto tblUserDto1 =  roleService.save(roleDto);
        if(roleDto != null){
            result.put("result", tblUserDto1);
            result.put("success",true);
        }
        return ResponseEntity.ok(result);
    }


    @PutMapping("/put/{id}")
    public ResponseEntity<Object> update(@RequestBody @Validated RoleDto roleDto, @PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        Optional.ofNullable(roleService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        RoleDto tblUserDto1 = roleService.update(roleDto,id);
        if (tblUserDto1 != null) {
            result.put("result", tblUserDto1);
            result.put("success",true);
        }
        return ResponseEntity.ok(result);
    }
}