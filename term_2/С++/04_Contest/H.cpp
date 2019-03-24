#include <iostream>
#include <vector>

//FILE* in = fopen("gcd.in", "r");
//FILE* out = fopen("gcd.out", "w");

int tab[9][9];

void fillTab() {
    for (auto &i : tab)
    {
        for (int &j : i)
        {
            scanf("%d",&j);
        }
    }
}

bool rowCheck() {
    for (auto &i : tab)
    {
        int check[9] = {};
        for (int j : i)
        {
            if (check[j - 1] == 0)
                check[j - 1] = 1;
            else
                return false;
        }
    }
    return true;
}

bool columnCheck() {
    for (int i = 0; i < 9; ++i) {
        int check[9] = {};
        for (int j = 0; j < 9; ++j) {
            if (check[tab[j][i] - 1] == 0)
                check[tab[j][i] - 1] = 1;
            else
                return false;
        }
    }
    return true;
}

bool sectorsCheck() {
    for (int i = 0; i < 9; i+=3) {
        for (int j = 0; j < 9; j+=3) {
            int check[9] = {};
            for (int ik = i; ik < i+3; ++ik) {
                for (int jk = j; jk < j+3; ++jk) {
                    if (check[tab[ik][jk] - 1] == 0)
                        check[tab[ik][jk] - 1] = 1;
                    else
                        return false;
                }
            }
        }
    }
    return true;
}

int main()
{
    fillTab();
    printf ((columnCheck() && rowCheck() && sectorsCheck()) ? "Yes" : "No");
    return 0;
}