package com.job.jobportal.service;
//package com.email2.demo2.EmailServive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.job.jobportal.modeldto.EnquiryDto;

@Service
public class EmailService {

	 @Autowired
    private final JavaMailSender mailSender;
    
	// @Autowired
    
   
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @GetMapping("/contactus")
    public void sendSimpleEmail(String emailaddress ,EnquiryDto eq) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailaddress);
        message.setSubject("Corfirmation");
        message.setText("Welcome to "+eq.getName()
        +" visit my website/n Imidate Contact you,"
        		+ " Thank you");
        message.setFrom("hkchauhan1008@gmail.com"); // Set this to your verified "from" email

        mailSender.send(message);
    }
//
//	public void sendSimpleEmail() {
//		// TODO Auto-generated method stub
//		
//	}
}
