package com.boco.msgl.modules.system.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boco.msgl.base.BaseDao;
import com.boco.msgl.base.BaseService;
import com.boco.msgl.modules.system.dao.UserDao;
import com.boco.msgl.modules.system.dto.User;

@Service
public class UserService extends BaseService{
	@Autowired
	UserDao dao;
	@Override
	public BaseDao getDao() {
		return dao;
	}
	
	public User getUserByNameAndPassword(String userName, String password){
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("name", userName);
		hashMap.put("password", password);
		return this.dao.getUserByNameAndPassword(hashMap);
	}
	
}
