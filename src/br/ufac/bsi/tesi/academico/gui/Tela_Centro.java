package br.ufac.bsi.tesi.academico.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JTable;

public class Tela_Centro extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela_Centro frame = new Tela_Centro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Tela_Centro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel_controles = new JPanel();
		panel_controles.setToolTipText("Painel_Controle");
		contentPane.add(panel_controles, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel_controles.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		panel.add(menuBar, BorderLayout.WEST);
		
		JMenu mnCentro = new JMenu("Centro");
		menuBar.add(mnCentro);
		
		JMenuItem menuItem = new JMenuItem("Editar Centro");
		mnCentro.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("Consulta Centro");
		mnCentro.add(menuItem_1);
		
		JMenuItem menuItem_2 = new JMenuItem("Adicionar Centro");
		mnCentro.add(menuItem_2);
		
		JMenuItem menuItem_3 = new JMenuItem("Remover Centro");
		mnCentro.add(menuItem_3);
		
		JMenu mnNewMenu = new JMenu("Professor");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Editar Professor");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Consultar Professor");
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Adicionar Professor");
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Remover Professor");
		mnNewMenu.add(mntmNewMenuItem_3);
		
		JMenu mnNewMenu_1 = new JMenu("Aluno");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Editar Aluno");
		mnNewMenu_1.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Consultar Aluno");
		mnNewMenu_1.add(mntmNewMenuItem_5);
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Adicionar Aluno");
		mnNewMenu_1.add(mntmNewMenuItem_6);
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Remover Aluno");
		mnNewMenu_1.add(mntmNewMenuItem_7);
		
		JLabel lblNewLabel = new JLabel("GERENCIADOR ACADEMICO");
		contentPane.add(lblNewLabel, BorderLayout.SOUTH);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Kleptocracy", Font.PLAIN, 29));
		
		JPanel panel_resultado = new JPanel();
		contentPane.add(panel_resultado, BorderLayout.CENTER);
		
		table = new JTable();
		panel_resultado.add(table);
	}

}
