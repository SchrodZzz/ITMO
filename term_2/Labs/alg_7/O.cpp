#include <utility>

#include <utility>

#include <iostream>
#include <vector>
#include <fstream>

int const N = 10007;

std::ifstream cin("multimap.in");
std::ofstream cout("multimap.out");

class hashMap
{
    struct Node
    {
        std::string key, val;
        Node* next, * a, * b;

        Node(std::string key, std::string val) : key(std::move(key)), val(std::move(val)),
                                                 next(nullptr), a(nullptr), b(nullptr)
        {}
    };

    int const MOD = 1009;
    std::vector<Node*> data;

    Node* tmp = nullptr;

    int getHash(const std::string& str)
    {
        int b = 1;
        int res = 0;
        for (char i : str)
        {
            res += (i * b) % MOD;
            res %= MOD;
            b *= 7;
            b %= MOD;
        }
        return res;
    }

public:
    hashMap()
    {
        data.assign(MOD, nullptr);
    }

    void put(const std::string& key, const std::string& val)
    {
        int idx = getHash(key);
        Node* cur = data[idx];
        if (cur == nullptr)
        {
            data[idx] = new Node(key, val);
            cur = data[idx];
        }
        else
        {
            while (cur->next != nullptr && cur->key != key)
            {
                cur = cur->next;
            }
            if (cur->key == key)
            {
                cur->val = val;
                return;
            }
            cur->next = new Node(key, val);
            cur = cur->next;
        }
        if (tmp != nullptr)
        {
            tmp->a = cur;
            cur->b = tmp;
        }
        tmp = cur;
    }

    void remove(const std::string& key)
    {
        int idx = getHash(key);
        Node* cur = data[idx];
        Node* prv = nullptr;
        while (cur != nullptr && cur->key != key)
        {
            prv = cur;
            cur = cur->next;
        }
        if (cur == nullptr)
        {
            return;
        }
        if (prv == nullptr)
        {
            data[idx] = cur->next;
        }
        else
        {
            prv->next = cur->next;
        }

        if (tmp == cur)
        {
            tmp = cur->b;
            if (tmp != nullptr)
            {
                tmp->a = nullptr;
            }
        }
        else
        {
            if (cur->b != nullptr)
            {
                cur->b->a = cur->a;
            }

            if (cur->a != nullptr)
            {
                cur->a->b = cur->b;
            }
        }
    }

    void removeAll(const std::string& key)
    {
        Node* cur = tmp;
        while (cur != nullptr)
        {
            if (cur->val == key)
            {
                Node* b = cur->b;
                Node* a = cur->a;
                if (b != nullptr)
                {
                    b->a = a;
                }
                if (a != nullptr)
                {
                    a->b = b;
                }
                if (cur == tmp)
                {
                    tmp = cur->b;
                }
            }
            cur = cur->b;
        }
    }

    std::string get(const std::string& key)
    {
        Node* cur = tmp;
        std::string res;
        int cnt = 0;
        while (cur != nullptr)
        {
            if (cur->val == key)
            {
                ++cnt;
                res += cur->key + " ";
            }
            cur = cur->b;
        }
        res = std::to_string(cnt) + " " + res;
        return res;
    }
};

std::vector<hashMap> uberData(N);
std::vector<int> bs(20);

int getUberHash(std::string str)
{
    int res = 0;
    for (size_t i = 0; i < str.length(); ++i)
    {
        res += (str[i] * bs[i]) % N;
        res %= N;
    }
    return res;
}

void solve()
{
    bs[0] = 1;
    for (size_t i = 1; i < 20; ++i)
    {
        bs[i] = bs[i - 1] * 7;
        bs[i] %= N;
    }
    std::string cmd, key, val;
    while (cin >> cmd)
    {
        cin >> key;
        if (cmd == "put")
        {
            cin >> val;
            uberData[getUberHash(key)].put(val, key);
        }
        else if (cmd == "delete")
        {
            cin >> val;
            uberData[getUberHash(std::move(key))].remove(val);
        }
        else if (cmd == "deleteall")
        {
            uberData[getUberHash(key)].removeAll(key);
        }
        else if (cmd == "get")
        {
            cout << uberData[getUberHash(key)].get(key) << '\n';
        }
    }
}


int main()
{
    solve();
    return 0;
}