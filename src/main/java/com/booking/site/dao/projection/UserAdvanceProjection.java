package com.booking.site.dao.projection;

public interface UserAdvanceProjection {
    byte[] getPfp();
    String getFname();
    String getLname();
    String getDob();
    String getLocation();
    boolean getVerified();
    String getPhoneNumber();
    String getEmail();
    int getId();

}
