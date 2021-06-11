/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

/**
 *
 * @author ARCHV
 */
public class Buscaminas {

	private static void parseArguments (String[] args) {
	
         Integer numero_casillas = 0;
	 double probabilidadMina = -1.0;
	long depuracionComp = -1;
	Integer i = 0;
	
		if (args.length != 0) {
		
			while (i < args.length) {
				switch (args[i]) {		
				case "-numCasillas":			
					if (i + 1 < args.length) {				
						try {
							numero_casillas = Integer.parseInt (args[++i]);
						
						} catch (NumberFormatException e) {
						
							System.out.println ("Uso: buscaminas java -numCasillas <valor entero>");
							return;
						}		
					} else {
					
						System.out.println ("Uso: buscaminas java -numCasillas <valor entero>");
						return;
					
					}
				
				break;
				
				case "-probMina":
					
					if (i + 1 < args.length) {
					
						try {
						
							probabilidadMina = Double.parseDouble (args[++i]);
						
						} catch (NumberFormatException e) {
						
							System.out.println ("Uso: buscaminas java -probMina <valor entero>");
							return;
						
						}
					
					} else {
					
						System.out.println ("-probMina");
						return;
					
					}
				
				break;
				
				case "-componente":
					
					if (i + 1 < args.length) {
					
						try {
						
							depuracionComp = Long.parseLong (args[++i]);
						
						} catch (NumberFormatException e) {
						
							System.out.println ("Uso: buscaminas java -semilla <valor entero>");
							return;
						
						}
					
					} else {
					
						System.out.println ("Uso: buscaminas java -componente <valor entero>");
						return;
					
					}
				
				break;
				
				default:
				case "-help":
				
					System.out.println ("Uso: buscaminas java -opcion1 param1 -opcion2 param2 ...");
					System.out.println ("Donde las opciones incluyen:");
					System.out.println ("\\ t-numCasillas <valor entero> \\ tLa cantidad de mosaicos de ancho y alto");
					System.out.println ("\\ t-mineProb <valor decimal> \\ la probabilidad de que una ficha sea mía");
					System.out.println ("\\ t-semilla <valor entero> \\ t \\ tLa semilla aleatoria exacta a usar");
					System.out.println ("\\ t-ayuda \\ t \\ t \\ t \\ t Imprime el mensaje de ayuda");
					return;
				
				}
				
				i++;
			
			}
		
		}
		
            new BuscaminasFrame(numero_casillas, probabilidadMina , depuracionComp);
	
	}
	
	public static void main (String[] args) {
		
		parseArguments (args);
	
	}

}
    

