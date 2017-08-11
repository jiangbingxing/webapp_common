package com.boco.msgl.modules.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.boco.msgl.authenticate.Authenticate;

import com.boco.msgl.base.BaseController;
import com.boco.msgl.modules.system.dto.Module;
import com.boco.msgl.modules.system.dto.User;
import com.boco.msgl.modules.system.service.ModuleService;
import com.boco.msgl.modules.system.service.UserService;
import com.boco.msgl.util.ConstantUtil;
import com.boco.msgl.util.JsonUtil;
/**
 * 
 * @author someone
 *
 */
@RequestMapping("system")
@Controller
public class UserController extends BaseController{
	@Autowired
	private UserService userService;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private Authenticate authenticate;
	@RequestMapping("login")
	public String initLoginPage(){
		return "system/login";
	}
	
	@RequestMapping("loginvalidate")
	public void loginvalidate(HttpServletResponse response, HttpServletRequest request){
		User user = authenticate.getUser(response, request);
		if(user == null){
			printMessage(response, ConstantUtil.USER_LOGIN_FAILED);
		} else {
			printMessage(response, ConstantUtil.USER_LOGIN_SUCCESS);
			session.setAttribute(ConstantUtil.USER_IN_SESSION_ALIAS, user);
		}
	}
	
	@RequestMapping("homepage")
	public String initMainPage(HttpServletResponse response, HttpServletRequest request){
		//鐢ㄦ埛妯″潡鏍�
		return "home/homepage";
	}
	
	/**
	 * 鑾峰彇鐢ㄦ埛鏉冮檺
	 * @param response
	 * @param request
	 */
	@RequestMapping("privilege")
	public void privilege(HttpServletResponse response, HttpServletRequest request){
		printMessage(response, getModulesByUser());
	}
	
	@RequestMapping("logout")
	public String logout(HttpServletResponse response, HttpServletRequest request){
		destoryUserInfo(request);
		return "system/login";
	}
	
	private void destoryUserInfo(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.removeAttribute(ConstantUtil.USER_IN_SESSION_ALIAS);
	}
	
	private String getModulesByUser(){
		User user = getCurrentUser();
		List<Map<String, Object>> moduleList = new ArrayList<Map<String, Object>>();
		List<Module> parentList = moduleService.getParentModuleByUser(user.getId());
		if(parentList != null && parentList.size() != 0){
			for(Module parentModule : parentList){
				Map<String, Object> obj = new HashMap<String, Object>();
				List<Module> childrenModule = moduleService.getChildrenByParentAndUser(parentModule.getId(), user.getId());
				obj.put("parent", parentModule);
				obj.put("children", childrenModule);
				moduleList.add(obj);
			}
		}
		String result = JsonUtil.list2Json(moduleList);
		return result;
	/*	List<Map<String, Object>> moduleList = new ArrayList<Map<String, Object>>();
		List<Module> parentList = moduleService.getAllParentModules();
		if(parentList != null && parentList.size() != 0){
			for(Module parentModule : parentList){
				Map<String, Object> obj = new HashMap<String, Object>();
				List<Module> childrenModule = moduleService.getChildrenModule(parentModule.getId());
				obj.put("parent", parentModule);
				obj.put("children", childrenModule);
				moduleList.add(obj);
			}
		}
		String result = JsonUtil.list2Json(moduleList);
		return result;*/
	}
}
