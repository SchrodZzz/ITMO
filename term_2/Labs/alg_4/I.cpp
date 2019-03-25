#include <cmath>
#include <iostream>

struct Node
{
    bool isSet = 0;
    long lt = 0;
    long rt = 0;
    long val = 0;
    int setVal = 0;
    long trueVal = 0;
    Node *lChildren = nullptr;
    Node *rChildren = nullptr;

    Node(long a, long b)
    {
        lt = a;
        rt = b;
    }
    void check()
    {
        long mid = (lt + rt) / 2;
        if (lChildren == nullptr)
        {
            lChildren = new Node(lt, mid);
        }
        if (rChildren == nullptr)
        {
            rChildren = new Node(mid + 1, rt);
        }
    }

    long get(long l, long r, long long h)
    {
        if (isSet)
            cal();
        if (lt == rt)
        {
            if (val <= h)
                return lt;
            else
                return lt - 1;
        }
        check();
        if (lChildren->isSet)
        {
            lChildren->cal();
        }
        if (lChildren->val > h)
        {
            return lChildren->get(l, r, h);
        }
        else if (r > (lt + rt) / 2)
            return rChildren->get(l, r, h - lChildren->trueVal);
        else
            return lChildren->get(l, r, h);
    }

    void cal()
    {
        check();
        lChildren->trueVal = rChildren->trueVal = trueVal / 2;
        lChildren->val = rChildren->val = (rChildren->rt - rChildren->lt + 1) * setVal;
        lChildren->setVal = rChildren->setVal = setVal;
        lChildren->isSet = rChildren->isSet = 1;
        setVal = 0;
        isSet = 0;
    }

    void set(long l, long r, int d)
    {
        long mid = (lt + rt) / 2;
        if (l == lt && r == rt)
        {
            trueVal = d * (rt - lt + 1);
            val = d * (rt - lt + 1);
            setVal = d;
            isSet = 1;
            return;
        }
        check();
        if (isSet)
            cal();
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
        val = std::max(lChildren->val, lChildren->trueVal + rChildren->val);
    }
};

int main()
{
    int n;
    std::cin >> n;
    Node *root = new Node(1, (int) pow(2, ((int) log2(n) + ((int) pow(2, (int) log2(n)) != n))));
    char ch;
    while (ch != 'E')
    {
        std::cin >> ch;
        switch (ch)
        {
            case 'I':
                long long a, b, d;
                std::cin >> a >> b >> d;
                root->set(a, b, d);
                break;
            case 'Q':
                long long h;
                std::cin >> h;
                std::cout << root->get(1, n, h) << std::endl;
                break;
        }
    }
    return 0;
}
