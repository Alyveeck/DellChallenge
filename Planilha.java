package app;

/*
 * Classe Planilha exerce a logistica do transporte, 
 * calculos de distancia e valores, modalidade de transporte
 * e faz acesso ao arquivo csv para consultar as cidades e distancias.
 */

public class Planilha {
	
	/*******************VARIAVEIS******************/
	String cidadeOrigem ;   						// variavel String temporaria armazena a cidade origem informada pelo usuario
	
	String cidadeDestino;							// variavel String temporaria armazena a cidade destino informada pelo usuario
	
	int distancia;									// variavel que armazena a distancia resultante de cada consulta a tabela do csv
	
	double[] pequenoPorte = {1000 , 4.87 ,0};		// array de double que contem [0]-capacidade máxima do caminhão em kg, [1]preço por km e [2]contadora de incrementos que resultam na quantidade de caminhões necessarios.
	double[] medioPorte = {4000 , 11.92, 0};		// ''''
	double[] grandePorte = {10000 , 27.44, 0};		// ''''
	
	
	
	LeituraCSV consulta = new LeituraCSV();    			// instancia da classe LeituraCSV 
	Verificacoes check = new Verificacoes();			// instancia da classe Verificacoes
	
	/*******************GETTERS/SETTERS******************/
	public double getPequenoPorte(int x) {         
		return pequenoPorte[x];
	}
	public double getMedioPorte(int x) {
		return medioPorte[x];
	}
	public double getGrandePorte(int x) {
		return grandePorte[x];
	}
	public int getDistancia() {
		return distancia;
	}
	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}
	public String getCidadeOrigem() {
		return cidadeOrigem;
	}
	public void setCidadeOrigem(String cidadeA) {
		this.cidadeOrigem = cidadeA;
	}
	public String getCidadeDestino() {
		return cidadeDestino;
	}
	public void setCidadeDestino(String cidadeB) {
		this.cidadeDestino = cidadeB;
	}
	
	
	
	/*******************METODOS******************/
	
	// consultaDistanciaNoCSV() o metodo utiliza as variaveis origem e destino para realizar a consulta na classe LeituraCSV da distancia entre as duas cidades.
	// utiliza o metodo localizaDistancia passando como entrada o localizaCidadeXY da cidade origem e da cidade destino.
	public int consultaDistanciaNoCSV() {
		this.distancia = consulta.localizaDistancia(consulta.localizaCidadeXY(cidadeOrigem), consulta.localizaCidadeXY(cidadeDestino));
		return  distancia;        // retorna um INT com a distancia entre Origem e Destino
		
	}
	
	
	// imprimeConsultaPorTrecho() imprime na tela o resultado da consulta de trecho feita em "Consultar trechos x modalidade"
	// Ele retorna a mensagem com os valores da cidadeOrigem, cidadeDestino.
	// recebe um index informado pelo usuario para selecionar a modalidade de transporte e utiliza para acessar a variavel modalidade
	// utiliza o metodo consultaDistanciaNoCSV() para informar a distancia entre cidadeOrigem e cidadeDestino
	// e utiliza o metodo calculoCustoBasico() para retornar o custo de 1 caminhão da modalidade com base na distancia do trecho.
	public void imprimeConsultaPorTrecho(int x) {
		
		String[] modalidade = {"pequeno porte","médio porte","grande porte"};   																// array de string para armazenar a modalidade que sera imprimida
		
		System.out.println("\nDe "+this.cidadeOrigem+" para "+this.cidadeDestino+", utilizando um caminhão "									//mensagem apresentada ao usuario como resultado da consulta 
				+ "de "+modalidade[x-1]+", a distância é de "+consultaDistanciaNoCSV()+" km e o custo será de R$"+check.formatoDecimal(calculoCustoBasico(x))+".\n");
		
	}
	
	
	// calculaCustoUnitarioProduto() recebe a a variavel quantidade de um produto e o custo total do produto
	// e retorna um double com o calculo da divisao entre quantidade e custo total
	public double calculaCustoUnitarioProduto(int quantidade, double custoTotal) {
		
		return custoTotal/quantidade;
	
	}
	
	
	// calculoPesoPorDestino() recebe um objeto do tipo carga e uma string contendo a cidade destino
	// atraves de um laço FOR ela identifica os produtos da carga com o destino informado e armazena na variavel peso
	// retorna o metodocalculoCustoTotalFinal() passando a variavel peso como parametro resultando no custo da carga para o destino
	public double calculoPesoPorDestino(Carga carga , String destino) {
		
		double peso = 0;																		// variavel PESO recebe valor do tipo double
		
		for(int i = 0; i < carga.size();i++) {													// laço for para percorrer a CARGA utilizando carga.size()
		
			if(carga.carga.get(i).getDestino() == destino) {									// condicional IF comparando o destino de entrada com o destino do produto na carga
		
				peso += carga.carga.get(i).getQuantidade()*carga.carga.get(i).getPeso();		// variavel PESO adiciona o peso do produto que tem o DESTINO informado
		
			}
			
		}
		
		return calculoCustoTotalFinal(peso);													// retorna o calculo do metodo calculoCustoTotalFinal() com a variavel PESO como parametro
	
	}
	

	// calculoCustoBasico() recebe um int como parametro proveniente do index informado pelo usuario
	// é utilizada para calcular o custo base de 1 caminhão pela distancia informada.
	// o index é utilizado para condicionar o tipo de caminhão
	// e retornar o calculo do valor por km da modalide multiplicado pela distancia do trecho
	public double calculoCustoBasico(int index) {
		
		
		if(index == 2) {			 				// IF index informado no menu for igual a 2, calculará transporte de médio porte
		
			return getMedioPorte(1)*this.distancia;
		
		}else if(index == 3) {						// IF index informado no menu for igual a 3, calculará transporte de grande porte
		
			return getGrandePorte(1)*this.distancia;
		
		}else {										// ELSE calcula transporte de pequeno porte
		
			return getPequenoPorte(1)*this.distancia;
		
		
		}
	}
	
	
	// calculoCustoTotalFinal() recebe um double no parametro de entrada
	// realiza a consulta da distancia entre origem e destino 
	// executa o metodo divideCargaPorte() para definir quantos caminhoes serão necessarios
	// faz o calculo de custo total multiplicando o valorKM pela quantidade de caminhões multiplicado pela distancia
	public double calculoCustoTotalFinal(Double carga) {

		distancia = consulta.localizaDistancia(consulta.localizaCidadeXY(cidadeOrigem), consulta.localizaCidadeXY(cidadeDestino));     		// variavel DISTANCIA recebe o resultado da consulta entre origem e destino
		
		divideCargaPorte(carga);																											// executa metodo divideCargaPorte() com a CARGA como parametro
		
		double custoTotal = distancia*(grandePorte[1]*grandePorte[2])+distancia*(medioPorte[1]*medioPorte[2])+distancia*(pequenoPorte[1]*pequenoPorte[2]);	// armazena o resultado da multiplicação na variavel CUSTOTOTAL
		
		return custoTotal;																																	// retorna o custototal
	}
	
	
	// divideCargaPorte() recebe um double com o peso total da carga como parametro
	// com base no peso total da carga executa uma arvore condicional que irá
	// reduzir do peso total e incrementar a quantidade de caminhões de cada modalidade
	public void divideCargaPorte(double carga) {
		
		pequenoPorte[2] = 0;							// posições contendo a quantidade de caminhão são zeradas toda vez que executa o metodo
		
		medioPorte[2] = 0;
		
		grandePorte[2] = 0;
		
		while(carga > 0) {								// ENQUANTO a carga não chegar a zero, continua executando      
			
			if(carga >  8000) {								// SE a carga for maior que 8 toneladas 
			
				carga = carga - grandePorte[0];					// diminui da carga o equivalente a um caminhão de grande porte 
			
				grandePorte[2]++;								// incrementa 1 caminhão de grande porte
			
			}
			
			else if(carga > 6000) {							// SENÃO SE a carga for maior que 6 toneladas
			
				carga = carga - (2*medioPorte[0]);				// diminui da carga o equivalente a 2 caminhões de médio porte
			
				medioPorte[2] +=2;								// incrementa 2 caminhões de médio porte
			
			}
			
			else if(carga > 5000) {							// SENÃO SE a carga for maior que 5 toneladas
				
				carga = carga - medioPorte[0] - (2*pequenoPorte[0]);	// diminui da carga o equivalente a 1 caminhão de medio porte e 2 pequenos			
				
				pequenoPorte[2]+=2;										// incrementa 2 caminhões de pequeno porte
				
				medioPorte[2]++;										// incrementa 1 caminhão de médio porte
			
			}
			
			else if(carga > 4000) {							// SENÃO SE a carga for maior que 4 toneladas
				
				carga = carga - medioPorte[0] - pequenoPorte[0];	// diminui da carga o equivalente a 1 caminhão de médio porte e 1 de pequeno porte			
				
				pequenoPorte[2]++;									// incrementa 1 caminhão pequeno porte
				
				medioPorte[2]++;									// incrementa 1 caminhão médio porte
			
			}
			
			else if(carga > 2000) {							// SENÃO SE a carga for maior que 2 toneladas
				
				carga = carga - medioPorte[0];					// diminui da carga o equivalente a 1 caminhão de médio porte
				
				medioPorte[2]++;								// incrementa 1 caminhão de médio porte
				
			}
			
			else {											// SENÃO qualquer valor igual ou a baixo de 2 toneladas	
			
				carga = carga - pequenoPorte[0];				// diminui da carga o equivalente a 1 caminhão de pequeno porte
				
				pequenoPorte[2]++;								// incrementa 1 caminhão de pequeno porte
			
			}
		
		}
	
	}
	
	
	// calculaCustoMedioPorKm() recebe como parametros de entrada o custo total da carga.
	// divide o custo total pelo km rodado
	public double calculaCustoMedioPorKm( double custoTotal) {
	    
		int distanciaTotal = consultaDistanciaNoCSV();		// declarado variavel INT para armazenar a distancia total que retorna do metodo consultaDistanciaNoCSV()
	    
		return custoTotal / distanciaTotal;	// retorna o calculo 
	
	}
	
	
	// custoTotalPorTrecho() recebe como parametros
	// CustoTotal, Origem da carga
	// CidadeA a origem do trecho e CidadeB o destino do trecho
	public double custoTotalPorTrecho(Double custoTotal,String origem, String cidadeA, String cidadeB) {
		
		int distanciaTotal = consultaDistanciaNoCSV(); 
		
		distancia = consulta.localizaDistancia(consulta.localizaCidadeXY(cidadeA), consulta.localizaCidadeXY(cidadeB)); 
		
		double result = (custoTotal*distancia)/distanciaTotal;
		
		return result;

	}
}
