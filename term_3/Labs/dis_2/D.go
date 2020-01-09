package main

import (
	"bufio"
	"fmt"
	"math"
	"os"
	"strconv"
	"strings"
)

const prefix = ""
const fileName = "check"
const bufSize = 100001

func solve() {
	n := readInt32()
	m := readInt32()
	pow := powed(n)
	fst := make([]bool, pow)
	snd := make([]bool, pow)
	vls := make([]int, pow)
	for i := 0; i < m; i++ {
		k := readInt32()
		cnt := 0
		for j := 0; j < k; j++ {
			val := readInt32()-1
			cnt = cnt | powed(val)
		}

		fst[cnt] = true
		vls[cnt] = k
	}

	for i := range vls {
		if fst[i] {
			snd[i] = true
			for j := 0; j < n; j++ {
				snd[i] = snd[i] && snd[i & (^powed(j))]
			}
		}
	}

	if !fst[0] {
		writeString("NO")
		return
	}

	for i := 0; i < pow; i++ {
		if fst[i] != snd[i] {
			writeString("NO")
			return
		}
	}

	for i:= 0; i < pow; i++ {
		for j := 0; j < pow; j++ {
			if fst[i] && fst[j] {
				if vls[i] > vls[j] {
					var check bool
					for k := 0; k < n; k++ {
						if ((i & powed(k)) != 0) && ((j & powed(k)) == 0) {
							if fst[j | powed(k)] {
								check = true
								break
							}
						}
					}
					if !check {
						writeString("NO")
						return
					}
				}
			}
		}
	}

	writeString("YES")

}

func powed(x int) int {
	return int(math.Pow(2, float64(x)))
}

type pairs []*pair

func (t pairs) Len() int {
	return len(t)
}

func (t pairs) Less(i, j int) bool {
	return t[i].a < t[j].a
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
	a, b int
}

func newPair(a, b int) *pair {
	return &pair{a, b}
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