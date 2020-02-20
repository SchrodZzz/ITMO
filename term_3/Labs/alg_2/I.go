package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func solve() {
	for n := readInt32(); n != 0; n = readInt32() {
		m := readInt32()

		cnt := make([]pair, n)
		for i := range cnt {
			cnt[i] = newPair(0, 0)
		}
		adjListT := make([][]int, n)
		for i := 0; i < m; i++ {
			u, v := readInt32()-1, readInt32()-1
			cnt[u].b++
			adjListT[v] = append(adjListT[v], u)
		}

		q := make(queue, 0)
		dp := make([]int, n)
		for i := range dp {
			if cnt[i].b == 0 {
				q.push(i)
				dp[i] = 0
			} else {
				dp[i] = -1
			}
		}

		for !q.isEmpty() {
			u := q.pop()
			if dp[u] == 0 {
				for _, v := range adjListT[u] {
					if dp[v] == -1 {
						dp[v] = 1
						q.push(v)
					}
				}
			} else if dp[u] == 1 {
				for _, v := range adjListT[u] {
					cnt[v].a += 1
					if cnt[v].a == cnt[v].b && dp[v] == -1 {
						dp[v] = 0
						q.push(v)
					}
				}
			}
		}

		//fmt.Printf("\n>>> %v and %v and %v <<<\n", dp, cnt, adjListT)
		for i := 0; i < n; i++ {
			if dp[i] == 1 {
				writeString("FIRST")
			} else if dp[i] == 0 {
				writeString("SECOND")
			} else {
				writeString("DRAW")
			}
			ln()
		}
		ln()
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