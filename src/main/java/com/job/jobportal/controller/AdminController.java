package com.job.jobportal.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.job.jobportal.Repository.EnquiryRepo;
import com.job.jobportal.Repository.JobApplyRepository;
import com.job.jobportal.Repository.JobRepository;
import com.job.jobportal.model.Enquiry;
import com.job.jobportal.model.Job;
import com.job.jobportal.model.JobApply;
import com.job.jobportal.modeldto.JobDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	JobRepository jobRepo;
	
	@Autowired
	EnquiryRepo erepo;
	
	@Autowired
	JobApplyRepository aprepo;

	@GetMapping("/adminhome")
	public String showAdminHome(HttpSession session, HttpServletResponse response, Model model) {
		
		try {
			response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
			if(session.getAttribute("adminid")!=null) {
			return "admin/adminhome";
			}
		}catch(Exception e) {
			return "redirect:/adminlogin";
		}
		return "redirect:/adminlogin";
	}
	
	@GetMapping("/postjob")
	public String ShowPostJob(Model model,HttpServletResponse response, HttpSession session) {
	  try {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		if(session.getAttribute("adminid")!=null) {	
		JobDto dto=new JobDto();
		model.addAttribute("dto", dto);
		return "admin/postjob";
		}else {
			return "redirect:/adminlogin";
		}
		}catch(Exception ex) {
			return "redirect:/adminlogin";
		}
	}

	@PostMapping("/postjob")
	public String addJob(@ModelAttribute JobDto jobDto,RedirectAttributes attrib,HttpServletResponse response,HttpSession session) {
		try {
		Job job=new Job();
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		if(session.getAttribute("adminid")!=null) {
		job.setTitle(jobDto.getTitle());
		job.setDescription(jobDto.getDescription());
		job.setLocation(jobDto.getLocation());
		job.setQualification(jobDto.getQualification());
		job.setCompanyName(jobDto.getCompanyName());
		job.setType(jobDto.getType());
		job.setPosteddate(new Date(0)+"");
		jobRepo.save(job);
		attrib.addFlashAttribute("msg","Add successful");
		return "redirect:postjob";
		}else {
			return "redirect:postjob";
		}
		}catch(Exception e) {
			attrib.addFlashAttribute("msg","somthing went wrong "+e.getMessage());
			return "redirect:postjob";
		}
	}
	
	@GetMapping("/logout")
	public String useLogout(HttpSession session) {
		session.invalidate();
		return "redirect:/adminlogin";
	}
	
	@GetMapping("/viewjob")
	public String showJob(HttpServletResponse response, HttpSession session, Model model) {
		try {
			response.setHeader("Cache-Control", "no-cache, no-store, must-ravalidate");
			if(session.getAttribute("adminid")!=null) {
				List<Job> jolist=jobRepo.findAll();
				model.addAttribute("jolist", jolist);
			return "admin/viewjob";
			}else {
				return "redirect:/adminlogin";
			}
		}catch(Exception ex) {
			return "redirect:/adminlogin";
		}
	}
	
	@GetMapping("/viewenquriy")
	public String showEnquriy(HttpServletResponse response, HttpSession session, Model model) {
		try {
			response.setHeader("Cache-Control", "no-cache, no-store, must-ravalidate");
			if(session.getAttribute("adminid")!=null) {
				List<Enquiry> elist=erepo.findAll();
				model.addAttribute("elist", elist);
			return "admin/viewenquriy";
			}else {
				return "redirect:/adminlogin";
			}
		}catch(Exception ex) {
			return "redirect:/adminlogin";
		}
	}
	
	@GetMapping("/viewenquriy/delete")
	public String DeleteEnquiry(@RequestParam Long Id, HttpSession session,Model model,RedirectAttributes redirectAttributes, HttpServletResponse response)
	{
		//return"redirect:/admin/viewfeedback";
		try {
			response.setHeader("Cache-Control", "no-cache , no-store, must-revalidate");
			if(session.getAttribute("adminid")!=null){
				Enquiry res= erepo.getById(Id);
				
				try {
				erepo.delete(res);
				redirectAttributes.addFlashAttribute("msg", Id+"is deleted successfully");
				return"redirect:/admin/viewenquriy";
				}catch(Exception e) {
					e.printStackTrace();
				}
				erepo.delete(res);
				return "redirect:/admin/viewenquriy";
			}else
			{
				return"redirect:/adminlogin";
			}
			
		} catch (Exception e) {
			return"redirect:/adminlogin";
		}
	}
	@GetMapping("/viewForm")
	public String viewApplyForm(HttpSession session, HttpServletResponse response,Model model) {
		try {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		if(session.getAttribute("adminid")!=null) {
			List<JobApply> flist=aprepo.findAll();
			model.addAttribute("flist", flist);
			return "admin/viewForm";
		}else
		{
			return "redirect:/adminlogin";
		}
		}catch(Exception e) {
			return "redirect:/adminlogin";
		}
	}
	
}