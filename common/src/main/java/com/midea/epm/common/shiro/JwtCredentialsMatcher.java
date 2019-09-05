package com.midea.epm.common.shiro;

import com.midea.epm.common.entity.JWTResult;
import com.midea.epm.common.util.JWTUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class JwtCredentialsMatcher implements CredentialsMatcher {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * JwtCredentialsMatcher只需验证JwtToken内容是否合法
	 */
	@Override
	public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
		String token = authenticationToken.getCredentials().toString();
		String username = authenticationToken.getPrincipal().toString();
		JWTResult jwtResult = JWTUtils.getInstance().checkToken(token);
		return jwtResult.isStatus();
	}

}
