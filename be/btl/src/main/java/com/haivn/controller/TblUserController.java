package com.haivn.controller;

import com.haivn.authenticate.JwtUtil;
import com.haivn.authenticate.UserPrincipal;
import com.haivn.common_api.HeThongNguoiDungToken;
import com.haivn.common_api.TblUser;
import com.haivn.dto.*;
import com.haivn.service.*;
import com.llq.springfilter.boot.Filter;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.FileSystemNotFoundException;
import java.util.*;

@RequestMapping("/api/tbl-user")
@RestController
@Slf4j
@Api("tbl-user")
@CrossOrigin("*")
public class TblUserController {
    private TblUserService tblUserService;


    private RoleService roleService;


    private final int LOGIN = 1;

    public TblUserController(TblUserService tblUserService,RoleService roleService) {
        this.tblUserService = tblUserService;
        this.roleService = roleService;
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            TblUserDto tblUser = tblUserService.findById(id);
            //tblUser.setRole_id(userRoleService.findByRoleIdByUserId(tblUser.getId()));
            result.put("result", tblUser);
            result.put("success", true);
        } catch (Exception e) {
            result.put("result", "Không tồn tại bản ghi");
            result.put("success", false);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(tblUserService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        tblUserService.deleteById(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/get/page")
    public ResponseEntity<Map<String, Object>> pageQuery(@Filter Specification<TblUser> spec, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Page<TblUserDto> tblUserPage = tblUserService.findByCondition(spec, pageable);
            result.put("result", tblUserPage);
            result.put("success", true);
        } catch (Exception e) {
            result.put("result", e.getMessage());
            result.put("success", false);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody @Validated TblUserDto tblUserDto) {
        Map<String, Object> result =new HashMap<String, Object>();
        TblUserDto tblUserDto1 =  tblUserService.save(tblUserDto);
        if(tblUserDto != null){
            result.put("result", tblUserDto1);
            result.put("success",true);
        }
        return ResponseEntity.ok(result);
    }


    @PutMapping("/put/{id}")
    public ResponseEntity<Object> update(@RequestBody @Validated TblUserDto tblUserDto, @PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        Optional.ofNullable(tblUserService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        TblUserDto tblUserDto1 = tblUserService.update(tblUserDto,id);
        if (tblUserDto1 != null) {
            result.put("result", tblUserDto1);
            result.put("success",true);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/khoa-tai-khoan/{id}")
    public ResponseEntity<Object> khoaTaiKhoan(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        Optional.ofNullable(tblUserService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        TblUserDto tblUserDto = tblUserService.khoaTaiKhoan(id);
        if (tblUserDto != null) {
            result.put("result", tblUserDto);
            result.put("success",true);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/mo-tai-khoan/{id}")
    public ResponseEntity<Object> moTaiKhoan(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        Optional.ofNullable(tblUserService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new FileSystemNotFoundException();
        });
        TblUserDto tblUserDto = tblUserService.moTaiKhoan(id);
        if (tblUserDto != null) {
            result.put("result", tblUserDto);
            result.put("success",true);
        }
        return ResponseEntity.ok(result);
    }
}