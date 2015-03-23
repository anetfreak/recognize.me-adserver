package com.glassify.adserver.facade;

import java.sql.SQLException;
import java.util.List;

import com.glassify.adserver.domain.Ad;

public interface AdFacade {

    public void saveAd(Ad ad) throws SQLException, Exception;
    public Ad getAdById(int id);
    public void updateAd(Ad ad);
    public void deleteAdById(int id);
    public List<Ad> getAllAds();
	public Ad retrieveAd(String brandName, long latitude, long longitude, String category);
}
