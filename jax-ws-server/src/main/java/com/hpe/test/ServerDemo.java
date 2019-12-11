package com.hpe.test;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

import com.hpe.service.UserService;
import com.hpe.service.impl.UserServiceImpl;

public class ServerDemo {
	
	public static void main(String[] args) {
		//创建服务器对象
		JaxWsServerFactoryBean factoryBean=new JaxWsServerFactoryBean();
		//发布地址
		factoryBean.setAddress("http://localhost:9088/userService");
		//发布的服务对象(暴露接口)
		factoryBean.setServiceClass(UserService.class);
		//发布服务器的目标
		factoryBean.setServiceBean(new UserServiceImpl());
		System.out.println("服务器运行成功");
		//运行服务器
		factoryBean.create();
		
	}

}
