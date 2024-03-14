package com.booking.site.filters;

import com.booking.site.custom.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtAuthFilter extends  OncePerRequestFilter {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserDetailsService uds;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookie=request.getCookies();
        String token=null;
        if(cookie==null)
        {
                filterChain.doFilter(request,response);
                return;
        }
        for(Cookie c:cookie)
        {
           token= c.getName().equals("bookmyeventToken")?c.getValue():null;
            System.out.println("Token detected: "+token);
        }
        if(token==null){
            filterChain.doFilter(request,response);
            return;
        }
        else if((jwtService.getExpiration(token).after(new Date(System.currentTimeMillis())))) {

           UserDetails ud=uds.loadUserByUsername(jwtService.getEmail(token));
            System.out.println(ud.getPassword());
            UsernamePasswordAuthenticationToken ut=new UsernamePasswordAuthenticationToken(ud,null,ud.getAuthorities());
            System.out.println("1");
            ut.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            System.out.println("2");
            SecurityContextHolder.getContext().setAuthentication(ut);
            System.out.println("3");
            System.out.println(request.getRequestURI());
        }
        filterChain.doFilter(request,response);
    }

}
