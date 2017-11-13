package com.assignment.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.assignment.entity.Users;

public interface UsersRepository extends CrudRepository<Users, Long> {
	
	List<Users> findByusername(String username);

}
