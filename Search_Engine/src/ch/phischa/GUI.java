package ch.phischa;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.lucene.document.Document;

public class GUI {
	
	private JFrame mainFrame;
	private JTextField suche, pfadFeld;
	private JButton sucheButton, indexButton;
	private JLabel dauer, dauerTitel, ergebnis;
	private JScrollPane scroll;
	private ButtonGroup bgroup;
	public GUI()
	{
		createFrame();
	}
	
	private void createFrame()
	{
		mainFrame = new JFrame("Lucene Search Engine");
		JMenuBar menubar = createMenuBar();
		
		JLabel sucheLabel = new JLabel("Suche:");
		JLabel indexLabel = new JLabel("Pfad:");
		suche = new JTextField(20);
		sucheButton = new JButton("Suche");
		dauer = new JLabel();
		dauerTitel = new JLabel("Dauer:");
		pfadFeld = new JTextField(20);
		indexButton = new JButton("Index");

		JPanel northPanel = new JPanel();
		final JPanel ostPanel = new JPanel();
		JPanel southPanel = new JPanel();
		final JPanel centerPanel = new JPanel();
		northPanel.add(sucheLabel);
		northPanel.add(suche);
		northPanel.add(sucheButton);
		ostPanel.add(dauerTitel);
		ostPanel.add(dauer);
		southPanel.add(indexLabel);
		southPanel.add(pfadFeld);
		southPanel.add(indexButton);
		
		mainFrame.add(northPanel,BorderLayout.NORTH);
		mainFrame.add(ostPanel,BorderLayout.EAST);
		mainFrame.add(southPanel,BorderLayout.SOUTH);
				
		mainFrame.setJMenuBar(menubar);
		
		mainFrame.pack();
		mainFrame.setSize(700,800);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		
		sucheButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String analyzer = bgroup.getSelection().getActionCommand();				
				Searcher searcher = new Searcher(pfadFeld.getText().toString(),suche.getText().toString(),analyzer);
				dauer.setText(String.valueOf(searcher.getDauer()) + " Millisekunden");
				ArrayList<Document> results = searcher.getResult();
				String cols[] = {"Ergebnisse"};
				DefaultTableModel tableModel = new DefaultTableModel(cols, 0);
				JTable table = new JTable(tableModel);	
				tableModel.getDataVector().removeAllElements();
				int i = 0;
				
				if(results.isEmpty())
				{
					JOptionPane.showMessageDialog(new JPanel(), "Keine Resultate","Error",JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					for (Document doc:results)
					{
						tableModel.addRow((Vector) null);
						tableModel.setValueAt(doc.get("fullpath"), i, 0);
						i++;
					}
				}
				scroll = new JScrollPane(table);	
				centerPanel.removeAll();
				centerPanel.add(scroll);
				mainFrame.add(centerPanel);
				mainFrame.repaint();
			}
			
		});
		
		indexButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String analyzer = bgroup.getSelection().getActionCommand();
				Index indexer = new Index(pfadFeld.getText().toString(),analyzer);
				long dauerZeit = indexer.getTime();
				dauer.setText(String.valueOf(dauerZeit) + " Millisekunden");
			}
			
		});
	}
	
	private JMenuBar createMenuBar()
	{
		JMenuBar menubar = new JMenuBar();
		JMenu datei, einstellungen;
		JMenuItem schliessen;
		
		JRadioButtonMenuItem saButton = new JRadioButtonMenuItem("Standard Analyzer", true);
		JRadioButtonMenuItem wsButton = new JRadioButtonMenuItem("Whitespace Analyzer", false);
		JRadioButtonMenuItem stButton = new JRadioButtonMenuItem("Stop-Analyzer",false);
		JRadioButtonMenuItem smButton = new JRadioButtonMenuItem("Simple Analyzer", false);
		
		saButton.setActionCommand("Standard Analyzer");
		wsButton.setActionCommand("Whitespace Analyzer");
		stButton.setActionCommand("Stop Analyzer");
		smButton.setActionCommand("Simple Analyzer");
		
		bgroup = new ButtonGroup();
		bgroup.add(saButton);
		bgroup.add(smButton);
		bgroup.add(wsButton);
		bgroup.add(stButton);
		
		datei = new JMenu("Datei");
		einstellungen = new JMenu("Einstellungen");
		
		schliessen = new JMenuItem("Schliessen");
		
		datei.add(schliessen);
		einstellungen.add(saButton);
		einstellungen.add(smButton);
		einstellungen.add(wsButton);
		einstellungen.add(stButton);
		
		menubar.add(datei);
		menubar.add(einstellungen);
		
		return menubar;
	}
}
