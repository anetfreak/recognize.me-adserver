package com.glassify.adserver.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.glassify.adserver.dao.AdDao;
import com.glassify.adserver.domain.Ad;

@Component
public class AdFacadeImpl implements AdFacade {
	
	@Autowired
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
	
}
