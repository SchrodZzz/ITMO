package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

const bufSize = 8196

const prefix = ""
const fileName = "fullham"

//	Dirac + ore
func solve() {
	n := readInt32()

	adjMatrix := make([][]bool, n)
	for i := range adjMatrix {
		str := readLine()
		adjMatrix[i] = make([]bool, n)
		for j, ch := range str {
			adjMatrix[i][j] = ch == '1'
			adjMatrix[j][i] = ch == '1'
		}
	}

	q := make(queue, n)

	for i := range q {
		q[i] = i
	}

	n *= n - 1

	for i := 0; i < n; i++ {
		if !adjMatrix[q[0]][q[1]] {
			last := 2
			for ; !(adjMatrix[q[0]][q[last]] && adjMatrix[q[1]][q[last+1]]); last++ {}
			for j := 0; 1 + j < last - j; j++ {
				q[1 + j], q[last - j] = q[last - j], q[1 + j]
			}
		}
		q.push(q.pop())
	}

	for _, v := range q {
		writeString(v + 1)
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
