package br.ufac.bsi.tesi.academico.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import br.ufac.bsi.tesi.academico.db.Conexao;
import br.ufac.bsi.tesi.academico.logic.Professor;
import br.ufac.bsi.tesi.academico.logic.ProfessorLogic; 				


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

	public ProfessorConsultaGUI(AcademicoGUI pai, Conexao cnx){ // mÃ©todo construtor
		super("Consulta de Professor");
		setSize(800, 600); // chamando construtor da classe mÃ£e
		setLocationRelativeTo(null);		

		this.cnx = cnx;
		this.pai = pai;

		try {
			pcgui = new ProfessorCadastroGUI(this, cnx);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());	
		}		
		professorLogic = new ProfessorLogic();
		professorLogic.setConexao(cnx);

		tblProfessores = new JTable(0,0);
		tblProfessores.setToolTipText("Lista de professores!");		
		tblProfessores.setFocusable(false);
		tblProfessores.addMouseListener(new MouseAdapter() {
			@Override
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

		cmbCampos = new JComboBox(new String[]{"Matricula", "Nome"});
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

	} //Fim do mÃ©todo construtor

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

		if (e.getSource() == btnSair){
			sair();			
		}
		if(e.getSource() == btnListar){
			Listar();
		}

	}

	public void Listar(){
		List<Professor> professores = new ArrayList<Professor>();
		try {
			professores = professorLogic.getTodosProfessores();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());	
		}

		if(professores != null){
			tblProfessores.setModel(new ProfessorTableModel(professores));
		}else{
			tblProfessores.setModel(null);
			JOptionPane.showMessageDialog(null, "Sua consulta nÃ£o produziu resultados!", 
					"Consulta de Professor", JOptionPane.PLAIN_MESSAGE);	
		}
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
	}
	public void atualize(){
		List<Professor> professores = new ArrayList<Professor>();
		try {
			professores = professorLogic.getTodosProfessores();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());	
		}
		tblProfessores.setModel(new ProfessorTableModel(professores));
	}
	
	public void buscar(){

		List<Professor> professores = new ArrayList<Professor>();

		if (fldValor.getText().equals(""))
			JOptionPane.showMessageDialog(null, "Você não digitou uma chave para a busca", 
					"Consulta de Professor FALHA", JOptionPane.PLAIN_MESSAGE);	
		else
			if(cmbCampos.getSelectedIndex() == 0)
				try{
					professores.add(professorLogic.getProfessor(Integer.parseInt(fldValor.getText())));
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(null, "A matricula apenas pode conter Numeros!!!!", 
							"Consulta de Professor FALHA", JOptionPane.PLAIN_MESSAGE);	
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());	
				}
			else
				if(cmbCampos.getSelectedIndex() == 1)
					try {
						professores.add(professorLogic.getProfessorPorNome(fldValor.getText()));
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());	
					}

		if(professores != null){
			tblProfessores.setModel(new ProfessorTableModel(professores));
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
		try {
			pcgui.editar(matricula);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());	
		}
	}

	public void excluir(){
		int matricula;

		matricula = Integer.parseInt(tblProfessores.getModel().getValueAt(tblProfessores.getSelectedRow(), 
				0).toString());

		setVisible(false);
		try {
			pcgui.excluir(matricula);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());	
		}
	}	

	private void sair(){
		setVisible(false);
		pai.setVisible(true);
	}

}//Fim da classe ProfessorConsultaGUI



