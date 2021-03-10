// Arvore AVL
class Avl {
	public static int numComparacoes = 0;
	private No raiz; // Raiz da arvore.

	public Avl() {
		raiz = null;
	}

   public int getAltura() {
      return No.getNivel(raiz) - 1;
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
	 * @param x Elemento a ser inserido.
	 * @throws Exception Se o elemento existir.
	 */
	public void inserir(Presidente x) throws Exception {
		raiz = inserir(raiz, x);
	}

	/**
	 * Metodo privado recursivo para inserir elemento.
	 * @param no No em analise.
	 * @param x Elemento a ser inserido.
	 * @return No em analise, alterado ou nao.
	 * @throws Exception Se o elemento existir.
	 */
	private No inserir(No no, Presidente x) throws Exception {
		if (no == null) { 
         no = new No(x);

      } else if (x.getNome().compareTo(no.elemento.getNome()) < 0) {
         no.esq = inserir(no.esq, x); 

      } else if (x.getNome().compareTo(no.elemento.getNome()) > 0) { 
         no.dir = inserir(no.dir, x); 
      
      } else { 
         throw new Exception("Erro ao inserir elemento"); 
      }

      no = balancear(no);
	  return no;
	}

   private No balancear(No no) throws Exception {
      if(no != null){
         int fator = No.getNivel(no.dir) - no.getNivel(no.esq);

         //Se balanceada
         if (Math.abs(fator) <= 1){
            no = no.setNivel();

         //Se desbalanceada para a direita
         }else if (fator == 2){

            int fatorFilhoDir = No.getNivel(no.dir.dir) - No.getNivel(no.dir.esq);

            //Se o filho a direita tambem estiver desbalanceado
            if (fatorFilhoDir == -1) {
               no.dir = rotacionarDir(no.dir);
            }
            no = rotacionarEsq(no);

         //Se desbalanceada para a esquerda
         }else if (fator == -2){

            int fatorFilhoEsq = No.getNivel(no.esq.dir) - No.getNivel(no.esq.esq);

            //Se o filho a esquerda tambem estiver desbalanceado
            if (fatorFilhoEsq == 1) {
               no.esq = rotacionarEsq(no.esq);
            }
            no = rotacionarDir(no);

         }else{
            throw new Exception("Erro fator de balanceamento (" + fator + ") invalido!"); 
         }
      }

      return no;
   }

   private No rotacionarDir(No no) {
      No noEsq = no.esq;
      No noEsqDir = noEsq.dir;

      noEsq.dir = no;
      no.esq = noEsqDir;

      no.setNivel();
      noEsq.setNivel();

      return noEsq;
   }

   private No rotacionarEsq(No no) {
      No noDir = no.dir;
      No noDirEsq = noDir.esq;

      noDir.esq = no;
      no.dir = noDirEsq;

      no.setNivel();
      noDir.setNivel();
      return noDir;
   }
}