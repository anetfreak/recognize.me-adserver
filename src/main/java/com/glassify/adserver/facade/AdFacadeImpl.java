package com.glassify.adserver.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.glassify.adserver.dao.AdDao;
import com.glassify.adserver.domain.Ad;
import com.glassify.adserver.domain.AdBrand;
import com.glassify.adserver.domain.AdCategory;
import com.glassify.adserver.domain.AdContentType;

@Component
public class AdFacadeImpl implements AdFacade {
	
	@Autowired
	private AdDao adDao;
	
	public void saveAd(Ad ad) throws Exception {
		adDao.saveAd(ad);
	}

	public Ad getAdById(int id) {
		return adDao.getAdById(id);
	}

	public void updateAd(Ad ad) {
		adDao.updateAd(ad);
	}

	public void deleteAdById(int id) {
		adDao.deleteAdById(id);
	}

	public List<Ad> getAllAds() {
		return adDao.getAllAds();
	}

	public Ad retrieveAd(String brandName, long latitude, long longitude, String category) {
		return adDao.retrieveAd(brandName, latitude, longitude, category);
	}
	
	public List<AdBrand> getAllBrands(){
		return adDao.getAllBrands();
	}
	
	public List<AdCategory> getAllAdCategories(){
		return adDao.getAllAdCategories();
	}
	
	public List<AdContentType> getallContentTypes(){
		return adDao.getallContentTypes();
	}

}
