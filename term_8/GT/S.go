package main

import (
	"bufio"
	"fmt"
	"math"
	"os"
	"strconv"
	"strings"
)

const bufSize = 8196
const isStdIO = true

const prefix = ""
const fileNameIn = "input.txt"
const fileNameOut = "output.txt"

func solve() {
	m, n := readInt32(), readInt32()
	a := scanMatrix(m, n)
	b := scanMatrix(m, n)
	res := process(a, b, m, n)
	fmt.Println(len(res))
	for _, v := range res {
		fmt.Println(v.a, v.b)
	}
}

/*  Helper  */

type vector []int
type matrix []vector
type vectorPair []pair
type pair struct {
	a int
	b int
}

func newPair(a int, b int) pair {
	return pair{a, b}
}

func process(a matrix, b matrix, m int, n int) []pair {
	res := make([]pair, 0)
	for i := 0; i < m; i++ {
	out:
		for j := 0; j < n; j++ {
			for k := 0; k < m; k++ {
				if a[i][j] < a[k][j] {
					continue out
				}
			}
			for k := 0; k < n; k++ {
				if b[i][j] < b[i][k] {
					continue out
				}
			}
			for k := 0; k < m; k++ {
				for l := 0; l < n; l++ {
					if a[i][j]+b[i][j] < a[k][l]+b[k][l] {
						continue out
					}
				}
			}
			res = append(res, newPair(i+1, j+1))
		}
	}
	return res
}

func scanMatrix(m int, n int) matrix {
	res := make(matrix, m, m)
	for i := 0; i < m; i++ {
		res[i] = make(vector, n, n)
		for j := 0; j < n; j++ {
			res[i][j] = readInt32()
		}
	}
	return res
}

func transposeMatrix(mat matrix) matrix {
	m := len(mat)
	n := len(mat[0])
	res := make(matrix, n, n)
	for i := 0; i < n; i++ {
		res[i] = make(vector, m, m)
		for j := 0; j < m; j++ {
			res[i][j] = mat[j][i]
		}
	}
	return res
}

func (vec vector) min() int {
	res := math.MaxInt32
	for _, val := range vec {
		if res > val {
			res = val
		}
	}
	return res
}

func (vec vector) max() int {
	res := math.MinInt32
	for _, val := range vec {
		if res < val {
			res = val
		}
	}
	return res
}

func (vec vector) getMaxMin(shouldBeMin bool) int {
	if shouldBeMin {
		return vec.min()
	} else {
		return vec.max()
	}
}

/*   Math   */

func int2bool(val int) bool {
	return val != 0
}

func bool2int(v bool) int {
	if v {
		return 1
	}
	return 0
}

/*   Main   */

func main() {
	if isStdIO {
		in = bufio.NewReaderSize(os.Stdin, bufSize)
		out = bufio.NewWriter(os.Stdout)
	} else {
		inFile, _ := os.Open(fmt.Sprintf("%s%s", prefix, fileNameIn))
		outFile, _ := os.Create(fmt.Sprintf("%s%s", prefix, fileNameOut))
		in = bufio.NewReaderSize(inFile, bufSize)
		out = bufio.NewWriter(outFile)
		defer inFile.Close()
		defer outFile.Close()
	}

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

func writeSlice(s interface{}, sysioIsProblematic bool) {
	val := strings.Trim(strings.Join(strings.Split(fmt.Sprint(s), " "), " "), "[]")
	if sysioIsProblematic {
		fmt.Println(val)
	} else {
		writeString(val)
	}
}

func ln() {
	out.Write([]byte{'\n'})
}
