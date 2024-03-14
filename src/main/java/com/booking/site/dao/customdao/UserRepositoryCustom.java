package com.booking.site.dao.customdao;



import com.booking.site.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepositoryCustom {
    User save(User entity);

}
