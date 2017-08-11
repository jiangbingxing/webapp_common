package com.boco.msgl.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

public abstract class BaseService {
	/*mybatis中parameterMap中的通用key名*/
	private static final String BEAN_ALIAS = "bean";
	private static final String PAGE_ALIAS = "page";
	private static final String PAGESIZE_ALIAS = "pagesize";
	private static final String ORDER_CLUMN_ALIAS = "orderClumn";
	private static final String ORDER_ALIAS = "order";
	
	public abstract BaseDao getDao();


	/**
	 * 完整的查询
	 * @param bean 包装查询条件的javabean
	 * @param page 页码(符合人类习惯，从1开始)
	 * @param pageSize 页面大小
	 * @param orderColum 排序的列名称（数据表中的）
	 * @param order  asc || desc
	 * @return
	 */
	public List<BaseObject> getBeans(BaseObject bean, Integer page, Integer pageSize, String orderColum, String order) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(page == null || pageSize == null){
			paramMap.put(PAGE_ALIAS, null);
			paramMap.put(PAGESIZE_ALIAS, null);
		} else {
			Integer startIndex = (page - 1) * pageSize;
			paramMap.put(PAGE_ALIAS, startIndex);
			paramMap.put(PAGESIZE_ALIAS, pageSize);
		}
		paramMap.put(BEAN_ALIAS, bean);
		paramMap.put(ORDER_CLUMN_ALIAS, orderColum);
		paramMap.put(ORDER_ALIAS, order);
		return getDao().getBeans(paramMap);
	}
	
	
	/**
	 * 分页查询
	 * @param bean 包装查询条件的javabean
	 * @param page 
	 * @param pageSize
	 * @return
	 */
	public List<BaseObject> getBeans(BaseObject bean, Integer page, Integer pageSize) {
		return getBeans(bean, page, pageSize, null, null);
	}

	/**
	 * 根据条件查询所有列
	 * @param bean 包装查询条件的javabean
	 * @return
	 */
	public List<BaseObject> getBeans(BaseObject bean) {
		return getBeans(bean, null, null, null, null);
	}

	public <T extends BaseObject> T getBeanById(Integer id) {
		return getDao().getBeanById(id);
	}

	public void insert(BaseObject bean) {
		getDao().insert(bean);
	}

	public void update(BaseObject bean) {
		getDao().update(bean);
	}

	public void delete(Integer id) {
		getDao().delete(id);
	}
	

	/**
	 * 得到total数量的查询，配合前端使用，用来分页
	 * @param bean 包含查询条件的bean
	 * @return
	 */
	public Integer getCountByParam(BaseObject bean){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(BEAN_ALIAS, bean);
		return getDao().getCountByParam(paramMap);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void batchDelete(String ids) {
		getDao().batchDelete(ids);
	}

}
