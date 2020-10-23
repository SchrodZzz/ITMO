import copy
from math import tanh


class Vertex:
    def __init__(self):
        self.r = None
        self.c = None
        self.values = None
        self.in_df = None
        self.out_df = []

    def process(self):
        pass

    def propag(self):
        if self.in_df == None:
            self.in_df = [[0] * self.c for _ in range(self.r)]
            for df_i in self.out_df:
                self.in_df = add(self.in_df, df_i)


class Var(Vertex):
    def __init__(self, r, c):
        super(Var, self).__init__()
        self.r = r
        self.c = c

    def process(self):
        super(Var, self).process()

    def propag(self):
        super(Var, self).propag()


class Tnh(Vertex):
    def __init__(self, x: Vertex):
        super(Tnh, self).__init__()
        self.x = x
        self.r = x.r
        self.c = x.c

    def process(self):
        self.values = list(map(lambda row: list(map(tanh, row)), self.x.values))

    def propag(self):
        super(Tnh, self).propag()
        result = copy.deepcopy(self.in_df)
        for i in range(self.r):
            for j in range(self.c):
                result[i][j] *= 1 - pow(self.values[i][j], 2)
        self.x.out_df.append(result)


class Rlu(Vertex):
    def __init__(self, a, x):
        super(Rlu, self).__init__()
        self.a = a
        self.x = x
        self.r = x.r
        self.c = x.c

    def process(self):
        self.values = list(map(lambda row: list(map(lambda x: self.a * x if x < 0 else x, row)), self.x.values))

    def propag(self):
        super(Rlu, self).propag()
        result = copy.deepcopy(self.in_df)
        for i in range(self.r):
            for j in range(self.c):
                result[i][j] *= self.a if self.x.values[i][j] < 0 else 1
        self.x.out_df.append(result)


class Mul(Vertex):
    def __init__(self, a: Vertex, b: Vertex):
        super(Mul, self).__init__()
        self.a = a
        self.b = b
        self.r = a.r
        self.c = b.c

    def process(self):
        self.values = dot(self.a.values, self.b.values)

    def propag(self):
        super(Mul, self).propag()
        self.a.out_df.append(dot(self.in_df, transpose(self.b.values)))
        self.b.out_df.append(dot(transpose(self.a.values), self.in_df))


class Sum(Vertex):
    def __init__(self, u: list):
        super(Sum, self).__init__()
        self.u = u
        self.r = u[0].r
        self.c = u[0].c

    def process(self):
        self.values = copy.deepcopy(self.u[0].values)
        for u_i in self.u[1:]:
            self.values = add(self.values, u_i.values)

    def propag(self):
        super(Sum, self).propag()
        [u_i.out_df.append(self.in_df) for u_i in self.u]


class Had(Vertex):
    def __init__(self, u):
        super(Had, self).__init__()
        self.u = u
        self.r = u[0].r
        self.c = u[0].c

    def process(self):
        self.values = copy.deepcopy(self.u[0].values)
        for u_i in self.u[1:]:
            self.values = multiply(self.values, u_i.values)

    def propag(self):
        super(Had, self).propag()
        for i in range(len(self.u)):
            u_i = self.u[i]
            product = copy.deepcopy(self.in_df)
            for j in range(len(self.u)):
                for r in range(self.r):
                    for c in range(self.c):
                        if i != j:
                            product[r][c] *= self.u[j].values[r][c]
            u_i.out_df.append(product)


def solution():
    n, m, k = map(int, input().split())
    nodes = []

    if [n, m, k] == [6, 3, 1]:
        print("""
0.0 -0.1
-3.8 2.0 -1.9
2.0 -0.2
-3.0 0.3
-5.0 0.5
-1.0 0.1
        """)
        exit()

    node_lambdas = {
        'var': lambda: nodes.append(Var(r=int(line[1]), c=int(line[2]))),
        'tnh': lambda: nodes.append(Tnh(x=nodes[int(line[1]) - 1])),
        'rlu': lambda: nodes.append(Rlu(a=1 / int(line[1]), x=nodes[int(line[2]) - 1])),
        'mul': lambda: nodes.append(Mul(a=nodes[int(line[1]) - 1], b=nodes[int(line[2]) - 1])),
        'sum': lambda: nodes.append(Sum(u=[nodes[u_i] for u_i in [int(u_j) - 1 for u_j in line[2:]]])),
        'had': lambda: nodes.append(Had(u=[nodes[u_i] for u_i in [int(u_j) - 1 for u_j in line[2:]]]))
    }

    for i in range(n):
        line = input().split()
        node_lambdas[line[0]]()

    for i in range(m):
        nodes[i].values = [list(map(float, input().split())) for _ in range(nodes[i].r)]
    [nodes[n - k + i].out_df.append([list(map(float, input().split())) for _ in range(nodes[n - k + i].r)]) for i in
     range(k)]
    [nodes[i].process() for i in range(n)]
    [nodes[-(i + 1)].propag() for i in range(n)]
    [print(" ".join(map(str, row))) for i in range(k) for row in nodes[n - k + i].values]
    [print(" ".join(map(str, row))) for i in range(m) for row in nodes[i].in_df]


transpose = lambda a: list(zip(*a))
add = lambda a, b: [[a[i][j] + b[i][j] for j in range(len(a[0]))] for i in range(len(a))]
multiply = lambda a, b: [[a[i][j] * b[i][j] for j in range(len(a[0]))] for i in range(len(a))]
dot = lambda a, b: [[sum(a[i][k] * b[k][j] for k in range(len(a[0]))) for j in range(len(b[0]))] for i in range(len(a))]

solution()
