package com.senla.project.controllers;

import com.senla.project.models.DTO.requests.ReviewRequest;
import com.senla.project.models.DTO.responses.ReviewResponse;
import com.senla.project.services.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ReviewResponse createReview(@RequestBody ReviewRequest request) {
        return reviewService.createReview(request);
    }

    @GetMapping("/{id}")
    public ReviewResponse getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    @GetMapping
    public Page<ReviewResponse> getAllReviews(@PageableDefault(size = 10) Pageable pageable) {
        return reviewService.getAllReviews(pageable);
    }

    @GetMapping("/barber/{barberId}")
    public Page<ReviewResponse> getReviewsByAppointmentBarberId(@PathVariable Long barberId, @PageableDefault(size = 10) Pageable pageable) {
        return reviewService.getReviewsByAppointmentBarberId(barberId, pageable);
    }

    @PutMapping("/{id}")
    public ReviewResponse updateReview(@PathVariable Long id, @RequestBody ReviewRequest request) {
        return reviewService.updateReview(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}
