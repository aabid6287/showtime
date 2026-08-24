package com.cfs.ShowTime.repository;

import com.cfs.ShowTime.entity.Booking;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

  @EntityGraph(attributePaths = {"seats", "seats.screen", "seats.screen.theater", "seats.screen.theater.city", "show", "show.movie", "show.screen", "show.screen.theater", "show.screen.theater.city", "user"})
  List<Booking> findByUserId(Long userId);

  @EntityGraph(attributePaths = {"seats", "seats.screen", "seats.screen.theater", "seats.screen.theater.city", "show", "show.movie", "show.screen", "show.screen.theater", "show.screen.theater.city", "user"})
  Optional<Booking> findById(Long id);

  List<Booking> findByShowId(Long showId);

  //find all seat ids that are already booked for given show
  @Query("SELECT s.id FROM Booking b JOIN b.seats s WHERE b.show.id=:showId AND b.status='CONFIRMED'")
  List<Long> findBookingSeatIdsByShowId(@Param("showId") Long showId);
}
