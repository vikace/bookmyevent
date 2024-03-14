package com.booking.site.custom;

import com.booking.site.entity.Authrorities;
import com.booking.site.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Transactional
@Component
public class UserInfoService implements UserDetailsService {
    @Autowired
private EntityManager em;
@Autowired
private HttpServletRequest request;

public UserInfoService()
{}
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

           TypedQuery<User> query= em.createQuery("SELECT u FROM User u WHERE u.email= :email", User.class);
           query.setParameter("email",email);
           User u=query.getSingleResult();
        System.out.println(u.getEmail());
        if(u==null)
            throw new UsernameNotFoundException("User with "+email+" not found");

        List<Authrorities> authList=u.getAuthrorities();
        List<GrantedAuthority> authorities=new ArrayList<>();
        if(authList!=null)
        {
            for(Authrorities i:authList)
            {
                authorities.add(new SimpleGrantedAuthority(i.getRole()));
            }
        }

          return new UserInfo(u,u.getPassword(),authorities);


    }


}
