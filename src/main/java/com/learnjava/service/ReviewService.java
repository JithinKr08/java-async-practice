package com.learnjava.service;

import com.learnjava.domain.Review;

import static com.learnjava.util.CommonUtil.delay;

public class ReviewService {

    public Review retrieveReviews(String productId) {
        System.out.println("------>started Review");
        delay(10000);
        System.out.println("<-----returning reviews");
        return new Review(200, 4.5);
    }
}
