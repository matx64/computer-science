#include <iostream>
#include <string>
using namespace std;

class Vertice {
	
	private:
		int corPrim; //0 - branco, 1 - cinza, 2 - preto
		int pai; //numero do vertice pai
		int nome; //numero que representa esse vertice
        char subGrafo;
        int corVertice;
	
	public:
		Vertice() {
			this->corPrim = 0; //branco
			this->pai = 0;
			this->nome = 0;
            this->subGrafo = ' ';
            this->corVertice = 0;
		}// fim construtor Vertice
		
		int getCorPrim() {
			return this->corPrim;			
		}// fim getCorPrim()
		
		int getPai() {
			return this->pai;			
		}// fim getPai()
		
		int getNome() {
			return this->nome;			
		}// fim getNome()

        char getSubGrafo() {
            return this->subGrafo;
        }// fim setSubGrafo()

        int getCorVertice() {
			return this->corVertice;			
		}// fim getCorVertice()
		
		void setCorPrim(int cp) {
			this->corPrim = cp;			
		}// fim setCorPrim()
		
		void setPai(int p) {
			this->pai = p;			
		}// fim setPai()
		
		void setNome(int n) {
			this->nome = n;			
		}// fim setNome()

        void setSubGrafo(char sg) {
            this->subGrafo = sg;
        }// fim setSubGrafo()

        void setCorVertice(int cv) {
			this->corVertice = cv;			
		}// fim setCorVertice()
	
}; //fim classe Vertice

class Grafo {
	
	private:
		int qtdVertices;
		int qtdArestas;
		int qtdComponentes;
        int qtdCores;
		
	public:
		bool** adjacencias;
		Vertice** v;
		
		Grafo(int qtdV, int qtdA, int qtdC) {
			this->qtdVertices = qtdV;
			this->qtdArestas = qtdA;
            this->qtdCores = qtdC;
			this->adjacencias = new bool*[qtdV];
			this->v = new Vertice*[qtdV];
			for (int i = 0; i < qtdV; i++) { // inicializa adj e vertices
				this->adjacencias[i] = new bool[qtdV];
				for (int j = 0; j < qtdV; j++) {
					this->adjacencias[i][j] = false;
				}//fim for
				this->v[i] = new Vertice();
				this->v[i]->setNome(i+1);
			}// fim for
		}// fim construtor Grafo
		
		int getQtdVertices() {
			return this->qtdVertices;			
		}// fim getQtdVertices()
		
		int getQtdArestas() {
			return this->qtdArestas;			
		}// fim getQtdArestas()

        int getQtdCores() {
			return this->qtdCores;			
		}// fim getQtdCores()
		
		int getQtdComponentes() {
			return this->qtdComponentes;			
		}// fim getQtdComponentes()
		
		void setQtdVertices(int qtdV) {
			this->qtdVertices = qtdV;			
		}// fim setQtdVertices()
		
		void setQtdArestas(int qtdA) {
			this->qtdArestas = qtdA;			
		}// fim setQtdArestas()

