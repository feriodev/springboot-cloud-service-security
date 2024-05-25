package com.feriodev.spring.cloud.security.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.feriodev.spring.cloud.security.entity.UserEntity;
import com.feriodev.spring.cloud.security.service.UserService;

@Component
public class InfoAditionalToken implements TokenEnhancer{

	@Autowired
	private UserService service;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<>();
		UserEntity user = service.findByUsername(authentication.getName());		
		info.put("name", user.getName());
		info.put("lastname", user.getLastname());
		info.put("email", user.getEmail());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info); 
		return accessToken;
	}

}
