package com.example.Kun_uz.service;

import com.example.Kun_uz.dto.history.SmsHistoryDTO;
import com.example.Kun_uz.dto.auth.AuthDTO;
import com.example.Kun_uz.dto.auth.RegistrationDTO;
import com.example.Kun_uz.dto.auth.SmsLoginDTO;
import com.example.Kun_uz.dto.profile.ProfileDTO;
import com.example.Kun_uz.entity.ProfileEntity;
import com.example.Kun_uz.entity.SmsHistoryEntity;
import com.example.Kun_uz.enums.ProfileRole;
import com.example.Kun_uz.enums.ProfileStatus;
import com.example.Kun_uz.exp.AppBadException;
import com.example.Kun_uz.repository.ProfileRepository;
import com.example.Kun_uz.repository.SmsHistoryRepository;
import com.example.Kun_uz.util.JWTUtil;
import com.example.Kun_uz.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private EmailHistoryService emailHistoryService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private SmsHistoryService smsHistoryService;
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;

    ///register a user with email
    public String registrationWithEmail(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (optional.isPresent()) {
            throw new AppBadException("Email already exists");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMD5(dto.getPassword()));

        entity.setCreatedDate(LocalDateTime.now());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);

        profileRepository.save(entity);
        sendRegistrationEmail(entity.getId(), dto.getEmail());

        return "To complete your registration please verify your email.";
    }

    ///register a user with sms
    public String registrationWithPhone(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(dto.getPhone());
        if (optional.isPresent()) {
            throw new AppBadException("Phone already exists");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.getMD5(dto.getPassword()));

        entity.setCreatedDate(LocalDateTime.now());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);

        profileRepository.save(entity);
        smsService.sendSms(dto.getPhone());


        return "To complete your registration please confirm the code sent to your phone number.";

    }

    //check for  expiration date of email and user's existence in database
    public String authorizationVerification(Integer userId) {
        Optional<ProfileEntity> optional = profileRepository.findById(userId);
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }

        ProfileEntity entity = optional.get();
        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration not completed");
        }
   //     emailHistoryService.isNotExpiredEmail(entity.getEmail());// check for expiration date
        profileRepository.updateStatus(userId, ProfileStatus.ACTIVE);
        return "Success";
    }



    //Login for registered users
    public ProfileDTO loginWithEmail(AuthDTO authDTO) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(authDTO.getEmail());
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getPassword().equals(MD5Util.getMD5(authDTO.getPassword()))) {
            throw new AppBadException("Wrong password");
        }
        if (entity.getStatus() != ProfileStatus.ACTIVE) {
            throw new AppBadException("User is not active");
        }
        ProfileDTO dto = new ProfileDTO();
//        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole());
//      dto.setStatus(entity.getStatus());
        dto.setJwt(JWTUtil.encode(entity.getId(), entity.getEmail(), entity.getRole()));
        return dto;
    }


    // send email for user's in registration process
    public void sendRegistrationEmail(Integer profileId, String email) {
        // send email
   //     String token = JWTUtil.encode(profileId, ProfileRole.ROLE_USER);
        String url = "http://localhost:8080/auth/verificationWithEmail/" + profileId;
        String formatText = "<style>\n" +
                "    a:link, a:visited {\n" +
                "        background-color: #f44336;\n" +
                "        color: white;\n" +
                "        padding: 14px 25px;\n" +
                "        text-align: center;\n" +
                "        text-decoration: none;\n" +
                "        display: inline-block;\n" +
                "    }\n" +
                "\n" +
                "    a:hover, a:active {\n" +
                "        background-color: red;\n" +
                "    }\n" +
                "</style>\n" +
                "<div style=\"text-align: center\">\n" +
                "    <h1>Welcome to kun.uz web portal</h1>\n" +
                "    <br>\n" +
                "    <p>Please button lick below to complete registration</p>\n" +
                "    <div style=\"text-align: center\">\n" +
                "        <a href=\"%s\" target=\"_blank\">This is a link</a>\n" +
                "    </div>";
        String text = String.format(formatText, url);
        mailSenderService.send(email, "Complete registration", text);
        emailHistoryService.create(email, text); // create history
    }

    // Authorization link or code resend to email
    public String authorizeResend(String email) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(email);
        if (optional.isEmpty()) {
            throw new AppBadException("Email not found");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getVisible() && !(entity.getStatus().equals(ProfileStatus.REGISTRATION))) {
            throw new AppBadException("Registration  not completed");
        }
        emailHistoryService.checkEmailLimit(email);
        sendRegistrationEmail(entity.getId(), email);

        return "To complete your registration please verify your email!";
    }


    public String authorizationVerificationWithCode(SmsHistoryDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(dto.getPhone());
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }

        ProfileEntity entity = optional.get();
        smsHistoryService.isNotExpiredCode(entity.getPhone());// check for expiration date
        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration not completed");
        }

        Optional<SmsHistoryEntity> entityOptional = smsHistoryRepository.findTopByPhoneOrderByCreatedDateDesc(entity.getPhone());
        if (entityOptional.isEmpty()) {
            throw new AppBadException("SMS not found");
        }
        SmsHistoryEntity smsHistoryEntity = entityOptional.get();
        if (smsHistoryEntity.getMessage().equals(dto.getMessage())) {
            profileRepository.updateStatus(entity.getId(), ProfileStatus.ACTIVE);
        }

        return "Successfully registered";
    }

    public String resendCode(String phone) {
        // Check if user exists and is visible
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(phone);
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }
        ProfileEntity entity = optional.get();
        // Check if user is visible and registration is not completed
        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration not completed");
        }
        // Check if the user has exceeded the allowed number of SMS attempts
        smsHistoryService.countSms(phone);

        // Send the SMS
        smsService.sendSms(phone);
        return "Message was resent";
    }

    public String loginWithPhone(SmsLoginDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(dto.getPhone());
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getPassword().equals(MD5Util.getMD5(dto.getPassword()))) {
            throw new AppBadException("Wrong password");
        }
        if (entity.getStatus().equals(ProfileStatus.NOT_ACTIVE) || entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("User should be  active");
        }
        return "Success";
    }
}
