package br.ufac.bsi.tesi.academico.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.ufac.bsi.tesi.academico.logic.Centro;
import br.ufac.bsi.tesi.academico.logic.Professor;
import br.ufac.bsi.tesi.academico.db.CentroDB;

public class ProfessorDB {

	private Conexao conexao;
	private CentroDB cdb = new CentroDB();
	private ResultSet rs; 
	
	public void setConexao(Conexao conexao){
		this.conexao = conexao;
		cdb.setConexao(conexao);
	}

	public boolean addProfessor(Professor professor){
		String strIncluir = "INSERT INTO professor (matricula, nome, rg, cpf, endereco, fone, centro_sigla) "
				+ "VALUES (" +
				+ professor.getMatricula() +", '" 
				+ professor.getNome() + "', "
				+ professor.getRg() + ", "
				+ professor.getCpf() + ", '"
				+ professor.getEndereco() + "', '"
				+ professor.getFone() + "', '"
				+ professor.getCentro().getSigla() + "');";
						
		return (conexao.atualize(strIncluir)>0);

	}
	
	public boolean updProfessor(Professor professor){
		String strEditar = "UPDATE professor "
				+ "SET nome = '" + professor.getNome() +"', "
				+ "    rg = '" + professor.getRg() + "', "
				+ "    cpf = '" + professor.getCpf() + "', "
				+ "    endereco = '" + professor.getEndereco() + "', "
				+ "    fone = '" + professor.getFone() + "', "
				+ "    centro_sigla = '" + professor.getCentro().getSigla() + "' "
				+ "WHERE matricula = " + professor.getMatricula() + ";";
		
		return (conexao.atualize(strEditar)>0);

	}
	
	public boolean delProfessor(Professor professor){
		String strExcluir = "DELETE FROM professor "
				+ "WHERE matricula = " + professor.getMatricula() + ";";
		
		return (conexao.atualize(strExcluir)>0);

	}
	
	public Professor getProfessor(int matricula){
	
		Professor professor = null;
		Centro centro = null;
				
		String strConsultar = "SELECT matricula, nome, rg, cpf, endereco, fone, centro_sigla"
				+ " FROM professor "
				+ " WHERE matricula = " + matricula + ";"; 

		rs = conexao.consulte(strConsultar);
		
		if(rs != null){
			try{ 
				if (rs.next()){
					professor = new Professor();
					professor.setMatricula(rs.getInt(1));
					professor.setNome(rs.getString(2));
					professor.setRg(rs.getInt(3));
					professor.setCpf(rs.getInt(4));
					professor.setEndereco(rs.getString(5)+"");
					professor.setFone(rs.getString(6)+"");

					centro = cdb.getCentro(rs.getString(7));
					
					if (centro != null){
						professor.setCentro(centro);
					}
					
				}
			}catch(SQLException sqle){
				JOptionPane.showMessageDialog(null, sqle.getErrorCode(), sqle.getMessage(), 
						JOptionPane.PLAIN_MESSAGE);
			}
		}
		return professor;
	}
	

	public Professor getProfessoresPorNome(String nome){
		Professor professor = null;
		Centro centro = null;
				
		String strConsultar = "SELECT matricula, nome, rg, cpf, endereco, fone, centro_sigla"
				+ " FROM professor "
				+ " WHERE nome = " + nome + ";"; 

		rs = conexao.consulte(strConsultar);
		
		if(rs != null){
			try{ 
				if (rs.next()){
					professor = new Professor();
					professor.setMatricula(rs.getInt(1));
					professor.setNome(rs.getString(2));
					professor.setRg(rs.getInt(3));
					professor.setCpf(rs.getInt(4));
					professor.setEndereco(rs.getString(5)+"");
					professor.setFone(rs.getString(6)+"");

					centro = cdb.getCentro(rs.getString(7));
					
					if (centro != null){
						professor.setCentro(centro);
					}
					
				}
			}catch(SQLException sqle){
				JOptionPane.showMessageDialog(null, sqle.getErrorCode(), sqle.getMessage(), 
						JOptionPane.PLAIN_MESSAGE);
			}
		}
		return professor;
	}

	public List<Professor> getTodosProfessores(){

		List<Professor> professores = new ArrayList<Professor>();
		Professor professor = null;
		Centro centro = null;
				
		String strConsultar = "SELECT matricula, nome, rg, cpf, endereco, fone, centro_sigla"
				+ " FROM professor;"; 

		rs = conexao.consulte(strConsultar);
		
		if(rs != null){
			try{ 
				while (rs.next()){
					professor = new Professor();
					professor.setMatricula(rs.getInt(1));
					professor.setNome(rs.getString(2));
					professor.setRg(rs.getInt(3));
					professor.setCpf(rs.getInt(4));
					professor.setEndereco(rs.getString(5));
					professor.setFone(rs.getString(6));

					centro = cdb.getCentro(rs.getString(7));
					
					if (centro != null){
						professor.setCentro(centro);
					}
					professores.add(professor);
				}
			}catch(SQLException sqle){
				JOptionPane.showMessageDialog(null, sqle.getErrorCode(), sqle.getMessage(), 
						JOptionPane.PLAIN_MESSAGE);
			}
		}
		return professores;
	}
}
