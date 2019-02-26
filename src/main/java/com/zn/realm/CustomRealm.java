package com.zn.realm;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.zn.pojo.ActiveUser;
import com.zn.pojo.SysPermission;
import com.zn.pojo.SysUser;
import com.zn.service.SysService;
import com.zn.service.SysServiceImpl;

public class CustomRealm extends AuthorizingRealm{
	@Resource
	private SysService sysService;
		@Override
	public void setName(String name) {
		super.setName("customRealm");
	}
		//认证
		@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
			// token是用户输入的用户名和密码 
			// 第一步从token中取出用户名
			String userCode = (String) token.getPrincipal();

			// 第二步：根据用户输入的userCode从数据库查询
			

			// 如果查询不到返回null
			/*if(!userCode.equals("zhangsansan")) {
				return null;
			}*/
			// 从数据库查询到密码
			//String password = "123456";
			SysUser sysUser = sysService.checkLogin(userCode);
			if(sysUser == null) {
				return null;
			}
			String password = sysUser.getPassword();
			// 如果查询到返回认证信息AuthenticationInfo
			//activeUser就是用户身份信息
			ActiveUser activeUser = new ActiveUser();
			activeUser.setUserid(sysUser.getId());
			activeUser.setUsercode(sysUser.getUsercode());
			activeUser.setUsername(sysUser.getUsername());
			//认证的时候还要加盐散列加密
			String salt = sysUser.getSalt();
			//根据用户id取出菜单
			List<SysPermission> menus  = null;
			try {
				//通过service取出菜单 
				menus = sysService.findMenuListByUserId(sysUser.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//将用户菜单 设置到activeUser
			activeUser.setMenus(menus);

			//将activeUser设置simpleAuthenticationInfo
			SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
					activeUser, password,ByteSource.Util.bytes(salt), this.getName());

			return simpleAuthenticationInfo;
		}
	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//从 principals获取主身份信息
				//将getPrimaryPrincipal方法返回值转为真实身份类型（在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo中身份类型），
				ActiveUser activeUser =  (ActiveUser) principals.getPrimaryPrincipal();
				
				//根据身份信息获取权限信息
				//从数据库获取到权限数据
				List<String> permissioncodes = new ArrayList<String>();
				List<SysPermission> permissions = null;
				try {
					permissions = sysService.findPermissionListByUserId(activeUser.getUserid());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(permissions != null) {
					for (SysPermission permission : permissions) {
						permissioncodes.add(permission.getPercode());
					}
				}
				/*List<String> permissions = new ArrayList<String>();
				permissions.add("user:create");//用户的创建
				permissions.add("item:query");//商品查询权限
				//....
				*/
				//查到权限数据，返回授权信息(要包括 上边的permissions)
				SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
				//将上边查询到授权信息填充到simpleAuthorizationInfo对象中
				simpleAuthorizationInfo.addStringPermissions(permissioncodes);

				return simpleAuthorizationInfo;
			}
			
			//清除缓存
			public void clearCached() {
				PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
				super.clearCache(principals);
			}


}
