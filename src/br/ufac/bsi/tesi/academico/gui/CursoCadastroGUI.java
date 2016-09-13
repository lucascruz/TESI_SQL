package br.ufac.bsi.tesi.academico.gui;

import br.ufac.bsi.tesi.academico.db.*;
import br.ufac.bsi.tesi.academico.logic.*;

import javax.swing.*; 					//importando classes do Swing
import java.awt.*; 						//importando classes do AWT
import java.awt.event.*; 				//importando classes de EVENTOS do AWT
import java.util.ArrayList;

class CursoCadastroGUI extends JFrame implements ActionListener {

	private JPanel pnlControles, pnlOperacoes, pnlRotulos, pnlCampos;
	private JComboBox<Centro> cmbCentro;
	private JTextField fldCodigo, fldNome;
	private JButton btnConfirmar, btnCancelar;

	private String[] operacoesNomes;
	private int operacao;

	private CursoConsultaGUI pai;
	private Conexao cnx;

	private CursoLogic cursoLogic = new CursoLogic();

	CursoCadastroGUI(CursoConsultaGUI pai, Conexao cnx){ // método construtor
		super(""); 				// chamando construtor da classe mãe
		setSize(800, 600);		// definindo dimensões da janela	

		this.pai = pai;
		this.cnx = cnx;

		cursoLogic.setConexao(cnx);

		operacoesNomes = new String[]{"Inclusão", "Edicação", "Exclusão"};

		pnlRotulos = new JPanel(new GridLayout(7,1,5,5));
		pnlRotulos.add(new JLabel("Cod. do Curso"));
		pnlRotulos.add(new JLabel("Nome"));
		
		fldCodigo = new JTextField();
		fldNome = new JTextField();;		
				
		
		pnlCampos = new JPanel(new GridLayout(7,1,5,5));
		pnlCampos.add(fldCodigo);
		pnlCampos.add(fldNome);
		
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
		setTitle(operacoesNomes[operacao]+ " de Curso");
		fldCodigo.setText("");
		fldNome.setText("");		
		fldCodigo.setEnabled(true);
		fldNome.setEnabled(true);
		setVisible(true);	
	}

	public void editar(String matricula){
		operacao = 1;		
		setTitle(operacoesNomes[operacao]+ " de Curso");

		carregarCampos(matricula);

		fldCodigo.setEnabled(false);
		fldNome.setEnabled(true);
		setVisible(true);	
	}

	public void excluir(String matricula){
		operacao = 2;		
		setTitle(operacoesNomes[operacao]+ " de Curso");

		carregarCampos(matricula);

		fldCodigo.setEnabled(false);
		fldNome.setEnabled(false);		
		cmbCentro.setEnabled(false);
		setVisible(true);	
	}

	public void carregarCampos(String codigo){

		Curso curso = cursoLogic.getCurso(codigo);

		if (curso == null){
			JOptionPane.showMessageDialog(null, "Curso não foi encontrado!", 
					"Cadastro de Curso", JOptionPane.PLAIN_MESSAGE);	
		}else{
			fldCodigo.setText(curso.getCodigo());
			fldNome.setText(curso.getNome());
		}
	}


	public void confirmar(){

		boolean confirmado;
		
		String codigo = fldCodigo.getText();
		String nome = fldNome.getText();
		
		
		switch (operacao) {
		case 0:
			confirmado = cursoLogic.addCurso (codigo, nome);
			break;
		case 1:
			confirmado = cursoLogic.updCurso  (codigo, nome);
			break;
		case 2:
			confirmado = cursoLogic.delCurso  (codigo, nome);
			break;			
		default:
			confirmado = false;
			break;
		}

		if (confirmado){
			JOptionPane.showMessageDialog(this, operacoesNomes[operacao]+ " de Curso realizada com sucesso!", "Acadêmico", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
			pai.setVisible(true);
			pai.buscar();
		}else{
			JOptionPane.showMessageDialog(this, "Falha ao incluir o curso!", "Acadêmico", JOptionPane.INFORMATION_MESSAGE);
		}

	}

}//Fim da classe CursoCadastroGUI
