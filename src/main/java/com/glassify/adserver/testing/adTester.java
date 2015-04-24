package com.glassify.adserver.testing;

import com.glassify.adserver.domain.Ad;
import com.glassify.adserver.facade.AdFacade;
import com.glassify.adserver.facade.AdFacadeImpl;

public class adTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AdFacade adFacade = new AdFacadeImpl();
		Ad ad = adFacade.retrieveAd("Starbucks", 37.7873, -122.411, "Promotion");
		System.out.println(ad.getBrand()+ " " + ad.getLatitude() + " "+ad.getLongitude() + " "+ ad.getName() + " "+ ad.getExpiryDate());
	}

}
