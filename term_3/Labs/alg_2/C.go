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

	edgesList := make(map[edge]int)
	for i := 0; i < n; i++ {
		for j := 0; j < n; j++ {
			tmp := readInt32()
			if tmp != 100000 {
				edgesList[newEdge(i, j)] = tmp
			}
		}
	}

	last := -1
	inf := 1000000000
	dp := make([]int, n)
	p := make([]int, n)
	for i := range dp {
		dp[i] = inf
		p[i] = -1
	}
	dp[0] = 0

	for _ = range dp {
		last = -1
		for e, w := range edgesList {
			if dp[e.v] > dp[e.u]+w {
				dp[e.v] = max(-inf, dp[e.u]+w)
				p[e.v] = e.u
				last = e.v
			}
		}
	}

	if last == -1 {
		writeString("NO")
	} else {
		writeString("YES")
		ln()

		ans := make([]int, 0)
		u := last
		for _ = range dp {
			u = p[u]
		}
		for cur := u; ;{
			ans = append(ans, cur+1)
			if p[cur] == ans[0]-1 || (cur == u && len(ans) > 1) {
				break
			}
			cur = p[cur]
		}

		writeString(len(ans))
		ln()

		for i, j := 0, len(ans)-1; i < j; i, j = i+1, j-1 {
			ans[i], ans[j] = ans[j], ans[i]
		}

		writeSlice(ans)
	}
}

/*   Helping stuff   */
type edge struct {
	u, v int
}

func newEdge(u, v int) edge {
	return edge{u, v}
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
