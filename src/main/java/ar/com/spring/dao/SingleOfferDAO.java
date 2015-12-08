package ar.com.spring.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ar.com.spring.Offers;

@Component("singleOfferDao")
public class SingleOfferDAO {
	
	
	/*
	 * Debemos usar NamedParameterJdbcTemplate para poder llamar 
	 * los parametros desde el Map: MapSqlParameterSource
	 * Notar: en caso de que solamente se usara JdbcTemplate,
	 * esta manera de usar el Map (MapSqlParameterSource) no sería
	 * posible.
	 * 
	 * */
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
	
	
	public boolean deleteById(int id){
		
		MapSqlParameterSource mapParameter = new MapSqlParameterSource();
		mapParameter.addValue("id", id);
						
		/*
		 * Notar, que para hacer un delete se usa el .update
		 * */
		return this.namedParameterJdbcTemlate.update("delete from offers where id = :id" , mapParameter) == 1;
		
	}
	
	public boolean updateMail(Offers offers){
		
		/*
		 * Notar que el update también se puede hacer de 2 maneras, una es la clásica,
		 * que es la que está comentada a continuación: 
		 * 
		 * MapSqlParameterSource mapParameter = new MapSqlParameterSource();
		 * mapParameter.addValue("mail", mailParameter);m
		 * apParameter.addValue("id", id);
		 * 
		 * De esta manera, se tienen que pasar todos los atributos por parámetro que se 
		 * quieran actualizar (Update). Pero corremos un riesgo y es dificil de manter.
		 * 
		 * Entonces para tener una mejor práctica al momento de hacer un Update, podemos
		 * usar: BeanPropertySqlParameterSource, como lo hace el insert.
		 * Tener en cuenta de que como en este caso usamos el id como referencia, tenemos
		 * que agregar al constructor del bean, el id. 
		 * 		
		*/
		BeanPropertySqlParameterSource beanPropertyParameterSource = new BeanPropertySqlParameterSource(offers);
		String updateMailSql = "update offers set  email = :email, "
				+ "text = :text where id=:id";
				
		return this.namedParameterJdbcTemlate.update(updateMailSql, beanPropertyParameterSource) == 1;
		
	}
	
	
	/**
	 * <p>Como podemos hacer queries en general a la base de datos, también podemos 
	 * hacer, logicamente, insert.
	 * </p>
	 * 
	 * <p>Para esto tenemos que tener al bean, en este caso sería Offers, con un
	 * constructor con todos sus parámetros, teniendo en cuenta que campo en 
	 * la tabla es autoincremental. De esta manera se puede crear un Offer
	 * directamente y poder hacer el create
	 * </p>
	 * 
	 * <p>Debemos tener en cuenta de usar:
	 * BBeanPropertySqlParameterSource, este sirve para mapear todos los atributos 
	 * del objeto que se quiera hacer insert. Lo que si hay que tener en cuenta es que
	 * en el momento de hacer la query con el insert, los parámetros tienen que ser 
	 * exactamente los mismos.
	 * </p>
	 *
	 *
	 * @param Offers
	 * */
	public boolean createOffer(Offers offers){
		
		BeanPropertySqlParameterSource beanPropertyParameterSource = new BeanPropertySqlParameterSource(offers);
		
		String insertSql = "insert into offers (name, email, text) "
				+ "values (:name, :email, :text)";
		
		return this.namedParameterJdbcTemlate.update(insertSql, beanPropertyParameterSource) == 1;
		
		/*
		 * Aclaración return para todos los métodos boolean
		 * 
		 * return this.namedParameterJdbcTemlate.update(insertSql, beanPropertyParameterSource) == 1;
		 * Notar que lo que se retorna es la igualdad ( == 1 ) ya que el 
		 * namedParameterJdbcTemplate.update retorna el número de rows (filas, registros)
		 * que ha afectado a la tabla a la que estamos haciendo el update.
		 * Para todos los boolean de este DAO, se está usando esta forma de return.
		 * 
		 * */
		
		
		
		
	}
	
	
	/**
	 * 
	 * <p>Además de poder insertar objetos uno a uno, podemos hacerlo de a muchos, por ejemplo
	 * podemos hacerlo a traves de una lista.
	 * Esto es debido a Spring Batch Updates, que es el que nos permite hacer este tipo de 
	 * inserts. Claro que podemos iterar una y otra vez el .update del método 
	 * createOffer(Offer offer), pero Spring nos ofrece una manera mucho mas elegante y mas 
	 * performante de poder hacer un insert con una lista de Objetos.
	 * </p>
	 * 
	 * <p> Para este tipo de insert, hay que tener en cuenta 2 cosas:
	 * <pre>1.- Usar SqlParameterSource[], esta es una Interface nos permite agregar valores
	 * 	(como parámetros) para una query Sql específica. Todos los valores de los 
	 * 	parámetros son identificados por su nombre específico. En este caso, :name,
	 * 	:email, :text.
	 *  
	 *2.- Método que provee: NamedParameterJdbcTemplate = batchUpdate. Este, nos ofrece
	 *    hacer el insert masivo (en este caso con la lista de Offers).<br>
	 *  </pre></p>
	 *    
	 *  <p>Esta forma de hacer el insert, reemplazaría al PreparedStatement de Java. Que si bien
	 *  se puede utilizar, Spring nos ofrece una manera mucho mejor de hacerlo.
	 *  </p>
	 * 
	 * @param Offers
	 * @return int Array
	 */	
	@Transactional
	public int[] createOffers(List<Offers> offersList){
		
		SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(offersList.toArray());
		
		String insertSql = "insert into offers (name, email, text) "
				+ "values (:name, :email, :text)";
		
		return this.namedParameterJdbcTemlate.batchUpdate(insertSql, params);
		
		
	}

	
	@Autowired
	public void setDataSource(DataSource dataSource){
		
		this.namedParameterJdbcTemlate = new NamedParameterJdbcTemplate(dataSource);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
