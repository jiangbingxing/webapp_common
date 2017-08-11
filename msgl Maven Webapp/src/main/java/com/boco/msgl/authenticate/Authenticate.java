package com.boco.msgl.authenticate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.boco.msgl.modules.system.dto.User;
@Component
public interface Authenticate {
	User getUser(HttpServletResponse response, HttpServletRequest request);
}
