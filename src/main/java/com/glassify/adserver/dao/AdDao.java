package com.glassify.adserver.dao;

import java.util.List;

import com.glassify.adserver.domain.Ad;
import com.glassify.adserver.domain.AdBrand;
import com.glassify.adserver.domain.AdCategory;
import com.glassify.adserver.domain.AdContentType;

public interface AdDao {

	//Create
    public void saveAd(Ad ad) throws Exception;
    //Read
    public Ad getAdById(int id);
    //Update
    public void updateAd(Ad ad);
    //Delete
    public void deleteAdById(int id);
    //Get All
    public List<Ad> getAllAds();
	public List<Ad> retrieveAd(String brandName, double latitude, double longitude, String category);
	public List<AdBrand> getAllBrands();
	public List<AdCategory> getAllAdCategories();
	public List<AdContentType> getallContentTypes();
}
