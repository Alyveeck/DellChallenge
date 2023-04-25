package app;

/*
 * classe Produtos cria objetos do tipo produto
 * contendo todos os atributos necessarios
 */

public class Produtos {
	
	/*******************VARIAVEIS******************/
	public String nome;
	
	public int quantidade;
	
	public double peso;
   
	String origem,destino;
    
	
	/*******************GETTERS/SETTERS******************/
    public void setOrigem(String origem) {
		
    	this.origem = origem;
	
    }
    
    public String getDestino() {
		
    	return destino;
	
    }
	
    public void setDestino(String destino) {
		
    	this.destino = destino;
	
    }

	public void setNome(String nome) {
		
		this.nome = nome;
	
	}
	
	public int getQuantidade() {
		
		return quantidade;
	
	}
	
	public void setQuantidade(int quantidade) {
		
		this.quantidade = quantidade;
	
	}
	
	public double getPeso() {
		
		return peso;
	
	}
	
	public void setPeso(double peso) {
		
		this.peso = peso;
	
	}
	
	
	
	/*******************METODOS******************/
	
	@Override       // toString formatado para apresentar o produto na lista da carga
	public String toString() {
		
		return nome + " - Quantidade = " + quantidade + " - Peso Unitário = "+peso+"kg - Total = " + (peso*quantidade) +"kg - Destino = "+destino;
	
	}
    

	
	
}
