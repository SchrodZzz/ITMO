package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

const bufSize = 100001

func solve() {
	str := readBigLine()
	n := len(str)

	pf := make([]int, n)
	for i := 1; i < n; i++ {
		p := pf[i-1]
		for p > 0 && str[i] != str[p] {
			p = pf[p-1]
		}
		if str[i] == str[p] {
			p++
		}
		pf[i] = p
	}

	zf := make([]int, n)
	for i := 1; i < n; i++ {
		if pf[i] > 0 {
			zf[i-pf[i]+1] = pf[i]
		}
	}

	zf[0] = n
	i := 1
	for i < n {
		k := i
		if zf[i] > 0 {
			for j := 1; j < zf[i]; j++ {
				if zf[i+j] > zf[j] {
					break
				} else {
					zf[i+j] = min(zf[j], zf[i]-j)
					k = i + j
				}
			}
		}
		i = k + 1
	}

	writeSlice(zf[1:])
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
