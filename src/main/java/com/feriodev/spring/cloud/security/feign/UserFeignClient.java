package com.feriodev.spring.cloud.security.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.feriodev.spring.cloud.security.entity.UserEntity;

@FeignClient(name = "service-user")
public interface UserFeignClient {

	@GetMapping("/security/search/findUsername")
	public UserEntity findByUsername(@RequestParam String username);
}
