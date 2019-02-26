package com.zn.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zn.exception.LoginException;
import com.zn.mapper.SysPermissionMapperCustom;
import com.zn.mapper.SysUserMapper;
import com.zn.pojo.ActiveUser;
import com.zn.pojo.SysPermission;
import com.zn.pojo.SysUser;
import com.zn.pojo.SysUserExample;
import com.zn.pojo.SysUserExample.Criteria;
import com.zn.util.MD5Util;
@Service
public class SysServiceImpl implements SysService{
	@Resource
	private SysUserMapper sysUserMapper;
	@Resource
	private SysPermissionMapperCustom sysPermissionMapperCustom;
	public ActiveUser authenticat(String usercode, String password) throws Exception {
		SysUser user = checkLogin(usercode);
		if(user == null) {
			throw new LoginException("用户不存在");
		}
		String password2 = user.getPassword();
		String md5 = MD5Util.md5(password);
		if(!password2.equals(md5)) {
			throw new LoginException("密码错误");
		}
		//用户成功认证之前要把相应的用户权限和menu放到Session中
		//得到用户id
		String id = user.getId();
		//根据用户id查询菜单
		List<SysPermission> menuList = sysPermissionMapperCustom.findMenuListByUserId(id);
		//根据用户id查询URL
		List<SysPermission> permissionList = sysPermissionMapperCustom.findPermissionListByUserId(id);
		//用户通过返回身份信息
		ActiveUser au = new ActiveUser();
		au.setUserid(user.getId());
		au.setUsername(user.getUsername());
		au.setUsercode(usercode);
		//放入权限范围内的菜单和URL
		au.setMenus(menuList);
		au.setPermissions(permissionList);
		return au;
	}
	public SysUser checkLogin(String usercode) {
		SysUserExample userExample = new SysUserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andUsercodeEqualTo(usercode);
		List<SysUser> list = sysUserMapper.selectByExample(userExample);
		if(list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	public List<SysPermission> findMenuListByUserId(String userId) throws Exception {
		return sysPermissionMapperCustom.findMenuListByUserId(userId);
	}
	public List<SysPermission> findPermissionListByUserId(String userId) throws Exception {
		return sysPermissionMapperCustom.findPermissionListByUserId(userId);
	}
}
