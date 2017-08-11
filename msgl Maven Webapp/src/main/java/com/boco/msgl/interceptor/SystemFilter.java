package com.boco.msgl.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.boco.msgl.modules.system.dto.User;
import com.boco.msgl.util.ConstantUtil;


public class SystemFilter implements Filter {

	Logger logger = Logger.getLogger(SystemFilter.class);
	static List<String> uncheckUrl = new ArrayList<String>();
	static {
		uncheckUrl.add("system/login.action");
		uncheckUrl.add("system/loginvalidate.action");
		uncheckUrl.add("system/logout.action");
	}
	
	private boolean inUncheck(String url){
		for(String unUrl : uncheckUrl){
			if(url.contains(unUrl))
				return true;
		}
		return false;
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
			throw new ServletException("OncePerRequestFilter just supports HTTP requests");
		}
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		HttpSession session = httpRequest.getSession(true);
		String home = httpRequest.getContextPath();
		StringBuffer url = httpRequest.getRequestURL();
		if(inUncheck(url.toString())){
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		} else {
			Object object = session.getAttribute(ConstantUtil.USER_IN_SESSION_ALIAS);
			User user = object == null ? null : (User) object;
			if (user == null) {
//				httpRequest.getRequestDispatcher("system/login.action").forward(httpRequest, httpResponse);
				String script = "<script>window.open ('"+httpRequest.getContextPath()+"/system/login.action','_top')</script>";
				httpResponse.getWriter().write(script);
				return;
			} else {
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}