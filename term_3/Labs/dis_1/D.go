package main

import (
	"bufio"
	"fmt"
	"math/rand"
	"os"
	"strconv"
	"strings"
	"time"
)

const bufSize = 8196

const prefix = ""
const fileName = "guyaury"


func solve() {
	n := readInt32()

	adjMatrix := make([][]bool, n)
	for i := range adjMatrix {
		str := readLine()
		adjMatrix[i] = make([]bool, n)
		for j, ch := range str {
			adjMatrix[i][j] = ch == '1'
			adjMatrix[j][i] = ch == '0'
		}
	}

	q := make(queue, n)
	for i := range q {
		q[i] = i
	}

	var ans queue
	rand.Seed(time.Now().UnixNano())
	for {
		ans = make(queue, 0)
		rand.Shuffle(n, func(i, j int) {q[i], q[j] = q[j], q[i]})
		for i := range q {
			for j := 0; j < i; j++ {
				if !adjMatrix[ans[j]][q[i]] {
					ans = append(ans, 0)
					copy(ans[j+1:], ans[j:])
					ans[j] = q[i]
					break
				}
			}
			if len(ans) == i {
				ans.push(q[i])
			}
		}
		if adjMatrix[ans[len(ans)-1]][ans[0]] {
			break
		}
	}

	for _, v := range ans {
		writeString(v+1)
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
	defer inFile.Close()
	defer outFile.Close()

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
