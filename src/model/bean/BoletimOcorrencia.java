package model.bean;

import java.util.List;

public class BoletimOcorrencia {
	private Delito delito;
	private List<Pessoa> vitimas;
	private List<Pessoa> acusados;
	private String descricao;
	private String rua;
	private String bairro;
	
	
	public BoletimOcorrencia() {
	}

	public BoletimOcorrencia(Delito delito, List<Pessoa> vitimas, List<Pessoa> acusados, String descricao, String rua,
			String bairro) {
		this.delito = delito;
		this.vitimas = vitimas;
		this.acusados = acusados;
		this.descricao = descricao;
		this.rua = rua;
		this.bairro = bairro;
	}
	
	public BoletimOcorrencia(Delito delito, String descricao, String rua,
			String bairro) {
		this.delito = delito;
		this.descricao = descricao;
		this.rua = rua;
		this.bairro = bairro;
	}
	
	public Delito getDelito() {
		return delito;
	}

	public void setTipo(Delito delito) {
		this.delito = delito;
	}

	public List<Pessoa> getVitimas() {
		return vitimas;
	}

	public void setVitimas(List<Pessoa> vitimas) {
		this.vitimas = vitimas;
	}

	public List<Pessoa> getAcusados() {
		return acusados;
	}

	public void setAcusados(List<Pessoa> acusados) {
		this.acusados = acusados;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	@Override
	public String toString() {
		return "Tipo de Ocorrencia: "+ this.delito.getNome() +"\nVitimas: "+ this.vitimas +"\nAcusados: "+ this.acusados + "\nDescris�o do B.O: "+ this.descricao + "\nRua: "+ this.rua
				+"\nBairro: "+ this.bairro; 
	}
}
