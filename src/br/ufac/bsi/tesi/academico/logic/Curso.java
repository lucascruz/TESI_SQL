package br.ufac.bsi.tesi.academico.logic;

public class Curso {
		private String nome;
		private String codigo;
		
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		
		public String getCodigo() {
			return codigo;
		}
		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}
		
		public String toString(){
			return this.nome;
		}
}

