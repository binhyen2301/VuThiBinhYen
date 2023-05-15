package com.haivn.repository;

import com.haivn.common_api.TblUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TblUserRepository extends JpaRepository<TblUser, Long>, JpaSpecificationExecutor<TblUser> {
      TblUser findByUsername(String username);


//    TblUser findByEmailAndPassword(String email, String password);
//    TblUser findByVerificationCode(String code);
//    TblUser findByNewPass(String newPass);
}