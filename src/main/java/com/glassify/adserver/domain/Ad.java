package com.glassify.adserver.domain;

import java.sql.Timestamp;
import com.glassify.adserver.domain.AdBrand;
import com.glassify.adserver.domain.AdCategory;
import com.glassify.adserver.domain.AdContentType;

public class Ad {

	private int id;
	private String name;
	private String url;
	private AdContentType contentType;
	private AdBrand brand;
	private String region;
	private String language;
	private Object content;
	private Timestamp createdDate;
	private Timestamp expiryDate;
	private AdCategory category;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public AdContentType getContentType() {
		return contentType;
	}
	public void setContentType(AdContentType contentType) {
		this.contentType = contentType;
	}
	public AdBrand getBrand() {
		return brand;
	}
	public void setBrand(AdBrand brand) {
		this.brand = brand;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}
	public AdCategory getCategory() {
		return category;
	}
	public void setCategory(AdCategory category) {
		this.category = category;
	}
}
