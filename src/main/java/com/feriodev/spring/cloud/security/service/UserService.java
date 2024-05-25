package com.feriodev.spring.cloud.security.service;

import com.feriodev.spring.cloud.security.entity.UserEntity;

public interface UserService {

	public UserEntity findByUsername(String username);
}
