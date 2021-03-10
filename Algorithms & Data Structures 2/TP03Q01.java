/* 
	Nome: Matheus Teixeira Alves
	Matricula: 636132
	Data: 20/4/2019
*/

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.*;

class TP03Q01{
	public static void main(String[]args) throws Exception{
		long inicioExecucao = System.currentTimeMillis();
		MyIO.setCharset("UTF-8");
		String[] entrada = new String[50];
		int numEntrada = 0;
		ArvoreBinaria arvoreBinaria = new ArvoreBinaria();

		// Entrada
		do{
			entrada[numEntrada] = MyIO.readLine();
		}while(entrada[numEntrada++].equals("FIM") == false);
		numEntrada--;

		// Inserindo presidentes na arvore
		Presidente[] presidentes1 = new Presidente[38];
		for(int i = 0; i < numEntrada; i++){
			presidentes1[i] = new Presidente();
			presidentes1[i].setPagina(entrada[i]);
			arvoreBinaria.inserir(analisePresidente(presidentes1[i]));
		}

		// Entrada de nomes para pesquisa...
		numEntrada = 0;
		String[] entrada2 = new String[150];
		do{
			entrada2[numEntrada] = MyIO.readLine();
		}while(entrada2[numEntrada++].equals("FIM") == false);
		numEntrada--;

		// Pesquisa e saídas
		for(int i = 0; i < numEntrada; i++){
			arvoreBinaria.pesquisar(entrada2[i]);
		}

		// Escrevendo informações no log
		Arq.openWrite("636132_arvoreBinaria.txt");
		long tempoExecucao = System.currentTimeMillis() - inicioExecucao;
		Arq.print("636132" + "\t" + tempoExecucao + "\t" + arvoreBinaria.numComparacoes);
		Arq.close();
	}

	// Metodo que formata datas...
	public static LocalDate formataData(String ano, String mes, int dia){
		LocalDate data = LocalDate.of(1800, Month.JANUARY, 1);
		switch(mes){
			case "janeiro":
				data = LocalDate.of(Integer.parseInt(ano), Month.JANUARY, dia);
				break;
			case "fevereiro":
				data = LocalDate.of(Integer.parseInt(ano), Month.FEBRUARY, dia);
				break;
			case "abril":
				data = LocalDate.of(Integer.parseInt(ano), Month.APRIL, dia);
				break;
			case "maio":
				data = LocalDate.of(Integer.parseInt(ano), Month.MAY, dia);
				break;
			case "junho":
				data = LocalDate.of(Integer.parseInt(ano), Month.JUNE, dia);
				break;
			case "julho":
				data = LocalDate.of(Integer.parseInt(ano), Month.JULY, dia);
				break;
			case "agosto":
				data = LocalDate.of(Integer.parseInt(ano), Month.AUGUST, dia);
				break;
			case "setembro":
				data = LocalDate.of(Integer.parseInt(ano), Month.SEPTEMBER, dia);
				break;
			case "outubro":
				data = LocalDate.of(Integer.parseInt(ano), Month.OCTOBER, dia);
				break;
			case "novembro":
				data = LocalDate.of(Integer.parseInt(ano), Month.NOVEMBER, dia);
				break;
			case "dezembro":
				data = LocalDate.of(Integer.parseInt(ano), Month.DECEMBER, dia);
				break;
			default:
				data = LocalDate.of(Integer.parseInt(ano), Month.MARCH, dia);
				break;
		}
		return data;
	}

