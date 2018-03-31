package com.andy.spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andy.spring.jpa.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);

    void deleteById(Long id);
}
