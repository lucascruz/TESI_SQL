package br.ufac.bsi.tesi.academico.gui;

import br.ufac.bsi.tesi.academico.db.*;
import br.ufac.bsi.tesi.academico.logic.*;

import javax.swing.*; 					//importando classes do Swing
import java.awt.*; 						//importando classes do AWT
import java.awt.event.*; 				//importando classes de EVENTOS do AWT
import java.util.ArrayList;

class DisciplinaCadastroGUI extends JFrame implements ActionListener {

	private JPanel pnlControles, pnlOperacoes, pnlRotulos, pnlCampos;
	private JTextField fldNome, fldCodigo, fldCh;
	private JButton btnConfirmar, btnCancelar;

	private String[] operacoesNomes;
	private int operacao;

	private DisciplinaConsultaGUI pai;
	private Conexao cnx;

	private DisciplinaLogic disciplinaLogic = new DisciplinaLogic();

	DisciplinaCadastroGUI(DisciplinaConsultaGUI pai, Conexao cnx){ // método construtor
		super(""); 				// chamando construtor da classe mãe
		setSize(800, 600);		// definindo dimensões da janela	

		this.pai = pai;
		this.cnx = cnx;

		disciplinaLogic.setConexao(cnx);

		operacoesNomes = new String[]{"Inclusão", "Edicação", "Exclusão"};

		pnlRotulos = new JPanel(new GridLayout(3,1,5,5));
		pnlRotulos.add(new JLabel("Codigo"));
		pnlRotulos.add(new JLabel("Nome"));	
		pnlRotulos.add(new JLabel("Ch"));		

		fldNome = new JTextField();		
		fldCodigo = new JTextField();		
		fldCh = new JTextField();
		
		pnlCampos = new JPanel(new GridLayout(3,1,5,5));
		pnlCampos.add(fldNome);
		pnlCampos.add(fldCodigo);
		pnlCampos.add(fldCh);

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
		setTitle(operacoesNomes[operacao]+ " de Disciplina");
		fldNome.setText("");
		fldCodigo.setText("");
		fldCh.setText("");		

		fldNome.setEnabled(true);
		fldCodigo.setEnabled(true);
		fldCh.setEnabled(true);

		setVisible(true);	
	}

	public void editar(String matricula){
		operacao = 1;		
		setTitle(operacoesNomes[operacao]+ " de Disciplina");

		carregarCampos(matricula);
		fldNome.setEnabled(true);
		fldCodigo.setEnabled(true);
		fldCh.setEnabled(true);
		setVisible(true);	
	}

	public void excluir(String matricula){
		operacao = 2;		
		setTitle(operacoesNomes[operacao]+ " de Disciplina");

		carregarCampos(matricula);


		fldNome.setEnabled(false);
		fldCodigo.setEnabled(false);
		fldCh.setEnabled(false);

		setVisible(true);	
	}


	public void carregarCampos(String codigo){

		Disciplina disciplina = disciplinaLogic.getDisciplina(codigo);

		if (disciplina == null){
			JOptionPane.showMessageDialog(null, "Disciplina nao encontrada!", 
					"Cadastro de Disciplina", JOptionPane.PLAIN_MESSAGE);	
		}else{
			fldCodigo.setText(""+disciplina.getCodigo());
			fldNome.setText(""+disciplina.getNome());
			fldCh.setText(""+disciplina.getCh());
			
		}
	}

	

	public void confirmar(){

		Centro centro;
		boolean confirmado;

		String nome = fldNome.getText();
		String codigo = fldCodigo.getText();
		String ch = fldCh.getText();

		
		switch (operacao) {
		case 0:
			confirmado = disciplinaLogic.addDisciplina(nome, codigo, ch);
			break;
		case 1:
			confirmado = disciplinaLogic.updDisciplina(nome, codigo, ch);
			break;
		case 2:
			confirmado = disciplinaLogic.delDisciplina(nome, codigo, ch);
			break;			
		default:
			confirmado = false;
			break;
		}

		if (confirmado){
			JOptionPane.showMessageDialog(this, operacoesNomes[operacao]+ " de Disciplina realizada com sucesso!", "Acadêmico", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
			pai.setVisible(true);
			pai.buscar();
		}else{
			JOptionPane.showMessageDialog(this, "Falha ao incluir o disciplina!", "Acadêmico", JOptionPane.INFORMATION_MESSAGE);
		}

	}

}//Fim da classe DisciplinaCadastroGUI
