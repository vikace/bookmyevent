package com.booking.site.custom;

import com.booking.site.dao.service.UserService;
import com.booking.site.entity.Event;
import com.booking.site.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
public class EventCreatorAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        if(isAdmin(authentication.get()))
        {
            return new AuthorizationDecision(true);
        }
        try
        {
            int eventId=Integer.parseInt(object.getRequest().getRequestURI().split("/")[2]);
            System.out.println(eventId);
            return new AuthorizationDecision(isCreator(authentication.get(),eventId));
        }
        catch(Exception e)
        {
            System.out.println("Exception in EventCreatorAuthorization "+e);
            return new AuthorizationDecision(false);
        }

    }
    private boolean isAdmin(Authentication authentication)
    {
        List<GrantedAuthority> grantedAuthorities=authentication.getAuthorities().stream().filter((e)->e.getAuthority().equals("ROLE_ADMIN")).collect(Collectors.toList());
        return !grantedAuthorities.isEmpty();
    }

    private boolean isCreator(Authentication authentication,int eventId)
    {
        User user=((UserInfo)authentication.getPrincipal()).getUser();
        //doing all this just to get the detached user entity to be reattacted toa session again
        // then only we can lazy load the created Events
        Session session=sessionFactory.openSession();
        Transaction tsn=session.beginTransaction();
       User u= session.merge(user);
        List<Event> eventList= u.getCreatedEvents().stream().filter(event->event.getId()==eventId).toList();
        tsn.commit();
        session.close();
        return !eventList.isEmpty();
    }
}
