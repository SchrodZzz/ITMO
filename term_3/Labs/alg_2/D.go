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
	m := readInt32()
	k := readInt32()
	s := readInt32() - 1

	adjList := make([][]pair, n)
	for i := 0; i < m; i++ {
		u, v, w := readInt32()-1, readInt32()-1, readInt32()
		adjList[v] = append(adjList[v], newPair(u, w))
	}

	const inf = 1000000001
	dp := make([][]int, k+1)
	for i := range dp {
		dp[i] = make([]int, n)
		for j := range dp[i] {
			dp[i][j] = inf
		}
	}
	dp[0][s] = 0

	for i := 1; i < k+1; i++ {
		for v := 0; v < n; v++ {
			for _, p := range adjList[v] {
				if dp[i-1][p.v] != inf {
					dp[i][v] = min(dp[i][v], dp[i-1][p.v]+p.w)
				}
			}
		}
	}

	for _, val := range dp[k] {
		if val == inf {
			writeString(-1)
		} else {
			writeString(val)
		}
		ln()
	}

}

/*   Helping stuff   */
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
