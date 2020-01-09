package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
	"strconv"
	"strings"
)

const bufSize = 100001

func solve() {
	s := readBigLine()
	str := []byte(s)
	n := len(str)

	suf := make(pairs, n)
	for i := range suf {
		suf[i] = newPair(i, str[i:])
	}

	sort.Sort(suf)

	sufM := make([]int, n)
	for i := range sufM {
		sufM[suf[i].a] = i
	}
	curSize := 0
	lcp := make([]int, n)
	for i := range lcp {
		if curSize > 0 {
			curSize--
		}
		if sufM[i] == n-1 {
			lcp[n-1] = -1
			curSize = 0
		} else {
			j := suf[sufM[i] + 1].a
			for max(i + curSize, j + curSize) < n && str[i + curSize] == str[j + curSize] {
				curSize++
			}
			lcp[sufM[i]] = curSize
		}
	}

	for _, val := range suf {
		writeString(val.a+1)
	}

	ln()

	writeSlice(lcp[:n-1])

}


type pairs []pair

func (t pairs) Len() int {
	return len(t)
}

func (t pairs) Less(i, j int) bool {
	str1 := string(t[i].b)
	str2 := string(t[j].b)
	return str1 < str2
}

func (t pairs) Swap(i, j int) {
	t[i], t[j] = t[j], t[i]
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
	a int
	b []byte
}

func newPair(a int, b []byte) pair {
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
	out.WriteString(fmt.Sprintf("%v ", s))
	out.Flush()
}

func writeSlice(s interface{}) {
	writeString(strings.Trim(strings.Join(strings.Split(fmt.Sprint(s), " "), " "), "[]"))
}

func ln() {
	out.Write([]byte{'\n'})
}
