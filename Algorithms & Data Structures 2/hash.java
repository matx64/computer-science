// Classe Hash com Reserva
class HashReserva {
   public static int numComparacoes = 0;
   Presidente tabela[];
   int m1, m2, m, reserva;
   Presidente NULO = new Presidente();

   public HashReserva (){
      this(21, 9);
   }

   public HashReserva (int m1, int m2){
      this.m1 = m1;
      this.m2 =  m2;
      this.m = m1 + m2;
      this.tabela = new Presidente[this.m];
      for(int i = 0; i < m; i++){
         tabela[i] = NULO;
      }
      reserva  = 0;
   }

   public int h(Presidente elemento){
      return elemento.getId() % m1;
   }

   public boolean inserir (Presidente elemento){
      boolean resp = false;

      if(elemento != NULO){

         int pos = h(elemento);

         if(tabela[pos] == NULO){
            tabela[pos] = elemento;
            resp = true;

         } else if (reserva < m2){
            tabela[m1 + reserva] = elemento;
            reserva++;
            resp = true;
         }
      }

      return resp;
   }

   public boolean pesquisar(String elemento){
      boolean resp = false;
      int i = 0;

      while(resp == false && i < m1 + reserva){
      	if(tabela[i].getNome().equals(elemento)) resp = true;
      	numComparacoes++;
      	i++;
      }

      return resp;  
   }
}