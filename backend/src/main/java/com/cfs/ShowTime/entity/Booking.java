package com.cfs.ShowTime.entity;

import com.cfs.ShowTime.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "show_id",nullable = false)
    private Show show;

    @ManyToMany
    @JoinTable(
            name = "booking_seats",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    private List<Seat> seats;

    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(name = "booked_at")
    private LocalDateTime bookAt;

    private void onCreate() {
        this.bookAt = LocalDateTime.now();
        if(this.status == null) {
            this.status = BookingStatus.CONFIRMED;
        }
    }
}
