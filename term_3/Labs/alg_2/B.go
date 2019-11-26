package main

import (
	"bufio"
	"container/heap"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func solve() {
	n := readInt32()
	m := readInt32()

	adjList := make([][]pair, n)
	priorityQueue := make(PriorityQueue, 0)
	heap.Init(&priorityQueue)
	for i := 0; i < n; i++ {
		adjList[i] = make([]pair, 0)
	}
	for i := 0; i < m; i++ {
		u, v, w := readInt32()-1, readInt32()-1, readInt32()
		adjList[u] = append(adjList[u], newPair(v, w))
		adjList[v] = append(adjList[v], newPair(u, w))
	}

	const inf = 1000000001
	dp := make([]int, n)
	for i := range dp {
		dp[i] = inf
	}

	dp[0] = 0
	heap.Push(&priorityQueue, newVertex(0, 0))

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
		//for _, val := range priorityQueue {
		//	writeString(*val)
		//}
		//ln()
	}

	writeSlice(dp)

}

/*   Priority Queue   */
type PriorityQueue []*vertex

func (h PriorityQueue) Len() int           { return len(h) }
func (h PriorityQueue) Less(i, j int) bool { return h[i].w < h[j].w }
func (h PriorityQueue) Swap(i, j int)      { h[i], h[j] = h[j], h[i] }

func (h *PriorityQueue) Push(x interface{}) {*h = append(*h, x.(*vertex))}

func (h *PriorityQueue) Pop() interface{} {
	old := *h
	n := len(old)
	x := old[n-1]
	*h = old[0 : n-1]
	return x
}

/*   Helping stuff   */
type vertex struct {
	u, w int
}

func newVertex(u, w int) *vertex {
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
	v, w int
}

func newPair(v, w int) pair {
	return pair{v, w}
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