// Classe Hash Indireta com lista
class HashIndireta {
   public static int numComparacoes = 0;
   Lista tabela[];
   int tamanho;
   Presidente NULL = new Presidente();

   public HashIndireta (){
      this(21);
   }

   public HashIndireta (int tamanho){
      this.tamanho = tamanho;
      tabela = new Lista[tamanho];
      for(int i = 0; i < tamanho; i++){
         tabela[i] = new Lista();
      }
   }

   public int h(Presidente elemento){
      return elemento.getId() % tamanho;
   }

   public void inserir(Presidente presidente){
   	  if(presidente != NULL){
   	  	int pos = h(presidente);
   	  	tabela[pos].inserirFim(presidente);
   	  }
   }

   public boolean pesquisar(String elemento){
   	  boolean resp = false;
   	  int i = 0;

   	  while(resp == false && i < tamanho){
   	  	if(tabela[i].pesquisar(elemento)) resp = true;
   	  	numComparacoes++;
   	  	i++;
   	  }

   	  return resp;
   }
}