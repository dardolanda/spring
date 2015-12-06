package ar.com.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ar.com.spring.Offers;

public class OffersRowMapper implements RowMapper<Offers>{
	
	
	
	/*
	 * Rowmapper es una interfaz genérica, podemos agregarle <Offers>.
	 * La funcionalidad del rowMapper es la de mappear el JDBC resultSet 
	 * a un único objeto Offers.
	 * 
	 * Notar: row = fila, hilera.
	 * Por lo tanto, es un registro de la tabla a la que estoy haciendo 
	 * una consulta. 
	 * 
	 * 
	 * */

	@Override
	public Offers mapRow(ResultSet resultSet, int row) throws SQLException {
		
		Offers offers = new Offers();
		
		offers.setId(resultSet.getInt("id"));
		offers.setName(resultSet.getString("name"));
		offers.setEmail(resultSet.getString("email"));		
		offers.setText(resultSet.getString("text"));
		
		
		return offers;
	}
	
	

}
