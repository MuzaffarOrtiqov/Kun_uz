package com.example.Kun_uz.entity;

import com.example.Kun_uz.enums.ProfileRole;
import com.example.Kun_uz.enums.ProfileStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
  //  @NotBlank(message = "Name is mandatory")
    private String name;
//    @NotBlank(message = "Surname is mandatory")
    @Column(name = "surname")
    private String surname;
    //    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;
    //    @NotBlank(message = "Phone is mandatory")
    @Column(name = "phone")
    private String phone;
    // @NotBlank(message = "Password is mandatory")
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;
  //  @NotNull(message = "Role should be specified")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ProfileRole role;
    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "photo_id")
    private Integer photoId;
}
