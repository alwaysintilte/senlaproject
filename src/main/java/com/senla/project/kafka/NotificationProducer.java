package com.senla.project.kafka;

import com.senla.project.models.DTO.requests.notifications.NotificationRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {
    private final KafkaTemplate<String, NotificationRequest> kafkaTemplate;
    public NotificationProducer(KafkaTemplate<String, NotificationRequest> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendNotificationRequest(NotificationRequest request){
        kafkaTemplate.send("booking-notifications", request);
    }
}
