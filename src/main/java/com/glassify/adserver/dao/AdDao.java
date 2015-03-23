package com.glassify.adserver.dao;

import java.sql.SQLException;
import java.util.List;

import com.glassify.adserver.domain.Ad;

public interface AdDao {

	//Create
    public void saveAd(Ad ad) throws SQLException, Exception;
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
