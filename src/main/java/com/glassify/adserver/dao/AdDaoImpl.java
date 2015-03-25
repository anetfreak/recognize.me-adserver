package com.glassify.adserver.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Component;

import com.glassify.adserver.domain.Ad;
import com.glassify.adserver.domain.AdBrand;
import com.glassify.adserver.domain.AdCategory;
import com.glassify.adserver.domain.AdContentType;

@Component
public class AdDaoImpl implements AdDao {

	@Autowired
	private DataSource dataSource;

	public void saveAd(Ad ad) throws Exception{
		String query = "insert into advertisement (id, name, url, ad_content_type, ad_brand_id, region, language, ad_content, created_date, expiry_date, ad_category_id) values (?,?,?,?,?,?,?,?,?,?,?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		 final File image = new File((String) ad.getContent());
		 final InputStream imageIs = new FileInputStream(image);   
		 LobHandler lobHandler = new DefaultLobHandler(); 
		   
		Object[] args = new Object[] { ad.getId(), ad.getName(), ad.getUrl(),
				ad.getContentType().getId(), ad.getBrand().getId(),
				ad.getRegion(), ad.getLanguage(), ad.getContent(),
				ad.getCreatedDate(), ad.getExpiryDate(),
				ad.getCategory().getId() };

		int out = jdbcTemplate.update(query, args);

		if (out != 0) {
			System.out.println("Ad saved with id=" + ad.getId());
		} else {
			System.out.println("Ad save failed with id=" + ad.getId());
		}
	}

