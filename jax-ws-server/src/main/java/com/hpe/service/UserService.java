package com.hpe.service;

import java.util.List;

import javax.jws.WebService;

import com.hpe.domain.User;

@WebService
public interface UserService {
	
	public void save(User user);
	
	public List<User> list();
	

}
