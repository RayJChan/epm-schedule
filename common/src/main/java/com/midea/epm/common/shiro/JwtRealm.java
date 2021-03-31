package com.midea.epm.common.shiro;

import java.util.Set;

import com.midea.epm.common.entity.JWTResult;
import com.midea.epm.common.util.JWTUtils;
import com.midea.epm.system.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;


/**
 * JwtRealm 只负责校验 JwtToken
 */
public class JwtRealm extends AuthorizingRealm {

	/**
	 * 限定这个 Realm 只处理我们自定义的 JwtToken
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JwtToken;
	}

	/**
	 * 此处的 SimpleAuthenticationInfo 可返回任意值，密码校验时不会用到它
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException{
		JwtToken jwtToken = (JwtToken) authcToken;
		JWTResult jwtResult = JWTUtils.getInstance().checkToken(jwtToken.getCredentials().toString());
		if(!jwtResult.isStatus()){
			jwtToken.setJwtResult(jwtResult);
			throw new  AuthenticationException(jwtResult.getMsg());
		}

//		if (jwtToken.getPrincipal() == null) {
//			throw new AccountException("JWT token参数异常！");
//		}
		// 从 JwtToken 中获取当前用户
		String username = jwtToken.getPrincipal().toString();
		// 查询数据库获取用户信息，此处使用 Map 来模拟数据库
		User user = ShiroRealm.userMap.get(username);

		// 用户不存在
//		if (user == null) {
//			throw new UnknownAccountException("用户不存在！");
//		}

		// 用户被锁定
//		if (user.getLocked()) {
//			throw new LockedAccountException("该用户已被锁定,暂时无法登录！");
//		}

		return new SimpleAuthenticationInfo(jwtResult, username, getName());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		return null;
	}

//	@Override
//	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		// 获取当前用户
//		User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
//		// UserEntity currentUser = (UserEntity) principals.getPrimaryPrincipal();
//		// 查询数据库，获取用户的角色信息
//		Set<String> roles = ShiroRealm.roleMap.get(currentUser.getUserName());
//		// 查询数据库，获取用户的权限信息
//		Set<String> perms = ShiroRealm.permMap.get(currentUser.getUserName());
//		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//		info.setRoles(roles);
//		info.setStringPermissions(perms);
//		return info;
//	}

}