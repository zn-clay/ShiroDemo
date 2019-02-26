package com.zn.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zn.exception.LoginException;
import com.zn.pojo.ActiveUser;
import com.zn.service.SysService;

@Controller
public class LoginController {
	@Resource
	private SysService sysService;
	@RequestMapping("/loginPage")
	public String loginPage() {
		return "login";
	}
	@RequestMapping("/login")
	public String login(HttpServletRequest request) throws Exception {
		//获取验证码
		/*String validateCode = 
				(String) session.getAttribute("validateCode");
		if(!randomcode.equals(validateCode)) {
			return "redirect:/loginPage.action";
		}*/
		//如果登录失败从request中获取认证异常信息
		String shiroLoginFailure = (String) request.getAttribute("shiroLoginFailure");
		//根据shiro返回的异常，抛出指定类型异常
		if(shiroLoginFailure != null) {
			//身份信息异常
			if(UnknownAccountException.class.getName().equals(shiroLoginFailure)) {
				throw new LoginException("账号不存在");
			}else {
				if(IncorrectCredentialsException.class.getName().equals(shiroLoginFailure)) {
					throw new LoginException("用户名或密码错误");
				}else {
					throw new Exception();//未知异常交给容器
				}
			}
		}
		//调用Service方法执行登录认证
	/*	ActiveUser user = sysService.authenticat(usercode, password);
		session.setAttribute("activeUser", user);*/
		//此方法只做登录失败的处理，shiro认证成功后会自动刷新跳转到上一个路径
		//登录失败
		return "login";
	}
	
	
}
