#include <string>
#include <vector>
#include <iostream>

using namespace std;

int players[200002];
int guilds[200002];
int exp[200002];

int get_guild(int v) {
    if (v == players[v])
        return v;
    return get_guild(players[v]);
}

void filler(int n) {
    for (int i = 1; i < n + 1; i++) {
        players[i] = i;
        guilds[i] = 0;
        exp[i] = 0;
    }
}

int main() {
    ios_base::sync_with_stdio(false); cout.tie(nullptr); cin.tie(nullptr);
    int n, m, a, b, i1, experience, t;
    cin >> n >> m;
    vector<int> currentAnswer;
    string currentCommand;
    filler(n);
    for (int i = 0; i < m; i++) {
        cin>> currentCommand;
        if ("join" == currentCommand) {
            cin >> a >> b;
            a = get_guild(a);
            b = get_guild(b);
            if (a != b) {
                if (guilds[a] < guilds[b]) {
                    t = a;
                    a = b;
                    b = t;
                }
                exp[b] -= exp[a];
                players[b] = a;
                if (guilds[a] == guilds[b]) {
                    ++guilds[a];
                }
            }
        }
        else if ("get" == currentCommand){
            cin >> a;
            experience = 0;
            while (a != players[a]) {
                experience += exp[a];
                a = players[a];
            }
            experience += exp[a];
            currentAnswer.push_back(experience);
        }
        else if ("add" == currentCommand) {
            cin >> a>> i1;
            a = get_guild(a);
            exp[a] += i1;
        }
    }
    for (int i : currentAnswer) {
        cout << i << endl;
    }
}
