package JV.DB.model;

public class Watch {
	
	
	
	String iduser;
	String username;
	String imguser;
	String data;
	public Watch(String iduser, String data,String username,String imguser) {
		super();
		this.iduser = iduser;
		this.data = data;
		this.username = username;
		this.imguser = imguser;
	}

	
	
	

	public String getUsername() {
		return username;
	}





	public void setUsername(String username) {
		this.username = username;
	}





	public String getImguser() {
		return imguser;
	}





	public void setImguser(String imguser) {
		this.imguser = imguser;
	}





	public String getIduser() {
		return iduser;
	}



	public void setIduser(String iduser) {
		this.iduser = iduser;
	}



	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	

}
