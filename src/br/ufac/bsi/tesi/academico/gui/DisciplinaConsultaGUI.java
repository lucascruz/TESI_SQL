package br.ufac.bsi.tesi.academico.gui;

import br.ufac.bsi.tesi.academico.db.*;
import br.ufac.bsi.tesi.academico.logic.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*; 					//importando classes do swing

public class DisciplinaConsultaGUI extends JFrame implements ActionListener{

	private JTable tblDisciplina;
	private JPanel pnlControles, pnlRotulos, pnlCampos, pnlComandos, pnlOperacoes;
	private JComboBox<String> cmbCampos;
	private JTextField fldValor;
	private JButton btnBuscar, btnSair, btnIncluir, btnEditar, btnExcluir, btnListar;

	private Conexao cnx;
	private AcademicoGUI pai;	
	private DisciplinaCadastroGUI disciplinagui;
	private DisciplinaLogic disciplinaLogic;
	

	public DisciplinaConsultaGUI(AcademicoGUI pai, Conexao cnx){ // método construtor
		super("Consulta de Disciplina");
		setSize(800, 600); // chamando construtor da classe mãe
		setLocationRelativeTo(null);		

		this.cnx = cnx;
		this.pai = pai;

		disciplinagui = new DisciplinaCadastroGUI(this, cnx);		
		disciplinaLogic = new DisciplinaLogic();
		disciplinaLogic.setConexao(cnx);
		
		tblDisciplina = new JTable(0,0);
		tblDisciplina.setToolTipText("Lista de disciplinas!");		
		tblDisciplina.setFocusable(false);
		tblDisciplina.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
			}
		});

		pnlControles = new JPanel(new BorderLayout());
		pnlRotulos = new JPanel(new GridLayout(2,1));
		pnlCampos = new JPanel(new GridLayout(2,1));
		pnlComandos = new JPanel(new GridLayout(2,1));
		pnlOperacoes = new JPanel();

		cmbCampos = new JComboBox(new String[]{"Codigo", "Nome"});
		fldValor = new JTextField();
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		
		btnSair = new JButton("Sair");
		btnSair.addActionListener(this);		
		
		btnIncluir = new JButton("Incluir");
		btnIncluir.addActionListener(this);

		btnEditar = new JButton("Editar");
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(this);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(this);
		btnExcluir.setEnabled(false);
		
		btnListar = new JButton("Listar");
		btnListar.addActionListener(this);

		pnlRotulos.add(new JLabel("Buscar por"));
		pnlRotulos.add(new JLabel("Chave de busca"));		

		pnlCampos.add(cmbCampos);
		pnlCampos.add(fldValor);
		
		pnlComandos.add(btnBuscar);
		pnlComandos.add(btnSair);
		
		pnlControles.add(pnlRotulos, BorderLayout.WEST);
		pnlControles.add(pnlCampos);
		pnlControles.add(pnlComandos, BorderLayout.EAST); 

		pnlOperacoes.add(btnIncluir);
		pnlOperacoes.add(btnEditar);		
		pnlOperacoes.add(btnExcluir);		
		
		add(new JScrollPane(tblDisciplina));
		add(pnlControles, BorderLayout.NORTH);
		add(pnlOperacoes, BorderLayout.SOUTH);

   } //Fim do método construtor

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btnBuscar){
			buscar();
		}

		if (e.getSource() == btnIncluir){
			incluir();
		}
		
		if (e.getSource() == btnEditar){
			editar();
		}

		if (e.getSource() == btnExcluir){
			excluir();
		}		
		if(e.getSource() == btnListar){
			listar();
		}
		
		if (e.getSource() == btnSair){
			sair();			
		}		
		
	}

	public void buscar(){

		List<Disciplina> disciplinaes = new ArrayList<Disciplina>();

		if (fldValor.getText().equals(""))
			disciplinaes = disciplinaLogic.lstDisciplina();
		else	
			if(cmbCampos.getSelectedIndex() == 0)
				disciplinaes.add(disciplinaLogic.getDisciplina(fldValor.getText()));
			else
				;// DEVERÁ CONSIDERAR O NOME E REALIZAR A CONSULTA COM O MÉTODO CORRESPODENTE
		
		if(disciplinaes != null){
			tblDisciplina.setModel(new DisciplinaTableModel(disciplinaes));
		}else{
			tblDisciplina.setModel(null);
			 JOptionPane.showMessageDialog(null, "Sua consulta não produziu resultados!", 
					 "Consulta de Disciplina", JOptionPane.PLAIN_MESSAGE);	
		}
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);		
	}

	public void incluir(){
		setVisible(false);
		disciplinagui.incluir();
	}

	public void editar(){
		String matricula;
		
		matricula = tblDisciplina.getModel().getValueAt(tblDisciplina.getSelectedRow(), 
				0).toString();
		
		setVisible(false);
		disciplinagui.editar(matricula);
	}

	public void excluir(){
		String matricula;
		
		matricula = tblDisciplina.getModel().getValueAt(tblDisciplina.getSelectedRow(), 
				0).toString();
		
		setVisible(false);
		disciplinagui.excluir(matricula);
	}	
	
	public void atualiza(){
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		disciplinas = disciplinaLogic.lstDisciplina();
		tblDisciplina.setModel(new DisciplinaTableModel(disciplinas));
	}
	
	private void sair(){
		setVisible(false);
		pai.setVisible(true);
	}
	
	public void listar(){
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		disciplinas = disciplinaLogic.lstDisciplina();
		
		if(disciplinas != null){
			tblDisciplina.setModel(new DisciplinaTableModel(disciplinas));
		}else{
			tblDisciplina.setModel(null);
			 JOptionPane.showMessageDialog(null, "Sua consulta não produziu resultados!", 
					 "Consulta de Disciplina", JOptionPane.PLAIN_MESSAGE);	
		}
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
	}

}//Fim da classe DisciplinaConsultaGUI



