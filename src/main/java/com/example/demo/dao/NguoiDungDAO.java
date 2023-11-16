package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.User;

public interface NguoiDungDAO extends JpaRepository<User, Integer> {
	User findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsById(int id);

}
