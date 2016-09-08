package br.ufac.bsi.tesi.academico.logic;

public class Aluno {
	
	private String nome, endereco, sexo, curso_codigo;
	private int fone, cep, matricula;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public int getFone() {
		return fone;
	}
	public void setFone(int fone) {
		this.fone = fone;
	}
	public String getCurso_codigo() {
		return curso_codigo;
	}
	public void setCurso_codigo(String codigo) {
		this.curso_codigo = codigo;
	}
	public int getCep() {
		return cep;
	}
	public void setCep(int cep) {
		this.cep = cep;
	}
	public int getMatricula() {
		return matricula;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
	
	

}
