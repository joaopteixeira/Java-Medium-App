package JV.DB.model;

public class Mensagem {
	
	
	public static final int VISTO=0,NVISTO=1;
	
	String id;
	
	User user;
	String msg;
	String date;
	int estado;
	
	
	
	
	public Mensagem(String id, User user, String msg, String date,int estado) {
		super();
		this.id = id;
		this.user = user;
		this.msg = msg;
		this.date = date;
		this.estado = estado;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	

}
