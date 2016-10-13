package br.ufac.bsi.tesi.academico.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.ufac.bsi.tesi.academico.db.Conexao;
import br.ufac.bsi.tesi.academico.exception.EntityAlreadyExistException;
import br.ufac.bsi.tesi.academico.exception.EntityNotExistException;
import br.ufac.bsi.tesi.academico.exception.InvalidFieldException;
import br.ufac.bsi.tesi.academico.exception.InvalidLenghtFieldException;
import br.ufac.bsi.tesi.academico.exception.InvalidNameException;
import br.ufac.bsi.tesi.academico.exception.NumberErroException;
import br.ufac.bsi.tesi.academico.exception.ParentHasChildrenException;
import br.ufac.bsi.tesi.academico.logic.Aluno;
import br.ufac.bsi.tesi.academico.logic.AlunoLogic;
import br.ufac.bsi.tesi.academico.logic.Curso;
import br.ufac.bsi.tesi.academico.logic.CursoLogic;


class AlunoCadastroGUI extends JFrame implements ActionListener {

	private JPanel pnlControles, pnlOperacoes, pnlRotulos, pnlCampos;
	private JComboBox<Curso> cmbCurso;
	private JTextField fldMatricula, fldNome, fldFone, fldCep, fldEndereco, fldSexo;
	private JButton btnConfirmar, btnCancelar;
	private String[] operacoesNomes;
	private int operacao;
	private AlunoConsultaGUI pai;
	private Conexao cnx;

	private AlunoLogic alunoLogic = new AlunoLogic();
	private CursoLogic cursoLogic = new CursoLogic();	

	public AlunoCadastroGUI(AlunoConsultaGUI pai, Conexao cnx) throws SQLException{ 
		super(""); 				
		setSize(600, 350);	

		this.pai = pai;
		this.cnx = cnx;
		
		alunoLogic.setConexao(cnx);
		cursoLogic.setConexao(cnx);
	

		operacoesNomes = new String[]{"Inclusao", "Edicao", "Exclusao"};

		pnlRotulos = new JPanel(new GridLayout(7,1,5,5));
		pnlRotulos.add(new JLabel("Matricula"));
		pnlRotulos.add(new JLabel("Nome"));
		pnlRotulos.add(new JLabel("Fone"));		
		pnlRotulos.add(new JLabel("Endereço"));
		pnlRotulos.add(new JLabel("Cep"));		
		pnlRotulos.add(new JLabel("Sexo"));		
		pnlRotulos.add(new JLabel("Curso"));		

		fldMatricula = new JTextField();
		fldNome = new JTextField();		
		fldCep = new JTextField();		
		fldSexo = new JTextField();
		fldEndereco = new JTextField();		
		fldFone = new JTextField();		

		cmbCurso = new JComboBox(cursoLogic.getTodosCursos().toArray());
		
		pnlCampos = new JPanel(new GridLayout(7,1,5,5));
		pnlCampos.add(fldMatricula);
		pnlCampos.add(fldNome);
		pnlCampos.add(fldFone);
		pnlCampos.add(fldEndereco);
		pnlCampos.add(fldCep);
		pnlCampos.add(fldSexo);		
		pnlCampos.add(cmbCurso);		

		pnlControles = new JPanel(new BorderLayout(5,5));
		pnlControles.add(pnlRotulos, BorderLayout.WEST);
		pnlControles.add(pnlCampos);

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setMnemonic('f');		
		btnConfirmar.addActionListener(this);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setMnemonic('C');		
		btnCancelar.addActionListener(this);

		pnlOperacoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlOperacoes.add(btnConfirmar);
		pnlOperacoes.add(btnCancelar);

		add(pnlControles);
		add(pnlOperacoes, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);

	} 

