class ArvoreArvore {
	private No raiz; // Raiz da arvore.
	public int numComparacoes = 0;

	/**
	 * Construtor da classe.
	 */
	public ArvoreArvore() throws Exception{
		raiz = null;
      	inserir(7);
      	inserir(3);
      	inserir(11);
      	inserir(1);
      	inserir(5);
      	inserir(9);
      	inserir(12);
      	inserir(0);
      	inserir(2);
      	inserir(4);
      	inserir(6);
      	inserir(8);
      	inserir(10);
      	inserir(13);
      	inserir(14);
	}

	public boolean pesquisar(No i, String x) throws Exception{
		boolean resp = false;
		if(i != null){
			if(!resp){
				resp = pesquisar(i.outro, x);
			}
			if(!resp){
				MyIO.print("esq "); resp = pesquisar(i.esq, x);
			}
			if(!resp){
				MyIO.print("dir "); resp = pesquisar(i.dir, x);
			}
		}
		return resp;
	}

	/**
	 * Metodo publico iterativo para pesquisar elemento.
	 * @param elemento Elemento que sera procurado.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
	public boolean pesquisar(String elemento) throws Exception{
		System.out.print(elemento + " ");
		MyIO.print("raiz ");

		boolean resp = pesquisar(raiz, elemento);
		if(resp){
			MyIO.println("SIM");
		}else{
			MyIO.println("NÃO");
		}
		return resp;
	}

	public boolean pesquisar(No2 i, String x) throws Exception{
		boolean resp = false;
		if(i != null){
				if(x.compareTo(i.elemento.getNome()) == 0){
					resp = true;
				}
			if(!resp){
				MyIO.print("ESQ "); resp = pesquisar(i.esq, x);
				
			}
			if(!resp){
				MyIO.print("DIR "); resp = pesquisar(i.dir, x);
			}
		}
		return resp;
	}

   /**
	 * Metodo publico iterativo para inserir elemento.
	 * @param x Elemento a ser inserido.
	 * @throws Exception Se o elemento existir.
	 */
	public void inserir(int x) throws Exception {
		raiz = inserir(x, raiz);
	}

	/**
	 * Metodo privado recursivo para inserir elemento.
	 * @param x Elemento a ser inserido.
	 * @param i No em analise.
	 * @return No em analise, alterado ou nao.
	 * @throws Exception Se o elemento existir.
	 */
	private No inserir(int x, No i) throws Exception {
		if (i == null) {
         i = new No(x);

      } else if (x < i.elemento) {
         i.esq = inserir(x, i.esq);

      } else if (x > i.elemento) {
         i.dir = inserir(x, i.dir);

      } else {
         throw new Exception("Erro ao inserir!");
      }

		return i;
	}

	public void mostrarPre(Presidente x) throws Exception{
		mostrarPre(raiz, x);
	}

	/**
	 * Metodo privado recursivo para exibir elementos.
	 * @param i No em analise.
	 */
	public void mostrarPre(No i, Presidente x) throws Exception{
		if(x.getIdade() % 15 < i.elemento){
			mostrarPre(i.esq, x);
		}else if(x.getIdade() % 15 > i.elemento){
			mostrarPre(i.dir, x);
		}else{
			i.outro = inserirSegundaArvore(x, i.outro);
		}
	}


	public No2 inserirSegundaArvore(Presidente x, No2 i) throws Exception{
		if (i == null){
			i = new No2(x);
		}else if(x.getIdade() < i.elemento.getIdade()){
			i.esq = inserirSegundaArvore(x, i.esq);
		}else if(x.getIdade() > i.elemento.getIdade()){
			i.dir = inserirSegundaArvore(x, i.dir);
		}else{
			//throw new Exception("Erro ao inserir!");
		}

		return i;
	}
}