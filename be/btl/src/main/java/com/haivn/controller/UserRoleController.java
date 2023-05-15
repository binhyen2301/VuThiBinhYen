package com.haivn.controller;

import com.haivn.authenticate.SecurityConfig;
import com.haivn.dto.UserRoleDto;
import com.haivn.service.UserRoleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.file.FileSystemNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RequestMapping("/api/user-role")
@RestController
@Slf4j
@Api("user-role")
public class UserRoleController {
    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PostMapping("/post")
    public ResponseEntity<Map<String, Object>> save(@RequestBody @Validated UserRoleDto userRoleDto) {
        Map<String, Object> result = new HashMap<String, Object>();
        String messErr = "";
        if(userRoleDto.getUserId() == null){
            messErr += "id người dùng";
        }
        if (userRoleDto.getRoleId() == null){
            if(messErr != ""){
                messErr += ", quyền";
            }
            else {
                messErr += "quyền";
            }
        }
        if(messErr != ""){
            messErr = "Không được bỏ trống " + messErr;
            result.put("result", messErr);
            result.put("success",false);
        }
        else{
            try{
                UserRoleDto item = userRoleService.save(userRoleDto);
                result.put("result", item.getId());
                result.put("success",true);
            }
            catch (Exception e){
                result.put("result","Thêm không thành công");
                result.put("success",false);
            }
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable("id") Long id) {
        Map<String, Object> result =new HashMap<String, Object>();
        try{
            UserRoleDto userRole = userRoleService.findById(id);
            result.put("result",userRole);
            result.put("success",true);
        } catch (Exception e) {
            result.put("result","Không tồn tại bản ghi");
            result.put("success",false);
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        try{
            Optional.ofNullable(userRoleService.findById(id)).orElseThrow(() -> {
                log.error("Unable to delete non-existent data！");
                return new FileSystemNotFoundException();
            });
            userRoleService.deleteById(id);
        } catch (Exception e){
            result.put("success", false);
            result.put("result", "Xóa không thành công");
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get/page")
    public ResponseEntity<Map<String, Object>> pageQuery(UserRoleDto userRoleDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Map<String, Object> result =new HashMap<String, Object>();
        try {
            Page<UserRoleDto> userRolePage = userRoleService.findByCondition(userRoleDto, pageable);
            result.put("result",userRolePage);
            result.put("success",true);
        } catch (Exception e) {
            result.put("result",e.getMessage());
            result.put("success",false);
        }

        return ResponseEntity.ok(result);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Map<String, Object>> update(@RequestBody @Validated UserRoleDto userRoleDto, @PathVariable("id") Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        String messErr = "";
        if(userRoleDto.getUserId() == null){
            messErr += "id người dùng";
        }
        if (userRoleDto.getRoleId() == null){
            if(messErr != ""){
                messErr += ", quyền";
            }
            else {
                messErr += "quyền";
            }
        }
        if(messErr != ""){
            messErr = "Không được bỏ trống " + messErr;
            result.put("result", messErr);
            result.put("success",false);
        }
        else{
            try{
                UserRoleDto item = userRoleService.update(userRoleDto, id);
                result.put("result", item.getId());
                result.put("success",true);
            }
            catch (Exception e){
                result.put("result","Cập nhật không thành công");
                result.put("success",false);
            }
        }

        return ResponseEntity.ok(result);
    }
}