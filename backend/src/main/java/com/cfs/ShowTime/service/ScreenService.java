package com.cfs.ShowTime.service;

import com.cfs.ShowTime.entity.Screen;
import com.cfs.ShowTime.repository.ScreenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreenService {

    private final ScreenRepository screenRepository;
    private final TheaterService theaterService;

    //addScreen

    public List<Screen> getAllScreen() {
        return screenRepository.findAll();
    }

    public Screen getScreenById(Long id) {
        return screenRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Screen not found with id: "+id));
    }

    public List<Screen> getScreenByTheater(Long theaterId) {
        return screenRepository.findByTheaterId(theaterId);
    }

}
