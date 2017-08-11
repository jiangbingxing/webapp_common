package com.boco.msgl.modules.system.dao;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.boco.msgl.base.BaseDao;
import com.boco.msgl.modules.system.dto.User;

@Component
public interface UserDao extends BaseDao {
	User getUserByNameAndPassword(Map<String, String> map);
}
