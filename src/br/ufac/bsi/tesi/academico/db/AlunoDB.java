package br.ufac.bsi.tesi.academico.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.ufac.bsi.tesi.academico.logic.Aluno;
import br.ufac.bsi.tesi.academico.logic.Curso;

public class AlunoDB {
	private Conexao conexao;
	private CursoDB cdb = new CursoDB();
	private ResultSet rs;
	
	public void setConexao(Conexao conexao){
		this.conexao = conexao;
		cdb.setConexao(conexao);
	}
	public boolean addAluno(Aluno aluno){
		String strIncluir = "INSERT INTO aluno (matricula, nome, fone, endereco, cep, sexo, curso_codigo) VALUES ('" +
				aluno.getMatricula() + "', '" + 
				aluno.getNome() + "', '" + 
				aluno.getFone() + "', '" + 
				aluno.getEndereco() + "', '" + 
				aluno.getCep() + "', '" + 
				aluno.getSexo() + "', '" + 
				aluno.getCurso().getCodigo() + "');";
			
		return (conexao.atualize(strIncluir)>0);
	}
	
	public boolean updAluno(Aluno aluno){
		String strEditar = "UPDATE aluno " +
				"SET nome = '" + aluno.getNome() + "', " + 
				"    fone = '" + aluno.getFone() + "', " + 
				"	 endereco = '" + aluno.getEndereco() + "', " + 
				"	 cep = '" + aluno.getCep() + "', " +
				"	 sexo = '" + aluno.getSexo() + "', " +
				"	 curso_codigo = '" + aluno.getCurso().getCodigo() + "' "+
				"WHERE matricula = " + aluno.getMatricula() + ";";
		
		return (conexao.atualize(strEditar)>0);

	}
	
	public boolean delAluno(Aluno aluno){
		String strExcluir = "DELETE FROM aluno "
				+ "WHERE matricula = '" + aluno.getMatricula() + "';";
		
		return (conexao.atualize(strExcluir)>0);

	}
	
	public Aluno getAluno(String matricula){
	
		Aluno aluno = null;
		Curso curso = null;
		
		String strConsultar = "SELECT matricula, nome, fone, endereco, cep, sexo, curso_codigo" 
			 + " FROM aluno "
			 + " WHERE matricula = " + matricula + ";"; 

		rs = conexao.consulte(strConsultar);
		
		if(rs != null){
			try{ 
				if (rs.next()){ 
					aluno = new Aluno();
					aluno.setMatricula(rs.getString(1));
					aluno.setNome(rs.getString(2));
					aluno.setFone(rs.getString(3));
					aluno.setEndereco(rs.getString(4));
					aluno.setCep(rs.getString(5));
					aluno.setSexo(rs.getString(6));
			
					curso = cdb.getCurso(rs.getString(7));
					
					if (curso != null){
						aluno.setCurso(curso);
					}
				}
			}catch(SQLException sqle){
				JOptionPane.showMessageDialog(null, sqle.getErrorCode(), sqle.getMessage(), 
						JOptionPane.PLAIN_MESSAGE);
				 
			}
		}
		return aluno;
	}
	public List<Aluno> getTodosAlunos() {
		List<Aluno> alunos = new ArrayList<Aluno>();
		Curso curso = null;
		Aluno aluno = null;
		
		String strConsultar = "SELECT matricula, nome, fone, endereco, cep, sexo, curso_codigo"
				+ " FROM aluno;"; 

		rs = conexao.consulte(strConsultar);
		
		if(rs != null){
			try{ 
				while (rs.next()){
					aluno = new Aluno();
					aluno.setMatricula(rs.getString(1));
					aluno.setNome(rs.getString(2));
					aluno.setFone(rs.getString(3));
					aluno.setEndereco(rs.getString(4));
					aluno.setCep(rs.getString(5));
					aluno.setSexo(rs.getString(6));

					curso = cdb.getCurso(rs.getString(7));
					
					if (curso != null){
						aluno.setCurso(curso);
					}
					alunos.add(aluno);
				}
			}catch(SQLException sqle){
				JOptionPane.showMessageDialog(null, sqle.getErrorCode(), sqle.getMessage(), 
						JOptionPane.PLAIN_MESSAGE);
			}
		}
		return alunos;
	}
}
