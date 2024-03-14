package com.booking.site.dao;

import com.booking.site.entity.Authrorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authrorities,Integer> {
}
