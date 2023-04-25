package app;

/*
 * a classe RegistroCarga armazena o registro de cada transporte finalizado
 * quando um transporte é finalizado, a mensagem contento toda a informação do transporte é 
 * enviada para o registro em forma de String.
 * Assim só é necessario armazenar strings pois o registro não precisa realizar nenhum calculo ou alterar informação.
 */

import java.util.ArrayList;
import java.util.List;


public class RegistroCarga {
	
	/*******************VARIAVEIS******************/
    public final List<String> registro = new ArrayList<>();    // variavel REGISTRO é uma ArrayList que recebe uma String em cada posição

    
    
    
    /*******************METODOS******************/
    
    
    // adicionaRegistro() recebe a string contendo todos os dados do transporte como parametro de entrada
    // utiliza o metodo ADD da biblioteca .ArrayList para adicionar a string a uma nova posição
    public void adicionaRegistro(String entrada) {
        registro.add(entrada);
    }

    
    // imprimeRegistro() imprime o arraylist contendo todos os transportes registrados
    public void imprimeRegistro() {
        if(!registro.isEmpty()) {									// condiciona IF checando se o registro NÃO estiver vazio
        	
        	for (int i = 0; i < registro.size(); i++) {					// laço FOR para percorrer todo o registro 
        		System.out.println("\n*****************************");
        		System.out.println("ID: "+(i+1));
        		System.out.println("*****************************");
        		System.out.println(registro.get(i));						// imprime o registro na posição referente a contadora do laço
        
        	}
        }else {														// SENÃO com o registro vazio
        	System.out.println("REGISTRO VAZIO");						// imprime REGISTRO VAZIO na tela
        	
        }
    }
    
    
    // imprimeUltimoRegistro() é uma variação da imprimeRegistro() porém imprimindo apenas o registro na ultima posição
    // é utilizada na finalização de cada solicitação de transporte
    public void imprimeUltimoRegistro() {
       
    	if (!registro.isEmpty()) {									// condiciona IF checando se o registro NÃO estiver vazio
          
    		System.out.println(registro.get(registro.size() - 1));		// imprime na tela o ultimo registro da lista
       
    	}else {														// SENÃO com o registro vazio
    		
    		System.out.println("REGISTRO VAZIO");						// imprime REGISTRO VAZIO na tela
        
    	}
   
    }

}
