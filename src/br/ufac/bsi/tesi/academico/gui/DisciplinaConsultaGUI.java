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
import br.ufac.bsi.tesi.academico.exception.DataBaseNotConnectedException;
import br.ufac.bsi.tesi.academico.exception.EntityNotExistException;
import br.ufac.bsi.tesi.academico.logic.Disciplina;
import br.ufac.bsi.tesi.academico.logic.DisciplinaLogic;


@SuppressWarnings("serial")
public class DisciplinaConsultaGUI extends JFrame implements ActionListener{
	private JTable tblDisciplina;
	private JPanel pnlControles, pnlRotulos, pnlCampos, pnlComandos, pnlOperacoes;
	private JComboBox<String> cmbCampos;
	private JTextField fldValor;
	private JButton btnBuscar, btnSair, btnIncluir, btnEditar, btnExcluir, btnListar;

	private Conexao cnx;
	private AcademicoGUI pai;	
	private DisciplinaCadastroGUI disciplinaGUI;
	private DisciplinaLogic disciplinaLogic;

	public DisciplinaConsultaGUI(AcademicoGUI pai, Conexao cnx){ 
		super("Consulta de Disciplina");
		setSize(800, 600); 
		setLocationRelativeTo(null);		

		this.cnx = cnx;
		this.pai = pai;

		disciplinaGUI = new DisciplinaCadastroGUI(this, cnx);		
		disciplinaLogic = new DisciplinaLogic();
		disciplinaLogic.setConexao(cnx);
		
		tblDisciplina = new JTable(0,0);
		tblDisciplina.setToolTipText("Lista de Disciplinas!");		
		tblDisciplina.setFocusable(false);
		tblDisciplina.addMouseListener(new MouseAdapter() {
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
		pnlOperacoes.add(btnListar);
		
		add(new JScrollPane(tblDisciplina));
		add(pnlControles, BorderLayout.NORTH);
		add(pnlOperacoes, BorderLayout.SOUTH);

   } 
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btnBuscar){
			try {
				buscar();
			} catch (DataBaseNotConnectedException | EntityNotExistException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if (e.getSource() == btnIncluir){
			incluir();
		}
		
		if (e.getSource() == btnEditar){
			try {
				editar();
			} catch (DataBaseNotConnectedException | EntityNotExistException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if (e.getSource() == btnExcluir){
			try {
				excluir();
			} catch (DataBaseNotConnectedException | EntityNotExistException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
		
		if (e.getSource() == btnSair){
			sair();			
		}
		if(e.getSource() == btnListar){
			try {
				Listar();
			} catch (DataBaseNotConnectedException | EntityNotExistException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	public void Listar() throws DataBaseNotConnectedException, EntityNotExistException{
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		try {
			disciplinas = disciplinaLogic.getTodasDisciplinas();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());	
		}
		
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
	public void atualize() throws DataBaseNotConnectedException, EntityNotExistException{
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		try {
			disciplinas = disciplinaLogic.getTodasDisciplinas();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());	
		}
		tblDisciplina.setModel(new DisciplinaTableModel(disciplinas));
	}
	public void buscar() throws DataBaseNotConnectedException, EntityNotExistException{

		List<Disciplina> disciplinas = new ArrayList<Disciplina>();

		if (fldValor.getText().equals(""))
			JOptionPane.showMessageDialog(null, "Você não digitou uma chave para a busca", 
					 "Consulta de disciplina FALHA", JOptionPane.PLAIN_MESSAGE);	
		else	
			if(cmbCampos.getSelectedIndex() == 0)
				try{
					int teste = Integer.parseInt(fldValor.getText());
					disciplinas.add(disciplinaLogic.getDisciplina(fldValor.getText()));
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(null, "Digite um Codigo numerico", 
							 "Consulta de disciplina FALHA", JOptionPane.PLAIN_MESSAGE);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());	
				}
		tblDisciplina.setModel(new DisciplinaTableModel(disciplinas));
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);		
	}

	public void incluir(){
		setVisible(false);
		disciplinaGUI.incluir();
	}

	public void editar() throws DataBaseNotConnectedException, EntityNotExistException{
		String codigo = (tblDisciplina.getModel().getValueAt(tblDisciplina.getSelectedRow(), 0).toString());
		
		setVisible(false);
		try {
			disciplinaGUI.editar(codigo);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());	
		}
	}

	public void excluir() throws DataBaseNotConnectedException, EntityNotExistException{	
		String codigo = (tblDisciplina.getModel().getValueAt(tblDisciplina.getSelectedRow(), 
				0).toString());
		
		setVisible(false);
		try {
			disciplinaGUI.excluir(codigo);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());	
		}
	}	
	
	private void sair(){
		setVisible(false);
		pai.setVisible(true);
	}

}

