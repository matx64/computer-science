#include <iostream>
#include <string>
using namespace std;

int main () {

    string s;
    getline(cin,s);
    while (s.compare("FIM") != 0) {
        int i = 0;
        int j = (s.length() - 1);
        bool palindromo = true;
        while (i <= j) {
            if ( (s.at(i) >= 'a' && s.at(i) <= 'z') || (s.at(i) >= 'A' && s.at(i) <= 'Z') ) {
                if (s.at(i) >= 'A' && s.at(i) <= 'Z') {
                    s.at(i) = s.at(i) + 32;
                }//fim if
                if ( (s.at(j) >= 'a' && s.at(j) <= 'z') || (s.at(j) >= 'A' && s.at(j) <= 'Z') ) {
                    if (s.at(j) >= 'A' && s.at(j) <= 'Z') {
                        s.at(j) = s.at(j) + 32;
                    }//fim if
                    if (s.at(i) != s.at(j)) {
                        cout << "NAO" << endl;
                        i = j+1;
                        palindromo = false;
                    }//fim if
                    else {
                    i++;
                    j--;
                    }//fim else                    
                }//fim if
                else {
                    j--;
                }//fim else
            }// fim if
            else {
                i++;
            }//fim else
        }// fim while
        if (palindromo) {
            cout << "SIM" << endl;
        }// fim if        
        getline(cin,s);
    }// fim while

    return 0;

}// fim main()