	// Metodo que analisa informações do presidente
	public static Presidente analisePresidente(Presidente presidente){
		Arq.openRead(presidente.getPagina(), "UTF-8");
		String[] scopeLinhas = new String[500];
		String str;
		int numLinha = 0;

		// Usa somente as linhas importantes do html, linha 40 a 430
		do{
			str = Arq.readLine();
			if(numLinha >= 40 && numLinha <= 430){
				scopeLinhas[numLinha-40] = str;
			}
			numLinha++;
		}while(numLinha <= 430);
		
		Arq.close();

		// Calcula tamanho da pagina
		Arq.openRead(presidente.getPagina(), "ISO-8859-1");
		String newStr = Arq.readAll();
		long paginaTam = newStr.length();
		Arq.close();

		// Encontra ID do presidente
		int id = 0;
		boolean achouID = false;
		for(int i = 0; i < 50; i++){
			if(!achouID){
				for(int j = 20; j < scopeLinhas[i].length(); j++){
					if(scopeLinhas[i].charAt(j) == 186 || scopeLinhas[i].charAt(j) == 170){
						if(scopeLinhas[i].charAt(j-1) >= 48 && scopeLinhas[i].charAt(j-1) <= 57 &&
						   scopeLinhas[i].charAt(j-2) >= 48 && scopeLinhas[i].charAt(j-2) <= 57){
							id += Character.getNumericValue(scopeLinhas[i].charAt(j-2))*10 + Character.getNumericValue(scopeLinhas[i].charAt(j-1));
						}else if(scopeLinhas[i].charAt(j-1) >= 48 && scopeLinhas[i].charAt(j-1) <= 57){
							id += Character.getNumericValue(scopeLinhas[i].charAt(j-1));
						}else if(scopeLinhas[i].charAt(j-6) >= 48 && scopeLinhas[i].charAt(j-6) <= 57 &&
						   		 scopeLinhas[i].charAt(j-7) >= 48 && scopeLinhas[i].charAt(j-7) <= 57){
						   	id += Character.getNumericValue(scopeLinhas[i].charAt(j-7))*10 + Character.getNumericValue(scopeLinhas[i].charAt(j-6));
						}else{
							id += Character.getNumericValue(scopeLinhas[i].charAt(j-6));
						}
						achouID = true;
					}
				}
			}
		}

		// VARIAVEIS DE MANDATO
		int diaInicio = 0;
		String mesInicio = "";
		String anoInicio = "";
		int diaFinal = 0;
		String mesFinal = "";
		String anoFinal = "";
		boolean achouDiaI = false, achouMesI = false, achouAnoI = false;
		boolean achouDiaF = false, achouMesF = false, achouAnoF = false;

		// MAIS VARIAVEIS...
		String vice = "";
		boolean achouVice = false;
		String antecessor = "";
		String sucessor = "";

		// Analisa linhas 50 a 109 do html, onde contem informações de mandato
		for(int i = 10; i < 70; i++){
			if(scopeLinhas[i].equals("<td scope=\"row\" style=\"vertical-align: top; text-align: left;\">Per" + ((char)237) + "odo")){
				for(int j = 30; j < scopeLinhas[i+2].length()-1; j++){
					if(scopeLinhas[i+2].charAt(j) == '>' && scopeLinhas[i+2].charAt(j+1) != '<' && scopeLinhas[i+2].charAt(j+1) != ' ' && 
						scopeLinhas[i+2].charAt(j+1) != '&' && scopeLinhas[i+2].charAt(j+1) != ',' && scopeLinhas[i+2].charAt(j+1) != 'a'){
						if(!achouDiaI){
							if(scopeLinhas[i+2].charAt(j+2) == ' ' || scopeLinhas[i+2].charAt(j+2) == 186){
								diaInicio = Character.getNumericValue(scopeLinhas[i+2].charAt(j+1));
								if(scopeLinhas[i+2].charAt(j+2) == 186){
									int counter = 7;
									while(scopeLinhas[i+2].charAt(j+counter) != '<'){
										mesInicio += scopeLinhas[i+2].charAt(j+counter);
										counter++;
									}
								}else{
									int counter = 6;
									while(scopeLinhas[i+2].charAt(j+counter) != '<'){
										mesInicio += scopeLinhas[i+2].charAt(j+counter);
										counter++;
									}
								}
							}else{
								diaInicio = Character.getNumericValue(scopeLinhas[i+2].charAt(j+1))*10 + Character.getNumericValue(scopeLinhas[i+2].charAt(j+2));
								int counter = 7;
								while(scopeLinhas[i+2].charAt(j+counter) != '<'){
									mesInicio += scopeLinhas[i+2].charAt(j+counter);
									counter++;
								}
							}
							achouDiaI = true;
							achouMesI = true;
						}else if(!achouAnoI){
							int counter = 1;
							while(scopeLinhas[i+2].charAt(j+counter) != '<'){
								anoInicio += scopeLinhas[i+2].charAt(j+counter);
								counter++;
							}
							achouAnoI = true;
						}else if(!achouDiaF){
							if(scopeLinhas[i+2].charAt(j+2) == ' ' || scopeLinhas[i+2].charAt(j+2) == 186){
								diaFinal = Character.getNumericValue(scopeLinhas[i+2].charAt(j+1));
								if(scopeLinhas[i+2].charAt(j+2) == 186){
									int counter = 7;
									while(scopeLinhas[i+2].charAt(j+counter) != '<'){
										mesFinal += scopeLinhas[i+2].charAt(j+counter);
										counter++;
									}
								}else{
									int counter = 6;
									while(scopeLinhas[i+2].charAt(j+counter) != '<'){
										mesFinal += scopeLinhas[i+2].charAt(j+counter);
										counter++;
									}
								}
							}else{
								diaFinal = Character.getNumericValue(scopeLinhas[i+2].charAt(j+1))*10 + Character.getNumericValue(scopeLinhas[i+2].charAt(j+2));
								int counter = 7;
								while(scopeLinhas[i+2].charAt(j+counter) != '<'){
									mesFinal += scopeLinhas[i+2].charAt(j+counter);
									counter++;
								}
							}
							achouDiaF = true;
							achouMesF = true;
						}else if(!achouAnoF){
							int counter = 1;
							while(scopeLinhas[i+2].charAt(j+counter) != '<'){
								anoFinal += scopeLinhas[i+2].charAt(j+counter);
								counter++;
							}
							achouAnoF = true;
						}
					}
				}
			}

			if(!achouVice){
				if(scopeLinhas[i].equals("<td scope=\"row\" style=\"vertical-align: top; text-align: left;\">Vice-presidente")){
					for(int j = 30; j < scopeLinhas[i+2].length()-1; j++){
						if(scopeLinhas[i+2].charAt(j) == '>' && scopeLinhas[i+2].charAt(j+1) != '<'){
							int count = 1;
							while(scopeLinhas[i+2].charAt(j+count) != '<'){
								vice += scopeLinhas[i+2].charAt(j+count);
								count++;
							}
						}
					}
					achouVice = true;
				}
			}
			

			if(scopeLinhas[i].equals("<td scope=\"row\" style=\"vertical-align: top; text-align: left;\">Antecessor")){
				for(int j = 30; j < scopeLinhas[i+2].length()-1; j++){
					if(scopeLinhas[i+2].charAt(j) == '>' && scopeLinhas[i+2].charAt(j+1) != '<'){
						int count = 1;
						while(scopeLinhas[i+2].charAt(j+count) != '<'){
							antecessor += scopeLinhas[i+2].charAt(j+count);
							count++;
						}
					}
				}
			}

			if(scopeLinhas[i].equals("<td scope=\"row\" style=\"vertical-align: top; text-align: left;\">Sucessor")){
				for(int j = 30; j < scopeLinhas[i+2].length()-1; j++){
					if(scopeLinhas[i+2].charAt(j) == '>' && scopeLinhas[i+2].charAt(j+1) != '<'){
						int count = 1;
						while(scopeLinhas[i+2].charAt(j+count) != '<'){
							sucessor += scopeLinhas[i+2].charAt(j+count);
							count++;
						}
					}
				}
			}
		}


		String nome = ""; // NOME COMPLETO

		// VARIAVEIS DE NASCIMENTO
		int dia = 0;
		String mes = "";
		String cidade = "";
		String estado = "";
		String ano = "";
		boolean achouDia = false, achouMes = false, achouAno = false, achouCidade = false;

		// VARIAVEIS DE MORTE
		int dia2 = 0;
		String mes2 = "";
		String cidade2 = "";
		String estado2 = "";
		String ano2 = "";
		boolean achouDia2 = false, achouMes2 = false, achouAno2 = false, achouCidade2 = false;
		boolean morreu = false;
		
		// Analisa linhas 240 a 350 do html, onde contem informações pessoais
		for(int i = 200; i < 390; i++){

			// Procura nome completo
			if(scopeLinhas[i].equals("<td scope=\"row\" style=\"vertical-align: top; text-align: left;\">Nome completo")){
				for(int j = 51; j < scopeLinhas[i+2].length(); j++){
					nome += scopeLinhas[i+2].charAt(j);
				}
			}

			// Procura informacoes de nascimento
			if(scopeLinhas[i].equals("<td scope=\"row\" style=\"vertical-align: top; text-align: left;\">Nascimento")){
				for(int j = 30; j < scopeLinhas[i+2].length()-1; j++){
					if(scopeLinhas[i+2].charAt(j) == '>' && scopeLinhas[i+2].charAt(j+1) != '<' && scopeLinhas[i+2].charAt(j+1) != ' ' && 
						scopeLinhas[i+2].charAt(j+1) != '&' && scopeLinhas[i+2].charAt(j+1) != ','){
						if(!achouDia){
							if(scopeLinhas[i+2].charAt(j+2) == ' ' || scopeLinhas[i+2].charAt(j+2) == 186){
								dia = Character.getNumericValue(scopeLinhas[i+2].charAt(j+1));
								if(scopeLinhas[i+2].charAt(j+2) == 186){
									int counter = 7;
									while(scopeLinhas[i+2].charAt(j+counter) != '<'){
										mes += scopeLinhas[i+2].charAt(j+counter);
										counter++;
									}
								}else{
									int counter = 6;
									while(scopeLinhas[i+2].charAt(j+counter) != '<'){
										mes += scopeLinhas[i+2].charAt(j+counter);
										counter++;
									}
								}
							}else{
								dia = Character.getNumericValue(scopeLinhas[i+2].charAt(j+1))*10 + Character.getNumericValue(scopeLinhas[i+2].charAt(j+2));
								int counter = 7;
								while(scopeLinhas[i+2].charAt(j+counter) != '<'){
									mes += scopeLinhas[i+2].charAt(j+counter);
									counter++;
								}
							}
							achouDia = true;
							achouMes = true;
						}else if(!achouAno){
							int counter = 1;
							while(scopeLinhas[i+2].charAt(j+counter) != '<'){
								ano += scopeLinhas[i+2].charAt(j+counter);
								counter++;
							}
							achouAno = true;
						}else if(!achouCidade){
							int counter = 1;
							while(scopeLinhas[i+2].charAt(j+counter) != '<'){
								cidade += scopeLinhas[i+2].charAt(j+counter);
								counter++;
							}
							achouCidade = true;
						}else{
							int counter = 1;
							while(scopeLinhas[i+2].charAt(j+counter) != '<'){
								estado += scopeLinhas[i+2].charAt(j+counter);
								counter++;
							}
						}
					}
				}
			}


			// Procura informacoes de morte
			if(scopeLinhas[i].equals("<td scope=\"row\" style=\"vertical-align: top; text-align: left;\">Morte")){
				morreu = true;
				for(int j = 30; j < scopeLinhas[i+2].length()-1; j++){
					if(scopeLinhas[i+2].charAt(j) == '>' && scopeLinhas[i+2].charAt(j+1) != '<' && scopeLinhas[i+2].charAt(j+1) != ' ' && 
						scopeLinhas[i+2].charAt(j+1) != '&' && scopeLinhas[i+2].charAt(j+1) != ','){
						if(!achouDia2){
							if(scopeLinhas[i+2].charAt(j+2) == ' ' || scopeLinhas[i+2].charAt(j+2) == 186){
								dia2 = Character.getNumericValue(scopeLinhas[i+2].charAt(j+1));
								if(scopeLinhas[i+2].charAt(j+2) == 186){
									int counter = 7;
									while(scopeLinhas[i+2].charAt(j+counter) != '<'){
										mes2 += scopeLinhas[i+2].charAt(j+counter);
										counter++;
									}
								}else{
									int counter = 6;
									while(scopeLinhas[i+2].charAt(j+counter) != '<'){
										mes2 += scopeLinhas[i+2].charAt(j+counter);
										counter++;
									}
								}
							}else{
								dia2 = Character.getNumericValue(scopeLinhas[i+2].charAt(j+1))*10 + Character.getNumericValue(scopeLinhas[i+2].charAt(j+2));;
								int counter = 7;
								while(scopeLinhas[i+2].charAt(j+counter) != '<'){
									mes2 += scopeLinhas[i+2].charAt(j+counter);
									counter++;
								}
							}
							achouDia2 = true;
							achouMes2 = true;
						}else if(!achouAno2){
							int counter = 1;
							while(scopeLinhas[i+2].charAt(j+counter) != '<'){
								ano2 += scopeLinhas[i+2].charAt(j+counter);
								counter++;
							}
							achouAno2 = true;
						}else if(!achouCidade2){
							int counter = 1;
							while(scopeLinhas[i+2].charAt(j+counter) != '<'){
								cidade2 += scopeLinhas[i+2].charAt(j+counter);
								counter++;
							}
							achouCidade2 = true;
						}else{
							int counter = 1;
							while(scopeLinhas[i+2].charAt(j+counter) != '<'){
								estado2 += scopeLinhas[i+2].charAt(j+counter);
								counter++;
							}
						}
					}
				}
			}
		}

		// FORMATANDO DATAS...
		LocalDate dataNascimento = formataData(ano, mes, dia);
		if(morreu){ 
			LocalDate dataMorte = formataData(ano2, mes2, dia2);
			presidente.setDataMorte(dataMorte);
		}
		LocalDate inicioMandato = formataData(anoInicio, mesInicio, diaInicio);
		LocalDate fimMandato = formataData(anoFinal, mesFinal, diaFinal);

		// SETS COM AS INFORMAÇÕES ENCONTRADAS
		presidente.setId(id);
		presidente.setInicioMandato(inicioMandato);
		presidente.setFimMandato(fimMandato);
		presidente.setVice(vice);
		presidente.setAntecessor(antecessor);
		presidente.setSucessor(sucessor);
		presidente.setNome(nome);
		presidente.setDataNascimento(dataNascimento);
		presidente.setPaginaTam(paginaTam);
		presidente.setIdade(2019 - Integer.parseInt(ano));
		if(id == 1){
			presidente.setLocalNascimento(cidade + "," + estado);
		}else if(id == 33){
			presidente.setLocalNascimento(cidade);
		}else{
			presidente.setLocalNascimento(cidade + ", " + estado);
		}
		if(id == 25){
			presidente.setLocalMorte(cidade2 + ", São Paulo");
		}else if(id == 28){
			presidente.setLocalMorte(cidade2 + ", RJ, Brasil");
		}else{
			presidente.setLocalMorte(cidade2 + ", " + estado2);
		}

		return presidente;
	}

