#include <iostream>
#include <string>
using namespace std;

class Vertice {
	
	private:
		int cor; //0 - branco, 1 - cinza, 2 - preto
		int pai; //numero do vertice pai
		int cidade; //numero que representa esse vertice
	
	public:
		Vertice() {
			this->cor = 0; //branco
			this->pai = 0;
			this->cidade = 0;
		}// fim construtor Vertice
		
		int getCor() {
			return this->cor;			
		}// fim getCor()
		
		int getPai() {
			return this->pai;			
		}// fim getPai()
		
		char getCidade() {
			return this->cidade;			
		}// fim getCidade()
		
		void setCor(int c) {
			this->cor = c;			
		}// fim setCor()
		
		void setPai(int p) {
			this->pai = p;			
		}// fim setPai()
		
		void setCidade(int c) {
			this->cidade = c;			
		}// fim setCidade()
	
}; //fim classe Vertice

class Grafo {
	
	private:
		int qtdVertices;
		int qtdArestas;
		
	public:
		int** adjacencias;
        int* totalPassagem;
		Vertice** v;
		
		Grafo(int qtdV, int qtdA) {
			this->qtdVertices = qtdV;
			this->qtdArestas = qtdA;
			this->adjacencias = new int*[qtdV];
            this->totalPassagem = new int[qtdA];
			this->v = new Vertice*[qtdV];
            for (int i = 0; i < qtdA; i++) {
                this->totalPassagem[i] = 0;
            }// fim for
			for (int i = 0; i < qtdV; i++) { // inicializa adj e vertices
				this->adjacencias[i] = new int[qtdV];
				for (int j = 0; j < qtdV; j++) {
					this->adjacencias[i][j] = 0;
				}//fim for
				this->v[i] = new Vertice();
				this->v[i]->setCidade(i+1);
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
                v[i]->setCor(0);
            }// fim for
        }// fim setTudoBranco()

        void ordenaPassagem(int n) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < (n-i-1); j++) {
                    if (this->totalPassagem[j] > this->totalPassagem[j+1]) {
                        int aux = totalPassagem[j];
                        totalPassagem[j] = totalPassagem[j+1];
                        totalPassagem[j+1] = aux;
                    }// fim if
                }//fim for
            }// fim for
        }// fim ordenaPassagem()

		void limpaMemoria() {
			for (int i = 0; i < this->getQtdVertices(); i++) {
				delete [] this->adjacencias[i];
				delete [] this->v[i];	
			}// fim for	
            delete [] this->totalPassagem;
		}// fim limpaMemoria()

}; //fim classe Grafo

int dfs(Grafo* g);
int dsfVisit(Vertice* ver, Grafo* g, int c, int valor);
bool verificaAssentos(Grafo* g, int amigos, int assentos);

int main() {
	
    int k = 3;
//    cin >> k;
    for (int z = 0; z < k; z++) {
        int cidades,rotas;
        cin >> cidades >> rotas;
        if (cidades >= 2 && cidades <= 100 && rotas >= 1 && rotas <= 5000) {
            Grafo* g = new Grafo(cidades,rotas);
            for (int i = 0; i < rotas; i++) {
                int a,b,preco;
                cin >> a >> b >> preco;
                if (a >= 1 && a <= cidades && b >= 1 && b <= cidades) {
                    if (g->adjacencias[a-1][b-1] == 0) {
                        g->adjacencias[a-1][b-1] = preco;
                        g->adjacencias[b-1][a-1] = preco;
                    }// fim if                
                }// fim if
            }// fim for
            int amigos,assentos;
            cin >> amigos >> assentos;
            bool ehPossivel = verificaAssentos(g,amigos,assentos);
            cout << "Instancia " << z+1 << "\n\n";
            if (ehPossivel) {
                int caminhos = dfs(g);
                int valorTotal = 0;
                for (int i = 0; i < caminhos; i++) {
                    if (amigos >= assentos) {
                        valorTotal += (assentos * g->totalPassagem[i]);
                        amigos -= assentos;
                    }// fim if
                    else {
                        valorTotal += (amigos * g->totalPassagem[i]);
                        i = caminhos;
                    }//fim else
                }// fim for
                cout << valorTotal << endl;
            }// fim if
            else {
                cout << "impossivel" << endl;
            }// fim else
            g->limpaMemoria();
		    delete g;
            if (z < k-1) {
                cout << "\n\n\n";
            }// fim if
            else {
                cout << endl;
            }// fim else
        }// fim if
    }// fim for
	
	return 0;
	
}// fim main()

bool verificaAssentos(Grafo* g, int amigos, int assentos) {

    bool ehPossivel = false;
    int cont = 0;
    for (int i = 0; i < g->getQtdVertices(); i++) {
        if (g->adjacencias[0][i] > 0) {
            cont++;
        }// fim if
    }// fim for
    int total = assentos * cont; // quantos assentos tem no total para viajar da cidade 1
    if (total >= amigos) {
        ehPossivel = true;
    }// fim if

    return ehPossivel;

}// fim verificaAssentos()

int dfs(Grafo* g) {
	
    int caminho = 0;
	for (int i = 1; i < g->getQtdVertices(); i++) {
		if (g->adjacencias[0][i] > 0) {
            if (i+1 == g->getQtdVertices() ){
                g->totalPassagem[caminho] = g->adjacencias[0][i];
                caminho++;
            }// fim if
            else {
                g->v[0]->setCor(1);
                caminho = dsfVisit(g->v[i],g,caminho,g->adjacencias[0][i]);
                g->setTudoBranco();
            }// fim else            
		}// fim if
	}// fim for
    g->ordenaPassagem(caminho);

    return caminho;
	
}// fim dfs()

int dsfVisit(Vertice* ver, Grafo* g, int c, int valor) {
	
    int total = valor;
	ver->setCor(1); // seta para cinza
	for (int i = 0; i < g->getQtdVertices(); i++) {
		if ( g->adjacencias[(ver->getCidade() - 1)][i] > 0 && g->v[i]->getCor() == 0) {
            total += g->adjacencias[(ver->getCidade() - 1)][i];
            g->v[i]->setPai(ver->getCidade());
            if ((i+1) == g->getQtdVertices()) { // se ultima cidade/cidade destino
                g->totalPassagem[c] = total;
                c++;
            }// fim if
            else {
                c = dsfVisit(g->v[i],g,c,total); // se nao for a cidade final, continua a procura
            }// fim else			
		}// fim if
	}// fim for
	ver->setCor(2); // seta para preto

    return c;
	
}// fim dsfVisit()