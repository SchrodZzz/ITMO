package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func solve() {
	n := readInt32()
	r := readInt32() - 1

	const undefined = -1
	cnt := make([]pair, n)
	isLeaf := make([]bool, n)
	parentOf := make([]int, n)
	for i := range cnt {
		cnt[i] = newPair(0, -1)
		isLeaf[i] = true
		parentOf[i] = undefined

	}
	cnt[r].b++

	edges := make(map[edge]int)
	adjList := make([][]int, n)
	for i := 0; i < n-1; i++ {
		u, v := readInt32()-1, readInt32()-1
		cnt[u].b++
		adjList[v] = append(adjList[v], u)
		adjList[u] = append(adjList[u], v)
		edges[newEdge(u, v)] = i
		edges[newEdge(v, u)] = i
	}

	adjListToRoot := make([][]int, n)
	adjListToRootT := make([][]int, n)
	q := make(queue, 0)
	q.add(r)
	for !q.isEmpty() {
		u := q.poll()
		for _, v := range adjList[u] {
			if parentOf[v] == undefined && v != r {
				adjListToRoot[v] = append(adjListToRoot[v], u)
				adjListToRootT[u] = append(adjListToRootT[u], v)
				isLeaf[u] = false
				parentOf[v] = u
				q.add(v)
			}
		}
	}

	dp := make([]int, n)
	for i := range dp {
		if isLeaf[i] {
			q.add(i)
			dp[i] = 0
		} else {
			dp[i] = -1
		}
		cnt[i].b = len(adjListToRootT[i])
	}

	for !q.isEmpty() {
		u := q.poll()
		for _, v := range adjListToRoot[u] {
			cnt[v].a++
			if cnt[v].a == cnt[v].b {
				num := 0
				for _, children := range adjListToRootT[v] {
					num ^= dp[children] + 1
				}
				dp[v] = num
				q.add(v)
			}
		}
	}

	if dp[r] == 0 {
		writeString(2)
	} else {
		writeString(1)

		ans := -1
		coef := make([]int, n)
		coef[r] = 0
		q.add(r)
		for !q.isEmpty() && ans == -1 {
			u := q.poll()
			for _, v := range adjListToRootT[u] {
				if v != parentOf[u] {
					cur := dp[u] ^ (dp[v] + 1) ^ coef[u]
					if cur == 0 {
						ans = edges[newEdge(u, v)] + 1
						break
					}
					coef[v] = cur - 1
					q.add(v)
				}
			}
		}

		writeString(ans)
	}

}

/*   Queue   */
type queue []int

func (q *queue) poll() int {
	old := *q
	x := old[0]
	*q = old[1:]
	return x
}

func (q queue) isEmpty() bool {
	return len(q) == 0
}

func (q *queue) add(x int) {
	*q = append(*q, x)
}

/*   Helping stuff   */
type edge struct {
	u, v int
}

func newEdge(u, v int) edge {
	return edge{u, v}
}

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
	in = bufio.NewReader(os.Stdin)
	out = bufio.NewWriter(os.Stdout)

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
	out.WriteString(fmt.Sprintf("%v ", s))
	out.Flush()
}

func writeSlice(s interface{}) {
	writeString(strings.Trim(strings.Join(strings.Split(fmt.Sprint(s), " "), " "), "[]"))
}

func ln() {
	out.Write([]byte{'\n'})
}

/*
11 11
1 5
4 8
5 8
8 11
2 6
3 6
6 9
9 11
7 10
10 11
*/
