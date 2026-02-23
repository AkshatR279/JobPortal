package com.akshatr.jobportal.repository;

import com.akshatr.jobportal.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT U FROM User U WHERE U.name = :username")
    public Optional<User> findByUsername(@Param("username") String username);
}
