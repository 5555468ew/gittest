package com.hpe.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.hpe.domain.Customer;
import com.hpe.utils.MainlUtil;

@Controller
@RequestMapping("customer")
public class CustomerController {
	
	@Resource
	private RedisTemplate redisTemplate;


	@RequestMapping("sendCode")
	@ResponseBody
	public String sendCode(String phone){
		Map<String, Object> map=new HashMap<String, Object>();
		//生成四位数验证码
		String verificationCode = String.valueOf((int)((Math.random()*9+1)*1000));

		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4Fc6xnooWo9F2wKEfRVX", "cRVpIBLVq6OtENBMZZ4etczVhL8AK8");
		IAcsClient client = new DefaultAcsClient(profile);
		CommonRequest request = new CommonRequest();
		//发送方式
		request.setMethod(MethodType.POST);
		request.setDomain("dysmsapi.aliyuncs.com");
		request.setVersion("2017-05-25");
		request.setAction("SendSms");

		request.putQueryParameter("RegionId", "cn-hangzhou");
		//手机号码
		request.putQueryParameter("PhoneNumbers", phone);
		//模板标签
		request.putQueryParameter("SignName", "BOS物流系统验证码");
		//短信模板
		request.putQueryParameter("TemplateCode", "SMS_178452083");
		//你的验证码为:${code}
		request.putQueryParameter("TemplateParam", "{\"code\":\""+verificationCode+"\"}");
		try {
			CommonResponse response = client.getCommonResponse(request);
			System.out.println(response.getData());
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
		} 

		String json=JSON.toJSONString(map);
		return json;
	} 

	@RequestMapping("saveCustomer")
	@ResponseBody
	public String saveCustomer(String telephone,String password,String checkCode,String inputemail){
		Customer customer=new Customer();
		customer.setUsername(telephone);
		customer.setTelephone(telephone);
		customer.setPassword(password);
		customer.setEmail(inputemail);
		Map<String, Object> map=new HashMap<>();
		try{
			//向服务器发送请求
			WebClient.create("http://localhost:9081/crm-web/service/customerService")
			.type(MediaType.APPLICATION_JSON)
			.post(customer);
			//激活码
			String activCode=UUID.randomUUID().toString();
			//发送邮件
			MainlUtil.sendSms(inputemail,telephone,activCode);
			//将激活码存到数据库中
			redisTemplate.opsForValue().set(telephone,activCode,48,TimeUnit.HOURS);
			map.put("success", true);
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
		}
		String json=JSON.toJSONString(map);
		return json;
	}


	@RequestMapping("activeCode")
	public void activeCode(HttpServletResponse response,String telephone,String activCode) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		//根据手机号码取出激活码
		String code=redisTemplate.opsForValue().get(telephone).toString();
		//判断激活码是否有效
		if(code==null||!code.equals(activCode)){
			response.getWriter().write("无效的激活码");
			return;
		}
		//激活用户
		WebClient.create("http://localhost:9081/crm-web/service/customerService/updateTypeByTelephone?telephone="+telephone)
		.put(null);
		response.getWriter().write("激活成功");
		//激活成功之后删除激活码
		redisTemplate.delete(telephone);
	}


}
