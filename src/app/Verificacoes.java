package app;

/*
 * Classe requisitada quando necessario confirmar, converter, formatar algum tipo de entrada de dado feita pelo usuário
 */
 
	/*******************BIBLIOTECAS******************/
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.regex.Pattern;
import java.util.Scanner;

public class Verificacoes {
	
	/*******************VARIAVEIS******************/
	public  String value = " À Á Â Ã Ä Å Æ Ç È É Ê Ë Ì Í Î Ï Ð Ñ Ò Ó Ô Õ Ö Ø Ù Ú Û Ü Ý Þ ß à á â ã ä å æ ç è é ê ë ì í î ï ð ñ ò ó ô õ ö ø ù ú û ü ý þ ÿ "; //variavel contentado todos os caracteres acentuados para uso do metodo checarAcento(), 
																																							//feito com a biblioteca .NORMALIZER e .PATTERN
	DecimalFormat df = new DecimalFormat();  // instancia o objeto da biblioteca .DECIMALFORMAT
	
	Scanner sc = new Scanner(System.in);	// instancia o objeto scanner da biblioteca .SCANNER   			
	
	
	/*******************METODOS******************/
	
	
	//public void main(String args[]) throws Exception {
       // System.out.println(checarAcento(value));
  //  }
	
	// checarAcento() recebe uma string de entrada e confere se há caracteres com acento na string. Havendo algum caracter, o metodo busca na variavel VALUE o correspondente não acentuado e retorna a string sem acento
	public String checarAcento(String entrada) {
       
		String semAcentoStr = Normalizer.normalize(entrada, Normalizer.Form.NFD); // variavel semAcentoStr armazena o resultado do metodo normalize(string de entrada, Normalizer.Form.NFD)
       
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+"); // variavel pattern armazena a compilação da biblioteca .PATTERN
       
		return pattern.matcher(semAcentoStr).replaceAll("");                    // retorna passando a variavel semAcentoStr com o metodo matcher
   
	}
	
	
	// converteMaiusculo() recebe string de entrada e retorna a mesma string porém com todos os caracteres em maiusculo
	public String converteMaiusculo(String entrada) {
		
		return entrada.toUpperCase();										// retorna a entrada com o metodo toUpperCase()		
	}
	
	
	// checaVirgula() recebe string de entrada, converte de virgula para ponto, e retorna convertido para double. 
	public double checaVirgula(String entrada) {
		
		if(entrada.contains(",")) {											// condicional IF para testar se a string contem virgula
		
			entrada = entrada.replaceAll(",",".");							// metodo replaceAll convertendo as virgulas em pontos
		
		}
		
		return Double.parseDouble(entrada);									// retorna a string convertida em double com o metodo parseDouble
	
	}
	
	
	// formatoDecimal() recebe um double de entrada e retorna o double formatado em string com apenas duas casas após a virgula
	public String formatoDecimal(double num) {
		
		return String.format("%.02f", num);									// retorna uma string do double com apenas 2 casas apos a virgula
	
	}

	
	// confirmaIndex() e utilizado na navegação dos menus para evitar a NumberFormatExpection, ele utiliza o scanner para ler o input do usuario e executa um loop até receber um INT válido
	public int confirmaIndex() {
	
		int index;															// variavel index para receber INT do input do usuario
	
		try {																// try/catch para prevenir NumberFormatException
																			
			index = Integer.parseInt(sc.next());							// armazena na variavel index um parseInt do scanner
		
			return index;													// retorna o index
		
		}catch(NumberFormatException n){									// catch caso o usuario informe uma entrada invalida para INT
		
			System.out.println("Opção inválida.");							// imprime na tela "opção invalida" para informar
		
			return confirmaIndex();											// retorna o proprio metodo para gerar o loop
		}
		
	}
}
