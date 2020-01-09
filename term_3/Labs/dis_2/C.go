package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
	"strconv"
	"strings"
)

const prefix = ""
const fileName = "matching"
const bufSize = 100001

var p, res []int
var used []bool
var es [][]int

func solve() {
	n := readInt32()
	ws := make(pairs, n)
	for i := range ws {
		ws[i] = newPair(readInt32(), i)
	}

	es = make([][]int, n)
	for i := range ws {
		m := readInt32()
		for j := 0; j < m; j++ {
			es[i] = append(es[i], readInt32()-1)
		}
	}

	sort.Sort(ws)

	p = make([]int, n)
	res = make([]int, n)
	for i:= range ws {
		p[i] = -1
	}
	for i := range ws {
		used = make([]bool, n)
		_ = dfs(ws[n-i-1].b)
	}

	writeSlice(res)

}

func dfs(v int) bool {
	if !used[v] {
		used[v] = true
		for _, u := range es[v] {
			if p[u] == -1 || dfs(p[u]) {
				p[u] = v
				res[v] = u+1
				return true
			}
		}
	}
	return false
}

type pairs []*pair

func (t pairs) Len() int {
	return len(t)
}

func (t pairs) Less(i, j int) bool {
	return t[i].a < t[j].a
}

func (t pairs) Swap(i, j int) {
	t[i], t[j] = t[j], t[i]
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

func newPair(a, b int) *pair {
	return &pair{a, b}
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
