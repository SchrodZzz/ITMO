package main

import (
	"bufio"
	"fmt"
	"math"
	"os"
	"strconv"
	"strings"
)

const prefix = ""
const fileName = "rainbow"
const bufSize = 100001

func solve() {
	n := readInt32()
	m := readInt32()
	maxW := 0
	edges := make([]edge, m)
	for i := range edges {
		edges[i] = newEdge(readInt32()-1, readInt32()-1, readInt32()-1)
		maxW = max(maxW, edges[i].w+1)
	}

	ok := true
	in := make([]bool, m)
	for ok {
		g := make([][]int, m)
		for i := range g {
			if in[i] {
				dsu := newDSU(n)
				usedColor := make([]bool, maxW)
				for j := range g {
					if i != j && in[j] {
						dsu.unite(edges[j].u, edges[j].v)
						usedColor[edges[j].w] = true
					}
				}
				for j := range g {
					if !in[j] {
						if dsu.get(edges[j].u) != dsu.get(edges[j].v) {
							g[i] = append(g[i], j)
						}
						if !usedColor[edges[j].w] {
							g[j] = append(g[j], i)
						}
					}
				}
			}
		}

		s := make([]int, 0)
		tt := make([]int, 0)
		dsu := newDSU(n)
		usedColor := make([]bool, maxW)
		for i := range g {
			if in[i] {
				dsu.unite(edges[i].u, edges[i].v)
				usedColor[edges[i].w] = true
			}
		}

		for i := range g {
			if !in[i] {
				if dsu.get(edges[i].u) != dsu.get(edges[i].v) {
					s = append(s, i)
				}

				if !usedColor[edges[i].w] {
					tt = append(tt, i)
				}
			}
		}

		from := make([]int, m)
		for i := range from {
			from[i] = -1
		}

		t := findPath(&g, &s, &tt, &from, m)
		if t == -1 {
			ok = false
		} else {
			for t != -1 {
				in[t] = !in[t]
				t = from[t]
			}
			ok = true
		}
	}

	ans := make([]int, 0)
	for i := range in {
		if in[i] {
			ans = append(ans, i+1)
		}
	}

	writeString(len(ans))
	writeSlice(ans)

}

func findPath(g *[][]int, s, tt, from *[]int, m int) int {
	d := make([]int, m)
	for i := range d {
		d[i] = 1337
	}
	q := make(queue, 0)
	for _, i := range *s {
		q.push(i)
		d[i] = 0
		(*from)[i] = -1
	}

	for !q.isEmpty() {
		cur := q.pop()
		for _, e := range (*g)[cur] {
			if d[cur]+1 < d[e] {
				d[e] = d[cur] + 1
				(*from)[e] = cur
				q.push(e)
			}
		}
	}

	minD := 1337
	minV := -1
	for _, i := range *tt {
		if d[i] < minD {
			minD = d[i]
			minV = i
		}
	}

	return minV
}

type DSU struct {
	p, r []int
}

func newDSU(n int) DSU {
	p := make([]int, n)
	r := make([]int, n)
	for i := range p {
		p[i] = i
		r[i] = 0
	}
	return DSU{p: p, r: r}
}

func (q *DSU) get(v int) int {
	if q.p[v] == v {
		return v
	} else {
		q.p[v] = q.get(q.p[v])
		return q.p[v]
	}
}

func (q *DSU) unite(u, v int) {
	pv := q.get(u)
	pu := q.get(v)

	if q.r[pv] < q.r[pu] {
		q.p[pv] = pu
	} else {
		q.p[pu] = pv
		if q.r[pv] == q.r[pu] {
			q.r[pv]++
		}
	}
}

type edge struct {
	u, v, w int
}

func newEdge(u, v, w int) edge {
	return edge{u: u, v: v, w: w}
}

func powed(x int) int {
	return int(math.Pow(2, float64(x)))
}

type pairs []pair

func (t pairs) Len() int {
	return len(t)
}

func (t pairs) Less(i, j int) bool {
	return t[i].b > t[j].b
}

func (t pairs) Swap(i, j int) {
	t[i], t[j] = t[j], t[i]
}

func reverse(arr *[]int) {
	for i, j := 0, len(*arr)-1; i < j; i, j = i+1, j-1 {
		(*arr)[i], (*arr)[j] = (*arr)[j], (*arr)[i]
	}
}

/*   Queue   */
type queue []int

func (q *queue) pop() int {
	old := *q
	x := old[0]
	*q = old[1:]
	return x
}

func (q queue) isEmpty() bool {
	return len(q) == 0
}

func (q *queue) push(x int) {
	*q = append(*q, x)
}

/*   Helping stuff   */
type pair struct {
	a, b int
}

func newPair(a, b int) pair {
	return pair{a, b}
}

func min(a, b int) int {
	if a > b {
		a = b
	}
	return a
}

func max(a, b int) int {
	if a < b {
		a = b
	}
	return a
}

func main() {
	inFile, _ := os.Open(fmt.Sprintf("%s%s.in", prefix, fileName))
	outFile, _ := os.Create(fmt.Sprintf("%s%s.out", prefix, fileName))
	in = bufio.NewReaderSize(inFile, bufSize)
	out = bufio.NewWriter(outFile)

	defer out.Flush()

	solve()
}

/*   Scanner   */
var in *bufio.Reader
var out *bufio.Writer
var stringParts []string

func readLine() string {
	str, _, _ := in.ReadLine()
	return string(str)
}

func readBigLine() string {
	var str string
	fmt.Fscanf(in, "%s\n", &str)
	return str
}

func readNext() string {
	if len(stringParts) == 0 {
		stringParts = strings.Split(readLine(), " ")
	}
	res := stringParts[0]
	stringParts = stringParts[1:]
	return res
}

func readInt32() int {
	res, _ := strconv.ParseInt(readNext(), 10, 32)
	return int(res)
}

func readInt64() int64 {
	res, _ := strconv.ParseInt(readNext(), 10, 64)
	return res
}

func readFloat32() float32 {
	res, _ := strconv.ParseFloat(readNext(), 32)
	return float32(res)
}

func readFloat64() float64 {
	res, _ := strconv.ParseFloat(readNext(), 32)
	return res
}

func writeString(s interface{}) {
	out.WriteString(fmt.Sprintf("%v\n", s))
	out.Flush()
}

func writeSlice(s interface{}) {
	writeString(strings.Trim(strings.Join(strings.Split(fmt.Sprint(s), " "), " "), "[]"))
}

func ln() {
	out.Write([]byte{'\n'})
}
