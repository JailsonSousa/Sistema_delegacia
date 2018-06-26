package model.bean;

public class Vitima extends Pessoa {
	public Vitima() {
		super();
	}

	public Vitima(String nome, String cpf, String rua, String bairro, String telefone) {
		super(nome, cpf, rua, bairro, telefone);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}