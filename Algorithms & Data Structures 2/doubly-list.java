class ListaDupla{
	private CelulaDupla primeiro, ultimo;

	ListaDupla(){
		primeiro = new CelulaDupla();
		ultimo = primeiro;
	}

	public CelulaDupla getPrimeiro(){
		return this.primeiro;
	}

	public CelulaDupla getUltimo(){
		return this.ultimo;
	}

	public void inserirInicio(Presidente presidente){
		CelulaDupla tmp = new CelulaDupla(presidente);
		tmp.ant = primeiro;
		tmp.prox = primeiro.prox;
		primeiro.prox = tmp;
		if(primeiro == ultimo){
			ultimo = tmp;
		}else{
			tmp.prox.ant = tmp;
		}
		tmp = null;
	}

	public void inserirFim(Presidente presidente){
		ultimo.prox = new CelulaDupla(presidente);
		ultimo.prox.ant = ultimo;
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
			CelulaDupla i = primeiro;
			for(int j = 0; j < pos; j++, i = i.prox);
			CelulaDupla tmp = new CelulaDupla(presidente);
			tmp.ant = i;
			tmp.prox = i.prox;
			tmp.ant.prox = tmp.prox.ant = tmp;
			tmp = i = null;
		}
	}

	public Presidente removerInicio() throws Exception{
		if(primeiro == ultimo)
			throw new Exception("Error!");

		CelulaDupla tmp = primeiro;
		primeiro = primeiro.prox;
		Presidente resp = primeiro.elemento;
		tmp.prox = primeiro.ant = null;
      	tmp = null;
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
			CelulaDupla i = primeiro.prox;
			for(int j = 0; j < pos; j++, i = i.prox);
			
			i.ant.prox = i.prox;
			i.prox.ant = i.ant;
			elemento = i.elemento;
			i.prox = i.ant = null;
			i = null;
		}

		return elemento;
	}

	public Presidente removerFim() throws Exception{
		if(primeiro == ultimo)
			throw new Exception("Error.");

		Presidente elemento = ultimo.elemento;
		ultimo = ultimo.ant;
		ultimo.prox.ant = null;
		ultimo.prox = null;
		return elemento;
	}

	public int tamanho(){
		int tamanho = 0;
		for(CelulaDupla i = primeiro; i != ultimo; i = i.prox, tamanho++);
		return tamanho;
	}

	public void swap(int pos1, int pos2){
		CelulaDupla tmp1 = primeiro.prox;
		for(int i = 0; i < pos1; i++, tmp1 = tmp1.prox);
		CelulaDupla tmp2 = primeiro.prox;
		for(int i = 0; i < pos2; i++, tmp2 = tmp2.prox);
		
		Presidente aux = tmp1.elemento;
		tmp1.elemento = tmp2.elemento;
		tmp2.elemento = aux;

		tmp1 = null;
		tmp2 = null;
	}
}