package com.glassify.adserver.facade;

import java.util.List;

import com.glassify.adserver.domain.Ad;
import com.glassify.adserver.domain.AdBrand;
import com.glassify.adserver.domain.AdCategory;
import com.glassify.adserver.domain.AdContentType;

public interface AdFacade {

    public void saveAd(Ad ad) throws Exception;
    public Ad getAdById(int id);
    public void updateAd(Ad ad);
    public void deleteAdById(int id);
    public List<Ad> getAllAds();
	public Ad retrieveAd(String brandName, double latitude, double longitude, String category) throws Exception;
	public List<AdBrand> getAllBrands();
	public List<AdCategory> getAllAdCategories();
	public List<AdContentType> getallContentTypes();
}
