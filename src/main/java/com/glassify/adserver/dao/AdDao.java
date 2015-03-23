package com.glassify.adserver.dao;

import java.util.List;

import com.glassify.adserver.domain.Ad;

public interface AdDao {

	//Create
    public void saveAd(Ad ad);
    //Read
    public Ad getAdById(int id);
    //Update
    public void updateAd(Ad ad);
    //Delete
    public void deleteAdById(int id);
    //Get All
    public List<Ad> getAllAds();
	public Ad retrieveAd(String brandName, long latitude, long longitude, String category);
}
