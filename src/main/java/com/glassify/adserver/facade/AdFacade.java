package com.glassify.adserver.facade;

import java.util.List;

import com.glassify.adserver.domain.Ad;

public interface AdFacade {

    public void saveAd(Ad ad);
    public Ad getAdById(int id);
    public void updateAd(Ad ad);
    public void deleteAdById(int id);
    public List<Ad> getAllAds();
}
