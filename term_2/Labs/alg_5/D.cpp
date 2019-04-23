#include <algorithm>
#include <vector>
#include <iostream>

struct Triple
{
    long x;
    int y, z;

    Triple() : x(0), y(0), z(0)
    {}

    Triple(long x, int y, int z)
    {
        this->x = x;
        this->y = y;
        this->z = z;
    }
};

struct Node
{
    long x;
    int y;
    int z;

    Node* left = nullptr;
    Node* right = nullptr;
    Node* parent = nullptr;

    Node(long x, int y, int z, Node* left, Node* right, Node* parent)
    {
        this->x = x;
        this->y = y;
        this->z = z;
        this->left = left;
        this->right = right;
        this->parent = parent;
    }
};

void printTree(Node* x, std::vector<Triple>& data)
{
    if (x != nullptr)
    {
        int idx = x->z - 1;
        data[idx].x = x->parent == nullptr ? 0 : x->parent->z;
        data[idx].y = x->left == nullptr ? 0 : x->left->z;
        data[idx].z = x->right == nullptr ? 0 : x->right->z;
        printTree(x->left, data);
        printTree(x->right, data);
    }
}


void solve()
{
    size_t n;
    std::cin >> n;
    std::vector<Triple> data(n);
    for (int i = 1; i <= n; i++)
    {
        std::cin >> data[i-1].x >> data[i-1].y;
        data[i-1].z = i;
    }
    std::sort(data.begin(), data.end(), [](Triple const &first, Triple const &second) {
        return first.x < second.x;
    });
    Node* root = new Node(data[0].x, data[0].y, data[0].z, nullptr, nullptr, nullptr);
    for (int i = 1; i < n; i++)
    {
        long curX = data[i].x;
        int curY = data[i].y;
        int curZ = data[i].z;
        if (root->y < curY)
        {
            root->right = new Node(curX, curY, curZ, nullptr, nullptr, root);
            root = root->right;
        }
        else
        {
            Node* tmp = root;
            while (tmp->parent != nullptr && tmp->y >= curY)
            {
                tmp = tmp->parent;
            }
            if (tmp->y >= curY)
            {
                root = new Node(curX, curY, curZ, tmp, nullptr, nullptr);
                tmp->parent = root;
            }
            else
            {
                root = new Node(curX, curY, curZ, tmp->right, nullptr, tmp);
                tmp->right = root;
                root->parent = tmp;
                root->left->parent = root;
            }
        }
    }
    std::cout << ("YES") << "\n";
    while (root->parent != nullptr)
    {
        root = root->parent;
    }
    std::vector<Triple> ans(n);
    printTree(root, ans);
    for (auto cur : ans)
    {
        std::cout << cur.x << " " << cur.y << " " << cur.z << "\n";
    }
}

int main () {
    solve();
    return 0;
}


//    /**
//     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
//     **/
//public static void main(String[] args) throws IOException {
//            Locale.setDefault(Locale.US);
//    (new C()).solution();
//    }
//
//    void solution() throws IOException {
//            try (FastScanner reader = new FastScanner()) {
//                solve(reader);
//            }
//    }
//
//    class FastScanner implements AutoCloseable {
//        final BufferedReader br;
//        final PrintWriter pw;
//        StringTokenizer st;
//
//        public FastScanner() throws IOException {
//                br = new BufferedReader(isSysRead
//                                        ? new InputStreamReader(System.in)
//                                        : new FileReader((isTest ? "test" : fileName) + ".in"));
//                pw = isSysRead ? null : new PrintWriter((isTest ? "test" : fileName) + ".out");
//        }
//
//        String next() {
//            while (st == null || !st.hasMoreElements()) {
//                try {
//                    st = new StringTokenizer(br.readLine());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return st.nextToken();
//        }
//
//        int nextInt() {
//            return Integer.parseInt(next());
//        }
//
//        long nextLong() {
//            return Long.parseLong(next());
//        }
//
//        double nextDouble() {
//            return Double.parseDouble(next());
//        }
//
//        String nextLine() {
//            String str = "";
//            try {
//                str = br.readLine();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return str;
//        }
//
//        void printStr(Object str) {
//            if (isSysRead) {
//                System.out.println(String.valueOf(str));
//            } else {
//                pw.println(String.valueOf(str));
//            }
//        }
//
//        public void close() throws IOException {
//                if (!isSysRead) {
//                    pw.close();
//                }
//                br.close();
//        }
//    }
//}
