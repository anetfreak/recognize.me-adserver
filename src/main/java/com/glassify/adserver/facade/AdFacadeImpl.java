package com.glassify.adserver.facade;

import java.util.List;

import com.glassify.adserver.dao.AdDao;
import com.glassify.adserver.domain.Ad;

public class AdFacadeImpl implements AdFacade {
	
	private AdDao adDao;

	public void saveAd(Ad ad) {
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
	
	public void setAdDao(AdDao adDao) {
		this.adDao = adDao;
	}

}
