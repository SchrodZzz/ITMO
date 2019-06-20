#include <utility>

#include <utility>

#include <fstream>
#include <string>
#include <vector>
#include <iostream>

struct Node
{
    std::string val, key;
    Node* next, * a, * b;

    Node(std::string key, std::string val) : key(std::move(key)), val(std::move(val)),
                                             next(nullptr), a(nullptr), b(nullptr)
    {}
};

int const MOD = 10007;
Node* data[MOD];

Node* tmp = nullptr;

struct CustomMap
{

    CustomMap() = default;

    static Node* find(std::string key)
    {
        int hash = getHash(key);
        Node* cur = data[hash];

        while (cur != nullptr && cur->key != key)
        {
            cur = cur->next;
        }

        return cur;
    }

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

    static void put(const std::string& key, const std::string& val)
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

            if (cur->key == (key))
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

    static std::string get(const std::string& key)
    {
        Node* cur = find(key);
        if (cur == nullptr)
        {
            return "none";
        }
        else
        {
            return cur->val;
        }
    }

    static void remove(const std::string& key)
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

    static std::string next(std::string key)
    {
        Node* cur = find(std::move(key));
        if (cur == nullptr || cur->a == nullptr)
        {
            return "none";
        }
        else
        {
            return cur->a->val;
        }
    }

    static std::string prev(const std::string& key)
    {
        int idx = getHash(key);
        Node* cur = data[idx];
        while (cur != nullptr && cur->key != key)
        {
            cur = cur->next;
        }
        if (cur == nullptr || cur->b == nullptr)
        {
            return "none";
        }
        else
        {
            return cur->b->val;
        }
    }
};

void solve()
{
    std::ifstream cin("linkedmap.in");
    std::ofstream cout("linkedmap.out");
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
        else if (cmd == "get")
        {
            cout << map->get(key) << '\n';
        }
        else if (cmd == "prev")
        {
            cout << map->prev(key) << '\n';
        }
        else
        {
            cout << map->next(key) << '\n';
        }
    }
}


int main()
{
    solve();
    return 0;
}