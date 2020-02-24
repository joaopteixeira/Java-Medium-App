package JV.DB.model;

import org.springframework.data.annotation.Id;

public class Notify {
	
	public static final int TPOST=0,TMSG=1,SUBTPLIKE=2,SUBTPCOMMENT=3,TEVENT=4;
	public static final int VISTO=0,NVISTO=1;

	
	@Id
	private String id;
	
	
	
	private int type;
	private int subtype;
	String userid;
	String userdo;
	String username;
	String userimage;
	
	
	String idmsg;
	String idchat;
	String msg;
	
	
	String idpost;
	String idcomment;
	String comment;
	
	String idevent;
	
	
	int estado;
	
	
	
	
	public Notify(int type, int subtype, String userid, String userdo, String username, String userimage,int estado) {
		super();
		this.type = type;
		this.subtype = subtype;
		this.userid = userid;
		this.userdo = userdo;
		this.username = username;
		this.userimage = userimage;
		this.estado = estado;
	}
	
	
	
	
	
	
	public Notify() {
		super();
	}






	public String getIdevent() {
		return idevent;
	}





	public void setIdevent(String idevent) {
		this.idevent = idevent;
	}





	public String getIdcomment() {
		return idcomment;
	}




	public void setIdcomment(String idcomment) {
		this.idcomment = idcomment;
	}




	public int getEstado() {
		return estado;
	}




	public void setEstado(int estado) {
		this.estado = estado;
	}




	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSubtype() {
		return subtype;
	}
	public void setSubtype(int subtype) {
		this.subtype = subtype;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserdo() {
		return userdo;
	}
	public void setUserdo(String userdo) {
		this.userdo = userdo;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserimage() {
		return userimage;
	}
	public void setUserimage(String userimage) {
		this.userimage = userimage;
	}
	public String getIdmsg() {
		return idmsg;
	}
	public void setIdmsg(String idmsg) {
		this.idmsg = idmsg;
	}
	public String getIdchat() {
		return idchat;
	}
	public void setIdchat(String idchat) {
		this.idchat = idchat;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getIdpost() {
		return idpost;
	}
	public void setIdpost(String idpost) {
		this.idpost = idpost;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
	
	
	
	

}
