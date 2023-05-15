package com.haivn.repository;

import com.haivn.common_api.Role;
import com.haivn.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    Optional<Role> findByName(ERole name);

    @Query(value = "SELECT role.id, role.name,role.code, role.description, \n" +
            "FROM tb_user, role\n" +
            "WHERE tb_user.username = ?1 AND tb_user.role_id = role.id", nativeQuery = true)
    List<Role> findRoles(String username);

}