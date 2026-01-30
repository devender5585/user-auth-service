package com.example.userauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.userauth.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Transactional
    @Query("delete from RefreshToken r where r.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
    
    
    @Modifying
    @Transactional
    @Query("delete from RefreshToken rt where rt.token = :token")
    void deleteByToken(@Param("token") String token);
}
