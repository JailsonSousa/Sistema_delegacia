package model.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.bean.Acusado;
import model.bean.BoletimOcorrencia;
import model.bean.Delito;
import model.bean.Pessoa;
import model.bean.Vitima;
import model.dao.BoletimOcorrenciaDAO;
import model.dao.DelitoDAO;
import model.dao.PessoaDAO;

public class SistemaDelegacia {
	
	public static void help(){
		System.out.println(" ===== BOLETIM DE OCORRENCIA ===== ");
		System.out.println(" criaBO         --  Gerar um Boletim de ocorrencia");
		System.out.println(" deletaBO       --  Apagar um Boletim de ocorrencia");
		System.out.println(" consultaBO     --  Pesquisar um Boletim de ocorrencia");
		//System.out.println(" exibirTodosBOs      --  mostra todos os B.O's cadastrado");
		System.out.println(" alteraBO       --  Modificar um Boletim de ocorrencia");
		System.out.println(" ===== GERAL ===== ");
		System.out.println(" help           --  exibe menu");
		System.out.println(" fechar         --  Fecha o sistema");
	}
	
	public static Pessoa criarPessoa(String cpf, Scanner input, PessoaDAO pessoa) {
		System.out.println("Informe o nome:");
		String nome = input.nextLine();
		System.out.println("Nome da rua:");
		String rua = input.nextLine();
		System.out.println("Bairro:");
		String bairro = input.nextLine();
		System.out.println("Informe o telefone");
		String telefone = input.nextLine();
		pessoa.create(new Pessoa(nome, cpf, rua, bairro, telefone));
		return new Pessoa(nome, cpf, rua, bairro, telefone);
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		PessoaDAO pessoa = new PessoaDAO();
		BoletimOcorrenciaDAO bo = new BoletimOcorrenciaDAO();
		DelitoDAO delito = new DelitoDAO();
		List<Pessoa> vitimas = null;
		List<Pessoa> acusados = null;
		List<Delito> listaDelito = null;
		Delito tipoDelito = null;
		boolean fechar = false;
		System.out.println(" ===== Sistema de Delegacia ===== ");
		while (!fechar) {
			
			System.out.print(">>");
			String linha = input.nextLine();
			String[] comando = linha.split(" ");
			String operacao = comando[0];
			switch (operacao) {
			
				case "criaBO":	
					
					vitimas = new ArrayList<Pessoa>(); 
					acusados = new ArrayList<Pessoa>();
					
					System.out.println("Informe o n�mero referente ao delito");
					
					listaDelito = delito.getAllDelito();
					for (int i = 0; i < listaDelito.size(); i++) {
						System.out.println(i +" - "+ listaDelito.get(i).getNome() + " - "+listaDelito.get(i).getDescricao());
					}
						
					tipoDelito = listaDelito.get(Integer.parseInt(input.nextLine()));
					
					System.out.println("Informe a quantidade de vitimas: ");
					int qtdVitima = Integer.parseInt(input.nextLine());
					
					for(int i = 0; i < qtdVitima; i++) {
						
						System.out.println("Informe o cpf da vitima: ");
						String cpf = input.nextLine();
					
						Pessoa vitima = pessoa.getPessoa(cpf);
			
						if(vitima != null) {
							vitimas.add(vitima);
						}
						else {
							vitimas.add(criarPessoa(cpf, input, pessoa));
						}
					}
					
					System.out.println("Informe a quantidade de Acusados: ");
					int qtdAcusados = Integer.parseInt(input.nextLine());
					
						for(int i = 0; i < qtdAcusados; i++) {
							System.out.println("Informe o cpf do acusado: ");
							String cpf = input.nextLine();
							Pessoa acusado = pessoa.getPessoa(cpf);
							if(acusado != null) {
								acusados.add(acusado);
							}
							else {
								acusados.add(criarPessoa(cpf, input, pessoa));
							}
						}
						
					System.out.println("Descreva o ocorrido.");
					String descricao = input.nextLine();
					System.out.println("informe a rua do ocorrido.");
					String rua = input.nextLine();
					System.out.println("Informe o bairro");
					String bairro = input.nextLine();
					
					System.out.println("Registrando o B.O ...");
					bo.create(new BoletimOcorrencia(tipoDelito, vitimas, acusados, descricao, rua, bairro));
					break;
				case "deletaBO":
					System.out.println("Informe o c�digo do B.O");
					int iBO = Integer.parseInt(input.nextLine());
					if(bo.getBoletimOcorrencia(iBO) == null) {
						System.out.println("B.O n�o existe.");
						break;
					}
					bo.delete(iBO);
					break;	
				case "consultaBO":
					System.out.println("Informe o c�digo do B.O");
					//int codigoBO = ;
					BoletimOcorrencia boletim = bo.getBoletimOcorrencia(Integer.parseInt(input.nextLine()));
					if(boletim == null) {
						System.out.println("Boletim não existe");
						break;
					}
					System.out.println(boletim);
					break;
				case "alteraBO":
					System.out.println("Informe o c�digo do B.O");
					int id = Integer.parseInt(input.nextLine());
					if(bo.getBoletimOcorrencia(id) == null) {
						System.out.println("B.O n�o existe.");
						break;
					}
					
					vitimas = new ArrayList<Pessoa>(); 
					acusados = new ArrayList<Pessoa>();
					
					System.out.println("Informe o n�mero referente ao delito");
					
					listaDelito = delito.getAllDelito();
					for (int i = 0; i < listaDelito.size(); i++) {
						System.out.println(i +" - "+ listaDelito.get(i).getNome() + " - "+listaDelito.get(i).getDescricao());
					}
						
					tipoDelito = listaDelito.get(Integer.parseInt(input.nextLine()));
					
					System.out.println("Informe a quantidade de vitimas: ");
					int qtd = Integer.parseInt(input.nextLine());
					
					for(int i = 0; i < qtd; i++) {
						
						System.out.println("Informe o cpf da vitima: ");
						String cpf = input.nextLine();
					
						Pessoa vitima = pessoa.getPessoa(cpf);
			
						if(vitima != null) {
							vitimas.add(vitima);
						}
						else {
							vitimas.add(criarPessoa(cpf, input, pessoa));
						}
					}
					
					System.out.println("Informe a quantidade de Acusados: ");
					qtd = Integer.parseInt(input.nextLine());
					
						for(int i = 0; i < qtd; i++) {
							System.out.println("Informe o cpf do acusado: ");
							String cpf = input.nextLine();
							Pessoa acusado = pessoa.getPessoa(cpf);
							if(acusado != null) {
								acusados.add(acusado);
							}
							else {
								acusados.add(criarPessoa(cpf, input, pessoa));
							}
						}
						
					System.out.println("Descreva o ocorrido.");
					String d = input.nextLine();
					System.out.println("informe a rua do ocorrido.");
					String r = input.nextLine();
					System.out.println("Informe o bairro");
					String b = input.nextLine();
					
					System.out.println("Atualizando o B.O ...");
					bo.update(id, new BoletimOcorrencia(tipoDelito, vitimas, acusados, d, r, b));
					break;					
				case "help":
						help();
					break;
				case "fechar":
					fechar = true;
					System.out.println("Fechando o sistema...");
					System.exit(1);
					break;
				default:
					System.out.println("Comando n�o encontrado!");
					break;	
			}
		}
	}
}
