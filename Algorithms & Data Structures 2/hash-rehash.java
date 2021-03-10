// Classe Hash com Rehash
class HashRehash {
   public static int numComparacoes = 0;
   Presidente tabela[];
   int m;
   Presidente NULO = new Presidente();

   public HashRehash (){
      this(21);
   }

   public HashRehash (int m){
      this.m = m;
      this.tabela = new Presidente[this.m];
      for(int i = 0; i < m; i++){
         tabela[i] = NULO;
      }
   }

   public int h(Presidente elemento){
      return elemento.getId() % m;
   }

   public int reh(Presidente elemento){
   	  int r = elemento.getId();
   	  return ++r % m;
   }

   public boolean inserir (Presidente elemento){
      boolean resp = false;

      if(elemento != NULO){

         int pos = h(elemento);

         if(tabela[pos] == NULO){
            tabela[pos] = elemento;
            resp = true;

         } else{
            pos = reh(elemento);  

            if(tabela[pos] == NULO){
            	tabela[pos] = elemento;
            	resp = true;
            }
         }
      }

      return resp;
   }

   public boolean pesquisar(String elemento){
      boolean resp = false;
      int i = 0;

      while(resp == false && i < m){
      	if(tabela[i].getNome().equals(elemento)) resp = true;
      	numComparacoes++;
      	i++;
      }

      return resp;  
   }
}