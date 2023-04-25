package app;

/*
 * a classe Carga manipula um arraylist de Produtos.
 * cada carga é referente a um transporte 
 * 
 */

	/*******************BIBLIOTECAS******************/
import java.util.ArrayList;

public class Carga{
	
	/*******************VARIAVEIS******************/
	public ArrayList<Produtos> carga;					// variavel carga é um ArrayList de produtos
	
	
	public Carga() {									// metodo construtor inicializando o objeto Carga 
		
		carga = new ArrayList<Produtos>();
	
	}

	
	/*******************METODOS******************/
	
	
	// adicionarProduto() recebe um parametro do tipo Produto como entrada e adiciona a Carga
	public void adicionarProduto(Produtos produto) {
		
		this.carga.add(produto);
		
	}	
	
	
	// removerProduto() recebe um index como parametro de entrada informando a posição do produto e remove da carga
	public void removerProduto(int i) {
		
		this.carga.remove(i-1);
	}
	
	
	// listaCarga() recebe uma string como parametro de entrada contendo o destino da carga porem pode ser executada vazia
	// se executada com destino, irá imprimir lista de produtos daquele destino
	// se executada vazia, imprimirá todos os produtos da lista.
	public void listarCarga(String destino) {
		
		if(destino != null) {										// SE parametro de entrada não for NULL
		
			for(int i = 0; i < this.carga.size();i++) {					// laço para percorrer toda a lista carga
		
				if(carga.get(i).getDestino() == destino) {					// SE destino do produto for igual ao destino do parametro de entrada
		
					System.out.println((i+1)+" | "+carga.get(i));				// imprime na tela o produto
			
				}
	
			}
	
			System.out.println("Peso total da carga = "+pesoTotal(destino)+"kg");	// após finalizar a lista, imprime o peso total da carga dos items com destino indicado
	
		}else {														// SENÃO (parametro de entrada vazio)
		
			for(int i = 0; i < this.carga.size();i++) {					// laço para percorrer toda a lista carga	
		
				System.out.println((i+1)+" | "+carga.get(i));				// imprime na tela o produto
		
			}
			
			System.out.println("Peso total da carga = "+pesoTotal(null)+"kg");		// após finalizar a lista, imprime o peso total da carga
		
		}
		
	}
	
	

	
	// pesoTotal() recebe uma string como parametro de entrada contendo o destino da carga porem pode ser executada vazia
	// se executada com destino, irá calcular o peso total dos produtos daquele destino
	// se executada vazia, irá calcular o peso total da carga.
	public double pesoTotal(String destino) {
		
		double peso = 0;					// declara variavel peso do tipo double para receber o calculo
		
		if(destino != null) {														// SE parametro de entrada não for NULL
		
			for(int i = 0; i < this.carga.size();i++) {									// executa laço FOR que percorre toda a carga
			
				if(carga.get(i).getDestino() == destino) {									// SE destino do produto for igual ao destino do parametro de entrada
			
					peso = peso + (carga.get(i).getPeso()*carga.get(i).getQuantidade());		// adiciona à variavel carga o calculo de peso do produto multiplicado pela quantidade
			
				}
		
			}
		
		}else {																		// SENÃO (parametro de entrada vazio)
		
			for(int i = 0; i < this.carga.size();i++) {									//executa laço FOR que percorre toda a carga
			
				peso = peso + (carga.get(i).getPeso()*carga.get(i).getQuantidade());		// adiciona à variavel carga o calculo de peso do produto multiplicado pela quantidade
			
			}
		
		}
		
		return peso;															// retorna a variavel peso contendo o peso total calculado
	
		}

	
		// identificaDestinosCarga() recebe um arraylist de Produtos como entrada
		// ela percorre a lista de produtos identificando cada destino
		// e armazenado em um novo arraylist toda vez que detecta um destino diferente na lista
		public  ArrayList<String> identificaDestinosCarga(ArrayList<Produtos> lista) {
		   
			ArrayList<String> destinosUnicos = new ArrayList<>();			// declarado um novo arraylist para receber os nomes dos destinos
		  
			for (int i = 0; i < lista.size();i++) {							// laço FOR para percorrer toda a lista de produtos
		   
				String destino = lista.get(i).getDestino();						// declarado uma variavel String para armazenar temporariamente a cidade daquele produto
		    
				if (!destinosUnicos.contains(destino)) {						// SE lista de destinos unicos não conter o destino do produto
		     
					destinosUnicos.add(destino);									// adiciona o destino do produto à lista de destinos
					
				}
		  
			}
		 
			return destinosUnicos;											// retorna lista de destinos unicos da carga
		}
		
		
		// quantidadeUnitaria() é utilizado para calcular a quantidade total de produtos da carga
		public int quantidadeUnitaria() {
		
			int quantidade = 0;												// declarado variavel do tipo INT para receber a quantidade total
			
			for(int i = 0; i < this.carga.size();i++) {						// laço FOR para percorrer toda a lista de produtos
				
				quantidade = quantidade + carga.get(i).getQuantidade();				// adiciona à variavel QUANTIDADE a quantidade do produto especifico
			
			}
			
			return quantidade;												// retorna a QUANTIDADE total de produtos unitarios da carga
		
		}
		
		
		// size() apenas invoca o metodo .size do objeto Carga fora da classe Carga
		public int size() {
			
			return this.carga.size();
		
		}
		
		public int quantidadeTotalProdutos(String destino) {
					int quantidadeTotal = 0;					// declara variavel peso do tipo double para receber o calculo
		
					if(destino != null) {														// SE parametro de entrada não for NULL
		
						for(int i = 0; i < this.carga.size();i++) {									// executa laço FOR que percorre toda a carga
			
							if(carga.get(i).getDestino() == destino) {									// SE destino do produto for igual ao destino do parametro de entrada
			
								quantidadeTotal = quantidadeTotal + carga.get(i).getQuantidade();		// adiciona à variavel carga o calculo de peso do produto multiplicado pela quantidade
			
							}
		
						}
		
					}else {																		// SENÃO (parametro de entrada vazio)
		
						for(int i = 0; i < this.carga.size();i++) {									//executa laço FOR que percorre toda a carga
			
							quantidadeTotal = quantidadeTotal + carga.get(i).getQuantidade();		// adiciona à variavel carga o calculo de peso do produto multiplicado pela quantidade
			
						}
		
					}
		
					return quantidadeTotal;
			}
		
		
		// custoMedioPorProduto() recebe o custo total de entrada
		// retorna o custo total dividido pela quantidade de produtos cadastrados na lista
		public double custoMedioPorProduto(Double custoTotal) {
			return custoTotal / this.carga.size();		
			
		}
		

}



