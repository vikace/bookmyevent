package com.booking.site.custom;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class UserIdAuthorization implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {

        if(isAdmin(authentication.get()))
        {
            return new AuthorizationDecision(true);
        }
        String uri=object.getRequest().getRequestURI();
        int userId=((UserInfo)authentication.get().getPrincipal()).getUser().getId();
        try
        {
            String idInStr=uri.split("/")[2];
            int id=Integer.parseInt(idInStr);
            return new AuthorizationDecision(id==userId);

        }
        catch (Exception e)
        {
            return new AuthorizationDecision(false);
        }
    }
    private boolean isAdmin(Authentication authentication)
    {
        List<GrantedAuthority> grantedAuthorities=authentication.getAuthorities().stream().filter((e)->e.getAuthority().equals("ROLE_ADMIN")).collect(Collectors.toList());
        return !grantedAuthorities.isEmpty();
    }
}