	public static void printInfo(Presidente presidente, int index){
		DateTimeFormatter formatadorBarra = DateTimeFormatter.ofPattern("d/M/yyyy");
		LocalDate data = LocalDate.of(1800, Month.JANUARY, 1);
		// OUT SE MORREU OU NAO...
		if(!presidente.getDataMorte().isEqual(data)){
			MyIO.println("[" + index + "] " + presidente.getId() + " ## " + presidente.getNome() + " ## " + presidente.getInicioMandato().format(formatadorBarra) + 
					 " (I) ## " + presidente.getFimMandato().format(formatadorBarra) + " (F) ## " + presidente.getDataNascimento().format(formatadorBarra) + 
					 " em " + presidente.getLocalNascimento() + " (N) ## " + presidente.getIdade() + " ## " + 
					 presidente.getDataMorte().format(formatadorBarra) + " em " + presidente.getLocalMorte() + " (M) ## " + 
					 presidente.getPagina() + " ## " + presidente.getPaginaTam() + " ## " + presidente.getAntecessor() + 
					 " ## " + presidente.getSucessor() + " ## " + presidente.getVice());
		}else{
			MyIO.println("[" + index + "] " + presidente.getId() + " ## " + presidente.getNome() + " ## " + presidente.getInicioMandato().format(formatadorBarra) + 
					 " (I) ## " + presidente.getFimMandato().format(formatadorBarra) + " (F) ## " + presidente.getDataNascimento().format(formatadorBarra) + 
					 " em " + presidente.getLocalNascimento() + " (N) ## " + presidente.getIdade() + " ## " + 
					 presidente.getPagina() + " ## " + presidente.getPaginaTam() + " ## " + presidente.getAntecessor() + 
					 " ## " + presidente.getSucessor() + " ## " + presidente.getVice());
		}
	}
}

