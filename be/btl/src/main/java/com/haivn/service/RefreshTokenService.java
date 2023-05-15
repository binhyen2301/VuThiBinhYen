//package com.haivn.service;
//
//import com.haivn.common_api.Classrooms;
//import com.haivn.common_api.RefreshToken;
//import com.haivn.dto.ClassroomsDto;
//import com.haivn.handler.Utils;
//import com.haivn.mapper.ClassroomsMapper;
//import com.haivn.mapper.TblUserMapper;
//import com.haivn.repository.ClassromsRepository;
//import com.haivn.repository.RefreshTokenRepository;
//import com.haivn.repository.TblUserRepository;
//import com.llq.springfilter.boot.Filter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityNotFoundException;
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Slf4j
//@Service
//@Transactional
//public class RefreshTokenService {
//    @Autowired
//    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, TblUserRepository userRepository) {
//        this.refreshTokenRepository = refreshTokenRepository;
//        this.userRepository = userRepository;
//    }
//
//    public Optional<RefreshToken> findByToken(String token) {
//        return refreshTokenRepository.findByToken(token);
//    }
//
//    public RefreshToken createRefreshToken(Long userId) {
//        RefreshToken refreshToken = new RefreshToken();
//        refreshToken.setTblUser(userRepository.findById(userId).get());
//        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtTokenValidity));
//        refreshToken.setToken(UUID.randomUUID().toString());
//        refreshToken = refreshTokenRepository.save(refreshToken);
//        return refreshToken;
//    }
//
//    public RefreshToken verifyExpiration(RefreshToken token) {
//        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
//            refreshTokenRepository.delete(token);
//            return null;
//        }
//        return token;
//    }
//
//    @Transactional
//    public int deleteByUserId(Long userId) {
//        return refreshTokenRepository.deleteByTblUser(userRepository.findById(userId).get());
//    }
//
//    @Value("${jwt.tokenValidity}")
//    public void setJwtTokenValidity(String jwtTokenValidity) {
//        Long time = Long.parseLong(jwtTokenValidity.replace("H", ""));
//        this.jwtTokenValidity = time * 60 * 60 * 1000; // 1H =  60 * 60 * 1000;
//    }
//
//    private Long jwtTokenValidity;
//    private final RefreshTokenRepository refreshTokenRepository;
//    private final TblUserRepository userRepository;
//
//}