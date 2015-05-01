package com.glassify.adserver.controller;

import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.glassify.adserver.domain.Ad;
import com.glassify.adserver.domain.AdBrand;
import com.glassify.adserver.domain.AdCategory;
import com.glassify.adserver.domain.AdContentType;
import com.glassify.adserver.facade.AdFacade;

@Controller
public class AdController {

	@Autowired
	private AdFacade adFacade;
	private String saveDirectory = "/var/www/html/";

	/**
	 * Method to retrieve all advertisements
	 * @return
	 */
	@RequestMapping("/getAds")
	public ModelAndView getAllAds() {
		List<Ad> ads = adFacade.getAllAds();
		ModelAndView modelAndView = new ModelAndView("show-ads");
		modelAndView.addObject("ads", ads);
		return modelAndView;
	}

	@RequestMapping("/createAd")
	public ModelAndView showCreateAdPage() {
		List<AdBrand> adbrands = adFacade.getAllBrands();
		List<AdCategory> adcategories = adFacade.getAllAdCategories();
		List<AdContentType> contenttype = adFacade.getallContentTypes();
		ModelAndView modelView = new ModelAndView("create-ad");
		modelView.addObject("brands", adbrands);
		modelView.addObject("categories", adcategories);
		modelView.addObject("contenttypes", contenttype);
		return modelView;
	}

	@RequestMapping(value = "/getAdbyId/{adId}", method = RequestMethod.GET)
	public @ResponseBody Ad getAdbyId(@PathVariable int adId) {
		return adFacade.getAdById(adId);
	}

	/**
	 * Method called by image recognition to retrieve advertisements related to a brand, category and location
	 * @param brandName
	 * @param latitude
	 * @param longitude
	 * @param category
	 * @return
	 */
	@RequestMapping(value = "/retrieveAd", method = RequestMethod.POST)
	public @ResponseBody String retrieveAd(@RequestParam String brandName,
			@RequestParam double latitude, @RequestParam double longitude,
			@RequestParam String category) {
		Ad ad = null;
		try {
			ad = adFacade.retrieveAd(brandName.toUpperCase(), latitude, longitude, category);
		} catch (Exception e) {
			System.out.println("Sorry! No advertisements are there matching to the Brand Name and location.");
			return "";
		}
		//System.out.println(ad.getBrand()+ " " + ad.getLatitude() + " "+ad.getLongitude() + " "+ ad.getName() + " "+ ad.getExpiryDate());
		if(ad == null) {
			return "";
		} else {
			if(ad.getContent() != null) {
				return ad.getContent();
			} else {
				return "";
			}
		}
		
	}

	/**
	 * Method to save an advertisement details
	 * @param brandid
	 * @param name
	 * @param url
	 * @param contentType
	 * @param region
	 * @param content
	 * @param latitude
	 * @param longitude
	 * @param category
	 * @param expiryDate
	 * @param textcontent
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createAd", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView createAd(
			@RequestParam("brandid") Integer brandid,
			@RequestParam("name") String name,
			@RequestParam("url") String url,
			@RequestParam("contenttypeid") Integer contentType,
			@RequestParam("region") String region,
			@RequestParam(value = "content", required = false) CommonsMultipartFile content,
			@RequestParam("locationLat") Float latitude,
			@RequestParam("locationLong") Float longitude,
			@RequestParam("categoryid") Integer category,
			@RequestParam("datetimepicker1") @DateTimeFormat(pattern = "yyyy-mm-dd") Date expiryDate,
			@RequestParam(value="textcontent", required = false) String textcontent) throws Exception {

		System.out.println(saveDirectory);
		Ad ad = new Ad();
		AdBrand brand = new AdBrand();
		brand.setId(brandid);
		ad.setBrand(brand);

		AdCategory adCategory = new AdCategory();
		adCategory.setId(category);
		ad.setCategory(adCategory);

		AdContentType adContentType = new AdContentType();
		adContentType.setId(contentType);
		ad.setContentType(adContentType);
		ad.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		ad.setExpiryDate(new Timestamp(expiryDate.getTime()));
		ad.setRegion(region);
		ad.setLanguage(region);
		ad.setName(name);
		ad.setUrl(url);
		ad.setLatitude(latitude);
		ad.setLongitude(longitude);
		
		if (content != null && !(content.isEmpty())) {
			// for (CommonsMultipartFile aFile : content){
			try {
				System.out.println("Saving file: " + content.getOriginalFilename());

				if (!content.getOriginalFilename().equals("")) {
					System.out.println(saveDirectory);
					content.transferTo(new File(saveDirectory + content.getOriginalFilename()));
					System.out.println("You have successfully uploaded the file " + content.getOriginalFilename());
					System.out.println(saveDirectory + content.getOriginalFilename());
					ad.setContent(saveDirectory + content.getOriginalFilename());
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Failed to upload the file " + content.getOriginalFilename());
				return new ModelAndView("errorAd");
			}
			// }
		}
		else if(!(textcontent.isEmpty())){
			System.out.println("textcontent not null "+textcontent);
				ad.setContent(textcontent);
		}
		else {
			System.out.println("Could not upload the file as it was empty.");
		}
		try {
			adFacade.saveAd(ad);
			return new ModelAndView("successAd");
		} catch (SQLException sql) {
			System.out.println("Error encountered in connecting to database");
			sql.printStackTrace();
			return new ModelAndView("errorAd");
		} catch (Exception e) {
			System.out.println("Exception encountered.!");
			e.printStackTrace();
			return new ModelAndView("errorAd");
	}
	}
	
	/**
	 * Successful addition of an advertisement into database
	 * @return
	 */
	@RequestMapping("/successAd")
	public ModelAndView getsuccessMessage() {
		ModelAndView modelAndView = new ModelAndView("successAd");
		return modelAndView;
	}
	
	/**
	 * Advertisement addition error
	 * @return
	 */
	@RequestMapping("/errorAd")
	public ModelAndView getErrorMessage() {
		ModelAndView modelAndView = new ModelAndView("errorAd");
		return modelAndView;
	}
	
}
