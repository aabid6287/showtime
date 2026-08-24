package com.cfs.ShowTime.entity;

import com.cfs.ShowTime.enums.SeatType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String seatNumber;

    @Column(name = "seat_row")
    private String row; // A K L

    @Column(name = "seat_col")
    private Integer col; //1, 2, 3

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "screen_id",nullable = false)
    private Screen screen;
}
