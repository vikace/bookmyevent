package com.booking.site.dao;

import com.booking.site.entity.Feature;
import org.hibernate.boot.jaxb.mapping.FetchableAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<Feature,Integer> {
}
