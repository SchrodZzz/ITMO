package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

const bufSize = 8196

func solve() {
	n := readInt32()

	arr := make(lamps, n)
	tmp := make(lamps, n)
	for i := range arr {
		arr[i] = i + 1
	}

	//sort.Sort(arr)

	for k := 1; k < n; k *= 2 {
		for l := 0; l+k < n; l += 2*k {
			m, i, j, r, bord := l, l, l+k, l+k, l+2*k
			if bord > n {
				bord = n
			}

			for ; i < r && j < bord; m++ {
				if arr.Less(i, j) {
					tmp[m] = arr[i]
					i++
				} else {
					tmp[m] = arr[j]
					j++
				}
			}

			for ; i < r; i, m = i+1, m+1 {
				tmp[m] = arr[i]
			}
			for ; j < bord; j, m = j+1, m+1 {
				tmp[m] = arr[j]
			}
			for idx := l; idx < bord; idx++ {
				arr[idx] = tmp[idx]
			}
		}
	}

	for i := 1; i < n; i++ {
		if arr.Less(i, i-1) {
			for j := 0; j < n+1; j++ {
				writeString(0)
			}
			return
		}
	}

	writeString(0)
	writeSlice(arr)
}

type lamps []int

func (l lamps) Len() int {
	return len(l)
}

func (l lamps) Less(i, j int) bool {
	writeString(fmt.Sprintf("1 %d %d\n", l[i], l[j]))
	out.Flush()
	res := readNext()
	for res != "YES" && res != "NO" {
		res = readNext()
	}
	return res == "YES"
}

func (l lamps) Swap(i, j int) {
	l[i], l[j] = l[j], l[i]
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