// Classe Presidente
class Presidente{

	// Atributos do presidente 
	private int id;
	private String nome;
	private int idade;
	private LocalDate dataNascimento;
	private String localNascimento;
	private LocalDate inicioMandato;
	private LocalDate fimMandato;
	private LocalDate dataMorte;
	private String localMorte;
	private String antecessor;
	private String sucessor;
	private String vice;
	private String pagina;
	private long paginaTam;

	// CONSTRUTORES
	public Presidente(){
		this.id = 0;
		this.nome = "";
		this.dataNascimento = LocalDate.of(1800, Month.JANUARY, 1);
		this.inicioMandato = LocalDate.of(1800, Month.JANUARY, 1);
		this.fimMandato = LocalDate.of(1800, Month.JANUARY, 1);
		this.idade = 0;
		this.localNascimento = "";
		this.dataMorte = LocalDate.of(1800, Month.JANUARY, 1);
		this.localMorte = "";
		this.antecessor = "";
		this.sucessor = "";
		this.vice = "";
		this.pagina =  "";
		this.paginaTam = 0;
	}

	public Presidente(int id, String nome, int idade, LocalDate dataNascimento, String localNascimento,
					  String localMorte, LocalDate inicioMandato, String antecessor, LocalDate fimMandato, 
					  String sucessor, LocalDate dataMorte, String vice, String pagina, long paginaTam){
		this.id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.idade = idade;
		this.localNascimento = localNascimento;
		this.localMorte = localMorte;
		this.dataMorte = dataMorte;
		this.inicioMandato = inicioMandato;
		this.fimMandato = fimMandato;
		this.antecessor = antecessor;
		this.sucessor = sucessor;
		this.vice = vice;
		this.pagina =  pagina;
		this.paginaTam = paginaTam;
	}

