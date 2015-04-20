package com.glassify.adserver.dao;

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
		String query = "insert into advertisement (id, name, url, ad_content_type, ad_brand_id, region, language, "
				+ "ad_content, created_date, expiry_date, ad_category_id, latitude, longitude) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		 //final File image = new File((String) ad.getContent());
		 //final InputStream imageIs = new FileInputStream(image);   
		 //LobHandler lobHandler = new DefaultLobHandler(); 
		   
		Object[] args = new Object[] { ad.getId(), ad.getName(), ad.getUrl(),
				ad.getContentType().getId(), ad.getBrand().getId(),
				ad.getRegion(), ad.getLanguage(), ad.getContent(),
				ad.getCreatedDate(), ad.getExpiryDate(),
				ad.getCategory().getId(), ad.getLatitude(), ad.getLongitude() };

		int out = jdbcTemplate.update(query, args);

		if (out != 0) {
			System.out.println("Ad saved to the database");
		} else {
			System.out.println("Error in saving the glass to database");
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
						ad.setContent(rs.getString("content"));
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
			ad.setContent(String.valueOf(adRow.get("content")));
			ad.setCreatedDate(Timestamp.valueOf(String.valueOf(adRow
					.get("created_date"))));
			ad.setExpiryDate(Timestamp.valueOf(String.valueOf(adRow
					.get("expiry_date"))));
			ad.setRegion(String.valueOf(adRow.get("region")));
			ad.setLanguage(String.valueOf(adRow.get("language")));
			ad.setUrl(String.valueOf(adRow.get("url")));
			AdCategory category = getCategoryDetails(Integer.parseInt(String.valueOf(adRow.get("ad_category_id"))));
			AdBrand brand = getBrandDetails(Integer.parseInt(String.valueOf(adRow.get("ad_brand_id"))));
			AdContentType contentType = getContentDetails(Integer.parseInt(String.valueOf(adRow.get("ad_content_type"))));
			ad.setBrand(brand);
			ad.setCategory(category);
			ad.setContentType(contentType);
			adList.add(ad);
		}
		return adList;
	}

	/**
	 * Method to retrieve advertisement details for a brand, location, category
	 */
	public List<Ad> retrieveAd(String brandName, long latitude, long longitude,
			String category) {
		AdBrand brandDetails = getBrandDetails(brandName);
		AdCategory categoryDetails = getCategoryDetails(category);
		int brandId = brandDetails.getId();
		int categoryId = categoryDetails.getId();
		String query = "select * from advertisement where ad_brand_id = ? and latitude = ? and longitude = ? and category = ? order by expiry_date";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Ad> adList = new ArrayList<Ad>();
		List<Map<String, Object>> adRows = jdbcTemplate.queryForList(query, brandId, latitude, longitude, categoryId);

		for (Map<String, Object> adRow : adRows) {
			Ad ad = new Ad();
			ad.setId(Integer.parseInt(String.valueOf(adRow.get("id"))));
			ad.setName(String.valueOf(adRow.get("name")));
			ad.setContent(String.valueOf(adRow.get("content")));
			ad.setCreatedDate(Timestamp.valueOf(String.valueOf(adRow
					.get("created_date"))));
			ad.setExpiryDate(Timestamp.valueOf(String.valueOf(adRow
					.get("expiry_date"))));
			ad.setRegion(String.valueOf(adRow.get("region")));
			ad.setLanguage(String.valueOf(adRow.get("language")));
			ad.setUrl(String.valueOf(adRow.get("url")));
			AdCategory adCategory = getCategoryDetails(Integer.parseInt(String.valueOf(adRow.get("ad_category_id"))));
			AdBrand brand = getBrandDetails(Integer.parseInt(String.valueOf(adRow.get("ad_brand_id"))));
			AdContentType contentType = getContentDetails(Integer.parseInt(String.valueOf(adRow.get("ad_content_type"))));
			ad.setBrand(brand);
			ad.setCategory(adCategory);
			ad.setContentType(contentType);
			adList.add(ad);
		}
		return adList;
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
				adbrand.setDesc(String.valueOf(brandRow.get("description")));

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
	
	/**
	 * Method to retrieve the category details according to category id
	 */
	public AdCategory getCategoryDetails(final Integer catId){
		
			String query = "select * from ad_category where id = ?";
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			AdCategory adCat = jdbcTemplate.queryForObject(query, new Object[] { catId },
					new RowMapper<AdCategory>() {
						AdCategory adCat = new AdCategory();
						public AdCategory mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							adCat.setId(catId);
							adCat.setName(rs.getString("name"));
							adCat.setDesc(rs.getString("desc"));
							return adCat;
						}
					});
			return adCat;
	}
	
	/**
	 * Method to retrieve the category details according to category name
	 */
	public AdCategory getCategoryDetails(final String categoryName){
		
			String query = "select * from ad_category where name = ?";
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			AdCategory adCat = jdbcTemplate.queryForObject(query, new Object[] { categoryName },
					new RowMapper<AdCategory>() {
						AdCategory adCat = new AdCategory();
						public AdCategory mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							adCat.setId(rs.getInt("id"));
							adCat.setName(categoryName);
							adCat.setDesc(rs.getString("desc"));
							return adCat;
						}
					});
			return adCat;
	}
	
	/**
	 * Method to retrieve the Brand details according to brand id
	 */
	public AdBrand getBrandDetails(final Integer brandId){
		
			String query = "select * from ad_brand where id = ?";
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			AdBrand adBrand = jdbcTemplate.queryForObject(query, new Object[] { brandId },
					new RowMapper<AdBrand>() {
						AdBrand adBrand = new AdBrand();
						public AdBrand mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							adBrand.setId(brandId);
							adBrand.setName(rs.getString("name"));
							adBrand.setWebsite(rs.getString("website"));
							adBrand.setDomain(rs.getString("domain"));
							adBrand.setDesc(rs.getString("description"));
							return adBrand;
						}
					});
			return adBrand;
	}
	
	/**
	 * Method to retrieve the Brand details according to brand name
	 */
	public AdBrand getBrandDetails(final String brandName){
		
			String query = "select * from ad_brand where name = ?";
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			AdBrand adBrand = jdbcTemplate.queryForObject(query, new Object[] { brandName },
					new RowMapper<AdBrand>() {
						AdBrand adBrand = new AdBrand();
						public AdBrand mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							adBrand.setId(rs.getInt("id"));
							adBrand.setName(brandName);
							adBrand.setWebsite(rs.getString("website"));
							adBrand.setDomain(rs.getString("domain"));
							adBrand.setDesc(rs.getString("description"));
							return adBrand;
						}
					});
			return adBrand;
	}
	
	/**
	 * Method to retrieve the Content Type details according to content type id
	 */
	public AdContentType getContentDetails(final Integer contentId){
		
			String query = "select * from ad_content_type where id = ?";
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			AdContentType adContent = jdbcTemplate.queryForObject(query, new Object[] { contentId },
					new RowMapper<AdContentType>() {
				AdContentType adContent = new AdContentType();
						public AdContentType mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							adContent.setId(contentId);
							adContent.setContentType(rs.getString("content_type_name"));
							adContent.setDesc(rs.getString("desc"));
							return adContent;
						}
					});
			return adContent;
	}
	
	
}
