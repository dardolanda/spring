package ar.com.spring.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import ar.com.spring.Offers;

@Component("singleOfferDao")
public class SingleOfferDAO {
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemlate;
	
	/*
	 * ': drop database' con esto pueden destruir la base, Ojo!!!!
	 * 
	 * */
	public Offers getOfferById(int idDesdeParametro){
		
		/* Se reemplaza los placeHolders en jdbc.
		 * placeHolder := marcador de posición , comodín. 
		 * 
		 * Para no usar el signo ? := para reemplazar parámetros de 
		 * búsqueda se puede hacer:
		 * 
		 * */
		
		MapSqlParameterSource mapParameter = new MapSqlParameterSource();
		mapParameter.addValue("idDesdeQuery", idDesdeParametro);
		/*
		 * Esto es lo que usamos, un map que es el objeto: "MapSqlParameterSource"
		 * y a este mismo le agregamos el nombre con el que usamos en la query,
		 * y el valor que por ejemplo lo podemos tener como parámetro.
		 * 
		 * */
		
		
		
		return this.namedParameterJdbcTemlate.queryForObject("select * from offers where id = :idDesdeQuery",
																	  mapParameter,
																	  new OffersRowMapper()); 
		/*
		 * Notar que el OffersRowMapper() --> que fue el que creamos nosotros implementando la interfaz:
		 * "RowMapper", retorna Offers, por lo tanto no hace falta castear nada y además no hace falta 
		 * indicarle el tipo de RowMapper que es agregando: new OfferRowMapper<Offers>(). Esto no es 
		 * necesario.
		 * 
		 * */
	}
	
	
	
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		
		this.namedParameterJdbcTemlate = new NamedParameterJdbcTemplate(dataSource);
		
	}
	

}
