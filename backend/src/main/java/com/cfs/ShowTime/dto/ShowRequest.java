package com.cfs.ShowTime.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowRequest {

    private Long movieId;
    private Long screenId;
    private LocalDate showDate;
    private LocalDate startTime;
    private LocalDate endTime;
    private Double ticketPrice;
}
