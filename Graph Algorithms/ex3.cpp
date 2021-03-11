#include <iostream>
#include <string>
using namespace std;

class Vertice {
	
	private:
		int cor; //0 - branco, 1 - cinza, 2 - preto
		char pai; //letra do vertice pai
		char nome; //letra que representa esse vertice
	
	public:
        bool* caminhos;

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
                this->v[i] = new Vertice();
                this->v[i]->caminhos = new bool[qtdV];
				for (int j = 0; j < qtdV; j++) {
					this->adjacencias[i][j] = false;
                    this->v[i]->caminhos[j] = false;
				}//fim for
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
		
		void setQtdVertices(int qtdV) {
			this->qtdVertices = qtdV;			
		}// fim setQtdVertices()
		
		void setQtdArestas(int qtdA) {
			this->qtdArestas = qtdA;			
		}// fim setQtdArestas()

        void setTudoBranco() {
            for (int i = 0; i < this->getQtdVertices(); i++) {
                this->v[i]->setCor(0);
            }// fim for
        }// fim setTudoBranco()

        void setCaminho(Vertice* ver) {
            for (int i = 0; i < this->getQtdVertices(); i++) {
                if (this->v[i]->getCor() == 2) {
                    ver->caminhos[i] = true;
                }// fim if
            }// fim for
        }// fim setCaminho()

		void limpaMemoria() {
			for (int i = 0; i < this->getQtdVertices(); i++) {
				delete [] this->adjacencias[i];
                delete [] this->v[i]->caminhos;
				delete [] this->v[i];	
			}// fim for	
		}// fim limpaMemoria()

}; //fim classe Grafo

bool dfs(Grafo* g);
void dsfVisit(Vertice* ver, Grafo* g);

int main() {
	
	int especies, relacoes;
	cin >> especies >> relacoes;
    if (especies >= 1 && especies <= 100000 && relacoes >= 1 && relacoes <= 1000000) {
        bool ehBolado;
        Grafo* g = new Grafo(especies,relacoes);
        for (int i = 0; i < relacoes; i++) {            
            int u,v;
            cin >> u >> v;
            if (u >= 1 && u <= especies && v >= 1 && v <= especies) {
                g->adjacencias[u-1][v-1] = true;
            }// fim if
        }//fim for
        if (especies == 1) {
            if (g->adjacencias[0][0]) {
                ehBolado = true;
            }// fim if
            else {
                ehBolado = false;
            }//fim else
        }// fim if
        else {
            ehBolado = dfs(g);
        }// fim else
        if (ehBolado) {
            cout << "Bolada";
        }// fim if
        else {
            cout << "Nao Bolada";
        }//fim else
        g->limpaMemoria();
	    delete g;
    }// fim if
    else {
            cout << "Nao Bolada";
    }//fim else 

	return 0;
	
}// fim main()

bool dfs(Grafo* g) {
	
    bool ehBolado = true;
	for (int i = 0; i < g->getQtdVertices(); i++) {
			dsfVisit(g->v[i],g);
            g->setCaminho(g->v[i]);
            g->setTudoBranco();
	}// fim for

    for (int i = 0; i < g->getQtdVertices(); i++) {
        for (int j = (i+1); j < (g->getQtdVertices() - 1); j++) {
            if (g->v[i]->caminhos[j] == false && g->v[j]->caminhos[i] == false) {
                ehBolado = false;
                j = g->getQtdVertices();
                i = g->getQtdVertices();
            }// fim if
        }// fim for
    }// fim for

    return ehBolado;
	
}// fim dfs()

void dsfVisit(Vertice* ver, Grafo* g) {
	
	ver->setCor(1); // seta para cinza
	for (int i = 0; i < g->getQtdVertices(); i++) {
		if ( g->adjacencias[( (int)ver->getNome() - 'a')][i] && g->v[i]->getCor() == 0) {
			g->v[i]->setPai(ver->getNome());
			dsfVisit(g->v[i],g);
		}// fim if
	}// fim for
	ver->setCor(2); // seta para preto
	
}// fim dsfVisit()