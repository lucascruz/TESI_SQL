package br.ufac.bsi.tesi.academico.gui;

import br.ufac.bsi.tesi.academico.db.*;
import br.ufac.bsi.tesi.academico.logic.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*; 					//importando classes do swing

public class ProfessorConsultaGUI extends JFrame implements ActionListener{

	private JTable tblProfessores;
	private JPanel pnlControles, pnlRotulos, pnlCampos, pnlComandos, pnlOperacoes;
	private JComboBox<String> cmbCampos;
	private JTextField fldValor;
	private JButton btnBuscar, btnSair, btnIncluir, btnEditar, btnExcluir, btnListar;

	private Conexao cnx;
	private AcademicoGUI pai;	
	private ProfessorCadastroGUI pcgui;
	private ProfessorLogic professorLogic;
	
//	ConsultaitorTableModel atm = new ConsultaitorTableModel();

	public ProfessorConsultaGUI(AcademicoGUI pai, Conexao cnx){ // método construtor
		super("Consulta de Professor");
		setSize(800, 600); // chamando construtor da classe mãe
		setLocationRelativeTo(null);		

		this.cnx = cnx;
		this.pai = pai;

		pcgui = new ProfessorCadastroGUI(this, cnx);		
		professorLogic = new ProfessorLogic();
		professorLogic.setConexao(cnx);
		
		tblProfessores = new JTable(0,0);
		tblProfessores.setToolTipText("Lista de professores!");		
		tblProfessores.setFocusable(false);
		tblProfessores.addMouseListener(new MouseAdapter() {
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

		cmbCampos = new JComboBox(new String[]{"Matrícula", "Nome"});
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
		pnlOperacoes.add(btnListar);
		
		add(new JScrollPane(tblProfessores));
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
		
		if (e.getSource() == btnListar){
			listar();			
		}	
		
		if (e.getSource() == btnSair){
			sair();			
		}		
		
	}

	public void buscar(){

		List<Professor> professores = new ArrayList<Professor>();

		if (fldValor.getText().equals(""))
			professores = professorLogic.getTodosProfessores();
		else	
			if(cmbCampos.getSelectedIndex() == 0)
				professores.add(professorLogic.getProfessor(Integer.parseInt(fldValor.getText())));
			else
				;// DEVERÁ CONSIDERAR O NOME E REALIZAR A CONSULTA COM O MÉTODO CORRESPODENTE
		
		if(professores != null){
			tblProfessores.setModel(new ProfessorTableModel(professores));
		}else{
			tblProfessores.setModel(null);
			 JOptionPane.showMessageDialog(null, "Sua consulta não produziu resultados!", 
					 "Consulta de Professor", JOptionPane.PLAIN_MESSAGE);	
		}
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);		
	}

	public void incluir(){
		setVisible(false);
		pcgui.incluir();
	}

	public void editar(){
		int matricula;
		
		matricula = Integer.parseInt(tblProfessores.getModel().getValueAt(tblProfessores.getSelectedRow(), 
				0).toString());
		
		setVisible(false);
		pcgui.editar(matricula);
	}

	public void excluir(){
		int matricula;
		
		matricula = Integer.parseInt(tblProfessores.getModel().getValueAt(tblProfessores.getSelectedRow(), 
				0).toString());
		
		setVisible(false);
		pcgui.excluir(matricula);;
	}	
	
	private void sair(){
		setVisible(false);
		pai.setVisible(true);
	}
	
	public void listar(){
		List<Professor> professores = new ArrayList<Professor>();
		professores = professorLogic.getTodosProfessores();

		if(professores != null){
			tblProfessores.setModel(new ProfessorTableModel(professores));
		}else{
			tblProfessores.setModel(null);
			JOptionPane.showMessageDialog(null, "Sua consulta não produziu resultados!", 
					"Consulta de Professor", JOptionPane.PLAIN_MESSAGE);	
		}
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
	}
	public void atualize(){
		List<Professor> professores = new ArrayList<Professor>();
		professores = professorLogic.getTodosProfessores();
		tblProfessores.setModel(new ProfessorTableModel(professores));
	}
	

}//Fim da classe ProfessorConsultaGUI



