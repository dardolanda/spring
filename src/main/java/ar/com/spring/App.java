package ar.com.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import ar.com.spring.dao.OffersDAO;

public class App {
	
    public static void main( String[] args ){
    	
    	/* INSTANCIAR OBJETOS SIN TENER EN CUENTA EL CLASSPATH
    	 *     	
    	 * Spring Bean container: Tal cual lo dice,es un contenedor de beans que nos 
    	 * permite instanciar los objetos a traves del XML llamado: beans.xml, que 
    	 * fue el que definimos en el root del proyecto. Y es por esto mismo que en
    	 * la definición del FileSystemXmlApplicationContext se usa "beans.xml" sin 
    	 * especificar el path
    	 * 
		
		ApplicationContext context = new FileSystemXmlApplicationContext("beans.xml");
    	
    	/*
    	 * Una vez que tenemos el contexto generado, podemos usarlo para instanciar al
    	 * objeto Person.
    	 * Antes, en java (sin ningún framework) lo hacíamos de esta manera:
    	 * 
    	 * Person persona = new Person();
    	 * 
    	 * Y es acá donde viene la diferencia....
    	 * Primero se toma el bean que se quiere generar: getBean("id del bean.xml");
    	 * Luego hay que castearlo para tener presente al objeto
    	 * 
    	 * 
    	
        Person persona = (Person) context.getBean("person");
        persona.speak();
        
        /*
         * Luego, cuando esta todo terminado, podemos "suprimir" el warning del context 
         * cerrándolo, de la siguiente manera:
         
        ((FileSystemXmlApplicationContext)context).close();

        
         * Notar que el context no es mas que un FileSystemXmlApplicationContext.
         * Además, cuando la aplicación termina, el context se termina cerrando. 
         * */
        
    	/*
    	 * INSTANCIAR OBJETOS TENIENDO EN CUENTA EL PATH  
    	 * 
    	 * Antes que nada, hay que cambiar de lugar al archivo beans.xml
    	 * y lo vamos hacer en otro package que se va a llamar ar.com.spring.beans
    	 * 
    	 * ar\\com\\spring\\beans\\
    	 * src/main/java/ar/com/spring/beans/
    	 * 
    	 * /SpringPrueba/src/main/java/ar/com/spring/beans/beans.xml
    	 * 
    	 * /home/landa/desarrolloJava/SpringWorkspace/SpringPrueba/ar/com/spring/beans/
    	 * /SpringPrueba/src/main/java/ar/com/spring/beans/beans.xml
    	 * */
    	
    	ApplicationContext context = new FileSystemXmlApplicationContext("beans.xml");
    	
    	
    	Person persona = (Person) context.getBean("person");    	
    	Adress adress = (Adress) context.getBean("adress");
    	WorkPlace workPlace = (WorkPlace) context.getBean("workPlace");
    	OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
    	
    	persona.speak();
    	
    	workPlace.setId(23);
    	workPlace.setName("Lugar 1");
    	workPlace.setNumber(1235014);
    	
//    	persona.setWorkPlace(workPlace);
    	
//    	System.out.println(workPlace);
    	System.out.println(adress);
    	System.out.println(persona);
    	
    	System.out.println("------------ Spring JDBC Test Conection MySql ------------");
    	
    	for (Offers offers: offersDao.getOffers()){
    		
//    		System.out.println(offers.getName());
//    		System.out.println(offers.getEmail());
//    		System.out.println(offers.getId());
//    		System.out.println(offers.getText());
    		
    		System.out.println(offers);
    		
    	}
    	
    	
    	
    	((FileSystemXmlApplicationContext)context).close();
    	
    }
}
