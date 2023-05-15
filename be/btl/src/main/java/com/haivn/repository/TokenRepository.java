package com.haivn.repository;

import com.haivn.common_api.HeThongNguoiDungToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<HeThongNguoiDungToken, Long> {
    HeThongNguoiDungToken findByToken(String token);
}