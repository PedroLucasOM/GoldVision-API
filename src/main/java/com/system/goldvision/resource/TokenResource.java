package com.system.goldvision.resource;

import com.system.goldvision.config.property.GoldVisionProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/tokens")
@Api(value = "Tokens")
public class TokenResource {

    @Autowired
    private GoldVisionProperty property;

    @DeleteMapping("/revoke")
    @ApiOperation(value = "Logout")
    public void revoke(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(property.getSeguranca().isEnableHttps());
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath() + "/oauth/token");

        response.addCookie(cookie);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
