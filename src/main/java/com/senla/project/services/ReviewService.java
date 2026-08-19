package com.senla.project.services;

import com.senla.project.models.Appointment;
import com.senla.project.models.DTO.requests.ReviewRequest;
import com.senla.project.models.DTO.responses.ReviewResponse;
import com.senla.project.models.Review;
import com.senla.project.models.enums.AppointmentStatus;
import com.senla.project.repositories.AppointmentRepository;
import com.senla.project.repositories.BarberRepository;
import com.senla.project.repositories.ReviewRepository;
import com.senla.project.utils.mapper.ReviewMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final AppointmentRepository appointmentRepository;
    private final BarberRepository barberRepository;
    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository, AppointmentRepository appointmentRepository, BarberRepository barberRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.appointmentRepository = appointmentRepository;
        this.barberRepository = barberRepository;
        this.reviewMapper = reviewMapper;
    }

    public ReviewResponse createReview(ReviewRequest request) {
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Запись не найдена"));
        if (appointment.getReview() != null) {
            throw new RuntimeException("Отзыв для этого посещения существует");
        }
        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new RuntimeException("Отзыв можно оставить только после посещения");
        }
        Review review = reviewMapper.toEntity(request);
        review.setAppointment(appointment);
        review.setCreatedBy(appointment.getClient());
        review.setUpdatedBy(appointment.getClient());
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDto(savedReview);
    }
    public ReviewResponse getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Отзыв с id " + id + " не найден"));
        return reviewMapper.toDto(review);
    }
    public Page<ReviewResponse> getReviewsByAppointmentBarberId(Long barberId, Pageable pageable) {
        if (!barberRepository.existsById(barberId)) {
            throw new RuntimeException("Барбер с ID " + barberId + " не найден");
        }
        Page<Review> reviewsPage = reviewRepository.findByAppointmentBarberId(barberId, pageable);
        return reviewsPage.map(review -> reviewMapper.toDto(review));
    }
    public Page<ReviewResponse> getAllReviews(Pageable pageable) {
        Page<Review> reviewsPage = reviewRepository.findAll(pageable);
        return reviewsPage.map(review -> reviewMapper.toDto(review));
    }
    public ReviewResponse updateReview(Long id, ReviewRequest request) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Отзыв с id " + id + " не найден"));
        reviewMapper.updateEntity(request, existingReview);
        Review updatedReview = reviewRepository.save(existingReview);
        return reviewMapper.toDto(updatedReview);
    }
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Отзыв с id " + id + " не найден"));
        review.getAppointment().setReview(null);
        reviewRepository.deleteById(id);
    }
}
