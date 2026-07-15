package com.cfs.ShowTime.controller;


import com.cfs.ShowTime.entity.Screen;
import com.cfs.ShowTime.service.ScreenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
@RequiredArgsConstructor
public class ScreenController {

    private final ScreenService screenService;

    @GetMapping
    public ResponseEntity<List<Screen>> getAllScreen()
    {
        return ResponseEntity.ok(screenService.getAllScreen());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Screen> getScreenById(@PathVariable Long id)
    {
        return ResponseEntity.ok(screenService.getScreenById(id));
    }

    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<Screen>> getScreenByTheaterId(@PathVariable Long theaterId)
    {
        return ResponseEntity.ok(screenService.getScreenByTheater(theaterId));
    }
}
