package ar.com.spring;

public class WorkPlace {
	
	private int id;
	private String name;
	private int number;
	
	
	public WorkPlace(int id, String name, int number){
		
		this.id = id;
		this.name = name;
		this.number = number;		
		
	}


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


	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}
	
	
	public String toString(){
		
		return "WORKPLACE   -   ID: " + this.id + " Name: " + this.name + " Number: " + this.number;
		
	}
	
	

}
