package ar.com.spring;

public class Adress {
	
	private String street;
	private String postCode;
	
	public Adress(String street, String postCode){
		
		this.street = street;
		this.postCode = postCode;
		
	}

	@Override
	public String toString() {
		return "Adress [street=" + street + ", postCode=" + postCode + "]";
	}
	
	
	
	

}
