package com.glassify.adserver.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.glassify.adserver.domain.Ad;

public class AdDaoImpl implements AdDao {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void saveAd(Ad ad) {
		String query = "insert into Ad (id, name, role) values (?,?,?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		Object[] args = new Object[] {ad.getId(), ad.getName(), ad.getRole()};

		int out = jdbcTemplate.update(query, args);

		if(out !=0){
			System.out.println("Ad saved with id="+ad.getId());
		}else System.out.println("Ad save failed with id="+ad.getId());
	}

	public Ad getAdById(int id) {
		String query = "select id, name, role from Ad where id = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		//using RowMapper anonymous class, we can create a separate RowMapper for reuse
		Ad ad = jdbcTemplate.queryForObject(query, new Object[]{id}, new RowMapper<Ad>(){

			public Ad mapRow(ResultSet rs, int rowNum) throws SQLException {
				Ad ad = new Ad();
				ad.setId(rs.getInt("id"));
				ad.setName(rs.getString("name"));
				ad.setRole(rs.getString("role"));
				return ad;
			}});

		return ad;
	}

	public void updateAd(Ad ad) {
		String query = "update Ad set name=?, role=? where id=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] args = new Object[] {ad.getName(), ad.getRole(), ad.getId()};

		int out = jdbcTemplate.update(query, args);
		if(out !=0){
			System.out.println("Ad updated with id="+ad.getId());
		}else System.out.println("No Ad found with id="+ad.getId());
	}

	public void deleteAdById(int id) {

		String query = "delete from Ad where id=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int out = jdbcTemplate.update(query, id);
		if(out !=0){
			System.out.println("Ad deleted with id="+id);
		}else System.out.println("No Ad found with id="+id);
	}

	public List<Ad> getAllAds() {
		String query = "select id, name, role from Ad";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Ad> adList = new ArrayList<Ad>();

		List<Map<String,Object>> adRows = jdbcTemplate.queryForList(query);

		for(Map<String,Object> adRow : adRows){
			Ad ad = new Ad();
			ad.setId(Integer.parseInt(String.valueOf(adRow.get("id"))));
			ad.setName(String.valueOf(adRow.get("name")));
			ad.setRole(String.valueOf(adRow.get("role")));
			adList.add(ad);
		}
		return adList;
	}

}
