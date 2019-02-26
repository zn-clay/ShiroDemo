package com.zn.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//系统首页
import org.springframework.web.bind.annotation.RequestMapping;

import com.zn.pojo.ActiveUser;
@Controller
public class FirstController {
	@RequestMapping("/first")
	public String first(Model model) {
		//取出shiroSession中取出activeUser信息，展示到jsp页面中
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		model.addAttribute("activeUser", activeUser);
		return "first";
	}
}