	// SETS
	public void setId(int id){
		this.id = id;
	}
	public void setInicioMandato(LocalDate inicioMandato){
		this.inicioMandato = inicioMandato;
	}
	public void setFimMandato(LocalDate fimMandato){
		this.fimMandato = fimMandato;
	}
	public void setDataNascimento(LocalDate dataNascimento){
		this.dataNascimento = dataNascimento;
	}
	public void setDataMorte(LocalDate dataMorte){
		this.dataMorte = dataMorte;
	}
	public void setNome(String nome){
		this.nome = nome; 
	}
	public void setIdade(int idade){
		this.idade = idade;
	}
	public void setLocalNascimento(String localNascimento){
		this.localNascimento = localNascimento;
	}
	public void setLocalMorte(String localMorte){
		this.localMorte = localMorte;
	}
	public void setAntecessor(String antecessor){
		this.antecessor = antecessor;
	}
	public void setSucessor(String sucessor){
		this.sucessor = sucessor;
	}
	public void setVice(String vice){
		this.vice = vice;
	}
	public void setPagina(String pagina){
		this.pagina = pagina;
	}
	public void setPaginaTam(long paginaTam){
		this.paginaTam = paginaTam;
	}

	// GETS
	public int getId(){
		return this.id;
	}
	public LocalDate getInicioMandato(){
		return this.inicioMandato;
	}
	public LocalDate getFimMandato(){
		return this.fimMandato;
	}
	public String getNome(){
		return this.nome;
	}
	public LocalDate getDataNascimento(){
		return this.dataNascimento;
	}
	public LocalDate getDataMorte(){
		return this.dataMorte;
	}
	public int getIdade(){
		return this.idade;
	}
	public String getLocalNascimento(){
		return this.localNascimento;
	}
	public String getLocalMorte(){
		return this.localMorte;
	}
	public String getAntecessor(){
		return this.antecessor;
	}
	public String getSucessor(){
		return this.sucessor;
	}
	public String getVice(){
		return this.vice;
	}
	public String getPagina(){
		return this.pagina;
	}
	public long getPaginaTam(){
		return this.paginaTam;
	}
}

