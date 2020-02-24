package JV.DB.model;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;

public class Post {

	@Id
	String id;
	
	String title,
	date,
	content;
	
	String username;
	int userwatched;
	String userimage;
	
	ArrayList<String> likes;
	
	
	String iduser;
		
	ArrayList<Comment> comments;
	
	Media media;
	
	
	public Post(String title, String date, String content, String iduser, String username,int userwatched,String userimage) {
		super();
		this.title = title;
		this.date = date;
		this.likes = new ArrayList<>();
		this.content = content;
		this.iduser = iduser;
		this.username = username;
		this.userimage = userimage;
		this.userwatched = userwatched;
		this.comments = new ArrayList<>();
	}
	
	

	public Post() {
		
		super();
		this.comments = new ArrayList<>();
		this.likes = new ArrayList<>();
		
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

	


	public String getUserimage() {
		return userimage;
	}



	public void setUserimage(String userimage) {
		this.userimage = userimage;
	}


	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	
	

	
	



	public ArrayList<Comment> getComments() {
		return comments;
	}



	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}



	public ArrayList<String> getLikes() {
		return likes;
	}



	public void setLikes(ArrayList<String> likes) {
		this.likes = likes;
	}





	public String getIduser() {
		return iduser;
	}



	public void setIduser(String iduser) {
		this.iduser = iduser;
	}



	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}


	
	
	
	
	
}
