package com.zn.pojo;

import java.util.List;

/**
 * 封装用户身份信息（身份、凭证、角色、权限）
 * @author Administrator
 *
 */
public class ActiveUser {
	private String userid;//用户id
	private String usercode;//用户账号
	private String username;//用户名称
	private List<SysPermission> menus;// 菜单
	private List<SysPermission> permissions;// 权限
	
	public List<SysPermission> getMenus() {
		return menus;
	}
	public void setMenus(List<SysPermission> menus) {
		this.menus = menus;
	}
	public List<SysPermission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<SysPermission> permissions) {
		this.permissions = permissions;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
