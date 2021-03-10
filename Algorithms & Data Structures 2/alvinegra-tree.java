// Arvore Alvinegra
class Alvinegra {
	public static int numComparacoes = 0;
	private No raiz; // Raiz da arvore.

	public Alvinegra() {
		raiz = null;
	}

	public boolean pesquisar(String x) {
		String str = caminhoPesquisa(x, raiz, x + " raiz");
		boolean resp = pesquisar(x, raiz);
		
		if(resp){
			MyIO.println(str + " SIM");
		}else{
			MyIO.println(str + " N"+(char)195+"O");
		}

		return resp;
	}

	private String caminhoPesquisa(String x, No i, String str) {
	
      boolean resp;
		if (i == null) {
         resp = false;

      } else if (x.compareTo(i.elemento.getNome()) == 0) {
         resp = true;

      } else if (x.compareTo(i.elemento.getNome()) < 0) {
         str += " esq";
         str = caminhoPesquisa(x, i.esq, str);

      } else {
      	 str += " dir";
      	 str = caminhoPesquisa(x, i.dir, str);
      }
      return str;
	}

	/**
	 * Metodo privado recursivo para pesquisar elemento.
	 * @param x Elemento que sera procurado.
	 * @param i No em analise.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
	private boolean pesquisar(String x, No i) {
      boolean resp;
		if (i == null) {
         resp = false;

      } else if (x.compareTo(i.elemento.getNome()) == 0) {
         numComparacoes++;
         resp = true;

      } else if (x.compareTo(i.elemento.getNome()) < 0) {
      	 numComparacoes+=2;
         resp = pesquisar(x, i.esq);

      } else {
      	 numComparacoes+=3;
         resp = pesquisar(x, i.dir);
      }
      return resp;
	}

	/**
	 * Metodo publico iterativo para inserir elemento.
	 * @param elemento Elemento a ser inserido.
	 * @throws Exception Se o elemento existir.
	 */
	public void inserir(Presidente elemento) throws Exception {
   
      //Se a arvore estiver vazia
      if(raiz == null){
         raiz = new No(elemento, false);

      //Senao, se a arvore tiver um elemento 
      } else if (raiz.esq == null && raiz.dir == null){
         if (raiz.elemento.getNome().compareTo(elemento.getNome()) > 0){
            raiz.esq = new No(elemento, true);
         } else {
            raiz.dir = new No(elemento, true);
         }

      //Senao, se a arvore tiver dois elementos (raiz e dir)
      } else if (raiz.esq == null){

         if(raiz.elemento.getNome().compareTo(elemento.getNome()) > 0){
            raiz.esq = new No(elemento);

         } else if (raiz.dir.elemento.getNome().compareTo(elemento.getNome()) > 0){
            raiz.esq = new No(raiz.elemento);
            raiz.elemento = elemento;

         } else {
            raiz.esq = new No(raiz.elemento);
            raiz.elemento = raiz.dir.elemento;
            raiz.dir.elemento = elemento;
         }

         raiz.esq.cor = raiz.dir.cor = false;
         
      //Senao, se a arvore tiver dois elementos (raiz e esq)
      } else if (raiz.dir == null){
         
         if(raiz.elemento.getNome().compareTo(elemento.getNome()) < 0){
            raiz.dir = new No(elemento);

         } else if (raiz.esq.elemento.getNome().compareTo(elemento.getNome()) < 0){
            raiz.dir = new No(raiz.elemento);
            raiz.elemento = elemento;

         } else {
            raiz.dir = new No(raiz.elemento);
            raiz.elemento = raiz.esq.elemento;
            raiz.esq.elemento = elemento;
         }

         raiz.esq.cor = raiz.dir.cor = false;

      //Senao, a arvore tem tres ou mais elementos
      } else {
		   inserir(elemento, null, null, null, raiz);
      }

      raiz.cor = false;
   }

   private void balancear(No bisavo, No avo, No pai, No i){

      //Se o pai tambem e preto, reequilibrar a arvore, rotacionando o avo
      if(pai.cor == true){

         //4 tipos de reequilibrios e acoplamento
         if(pai.elemento.getNome().compareTo(avo.elemento.getNome()) > 0){ // rotacao a esquerda ou direita-esquerda
            if(i.elemento.getNome().compareTo(pai.elemento.getNome()) > 0){
               avo = rotacaoEsq(avo);
            } else {
               avo = rotacaoDirEsq(avo);
            }

         } else { // rotacao a direita ou esquerda-direita
            if(i.elemento.getNome().compareTo(pai.elemento.getNome()) < 0){
               avo = rotacaoDir(avo);
            } else {
               avo = rotacaoEsqDir(avo);
            }
         }

         if (bisavo == null){
            raiz = avo;
         } else {
            if(avo.elemento.getNome().compareTo(bisavo.elemento.getNome()) < 0){
               bisavo.esq = avo;
            } else {
               bisavo.dir = avo;
            }
         }

         //reestabelecer as cores apos a rotacao
         avo.cor = false;
         avo.esq.cor = avo.dir.cor = true;

      } //if(pai.cor == true)
   }

	/**
	 * Metodo privado recursivo para inserir elemento.
	 * @param elemento Elemento a ser inserido.
	 * @param avo No em analise.
	 * @param pai No em analise.
	 * @param i No em analise.
	 * @throws Exception Se o elemento existir.
	 */
	private void inserir(Presidente elemento, No bisavo, No avo, No pai, No i) throws Exception {
		if (i == null) {
	         if(elemento.getNome().compareTo(pai.elemento.getNome()) < 0){
	            i = pai.esq = new No(elemento, true);
	         } else {
	            i = pai.dir = new No(elemento, true);
	         }
	         if(pai.cor == true){
	            balancear(bisavo, avo, pai, i);
	         }
      } else {
        //Achou um 4-no: eh preciso fragmeta-lo e reequilibrar a arvore
         if(i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true){
            i.cor = true;
            i.esq.cor = i.dir.cor = false;
            if(i == raiz){
               i.cor = false;
            }else if(pai.cor == true){
               balancear(bisavo, avo, pai, i);
            }
         }
         if (elemento.getNome().compareTo(i.elemento.getNome()) < 0) {
            inserir(elemento, avo, pai, i, i.esq);

         } else if (elemento.getNome().compareTo(i.elemento.getNome()) > 0) {
            inserir(elemento, avo, pai, i, i.dir);

         } else {
            throw new Exception("Erro inserir (elemento repetido)!");
         }
      }
	}

   private No rotacaoDir(No no) {
      No noEsq = no.esq;
      No noEsqDir = noEsq.dir;

      noEsq.dir = no;
      no.esq = noEsqDir;

      return noEsq;
   }

   private No rotacaoEsq(No no) {
      No noDir = no.dir;
      No noDirEsq = noDir.esq;

      noDir.esq = no;
      no.dir = noDirEsq;
      return noDir;
   }

   private No rotacaoDirEsq(No no) {
      no.dir = rotacaoDir(no.dir);
      return rotacaoEsq(no);
   }

   private No rotacaoEsqDir(No no) {
      no.esq = rotacaoEsq(no.esq);
      return rotacaoDir(no);
   }
}