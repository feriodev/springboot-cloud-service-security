package com.feriodev.spring.cloud.security.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.feriodev.spring.cloud.security.entity.UserEntity;
import com.feriodev.spring.cloud.security.feign.UserFeignClient;
import com.feriodev.spring.cloud.security.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService, UserService{

	@Autowired
	private UserFeignClient client;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = client.findByUsername(username);
		
		if (user == null) {
			log.error("No existe el usuario en el sistema:: " + username);
			throw new UsernameNotFoundException("No existe el usuario en el sistema:: " + username);
		}
		
		List<GrantedAuthority> authorities = user.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.peek(auth -> log.info("Role:: " + auth.getAuthority()))
				.collect(Collectors.toList());
		log.info("Usuario autenticado con exito:: " + username);
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(), 
				user.getPassword(), 
				user.getStatus() == 1 ? true : false,
				true,
				true,
				true,
				authorities);
	}

	@Override
	public UserEntity findByUsername(String username) {
		return client.findByUsername(username);
	}

}
