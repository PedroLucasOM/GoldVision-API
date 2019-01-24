package com.system.algamoney.resource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tokens")
public class TokenResource {

	@DeleteMapping("/revoke")
	public void revoke(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(true);
		cookie.setSecure(false);
		cookie.setMaxAge(0);
		cookie.setPath(request.getContextPath() + "/oauth/token");
		
		response.addCookie(cookie);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
}
