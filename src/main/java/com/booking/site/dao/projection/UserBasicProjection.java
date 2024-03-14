package com.booking.site.dao.projection;

import com.booking.site.entity.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "userBasic",types = {User.class})
public interface UserBasicProjection {
    int getId();
    String getFname();
    String getLname();
}
