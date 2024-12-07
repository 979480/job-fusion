package com.job.jobportal.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.job.jobportal.Repository.AdminLoginReopo;
import com.job.jobportal.Repository.EnquiryRepo;
import com.job.jobportal.Repository.JobRepository;
import com.job.jobportal.Repository.UserRepository;
import com.job.jobportal.model.Enquiry;
import com.job.jobportal.model.Job;
import com.job.jobportal.modeldto.EnquiryDto;
import com.job.jobportal.modeldto.UserDto;
import com.job.jobportal.service.EmailService;
import com.job.jobportal.smsapi.SmsSender;

import jakarta.servlet.http.HttpSession;

import com.job.jobportal.modeldto.AdminLoginDto;
import com.job.jobportal.model.AdminLogin;
@Controller
public class MainController {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	EnquiryRepo erepo;
	
	@Autowired
	AdminLoginReopo adrepo;
	
	@GetMapping("/home")
	public String ShowIndex() {
		return "index";
	}
	 @Autowired
	 EmailService emailService;

	     @Autowired
	     private JobRepository jobRepository; // Inject JobRepository

//	     @GetMapping("/")
//	     public String showHomePage(Model model) {
//	         List<Job> jobs = jobRepository.findAll(); // Fetch all jobs from the database
//	         model.addAttribute("jobs", jobs); // Add the jobs list to the model
//	         return "index"; // Return the name of the Thymeleaf template for the homepage
//	     }
	     @GetMapping("/")
	     public String getJobs(Model model) {
	         List<Job> jobs = jobRepository.findAll();

	         // Partition jobs into sublists of size 2
	         List<List<Job>> partitionedJobs = new ArrayList<>();
	         for (int i = jobs.size(); i > 0; i -= 2) {
	             int start = Math.max(i - 2, 0); // Calculate the start index, ensuring it doesn't go below 0
	             partitionedJobs.add(jobs.subList(start, i)); // Add the sublist from `start` to `i`
	         }

	         model.addAttribute("jobsPartitioned", partitionedJobs); // Add partitioned list
	         return "index";
	     }


	@GetMapping("/contactus")
	public String ShowCont(Model model) {
		EnquiryDto dto=new EnquiryDto();
		model.addAttribute("dto", dto);
		return "contactus";
	}
	
	@PostMapping("/contactus")
	public String SubmitEnquiry(@ModelAttribute EnquiryDto enquiryDto, BindingResult result, RedirectAttributes attrib) {
		try {
			Enquiry eq=new Enquiry();
			eq.setName(enquiryDto.getName());
			eq.setEmailaddress(enquiryDto.getEmailaddress());
			eq.setMobile(enquiryDto.getMobile());
			eq.setGender(enquiryDto.getGender());
			eq.setEnquiry(enquiryDto.getEnquiry());
			eq.setPostdate(new Date(0)+"");
			erepo.save(eq);
			SmsSender sms=new SmsSender();
			sms.sendSms(enquiryDto.getMobile());
			 SimpleMailMessage message = new SimpleMailMessage();
			 emailService.sendSimpleEmail(enquiryDto.getEmailaddress(), enquiryDto);
		       // return "Email sent successfully";
			attrib.addFlashAttribute("msg", "successful your enquiry");
			return "redirect:/contactus";
		}catch(Exception e) {
			attrib.addFlashAttribute("msg", "somthing went wrong"+e.getMessage());
		   return "redirect:/contactus";
		}
	}
	@GetMapping("/adminlogin")
    public String ShowAdmiLogin(Model model) {
    	AdminLoginDto dto=new AdminLoginDto();
    	model.addAttribute("dto", dto);
    	return "adminlogin";
    }
	
	@PostMapping("/adminlogin")
	public String AdminLogin(@ModelAttribute AdminLoginDto adminLoginDto, HttpSession session, RedirectAttributes attrib) {
		try {
			
		   AdminLogin admin=adrepo.getById(adminLoginDto.getUserid());
		   if(admin.getPassword().equals(adminLoginDto.getPassword())) {
			   session.setAttribute("adminid", admin.getUserid());
			   return "redirect:/admin/adminhome";
		   }
		   else {
			   attrib.addFlashAttribute("msg", "Invaild user");
			   return "redirect:/adminlogin";
		   }
			
			
		}catch(Exception e) {
			attrib.addFlashAttribute("msg", "user not exsit");
			return "redirect:/adminlogin";
		}
	}
	@GetMapping("/postjob")
    public String postJob(Model model) {
    	AdminLoginDto dto=new AdminLoginDto();
    	model.addAttribute("dto", dto);
    	return "adminlogin";
    }
	@PostMapping("/postjob")
	public String PostJob(@ModelAttribute AdminLoginDto adminLoginDto, HttpSession session, RedirectAttributes attrib) {
		try {
			
		   AdminLogin admin=adrepo.getById(adminLoginDto.getUserid());
		   if(admin.getPassword().equals(adminLoginDto.getPassword())) {
			   session.setAttribute("adminid", admin.getUserid());
			   return "redirect:/admin/postjob";
		   }
		   else {
			   attrib.addFlashAttribute("msg", "Invaild user");
			   return "redirect:/adminlogin";
		   }
			
			
		}catch(Exception e) {
			attrib.addFlashAttribute("msg", "user not exsit");
			return "redirect:/adminlogin";
		}
	}
	@GetMapping("/userlogin")
    public String ShowUerLogin(Model model) {
    	UserDto dto=new UserDto();
    	model.addAttribute("dto", dto);
    	return "userlogin";
    }
	
}

