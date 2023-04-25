package app;

/*
 *  a classe Programa � a responsavel por operar toda a estrutura do codigo
 *  e realizar toda a intera��o com o usu�rio
 */



/*******************BIBLIOTECAS******************/
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



public class Programa {
	
	/*******************VARIAVEIS******************/
	public int index;								// variavel index do tipo INT � usada para armazenar a op��o de escolha no menu
	
	public char confirm;							// variavel confirm do tipo CHAR armazena op��o do menu em que � necess�rio escolher entre Y e N (Yes/No)
	
	public String tempString;						// variavel tempString � uma String que armazena temporariamente o nome da cidade informado pelo usu�rio
	
	Scanner sc = new Scanner(System.in);			// criando instancia do Scanner com parametro System.in
	Planilha plan = new Planilha();					// criando instancia da Planinha
	Verificacoes check = new Verificacoes();		// criando instancia de Verifica��es
	RegistroCarga rg = new RegistroCarga();			// criando instancia de RegistroCarga
	
	
	/*******************METODOS******************/
	
	
	// menu() � o menu inicial do programa
	// � utilizado um switch case com escolha das op��es informadas no console
	// dentro de um la�o DO WHILE que s� se encerra quando o usuario escolhe sair do programa
	public void menu() {
	
		do {																		// abertura do la�o DO WHILE
		
			limpaTela();																// limpaTela() limpa a tela do console cada vez que o menu � iniciado
			
			System.out.println("Selecione a op��o desejada com base no �ndice.");		// impress�o do menu de op��es com base no indice
			
			System.out.println("1 - Consulta de Trechos x Modalidade.");
			
			System.out.println("2 - Cadastrar transporte.");
			
			System.out.println("3 - Dados Estat�sticos.");
			
			System.out.println("0 - Sair.");
			
			index = check.confirmaIndex();				// variavel INDEX recebe o metodo confirmaIndex() da classe verifica��es
			
			switch(index) {								// abertura do SWITCH CASE
			
				case 1: 								// case 1 (Consulta de Trechos x Modalidade)
																					
					limpaTela();																
					
					plan.consulta.mostraCidadesAtendidas();		// executa mostraCidadesAtendidas() da classe LeituraCSV
					
					informaOrigem();							// executa informaOrigem() para receber do usu�rio a cidade de origem
					
					informaDestino();							// executa informaDestino() para receber do usu�rio a cidade destino
					
					informaModalidade();						// executa informaModalidade() para receber do usuario a modalidade escolhida
					
					aguardaTecla();								// aguardaTecla() pausa o programa at� o usuario digitar alguma tecla
					
					break;										// termina o caso e recome�a o menu inicial
				
				case 2: 								// case 2 (Cadastrar Transporte)												
					
					//limpaTela();						
					
					interacaoCadastroTransporte();			// executa a interacaoCadastroTransporte() contendo um menu posterior
					
					break;									
				
				case 3: 								// case 3 ( Dados Estat�sticos)
				
					limpaTela();
					
					rg.imprimeRegistro(); 				// executa o metodo imprimeRegistro() da clase RegistroCarga para emitir o hist�rico de transportes
					
					aguardaTecla();
					
					break;
				
				case 0:									// case 0 ( Encerra o programa)    variavel index recebe o valor 0, resultando no encerramento do la�o DO WHILE
				
					limpaTela();
					
					System.out.println("\nPROGRAMA ENCERRADO.");
					
					break;
			
			}
		
		} while(index != 0);
		
	}
	
	
	// informaOrigem() metodo de intera��o com o usu�rio para receber a cidade de Origem
	// � utilizado metodos de checar acento, converter para maiusculo para formatar a string da mesma maneira que o arquivo CSV fornecido
	// tamb�m dentro de um DO WHILE para executar enquanto o INPUT nao corresponder a uma cidade do arquivo CSV
	public void informaOrigem() {
		
		do {																			// abre la�o DO WHILE
		
			System.out.println("INFORME A CIDADE DE ORIGEM:");							
			
			tempString = check.checarAcento((check.converteMaiusculo(sc.nextLine())));		// armazena na tempString a entrada do scanner, passando pelos metodos de converteMaiusculo() e checarAcento()
			
			if(plan.consulta.confirmaCidadeX(tempString)) {									// SE localizar a cidade informada no arquivo CSV o metodo confirmaCidadeX retorna TRUE
			
				plan.setCidadeOrigem(tempString);												// define Cidade Origem na planilha
				
				System.out.println("Cidade origem definida: "+tempString+"\n");					
			
			}else {																			// SEN�O 
							
				System.out.print("Cidade n�o encontrada.\n\nPOR FAVOR, ");					// solicita nova tentiva
			
			}
		
		}while(!plan.consulta.confirmaCidadeX(tempString));									// executa la�o enquanto o metodo confirmaCidadeX() retornar FALSE
	
	}
	
	
	// informaDestino() metodo de intera��o com o usu�rio para receber a cidade destno
	// � utilizado metodos de checar acento, converter para maiusculo para formatar a string da mesma maneira que o arquivo CSV fornecido
	// tamb�m dentro de um DO WHILE para executar enquanto o INPUT nao corresponder a uma cidade do arquivo CSV
	public void informaDestino() {
	
		do {																			// abre la�o DO WHILE
		
			System.out.println("INFORME A CIDADE DESTINO:");								
		
			tempString = check.checarAcento((check.converteMaiusculo(sc.nextLine())));			// armazena na tempString a entrada do scanner, passando pelos metodos de converteMaiusculo() e checarAcento()
		
			if(plan.consulta.confirmaCidadeX(tempString)) {										// SE localizar a cidade informada no arquivo CSV o metodo confirmaCidadeX retorna TRUE
				
				if(tempString.equals(plan.getCidadeOrigem())){										// SE a cidade destino for igual a cidade origem j� definida, alerta o usuario e recome�a o la�o
				
					System.out.print("Cidade destino n�o pode ser igual a origem.\n\nPOR FAVOR, ");
				
				}else {																				// SEN�O 
										
					plan.setCidadeDestino(tempString);													// define Cidade Destino na planilha
				
					System.out.println("Cidade destino definida: "+plan.getCidadeDestino()+"\n");
				
				}		
			
			}else {																				// SEN�O 
			
				System.out.print("Cidade n�o encontrada.\n\nPOR FAVOR, ");							// solicita nova tentativa
			
			}
			
	
		}while(!plan.consulta.confirmaCidadeX(tempString) || tempString.equals(plan.getCidadeOrigem()));		// executa la�o enquanto n�o localizar a cidade na planilha OU a cidade destino for igual a origem
		
	
	}
	
	
	// informaModalidade() metodo de intera��o para receber modalidade de transporte do usuario
	// utiliza um menu com escolha de indice para cada modalidade
	// recebendo um indice valido, executa o metodo imprimeConsultaPorTrecho() que informa pro usuario o calculo final
	public void informaModalidade() {
		
		do {
			
			System.out.println("\nInforme a modalidade de transporte pelo �ndice:");					
		
			System.out.println("1 - Pequeno Porte(1t) \n2 - M�dio Porte(4t) \n3 - Grande Porte(10t)");
		
			index = check.confirmaIndex();						//// variavel INDEX recebe o metodo confirmaIndex() da classe verifica��es	
		
			if (index > 3 || index < 1) {						// SE index informado for maior que 3 ou menor que 1
		
				System.out.println("Op��o inv�lida.");				// informa erro e repete o la�o
			
			}else {												// SEN�O
			
				plan.imprimeConsultaPorTrecho(index);				// envia o index para o metodoimprimeConsultaPorTrecho()
		
			}
		
		}while(index > 3 || index < 1);						// executa o la�o DO WHILE o input do usuario for maior que 3 ou menor que 1

	}
	
	
	// intera��oCadastroTransporte() � o segundo menu do programa
	// � acessado atraves da segunda op��o do menu principal
	// ele executa a intera��o com usuario para cadastrar um novo transporte
	// possui dois la�os DO WHILE um dentro do outro
	// o primeiro gera o loop dentro do cadastro do transporte
	// o segundo gera um loop para adicionar proximos destinos
	public void interacaoCadastroTransporte() {
		
		Carga carga = new Carga();			// criando um novo objeto CARGA no inicio de um novo transporte
		
		informaOrigem();					// metodo para receber cidade origem do usuario
	
		do {								// la�o DO WHILE executa enquanto o index n�o for 9
		
			informaDestino();					// metodo para receber cidade destino do usuario
		
			//limpaTela();				
		
			do {								// la�o DO WHILE executa enquanto index nao for 4			
			
				//limpaTela();
			
				System.out.println("ROTA: "+plan.getCidadeOrigem()+" >>> "+plan.getCidadeDestino()+"\n1 - Adicionar produto.\n2 - Remover Produto.\n3 - Listar Carga.\n4 - Finalizar esta carga.\n");
			
				index = check.confirmaIndex();			
			
				switch(index) {					// abre um SWITCH CASE com o menu de intera��o do cadastro de transporte
			
				case 1:							// case 1 (Adicionar Produto)
			
					//limpaTela();			
			
					carga.adicionarProduto(interacaoNovoProduto());			// adiciona na carga um novo produto atraves do metodo intera��oNovoProduto()
				
					aguardaTecla();
				
					break;
			
				case 2:							// case 2 (Remover Produto)
				
					//limpaTela();
				
					interacaoRemoveProduto(carga);							// remove produto da CARGA com metodo intera��oRemoveProduto()
				
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
				
					finalizaTrecho(carga);			// executa o metodo finalizaTrecho com o parametro carga atual e imprimindo as informa��es do trecho atual
						
					break;								
				
				}
			
			}while(index != 4);					// executa DO WHILE enquanto o index nao for 4 (Finalizar esta carga)	
			
			index = destinoAdicional();			// executa intera�ao destinoAdicional() que pergunta se h� mais um destino para cadastrar ou pode finalizar a carga
												
		}while(index!=9);						// executa DO WHILE enquanto o index nao for 9 (retorno do metodo destinoAdicional
		
		carga.listarCarga(null);				// listarCarga() com parametro NULL ir� listar toda a carga registrada mesmo com destinos diferentes
		
		rg.adicionaRegistro(finalizaTransporte(carga));		// passa o metodo finalizaTransporte() como parametro para adicionaRegistro() para inserir carga finalizada no registro de carga
		
		rg.imprimeUltimoRegistro();				// imprime na tela a carga registrada com informa��es adicionais
		
		aguardaTecla();
		
	}
	
	
	// intera��oNovoProduto() metodo para solicitar do usuario os atributos do produto
	public Produtos interacaoNovoProduto() {
		
		Produtos p = new Produtos();					// instancia um novo objeto do tipo Produto
		
		System.out.println("INFORME O NOME DO PRODUTO:");
		
		p.setNome(sc.nextLine());						// atribui o nome do produto informado pelo usuario
		
		do {											// abre la�o DO WHILE da quantidade
		
			System.out.println("INFORME A QUANTIDADE:");
		
			try {										// TRY CATCH para tratar Exception em caso de input diferente de INTEIRO
		
				p.setQuantidade(Integer.parseInt(sc.next()));				// atribui a quantidade informada pelo usuario
		
			}catch (NumberFormatException n) {}			
		
			if(p.getQuantidade() <= 0) {				// SE o usuario inserir quantidade igual ou menor que zero
			
				System.out.print("Quantidade inv�lida.\nPOR FAVOR, ");				// informa quantidade invalida
		
			}
		
		}while(p.getQuantidade() <= 0);					// la�o DO WHILE para repetir em caso de  produto com quantidade igual ou menor que zero
		
		sc.nextLine();    // scanner para fechar o sc.next anterior
		
		do {											// abre la�o DO WHILE do peso do produto
		
			System.out.println("Informe o peso(kg) unit�rio do produto:");
		
			try {											// TRY CATCH para tratar Exception em caso de input diferente de DOUBLE ou INT
		
				p.setPeso(check.checaVirgula(sc.nextLine()));			// atribui o input do usuario na atributo PESO, passando pelo metodo checaVirgula para converter virgula em ponto
		
			}catch(NumberFormatException n) {}
		
			if(p.getPeso() <= 0) {							// SE usuario inserir peso igual ou menor que zero
			
				System.out.print("Peso inv�lido.\nPOR FAVOR, ");		// informa peso invalido
			
			}
		
		}while(p.getPeso() <= 0);						// la�o DO WHILE para repetir em caso de peso igual ou menor que zero
		
		p.setOrigem(plan.getCidadeOrigem());			// insere a Cidade Origem informada no atributo ORIGEM do produto
		
		p.setDestino(plan.getCidadeDestino());			// insere a cidade Destino informada no atributo DESTINO do produto
		
		return p;										// retorna o produto finalizado para ser adicionado ao ArrayList da Carga
	}
	
	
	// intera��oRemoveProduto() metodo para remover produto do ArrayList de Carga
	public void interacaoRemoveProduto(Carga carga) {
		
		carga.listarCarga(null);   // imprime na tela a carga atual com ID dos produtos
		
		System.out.println("\nInforme o �ndice do produto que deseja remover:");
		
		do {
			index = check.confirmaIndex();
			
			if(index <= 0 || index > carga.size()) {
			
				System.out.println("Op��o inv�lida");
			
			}
						
		}while(index <= 0 || index > carga.size());
		carga.removerProduto(index);
				// executa o metodo removerProduto da classe carga, passa o index como parametro
		
		System.out.println("Produto removido!\n");
	}
	
	
	// destinoAdicional() metodo para questionar se h� algum destino adicional a ser adicionado a carga
	// Se o usuario confirmar, retorna 1, resultando na repeti��o do la�o de cadastro de transporte
	// Se o usuario recusar retornar� 9 e encerrar� o la�o de cadastro de transporte
	public int destinoAdicional() {
	
		System.out.println("Deseja adicionar mais um destino? (Y/N)");
		
		do{															// la�o DO WHILE 
		
			confirm = check.converteMaiusculo(sc.next()).charAt(0);		// atribui � variavel CONFIRM o input do usu�rio passando pelo metodo converteMaiusculo
		
			if(confirm != 'Y' && confirm != 'N') {						//SE o input do usuario n�o for Y e n�o for N
		
				System.out.println("Digite uma op��o v�lida. (Y/N)");		// informa op��o invalida
		
			}
		
		}while(confirm != 'Y' && confirm != 'N');					// enquanto o input do usuario n�o for Y e n�o for N
		
		sc.nextLine();
		
		if(confirm == 'Y') {							// SE input for Y retorna 1
		
			return 1;
		
		}else {											// SEN�O retorna 9 e encerra o pedido
		
			limpaTela();
		
			System.out.println("PEDIDO FINALIZADO.");
		
			return 9;
		
		}
	
	}
	
	
	// finalizaTrecho() recebe objeto do Tipo Carga como parametro
	// o metodo � executado ao finalizar o cadastro de um trecho
	// ele executa o metodo divideCargaPorte() para calcular o numero de caminh�es necess�rios
	// ap�s, imprime na tela as informa��es sobre o trecho finalizado
	public void finalizaTrecho(Carga carga) {
		
		plan.divideCargaPorte(carga.pesoTotal(plan.cidadeDestino));   // metodo divideCargaPorte recebendo o calculo do metodo pesoTotal 
		
		System.out.println("\nDe: "+plan.cidadeOrigem+" para: "+plan.cidadeDestino);			// imprime Cidade Origem e Destino
		
		System.out.println("Dist�ncia a ser percorrida: "+plan.consultaDistanciaNoCSV()+" km");		// imprime a distancia do trecho atraves da consultaDistanciaNoCSV()
		
		System.out.println("Para transporte dos produtos:");
		
		carga.listarCarga(plan.cidadeDestino);														// lista os produtos cadastrados nesse trecho
		
		System.out.println(formatarQtdCaminhao()+", de forma a resultar no menor custo de transporte por km rodado.");		// executa metodo formatarQtdCaminh�o() para formatar a mensagem conforme a quantidade de caminh�es necess�ria
		
		System.out.println("O valor total do transporte dos itens � R$"+check.formatoDecimal(plan.calculoPesoPorDestino(carga, plan.getCidadeDestino()))+".");  // informa valor total do trecho 
		
		System.out.println("R$"+check.formatoDecimal(plan.calculaCustoUnitarioProduto(carga.quantidadeUnitaria(), plan.calculoCustoTotalFinal(carga.pesoTotal(plan.cidadeDestino))))+" � o custo unit�rio m�dio.\n");	// informa custo medio por quantidade
	
	}
	
	
	// finalizaTransporte() recebe objeto do tipo Carga como parametro
	// metodo semelhante ao finalizaTrecho()
	// por�m com duas diferen�as
	// utilizado um objeto StringBuilder para formar uma unica string do texto que ser� enviada para o RegistroCarga
	// e possui informa��es adicionais requisitadas ao finalizar o transporte geral
	// OBS esse metodo est� uma bagun�a e contem erro. N�o foi finalizado e muito menos refatorado.
	public String finalizaTransporte(Carga carga) {


		    StringBuilder sb = new StringBuilder();				// instancia do objeto classe StringBuilder;
		    
		    plan.divideCargaPorte(carga.pesoTotal(null));		// executa divideCargaPorte() com parametro NULL para calcular TODA a carga
		   
		    ArrayList<String> destinosUnicos = carga.identificaDestinosCarga(carga.carga);		// declaro um ArrayList de String com destinos unicos. Recebe o retorno do metodo identificaDestinosCarga()
		    
		    sb.append("\nRota: ").append(plan.cidadeOrigem);		// come�a-se a utilizar a fun��o append do StringBuilder para formar a String
		   
		    for(int i = 0; i < destinosUnicos.size(); i++) {		// la�o FOR para percorrer o ArrayList dos destinosUnicos
		   
		    	sb.append(">>>").append(destinosUnicos.get(i));			// cada posi��o formata um novo destino na String
		  
		    }
		   
		    sb.append("\n");
		   
		    sb.append("Dist�ncia a ser percorrida: ").append(plan.consultaDistanciaNoCSV()).append(" km\n");        
		   
		    sb.append("Para transporte dos produtos:\n");
		   
		    for(int i = 0; i < carga.size(); i++) {						// la�o FOR para percorrer a lista de produtos da Carga
		    
		    	sb.append(carga.carga.get(i).toString()).append("\n");		// cada posi��o retorna um produto pelo index
		   
		    }
		   
		    sb.append(formatarQtdCaminhao()).append(", de forma a resultar no menor custo de transporte por km rodado.\n");
		   
		    sb.append("O valor total do transporte dos itens � R$").append(check.formatoDecimal(plan.calculoCustoTotalFinal(carga.pesoTotal(plan.cidadeDestino)))).append("\n");  // executa calculoCustoTotalFinal() com parametro do peso total do destino Final e formata em 2 casas apos a virgula
		  
		    sb.append("O custo m�dio por km � R$").append(check.formatoDecimal(plan.calculaCustoMedioPorKm(plan.calculoCustoTotalFinal(carga.pesoTotal(plan.cidadeDestino))))).append("\n");  // executa calculaCustoMedioPorKM() com resultado de calculoCustoTotalFinal() e formata em 2 casas apos a virgula
		  
		    sb.append("O custo m�dio por tipo de produto � R$").append(check.formatoDecimal(carga.custoMedioPorProduto(plan.calculoCustoTotalFinal(carga.pesoTotal(plan.cidadeDestino))))).append("\n");	// executa custoMedioPorProduto() com resultado de calculoCustoTotalFinal() e formata em 2 casas apos a virgula
		  
		    sb.append("O n�mero total de ve�culos deslocados � ").append((int)plan.getGrandePorte(2)+plan.getMedioPorte(2)+plan.getPequenoPorte(2)).append(".\n");  // soma o total de veiculos de getGrandePorte,getMedioPorte e getPequenoPorte 
		  
		    sb.append("O total de itens transportados �: ").append(carga.quantidadeTotalProdutos(null)).append(" produtos.\n");  // executa metodo quantidadeTotalProdutos com parametro null para retornar toda a carga
		  
		    sb.append("O custo total por trecho �: \n");
		   
		    for(int i = 0; i < destinosUnicos.size(); i++) {		// la�o FOR que percorre o arrayList de destinos unicos para imprimir todos os trechos do transporte
		    	
		    	// SE i = 0 � condi��o para ser executado s� na primeira volta do la�o. Pegando a cidadeOrigem e executando custoTotalPorTrecho com resultado de calculoCustoTotalFinal() at� o primeiro destino
		    	if(i == 0) {sb.append(plan.cidadeOrigem).append(">>>").append(destinosUnicos.get(i)).append(" = R$").append(check.formatoDecimal(plan.custoTotalPorTrecho(plan.calculoCustoTotalFinal(carga.pesoTotal(null)),plan.cidadeOrigem,plan.cidadeOrigem,destinosUnicos.get(i)))).append(".\n");
		        
		        }else {	
		        	// SEN�O � executado sempre ap�s o primeiro la�o. Pegando o destino anterior e executando o mesmo codigo por�m com o proximo destino.
		        	sb.append(destinosUnicos.get(i-1)).append(">>>").append(destinosUnicos.get(i)).append(" = R$").append(check.formatoDecimal(plan.custoTotalPorTrecho(plan.calculoCustoTotalFinal(carga.pesoTotal(null)),plan.cidadeOrigem,destinosUnicos.get(i-1),destinosUnicos.get(i)))).append(".\n");
		      
		        }
		   
		    }
		  
		    return sb.toString();			// retorna o toString do StringBuilder contendo toda a informa��o desse metodo em uma unica String que ser� armazenada no RegistroCarga
		
	}
	

