package JV.DB.model;

public class Tag {

	
	String descricao;
	boolean estado;
	public Tag(String descricao, boolean estado) {
		super();
		this.descricao = descricao;
		this.estado = estado;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	
	
	
}
