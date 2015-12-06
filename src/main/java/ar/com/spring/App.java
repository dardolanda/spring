package ar.com.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import ar.com.spring.dao.OffersDAO;
import ar.com.spring.dao.SingleOfferDAO;

public class App {

	public static void main(String[] args) {

		/*
		 * INSTANCIAR OBJETOS SIN TENER EN CUENTA EL CLASSPATH
		 * 
		 * Spring Bean container: Tal cual lo dice,es un contenedor de beans que
		 * nos permite instanciar los objetos a traves del XML llamado:
		 * beans.xml, que fue el que definimos en el root del proyecto. Y es por
		 * esto mismo que en la definición del FileSystemXmlApplicationContext
		 * se usa "beans.xml" sin especificar el path
		 * 
		 * 
		 * ApplicationContext context = new
		 * FileSystemXmlApplicationContext("beans.xml");
		 * 
		 * /* Una vez que tenemos el contexto generado, podemos usarlo para
		 * instanciar al objeto Person. Antes, en java (sin ningún framework) lo
		 * hacíamos de esta manera:
		 * 
		 * Person persona = new Person();
		 * 
		 * Y es acá donde viene la diferencia.... Primero se toma el bean que se
		 * quiere generar: getBean("id del bean.xml"); Luego hay que castearlo
		 * para tener presente al objeto
		 * 
		 * 
		 * 
		 * Person persona = (Person) context.getBean("person"); persona.speak();
		 * 
		 * /* Luego, cuando esta todo terminado, podemos "suprimir" el warning
		 * del context cerrándolo, de la siguiente manera:
		 * 
		 * ((FileSystemXmlApplicationContext)context).close();
		 * 
		 * 
		 * Notar que el context no es mas que un
		 * FileSystemXmlApplicationContext. Además, cuando la aplicación
		 * termina, el context se termina cerrando.
		 */

		/*
		 * INSTANCIAR OBJETOS TENIENDO EN CUENTA EL PATH
		 * 
		 * Antes que nada, hay que cambiar de lugar al archivo beans.xml y lo
		 * vamos hacer en otro package que se va a llamar ar.com.spring.beans
		 * 
		 * ar\\com\\spring\\beans\\ src/main/java/ar/com/spring/beans/
		 * 
		 * /SpringPrueba/src/main/java/ar/com/spring/beans/beans.xml
		 * 
		 * /home/landa/desarrolloJava/SpringWorkspace/SpringPrueba/ar/com/spring
		 * /beans/ /SpringPrueba/src/main/java/ar/com/spring/beans/beans.xml
		 */

		ApplicationContext context = new FileSystemXmlApplicationContext("beans.xml");

		Person persona = (Person) context.getBean("person");
		Adress adress = (Adress) context.getBean("adress");
		WorkPlace workPlace = (WorkPlace) context.getBean("workPlace");
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		SingleOfferDAO singleOfferDao = (SingleOfferDAO) context.getBean("singleOfferDao");
		
		persona.speak();

		workPlace.setId(23);
		workPlace.setName("Lugar 1");
		workPlace.setNumber(1235014);

		// persona.setWorkPlace(workPlace);

		// System.out.println(workPlace);
		System.out.println(adress);
		System.out.println(persona);

		System.out.println();
		System.out.println("------------ Spring JDBC Test Conection MySql ------------");

		try {
			
			if(singleOfferDao.deleteById(4)){
				
				System.out.println("El integrante con Id: 4 se eliminó correctamente");
				
			}
			
			System.out.println();
			
			
			/*
			 * Offers Update desde el objeto sin usar los parámetros, usando:
			 * BeanPropertySqlParameterSource 
			 * 
			 * */
			Offers offerUpdate = new Offers(5, "pirulino", "pHijoDePuta@forro.com", "no te pido 28 pases seguidos como el Barcelona, te pido 3!!");
			
			if(singleOfferDao.updateMail(offerUpdate)){
				
				System.out.println("El integrante 5 ha cambiado el mail correctamente.");
				
			}
			/*
			 * Notar que hicimos el insert desde el DAO directamente, creando el objeto Offers
			 * con el nuevo constructor.
			 * */
			Offers offersDardo = new Offers("Dardo", "dardolanda@gmail.com", "Creando desde Create");
			Offers offersCarlos = new Offers("Carlos", "carlosleis@gmail.com", "Insert desde Create");
			
			singleOfferDao.createOffer(offersDardo);
			singleOfferDao.createOffer(offersCarlos);			

			System.out.println();
			for (Offers offers : offersDao.getOffers()) {

				// System.out.println(offers.getName());
				// System.out.println(offers.getEmail());
				// System.out.println(offers.getId());
				// System.out.println(offers.getText());

				System.out.println(offers);

			}
			
			System.out.println();
			System.out.println();
			System.out.println("Debería ser Pirulino:  " + singleOfferDao.getOfferById(5));
			
			
			

		} catch (CannotGetJdbcConnectionException connectionException) {

			connectionException.printStackTrace();
			System.out.println(connectionException.getMessage());
			System.out.println("No se puede conectar a la base de datos");
			
			/*
			 * Notar que en este caso no hace falta pedir el .getClass, 
			 * ya que sabemos que tipo de clase es: 
			 * 
			 * CannotGetJbdcConnectionException
			 * */
			
		} catch (DataAccessException dataAccessException) {

			/*
			 * Cualquier exception que venga desde el dao, esta cĺáusula:
			 * try-catch, la va a tomar con esta RuntimeException.
			 * 
			 */

			dataAccessException.printStackTrace();

			/*
			 * Notar para un buen manejo de Excepciones: se puede manejar el
			 * printStackTrace, getMessage --> Tipo String. Pero además, se
			 * puede manejar el getClass --> y con este se puede saber que tipo
			 * de exception se puede catchear
			 * 
			 */

			System.out.println(dataAccessException.getMessage());
			System.out.println(dataAccessException.getClass());

		}

		((FileSystemXmlApplicationContext) context).close();

	}
}
