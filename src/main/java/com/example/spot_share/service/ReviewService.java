package com.example.spot_share.service;

import com.example.spot_share.entity.Review;
import com.example.spot_share.util.api_response.ReviewRequest;

public interface ReviewService {

    Review saveReview(ReviewRequest request);
}
