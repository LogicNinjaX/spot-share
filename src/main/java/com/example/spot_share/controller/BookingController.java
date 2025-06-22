package com.example.spot_share.controller;

import com.example.spot_share.entity.Booking;
import com.example.spot_share.entity.ParkingSpot;
import com.example.spot_share.security.CustomUserDetails;
import com.example.spot_share.service.BookingService;
import com.example.spot_share.util.api_response.ApiResponse;
import com.example.spot_share.util.api_response.BookingResponse;
import com.example.spot_share.util.dto.BookingDetails;
import com.example.spot_share.util.dto.BookingWithoutParker;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class BookingController {

    private final ModelMapper modelMapper;
    private final BookingService bookingService;

    public BookingController(ModelMapper modelMapper, BookingService bookingService) {
        this.modelMapper = modelMapper;
        this.bookingService = bookingService;
    }

    @PreAuthorize("hasRole('PARKER')")
    @PostMapping("/bookings/{parking-id}")
    public ResponseEntity<ApiResponse<BookingResponse>> saveBooking(@PathVariable("parking-id") UUID parkingId, @AuthenticationPrincipal CustomUserDetails user){
        Booking booking = bookingService.saveBooking(user.getUserId(), parkingId);
        BookingResponse bookingResponse = modelMapper.map(booking, BookingResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "booking confirmed", bookingResponse));
    }

    @PreAuthorize("hasRole('PARKER')")
    @GetMapping("/bookings/my")
    public ResponseEntity<ApiResponse<?>> getBookingsWithoutParker(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @AuthenticationPrincipal CustomUserDetails user
    ){
        List<BookingWithoutParker> bookingWithoutParkers = bookingService.getBookingWithoutParker(user.getUserId(), pageNumber, pageSize);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "data retrieved successfully", bookingWithoutParkers));
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/bookings/owner/{parking-id}")
    public ResponseEntity<ApiResponse<List<BookingDetails>>> getBookingDetails(@PathVariable("parking-id") UUID parkingId,
                                                                               @RequestParam(defaultValue = "1") int pageNumber,
                                                                               @RequestParam(defaultValue = "10") int pageSize){
        List<BookingDetails> bookingDetails = bookingService.getBookingDetails(pageNumber, pageSize, parkingId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "data retrieved successfully", bookingDetails));

    }

    @PreAuthorize("hasRole('PARKER')")
    @PutMapping("/bookings/{booking-id}/cancel")
    public ResponseEntity<ApiResponse<?>> cancelBooking(@PathVariable("booking-id") UUID bookingId){
        boolean result = bookingService.cancelBooking(bookingId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "booking cancellation successful", null));
    }


    @PreAuthorize("hasAnyRole('PARKER', 'OWNER')")
    @GetMapping("/bookings/{booking-id}")
    public ResponseEntity<ApiResponse<BookingDetails>> getBookingDetails(@PathVariable("booking-id") UUID bookingId){
        BookingDetails bookingDetails = bookingService.getBookingDetails(bookingId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "data retrieved successfully", bookingDetails));
    }
}
