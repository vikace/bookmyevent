package com.booking.site.custom;

import com.booking.site.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserInfo implements UserDetails {
    private final String username,password;
    private User user;
   private final Collection<? extends GrantedAuthority> authorities;
    public UserInfo(com.booking.site.entity.User user, String password, Collection<? extends GrantedAuthority> authorities)
    {
        this.user=user;
        this.username=user.getEmail();
        this.password=password;
        this.authorities=authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public User getUser()
    {
        return this.user;
    }
}
