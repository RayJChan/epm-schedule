package com.midea.epm.common.shiro;

import com.midea.epm.common.entity.JWTResult;
import com.midea.epm.common.util.JWTUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationToken;

public class JwtToken implements AuthenticationToken {

	private static final long serialVersionUID = 1L;

	private JWTResult jwtResult;

	// 加密后的 JWT token串
	private String token;

	private String userName;

	public JwtToken(String token) {
			Claims claims = JWTUtils.getInstance().getClaimsFromToken(token);
			this.token = token;
			if(null!=claims){
				this.userName = claims.get("username",String.class);
			}
	}

	@Override
	public Object getPrincipal() {
		return this.userName;
	}

	@Override
	public Object getCredentials() {
		return token;
	}


	public JWTResult getJwtResult() {
		return jwtResult;
	}

	public void setJwtResult(JWTResult jwtResult) {
		this.jwtResult = jwtResult;
	}
}
