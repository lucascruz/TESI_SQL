package br.ufac.bsi.tesi.academico.logic;

public class Professor{
	private String nome, endereco, cpf, fone;
	private String centro_sigla, matricula, rg;
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula2) {
		this.matricula = matricula2;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getFone() {
		return fone;
	}
	public void setFone(String fone) {
		this.fone = fone;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCentro_sigla() {
		return centro_sigla;
	}
	public void setCentro_sigla(String centro_sigla) {
		this.centro_sigla = centro_sigla;
	}

}
