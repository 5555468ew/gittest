package com.hpe.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hpe.domain.User;
import com.hpe.service.UserService;

public class UserServiceImpl implements UserService {

	public void save(User user) {
		System.out.println(user);
	}

	public List<User> list() {
		User user=new User(1,"小苍","1234");
		List<User> list=new ArrayList<User>();
		list.add(user);
		return list;
	}

	
	
}
