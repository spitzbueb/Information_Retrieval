package ch.phischa;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI {
	
	private JFrame mainFrame;
	private JTextField suche, pfadFeld;
	private JButton sucheButton, indexButton;
	private JLabel dauer, dauerTitel;
	
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
		JPanel centerPanel = new JPanel();
		JPanel southPanel = new JPanel();
		northPanel.add(sucheLabel);
		northPanel.add(suche);
		northPanel.add(sucheButton);
		centerPanel.add(dauerTitel);
		centerPanel.add(dauer);
		southPanel.add(indexLabel);
		southPanel.add(pfadFeld);
		southPanel.add(indexButton);
		
		mainFrame.add(northPanel,BorderLayout.NORTH);
		mainFrame.add(centerPanel,BorderLayout.CENTER);
		mainFrame.add(southPanel,BorderLayout.SOUTH);
				
		mainFrame.setJMenuBar(menubar);
		
		mainFrame.pack();
		mainFrame.setSize(700,800);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		
		indexButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Index indexer = new Index(pfadFeld.getText().toString());
				long dauerZeit = indexer.getTime();
				dauer.setText(String.valueOf(dauerZeit) + " Millisekunden");
			}
			
		});
	}
	
	private JMenuBar createMenuBar()
	{
		JMenuBar menubar = new JMenuBar();
		JMenu datei, einstellungen;
		JMenuItem schliessen,pfadSetting;
		
		datei = new JMenu("Datei");
		einstellungen = new JMenu("Einstellungen");
		
		schliessen = new JMenuItem("Schliessen");
		pfadSetting = new JMenuItem("Pfad setzen");
		
		datei.add(schliessen);
		einstellungen.add(pfadSetting);
		
		menubar.add(datei);
		menubar.add(einstellungen);
		
		return menubar;
	}
}
