/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BuscaminasPanel extends JPanel {
	

	private final BuscaminasFrame frame;
	private JPanel rejaPanel;
	private final JLabel minaLabel;
	private final JLabel estatusLabel;
	private int contadorMina;
	private String estatus;
	private final Casilla [] tiles;

        	public void reset () {
	
		int numero_casillas = 0;
	
		if (tiles != null) {
		
			for (int i = 0; i < numero_casillas; i++) {
				rejaPanel.remove (tiles[i]);
			}
		}
		
		if (rejaPanel != null) {
		
			remove (rejaPanel);
		}
		
                numero_casillas =(int)Math.pow (frame.getGame().getLongitudCuadro(), 2.0);
		rejaPanel = new JPanel (new GridLayout (frame.getGame().getLongitudCuadro(), frame.getGame().getLongitudCuadro()));
		
                	for (int i = 0; i < numero_casillas; i++) {
		
			tiles[i] = new Casilla (i, this);
			tiles[i].addMouseListener (new MouseHandler ());
			rejaPanel.add (tiles[i]);
		
		}
		
		contadorMina = getGame ().getEstimatedoNumberoMinas () ;
		
		estatus = "Cuidado!";
		
		add (rejaPanel, BorderLayout.CENTER);
		
		repaint ();
	
	}
	
	public BuscaminasPanel (BuscaminasFrame parent) {
	
		frame = parent;
		tiles= null;
		rejaPanel = null;
		
		minaLabel = new JLabel ("Minas: "+ Integer.toString (contadorMina));
		estatusLabel = new JLabel ("Cuidado!", SwingConstants.CENTER);
		
		setLayout (new BorderLayout ());
		
		add (minaLabel, BorderLayout.SOUTH);
		add (estatusLabel, BorderLayout.NORTH);
	        reset ();
	
	}
	
       

        @Override
	public void paintComponent (Graphics g) {
	
		super.paintComponent (g);
		
		minaLabel.setText ("Minas: "+ Integer.toString (contadorMina));
		estatusLabel.setText (estatus);

	}
	

	public BuscaminasJuego getGame () {
	
		return (BuscaminasJuego) frame.getGame ();
	
	}
        
        
        
        
	

	private class MouseHandler extends MouseAdapter {

		public void ratonPresionado (MouseEvent e) {
		
			if (e.getButton () == MouseEvent.BUTTON3) {
			
				getGame ().FichaBandera(((Casilla)(e.getSource ())).getIndiceCasillas ());
			
			} else if (e.getButton () == MouseEvent.BUTTON1) {
			
				getGame ().exploreTile (((Casilla)(e.getSource ())).getIndiceCasillas());
			
			}
			
			switch (getGame ().getEstadoJuego()) {
		
			case JUGANDO -> contadorMina = getGame ().getEstimatedoNumberoMinas ();
			
			case JUEGO_ACABADO -> {
                            contadorMina = getGame ().getEstimatedoNumberoMinas ();
                            estatus = "Perdiste en unos pocos segundos " + Float.toString (getGame ().getTiempoFinal ()) + " Segundos!";
                        }
			
			case GANASTE -> {
                            contadorMina = getGame ().getEstimatedoNumberoMinas ();
                            estatus = "Ganaste en unos pocos " + Float.toString (getGame ().getTiempoFinal()) + " Segundos!";
                        }
				
			}
			
			repaint ();
		
		}
	
	}
	
}