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
import br.ufac.bsi.tesi.academico.logic.Curso;
import br.ufac.bsi.tesi.academico.logic.CursoLogic;


public class CursoConsultaGUI extends JFrame implements ActionListener{
		private JTable tblCursos;
		private JPanel pnlControles, pnlRotulos, pnlCampos, pnlComandos, pnlOperacoes;
		private JComboBox<String> cmbCampos;
		private JTextField fldValor;
		private JButton btnBuscar, btnSair, btnIncluir, btnEditar, btnExcluir, btnListar;

		private Conexao cnx = Conexao.getInstacia();
		private AcademicoGUI pai;	
		private CursoCadastroGUI cursoGUI;
		private CursoLogic cursoLogic;

		public CursoConsultaGUI(AcademicoGUI pai, Conexao cnx){ // mÃ©todo construtor
			super("Consulta de Curso");
			setSize(800, 600); // chamando construtor da classe mÃ£e
			setLocationRelativeTo(null);		

			this.cnx = cnx;
			this.pai = pai;

			cursoGUI = new CursoCadastroGUI(this, cnx);		
			cursoLogic = new CursoLogic();

			
			tblCursos = new JTable(0,0);
			tblCursos.setToolTipText("Lista de Cursos!");		
			tblCursos.setFocusable(false);
			tblCursos.addMouseListener(new MouseAdapter() {
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
			
			add(new JScrollPane(tblCursos));
			add(pnlControles, BorderLayout.NORTH);
			add(pnlOperacoes, BorderLayout.SOUTH);

	   } 
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
			List<Curso> cursos = new ArrayList<Curso>();
			try {
				cursos = cursoLogic.getTodosCursos();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());	
			}
			
			if(cursos != null){
				tblCursos.setModel(new CursoTableModel(cursos));
			}else{
				tblCursos.setModel(null);
				 JOptionPane.showMessageDialog(null, "Sua consulta não produziu resultados!", 
						 "Consulta de Curso", JOptionPane.PLAIN_MESSAGE);	
			}
			btnEditar.setEnabled(false);
			btnExcluir.setEnabled(false);
		}
		
		public void atualize(){
			List<Curso> cursos = new ArrayList<Curso>();
			try {
				cursos = cursoLogic.getTodosCursos();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());	
			}
			tblCursos.setModel(new CursoTableModel(cursos));
		}
		@SuppressWarnings("unused")
		public void buscar(){

			List<Curso> cursos = new ArrayList<Curso>();

			if (fldValor.getText().equals(""))
				 JOptionPane.showMessageDialog(null, "Digite um codigo para a consulta!", 
						 "Consulta de Curso", JOptionPane.PLAIN_MESSAGE);	
			else	
				if(cmbCampos.getSelectedIndex() == 0)
					try{
						int teste = Integer.parseInt(fldValor.getText());
						cursos.add(cursoLogic.getCurso(fldValor.getText()));
					}catch(NumberFormatException e){
						 JOptionPane.showMessageDialog(null, "Entre com um codigo numerico!", 
								 "Consulta de curso", JOptionPane.PLAIN_MESSAGE);	
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());	
					}
				/*else
					if (fldValor.getText().equals(""))
						centros = centroLogic.getTodosCentros();
					else	
						if(cmbCampos.getSelectedIndex() == 1)
							centros.add(professorLogic.getProfessorPorNome(fldValor.getText()));
			*/
			if(cursos != null){
				tblCursos.setModel(new CursoTableModel(cursos));
			}else{
				tblCursos.setModel(null);
				 JOptionPane.showMessageDialog(null, "Sua consulta nÃ£o produziu resultados!", 
						 "Consulta de Curso", JOptionPane.PLAIN_MESSAGE);	
			}
			btnEditar.setEnabled(false);
			btnExcluir.setEnabled(false);		
		}

		public void incluir(){
			setVisible(false);
			cursoGUI.incluir();
		}

		public void editar(){
			String sigla = (tblCursos.getModel().getValueAt(tblCursos.getSelectedRow(),
					0).toString());
			
			setVisible(false);
			try {
				cursoGUI.editar(sigla);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());	
			}
		}

		public void excluir(){	
			String sigla = (tblCursos.getModel().getValueAt(tblCursos.getSelectedRow(), 
					0).toString());
			
			setVisible(false);
			try {
				cursoGUI.excluir(sigla);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());	
			}
		}	
		
		private void sair(){
			setVisible(false);
			pai.setVisible(true);
		}

	}

