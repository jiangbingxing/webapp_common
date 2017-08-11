package com.boco.msgl.modules.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.boco.msgl.base.BaseDao;
import com.boco.msgl.modules.system.dto.Module;

@Component
public interface ModuleDao extends BaseDao{
	public List<Module> getModulesByRole(Integer roleId);
	public List<Module> getModuleByUser(Integer UserId);
	public List<Module> getAllParentModules();
	public List<Module> getChildrenModule(Integer parentId);
	public List<Module> getParentModuleByUser(Integer userId);
	public List<Module> getChildrenByParentAndUser(Map<String, Integer> map);
	public List<Module> getAllModules();
}
