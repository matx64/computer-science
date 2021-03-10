class Lista{
	private Celula primeiro;
	private Celula ultimo;

	Lista(){
		primeiro = new Celula();
		ultimo = primeiro;
	}

	public Celula getPrimeiro(){
		return this.primeiro;
	}

	public Celula getUltimo(){
		return this.ultimo;
	}

	public void inserirInicio(Presidente presidente){
		Celula tmp = new Celula(presidente);
		tmp.prox = primeiro.prox;
		primeiro.prox = tmp;
		if(primeiro == ultimo){
			ultimo = tmp;
		}
		tmp = null;
	}

	public void inserirFim(Presidente presidente){
		ultimo.prox = new Celula(presidente);
		ultimo = ultimo.prox;
	}

	public void inserir(Presidente presidente, int pos) throws Exception{
		int tamanho = tamanho();
		if(pos < 0 || pos > tamanho){
			throw new Exception("Error!");
		}else if(pos == 0){
			inserirInicio(presidente);
		}else if(pos == tamanho){
			inserirFim(presidente);
		}else{
			Celula i = primeiro;
			for(int j = 0; j < pos; j++, i = i.prox);
			Celula tmp = new Celula(presidente);
			tmp.prox = i.prox;
			i.prox = tmp;
			tmp = i = null;
		}
	}

	public Presidente removerInicio() throws Exception{
		if(primeiro == ultimo)
			throw new Exception("Error!");

		Presidente resp = primeiro.prox.elemento;
      	primeiro.prox = primeiro.prox.prox;
		return resp;
	}

	public Presidente remover(int pos) throws Exception{
		Presidente elemento;
		int tamanho = tamanho();
		if(primeiro == ultimo || pos < 0 || pos >= tamanho){
			throw new Exception("Error!");
		}else if(pos == 0){
			elemento = removerInicio();
		}else if(pos == tamanho - 1){
			elemento = removerFim();
		}else{
			Celula i = primeiro;
			for(int j = 0; j < pos; j++, i = i.prox);
			Celula tmp = i.prox;
			elemento = tmp.elemento;
			i.prox = tmp.prox;
			tmp.prox = null;
			i = tmp = null;
		}

		return elemento;
	}

	public Presidente removerFim() throws Exception{
		if(primeiro == ultimo)
			throw new Exception("Error.");

		Celula i;
		for(i = primeiro; i.prox != ultimo; i = i.prox);

		Presidente elemento = ultimo.elemento;
		ultimo = i;
		i = ultimo.prox = null;
		return elemento;
	}

	public int tamanho(){
		int tamanho = 0;
		for(Celula i = primeiro; i != ultimo; i = i.prox, tamanho++);
		return tamanho;
	}
}