package com.haivn.controller;

import com.haivn.authenticate.JwtUtil;
import com.haivn.authenticate.UserPrincipal;

import com.haivn.common_api.HeThongNguoiDungToken;
import com.haivn.dto.JwtResponse;
import com.haivn.dto.StudentsDto;
import com.haivn.dto.TblUserDto;
import com.haivn.service.StudentsService;
import com.haivn.service.TblUserService;
import com.haivn.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api")
@RestController
@Slf4j
public class JwtController extends TextWebSocketHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TblUserService tblUserService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private StudentsService studentsService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserPrincipal userPrincipal) {
        Map<String, Object> result = new HashMap<>();
        try {
            TblUserDto userDto = tblUserService.findByUserName(userPrincipal.getUsername());
            Boolean checkPass = BCrypt.checkpw(userPrincipal.getPassword(), userDto.getPassword());
            if (userDto == null) {
                result.put("result", "Đăng nhập thất bại (Tài khoản không tồn tại)");
                result.put("success", false);
            } else if (!checkPass) {
                result.put("result", "Đăng nhập thất bại (Sai mật khẩu)");
                result.put("success", false);
            } else if (userDto.getStatus() == 0) {
                result.put("result", "Đăng nhập thất bại (Tài khoản chưa được kích hoạt)");
                result.put("success", false);
            } else if (userDto.getStatus() == 0) {
                result.put("result", "Đăng nhập thất bại (Tài khoản đã bị xóa)");
                result.put("success", false);
            } else {
                userPrincipal.setUserId(userDto.getId());
                List<String> authorities = new ArrayList<>();
                authorities.add(userDto.getRole().getCode());
                userPrincipal.setAuthorities(authorities);
                String token = jwtUtil.generateToken(userPrincipal);

                HeThongNguoiDungToken hethongToken = new HeThongNguoiDungToken();
                hethongToken.setCreatedUser(userDto.getId());
                hethongToken.setToken(token);
                hethongToken.setTokenexpdate(jwtUtil.generateExpirationDate());

                tokenService.createToken(hethongToken);

                result.put("result", new JwtResponse(token, userDto, userDto.getRole_id()));
                result.put("success", true);
            }
        } catch (Exception e) {
            result.put("result", e.getMessage());
            result.put("success", false);
        }

        return ResponseEntity.ok(result);
    }


    @PostMapping(value = "/student/login")
    public ResponseEntity<?> loginStudent(@Valid @RequestBody UserPrincipal userPrincipal) {
        Map<String, Object> result = new HashMap<>();
        try {
            StudentsDto userDto = studentsService.findByUserName(userPrincipal.getUsername());
            Boolean checkPass = BCrypt.checkpw(userPrincipal.getPassword(), userDto.getPassword());
            if (!checkPass){
                result.put("result", "Sai tài khoản hoặc mật khẩu !");
                result.put("success", false);
                return ResponseEntity.ok(result);
            }
            if (userDto == null) {
                result.put("result", "Đăng nhập thất bại (Tài khoản không tồn tại)");
                result.put("success", false);
            } else if (userDto.getStatus() == 0) {
                result.put("result", "Đăng nhập thất bại (Tài khoản đã bị xóa)");
                result.put("success", false);
            }else{
                userPrincipal.setUserId(userDto.getId());
                List<String> authorities = new ArrayList<>();
                authorities.add(userDto.getRole().getCode());
                userPrincipal.setAuthorities(authorities);
                String token = jwtUtil.generateToken(userPrincipal);

                HeThongNguoiDungToken hethongToken = new HeThongNguoiDungToken();
                hethongToken.setCreatedUser(userDto.getId());
                hethongToken.setToken(token);
                hethongToken.setTokenexpdate(jwtUtil.generateExpirationDate());

                tokenService.createToken(hethongToken);

                result.put("result", new JwtResponse(token, userDto, userDto.getRole_id()));
                result.put("success", true);
            }
        } catch (Exception e) {
            result.put("result", e.getMessage());
            result.put("success", false);
        }

        return ResponseEntity.ok(result);
    }
}
