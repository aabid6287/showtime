package com.cfs.ShowTime.service;

import com.cfs.ShowTime.dto.BookingRequest;
import com.cfs.ShowTime.entity.*;
import com.cfs.ShowTime.enums.BookingStatus;
import com.cfs.ShowTime.repository.BookingRepository;
import com.cfs.ShowTime.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final UserService userService;
    private final ShowService showService;

    private User getCurrentAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            throw new AccessDeniedException("Authentication required");
        }
        return userService.getUserByEmail(auth.getName());
    }

    @Transactional
    public Booking createBooking(BookingRequest request) {
        User currentUser = getCurrentAuthenticatedUser();
        Show show = showService.getShowById(request.getShowId());

        //check if any of the requested seat are already booked
        List<Long> alreadyBookedSeats = bookingRepository.findBookingSeatIdsByShowId(show.getId());
        for(Long seatId : request.getSeatIds())
        {
            if(alreadyBookedSeats.contains(seatId))
            {
                throw new RuntimeException("Seat with id " + seatId + " is already Booked");
            }
        }
        List<Seat> seats = seatRepository.findAllById(request.getSeatIds());
        if(seats.size() != request.getSeatIds().size())
        {
            throw new RuntimeException("Some Seats Are Invalid");
        }

        double totalPrice = seats.size() * show.getTicketPrice();
        Booking booking = Booking.builder()
                .user(currentUser)
                .show(show)
                .seats(seats)
                .totalPrice(totalPrice)
                .status(BookingStatus.CONFIRMED)
                .build();
        return bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public Booking getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        User currentUser = getCurrentAuthenticatedUser();
        if (!"ADMIN".equalsIgnoreCase(currentUser.getRole()) && !booking.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Access denied: You do not own this booking");
        }
        return booking;
    }

    @Transactional(readOnly = true)
    public List<Booking> getBookingByUser(Long userId) {
        User currentUser = getCurrentAuthenticatedUser();
        if (!"ADMIN".equalsIgnoreCase(currentUser.getRole()) && !currentUser.getId().equals(userId)) {
            throw new AccessDeniedException("Access denied: Cannot view another user's bookings");
        }
        return bookingRepository.findByUserId(userId);
    }

    @Transactional
    public Booking cancelBooking(Long bookingId) {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    public List<Seat> getAvailableSeats(Long showId) {
        Show show = showService.getShowById(showId);
        List<Seat> allSeats = seatRepository.findByScreenId(show.getScreen().getId());
        List<Long> bookingSeatIds = bookingRepository.findBookingSeatIdsByShowId(showId);
        return allSeats.stream()
                .filter(seat -> !bookingSeatIds.contains(seat.getId()))
                .toList();
    }
}

