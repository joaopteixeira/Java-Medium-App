package JV.DB.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

public class Country {
	
	
	@Id
	private String id;
	
	private String name;
	
	private ArrayList<District> districts;

	public Country(String name) {
		super();
		this.name = name;
		this.districts = new ArrayList<>();
	}
	

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<District> getDistricts() {
		return districts;
	}
	
	
	
	
	
	

}
