package com.hpe.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hpe.domain.User;
import com.hpe.service.UserService;

public class UserServiceImpl implements UserService {

	public void save(User user) {
		System.out.println(user);
	}

	public void update(User user){
		System.out.println("修改方法"+user);
	}

	public void delete(Integer id){
		System.out.println("执行删除方法"+id);
	}

	public List<User> findAll() {
		User user=new User(1,"小苍","1234");
		List<User> list=new ArrayList<User>();
		list.add(user);
		return list;
	}

	public User findById(Integer id){
		User user=new User();
		user.setId(1);
		user.setName("admin");
		user.setPass("123456");
		return user;
	}

}
