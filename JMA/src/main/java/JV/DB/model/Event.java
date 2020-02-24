package JV.DB.model;

import org.springframework.data.annotation.Id;

public class Event {
	
	@Id
	private String id;
	private String title;
	private String iduser;
	private District district;
	private String description;
	private String address;
	private String date;
	private Category category;
	private String pathimage;
	private String username;
	
	
	
	
	public Event() {
		super();
		this.district = new District();
		this.category = new Category();
	}

	public Event(String title, String iduser, District district, String description, String address,Category category,String date,String pathimage,String username) {
		super();
		this.title = title;
		this.iduser = iduser;
		this.district = district;
		this.description = description;
		this.address = address;
		this.category = category;
		this.date = date;
		this.pathimage = pathimage;
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
	
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPathimage() {
		return pathimage;
	}

	public void setPathimage(String pathimage) {
		this.pathimage = pathimage;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIduser() {
		return iduser;
	}

	public void setIduser(String iduser) {
		this.iduser = iduser;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	

}
