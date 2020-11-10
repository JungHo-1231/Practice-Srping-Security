package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;

// CRUD 함수를 JpaReposityor가 들고 있음.
// @Repostitory라는 어노테이션이 없어도 ioc됩니다. 이유는 JpaRepository를 상속했기 때문에
public interface UserRepository extends JpaRepository<User, Integer> {

	// findBy 규칙 -> Username 문법
	// select * from user where username = ?
	public User findByUsername(String username); //Jpa query method

}
