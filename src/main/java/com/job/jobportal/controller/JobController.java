package com.job.jobportal.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.job.jobportal.Repository.JobApplyRepository;
import com.job.jobportal.Repository.JobRepository;
import com.job.jobportal.model.Job;
import com.job.jobportal.model.JobApply;
import com.job.jobportal.modeldto.JobApplyDto;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobApplyRepository jobApplyRepository;

    @GetMapping("/searchbar")
    public String viewShowJobSearch() {
    	return "jobs/serach_bar";
    }
    
    // Display search bar and results on the same page
    @GetMapping
    public String searchJobs(@RequestParam(required = false) String keyword, Model model, HttpSession session) {
        // Search for jobs based on keyword (title or location)
        List<Job> jobs = (keyword != null && !keyword.isEmpty())
                ? jobRepository.findByTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(keyword, keyword)
                : jobRepository.findAll();

        model.addAttribute("jobs", jobs);  // Add jobs to the model
        model.addAttribute("keyword", keyword);  // Preserve the search keyword in the search bar
        session.setAttribute("jobs", jobs);
        return "jobs/serach_bar";  // Render the search bar page
    }

    // Display job application form
    @GetMapping("/apply/{jobId}")
    public String showApplicationForm(@PathVariable Long jobId, Model model) {
    	JobApplyDto dto=new JobApplyDto();
        model.addAttribute("dto", dto);
        return "jobs/applyJob";
    }

    // Handle job application submissions
    @PostMapping("/apply/{jobId}")
    public String submitApplication(@PathVariable Long jobId,@ModelAttribute JobApplyDto applyDto,RedirectAttributes redirectAttributes,HttpSession session) {
    	try {
    		 Job jo = jobRepository.findById(jobId)
    	                .orElseThrow(() -> new IllegalArgumentException("Invalid Job ID"));

    	JobApply job=new JobApply();
    	job.setJob(jo);
    	MultipartFile resume=applyDto.getResume();
		String storageFileName=new Date().getTime()+"_"+resume.getOriginalFilename();
		String uploadDir="public/mat/";
		Path uploadPath=Paths.get(uploadDir);
		if(!Files.exists(uploadPath)) {
			
			Files.createDirectories(uploadPath);
		}
		try(InputStream inputStream=resume.getInputStream()){
			
			Files.copy(inputStream,Paths.get(uploadDir+storageFileName),StandardCopyOption.REPLACE_EXISTING);
			//Files.copy(inputStream,Paths.get(uploadDir+storageFileName),StandardCopyOption.REPLACE_EXISTING);
		}
    	job.setName(applyDto.getName());
    	job.setEmail(applyDto.getEmail());
    	job.setCurrentLocation(applyDto.getCurrentLocation());
    	job.setQualification(applyDto.getQualification());
    	job.setFilename(storageFileName);
    	job.setMobileNumber(applyDto.getMobileNumber());
    	job.setPostedate(new Date()+"");
    	jobApplyRepository.save(job);
        redirectAttributes.addFlashAttribute("message", "Application submitted successfully!");
        return "redirect:/jobs/apply/" +jobId;
    	}catch(Exception e) {
    		redirectAttributes.addFlashAttribute("message", "Something went wrong"+e.getMessage());
            return "redirect:/jobs/apply/" +jobId;
    	}
    }
}
