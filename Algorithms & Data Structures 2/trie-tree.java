class ArvoreTrie{
	public static int numComparacoes = 0;
	private No raiz;

	public ArvoreTrie(){
		raiz = new No();
	}

	public void inserir(String s){
      inserir(s, raiz, 0);
   }

   public void inserir(String s, No no, int i){
      if(no.prox[s.charAt(i)] == null){
         no.prox[s.charAt(i)] = new No(s.charAt(i));

         if(i == s.length() - 1){
            no.prox[s.charAt(i)].folha = true;
         }else{
            inserir(s, no.prox[s.charAt(i)], i + 1);
         }

      } else if (no.prox[s.charAt(i)].folha == false && i < s.length() - 1){
         inserir(s, no.prox[s.charAt(i)], i + 1);
      }
   }

	public boolean pesquisar(String s){
		return pesquisar(s, raiz, 0);
	}

	private boolean pesquisar(String s, No no, int i){
		boolean resp = false;
		if(no.prox[s.charAt(i)] == null){
			numComparacoes++;
			resp = false;
		}else if(i == s.length() - 1){
			resp = (no.prox[s.charAt(i)].folha == true);
			numComparacoes++;
		}else if(i < s.length() - 1){
			resp = pesquisar(s, no.prox[s.charAt(i)], i + 1);
			numComparacoes++;
		}

		return resp;
	}

	public String[] arrayElementos(){
		String[] array = new String[25];
		for(int i = 0; i < 25; i++){
			array[i] = "";
		}

		arrayElementos("", raiz, 0, array);
		return array;
	}

	public int arrayElementos(String s, No no, int j, String[] array){
		if(no.folha == true){
			array[j] = (s + no.elemento);
			j++;
		}else{
			for(int i = 0; i < no.prox.length; i++){
				if(no.prox[i] != null){
				 	j = arrayElementos(s + no.elemento, no.prox[i], j, array);
				}
			}
		}

		return j;
	}



	public void merge(ArvoreTrie arvore2){
		String[] elementosA2 = arvore2.arrayElementos();
		
		for(int i = 0; elementosA2[i] != ""; i++){
			this.inserir(elementosA2[i].substring(1));
		}
	}
}