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
	s := readInt32() - 1

	edgesList := make([]edge, 0)
	for i := 0; i < m; i++ {
		edgesList = append(edgesList, newEdge(readInt32()-1, readInt32()-1, readInt64()))
	}

	const inf = 9223372036854775807 / 4
	dp := make([]int64, n)
	for i := range dp {
		dp[i] = inf
	}
	dp[s] = 0

	for _ = range dp {
		for _, e := range edgesList {
			if dp[e.u] < inf && dp[e.v] > dp[e.u] + e.w {
				dp[e.v] = max(dp[e.u] + e.w, -inf)
			}
		}
	}

	for _ = range dp {
		for _, e := range edgesList {
			if dp[e.u] < inf && dp[e.v] > dp[e.u] + e.w {
				dp[e.v] = -inf
			}
		}
	}

	for _, val := range dp {
		switch val {
		case inf:
			writeString("*")
		case -inf:
			writeString("-")
		default:
			writeString(val)
		}
		ln()
	}

}

/*   Helping stuff   */
type edge struct {
	u, v int
	w int64
}

func newEdge(u, v int, w int64) edge {
	return edge{u, v, w}
}

func min(a, b int) int {
	if a > b {
		a = b
	}
	return a
}

func max(a, b int64) int64 {
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