	// formatarQtdCaminhao() metodo que com base na quantidade de caminh�es por modalidade
	// define a melhor String para apresentar ao usu�rio.
	// Utiliza uma arvore de Condicional IF,ELSE IF e ELSE
	public String formatarQtdCaminhao() {
		
		// 1. SE houver caminh�es de grande,m�dio e pequeno porte.
		if(plan.grandePorte[2]>0 && plan.medioPorte[2]>0 && plan.pequenoPorte[2]>0) {      
			
			// retorna formatado caminh�es grande, m�dio e pequeno
			return "Ser� necess�rio utilizar "+(int)plan.grandePorte[2]+" caminh"+formataCaminhao(plan.grandePorte[2])+" de grande porte, "+(int)plan.medioPorte[2]+" caminh"+formataCaminhao(plan.medioPorte[2])+" de m�dio porte e "+(int)plan.pequenoPorte[2]+" caminh"+formataCaminhao(plan.pequenoPorte[2])+" de pequeno porte";
		
		}
		// 2. SEN�O SE houver caminh�es de grande porte
		else if(plan.grandePorte[2]>0) {	
			
			// 2.1 SE houver caminh�es de m�dio porte 
			if(plan.medioPorte[2]>0) {
				
				// retorna formatado caminh�es grande e m�dio
				return "Ser� necess�rio utilizar "+(int)plan.grandePorte[2]+" caminh"+formataCaminhao(plan.grandePorte[2])+" de grande porte e "+(int)plan.medioPorte[2]+" caminh"+formataCaminhao(plan.medioPorte[2])+" de m�dio porte";
		
			}
			// 2.2 SEN�O SE houver caminh�es de pequeno porte
			else if(plan.pequenoPorte[2]>0){
				
				// retorna formatado caminh�es grande e pequeno
				return "Ser� necess�rio utilizar "+(int)plan.grandePorte[2]+" caminh"+formataCaminhao(plan.grandePorte[2])+" de grande porte e "+(int)plan.pequenoPorte[2]+" caminh"+formataCaminhao(plan.pequenoPorte[2])+" de pequeno porte";
		
			}
			// 2.3 SEN�O 
			else {
				
				// retorna formatado caminh�es grande
				return "Ser� necess�rio utilizar "+(int)plan.grandePorte[2]+" caminh"+formataCaminhao(plan.grandePorte[2])+" de grande porte";
		
			}
		
		}
		// 3. SEN�O SE houver caminh�es de m�dio porte
		else if(plan.medioPorte[2]>0) {
			
			// 3.1 SE houver caminh�es de pequeno porte
			if(plan.pequenoPorte[2]>0) {
				
				// retorna formatado caminh�es m�dio e pequeno
				return "Ser� necess�rio utilizar "+(int)plan.medioPorte[2]+" caminh"+formataCaminhao(plan.medioPorte[2])+" de m�dio porte e "+(int)plan.pequenoPorte[2]+" caminh"+formataCaminhao(plan.pequenoPorte[2])+" de pequeno porte";
			
			}
			//3.2 SEN�O	
			else {
				
				// retorna formatado caminh�es m�dio 
				return "Ser� necess�rio utilizar "+(int)plan.medioPorte[2]+" caminh"+formataCaminhao(plan.medioPorte[2])+" de m�dio porte";
			
			}
		
		}
		// 4. SEN�O
		else {
			
			// retorna formatado caminh�es pequeno
			return "Ser� necess�rio utilizar "+(int)plan.pequenoPorte[2]+" caminh"+formataCaminhao(plan.pequenoPorte[2])+" de pequeno porte";
		}
		
	}
	
	
	// formataCaminh�o() recebe como parametro de entrada a QUANTIDADE de caminh�es 
	// das vari�veis pequenoPorte[2],medioPorte[2],grandePorte[2]
	// e retorna a string no singular ou no plural
	public String formataCaminhao(double x) {
		
		if(x > 1) {				// se QUANTIDADE for maior que 1
	
			return "�es";			// retorna o plural de caminh�es
	
		}else {					// SEN�O
	
			return "�o";			// retorna no singular
			
		}
	
	}
	
	
	// limpaTela() executa um la�o FOR 50 vezes imprimindo uma linha vazia na tela
	// para dar a sensa��o de limpar a tela do Console
	public static void limpaTela() {  
		
		for (int i = 0; i < 50; ++i) System.out.println();
	
	} 
	
	
	// aguardaTecla() um metodo que quando executado pausa o programa at� que receba qualquer input do usuario
	public static void aguardaTecla() {
		
		System.out.println("\nPressione uma tecla para retornar.");
		
		try {
		
			System.in.read();
		
		} catch (IOException e) {
		
			e.printStackTrace();
		
		}
	}
}
