package com.glassify.adserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public class AdController {

	@RequestMapping("/showAds")
	public ModelAndView showAllAds() {
		return new ModelAndView("show-ads");
	}
}
