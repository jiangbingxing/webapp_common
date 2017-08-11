package com.boco.msgl.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ExceptionHandler implements HandlerExceptionResolver {
	Logger logger = Logger.getLogger(ExceptionHandler.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		logger.error(ex);
		String script = "<script>alert('net error!')</script>";
		try {
			response.getWriter().write(script);
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}

}