package com.example.spot_share.controller;

import com.example.spot_share.entity.Review;
import com.example.spot_share.service.ReviewService;
import com.example.spot_share.util.api_response.ApiResponse;
import com.example.spot_share.util.api_response.ReviewRequest;
import com.example.spot_share.util.api_response.ReviewResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ReviewController {

    private final ReviewService reviewService;
    private final ModelMapper modelMapper;

    public ReviewController(ReviewService reviewService, ModelMapper modelMapper) {
        this.reviewService = reviewService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('PARKER')")
    @PostMapping("/reviews")
    public ResponseEntity<ApiResponse<ReviewResponse>> submitReview(@RequestBody ReviewRequest reviewRequest){
        Review review = reviewService.saveReview(reviewRequest);

        ReviewResponse reviewResponse = modelMapper.map(review, ReviewResponse.class);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "request submitted", reviewResponse));
    }
}
