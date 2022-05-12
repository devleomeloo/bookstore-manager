package com.leonardo.bookstoremanager.config;

import com.leonardo.bookstoremanager.service.AuthenticationService;
import com.leonardo.bookstoremanager.service.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtTokenManager jwtTokenManager;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        var userName = "";
        var jwtToken = "";

        String requestTokenHeader = request.getHeader("Authorization");
        if (isTokenPresent(requestTokenHeader)){
            jwtToken = requestTokenHeader.substring(7);
            userName = jwtTokenManager.gerUserNameFromToken(jwtToken);
        } else {
            logger.warn("JWT Token does note begin with Bearer String");
        }

        if (isUserNameInContext(userName)){
            addUserNameInContext(request, userName, jwtToken);
        }

        chain.doFilter(request, response);
    }

    private boolean isTokenPresent(String requestTokenHeader) {
        return requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ");
    }

    private boolean isUserNameInContext(String userName) {
        return !userName.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private void addUserNameInContext(HttpServletRequest request, String userName, String jwtToken) {
        UserDetails userDetails = authenticationService.loadUserByUsername(userName);
        if (jwtTokenManager.validateToken(jwtToken, userDetails)){
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }
}
