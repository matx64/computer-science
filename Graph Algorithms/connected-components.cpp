#include <iostream>
#include <string>
using namespace std;

class Vertice {
	
	private:
		int cor; //0 - branco, 1 - cinza, 2 - preto
		char pai; //letra do vertice pai
		char nome; //letra que representa esse vertice
	
	public:
		Vertice() {
			this->cor = 0; //branco
			this->pai = ' ';
			this->nome = ' ';
		}// fim construtor Vertice
		
		int getCor() {
			return this->cor;			
		}// fim getCor()
		
		char getPai() {
			return this->pai;			
		}// fim getPai()
		
		char getNome() {
			return this->nome;			
		}// fim getNome()
		
		void setCor(int c) {
			this->cor = c;			
		}// fim setCor()
		
		void setPai(char p) {
			this->pai = p;			
		}// fim setPai()
		
		void setNome(char n) {
			this->nome = n;			
		}// fim setNome()
	
}; //fim classe Vertice

class Grafo {
	
	private:
		int qtdVertices;
		int qtdArestas;
		int qtdComponentes;
		
	public:
		bool** adjacencias;
		Vertice** v;
		
		Grafo(int qtdV, int qtdA) {
			this->qtdVertices = qtdV;
			this->qtdArestas = qtdA;
			this->adjacencias = new bool*[qtdV];
			this->v = new Vertice*[qtdV];
			for (int i = 0; i < qtdV; i++) { // inicializa adj e vertices
				this->adjacencias[i] = new bool[qtdV];
				for (int j = 0; j < qtdV; j++) {
					this->adjacencias[i][j] = false;
				}//fim for
				this->v[i] = new Vertice();
				int nome = (int)'a' + i;
				this->v[i]->setNome((char)nome);
			}// fim for
		}// fim construtor Grafo
		
		int getQtdVertices() {
			return this->qtdVertices;			
		}// fim getQtdVertices()
		
		int getQtdArestas() {
			return this->qtdArestas;			
		}// fim getQtdArestas()
		
		int getQtdComponentes() {
			return this->qtdComponentes;			
		}// fim getQtdComponentes()
		
		void setQtdVertices(int qtdV) {
			this->qtdVertices = qtdV;			
		}// fim setQtdVertices()
		
		void setQtdArestas(int qtdA) {
			this->qtdArestas = qtdA;			
		}// fim setQtdArestas()
		
		void setQtdComponentes(int qtdC) {
			this->qtdComponentes = qtdC;			
		}// fim setQtdComponentes()

		void limpaMemoria() {
			for (int i = 0; i < this->getQtdVertices(); i++) {
				delete [] this->adjacencias[i];
				delete [] this->v[i];	
			}// fim for	
		}// fim limpaMemoria()

}; //fim classe Grafo

void dfs(Grafo* g);
void dsfVisit(Vertice* ver, Grafo* g);

int main() {
	
	int qtdEntrada;
	cin >> qtdEntrada;
	for (int i = 0; i < qtdEntrada; i++) {
		int vertices, arestas;
		cin >> vertices;
		cin >> arestas;
		Grafo* g = new Grafo(vertices, arestas);
		for (int j = 0; j < arestas; j++) {
			char a1, a2;
			cin >> a1;
			cin >> a2;
			g->adjacencias[(int)a1 - 'a'][(int)a2 - 'a'] = true;
			g->adjacencias[(int)a2 - 'a'][(int)a1 - 'a'] = true;
		}// fim for
		cout << "Case #" << i+1 << ":\n";
		dfs(g);	
		cout << g->getQtdComponentes() << " connected components\n";
		if (i < (qtdEntrada - 1)) {
			cout << endl;
		}// fim if
		g->limpaMemoria();
		delete g;
	}// fim for
	
	return 0;
	
}// fim main()

void dfs(Grafo* g) {
	
	int componentes = 0;
	for (int i = 0; i < g->getQtdVertices(); i++) {
		if (g->v[i]->getCor() == 0) {
			componentes++;
			dsfVisit(g->v[i],g);
			cout << endl;
		}// fim if
	}// fim for
	g->setQtdComponentes(componentes);
	
}// fim dfs()

void dsfVisit(Vertice* ver, Grafo* g) {
	
	ver->setCor(1); // seta para cinza
	cout << ver->getNome() << ',';
	for (int i = 0; i < g->getQtdVertices(); i++) {
		if ( g->adjacencias[( (int)ver->getNome() - 'a')][i] && g->v[i]->getCor() == 0) {
			g->v[i]->setPai(ver->getNome());
			dsfVisit(g->v[i],g);
		}// fim if
	}// fim for
	ver->setCor(2); // seta para preto
	
}// fim dsfVisit()