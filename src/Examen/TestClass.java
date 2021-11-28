package Examen;

import java.io.BufferedReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;
import org.apache.commons.lang3.StringUtils;

public class TestClass {
	public static void main(String args[]) throws IOException {

		Stack<String> pila = new Stack();

		Scanner sc = new Scanner(System.in);
	
		System.out.print("ingresa texto");
		String algo = sc.nextLine();    
		
		String car="";
		String cadena="";
		String coincidencia="";
		String coincidencias="";
		int cont=0;
		ArrayList<String> cado=new ArrayList<String>();
	   cado= Analizador(algo);
	    String[] array=new String[cado.size()];
        array=cado.toArray(array);
        
	    for(int i=0;i<array.length;i++) {
	    	car+=array[i];
	    }
	    
		InputStream ls = new ByteArrayInputStream(algo.getBytes());
		NewLexer lexer2 = new NewLexer(ls);
		
		pila.push("$");
		pila.push("E");
		
		String ip = "";
		boolean error = false;

		Token token = lexer2.yylex();

		ip = token.toString(); // ip primer token de la cadena
		String X = pila.peek(); // X valor tope pila E
		System.out.println("coincidencia              pila                   entrada                              salida         ");
		System.out.print("                      " + pila);
		 System.out.print("                    "+algo+"$");
		 while (X != "$" && error == false) {
			if (X == ip) {
				System.out.println("		coincide " + X + " ->" + ip);
				pila.pop();
				token = lexer2.yylex();
				
				coincidencia+=array[cont];
				array[cont]="";
				cont++;
					
				if (token == null) {
					ip = "$";
				} else if (token.toString() == "ERROR") {
					System.out.println("            ERROR");
					System.out.println("\n"+"Cadena Rechazada");
					error = true;
				} else if (token != null) {
					ip = token.toString();
				}
			} else if (Matriz(X, ip) == "0") {
				System.out.println("               ERROR");
				System.out.println("\n"+"Cadena Rechazada");
				error = true;
			} else if (Matriz(X, ip) == "TE'") {
				System.out.println("				Salida " + X + " -> TE'");
				pila.pop();
				pila.push("E'");
				pila.push("T");
			
			} else if (Matriz(X, ip) == "FT'") {
				System.out.println("				Salida " + X + " -> FT'");
				pila.pop();
				pila.push("T'");
				pila.push("F");
			
			} else if (Matriz(X, ip) == "+TE'") {
				System.out.println("				Salida " + X + " -> +TE'");
				pila.pop();
				pila.push("E'");
				pila.push("T");
				pila.push("suma");
			
			} else if (Matriz(X, ip) == "*FT'") {
				System.out.println("				Salida " + X + " -> *FT'");
				pila.pop();
				pila.push("T'");
				pila.push("F");
				pila.push("multiplicacion");
		
			} else if (Matriz(X, ip) == "(E)") {
				System.out.println("				Salida " + X + " -> (E)");
				pila.pop();
				pila.push("pard");
				pila.push("E");
				pila.push("parl");
				
			} else if (Matriz(X, ip) == "entero" || Matriz(X, ip) == "flotante" || Matriz(X, ip) == "multiplicacion"
				|| Matriz(X, ip) == "exponencial" || Matriz(X, ip) == "pard" || Matriz(X, ip) == "parl") {
				System.out.println("				Salida " + X + " -> " + ip);
				pila.pop();
				pila.push(ip);
			
			} else if (Matriz(X, ip) == "epsilon") {
				System.out.println("				Salida " + X + " -> epsilon");
				pila.pop();
				
			}
			if(error==false) {
			System.out.print("coincidencia: "+coincidencia+"   ");
			System.out.print("    " + pila);
			System.out.print("            "+StringUtils.difference(coincidencia ,algo)+"$");
			
			}
			if(pila.peek()=="$") {
				System.out.println("\n"+"Cadena Aceptada");
			}
			X = pila.peek();
		}
	}
public static String Matriz(String tope, String token) {
		String aux = "";
		String Matriz[][] = { { "", "entero", "flotante", "exponente", "suma", "multiplicacion", "parl", "pard", "$" },
				{ "E", "TE'", "TE'", "TE'", "0", "0", "TE'", "0", "0" },
				{ "E'", "0", "0", "0", "+TE'", "0", "TE'", "epsilon", "epsilon" },
				{ "T", "FT'", "FT'", "FT'", "0", "0", "FT'", "0", "0" },
				{ "T'", "0", "0", "0", "epsilon", "*FT'", "0", "epsilon", "epsilon" },
				{ "F", "entero", "flotante", "exponente", "0", "0", "(E)", "0", "0" } };
	
		for (int i = 0; i < Matriz.length; i++) { 
			for (int j = 0; j < Matriz[i].length; j++) { 
				if (Matriz[i][j] == tope) {
					for (int h = 0; h < Matriz.length; h++) {
						for (int k = 0; k < Matriz[h].length; k++) {
							if (Matriz[h][k] == token) {				
								aux = Matriz[i][k];
							}
						}
					}
				}
			}
		}
		return aux;
	}


public static ArrayList<String> Analizador(String cadena) throws IOException {
	
	 InputStream ls = new ByteArrayInputStream(cadena.getBytes());
		NewLexer lexer = new NewLexer (ls);
		
		ArrayList<String> cad=new ArrayList<String>();
		
		
	 while(true){
		 Token token = lexer.yylex();
		  if(token == null){	
			 break;
		 } switch(token){
		 case entero:
			     cad.add(lexer.lexeme);
	 		break;
		 case flotante:
		     cad.add(lexer.lexeme);
	 		break;
		 case exponente:
		     cad.add(lexer.lexeme);
	 		break;
		 case suma:
		     cad.add(lexer.lexeme);
		 break;
		 case pard:
		     cad.add(lexer.lexeme);
	 		break;
		 case parl:
		     cad.add(lexer.lexeme);
	 		break;
		 case multiplicacion:
		     cad.add(lexer.lexeme);
	 		break;
		default:
			
			break;
		 }
	    }
	 
	return cad;
	
}}
		
		
		
		
		