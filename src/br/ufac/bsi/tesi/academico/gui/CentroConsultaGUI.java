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
import br.ufac.bsi.tesi.academico.logic.Centro;
import br.ufac.bsi.tesi.academico.logic.CentroLogic;


public class CentroConsultaGUI extends JFrame implements ActionListener{
	private JTable tblCentros;
	private JPanel pnlControles, pnlRotulos, pnlCampos, pnlComandos, pnlOperacoes;
	private JComboBox<String> cmbCampos;
	private JTextField fldValor;
	private JButton btnBuscar, btnSair, btnIncluir, btnEditar, btnExcluir, btnListar;

	private Conexao cnx;
	private AcademicoGUI pai;	
	private CentroCadastroGUI centroGUI;
	private CentroLogic centroLogic;
	


	public CentroConsultaGUI(AcademicoGUI pai, Conexao cnx){ 
		super("Consulta de Centro");
		setSize(800, 600); 
		setLocationRelativeTo(null);		

		this.cnx = cnx;
		this.pai = pai;

		centroGUI = new CentroCadastroGUI(this, cnx);		
		centroLogic = new CentroLogic();
		centroLogic.setConexao(cnx);
		
		tblCentros = new JTable(0,0);
		tblCentros.setToolTipText("Lista de Centros!");		
		tblCentros.setFocusable(false);
		tblCentros.addMouseListener(new MouseAdapter() {
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

		cmbCampos = new JComboBox(new String[]{"Sigla", "Nome"});
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
		
		add(new JScrollPane(tblCentros));
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
		List<Centro> centros = new ArrayList<Centro>();
		try {
			centros = centroLogic.getTodosCentros();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());	
		}
		
		if(centros != null){
			tblCentros.setModel(new CentroTableModel(centros));
		}else{
			tblCentros.setModel(null);
			 JOptionPane.showMessageDialog(null, "Sua consulta não produziu resultados!", 
					 "Consulta de Centro", JOptionPane.PLAIN_MESSAGE);	
		}
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
	}
	public void atualize() throws DataBaseNotConnectedException, EntityNotExistException{
		List<Centro> centros = new ArrayList<Centro>();
		try {
			centros = centroLogic.getTodosCentros();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());	
		}
		tblCentros.setModel(new CentroTableModel(centros));
	}
	public void buscar() throws DataBaseNotConnectedException, EntityNotExistException{

		List<Centro> centros = new ArrayList<Centro>();

		if (fldValor.getText().equals(""))
			 JOptionPane.showMessageDialog(null, "Digite uma sigla para a consulta!", 
					 "Consulta de Centro", JOptionPane.PLAIN_MESSAGE);	
		else	
			if(cmbCampos.getSelectedIndex() == 0)
				try {
					centros.add(centroLogic.getCentro(fldValor.getText()));
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());	
				}
			
		tblCentros.setModel(new CentroTableModel(centros));
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);		
	}

	public void incluir(){
		setVisible(false);
		centroGUI.incluir();
	}

	public void editar() throws DataBaseNotConnectedException, EntityNotExistException{
		String sigla = (tblCentros.getModel().getValueAt(tblCentros.getSelectedRow(), 0).toString());
		
		setVisible(false);
		try {
			centroGUI.editar(sigla);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());	
		}
	}

	public void excluir() throws DataBaseNotConnectedException, EntityNotExistException{	
		String sigla = (tblCentros.getModel().getValueAt(tblCentros.getSelectedRow(), 
				0).toString());
		
		setVisible(false);
		try {
			centroGUI.excluir(sigla);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());	
		}
	}	
	
	private void sair(){
		setVisible(false);
		pai.setVisible(true);
	}

}
