package app;

/*
 * a classe RegistroCarga armazena o registro de cada transporte finalizado
 * quando um transporte � finalizado, a mensagem contento toda a informa��o do transporte � 
 * enviada para o registro em forma de String.
 * Assim s� � necessario armazenar strings pois o registro n�o precisa realizar nenhum calculo ou alterar informa��o.
 */

import java.util.ArrayList;
import java.util.List;


public class RegistroCarga {
	
	/*******************VARIAVEIS******************/
    public final List<String> registro = new ArrayList<>();    // variavel REGISTRO � uma ArrayList que recebe uma String em cada posi��o

    
    
    
    /*******************METODOS******************/
    
    
    // adicionaRegistro() recebe a string contendo todos os dados do transporte como parametro de entrada
    // utiliza o metodo ADD da biblioteca .ArrayList para adicionar a string a uma nova posi��o
    public void adicionaRegistro(String entrada) {
        registro.add(entrada);
    }

    
    // imprimeRegistro() imprime o arraylist contendo todos os transportes registrados
    public void imprimeRegistro() {
        if(!registro.isEmpty()) {									// condiciona IF checando se o registro N�O estiver vazio
        	
        	for (int i = 0; i < registro.size(); i++) {					// la�o FOR para percorrer todo o registro 
        		System.out.println("\n*****************************");
        		System.out.println("ID: "+(i+1));
        		System.out.println("*****************************");
        		System.out.println(registro.get(i));						// imprime o registro na posi��o referente a contadora do la�o
        
        	}
        }else {														// SEN�O com o registro vazio
        	System.out.println("REGISTRO VAZIO");						// imprime REGISTRO VAZIO na tela
        	
        }
    }
    
    
    // imprimeUltimoRegistro() � uma varia��o da imprimeRegistro() por�m imprimindo apenas o registro na ultima posi��o
    // � utilizada na finaliza��o de cada solicita��o de transporte
    public void imprimeUltimoRegistro() {
       
    	if (!registro.isEmpty()) {									// condiciona IF checando se o registro N�O estiver vazio
          
    		System.out.println(registro.get(registro.size() - 1));		// imprime na tela o ultimo registro da lista
       
    	}else {														// SEN�O com o registro vazio
    		
    		System.out.println("REGISTRO VAZIO");						// imprime REGISTRO VAZIO na tela
        
    	}
   
    }

}
