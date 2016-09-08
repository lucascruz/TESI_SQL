package br.ufac.bsi.tesi.academico.logic;

public class Professor {

	private String sigla;
	private String nome, endereco;
	private int rg, cpf, fone, matricula;

	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public int getRg() {
		return rg;
	}
	public void setRg(int rg) {
		this.rg = rg;
	}
	public int getCpf() {
		return cpf;
	}
	public void setCpf(int cpf) {
		this.cpf = cpf;
	}
	public int getFone() {
		return fone;
	}
	public void setFone(int fone) {
		this.fone = fone;
	}
	public int getMatricula() {
		return matricula;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
	public String getSigla() {
		return sigla;
	}
	public String getNome() {
		return nome;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
