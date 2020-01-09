package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
	"strconv"
	"strings"
)

const prefix = ""
const fileName = "schedule"
const bufSize = 100001

func solve() {
	n := readInt32()
	maxPenalty := 0
	ts := make([]int, n)
	tasks := make(tasks, n)
	for i := range tasks {
		d := readInt32()
		w := readInt32()
		maxPenalty += w
		tasks[i] = &task{d: d, w: w}
		ts[i] = i + 1
	}

	sort.Sort(tasks)

	var ans int64 = 0
	for i := range tasks {
		pos := binaryLower(ts, tasks[i].d, 0, len(ts)-1)
		if pos < 0 || pos >= len(ts) {
			continue
		}
		if ts[pos] == tasks[i].d {
			ts = append(ts[:pos], ts[pos+1:]...)
		} else if pos == 0 {
			ans += (int64)(tasks[i].w)
		} else {
			ts = append(ts[:pos-1], ts[pos:]...)
		}
	}

	writeString(ans)
}

func binaryLower(a []int, m, l, r int) int {
	mid := 0
	if l <= r {
		mid = (l + r) / 2;
		if m <= a[mid] {
			return binaryLower(a, m, l, mid-1)
		} else {
			return binaryLower(a, m, mid+1, r)
		}
	} else {
		return l
	}
}

type task struct {
	d, w int
}

type tasks []*task

func (t tasks) Len() int {
	return len(t)
}

func (t tasks) Less(i, j int) bool {
	return t[i].w > t[j].w
}

func (t tasks) Swap(i, j int) {
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
