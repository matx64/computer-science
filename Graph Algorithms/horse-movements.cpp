#include <bits/stdc++.h>

using namespace std;

int xx,yy,x,y,matriz[10][10];

struct cord{
    int x;
    int y;
    int i;
};

vector<cord> dfs;

void coloca(int a,int b,int c){
        cord aux;
        c++;

        aux.x=a+2;
        aux.y=b+1;
        aux.i=c;

        if(aux.x>0 && aux.x<9 && aux.y>0 && aux.y<9)
            dfs.push_back(aux);

        aux.x=a+1;
        aux.y=b+2;
        aux.i=c;

        if(aux.x>0 && aux.x<9 && aux.y>0 && aux.y<9)
            dfs.push_back(aux);

        aux.x=a-2;
        aux.y=b+1;
        aux.i=c;

        if(aux.x>0 && aux.x<9 && aux.y>0 && aux.y<9)
            dfs.push_back(aux);

        aux.x=a-1;
        aux.y=b+2;
        aux.i=c;

        if(aux.x>0 && aux.x<9 && aux.y>0 && aux.y<9)
            dfs.push_back(aux);

        aux.x=a+2;
        aux.y=b-1;
        aux.i=c;

        if(aux.x>0 && aux.x<9 && aux.y>0 && aux.y<9)
            dfs.push_back(aux);

        aux.x=a+1;
        aux.y=b-2;
        aux.i=c;

        if(aux.x>0 && aux.x<9 && aux.y>0 && aux.y<9)
            dfs.push_back(aux);

        aux.x=a-2;
        aux.y=b-1;
        aux.i=c;

        if(aux.x>0 && aux.x<9 && aux.y>0 && aux.y<9)
            dfs.push_back(aux);

        aux.x=a-1;
        aux.y=b-2;
        aux.i=c;

        if(aux.x>0 && aux.x<9 && aux.y>0 && aux.y<9)
            dfs.push_back(aux);
}

void dfsF(){

    while(dfs.size()>0){
        cord aux = dfs.back();

        dfs.pop_back();

        if(matriz[aux.x][aux.y]==0 || matriz[aux.x][aux.y]>aux.i){
            matriz[aux.x][aux.y]=aux.i;

            if(aux.x!=xx && aux.y!=y)           
                coloca(aux.x,aux.y,aux.i);          
        }
    }
}

int main(){
    char c1,c2;

    while(scanf("\n%c%d %c%d",&c1,&y,&c2,&yy)!=EOF){
        char aa=c1,bb=c2;
        x = ((int) c1)-96;
        xx = ((int) c2)-96;

        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
                matriz[i][j]=0;

        cord au;

        au.x=x;
        au.y=y;
        au.i=0;

        dfs.push_back(au);

        coloca(x,y,0);

        dfsF();

        printf("To get from %c%d to %c%d takes %d knight moves.\n", aa,y,bb,yy,matriz[xx][yy]);
    }

    return 0;
}