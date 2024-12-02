package com.job.jobportal.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpEmailService {

    @Autowired
    private JavaMailSender mailSender;

    private String otp;

    // Generate OTP
    public String generateOtp() {
        otp = String.valueOf(new Random().nextInt(900000) + 100000); // 6-digit OTP
        return otp;
    }

    // Send OTP to email
    public void sendOtp(String email) {
        String generatedOtp = generateOtp();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP for Login");
        message.setText("Your OTP is: " + generatedOtp);

        mailSender.send(message);
    }

    // Validate OTP
    public boolean validateOtp(String enteredOtp) {
        return enteredOtp.equals(otp);
    }
}
