package com.cfs.ShowTime.service;

import com.cfs.ShowTime.dto.TheaterRequest;
import com.cfs.ShowTime.entity.City;
import com.cfs.ShowTime.entity.Theater;
import com.cfs.ShowTime.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;
    private final CityService cityService;

    private Theater addTheater(TheaterRequest request) {
        City city=cityService.getCityById(request.getCityId());
        Theater theater=Theater.builder()
                .name(request.getName())
                .address(request.getAddress())
                .city(city)
                .build();
        return theaterRepository.save(theater);
    }

    public List<Theater> getAllTheaters() {
        return theaterRepository.findAll();
    }

    public Theater getTheaterById(Long id) {
        return theaterRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Theater not found with id: "+id));
    }

    public List<Theater> getTheaterByCity(Long cityId) {
        return theaterRepository.findByCityId(cityId);
    }


}
