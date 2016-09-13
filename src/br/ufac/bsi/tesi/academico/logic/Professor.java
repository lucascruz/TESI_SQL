package br.ufac.bsi.tesi.academico.logic;

public class Professor {

	private int matricula;
	private String nome;
	private int rg;
	private int cpf;
	private String endereco;
	private String fone;
	private Centro centro;
	
	public int getMatricula() {
		return matricula;
	}
	public String getNome() {
		return nome;
	}
	public int getRg() {
		return rg;
	}
	public int getCpf() {
		return cpf;
	}
	public String getEndereco() {
		return endereco;
	}
	public String getFone() {
		return fone;
	}
	public Centro getCentro() {
		return centro;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setRg(int rg) {
		this.rg = rg;
	}
	public void setCpf(int cpf) {
		this.cpf = cpf;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public void setFone(String fone) {
		this.fone = fone;
	}
	public void setCentro(Centro centro) {
		this.centro = centro;
	}
	
}
