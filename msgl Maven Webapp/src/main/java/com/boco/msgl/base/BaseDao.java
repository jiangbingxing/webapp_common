package com.boco.msgl.base;

import java.util.List;
import java.util.Map;

public interface BaseDao {
	public BaseDao getMapper();
	
	public List<BaseObject> getBeans(Map<String, Object> map);
	
	public <T extends BaseObject> T getBeanById(Integer id);
	
	public Integer getCountByParam(Map<String, Object> map);
	
	public void insert(BaseObject bean);
	
	public void update(BaseObject bean);
	
	public void delete(Integer id);
	
	public void batchDelete(String ids);
}
