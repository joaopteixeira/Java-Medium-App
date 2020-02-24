package JV.DB.model;

public class District {

	private String id;
	private String name;
	
	
	
	 
	public District() {
		super();
	}
	public District( String name) {
		super();
		this.name = name;
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
	
	
}
