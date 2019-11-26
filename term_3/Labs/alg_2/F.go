package main

import (
	"bufio"
	"container/heap"
	"fmt"
	"os"
	"strconv"
	"strings"
)

const inf int64 = 9223372036854775807 / 4

func solve() {
	n := readInt32()
	m := readInt32()

	adjList := make([][]pair, n)
	for i := 0; i < m; i++ {
		u, v, w := readInt32()-1, readInt32()-1, readInt64()
		adjList[u] = append(adjList[u], newPair(v, w))
		adjList[v] = append(adjList[v], newPair(u, w))
	}

	houses := make([]int, 3)
	for i := range houses {
		houses[i] = readInt32() - 1
	}

	paths := make([]int64, 3)
	for i := range paths {
		s := houses[i]
		t := houses[(i+1)%len(houses)]
		paths[i] = dijkstraMinPath(n, s, t, adjList)
	}

	sums := make([]int64, 3)
	sums[0] = paths[0] + paths[1]
	sums[1] = paths[0] + paths[2]
	sums[2] = paths[1] + paths[2]

	res := inf
	for _, val := range sums {
		if val < res {
			res = val
		}
	}

	if res == inf {
		writeString(-1)
	} else {
		writeString(res)
	}

}

/*   Priority Queue   */
type PriorityQueue []*vertex

func (h PriorityQueue) Len() int           { return len(h) }
func (h PriorityQueue) Less(i, j int) bool { return h[i].w < h[j].w }
func (h PriorityQueue) Swap(i, j int)      { h[i], h[j] = h[j], h[i] }

func (h *PriorityQueue) Push(x interface{}) { *h = append(*h, x.(*vertex)) }

func (h *PriorityQueue) Pop() interface{} {
	old := *h
	n := len(old)
	x := old[n-1]
	*h = old[0 : n-1]
	return x
}

/*   Helping stuff   */
type vertex struct {
	u int
	w int64
}

func newVertex(u int, w int64) *vertex {
	v := vertex{u, w}
	return &v
}

type edge struct {
	u, v int
}

func newEdge(u, v int) edge {
	return edge{u, v}
}

type pair struct {
	v int
	w int64
}

func newPair(v int, w int64) pair {
	return pair{v, w}
}

func dijkstraMinPath(n, s, t int, adjList [][]pair) int64 {
	priorityQueue := make(PriorityQueue, 0)
	heap.Init(&priorityQueue)

	dp := make([]int64, n)
	for i := range dp {
		dp[i] = inf
	}

	dp[s] = 0
	heap.Push(&priorityQueue, newVertex(s, 0))

	for priorityQueue.Len() > 0 {
		vert := heap.Pop(&priorityQueue).(*vertex)
		if vert.w > dp[vert.u] {
			continue
		}
		u := vert.u
		for _, p := range adjList[u] {
			if dp[u]+p.w < dp[p.v] {
				dp[p.v] = dp[u] + p.w
				heap.Push(&priorityQueue, newVertex(p.v, dp[p.v]))
			}
		}
	}

	return dp[t]
}

func min(a, b int64) int64 {
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
}

func writeSlice(s interface{}) {
	writeString(strings.Trim(strings.Join(strings.Split(fmt.Sprint(s), " "), " "), "[]"))
}

func ln() {
	out.Write([]byte{'\n'})
}
