package com.glassify.adserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glassify.adserver.domain.Ad;
import com.glassify.adserver.facade.AdFacade;

@Controller
public class AdController {

	@Autowired
	private AdFacade adFacade;
	
	@RequestMapping("/getAds")
	public @ResponseBody List<Ad> getAllAds() {
		return adFacade.getAllAds();
	}
	
}
