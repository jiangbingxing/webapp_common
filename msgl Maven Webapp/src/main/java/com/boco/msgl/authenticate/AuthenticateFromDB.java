package com.boco.msgl.authenticate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boco.msgl.modules.system.dto.User;
import com.boco.msgl.modules.system.service.UserService;

@Component
public class AuthenticateFromDB implements Authenticate{
	@Autowired
	private UserService userService;
	@Override
	public User getUser(HttpServletResponse response, HttpServletRequest request) {
		String userName = request.getParameter("user_name");
		String password = request.getParameter("user_password");
		return userService.getUserByNameAndPassword(userName, password);
	}
}
