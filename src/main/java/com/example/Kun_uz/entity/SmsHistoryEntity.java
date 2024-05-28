package com.example.Kun_uz.entity;

import com.example.Kun_uz.enums.SmsStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "sms_history")
public class SmsHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "phone")
    private String phone;
    @Column(name = "message")
    private String message;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SmsStatus status;
    @Column(name = "type")
    private String type;
    @Column(name = "created_date")
    private LocalDateTime createdDate=LocalDateTime.now();
}