	@Override
	public void actionPerformed(ActionEvent e){

		if (e.getSource() == btnConfirmar){
			try {
				confirmar();
			} catch (InvalidNameException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	

		if (e.getSource() == btnCancelar){
			setVisible(false);
			pai.setVisible(true);
		}	

	}

	public void incluir(){
		operacao = 0;		
		setTitle(operacoesNomes[operacao]+ " de Aluno");
		fldMatricula.setText("");
		fldNome.setText("");
		fldFone.setText("");
		fldCep.setText("");		
		fldEndereco.setText("");
		fldSexo.setText("");		
		cmbCurso.setSelectedIndex(0);		
		fldMatricula.setEnabled(true);
		fldNome.setEnabled(true);
		fldFone.setEnabled(true);
		fldCep.setEnabled(true);
		fldEndereco.setEnabled(true);
		fldFone.setEnabled(true);
		cmbCurso.setEnabled(true);
		setVisible(true);	
	}

	public void editar(String matricula) throws SQLException{
		operacao = 1;		
		setTitle(operacoesNomes[operacao]+ " de Aluno");

		carregarCampos(matricula);

		fldMatricula.setEnabled(false);
		fldNome.setEnabled(true);
		fldFone.setEnabled(true);
		fldCep.setEnabled(true);
		fldEndereco.setEnabled(true);
		fldSexo.setEnabled(true);		
		cmbCurso.setEnabled(true);
		setVisible(true);	
	}

	public void excluir(String matricula) throws SQLException{
		operacao = 2;		
		setTitle(operacoesNomes[operacao]+ " de Aluno");

		carregarCampos(matricula);

		fldMatricula.setEnabled(false);
		fldNome.setEnabled(false);
		fldFone.setEnabled(false);
		fldCep.setEnabled(false);
		fldEndereco.setEnabled(false);
		fldSexo.setEnabled(false);		
		cmbCurso.setEnabled(false);
		setVisible(true);	
	}

	public void carregarCampos(String matricula) throws SQLException{

		Aluno aluno = alunoLogic.getAluno(matricula);
		Curso curso;
		
		if (aluno == null){
			JOptionPane.showMessageDialog(null, "Aluno não encontrado!", 
					"Cadastro de Aluno", JOptionPane.PLAIN_MESSAGE);	
		}else{
			fldMatricula.setText(""+aluno.getMatricula());
			fldNome.setText(""+aluno.getNome());
			fldFone.setText(""+aluno.getFone());
			fldCep.setText(""+aluno.getCep());
			fldEndereco.setText(""+aluno.getEndereco());
			fldSexo.setText(""+aluno.getSexo());

			for (int i=0; i < cmbCurso.getModel().getSize(); i++){
				curso = cmbCurso.getModel().getElementAt(i);
				if (curso.getCodigo().equals(aluno.getCurso().getCodigo()))
					cmbCurso.setSelectedItem(curso);
			}
		}

	}


	public void confirmar() throws InvalidNameException{

		Curso curso;
		boolean confirmado= true;
		
		String matricula = fldMatricula.getText();
		String nome = fldNome.getText();
		String fone = fldFone.getText();
		String cep = fldCep.getText();
		String endereco = fldEndereco.getText();
		String sexo = fldSexo.getText();

		curso = (Curso)cmbCurso.getSelectedItem();
		String cursoNome = curso.getCodigo();
		
		switch (operacao) {
		case 0:
				try{
					confirmado = alunoLogic.addAluno(matricula, nome, fone, cep, endereco, sexo, cursoNome);
				}catch(InvalidFieldException e){
					JOptionPane.showMessageDialog(null, e, 
							"Campos Vazio", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				}catch(InvalidLenghtFieldException eoo){
					JOptionPane.showMessageDialog(null, eoo, 
							"Tamanho maximo atingido", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				}catch(NumberErroException number){
					JOptionPane.showMessageDialog(null, number, 
							"Erro De Caracter", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				}catch(EntityAlreadyExistException exst){
					JOptionPane.showMessageDialog(null, exst, 
							"\nAluno Já Existe", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				} catch (ParentHasChildrenException parente) {
					JOptionPane.showMessageDialog(null, parente, 
							"ERRO CHAVE PRIMARIA", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				} catch (InvalidNameException nomee) {
					JOptionPane.showMessageDialog(null, nomee, 
							"ERRO NOME VAZIO", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				} catch (SQLException sqle) {
					JOptionPane.showMessageDialog(null, sqle, 
							"Erro de SQL", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				}
			break;
		case 1:
			try{
				confirmado = alunoLogic.updAluno(matricula, nome, fone, cep, endereco, sexo, cursoNome);
			}catch(InvalidFieldException e){
				JOptionPane.showMessageDialog(null, e, 
						"Campos Vazio", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			}catch(InvalidLenghtFieldException eoo){
				JOptionPane.showMessageDialog(null, eoo, 
						"Tamanho maximo atingido", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			}catch(NumberErroException number){
				JOptionPane.showMessageDialog(null, number, 
						"Erro De Caracter", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			}catch(EntityNotExistException jamais){
				JOptionPane.showMessageDialog(null, jamais, 
						"ALUNO NÃO EXISTE", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			} catch (ParentHasChildrenException parente) {
				JOptionPane.showMessageDialog(null, parente, 
						"ERRO CHAVE PRIMARIA", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			} catch (InvalidNameException nomee) {
				JOptionPane.showMessageDialog(null, nomee, 
						"ERRO NOME VAZIO", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, sqle, 
						"Erro de SQL", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			}
			break;
		case 2:
			try{
				confirmado = alunoLogic.delAluno(matricula, nome, fone, cep, endereco, sexo, cursoNome);
			}catch(EntityNotExistException jamais){
				JOptionPane.showMessageDialog(null, jamais, 
						"ALUNO NÃO EXISTE", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			} catch (ParentHasChildrenException parente) {
				JOptionPane.showMessageDialog(null, parente, 
						"ERRO CHAVE PRIMARIA", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			} catch (InvalidNameException nomee) {
				JOptionPane.showMessageDialog(null, nomee, 
						"ERRO NOME VAZIO", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, sqle, 
						"Erro de SQL", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			}
			break;			
		default:
			confirmado = false;
			break;
		}

		if (confirmado){
			JOptionPane.showMessageDialog(this, operacoesNomes[operacao]+ 
				" de Aluno realizada com sucesso!", "Academico", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
			pai.setVisible(true);
			pai.atualize();
		}else{
			JOptionPane.showMessageDialog(this, operacoesNomes[operacao] +
			" de Aluno falhou" , "Academico", JOptionPane.INFORMATION_MESSAGE);
			confirmado = true;
		}

	}

}
