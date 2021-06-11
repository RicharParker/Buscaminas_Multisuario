/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class BuscaminasJuego {

	private double probabilidadMina;
	private long randomComponente;
	private int [] cuadroJuego;
	private ArrayList<Integer> banderas;
	private boolean [] explorado;
	private int numberoMinas;
	private int LongitudCuadro;
	private int numberoCasillasEx;
	private long empezarTiempo;
	private long pararTiempo;
	
	public enum EstadoJuego { JUGANDO, JUEGO_ACABADO, GANASTE }
	public enum Dificultad { FACIL, INTERMEDIO, EXPERTO }
	private EstadoJuego estadoJuego;
	public static final int MINA = -1; 
	public static final int CON_BANDERAS = -2; 
	public static final int NO_EXPLORADO = -3; 
	
	private static final int NUMERO_CASILLA_FACIL = 9;
	private static final double PROBABILIDAD_MINA_FACIL = 0.123456789;
	
	private static final int NUMERO_CASILLAS_INTERMEDIO = 16;
	private static final double PROBABILIDAD_MINAS_INTERMEDIO = 0.15625;
	
	private static final int NUMERO_CASILLAS_EXPEETO = 22;
	private static final double PROBABILIDAD_MINAS_EXPERTO = 0.20661157;
	
	private static final String EXTENCION_ARCHIVO = "msg";
	
	

	public BuscaminasJuego (int numero_fichas, double probabilidadMina, long depuracionComp) {
	
		reset (numero_fichas, probabilidadMina, depuracionComp);
	
	}
	

	public EstadoJuego getEstadoJuego () {
	
		return estadoJuego;
	
	}
	

	public int getNumberoMinas () {
	
		return numberoMinas;
	
	}
	
	public long getRandomComponente () {
	
		return randomComponente;
	
	}
	

	public int getLongitudCuadro(){
	
		return LongitudCuadro;
	
	}

	public int getBanderas () {
	
		return banderas.size ();
	
	}
	

	public int getEstimatedoNumberoMinas () {
	
		return numberoMinas - banderas.size ();
	
	}
	
	public int getEstadoDe (int posicion) {

		if (posicion < 0 || posicion >= cuadroJuego.length) {
		
			System.out.println ("Tile Invalid");
			assert (false);		
		}
		if (explorado[posicion]) {
		
			return cuadroJuego[posicion];
		}
		for (int i = 0; i < banderas.size (); i++) {
			
			if (banderas.get (i) == posicion) {
				
				return CON_BANDERAS;
			}
			
		}
	
		return NO_EXPLORADO;
	
	}
	

	public float getTiempoJuego () {
		return (System.currentTimeMillis () - empezarTiempo) / 1000.0f;

	}	

	public float getTiempoFinal() {

		return (pararTiempo - empezarTiempo) / 1000.0f;

	}	

	public static String getFileExtension () {
	
		return EXTENCION_ARCHIVO;
	}
	public void print () {
	
		for (int i = 0; i < cuadroJuego.length; i++) {
			if (i != 0 && i % LongitudCuadro == 0) {
			
				System.out.println ();
			
			}	
			if (cuadroJuego[i] == MINA) {
				System.out.print ('*');
			} else {
				System.out.print (cuadroJuego[i]);
			}
		}
	}
	

	public boolean exploreTile (int posicion) {

		if (posicion < 0 || posicion >= cuadroJuego.length) {
			System.out.println ("Ficha Invalida");
			assert (false);
		
		}
		
		if (explorado[posicion]) {
			return false;
		}
		explorado[posicion] = true;
		if (cuadroJuego[posicion] != MINA) {
			numberoCasillasEx++;
			if (numberoMinas + numberoCasillasEx == cuadroJuego.length) {
				estadoJuego = estadoJuego.GANASTE;
				pararTiempo = System.currentTimeMillis ();
				revealAll ();
			
			} else if (cuadroJuego[posicion] == 0) {
			
				exploreAdjacent (posicion);
			}
			return false;
		
		}

		estadoJuego = EstadoJuego.JUEGO_ACABADO;
		pararTiempo = System.currentTimeMillis ();
		revealAll ();
		return true;
	
	}
	
	public void FichaBandera (int posicion) {
	
		if (posicion < 0 || posicion >= cuadroJuego.length) {
		
			System.out.println ("Ficha Invalida");
			assert (false);
		}
		if (explorado[posicion]) {
		
			return;
		
		}
		
		for (int i = 0; i < banderas.size (); i++) {
			if (banderas.get (i) == posicion) {
				banderas.remove (i);
				return;
			}
		}
		
		if (banderas.size () < numberoMinas) {
			banderas.add (posicion);
		}
	
	}

	public void newGame (Dificultad dificultad) {
	
		switch (dificultad) {
		case FACIL:
			reset (NUMERO_CASILLA_FACIL, PROBABILIDAD_MINA_FACIL, -1);
			break;
			
		case INTERMEDIO:
			reset (NUMERO_CASILLAS_INTERMEDIO, PROBABILIDAD_MINAS_INTERMEDIO, -1);
			break;
			
		case EXPERTO:
			reset (NUMERO_CASILLAS_EXPEETO, PROBABILIDAD_MINAS_EXPERTO, -1);
			break;
		
		}
	
	}
	
	public boolean load (File guardarArchivo) {
	
		String nombreArchivo = guardarArchivo.getName ();
		FileInputStream inStream = null;
		BufferedInputStream bufferedInStream = null;
		ObjectInputStream objectStream = null;
		
		if (nombreArchivo.indexOf (".") == -1) {
		
			guardarArchivo = new File (nombreArchivo + "." + EXTENCION_ARCHIVO);
			nombreArchivo = guardarArchivo.getName ();
		}
		if (guardarArchivo.exists () && guardarArchivo.canRead () && (nombreArchivo.substring (nombreArchivo.indexOf ("."))).equals ("." + EXTENCION_ARCHIVO)) {
			try {			
				inStream = new FileInputStream (guardarArchivo);
				bufferedInStream = new BufferedInputStream (inStream);
				objectStream = new ObjectInputStream (bufferedInStream);
				probabilidadMina = (Double) (objectStream.readObject ());
				randomComponente = (Long) (objectStream.readObject ());
				numberoMinas = (Integer) (objectStream.readObject ());
				LongitudCuadro = (Integer) (objectStream.readObject ());
				numberoCasillasEx= (Integer) (objectStream.readObject ());
				empezarTiempo = System.currentTimeMillis () - (Long) (objectStream.readObject ());
				pararTiempo = (Long) (objectStream.readObject ());
				estadoJuego = (EstadoJuego) (objectStream.readObject ());
				banderas = castObjectToArrayList (objectStream.readObject ());
				cuadroJuego = (int []) (objectStream.readObject ());
				explorado = (boolean []) (objectStream.readObject ());
			
			} catch (Exception e) {
			
				return false;
			
			}
		
			return true;
		
		}
		
		return false;
	
	}
	
	public boolean save (File guardarArchivo) {
	
		String nombreArchivo = guardarArchivo.getName ();
		FileOutputStream outStream = null;
		BufferedOutputStream bufferedOutStream = null;
		ObjectOutputStream objectStream = null;

		if (nombreArchivo.indexOf (".") == -1) {
		
			guardarArchivo = new File (nombreArchivo + "." + EXTENCION_ARCHIVO);
			nombreArchivo = guardarArchivo.getName ();
		}
		if (nombreArchivo.substring (nombreArchivo.indexOf (".")).equals ("." + EXTENCION_ARCHIVO)) {
		
			try {
				outStream = new FileOutputStream (guardarArchivo);
				bufferedOutStream = new BufferedOutputStream (outStream);
				objectStream = new ObjectOutputStream (bufferedOutStream);

				objectStream.writeObject (new Double (probabilidadMina));
				objectStream.writeObject (new Long (randomComponente));
				objectStream.writeObject (new Integer (numberoMinas));
				objectStream.writeObject (new Integer (LongitudCuadro));
				objectStream.writeObject (new Integer (numberoCasillasEx));
				objectStream.writeObject (new Long (System.currentTimeMillis () - empezarTiempo));
				objectStream.writeObject (new Long (pararTiempo));
				objectStream.writeObject (estadoJuego);
				objectStream.writeObject (banderas);
				objectStream.writeObject (cuadroJuego);
				objectStream.writeObject (explorado);
				
				bufferedOutStream.flush ();
			
			} catch (Exception e) {
			
				return false;
			
			}
		
			return true;
		
		}
		
		return false;
	}
	public void reset (int numero_casillas, double probabilidadMina, long depuracionComp) {
	
		numberoMinas = 0;
		numberoCasillasEx = 0;
		estadoJuego = EstadoJuego.JUGANDO;
		pararTiempo = -1;
		empezarTiempo = 0;
	
		if (numero_casillas < 1) {
		
			numero_casillas = NUMERO_CASILLAS_INTERMEDIO * NUMERO_CASILLAS_INTERMEDIO;
		} else {
		
			numero_casillas = numero_casillas * numero_casillas;
		}
		if (probabilidadMina < 0.0) {
		
			this.probabilidadMina = PROBABILIDAD_MINAS_INTERMEDIO;
		
		} else {
		
			this.probabilidadMina = probabilidadMina;
		
		}
		
		if (depuracionComp == -1) {
		
			this.randomComponente = System.currentTimeMillis ();
		
		} else {
		
			this.randomComponente = depuracionComp;
		
		}
		
		LongitudCuadro = (int)Math.sqrt (numero_casillas);
		
		cuadroJuego = new int [numero_casillas];
		explorado = new boolean [numero_casillas];
		propogateGameGrid ();
		
		banderas = new ArrayList<Integer> (numero_casillas);
		
		empezarTiempo = System.currentTimeMillis ();
	
	}

	private void propogateGameGrid () {
	
		Random rand = new Random (randomComponente);
	
		for (int i = 0; i < cuadroJuego.length; i++) {
		
			if (rand.nextDouble () <= probabilidadMina) {
			
				cuadroJuego[i] = MINA;
				numberoMinas++;
				updateAdjacent (i);
			
			}
		
		}
	
	}

	private void updateAdjacent (int minePosicion) {
	
		int adjacentIndex = 0;
		if (minePosicion < 0 || minePosicion >= cuadroJuego.length || cuadroJuego[minePosicion] != MINA) {	
			return;		
		}
		for (int j = -1; j <= 1; j++) {		
			for (int k = -1; k <= 1; k++) {			
			adjacentIndex = minePosicion + (j * LongitudCuadro);
				
				if ((adjacentIndex % LongitudCuadro == 0 && k == -1) || ((adjacentIndex + 1) % LongitudCuadro == 0 && k == 1)) {
					continue;
				}
				adjacentIndex += k;
				if (adjacentIndex >= 0 && adjacentIndex < cuadroJuego.length && cuadroJuego[adjacentIndex] != MINA) {
					
					cuadroJuego[adjacentIndex]++;
					
				}
				
			}
		}
		
	}
	
	@SuppressWarnings("No comprobado")
	private ArrayList<Integer> castObjectToArrayList (Object obj) {
	
		if (obj instanceof ArrayList) {

			return (ArrayList<Integer>)(obj);
			
		}
		
		return null;
	
	}
	
	private void exploreAdjacent (int posicion) {
	
		int adjacentIndex = 0;

		if (posicion < 0 || posicion >= cuadroJuego.length) {
			return;
		}
		for (int j = -1; j <= 1; j++) {	
			for (int k = -1; k <= 1; k++) {
				adjacentIndex = posicion + (j * LongitudCuadro);
				if ((adjacentIndex % LongitudCuadro == 0 && k == -1) || ((adjacentIndex + 1) % LongitudCuadro == 0 && k == 1)) {
					continue;
				}
				adjacentIndex += k;
				if (adjacentIndex >= 0 && adjacentIndex < cuadroJuego.length) {
					exploreTile (adjacentIndex);
					
				}
				
			}
			
		}
	
	}

	private void revealAll () {
	
		for (int i = 0; i < explorado.length; i++) {
		
			explorado[i] = true;
		
		}
	
	}
	
}