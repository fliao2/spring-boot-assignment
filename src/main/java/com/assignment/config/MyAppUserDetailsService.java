package com.assignment.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.assignment.dao.UsersRepository;
import com.assignment.entity.Users;

@Service
public class MyAppUserDetailsService implements UserDetailsService {
	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {		
		List<Users> list = usersRepository.findByusername(userName);
		Users users = list.get(0);
		
		GrantedAuthority authority = new SimpleGrantedAuthority(users.getRole());
		UserDetails userDetails = (UserDetails)new User(users.getUsername(), users.getPassword(), Arrays.asList(authority));
		
		return userDetails;
	}
}
