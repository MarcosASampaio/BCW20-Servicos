package com.soulcode.Servicos.Security;

import com.soulcode.Servicos.Util.TokenUtils;
import com.soulcode.Servicos.Util.TokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.jsf.FacesContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;


public class AuthorizationFilter extends BasicAuthenticationFilter {
    private TokenUtils tokenUtils;

    public AuthorizationFilter(AuthenticationManager manager, TokenUtils tokenUtils) {
        super(manager);
        this.tokenUtils = tokenUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            UsernamePasswordAuthenticationToken authToken = getAuthentication(token.substring(7));
            if (authToken != null) {
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }
        chain.doFilter(request, response);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String login = tokenUtils.getLogin(token);
        if (login == null) {
            return null;
        }
        return new UsernamePasswordAuthenticationToken(login, null, new ArrayList<>());
    }


}
