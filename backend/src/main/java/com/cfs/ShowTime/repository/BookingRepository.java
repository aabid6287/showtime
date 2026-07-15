package com.cfs.ShowTime.repository;

import com.cfs.ShowTime.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

  List<Booking> findByUserId(Long userId);
  List<Booking> findByShowId(Long showId);

  //find all seat ids that are already booked for given show
  @Query("SELECT s.id FROM Booking b JOIN b.seats s WHERE b.show.id=:showId AND b.status='CONFIRMED'")
  List<Long> findBookingSeatIdsByShowId(@Param("showId") Long showId);
}
