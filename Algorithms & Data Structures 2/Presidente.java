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