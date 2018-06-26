package model.bean;

public class Delito {

	private String nome;
	private String descricao;
	
	public Delito() {

	}
	
	public Delito(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
	}
	
	public Delito(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return "Delito: " + this.nome;
	}
}
