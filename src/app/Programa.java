package app;

/*
 *  a classe Programa é a responsavel por operar toda a estrutura do codigo
 *  e realizar toda a interação com o usuário
 */



/*******************BIBLIOTECAS******************/
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



public class Programa {
	
	/*******************VARIAVEIS******************/
	public int index;								// variavel index do tipo INT é usada para armazenar a opção de escolha no menu
	
	public char confirm;							// variavel confirm do tipo CHAR armazena opção do menu em que é necessário escolher entre Y e N (Yes/No)
	
	public String tempString;						// variavel tempString é uma String que armazena temporariamente o nome da cidade informado pelo usuário
	
	Scanner sc = new Scanner(System.in);			// criando instancia do Scanner com parametro System.in
	Planilha plan = new Planilha();					// criando instancia da Planinha
	Verificacoes check = new Verificacoes();		// criando instancia de Verificações
	RegistroCarga rg = new RegistroCarga();			// criando instancia de RegistroCarga
	
	
	/*******************METODOS******************/
	
	
	// menu() é o menu inicial do programa
	// é utilizado um switch case com escolha das opções informadas no console
	// dentro de um laço DO WHILE que só se encerra quando o usuario escolhe sair do programa
	public void menu() {
	
		do {																		// abertura do laço DO WHILE
		
			limpaTela();																// limpaTela() limpa a tela do console cada vez que o menu é iniciado
			
			System.out.println("Selecione a opção desejada com base no índice.");		// impressão do menu de opções com base no indice
			
			System.out.println("1 - Consulta de Trechos x Modalidade.");
			
			System.out.println("2 - Cadastrar transporte.");
			
			System.out.println("3 - Dados Estatísticos.");
			
			System.out.println("0 - Sair.");
			
			index = check.confirmaIndex();				// variavel INDEX recebe o metodo confirmaIndex() da classe verificações
			
			switch(index) {								// abertura do SWITCH CASE
			
				case 1: 								// case 1 (Consulta de Trechos x Modalidade)
																					
					limpaTela();																
					
					plan.consulta.mostraCidadesAtendidas();		// executa mostraCidadesAtendidas() da classe LeituraCSV
					
					informaOrigem();							// executa informaOrigem() para receber do usuário a cidade de origem
					
					informaDestino();							// executa informaDestino() para receber do usuário a cidade destino
					
					informaModalidade();						// executa informaModalidade() para receber do usuario a modalidade escolhida
					
					aguardaTecla();								// aguardaTecla() pausa o programa até o usuario digitar alguma tecla
					
					break;										// termina o caso e recomeça o menu inicial
				
				case 2: 								// case 2 (Cadastrar Transporte)												
					
					//limpaTela();						
					
					interacaoCadastroTransporte();			// executa a interacaoCadastroTransporte() contendo um menu posterior
					
					break;									
				
				case 3: 								// case 3 ( Dados Estatísticos)
				
					limpaTela();
					
					rg.imprimeRegistro(); 				// executa o metodo imprimeRegistro() da clase RegistroCarga para emitir o histórico de transportes
					
					aguardaTecla();
					
					break;
				
				case 0:									// case 0 ( Encerra o programa)    variavel index recebe o valor 0, resultando no encerramento do laço DO WHILE
				
					limpaTela();
					
					System.out.println("\nPROGRAMA ENCERRADO.");
					
					break;
			
			}
		
		} while(index != 0);
		
	}
	
	
	// informaOrigem() metodo de interação com o usuário para receber a cidade de Origem
	// é utilizado metodos de checar acento, converter para maiusculo para formatar a string da mesma maneira que o arquivo CSV fornecido
	// também dentro de um DO WHILE para executar enquanto o INPUT nao corresponder a uma cidade do arquivo CSV
	public void informaOrigem() {
		
		do {																			// abre laço DO WHILE
		
			System.out.println("INFORME A CIDADE DE ORIGEM:");							
			
			tempString = check.checarAcento((check.converteMaiusculo(sc.nextLine())));		// armazena na tempString a entrada do scanner, passando pelos metodos de converteMaiusculo() e checarAcento()
			
			if(plan.consulta.confirmaCidadeX(tempString)) {									// SE localizar a cidade informada no arquivo CSV o metodo confirmaCidadeX retorna TRUE
			
				plan.setCidadeOrigem(tempString);												// define Cidade Origem na planilha
				
				System.out.println("Cidade origem definida: "+tempString+"\n");					
			
			}else {																			// SENÃO 
							
				System.out.print("Cidade não encontrada.\n\nPOR FAVOR, ");					// solicita nova tentiva
			
			}
		
		}while(!plan.consulta.confirmaCidadeX(tempString));									// executa laço enquanto o metodo confirmaCidadeX() retornar FALSE
	
	}
	
	
	// informaDestino() metodo de interação com o usuário para receber a cidade destno
	// é utilizado metodos de checar acento, converter para maiusculo para formatar a string da mesma maneira que o arquivo CSV fornecido
	// também dentro de um DO WHILE para executar enquanto o INPUT nao corresponder a uma cidade do arquivo CSV
	public void informaDestino() {
	
		do {																			// abre laço DO WHILE
		
			System.out.println("INFORME A CIDADE DESTINO:");								
		
			tempString = check.checarAcento((check.converteMaiusculo(sc.nextLine())));			// armazena na tempString a entrada do scanner, passando pelos metodos de converteMaiusculo() e checarAcento()
		
			if(plan.consulta.confirmaCidadeX(tempString)) {										// SE localizar a cidade informada no arquivo CSV o metodo confirmaCidadeX retorna TRUE
				
				if(tempString.equals(plan.getCidadeOrigem())){										// SE a cidade destino for igual a cidade origem já definida, alerta o usuario e recomeça o laço
				
					System.out.print("Cidade destino não pode ser igual a origem.\n\nPOR FAVOR, ");
				
				}else {																				// SENÃO 
										
					plan.setCidadeDestino(tempString);													// define Cidade Destino na planilha
				
					System.out.println("Cidade destino definida: "+plan.getCidadeDestino()+"\n");
				
				}		
			
			}else {																				// SENÃO 
			
				System.out.print("Cidade não encontrada.\n\nPOR FAVOR, ");							// solicita nova tentativa
			
			}
			
	
		}while(!plan.consulta.confirmaCidadeX(tempString) || tempString.equals(plan.getCidadeOrigem()));		// executa laço enquanto não localizar a cidade na planilha OU a cidade destino for igual a origem
		
	
	}
	
	
	// informaModalidade() metodo de interação para receber modalidade de transporte do usuario
	// utiliza um menu com escolha de indice para cada modalidade
	// recebendo um indice valido, executa o metodo imprimeConsultaPorTrecho() que informa pro usuario o calculo final
	public void informaModalidade() {
		
		do {
			
			System.out.println("\nInforme a modalidade de transporte pelo índice:");					
		
			System.out.println("1 - Pequeno Porte(1t) \n2 - Médio Porte(4t) \n3 - Grande Porte(10t)");
		
			index = check.confirmaIndex();						//// variavel INDEX recebe o metodo confirmaIndex() da classe verificações	
		
			if (index > 3 || index < 1) {						// SE index informado for maior que 3 ou menor que 1
		
				System.out.println("Opção inválida.");				// informa erro e repete o laço
			
			}else {												// SENÃO
			
				plan.imprimeConsultaPorTrecho(index);				// envia o index para o metodoimprimeConsultaPorTrecho()
		
			}
		
		}while(index > 3 || index < 1);						// executa o laço DO WHILE o input do usuario for maior que 3 ou menor que 1

	}
	
	
	// interaçãoCadastroTransporte() é o segundo menu do programa
	// é acessado atraves da segunda opção do menu principal
	// ele executa a interação com usuario para cadastrar um novo transporte
	// possui dois laços DO WHILE um dentro do outro
	// o primeiro gera o loop dentro do cadastro do transporte
	// o segundo gera um loop para adicionar proximos destinos
	public void interacaoCadastroTransporte() {
		
		Carga carga = new Carga();			// criando um novo objeto CARGA no inicio de um novo transporte
		
		informaOrigem();					// metodo para receber cidade origem do usuario
	
		do {								// laço DO WHILE executa enquanto o index não for 9
		
			informaDestino();					// metodo para receber cidade destino do usuario
		
			//limpaTela();				
		
			do {								// laço DO WHILE executa enquanto index nao for 4			
			
				//limpaTela();
			
				System.out.println("ROTA: "+plan.getCidadeOrigem()+" >>> "+plan.getCidadeDestino()+"\n1 - Adicionar produto.\n2 - Remover Produto.\n3 - Listar Carga.\n4 - Finalizar esta carga.\n");
			
				index = check.confirmaIndex();			
			
				switch(index) {					// abre um SWITCH CASE com o menu de interação do cadastro de transporte
			
				case 1:							// case 1 (Adicionar Produto)
			
					//limpaTela();			
			
					carga.adicionarProduto(interacaoNovoProduto());			// adiciona na carga um novo produto atraves do metodo interaçãoNovoProduto()
				
					aguardaTecla();
				
					break;
			
				case 2:							// case 2 (Remover Produto)
				
					//limpaTela();
				
					interacaoRemoveProduto(carga);							// remove produto da CARGA com metodo interaçãoRemoveProduto()
				
					aguardaTecla();
				
					break;
				
				case 3:							// case 3 (Listar Carga)
				
					//limpaTela();
				
					carga.listarCarga(plan.cidadeDestino);			// executa o metodo listarCarga passando como parametro a cidade destino da carga atual
				
					aguardaTecla();
				
					break;
				
				case 4:							// case 4 (Finalizar esta carga)
				
					//limpaTela();
				
					System.out.println("TRECHO FINALIZADO.");
				
					finalizaTrecho(carga);			// executa o metodo finalizaTrecho com o parametro carga atual e imprimindo as informações do trecho atual
						
					break;								
				
				}
			
			}while(index != 4);					// executa DO WHILE enquanto o index nao for 4 (Finalizar esta carga)	
			
			index = destinoAdicional();			// executa interaçao destinoAdicional() que pergunta se há mais um destino para cadastrar ou pode finalizar a carga
												
		}while(index!=9);						// executa DO WHILE enquanto o index nao for 9 (retorno do metodo destinoAdicional
		
		carga.listarCarga(null);				// listarCarga() com parametro NULL irá listar toda a carga registrada mesmo com destinos diferentes
		
		rg.adicionaRegistro(finalizaTransporte(carga));		// passa o metodo finalizaTransporte() como parametro para adicionaRegistro() para inserir carga finalizada no registro de carga
		
		rg.imprimeUltimoRegistro();				// imprime na tela a carga registrada com informações adicionais
		
		aguardaTecla();
		
	}
	
	
	// interaçãoNovoProduto() metodo para solicitar do usuario os atributos do produto
	public Produtos interacaoNovoProduto() {
		
		Produtos p = new Produtos();					// instancia um novo objeto do tipo Produto
		
		System.out.println("INFORME O NOME DO PRODUTO:");
		
		p.setNome(sc.nextLine());						// atribui o nome do produto informado pelo usuario
		
		do {											// abre laço DO WHILE da quantidade
		
			System.out.println("INFORME A QUANTIDADE:");
		
			try {										// TRY CATCH para tratar Exception em caso de input diferente de INTEIRO
		
				p.setQuantidade(Integer.parseInt(sc.next()));				// atribui a quantidade informada pelo usuario
		
			}catch (NumberFormatException n) {}			
		
			if(p.getQuantidade() <= 0) {				// SE o usuario inserir quantidade igual ou menor que zero
			
				System.out.print("Quantidade inválida.\nPOR FAVOR, ");				// informa quantidade invalida
		
			}
		
		}while(p.getQuantidade() <= 0);					// laço DO WHILE para repetir em caso de  produto com quantidade igual ou menor que zero
		
		sc.nextLine();    // scanner para fechar o sc.next anterior
		
		do {											// abre laço DO WHILE do peso do produto
		
			System.out.println("Informe o peso(kg) unitário do produto:");
		
			try {											// TRY CATCH para tratar Exception em caso de input diferente de DOUBLE ou INT
		
				p.setPeso(check.checaVirgula(sc.nextLine()));			// atribui o input do usuario na atributo PESO, passando pelo metodo checaVirgula para converter virgula em ponto
		
			}catch(NumberFormatException n) {}
		
			if(p.getPeso() <= 0) {							// SE usuario inserir peso igual ou menor que zero
			
				System.out.print("Peso inválido.\nPOR FAVOR, ");		// informa peso invalido
			
			}
		
		}while(p.getPeso() <= 0);						// laço DO WHILE para repetir em caso de peso igual ou menor que zero
		
		p.setOrigem(plan.getCidadeOrigem());			// insere a Cidade Origem informada no atributo ORIGEM do produto
		
		p.setDestino(plan.getCidadeDestino());			// insere a cidade Destino informada no atributo DESTINO do produto
		
		return p;										// retorna o produto finalizado para ser adicionado ao ArrayList da Carga
	}
	
	
	// interaçãoRemoveProduto() metodo para remover produto do ArrayList de Carga
	public void interacaoRemoveProduto(Carga carga) {
		
		carga.listarCarga(null);   // imprime na tela a carga atual com ID dos produtos
		
		System.out.println("\nInforme o índice do produto que deseja remover:");
		
		do {
			index = check.confirmaIndex();
			
			if(index <= 0 || index > carga.size()) {
			
				System.out.println("Opção inválida");
			
			}
						
		}while(index <= 0 || index > carga.size());
		carga.removerProduto(index);
				// executa o metodo removerProduto da classe carga, passa o index como parametro
		
		System.out.println("Produto removido!\n");
	}
	
	
	// destinoAdicional() metodo para questionar se há algum destino adicional a ser adicionado a carga
	// Se o usuario confirmar, retorna 1, resultando na repetição do laço de cadastro de transporte
	// Se o usuario recusar retornará 9 e encerrará o laço de cadastro de transporte
	public int destinoAdicional() {
	
		System.out.println("Deseja adicionar mais um destino? (Y/N)");
		
		do{															// laço DO WHILE 
		
			confirm = check.converteMaiusculo(sc.next()).charAt(0);		// atribui à variavel CONFIRM o input do usuário passando pelo metodo converteMaiusculo
		
			if(confirm != 'Y' && confirm != 'N') {						//SE o input do usuario não for Y e não for N
		
				System.out.println("Digite uma opção válida. (Y/N)");		// informa opção invalida
		
			}
		
		}while(confirm != 'Y' && confirm != 'N');					// enquanto o input do usuario não for Y e não for N
		
		sc.nextLine();
		
		if(confirm == 'Y') {							// SE input for Y retorna 1
		
			return 1;
		
		}else {											// SENÃO retorna 9 e encerra o pedido
		
			limpaTela();
		
			System.out.println("PEDIDO FINALIZADO.");
		
			return 9;
		
		}
	
	}
	
	
	// finalizaTrecho() recebe objeto do Tipo Carga como parametro
	// o metodo é executado ao finalizar o cadastro de um trecho
	// ele executa o metodo divideCargaPorte() para calcular o numero de caminhões necessários
	// após, imprime na tela as informações sobre o trecho finalizado
	public void finalizaTrecho(Carga carga) {
		
		plan.divideCargaPorte(carga.pesoTotal(plan.cidadeDestino));   // metodo divideCargaPorte recebendo o calculo do metodo pesoTotal 
		
		System.out.println("\nDe: "+plan.cidadeOrigem+" para: "+plan.cidadeDestino);			// imprime Cidade Origem e Destino
		
		System.out.println("Distância a ser percorrida: "+plan.consultaDistanciaNoCSV()+" km");		// imprime a distancia do trecho atraves da consultaDistanciaNoCSV()
		
		System.out.println("Para transporte dos produtos:");
		
		carga.listarCarga(plan.cidadeDestino);														// lista os produtos cadastrados nesse trecho
		
		System.out.println(formatarQtdCaminhao()+", de forma a resultar no menor custo de transporte por km rodado.");		// executa metodo formatarQtdCaminhão() para formatar a mensagem conforme a quantidade de caminhões necessária
		
		System.out.println("O valor total do transporte dos itens é R$"+check.formatoDecimal(plan.calculoPesoPorDestino(carga, plan.getCidadeDestino()))+".");  // informa valor total do trecho 
		
		System.out.println("R$"+check.formatoDecimal(plan.calculaCustoUnitarioProduto(carga.quantidadeUnitaria(), plan.calculoCustoTotalFinal(carga.pesoTotal(plan.cidadeDestino))))+" é o custo unitário médio.\n");	// informa custo medio por quantidade
	
	}
	
	
	// finalizaTransporte() recebe objeto do tipo Carga como parametro
	// metodo semelhante ao finalizaTrecho()
	// porém com duas diferenças
	// utilizado um objeto StringBuilder para formar uma unica string do texto que será enviada para o RegistroCarga
	// e possui informações adicionais requisitadas ao finalizar o transporte geral
	// OBS esse metodo está uma bagunça e contem erro. Não foi finalizado e muito menos refatorado.
	public String finalizaTransporte(Carga carga) {


		    StringBuilder sb = new StringBuilder();				// instancia do objeto classe StringBuilder;
		    
		    plan.divideCargaPorte(carga.pesoTotal(null));		// executa divideCargaPorte() com parametro NULL para calcular TODA a carga
		   
		    ArrayList<String> destinosUnicos = carga.identificaDestinosCarga(carga.carga);		// declaro um ArrayList de String com destinos unicos. Recebe o retorno do metodo identificaDestinosCarga()
		    
		    sb.append("\nRota: ").append(plan.cidadeOrigem);		// começa-se a utilizar a função append do StringBuilder para formar a String
		   
		    for(int i = 0; i < destinosUnicos.size(); i++) {		// laço FOR para percorrer o ArrayList dos destinosUnicos
		   
		    	sb.append(">>>").append(destinosUnicos.get(i));			// cada posição formata um novo destino na String
		  
		    }
		   
		    sb.append("\n");
		   
		    sb.append("Distância a ser percorrida: ").append(plan.consultaDistanciaNoCSV()).append(" km\n");        
		   
		    sb.append("Para transporte dos produtos:\n");
		   
		    for(int i = 0; i < carga.size(); i++) {						// laço FOR para percorrer a lista de produtos da Carga
		    
		    	sb.append(carga.carga.get(i).toString()).append("\n");		// cada posição retorna um produto pelo index
		   
		    }
		   
		    sb.append(formatarQtdCaminhao()).append(", de forma a resultar no menor custo de transporte por km rodado.\n");
		   
		    sb.append("O valor total do transporte dos itens é R$").append(check.formatoDecimal(plan.calculoCustoTotalFinal(carga.pesoTotal(plan.cidadeDestino)))).append("\n");  // executa calculoCustoTotalFinal() com parametro do peso total do destino Final e formata em 2 casas apos a virgula
		  
		    sb.append("O custo médio por km é R$").append(check.formatoDecimal(plan.calculaCustoMedioPorKm(plan.calculoCustoTotalFinal(carga.pesoTotal(plan.cidadeDestino))))).append("\n");  // executa calculaCustoMedioPorKM() com resultado de calculoCustoTotalFinal() e formata em 2 casas apos a virgula
		  
		    sb.append("O custo médio por tipo de produto é R$").append(check.formatoDecimal(carga.custoMedioPorProduto(plan.calculoCustoTotalFinal(carga.pesoTotal(plan.cidadeDestino))))).append("\n");	// executa custoMedioPorProduto() com resultado de calculoCustoTotalFinal() e formata em 2 casas apos a virgula
		  
		    sb.append("O número total de veículos deslocados é ").append((int)plan.getGrandePorte(2)+plan.getMedioPorte(2)+plan.getPequenoPorte(2)).append(".\n");  // soma o total de veiculos de getGrandePorte,getMedioPorte e getPequenoPorte 
		  
		    sb.append("O total de itens transportados é: ").append(carga.quantidadeTotalProdutos(null)).append(" produtos.\n");  // executa metodo quantidadeTotalProdutos com parametro null para retornar toda a carga
		  
		    sb.append("O custo total por trecho é: \n");
		   
		    for(int i = 0; i < destinosUnicos.size(); i++) {		// laço FOR que percorre o arrayList de destinos unicos para imprimir todos os trechos do transporte
		    	
		    	// SE i = 0 é condição para ser executado só na primeira volta do laço. Pegando a cidadeOrigem e executando custoTotalPorTrecho com resultado de calculoCustoTotalFinal() até o primeiro destino
		    	if(i == 0) {sb.append(plan.cidadeOrigem).append(">>>").append(destinosUnicos.get(i)).append(" = R$").append(check.formatoDecimal(plan.custoTotalPorTrecho(plan.calculoCustoTotalFinal(carga.pesoTotal(null)),plan.cidadeOrigem,plan.cidadeOrigem,destinosUnicos.get(i)))).append(".\n");
		        
		        }else {	
		        	// SENÃO é executado sempre após o primeiro laço. Pegando o destino anterior e executando o mesmo codigo porém com o proximo destino.
		        	sb.append(destinosUnicos.get(i-1)).append(">>>").append(destinosUnicos.get(i)).append(" = R$").append(check.formatoDecimal(plan.custoTotalPorTrecho(plan.calculoCustoTotalFinal(carga.pesoTotal(null)),plan.cidadeOrigem,destinosUnicos.get(i-1),destinosUnicos.get(i)))).append(".\n");
		      
		        }
		   
		    }
		  
		    return sb.toString();			// retorna o toString do StringBuilder contendo toda a informação desse metodo em uma unica String que será armazenada no RegistroCarga
		
	}
	

