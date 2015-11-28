package ar.com.spring;

public class Offers {
	
	/*
	 * Esta clase sería como un POJO, pero en este entorno 
	 * la vamos a llamar bean (Ya que estamos usando Spring)
	 * 
	 * */
	
	private int id;
	private String name;
	private String email;
	private String text;
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		
		return "Offers [id=" + id +
			   ", name=" + name + 
			   ", email=" + email + 
			   ", text=" + text 
			   + "]";
	}
	
	
	

}
