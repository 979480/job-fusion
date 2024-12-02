package com.job.jobportal.controller;

import com.job.jobportal.model.User;
import com.job.jobportal.modeldto.UserDto;
import com.job.jobportal.Repository.UserRepository;
import com.job.jobportal.service.OtpEmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api/otp")
public class OtpController {

    @Autowired
    private OtpEmailService otpService;

    @Autowired
    private UserRepository userRepository;

    private String email;
    
    // Step 1: Request OTP
    @PostMapping("/send")
    public String sendOtp(@ModelAttribute("dto") UserDto dto, HttpSession session, RedirectAttributes attrib) {
       try {
    	// Use dto.getEmail() to retrieve the email
        otpService.sendOtp(dto.getEmail());
        session.setAttribute("userEmail", dto.getEmail());
        attrib.addFlashAttribute("msg1", "Successfully sent OTP");
        return "redirect:/userlogin";
       }catch(Exception e) {
    	   attrib.addFlashAttribute("msg1", "Something went wrong"+e.getMessage());
    	   return "redirect:/userlogin";
       }
    }

    // Step 2: Validate OTP and Save User Details
    @PostMapping("/verify")
    public String verifyOtp(@ModelAttribute("userDto") UserDto userDto ,@RequestParam String enteredOtp, HttpSession session,RedirectAttributes attrib) {
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail != null && otpService.validateOtp(enteredOtp)) {
            // Save the user details in the database
            User user = new User();
            user.setEmail(userDto.getEmail());
            user.setUsername(userDto.getUsername());
            userRepository.save(user); // Save to DB

            session.setAttribute("user", userDto.getEmail()); // Set session attribute for logged-in user
            attrib.addFlashAttribute("msg","Scuessfuly login");
            return "redirect:/home"; // Redirect to home page
        }
        attrib.addFlashAttribute("msg","Please enter vaild otp");
        return "redirect:/userlogin"; // Redirect to login page if validation fails
    }
}
