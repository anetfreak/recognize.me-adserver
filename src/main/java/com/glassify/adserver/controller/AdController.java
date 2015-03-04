package com.glassify.adserver.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glassify.adserver.domain.Ad;
import com.glassify.adserver.domain.AdBrand;
import com.glassify.adserver.domain.AdCategory;
import com.glassify.adserver.domain.AdContentType;
import com.glassify.adserver.facade.AdFacade;

@Controller
public class AdController {

	@Autowired
	private AdFacade adFacade;
	
	@RequestMapping("/getAds")
	public @ResponseBody List<Ad> getAllAds() {
		return adFacade.getAllAds();
	}
	
	@RequestMapping(value = "/getAdbyId/{adId}", method = RequestMethod.GET)
	public @ResponseBody Ad getAdbyId(@PathVariable int adId) {
		return adFacade.getAdById(adId);
	}
	
	@RequestMapping(value = "/retrieveAd", method = RequestMethod.POST)
	public @ResponseBody Ad retrieveAd(@RequestParam String brandName,
			@RequestParam long latitude,
			@RequestParam long longitude,
			@RequestParam String category) {
		return adFacade.retrieveAd(brandName, latitude, longitude, category);
	}
	
	@RequestMapping(value = "/createAd", method = RequestMethod.POST)
	@ResponseBody
	public void createAd(@RequestParam String brandName,
			@RequestParam String name,
			@RequestParam String url,
			@RequestParam String contentType,
			@RequestParam String language,
//			@RequestParam Object content,
			@RequestParam String category) {
		
		Ad ad = new Ad();
		AdBrand brand = new AdBrand();
		brand.setName(brandName);
		brand.setId(1);
		ad.setBrand(brand);
		
		AdCategory adCategory = new AdCategory();
		adCategory.setName(category);
		adCategory.setId(1);
		ad.setCategory(adCategory);
		
		AdContentType adContentType = new AdContentType();
		adContentType.setContentType(contentType);
		adContentType.setId(1);
		ad.setContentType(adContentType);
		
		ad.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		ad.setExpiryDate(new Timestamp(System.currentTimeMillis()));
		ad.setLanguage(language);
		ad.setName(name);
		ad.setRegion("EN-US");
		ad.setUrl(url);
		ad.setContent("Hello to your first advertisement");
		
		adFacade.saveAd(ad);
		
	}
	
}
