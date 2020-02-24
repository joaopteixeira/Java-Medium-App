package JV.DB.model;

import org.springframework.data.annotation.Id;

public class Comment {
	
	//User user;
	
	@Id
	String id;
	
	String iduser;
	String imguser;
	String username;
	String content;
	String date;

	String tag;
	
	
	public Comment(String id,String iduser,String username, String content, String date,String imguser,String tag) {
		super();
		this.id=id;
		this.iduser = iduser;
		this.username = username;
		this.imguser = imguser;
		this.content = content;
		this.date = date;
		this.username = username;
		this.tag = tag;
	}
	
	
	
	public String getId() {
		return id;
	}

	


	public String getTag() {
		return tag;
	}



	public void setTag(String tag) {
		this.tag = tag;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getIduser() {
		return iduser;
	}
	
	
	
	public String getImguser() {
		return imguser;
	}
	public void setImguser(String imguser) {
		this.imguser = imguser;
	}
	public void setIduser(String iduser) {
		this.iduser = iduser;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	
	
	

}
