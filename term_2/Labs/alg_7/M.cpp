#include <utility>

#include <fstream>
#include <string>
#include <vector>
#include <iostream>

struct Node
{
    std::string val, key;
    Node* next;

    Node(std::string key, std::string val) : key(std::move(key)), val(std::move(val)), next(0)
    {}
};

int const MOD = 10007;
Node* data[MOD];

struct CustomMap
{

    CustomMap() = default;

    static int getHash(const std::string& str)
    {
        int b = 1;
        int hash = 0;
        for (char i : str)
        {
            hash += (i * b) % MOD;
            hash %= MOD;
            b *= 43;
            b %= MOD;
        }
        return hash;
    }

    void put(const std::string& key, const std::string& val)
    {
        int idx = getHash(key);
        Node* cur = data[idx];
        if (cur == nullptr)
        {
            data[idx] = new Node(key, val);
            return;
        }

        while (cur->next != nullptr && cur->key != key)
        {
            cur = cur->next;
        }

        if (cur->key == (key))
        {
            cur->val = val;
            return;
        }
        cur->next = new Node(key, val);
    }

    std::string get(const std::string& key)
    {
        int idx = getHash(key);
        Node* cur = data[idx];
        while (cur != nullptr && cur->key != (key))
        {
            cur = cur->next;
        }
        if (cur == nullptr)
        {
            return "none";
        }
        else
        {
            return cur->val;
        }
    }

    void remove(const std::string& key)
    {
        int idx = getHash(key);
        Node* cur = data[idx];
        Node* prv = nullptr;
        while (cur != nullptr && cur->key != (key))
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
            return;
        }
        prv->next = cur->next;
    }
};

void solve()
{
    std::ifstream cin("map.in");
    std::ofstream cout("map.out");
    auto* map = new CustomMap();
    std::string cmd, key, val;
    while (cin >> cmd)
    {
        cin >> key;
        if (cmd == "put")
        {
            cin >> val;
            map->put(key, val);
        }
        else if (cmd == "delete")
        {
            map->remove(key);
        }
        else
        {
            cout << map->get(key) << '\n';
        }
    }
}


int main()
{
    solve();
    return 0;
}