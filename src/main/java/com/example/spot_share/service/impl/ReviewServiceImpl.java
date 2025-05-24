package com.example.spot_share.service.impl;

import com.example.spot_share.entity.Booking;
import com.example.spot_share.entity.ParkingSpot;
import com.example.spot_share.entity.Review;
import com.example.spot_share.entity.User;
import com.example.spot_share.repository.BookingRepository;
import com.example.spot_share.repository.ReviewRepository;
import com.example.spot_share.service.ReviewService;
import com.example.spot_share.util.api_response.ReviewRequest;
import com.example.spot_share.util.exception.BookingException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, BookingRepository bookingRepository) {
        this.reviewRepository = reviewRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Review saveReview(ReviewRequest request) {

        Booking booking = bookingRepository.findWithUserById(request.getBookingId())
                .orElseThrow(() -> new BookingException("booking details not found"));

        User user = booking.getParker();
        ParkingSpot parkingSpot = booking.getParkingSpot();

        Review review = new Review(user, parkingSpot, request.getComment(), request.getRating());

        return reviewRepository.save(review);
    }
}
