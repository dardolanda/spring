package ar.com.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ar.com.spring.Offers;


/*
 * Notación: Component.
 * 
 * Normally you declare all the beans or components in XML bean configuration file,
 * so that Spring container can detect and register your beans or components. 
 * Actually, Spring is able to auto scan, detect and instantiate your beans 
 * from pre-defined project package, no more tedious beans declaration 
 * in in XML file.
 * 
 * Sin ir mas lejos, con esta notación podemos crear el bean sin hacerlo desde
 * el beans.xml.
 * */
@Component("offersDao")
public class OffersDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public List<Offers> getOffers(){
				
		return jdbcTemplate.query("select * from offers", new OffersRowMapper());
		
	}


	@Autowired
	public void setDataSource(DataSource jdbcDataSource) {
		this.jdbcTemplate = new JdbcTemplate(jdbcDataSource);
	}
	
	
	
	
	

}
