/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Casilla extends JButton {

	private int indiceCasillas;
	private int estado;
	private BuscaminasPanel panel;
	private ImageIcon mineImage;
	private ImageIcon flagImage;
	
	private static final String MINE_IMG_PATH = "../assets/mine.png";
	private static final String FLAG_IMG_PATH = "../assets/flag.png";
	

	public Casilla (int tileIndex, BuscaminasPanel p) {
		
		super ("");
		this.indiceCasillas = indiceCasillas;
		this.panel = p;
		estado = p.getGame ().getEstadoDe(this.indiceCasillas);
		setEnabled (true);
		mineImage = new ImageIcon (Toolkit.getDefaultToolkit ().getImage (MINE_IMG_PATH));
		flagImage = new ImageIcon (Toolkit.getDefaultToolkit ().getImage (FLAG_IMG_PATH));
		
	}

	public int getIndiceCasillas () {
	
		return indiceCasillas;
	
	}
	

	public void paintComponent (Graphics g) {
	
		super.paintComponent (g);
		
		estado = panel.getGame ().getEstadoDe(this.indiceCasillas);
		
		switch (estado) {
		
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
			setEnabled (false);
			setIcon (null);
			setDisabledIcon (null);
			if (estado == 0) {
				setText (" ");
			
			} else {
			
				setText (Integer.toString (estado));
			
			}
			break;
		
		case BuscaminasJuego.MINA:
		
			setEnabled (false);
			setText ("");
			setIcon (mineImage);
			setDisabledIcon (mineImage);
			break;
		
		case BuscaminasJuego.CON_BANDERAS:
			
			setText ("");
			setIcon (flagImage);
			break;
		
		case BuscaminasJuego.NO_EXPLORADO:
			setText ("?");
			setIcon (null);
			setDisabledIcon (null);
			break;
		
		}

	}

}