package JV.DB.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

public class Chat {
	
	@Id
	private String id;
	ArrayList<User> users;
	ArrayList<Mensagem> mensagens;
	
	
	public Chat() {
		super();
		this.users = new ArrayList<>();
		this.mensagens = new ArrayList<>();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public ArrayList<User> getUsers() {
		return users;
	}


	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}


	public ArrayList<Mensagem> getMensagens() {
		return mensagens;
	}


	public void setMensagens(ArrayList<Mensagem> mensagens) {
		this.mensagens = mensagens;
	}
	
	
	
	
	
	

}
