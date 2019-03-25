#include <iostream>

#define MAX_N 500001

struct Node
{
    int lt;
    int rt;
    int val;
    int setVal = -1;
    int trueVal = 0;
    bool hasL = false;
    bool hasR = false;
    Node* lChildren;
    Node* rChildren;

    Node(int a, int b)
    {
        lt = a;
        rt = b;
        val = trueVal;
    }

    void build(int mid) {
        if (!hasL)
        {
            lChildren = new Node(lt, mid);
            hasL = true;
        }
        if (!hasR)
        {
            rChildren = new Node(mid + 1, rt);
            hasR = true;
        }
    }

    void setter () {
        if (setVal != -1)
        {
            lChildren->trueVal = rChildren->trueVal = trueVal / 2;
            lChildren->val = rChildren->val = lChildren->setVal = rChildren->setVal = ((trueVal != 0) ? 1 : 0);
            setVal = -1;
        }
    }

    int get(int l, int r)
    {
        int mid = (lt + rt) / 2;
        if (l == lt && r == rt)
        {
            return trueVal;
        }
        build(mid);
        setter();
        if (r <= mid)
        {
            return lChildren->get(l, r);
        }
        else if (l > mid)
        {
            return rChildren->get(l, r);
        }
        else
        {
            return lChildren->get(l, mid) + get(mid + 1, r);
        }
    }

    void set(int l, int r, int d)
    {
        int mid = (lt + rt) / 2;
        if (l == lt && r == rt)
        {
            trueVal = d * (rt - lt + 1);
            setVal = d;
            val = d;
            return;
        }
        build(mid);
        setter();
        if (r <= mid)
        {
            lChildren->set(l, r, d);
        }
        else if (l > mid)
        {
            rChildren->set(l, r, d);
        }
        else
        {
            lChildren->set(l, mid, d);
            rChildren->set(mid + 1, r, d);
        }
        trueVal = lChildren->trueVal + rChildren->trueVal;
        int left = lChildren->rt;
        val = lChildren->val + rChildren->val - lChildren->get(left, left) * rChildren->get(left + 1, left + 1);
    }
};

int check(char c)
{
    return (c == 'B') ? 1 : 0;
}

void solve()
{
    int n;
    std::cin >> n;
    Node* root = new Node(1, 1 << 20);
    for (int i = 0; i < n; ++i)
    {
        char c;
        int l, r;
        std::cin >> c >> l >> r;
        root->set(l + MAX_N, l + r - 1 + MAX_N, check(c));
        printf("%d %d\n", root->val, root->trueVal);
    }
}

int main () {
    solve();
    return 0;
}
