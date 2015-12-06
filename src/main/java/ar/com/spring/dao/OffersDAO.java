package ar.com.spring.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

		
		/*
		 * Tener en cuenta las Excepciones de la base de datos:
		 * Notar que tanto en este métdo como en el RowMapper, 
		 * no se están teniendo en cuanta las excepciones, y la 
		 * razón de eso es porque Spring toma las excepciones 
		 * wraps --> data acces exceptions: es una Spring Class 
		 * que es un tipo de exception: RunTime Exception.
		 * 
		 * All the exceptions thrown by the Spring JDBC Framework 
		 * are subclasses of DataAccessException which is a type 
		 * of RuntimeException, so you need not handle it explicitly. 
		 * Any checked exception when thrown will be mapped to any of
		 * the subclasses of the DataAccessException by the framework.
		 *  
		 * Podemos o no tomarlas, lo que si no hace falta es poner es 
		 * el throws en el método, ya que la exception es tomada por 
		 * Spring.
		 * Ahora, si queremos tomar la exception, lo que debemos hacer 
		 * es usar, logicamente un try-catch, pero en donde se llama.
		 * Y esto tiene que ser en App.java. Allí mismo es donde se 
		 * va a usar el try-catch.
		 * En el catch se tiene que usar el DataAccessException (hereda de 
		 * runtime exception), y esta toma todas las excepciones que vengan
		 * del DAO.
		 * 
		 * */
		
		return jdbcTemplate.query("select * from offers", new OffersRowMapper());		
		
	}


	@Autowired
	public void setDataSource(DataSource jdbcDataSource) {
		this.jdbcTemplate = new JdbcTemplate(jdbcDataSource);
	}
	
	
	
	
	

}
