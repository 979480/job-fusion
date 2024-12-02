package com.job.jobportal.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	
	@GetMapping("/viewuser")
	public String showAdminHome(HttpSession session, HttpServletResponse response, Model model) {
		
		try {
			response.setHeader("Catch-Control","no-catch, no-store, must-revalidate");
			if(session.getAttribute("userid")!=null) {
			return "user/viewuser";
			}
		}catch(Exception e) {
			return "redirect:/userlogin";
		}
		return "redirect:/userlogin";
	}
	
}
