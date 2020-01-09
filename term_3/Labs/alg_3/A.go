package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

const bufSize = 100001

var hash []int64

func solve() {
	str := readBigLine()
	n := len(str)
	m := readInt32()

	p := 37
	pows := make([]int64, n)
	pows[0] = 1
	for i := 1; i < n; i++ {
		pows[i] = int64(p) * pows[i-1]
	}

	hash = make([]int64, n)
	hash[0] = getVal(str[0])
	for i := 1; i < n; i++ {
		hash[i] = hash[i-1] + getVal(str[i])*pows[i]
	}

	for i := 0; i < m; i++ {
		a := readInt32() - 1
		b := readInt32() - 1
		c := readInt32() - 1
		d := readInt32() - 1

		h1 := getHash(a, b)
		h2 := getHash(c, d)

		if a <= c {
			h1 *= pows[c-a]
		} else {
			h2 *= pows[a-c]
		}

		if h1 == h2 {
			writeString("Yes")
		} else {
			writeString("No")
		}
	}
}

func getVal(ch byte) int64 {
	return int64(ch - '0')
}

func getHash(l int, r int) int64 {
	if l > 0 {
		return hash[r] - hash[l-1]
	}
	return hash[r]
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
	in = bufio.NewReaderSize(os.Stdin, bufSize)
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
