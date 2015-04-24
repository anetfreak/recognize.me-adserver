package com.glassify.adserver.facade;

import java.util.ArrayList;
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

	public Ad retrieveAd(String brandName, double latitude, double longitude, String category) throws Exception {
		
		//First filtering out the advertisements located within 15 miles
		List<Ad> advertisements = adDao.retrieveAd(brandName, latitude, longitude, category);
		List<Ad> filteredAds = new ArrayList<Ad>();
		for (Ad adRow: advertisements){
			//System.out.println(adRow.getId());
			//System.out.println(adRow.getName());
			double distance = distance(adRow.getLatitude(), adRow.getLongitude(), latitude, longitude);
			if (distance <= 15){
				filteredAds.add(adRow);
			}
		}
		//The advertisement list is sorted by expiry date and the top ad is returned from the list.
		System.out.println(filteredAds.size());
		if(filteredAds.isEmpty()){
			throw new Exception("Sorry.! No advertisements are there matching to the Brand Name and location");
		}
		else{
			Ad advert = filteredAds.get(0);
			return advert;
		}
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

	/**
	 * This routine calculates the distance between two points (given the
	/* latitude/longitude of those points). It is being used to calculate
	/* the distance between two locations using GeoDataSource (TM) prodducts
	 * @param latitude1
	 * @param longitude1
	 * @param latitude2
	 * @param longitude2
	 * @return
	 */
	private double distance(double latitude1, double longitude1, double latitude2, double longitude2) {
	  double theta = longitude1 - longitude2;
	  double distance = Math.sin(deg2rad(latitude1)) * Math.sin(deg2rad(latitude2)) + 
			  Math.cos(deg2rad(latitude1)) * Math.cos(deg2rad(latitude2)) * Math.cos(deg2rad(theta));
	  distance = Math.acos(distance);
	  distance = rad2deg(distance);
	  distance = distance * 60 * 1.1515;
	  //System.out.println("distance: "+distance);
	  return distance;
	}

	/**
	 * This function converts decimal degrees to radians
	 * @param deg
	 * @return
	 */
	private double deg2rad(double deg) {
	  return (deg * Math.PI / 180.0);
	}

	/**
	 * This function converts radians to decimal degrees
	 * @param rad
	 * @return
	 */
	private double rad2deg(double rad) {
	  return (rad * 180 / Math.PI);
	}

}
