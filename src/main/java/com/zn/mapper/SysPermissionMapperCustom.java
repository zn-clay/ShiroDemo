package com.zn.mapper;

import java.util.List;

import com.zn.pojo.SysPermission;

public interface SysPermissionMapperCustom {
	//根据用户id查询菜单
	List<SysPermission>findMenuListByUserId(String userId);
	//根据用户id查询权限
	List<SysPermission>findPermissionListByUserId(String userId);
}
