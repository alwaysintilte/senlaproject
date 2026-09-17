package com.senla.project.controllers;

import com.senla.project.models.DTO.requests.ReviewRequest;
import com.senla.project.models.DTO.responses.ReviewResponse;
import com.senla.project.services.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity createReview(@RequestBody ReviewRequest request) {
        return new ResponseEntity<>(reviewService.createReview(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getReviewById(@PathVariable Long id) {
        return new ResponseEntity<>(reviewService.getReviewById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllReviews(@PageableDefault(size = 10) Pageable pageable) {
        return new ResponseEntity<>(reviewService.getAllReviews(pageable), HttpStatus.OK);
    }

    @GetMapping("/barber/{barberId}")
    public ResponseEntity getReviewsByAppointmentBarberId(@PathVariable Long barberId, @PageableDefault(size = 10) Pageable pageable) {
        return new ResponseEntity<>(reviewService.getReviewsByAppointmentBarberId(barberId, pageable), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateReview(@PathVariable Long id, @RequestBody ReviewRequest request) {
        return new ResponseEntity<>(reviewService.updateReview(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
