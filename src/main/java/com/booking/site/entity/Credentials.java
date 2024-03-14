package com.booking.site.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {
    private String email,password;
   private boolean remember;

}
