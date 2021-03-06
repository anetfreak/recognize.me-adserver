package com.glassify.adserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@RequestMapping("/home")
	public ModelAndView showUploadPage() {
		return new ModelAndView("home");
	}
	
	@RequestMapping("/about")
	public ModelAndView showAboutPage() {
		return new ModelAndView("about");
	}
	
	@RequestMapping("/contact")
	public ModelAndView showContactPage() {
		return new ModelAndView("contact");
	}
}
