package com.haivn.service;

import com.haivn.common_api.Classrooms;
import com.haivn.common_api.HeThongNguoiDungToken;
import com.haivn.dto.ClassroomsDto;
import com.haivn.handler.Utils;
import com.haivn.mapper.ClassroomsMapper;
import com.haivn.mapper.TblUserMapper;
import com.haivn.repository.ClassromsRepository;
import com.haivn.repository.TokenRepository;
import com.llq.springfilter.boot.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    public HeThongNguoiDungToken createToken(HeThongNguoiDungToken token){
        return tokenRepository.saveAndFlush(token);
    }


    public HeThongNguoiDungToken findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

}