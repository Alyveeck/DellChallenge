package app;
/*
 *  Classe para leitura do arquivo CSV contendo os dados do DNIT.
 */

	/*******************BIBLIOTECAS******************/
import java.io.BufferedReader; 
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class LeituraCSV {
	
	/*******************VARIAVEIS******************/
	String[][] array = leituraCSV();  // 
	
	/*******************METODOS******************/
	
	// leituraCSV() faz a leitura inicial do arquivo CSV, e já armazena em uma matriz as linhas do arquivo em forma de array;
	public String[][] leituraCSV() {
	       
		
		String arquivo = "src/DNIT-Distancias.csv";                              // armazena a rota do arquivo CSV na variavel ARQUIVO
	   
		String linha;                                                            // variavel para armazenar cada campo de linha lida do arquivo
	  
		List<String[]> listaLinha = new ArrayList<String[]>();    				// List de strings pra juntar os campos lidos na variavel linha
	    
	    
	    try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {	    //   try/catch com BufferedRead e FileReader para ler os caracteres do arquivo    
	    	
	    	while((linha = br.readLine()) != null){	            					// loop while para ler cada campo da linha enquanto não for null(campo vazio)
	    	
	    		listaLinha.add(linha.split(";"));    	    						// usando o delimitador ';' para separar os campos da linha e adicionando ao array de linhas
	    	
	    	}	       
	   
	    } catch (Exception e){	     												// catch para qualquer exceção que ocorrer durante a leitura
	    
	    	System.out.println(e);	    
	  
	    }
	    
	    
	    String[][] arrayLeitura = new String[listaLinha.size()][0];	   				// criando um array 2d para armazenar os dados da lista de linhas com o tamanho da lista
	   
	    listaLinha.toArray(arrayLeitura);	    									// convertendo a lista para um array 2d
	    
	    return arrayLeitura;														// retorna o array 2d com os dados do arquivo CSV para a variavel array da classe
	}
	
	
	// mostraCidadesAtendidas() é um metodo para imprimir na tela do menu as cidades armazenadas na primeira linha do array 
	public void mostraCidadesAtendidas() {
		
		System.out.println("*CIDADES ATENDIDAS*");       // formatando texto imprimido na tela
		
		System.out.println("----------------------");
		
		for(int i = 0; i < array[0].length ; i++) {		 // laço FOR para percorrer todo o LENGTH da linha 0 do array
		
			System.out.println("*** "+array[0][i]);		 // imprime o campo coluna contendo o nome da cidade 
	
		}	
	
		System.out.println("----------------------");

	}
	
	
	// confirmaCidadeX() é um metodo para verificar se a cidade informada consta na lista de cidades atendidas, recebe uma String entrada informada pelo usuario
	public boolean confirmaCidadeX(String entrada) {  
		
		for(int i = 0; i < array[0].length ; i++) {   // laço FOR percorrendo o cabeçalho do array com o nome das cidades
		
			if(array[0][i].equals(entrada)) {         // condicional IF testando se o valor da variavel é igual a String de entrada
		
				return true;						  // se a String de entrada conferir com alguma cidade atendida, retorna true
			
			}
		
		}		
	
		return false;								  // se não retornar true no laço, retornara falso
	
	}
	
	
	// localizaCidadeXY()  semelhante ao confirmaCidadeX(), irá receber a String de entrada e procurar se consta no array, 
	// porém irá retornar o INDEX do array onde a cidade se encontra; Detalhe, com o indice da coluna, adicionando +1 temos o indice da linha. Por isso XY.
	public int localizaCidadeXY (String entrada) {
		
		
		for(int i = 0; i < array[0].length ; i++) {     //laço FOR percorrendo o cabeçalho do array com o nome das cidades
			
			if(array[0][i].equals(entrada)) {           // condicional IF testando se o valor da variavel é igual a String de entrada
			
				return i;								// se a String de entrada conferir com alguma cidade atendida, retorna o INDEX da coluna, baseado na contadora do laço.
			
			}
		
		}		
		
		return -1;										// em ultimo caso retorna -1, o que daria erro, porém este método só é usado após a conferencia do confirmaCidadeX();
	
	}
	
	
	// localizaDistancia() é o metodo que recebe dois inteiros de entrada que são utilizados como coordenadas da linha e coluna para encontrar o campo da distancia
	// as duas entradas são retorno do metodo localizaCidadeXY() duas vezes, uma para a ORIGEM e outra para DESTINO.
	public int localizaDistancia(int linha, int coluna) {
		
		return Integer.parseInt(array[linha+1][coluna]); // Integer.parseINT para converter a String do CSV em Inteiro. 
														 // o index da linha é adicionado a +1 para compensar a formatação da tabela do CSV.
	}
		
}
	
	

