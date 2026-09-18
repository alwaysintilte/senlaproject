package com.senla.project.services;

import com.senla.project.exceptions.AlreadyExistsException;
import com.senla.project.exceptions.InvalidRequestDataException;
import com.senla.project.exceptions.NotFoundException;
import com.senla.project.models.Appointment;
import com.senla.project.models.DTO.requests.ReviewRequest;
import com.senla.project.models.DTO.responses.ReviewResponse;
import com.senla.project.models.Review;
import com.senla.project.models.User;
import com.senla.project.models.enums.AppointmentStatus;
import com.senla.project.repositories.AppointmentRepository;
import com.senla.project.repositories.BarberRepository;
import com.senla.project.repositories.ReviewRepository;
import com.senla.project.utils.AuditingService;
import com.senla.project.utils.mapper.ReviewMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final AppointmentRepository appointmentRepository;
    private final BarberRepository barberRepository;
    private final ReviewMapper reviewMapper;
    private final AuditingService auditingService;


    public ReviewService(ReviewRepository reviewRepository, AppointmentRepository appointmentRepository, BarberRepository barberRepository, ReviewMapper reviewMapper, AuditingService auditingService) {
        this.reviewRepository = reviewRepository;
        this.appointmentRepository = appointmentRepository;
        this.barberRepository = barberRepository;
        this.reviewMapper = reviewMapper;
        this.auditingService = auditingService;
    }

    @Transactional
    public ReviewResponse createReview(ReviewRequest request) {
        User auditingUser = auditingService.getCurrentAuditor();
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new NotFoundException("Appointment"));
        if (appointment.getReview() != null) {
            throw new AlreadyExistsException("Review");
        }
        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new InvalidRequestDataException("Review can be left only after appointment", HttpStatus.BAD_REQUEST);
        }
        Review review = reviewMapper.toEntity(request);
        review.setAppointment(appointment);
        review.setCreatedBy(auditingUser);
        review.setUpdatedBy(auditingUser);
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDto(savedReview);
    }
    public ReviewResponse getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review"));
        return reviewMapper.toDto(review);
    }
    public Page<ReviewResponse> getReviewsByAppointmentBarberId(Long barberId, Pageable pageable) {
        if (!barberRepository.existsById(barberId)) {
            throw new NotFoundException("Barber");
        }
        Page<Review> reviewsPage = reviewRepository.findByAppointmentBarberId(barberId, pageable);
        return reviewsPage.map(review -> reviewMapper.toDto(review));
    }
    public Page<ReviewResponse> getAllReviews(Pageable pageable) {
        Page<Review> reviewsPage = reviewRepository.findAll(pageable);
        return reviewsPage.map(review -> reviewMapper.toDto(review));
    }
    @Transactional
    public ReviewResponse updateReview(Long id, ReviewRequest request) {
        User auditingUser = auditingService.getCurrentAuditor();
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review"));
        reviewMapper.updateEntity(request, existingReview);
        existingReview.setUpdatedBy(auditingUser);
        Review updatedReview = reviewRepository.save(existingReview);
        return reviewMapper.toDto(updatedReview);
    }
    @Transactional
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review"));
        review.getAppointment().setReview(null);
        reviewRepository.deleteById(id);
    }
}
