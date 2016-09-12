package br.ufac.bsi.tesi.academico.gui;

import br.ufac.bsi.tesi.academico.db.*;
import br.ufac.bsi.tesi.academico.logic.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*; 					//importando classes do swing

public class CentroConsultaGUI extends JFrame implements ActionListener{

	private JTable tblCentros;
	private JPanel pnlControles, pnlRotulos, pnlCampos, pnlComandos, pnlOperacoes;
	private JComboBox<String> cmbCampos;
	private JTextField fldValor;
	private JButton btnBuscar, btnSair, btnIncluir, btnEditar, btnExcluir;

	private Conexao cnx;
	private AcademicoGUI pai;	
	private CentroCadastroGUI pcgui;
	private CentroLogic centroLogic;
	
//	ConsultaitorTableModel atm = new ConsultaitorTableModel();

	public CentroConsultaGUI(AcademicoGUI pai, Conexao cnx){ // método construtor
		super("Consulta de Centro");
		setSize(800, 600); // chamando construtor da classe mãe
		setLocationRelativeTo(null);		

		this.cnx = cnx;
		this.pai = pai;

		pcgui = new CentroCadastroGUI(this, cnx);		
		centroLogic = new CentroLogic();
		centroLogic.setConexao(cnx);
		
		tblCentros = new JTable(0,0);
		tblCentros.setToolTipText("Lista de centroes!");		
		tblCentros.setFocusable(false);
		tblCentros.addMouseListener(new MouseAdapter() {
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
		
		add(new JScrollPane(tblCentros));
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
		
		if (e.getSource() == btnSair){
			sair();			
		}		
		
	}

	public void buscar(){

		List<Centro> centroes = new ArrayList<Centro>();

		if (fldValor.getText().equals(""))
			centroes = centroLogic.lstCentros();
		else	
			if(cmbCampos.getSelectedIndex() == 0)
				centroes.add(centroLogic.getCentro(fldValor.getText()));
			else
				;// DEVERÁ CONSIDERAR O NOME E REALIZAR A CONSULTA COM O MÉTODO CORRESPODENTE
		
		if(centroes != null){
			tblCentros.setModel(new CentroTableModel(centroes));
		}else{
			tblCentros.setModel(null);
			 JOptionPane.showMessageDialog(null, "Sua consulta não produziu resultados!", 
					 "Consulta de Centro", JOptionPane.PLAIN_MESSAGE);	
		}
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);		
	}

	public void incluir(){
		setVisible(false);
		pcgui.incluir();
	}

	public void editar(){
		String matricula;
		
		matricula = tblCentros.getModel().getValueAt(tblCentros.getSelectedRow(), 
				0).toString();
		
		setVisible(false);
		pcgui.editar(matricula);
	}

	public void excluir(){
		String matricula;
		
		matricula = tblCentros.getModel().getValueAt(tblCentros.getSelectedRow(), 
				0).toString();
		
		setVisible(false);
		pcgui.excluir(matricula);
	}	
	
	private void sair(){
		setVisible(false);
		pai.setVisible(true);
	}

}//Fim da classe CentroConsultaGUI