	// formatarQtdCaminhao() metodo que com base na quantidade de caminhões por modalidade
	// define a melhor String para apresentar ao usuário.
	// Utiliza uma arvore de Condicional IF,ELSE IF e ELSE
	public String formatarQtdCaminhao() {
		
		// 1. SE houver caminhões de grande,médio e pequeno porte.
		if(plan.grandePorte[2]>0 && plan.medioPorte[2]>0 && plan.pequenoPorte[2]>0) {      
			
			// retorna formatado caminhões grande, médio e pequeno
			return "Será necessário utilizar "+(int)plan.grandePorte[2]+" caminh"+formataCaminhao(plan.grandePorte[2])+" de grande porte, "+(int)plan.medioPorte[2]+" caminh"+formataCaminhao(plan.medioPorte[2])+" de médio porte e "+(int)plan.pequenoPorte[2]+" caminh"+formataCaminhao(plan.pequenoPorte[2])+" de pequeno porte";
		
		}
		// 2. SENÃO SE houver caminhões de grande porte
		else if(plan.grandePorte[2]>0) {	
			
			// 2.1 SE houver caminhões de médio porte 
			if(plan.medioPorte[2]>0) {
				
				// retorna formatado caminhões grande e médio
				return "Será necessário utilizar "+(int)plan.grandePorte[2]+" caminh"+formataCaminhao(plan.grandePorte[2])+" de grande porte e "+(int)plan.medioPorte[2]+" caminh"+formataCaminhao(plan.medioPorte[2])+" de médio porte";
		
			}
			// 2.2 SENÃO SE houver caminhões de pequeno porte
			else if(plan.pequenoPorte[2]>0){
				
				// retorna formatado caminhões grande e pequeno
				return "Será necessário utilizar "+(int)plan.grandePorte[2]+" caminh"+formataCaminhao(plan.grandePorte[2])+" de grande porte e "+(int)plan.pequenoPorte[2]+" caminh"+formataCaminhao(plan.pequenoPorte[2])+" de pequeno porte";
		
			}
			// 2.3 SENÃO 
			else {
				
				// retorna formatado caminhões grande
				return "Será necessário utilizar "+(int)plan.grandePorte[2]+" caminh"+formataCaminhao(plan.grandePorte[2])+" de grande porte";
		
			}
		
		}
		// 3. SENÃO SE houver caminhões de médio porte
		else if(plan.medioPorte[2]>0) {
			
			// 3.1 SE houver caminhões de pequeno porte
			if(plan.pequenoPorte[2]>0) {
				
				// retorna formatado caminhões médio e pequeno
				return "Será necessário utilizar "+(int)plan.medioPorte[2]+" caminh"+formataCaminhao(plan.medioPorte[2])+" de médio porte e "+(int)plan.pequenoPorte[2]+" caminh"+formataCaminhao(plan.pequenoPorte[2])+" de pequeno porte";
			
			}
			//3.2 SENÃO	
			else {
				
				// retorna formatado caminhões médio 
				return "Será necessário utilizar "+(int)plan.medioPorte[2]+" caminh"+formataCaminhao(plan.medioPorte[2])+" de médio porte";
			
			}
		
		}
		// 4. SENÃO
		else {
			
			// retorna formatado caminhões pequeno
			return "Será necessário utilizar "+(int)plan.pequenoPorte[2]+" caminh"+formataCaminhao(plan.pequenoPorte[2])+" de pequeno porte";
		}
		
	}
	
	
	// formataCaminhão() recebe como parametro de entrada a QUANTIDADE de caminhões 
	// das variáveis pequenoPorte[2],medioPorte[2],grandePorte[2]
	// e retorna a string no singular ou no plural
	public String formataCaminhao(double x) {
		
		if(x > 1) {				// se QUANTIDADE for maior que 1
	
			return "ões";			// retorna o plural de caminhões
	
		}else {					// SENÃO
	
			return "ão";			// retorna no singular
			
		}
	
	}
	
	
	// limpaTela() executa um laço FOR 50 vezes imprimindo uma linha vazia na tela
	// para dar a sensação de limpar a tela do Console
	public static void limpaTela() {  
		
		for (int i = 0; i < 50; ++i) System.out.println();
	
	} 
	
	
	// aguardaTecla() um metodo que quando executado pausa o programa até que receba qualquer input do usuario
	public static void aguardaTecla() {
		
		System.out.println("\nPressione uma tecla para retornar.");
		
		try {
		
			System.in.read();
		
		} catch (IOException e) {
		
			e.printStackTrace();
		
		}
	}
}
