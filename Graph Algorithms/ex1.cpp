#include <iostream>
#include <string>
#include <cmath>
using namespace std;

#define DOU_MAX	2147483647.0

class Vertice {
	
	private:
		int x; //coordenadas
		int y;
		double chave; // menor caminho disponivel
		char nome; //letra que representa esse vertice
	
	public:
		double* distancia; // distancia entre os vertices

		Vertice(int n) {
			this->x = 0;
			this->y = 0;
			this->nome = ' ';
			this->chave = DOU_MAX;
			this->distancia = new double[n];
			for (int i = 0; i < n; i++) {
				this->distancia[i] = -1;
			}
		}// fim construtor Vertice
		
		int getX() {
			return this->x;			
		}// fim getX()
		
		int getY() {
			return this->y;			
		}// fim getY()

		double getChave() {
			return this->chave;			
		}// fim getChave()
		
		char getNome() {
			return this->nome;			
		}// fim getNome()
		
		void setX(int x) {
			this->x = x;			
		}// fim setX()
		
		void setY(int y) {
			this->y = y;			
		}// fim setY()

		void setChave(double c) {
			this->chave = c;			
		}// fim setChave()
		
		void setNome(char n) {
			this->nome = n;			
		}// fim setNome()

		void limpaMemoria() {
			delete [] this->distancia;
		}
	
}; //fim classe Vertice

void limpaMemoria(Vertice** v, int n);
void iniciaVertice(Vertice** v, int n);
void calDisVert(Vertice** v, int n);
void ordenaMin(Vertice** v, int n);
double prim(Vertice** v, int n);

int main() {
	
	int entradas;
	cin >> entradas;
	for (int i = 0; i < entradas; i++) {
		int n,nome;
		cin >> n;		
		nome = (int)'a';
		if (n > 0 && n <= 500) {
			Vertice** v = new Vertice*[n];
			iniciaVertice(v,n); // inicializa o vetor de vertices
			for (int j = 0; j < n; j++) {
				int x,y;
				cin >> x;
				cin >> y;
				v[j]->setNome( (char)nome);
				if (x >= 0 && x <= 10000) {
					v[j]->setX(x);
				}// fim if
				if (y >= 0 && y <= 10000) {
					v[j]->setY(y);
				}// fim if
				nome++;				
			}// fim for
			calDisVert(v,n); // calcula a distancia entre os vertices
			v[0]->setChave(0); // zera chave do vertice inicial
			Vertice** arvorePrim = v;
			double compMinTeia = prim(arvorePrim,n); // comprimento minimo de teia
			cout << compMinTeia;
			if (i < (entradas - 1) ) {
				cout << "\n\n";
			}// fim if
			limpaMemoria(v,n);
		}// fim if
	}// fim for	
	
	return 0;
	
}// fim main()

double prim(Vertice** v, int n) {
	
	int aux;
	for (int i = 0; i < (n-1); i++ ) {
		for (int j = 1; j < (n-i); j++) {
			aux = (int)v[j]->getNome() - (int)'a'; // calcula posicao correta do vertice que vai comparar com
			if (v[0]->distancia[aux] < v[j]->getChave() ) {
				v[j]->setChave( v[0]->distancia[aux] );
			}// fim if
		}// fim for
		ordenaMin(v,(n-i));
	}// fim for
	double distMin = 0; //distancia minima
	for (int i = 0; i < n; i++ ) {
		distMin += v[i]->getChave();
	}// fim for
	distMin = round(distMin);
	distMin = distMin / 100;
	
	return distMin;
	
}// fim prim()

void ordenaMin(Vertice** v, int n) {
	
	Vertice* aux;
	aux = v[n-1];
	v[n-1] = v[0];
	v[0] = aux;
	for (int i = n-2; i > 0; i--) {
		if (v[i]->getChave() < v[i-1]->getChave() ) {
			aux = v[i];
			v[i] = v[i-1];
			v[i-1] = aux;		
		}// fim if
	}// fim for
	
}// fim ordenaMin()

void calDisVert(Vertice** v, int n) {
	
	double aux,dist;
	for (int i = 0; i < (n-1); i++) {
		for (int j = (i+1); j < n; j++) {
			aux = pow( (v[i]->getX() - v[j]->getX() ), 2) + pow( (v[i]->getY() - v[j]->getY() ), 2);
			dist = sqrt(aux);
			v[i]->distancia[ (int)v[j]->getNome() - (int)'a' ] = dist;
			v[j]->distancia[ (int)v[i]->getNome() - (int)'a' ] = dist;
		}// fim for
	}// fim for
	
}// fim calDisVert()

void iniciaVertice(Vertice** v, int n) {
	
	for (int i = 0; i < n; i++) {
		v[i] = new Vertice(n);
	}// fim for
	
}// fim iniciaVertice()

void limpaMemoria(Vertice** v, int n) {
	
	for (int i = 0; i < n; i++) {
		v[i]->limpaMemoria();
		delete [] v[i];
	}// fim for
	delete [] v;
	
}// fim limpaMemoria()