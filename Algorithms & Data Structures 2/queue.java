class Fila{
	private Celula primeiro;
	private Celula ultimo;
	private int tamanho;

	public Fila(){
		primeiro = new Celula();
		ultimo = primeiro;
		tamanho = 0;
	}

	public void inserir(Presidente presidente){
		ultimo.prox = new Celula(presidente);
		ultimo = ultimo.prox;
		tamanho++;
	}

	public Presidente remover() throws Exception{
		if(primeiro == ultimo)
			throw new Exception("Erro!");

		Celula tmp = primeiro;
		primeiro = primeiro.prox;
		Presidente resp = primeiro.elemento;
		tmp.prox = null;
		tmp = null;
		tamanho--;

		return resp;
	}

	public int getTamanho(){
		return this.tamanho;
	}
}