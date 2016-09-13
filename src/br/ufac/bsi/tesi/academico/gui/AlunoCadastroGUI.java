package br.ufac.bsi.tesi.academico.gui;

import br.ufac.bsi.tesi.academico.db.*;
import br.ufac.bsi.tesi.academico.logic.*;

import javax.swing.*; 					//importando classes do Swing
import java.awt.*; 						//importando classes do AWT
import java.awt.event.*; 				//importando classes de EVENTOS do AWT
import java.util.ArrayList;

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

	AlunoCadastroGUI(AlunoConsultaGUI pai, Conexao cnx){ // método construtor
		super(""); 				// chamando construtor da classe mãe
		setSize(800, 600);		// definindo dimensões da janela	

		this.pai = pai;
		this.cnx = cnx;

		alunoLogic.setConexao(cnx);
		cursoLogic.setConexao(cnx);		

		operacoesNomes = new String[]{"Inclusão", "Edicação", "Exclusão"};

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

	} //Fim do método construtor

	public void actionPerformed(ActionEvent e){

		if (e.getSource() == btnConfirmar){
			confirmar();
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

	public void editar(String matricula){
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

	public void excluir(String matricula){
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

	public void carregarCampos(String matricula){

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


	public void confirmar(){

		Curso curso;
		boolean confirmado;
		
		String matricula = fldMatricula.getText();
		String nome = fldNome.getText();
		String fone = fldFone.getText();
		String cep = fldCep.getText();
		String endereco = fldEndereco.getText();
		String sexo = fldSexo.getText();

		curso = (Curso)cmbCurso.getSelectedItem();
		String cursoNome = curso.getNome();
		
		switch (operacao) {
		case 0:
			confirmado = alunoLogic.addAluno(matricula, nome, fone, cep, endereco, sexo, cursoNome);
			break;
		case 1:
			confirmado = alunoLogic.updAluno(matricula, nome, fone, cep, endereco, sexo, cursoNome);
			break;
		case 2:
			confirmado = alunoLogic.delAluno(matricula, nome, fone, cep, endereco, sexo, cursoNome);
			break;			
		default:
			confirmado = false;
			break;
		}

		if (confirmado){
			JOptionPane.showMessageDialog(this, operacoesNomes[operacao]+ " de Aluno realizada com sucesso!", "Acadêmico", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
			pai.setVisible(true);
			pai.buscar();
		}else{
			JOptionPane.showMessageDialog(this, "Falha ao incluir o aluno!", "Acadêmico", JOptionPane.INFORMATION_MESSAGE);
		}

	}

}//Fim da classe AlunoCadastroGUI
