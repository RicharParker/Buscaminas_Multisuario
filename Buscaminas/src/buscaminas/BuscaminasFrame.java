/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

public class BuscaminasFrame extends JFrame implements ActionListener {

	private BuscaminasJuego juego;
	private BuscaminasPanel panel;
	
	private JMenuBar menuBar;
	private JMenuItem newMenuItem;
	private JMenuItem loadMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem quitMenuItem;

	private JFileChooser fileBrowser;
	
	private static final String NUEVO_ACCION = "nuevo";
	private static final String GUARDAR_ACCION = "guardar";
	private static final String CARGAR_ACCION = "cargar";
	private static final String SALIR_ACCION = "salir";
	

	public BuscaminasFrame (int numero_casillas, double probabilidadMina, long depuracionComp) {
	
		setTitle ("Buscaminas");
		setSize (800, 800);
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		
		juego = new BuscaminasJuego (numero_casillas, probabilidadMina, depuracionComp);
		
		fileBrowser = new JFileChooser ();
		FileNameExtensionFilter filter = new FileNameExtensionFilter ("Buscaminas Juego (.msg)", BuscaminasJuego.getFileExtension ());
		fileBrowser.setFileFilter (filter);
		fileBrowser.setFileSelectionMode (JFileChooser.FILES_ONLY );
		
		buildMenuBar ();
		panel = new BuscaminasPanel (this);
		add (panel);
		setVisible (true);	
	}

	public BuscaminasJuego getGame () {
	
		return juego;	
	}
	
	public void actionPerformed (ActionEvent e) {
	
        switch (e.getActionCommand ()) {
		
		case NUEVO_ACCION:
			newGame ();
			break;
			
		case CARGAR_ACCION:
			loadGame ();
			break;
			
		case GUARDAR_ACCION:
			saveGame ();
			break;
			
		case SALIR_ACCION:
			System.exit (0);
			break;
		
		}
		
		panel.reset ();
		invalidate ();
		validate ();
		repaint ();
		
	}
	
	private void loadGame () {
	
		int fileChooserReturnValue = fileBrowser.showOpenDialog (this);
		File saveFile = null;
		String filename = null;
		
		if (fileChooserReturnValue == JFileChooser.APPROVE_OPTION) {
			
			saveFile = fileBrowser.getSelectedFile ();
			filename = saveFile.getName ();
			
			if (filename.indexOf (".") == -1) {
		
				saveFile = new File (filename + "." + BuscaminasJuego.getFileExtension ());
				filename = saveFile.getName ();
			
			}
			
			if (!juego.load (saveFile)) {
			
				JOptionPane.showMessageDialog (this,"Could not load game.");
			
			}
			
		}
	
	}
	
	private void saveGame () {
            
		int fileChooserReturnValue = fileBrowser.showSaveDialog (this);
		File saveFile = null;
		String filename = null;
		
		if (fileChooserReturnValue == JFileChooser.APPROVE_OPTION) {

			saveFile = fileBrowser.getSelectedFile ();
			filename = saveFile.getName ();
			
			if (filename.indexOf (".") == -1) {
		
				saveFile = new File (filename + "." + BuscaminasJuego.getFileExtension ());
				filename = saveFile.getName ();
			
			}
		
			if (!juego.save (saveFile)) {
				
				JOptionPane.showMessageDialog (this,"no se pudo guardar el archivo.");
				
			}
			
		}
	
	}
	
	private void newGame () {
	
		String difficulty = (String) (JOptionPane.showInputDialog (this, "Diffficultad:", "Juego Nuevo", JOptionPane.QUESTION_MESSAGE, 
																       null, new String [] {"Facil", "Intermedio", "Experto"}, "Intermedio"));
		
		if (difficulty != null) {
		
			if (difficulty.equals ("Facil")) {
			
				juego.newGame (BuscaminasJuego.Dificultad.FACIL);
			
			} else if (difficulty.equals ("Experto")) {
			
				juego.newGame (BuscaminasJuego.Dificultad.EXPERTO);
			
			} else {
			
				juego.newGame (BuscaminasJuego.Dificultad.INTERMEDIO);
				
			}
			
		} else {
		
			return;
		
		}
	
	}
	
	private void buildMenuBar () {

		menuBar = new JMenuBar ();
			newMenuItem = new JMenuItem ("Nuevo");
		newMenuItem.setActionCommand (NUEVO_ACCION);
		newMenuItem.addActionListener (this);
		menuBar.add (newMenuItem);

		loadMenuItem = new JMenuItem ("Cargar");
		loadMenuItem.setActionCommand (CARGAR_ACCION);
		loadMenuItem.addActionListener (this);
		menuBar.add (loadMenuItem);

		saveMenuItem = new JMenuItem ("Guardar");
		saveMenuItem.setActionCommand (GUARDAR_ACCION);
		saveMenuItem.addActionListener (this);
		menuBar.add (saveMenuItem);

		quitMenuItem = new JMenuItem ("Salir");
		quitMenuItem.setActionCommand (SALIR_ACCION);
		quitMenuItem.addActionListener (this);
		menuBar.add (quitMenuItem);

		setJMenuBar (menuBar);
	
	}

}