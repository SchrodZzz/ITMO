#include <iostream>
#include <algorithm>
#include <string>
#include <vector>
#include <sstream>

struct Window
{
    int x1, y1, x2, y2;

    Window(int _x1, int _y1, int _x2, int _y2)
    {
        x1 = _x1;
        y1 = _y1;
        x2 = _x2;
        y2 = _y2;
    }
};

struct Node
{
    int lt;
    int rt;
    int val = 0;
    int setVal = 0;
    Node* lChildren{};
    Node* rChildren{};

    Node(int a, int b)
    {
        lt = a;
        rt = b;
    }

    void makeTree()
    {
        if (lt == rt)
            return;
        int mid = (lt + rt) / 2;
        lChildren = new Node(lt, mid);
        lChildren->makeTree();
        rChildren = new Node(mid + 1, rt);
        rChildren->makeTree();
    }

    int get()
    {
        if (lt == rt)
            return lt;
        if (setVal != 0)
        {
            setVals();
        }
        if (lChildren->setVal != 0)
        {
            lChildren->setVals();
        }
        if (lChildren->val == val)
        {
            return lChildren->get();
        }
        if (rChildren->setVal != 0)
        {
            rChildren->setVals();
        }
        if (rChildren->val == val)
        {
            return rChildren->get();
        }
        return 0;
    }

    void set(int l, int r, int d)
    {
        int mid = (lt + rt) / 2;
        if (l == lt && r == rt)
        {
            val += d;
            setVal += d;
            return;
        }
        if (setVal != 0)
            setVals();
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
        val = std::max(lChildren->val, rChildren->val);
    }

    void setVals()
    {
        if (lt == rt)
        {
            return;
        }
        lChildren->val += setVal;
        rChildren->val += setVal;
        lChildren->setVal += setVal;
        rChildren->setVal += setVal;
        setVal = 0;
    }
};

bool compare(Window* a, Window* b) {
    if (a->x1 != b->x1)
        return a->x1 < b->x1;
    else
        return a->y1 > b->y1;
}

int main()
{
    int n;
    std::cin >> n;
    Node* root = new Node(1, 1 << 20);
    root->makeTree();
    std::vector <Window*> windows;
    for (int i = 0; i < n; ++i)
    {
        int a,b,c,d;
        std::cin >> a >> b >> c >>d;
        windows.push_back(new Window(a, 1, b, d));
        windows.push_back(new Window(c, -1, b, d));
    }
    std::sort(windows.begin(), windows.end(), compare);
//        for (Window cur : windows) {
//            System.out.println(cur.x1 +" "+cur.y1 +" "+cur.x2 +" "+cur.y2 +" ");
//        }
    int cnt = 0;
    std::pair <int, int> ans;
    for (int i = 0; i < (n << 1); ++i)
    {
        int MAX_VAL = 200001;
        root->set(windows[i]->x2 + MAX_VAL, windows[i]->y2 + MAX_VAL, windows[i]->y1);
        if (root->val > cnt)
        {
            cnt = root->val;
            ans.first = windows[i]->x1;
            ans.second = root->get() - MAX_VAL;
        }
    }
    printf("%d\n%d %d\n", cnt, ans.first, ans.second);
    return 0;
}
