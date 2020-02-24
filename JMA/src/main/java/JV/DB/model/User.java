package JV.DB.model;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	
	public static final int FEMALE = 1; 
	public static final int MALE = 2; 

	public static final int WATCHED = 1,WATCHING =2; 
	
	@Id
	String id;
	
	 String firstname;

	 String lastname;
	 
	 String email;

	 String birthdate;

	 String district;

	 String country;

	 int type;     //1 Procura 0 Artistas

	 String phonenumber;
	 
	 String website;
	 
	 String description;
	 
		String pathimage;   

		int tokkensquantity,gender;
	
		int status, accactivated;
		
		String category;
		String subcategory;
		

		ArrayList <Tag> preferences;
		
		ArrayList <Watch> watching,watched;
		
		ArrayList<String> hashes;

		String username;
		
		int userwatched;   //Size do arraylist que está dentro dele depois, quando o gajo pedir os posts, vou fazer um set ás variaveis 


		
		
	 
	 public String getWebsite() {
		return website;
	}





	public void setWebsite(String website) {
		this.website = website;
	}







	
	
	public String getDescription() {
		return description;
	}





	public void setDescription(String description) {
		this.description = description;
	}





	public String getCategory() {
		return category;
	}





	public User() {
		super();
		this.preferences = new ArrayList<>();
		this.hashes = new ArrayList<>();
		this.watched = new ArrayList<>();
		this.watching = new ArrayList<>();
	}





	public User(String id,String firstname, String lastname, String email, String pathimage, String birthdate, int gender,
			String district, String country, int type, String phonenumber, int tokkensquantity, int status,
			int accactivated,String category,String subcategory,String description,String website) {
		super();
		this.id=id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.pathimage = pathimage;
		this.email = email;
		this.birthdate = birthdate;
		this.website = website;
		this.description = description;
		this.gender = gender;
		this.district = district;
		this.country = country;
		this.type = type;
		this.phonenumber = phonenumber;
		this.tokkensquantity = tokkensquantity;
		this.status = status;
		this.accactivated = accactivated;
		this.category = category;
		this.subcategory = subcategory;
		this.username = "";
		this.userwatched = 0;
		this.preferences = new ArrayList<>();
		this.hashes = new ArrayList<>();
		this.watched = new ArrayList<>();
		this.watching = new ArrayList<>();
		
	}


	
	

	public String getUsername() {
		return username;
	}





	public void setUsername(String username) {
		this.username = username;
	}





	public int getUserwatched() {
		return userwatched;
	}





	public void setUserwatched(int userwatched) {
		this.userwatched = userwatched;
	}





	public void setHashes() {
		this.hashes = new ArrayList<>();
	}





	public String getPathimage() {
		return pathimage;
	}




	public void setPathimage(String pathimage) {
		this.pathimage = pathimage;
	}





	public String getFirstname() {
		return firstname;
	}




	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}




	public String getLastname() {
		return lastname;
	}




	public void setLastname(String lastname) {
		this.lastname = lastname;
	}




	public String getBirthdate() {
		return birthdate;
	}




	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}




	public String getPhonenumber() {
		return phonenumber;
	}




	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}




	public int getTokkensquantity() {
		return tokkensquantity;
	}




	public void setTokkensquantity(int tokkensquantity) {
		this.tokkensquantity = tokkensquantity;
	}




	public int getAccactivated() {
		return accactivated;
	}




	public void setAccactivated(int accactivated) {
		this.accactivated = accactivated;
	}











	public String getSubcategory() {
		return subcategory;
	}





	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}





	public void setCategory(String category) {
		this.category = category;
	}






	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	

	public String getDistrict() {
		return district;
	}





	public void setDistrict(String district) {
		this.district = district;
	}



	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	

	public ArrayList<Tag> getPreferences() {
		return preferences;
	}

	public ArrayList<Watch> getWatching() {
		return watching;
	}

	public ArrayList<Watch> getWatched() {
		return watched;
	}

	public ArrayList<String> getHashes() {
		return hashes;
	}
	
	
	public ArrayList<Tag> mockupdata(){
		
		ArrayList<Tag> aux = new ArrayList<>();
		aux.add(new Tag("Musica",false));
		return aux;
		
	}
	
	
	

}
