package com.booking.site.dao;

import com.booking.site.entity.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CarouselRepository extends JpaRepository<Carousel,Integer> {
}
