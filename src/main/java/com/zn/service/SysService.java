package com.zn.service;

import java.util.List;

import com.zn.pojo.ActiveUser;
import com.zn.pojo.SysPermission;
import com.zn.pojo.SysUser;

//根据用户的身份信息和密码进行认证，如果认证通过则返回
//身份信息
public interface SysService {
	public ActiveUser authenticat(String usercode,String password)throws Exception;
	//根据用户id查询权限范围内的菜单
	public List<SysPermission> findMenuListByUserId(String userId)throws Exception;
	//根据用户id查询权限范围内的URL 
	public List<SysPermission> findPermissionListByUserId(String userId)throws Exception;
	//根据身份查找凭证
	public SysUser checkLogin(String usercode);
}