class No {
	public Presidente elemento; // Conteudo do no.
	public No esq, dir;  // Filhos da esq e dir.

	/**
	 * Construtor da classe.
	 * @param elemento Conteudo do no.
	 */
	public No(Presidente elemento) {
		this(elemento, null, null);
	}

	/**
	 * Construtor da classe.
	 * @param elemento Conteudo do no.
	 * @param esq No da esquerda.
	 * @param dir No da direita.
	 */
	public No(Presidente elemento, No esq, No dir) {
		this.elemento = elemento;
		this.esq = esq;
		this.dir = dir;
	}
}

/**
 * Arvore binaria de pesquisa
 * @author Max do Val Machado
 */
class ArvoreBinaria {
	public static int numComparacoes = 0;
	private No raiz; // Raiz da arvore.

	/**
	 * Construtor da classe.
	 */
	public ArvoreBinaria() {
		raiz = null;
	}

	/**
	 * Metodo publico iterativo para pesquisar elemento.
	 * @param x Elemento que sera procurado.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
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
	 * Metodo publico iterativo para exibir elementos.
	 */
	public void mostrarPre() {
		System.out.print("[ ");
		mostrarPre(raiz);
		System.out.println("]");
	}

	/**
	 * Metodo privado recursivo para exibir elementos.
	 * @param i No em analise.
	 */
	private void mostrarPre(No i) {
		if (i != null) {
			System.out.print(i.elemento + " "); // Conteudo do no.
			mostrarPre(i.esq); // Elementos da esquerda.
			mostrarPre(i.dir); // Elementos da direita.
		}
	}

	/**
	 * Metodo publico iterativo para inserir elemento.
	 * @param x Elemento a ser inserido.
	 * @throws Exception Se o elemento existir.
	 */
	public void inserir(Presidente x) throws Exception {
		raiz = inserir(x, raiz);
	}

	/**
	 * Metodo privado recursivo para inserir elemento.
	 * @param x Elemento a ser inserido.
	 * @param i No em analise.
	 * @return No em analise, alterado ou nao.
	 * @throws Exception Se o elemento existir.
	 */
	private No inserir(Presidente x, No i) throws Exception {
		if (i == null) {
         i = new No(x);

      } else if (x.getNome().compareTo(i.elemento.getNome()) < 0) {
         i.esq = inserir(x, i.esq);

      } else if (x.getNome().compareTo(i.elemento.getNome()) > 0) {
         i.dir = inserir(x, i.dir);

      } else {
         throw new Exception("Erro ao inserir!");
      }

		return i;
	}
}
