package model.bean;

import java.util.ArrayList;
import java.util.List;

public class Pessoa {
	private String nome;
	private String cpf;
	private String rua;
	private String bairro;
	private String telefone;
	
	public Pessoa() {

	}

	public Pessoa(String nome, String cpf, String rua, String bairro, String telefone) {
		this.nome = nome;
		this.cpf = cpf;
		this.rua = rua;
		this.bairro = bairro;
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@Override
	public String toString() {
		return "Nome: "+this.nome+" CPF: "+this.cpf+" Rua: "+ this.rua+ "Bairro"+this.bairro+"Telefone: "+this.telefone;
	}
	
}