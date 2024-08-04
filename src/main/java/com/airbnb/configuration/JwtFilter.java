package com.airbnb.configuration;

import com.airbnb.exception.InvalidJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private HandlerExceptionResolver handlerExceptionResolver;

    public JwtFilter(JwtHelper jwtHelper, UserDetailsService userDetailsService, HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(JwtConstant.JWT_HEADER);
        try{
            if(jwt !=null){
                if(!jwt.startsWith("Bearer"))
                    throw new InvalidJwtException("invalid jwt token");
                jwt = jwt.substring(7);
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
                String userName = String.valueOf(claims.get("username"));
                Authentication authentication =  new UsernamePasswordAuthenticationToken(userName,null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception e){
            e.printStackTrace();
            handlerExceptionResolver.resolveException(request,response,null,e);
            return;
        }
        filterChain.doFilter(request,response);
    }
}
