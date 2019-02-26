package com.zn.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zn.pojo.ActiveUser;
import com.zn.pojo.SysPermission;
import com.zn.util.ResourcesUtil;

public class PermissionInterceptor implements HandlerInterceptor{

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//在执行handler之前执行，用于用户认证、用户授权校验
		//得到请求的URL
		String url = request.getRequestURI();
		//判断是否是公开地址
		//实际开发的时候公开访问地址放在配置文件中
		//读取配置文件中的公开URL
		List<String> list = ResourcesUtil.gekeyList("commonURL");
		//遍历公开地址，如果是公开地址就放行
		for (String open_url : list) {
			if(url.indexOf(open_url)>=0) {
				return true;
			}
		}
		//从配置文件中获取公用的访问地址
		List<String> public_urls = ResourcesUtil.gekeyList("publicURL");
		//遍历公开地址，如果是公开地址就放行
		for (String public_url : public_urls) {
			if(url.indexOf(public_url)>=0) {
				//若果是公用地址就放行
				return true;
			}
		}
		//判断用户身份是否在Session中
		HttpSession session = request.getSession();
		ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
		//如果用户存在就放行
		if(activeUser != null) {
			//从Session中取出权限范围的URL
			List<SysPermission> permissions = activeUser.getPermissions();
			for (SysPermission sysPermission : permissions) {
				String url2 = sysPermission.getUrl();
				if(url.indexOf(url2)>=0) {
					//如果是权限允许的范围的地址就放行
					return true;
				}
			}
		}
		//执行到这里，跳转到登录界面，用户进行认证
		request.getRequestDispatcher("/WEB-INF/jsp/refuse.jsp").forward(request, response);
		return false;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
