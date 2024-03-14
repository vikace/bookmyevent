package com.booking.site.filters;

import com.booking.site.custom.SerializationViewContextHolder;
import com.booking.site.custom.UserInfo;
import com.booking.site.entity.User;
import com.booking.site.jsonViews.Views;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JsonViewSetterFilter extends  OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null&&!(((UserInfo)authentication.getPrincipal()).getAuthorities().stream().filter(auth->auth.equals("ROLE_ADMIN")).toList().isEmpty()))
        {
            SerializationViewContextHolder.setView(Views.adminView.class);
        }
        else
        {
            SerializationViewContextHolder.setView(Views.userView.class);
        }
        filterChain.doFilter(request,response);
    }
}
