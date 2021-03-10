class Pilha{
	private Celula topo;
	public Pilha(){
		topo = null;
	}
	public void inserir(Presidente x){
		Celula tmp = new Celula(x);
		tmp.prox = topo;
		topo = tmp;
		tmp = null;
	}

	public Presidente remover() throws Exception{
		if(topo == null)
			throw new Exception("Erro!");

		Presidente elemento = topo.elemento;
		Celula tmp = topo;
		topo = topo.prox;
		tmp.prox = null;
		tmp = null;

		return elemento;
	}

	public Celula getTopo(){
		return this.topo;
	}

	public void mostrar(){
		System.out.print("[ ");
		for(Celula i = topo; i != null; i = i.prox){
			System.out.print(i.elemento + " ");
		}
		System.out.println("]");
	}

	public void mostrarRecursivo(){
		mostrarRecursivo(topo, 25);
	}

	public void mostrarRecursivo(Celula i, int index){
		if(i != null){
			mostrarRecursivo(i.prox, index-1);
			
			// Print informações
			DateTimeFormatter formatadorBarra = DateTimeFormatter.ofPattern("d/M/yyyy");
			LocalDate data = LocalDate.of(1800, Month.JANUARY, 1);
			if(!i.elemento.getDataMorte().isEqual(data)){
				MyIO.println("[" + index + "] " + i.elemento.getId() + " ## " + i.elemento.getNome() + " ## " + i.elemento.getInicioMandato().format(formatadorBarra) + 
						 " (I) ## " + i.elemento.getFimMandato().format(formatadorBarra) + " (F) ## " + i.elemento.getDataNascimento().format(formatadorBarra) + 
						 " em " + i.elemento.getLocalNascimento() + " (N) ## " + i.elemento.getIdade() + " ## " + 
						 i.elemento.getDataMorte().format(formatadorBarra) + " em " + i.elemento.getLocalMorte() + " (M) ## " + 
						 i.elemento.getPagina() + " ## " + i.elemento.getPaginaTam() + " ## " + i.elemento.getAntecessor() + 
						 " ## " + i.elemento.getSucessor() + " ## " + i.elemento.getVice());
			}else{
				MyIO.println("[" + index + "] " + i.elemento.getId() + " ## " + i.elemento.getNome() + " ## " + i.elemento.getInicioMandato().format(formatadorBarra) + 
						 " (I) ## " + i.elemento.getFimMandato().format(formatadorBarra) + " (F) ## " + i.elemento.getDataNascimento().format(formatadorBarra) + 
						 " em " + i.elemento.getLocalNascimento() + " (N) ## " + i.elemento.getIdade() + " ## " + 
						 i.elemento.getPagina() + " ## " + i.elemento.getPaginaTam() + " ## " + i.elemento.getAntecessor() + 
						 " ## " + i.elemento.getSucessor() + " ## " + i.elemento.getVice());
			}
		}
	}
}