        void setQtdCores(int qtdC) {
			this->qtdCores = qtdC;			
		}// fim setQtdCores()
		
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
void dsfVisit(Vertice* ver, Grafo* g, char sg);
bool insereArestaConexa(Grafo* g, int ai);
bool insereArestaDescon(Grafo* g, int ai, int an);

int main() {
	
	int qtdEntrada;
	cin >> qtdEntrada;
    if (qtdEntrada > 0 && qtdEntrada < 70) {
        for (int i = 0; i < qtdEntrada; i++) {
            int vertices, arestas, aInseridas, nCores; //arestasInseridas e numeroCores
            cin >> vertices >> arestas >> aInseridas >> nCores;
            if (vertices >= 1 && vertices <= 1000 && arestas >= 0 && arestas <= 100000 && aInseridas >= 0 && aInseridas <= 1000000 && nCores >= 1 && nCores <= 1000) {
                Grafo* g = new Grafo(vertices,arestas,nCores);
                for (int j = 0; j < vertices; j++) {
                    int cor;
                    cin >> cor;
                    if (cor >= 1 && cor <= nCores) {
                        g->v[i]->setCorVertice(cor);
                    }//fim if                    
                }//fim for
                for (int j = 0; j < arestas; j++) {
                    int v1,v2;
                    cin >> v1 >> v2;
                    if (v1 >= 1 && v1 <= vertices && v2 >= 1 && v2 <= vertices) {
                        g->adjacencias[v1-1][v2-1] = true;
                        g->adjacencias[v2-1][v1-1] = true;
                    }//fim if
                }//fim for
                dfs(g);
                if ( (arestas + aInseridas) >= (vertices - 1) ) {
                    bool ehPossivel;
                    if ( g->getQtdComponentes() > 1) {
                        int aNecessarias = g->getQtdComponentes() - 1; //quantidade de arestas necessarias para o grafo se tornar conexo
                        ehPossivel = insereArestaDescon(g,aInseridas,aNecessarias);
                    }// fim if
                    else {
                        ehPossivel = insereArestaConexa(g,aInseridas);
                    }//fim else
					if (ehPossivel) {
						cout << "Y" << endl;
					}//fim if
					else {
						cout << "N" << endl;
					}// fim if
                }// fim if
                else {
                    cout << "N" << endl;
                }// fim else
				g->limpaMemoria();
				delete g;
            }// fim if
			if (i < (qtdEntrada - 1) ) {
				cout << endl;
			}//fim if
        }// fim for
        
    }// fim if
	
	return 0;
	
}// fim main()

//tenta inserir as arestas necessarias para deixar o grafo conexo (caso seja desconexo)
bool insereArestaDescon(Grafo* g, int ai, int an) {

	bool sucesso = false;
	for (int i = 0; i < g->getQtdVertices(); i++) {
		for (int j = (i+1); j < g->getQtdVertices(); j++) {
			if (g->adjacencias[i][j] == false && g->v[i]->getSubGrafo() != g->v[j]->getSubGrafo() && g->v[i]->getCorVertice() != g->v[j]->getCorVertice() ) {
				g->adjacencias[i][j] = true;
				g->adjacencias[j][i] = true;
				ai--;
				an--;
				if (ai == 0) {
					i = g->getQtdVertices();
					j = g->getQtdVertices();
					sucesso = true;
				}// fim if
				else if (an == 0) {
					sucesso = insereArestaConexa(g,ai);
					i = g->getQtdVertices();
					j = g->getQtdVertices();
				}// fim else if
			} // fim if
		}//fim for
	}// fim for

	return sucesso;

}// fim insereArestaDescon

//tenta inserir as arestas sugeridas e manter o grafo conexo, simples e sem vertices de cores iguais adjacentes
bool insereArestaConexa(Grafo* g, int ai) {

	bool sucesso = false;
	for (int i = 0; i < g->getQtdVertices(); i++) {
		for (int j = (i+1); j < g->getQtdVertices(); j++) {
			if (g->adjacencias[i][j] == false && g->v[i]->getCorVertice() != g->v[j]->getCorVertice() ) {
				g->adjacencias[i][j] = true;
				g->adjacencias[j][i] = true;
				ai--;
				if (ai == 0) {
					i = g->getQtdVertices();
					j = g->getQtdVertices();
					sucesso = true;
				}// fim if
			} // fim if
		}//fim for
	}// fim for

	return sucesso;

}// fim insereArestaConexa

void dfs(Grafo* g) {
	
	int componentes = 0;
    char subGrafo = 'a';
	for (int i = 0; i < g->getQtdVertices(); i++) {
		if (g->v[i]->getCorPrim() == 0) {
			componentes++;
			dsfVisit(g->v[i],g,subGrafo);
            subGrafo = (char)( (int)subGrafo + 1); // caso haja mais de 1 componente, muda a letra do proximo subgrafo
		}// fim if
	}// fim for
	g->setQtdComponentes(componentes);
	
}// fim dfs()

void dsfVisit(Vertice* ver, Grafo* g, char sg) {
	
	ver->setCorPrim(1); // seta para cinza
    ver->setSubGrafo(sg);
	for (int i = 0; i < g->getQtdVertices(); i++) {
		if ( g->adjacencias[(ver->getNome() - 1)][i] && g->v[i]->getCorPrim() == 0) {
			g->v[i]->setPai(ver->getNome());
			dsfVisit(g->v[i],g,sg);
		}// fim if
	}// fim for
	ver->setCorPrim(2); // seta para preto
	
}// fim dsfVisit()