	public Ad getAdById(final int id) {
		String query = "select name, url, ad_content_type, ad_brand_id, region, language, ad_content, created_date, expiry_date, ad_category_id from advertisement where id = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		Ad ad = jdbcTemplate.queryForObject(query, new Object[] { id },
				new RowMapper<Ad>() {

					public Ad mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Ad ad = new Ad();
						ad.setId(id);
						ad.setName(rs.getString("name"));
						ad.setContent(rs.getObject("content"));
						ad.setCreatedDate(rs.getTimestamp("created_date"));
						ad.setExpiryDate(rs.getTimestamp("expiry_date"));
						ad.setRegion(rs.getString("region"));
						ad.setLanguage(rs.getString("language"));
						ad.setUrl(rs.getString("url"));

						AdCategory category = new AdCategory();
						category.setId(rs.getInt("ad_category_id"));
						AdBrand brand = new AdBrand();
						brand.setId(rs.getInt("ad_brand_id"));
						AdContentType contentType = new AdContentType();
						contentType.setId(rs.getInt("ad_content_type"));

						ad.setBrand(brand);
						ad.setCategory(category);
						ad.setContentType(contentType);
						return ad;
					}
				});

		return ad;
	}

	public void updateAd(Ad ad) {
		String query = "update advertisement set name=?, url=?, ad_content_type=?, ad_brand_id=?, region=?, language=?, ad_content=?, created_date=?, expiry_date=?, ad_category_id=? where id=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] args = new Object[] { ad.getName(), ad.getUrl(),
				ad.getContentType().getId(), ad.getBrand().getId(),
				ad.getRegion(), ad.getLanguage(), ad.getContent(),
				ad.getCreatedDate(), ad.getExpiryDate(),
				ad.getCategory().getId() };

		int out = jdbcTemplate.update(query, args);
		if (out != 0) {
			System.out.println("Ad updated with id=" + ad.getId());
		} else {
			System.out.println("No Ad found with id=" + ad.getId());
		}
	}

	public void deleteAdById(int id) {

		String query = "delete from advertisement where id=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int out = jdbcTemplate.update(query, id);
		if (out != 0) {
			System.out.println("Ad deleted with id=" + id);
		} else {
			System.out.println("No Ad found with id=" + id);
		}
	}

	public List<Ad> getAllAds() {
		String query = "select id, name, url, ad_content_type, ad_brand_id, region, language, ad_content, created_date, expiry_date, ad_category_id from advertisement";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Ad> adList = new ArrayList<Ad>();

		List<Map<String, Object>> adRows = jdbcTemplate.queryForList(query);

		for (Map<String, Object> adRow : adRows) {
			Ad ad = new Ad();
			ad.setId(Integer.parseInt(String.valueOf(adRow.get("id"))));
			ad.setName(String.valueOf(adRow.get("name")));
			ad.setContent(adRow.get("content"));
			ad.setCreatedDate(Timestamp.valueOf(String.valueOf(adRow
					.get("created_date"))));
			ad.setExpiryDate(Timestamp.valueOf(String.valueOf(adRow
					.get("expiry_date"))));
			ad.setRegion(String.valueOf(adRow.get("region")));
			ad.setLanguage(String.valueOf(adRow.get("language")));
			ad.setUrl(String.valueOf(adRow.get("url")));

			AdCategory category = new AdCategory();
			category.setId(Integer.parseInt(String.valueOf(adRow
					.get("ad_category_id"))));
			AdBrand brand = new AdBrand();
			brand.setId(Integer.parseInt(String.valueOf(adRow
					.get("ad_brand_id"))));
			AdContentType contentType = new AdContentType();
			contentType.setId(Integer.parseInt(String.valueOf(adRow
					.get("ad_content_type"))));

			ad.setBrand(brand);
			ad.setCategory(category);
			ad.setContentType(contentType);
			adList.add(ad);
		}
		return adList;
	}

	public Ad retrieveAd(String brandName, long latitude, long longitude,
			String category) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method to return all the existing brands
	 */
	public List<AdBrand> getAllBrands() {
		List<AdBrand> brandList = new ArrayList<AdBrand>();
		try {
			String query = "select * from ad_brand";
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			List<Map<String, Object>> brandRows = jdbcTemplate
					.queryForList(query);

			for (Map<String, Object> brandRow : brandRows) {
				AdBrand adbrand = new AdBrand();
				adbrand.setId(Integer.parseInt(String.valueOf(brandRow
						.get("id"))));
				adbrand.setName(String.valueOf(brandRow.get("name")));
				adbrand.setWebsite(String.valueOf(brandRow.get("website")));
				adbrand.setDomain(String.valueOf(brandRow.get("domain")));
				adbrand.setDesc(String.valueOf(brandRow.get("desc")));

				brandList.add(adbrand);
			}
		} catch (Exception exception) {
			System.out.println("Error encountered ! ");
			exception.printStackTrace();
		} finally {
			return brandList;
		}
	}

	/**
	 * Method to return all the advertisement categories
	 */
	public List<AdCategory> getAllAdCategories(){
		List<AdCategory> categoryList = new ArrayList<AdCategory>();
		try {
			String query = "select * from ad_category";
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			List<Map<String, Object>> categoryRows = jdbcTemplate
					.queryForList(query);

			for (Map<String, Object> categoryRow : categoryRows) {
				AdCategory adCategory = new AdCategory();
				adCategory.setId(Integer.parseInt(String.valueOf(categoryRow
						.get("id"))));
				adCategory.setName(String.valueOf(categoryRow.get("name")));
				adCategory.setDesc(String.valueOf(categoryRow.get("desc")));
				categoryList.add(adCategory);
			}
		} catch (Exception exception) {
			System.out.println("Error encountered ! ");
			exception.printStackTrace();
		} finally {
			return categoryList;
		}
	}
	
	/**
	 * Method to return all the content types
	 */
	public List<AdContentType> getallContentTypes(){
		List<AdContentType> contenttypelist = new ArrayList<AdContentType>();
		try {
			String query = "select * from ad_content_type";
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			List<Map<String, Object>> contenttyperows = jdbcTemplate
					.queryForList(query);

			for (Map<String, Object> contenttyperow : contenttyperows) {
				AdContentType contenttype = new AdContentType();
				contenttype.setId(Integer.parseInt(String.valueOf(contenttyperow
						.get("id"))));
				contenttype.setContentType(String.valueOf(contenttyperow.get("content_type_name")));
				contenttype.setDesc(String.valueOf(contenttyperow.get("desc")));
				contenttypelist.add(contenttype);
			}
		} catch (Exception exception) {
			System.out.println("Error encountered ! ");
			exception.printStackTrace();
		} finally {
			return contenttypelist;
		}
	}
}
