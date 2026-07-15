package com.cfs.ShowTime.repository;

import com.cfs.ShowTime.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreenRepository extends JpaRepository<Screen, Long> {

  List<Screen> findByTheaterId(Long theaterId);
}
