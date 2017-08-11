package com.boco.msgl.modules.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.msgl.base.BaseDao;
import com.boco.msgl.base.BaseService;
import com.boco.msgl.modules.system.dao.ModuleDao;
import com.boco.msgl.modules.system.dto.Module;

@Service
public class ModuleService extends BaseService{
	@Autowired
	ModuleDao dao;
	
	@Override
	public BaseDao getDao() {
		return dao;
	}
	
	public List<Module> getModulesByRole(Integer roleId){
		return dao.getModulesByRole(roleId);
	}
	public List<Module> getModuleByUser(Integer UserId){
		return dao.getModuleByUser(UserId);
	}
	public List<Module> getAllParentModules(){
		return dao.getAllParentModules();
	}
	public List<Module> getChildrenModule(Integer parentId){
		return dao.getChildrenModule(parentId);
	}
	
	public List<Module> getParentModuleByUser(Integer userId){
		return dao.getParentModuleByUser(userId);
	}
	
	public List<Module> getChildrenByParentAndUser(Integer parentId, Integer userId){
		Map<String ,Integer> map = new HashMap<String, Integer>();
		map.put("parentId", parentId);
		map.put("userId", userId);
		return dao.getChildrenByParentAndUser(map);
	}
	
	public List<Module> getAllModules(){
		return dao.getAllModules();
	}
}
