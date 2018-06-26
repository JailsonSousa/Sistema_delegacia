package model.bean;

public class Acusado extends Pessoa {
	public Acusado() {
		super();
	}

	public Acusado(String nome, String cpf, String rua, String bairro, String telefone) {
		super(nome, cpf, rua, bairro, telefone);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}